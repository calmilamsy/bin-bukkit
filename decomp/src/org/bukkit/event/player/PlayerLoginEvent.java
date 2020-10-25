/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ 
/*     */ public class PlayerLoginEvent
/*     */   extends PlayerEvent
/*     */ {
/*     */   private Result result;
/*     */   private String message;
/*     */   
/*     */   public PlayerLoginEvent(Player player) {
/*  13 */     super(Event.Type.PLAYER_LOGIN, player);
/*  14 */     this.result = Result.ALLOWED;
/*  15 */     this.message = "";
/*     */   }
/*     */   
/*     */   public PlayerLoginEvent(Event.Type type, Player player, Result result, String message) {
/*  19 */     super(type, player);
/*  20 */     this.result = result;
/*  21 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   public Result getResult() { return this.result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   public void setResult(Result result) { this.result = result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public String getKickMessage() { return this.message; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public void setKickMessage(String message) { this.message = message; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void allow() {
/*  64 */     this.result = Result.ALLOWED;
/*  65 */     this.message = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disallow(Result result, String message) {
/*  75 */     this.result = result;
/*  76 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Result
/*     */   {
/*  87 */     ALLOWED,
/*     */ 
/*     */ 
/*     */     
/*  91 */     KICK_FULL,
/*     */ 
/*     */ 
/*     */     
/*  95 */     KICK_BANNED,
/*     */ 
/*     */ 
/*     */     
/*  99 */     KICK_WHITELIST,
/*     */ 
/*     */ 
/*     */     
/* 103 */     KICK_OTHER;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerLoginEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */