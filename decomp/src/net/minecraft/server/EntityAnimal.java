/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityAnimal
/*    */   extends EntityCreature
/*    */   implements IAnimal
/*    */ {
/* 13 */   public EntityAnimal(World paramWorld) { super(paramWorld); }
/*    */ 
/*    */   
/*    */   protected float a(int paramInt1, int paramInt2, int paramInt3) {
/* 17 */     if (this.world.getTypeId(paramInt1, paramInt2 - 1, paramInt3) == Block.GRASS.id) return 10.0F; 
/* 18 */     return this.world.n(paramInt1, paramInt2, paramInt3) - 0.5F;
/*    */   }
/*    */ 
/*    */   
/* 22 */   public void b(NBTTagCompound paramNBTTagCompound) { super.b(paramNBTTagCompound); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public void a(NBTTagCompound paramNBTTagCompound) { super.a(paramNBTTagCompound); }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 30 */     int i = MathHelper.floor(this.locX);
/* 31 */     int j = MathHelper.floor(this.boundingBox.b);
/* 32 */     int k = MathHelper.floor(this.locZ);
/* 33 */     return (this.world.getTypeId(i, j - 1, k) == Block.GRASS.id && this.world.k(i, j, k) > 8 && super.d());
/*    */   }
/*    */ 
/*    */   
/* 37 */   public int e() { return 120; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityAnimal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */