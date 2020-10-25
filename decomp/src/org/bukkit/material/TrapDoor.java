/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrapDoor
/*    */   extends SimpleAttachableMaterialData
/*    */ {
/* 11 */   public TrapDoor() { super(Material.TRAP_DOOR); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public TrapDoor(int type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public TrapDoor(Material type) { super(type); }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public TrapDoor(int type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public TrapDoor(Material type, byte data) { super(type, data); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public boolean isOpen() { return ((getData() & 0x4) == 4); }
/*    */ 
/*    */   
/*    */   public BlockFace getAttachedFace() {
/* 40 */     byte data = (byte)(getData() & 0x3);
/*    */     
/* 42 */     switch (data) {
/*    */       case 0:
/* 44 */         return BlockFace.WEST;
/*    */       
/*    */       case 1:
/* 47 */         return BlockFace.EAST;
/*    */       
/*    */       case 2:
/* 50 */         return BlockFace.SOUTH;
/*    */       
/*    */       case 3:
/* 53 */         return BlockFace.NORTH;
/*    */     } 
/*    */     
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/* 61 */     byte data = (byte)(getData() & 0x4);
/*    */     
/* 63 */     switch (face) {
/*    */       case WEST:
/* 65 */         data = (byte)(data | true);
/*    */         break;
/*    */       case NORTH:
/* 68 */         data = (byte)(data | 0x2);
/*    */         break;
/*    */       case SOUTH:
/* 71 */         data = (byte)(data | 0x3);
/*    */         break;
/*    */     } 
/*    */     
/* 75 */     setData(data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 80 */   public String toString() { return (isOpen() ? "OPEN " : "CLOSED ") + super.toString() + " with hinges set " + getAttachedFace(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\TrapDoor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */