package me.connlost.allstackable.mixin;

import me.connlost.allstackable.util.ItemsHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MixinMilkBucketItem {

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/item/ItemStack;decrement(I)V"), cancellable = true)
    private void stackableMilkBucket(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir){
        if (!stack.isEmpty() && ItemsHelper.isModified(stack)){
            if (!world.isClient)
                user.clearStatusEffects();
            if (user instanceof PlayerEntity){
                ItemsHelper.insertNewItem((PlayerEntity)user, new ItemStack(Items.BUCKET));
                cir.setReturnValue(stack);
            }
        }


    }
}
