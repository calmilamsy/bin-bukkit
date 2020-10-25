/*     */ package com.avaje.ebeaninternal.server.transaction.log;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
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
/*     */ public class LogTime
/*     */ {
/*  34 */   private static final String[] sep = { ":", "." };
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static LogTime day = new LogTime();
/*     */   
/*     */   private final String ymd;
/*     */   
/*  42 */   public static LogTime get() { return day; }
/*     */   private final long startMidnight; private final long startTomorrow;
/*     */   
/*     */   public static LogTime nextDay() {
/*  46 */     d = new LogTime();
/*  47 */     day = d;
/*  48 */     return d;
/*     */   }
/*     */   
/*     */   public static LogTime getWithCheck() {
/*  52 */     d = day;
/*  53 */     if (d.isNextDay()) {
/*  54 */       return nextDay();
/*     */     }
/*  56 */     return d;
/*     */   }
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
/*     */   private LogTime() {
/*  72 */     GregorianCalendar now = new GregorianCalendar();
/*     */     
/*  74 */     now.set(11, 0);
/*  75 */     now.set(12, 0);
/*  76 */     now.set(13, 0);
/*  77 */     now.set(14, 0);
/*     */     
/*  79 */     this.startMidnight = now.getTime().getTime();
/*  80 */     this.ymd = getDayDerived(now);
/*     */     
/*  82 */     now.add(5, 1);
/*  83 */     this.startTomorrow = now.getTime().getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public boolean isNextDay() { return (System.currentTimeMillis() >= this.startTomorrow); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public String getYMD() { return this.ymd; }
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
/* 113 */   public String getNow(String[] separators) { return getTimestamp(System.currentTimeMillis(), separators); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTimestamp(long systime) {
/* 119 */     StringBuilder sb = new StringBuilder();
/* 120 */     getTime(sb, systime, this.startMidnight, sep);
/* 121 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTimestamp(long systime, String[] separators) {
/* 126 */     StringBuilder sb = new StringBuilder();
/* 127 */     getTime(sb, systime, this.startMidnight, separators);
/* 128 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public String getNow() { return getNow(sep); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDayDerived(Calendar now) {
/* 146 */     int nowyear = now.get(1);
/* 147 */     int nowmonth = now.get(2);
/* 148 */     int nowday = now.get(5);
/*     */     
/* 150 */     nowmonth++;
/*     */     
/* 152 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 154 */     format(sb, nowyear, 4);
/* 155 */     format(sb, nowmonth, 2);
/* 156 */     format(sb, nowday, 2);
/*     */     
/* 158 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void getTime(StringBuilder sb, long time, long midnight, String[] separator) {
/* 163 */     long rem = time - midnight;
/*     */     
/* 165 */     long millis = rem % 1000L;
/* 166 */     rem /= 1000L;
/* 167 */     long secs = rem % 60L;
/* 168 */     rem /= 60L;
/* 169 */     long mins = rem % 60L;
/* 170 */     rem /= 60L;
/* 171 */     long hrs = rem;
/*     */     
/* 173 */     format(sb, hrs, 2);
/* 174 */     sb.append(separator[0]);
/* 175 */     format(sb, mins, 2);
/* 176 */     sb.append(separator[0]);
/* 177 */     format(sb, secs, 2);
/* 178 */     sb.append(separator[1]);
/* 179 */     format(sb, millis, 3);
/*     */   }
/*     */   
/*     */   private void format(StringBuilder sb, long value, int places) {
/* 183 */     String format = Long.toString(value);
/*     */     
/* 185 */     int pad = places - format.length();
/* 186 */     for (int i = 0; i < pad; i++) {
/* 187 */       sb.append("0");
/*     */     }
/* 189 */     sb.append(format);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\log\LogTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */