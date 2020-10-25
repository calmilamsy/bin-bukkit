/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemRecord
/*    */   extends Item
/*    */ {
/*    */   public final String a;
/*    */   
/*    */   protected ItemRecord(int paramInt, String paramString) {
/* 13 */     super(paramInt);
/* 14 */     this.a = paramString;
/* 15 */     this.maxStackSize = 1;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 19 */     if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) == Block.JUKEBOX.id && paramWorld.getData(paramInt1, paramInt2, paramInt3) == 0) {
/* 20 */       if (paramWorld.isStatic) return true;
/*    */       
/* 22 */       ((BlockJukeBox)Block.JUKEBOX).f(paramWorld, paramInt1, paramInt2, paramInt3, this.id);
/* 23 */       paramWorld.a(null, 1005, paramInt1, paramInt2, paramInt3, this.id);
/* 24 */       paramItemStack.count--;
/* 25 */       return true;
/*    */     } 
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ItemRecord.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */