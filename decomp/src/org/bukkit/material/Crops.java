/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.CropState;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Crops
/*    */   extends MaterialData
/*    */ {
/* 11 */   public Crops() { super(Material.CROPS); }
/*    */ 
/*    */   
/*    */   public Crops(CropState state) {
/* 15 */     this();
/* 16 */     setState(state);
/*    */   }
/*    */ 
/*    */   
/* 20 */   public Crops(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Crops(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public Crops(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Crops(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public CropState getState() { return CropState.getByData(getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void setState(CropState state) { setData(state.getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String toString() { return getState() + " " + super.toString(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Crops.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */