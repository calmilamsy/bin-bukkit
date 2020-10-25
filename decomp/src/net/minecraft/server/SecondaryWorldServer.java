/*    */ package net.minecraft.server;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.generator.ChunkGenerator;
/*    */ 
/*    */ public class SecondaryWorldServer extends WorldServer {
/*    */   public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, long j, WorldServer worldserver, World.Environment env, ChunkGenerator gen) {
/*  8 */     super(minecraftserver, idatamanager, s, i, j, env, gen);
/*    */     
/* 10 */     this.worldMaps = worldserver.worldMaps;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\SecondaryWorldServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */