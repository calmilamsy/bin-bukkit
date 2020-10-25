/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.lib.resource.ResourceContent;
/*     */ import com.avaje.ebeaninternal.server.lib.resource.ResourceSource;
/*     */ import com.avaje.ebeaninternal.server.lib.util.Dnode;
/*     */ import com.avaje.ebeaninternal.server.lib.util.DnodeReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class DeployOrmXml
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(DeployOrmXml.class.getName());
/*     */ 
/*     */   
/*     */   private final HashMap<String, DNativeQuery> nativeQueryCache;
/*     */   
/*     */   private final ArrayList<Dnode> ormXmlList;
/*     */   
/*     */   private final ResourceSource resSource;
/*     */ 
/*     */   
/*     */   public DeployOrmXml(ResourceSource resSource) {
/*  55 */     this.resSource = resSource;
/*  56 */     this.nativeQueryCache = new HashMap();
/*  57 */     this.ormXmlList = findAllOrmXml();
/*     */ 
/*     */     
/*  60 */     initialiseNativeQueries();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialiseNativeQueries() {
/*  67 */     for (Dnode ormXml : this.ormXmlList) {
/*  68 */       initialiseNativeQueries(ormXml);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialiseNativeQueries(Dnode ormXml) {
/*  77 */     Dnode entityMappings = ormXml.find("entity-mappings");
/*  78 */     if (entityMappings != null) {
/*  79 */       List<Dnode> nq = entityMappings.findAll("named-native-query", 1);
/*  80 */       for (int i = 0; i < nq.size(); i++) {
/*  81 */         Dnode nqNode = (Dnode)nq.get(i);
/*  82 */         Dnode nqQueryNode = nqNode.find("query");
/*  83 */         if (nqQueryNode != null) {
/*  84 */           String queryContent = nqQueryNode.getNodeContent();
/*  85 */           String queryName = nqNode.getAttribute("name");
/*     */           
/*  87 */           if (queryName != null && queryContent != null) {
/*  88 */             DNativeQuery query = new DNativeQuery(queryContent);
/*  89 */             this.nativeQueryCache.put(queryName, query);
/*     */           } 
/*     */         } 
/*     */       } 
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
/* 103 */   public DNativeQuery getNativeQuery(String name) { return (DNativeQuery)this.nativeQueryCache.get(name); }
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Dnode> findAllOrmXml() {
/* 108 */     ArrayList<Dnode> ormXmlList = new ArrayList<Dnode>();
/*     */ 
/*     */     
/* 111 */     String defaultFile = "orm.xml";
/* 112 */     readOrmXml(defaultFile, ormXmlList);
/*     */     
/* 114 */     if (!ormXmlList.isEmpty()) {
/* 115 */       StringBuilder sb = new StringBuilder();
/* 116 */       for (Dnode ox : ormXmlList) {
/* 117 */         sb.append(", ").append(ox.getAttribute("ebean.filename"));
/*     */       }
/* 119 */       String loadedFiles = sb.toString().substring(2);
/* 120 */       logger.info("Deployment xml [" + loadedFiles + "]  loaded.");
/*     */     } 
/*     */     
/* 123 */     return ormXmlList;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean readOrmXml(String ormXmlName, ArrayList<Dnode> ormXmlList) {
/*     */     try {
/* 129 */       Dnode ormXml = null;
/* 130 */       ResourceContent content = this.resSource.getContent(ormXmlName);
/* 131 */       if (content != null) {
/*     */         
/* 133 */         ormXml = readOrmXml(content.getInputStream());
/*     */       }
/*     */       else {
/*     */         
/* 137 */         ormXml = readOrmXmlFromClasspath(ormXmlName);
/*     */       } 
/*     */       
/* 140 */       if (ormXml != null) {
/* 141 */         ormXml.setAttribute("ebean.filename", ormXmlName);
/* 142 */         ormXmlList.add(ormXml);
/* 143 */         return true;
/*     */       } 
/*     */       
/* 146 */       return false;
/*     */     }
/* 148 */     catch (IOException e) {
/* 149 */       logger.log(Level.SEVERE, "error reading orm xml deployment " + ormXmlName, e);
/* 150 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Dnode readOrmXmlFromClasspath(String ormXmlName) throws IOException {
/* 155 */     InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ormXmlName);
/* 156 */     if (is == null) {
/* 157 */       return null;
/*     */     }
/* 159 */     return readOrmXml(is);
/*     */   }
/*     */ 
/*     */   
/*     */   private Dnode readOrmXml(InputStream in) throws IOException {
/* 164 */     DnodeReader reader = new DnodeReader();
/* 165 */     Dnode ormXml = reader.parseXml(in);
/* 166 */     in.close();
/* 167 */     return ormXml;
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
/*     */   public Dnode findEntityDeploymentXml(String className) throws IOException {
/* 179 */     for (Dnode ormXml : this.ormXmlList) {
/* 180 */       Dnode entityMappings = ormXml.find("entity-mappings");
/*     */       
/* 182 */       List<Dnode> entities = entityMappings.findAll("entity", "class", className, 1);
/* 183 */       if (entities.size() == 1) {
/* 184 */         return (Dnode)entities.get(0);
/*     */       }
/*     */     } 
/*     */     
/* 188 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployOrmXml.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */