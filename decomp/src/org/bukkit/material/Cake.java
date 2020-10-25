/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Cake
/*    */   extends MaterialData {
/*  7 */   public Cake() { super(Material.CAKE_BLOCK); }
/*    */ 
/*    */ 
/*    */   
/* 11 */   public Cake(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public Cake(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public Cake(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Cake(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public int getSlicesEaten() { return getData(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public int getSlicesRemaining() { return 6 - getData(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSlicesEaten(int n) {
/* 50 */     if (n < 6) {
/* 51 */       setData((byte)n);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSlicesRemaining(int n) {
/* 61 */     if (n > 6) {
/* 62 */       n = 6;
/*    */     }
/* 64 */     setData((byte)(6 - n));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public String toString() { return super.toString() + " " + getSlicesEaten() + "/" + getSlicesRemaining() + " slices eaten/remaining"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Cake.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */