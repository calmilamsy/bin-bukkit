/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class TileEntityDispenser
/*     */   extends TileEntity implements IInventory {
/*   7 */   private ItemStack[] items = new ItemStack[9];
/*   8 */   private Random b = new Random();
/*     */ 
/*     */ 
/*     */   
/*  12 */   public ItemStack[] getContents() { return this.items; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  19 */   public int getSize() { return 9; }
/*     */ 
/*     */ 
/*     */   
/*  23 */   public ItemStack getItem(int i) { return this.items[i]; }
/*     */ 
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  27 */     if (this.items[i] != null) {
/*     */ 
/*     */       
/*  30 */       if ((this.items[i]).count <= j) {
/*  31 */         ItemStack itemstack = this.items[i];
/*  32 */         this.items[i] = null;
/*  33 */         update();
/*  34 */         return itemstack;
/*     */       } 
/*  36 */       ItemStack itemstack = this.items[i].a(j);
/*  37 */       if ((this.items[i]).count == 0) {
/*  38 */         this.items[i] = null;
/*     */       }
/*     */       
/*  41 */       update();
/*  42 */       return itemstack;
/*     */     } 
/*     */     
/*  45 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int findDispenseSlot() {
/*  51 */     int i = -1;
/*  52 */     int j = 1;
/*     */     
/*  54 */     for (int k = 0; k < this.items.length; k++) {
/*  55 */       if (this.items[k] != null && this.b.nextInt(j++) == 0 && 
/*  56 */         (this.items[k]).count != 0) {
/*  57 */         i = k;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  62 */     return i;
/*     */   }
/*     */   
/*     */   public ItemStack b() {
/*  66 */     int i = findDispenseSlot();
/*     */ 
/*     */     
/*  69 */     if (i >= 0) {
/*  70 */       return splitStack(i, 1);
/*     */     }
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/*  77 */     this.items[i] = itemstack;
/*  78 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/*  79 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/*  82 */     update();
/*     */   }
/*     */ 
/*     */   
/*  86 */   public String getName() { return "Trap"; }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  90 */     super.a(nbttagcompound);
/*  91 */     NBTTagList nbttaglist = nbttagcompound.l("Items");
/*     */     
/*  93 */     this.items = new ItemStack[getSize()];
/*     */     
/*  95 */     for (int i = 0; i < nbttaglist.c(); i++) {
/*  96 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.a(i);
/*  97 */       int j = nbttagcompound1.c("Slot") & 0xFF;
/*     */       
/*  99 */       if (j >= 0 && j < this.items.length) {
/* 100 */         this.items[j] = new ItemStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 106 */     super.b(nbttagcompound);
/* 107 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 109 */     for (int i = 0; i < this.items.length; i++) {
/* 110 */       if (this.items[i] != null) {
/* 111 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 113 */         nbttagcompound1.a("Slot", (byte)i);
/* 114 */         this.items[i].a(nbttagcompound1);
/* 115 */         nbttaglist.a(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     nbttagcompound.a("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   
/* 123 */   public int getMaxStackSize() { return 64; }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public boolean a_(EntityHuman entityhuman) { return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D)); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntityDispenser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */