/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.server.lib.util.MailEvent;
/*     */ import com.avaje.ebeaninternal.server.lib.util.MailListener;
/*     */ import com.avaje.ebeaninternal.server.lib.util.MailMessage;
/*     */ import com.avaje.ebeaninternal.server.lib.util.MailSender;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleAlerter
/*     */   implements DataSourceAlertListener, MailListener
/*     */ {
/*  41 */   private static final Logger logger = Logger.getLogger(SimpleAlerter.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleEvent(MailEvent event) {
/*  55 */     Throwable e = event.getError();
/*  56 */     if (e != null) {
/*  57 */       logger.log(Level.SEVERE, null, e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dataSourceDown(String dataSourceName) {
/*  65 */     String msg = getSubject(true, dataSourceName);
/*  66 */     sendMessage(msg, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dataSourceUp(String dataSourceName) {
/*  73 */     String msg = getSubject(false, dataSourceName);
/*  74 */     sendMessage(msg, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public void warning(String subject, String msg) { sendMessage(subject, msg); }
/*     */ 
/*     */   
/*     */   private String getSubject(boolean isDown, String dsName) {
/*  85 */     String msg = "The DataSource " + dsName;
/*  86 */     if (isDown) {
/*  87 */       msg = msg + " is DOWN!!";
/*     */     } else {
/*  89 */       msg = msg + " is UP.";
/*     */     } 
/*  91 */     return msg;
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendMessage(String subject, String msg) {
/*  96 */     String fromUser = GlobalProperties.get("alert.fromuser", null);
/*  97 */     String fromEmail = GlobalProperties.get("alert.fromemail", null);
/*  98 */     String mailServerName = GlobalProperties.get("alert.mailserver", null);
/*  99 */     String toEmail = GlobalProperties.get("alert.toemail", null);
/*     */     
/* 101 */     if (mailServerName == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 106 */     MailMessage data = new MailMessage();
/* 107 */     data.setSender(fromUser, fromEmail);
/* 108 */     data.addBodyLine(msg);
/* 109 */     data.setSubject(subject);
/*     */     
/* 111 */     String[] toList = toEmail.split(",");
/* 112 */     if (toList.length == 0) {
/* 113 */       throw new RuntimeException("alert.toemail has not been set?");
/*     */     }
/* 115 */     for (int i = 0; i < toList.length; i++) {
/* 116 */       data.addRecipient(null, toList[i].trim());
/*     */     }
/*     */     
/* 119 */     MailSender sender = new MailSender(mailServerName);
/* 120 */     sender.setMailListener(this);
/* 121 */     sender.sendInBackground(data);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\SimpleAlerter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */