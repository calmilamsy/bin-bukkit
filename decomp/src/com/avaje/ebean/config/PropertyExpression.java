/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import java.util.logging.Logger;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
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
/*     */ final class PropertyExpression
/*     */ {
/*  19 */   private static final Logger logger = Logger.getLogger(PropertyExpression.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String JAVA_COMP_ENV = "java:comp/env/";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   private static String START = "${";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private static String END = "}";
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
/*     */   static String eval(String val, PropertyMap map) {
/*  47 */     if (val == null) {
/*  48 */       return null;
/*     */     }
/*  50 */     int sp = val.indexOf(START);
/*  51 */     if (sp > -1) {
/*  52 */       int ep = val.indexOf(END, sp + 1);
/*  53 */       if (ep > -1) {
/*  54 */         return eval(val, sp, ep, map);
/*     */       }
/*     */     } 
/*  57 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String evaluateExpression(String exp, PropertyMap map) {
/*  66 */     if (isJndiExpression(exp)) {
/*     */       
/*  68 */       String val = getJndiProperty(exp);
/*  69 */       if (val != null) {
/*  70 */         return val;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  75 */     String val = System.getenv(exp);
/*  76 */     if (val == null)
/*     */     {
/*  78 */       val = System.getProperty(exp);
/*     */     }
/*  80 */     if (val == null && map != null)
/*     */     {
/*  82 */       val = map.get(exp);
/*     */     }
/*     */     
/*  85 */     if (val != null) {
/*  86 */       return val;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     logger.fine("Unable to evaluate expression [" + exp + "]");
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String eval(String val, int sp, int ep, PropertyMap map) {
/* 100 */     StringBuilder sb = new StringBuilder();
/* 101 */     sb.append(val.substring(0, sp));
/*     */     
/* 103 */     String cal = evalExpression(val, sp, ep, map);
/* 104 */     sb.append(cal);
/*     */     
/* 106 */     eval(val, ep + 1, sb, map);
/*     */     
/* 108 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private static void eval(String val, int startPos, StringBuilder sb, PropertyMap map) {
/* 112 */     if (startPos < val.length()) {
/* 113 */       int sp = val.indexOf(START, startPos);
/* 114 */       if (sp > -1) {
/*     */         
/* 116 */         sb.append(val.substring(startPos, sp));
/* 117 */         int ep = val.indexOf(END, sp + 1);
/* 118 */         if (ep > -1) {
/* 119 */           String cal = evalExpression(val, sp, ep, map);
/* 120 */           sb.append(cal);
/* 121 */           eval(val, ep + 1, sb, map);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 127 */     sb.append(val.substring(startPos));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String evalExpression(String val, int sp, int ep, PropertyMap map) {
/* 132 */     String exp = val.substring(sp + START.length(), ep);
/*     */ 
/*     */     
/* 135 */     String evaled = evaluateExpression(exp, map);
/* 136 */     if (evaled != null) {
/* 137 */       return evaled;
/*     */     }
/*     */     
/* 140 */     return START + exp + END;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isJndiExpression(String exp) {
/* 145 */     if (exp.startsWith("JNDI:")) {
/* 146 */       return true;
/*     */     }
/* 148 */     if (exp.startsWith("jndi:")) {
/* 149 */       return true;
/*     */     }
/* 151 */     return false;
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
/*     */   private static String getJndiProperty(String key) {
/*     */     try {
/* 165 */       key = key.substring(5);
/*     */       
/* 167 */       return (String)getJndiObject(key);
/*     */     }
/* 169 */     catch (NamingException ex) {
/* 170 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getJndiObject(String key) throws NamingException {
/* 180 */     InitialContext ctx = new InitialContext();
/* 181 */     return ctx.lookup("java:comp/env/" + key);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\PropertyExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */