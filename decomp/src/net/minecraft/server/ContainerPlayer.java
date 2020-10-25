/*     */ package net.minecraft.server;
/*     */ 
/*     */ public class ContainerPlayer
/*     */   extends Container
/*     */ {
/*     */   public InventoryCrafting craftInventory;
/*     */   public IInventory resultInventory;
/*     */   public boolean c;
/*     */   
/*  10 */   public ContainerPlayer(InventoryPlayer inventoryplayer) { this(inventoryplayer, true); }
/*     */ 
/*     */   
/*     */   public ContainerPlayer(InventoryPlayer inventoryplayer, boolean flag) {
/*  14 */     this.craftInventory = new InventoryCrafting(this, 2, 2);
/*  15 */     this.resultInventory = new InventoryCraftResult();
/*  16 */     this.c = false;
/*  17 */     this.c = flag;
/*  18 */     a(new SlotResult(inventoryplayer.d, this.craftInventory, this.resultInventory, false, '', 36));
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/*  23 */     for (i = 0; i < 2; i++) {
/*  24 */       for (int j = 0; j < 2; j++) {
/*  25 */         a(new Slot(this.craftInventory, j + i * 2, 88 + j * 18, 26 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  29 */     for (i = 0; i < 4; i++) {
/*  30 */       a(new SlotArmor(this, inventoryplayer, inventoryplayer.getSize() - 1 - i, 8, 8 + i * 18, i));
/*     */     }
/*     */     
/*  33 */     for (i = 0; i < 3; i++) {
/*  34 */       for (int j = 0; j < 9; j++) {
/*  35 */         a(new Slot(inventoryplayer, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  39 */     for (i = 0; i < 9; i++) {
/*  40 */       a(new Slot(inventoryplayer, i, 8 + i * 18, ''));
/*     */     }
/*     */     
/*  43 */     a(this.craftInventory);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(IInventory iinventory) {
/*  48 */     ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory);
/*  49 */     this.resultInventory.setItem(0, craftResult);
/*  50 */     if (this.listeners.size() < 1) {
/*     */       return;
/*     */     }
/*     */     
/*  54 */     EntityPlayer player = (EntityPlayer)this.listeners.get(0);
/*  55 */     player.netServerHandler.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, false, craftResult));
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(EntityHuman entityhuman) {
/*  60 */     super.a(entityhuman);
/*     */     
/*  62 */     for (int i = 0; i < 4; i++) {
/*  63 */       ItemStack itemstack = this.craftInventory.getItem(i);
/*     */       
/*  65 */       if (itemstack != null) {
/*  66 */         entityhuman.b(itemstack);
/*  67 */         this.craftInventory.setItem(i, (ItemStack)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  73 */   public boolean b(EntityHuman entityhuman) { return true; }
/*     */ 
/*     */   
/*     */   public ItemStack a(int i) {
/*  77 */     ItemStack itemstack = null;
/*  78 */     Slot slot = (Slot)this.e.get(i);
/*     */     
/*  80 */     if (slot != null && slot.b()) {
/*  81 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  83 */       itemstack = itemstack1.cloneItemStack();
/*  84 */       if (i == 0) {
/*  85 */         a(itemstack1, 9, 45, true);
/*  86 */       } else if (i >= 9 && i < 36) {
/*  87 */         a(itemstack1, 36, 45, false);
/*  88 */       } else if (i >= 36 && i < 45) {
/*  89 */         a(itemstack1, 9, 36, false);
/*     */       } else {
/*  91 */         a(itemstack1, 9, 45, false);
/*     */       } 
/*     */       
/*  94 */       if (itemstack1.count == 0) {
/*  95 */         slot.c((ItemStack)null);
/*     */       } else {
/*  97 */         slot.c();
/*     */       } 
/*     */       
/* 100 */       if (itemstack1.count == itemstack.count) {
/* 101 */         return null;
/*     */       }
/*     */       
/* 104 */       slot.a(itemstack1);
/*     */     } 
/*     */     
/* 107 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ContainerPlayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */