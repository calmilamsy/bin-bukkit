/*     */ package org.bukkit.event.block;
/*     */ 
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ public class BlockPlaceEvent
/*     */   extends BlockEvent
/*     */   implements Cancellable
/*     */ {
/*     */   protected boolean cancel;
/*     */   protected boolean canBuild;
/*     */   protected Block placedAgainst;
/*     */   protected BlockState replacedBlockState;
/*     */   protected ItemStack itemInHand;
/*     */   protected Player player;
/*     */   
/*     */   public BlockPlaceEvent(Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand, Player thePlayer, boolean canBuild) {
/*  23 */     super(Event.Type.BLOCK_PLACE, placedBlock);
/*  24 */     this.placedAgainst = placedAgainst;
/*  25 */     this.itemInHand = itemInHand;
/*  26 */     this.player = thePlayer;
/*  27 */     this.replacedBlockState = replacedBlockState;
/*  28 */     this.canBuild = canBuild;
/*  29 */     this.cancel = false;
/*     */   }
/*     */ 
/*     */   
/*  33 */   public boolean isCancelled() { return this.cancel; }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public Player getPlayer() { return this.player; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public Block getBlockPlaced() { return getBlock(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public BlockState getBlockReplacedState() { return this.replacedBlockState; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public Block getBlockAgainst() { return this.placedAgainst; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public ItemStack getItemInHand() { return this.itemInHand; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public boolean canBuild() { return this.canBuild; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void setBuild(boolean canBuild) { this.canBuild = canBuild; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockPlaceEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */