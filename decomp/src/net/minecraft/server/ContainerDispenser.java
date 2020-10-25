/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerDispenser
/*    */   extends Container
/*    */ {
/*    */   private TileEntityDispenser a;
/*    */   
/*    */   public ContainerDispenser(IInventory paramIInventory, TileEntityDispenser paramTileEntityDispenser) {
/* 11 */     this.a = paramTileEntityDispenser;
/*    */     byte b;
/* 13 */     for (b = 0; b < 3; b++) {
/* 14 */       for (byte b1 = 0; b1 < 3; b1++) {
/* 15 */         a(new Slot(paramTileEntityDispenser, b1 + b * 3, 62 + b1 * 18, 17 + b * 18));
/*    */       }
/*    */     } 
/*    */     
/* 19 */     for (b = 0; b < 3; b++) {
/* 20 */       for (byte b1 = 0; b1 < 9; b1++) {
/* 21 */         a(new Slot(paramIInventory, b1 + b * 9 + 9, 8 + b1 * 18, 84 + b * 18));
/*    */       }
/*    */     } 
/* 24 */     for (b = 0; b < 9; b++) {
/* 25 */       a(new Slot(paramIInventory, b, 8 + b * 18, ''));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 30 */   public boolean b(EntityHuman paramEntityHuman) { return this.a.a_(paramEntityHuman); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ContainerDispenser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */