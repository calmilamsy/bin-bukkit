/*     */ package com.mysql.jdbc.log;
/*     */ 
/*     */ import com.mysql.jdbc.Util;
/*     */ import com.mysql.jdbc.profiler.ProfilerEvent;
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
/*     */ public class LogUtils
/*     */ {
/*     */   public static final String CALLER_INFORMATION_NOT_AVAILABLE = "Caller information not available";
/*  33 */   private static final String LINE_SEPARATOR = System.getProperty("line.separator");
/*     */ 
/*     */   
/*  36 */   private static final int LINE_SEPARATOR_LENGTH = LINE_SEPARATOR.length();
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object expandProfilerEventIfNecessary(Object possibleProfilerEvent) {
/*  41 */     if (possibleProfilerEvent instanceof ProfilerEvent) {
/*  42 */       StringBuffer msgBuf = new StringBuffer();
/*     */       
/*  44 */       ProfilerEvent evt = (ProfilerEvent)possibleProfilerEvent;
/*     */       
/*  46 */       Throwable locationException = evt.getEventCreationPoint();
/*     */       
/*  48 */       if (locationException == null) {
/*  49 */         locationException = new Throwable();
/*     */       }
/*     */       
/*  52 */       msgBuf.append("Profiler Event: [");
/*     */       
/*  54 */       boolean appendLocationInfo = false;
/*     */       
/*  56 */       switch (evt.getEventType()) {
/*     */         case 4:
/*  58 */           msgBuf.append("EXECUTE");
/*     */           break;
/*     */ 
/*     */         
/*     */         case 5:
/*  63 */           msgBuf.append("FETCH");
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1:
/*  68 */           msgBuf.append("CONSTRUCT");
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/*  73 */           msgBuf.append("PREPARE");
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/*  78 */           msgBuf.append("QUERY");
/*     */           break;
/*     */ 
/*     */         
/*     */         case 0:
/*  83 */           msgBuf.append("WARN");
/*  84 */           appendLocationInfo = true;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 6:
/*  89 */           msgBuf.append("SLOW QUERY");
/*  90 */           appendLocationInfo = false;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/*  95 */           msgBuf.append("UNKNOWN");
/*     */           break;
/*     */       } 
/*  98 */       msgBuf.append("] ");
/*  99 */       msgBuf.append(findCallingClassAndMethod(locationException));
/* 100 */       msgBuf.append(" duration: ");
/* 101 */       msgBuf.append(evt.getEventDuration());
/* 102 */       msgBuf.append(" ");
/* 103 */       msgBuf.append(evt.getDurationUnits());
/* 104 */       msgBuf.append(", connection-id: ");
/* 105 */       msgBuf.append(evt.getConnectionId());
/* 106 */       msgBuf.append(", statement-id: ");
/* 107 */       msgBuf.append(evt.getStatementId());
/* 108 */       msgBuf.append(", resultset-id: ");
/* 109 */       msgBuf.append(evt.getResultSetId());
/*     */       
/* 111 */       String evtMessage = evt.getMessage();
/*     */       
/* 113 */       if (evtMessage != null) {
/* 114 */         msgBuf.append(", message: ");
/* 115 */         msgBuf.append(evtMessage);
/*     */       } 
/*     */       
/* 118 */       if (appendLocationInfo) {
/* 119 */         msgBuf.append("\n\nFull stack trace of location where event occurred:\n\n");
/*     */         
/* 121 */         msgBuf.append(Util.stackTraceToString(locationException));
/* 122 */         msgBuf.append("\n");
/*     */       } 
/*     */       
/* 125 */       return msgBuf;
/*     */     } 
/*     */     
/* 128 */     return possibleProfilerEvent;
/*     */   }
/*     */   
/*     */   public static String findCallingClassAndMethod(Throwable t) {
/* 132 */     String stackTraceAsString = Util.stackTraceToString(t);
/*     */     
/* 134 */     String callingClassAndMethod = "Caller information not available";
/*     */     
/* 136 */     int endInternalMethods = stackTraceAsString.lastIndexOf("com.mysql.jdbc");
/*     */ 
/*     */     
/* 139 */     if (endInternalMethods != -1) {
/* 140 */       int endOfLine = -1;
/* 141 */       int compliancePackage = stackTraceAsString.indexOf("com.mysql.jdbc.compliance", endInternalMethods);
/*     */ 
/*     */       
/* 144 */       if (compliancePackage != -1) {
/* 145 */         endOfLine = compliancePackage - LINE_SEPARATOR_LENGTH;
/*     */       } else {
/* 147 */         endOfLine = stackTraceAsString.indexOf(LINE_SEPARATOR, endInternalMethods);
/*     */       } 
/*     */ 
/*     */       
/* 151 */       if (endOfLine != -1) {
/* 152 */         int nextEndOfLine = stackTraceAsString.indexOf(LINE_SEPARATOR, endOfLine + LINE_SEPARATOR_LENGTH);
/*     */ 
/*     */         
/* 155 */         if (nextEndOfLine != -1) {
/* 156 */           callingClassAndMethod = stackTraceAsString.substring(endOfLine + LINE_SEPARATOR_LENGTH, nextEndOfLine);
/*     */         } else {
/*     */           
/* 159 */           callingClassAndMethod = stackTraceAsString.substring(endOfLine + LINE_SEPARATOR_LENGTH);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 165 */     if (!callingClassAndMethod.startsWith("\tat ") && !callingClassAndMethod.startsWith("at "))
/*     */     {
/* 167 */       return "at " + callingClassAndMethod;
/*     */     }
/*     */     
/* 170 */     return callingClassAndMethod;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\log\LogUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */