package com.aether.entities;

import com.aether.Aether;
import com.aether.blocks.AetherBlocks;
import com.aether.entities.block.FloatingBlockEntity;
import com.aether.entities.hostile.*;
import com.aether.entities.passive.*;
import com.aether.entities.projectile.EnchantedDartEntity;
import com.aether.entities.projectile.GoldenDartEntity;
import com.aether.entities.projectile.PoisonDartEntity;
import com.aether.entities.projectile.PoisonNeedleEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.entity.*;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import java.util.Random;

import static net.minecraft.world.entity.MobSpawnType.SPAWNER;

public class AetherEntityTypes {
    public static final EntityType<AechorPlantEntity> AECHOR_PLANT;
    public static final EntityType<FlyingCowEntity> FLYING_COW;
    public static final EntityType<AerbunnyEntity> AERBUNNY;
    public static final EntityType<MoaEntity> MOA;
    public static final EntityType<PhygEntity> PHYG;
    public static final EntityType<SheepuffEntity> SHEEPUFF;
    public static final EntityType<CockatriceEntity> COCKATRICE;
    public static final EntityType<ChestMimicEntity> CHEST_MIMIC;
    public static final EntityType<FloatingBlockEntity> FLOATING_BLOCK;
    public static final EntityType<GoldenDartEntity> GOLDEN_DART;
    public static final EntityType<EnchantedDartEntity> ENCHANTED_DART;
    public static final EntityType<PoisonDartEntity> POISON_DART;
    public static final EntityType<PoisonNeedleEntity> POISON_NEEDLE;
//    public static EntityType<WhirlwindEntity> WHIRLWIND;
    public static final EntityType<AerwhaleEntity> AERWHALE;
//    public static EntityType<MiniCloudEntity> MINI_CLOUD;
//    public static EntityType<FireMinionEntity> FIRE_MINION;
//    public static EntityType<CrystalEntity> CRYSTAL;
//    public static EntityType<PhoenixArrowEntity> PHOENIX_ARROW;
    public static final EntityType<SwetEntity> BLUE_SWET;
    public static final EntityType<SwetEntity> PURPLE_SWET;
    public static final EntityType<SwetEntity> WHITE_SWET;
    public static final EntityType<SwetEntity> GOLDEN_SWET;

    static {
        AECHOR_PLANT = register("aechor_plant", MobCategory.MONSTER, EntityDimensions.scalable(1.0F, 1.0F), (entityType, world) -> new AechorPlantEntity(world));
        FLYING_COW = register("flying_cow", MobCategory.CREATURE, EntityDimensions.scalable(0.9F, 1.3F), (entityType, world) -> new FlyingCowEntity(world));
        AERBUNNY = register("aerbunny", MobCategory.CREATURE, EntityDimensions.scalable(0.55F, 0.55F), (entityType, world) -> new AerbunnyEntity(world));
        MOA = register("moa", MobCategory.CREATURE, EntityDimensions.scalable(1.0F, 2.0F), (entityType, world) -> new MoaEntity(world));
        PHYG = register("phyg", MobCategory.CREATURE, EntityDimensions.scalable(0.9F, 1.3F), (entityType, world) -> new PhygEntity(world));
        SHEEPUFF = register("sheepuff", MobCategory.CREATURE, EntityDimensions.scalable(0.9F, 1.3F), (entityType, world) -> new SheepuffEntity(world));
        COCKATRICE = register("cockatrice", MobCategory.MONSTER, EntityDimensions.scalable(1.0F, 2.0F), (entityType, world) -> new CockatriceEntity(world));
        CHEST_MIMIC = register("chest_mimic", MobCategory.MONSTER, EntityDimensions.scalable(1.0F, 2.0F), (entityType, world) -> new ChestMimicEntity(world));
        FLOATING_BLOCK = register("floating_block", 160, 20, true, EntityDimensions.scalable(0.98F, 0.98F), FloatingBlockEntity::new);
        GOLDEN_DART = register("golden_dart", MobCategory.MISC, EntityDimensions.scalable(0.5F, 0.5F), (entityType, world) -> new GoldenDartEntity(world));
        ENCHANTED_DART = register("enchanted_dart", MobCategory.MISC, EntityDimensions.scalable(0.5F, 0.5F), (entityType, world) -> new EnchantedDartEntity(world));
        POISON_DART = register("poison_dart", MobCategory.MISC, EntityDimensions.scalable(0.5F, 0.5F), (entityType, world) -> new PoisonDartEntity(world));
        POISON_NEEDLE = register("poison_needle", MobCategory.MISC, EntityDimensions.scalable(0.5F, 0.5F), (entityType, world) -> new PoisonNeedleEntity(world));
        AERWHALE = register("aerwhale", MobCategory.CREATURE, EntityDimensions.scalable(3.0F, 1.2F), (entityType, world) -> new AerwhaleEntity(world));
//        WHIRLWIND = register("whirlwind", ...);
//        MINI_CLOUD = register("mini_cloud", ...);
//        FIRE_MINION = register("fire_minion", ...);
//        CRYSTAL = register("crystal", ...);
//        PHOENIX_ARROW = register("phoenix_arrow", ...);
        BLUE_SWET = register("blue_swet", MobCategory.MONSTER, EntityDimensions.scalable(2.0F, 2.0F), (entityType, world) -> new BlueSwetEntity(world));
        PURPLE_SWET = register("purple_swet", MobCategory.MONSTER, EntityDimensions.scalable(2.0F, 2.0F), (entityType, world) -> new PurpleSwetEntity(world));
        WHITE_SWET = register("white_swet", MobCategory.MONSTER, EntityDimensions.scalable(2.0F, 2.0F), (entityType, world) -> new WhiteSwetEntity(world));
        GOLDEN_SWET = register("golden_swet", MobCategory.MONSTER, EntityDimensions.scalable(2.0F, 2.0F), (entityType, world) -> new GoldenSwetEntity(world));
    }

    public static void init() {
        // Register Entity Attribute Data and Spawn Restrictions - TODO
        FabricDefaultAttributeRegistry.register(MOA, MoaEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(FLYING_COW, FlyingCowEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(SHEEPUFF, SheepuffEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(AERBUNNY, AerbunnyEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(AECHOR_PLANT, AechorPlantEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(PHYG, PhygEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(COCKATRICE, CockatriceEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(AERWHALE, AerwhaleEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(CHEST_MIMIC, ChestMimicEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(BLUE_SWET, SwetEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(PURPLE_SWET, SwetEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(WHITE_SWET, SwetEntity.initAttributes());
        FabricDefaultAttributeRegistry.register(GOLDEN_SWET, SwetEntity.initAttributes());

        // Don't seem to spawn if there is a restriction, i'm not sure but maybe it's because of their size?
        //SpawnRestriction.register(AERWHALE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getAnimalData);
        SpawnPlacements.register(SHEEPUFF, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getAnimalData);
        SpawnPlacements.register(PHYG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getAnimalData);
        SpawnPlacements.register(AERBUNNY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getAnimalData);
        SpawnPlacements.register(MOA, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getAnimalData);
        SpawnPlacements.register(FLYING_COW, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getAnimalData);
        SpawnPlacements.register(COCKATRICE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getHostileData);
        SpawnPlacements.register(AECHOR_PLANT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getHostileData);
        SpawnPlacements.register(BLUE_SWET, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getHostileData);
        SpawnPlacements.register(PURPLE_SWET, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getHostileData);
        SpawnPlacements.register(WHITE_SWET, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getHostileData);
        SpawnPlacements.register(GOLDEN_SWET, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherEntityTypes::getHostileData);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 6);
    }

    public static <X extends Entity> EntityType<X> register(String name, int trackingDistance, int updateIntervalTicks, boolean alwaysUpdateVelocity, EntityDimensions size, EntityType.EntityFactory<X> factory) {
        return Registry.register(Registry.ENTITY_TYPE, Aether.locate(name), FabricEntityTypeBuilder.create(MobCategory.MISC, factory).trackRangeBlocks(trackingDistance).trackedUpdateRate(updateIntervalTicks).forceTrackedVelocityUpdates(alwaysUpdateVelocity).dimensions(size).disableSaving().build());
    }

    public static <X extends Entity> EntityType<X> register(String name, MobCategory category, EntityDimensions size, EntityType.EntityFactory<X> factory) {
        return Registry.register(Registry.ENTITY_TYPE, Aether.locate(name), FabricEntityTypeBuilder.create(category, factory).dimensions(size).build());
    }

    public static boolean getAnimalData(EntityType<? extends Entity> entityType, LevelAccessor WorldAccess, MobSpawnType SpawnReason, BlockPos blockPos, Random random) {
        return WorldAccess.getBlockState(blockPos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK && WorldAccess.getRawBrightness(blockPos, 0) > 8 && (SpawnReason == SPAWNER || WorldAccess.getBlockState(blockPos.below()).isValidSpawn(WorldAccess, blockPos, entityType));
    }

    public static boolean getHostileData(EntityType<? extends Entity> entityType_1, ServerLevelAccessor WorldAccess_1, MobSpawnType SpawnReason, BlockPos blockPos_1, Random random_1) {
        return WorldAccess_1.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(WorldAccess_1, blockPos_1, random_1) && (SpawnReason == SPAWNER || WorldAccess_1.getBlockState(blockPos_1.below()).isValidSpawn(WorldAccess_1, blockPos_1, entityType_1));
    }
}
