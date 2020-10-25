/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.TreeSpecies;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Leaves
/*    */   extends MaterialData
/*    */ {
/* 11 */   public Leaves() { super(Material.LEAVES); }
/*    */ 
/*    */   
/*    */   public Leaves(TreeSpecies species) {
/* 15 */     this();
/* 16 */     setSpecies(species);
/*    */   }
/*    */ 
/*    */   
/* 20 */   public Leaves(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Leaves(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public Leaves(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Leaves(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public TreeSpecies getSpecies() { return TreeSpecies.getByData(getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void setSpecies(TreeSpecies species) { setData(species.getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String toString() { return getSpecies() + " " + super.toString(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Leaves.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */