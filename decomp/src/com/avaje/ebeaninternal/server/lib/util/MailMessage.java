/*     */ package com.avaje.ebeaninternal.server.lib.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public class MailMessage
/*     */ {
/*  48 */   HashMap<String, String> header = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   ArrayList<MailAddress> recipientList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   ArrayList<String> bodylines = new ArrayList();
/*     */   
/*     */   MailAddress senderAddress;
/*     */   
/*     */   MailAddress currentRecipient;
/*     */ 
/*     */   
/*  71 */   public void setCurrentRecipient(MailAddress currentRecipient) { this.currentRecipient = currentRecipient; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public MailAddress getCurrentRecipient() { return this.currentRecipient; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public void addRecipient(String alias, String emailAddress) { this.recipientList.add(new MailAddress(alias, emailAddress)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void setSender(String alias, String senderEmail) { this.senderAddress = new MailAddress(alias, senderEmail); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public MailAddress getSender() { return this.senderAddress; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public Iterator<MailAddress> getRecipientList() { return this.recipientList.iterator(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public void addHeader(String key, String val) { this.header.put(key, val); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public void setSubject(String subject) { addHeader("Subject", subject); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public String getSubject() { return getHeader("Subject"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public void addBodyLine(String line) { this.bodylines.add(line); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public Iterator<String> getBodyLines() { return this.bodylines.iterator(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public Iterator<String> getHeaderFields() { return this.header.keySet().iterator(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public String getHeader(String key) { return (String)this.header.get(key); }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     StringBuilder sb = new StringBuilder(100);
/* 159 */     sb.append("Sender: " + this.senderAddress + "\tRecipient: " + this.recipientList + "\n");
/* 160 */     Iterator<String> hi = this.header.keySet().iterator();
/* 161 */     while (hi.hasNext()) {
/* 162 */       String key = (String)hi.next();
/* 163 */       String hline = key + ": " + (String)this.header.get(key) + "\n";
/* 164 */       sb.append(hline);
/*     */     } 
/* 166 */     sb.append("\n");
/* 167 */     Iterator<String> e = this.bodylines.iterator();
/* 168 */     while (e.hasNext()) {
/* 169 */       sb.append((String)e.next()).append("\n");
/*     */     }
/* 171 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\MailMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */