/*     */ package org.bukkit.util.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.Yaml;
/*     */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*     */ import org.yaml.snakeyaml.reader.UnicodeReader;
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
/*     */ public class Configuration
/*     */   extends ConfigurationNode
/*     */ {
/*     */   private Yaml yaml;
/*     */   private File file;
/*  59 */   private String header = null;
/*     */   
/*     */   public Configuration(File file) {
/*  62 */     super(new HashMap());
/*     */     
/*  64 */     DumperOptions options = new DumperOptions();
/*     */     
/*  66 */     options.setIndent(4);
/*  67 */     options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
/*     */     
/*  69 */     this.yaml = new Yaml(new SafeConstructor(), new EmptyNullRepresenter(), options);
/*     */     
/*  71 */     this.file = file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/*  78 */     stream = null;
/*     */     
/*     */     try {
/*  81 */       stream = new FileInputStream(this.file);
/*  82 */       read(this.yaml.load(new UnicodeReader(stream)));
/*  83 */     } catch (IOException e) {
/*  84 */       this.root = new HashMap();
/*  85 */     } catch (ConfigurationException e) {
/*  86 */       this.root = new HashMap();
/*     */     } finally {
/*     */       try {
/*  89 */         if (stream != null) {
/*  90 */           stream.close();
/*     */         }
/*  92 */       } catch (IOException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeader(String... headerLines) {
/* 103 */     StringBuilder header = new StringBuilder();
/*     */     
/* 105 */     for (String line : headerLines) {
/* 106 */       if (header.length() > 0) {
/* 107 */         header.append("\r\n");
/*     */       }
/* 109 */       header.append(line);
/*     */     } 
/*     */     
/* 112 */     setHeader(header.toString());
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
/* 124 */   public void setHeader(String header) { this.header = header; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public String getHeader() { return this.header; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean save() {
/* 143 */     stream = null;
/*     */     
/* 145 */     File parent = this.file.getParentFile();
/*     */     
/* 147 */     if (parent != null) {
/* 148 */       parent.mkdirs();
/*     */     }
/*     */ 
/*     */     
/* 152 */     try { stream = new FileOutputStream(this.file);
/* 153 */       OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
/* 154 */       if (this.header != null) {
/* 155 */         writer.append(this.header);
/* 156 */         writer.append("\r\n");
/*     */       } 
/* 158 */       this.yaml.dump(this.root, writer);
/* 159 */       return true; }
/* 160 */     catch (IOException e)
/*     */     { 
/* 162 */       try { if (stream != null) {
/* 163 */           stream.close();
/*     */         } }
/* 165 */       catch (IOException e) { IOException e; }  } finally { try { if (stream != null) stream.close();  } catch (IOException e) {} }
/*     */ 
/*     */     
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void read(Object input) throws ConfigurationException {
/*     */     try {
/* 174 */       if (null == input) {
/* 175 */         this.root = new HashMap();
/*     */       } else {
/* 177 */         this.root = (Map)input;
/*     */       } 
/* 179 */     } catch (ClassCastException e) {
/* 180 */       throw new ConfigurationException("Root document must be an key-value structure");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public static ConfigurationNode getEmptyNode() { return new ConfigurationNode(new HashMap()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\config\Configuration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */