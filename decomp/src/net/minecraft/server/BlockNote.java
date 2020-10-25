/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockNote
/*    */   extends BlockContainer
/*    */ {
/* 11 */   public BlockNote(int paramInt) { super(paramInt, 74, Material.WOOD); }
/*    */ 
/*    */ 
/*    */   
/* 15 */   public int a(int paramInt) { return this.textureId; }
/*    */ 
/*    */   
/*    */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 19 */     if (paramInt4 > 0 && Block.byId[paramInt4].isPowerSource()) {
/* 20 */       boolean bool = paramWorld.isBlockPowered(paramInt1, paramInt2, paramInt3);
/* 21 */       TileEntityNote tileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 22 */       if (tileEntityNote.b != bool) {
/* 23 */         if (bool) {
/* 24 */           tileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */         }
/* 26 */         tileEntityNote.b = bool;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 32 */     if (paramWorld.isStatic) return true; 
/* 33 */     TileEntityNote tileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 34 */     tileEntityNote.a();
/* 35 */     tileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
/* 36 */     return true;
/*    */   }
/*    */   
/*    */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 40 */     if (paramWorld.isStatic)
/* 41 */       return;  TileEntityNote tileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 42 */     tileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */   
/* 46 */   protected TileEntity a_() { return new TileEntityNote(); }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 50 */     float f = (float)Math.pow(2.0D, (paramInt5 - 12) / 12.0D);
/*    */     
/* 52 */     String str = "harp";
/* 53 */     if (paramInt4 == 1) str = "bd"; 
/* 54 */     if (paramInt4 == 2) str = "snare"; 
/* 55 */     if (paramInt4 == 3) str = "hat"; 
/* 56 */     if (paramInt4 == 4) str = "bassattack";
/*    */     
/* 58 */     paramWorld.makeSound(paramInt1 + 0.5D, paramInt2 + 0.5D, paramInt3 + 0.5D, "note." + str, 3.0F, f);
/* 59 */     paramWorld.a("note", paramInt1 + 0.5D, paramInt2 + 1.2D, paramInt3 + 0.5D, paramInt5 / 24.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockNote.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */