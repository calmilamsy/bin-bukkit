/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMobSpawner
/*    */   extends BlockContainer
/*    */ {
/* 10 */   protected BlockMobSpawner(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.STONE); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   protected TileEntity a_() { return new TileEntityMobSpawner(); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public int a(int paramInt, Random paramRandom) { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public int a(Random paramRandom) { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public boolean a() { return false; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockMobSpawner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */