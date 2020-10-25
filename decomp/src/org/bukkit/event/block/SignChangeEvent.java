/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class SignChangeEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancel = false;
/*    */   private Player player;
/*    */   private String[] lines;
/*    */   
/*    */   public SignChangeEvent(Block theBlock, Player thePlayer, String[] theLines) {
/* 18 */     super(Event.Type.SIGN_CHANGE, theBlock);
/* 19 */     this.player = thePlayer;
/* 20 */     this.lines = theLines;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public Player getPlayer() { return this.player; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public String[] getLines() { return this.lines; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public String getLine(int index) throws IndexOutOfBoundsException { return this.lines[index]; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public void setLine(int index, String line) throws IndexOutOfBoundsException { this.lines[index] = line; }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\SignChangeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */