/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerNBTManager
/*     */   implements PlayerFileData, IDataManager
/*     */ {
/*  18 */   private static final Logger a = Logger.getLogger("Minecraft"); private final File b;
/*     */   private final File c;
/*     */   
/*     */   public PlayerNBTManager(File file1, String s, boolean flag) {
/*  22 */     this.e = System.currentTimeMillis();
/*  23 */     this.uuid = null;
/*     */ 
/*     */     
/*  26 */     this.b = new File(file1, s);
/*  27 */     this.b.mkdirs();
/*  28 */     this.c = new File(this.b, "players");
/*  29 */     this.d = new File(this.b, "data");
/*  30 */     this.d.mkdirs();
/*  31 */     if (flag) {
/*  32 */       this.c.mkdirs();
/*     */     }
/*     */     
/*  35 */     f();
/*     */   }
/*     */   private final File d; private final long e; private UUID uuid;
/*     */   private void f() {
/*     */     try {
/*  40 */       File file1 = new File(this.b, "session.lock");
/*  41 */       dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/*     */       
/*     */       try {
/*  44 */         dataoutputstream.writeLong(this.e);
/*     */       } finally {
/*  46 */         dataoutputstream.close();
/*     */       } 
/*  48 */     } catch (IOException ioexception) {
/*  49 */       ioexception.printStackTrace();
/*  50 */       throw new RuntimeException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  55 */   protected File a() { return this.b; }
/*     */ 
/*     */   
/*     */   public void b() {
/*     */     try {
/*  60 */       File file1 = new File(this.b, "session.lock");
/*  61 */       datainputstream = new DataInputStream(new FileInputStream(file1));
/*     */       
/*     */       try {
/*  64 */         if (datainputstream.readLong() != this.e) {
/*  65 */           throw new MinecraftException("The save is being accessed from another location, aborting");
/*     */         }
/*     */       } finally {
/*  68 */         datainputstream.close();
/*     */       } 
/*  70 */     } catch (IOException ioexception) {
/*  71 */       throw new MinecraftException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */   
/*     */   public IChunkLoader a(WorldProvider worldprovider) {
/*  76 */     if (worldprovider instanceof WorldProviderHell) {
/*  77 */       File file1 = new File(this.b, "DIM-1");
/*     */       
/*  79 */       file1.mkdirs();
/*  80 */       return new ChunkLoader(file1, true);
/*     */     } 
/*  82 */     return new ChunkLoader(this.b, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldData c() {
/*  87 */     File file1 = new File(this.b, "level.dat");
/*     */ 
/*     */ 
/*     */     
/*  91 */     if (file1.exists()) {
/*     */       try {
/*  93 */         NBTTagCompound nbttagcompound = CompressedStreamTools.a(new FileInputStream(file1));
/*  94 */         NBTTagCompound nbttagcompound1 = nbttagcompound.k("Data");
/*  95 */         return new WorldData(nbttagcompound1);
/*  96 */       } catch (Exception exception) {
/*  97 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 101 */     file1 = new File(this.b, "level.dat_old");
/* 102 */     if (file1.exists()) {
/*     */       try {
/* 104 */         NBTTagCompound nBTTagCompound1 = CompressedStreamTools.a(new FileInputStream(file1));
/* 105 */         NBTTagCompound nBTTagCompound2 = nBTTagCompound1.k("Data");
/* 106 */         return new WorldData(nBTTagCompound2);
/* 107 */       } catch (Exception exception1) {
/* 108 */         exception1.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 112 */     return null;
/*     */   }
/*     */   
/*     */   public void a(WorldData worlddata, List list) {
/* 116 */     NBTTagCompound nbttagcompound = worlddata.a(list);
/* 117 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */     
/* 119 */     nbttagcompound1.a("Data", nbttagcompound);
/*     */     
/*     */     try {
/* 122 */       File file1 = new File(this.b, "level.dat_new");
/* 123 */       File file2 = new File(this.b, "level.dat_old");
/* 124 */       File file3 = new File(this.b, "level.dat");
/*     */       
/* 126 */       CompressedStreamTools.a(nbttagcompound1, new FileOutputStream(file1));
/* 127 */       if (file2.exists()) {
/* 128 */         file2.delete();
/*     */       }
/*     */       
/* 131 */       file3.renameTo(file2);
/* 132 */       if (file3.exists()) {
/* 133 */         file3.delete();
/*     */       }
/*     */       
/* 136 */       file1.renameTo(file3);
/* 137 */       if (file1.exists()) {
/* 138 */         file1.delete();
/*     */       }
/* 140 */     } catch (Exception exception) {
/* 141 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(WorldData worlddata) {
/* 146 */     NBTTagCompound nbttagcompound = worlddata.a();
/* 147 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */     
/* 149 */     nbttagcompound1.a("Data", nbttagcompound);
/*     */     
/*     */     try {
/* 152 */       File file1 = new File(this.b, "level.dat_new");
/* 153 */       File file2 = new File(this.b, "level.dat_old");
/* 154 */       File file3 = new File(this.b, "level.dat");
/*     */       
/* 156 */       CompressedStreamTools.a(nbttagcompound1, new FileOutputStream(file1));
/* 157 */       if (file2.exists()) {
/* 158 */         file2.delete();
/*     */       }
/*     */       
/* 161 */       file3.renameTo(file2);
/* 162 */       if (file3.exists()) {
/* 163 */         file3.delete();
/*     */       }
/*     */       
/* 166 */       file1.renameTo(file3);
/* 167 */       if (file1.exists()) {
/* 168 */         file1.delete();
/*     */       }
/* 170 */     } catch (Exception exception) {
/* 171 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(EntityHuman entityhuman) {
/*     */     try {
/* 177 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 179 */       entityhuman.d(nbttagcompound);
/* 180 */       File file1 = new File(this.c, "_tmp_.dat");
/* 181 */       File file2 = new File(this.c, entityhuman.name + ".dat");
/*     */       
/* 183 */       CompressedStreamTools.a(nbttagcompound, new FileOutputStream(file1));
/* 184 */       if (file2.exists()) {
/* 185 */         file2.delete();
/*     */       }
/*     */       
/* 188 */       file1.renameTo(file2);
/* 189 */     } catch (Exception exception) {
/* 190 */       a.warning("Failed to save player data for " + entityhuman.name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 195 */     NBTTagCompound nbttagcompound = a(entityhuman.name);
/*     */     
/* 197 */     if (nbttagcompound != null) {
/* 198 */       entityhuman.e(nbttagcompound);
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(String s) {
/*     */     try {
/* 204 */       File file1 = new File(this.c, s + ".dat");
/*     */       
/* 206 */       if (file1.exists()) {
/* 207 */         return CompressedStreamTools.a(new FileInputStream(file1));
/*     */       }
/* 209 */     } catch (Exception exception) {
/* 210 */       a.warning("Failed to load player data for " + s);
/*     */     } 
/*     */     
/* 213 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 217 */   public PlayerFileData d() { return this; }
/*     */ 
/*     */   
/*     */   public void e() {}
/*     */ 
/*     */   
/* 223 */   public File b(String s) { return new File(this.d, s + ".dat"); }
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getUUID() {
/* 228 */     if (this.uuid != null) return this.uuid; 
/*     */     try {
/* 230 */       File file1 = new File(this.b, "uid.dat");
/* 231 */       if (!file1.exists()) {
/* 232 */         DataOutputStream dos = new DataOutputStream(new FileOutputStream(file1));
/* 233 */         this.uuid = UUID.randomUUID();
/* 234 */         dos.writeLong(this.uuid.getMostSignificantBits());
/* 235 */         dos.writeLong(this.uuid.getLeastSignificantBits());
/* 236 */         dos.close();
/*     */       } else {
/*     */         
/* 239 */         DataInputStream dis = new DataInputStream(new FileInputStream(file1));
/* 240 */         this.uuid = new UUID(dis.readLong(), dis.readLong());
/* 241 */         dis.close();
/*     */       } 
/* 243 */       return this.uuid;
/*     */     }
/* 245 */     catch (IOException ex) {
/* 246 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PlayerNBTManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */