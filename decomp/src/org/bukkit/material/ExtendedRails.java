/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtendedRails
/*    */   extends Rails
/*    */ {
/* 11 */   public ExtendedRails(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public ExtendedRails(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public ExtendedRails(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public ExtendedRails(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public boolean isCurve() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   protected byte getConvertedData() { return (byte)(getData() & 0x7); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDirection(BlockFace face, boolean isOnSlope) {
/* 38 */     boolean extraBitSet = ((getData() & 0x8) == 8);
/*    */     
/* 40 */     if (face != BlockFace.NORTH && face != BlockFace.SOUTH && face != BlockFace.EAST && face != BlockFace.WEST) {
/* 41 */       throw new IllegalArgumentException("Detector rails and powered rails cannot be set on a curve!");
/*    */     }
/*    */     
/* 44 */     super.setDirection(face, isOnSlope);
/* 45 */     setData((byte)(extraBitSet ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\ExtendedRails.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */