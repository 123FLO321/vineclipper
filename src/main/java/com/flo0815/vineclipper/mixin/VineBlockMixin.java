package com.flo0815.vineclipper.mixin;

import com.flo0815.vineclipper.VineClipper;
//? if <26.2 {
/*import net.minecraft.advancements.CriteriaTriggers;
*///?} else {
import net.minecraft.advancements.triggers.CriteriaTriggers;
//?}
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
//? if >=1.19 {
import net.minecraft.util.RandomSource;
//?} else {
/*import java.util.Random;
*/
//?}
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
//? if >=1.20.5 && <1.21.10 {
/*import net.minecraft.world.entity.LivingEntity;
*/
//?}
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
//? if >=1.20.5 && <1.21.2 {
/*import net.minecraft.world.ItemInteractionResult;
*/
//?}
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
//? if >=1.19.4 {
import net.minecraft.world.level.gameevent.GameEvent;
//?}
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VineBlock.class)
public abstract class VineBlockMixin extends Block {

    private VineBlockMixin(Properties properties) {
        super(properties);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/VineBlock;registerDefaultState(Lnet/minecraft/world/level/block/state/BlockState;)V"))
    public BlockState initModRegisterDefaultState(BlockState blockState) {
        if (!blockState.hasProperty(VineClipper.CLIPPED)) return blockState;
        return blockState.setValue(VineClipper.CLIPPED, false);
    }

    @Inject(method = "createBlockStateDefinition", at = @At("RETURN"))
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(VineClipper.CLIPPED);
    }

    //? if >=1.19 {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        if (blockState.hasProperty(VineClipper.CLIPPED) && blockState.getValue(VineClipper.CLIPPED)) {
            ci.cancel();
        }
    }
    //?} else {
    /*@Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random, CallbackInfo ci) {
        if (blockState.hasProperty(VineClipper.CLIPPED) && blockState.getValue(VineClipper.CLIPPED)) {
            ci.cancel();
        }
    }
    */
    //?}

    //? if <1.20.5 {
    /*@Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack heldItem = player.getItemInHand(interactionHand);
        if (tryClip(heldItem, blockState, level, blockPos, player, interactionHand)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
    *///?} elif <1.21.2 {
    /*@Override
    protected ItemInteractionResult useItemOn(ItemStack heldItem, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (tryClip(heldItem, blockState, level, blockPos, player, interactionHand)) {
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
    */
    //?} else {
    @Override
    protected InteractionResult useItemOn(ItemStack heldItem, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (tryClip(heldItem, blockState, level, blockPos, player, interactionHand)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    //?}

    private boolean tryClip(ItemStack heldItem, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand) {
        if (!(heldItem.getItem() instanceof ShearsItem)) return false;
        if (!blockState.hasProperty(VineClipper.CLIPPED)) return false;
        if (blockState.getValue(VineClipper.CLIPPED)) return false;

        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, heldItem);
        }
        //? if >=1.18 {
        level.playSound(player, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
        //?} else {
        /*level.playSound(player, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
        */
        //?}
        BlockState newState = blockState.setValue(VineClipper.CLIPPED, true);
        level.setBlockAndUpdate(blockPos, newState);
        //? if >=1.19.4 {
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, newState));
        //?}
        //? if <1.20.5 {
        /*heldItem.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(interactionHand));
        *///?} elif <1.21.10 {
        /*heldItem.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
        */
        //?} else {
        heldItem.hurtAndBreak(1, player, interactionHand.asEquipmentSlot());
        //?}
        return true;
    }
}
