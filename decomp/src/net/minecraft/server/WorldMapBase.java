/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldMapBase
/*    */ {
/*    */   public final String a;
/*    */   private boolean b;
/*    */   
/* 10 */   public WorldMapBase(String paramString) { this.a = paramString; }
/*    */ 
/*    */   
/*    */   public abstract void a(NBTTagCompound paramNBTTagCompound);
/*    */ 
/*    */   
/*    */   public abstract void b(NBTTagCompound paramNBTTagCompound);
/*    */   
/* 18 */   public void a() { a(true); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public void a(boolean paramBoolean) { this.b = paramBoolean; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public boolean b() { return this.b; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldMapBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */