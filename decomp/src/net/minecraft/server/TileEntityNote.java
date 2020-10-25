/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityNote
/*    */   extends TileEntity
/*    */ {
/*  9 */   public byte note = 0;
/*    */   public boolean b = false;
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 13 */     super.b(paramNBTTagCompound);
/* 14 */     paramNBTTagCompound.a("note", this.note);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 18 */     super.a(paramNBTTagCompound);
/* 19 */     this.note = paramNBTTagCompound.c("note");
/* 20 */     if (this.note < 0) this.note = 0; 
/* 21 */     if (this.note > 24) this.note = 24; 
/*    */   }
/*    */   
/*    */   public void a() {
/* 25 */     this.note = (byte)((this.note + 1) % 25);
/* 26 */     update();
/*    */   }
/*    */   
/*    */   public void play(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 30 */     if (paramWorld.getMaterial(paramInt1, paramInt2 + true, paramInt3) != Material.AIR)
/*    */       return; 
/* 32 */     Material material = paramWorld.getMaterial(paramInt1, paramInt2 - 1, paramInt3);
/*    */     
/* 34 */     byte b1 = 0;
/* 35 */     if (material == Material.STONE) b1 = 1; 
/* 36 */     if (material == Material.SAND) b1 = 2; 
/* 37 */     if (material == Material.SHATTERABLE) b1 = 3; 
/* 38 */     if (material == Material.WOOD) b1 = 4;
/*    */     
/* 40 */     paramWorld.playNote(paramInt1, paramInt2, paramInt3, b1, this.note);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntityNote.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */