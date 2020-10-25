/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class EntityGiantZombie
/*    */   extends EntityMonster
/*    */ {
/*    */   public EntityGiantZombie(World paramWorld) {
/*  7 */     super(paramWorld);
/*  8 */     this.texture = "/mob/zombie.png";
/*  9 */     this.aE = 0.5F;
/* 10 */     this.damage = 50;
/* 11 */     this.health *= 10;
/* 12 */     this.height *= 6.0F;
/* 13 */     b(this.length * 6.0F, this.width * 6.0F);
/*    */   }
/*    */ 
/*    */   
/* 17 */   protected float a(int paramInt1, int paramInt2, int paramInt3) { return this.world.n(paramInt1, paramInt2, paramInt3) - 0.5F; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityGiantZombie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */