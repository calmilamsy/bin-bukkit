/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class PlayerInteractEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*     */   protected ItemStack item;
/*     */   protected Action action;
/*     */   protected Block blockClicked;
/*     */   protected BlockFace blockFace;
/*     */   private Event.Result useClickedBlock;
/*     */   private Event.Result useItemInHand;
/*     */   
/*     */   public PlayerInteractEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace) {
/*  24 */     super(Event.Type.PLAYER_INTERACT, who);
/*  25 */     this.action = action;
/*  26 */     this.item = item;
/*  27 */     this.blockClicked = clickedBlock;
/*  28 */     this.blockFace = clickedFace;
/*     */     
/*  30 */     this.useItemInHand = Event.Result.DEFAULT;
/*  31 */     this.useClickedBlock = (clickedBlock == null) ? Event.Result.DENY : Event.Result.ALLOW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public Action getAction() { return this.action; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public boolean isCancelled() { return (useInteractedBlock() == Event.Result.DENY); }
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
/*     */   public void setCancelled(boolean cancel) {
/*  64 */     setUseInteractedBlock(cancel ? Event.Result.DENY : ((useInteractedBlock() == Event.Result.DENY) ? Event.Result.DEFAULT : useInteractedBlock()));
/*  65 */     setUseItemInHand(cancel ? Event.Result.DENY : ((useItemInHand() == Event.Result.DENY) ? Event.Result.DEFAULT : useItemInHand()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public ItemStack getItem() { return this.item; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  84 */     if (!hasItem()) {
/*  85 */       return Material.AIR;
/*     */     }
/*     */     
/*  88 */     return this.item.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public boolean hasBlock() { return (this.blockClicked != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public boolean hasItem() { return (this.item != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockInHand() {
/* 116 */     if (!hasItem()) {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     return this.item.getType().isBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public Block getClickedBlock() { return this.blockClicked; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public BlockFace getBlockFace() { return this.blockFace; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public Event.Result useInteractedBlock() { return this.useClickedBlock; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public void setUseInteractedBlock(Event.Result useInteractedBlock) { this.useClickedBlock = useInteractedBlock; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public Event.Result useItemInHand() { return this.useItemInHand; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public void setUseItemInHand(Event.Result useItemInHand) { this.useItemInHand = useItemInHand; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerInteractEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */