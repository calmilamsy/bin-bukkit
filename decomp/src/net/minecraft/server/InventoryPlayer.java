/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ public class InventoryPlayer
/*     */   implements IInventory
/*     */ {
/*     */   public ItemStack[] items;
/*     */   public ItemStack[] armor;
/*     */   public int itemInHandIndex;
/*     */   public EntityHuman d;
/*     */   private ItemStack f;
/*     */   public boolean e;
/*     */   
/*  14 */   public ItemStack[] getContents() { return this.items; }
/*     */ 
/*     */ 
/*     */   
/*  18 */   public ItemStack[] getArmorContents() { return this.armor; } public InventoryPlayer(EntityHuman entityhuman) {
/*     */     this.items = new ItemStack[36];
/*     */     this.armor = new ItemStack[4];
/*     */     this.itemInHandIndex = 0;
/*     */     this.e = false;
/*  23 */     this.d = entityhuman;
/*     */   }
/*     */ 
/*     */   
/*  27 */   public ItemStack getItemInHand() { return (this.itemInHandIndex < 9 && this.itemInHandIndex >= 0) ? this.items[this.itemInHandIndex] : null; }
/*     */ 
/*     */ 
/*     */   
/*  31 */   public static int e() { return 9; }
/*     */ 
/*     */   
/*     */   private int d(int i) {
/*  35 */     for (int j = 0; j < this.items.length; j++) {
/*  36 */       if (this.items[j] != null && (this.items[j]).id == i) {
/*  37 */         return j;
/*     */       }
/*     */     } 
/*     */     
/*  41 */     return -1;
/*     */   }
/*     */   
/*     */   private int firstPartial(ItemStack itemstack) {
/*  45 */     for (int i = 0; i < this.items.length; i++) {
/*  46 */       if (this.items[i] != null && (this.items[i]).id == itemstack.id && this.items[i].isStackable() && (this.items[i]).count < this.items[i].getMaxStackSize() && (this.items[i]).count < getMaxStackSize() && (!this.items[i].usesData() || this.items[i].getData() == itemstack.getData())) {
/*  47 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  51 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int canHold(ItemStack itemstack) {
/*  56 */     int remains = itemstack.count;
/*  57 */     for (int i = 0; i < this.items.length; i++) {
/*  58 */       if (this.items[i] == null) return itemstack.count;
/*     */ 
/*     */       
/*  61 */       if (this.items[i] != null && (this.items[i]).id == itemstack.id && this.items[i].isStackable() && (this.items[i]).count < this.items[i].getMaxStackSize() && (this.items[i]).count < getMaxStackSize() && (!this.items[i].usesData() || this.items[i].getData() == itemstack.getData())) {
/*  62 */         remains -= ((this.items[i].getMaxStackSize() < getMaxStackSize()) ? this.items[i].getMaxStackSize() : getMaxStackSize()) - (this.items[i]).count;
/*     */       }
/*  64 */       if (remains <= 0) return itemstack.count; 
/*     */     } 
/*  66 */     return itemstack.count - remains;
/*     */   }
/*     */ 
/*     */   
/*     */   private int k() {
/*  71 */     for (int i = 0; i < this.items.length; i++) {
/*  72 */       if (this.items[i] == null) {
/*  73 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  77 */     return -1;
/*     */   }
/*     */   
/*     */   private int e(ItemStack itemstack) {
/*  81 */     int i = itemstack.id;
/*  82 */     int j = itemstack.count;
/*  83 */     int k = firstPartial(itemstack);
/*     */     
/*  85 */     if (k < 0) {
/*  86 */       k = k();
/*     */     }
/*     */     
/*  89 */     if (k < 0) {
/*  90 */       return j;
/*     */     }
/*  92 */     if (this.items[k] == null) {
/*  93 */       this.items[k] = new ItemStack(i, false, itemstack.getData());
/*     */     }
/*     */     
/*  96 */     int l = j;
/*     */     
/*  98 */     if (j > this.items[k].getMaxStackSize() - (this.items[k]).count) {
/*  99 */       l = this.items[k].getMaxStackSize() - (this.items[k]).count;
/*     */     }
/*     */     
/* 102 */     if (l > getMaxStackSize() - (this.items[k]).count) {
/* 103 */       l = getMaxStackSize() - (this.items[k]).count;
/*     */     }
/*     */     
/* 106 */     if (l == 0) {
/* 107 */       return j;
/*     */     }
/* 109 */     j -= l;
/* 110 */     (this.items[k]).count += l;
/* 111 */     (this.items[k]).b = 5;
/* 112 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void f() {
/* 118 */     for (int i = 0; i < this.items.length; i++) {
/* 119 */       if (this.items[i] != null) {
/* 120 */         this.items[i].a(this.d.world, this.d, i, (this.itemInHandIndex == i));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean b(int i) {
/* 126 */     int j = d(i);
/*     */     
/* 128 */     if (j < 0) {
/* 129 */       return false;
/*     */     }
/* 131 */     if (--(this.items[j]).count <= 0) {
/* 132 */       this.items[j] = null;
/*     */     }
/*     */     
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean pickup(ItemStack itemstack) {
/*     */     int i;
/* 142 */     if (itemstack.f()) {
/* 143 */       i = k();
/* 144 */       if (i >= 0) {
/* 145 */         this.items[i] = ItemStack.b(itemstack);
/* 146 */         (this.items[i]).b = 5;
/* 147 */         itemstack.count = 0;
/* 148 */         return true;
/*     */       } 
/* 150 */       return false;
/*     */     } 
/*     */     
/*     */     do {
/* 154 */       i = itemstack.count;
/* 155 */       itemstack.count = e(itemstack);
/* 156 */     } while (itemstack.count > 0 && itemstack.count < i);
/*     */     
/* 158 */     return (itemstack.count < i);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 163 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 165 */     if (i >= this.items.length) {
/* 166 */       aitemstack = this.armor;
/* 167 */       i -= this.items.length;
/*     */     } 
/*     */     
/* 170 */     if (aitemstack[i] != null) {
/*     */ 
/*     */       
/* 173 */       if ((aitemstack[i]).count <= j) {
/* 174 */         ItemStack itemstack = aitemstack[i];
/* 175 */         aitemstack[i] = null;
/* 176 */         return itemstack;
/*     */       } 
/* 178 */       ItemStack itemstack = aitemstack[i].a(j);
/* 179 */       if ((aitemstack[i]).count == 0) {
/* 180 */         aitemstack[i] = null;
/*     */       }
/*     */       
/* 183 */       return itemstack;
/*     */     } 
/*     */     
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 191 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 193 */     if (i >= aitemstack.length) {
/* 194 */       i -= aitemstack.length;
/* 195 */       aitemstack = this.armor;
/*     */     } 
/*     */     
/* 198 */     aitemstack[i] = itemstack;
/*     */   }
/*     */   
/*     */   public float a(Block block) {
/* 202 */     float f = 1.0F;
/*     */     
/* 204 */     if (this.items[this.itemInHandIndex] != null) {
/* 205 */       f *= this.items[this.itemInHandIndex].a(block);
/*     */     }
/*     */     
/* 208 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList a(NBTTagList nbttaglist) {
/*     */     int i;
/* 215 */     for (i = 0; i < this.items.length; i++) {
/* 216 */       if (this.items[i] != null) {
/* 217 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 218 */         nbttagcompound.a("Slot", (byte)i);
/* 219 */         this.items[i].a(nbttagcompound);
/* 220 */         nbttaglist.a(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     for (i = 0; i < this.armor.length; i++) {
/* 225 */       if (this.armor[i] != null) {
/* 226 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 227 */         nbttagcompound.a("Slot", (byte)(i + 100));
/* 228 */         this.armor[i].a(nbttagcompound);
/* 229 */         nbttaglist.a(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 233 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public void b(NBTTagList nbttaglist) {
/* 237 */     this.items = new ItemStack[36];
/* 238 */     this.armor = new ItemStack[4];
/*     */     
/* 240 */     for (int i = 0; i < nbttaglist.c(); i++) {
/* 241 */       NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.a(i);
/* 242 */       int j = nbttagcompound.c("Slot") & 0xFF;
/* 243 */       ItemStack itemstack = new ItemStack(nbttagcompound);
/*     */       
/* 245 */       if (itemstack.getItem() != null) {
/* 246 */         if (j >= 0 && j < this.items.length) {
/* 247 */           this.items[j] = itemstack;
/*     */         }
/*     */         
/* 250 */         if (j >= 100 && j < this.armor.length + 100) {
/* 251 */           this.armor[j - 100] = itemstack;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 258 */   public int getSize() { return this.items.length + 4; }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int i) {
/* 262 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 264 */     if (i >= aitemstack.length) {
/* 265 */       i -= aitemstack.length;
/* 266 */       aitemstack = this.armor;
/*     */     } 
/*     */     
/* 269 */     return aitemstack[i];
/*     */   }
/*     */ 
/*     */   
/* 273 */   public String getName() { return "Inventory"; }
/*     */ 
/*     */ 
/*     */   
/* 277 */   public int getMaxStackSize() { return 64; }
/*     */ 
/*     */   
/*     */   public int a(Entity entity) {
/* 281 */     ItemStack itemstack = getItem(this.itemInHandIndex);
/*     */     
/* 283 */     return (itemstack != null) ? itemstack.a(entity) : 1;
/*     */   }
/*     */   
/*     */   public boolean b(Block block) {
/* 287 */     if (block.material.i()) {
/* 288 */       return true;
/*     */     }
/* 290 */     ItemStack itemstack = getItem(this.itemInHandIndex);
/*     */     
/* 292 */     return (itemstack != null) ? itemstack.b(block) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int g() {
/* 297 */     int i = 0;
/* 298 */     int j = 0;
/* 299 */     int k = 0;
/*     */     
/* 301 */     for (int l = 0; l < this.armor.length; l++) {
/* 302 */       if (this.armor[l] != null && this.armor[l].getItem() instanceof ItemArmor) {
/* 303 */         int i1 = this.armor[l].i();
/* 304 */         int j1 = this.armor[l].g();
/* 305 */         int k1 = i1 - j1;
/*     */         
/* 307 */         j += k1;
/* 308 */         k += i1;
/* 309 */         int l1 = ((ItemArmor)this.armor[l].getItem()).bl;
/*     */         
/* 311 */         i += l1;
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     if (k == 0) {
/* 316 */       return 0;
/*     */     }
/* 318 */     return (i - 1) * j / k + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void c(int i) {
/* 323 */     for (int j = 0; j < this.armor.length; j++) {
/* 324 */       if (this.armor[j] != null && this.armor[j].getItem() instanceof ItemArmor) {
/* 325 */         this.armor[j].damage(i, this.d);
/* 326 */         if ((this.armor[j]).count == 0) {
/* 327 */           this.armor[j].a(this.d);
/* 328 */           this.armor[j] = null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/*     */     int i;
/* 337 */     for (i = 0; i < this.items.length; i++) {
/* 338 */       if (this.items[i] != null) {
/* 339 */         this.d.a(this.items[i], true);
/* 340 */         this.items[i] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     for (i = 0; i < this.armor.length; i++) {
/* 345 */       if (this.armor[i] != null) {
/* 346 */         this.d.a(this.armor[i], true);
/* 347 */         this.armor[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 353 */   public void update() { this.e = true; }
/*     */ 
/*     */   
/*     */   public void b(ItemStack itemstack) {
/* 357 */     this.f = itemstack;
/* 358 */     this.d.a(itemstack);
/*     */   }
/*     */ 
/*     */   
/* 362 */   public ItemStack j() { return this.f; }
/*     */ 
/*     */ 
/*     */   
/* 366 */   public boolean a_(EntityHuman entityhuman) { return this.d.dead ? false : ((entityhuman.g(this.d) <= 64.0D)); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(ItemStack itemstack) {
/*     */     int i;
/* 372 */     for (i = 0; i < this.armor.length; i++) {
/* 373 */       if (this.armor[i] != null && this.armor[i].c(itemstack)) {
/* 374 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 378 */     for (i = 0; i < this.items.length; i++) {
/* 379 */       if (this.items[i] != null && this.items[i].c(itemstack)) {
/* 380 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 384 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\InventoryPlayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */