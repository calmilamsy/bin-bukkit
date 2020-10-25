/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class MaterialLogic
/*    */   extends Material {
/*  5 */   public MaterialLogic(MaterialMapColor paramMaterialMapColor) { super(paramMaterialMapColor); }
/*    */ 
/*    */ 
/*    */   
/*  9 */   public boolean isBuildable() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 13 */   public boolean blocksLight() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public boolean isSolid() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MaterialLogic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */