/*     */ package net.minecraft.server;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerFurnace
/*     */   extends Container
/*     */ {
/*     */   private TileEntityFurnace a;
/*     */   private int b;
/*     */   private int c;
/*     */   private int h;
/*     */   
/*     */   public ContainerFurnace(InventoryPlayer paramInventoryPlayer, TileEntityFurnace paramTileEntityFurnace) {
/*  38 */     this.b = 0;
/*  39 */     this.c = 0;
/*  40 */     this.h = 0; this.a = paramTileEntityFurnace; a(new Slot(paramTileEntityFurnace, false, 56, 17)); a(new Slot(paramTileEntityFurnace, true, 56, 53)); a(new SlotResult2(paramInventoryPlayer.d, paramTileEntityFurnace, 2, 116, 35)); byte b1; for (b1 = 0; b1 < 3; b1++) { for (byte b2 = 0; b2 < 9; b2++)
/*     */         a(new Slot(paramInventoryPlayer, b2 + b1 * 9 + 9, 8 + b2 * 18, 84 + b1 * 18));  }
/*     */      for (b1 = 0; b1 < 9; b1++)
/*  43 */       a(new Slot(paramInventoryPlayer, b1, 8 + b1 * 18, ''));  } public void a(ICrafting paramICrafting) { super.a(paramICrafting);
/*  44 */     paramICrafting.a(this, 0, this.a.cookTime);
/*  45 */     paramICrafting.a(this, 1, this.a.burnTime);
/*  46 */     paramICrafting.a(this, 2, this.a.ticksForCurrentFuel); }
/*     */ 
/*     */   
/*     */   public void a() {
/*  50 */     super.a();
/*     */     
/*  52 */     for (byte b1 = 0; b1 < this.listeners.size(); b1++) {
/*  53 */       ICrafting iCrafting = (ICrafting)this.listeners.get(b1);
/*  54 */       if (this.b != this.a.cookTime) {
/*  55 */         iCrafting.a(this, 0, this.a.cookTime);
/*     */       }
/*  57 */       if (this.c != this.a.burnTime) {
/*  58 */         iCrafting.a(this, 1, this.a.burnTime);
/*     */       }
/*  60 */       if (this.h != this.a.ticksForCurrentFuel) {
/*  61 */         iCrafting.a(this, 2, this.a.ticksForCurrentFuel);
/*     */       }
/*     */     } 
/*     */     
/*  65 */     this.b = this.a.cookTime;
/*  66 */     this.c = this.a.burnTime;
/*  67 */     this.h = this.a.ticksForCurrentFuel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public boolean b(EntityHuman paramEntityHuman) { return this.a.a_(paramEntityHuman); }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack a(int paramInt) {
/*  82 */     ItemStack itemStack = null;
/*  83 */     Slot slot = (Slot)this.e.get(paramInt);
/*  84 */     if (slot != null && slot.b()) {
/*  85 */       ItemStack itemStack1 = slot.getItem();
/*  86 */       itemStack = itemStack1.cloneItemStack();
/*     */       
/*  88 */       if (paramInt == 2) {
/*  89 */         a(itemStack1, 3, 39, true);
/*  90 */       } else if (paramInt >= 3 && paramInt < 30) {
/*  91 */         a(itemStack1, 30, 39, false);
/*  92 */       } else if (paramInt >= 30 && paramInt < 39) {
/*  93 */         a(itemStack1, 3, 30, false);
/*     */       } else {
/*  95 */         a(itemStack1, 3, 39, false);
/*     */       } 
/*  97 */       if (itemStack1.count == 0) {
/*  98 */         slot.c(null);
/*     */       } else {
/* 100 */         slot.c();
/*     */       } 
/* 102 */       if (itemStack1.count != itemStack.count) {
/* 103 */         slot.a(itemStack1);
/*     */       } else {
/* 105 */         return null;
/*     */       } 
/*     */     } 
/* 108 */     return itemStack;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ContainerFurnace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */