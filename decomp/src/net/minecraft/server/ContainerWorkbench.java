/*     */ package net.minecraft.server;public class ContainerWorkbench extends Container { public InventoryCrafting craftInventory; public IInventory resultInventory;
/*     */   private World c;
/*     */   
/*     */   public ContainerWorkbench(InventoryPlayer inventoryplayer, World world, int i, int j, int k) {
/*   5 */     this.craftInventory = new InventoryCrafting(this, 3, 3);
/*   6 */     this.resultInventory = new InventoryCraftResult();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  13 */     this.c = world;
/*  14 */     this.h = i;
/*  15 */     this.i = j;
/*  16 */     this.j = k;
/*  17 */     a(new SlotResult(inventoryplayer.d, this.craftInventory, this.resultInventory, false, 124, 35));
/*     */ 
/*     */     
/*     */     int l;
/*     */     
/*  22 */     for (l = 0; l < 3; l++) {
/*  23 */       for (int i1 = 0; i1 < 3; i1++) {
/*  24 */         a(new Slot(this.craftInventory, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  28 */     for (l = 0; l < 3; l++) {
/*  29 */       for (int i1 = 0; i1 < 9; i1++) {
/*  30 */         a(new Slot(inventoryplayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  34 */     for (l = 0; l < 9; l++) {
/*  35 */       a(new Slot(inventoryplayer, l, 8 + l * 18, ''));
/*     */     }
/*     */     
/*  38 */     a(this.craftInventory);
/*     */   }
/*     */   private int h; private int i; private int j;
/*     */   
/*     */   public void a(IInventory iinventory) {
/*  43 */     ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory);
/*  44 */     this.resultInventory.setItem(0, craftResult);
/*  45 */     if (this.listeners.size() < 1) {
/*     */       return;
/*     */     }
/*     */     
/*  49 */     EntityPlayer player = (EntityPlayer)this.listeners.get(0);
/*  50 */     player.netServerHandler.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, false, craftResult));
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(EntityHuman entityhuman) {
/*  55 */     super.a(entityhuman);
/*  56 */     if (!this.c.isStatic) {
/*  57 */       for (int i = 0; i < 9; i++) {
/*  58 */         ItemStack itemstack = this.craftInventory.getItem(i);
/*     */         
/*  60 */         if (itemstack != null) {
/*  61 */           entityhuman.b(itemstack);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  68 */   public boolean b(EntityHuman entityhuman) { return (this.c.getTypeId(this.h, this.i, this.j) != Block.WORKBENCH.id) ? false : ((entityhuman.e(this.h + 0.5D, this.i + 0.5D, this.j + 0.5D) <= 64.0D)); }
/*     */ 
/*     */   
/*     */   public ItemStack a(int i) {
/*  72 */     ItemStack itemstack = null;
/*  73 */     Slot slot = (Slot)this.e.get(i);
/*     */     
/*  75 */     if (slot != null && slot.b()) {
/*  76 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  78 */       itemstack = itemstack1.cloneItemStack();
/*  79 */       if (i == 0) {
/*  80 */         a(itemstack1, 10, 46, true);
/*  81 */       } else if (i >= 10 && i < 37) {
/*  82 */         a(itemstack1, 37, 46, false);
/*  83 */       } else if (i >= 37 && i < 46) {
/*  84 */         a(itemstack1, 10, 37, false);
/*     */       } else {
/*  86 */         a(itemstack1, 10, 46, false);
/*     */       } 
/*     */       
/*  89 */       if (itemstack1.count == 0) {
/*  90 */         slot.c((ItemStack)null);
/*     */       } else {
/*  92 */         slot.c();
/*     */       } 
/*     */       
/*  95 */       if (itemstack1.count == itemstack.count) {
/*  96 */         return null;
/*     */       }
/*     */       
/*  99 */       slot.a(itemstack1);
/*     */     } 
/*     */     
/* 102 */     return itemstack;
/*     */   } }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ContainerWorkbench.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */