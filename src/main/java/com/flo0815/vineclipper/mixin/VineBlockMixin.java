package com.flo0815.vineclipper.mixin;

import com.flo0815.vineclipper.VineClipper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
//? if >=1.20.5 {
/*import net.minecraft.world.ItemInteractionResult;
*/
//?}
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
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
        return blockState.setValue(VineClipper.CLIPPED, false);
    }

    @Inject(method = "createBlockStateDefinition", at = @At("RETURN"))
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(VineClipper.CLIPPED);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        if (blockState.getValue(VineClipper.CLIPPED)) {
            ci.cancel();
        }
    }

    //? if <1.20.5 {
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack heldItem = player.getItemInHand(interactionHand);
        if (tryClip(heldItem, blockState, level, blockPos, player, interactionHand)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
    //?} else {
    /*@Override
    protected ItemInteractionResult useItemOn(ItemStack heldItem, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (tryClip(heldItem, blockState, level, blockPos, player, interactionHand)) {
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
    */
    //?}

    private boolean tryClip(ItemStack heldItem, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand) {
        if (interactionHand != InteractionHand.MAIN_HAND) return false;
        if (heldItem.isEmpty()) return false;
        if (!(heldItem.getItem() instanceof ShearsItem)) return false;
        if (!blockState.hasProperty(VineClipper.CLIPPED)) return false;

        level.playSound(player, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
        heldItem.setDamageValue(heldItem.getDamageValue() + 1);
        level.setBlockAndUpdate(blockPos, blockState.setValue(VineClipper.CLIPPED, true));
        return true;
    }
}
