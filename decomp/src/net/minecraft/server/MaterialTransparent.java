/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class MaterialTransparent extends Material {
/*    */   public MaterialTransparent(MaterialMapColor paramMaterialMapColor) {
/*  5 */     super(paramMaterialMapColor);
/*  6 */     f();
/*    */   }
/*    */ 
/*    */   
/* 10 */   public boolean isBuildable() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public boolean blocksLight() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public boolean isSolid() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MaterialTransparent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */