/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityFlying
/*    */   extends EntityLiving
/*    */ {
/*  9 */   public EntityFlying(World paramWorld) { super(paramWorld); }
/*    */ 
/*    */   
/*    */   protected void a(float paramFloat) {}
/*    */ 
/*    */   
/*    */   public void a(float paramFloat1, float paramFloat2) {
/* 16 */     if (ad()) {
/* 17 */       a(paramFloat1, paramFloat2, 0.02F);
/* 18 */       move(this.motX, this.motY, this.motZ);
/*    */       
/* 20 */       this.motX *= 0.800000011920929D;
/* 21 */       this.motY *= 0.800000011920929D;
/* 22 */       this.motZ *= 0.800000011920929D;
/* 23 */     } else if (ae()) {
/* 24 */       a(paramFloat1, paramFloat2, 0.02F);
/* 25 */       move(this.motX, this.motY, this.motZ);
/* 26 */       this.motX *= 0.5D;
/* 27 */       this.motY *= 0.5D;
/* 28 */       this.motZ *= 0.5D;
/*    */     } else {
/* 30 */       float f1 = 0.91F;
/* 31 */       if (this.onGround) {
/* 32 */         f1 = 0.54600006F;
/* 33 */         int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
/* 34 */         if (i > 0) {
/* 35 */           f1 = (Block.byId[i]).frictionFactor * 0.91F;
/*    */         }
/*    */       } 
/*    */       
/* 39 */       float f2 = 0.16277136F / f1 * f1 * f1;
/* 40 */       a(paramFloat1, paramFloat2, this.onGround ? (0.1F * f2) : 0.02F);
/*    */       
/* 42 */       f1 = 0.91F;
/* 43 */       if (this.onGround) {
/* 44 */         f1 = 0.54600006F;
/* 45 */         int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
/* 46 */         if (i > 0) {
/* 47 */           f1 = (Block.byId[i]).frictionFactor * 0.91F;
/*    */         }
/*    */       } 
/*    */       
/* 51 */       move(this.motX, this.motY, this.motZ);
/*    */       
/* 53 */       this.motX *= f1;
/* 54 */       this.motY *= f1;
/* 55 */       this.motZ *= f1;
/*    */     } 
/* 57 */     this.an = this.ao;
/* 58 */     double d1 = this.locX - this.lastX;
/* 59 */     double d2 = this.locZ - this.lastZ;
/* 60 */     float f = MathHelper.a(d1 * d1 + d2 * d2) * 4.0F;
/* 61 */     if (f > 1.0F) f = 1.0F; 
/* 62 */     this.ao += (f - this.ao) * 0.4F;
/* 63 */     this.ap += this.ao;
/*    */   }
/*    */ 
/*    */   
/* 67 */   public boolean p() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityFlying.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */