package com.github.harrytechrevs.taanr.init;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HammerNine {
	
// a subset copy of Supertools ...  https://github.com/TheKiritoPlayer20/SuperTools 
    @SubscribeEvent
    public static void SuperPickaxeDestroyedBlock(BlockEvent.BreakEvent event) {
        World world = (World) event.getWorld();
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getPos();
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        boolean firstUse = true;

        if (TutorialItems.taanr_hammernine.equals(stack.getItem())) {
            int bx = pos.getX();
            int by = pos.getY();
            int bz = pos.getZ();

            Direction headRot = player.getHorizontalFacing();
            ArrayList<INBT> enchantments = new ArrayList<>();
            Random random = new Random();
            String unbreakingEnchantment = "";
            String silktouchEnchantment = "";
            String fortuneEnchantment = "";

            enchantments.add(INBT.create((byte) 0));

            for (INBT enchantment : stack.getEnchantmentTagList()) {
                enchantments.add(enchantment);
            }

            for (int i = 0; i < enchantments.size(); i++) {
                if (enchantments.get(i).toString().contains("minecraft:unbreaking")) {
                    unbreakingEnchantment = enchantments.get(i).toString();
                } else if (enchantments.get(i).toString().contains("minecraft:silk_touch")) {
                    silktouchEnchantment = enchantments.get(i).toString();
                } else if (enchantments.get(i).toString().contains("minecraft:fortune")) {
                    fortuneEnchantment = enchantments.get(i).toString();
                }
            }

            if (world.getBlockState(pos).getMaterial() == Material.ROCK || world.getBlockState(pos).getMaterial() == Material.IRON || world.getBlockState(pos).getMaterial() == Material.ANVIL || world.getBlockState(pos).getMaterial() == Material.GLASS || world.getBlockState(pos).getMaterial() == Material.ICE || world.getBlockState(pos).getMaterial() == Material.PACKED_ICE) {
                if (player.getLookVec().y <= -0.52f || player.getLookVec().y >= 0.52f) {
                    for (int x = -1; x < 2; x++) {
                        for (int z = -1; z < 2; z++) {
                            if (stack.getDamage() >= stack.getMaxDamage() - 1) {
                                stack.shrink(1);
                                break;
                            }

                            int randomNumber = random.nextInt(100) + 1;
                            BlockPos newBlockPos = new BlockPos(bx + x, by, bz + z);


                            if (world.getBlockState(newBlockPos).getMaterial() == Material.ROCK || world.getBlockState(newBlockPos).getMaterial() == Material.IRON || world.getBlockState(newBlockPos).getMaterial() == Material.ANVIL || world.getBlockState(newBlockPos).getMaterial() == Material.GLASS || world.getBlockState(newBlockPos).getMaterial() == Material.ICE || world.getBlockState(newBlockPos).getMaterial() == Material.PACKED_ICE) {
                                if (world.getBlockState(newBlockPos).getBlock() != Blocks.BEDROCK && world.getBlockState(newBlockPos).getBlock() != Blocks.END_PORTAL_FRAME) {
                                    Block blockToDestroy = world.getBlockState(newBlockPos).getBlock();
                                    if (silktouchEnchantment.length() != 0) {
                                        if(  !player.isCreative()){
                                            Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                            world.destroyBlock(newBlockPos, false);
                                            Block.spawnAsEntity(world, newBlockPos, new ItemStack(destroyedBlock));
                                        }else{
                                            world.destroyBlock(newBlockPos, false);
                                        }
                                    }else if (fortuneEnchantment.length() != 0) {
                                        if (blockToDestroy.getTags().toString().contains("forge:ores/") && blockToDestroy != Blocks.IRON_ORE && blockToDestroy != Blocks.GOLD_ORE) {
                                            if (fortuneEnchantment.contains("lvl:1")) {
                                                if (randomNumber <= 33) {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 1, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            } else if (fortuneEnchantment.contains("lvl:2")) {
                                                if (randomNumber <= 25) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 50 && randomNumber > 25) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            } else if (fortuneEnchantment.contains("lvl:3")) {
                                                if (randomNumber <= 20) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 40 && randomNumber > 20) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 60 && randomNumber > 40) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            }
                                        } else {
                                            if( !player.isCreative()){
                                                Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                BlockState blockState = world.getBlockState(newBlockPos);
                                                world.destroyBlock(newBlockPos, true);
                                                int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                if (amount != 0) {
                                                    world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                }
                                            }else{
                                                world.destroyBlock(newBlockPos, false);
                                            }
                                        }
                                    } else {
                                        if(!player.isCreative()){
                                            Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                            BlockState blockState = world.getBlockState(newBlockPos);
                                            world.destroyBlock(newBlockPos, true);
                                            int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                            if (amount != 0) {
                                                world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                            }
                                        }else{
                                            world.destroyBlock(newBlockPos, false);
                                        }
                                    }
                                }
                                if (!player.isCreative()) {
                                    if (firstUse) {
                                        firstUse = false;
                                    } else {
                                        if (unbreakingEnchantment.length() != 0) {
                                            if (unbreakingEnchantment.contains("lvl:1")) {
                                                if (random.nextInt(100) + 1 <= 50) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            } else if (unbreakingEnchantment.contains("lvl:2")) {
                                                if (random.nextInt(100) + 1 <= 33) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            } else if (unbreakingEnchantment.contains("lvl:3")) {
                                                if (random.nextInt(100) + 1 <= 25) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            }
                                        } else {
                                            stack.damageItem(1, player, PlayerEntity::tick);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (headRot.equals(Direction.NORTH) || headRot.equals(Direction.SOUTH)) {
                    for (int x = -1; x < 2; x++) {
                        for (int y = -1; y < 2; y++) {
                            if (stack.getDamage() >= stack.getMaxDamage() - 1) {
                                stack.shrink(1);
                                break;
                            }


                            int randomNumber = random.nextInt(100) + 1;
                            BlockPos newBlockPos = new BlockPos(bx + x, by + y, bz);

                            if (world.getBlockState(newBlockPos).getMaterial() == Material.ROCK || world.getBlockState(newBlockPos).getMaterial() == Material.IRON || world.getBlockState(newBlockPos).getMaterial() == Material.ANVIL || world.getBlockState(newBlockPos).getMaterial() == Material.GLASS || world.getBlockState(newBlockPos).getMaterial() == Material.ICE || world.getBlockState(newBlockPos).getMaterial() == Material.PACKED_ICE) {
                                if (world.getBlockState(newBlockPos).getBlock() != Blocks.BEDROCK && world.getBlockState(newBlockPos).getBlock() != Blocks.END_PORTAL_FRAME) {
                                    Block blockToDestroy = world.getBlockState(newBlockPos).getBlock();
                                    if (silktouchEnchantment.length() != 0) {
                                        if( !player.isCreative()){
                                            Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                            world.destroyBlock(newBlockPos, false);
                                            Block.spawnAsEntity(world, newBlockPos, new ItemStack(destroyedBlock));
                                        }else{
                                            world.destroyBlock(newBlockPos, false);
                                        }
                                    }else if (fortuneEnchantment.length() != 0) {
                                        if (blockToDestroy.getTags().toString().contains("forge:ores/") && blockToDestroy != Blocks.IRON_ORE && blockToDestroy != Blocks.GOLD_ORE) {
                                            if (fortuneEnchantment.contains("lvl:1")) {
                                                if (randomNumber <= 33) {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 1, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            } else if (fortuneEnchantment.contains("lvl:2")) {
                                                if (randomNumber <= 25) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 50 && randomNumber > 25) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            } else if (fortuneEnchantment.contains("lvl:3")) {
                                                if (randomNumber <= 20) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 40 && randomNumber > 20) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 60 && randomNumber > 40) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            }
                                        } else {
                                            if(!player.isCreative()){
                                                Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                BlockState blockState = world.getBlockState(newBlockPos);
                                                world.destroyBlock(newBlockPos, true);
                                                int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                if (amount != 0) {
                                                    world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                }
                                            }else{
                                                world.destroyBlock(newBlockPos, false);
                                            }
                                        }
                                    } else {
                                        if(!player.isCreative()){
                                            Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                            BlockState blockState = world.getBlockState(newBlockPos);
                                            world.destroyBlock(newBlockPos, true);
                                            int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                            if (amount != 0) {
                                                world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                            }
                                        }else{
                                            world.destroyBlock(newBlockPos, false);
                                        }
                                    }
                                }
                                if (!player.isCreative()) {
                                    if (firstUse) {
                                        firstUse = false;
                                    } else {
                                        if (unbreakingEnchantment.length() != 0) {
                                            if (unbreakingEnchantment.contains("lvl:1")) {
                                                if (random.nextInt(100) + 1 <= 50) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            } else if (unbreakingEnchantment.contains("lvl:2")) {
                                                if (random.nextInt(100) + 1 <= 33) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            } else if (unbreakingEnchantment.contains("lvl:3")) {
                                                if (random.nextInt(100) + 1 <= 25) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            }
                                        } else {
                                            stack.damageItem(1, player, PlayerEntity::tick);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }else if (headRot.equals(Direction.WEST) || headRot.equals(Direction.EAST)) {
                    for (int z = -1; z < 2; z++) {
                        for (int y = -1; y < 2; y++) {
                            if (stack.getDamage() >= stack.getMaxDamage() - 1) {
                                stack.shrink(1);
                                break;
                            }

                            int randomNumber = random.nextInt(100) + 1;
                            BlockPos newBlockPos = new BlockPos(bx, by + y, bz + z);

                            if (world.getBlockState(newBlockPos).getMaterial() == Material.ROCK || world.getBlockState(newBlockPos).getMaterial() == Material.IRON || world.getBlockState(newBlockPos).getMaterial() == Material.ANVIL || world.getBlockState(newBlockPos).getMaterial() == Material.GLASS || world.getBlockState(newBlockPos).getMaterial() == Material.ICE || world.getBlockState(newBlockPos).getMaterial() == Material.PACKED_ICE) {
                                if (world.getBlockState(newBlockPos).getBlock() != Blocks.BEDROCK && world.getBlockState(newBlockPos).getBlock() != Blocks.END_PORTAL_FRAME) {
                                    Block blockToDestroy = world.getBlockState(newBlockPos).getBlock();
                                    if (silktouchEnchantment.length() != 0) {
                                        if(!player.isCreative()){
                                            Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                            world.destroyBlock(newBlockPos, false);
                                            Block.spawnAsEntity(world, newBlockPos, new ItemStack(destroyedBlock));
                                        }else{
                                            world.destroyBlock(newBlockPos, false);
                                        }
                                    }else if (fortuneEnchantment.length() != 0) {
                                        if (blockToDestroy.getTags().toString().contains("forge:ores/") && blockToDestroy != Blocks.IRON_ORE && blockToDestroy != Blocks.GOLD_ORE) {
                                            if (fortuneEnchantment.contains("lvl:1")) {
                                                if (randomNumber <= 33) {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 1, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            } else if (fortuneEnchantment.contains("lvl:2")) {
                                                if (randomNumber <= 25) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 50 && randomNumber > 25) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            } else if (fortuneEnchantment.contains("lvl:3")) {
                                                if (randomNumber <= 20) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 40 && randomNumber > 20) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else if (randomNumber <= 60 && randomNumber > 40) {
                                                    if(!player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        TileEntity tileentity = blockState.hasTileEntity() ? world.getTileEntity(newBlockPos) : null;
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        Block.spawnDrops(blockState, world, newBlockPos, tileentity);
                                                        world.destroyBlock(newBlockPos, false);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 3, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                } else {
                                                    if( !player.isCreative()){
                                                        Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                        BlockState blockState = world.getBlockState(newBlockPos);
                                                        world.destroyBlock(newBlockPos, true);
                                                        int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                        if (amount != 0) {
                                                            world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                        }
                                                    }else{
                                                        world.destroyBlock(newBlockPos, false);
                                                    }
                                                }
                                            }
                                        } else {
                                            if( !player.isCreative()){
                                                Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                                BlockState blockState = world.getBlockState(newBlockPos);
                                                world.destroyBlock(newBlockPos, true);
                                                int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                                if (amount != 0) {
                                                    world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                                }
                                            }else{
                                                world.destroyBlock(newBlockPos, false);
                                            }
                                        }
                                    } else {
                                        if(!player.isCreative()){
                                            Block destroyedBlock = world.getBlockState(newBlockPos).getBlock();
                                            BlockState blockState = world.getBlockState(newBlockPos);
                                            world.destroyBlock(newBlockPos, true);
                                            int amount = destroyedBlock.getExpDrop(blockState, world, newBlockPos, 0, 0);
                                            if (amount != 0) {
                                                world.addEntity(new ExperienceOrbEntity(world, (double) newBlockPos.getX() + 0.5D, (double) newBlockPos.getY() + 0.5D, (double) newBlockPos.getZ() + 0.5D, amount));
                                            }
                                        }else{
                                            world.destroyBlock(newBlockPos, false);
                                        }
                                    }
                                }
                                if (!player.isCreative()) {
                                    if (firstUse) {
                                        firstUse = false;
                                    } else {
                                        if (unbreakingEnchantment.length() != 0) {
                                            if (unbreakingEnchantment.contains("lvl:1")) {
                                                if (random.nextInt(100) + 1 <= 50) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            } else if (unbreakingEnchantment.contains("lvl:2")) {
                                                if (random.nextInt(100) + 1 <= 33) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            } else if (unbreakingEnchantment.contains("lvl:3")) {
                                                if (random.nextInt(100) + 1 <= 25) {
                                                    stack.damageItem(1, player, PlayerEntity::tick);
                                                }
                                            }
                                        } else {
                                            stack.damageItem(1, player, PlayerEntity::tick);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

	
	
	

}
