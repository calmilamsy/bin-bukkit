/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityChicken
/*    */   extends EntityAnimal
/*    */ {
/*    */   public boolean a = false;
/* 10 */   public float b = 0.0F;
/* 11 */   public float c = 0.0F;
/*    */   public float f;
/* 13 */   public float h = 1.0F; public float g;
/*    */   public int i;
/*    */   
/*    */   public EntityChicken(World paramWorld) {
/* 17 */     super(paramWorld);
/* 18 */     this.texture = "/mob/chicken.png";
/* 19 */     b(0.3F, 0.4F);
/* 20 */     this.health = 4;
/* 21 */     this.i = this.random.nextInt(6000) + 6000;
/*    */   }
/*    */   
/*    */   public void v() {
/* 25 */     super.v();
/*    */     
/* 27 */     this.g = this.b;
/* 28 */     this.f = this.c;
/*    */     
/* 30 */     this.c = (float)(this.c + (this.onGround ? -1 : 4) * 0.3D);
/* 31 */     if (this.c < 0.0F) this.c = 0.0F; 
/* 32 */     if (this.c > 1.0F) this.c = 1.0F;
/*    */     
/* 34 */     if (!this.onGround && this.h < 1.0F) this.h = 1.0F; 
/* 35 */     this.h = (float)(this.h * 0.9D);
/*    */     
/* 37 */     if (!this.onGround && this.motY < 0.0D) {
/* 38 */       this.motY *= 0.6D;
/*    */     }
/*    */     
/* 41 */     this.b += this.h * 2.0F;
/*    */     
/* 43 */     if (!this.world.isStatic && --this.i <= 0) {
/* 44 */       this.world.makeSound(this, "mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/* 45 */       b(Item.EGG.id, 1);
/* 46 */       this.i = this.random.nextInt(6000) + 6000;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a(float paramFloat) {}
/*    */ 
/*    */   
/* 55 */   public void b(NBTTagCompound paramNBTTagCompound) { super.b(paramNBTTagCompound); }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public void a(NBTTagCompound paramNBTTagCompound) { super.a(paramNBTTagCompound); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   protected String g() { return "mob.chicken"; }
/*    */ 
/*    */ 
/*    */   
/* 67 */   protected String h() { return "mob.chickenhurt"; }
/*    */ 
/*    */ 
/*    */   
/* 71 */   protected String i() { return "mob.chickenhurt"; }
/*    */ 
/*    */ 
/*    */   
/* 75 */   protected int j() { return Item.FEATHER.id; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityChicken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */