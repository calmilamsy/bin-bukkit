/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
/*     */ import com.avaje.ebean.config.dbplatform.DbType;
/*     */ import com.avaje.ebean.config.dbplatform.DbTypeMap;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DdlGenContext
/*     */ {
/*     */   private final StringWriter stringWriter;
/*     */   private final DbTypeMap dbTypeMap;
/*     */   private final DbDdlSyntax ddlSyntax;
/*     */   private final String newLine;
/*     */   private final List<String> contentBuffer;
/*     */   private Set<String> intersectionTables;
/*     */   
/*     */   public DdlGenContext(DatabasePlatform dbPlatform, NamingConvention namingConvention) {
/*  30 */     this.stringWriter = new StringWriter();
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
/*  50 */     this.contentBuffer = new ArrayList();
/*     */     
/*  52 */     this.intersectionTables = new HashSet();
/*     */     
/*  54 */     this.intersectionTablesCreateDdl = new ArrayList();
/*  55 */     this.intersectionTablesFkDdl = new ArrayList();
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
/*  69 */     this.dbPlatform = dbPlatform;
/*  70 */     this.dbTypeMap = dbPlatform.getDbTypeMap();
/*  71 */     this.ddlSyntax = dbPlatform.getDbDdlSyntax();
/*  72 */     this.newLine = this.ddlSyntax.getNewLine();
/*  73 */     this.namingConvention = namingConvention;
/*     */   }
/*     */   private List<String> intersectionTablesCreateDdl; private List<String> intersectionTablesFkDdl; private final DatabasePlatform dbPlatform;
/*     */   private final NamingConvention namingConvention;
/*     */   private int fkCount;
/*     */   private int ixCount;
/*     */   
/*  80 */   public DatabasePlatform getDbPlatform() { return this.dbPlatform; }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public boolean isProcessIntersectionTable(String tableName) { return this.intersectionTables.add(tableName); }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void addCreateIntersectionTable(String createTableDdl) { this.intersectionTablesCreateDdl.add(createTableDdl); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void addIntersectionTableFk(String intTableFk) { this.intersectionTablesFkDdl.add(intTableFk); }
/*     */ 
/*     */   
/*     */   public void addIntersectionCreateTables() {
/*  96 */     for (String intTableCreate : this.intersectionTablesCreateDdl) {
/*  97 */       write(this.newLine);
/*  98 */       write(intTableCreate);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addIntersectionFkeys() {
/* 103 */     write(this.newLine);
/* 104 */     write(this.newLine);
/* 105 */     for (String intTableFk : this.intersectionTablesFkDdl) {
/* 106 */       write(this.newLine);
/* 107 */       write(intTableFk);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public String getContent() { return this.stringWriter.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public DbTypeMap getDbTypeMap() { return this.dbTypeMap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public DbDdlSyntax getDdlSyntax() { return this.ddlSyntax; }
/*     */ 
/*     */   
/*     */   public String getColumnDefn(BeanProperty p) {
/* 134 */     DbType dbType = getDbType(p);
/* 135 */     return p.renderDbType(dbType);
/*     */   }
/*     */ 
/*     */   
/*     */   private DbType getDbType(BeanProperty p) {
/* 140 */     ScalarType<?> scalarType = p.getScalarType();
/* 141 */     if (scalarType == null) {
/* 142 */       throw new RuntimeException("No scalarType for " + p.getFullBeanName());
/*     */     }
/*     */     
/* 145 */     if (p.isDbEncrypted()) {
/* 146 */       return this.dbTypeMap.get(p.getDbEncryptedType());
/*     */     }
/*     */     
/* 149 */     int jdbcType = scalarType.getJdbcType();
/* 150 */     if (p.isLob() && jdbcType == 12)
/*     */     {
/*     */       
/* 153 */       jdbcType = 2005;
/*     */     }
/* 155 */     return this.dbTypeMap.get(jdbcType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DdlGenContext write(String content, int minWidth) {
/* 162 */     content = pad(content, minWidth);
/*     */     
/* 164 */     this.contentBuffer.add(content);
/*     */     
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public DdlGenContext write(String content) { return write(content, 0); }
/*     */ 
/*     */   
/*     */   public DdlGenContext writeNewLine() {
/* 178 */     write(this.newLine);
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DdlGenContext removeLast() {
/* 186 */     if (!this.contentBuffer.isEmpty()) {
/* 187 */       this.contentBuffer.remove(this.contentBuffer.size() - 1);
/*     */     } else {
/* 189 */       throw new RuntimeException("No lastContent to remove?");
/*     */     } 
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DdlGenContext flush() {
/* 198 */     if (!this.contentBuffer.isEmpty()) {
/* 199 */       for (String s : this.contentBuffer) {
/*     */         
/* 201 */         if (s != null) {
/* 202 */           this.stringWriter.write(s);
/*     */         }
/*     */       } 
/* 205 */       this.contentBuffer.clear();
/*     */     } 
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private String padding(int length) {
/* 212 */     StringBuffer sb = new StringBuffer(length);
/* 213 */     for (int i = 0; i < length; i++) {
/* 214 */       sb.append(" ");
/*     */     }
/* 216 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String pad(String content, int minWidth) {
/* 220 */     if (minWidth > 0 && content.length() < minWidth) {
/* 221 */       int padding = minWidth - content.length();
/* 222 */       return content + padding(padding);
/*     */     } 
/* 224 */     return content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public NamingConvention getNamingConvention() { return this.namingConvention; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public int incrementFkCount() { return ++this.fkCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public int incrementIxCount() { return ++this.ixCount; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\DdlGenContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */