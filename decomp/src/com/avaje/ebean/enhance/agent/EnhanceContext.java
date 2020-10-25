/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnhanceContext
/*     */ {
/*  13 */   private static final Logger logger = Logger.getLogger(EnhanceContext.class.getName());
/*     */   
/*     */   private final IgnoreClassHelper ignoreClassHelper;
/*     */   
/*     */   private final boolean subclassing;
/*     */   
/*     */   private final HashMap<String, String> agentArgsMap;
/*     */   
/*     */   private final boolean readOnly;
/*     */   
/*     */   private final boolean transientInternalFields;
/*     */   
/*     */   private final boolean checkNullManyFields;
/*     */   
/*     */   private final ClassMetaReader reader;
/*     */   
/*     */   private final ClassBytesReader classBytesReader;
/*     */   private PrintStream logout;
/*     */   private int logLevel;
/*     */   private HashMap<String, ClassMeta> map;
/*     */   
/*     */   public EnhanceContext(ClassBytesReader classBytesReader, boolean subclassing, String agentArgs) {
/*  35 */     this.map = new HashMap();
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
/*  48 */     this.ignoreClassHelper = new IgnoreClassHelper(agentArgs);
/*  49 */     this.subclassing = subclassing;
/*  50 */     this.agentArgsMap = ArgParser.parse(agentArgs);
/*     */     
/*  52 */     this.logout = System.out;
/*     */     
/*  54 */     this.classBytesReader = classBytesReader;
/*  55 */     this.reader = new ClassMetaReader(this);
/*     */     
/*  57 */     String debugValue = (String)this.agentArgsMap.get("debug");
/*  58 */     if (debugValue != null) {
/*     */       try {
/*  60 */         this.logLevel = Integer.parseInt(debugValue);
/*  61 */       } catch (NumberFormatException e) {
/*  62 */         String msg = "Agent debug argument [" + debugValue + "] is not an int?";
/*  63 */         logger.log(Level.WARNING, msg);
/*     */       } 
/*     */     }
/*     */     
/*  67 */     this.readOnly = getPropertyBoolean("readonly", false);
/*  68 */     this.transientInternalFields = getPropertyBoolean("transientInternalFields", false);
/*  69 */     this.checkNullManyFields = getPropertyBoolean("checkNullManyFields", true);
/*     */   }
/*     */ 
/*     */   
/*  73 */   public byte[] getClassBytes(String className, ClassLoader classLoader) { return this.classBytesReader.getClassBytes(className, classLoader); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String getProperty(String key) { return (String)this.agentArgsMap.get(key.toLowerCase()); }
/*     */ 
/*     */   
/*     */   public boolean getPropertyBoolean(String key, boolean dflt) {
/*  84 */     String s = getProperty(key);
/*  85 */     if (s == null) {
/*  86 */       return dflt;
/*     */     }
/*  88 */     return s.trim().equalsIgnoreCase("true");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public boolean isIgnoreClass(String className) { return this.ignoreClassHelper.isIgnoreClass(className); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public void setLogout(PrintStream logout) { this.logout = logout; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public ClassMeta createClassMeta() { return new ClassMeta(this, this.subclassing, this.logLevel, this.logout); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassMeta getSuperMeta(String superClassName, ClassLoader classLoader) {
/*     */     try {
/* 124 */       if (isIgnoreClass(superClassName)) {
/* 125 */         return null;
/*     */       }
/* 127 */       return this.reader.get(false, superClassName, classLoader);
/*     */     }
/* 129 */     catch (ClassNotFoundException e) {
/* 130 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassMeta getInterfaceMeta(String interfaceClassName, ClassLoader classLoader) {
/*     */     try {
/* 143 */       if (isIgnoreClass(interfaceClassName)) {
/* 144 */         return null;
/*     */       }
/* 146 */       return this.reader.get(true, interfaceClassName, classLoader);
/*     */     }
/* 148 */     catch (ClassNotFoundException e) {
/* 149 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 154 */   public void addClassMeta(ClassMeta meta) { this.map.put(meta.getClassName(), meta); }
/*     */ 
/*     */ 
/*     */   
/* 158 */   public ClassMeta get(String className) { return (ClassMeta)this.map.get(className); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(int level, String msg) {
/* 165 */     if (this.logLevel >= level) {
/* 166 */       this.logout.println(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   public void log(String className, String msg) {
/* 171 */     if (className != null) {
/* 172 */       msg = "cls: " + className + "  msg: " + msg;
/*     */     }
/* 174 */     this.logout.println("transform> " + msg);
/*     */   }
/*     */ 
/*     */   
/* 178 */   public boolean isLog(int level) { return (this.logLevel >= level); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   public void log(Throwable e) { e.printStackTrace(this.logout); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public int getLogLevel() { return this.logLevel; }
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
/* 204 */   public boolean isReadOnly() { return this.readOnly; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 211 */   public boolean isTransientInternalFields() { return this.transientInternalFields; }
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
/* 222 */   public boolean isCheckNullManyFields() { return this.checkNullManyFields; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\EnhanceContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */