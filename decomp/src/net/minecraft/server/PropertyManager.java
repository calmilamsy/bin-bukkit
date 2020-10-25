/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.util.Properties;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import joptsimple.OptionSet;
/*    */ 
/*    */ public class PropertyManager {
/* 12 */   public static Logger a = Logger.getLogger("Minecraft");
/* 13 */   public Properties properties; private File c; private OptionSet options; public PropertyManager(File file1) { this.properties = new Properties();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     this.options = null; this.c = file1; if (file1.exists()) { try { this.properties.load(new FileInputStream(file1)); } catch (Exception exception) { a.log(Level.WARNING, "Failed to load " + file1, exception); a(); }
/*    */        }
/*    */     else { a.log(Level.WARNING, file1 + " does not exist"); a(); }
/* 35 */      } public PropertyManager(OptionSet options) { this((File)options.valueOf("config"));
/*    */     
/* 37 */     this.options = options; }
/*    */ 
/*    */   
/*    */   private <T> T getOverride(String name, T value) {
/* 41 */     if (this.options != null && this.options.has(name)) {
/* 42 */       return (T)this.options.valueOf(name);
/*    */     }
/*    */     
/* 45 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a() {
/* 50 */     a.log(Level.INFO, "Generating new properties file");
/* 51 */     savePropertiesFile();
/*    */   }
/*    */   
/*    */   public void savePropertiesFile() {
/*    */     try {
/* 56 */       this.properties.store(new FileOutputStream(this.c), "Minecraft server properties");
/* 57 */     } catch (Exception exception) {
/* 58 */       a.log(Level.WARNING, "Failed to save " + this.c, exception);
/* 59 */       a();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getString(String s, String s1) {
/* 64 */     if (!this.properties.containsKey(s)) {
/* 65 */       s1 = (String)getOverride(s, s1);
/* 66 */       this.properties.setProperty(s, s1);
/* 67 */       savePropertiesFile();
/*    */     } 
/*    */     
/* 70 */     return (String)getOverride(s, this.properties.getProperty(s, s1));
/*    */   }
/*    */   
/*    */   public int getInt(String s, int i) {
/*    */     try {
/* 75 */       return ((Integer)getOverride(s, Integer.valueOf(Integer.parseInt(getString(s, "" + i))))).intValue();
/* 76 */     } catch (Exception exception) {
/* 77 */       i = ((Integer)getOverride(s, Integer.valueOf(i))).intValue();
/* 78 */       this.properties.setProperty(s, "" + i);
/* 79 */       return i;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean getBoolean(String s, boolean flag) {
/*    */     try {
/* 85 */       return ((Boolean)getOverride(s, Boolean.valueOf(Boolean.parseBoolean(getString(s, "" + flag))))).booleanValue();
/* 86 */     } catch (Exception exception) {
/* 87 */       flag = ((Boolean)getOverride(s, Boolean.valueOf(flag))).booleanValue();
/* 88 */       this.properties.setProperty(s, "" + flag);
/* 89 */       return flag;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(String s, boolean flag) {
/* 94 */     flag = ((Boolean)getOverride(s, Boolean.valueOf(flag))).booleanValue();
/* 95 */     this.properties.setProperty(s, "" + flag);
/* 96 */     savePropertiesFile();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PropertyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */