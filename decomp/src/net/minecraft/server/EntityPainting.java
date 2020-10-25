/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Painting;
/*     */ import org.bukkit.event.painting.PaintingBreakByEntityEvent;
/*     */ import org.bukkit.event.painting.PaintingBreakByWorldEvent;
/*     */ 
/*     */ 
/*     */ public class EntityPainting
/*     */   extends Entity
/*     */ {
/*     */   private int f;
/*     */   public int a;
/*     */   public int b;
/*     */   public int c;
/*     */   public int d;
/*     */   public EnumArt e;
/*     */   
/*     */   public EntityPainting(World world) {
/*  21 */     super(world);
/*  22 */     this.f = 0;
/*  23 */     this.a = 0;
/*  24 */     this.height = 0.0F;
/*  25 */     b(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityPainting(World world, int i, int j, int k, int l) {
/*  29 */     this(world);
/*  30 */     this.b = i;
/*  31 */     this.c = j;
/*  32 */     this.d = k;
/*  33 */     ArrayList arraylist = new ArrayList();
/*  34 */     EnumArt[] aenumart = EnumArt.values();
/*  35 */     int i1 = aenumart.length;
/*     */     
/*  37 */     for (int j1 = 0; j1 < i1; j1++) {
/*  38 */       EnumArt enumart = aenumart[j1];
/*     */       
/*  40 */       this.e = enumart;
/*  41 */       b(l);
/*  42 */       if (h()) {
/*  43 */         arraylist.add(enumart);
/*     */       }
/*     */     } 
/*     */     
/*  47 */     if (arraylist.size() > 0) {
/*  48 */       this.e = (EnumArt)arraylist.get(this.random.nextInt(arraylist.size()));
/*     */     }
/*     */     
/*  51 */     b(l);
/*     */   }
/*     */   
/*     */   protected void b() {}
/*     */   
/*     */   public void b(int i) {
/*  57 */     this.a = i;
/*  58 */     this.lastYaw = this.yaw = (i * 90);
/*  59 */     float f = this.e.B;
/*  60 */     float f1 = this.e.C;
/*  61 */     float f2 = this.e.B;
/*     */     
/*  63 */     if (i != 0 && i != 2) {
/*  64 */       f = 0.5F;
/*     */     } else {
/*  66 */       f2 = 0.5F;
/*     */     } 
/*     */     
/*  69 */     f /= 32.0F;
/*  70 */     f1 /= 32.0F;
/*  71 */     f2 /= 32.0F;
/*  72 */     float f3 = this.b + 0.5F;
/*  73 */     float f4 = this.c + 0.5F;
/*  74 */     float f5 = this.d + 0.5F;
/*  75 */     float f6 = 0.5625F;
/*     */     
/*  77 */     if (i == 0) {
/*  78 */       f5 -= f6;
/*     */     }
/*     */     
/*  81 */     if (i == 1) {
/*  82 */       f3 -= f6;
/*     */     }
/*     */     
/*  85 */     if (i == 2) {
/*  86 */       f5 += f6;
/*     */     }
/*     */     
/*  89 */     if (i == 3) {
/*  90 */       f3 += f6;
/*     */     }
/*     */     
/*  93 */     if (i == 0) {
/*  94 */       f3 -= c(this.e.B);
/*     */     }
/*     */     
/*  97 */     if (i == 1) {
/*  98 */       f5 += c(this.e.B);
/*     */     }
/*     */     
/* 101 */     if (i == 2) {
/* 102 */       f3 += c(this.e.B);
/*     */     }
/*     */     
/* 105 */     if (i == 3) {
/* 106 */       f5 -= c(this.e.B);
/*     */     }
/*     */     
/* 109 */     f4 += c(this.e.C);
/* 110 */     setPosition(f3, f4, f5);
/* 111 */     float f7 = -0.00625F;
/*     */     
/* 113 */     this.boundingBox.c((f3 - f - f7), (f4 - f1 - f7), (f5 - f2 - f7), (f3 + f + f7), (f4 + f1 + f7), (f5 + f2 + f7));
/*     */   }
/*     */ 
/*     */   
/* 117 */   private float c(int i) { return (i == 32) ? 0.5F : ((i == 64) ? 0.5F : 0.0F); }
/*     */ 
/*     */   
/*     */   public void m_() {
/* 121 */     if (this.f++ == 100 && !this.world.isStatic) {
/* 122 */       this.f = 0;
/* 123 */       if (!h()) {
/*     */         
/* 125 */         PaintingBreakByWorldEvent event = new PaintingBreakByWorldEvent((Painting)getBukkitEntity());
/* 126 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 128 */         if (event.isCancelled()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 133 */         die();
/* 134 */         this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.PAINTING)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean h() {
/* 140 */     if (this.world.getEntities(this, this.boundingBox).size() > 0) {
/* 141 */       return false;
/*     */     }
/* 143 */     int i = this.e.B / 16;
/* 144 */     int j = this.e.C / 16;
/* 145 */     int k = this.b;
/* 146 */     int l = this.c;
/* 147 */     int i1 = this.d;
/*     */     
/* 149 */     if (this.a == 0) {
/* 150 */       k = MathHelper.floor(this.locX - (this.e.B / 32.0F));
/*     */     }
/*     */     
/* 153 */     if (this.a == 1) {
/* 154 */       i1 = MathHelper.floor(this.locZ - (this.e.B / 32.0F));
/*     */     }
/*     */     
/* 157 */     if (this.a == 2) {
/* 158 */       k = MathHelper.floor(this.locX - (this.e.B / 32.0F));
/*     */     }
/*     */     
/* 161 */     if (this.a == 3) {
/* 162 */       i1 = MathHelper.floor(this.locZ - (this.e.B / 32.0F));
/*     */     }
/*     */     
/* 165 */     l = MathHelper.floor(this.locY - (this.e.C / 32.0F));
/*     */ 
/*     */ 
/*     */     
/* 169 */     for (k1 = 0; k1 < i; k1++) {
/* 170 */       for (int j1 = 0; j1 < j; j1++) {
/*     */         Material material;
/*     */         
/* 173 */         if (this.a != 0 && this.a != 2) {
/* 174 */           material = this.world.getMaterial(this.b, l + j1, i1 + k1);
/*     */         } else {
/* 176 */           material = this.world.getMaterial(k + k1, l + j1, this.d);
/*     */         } 
/*     */         
/* 179 */         if (!material.isBuildable()) {
/* 180 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     List list = this.world.b(this, this.boundingBox);
/*     */     
/* 187 */     for (int j1 = 0; j1 < list.size(); j1++) {
/* 188 */       if (list.get(j1) instanceof EntityPainting) {
/* 189 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 198 */   public boolean l_() { return true; }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(Entity entity, int i) {
/* 202 */     if (!this.dead && !this.world.isStatic) {
/*     */       
/* 204 */       PaintingBreakByEntityEvent event = new PaintingBreakByEntityEvent((Painting)getBukkitEntity(), (entity == null) ? null : entity.getBukkitEntity());
/* 205 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 207 */       if (event.isCancelled()) {
/* 208 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 212 */       die();
/* 213 */       af();
/* 214 */       this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.PAINTING)));
/*     */     } 
/*     */     
/* 217 */     return true;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 221 */     nbttagcompound.a("Dir", (byte)this.a);
/* 222 */     nbttagcompound.setString("Motive", this.e.A);
/* 223 */     nbttagcompound.a("TileX", this.b);
/* 224 */     nbttagcompound.a("TileY", this.c);
/* 225 */     nbttagcompound.a("TileZ", this.d);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 229 */     this.a = nbttagcompound.c("Dir");
/* 230 */     this.b = nbttagcompound.e("TileX");
/* 231 */     this.c = nbttagcompound.e("TileY");
/* 232 */     this.d = nbttagcompound.e("TileZ");
/* 233 */     String s = nbttagcompound.getString("Motive");
/* 234 */     EnumArt[] aenumart = EnumArt.values();
/* 235 */     int i = aenumart.length;
/*     */     
/* 237 */     for (int j = 0; j < i; j++) {
/* 238 */       EnumArt enumart = aenumart[j];
/*     */       
/* 240 */       if (enumart.A.equals(s)) {
/* 241 */         this.e = enumart;
/*     */       }
/*     */     } 
/*     */     
/* 245 */     if (this.e == null) {
/* 246 */       this.e = EnumArt.KEBAB;
/*     */     }
/*     */     
/* 249 */     b(this.a);
/*     */   }
/*     */   
/*     */   public void a(double d0, double d1, double d2) {
/* 253 */     if (!this.world.isStatic && d0 * d0 + d1 * d1 + d2 * d2 > 0.0D) {
/* 254 */       die();
/* 255 */       this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.PAINTING)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(double d0, double d1, double d2) {
/* 260 */     if (!this.world.isStatic && d0 * d0 + d1 * d1 + d2 * d2 > 0.0D) {
/* 261 */       die();
/* 262 */       this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.PAINTING)));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityPainting.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */