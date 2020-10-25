/*     */ package net.minecraft.server;
/*     */ 
/*     */ import org.bukkit.craftbukkit.inventory.CraftItemStack;
/*     */ import org.bukkit.event.inventory.FurnaceBurnEvent;
/*     */ import org.bukkit.event.inventory.FurnaceSmeltEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class TileEntityFurnace
/*     */   extends TileEntity
/*     */   implements IInventory {
/*  11 */   private ItemStack[] items = new ItemStack[3];
/*  12 */   public int burnTime = 0;
/*  13 */   public int ticksForCurrentFuel = 0;
/*  14 */   public int cookTime = 0;
/*     */ 
/*     */   
/*  17 */   private int lastTick = (int)(System.currentTimeMillis() / 50L);
/*     */   
/*  19 */   public ItemStack[] getContents() { return this.items; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   public int getSize() { return this.items.length; }
/*     */ 
/*     */ 
/*     */   
/*  30 */   public ItemStack getItem(int i) { return this.items[i]; }
/*     */ 
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  34 */     if (this.items[i] != null) {
/*     */ 
/*     */       
/*  37 */       if ((this.items[i]).count <= j) {
/*  38 */         ItemStack itemstack = this.items[i];
/*  39 */         this.items[i] = null;
/*  40 */         return itemstack;
/*     */       } 
/*  42 */       ItemStack itemstack = this.items[i].a(j);
/*  43 */       if ((this.items[i]).count == 0) {
/*  44 */         this.items[i] = null;
/*     */       }
/*     */       
/*  47 */       return itemstack;
/*     */     } 
/*     */     
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/*  55 */     this.items[i] = itemstack;
/*  56 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/*  57 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  62 */   public String getName() { return "Furnace"; }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  66 */     super.a(nbttagcompound);
/*  67 */     NBTTagList nbttaglist = nbttagcompound.l("Items");
/*     */     
/*  69 */     this.items = new ItemStack[getSize()];
/*     */     
/*  71 */     for (int i = 0; i < nbttaglist.c(); i++) {
/*  72 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.a(i);
/*  73 */       byte b0 = nbttagcompound1.c("Slot");
/*     */       
/*  75 */       if (b0 >= 0 && b0 < this.items.length) {
/*  76 */         this.items[b0] = new ItemStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */     
/*  80 */     this.burnTime = nbttagcompound.d("BurnTime");
/*  81 */     this.cookTime = nbttagcompound.d("CookTime");
/*  82 */     this.ticksForCurrentFuel = fuelTime(this.items[1]);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  86 */     super.b(nbttagcompound);
/*  87 */     nbttagcompound.a("BurnTime", (short)this.burnTime);
/*  88 */     nbttagcompound.a("CookTime", (short)this.cookTime);
/*  89 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  91 */     for (int i = 0; i < this.items.length; i++) {
/*  92 */       if (this.items[i] != null) {
/*  93 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/*  95 */         nbttagcompound1.a("Slot", (byte)i);
/*  96 */         this.items[i].a(nbttagcompound1);
/*  97 */         nbttaglist.a(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     nbttagcompound.a("Items", nbttaglist);
/*     */   }
/*     */ 
/*     */   
/* 105 */   public int getMaxStackSize() { return 64; }
/*     */ 
/*     */ 
/*     */   
/* 109 */   public boolean isBurning() { return (this.burnTime > 0); }
/*     */ 
/*     */   
/*     */   public void g_() {
/* 113 */     boolean flag = (this.burnTime > 0);
/* 114 */     boolean flag1 = false;
/*     */ 
/*     */     
/* 117 */     int currentTick = (int)(System.currentTimeMillis() / 50L);
/* 118 */     int elapsedTicks = currentTick - this.lastTick;
/* 119 */     this.lastTick = currentTick;
/*     */ 
/*     */     
/* 122 */     if (isBurning() && canBurn()) {
/* 123 */       this.cookTime += elapsedTicks;
/* 124 */       if (this.cookTime >= 200) {
/* 125 */         this.cookTime %= 200;
/* 126 */         burn();
/* 127 */         flag1 = true;
/*     */       } 
/*     */     } else {
/* 130 */       this.cookTime = 0;
/*     */     } 
/*     */ 
/*     */     
/* 134 */     if (this.burnTime > 0) {
/* 135 */       this.burnTime -= elapsedTicks;
/*     */     }
/*     */     
/* 138 */     if (!this.world.isStatic) {
/*     */       
/* 140 */       if (this.burnTime <= 0 && canBurn() && this.items[true] != null) {
/* 141 */         CraftItemStack fuel = new CraftItemStack(this.items[1]);
/*     */         
/* 143 */         FurnaceBurnEvent furnaceBurnEvent = new FurnaceBurnEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), fuel, fuelTime(this.items[1]));
/* 144 */         this.world.getServer().getPluginManager().callEvent(furnaceBurnEvent);
/*     */         
/* 146 */         if (furnaceBurnEvent.isCancelled()) {
/*     */           return;
/*     */         }
/*     */         
/* 150 */         this.ticksForCurrentFuel = furnaceBurnEvent.getBurnTime();
/* 151 */         this.burnTime += this.ticksForCurrentFuel;
/* 152 */         if (this.burnTime > 0 && furnaceBurnEvent.isBurning()) {
/*     */           
/* 154 */           flag1 = true;
/* 155 */           if (this.items[true] != null) {
/* 156 */             (this.items[1]).count--;
/* 157 */             if ((this.items[1]).count == 0) {
/* 158 */               this.items[1] = null;
/*     */             }
/*     */           } 
/*     */         } 
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
/*     */ 
/*     */ 
/*     */       
/* 177 */       if (flag != ((this.burnTime > 0))) {
/* 178 */         flag1 = true;
/* 179 */         BlockFurnace.a((this.burnTime > 0), this.world, this.x, this.y, this.z);
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     if (flag1) {
/* 184 */       update();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean canBurn() {
/* 189 */     if (this.items[false] == null) {
/* 190 */       return false;
/*     */     }
/* 192 */     ItemStack itemstack = FurnaceRecipes.getInstance().a((this.items[0].getItem()).id);
/*     */ 
/*     */     
/* 195 */     return (itemstack == null) ? false : ((this.items[2] == null) ? true : (!this.items[2].doMaterialsMatch(itemstack) ? false : (((this.items[2]).count + itemstack.count <= getMaxStackSize() && (this.items[2]).count < this.items[2].getMaxStackSize()) ? true : (((this.items[2]).count + itemstack.count <= itemstack.getMaxStackSize())))));
/*     */   }
/*     */ 
/*     */   
/*     */   public void burn() {
/* 200 */     if (canBurn()) {
/* 201 */       ItemStack itemstack = FurnaceRecipes.getInstance().a((this.items[0].getItem()).id);
/*     */ 
/*     */       
/* 204 */       CraftItemStack source = new CraftItemStack(this.items[0]);
/* 205 */       CraftItemStack result = new CraftItemStack(itemstack.cloneItemStack());
/*     */       
/* 207 */       FurnaceSmeltEvent furnaceSmeltEvent = new FurnaceSmeltEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), source, result);
/* 208 */       this.world.getServer().getPluginManager().callEvent(furnaceSmeltEvent);
/*     */       
/* 210 */       if (furnaceSmeltEvent.isCancelled()) {
/*     */         return;
/*     */       }
/*     */       
/* 214 */       ItemStack oldResult = furnaceSmeltEvent.getResult();
/* 215 */       ItemStack newResult = new ItemStack(oldResult.getTypeId(), oldResult.getAmount(), oldResult.getDurability());
/* 216 */       itemstack = newResult;
/*     */       
/* 218 */       if (this.items[2] == null) {
/* 219 */         this.items[2] = itemstack.cloneItemStack();
/* 220 */       } else if ((this.items[2]).id == itemstack.id) {
/*     */         
/* 222 */         if ((this.items[2]).damage == itemstack.damage) {
/* 223 */           (this.items[2]).count += itemstack.count;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 228 */       (this.items[0]).count--;
/* 229 */       if ((this.items[0]).count <= 0) {
/* 230 */         this.items[0] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int fuelTime(ItemStack itemstack) {
/* 236 */     if (itemstack == null) {
/* 237 */       return 0;
/*     */     }
/* 239 */     int i = (itemstack.getItem()).id;
/*     */     
/* 241 */     return (i < 256 && (Block.byId[i]).material == Material.WOOD) ? 300 : ((i == Item.STICK.id) ? 100 : ((i == Item.COAL.id) ? 1600 : ((i == Item.LAVA_BUCKET.id) ? 20000 : ((i == Block.SAPLING.id) ? 100 : 0))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 246 */   public boolean a_(EntityHuman entityhuman) { return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D)); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntityFurnace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */