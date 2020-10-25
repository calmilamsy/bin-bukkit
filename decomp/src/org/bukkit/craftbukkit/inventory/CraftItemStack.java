/*     */ package org.bukkit.craftbukkit.inventory;
/*     */ 
/*     */ import net.minecraft.server.ItemStack;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CraftItemStack
/*     */   extends ItemStack {
/*     */   public CraftItemStack(ItemStack item) {
/*  10 */     super((item != null) ? item.id : 0, (item != null) ? item.count : 0, (short)((item != null) ? item.damage : 0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  15 */     this.item = item;
/*     */   }
/*     */   
/*     */   protected ItemStack item;
/*     */   
/*  20 */   public CraftItemStack(int type) { this(type, 0); }
/*     */ 
/*     */ 
/*     */   
/*  24 */   public CraftItemStack(Material type) { this(type, 0); }
/*     */ 
/*     */ 
/*     */   
/*  28 */   public CraftItemStack(int type, int amount) { this(type, amount, (short)0); }
/*     */ 
/*     */ 
/*     */   
/*  32 */   public CraftItemStack(Material type, int amount) { this(type.getId(), amount); }
/*     */ 
/*     */ 
/*     */   
/*  36 */   public CraftItemStack(int type, int amount, short damage) { this(type, amount, damage, null); }
/*     */ 
/*     */ 
/*     */   
/*  40 */   public CraftItemStack(Material type, int amount, short damage) { this(type.getId(), amount, damage); }
/*     */ 
/*     */ 
/*     */   
/*  44 */   public CraftItemStack(Material type, int amount, short damage, Byte data) { this(type.getId(), amount, damage, data); }
/*     */ 
/*     */ 
/*     */   
/*  48 */   public CraftItemStack(int type, int amount, short damage, Byte data) { this(new ItemStack(type, amount, (data != null) ? data.byteValue() : damage)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getType() {
/*  58 */     super.setTypeId((this.item != null) ? this.item.id : 0);
/*  59 */     return super.getType();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTypeId() {
/*  64 */     super.setTypeId((this.item != null) ? this.item.id : 0);
/*  65 */     return (this.item != null) ? this.item.id : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTypeId(int type) {
/*  70 */     if (type == 0) {
/*  71 */       super.setTypeId(0);
/*  72 */       super.setAmount(0);
/*  73 */       this.item = null;
/*     */     }
/*  75 */     else if (this.item == null) {
/*  76 */       this.item = new ItemStack(type, true, false);
/*  77 */       super.setAmount(1);
/*     */     } else {
/*  79 */       this.item.id = type;
/*  80 */       super.setTypeId(this.item.id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmount() {
/*  87 */     super.setAmount((this.item != null) ? this.item.count : 0);
/*  88 */     return (this.item != null) ? this.item.count : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAmount(int amount) {
/*  93 */     if (amount == 0) {
/*  94 */       super.setTypeId(0);
/*  95 */       super.setAmount(0);
/*  96 */       this.item = null;
/*     */     } else {
/*  98 */       super.setAmount(amount);
/*  99 */       this.item.count = amount;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDurability(short durability) {
/* 106 */     if (this.item != null) {
/* 107 */       super.setDurability(durability);
/* 108 */       this.item.damage = durability;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public short getDurability() {
/* 114 */     if (this.item != null) {
/* 115 */       super.setDurability((short)this.item.damage);
/* 116 */       return (short)this.item.damage;
/*     */     } 
/* 118 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public int getMaxStackSize() { return this.item.getItem().getMaxStackSize(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\inventory\CraftItemStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */