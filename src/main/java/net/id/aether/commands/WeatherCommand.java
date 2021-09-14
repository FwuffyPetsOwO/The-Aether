package net.id.aether.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import net.id.aether.duck.ServerWorldDuck;
import net.id.aether.world.dimension.AetherDimension;
import net.id.aether.world.weather.AetherWeatherController;
import net.id.aether.world.weather.WeatherController;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import static net.id.aether.Aether.MOD_ID;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * The command to set weather in the Aether.<br>
 * <br>
 * This "overlaps" with vanilla on purpose, it is unlikely to conflict because of the required "aether" keyword.<br>
 * <br>
 * Usage:<br>
 * /weather aether set (biome) (weather) (nbt)<br>
 * /weather aether get (biome) (weather)
 */
final class WeatherCommand{
    /**
     * Suggests all the Aether biomes.
     */
    private static final SuggestionProvider<ServerCommandSource> BIOME_SUGGESTER = (context, builder)->{
        var registry = context.getSource().getServer().getRegistryManager().get(Registry.BIOME_KEY);
        registry.stream()
            .map(registry::getId)
            .filter(Objects::nonNull)
            .filter((biome)->biome.getNamespace().equals(MOD_ID))
            .map(Identifier::toString)
            .forEach(builder::suggest);
        return builder.buildFuture();
    };
    
    /**
     * Suggests all values of the weather.
     */
    private static final SuggestionProvider<ServerCommandSource> WEATHER_SUGGESTER = (context, builder)->{
        AetherWeatherController.getControllers().forEach((controller)->
            builder.suggest(controller.getIdentifier().toString())
        );
        return builder.buildFuture();
    };
    
    static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(
            literal("weather").requires((source)->source.hasPermissionLevel(2))
                .then(literal("aether")
                    .then(literal("set")
                        .then(argument("biome", IdentifierArgumentType.identifier()).suggests(BIOME_SUGGESTER)
                            .then(argument("type", IdentifierArgumentType.identifier()).suggests(WEATHER_SUGGESTER)
                                .then(argument("data", NbtCompoundArgumentType.nbtCompound())
                                    .executes(WeatherCommand::executeSet)
                                )
                            )
                        )
                    )
                    .then(literal("get")
                        .then(argument("biome", IdentifierArgumentType.identifier()).suggests(BIOME_SUGGESTER)
                            .then(argument("type", IdentifierArgumentType.identifier()).suggests(WEATHER_SUGGESTER)
                                .executes(WeatherCommand::executeGet)
                            )
                        )
                    )
                )
        );
    }
    
    /**
     * Gets an instance of the biome from the issued command.
     *
     * @param context The command context
     * @return The biome or null if it failed
     */
    private static Biome getBiome(CommandContext<ServerCommandSource> context){
        var id = IdentifierArgumentType.getIdentifier(context, "biome");
        if(!id.getNamespace().equals(MOD_ID)){
            context.getSource().sendError(new TranslatableText("commands.aether.weather.invalid_biome", id.toString()));
            return null;
        }
        var registry = context.getSource().getServer().getRegistryManager().get(Registry.BIOME_KEY);
        var biome = registry.get(id);
        if(biome == null){
            context.getSource().sendError(new TranslatableText("commands.aether.weather.invalid_biome", id.toString()));
            return null;
        }
        return biome;
    }
    
    /**
     * Gets the type of weather from the issued command.
     *
     * @param context The command context
     * @return The weather type or null if it failed
     */
    private static <T> WeatherController<T> getWeatherType(AetherWeatherController controller, CommandContext<ServerCommandSource> context){
        var string = IdentifierArgumentType.getIdentifier(context, "type");
        Optional<WeatherController<T>> type = controller.getController(string);
        return type.orElseGet(()->{
            context.getSource().sendError(new TranslatableText("commands.aether.weather.invalid_type", string));
            return null;
        });
    }
    
    /**
     * Gets the instance of the Aether Server world.
     *
     * @param context The command context
     * @return A bi-type world instance
     */
    @SuppressWarnings("unchecked")
    private static <T extends ServerWorld & ServerWorldDuck> T getAetherWorld(CommandContext<ServerCommandSource> context){
        return (T)context.getSource().getServer().getWorld(AetherDimension.AETHER_WORLD_KEY);
    }
    
    /**
     * Handles the `get` command.
     *
     * @param context The command context
     * @return The command status
     */
    private static int executeGet(CommandContext<ServerCommandSource> context){
        try{
            var world = getAetherWorld(context);
            var controller = world.the_aether$getWeatherController();
    
            var type = getWeatherType(controller, context);
            if(type == null){
                // Status is already sent to the user
                return 0;
            }
    
            var biome = getBiome(context);
            if(biome == null){
                // Status is already sent to the user
                return 0;
            }
    
            var source = context.getSource();
    
            // Get the weather, empty if the biome doesn't support that weather type
            var data = controller.getWeather(biome, type);
            if(data.isPresent()){
                // Return the amount of time the current state lasts
                source.sendFeedback(new TranslatableText(
                    "commands.aether.weather.get",
                    type.getIdentifier(),
                    data.get()
                ), false);
                return 1;
            }else{
                // The biome doesn't support that weather
                source.sendFeedback(new TranslatableText("commands.aether.weather.get.failed"), false);
                return 0;
            }
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }
    
    /**
     * Handles the set command.
     *
     * @param context The context of the command
     * @return The command result
     */
    private static int executeSet(CommandContext<ServerCommandSource> context){
        try{
            var world = getAetherWorld(context);
            var controller = world.the_aether$getWeatherController();
    
            var type = getWeatherType(controller, context);
            if(type == null){
                // Status is already sent to the user
                return 0;
            }
    
            var biome = getBiome(context);
            if(biome == null){
                // Status is already sent to the user
                return 0;
            }
    
            var data = NbtCompoundArgumentType.getNbtCompound(context, "data");
    
            var source = context.getSource();
            if(controller.setWeather(biome, type, data)){
                source.sendFeedback(new TranslatableText("commands.aether.weather.set"), false);
                return 1;
            }else{
                source.sendFeedback(new TranslatableText("commands.aether.weather.set.failed"), false);
                return 0;
            }
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }
}
