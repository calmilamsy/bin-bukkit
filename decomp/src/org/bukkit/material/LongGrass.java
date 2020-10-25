/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.GrassSpecies;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LongGrass
/*    */   extends MaterialData
/*    */ {
/* 11 */   public LongGrass() { super(Material.LOG); }
/*    */ 
/*    */   
/*    */   public LongGrass(GrassSpecies species) {
/* 15 */     this();
/* 16 */     setSpecies(species);
/*    */   }
/*    */ 
/*    */   
/* 20 */   public LongGrass(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public LongGrass(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public LongGrass(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public LongGrass(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public GrassSpecies getSpecies() { return GrassSpecies.getByData(getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void setSpecies(GrassSpecies species) { setData(species.getData()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String toString() { return getSpecies() + " " + super.toString(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\LongGrass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */