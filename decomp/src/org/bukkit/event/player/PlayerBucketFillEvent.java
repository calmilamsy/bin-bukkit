/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class PlayerBucketFillEvent
/*    */   extends PlayerBucketEvent
/*    */ {
/* 14 */   public PlayerBucketFillEvent(Player who, Block blockClicked, BlockFace blockFace, Material bucket, ItemStack itemInHand) { super(Event.Type.PLAYER_BUCKET_FILL, who, blockClicked, blockFace, bucket, itemInHand); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerBucketFillEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */