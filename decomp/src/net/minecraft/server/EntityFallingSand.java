/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityFallingSand
/*    */   extends Entity
/*    */ {
/*    */   public int a;
/* 13 */   public int b = 0;
/*    */ 
/*    */   
/* 16 */   public EntityFallingSand(World paramWorld) { super(paramWorld); }
/*    */ 
/*    */   
/*    */   public EntityFallingSand(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt) {
/* 20 */     super(paramWorld);
/* 21 */     this.a = paramInt;
/* 22 */     this.aI = true;
/* 23 */     b(0.98F, 0.98F);
/* 24 */     this.height = this.width / 2.0F;
/* 25 */     setPosition(paramDouble1, paramDouble2, paramDouble3);
/*    */     
/* 27 */     this.motX = 0.0D;
/* 28 */     this.motY = 0.0D;
/* 29 */     this.motZ = 0.0D;
/*    */     
/* 31 */     this.lastX = paramDouble1;
/* 32 */     this.lastY = paramDouble2;
/* 33 */     this.lastZ = paramDouble3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 38 */   protected boolean n() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void b() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public boolean l_() { return !this.dead; }
/*    */ 
/*    */   
/*    */   public void m_() {
/* 52 */     if (this.a == 0) {
/* 53 */       die();
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     this.lastX = this.locX;
/* 58 */     this.lastY = this.locY;
/* 59 */     this.lastZ = this.locZ;
/* 60 */     this.b++;
/*    */     
/* 62 */     this.motY -= 0.03999999910593033D;
/* 63 */     move(this.motX, this.motY, this.motZ);
/* 64 */     this.motX *= 0.9800000190734863D;
/* 65 */     this.motY *= 0.9800000190734863D;
/* 66 */     this.motZ *= 0.9800000190734863D;
/*    */     
/* 68 */     int i = MathHelper.floor(this.locX);
/* 69 */     int j = MathHelper.floor(this.locY);
/* 70 */     int k = MathHelper.floor(this.locZ);
/* 71 */     if (this.world.getTypeId(i, j, k) == this.a) {
/* 72 */       this.world.setTypeId(i, j, k, 0);
/*    */     }
/*    */     
/* 75 */     if (this.onGround) {
/* 76 */       this.motX *= 0.699999988079071D;
/* 77 */       this.motZ *= 0.699999988079071D;
/* 78 */       this.motY *= -0.5D;
/*    */       
/* 80 */       die();
/*    */       
/* 82 */       if ((!this.world.a(this.a, i, j, k, true, 1) || BlockSand.c_(this.world, i, j - 1, k) || !this.world.setTypeId(i, j, k, this.a)) && 
/* 83 */         !this.world.isStatic) {
/* 84 */         b(this.a, 1);
/*    */       }
/* 86 */     } else if (this.b > 100 && !this.world.isStatic) {
/* 87 */       b(this.a, 1);
/* 88 */       die();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 94 */   protected void b(NBTTagCompound paramNBTTagCompound) { paramNBTTagCompound.a("Tile", (byte)this.a); }
/*    */ 
/*    */ 
/*    */   
/* 98 */   protected void a(NBTTagCompound paramNBTTagCompound) { this.a = paramNBTTagCompound.c("Tile") & 0xFF; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityFallingSand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */