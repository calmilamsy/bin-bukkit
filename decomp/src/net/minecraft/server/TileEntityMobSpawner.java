/*     */ package net.minecraft.server;
/*     */ public class TileEntityMobSpawner extends TileEntity {
/*     */   public int spawnDelay;
/*     */   public String mobName;
/*     */   
/*     */   public TileEntityMobSpawner() {
/*   7 */     this.spawnDelay = -1;
/*   8 */     this.mobName = "Pig";
/*     */     
/*  10 */     this.c = 0.0D;
/*     */ 
/*     */     
/*  13 */     this.spawnDelay = 20;
/*     */   }
/*     */   public double b; public double c;
/*     */   
/*  17 */   public void a(String s) { this.mobName = s; }
/*     */ 
/*     */ 
/*     */   
/*  21 */   public boolean a() { return (this.world.a(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D, 16.0D) != null); }
/*     */ 
/*     */   
/*     */   public void g_() {
/*  25 */     this.c = this.b;
/*  26 */     if (a()) {
/*  27 */       double d0 = (this.x + this.world.random.nextFloat());
/*  28 */       double d1 = (this.y + this.world.random.nextFloat());
/*  29 */       double d2 = (this.z + this.world.random.nextFloat());
/*     */       
/*  31 */       this.world.a("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/*  32 */       this.world.a("flame", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */       
/*  34 */       for (this.b += (1000.0F / (this.spawnDelay + 200.0F)); this.b > 360.0D; this.c -= 360.0D) {
/*  35 */         this.b -= 360.0D;
/*     */       }
/*     */       
/*  38 */       if (!this.world.isStatic) {
/*  39 */         if (this.spawnDelay == -1) {
/*  40 */           c();
/*     */         }
/*     */         
/*  43 */         if (this.spawnDelay > 0) {
/*  44 */           this.spawnDelay--;
/*     */           
/*     */           return;
/*     */         } 
/*  48 */         byte b0 = 4;
/*     */         
/*  50 */         for (int i = 0; i < b0; i++) {
/*  51 */           EntityLiving entityliving = (EntityLiving)EntityTypes.a(this.mobName, this.world);
/*     */           
/*  53 */           if (entityliving == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  58 */           boolean isAnimal = (entityliving instanceof EntityAnimal || entityliving instanceof EntityWaterAnimal);
/*  59 */           if ((isAnimal && !this.world.allowAnimals) || (!isAnimal && !this.world.allowMonsters)) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  64 */           int j = this.world.a(entityliving.getClass(), AxisAlignedBB.b(this.x, this.y, this.z, (this.x + 1), (this.y + 1), (this.z + 1)).b(8.0D, 4.0D, 8.0D)).size();
/*     */           
/*  66 */           if (j >= 6) {
/*  67 */             c();
/*     */             
/*     */             return;
/*     */           } 
/*  71 */           if (entityliving != null) {
/*  72 */             double d3 = this.x + (this.world.random.nextDouble() - this.world.random.nextDouble()) * 4.0D;
/*  73 */             double d4 = (this.y + this.world.random.nextInt(3) - 1);
/*  74 */             double d5 = this.z + (this.world.random.nextDouble() - this.world.random.nextDouble()) * 4.0D;
/*     */             
/*  76 */             entityliving.setPositionRotation(d3, d4, d5, this.world.random.nextFloat() * 360.0F, 0.0F);
/*  77 */             if (entityliving.d()) {
/*     */               
/*  79 */               this.world.addEntity(entityliving, CreatureSpawnEvent.SpawnReason.SPAWNER);
/*     */               
/*  81 */               for (int k = 0; k < 20; k++) {
/*  82 */                 d0 = this.x + 0.5D + (this.world.random.nextFloat() - 0.5D) * 2.0D;
/*  83 */                 d1 = this.y + 0.5D + (this.world.random.nextFloat() - 0.5D) * 2.0D;
/*  84 */                 d2 = this.z + 0.5D + (this.world.random.nextFloat() - 0.5D) * 2.0D;
/*  85 */                 this.world.a("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/*  86 */                 this.world.a("flame", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */               } 
/*     */               
/*  89 */               entityliving.S();
/*  90 */               c();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  96 */       super.g_();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 101 */   private void c() { this.spawnDelay = 200 + this.world.random.nextInt(600); }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 105 */     super.a(nbttagcompound);
/* 106 */     this.mobName = nbttagcompound.getString("EntityId");
/* 107 */     this.spawnDelay = nbttagcompound.d("Delay");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 111 */     super.b(nbttagcompound);
/* 112 */     nbttagcompound.setString("EntityId", this.mobName);
/* 113 */     nbttagcompound.a("Delay", (short)this.spawnDelay);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntityMobSpawner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */