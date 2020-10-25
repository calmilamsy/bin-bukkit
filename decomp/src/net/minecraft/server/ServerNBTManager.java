/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerNBTManager
/*    */   extends PlayerNBTManager
/*    */ {
/* 17 */   public ServerNBTManager(File paramFile, String paramString, boolean paramBoolean) { super(paramFile, paramString, paramBoolean); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader a(WorldProvider paramWorldProvider) {
/* 23 */     File file = a();
/*    */     
/* 25 */     if (paramWorldProvider instanceof WorldProviderHell) {
/* 26 */       File file1 = new File(file, "DIM-1");
/* 27 */       file1.mkdirs();
/* 28 */       return new ChunkRegionLoader(file1);
/*    */     } 
/*    */     
/* 31 */     return new ChunkRegionLoader(file);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(WorldData paramWorldData, List paramList) {
/* 36 */     paramWorldData.a(19132);
/* 37 */     super.a(paramWorldData, paramList);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public void e() { RegionFileCache.a(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\ServerNBTManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */