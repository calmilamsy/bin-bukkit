/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomeBase
/*     */ {
/*  26 */   public static final BiomeBase RAINFOREST = (new BiomeRainforest()).b(588342).a("Rainforest").a(2094168);
/*  27 */   public static final BiomeBase SWAMPLAND = (new BiomeSwamp()).b(522674).a("Swampland").a(9154376);
/*  28 */   public static final BiomeBase SEASONAL_FOREST = (new BiomeBase()).b(10215459).a("Seasonal Forest");
/*  29 */   public static final BiomeBase FOREST = (new BiomeForest()).b(353825).a("Forest").a(5159473);
/*  30 */   public static final BiomeBase SAVANNA = (new BiomeDesert()).b(14278691).a("Savanna");
/*  31 */   public static final BiomeBase SHRUBLAND = (new BiomeBase()).b(10595616).a("Shrubland");
/*  32 */   public static final BiomeBase TAIGA = (new BiomeTaiga()).b(3060051).a("Taiga").b().a(8107825);
/*  33 */   public static final BiomeBase DESERT = (new BiomeDesert()).b(16421912).a("Desert").e();
/*  34 */   public static final BiomeBase PLAINS = (new BiomeDesert()).b(16767248).a("Plains");
/*  35 */   public static final BiomeBase ICE_DESERT = (new BiomeDesert()).b(16772499).a("Ice Desert").b().e().a(12899129);
/*  36 */   public static final BiomeBase TUNDRA = (new BiomeBase()).b(5762041).a("Tundra").b().a(12899129);
/*     */   
/*  38 */   public static final BiomeBase HELL = (new BiomeHell()).b(16711680).a("Hell").e();
/*  39 */   public static final BiomeBase SKY = (new BiomeSky()).b(8421631).a("Sky").e();
/*     */   
/*     */   public String n;
/*     */   
/*     */   public int o;
/*     */   
/*     */   public byte p;
/*     */   public byte q;
/*     */   public int r;
/*     */   protected List s;
/*     */   protected List t;
/*     */   protected List u;
/*     */   private boolean v;
/*     */   private boolean w;
/*  53 */   private static BiomeBase[] x = new BiomeBase[4096]; protected BiomeBase() { this.p = (byte)Block.GRASS.id; this.q = (byte)Block.DIRT.id; this.r = 5169201; this.s = new ArrayList();
/*     */     this.t = new ArrayList();
/*     */     this.u = new ArrayList();
/*     */     this.w = true;
/*  57 */     this.s.add(new BiomeMeta(EntitySpider.class, 10));
/*  58 */     this.s.add(new BiomeMeta(EntityZombie.class, 10));
/*  59 */     this.s.add(new BiomeMeta(EntitySkeleton.class, 10));
/*  60 */     this.s.add(new BiomeMeta(EntityCreeper.class, 10));
/*  61 */     this.s.add(new BiomeMeta(EntitySlime.class, 10));
/*     */     
/*  63 */     this.t.add(new BiomeMeta(EntitySheep.class, 12));
/*  64 */     this.t.add(new BiomeMeta(EntityPig.class, 10));
/*  65 */     this.t.add(new BiomeMeta(EntityChicken.class, 10));
/*  66 */     this.t.add(new BiomeMeta(EntityCow.class, 8));
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.u.add(new BiomeMeta(EntitySquid.class, 10)); }
/*     */ 
/*     */   
/*     */   private BiomeBase e() {
/*  74 */     this.w = false;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public static void a() {
/*  79 */     for (byte b = 0; b < 64; b++) {
/*  80 */       for (byte b1 = 0; b1 < 64; b1++) {
/*  81 */         x[b + b1 * 64] = a(b / 63.0F, b1 / 63.0F);
/*     */       }
/*     */     } 
/*     */     
/*  85 */     DESERT.p = DESERT.q = (byte)Block.SAND.id;
/*  86 */     ICE_DESERT.p = ICE_DESERT.q = (byte)Block.SAND.id;
/*     */   }
/*     */   
/*     */   static  {
/*  90 */     a();
/*     */   }
/*     */   
/*     */   public WorldGenerator a(Random paramRandom) {
/*  94 */     if (paramRandom.nextInt(10) == 0) {
/*  95 */       return new WorldGenBigTree();
/*     */     }
/*  97 */     return new WorldGenTrees();
/*     */   }
/*     */   
/*     */   protected BiomeBase b() {
/* 101 */     this.v = true;
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase a(String paramString) {
/* 106 */     this.n = paramString;
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase a(int paramInt) {
/* 111 */     this.r = paramInt;
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase b(int paramInt) {
/* 116 */     this.o = paramInt;
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public static BiomeBase a(double paramDouble1, double paramDouble2) {
/* 121 */     int i = (int)(paramDouble1 * 63.0D);
/* 122 */     int j = (int)(paramDouble2 * 63.0D);
/* 123 */     return x[i + j * 64];
/*     */   }
/*     */   
/*     */   public static BiomeBase a(float paramFloat1, float paramFloat2) {
/* 127 */     paramFloat2 *= paramFloat1;
/* 128 */     if (paramFloat1 < 0.1F)
/* 129 */       return TUNDRA; 
/* 130 */     if (paramFloat2 < 0.2F) {
/* 131 */       if (paramFloat1 < 0.5F)
/* 132 */         return TUNDRA; 
/* 133 */       if (paramFloat1 < 0.95F) {
/* 134 */         return SAVANNA;
/*     */       }
/* 136 */       return DESERT;
/*     */     } 
/* 138 */     if (paramFloat2 > 0.5F && paramFloat1 < 0.7F)
/* 139 */       return SWAMPLAND; 
/* 140 */     if (paramFloat1 < 0.5F)
/* 141 */       return TAIGA; 
/* 142 */     if (paramFloat1 < 0.97F) {
/* 143 */       if (paramFloat2 < 0.35F) {
/* 144 */         return SHRUBLAND;
/*     */       }
/* 146 */       return FOREST;
/*     */     } 
/*     */     
/* 149 */     if (paramFloat2 < 0.45F)
/* 150 */       return PLAINS; 
/* 151 */     if (paramFloat2 < 0.9F) {
/* 152 */       return SEASONAL_FOREST;
/*     */     }
/* 154 */     return RAINFOREST;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List a(EnumCreatureType paramEnumCreatureType) {
/* 178 */     if (paramEnumCreatureType == EnumCreatureType.MONSTER) return this.s; 
/* 179 */     if (paramEnumCreatureType == EnumCreatureType.CREATURE) return this.t; 
/* 180 */     if (paramEnumCreatureType == EnumCreatureType.WATER_CREATURE) return this.u; 
/* 181 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/* 195 */   public boolean c() { return this.v; }
/*     */ 
/*     */   
/*     */   public boolean d() {
/* 199 */     if (this.v) return false; 
/* 200 */     return this.w;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BiomeBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */