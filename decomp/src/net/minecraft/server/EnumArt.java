/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum EnumArt
/*    */ {
/*    */   public static final int z;
/*    */   public final String A;
/* 17 */   KEBAB("Kebab", 16, 16, false, false),
/* 18 */   AZTEC("Aztec", 16, 16, 16, false),
/* 19 */   ALBAN("Alban", 16, 16, 32, false),
/* 20 */   AZTEC2("Aztec2", 16, 16, 48, false),
/* 21 */   BOMB("Bomb", 16, 16, 64, false),
/* 22 */   PLANT("Plant", 16, 16, 80, false),
/* 23 */   WASTELAND("Wasteland", 16, 16, 96, false),
/*    */   
/* 25 */   POOL("Pool", 32, 16, false, 32),
/* 26 */   COURBET("Courbet", 32, 16, 32, 32),
/* 27 */   SEA("Sea", 32, 16, 64, 32),
/* 28 */   SUNSET("Sunset", 32, 16, 96, 32),
/* 29 */   CREEBET("Creebet", 32, 16, '', 32),
/*    */   
/* 31 */   WANDERER("Wanderer", 16, 32, false, 64),
/* 32 */   GRAHAM("Graham", 16, 32, 16, 64),
/*    */   
/* 34 */   MATCH("Match", 32, 32, false, ''),
/* 35 */   BUST("Bust", 32, 32, 32, ''),
/* 36 */   STAGE("Stage", 32, 32, 64, ''),
/* 37 */   VOID("Void", 32, 32, 96, ''),
/* 38 */   SKULL_AND_ROSES("SkullAndRoses", 32, 32, '', ''),
/* 39 */   FIGHTERS("Fighters", 64, 32, false, 96),
/*    */   
/* 41 */   POINTER("Pointer", 64, 64, false, 'À'),
/* 42 */   PIGSCENE("Pigscene", 64, 64, 64, 'À'),
/* 43 */   BURNINGSKULL("BurningSkull", 64, 64, '', 'À'),
/*    */   
/* 45 */   SKELETON("Skeleton", 64, 48, 'À', 64),
/* 46 */   DONKEYKONG("DonkeyKong", 64, 48, 'À', 112);
/*    */   public final int B;
/*    */   
/*    */   static  {
/* 50 */     z = "SkullAndRoses".length();
/*    */   }
/*    */ 
/*    */   
/*    */   public final int C;
/*    */   
/*    */   EnumArt(String paramString1, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 57 */     this.A = paramString1;
/* 58 */     this.B = paramInt1;
/* 59 */     this.C = paramInt2;
/* 60 */     this.D = paramInt3;
/* 61 */     this.E = paramInt4;
/*    */   }
/*    */   
/*    */   public final int D;
/*    */   public final int E;
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EnumArt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */