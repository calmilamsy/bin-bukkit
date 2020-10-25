/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeHell
/*    */   extends BiomeBase
/*    */ {
/*    */   public BiomeHell() {
/*  9 */     this.s.clear();
/* 10 */     this.t.clear();
/* 11 */     this.u.clear();
/*    */     
/* 13 */     this.s.add(new BiomeMeta(EntityGhast.class, 10));
/* 14 */     this.s.add(new BiomeMeta(EntityPigZombie.class, 10));
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BiomeHell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */