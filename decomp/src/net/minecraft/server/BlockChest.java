/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class BlockChest
/*     */   extends BlockContainer
/*     */ {
/*  17 */   private Random a = new Random();
/*     */   
/*     */   protected BlockChest(int paramInt) {
/*  20 */     super(paramInt, Material.WOOD);
/*  21 */     this.textureId = 26;
/*     */   }
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
/*     */   public int a(int paramInt) {
/*  77 */     if (paramInt == 1) return this.textureId - 1; 
/*  78 */     if (paramInt == 0) return this.textureId - 1; 
/*  79 */     if (paramInt == 3) return this.textureId + 1; 
/*  80 */     return this.textureId;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  84 */     byte b = 0;
/*     */     
/*  86 */     if (paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3) == this.id) b++; 
/*  87 */     if (paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3) == this.id) b++; 
/*  88 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1) == this.id) b++; 
/*  89 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1) == this.id) b++;
/*     */     
/*  91 */     if (b > 1) return false;
/*     */     
/*  93 */     if (g(paramWorld, paramInt1 - 1, paramInt2, paramInt3)) return false; 
/*  94 */     if (g(paramWorld, paramInt1 + 1, paramInt2, paramInt3)) return false; 
/*  95 */     if (g(paramWorld, paramInt1, paramInt2, paramInt3 - 1)) return false; 
/*  96 */     if (g(paramWorld, paramInt1, paramInt2, paramInt3 + 1)) return false; 
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   private boolean g(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 101 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) != this.id) return false; 
/* 102 */     if (paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3) == this.id) return true; 
/* 103 */     if (paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3) == this.id) return true; 
/* 104 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1) == this.id) return true; 
/* 105 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1) == this.id) return true; 
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 110 */     TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 111 */     for (byte b = 0; b < tileEntityChest.getSize(); b++) {
/* 112 */       ItemStack itemStack = tileEntityChest.getItem(b);
/* 113 */       if (itemStack != null) {
/* 114 */         float f1 = this.a.nextFloat() * 0.8F + 0.1F;
/* 115 */         float f2 = this.a.nextFloat() * 0.8F + 0.1F;
/* 116 */         float f3 = this.a.nextFloat() * 0.8F + 0.1F;
/*     */         
/* 118 */         while (itemStack.count > 0) {
/* 119 */           int i = this.a.nextInt(21) + 10;
/* 120 */           if (i > itemStack.count) i = itemStack.count; 
/* 121 */           itemStack.count -= i;
/*     */           
/* 123 */           EntityItem entityItem = new EntityItem(paramWorld, (paramInt1 + f1), (paramInt2 + f2), (paramInt3 + f3), new ItemStack(itemStack.id, i, itemStack.getData()));
/* 124 */           float f = 0.05F;
/* 125 */           entityItem.motX = ((float)this.a.nextGaussian() * f);
/* 126 */           entityItem.motY = ((float)this.a.nextGaussian() * f + 0.2F);
/* 127 */           entityItem.motZ = ((float)this.a.nextGaussian() * f);
/* 128 */           paramWorld.addEntity(entityItem);
/*     */         } 
/*     */       } 
/*     */     } 
/* 132 */     super.remove(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 136 */     InventoryLargeChest inventoryLargeChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*     */     
/* 138 */     if (paramWorld.e(paramInt1, paramInt2 + 1, paramInt3)) return true;
/*     */     
/* 140 */     if (paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3) == this.id && paramWorld.e(paramInt1 - 1, paramInt2 + 1, paramInt3)) return true; 
/* 141 */     if (paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3) == this.id && paramWorld.e(paramInt1 + 1, paramInt2 + 1, paramInt3)) return true; 
/* 142 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1) == this.id && paramWorld.e(paramInt1, paramInt2 + 1, paramInt3 - 1)) return true; 
/* 143 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1) == this.id && paramWorld.e(paramInt1, paramInt2 + 1, paramInt3 + 1)) return true;
/*     */     
/* 145 */     if (paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3) == this.id) inventoryLargeChest = new InventoryLargeChest("Large chest", (TileEntityChest)paramWorld.getTileEntity(paramInt1 - 1, paramInt2, paramInt3), inventoryLargeChest); 
/* 146 */     if (paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3) == this.id) inventoryLargeChest = new InventoryLargeChest("Large chest", inventoryLargeChest, (TileEntityChest)paramWorld.getTileEntity(paramInt1 + 1, paramInt2, paramInt3)); 
/* 147 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1) == this.id) inventoryLargeChest = new InventoryLargeChest("Large chest", (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3 - 1), inventoryLargeChest); 
/* 148 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1) == this.id) inventoryLargeChest = new InventoryLargeChest("Large chest", inventoryLargeChest, (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3 + 1));
/*     */     
/* 150 */     if (paramWorld.isStatic) {
/* 151 */       return true;
/*     */     }
/*     */     
/* 154 */     paramEntityHuman.a(inventoryLargeChest);
/*     */     
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 160 */   protected TileEntity a_() { return new TileEntityChest(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockChest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */