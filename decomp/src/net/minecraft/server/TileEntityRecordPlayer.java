/*    */ package net.minecraft.server;
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
/*    */ public class TileEntityRecordPlayer
/*    */   extends TileEntity
/*    */ {
/*    */   public int a;
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 18 */     super.a(paramNBTTagCompound);
/* 19 */     this.a = paramNBTTagCompound.e("Record");
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 24 */     super.b(paramNBTTagCompound);
/* 25 */     if (this.a > 0) paramNBTTagCompound.a("Record", this.a); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntityRecordPlayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */