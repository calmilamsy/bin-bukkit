/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Diode
/*     */   extends MaterialData implements Directional {
/*   8 */   public Diode() { super(Material.DIODE_BLOCK_ON); }
/*     */ 
/*     */ 
/*     */   
/*  12 */   public Diode(int type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  16 */   public Diode(Material type) { super(type); }
/*     */ 
/*     */ 
/*     */   
/*  20 */   public Diode(int type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */   
/*  24 */   public Diode(Material type, byte data) { super(type, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDelay(int delay) {
/*  34 */     if (delay > 4) {
/*  35 */       delay = 4;
/*     */     }
/*  37 */     if (delay < 1) {
/*  38 */       delay = 1;
/*     */     }
/*  40 */     byte newData = (byte)(getData() & 0x3);
/*     */     
/*  42 */     setData((byte)(newData | delay - 1 << 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public int getDelay() { return (getData() >> 2) + 1; }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/*  55 */     int delay = getDelay();
/*     */ 
/*     */     
/*  58 */     switch (face) {
/*     */       case SOUTH:
/*  60 */         data = 1;
/*     */         break;
/*     */       
/*     */       case WEST:
/*  64 */         data = 2;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  68 */         data = 3;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  73 */         data = 0;
/*     */         break;
/*     */     } 
/*  76 */     setData(data);
/*  77 */     setDelay(delay);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  81 */     byte data = (byte)(getData() & 0x3);
/*     */     
/*  83 */     switch (data) {
/*     */       
/*     */       default:
/*  86 */         return BlockFace.EAST;
/*     */       
/*     */       case 1:
/*  89 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 2:
/*  92 */         return BlockFace.WEST;
/*     */       case 3:
/*     */         break;
/*  95 */     }  return BlockFace.NORTH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public String toString() { return super.toString() + " facing " + getFacing() + " with " + getDelay() + " ticks delay"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\Diode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */