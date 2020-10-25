/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ public final class ItemStack
/*     */ {
/*     */   public int count;
/*     */   public int b;
/*     */   public int id;
/*     */   public int damage;
/*     */   
/*  11 */   public ItemStack(Block block) { this(block, 1); }
/*     */ 
/*     */ 
/*     */   
/*  15 */   public ItemStack(Block block, int i) { this(block.id, i, 0); }
/*     */ 
/*     */ 
/*     */   
/*  19 */   public ItemStack(Block block, int i, int j) { this(block.id, i, j); }
/*     */ 
/*     */ 
/*     */   
/*  23 */   public ItemStack(Item item) { this(item.id, 1, 0); }
/*     */ 
/*     */ 
/*     */   
/*  27 */   public ItemStack(Item item, int i) { this(item.id, i, 0); }
/*     */ 
/*     */ 
/*     */   
/*  31 */   public ItemStack(Item item, int i, int j) { this(item.id, i, j); }
/*     */ 
/*     */   
/*     */   public ItemStack(int i, int j, int k) {
/*  35 */     this.count = 0;
/*  36 */     this.id = i;
/*  37 */     this.count = j;
/*  38 */     this.damage = k;
/*     */   }
/*     */   
/*     */   public ItemStack(NBTTagCompound nbttagcompound) {
/*  42 */     this.count = 0;
/*  43 */     b(nbttagcompound);
/*     */   }
/*     */   
/*     */   public ItemStack a(int i) {
/*  47 */     this.count -= i;
/*  48 */     return new ItemStack(this.id, i, this.damage);
/*     */   }
/*     */ 
/*     */   
/*  52 */   public Item getItem() { return Item.byId[this.id]; }
/*     */ 
/*     */   
/*     */   public boolean placeItem(EntityHuman entityhuman, World world, int i, int j, int k, int l) {
/*  56 */     boolean flag = getItem().a(this, entityhuman, world, i, j, k, l);
/*     */     
/*  58 */     if (flag) {
/*  59 */       entityhuman.a(StatisticList.E[this.id], 1);
/*     */     }
/*     */     
/*  62 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*  66 */   public float a(Block block) { return getItem().a(this, block); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public ItemStack a(World world, EntityHuman entityhuman) { return getItem().a(this, world, entityhuman); }
/*     */ 
/*     */   
/*     */   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
/*  74 */     nbttagcompound.a("id", (short)this.id);
/*  75 */     nbttagcompound.a("Count", (byte)this.count);
/*  76 */     nbttagcompound.a("Damage", (short)this.damage);
/*  77 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  81 */     this.id = nbttagcompound.d("id");
/*  82 */     this.count = nbttagcompound.c("Count");
/*  83 */     this.damage = nbttagcompound.d("Damage");
/*     */   }
/*     */ 
/*     */   
/*  87 */   public int getMaxStackSize() { return getItem().getMaxStackSize(); }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public boolean isStackable() { return (getMaxStackSize() > 1 && (!d() || !f())); }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public boolean d() { return (Item.byId[this.id].e() > 0); }
/*     */ 
/*     */ 
/*     */   
/*  99 */   public boolean usesData() { return Item.byId[this.id].d(); }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public boolean f() { return (d() && this.damage > 0); }
/*     */ 
/*     */ 
/*     */   
/* 107 */   public int g() { return this.damage; }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public int getData() { return this.damage; }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public void b(int i) { this.damage = i; }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public int i() { return Item.byId[this.id].e(); }
/*     */ 
/*     */   
/*     */   public void damage(int i, Entity entity) {
/* 123 */     if (d()) {
/* 124 */       this.damage += i;
/* 125 */       if (this.damage > i()) {
/* 126 */         if (entity instanceof EntityHuman) {
/* 127 */           ((EntityHuman)entity).a(StatisticList.F[this.id], 1);
/*     */         }
/*     */         
/* 130 */         this.count--;
/* 131 */         if (this.count < 0) {
/* 132 */           this.count = 0;
/*     */         }
/*     */         
/* 135 */         this.damage = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving, EntityHuman entityhuman) {
/* 141 */     boolean flag = Item.byId[this.id].a(this, entityliving, entityhuman);
/*     */     
/* 143 */     if (flag) {
/* 144 */       entityhuman.a(StatisticList.E[this.id], 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k, int l, EntityHuman entityhuman) {
/* 149 */     boolean flag = Item.byId[this.id].a(this, i, j, k, l, entityhuman);
/*     */     
/* 151 */     if (flag) {
/* 152 */       entityhuman.a(StatisticList.E[this.id], 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 157 */   public int a(Entity entity) { return Item.byId[this.id].a(entity); }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public boolean b(Block block) { return Item.byId[this.id].a(block); }
/*     */ 
/*     */   
/*     */   public void a(EntityHuman entityhuman) {}
/*     */ 
/*     */   
/* 167 */   public void a(EntityLiving entityliving) { Item.byId[this.id].a(this, entityliving); }
/*     */ 
/*     */ 
/*     */   
/* 171 */   public ItemStack cloneItemStack() { return new ItemStack(this.id, this.count, this.damage); }
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static boolean equals(ItemStack itemstack, ItemStack itemstack1) { return (itemstack == null && itemstack1 == null) ? true : ((itemstack != null && itemstack1 != null) ? itemstack.d(itemstack1) : 0); }
/*     */ 
/*     */ 
/*     */   
/* 179 */   private boolean d(ItemStack itemstack) { return (this.count != itemstack.count) ? false : ((this.id != itemstack.id) ? false : ((this.damage == itemstack.damage))); }
/*     */ 
/*     */ 
/*     */   
/* 183 */   public boolean doMaterialsMatch(ItemStack itemstack) { return (this.id == itemstack.id && this.damage == itemstack.damage); }
/*     */ 
/*     */ 
/*     */   
/* 187 */   public static ItemStack b(ItemStack itemstack) { return (itemstack == null) ? null : itemstack.cloneItemStack(); }
/*     */ 
/*     */ 
/*     */   
/* 191 */   public String toString() { return this.count + "x" + Item.byId[this.id].a() + "@" + this.damage; }
/*     */ 
/*     */   
/*     */   public void a(World world, Entity entity, int i, boolean flag) {
/* 195 */     if (this.b > 0) {
/* 196 */       this.b--;
/*     */     }
/*     */     
/* 199 */     Item.byId[this.id].a(this, world, entity, i, flag);
/*     */   }
/*     */   
/*     */   public void b(World world, EntityHuman entityhuman) {
/* 203 */     entityhuman.a(StatisticList.D[this.id], this.count);
/* 204 */     Item.byId[this.id].c(this, world, entityhuman);
/*     */   }
/*     */ 
/*     */   
/* 208 */   public boolean c(ItemStack itemstack) { return (this.id == itemstack.id && this.count == itemstack.count && this.damage == itemstack.damage); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */