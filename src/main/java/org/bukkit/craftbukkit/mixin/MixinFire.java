package org.bukkit.craftbukkit.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Fire;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.craftbukkit.util.mixin.CraftLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(Fire.class)
public abstract class MixinFire extends BlockBase {

    protected MixinFire(int id, Material material) {
        super(id, material);
    }

    @Shadow public abstract boolean canPlaceAt(Level level, int x, int y, int z);

    @Shadow public abstract int getTickrate();

    @Shadow protected abstract boolean method_1826(Level arg, int i, int j, int k);

    @Shadow public abstract boolean method_1824(BlockView arg, int i, int j, int k);

    @Shadow protected abstract void method_1823(Level arg, int i, int j, int k, int i1, Random random, int j1);

    @Shadow protected abstract int method_1827(Level arg, int i, int j, int k);

    /**
     * @author calmilamsy
     * screw this shit.
     */
    @Overwrite
    public void onScheduledTick(Level world, int i, int j, int k, Random random) {
        boolean flag = world.getTileId(i, j - 1, k) == BlockBase.NETHERRACK.id;

        if (!this.canPlaceAt(world, i, j, k)) {
            world.setTile(i, j, k, 0);
        }

        if (!flag && world.isRaining() && (world.canRainAt(i, j, k) || world.canRainAt(i - 1, j, k) || world.canRainAt(i + 1, j, k) || world.canRainAt(i, j, k - 1) || world.canRainAt(i, j, k + 1))) {
            world.setTile(i, j, k, 0);
        } else {
            int l = world.getTileMeta(i, j, k);

            if (l < 15) {
                world.setTileMeta(i, j, k, l + random.nextInt(3) / 2);
            }

            world.method_216(i, j, k, id, this.getTickrate());
            if (!flag && !this.method_1826(world, i, j, k)) {
                if (!world.canSuffocate(i, j - 1, k) || l > 3) {
                    world.setTile(i, j, k, 0);
                }
            } else if (!flag && !this.method_1824(world, i, j - 1, k) && l == 15 && random.nextInt(4) == 0) {
                world.setTile(i, j, k, 0);
            } else {
                this.method_1823(world, i + 1, j, k, 300, random, l);
                this.method_1823(world, i - 1, j, k, 300, random, l);
                this.method_1823(world, i, j - 1, k, 250, random, l);
                this.method_1823(world, i, j + 1, k, 250, random, l);
                this.method_1823(world, i, j, k - 1, 300, random, l);
                this.method_1823(world, i, j, k + 1, 300, random, l);

                // CraftBukkit start - Call to stop spread of fire.
                org.bukkit.Server server = ((CraftLevel) world).getServer();
                org.bukkit.World bworld = ((CraftLevel) world).getWorld();

                BlockIgniteEvent.IgniteCause igniteCause = BlockIgniteEvent.IgniteCause.SPREAD;
                org.bukkit.block.Block fromBlock = bworld.getBlockAt(i, j, k);
                // CraftBukkit end

                for (int i1 = i - 1; i1 <= i + 1; ++i1) {
                    for (int j1 = k - 1; j1 <= k + 1; ++j1) {
                        for (int k1 = j - 1; k1 <= j + 4; ++k1) {
                            if (i1 != i || k1 != j || j1 != k) {
                                int l1 = 100;

                                if (k1 > j + 1) {
                                    l1 += (k1 - (j + 1)) * 100;
                                }

                                int i2 = this.method_1827(world, i1, k1, j1);

                                if (i2 > 0) {
                                    int j2 = (i2 + 40) / (l + 30);

                                    if (j2 > 0 && random.nextInt(l1) <= j2 && (!world.isRaining() || !world.canRainAt(i1, k1, j1)) && !world.canRainAt(i1 - 1, k1, k) && !world.canRainAt(i1 + 1, k1, j1) && !world.canRainAt(i1, k1, j1 - 1) && !world.canRainAt(i1, k1, j1 + 1)) {
                                        int k2 = l + random.nextInt(5) / 4;

                                        if (k2 > 15) {
                                            k2 = 15;
                                        }
                                        // CraftBukkit start - Call to stop spread of fire.
                                        Block block = bworld.getBlockAt(i1, k1, j1);

                                        if (block.getTypeId() != BlockBase.FIRE.id) {
                                            BlockIgniteEvent event = new BlockIgniteEvent(block, igniteCause, null);
                                            server.getPluginManager().callEvent(event);

                                            if (event.isCancelled()) {
                                                continue;
                                            }

                                            org.bukkit.block.BlockState blockState = bworld.getBlockAt(i1, k1, j1).getState();
                                            blockState.setTypeId(this.id);
                                            blockState.setData(new MaterialData(this.id, (byte) k2));

                                            BlockSpreadEvent spreadEvent = new BlockSpreadEvent(blockState.getBlock(), fromBlock, blockState);
                                            server.getPluginManager().callEvent(spreadEvent);

                                            if (!spreadEvent.isCancelled()) {
                                                blockState.update(true);
                                            }
                                        }
                                        // CraftBukkit end
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
