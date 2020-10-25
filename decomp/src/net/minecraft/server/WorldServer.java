/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.BlockChangeDelegate;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.generator.CustomChunkGenerator;
/*     */ import org.bukkit.craftbukkit.generator.NetherChunkGenerator;
/*     */ import org.bukkit.craftbukkit.generator.NormalChunkGenerator;
/*     */ import org.bukkit.craftbukkit.generator.SkyLandsChunkGenerator;
/*     */ import org.bukkit.entity.LightningStrike;
/*     */ import org.bukkit.event.weather.LightningStrikeEvent;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ 
/*     */ public class WorldServer
/*     */   extends World
/*     */   implements BlockChangeDelegate
/*     */ {
/*     */   public ChunkProviderServer chunkProviderServer;
/*     */   public boolean weirdIsOpCache = false;
/*     */   public boolean canSave;
/*     */   public final MinecraftServer server;
/*  23 */   private EntityList G = new EntityList();
/*     */   public final int dimension;
/*     */   
/*     */   public WorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, long j, World.Environment env, ChunkGenerator gen) {
/*  27 */     super(idatamanager, s, j, WorldProvider.byDimension(env.getId()), gen, env);
/*  28 */     this.server = minecraftserver;
/*     */     
/*  30 */     this.dimension = i;
/*  31 */     this.pvpMode = minecraftserver.pvpMode;
/*  32 */     this.manager = new PlayerManager(minecraftserver, this.dimension, minecraftserver.propertyManager.getInt("view-distance", 10));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityTracker tracker;
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerManager manager;
/*     */ 
/*     */ 
/*     */   
/*     */   public void entityJoinedWorld(Entity entity, boolean flag) {
/*  47 */     if (entity.passenger == null || !(entity.passenger instanceof EntityHuman)) {
/*  48 */       super.entityJoinedWorld(entity, flag);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  53 */   public void vehicleEnteredWorld(Entity entity, boolean flag) { super.entityJoinedWorld(entity, flag); }
/*     */   
/*     */   protected IChunkProvider b() {
/*     */     NormalChunkGenerator normalChunkGenerator;
/*  57 */     IChunkLoader ichunkloader = this.w.a(this.worldProvider);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     if (this.generator != null) {
/*  63 */       normalChunkGenerator = new CustomChunkGenerator(this, getSeed(), this.generator);
/*  64 */     } else if (this.worldProvider instanceof WorldProviderHell) {
/*  65 */       normalChunkGenerator = new NetherChunkGenerator(this, getSeed());
/*  66 */     } else if (this.worldProvider instanceof WorldProviderSky) {
/*  67 */       normalChunkGenerator = new SkyLandsChunkGenerator(this, getSeed());
/*     */     } else {
/*  69 */       normalChunkGenerator = new NormalChunkGenerator(this, getSeed());
/*     */     } 
/*     */     
/*  72 */     this.chunkProviderServer = new ChunkProviderServer(this, ichunkloader, normalChunkGenerator);
/*     */ 
/*     */     
/*  75 */     return this.chunkProviderServer;
/*     */   }
/*     */   
/*     */   public List getTileEntities(int i, int j, int k, int l, int i1, int j1) {
/*  79 */     ArrayList arraylist = new ArrayList();
/*     */     
/*  81 */     for (int k1 = 0; k1 < this.c.size(); k1++) {
/*  82 */       TileEntity tileentity = (TileEntity)this.c.get(k1);
/*     */       
/*  84 */       if (tileentity.x >= i && tileentity.y >= j && tileentity.z >= k && tileentity.x < l && tileentity.y < i1 && tileentity.z < j1) {
/*  85 */         arraylist.add(tileentity);
/*     */       }
/*     */     } 
/*     */     
/*  89 */     return arraylist;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman, int i, int j, int k) {
/*  93 */     int l = (int)MathHelper.abs((i - this.worldData.c()));
/*  94 */     int i1 = (int)MathHelper.abs((k - this.worldData.e()));
/*     */     
/*  96 */     if (l > i1) {
/*  97 */       i1 = l;
/*     */     }
/*     */ 
/*     */     
/* 101 */     return (i1 > getServer().getSpawnRadius() || this.server.serverConfigurationManager.isOp(entityhuman.name));
/*     */   }
/*     */   
/*     */   protected void c(Entity entity) {
/* 105 */     super.c(entity);
/* 106 */     this.G.a(entity.id, entity);
/*     */   }
/*     */   
/*     */   protected void d(Entity entity) {
/* 110 */     super.d(entity);
/* 111 */     this.G.d(entity.id);
/*     */   }
/*     */ 
/*     */   
/* 115 */   public Entity getEntity(int i) { return (Entity)this.G.a(i); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean strikeLightning(Entity entity) {
/* 120 */     LightningStrikeEvent lightning = new LightningStrikeEvent(getWorld(), (LightningStrike)entity.getBukkitEntity());
/* 121 */     getServer().getPluginManager().callEvent(lightning);
/*     */     
/* 123 */     if (lightning.isCancelled()) {
/* 124 */       return false;
/*     */     }
/*     */     
/* 127 */     if (super.strikeLightning(entity)) {
/* 128 */       this.server.serverConfigurationManager.sendPacketNearby(entity.locX, entity.locY, entity.locZ, 512.0D, this.dimension, new Packet71Weather(entity));
/*     */       
/* 130 */       return true;
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(Entity entity, byte b0) {
/* 137 */     Packet38EntityStatus packet38entitystatus = new Packet38EntityStatus(entity.id, b0);
/*     */ 
/*     */     
/* 140 */     this.server.getTracker(this.dimension).sendPacketToEntity(entity, packet38entitystatus);
/*     */   }
/*     */ 
/*     */   
/*     */   public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag) {
/* 145 */     Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag);
/*     */     
/* 147 */     if (explosion.wasCanceled) {
/* 148 */       return explosion;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     this.server.serverConfigurationManager.sendPacketNearby(d0, d1, d2, 64.0D, this.dimension, new Packet60Explosion(d0, d1, d2, f, explosion.blocks));
/*     */     
/* 158 */     return explosion;
/*     */   }
/*     */   
/*     */   public void playNote(int i, int j, int k, int l, int i1) {
/* 162 */     super.playNote(i, j, k, l, i1);
/*     */     
/* 164 */     this.server.serverConfigurationManager.sendPacketNearby(i, j, k, 64.0D, this.dimension, new Packet54PlayNoteBlock(i, j, k, l, i1));
/*     */   }
/*     */ 
/*     */   
/* 168 */   public void saveLevel() { this.w.e(); }
/*     */ 
/*     */   
/*     */   protected void i() {
/* 172 */     boolean flag = v();
/*     */     
/* 174 */     super.i();
/* 175 */     if (flag != v())
/*     */     {
/* 177 */       for (int i = 0; i < this.players.size(); i++) {
/* 178 */         if (((EntityPlayer)this.players.get(i)).world == this)
/* 179 */           ((EntityPlayer)this.players.get(i)).netServerHandler.sendPacket(new Packet70Bed(flag ? 2 : 1)); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */