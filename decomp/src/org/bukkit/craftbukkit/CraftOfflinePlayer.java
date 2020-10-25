/*    */ package org.bukkit.craftbukkit;
/*    */ 
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.Server;
/*    */ 
/*    */ public class CraftOfflinePlayer implements OfflinePlayer {
/*    */   private final String name;
/*    */   private final CraftServer server;
/*    */   
/*    */   protected CraftOfflinePlayer(CraftServer server, String name) {
/* 11 */     this.server = server;
/* 12 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/* 16 */   public boolean isOnline() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Server getServer() { return this.server; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public boolean isOp() { return this.server.getHandle().isOp(getName().toLowerCase()); }
/*    */ 
/*    */   
/*    */   public void setOp(boolean value) {
/* 32 */     if (value == isOp())
/*    */       return; 
/* 34 */     if (value) {
/* 35 */       this.server.getHandle().e(getName().toLowerCase());
/*    */     } else {
/* 37 */       this.server.getHandle().f(getName().toLowerCase());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 42 */   public boolean isBanned() { return (this.server.getHandle()).banByName.contains(this.name.toLowerCase()); }
/*    */ 
/*    */   
/*    */   public void setBanned(boolean value) {
/* 46 */     if (value) {
/* 47 */       this.server.getHandle().a(this.name.toLowerCase());
/*    */     } else {
/* 49 */       this.server.getHandle().b(this.name.toLowerCase());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 54 */   public boolean isWhitelisted() { return this.server.getHandle().e().contains(this.name.toLowerCase()); }
/*    */ 
/*    */   
/*    */   public void setWhitelisted(boolean value) {
/* 58 */     if (value) {
/* 59 */       this.server.getHandle().k(this.name.toLowerCase());
/*    */     } else {
/* 61 */       this.server.getHandle().l(this.name.toLowerCase());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\CraftOfflinePlayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */