/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemDye
/*     */   extends Item
/*     */ {
/*     */   public static final String[] a = { 
/*  11 */       "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };
/*     */ 
/*     */   
/*     */   public static final int[] bk = { 
/*  15 */       1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
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
/*     */   public ItemDye(int paramInt) {
/*  37 */     super(paramInt);
/*     */     
/*  39 */     a(true);
/*  40 */     d(0);
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
/*     */   public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  57 */     if (paramItemStack.getData() == 15) {
/*     */ 
/*     */       
/*  60 */       int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
/*  61 */       if (i == Block.SAPLING.id) {
/*  62 */         if (!paramWorld.isStatic) {
/*  63 */           ((BlockSapling)Block.SAPLING).b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.random);
/*  64 */           paramItemStack.count--;
/*     */         } 
/*  66 */         return true;
/*  67 */       }  if (i == Block.CROPS.id) {
/*  68 */         if (!paramWorld.isStatic) {
/*  69 */           ((BlockCrops)Block.CROPS).d_(paramWorld, paramInt1, paramInt2, paramInt3);
/*  70 */           paramItemStack.count--;
/*     */         } 
/*  72 */         return true;
/*  73 */       }  if (i == Block.GRASS.id) {
/*  74 */         if (!paramWorld.isStatic) {
/*  75 */           paramItemStack.count--; byte b;
/*  76 */           label38: for (b = 0; b < ''; b++) {
/*  77 */             int j = paramInt1;
/*  78 */             int k = paramInt2 + 1;
/*  79 */             int m = paramInt3;
/*  80 */             for (byte b1 = 0; b1 < b / 16; ) {
/*  81 */               j += b.nextInt(3) - 1;
/*  82 */               k += (b.nextInt(3) - 1) * b.nextInt(3) / 2;
/*  83 */               m += b.nextInt(3) - 1;
/*  84 */               if (paramWorld.getTypeId(j, k - 1, m) == Block.GRASS.id) { if (paramWorld.e(j, k, m))
/*     */                   continue label38;  b1++; }
/*     */               
/*     */               continue label38;
/*     */             } 
/*  89 */             if (paramWorld.getTypeId(j, k, m) == 0) {
/*  90 */               if (b.nextInt(10) != 0) {
/*  91 */                 paramWorld.setTypeIdAndData(j, k, m, Block.LONG_GRASS.id, 1);
/*  92 */               } else if (b.nextInt(3) != 0) {
/*  93 */                 paramWorld.setTypeId(j, k, m, Block.YELLOW_FLOWER.id);
/*     */               } else {
/*  95 */                 paramWorld.setTypeId(j, k, m, Block.RED_ROSE.id);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/* 100 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(ItemStack paramItemStack, EntityLiving paramEntityLiving) {
/* 109 */     if (paramEntityLiving instanceof EntitySheep) {
/* 110 */       EntitySheep entitySheep = (EntitySheep)paramEntityLiving;
/*     */       
/* 112 */       int i = BlockCloth.c(paramItemStack.getData());
/* 113 */       if (!entitySheep.isSheared() && entitySheep.getColor() != i) {
/* 114 */         entitySheep.setColor(i);
/* 115 */         paramItemStack.count--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemDye.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */