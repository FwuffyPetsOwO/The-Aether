package net.id.aether.entities.hostile;

import net.id.aether.blocks.AetherBlocks;
import net.id.aether.entities.passive.AetherAnimalEntity;
import net.id.aether.entities.projectile.PoisonNeedleEntity;
import net.id.aether.items.AetherItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class AechorPlantEntity extends AetherAnimalEntity implements RangedAttackMob {
    public final int poisonRemaining;
    public float sinage;
    public int size;

    public AechorPlantEntity(EntityType<? extends AechorPlantEntity> entityType, World world) {
        super(entityType, world);

        this.size = this.random.nextInt(4) + 1;
        this.sinage = this.random.nextFloat() * 6F;
        this.poisonRemaining = this.random.nextInt(4) + 2;

        this.setPosition(this.getX(), this.getY(), this.getZ());
    }

    public static DefaultAttributeContainer.Builder createAechorPlantAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0);
    }

    public EntityDimensions getSizeForStatus(EntityPose entityPose_1) {
        return EntityDimensions.changing(0.75F + ((float) this.size * 0.125F), 0.5F + ((float) this.size * 0.075F));
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(4, new ProjectileAttackGoal(this, 0.0D, 30, 1.0F));
        this.targetSelector.add(1, new RevengeGoal(this, AechorPlantEntity.class));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, LivingEntity.class, 10, true, false,
                entity -> !(entity instanceof AechorPlantEntity)));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hurtTime > 0)
            this.sinage += 0.9F;

        this.sinage += (this.getAttacker() != null ? 0.3f : 0.1f);

        if (this.sinage > 3.141593F * 2F)
            this.sinage -= (3.141593F * 2F);

        if (this.world.getBlockState(this.getBlockPos().down(1)).getBlock() != AetherBlocks.AETHER_GRASS_BLOCK) {
            this.tickInVoid();
        }
    }

    @Override
    public boolean canSee(Entity entity) {
        double distance = this.distanceTo(entity);
        return distance <= 6.0F && super.canSee(entity);
    }

    @Override
    public boolean canSpawn(WorldAccess worldIn, SpawnReason SpawnReason) {
        return worldIn.getBlockState(this.getBlockPos().down(1)).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK
                && this.random.nextInt(400) == 0;
    }

    @Override
    public void attack(LivingEntity targetIn, float arg1) {
        double x = targetIn.getX() - this.getX();
        double z = targetIn.getZ() - this.getZ();
        final double sqrt = Math.sqrt((x * x) + (z * z) + 0.1D);
        double y = 0.1D + (sqrt * 0.5D) + ((this.getY() - targetIn.getY()) * 0.25D);
        double distance = 5.0D / sqrt;

        PoisonNeedleEntity needle = new PoisonNeedleEntity(this, this.world);
        needle.setVelocity(x * distance, y, z * distance, 0.285F + ((float) y * 0.05F), 1.0F);
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.2F / (this.getRandom().nextFloat() * 0.2F + 0.9F));
        this.world.spawnEntity(needle);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.getItem() == AetherItems.SKYROOT_BUCKET && !player.getAbilities().creativeMode) {
            heldItem.setCount(heldItem.getCount() - 1);

            if (heldItem.isEmpty())
                player.setStackInHand(hand, new ItemStack(AetherItems.SKYROOT_POISON_BUCKET));
            else if (!player.getInventory().insertStack(new ItemStack(AetherItems.SKYROOT_POISON_BUCKET)))
                player.dropItem(new ItemStack(AetherItems.SKYROOT_POISON_BUCKET), false);

            return ActionResult.SUCCESS;
        } else
            return super.interactMob(player, hand);
    }

    public Vec3d getVelocity() {
        return Vec3d.ZERO;
    }

    public void setVelocity(Vec3d velocity) {
    }

    @Override
    public void takeKnockback(double strength, double xRatio, double zRatio) {
        if (this.getHealth() <= 0.0F)
            super.takeKnockback(strength, xRatio, zRatio);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putInt("size", this.size);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        this.size = compound.getInt("size");
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entityIn) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_BIG_FALL;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}