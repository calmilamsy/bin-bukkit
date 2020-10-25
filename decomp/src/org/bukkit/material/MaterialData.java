/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MaterialData
/*     */ {
/*     */   private final int type;
/*     */   private byte data;
/*     */   
/*  14 */   public MaterialData(int type) { this(type, (byte)0); }
/*     */ 
/*     */ 
/*     */   
/*  18 */   public MaterialData(Material type) { this(type, (byte)0); }
/*     */   
/*     */   public MaterialData(int type, byte data) {
/*     */     this.data = 0;
/*  22 */     this.type = type;
/*  23 */     this.data = data;
/*     */   }
/*     */ 
/*     */   
/*  27 */   public MaterialData(Material type, byte data) { this(type.getId(), data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public byte getData() { return this.data; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public void setData(byte data) { this.data = data; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public Material getItemType() { return Material.getMaterial(this.type); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public int getItemTypeId() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public ItemStack toItemStack() { return new ItemStack(this.type, false, (short)this.data); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public ItemStack toItemStack(int amount) { return new ItemStack(this.type, amount, (short)this.data); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public String toString() { return getItemType() + "(" + getData() + ")"; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public int hashCode() { return getItemTypeId() << 8 ^ getData(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  96 */     if (obj != null && obj instanceof MaterialData) {
/*  97 */       MaterialData md = (MaterialData)obj;
/*     */       
/*  99 */       return (md.getItemTypeId() == getItemTypeId() && md.getData() == getData());
/*     */     } 
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\material\MaterialData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */