/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.TreeSpecies;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tree
/*    */   extends MaterialData
/*    */ {
/* 11 */   public Tree() { super(Material.LOG); }
/*    */ 
/*    */   
/*    */   public Tree(TreeSpecies species) {
/* 15 */     this();
/* 16 */     setSpecies(species);
/*    */   }
/*    */ 
/*    */   
/* 20 */   public Tree(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Tree(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public Tree(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Tree(Material type, byte data) { super(type, data); }
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


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Tree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */