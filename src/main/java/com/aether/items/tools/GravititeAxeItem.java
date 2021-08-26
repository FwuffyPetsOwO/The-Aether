package com.aether.items.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class GravititeAxeItem extends AxeItem {
    public GravititeAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand){
        return GravititeTool.flipEntity(stack, player, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return GravititeTool.tryFloatBlock(context, super.useOnBlock(context));
    }
}