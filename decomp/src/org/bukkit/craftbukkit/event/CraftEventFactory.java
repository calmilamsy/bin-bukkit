/*     */ package org.bukkit.craftbukkit.event;
/*     */ 
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.ChunkCoordinates;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldServer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.craftbukkit.block.CraftBlock;
/*     */ import org.bukkit.craftbukkit.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.AnimalTamer;
/*     */ import org.bukkit.entity.CreatureType;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockDamageEvent;
/*     */ import org.bukkit.event.block.BlockFadeEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityTameEvent;
/*     */ import org.bukkit.event.entity.ItemSpawnEvent;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ import org.bukkit.event.player.PlayerEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CraftEventFactory
/*     */ {
/*     */   private static boolean canBuild(CraftWorld world, Player player, int x, int z) {
/*  47 */     WorldServer worldServer = world.getHandle();
/*  48 */     int spawnSize = Bukkit.getServer().getSpawnRadius();
/*     */     
/*  50 */     if (spawnSize <= 0) return true; 
/*  51 */     if (player.isOp()) return true;
/*     */     
/*  53 */     ChunkCoordinates chunkcoordinates = worldServer.getSpawn();
/*     */     
/*  55 */     int distanceFromSpawn = Math.max(Math.abs(x - chunkcoordinates.x), Math.abs(z - chunkcoordinates.z));
/*  56 */     return (distanceFromSpawn > spawnSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, int type) { return callBlockPlaceEvent(world, who, replacedBlockState, clickedX, clickedY, clickedZ, Block.byId[type]); }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, Block block) { return callBlockPlaceEvent(world, who, replacedBlockState, clickedX, clickedY, clickedZ, new ItemStack(block)); }
/*     */ 
/*     */   
/*     */   public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, ItemStack itemstack) {
/*  71 */     CraftWorld craftWorld = ((WorldServer)world).getWorld();
/*  72 */     CraftServer craftServer = ((WorldServer)world).getServer();
/*     */     
/*  74 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/*  75 */     CraftItemStack itemInHand = new CraftItemStack(itemstack);
/*     */     
/*  77 */     Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/*  78 */     Block placedBlock = replacedBlockState.getBlock();
/*     */     
/*  80 */     boolean canBuild = canBuild(craftWorld, player, placedBlock.getX(), placedBlock.getZ());
/*     */     
/*  82 */     BlockPlaceEvent event = new BlockPlaceEvent(placedBlock, replacedBlockState, blockClicked, itemInHand, player, canBuild);
/*  83 */     craftServer.getPluginManager().callEvent(event);
/*     */     
/*  85 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemInHand) { return (PlayerBucketEmptyEvent)getPlayerBucketEvent(Event.Type.PLAYER_BUCKET_EMPTY, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, Item.BUCKET); }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static PlayerBucketFillEvent callPlayerBucketFillEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemInHand, Item bucket) { return (PlayerBucketFillEvent)getPlayerBucketEvent(Event.Type.PLAYER_BUCKET_FILL, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket); }
/*     */ 
/*     */   
/*     */   private static PlayerEvent getPlayerBucketEvent(Event.Type type, EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemstack, Item item) {
/* 100 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/* 101 */     CraftItemStack itemInHand = new CraftItemStack(new ItemStack(item));
/* 102 */     Material bucket = Material.getMaterial(itemstack.id);
/*     */     
/* 104 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 105 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 107 */     Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/* 108 */     BlockFace blockFace = CraftBlock.notchToBlockFace(clickedFace);
/*     */     
/* 110 */     PlayerBucketFillEvent playerBucketFillEvent = null;
/* 111 */     if (type == Event.Type.PLAYER_BUCKET_EMPTY) {
/* 112 */       PlayerBucketEmptyEvent playerBucketEmptyEvent = new PlayerBucketEmptyEvent(player, blockClicked, blockFace, bucket, itemInHand);
/* 113 */       ((PlayerBucketEmptyEvent)playerBucketEmptyEvent).setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
/* 114 */     } else if (type == Event.Type.PLAYER_BUCKET_FILL) {
/* 115 */       playerBucketFillEvent = new PlayerBucketFillEvent(player, blockClicked, blockFace, bucket, itemInHand);
/* 116 */       ((PlayerBucketFillEvent)playerBucketFillEvent).setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
/*     */     } 
/*     */     
/* 119 */     craftServer.getPluginManager().callEvent(playerBucketFillEvent);
/*     */     
/* 121 */     return playerBucketFillEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, ItemStack itemstack) {
/* 129 */     if (action != Action.LEFT_CLICK_AIR && action != Action.RIGHT_CLICK_AIR) {
/* 130 */       throw new IllegalArgumentException();
/*     */     }
/* 132 */     return callPlayerInteractEvent(who, action, 0, 255, 0, 0, itemstack);
/*     */   }
/*     */   public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemstack) {
/* 135 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/* 136 */     CraftItemStack itemInHand = new CraftItemStack(itemstack);
/*     */     
/* 138 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 139 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 141 */     Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/* 142 */     BlockFace blockFace = CraftBlock.notchToBlockFace(clickedFace);
/*     */     
/* 144 */     if (clickedY == 255) {
/* 145 */       blockClicked = null;
/* 146 */       switch (action) {
/*     */         case LEFT_CLICK_BLOCK:
/* 148 */           action = Action.LEFT_CLICK_AIR;
/*     */           break;
/*     */         case RIGHT_CLICK_BLOCK:
/* 151 */           action = Action.RIGHT_CLICK_AIR;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 156 */     if (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0) {
/* 157 */       itemInHand = null;
/*     */     }
/*     */     
/* 160 */     PlayerInteractEvent event = new PlayerInteractEvent(player, action, itemInHand, blockClicked, blockFace);
/* 161 */     craftServer.getPluginManager().callEvent(event);
/*     */     
/* 163 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockDamageEvent callBlockDamageEvent(EntityHuman who, int x, int y, int z, ItemStack itemstack, boolean instaBreak) {
/* 170 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/* 171 */     CraftItemStack itemInHand = new CraftItemStack(itemstack);
/*     */     
/* 173 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 174 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 176 */     Block blockClicked = craftWorld.getBlockAt(x, y, z);
/*     */     
/* 178 */     BlockDamageEvent event = new BlockDamageEvent(player, blockClicked, itemInHand, instaBreak);
/* 179 */     craftServer.getPluginManager().callEvent(event);
/*     */     
/* 181 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CreatureSpawnEvent callCreatureSpawnEvent(EntityLiving entityliving, CreatureSpawnEvent.SpawnReason spawnReason) {
/* 188 */     Entity entity = entityliving.getBukkitEntity();
/* 189 */     CraftServer craftServer = (CraftServer)entity.getServer();
/*     */     
/* 191 */     CreatureType type = null;
/*     */     
/* 193 */     if (entityliving instanceof net.minecraft.server.EntityChicken) {
/* 194 */       type = CreatureType.CHICKEN;
/* 195 */     } else if (entityliving instanceof net.minecraft.server.EntityCow) {
/* 196 */       type = CreatureType.COW;
/* 197 */     } else if (entityliving instanceof net.minecraft.server.EntityCreeper) {
/* 198 */       type = CreatureType.CREEPER;
/* 199 */     } else if (entityliving instanceof net.minecraft.server.EntityGhast) {
/* 200 */       type = CreatureType.GHAST;
/* 201 */     } else if (entityliving instanceof net.minecraft.server.EntityGiantZombie) {
/* 202 */       type = CreatureType.GIANT;
/* 203 */     } else if (entityliving instanceof net.minecraft.server.EntityWolf) {
/* 204 */       type = CreatureType.WOLF;
/* 205 */     } else if (entityliving instanceof net.minecraft.server.EntityPig) {
/* 206 */       type = CreatureType.PIG;
/* 207 */     } else if (entityliving instanceof net.minecraft.server.EntityPigZombie) {
/* 208 */       type = CreatureType.PIG_ZOMBIE;
/* 209 */     } else if (entityliving instanceof net.minecraft.server.EntitySheep) {
/* 210 */       type = CreatureType.SHEEP;
/* 211 */     } else if (entityliving instanceof net.minecraft.server.EntitySkeleton) {
/* 212 */       type = CreatureType.SKELETON;
/* 213 */     } else if (entityliving instanceof net.minecraft.server.EntitySlime) {
/* 214 */       type = CreatureType.SLIME;
/* 215 */     } else if (entityliving instanceof net.minecraft.server.EntitySpider) {
/* 216 */       type = CreatureType.SPIDER;
/* 217 */     } else if (entityliving instanceof net.minecraft.server.EntitySquid) {
/* 218 */       type = CreatureType.SQUID;
/* 219 */     } else if (entityliving instanceof net.minecraft.server.EntityZombie) {
/* 220 */       type = CreatureType.ZOMBIE;
/*     */     }
/* 222 */     else if (entityliving instanceof net.minecraft.server.EntityMonster) {
/* 223 */       type = CreatureType.MONSTER;
/*     */     } 
/*     */     
/* 226 */     CreatureSpawnEvent event = new CreatureSpawnEvent(entity, type, entity.getLocation(), spawnReason);
/* 227 */     craftServer.getPluginManager().callEvent(event);
/* 228 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityTameEvent callEntityTameEvent(EntityLiving entity, EntityHuman tamer) {
/* 235 */     Entity bukkitEntity = entity.getBukkitEntity();
/* 236 */     AnimalTamer bukkitTamer = (tamer != null) ? (AnimalTamer)tamer.getBukkitEntity() : null;
/* 237 */     CraftServer craftServer = (CraftServer)bukkitEntity.getServer();
/*     */     
/* 239 */     EntityTameEvent event = new EntityTameEvent(bukkitEntity, bukkitTamer);
/* 240 */     craftServer.getPluginManager().callEvent(event);
/* 241 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemSpawnEvent callItemSpawnEvent(EntityItem entityitem) {
/* 248 */     Entity entity = entityitem.getBukkitEntity();
/* 249 */     CraftServer craftServer = (CraftServer)entity.getServer();
/*     */     
/* 251 */     ItemSpawnEvent event = new ItemSpawnEvent(entity, entity.getLocation());
/*     */     
/* 253 */     craftServer.getPluginManager().callEvent(event);
/* 254 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockFadeEvent callBlockFadeEvent(Block block, int type) {
/* 261 */     BlockState state = block.getState();
/* 262 */     state.setTypeId(type);
/*     */     
/* 264 */     BlockFadeEvent event = new BlockFadeEvent(block, state);
/* 265 */     Bukkit.getPluginManager().callEvent(event);
/* 266 */     return event;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\event\CraftEventFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */