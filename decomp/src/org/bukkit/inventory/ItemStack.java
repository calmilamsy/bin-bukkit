/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStack
/*     */ {
/*     */   private int type;
/*     */   private int amount;
/*     */   private MaterialData data;
/*     */   private short durability;
/*     */   
/*  16 */   public ItemStack(int type) { this(type, 0); }
/*     */ 
/*     */ 
/*     */   
/*  20 */   public ItemStack(Material type) { this(type, 0); }
/*     */ 
/*     */ 
/*     */   
/*  24 */   public ItemStack(int type, int amount) { this(type, amount, (short)0); }
/*     */ 
/*     */ 
/*     */   
/*  28 */   public ItemStack(Material type, int amount) { this(type.getId(), amount); }
/*     */ 
/*     */ 
/*     */   
/*  32 */   public ItemStack(int type, int amount, short damage) { this(type, amount, damage, null); }
/*     */ 
/*     */ 
/*     */   
/*  36 */   public ItemStack(Material type, int amount, short damage) { this(type.getId(), amount, damage); } public ItemStack(int type, int amount, short damage, Byte data) {
/*     */     this.amount = 0;
/*     */     this.data = null;
/*     */     this.durability = 0;
/*  40 */     this.type = type;
/*  41 */     this.amount = amount;
/*  42 */     this.durability = damage;
/*  43 */     if (data != null) {
/*  44 */       createData(data.byteValue());
/*  45 */       this.durability = (short)data.byteValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  50 */   public ItemStack(Material type, int amount, short damage, Byte data) { this(type.getId(), amount, damage, data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public Material getType() { return Material.getMaterial(this.type); }
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
/*  70 */   public void setType(Material type) { setTypeId(type.getId()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public int getTypeId() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTypeId(int type) {
/*  90 */     this.type = type;
/*  91 */     createData((byte)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public int getAmount() { return this.amount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public void setAmount(int amount) { this.amount = amount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialData getData() {
/* 118 */     if (Material.getMaterial(getTypeId()).getData() != null) {
/* 119 */       this.data = Material.getMaterial(getTypeId()).getNewData((byte)this.durability);
/*     */     }
/*     */     
/* 122 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(MaterialData data) {
/* 131 */     Material mat = getType();
/*     */     
/* 133 */     if (mat == null || mat.getData() == null) {
/* 134 */       this.data = data;
/*     */     }
/* 136 */     else if (data.getClass() == mat.getData() || data.getClass() == MaterialData.class) {
/* 137 */       this.data = data;
/*     */     } else {
/* 139 */       throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setDurability(short durability) { this.durability = durability; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public short getDurability() { return this.durability; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public int getMaxStackSize() { return -1; }
/*     */ 
/*     */   
/*     */   private void createData(byte data) {
/* 173 */     Material mat = Material.getMaterial(this.type);
/*     */     
/* 175 */     if (mat == null) {
/* 176 */       this.data = new MaterialData(this.type, data);
/*     */     } else {
/* 178 */       this.data = mat.getNewData(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 184 */   public String toString() { return "ItemStack{" + getType().name() + " x " + getAmount() + "}"; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 189 */     if (!(obj instanceof ItemStack)) {
/* 190 */       return false;
/*     */     }
/*     */     
/* 193 */     ItemStack item = (ItemStack)obj;
/*     */     
/* 195 */     return (item.getAmount() == getAmount() && item.getTypeId() == getTypeId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 200 */   public ItemStack clone() { return new ItemStack(this.type, this.amount, this.durability); }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 205 */     hash = 11;
/*     */     
/* 207 */     hash = hash * 19 + 7 * getTypeId();
/* 208 */     return hash * 7 + 23 * getAmount();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\inventory\ItemStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */