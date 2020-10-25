/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class MaterialLiquid extends Material {
/*    */   public MaterialLiquid(MaterialMapColor paramMaterialMapColor) {
/*  5 */     super(paramMaterialMapColor);
/*  6 */     f();
/*  7 */     k();
/*    */   }
/*    */ 
/*    */   
/* 11 */   public boolean isLiquid() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public boolean isSolid() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public boolean isBuildable() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MaterialLiquid.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */