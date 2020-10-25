/*     */ package net.minecraft.server;
/*     */ import org.bukkit.craftbukkit.event.CraftEventFactory;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ 
/*     */ public class ItemInWorldManager {
/*     */   private WorldServer world;
/*     */   public EntityHuman player;
/*     */   private float c;
/*     */   private int lastDigTick;
/*     */   private int e;
/*     */   private int f;
/*     */   
/*     */   public ItemInWorldManager(WorldServer worldserver) {
/*  16 */     this.c = 0.0F;
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
/*  29 */     this.world = worldserver;
/*     */   }
/*     */   private int g; private int currentTick; private boolean i; private int j; private int k; private int l; private int m;
/*     */   public void a() {
/*  33 */     this.currentTick = (int)(System.currentTimeMillis() / 50L);
/*  34 */     if (this.i) {
/*  35 */       int i = this.currentTick - this.m;
/*  36 */       int j = this.world.getTypeId(this.j, this.k, this.l);
/*     */       
/*  38 */       if (j != 0) {
/*  39 */         Block block = Block.byId[j];
/*  40 */         float f = block.getDamage(this.player) * (i + 1);
/*     */         
/*  42 */         if (f >= 1.0F) {
/*  43 */           this.i = false;
/*  44 */           c(this.j, this.k, this.l);
/*     */         } 
/*     */       } else {
/*  47 */         this.i = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dig(int i, int j, int k, int l) {
/*  54 */     this.lastDigTick = (int)(System.currentTimeMillis() / 50L);
/*  55 */     int i1 = this.world.getTypeId(i, j, k);
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (i1 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*  63 */     PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, i, j, k, l, this.player.inventory.getItemInHand());
/*     */     
/*  65 */     if (event.useInteractedBlock() == Event.Result.DENY) {
/*     */       
/*  67 */       if (i1 == Block.WOODEN_DOOR.id) {
/*     */         
/*  69 */         boolean bottom = ((this.world.getData(i, j, k) & 0x8) == 0);
/*  70 */         ((EntityPlayer)this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
/*  71 */         ((EntityPlayer)this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j + (bottom ? 1 : -1), k, this.world));
/*  72 */       } else if (i1 == Block.TRAP_DOOR.id) {
/*  73 */         ((EntityPlayer)this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
/*     */       } 
/*     */     } else {
/*  76 */       Block.byId[i1].b(this.world, i, j, k, this.player);
/*     */       
/*  78 */       this.world.douseFire((EntityHuman)null, i, j, k, l);
/*     */     } 
/*     */ 
/*     */     
/*  82 */     float toolDamage = Block.byId[i1].getDamage(this.player);
/*  83 */     if (event.useItemInHand() == Event.Result.DENY) {
/*     */       
/*  85 */       if (toolDamage > 1.0F) {
/*  86 */         ((EntityPlayer)this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
/*     */       }
/*     */       return;
/*     */     } 
/*  90 */     BlockDamageEvent blockEvent = CraftEventFactory.callBlockDamageEvent(this.player, i, j, k, this.player.inventory.getItemInHand(), (toolDamage >= 1.0F));
/*     */     
/*  92 */     if (blockEvent.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     if (blockEvent.getInstaBreak()) {
/*  97 */       toolDamage = 2.0F;
/*     */     }
/*     */     
/* 100 */     if (toolDamage >= 1.0F) {
/*     */       
/* 102 */       c(i, j, k);
/*     */     } else {
/* 104 */       this.e = i;
/* 105 */       this.f = j;
/* 106 */       this.g = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k) {
/* 111 */     if (i == this.e && j == this.f && k == this.g) {
/* 112 */       this.currentTick = (int)(System.currentTimeMillis() / 50L);
/* 113 */       int l = this.currentTick - this.lastDigTick;
/* 114 */       int i1 = this.world.getTypeId(i, j, k);
/*     */       
/* 116 */       if (i1 != 0) {
/* 117 */         Block block = Block.byId[i1];
/* 118 */         float f = block.getDamage(this.player) * (l + 1);
/*     */         
/* 120 */         if (f >= 0.7F) {
/* 121 */           c(i, j, k);
/* 122 */         } else if (!this.i) {
/* 123 */           this.i = true;
/* 124 */           this.j = i;
/* 125 */           this.k = j;
/* 126 */           this.l = k;
/* 127 */           this.m = this.lastDigTick;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 132 */       ((EntityPlayer)this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
/*     */     } 
/*     */ 
/*     */     
/* 136 */     this.c = 0.0F;
/*     */   }
/*     */   
/*     */   public boolean b(int i, int j, int k) {
/* 140 */     Block block = Block.byId[this.world.getTypeId(i, j, k)];
/* 141 */     int l = this.world.getData(i, j, k);
/* 142 */     boolean flag = this.world.setTypeId(i, j, k, 0);
/*     */     
/* 144 */     if (block != null && flag) {
/* 145 */       block.postBreak(this.world, i, j, k, l);
/*     */     }
/*     */     
/* 148 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c(int i, int j, int k) {
/* 153 */     if (this.player instanceof EntityPlayer) {
/* 154 */       Block block = this.world.getWorld().getBlockAt(i, j, k);
/*     */       
/* 156 */       BlockBreakEvent event = new BlockBreakEvent(block, (Player)this.player.getBukkitEntity());
/* 157 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 159 */       if (event.isCancelled()) {
/* 160 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 165 */     int l = this.world.getTypeId(i, j, k);
/* 166 */     int i1 = this.world.getData(i, j, k);
/*     */     
/* 168 */     this.world.a(this.player, 2001, i, j, k, l + this.world.getData(i, j, k) * 256);
/* 169 */     boolean flag = b(i, j, k);
/* 170 */     ItemStack itemstack = this.player.G();
/*     */     
/* 172 */     if (itemstack != null) {
/* 173 */       itemstack.a(l, i, j, k, this.player);
/* 174 */       if (itemstack.count == 0) {
/* 175 */         itemstack.a(this.player);
/* 176 */         this.player.H();
/*     */       } 
/*     */     } 
/*     */     
/* 180 */     if (flag && this.player.b(Block.byId[l])) {
/* 181 */       Block.byId[l].a(this.world, this.player, i, j, k, i1);
/* 182 */       ((EntityPlayer)this.player).netServerHandler.sendPacket(new Packet53BlockChange(i, j, k, this.world));
/*     */     } 
/*     */     
/* 185 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean useItem(EntityHuman entityhuman, World world, ItemStack itemstack) {
/* 189 */     int i = itemstack.count;
/* 190 */     ItemStack itemstack1 = itemstack.a(world, entityhuman);
/*     */     
/* 192 */     if (itemstack1 == itemstack && (itemstack1 == null || itemstack1.count == i)) {
/* 193 */       return false;
/*     */     }
/* 195 */     entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = itemstack1;
/* 196 */     if (itemstack1.count == 0) {
/* 197 */       entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = null;
/*     */     }
/*     */     
/* 200 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(EntityHuman entityhuman, World world, ItemStack itemstack, int i, int j, int k, int l) {
/* 205 */     int i1 = world.getTypeId(i, j, k);
/*     */ 
/*     */     
/* 208 */     boolean result = false;
/* 209 */     if (i1 > 0) {
/* 210 */       PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);
/* 211 */       if (event.useInteractedBlock() == Event.Result.DENY) {
/*     */         
/* 213 */         if (i1 == Block.WOODEN_DOOR.id) {
/* 214 */           boolean bottom = ((world.getData(i, j, k) & 0x8) == 0);
/* 215 */           ((EntityPlayer)entityhuman).netServerHandler.sendPacket(new Packet53BlockChange(i, j + (bottom ? 1 : -1), k, world));
/*     */         } 
/* 217 */         result = (event.useItemInHand() != Event.Result.ALLOW);
/*     */       } else {
/* 219 */         result = Block.byId[i1].interact(world, i, j, k, entityhuman);
/*     */       } 
/*     */       
/* 222 */       if (itemstack != null && !result) {
/* 223 */         result = itemstack.placeItem(entityhuman, world, i, j, k, l);
/*     */       }
/*     */ 
/*     */       
/* 227 */       if (itemstack != null && ((!result && event.useItemInHand() != Event.Result.DENY) || event.useItemInHand() == Event.Result.ALLOW)) {
/* 228 */         useItem(entityhuman, world, itemstack);
/*     */       }
/*     */     } 
/* 231 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemInWorldManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */