/*     */ package net.minecraft.server;
/*     */ 
/*     */ public class Material {
/*   4 */   public static final Material AIR = new MaterialTransparent(MaterialMapColor.b);
/*   5 */   public static final Material GRASS = new Material(MaterialMapColor.c);
/*   6 */   public static final Material EARTH = new Material(MaterialMapColor.l);
/*   7 */   public static final Material WOOD = (new Material(MaterialMapColor.o)).o();
/*   8 */   public static final Material STONE = (new Material(MaterialMapColor.m)).n();
/*   9 */   public static final Material ORE = (new Material(MaterialMapColor.h)).n();
/*  10 */   public static final Material WATER = (new MaterialLiquid(MaterialMapColor.n)).k();
/*  11 */   public static final Material LAVA = (new MaterialLiquid(MaterialMapColor.f)).k();
/*  12 */   public static final Material LEAVES = (new Material(MaterialMapColor.i)).o().m().k();
/*  13 */   public static final Material PLANT = (new MaterialLogic(MaterialMapColor.i)).k();
/*  14 */   public static final Material SPONGE = new Material(MaterialMapColor.e);
/*  15 */   public static final Material CLOTH = (new Material(MaterialMapColor.e)).o();
/*  16 */   public static final Material FIRE = (new MaterialTransparent(MaterialMapColor.b)).k();
/*  17 */   public static final Material SAND = new Material(MaterialMapColor.d);
/*  18 */   public static final Material ORIENTABLE = (new MaterialLogic(MaterialMapColor.b)).k();
/*  19 */   public static final Material SHATTERABLE = (new Material(MaterialMapColor.b)).m();
/*  20 */   public static final Material TNT = (new Material(MaterialMapColor.f)).o().m();
/*  21 */   public static final Material CORAL = (new Material(MaterialMapColor.i)).k();
/*  22 */   public static final Material ICE = (new Material(MaterialMapColor.g)).m();
/*  23 */   public static final Material SNOW_LAYER = (new MaterialLogic(MaterialMapColor.j)).f().m().n().k();
/*  24 */   public static final Material SNOW_BLOCK = (new Material(MaterialMapColor.j)).n();
/*  25 */   public static final Material CACTUS = (new Material(MaterialMapColor.i)).m().k();
/*  26 */   public static final Material CLAY = new Material(MaterialMapColor.k);
/*  27 */   public static final Material PUMPKIN = (new Material(MaterialMapColor.i)).k();
/*  28 */   public static final Material PORTAL = (new MaterialPortal(MaterialMapColor.b)).l();
/*  29 */   public static final Material CAKE = (new Material(MaterialMapColor.b)).k();
/*  30 */   public static final Material WEB = (new Material(MaterialMapColor.e)).n().k();
/*  31 */   public static final Material PISTON = (new Material(MaterialMapColor.m)).l();
/*     */   private boolean canBurn;
/*     */   private boolean E;
/*     */   private boolean F;
/*     */   public final MaterialMapColor C;
/*     */   private boolean G;
/*     */   private int H;
/*     */   
/*     */   public Material(MaterialMapColor paramMaterialMapColor) {
/*  40 */     this.G = true;
/*     */ 
/*     */ 
/*     */     
/*  44 */     this.C = paramMaterialMapColor;
/*     */   }
/*     */ 
/*     */   
/*  48 */   public boolean isLiquid() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public boolean isBuildable() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public boolean blocksLight() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  64 */   public boolean isSolid() { return true; }
/*     */ 
/*     */   
/*     */   private Material m() {
/*  68 */     this.F = true;
/*  69 */     return this;
/*     */   }
/*     */   
/*     */   private Material n() {
/*  73 */     this.G = false;
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   private Material o() {
/*  78 */     this.canBurn = true;
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  83 */   public boolean isBurnable() { return this.canBurn; }
/*     */ 
/*     */   
/*     */   public Material f() {
/*  87 */     this.E = true;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  92 */   public boolean isReplacable() { return this.E; }
/*     */ 
/*     */   
/*     */   public boolean h() {
/*  96 */     if (this.F) return false; 
/*  97 */     return isSolid();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public boolean i() { return this.G; }
/*     */ 
/*     */ 
/*     */   
/* 107 */   public int j() { return this.H; }
/*     */ 
/*     */   
/*     */   protected Material k() {
/* 111 */     this.H = 1;
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   protected Material l() {
/* 116 */     this.H = 2;
/* 117 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Material.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */