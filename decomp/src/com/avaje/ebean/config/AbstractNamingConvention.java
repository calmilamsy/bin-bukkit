/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.Inheritance;
/*     */ import javax.persistence.Table;
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
/*     */ public abstract class AbstractNamingConvention
/*     */   implements NamingConvention
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger(AbstractNamingConvention.class.getName());
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_SEQ_FORMAT = "{table}_seq";
/*     */ 
/*     */   
/*     */   public static final String TABLE_PKCOLUMN_SEQ_FORMAT = "{table}_{column}_seq";
/*     */   
/*     */   private String catalog;
/*     */   
/*     */   private String schema;
/*     */   
/*     */   private String sequenceFormat;
/*     */   
/*     */   protected DatabasePlatform databasePlatform;
/*     */   
/*     */   protected int maxConstraintNameLength;
/*     */   
/*     */   protected int rhsPrefixLength;
/*     */   
/*     */   protected boolean useForeignKeyPrefix;
/*     */ 
/*     */   
/*     */   public AbstractNamingConvention(String sequenceFormat, boolean useForeignKeyPrefix) {
/*  62 */     this.rhsPrefixLength = 3;
/*     */     
/*  64 */     this.useForeignKeyPrefix = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.sequenceFormat = sequenceFormat;
/*  71 */     this.useForeignKeyPrefix = useForeignKeyPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractNamingConvention(String sequenceFormat) {
/*     */     this.rhsPrefixLength = 3;
/*     */     this.useForeignKeyPrefix = true;
/*  81 */     this.sequenceFormat = sequenceFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public AbstractNamingConvention() { this("{table}_seq"); }
/*     */ 
/*     */   
/*     */   public void setDatabasePlatform(DatabasePlatform databasePlatform) {
/*  93 */     this.databasePlatform = databasePlatform;
/*  94 */     this.maxConstraintNameLength = databasePlatform.getDbDdlSyntax().getMaxConstraintNameLength();
/*     */     
/*  96 */     logger.finer("Using maxConstraintNameLength of " + this.maxConstraintNameLength);
/*     */   }
/*     */   
/*     */   public String getSequenceName(String tableName, String pkColumn) {
/* 100 */     String s = this.sequenceFormat.replace("{table}", tableName);
/* 101 */     if (pkColumn == null) {
/* 102 */       pkColumn = "";
/*     */     }
/* 104 */     return s.replace("{column}", pkColumn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public String getCatalog() { return this.catalog; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void setCatalog(String catalog) { this.catalog = catalog; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public String getSchema() { return this.schema; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public void setSchema(String schema) { this.schema = schema; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public String getSequenceFormat() { return this.sequenceFormat; }
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
/* 154 */   public void setSequenceFormat(String sequenceFormat) { this.sequenceFormat = sequenceFormat; }
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
/* 173 */   public boolean isUseForeignKeyPrefix() { return this.useForeignKeyPrefix; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public void setUseForeignKeyPrefix(boolean useForeignKeyPrefix) { this.useForeignKeyPrefix = useForeignKeyPrefix; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract TableName getTableNameByConvention(Class<?> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableName getTableName(Class<?> beanClass) {
/* 202 */     TableName tableName = getTableNameFromAnnotation(beanClass);
/* 203 */     if (tableName == null) {
/*     */       
/* 205 */       Class<?> supCls = beanClass.getSuperclass();
/* 206 */       Inheritance inheritance = (Inheritance)supCls.getAnnotation(Inheritance.class);
/* 207 */       if (inheritance != null)
/*     */       {
/*     */         
/* 210 */         return getTableName(supCls);
/*     */       }
/*     */       
/* 213 */       tableName = getTableNameByConvention(beanClass);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 218 */     String catalog = tableName.getCatalog();
/* 219 */     if (isEmpty(catalog)) {
/* 220 */       catalog = getCatalog();
/*     */     }
/* 222 */     String schema = tableName.getSchema();
/* 223 */     if (isEmpty(schema)) {
/* 224 */       schema = getSchema();
/*     */     }
/* 226 */     return new TableName(catalog, schema, tableName.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public TableName getM2MJoinTableName(TableName lhsTable, TableName rhsTable) {
/* 231 */     StringBuilder buffer = new StringBuilder();
/* 232 */     buffer.append(lhsTable.getName());
/* 233 */     buffer.append("_");
/*     */     
/* 235 */     String rhsTableName = rhsTable.getName();
/* 236 */     if (rhsTableName.indexOf('_') < this.rhsPrefixLength)
/*     */     {
/* 238 */       rhsTableName = rhsTableName.substring(rhsTableName.indexOf('_') + 1);
/*     */     }
/* 240 */     buffer.append(rhsTableName);
/*     */     
/* 242 */     int maxConstraintNameLength = this.databasePlatform.getDbDdlSyntax().getMaxConstraintNameLength();
/*     */ 
/*     */     
/* 245 */     if (buffer.length() > maxConstraintNameLength) {
/* 246 */       buffer.setLength(maxConstraintNameLength);
/*     */     }
/*     */     
/* 249 */     return new TableName(lhsTable.getCatalog(), lhsTable.getSchema(), buffer.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableName getTableNameFromAnnotation(Class<?> beanClass) {
/* 257 */     Table t = findTableAnnotation(beanClass);
/*     */ 
/*     */     
/* 260 */     if (t != null && !isEmpty(t.name()))
/*     */     {
/*     */       
/* 263 */       return new TableName(quoteIdentifiers(t.catalog()), quoteIdentifiers(t.schema()), quoteIdentifiers(t.name()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Table findTableAnnotation(Class<?> cls) {
/* 275 */     if (cls.equals(Object.class)) {
/* 276 */       return null;
/*     */     }
/* 278 */     Table table = (Table)cls.getAnnotation(Table.class);
/* 279 */     if (table != null) {
/* 280 */       return table;
/*     */     }
/* 282 */     return findTableAnnotation(cls.getSuperclass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 290 */   protected String quoteIdentifiers(String s) { return this.databasePlatform.convertQuotedIdentifiers(s); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEmpty(String s) {
/* 297 */     if (s == null || s.trim().length() == 0) {
/* 298 */       return true;
/*     */     }
/* 300 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\AbstractNamingConvention.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */