/*     */ package org.bukkit.craftbukkit.block;
/*     */ 
/*     */ import net.minecraft.server.World;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.CraftChunk;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ public class CraftBlockState implements BlockState {
/*     */   private final CraftWorld world;
/*     */   private final CraftChunk chunk;
/*     */   private final int x;
/*     */   private final int y;
/*     */   private final int z;
/*     */   protected int type;
/*     */   protected MaterialData data;
/*     */   protected byte light;
/*     */   
/*     */   public CraftBlockState(Block block) {
/*  25 */     this.world = (CraftWorld)block.getWorld();
/*  26 */     this.x = block.getX();
/*  27 */     this.y = block.getY();
/*  28 */     this.z = block.getZ();
/*  29 */     this.type = block.getTypeId();
/*  30 */     this.light = block.getLightLevel();
/*  31 */     this.chunk = (CraftChunk)block.getChunk();
/*     */     
/*  33 */     createData(block.getData());
/*     */   }
/*     */ 
/*     */   
/*  37 */   public static CraftBlockState getBlockState(World world, int x, int y, int z) { return new CraftBlockState(world.getWorld().getBlockAt(x, y, z)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public World getWorld() { return this.world; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public int getX() { return this.x; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public int getY() { return this.y; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public int getZ() { return this.z; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public Chunk getChunk() { return this.chunk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(MaterialData data) {
/*  91 */     Material mat = getType();
/*     */     
/*  93 */     if (mat == null || mat.getData() == null) {
/*  94 */       this.data = data;
/*     */     }
/*  96 */     else if (data.getClass() == mat.getData() || data.getClass() == MaterialData.class) {
/*  97 */       this.data = data;
/*     */     } else {
/*  99 */       throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public MaterialData getData() { return this.data; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public void setType(Material type) { setTypeId(type.getId()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setTypeId(int type) {
/* 129 */     this.type = type;
/*     */     
/* 131 */     createData((byte)0);
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public Material getType() { return Material.getMaterial(getTypeId()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public int getTypeId() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public byte getLightLevel() { return this.light; }
/*     */ 
/*     */ 
/*     */   
/* 163 */   public Block getBlock() { return this.world.getBlockAt(this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */   
/* 167 */   public boolean update() { return update(false); }
/*     */ 
/*     */   
/*     */   public boolean update(boolean force) {
/* 171 */     Block block = getBlock();
/*     */     
/* 173 */     synchronized (block) {
/* 174 */       if (block.getType() != getType()) {
/* 175 */         if (force) {
/* 176 */           block.setTypeId(getTypeId());
/*     */         } else {
/* 178 */           return false;
/*     */         } 
/*     */       }
/*     */       
/* 182 */       block.setData(getRawData());
/*     */     } 
/*     */     
/* 185 */     return true;
/*     */   }
/*     */   
/*     */   private void createData(byte data) {
/* 189 */     Material mat = Material.getMaterial(this.type);
/* 190 */     if (mat == null || mat.getData() == null) {
/* 191 */       this.data = new MaterialData(this.type, data);
/*     */     } else {
/* 193 */       this.data = mat.getNewData(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 198 */   public byte getRawData() { return this.data.getData(); }
/*     */ 
/*     */ 
/*     */   
/* 202 */   public Location getLocation() { return new Location(this.world, this.x, this.y, this.z); }
/*     */ 
/*     */ 
/*     */   
/* 206 */   public void setData(byte data) { createData(data); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\block\CraftBlockState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */