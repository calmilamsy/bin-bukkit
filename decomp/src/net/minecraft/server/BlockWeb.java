/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWeb
/*    */   extends Block
/*    */ {
/* 14 */   public BlockWeb(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.WEB); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 19 */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) { paramEntity.bf = true; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public boolean a() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public boolean b() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public int a(int paramInt, Random paramRandom) { return Item.STRING.id; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockWeb.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */