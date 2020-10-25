/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DRawSqlSelectColumnsParser
/*     */ {
/*  17 */   private static Logger logger = Logger.getLogger(DRawSqlSelectColumnsParser.class.getName());
/*     */   
/*     */   private String matchDescription;
/*     */   
/*     */   private String searchColumn;
/*     */   
/*     */   private int columnIndex;
/*     */   
/*     */   private int pos;
/*     */   
/*     */   private final int end;
/*     */   
/*     */   private final String sqlSelect;
/*     */   
/*     */   private final List<DRawSqlColumnInfo> columns;
/*     */   
/*     */   private final BeanDescriptor<?> desc;
/*     */   
/*     */   private final NamingConvention namingConvention;
/*     */   private final DRawSqlSelectBuilder parent;
/*     */   private final boolean debug;
/*     */   
/*     */   public DRawSqlSelectColumnsParser(DRawSqlSelectBuilder parent, String sqlSelect) {
/*  40 */     this.columns = new ArrayList();
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
/*  51 */     this.parent = parent;
/*  52 */     this.debug = parent.isDebug();
/*  53 */     this.namingConvention = parent.getNamingConvention();
/*  54 */     this.desc = parent.getBeanDescriptor();
/*  55 */     this.sqlSelect = sqlSelect;
/*  56 */     this.end = sqlSelect.length();
/*     */   }
/*     */   
/*     */   public List<DRawSqlColumnInfo> parse() {
/*  60 */     while (this.pos <= this.end) {
/*  61 */       nextColumnInfo();
/*     */     }
/*  63 */     return this.columns;
/*     */   }
/*     */   
/*     */   private void nextColumnInfo() {
/*  67 */     int start = this.pos;
/*  68 */     nextComma();
/*  69 */     String colInfo = this.sqlSelect.substring(start, this.pos);
/*  70 */     this.pos++;
/*  71 */     colInfo = colInfo.trim();
/*  72 */     int secLastSpace = -1;
/*  73 */     int lastSpace = colInfo.lastIndexOf(' ');
/*  74 */     if (lastSpace > -1) {
/*  75 */       secLastSpace = colInfo.lastIndexOf(' ', lastSpace - 1);
/*     */     }
/*  77 */     String colName = null;
/*  78 */     String colLabel = null;
/*  79 */     if (lastSpace == -1) {
/*     */       
/*  81 */       colName = colInfo;
/*  82 */       colLabel = colName;
/*  83 */     } else if (secLastSpace == -1) {
/*     */       
/*  85 */       colName = colInfo.substring(0, lastSpace);
/*  86 */       colLabel = colInfo.substring(lastSpace + 1);
/*  87 */       if (colName.equals("")) {
/*  88 */         colName = colLabel;
/*     */       }
/*     */     } else {
/*     */       
/*  92 */       String expectedAs = colInfo.substring(secLastSpace + 1, lastSpace);
/*  93 */       if (expectedAs.toLowerCase().equals("as")) {
/*  94 */         colName = colInfo.substring(0, secLastSpace);
/*  95 */         colLabel = colInfo.substring(lastSpace + 1);
/*     */       } else {
/*  97 */         String msg = "Error in " + this.parent.getErrName() + ". ";
/*  98 */         msg = msg + "Expected \"AS\" keyword but got [" + expectedAs + "] in select clause [" + colInfo + "]";
/*     */         
/* 100 */         throw new PersistenceException(msg);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 105 */     BeanProperty prop = findProperty(colLabel);
/* 106 */     if (prop == null) {
/* 107 */       if (this.debug) {
/* 108 */         String msg = "ColumnMapping ... idx[" + this.columnIndex + "] ERROR, no property found to match... column[" + colName + "] label[" + colLabel + "] search[" + this.searchColumn + "]";
/*     */ 
/*     */         
/* 111 */         this.parent.debug(msg);
/*     */       } 
/* 113 */       String msg = "Error in " + this.parent.getErrName() + ". ";
/* 114 */       msg = msg + "No matching bean property for column[" + colName + "] columnLabel[" + colLabel + "] idx[" + this.columnIndex + "] using search[" + this.searchColumn + "] found?";
/*     */       
/* 116 */       logger.log(Level.SEVERE, msg);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 121 */       String msg = null;
/* 122 */       if (this.debug || logger.isLoggable(Level.FINE)) {
/* 123 */         msg = "ColumnMapping ... idx[" + this.columnIndex + "] match column[" + colName + "] label[" + colLabel + "] to property[" + prop + "]" + this.matchDescription;
/*     */       }
/*     */ 
/*     */       
/* 127 */       if (this.debug) {
/* 128 */         this.parent.debug(msg);
/*     */       }
/* 130 */       if (logger.isLoggable(Level.FINE)) {
/* 131 */         logger.fine(msg);
/*     */       }
/*     */       
/* 134 */       DRawSqlColumnInfo info = new DRawSqlColumnInfo(colName, colLabel, prop.getName(), prop.isScalar());
/* 135 */       this.columns.add(info);
/* 136 */       this.columnIndex++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String removeQuotedIdentifierChars(String columnLabel) {
/* 143 */     char c = columnLabel.charAt(0);
/* 144 */     if (Character.isJavaIdentifierStart(c)) {
/* 145 */       return columnLabel;
/*     */     }
/*     */ 
/*     */     
/* 149 */     String result = columnLabel.substring(1, columnLabel.length() - 1);
/*     */     
/* 151 */     String msg = "sql-select trimming quoted identifier from[" + columnLabel + "] to[" + result + "]";
/*     */     
/* 153 */     logger.fine(msg);
/*     */     
/* 155 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanProperty findProperty(String column) {
/* 163 */     this.searchColumn = column;
/* 164 */     int dotPos = this.searchColumn.indexOf(".");
/* 165 */     if (dotPos > -1) {
/* 166 */       this.searchColumn = this.searchColumn.substring(dotPos + 1);
/*     */     }
/*     */     
/* 169 */     this.searchColumn = removeQuotedIdentifierChars(this.searchColumn);
/*     */     
/* 171 */     BeanProperty matchingProp = this.desc.getBeanProperty(this.searchColumn);
/* 172 */     if (matchingProp != null) {
/* 173 */       this.matchDescription = "";
/* 174 */       return matchingProp;
/*     */     } 
/*     */ 
/*     */     
/* 178 */     String propertyName = this.namingConvention.getPropertyFromColumn(this.desc.getBeanType(), this.searchColumn);
/* 179 */     matchingProp = this.desc.getBeanProperty(propertyName);
/* 180 */     if (matchingProp != null) {
/* 181 */       this.matchDescription = " ... using naming convention";
/* 182 */       return matchingProp;
/*     */     } 
/*     */     
/* 185 */     this.matchDescription = " ... by linear search";
/*     */ 
/*     */     
/* 188 */     BeanProperty[] propertiesBase = this.desc.propertiesBaseScalar();
/* 189 */     for (i = 0; i < propertiesBase.length; i++) {
/* 190 */       BeanProperty prop = propertiesBase[i];
/* 191 */       if (isMatch(prop, this.searchColumn)) {
/* 192 */         return prop;
/*     */       }
/*     */     } 
/*     */     
/* 196 */     BeanProperty[] propertiesId = this.desc.propertiesId();
/* 197 */     for (i = 0; i < propertiesId.length; i++) {
/* 198 */       BeanProperty prop = propertiesId[i];
/* 199 */       if (isMatch(prop, this.searchColumn)) {
/* 200 */         return prop;
/*     */       }
/*     */     } 
/*     */     
/* 204 */     BeanPropertyAssocOne[] propertiesAssocOne = this.desc.propertiesOne();
/* 205 */     for (int i = 0; i < propertiesAssocOne.length; i++) {
/* 206 */       BeanProperty prop = propertiesAssocOne[i];
/* 207 */       if (isMatch(prop, this.searchColumn)) {
/* 208 */         return prop;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isMatch(BeanProperty prop, String columnLabel) {
/* 216 */     if (columnLabel.equalsIgnoreCase(prop.getDbColumn())) {
/* 217 */       return true;
/*     */     }
/* 219 */     if (columnLabel.equalsIgnoreCase(prop.getName())) {
/* 220 */       return true;
/*     */     }
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   private int nextComma() {
/* 226 */     boolean inQuote = false;
/* 227 */     while (this.pos < this.end) {
/* 228 */       char c = this.sqlSelect.charAt(this.pos);
/* 229 */       if (c == '\'') {
/* 230 */         inQuote = !inQuote;
/* 231 */       } else if (!inQuote && c == ',') {
/* 232 */         return this.pos;
/*     */       } 
/* 234 */       this.pos++;
/*     */     } 
/* 236 */     return this.pos;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DRawSqlSelectColumnsParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */