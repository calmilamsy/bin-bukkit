/*     */ package com.avaje.ebeaninternal.server.lib.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
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
/*     */ public class MailSender
/*     */   implements Runnable
/*     */ {
/*     */   int traceLevel;
/*     */   Socket sserver;
/*     */   String server;
/*     */   BufferedReader in;
/*  36 */   private static final Logger logger = Logger.getLogger(MailSender.class.getName());
/*     */   public MailSender(String server) {
/*  38 */     this.traceLevel = 0;
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
/*  49 */     this.listener = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.server = server;
/*     */   }
/*     */   OutputStreamWriter out;
/*     */   MailMessage message;
/*     */   MailListener listener;
/*     */   private static final int SMTP_PORT = 25;
/*     */   
/*  64 */   public void setMailListener(MailListener listener) { this.listener = listener; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public void run() { send(this.message); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendInBackground(MailMessage message) {
/*  78 */     this.message = message;
/*  79 */     Thread thread = new Thread(this);
/*  80 */     thread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void send(MailMessage message) {
/*     */     try {
/*  89 */       Iterator<MailAddress> i = message.getRecipientList();
/*  90 */       while (i.hasNext()) {
/*  91 */         MailAddress recipientAddress = (MailAddress)i.next();
/*  92 */         this.sserver = new Socket(this.server, 25);
/*  93 */         send(message, this.sserver, recipientAddress);
/*  94 */         this.sserver.close();
/*     */         
/*  96 */         if (this.listener != null) {
/*  97 */           MailEvent event = new MailEvent(message, null);
/*  98 */           this.listener.handleEvent(event);
/*     */         } 
/*     */       } 
/* 101 */     } catch (Exception ex) {
/* 102 */       if (this.listener != null) {
/* 103 */         MailEvent event = new MailEvent(message, ex);
/* 104 */         this.listener.handleEvent(event);
/*     */       } else {
/* 106 */         logger.log(Level.SEVERE, null, ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void send(MailMessage message, Socket sserver, MailAddress recipientAddress) throws IOException {
/* 114 */     InetAddress localhost = sserver.getLocalAddress();
/* 115 */     String localaddress = localhost.getHostAddress();
/* 116 */     MailAddress sender = message.getSender();
/* 117 */     message.setCurrentRecipient(recipientAddress);
/*     */ 
/*     */     
/* 120 */     if (message.getHeader("Date") == null) {
/* 121 */       message.addHeader("Date", (new Date()).toString());
/*     */     }
/* 123 */     if (message.getHeader("From") == null) {
/* 124 */       message.addHeader("From", sender.getAlias() + " <" + sender.getEmailAddress() + ">");
/*     */     }
/*     */ 
/*     */     
/* 128 */     message.addHeader("To", recipientAddress.getAlias() + " <" + recipientAddress.getEmailAddress() + ">");
/*     */ 
/*     */     
/* 131 */     this.out = new OutputStreamWriter(sserver.getOutputStream());
/* 132 */     this.in = new BufferedReader(new InputStreamReader(sserver.getInputStream()));
/* 133 */     String sintro = readln();
/* 134 */     if (!sintro.startsWith("220")) {
/* 135 */       logger.fine("SmtpSender: intro==" + sintro);
/*     */       
/*     */       return;
/*     */     } 
/* 139 */     writeln("EHLO " + localaddress);
/* 140 */     if (!expect250()) {
/*     */       return;
/*     */     }
/*     */     
/* 144 */     writeln("MAIL FROM:<" + sender.getEmailAddress() + ">");
/* 145 */     if (!expect250()) {
/*     */       return;
/*     */     }
/* 148 */     writeln("RCPT TO:<" + recipientAddress.getEmailAddress() + ">");
/* 149 */     if (!expect250()) {
/*     */       return;
/*     */     }
/* 152 */     writeln("DATA");
/*     */     while (true) {
/* 154 */       String line = readln();
/* 155 */       if (line.startsWith("3"))
/*     */         break; 
/* 157 */       if (!line.startsWith("2")) {
/* 158 */         logger.fine("SmtpSender.send reponse to DATA: " + line);
/*     */         return;
/*     */       } 
/*     */     } 
/* 162 */     Iterator<String> hi = message.getHeaderFields();
/* 163 */     while (hi.hasNext()) {
/* 164 */       String key = (String)hi.next();
/* 165 */       writeln(key + ": " + message.getHeader(key));
/*     */     } 
/* 167 */     writeln("");
/* 168 */     Iterator<String> e = message.getBodyLines();
/* 169 */     while (e.hasNext()) {
/* 170 */       String bline = (String)e.next();
/* 171 */       if (bline.startsWith(".")) {
/* 172 */         bline = "." + bline;
/*     */       }
/* 174 */       writeln(bline);
/*     */     } 
/* 176 */     writeln(".");
/* 177 */     expect250();
/* 178 */     writeln("QUIT");
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean expect250() throws IOException {
/* 183 */     String line = readln();
/* 184 */     if (!line.startsWith("2")) {
/* 185 */       logger.info("SmtpSender.expect250: " + line);
/* 186 */       return false;
/*     */     } 
/* 188 */     return true;
/*     */   }
/*     */   
/*     */   private void writeln(String s) {
/* 192 */     if (this.traceLevel > 2) {
/* 193 */       logger.fine("From client: " + s);
/*     */     }
/* 195 */     this.out.write(s + "\r\n");
/* 196 */     this.out.flush();
/*     */   }
/*     */   
/*     */   private String readln() throws IOException {
/* 200 */     String line = this.in.readLine();
/* 201 */     if (this.traceLevel > 1) {
/* 202 */       logger.fine("From server: " + line);
/*     */     }
/* 204 */     return line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public void setTraceLevel(int traceLevel) { this.traceLevel = traceLevel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalHostName() throws IOException {
/*     */     try {
/* 220 */       InetAddress ipaddress = InetAddress.getLocalHost();
/* 221 */       String localHost = ipaddress.getHostName();
/* 222 */       if (localHost == null) {
/* 223 */         return "localhost";
/*     */       }
/* 225 */       return localHost;
/*     */     }
/* 227 */     catch (UnknownHostException e) {
/* 228 */       return "localhost";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\MailSender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */