/*     */ package com.avaje.ebeaninternal.server.lib.cron;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CronSchedule
/*     */ {
/*     */   private String schedule;
/*     */   private boolean[] bHours;
/*     */   private boolean[] bMinutes;
/*     */   private boolean[] bMonths;
/*     */   private boolean[] bDaysOfWeek;
/*     */   private boolean[] bDaysOfMonth;
/*     */   
/*  60 */   public CronSchedule(String scheduleLine) { setSchedule(scheduleLine); }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  64 */     if (obj == null) {
/*  65 */       return false;
/*     */     }
/*  67 */     if (obj instanceof CronSchedule) {
/*  68 */       CronSchedule cs = (CronSchedule)obj;
/*  69 */       if (this.schedule.equals(cs.getSchedule())) {
/*  70 */         return true;
/*     */       }
/*     */     } 
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  77 */     hc = CronSchedule.class.getName().hashCode();
/*  78 */     return hc * 31 + this.schedule.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initBooleanArrays() {
/*  83 */     this.bHours = new boolean[24];
/*  84 */     this.bMinutes = new boolean[60];
/*  85 */     this.bMonths = new boolean[12];
/*  86 */     this.bDaysOfWeek = new boolean[7];
/*  87 */     this.bDaysOfMonth = new boolean[31];
/*  88 */     for (int i = 0; i < 60; i++) {
/*  89 */       if (i < 24)
/*  90 */         this.bHours[i] = false; 
/*  91 */       if (i < 60)
/*  92 */         this.bMinutes[i] = false; 
/*  93 */       if (i < 12)
/*  94 */         this.bMonths[i] = false; 
/*  95 */       if (i < 7)
/*  96 */         this.bDaysOfWeek[i] = false; 
/*  97 */       if (i < 31) {
/*  98 */         this.bDaysOfMonth[i] = false;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchedule(String schedule) {
/* 106 */     this.schedule = schedule;
/* 107 */     initBooleanArrays();
/*     */     
/* 109 */     StringTokenizer tokenizer = new StringTokenizer(schedule);
/*     */     
/* 111 */     int numTokens = tokenizer.countTokens();
/* 112 */     for (int i = 0; tokenizer.hasMoreElements(); i++) {
/* 113 */       String token = tokenizer.nextToken();
/* 114 */       switch (i) {
/*     */         case 0:
/* 116 */           parseToken(token, this.bMinutes, false);
/*     */           break;
/*     */         case 1:
/* 119 */           parseToken(token, this.bHours, false);
/*     */           break;
/*     */         case 2:
/* 122 */           parseToken(token, this.bDaysOfMonth, true);
/*     */           break;
/*     */         case 3:
/* 125 */           parseToken(token, this.bMonths, true);
/*     */           break;
/*     */         case 4:
/* 128 */           parseToken(token, this.bDaysOfWeek, false);
/*     */           break;
/*     */       } 
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
/*     */     } 
/* 143 */     if (numTokens < 5) {
/* 144 */       String msg = "The schedule[" + schedule + "] did not contain enough tokens (5 required) [" + numTokens + "].";
/* 145 */       throw new RuntimeException(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseToken(String token, boolean[] arrayBool, boolean bBeginInOne) {
/*     */     try {
/* 152 */       if (token.equals("*")) {
/* 153 */         for (int i = 0; i < arrayBool.length; i++) {
/* 154 */           arrayBool[i] = true;
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 159 */       int index = token.indexOf(",");
/* 160 */       if (index > 0) {
/* 161 */         StringTokenizer tokenizer = new StringTokenizer(token, ",");
/* 162 */         while (tokenizer.hasMoreTokens()) {
/* 163 */           parseToken(tokenizer.nextToken(), arrayBool, bBeginInOne);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 168 */       index = token.indexOf("-");
/* 169 */       if (index > 0) {
/* 170 */         int start = Integer.parseInt(token.substring(0, index));
/* 171 */         int end = Integer.parseInt(token.substring(index + 1));
/*     */         
/* 173 */         if (bBeginInOne) {
/* 174 */           start--;
/* 175 */           end--;
/*     */         } 
/*     */         
/* 178 */         for (int j = start; j <= end; j++) {
/* 179 */           arrayBool[j] = true;
/*     */         }
/*     */         return;
/*     */       } 
/* 183 */       index = token.indexOf("/");
/* 184 */       if (index > 0) {
/* 185 */         int each = Integer.parseInt(token.substring(index + 1)); int j;
/* 186 */         for (j = 0; j < arrayBool.length; j += each)
/* 187 */           arrayBool[j] = true; 
/*     */         return;
/*     */       } 
/* 190 */       int iValue = Integer.parseInt(token);
/* 191 */       if (bBeginInOne) {
/* 192 */         iValue--;
/*     */       }
/* 194 */       arrayBool[iValue] = true;
/*     */       
/*     */       return;
/* 197 */     } catch (Exception e) {
/* 198 */       String msg = "The schedule[" + this.schedule + "] had a problem parsing a token [" + token + "].";
/* 199 */       throw new RuntimeException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   public boolean isScheduledToRunNow(Calendar thisMinute) { return (this.bHours[thisMinute.get(11)] && this.bMinutes[thisMinute.get(12)] && this.bMonths[thisMinute.get(2)] && this.bDaysOfWeek[thisMinute.get(7) - 1] && this.bDaysOfMonth[thisMinute.get(5) - 1]); }
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
/* 221 */   public String getSchedule() { return this.schedule; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\cron\CronSchedule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */