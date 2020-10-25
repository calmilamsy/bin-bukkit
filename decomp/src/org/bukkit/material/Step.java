/*    */ package org.bukkit.material;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Step
/*    */   extends MaterialData
/*    */ {
/* 11 */   private static HashSet<Material> stepTypes = new HashSet();
/*    */   static  {
/* 13 */     stepTypes.add(Material.SANDSTONE);
/* 14 */     stepTypes.add(Material.WOOD);
/* 15 */     stepTypes.add(Material.COBBLESTONE);
/* 16 */     stepTypes.add(Material.STONE);
/*    */   }
/*    */ 
/*    */   
/* 20 */   public Step() { super(Material.STEP); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Step(int type) { super(type); }
/*    */ 
/*    */   
/*    */   public Step(Material type) {
/* 28 */     super(stepTypes.contains(type) ? Material.STEP : type);
/* 29 */     if (stepTypes.contains(type)) {
/* 30 */       setMaterial(type);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 35 */   public Step(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public Step(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Material getMaterial() {
/* 48 */     switch (getData()) {
/*    */       case 1:
/* 50 */         return Material.SANDSTONE;
/*    */       
/*    */       case 2:
/* 53 */         return Material.WOOD;
/*    */       
/*    */       case 3:
/* 56 */         return Material.COBBLESTONE;
/*    */     } 
/*    */ 
/*    */     
/* 60 */     return Material.STONE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMaterial(Material material) {
/* 70 */     switch (material) {
/*    */       case SANDSTONE:
/* 72 */         setData((byte)1);
/*    */         return;
/*    */       
/*    */       case WOOD:
/* 76 */         setData((byte)2);
/*    */         return;
/*    */       
/*    */       case COBBLESTONE:
/* 80 */         setData((byte)3);
/*    */         return;
/*    */     } 
/*    */ 
/*    */     
/* 85 */     setData((byte)0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 91 */   public String toString() { return getMaterial() + " " + super.toString(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Step.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */