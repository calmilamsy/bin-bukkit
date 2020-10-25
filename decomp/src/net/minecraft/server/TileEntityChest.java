/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class TileEntityChest
/*    */   extends TileEntity implements IInventory {
/*  5 */   private ItemStack[] items = new ItemStack[27];
/*    */ 
/*    */ 
/*    */   
/*  9 */   public ItemStack[] getContents() { return this.items; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   public int getSize() { return 27; }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public ItemStack getItem(int i) { return this.items[i]; }
/*    */ 
/*    */   
/*    */   public ItemStack splitStack(int i, int j) {
/* 24 */     if (this.items[i] != null) {
/*    */ 
/*    */       
/* 27 */       if ((this.items[i]).count <= j) {
/* 28 */         ItemStack itemstack = this.items[i];
/* 29 */         this.items[i] = null;
/* 30 */         update();
/* 31 */         return itemstack;
/*    */       } 
/* 33 */       ItemStack itemstack = this.items[i].a(j);
/* 34 */       if ((this.items[i]).count == 0) {
/* 35 */         this.items[i] = null;
/*    */       }
/*    */       
/* 38 */       update();
/* 39 */       return itemstack;
/*    */     } 
/*    */     
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setItem(int i, ItemStack itemstack) {
/* 47 */     this.items[i] = itemstack;
/* 48 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/* 49 */       itemstack.count = getMaxStackSize();
/*    */     }
/*    */     
/* 52 */     update();
/*    */   }
/*    */ 
/*    */   
/* 56 */   public String getName() { return "Chest"; }
/*    */ 
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 60 */     super.a(nbttagcompound);
/* 61 */     NBTTagList nbttaglist = nbttagcompound.l("Items");
/*    */     
/* 63 */     this.items = new ItemStack[getSize()];
/*    */     
/* 65 */     for (int i = 0; i < nbttaglist.c(); i++) {
/* 66 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.a(i);
/* 67 */       int j = nbttagcompound1.c("Slot") & 0xFF;
/*    */       
/* 69 */       if (j >= 0 && j < this.items.length) {
/* 70 */         this.items[j] = new ItemStack(nbttagcompound1);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 76 */     super.b(nbttagcompound);
/* 77 */     NBTTagList nbttaglist = new NBTTagList();
/*    */     
/* 79 */     for (int i = 0; i < this.items.length; i++) {
/* 80 */       if (this.items[i] != null) {
/* 81 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*    */         
/* 83 */         nbttagcompound1.a("Slot", (byte)i);
/* 84 */         this.items[i].a(nbttagcompound1);
/* 85 */         nbttaglist.a(nbttagcompound1);
/*    */       } 
/*    */     } 
/*    */     
/* 89 */     nbttagcompound.a("Items", nbttaglist);
/*    */   }
/*    */ 
/*    */   
/* 93 */   public int getMaxStackSize() { return 64; }
/*    */ 
/*    */ 
/*    */   
/* 97 */   public boolean a_(EntityHuman entityhuman) { return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntityChest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */