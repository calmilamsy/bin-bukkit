/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import org.bukkit.event.Event;
/*     */ 
/*     */ 
/*     */ public class PlayerPreLoginEvent
/*     */   extends Event
/*     */ {
/*     */   private Result result;
/*     */   private String message;
/*     */   private String name;
/*     */   private InetAddress ipAddress;
/*     */   
/*     */   public PlayerPreLoginEvent(String name, InetAddress ipAddress) {
/*  16 */     super(Event.Type.PLAYER_PRELOGIN);
/*  17 */     this.result = Result.ALLOWED;
/*  18 */     this.message = "";
/*  19 */     this.name = name;
/*  20 */     this.ipAddress = ipAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   public Result getResult() { return this.result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public void setResult(Result result) { this.result = result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public String getKickMessage() { return this.message; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public void setKickMessage(String message) { this.message = message; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void allow() {
/*  63 */     this.result = Result.ALLOWED;
/*  64 */     this.message = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disallow(Result result, String message) {
/*  74 */     this.result = result;
/*  75 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public InetAddress getAddress() { return this.ipAddress; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Result
/*     */   {
/* 104 */     ALLOWED,
/*     */ 
/*     */ 
/*     */     
/* 108 */     KICK_FULL,
/*     */ 
/*     */ 
/*     */     
/* 112 */     KICK_BANNED,
/*     */ 
/*     */ 
/*     */     
/* 116 */     KICK_WHITELIST,
/*     */ 
/*     */ 
/*     */     
/* 120 */     KICK_OTHER;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerPreLoginEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */