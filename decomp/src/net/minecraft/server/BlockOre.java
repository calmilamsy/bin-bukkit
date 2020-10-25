/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockOre
/*    */   extends Block
/*    */ {
/* 11 */   public BlockOre(int paramInt1, int paramInt2) { super(paramInt1, paramInt2, Material.STONE); }
/*    */ 
/*    */   
/*    */   public int a(int paramInt, Random paramRandom) {
/* 15 */     if (this.id == Block.COAL_ORE.id) return Item.COAL.id; 
/* 16 */     if (this.id == Block.DIAMOND_ORE.id) return Item.DIAMOND.id; 
/* 17 */     if (this.id == Block.LAPIS_ORE.id) return Item.INK_SACK.id; 
/* 18 */     return this.id;
/*    */   }
/*    */   
/*    */   public int a(Random paramRandom) {
/* 22 */     if (this.id == Block.LAPIS_ORE.id) return 4 + paramRandom.nextInt(5); 
/* 23 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int a_(int paramInt) {
/* 29 */     if (this.id == Block.LAPIS_ORE.id) return 4; 
/* 30 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockOre.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */