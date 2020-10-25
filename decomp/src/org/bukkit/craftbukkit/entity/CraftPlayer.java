/*     */ package org.bukkit.craftbukkit.entity;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.EntityPlayer;
/*     */ import net.minecraft.server.Packet131;
/*     */ import net.minecraft.server.Packet200Statistic;
/*     */ import net.minecraft.server.Packet3Chat;
/*     */ import net.minecraft.server.Packet51MapChunk;
/*     */ import net.minecraft.server.Packet53BlockChange;
/*     */ import net.minecraft.server.Packet54PlayNoteBlock;
/*     */ import net.minecraft.server.Packet61;
/*     */ import net.minecraft.server.Packet6SpawnPosition;
/*     */ import net.minecraft.server.WorldServer;
/*     */ import org.bukkit.Achievement;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Instrument;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Note;
/*     */ import org.bukkit.Statistic;
/*     */ import org.bukkit.craftbukkit.CraftServer;
/*     */ import org.bukkit.craftbukkit.CraftWorld;
/*     */ import org.bukkit.craftbukkit.map.CraftMapView;
/*     */ import org.bukkit.craftbukkit.map.RenderData;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.map.MapView;
/*     */ 
/*     */ public class CraftPlayer extends CraftHumanEntity implements Player {
/*  33 */   public CraftPlayer(CraftServer server, EntityPlayer entity) { super(server, entity); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public boolean isOp() { return this.server.getHandle().isOp(getName()); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOp(boolean value) {
/*  43 */     if (value == isOp())
/*     */       return; 
/*  45 */     if (value) {
/*  46 */       this.server.getHandle().e(getName());
/*     */     } else {
/*  48 */       this.server.getHandle().f(getName());
/*     */     } 
/*     */     
/*  51 */     this.perm.recalculatePermissions();
/*     */   }
/*     */ 
/*     */   
/*  55 */   public boolean isPlayer() { return true; }
/*     */ 
/*     */   
/*     */   public boolean isOnline() {
/*  59 */     for (Object obj : (this.server.getHandle()).players) {
/*  60 */       EntityPlayer player = (EntityPlayer)obj;
/*  61 */       if (player.name.equalsIgnoreCase(getName())) {
/*  62 */         return true;
/*     */       }
/*     */     } 
/*  65 */     return false;
/*     */   }
/*     */   
/*     */   public InetSocketAddress getAddress() {
/*  69 */     SocketAddress addr = (getHandle()).netServerHandler.networkManager.getSocketAddress();
/*  70 */     if (addr instanceof InetSocketAddress) {
/*  71 */       return (InetSocketAddress)addr;
/*     */     }
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public EntityPlayer getHandle() { return (EntityPlayer)this.entity; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public double getEyeHeight() { return getEyeHeight(false); }
/*     */ 
/*     */   
/*     */   public double getEyeHeight(boolean ignoreSneaking) {
/*  87 */     if (ignoreSneaking) {
/*  88 */       return 1.62D;
/*     */     }
/*  90 */     if (isSneaking()) {
/*  91 */       return 1.42D;
/*     */     }
/*  93 */     return 1.62D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHandle(EntityPlayer entity) {
/*  99 */     setHandle(entity);
/* 100 */     this.entity = entity;
/*     */   }
/*     */ 
/*     */   
/* 104 */   public void sendRawMessage(String message) { (getHandle()).netServerHandler.sendPacket(new Packet3Chat(message)); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public void sendMessage(String message) { sendRawMessage(message); }
/*     */ 
/*     */ 
/*     */   
/* 112 */   public String getDisplayName() { return (getHandle()).displayName; }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public void setDisplayName(String name) { (getHandle()).displayName = name; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public String toString() { return "CraftPlayer{name=" + getName() + '}'; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 126 */     if (obj == null) {
/* 127 */       return false;
/*     */     }
/* 129 */     if (getClass() != obj.getClass()) {
/* 130 */       return false;
/*     */     }
/* 132 */     CraftPlayer other = (CraftPlayer)obj;
/* 133 */     if ((getName() == null) ? (other.getName() != null) : !getName().equals(other.getName())) {
/* 134 */       return false;
/*     */     }
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     hash = 5;
/* 142 */     return 97 * hash + ((getName() != null) ? getName().hashCode() : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public void kickPlayer(String message) { (getHandle()).netServerHandler.disconnect((message == null) ? "" : message); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public void setCompassTarget(Location loc) { (getHandle()).netServerHandler.sendPacket(new Packet6SpawnPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())); }
/*     */ 
/*     */ 
/*     */   
/* 156 */   public Location getCompassTarget() { return (getHandle()).compassTarget; }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public void chat(String msg) { (getHandle()).netServerHandler.chat(msg); }
/*     */ 
/*     */ 
/*     */   
/* 164 */   public boolean performCommand(String command) { return this.server.dispatchCommand(this, command); }
/*     */ 
/*     */ 
/*     */   
/* 168 */   public void playNote(Location loc, byte instrument, byte note) { (getHandle()).netServerHandler.sendPacket(new Packet54PlayNoteBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), instrument, note)); }
/*     */ 
/*     */ 
/*     */   
/* 172 */   public void playNote(Location loc, Instrument instrument, Note note) { (getHandle()).netServerHandler.sendPacket(new Packet54PlayNoteBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), instrument.getType(), note.getId())); }
/*     */ 
/*     */   
/*     */   public void playEffect(Location loc, Effect effect, int data) {
/* 176 */     int packetData = effect.getId();
/* 177 */     Packet61 packet = new Packet61(packetData, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), data);
/* 178 */     (getHandle()).netServerHandler.sendPacket(packet);
/*     */   }
/*     */ 
/*     */   
/* 182 */   public void sendBlockChange(Location loc, Material material, byte data) { sendBlockChange(loc, material.getId(), data); }
/*     */ 
/*     */   
/*     */   public void sendBlockChange(Location loc, int material, byte data) {
/* 186 */     Packet53BlockChange packet = new Packet53BlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), ((CraftWorld)loc.getWorld()).getHandle());
/*     */     
/* 188 */     packet.material = material;
/* 189 */     packet.data = data;
/* 190 */     (getHandle()).netServerHandler.sendPacket(packet);
/*     */   }
/*     */   
/*     */   public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
/* 194 */     int x = loc.getBlockX();
/* 195 */     int y = loc.getBlockY();
/* 196 */     int z = loc.getBlockZ();
/*     */     
/* 198 */     int cx = x >> 4;
/* 199 */     int cz = z >> 4;
/*     */     
/* 201 */     if (sx <= 0 || sy <= 0 || sz <= 0) {
/* 202 */       return false;
/*     */     }
/*     */     
/* 205 */     if (x + sx - 1 >> 4 != cx || z + sz - 1 >> 4 != cz || y < 0 || y + sy > 128) {
/* 206 */       return false;
/*     */     }
/*     */     
/* 209 */     if (data.length != sx * sy * sz * 5 / 2) {
/* 210 */       return false;
/*     */     }
/*     */     
/* 213 */     Packet51MapChunk packet = new Packet51MapChunk(x, y, z, sx, sy, sz, data);
/*     */     
/* 215 */     (getHandle()).netServerHandler.sendPacket(packet);
/*     */     
/* 217 */     return true;
/*     */   }
/*     */   
/*     */   public void sendMap(MapView map) {
/* 221 */     RenderData data = ((CraftMapView)map).render(this);
/* 222 */     for (int x = 0; x < 128; x++) {
/* 223 */       byte[] bytes = new byte[131];
/* 224 */       bytes[1] = (byte)x;
/* 225 */       for (int y = 0; y < 128; y++) {
/* 226 */         bytes[y + 3] = data.buffer[y * 128 + x];
/*     */       }
/* 228 */       Packet131 packet = new Packet131((short)Material.MAP.getId(), map.getId(), bytes);
/* 229 */       (getHandle()).netServerHandler.sendPacket(packet);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean teleport(Location location) {
/* 236 */     Location from = getLocation();
/*     */     
/* 238 */     Location to = location;
/*     */     
/* 240 */     PlayerTeleportEvent event = new PlayerTeleportEvent(this, from, to);
/* 241 */     this.server.getPluginManager().callEvent(event);
/*     */     
/* 243 */     if (event.isCancelled() == true) {
/* 244 */       return false;
/*     */     }
/*     */     
/* 247 */     from = event.getFrom();
/*     */     
/* 249 */     to = event.getTo();
/*     */     
/* 251 */     WorldServer fromWorld = ((CraftWorld)from.getWorld()).getHandle();
/* 252 */     WorldServer toWorld = ((CraftWorld)to.getWorld()).getHandle();
/*     */     
/* 254 */     EntityPlayer entity = getHandle();
/*     */ 
/*     */     
/* 257 */     if (fromWorld == toWorld) {
/* 258 */       entity.netServerHandler.teleport(to);
/*     */     } else {
/* 260 */       this.server.getHandle().moveToWorld(entity, toWorld.dimension, to);
/*     */     } 
/* 262 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 266 */   public void setSneaking(boolean sneak) { getHandle().setSneak(sneak); }
/*     */ 
/*     */ 
/*     */   
/* 270 */   public boolean isSneaking() { return getHandle().isSneaking(); }
/*     */ 
/*     */ 
/*     */   
/* 274 */   public void loadData() { (this.server.getHandle()).playerFileData.b(getHandle()); }
/*     */ 
/*     */ 
/*     */   
/* 278 */   public void saveData() { (this.server.getHandle()).playerFileData.a(getHandle()); }
/*     */ 
/*     */ 
/*     */   
/* 282 */   public void updateInventory() { getHandle().updateInventory((getHandle()).activeContainer); }
/*     */ 
/*     */   
/*     */   public void setSleepingIgnored(boolean isSleeping) {
/* 286 */     (getHandle()).fauxSleeping = isSleeping;
/* 287 */     ((CraftWorld)getWorld()).getHandle().checkSleepStatus();
/*     */   }
/*     */ 
/*     */   
/* 291 */   public boolean isSleepingIgnored() { return (getHandle()).fauxSleeping; }
/*     */ 
/*     */ 
/*     */   
/* 295 */   public void awardAchievement(Achievement achievement) { sendStatistic(achievement.getId(), 1); }
/*     */ 
/*     */ 
/*     */   
/* 299 */   public void incrementStatistic(Statistic statistic) { incrementStatistic(statistic, 1); }
/*     */ 
/*     */ 
/*     */   
/* 303 */   public void incrementStatistic(Statistic statistic, int amount) { sendStatistic(statistic.getId(), amount); }
/*     */ 
/*     */ 
/*     */   
/* 307 */   public void incrementStatistic(Statistic statistic, Material material) { incrementStatistic(statistic, material, 1); }
/*     */ 
/*     */   
/*     */   public void incrementStatistic(Statistic statistic, Material material, int amount) {
/* 311 */     if (!statistic.isSubstatistic()) {
/* 312 */       throw new IllegalArgumentException("Given statistic is not a substatistic");
/*     */     }
/* 314 */     if (statistic.isBlock() != material.isBlock()) {
/* 315 */       throw new IllegalArgumentException("Given material is not valid for this substatistic");
/*     */     }
/*     */     
/* 318 */     int mat = material.getId();
/*     */     
/* 320 */     if (!material.isBlock()) {
/* 321 */       mat -= 255;
/*     */     }
/*     */     
/* 324 */     sendStatistic(statistic.getId() + mat, amount);
/*     */   }
/*     */   
/*     */   private void sendStatistic(int id, int amount) {
/* 328 */     while (amount > 127) {
/* 329 */       sendStatistic(id, 127);
/* 330 */       amount -= 127;
/*     */     } 
/*     */     
/* 333 */     (getHandle()).netServerHandler.sendPacket(new Packet200Statistic(id, amount));
/*     */   }
/*     */   
/*     */   public void setPlayerTime(long time, boolean relative) {
/* 337 */     (getHandle()).timeOffset = time;
/* 338 */     (getHandle()).relativeTime = relative;
/*     */   }
/*     */ 
/*     */   
/* 342 */   public long getPlayerTimeOffset() { return (getHandle()).timeOffset; }
/*     */ 
/*     */ 
/*     */   
/* 346 */   public long getPlayerTime() { return getHandle().getPlayerTime(); }
/*     */ 
/*     */ 
/*     */   
/* 350 */   public boolean isPlayerTimeRelative() { return (getHandle()).relativeTime; }
/*     */ 
/*     */ 
/*     */   
/* 354 */   public void resetPlayerTime() { setPlayerTime(0L, true); }
/*     */ 
/*     */ 
/*     */   
/* 358 */   public boolean isBanned() { return (this.server.getHandle()).banByName.contains(getName().toLowerCase()); }
/*     */ 
/*     */   
/*     */   public void setBanned(boolean value) {
/* 362 */     if (value) {
/* 363 */       this.server.getHandle().a(getName().toLowerCase());
/*     */     } else {
/* 365 */       this.server.getHandle().b(getName().toLowerCase());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 370 */   public boolean isWhitelisted() { return this.server.getHandle().e().contains(getName().toLowerCase()); }
/*     */ 
/*     */   
/*     */   public void setWhitelisted(boolean value) {
/* 374 */     if (value) {
/* 375 */       this.server.getHandle().k(getName().toLowerCase());
/*     */     } else {
/* 377 */       this.server.getHandle().l(getName().toLowerCase());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\entity\CraftPlayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */