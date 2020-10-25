/*     */ package net.minecraft.server;
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
/*     */ public class EntitySlime
/*     */   extends EntityLiving
/*     */   implements IMonster
/*     */ {
/*     */   public float a;
/*     */   public float b;
/*  19 */   private int size = 0;
/*     */   
/*     */   public EntitySlime(World paramWorld) {
/*  22 */     super(paramWorld);
/*  23 */     this.texture = "/mob/slime.png";
/*  24 */     int i = 1 << this.random.nextInt(3);
/*  25 */     this.height = 0.0F;
/*  26 */     this.size = this.random.nextInt(20) + 10;
/*  27 */     setSize(i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b() {
/*  32 */     super.b();
/*     */     
/*  34 */     this.datawatcher.a(16, new Byte(true));
/*     */   }
/*     */   
/*     */   public void setSize(int paramInt) {
/*  38 */     this.datawatcher.watch(16, new Byte((byte)paramInt));
/*  39 */     b(0.6F * paramInt, 0.6F * paramInt);
/*  40 */     this.health = paramInt * paramInt;
/*  41 */     setPosition(this.locX, this.locY, this.locZ);
/*     */   }
/*     */ 
/*     */   
/*  45 */   public int getSize() { return this.datawatcher.a(16); }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/*  49 */     super.b(paramNBTTagCompound);
/*  50 */     paramNBTTagCompound.a("Size", getSize() - 1);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/*  54 */     super.a(paramNBTTagCompound);
/*  55 */     setSize(paramNBTTagCompound.e("Size") + 1);
/*     */   }
/*     */   
/*     */   public void m_() {
/*  59 */     this.b = this.a;
/*  60 */     boolean bool = this.onGround;
/*  61 */     super.m_();
/*  62 */     if (this.onGround && !bool) {
/*  63 */       int i = getSize();
/*  64 */       for (byte b1 = 0; b1 < i * 8; b1++) {
/*  65 */         float f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
/*  66 */         float f2 = this.random.nextFloat() * 0.5F + 0.5F;
/*  67 */         float f3 = MathHelper.sin(f1) * i * 0.5F * f2;
/*  68 */         float f4 = MathHelper.cos(f1) * i * 0.5F * f2;
/*  69 */         this.world.a("slime", this.locX + f3, this.boundingBox.b, this.locZ + f4, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */       
/*  72 */       if (i > 2) {
/*  73 */         this.world.makeSound(this, "mob.slime", k(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/*     */       }
/*  75 */       this.a = -0.5F;
/*     */     } 
/*  77 */     this.a *= 0.6F;
/*     */   }
/*     */   
/*     */   protected void c_() {
/*  81 */     U();
/*  82 */     EntityHuman entityHuman = this.world.findNearbyPlayer(this, 16.0D);
/*  83 */     if (entityHuman != null) {
/*  84 */       a(entityHuman, 10.0F, 20.0F);
/*     */     }
/*  86 */     if (this.onGround && this.size-- <= 0) {
/*  87 */       this.size = this.random.nextInt(20) + 10;
/*  88 */       if (entityHuman != null) {
/*  89 */         this.size /= 3;
/*     */       }
/*  91 */       this.aC = true;
/*  92 */       if (getSize() > 1) {
/*  93 */         this.world.makeSound(this, "mob.slime", k(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */       }
/*     */       
/*  96 */       this.a = 1.0F;
/*  97 */       this.az = 1.0F - this.random.nextFloat() * 2.0F;
/*  98 */       this.aA = (1 * getSize());
/*     */     } else {
/* 100 */       this.aC = false;
/* 101 */       if (this.onGround) {
/* 102 */         this.az = this.aA = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void die() {
/* 108 */     int i = getSize();
/* 109 */     if (!this.world.isStatic && i > 1 && this.health == 0) {
/* 110 */       for (byte b1 = 0; b1 < 4; b1++) {
/* 111 */         float f1 = ((b1 % 2) - 0.5F) * i / 4.0F;
/* 112 */         float f2 = ((b1 / 2) - 0.5F) * i / 4.0F;
/* 113 */         EntitySlime entitySlime = new EntitySlime(this.world);
/* 114 */         entitySlime.setSize(i / 2);
/* 115 */         entitySlime.setPositionRotation(this.locX + f1, this.locY + 0.5D, this.locZ + f2, this.random.nextFloat() * 360.0F, 0.0F);
/* 116 */         this.world.addEntity(entitySlime);
/*     */       } 
/*     */     }
/* 119 */     super.die();
/*     */   }
/*     */   
/*     */   public void b(EntityHuman paramEntityHuman) {
/* 123 */     int i = getSize();
/* 124 */     if (i > 1 && 
/* 125 */       e(paramEntityHuman) && f(paramEntityHuman) < 0.6D * i && 
/* 126 */       paramEntityHuman.damageEntity(this, i)) {
/* 127 */       this.world.makeSound(this, "mob.slimeattack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   protected String h() { return "mob.slime"; }
/*     */ 
/*     */ 
/*     */   
/* 139 */   protected String i() { return "mob.slime"; }
/*     */ 
/*     */   
/*     */   protected int j() {
/* 143 */     if (getSize() == 1) return Item.SLIME_BALL.id; 
/* 144 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 148 */     Chunk chunk = this.world.getChunkAtWorldCoords(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
/* 149 */     if ((getSize() == 1 || this.world.spawnMonsters > 0) && this.random.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && this.locY < 16.0D) {
/* 150 */       return true;
/*     */     }
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 156 */   protected float k() { return 0.6F; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntitySlime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */