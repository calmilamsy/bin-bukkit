/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum EnumCreatureType
/*    */ {
/* 10 */   MONSTER(IMonster.class, 70, Material.AIR, false),
/*    */   
/* 12 */   CREATURE(EntityAnimal.class, 15, Material.AIR, true),
/*    */   
/* 14 */   WATER_CREATURE(EntityWaterAnimal.class, 5, Material.WATER, true);
/*    */   
/*    */   private final Class d;
/*    */   private final int e;
/*    */   private final Material f;
/*    */   private final boolean g;
/*    */   
/*    */   EnumCreatureType(Class paramClass, int paramInt1, Material paramMaterial, boolean paramBoolean) {
/* 22 */     this.d = paramClass;
/* 23 */     this.e = paramInt1;
/* 24 */     this.f = paramMaterial;
/* 25 */     this.g = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/* 29 */   public Class a() { return this.d; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public int b() { return this.e; }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public Material c() { return this.f; }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public boolean d() { return this.g; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EnumCreatureType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */