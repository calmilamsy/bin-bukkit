/*     */ package org.bukkit.craftbukkit.block;
/*     */ import net.minecraft.server.BiomeBase;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockRedstoneWire;
/*     */ import net.minecraft.server.World;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Biome;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.PistonMoveReaction;
/*     */ import org.bukkit.craftbukkit.CraftChunk;
/*     */ import org.bukkit.util.BlockVector;
/*     */ 
/*     */ public class CraftBlock implements Block {
/*     */   private final CraftChunk chunk;
/*     */   private final int x;
/*     */   
/*     */   public CraftBlock(CraftChunk chunk, int x, int y, int z) {
/*  22 */     this.x = x;
/*  23 */     this.y = y;
/*  24 */     this.z = z;
/*  25 */     this.chunk = chunk;
/*     */   }
/*     */   private final int y; private final int z;
/*     */   
/*  29 */   public World getWorld() { return this.chunk.getWorld(); }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public Location getLocation() { return new Location(getWorld(), this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public BlockVector getVector() { return new BlockVector(this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public int getX() { return this.x; }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public int getY() { return this.y; }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public int getZ() { return this.z; }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public Chunk getChunk() { return this.chunk; }
/*     */ 
/*     */ 
/*     */   
/*  57 */   public void setData(byte data) { (this.chunk.getHandle()).world.setData(this.x, this.y, this.z, data); }
/*     */ 
/*     */   
/*     */   public void setData(byte data, boolean applyPhysics) {
/*  61 */     if (applyPhysics) {
/*  62 */       (this.chunk.getHandle()).world.setData(this.x, this.y, this.z, data);
/*     */     } else {
/*  64 */       (this.chunk.getHandle()).world.setRawData(this.x, this.y, this.z, data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  69 */   public byte getData() { return (byte)this.chunk.getHandle().getData(this.x & 0xF, this.y & 0x7F, this.z & 0xF); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void setType(Material type) { setTypeId(type.getId()); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public boolean setTypeId(int type) { return (this.chunk.getHandle()).world.setTypeId(this.x, this.y, this.z, type); }
/*     */ 
/*     */   
/*     */   public boolean setTypeId(int type, boolean applyPhysics) {
/*  81 */     if (applyPhysics) {
/*  82 */       return setTypeId(type);
/*     */     }
/*  84 */     return (this.chunk.getHandle()).world.setRawTypeId(this.x, this.y, this.z, type);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTypeIdAndData(int type, byte data, boolean applyPhysics) {
/*  89 */     if (applyPhysics) {
/*  90 */       return (this.chunk.getHandle()).world.setTypeIdAndData(this.x, this.y, this.z, type, data);
/*     */     }
/*  92 */     boolean success = (this.chunk.getHandle()).world.setRawTypeIdAndData(this.x, this.y, this.z, type, data);
/*  93 */     if (success) {
/*  94 */       (this.chunk.getHandle()).world.notify(this.x, this.y, this.z);
/*     */     }
/*  96 */     return success;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public Material getType() { return Material.getMaterial(getTypeId()); }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public int getTypeId() { return this.chunk.getHandle().getTypeId(this.x & 0xF, this.y & 0x7F, this.z & 0xF); }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public byte getLightLevel() { return (byte)(this.chunk.getHandle()).world.getLightLevel(this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public Block getFace(BlockFace face) { return getRelative(face, 1); }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public Block getFace(BlockFace face, int distance) { return getRelative(face, distance); }
/*     */ 
/*     */ 
/*     */   
/* 121 */   public Block getRelative(int modX, int modY, int modZ) { return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ); }
/*     */ 
/*     */ 
/*     */   
/* 125 */   public Block getRelative(BlockFace face) { return getRelative(face, 1); }
/*     */ 
/*     */ 
/*     */   
/* 129 */   public Block getRelative(BlockFace face, int distance) { return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance); }
/*     */ 
/*     */   
/*     */   public BlockFace getFace(Block block) {
/* 133 */     BlockFace[] values = BlockFace.values();
/*     */     
/* 135 */     for (BlockFace face : values) {
/* 136 */       if (getX() + face.getModX() == block.getX() && getY() + face.getModY() == block.getY() && getZ() + face.getModZ() == block.getZ())
/*     */       {
/*     */ 
/*     */         
/* 140 */         return face;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 149 */   public String toString() { return "CraftBlock{chunk=" + this.chunk + "x=" + this.x + "y=" + this.y + "z=" + this.z + '}'; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockFace notchToBlockFace(int notch) {
/* 159 */     switch (notch) {
/*     */       case 0:
/* 161 */         return BlockFace.DOWN;
/*     */       case 1:
/* 163 */         return BlockFace.UP;
/*     */       case 2:
/* 165 */         return BlockFace.EAST;
/*     */       case 3:
/* 167 */         return BlockFace.WEST;
/*     */       case 4:
/* 169 */         return BlockFace.NORTH;
/*     */       case 5:
/* 171 */         return BlockFace.SOUTH;
/*     */     } 
/* 173 */     return BlockFace.SELF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blockFaceToNotch(BlockFace face) {
/* 178 */     switch (face) {
/*     */       case SIGN:
/* 180 */         return 0;
/*     */       case SIGN_POST:
/* 182 */         return 1;
/*     */       case WALL_SIGN:
/* 184 */         return 2;
/*     */       case CHEST:
/* 186 */         return 3;
/*     */       case BURNING_FURNACE:
/* 188 */         return 4;
/*     */       case FURNACE:
/* 190 */         return 5;
/*     */     } 
/* 192 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getState() {
/* 197 */     Material material = getType();
/*     */     
/* 199 */     switch (material) {
/*     */       case SIGN:
/*     */       case SIGN_POST:
/*     */       case WALL_SIGN:
/* 203 */         return new CraftSign(this);
/*     */       case CHEST:
/* 205 */         return new CraftChest(this);
/*     */       case BURNING_FURNACE:
/*     */       case FURNACE:
/* 208 */         return new CraftFurnace(this);
/*     */       case DISPENSER:
/* 210 */         return new CraftDispenser(this);
/*     */       case MOB_SPAWNER:
/* 212 */         return new CraftCreatureSpawner(this);
/*     */       case NOTE_BLOCK:
/* 214 */         return new CraftNoteBlock(this);
/*     */     } 
/* 216 */     return new CraftBlockState(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 221 */   public Biome getBiome() { return biomeBaseToBiome((this.chunk.getHandle()).world.getWorldChunkManager().getBiome(this.x, this.z)); }
/*     */ 
/*     */   
/*     */   public static final Biome biomeBaseToBiome(BiomeBase base) {
/* 225 */     if (base == BiomeBase.RAINFOREST)
/* 226 */       return Biome.RAINFOREST; 
/* 227 */     if (base == BiomeBase.SWAMPLAND)
/* 228 */       return Biome.SWAMPLAND; 
/* 229 */     if (base == BiomeBase.SEASONAL_FOREST)
/* 230 */       return Biome.SEASONAL_FOREST; 
/* 231 */     if (base == BiomeBase.FOREST)
/* 232 */       return Biome.FOREST; 
/* 233 */     if (base == BiomeBase.SAVANNA)
/* 234 */       return Biome.SAVANNA; 
/* 235 */     if (base == BiomeBase.SHRUBLAND)
/* 236 */       return Biome.SHRUBLAND; 
/* 237 */     if (base == BiomeBase.TAIGA)
/* 238 */       return Biome.TAIGA; 
/* 239 */     if (base == BiomeBase.DESERT)
/* 240 */       return Biome.DESERT; 
/* 241 */     if (base == BiomeBase.PLAINS)
/* 242 */       return Biome.PLAINS; 
/* 243 */     if (base == BiomeBase.ICE_DESERT)
/* 244 */       return Biome.ICE_DESERT; 
/* 245 */     if (base == BiomeBase.TUNDRA)
/* 246 */       return Biome.TUNDRA; 
/* 247 */     if (base == BiomeBase.HELL)
/* 248 */       return Biome.HELL; 
/* 249 */     if (base == BiomeBase.SKY) {
/* 250 */       return Biome.SKY;
/*     */     }
/*     */     
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 257 */   public double getTemperature() { return getWorld().getTemperature(this.x, this.z); }
/*     */ 
/*     */ 
/*     */   
/* 261 */   public double getHumidity() { return getWorld().getHumidity(this.x, this.z); }
/*     */ 
/*     */ 
/*     */   
/* 265 */   public boolean isBlockPowered() { return (this.chunk.getHandle()).world.isBlockPowered(this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */   
/* 269 */   public boolean isBlockIndirectlyPowered() { return (this.chunk.getHandle()).world.isBlockIndirectlyPowered(this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 274 */   public boolean equals(Object o) { return (this == o); }
/*     */ 
/*     */ 
/*     */   
/* 278 */   public boolean isBlockFacePowered(BlockFace face) { return (this.chunk.getHandle()).world.isBlockFacePowered(this.x, this.y, this.z, blockFaceToNotch(face)); }
/*     */ 
/*     */ 
/*     */   
/* 282 */   public boolean isBlockFaceIndirectlyPowered(BlockFace face) { return (this.chunk.getHandle()).world.isBlockFaceIndirectlyPowered(this.x, this.y, this.z, blockFaceToNotch(face)); }
/*     */ 
/*     */   
/*     */   public int getBlockPower(BlockFace face) {
/* 286 */     int power = 0;
/* 287 */     BlockRedstoneWire wire = (BlockRedstoneWire)Block.REDSTONE_WIRE;
/* 288 */     World world = (this.chunk.getHandle()).world;
/* 289 */     if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y - 1, this.z, 0)) power = wire.getPower(world, this.x, this.y - 1, this.z, power); 
/* 290 */     if ((face == BlockFace.UP || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y + 1, this.z, 1)) power = wire.getPower(world, this.x, this.y + 1, this.z, power); 
/* 291 */     if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y, this.z - 1, 2)) power = wire.getPower(world, this.x, this.y, this.z - 1, power); 
/* 292 */     if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y, this.z + 1, 3)) power = wire.getPower(world, this.x, this.y, this.z + 1, power); 
/* 293 */     if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.isBlockFacePowered(this.x - 1, this.y, this.z, 4)) power = wire.getPower(world, this.x - 1, this.y, this.z, power); 
/* 294 */     if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.isBlockFacePowered(this.x + 1, this.y, this.z, 5)) power = wire.getPower(world, this.x + 1, this.y, this.z, power); 
/* 295 */     return (power > 0) ? power : (((face == BlockFace.SELF) ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face)) ? 15 : 0);
/*     */   }
/*     */ 
/*     */   
/* 299 */   public int getBlockPower() { return getBlockPower(BlockFace.SELF); }
/*     */ 
/*     */ 
/*     */   
/* 303 */   public boolean isEmpty() { return (getType() == Material.AIR); }
/*     */ 
/*     */ 
/*     */   
/* 307 */   public boolean isLiquid() { return (getType() == Material.WATER || getType() == Material.STATIONARY_WATER || getType() == Material.LAVA || getType() == Material.STATIONARY_LAVA); }
/*     */ 
/*     */ 
/*     */   
/* 311 */   public PistonMoveReaction getPistonMoveReaction() { return PistonMoveReaction.getById((Block.byId[getTypeId()]).material.j()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\block\CraftBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */