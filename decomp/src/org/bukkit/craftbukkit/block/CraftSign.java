/*    */ package org.bukkit.craftbukkit.block;
/*    */ 
/*    */ import net.minecraft.server.TileEntitySign;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Sign;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ 
/*    */ public class CraftSign extends CraftBlockState implements Sign {
/*    */   private final CraftWorld world;
/*    */   private final TileEntitySign sign;
/*    */   
/*    */   public CraftSign(Block block) {
/* 13 */     super(block);
/*    */     
/* 15 */     this.world = (CraftWorld)block.getWorld();
/* 16 */     this.sign = (TileEntitySign)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */ 
/*    */   
/* 20 */   public String[] getLines() { return this.sign.lines; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public String getLine(int index) throws IndexOutOfBoundsException { return this.sign.lines[index]; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public void setLine(int index, String line) throws IndexOutOfBoundsException { this.sign.lines[index] = line; }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean update(boolean force) {
/* 33 */     boolean result = super.update(force);
/*    */     
/* 35 */     if (result) {
/* 36 */       this.sign.update();
/*    */     }
/*    */     
/* 39 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\block\CraftSign.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */