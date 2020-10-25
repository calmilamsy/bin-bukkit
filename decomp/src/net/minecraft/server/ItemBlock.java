/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.craftbukkit.block.CraftBlockState;
/*     */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ 
/*     */ 
/*     */ public class ItemBlock
/*     */   extends Item
/*     */ {
/*     */   private int id;
/*     */   
/*     */   public ItemBlock(int i) {
/*  14 */     super(i);
/*  15 */     this.id = i + 256;
/*  16 */     b(Block.byId[i + 256].a(2));
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/*  20 */     int clickedX = i, clickedY = j, clickedZ = k;
/*     */     
/*  22 */     if (world.getTypeId(i, j, k) == Block.SNOW.id) {
/*  23 */       l = 0;
/*     */     } else {
/*  25 */       if (l == 0) {
/*  26 */         j--;
/*     */       }
/*     */       
/*  29 */       if (l == 1) {
/*  30 */         j++;
/*     */       }
/*     */       
/*  33 */       if (l == 2) {
/*  34 */         k--;
/*     */       }
/*     */       
/*  37 */       if (l == 3) {
/*  38 */         k++;
/*     */       }
/*     */       
/*  41 */       if (l == 4) {
/*  42 */         i--;
/*     */       }
/*     */       
/*  45 */       if (l == 5) {
/*  46 */         i++;
/*     */       }
/*     */     } 
/*     */     
/*  50 */     if (itemstack.count == 0)
/*  51 */       return false; 
/*  52 */     if (j == 127 && (Block.byId[this.id]).material.isBuildable())
/*  53 */       return false; 
/*  54 */     if (world.a(this.id, i, j, k, false, l)) {
/*  55 */       Block block = Block.byId[this.id];
/*     */ 
/*     */       
/*  58 */       CraftBlockState replacedBlockState = CraftBlockState.getBlockState(world, i, j, k);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  64 */       CraftBlockState blockStateBelow = null;
/*     */       
/*  66 */       boolean eventUseBlockBelow = false;
/*  67 */       if ((world.getTypeId(i, j - 1, k) == Block.STEP.id || world.getTypeId(i, j - 1, k) == Block.DOUBLE_STEP.id) && (itemstack.id == Block.DOUBLE_STEP.id || itemstack.id == Block.STEP.id)) {
/*     */         
/*  69 */         blockStateBelow = CraftBlockState.getBlockState(world, i, j - 1, k);
/*     */         
/*  71 */         eventUseBlockBelow = (itemstack.id == Block.STEP.id && blockStateBelow.getTypeId() == Block.STEP.id);
/*     */       } 
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
/*  84 */       if (world.setRawTypeIdAndData(i, j, k, this.id, filterData(itemstack.getData()))) {
/*  85 */         BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, eventUseBlockBelow ? blockStateBelow : replacedBlockState, clickedX, clickedY, clickedZ, block);
/*     */         
/*  87 */         if (event.isCancelled() || !event.canBuild()) {
/*  88 */           if (blockStateBelow != null) {
/*  89 */             world.setTypeIdAndData(i, j, k, replacedBlockState.getTypeId(), replacedBlockState.getRawData());
/*  90 */             world.setTypeIdAndData(i, j - 1, k, blockStateBelow.getTypeId(), blockStateBelow.getRawData());
/*     */           }
/*     */           else {
/*     */             
/*  94 */             if (this.id == Block.ICE.id)
/*     */             {
/*  96 */               world.setTypeId(i, j, k, 20);
/*     */             }
/*     */             
/*  99 */             world.setTypeIdAndData(i, j, k, replacedBlockState.getTypeId(), replacedBlockState.getRawData());
/*     */           } 
/* 101 */           return true;
/*     */         } 
/*     */         
/* 104 */         world.update(i, j, k, this.id);
/*     */ 
/*     */ 
/*     */         
/* 108 */         Block.byId[this.id].postPlace(world, i, j, k, l);
/* 109 */         Block.byId[this.id].postPlace(world, i, j, k, entityhuman);
/* 110 */         world.makeSound((i + 0.5F), (j + 0.5F), (k + 0.5F), block.stepSound.getName(), (block.stepSound.getVolume1() + 1.0F) / 2.0F, block.stepSound.getVolume2() * 0.8F);
/* 111 */         itemstack.count--;
/*     */       } 
/*     */       
/* 114 */       return true;
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 121 */   public String a() { return Block.byId[this.id].l(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */