/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Button
/*     */   extends SimpleAttachableMaterialData
/*     */   implements Redstone
/*     */ {
/*  11 */   public Button() { super(Material.STONE_BUTTON); }
/*     */ 
/*     */ 
/*     */   
/*  15 */   public Button(int type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  19 */   public Button(Material type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  23 */   public Button(int type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */   
/*  27 */   public Button(Material type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public boolean isPowered() { return ((getData() & 0x8) == 8); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public void setPowered(boolean bool) { setData((byte)(bool ? (getData() | 0x8) : (getData() & 0xFFFFFFF7))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  56 */     byte data = (byte)(getData() & 0x7);
/*     */     
/*  58 */     switch (data) {
/*     */       case 1:
/*  60 */         return BlockFace.NORTH;
/*     */       
/*     */       case 2:
/*  63 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 3:
/*  66 */         return BlockFace.EAST;
/*     */       
/*     */       case 4:
/*  69 */         return BlockFace.WEST;
/*     */     } 
/*     */     
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  79 */     byte data = (byte)(getData() & 0x8);
/*     */     
/*  81 */     switch (face) {
/*     */       case SOUTH:
/*  83 */         data = (byte)(data | true);
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  87 */         data = (byte)(data | 0x2);
/*     */         break;
/*     */       
/*     */       case WEST:
/*  91 */         data = (byte)(data | 0x3);
/*     */         break;
/*     */       
/*     */       case EAST:
/*  95 */         data = (byte)(data | 0x4);
/*     */         break;
/*     */     } 
/*     */     
/*  99 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public String toString() { return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Button.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */