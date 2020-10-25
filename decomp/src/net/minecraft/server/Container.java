/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Container
/*     */ {
/*  17 */   public List d = new ArrayList();
/*  18 */   public List e = new ArrayList();
/*  19 */   public int windowId = 0;
/*  20 */   private short a = 0;
/*     */   
/*  22 */   protected List listeners = new ArrayList();
/*     */   
/*     */   protected void a(Slot paramSlot) {
/*  25 */     paramSlot.a = this.e.size();
/*  26 */     this.e.add(paramSlot);
/*  27 */     this.d.add(null);
/*     */   }
/*     */   
/*     */   public void a(ICrafting paramICrafting) {
/*  31 */     if (this.listeners.contains(paramICrafting)) {
/*  32 */       throw new IllegalArgumentException("Listener already listening");
/*     */     }
/*  34 */     this.listeners.add(paramICrafting);
/*     */ 
/*     */     
/*  37 */     paramICrafting.a(this, b());
/*  38 */     a();
/*     */   }
/*     */   
/*     */   public List b() {
/*  42 */     ArrayList arrayList = new ArrayList();
/*  43 */     for (byte b1 = 0; b1 < this.e.size(); b1++) {
/*  44 */       arrayList.add(((Slot)this.e.get(b1)).getItem());
/*     */     }
/*  46 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {
/*  56 */     for (byte b1 = 0; b1 < this.e.size(); b1++) {
/*  57 */       ItemStack itemStack1 = ((Slot)this.e.get(b1)).getItem();
/*  58 */       ItemStack itemStack2 = (ItemStack)this.d.get(b1);
/*  59 */       if (!ItemStack.equals(itemStack2, itemStack1)) {
/*  60 */         itemStack2 = (itemStack1 == null) ? null : itemStack1.cloneItemStack();
/*  61 */         this.d.set(b1, itemStack2);
/*  62 */         for (byte b2 = 0; b2 < this.listeners.size(); b2++) {
/*  63 */           ((ICrafting)this.listeners.get(b2)).a(this, b1, itemStack2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Slot a(IInventory paramIInventory, int paramInt) {
/*  70 */     for (byte b1 = 0; b1 < this.e.size(); b1++) {
/*  71 */       Slot slot = (Slot)this.e.get(b1);
/*  72 */       if (slot.a(paramIInventory, paramInt)) {
/*  73 */         return slot;
/*     */       }
/*     */     } 
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */   
/*  80 */   public Slot b(int paramInt) { return (Slot)this.e.get(paramInt); }
/*     */ 
/*     */   
/*     */   public ItemStack a(int paramInt) {
/*  84 */     Slot slot = (Slot)this.e.get(paramInt);
/*  85 */     if (slot != null) {
/*  86 */       return slot.getItem();
/*     */     }
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack a(int paramInt1, int paramInt2, boolean paramBoolean, EntityHuman paramEntityHuman) {
/*  92 */     ItemStack itemStack = null;
/*  93 */     if (paramInt2 == 0 || paramInt2 == 1) {
/*  94 */       InventoryPlayer inventoryPlayer = paramEntityHuman.inventory;
/*  95 */       if (paramInt1 == -999) {
/*  96 */         if (inventoryPlayer.j() != null && 
/*  97 */           paramInt1 == -999) {
/*  98 */           if (paramInt2 == 0) {
/*  99 */             paramEntityHuman.b(inventoryPlayer.j());
/* 100 */             inventoryPlayer.b(null);
/*     */           } 
/* 102 */           if (paramInt2 == 1) {
/* 103 */             paramEntityHuman.b(inventoryPlayer.j().a(1));
/* 104 */             if ((inventoryPlayer.j()).count == 0) inventoryPlayer.b(null);
/*     */           
/*     */           }
/*     */         
/*     */         } 
/* 109 */       } else if (paramBoolean) {
/* 110 */         ItemStack itemStack1 = a(paramInt1);
/* 111 */         if (itemStack1 != null) {
/* 112 */           int i = itemStack1.count;
/* 113 */           itemStack = itemStack1.cloneItemStack();
/*     */           
/* 115 */           Slot slot = (Slot)this.e.get(paramInt1);
/* 116 */           if (slot != null && 
/* 117 */             slot.getItem() != null) {
/* 118 */             int j = (slot.getItem()).count;
/* 119 */             if (j < i) a(paramInt1, paramInt2, paramBoolean, paramEntityHuman);
/*     */           
/*     */           } 
/*     */         } 
/*     */       } else {
/* 124 */         Slot slot = (Slot)this.e.get(paramInt1);
/* 125 */         if (slot != null) {
/* 126 */           slot.c();
/* 127 */           ItemStack itemStack1 = slot.getItem();
/* 128 */           ItemStack itemStack2 = inventoryPlayer.j();
/*     */           
/* 130 */           if (itemStack1 != null) {
/* 131 */             itemStack = itemStack1.cloneItemStack();
/*     */           }
/*     */           
/* 134 */           if (itemStack1 == null) {
/* 135 */             if (itemStack2 != null && slot.isAllowed(itemStack2)) {
/* 136 */               int i = (paramInt2 == 0) ? itemStack2.count : 1;
/* 137 */               if (i > slot.d()) {
/* 138 */                 i = slot.d();
/*     */               }
/* 140 */               slot.c(itemStack2.a(i));
/* 141 */               if (itemStack2.count == 0) {
/* 142 */                 inventoryPlayer.b(null);
/*     */               }
/*     */             }
/*     */           
/* 146 */           } else if (itemStack2 == null) {
/*     */             
/* 148 */             int i = (paramInt2 == 0) ? itemStack1.count : ((itemStack1.count + 1) / 2);
/* 149 */             ItemStack itemStack3 = slot.a(i);
/*     */             
/* 151 */             inventoryPlayer.b(itemStack3);
/* 152 */             if (itemStack1.count == 0) {
/* 153 */               slot.c(null);
/*     */             }
/* 155 */             slot.a(inventoryPlayer.j());
/* 156 */           } else if (slot.isAllowed(itemStack2)) {
/*     */             
/* 158 */             if (itemStack1.id != itemStack2.id || (itemStack1.usesData() && itemStack1.getData() != itemStack2.getData())) {
/*     */               
/* 160 */               if (itemStack2.count <= slot.d()) {
/* 161 */                 ItemStack itemStack3 = itemStack1;
/* 162 */                 slot.c(itemStack2);
/* 163 */                 inventoryPlayer.b(itemStack3);
/*     */               } 
/*     */             } else {
/*     */               
/* 167 */               int i = (paramInt2 == 0) ? itemStack2.count : 1;
/* 168 */               if (i > slot.d() - itemStack1.count) {
/* 169 */                 i = slot.d() - itemStack1.count;
/*     */               }
/* 171 */               if (i > itemStack2.getMaxStackSize() - itemStack1.count) {
/* 172 */                 i = itemStack2.getMaxStackSize() - itemStack1.count;
/*     */               }
/* 174 */               itemStack2.a(i);
/* 175 */               if (itemStack2.count == 0) {
/* 176 */                 inventoryPlayer.b(null);
/*     */               }
/* 178 */               itemStack1.count += i;
/*     */             }
/*     */           
/*     */           }
/* 182 */           else if (itemStack1.id == itemStack2.id && itemStack2.getMaxStackSize() > 1 && (!itemStack1.usesData() || itemStack1.getData() == itemStack2.getData())) {
/* 183 */             int i = itemStack1.count;
/* 184 */             if (i > 0 && i + itemStack2.count <= itemStack2.getMaxStackSize()) {
/* 185 */               itemStack2.count += i;
/* 186 */               itemStack1.a(i);
/* 187 */               if (itemStack1.count == 0) slot.c(null); 
/* 188 */               slot.a(inventoryPlayer.j());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     return itemStack;
/*     */   }
/*     */   
/*     */   public void a(EntityHuman paramEntityHuman) {
/* 203 */     InventoryPlayer inventoryPlayer = paramEntityHuman.inventory;
/* 204 */     if (inventoryPlayer.j() != null) {
/* 205 */       paramEntityHuman.b(inventoryPlayer.j());
/* 206 */       inventoryPlayer.b(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 211 */   public void a(IInventory paramIInventory) { a(); }
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
/* 248 */   private Set b = new HashSet();
/*     */ 
/*     */   
/* 251 */   public boolean c(EntityHuman paramEntityHuman) { return !this.b.contains(paramEntityHuman); }
/*     */ 
/*     */   
/*     */   public void a(EntityHuman paramEntityHuman, boolean paramBoolean) {
/* 255 */     if (paramBoolean) { this.b.remove(paramEntityHuman); }
/* 256 */     else { this.b.add(paramEntityHuman); }
/*     */   
/*     */   }
/*     */   
/*     */   public abstract boolean b(EntityHuman paramEntityHuman);
/*     */   
/*     */   protected void a(ItemStack paramItemStack, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 263 */     int i = paramInt1;
/* 264 */     if (paramBoolean) {
/* 265 */       i = paramInt2 - 1;
/*     */     }
/*     */ 
/*     */     
/* 269 */     if (paramItemStack.isStackable()) {
/* 270 */       while (paramItemStack.count > 0 && ((!paramBoolean && i < paramInt2) || (paramBoolean && i >= paramInt1))) {
/*     */         
/* 272 */         Slot slot = (Slot)this.e.get(i);
/* 273 */         ItemStack itemStack = slot.getItem();
/* 274 */         if (itemStack != null && itemStack.id == paramItemStack.id && (!paramItemStack.usesData() || paramItemStack.getData() == itemStack.getData())) {
/* 275 */           int j = itemStack.count + paramItemStack.count;
/* 276 */           if (j <= paramItemStack.getMaxStackSize()) {
/* 277 */             paramItemStack.count = 0;
/* 278 */             itemStack.count = j;
/* 279 */             slot.c();
/* 280 */           } else if (itemStack.count < paramItemStack.getMaxStackSize()) {
/* 281 */             paramItemStack.count -= paramItemStack.getMaxStackSize() - itemStack.count;
/* 282 */             itemStack.count = paramItemStack.getMaxStackSize();
/* 283 */             slot.c();
/*     */           } 
/*     */         } 
/*     */         
/* 287 */         if (paramBoolean) {
/* 288 */           i--; continue;
/*     */         } 
/* 290 */         i++;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 296 */     if (paramItemStack.count > 0) {
/* 297 */       if (paramBoolean) {
/* 298 */         i = paramInt2 - 1;
/*     */       } else {
/* 300 */         i = paramInt1;
/*     */       } 
/* 302 */       while ((!paramBoolean && i < paramInt2) || (paramBoolean && i >= paramInt1)) {
/* 303 */         Slot slot = (Slot)this.e.get(i);
/* 304 */         ItemStack itemStack = slot.getItem();
/*     */         
/* 306 */         if (itemStack == null) {
/* 307 */           slot.c(paramItemStack.cloneItemStack());
/* 308 */           slot.c();
/* 309 */           paramItemStack.count = 0;
/*     */           
/*     */           break;
/*     */         } 
/* 313 */         if (paramBoolean) {
/* 314 */           i--; continue;
/*     */         } 
/* 316 */         i++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Container.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */