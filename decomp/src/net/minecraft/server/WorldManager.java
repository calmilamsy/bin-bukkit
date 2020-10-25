/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class WorldManager
/*    */   implements IWorldAccess {
/*    */   private MinecraftServer server;
/*    */   public WorldServer world;
/*    */   
/*    */   public WorldManager(MinecraftServer minecraftserver, WorldServer worldserver) {
/*  9 */     this.server = minecraftserver;
/* 10 */     this.world = worldserver;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(String s, double d0, double d1, double d2, double d3, double d4, double d5) {}
/*    */   
/* 16 */   public void a(Entity entity) { this.server.getTracker(this.world.dimension).track(entity); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public void b(Entity entity) { this.server.getTracker(this.world.dimension).untrackEntity(entity); }
/*    */ 
/*    */   
/*    */   public void a(String s, double d0, double d1, double d2, float f, float f1) {}
/*    */ 
/*    */   
/*    */   public void a(int i, int j, int k, int l, int i1, int j1) {}
/*    */   
/*    */   public void a() {}
/*    */   
/* 30 */   public void a(int i, int j, int k) { this.server.serverConfigurationManager.flagDirty(i, j, k, this.world.dimension); }
/*    */ 
/*    */   
/*    */   public void a(String s, int i, int j, int k) {}
/*    */ 
/*    */   
/* 36 */   public void a(int i, int j, int k, TileEntity tileentity) { this.server.serverConfigurationManager.a(i, j, k, tileentity); }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public void a(EntityHuman entityhuman, int i, int j, int k, int l, int i1) { this.server.serverConfigurationManager.sendPacketNearby(entityhuman, j, k, l, 64.0D, this.world.dimension, new Packet61(i, j, k, l, i1)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */