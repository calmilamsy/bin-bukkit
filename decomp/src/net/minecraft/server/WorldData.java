/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class WorldData
/*     */ {
/*     */   private long a;
/*     */   private int b;
/*     */   private int c;
/*     */   private int d;
/*     */   private long e;
/*     */   private long f;
/*     */   private long g;
/*     */   private NBTTagCompound h;
/*     */   private int i;
/*     */   public String name;
/*     */   private int k;
/*     */   private boolean l;
/*     */   private int m;
/*     */   private boolean n;
/*     */   private int o;
/*     */   
/*     */   public WorldData(NBTTagCompound nbttagcompound) {
/*  24 */     this.a = nbttagcompound.getLong("RandomSeed");
/*  25 */     this.b = nbttagcompound.e("SpawnX");
/*  26 */     this.c = nbttagcompound.e("SpawnY");
/*  27 */     this.d = nbttagcompound.e("SpawnZ");
/*  28 */     this.e = nbttagcompound.getLong("Time");
/*  29 */     this.f = nbttagcompound.getLong("LastPlayed");
/*  30 */     this.g = nbttagcompound.getLong("SizeOnDisk");
/*  31 */     this.name = nbttagcompound.getString("LevelName");
/*  32 */     this.k = nbttagcompound.e("version");
/*  33 */     this.m = nbttagcompound.e("rainTime");
/*  34 */     this.l = nbttagcompound.m("raining");
/*  35 */     this.o = nbttagcompound.e("thunderTime");
/*  36 */     this.n = nbttagcompound.m("thundering");
/*  37 */     if (nbttagcompound.hasKey("Player")) {
/*  38 */       this.h = nbttagcompound.k("Player");
/*  39 */       this.i = this.h.e("Dimension");
/*     */     } 
/*     */   }
/*     */   
/*     */   public WorldData(long i, String s) {
/*  44 */     this.a = i;
/*  45 */     this.name = s;
/*     */   }
/*     */   
/*     */   public WorldData(WorldData worlddata) {
/*  49 */     this.a = worlddata.a;
/*  50 */     this.b = worlddata.b;
/*  51 */     this.c = worlddata.c;
/*  52 */     this.d = worlddata.d;
/*  53 */     this.e = worlddata.e;
/*  54 */     this.f = worlddata.f;
/*  55 */     this.g = worlddata.g;
/*  56 */     this.h = worlddata.h;
/*  57 */     this.i = worlddata.i;
/*  58 */     this.name = worlddata.name;
/*  59 */     this.k = worlddata.k;
/*  60 */     this.m = worlddata.m;
/*  61 */     this.l = worlddata.l;
/*  62 */     this.o = worlddata.o;
/*  63 */     this.n = worlddata.n;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a() {
/*  67 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/*  69 */     a(nbttagcompound, this.h);
/*  70 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(List list) {
/*  74 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  75 */     EntityHuman entityhuman = null;
/*  76 */     NBTTagCompound nbttagcompound1 = null;
/*     */     
/*  78 */     if (list.size() > 0) {
/*  79 */       entityhuman = (EntityHuman)list.get(0);
/*     */     }
/*     */     
/*  82 */     if (entityhuman != null) {
/*  83 */       nbttagcompound1 = new NBTTagCompound();
/*  84 */       entityhuman.d(nbttagcompound1);
/*     */     } 
/*     */     
/*  87 */     a(nbttagcompound, nbttagcompound1);
/*  88 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   private void a(NBTTagCompound nbttagcompound, NBTTagCompound nbttagcompound1) {
/*  92 */     nbttagcompound.setLong("RandomSeed", this.a);
/*  93 */     nbttagcompound.a("SpawnX", this.b);
/*  94 */     nbttagcompound.a("SpawnY", this.c);
/*  95 */     nbttagcompound.a("SpawnZ", this.d);
/*  96 */     nbttagcompound.setLong("Time", this.e);
/*  97 */     nbttagcompound.setLong("SizeOnDisk", this.g);
/*  98 */     nbttagcompound.setLong("LastPlayed", System.currentTimeMillis());
/*  99 */     nbttagcompound.setString("LevelName", this.name);
/* 100 */     nbttagcompound.a("version", this.k);
/* 101 */     nbttagcompound.a("rainTime", this.m);
/* 102 */     nbttagcompound.a("raining", this.l);
/* 103 */     nbttagcompound.a("thunderTime", this.o);
/* 104 */     nbttagcompound.a("thundering", this.n);
/* 105 */     if (nbttagcompound1 != null) {
/* 106 */       nbttagcompound.a("Player", nbttagcompound1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 111 */   public long getSeed() { return this.a; }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public int c() { return this.b; }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public int d() { return this.c; }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public int e() { return this.d; }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public long f() { return this.e; }
/*     */ 
/*     */ 
/*     */   
/* 131 */   public long g() { return this.g; }
/*     */ 
/*     */ 
/*     */   
/* 135 */   public int h() { return this.i; }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public void a(long i) { this.e = i; }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public void b(long i) { this.g = i; }
/*     */ 
/*     */   
/*     */   public void setSpawn(int i, int j, int k) {
/* 147 */     this.b = i;
/* 148 */     this.c = j;
/* 149 */     this.d = k;
/*     */   }
/*     */ 
/*     */   
/* 153 */   public void a(String s) { this.name = s; }
/*     */ 
/*     */ 
/*     */   
/* 157 */   public int i() { return this.k; }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public void a(int i) { this.k = i; }
/*     */ 
/*     */ 
/*     */   
/* 165 */   public boolean isThundering() { return this.n; }
/*     */ 
/*     */ 
/*     */   
/* 169 */   public void setThundering(boolean flag) { this.n = flag; }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public int getThunderDuration() { return this.o; }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public void setThunderDuration(int i) { this.o = i; }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public boolean hasStorm() { return this.l; }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public void setStorm(boolean flag) { this.l = flag; }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public int getWeatherDuration() { return this.m; }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public void setWeatherDuration(int i) { this.m = i; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */