/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
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
/*     */ public class ResultSetMetaData
/*     */   implements ResultSetMetaData
/*     */ {
/*     */   Field[] fields;
/*     */   boolean useOldAliasBehavior;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   private static int clampedGetLength(Field f) {
/*  41 */     long fieldLength = f.getLength();
/*     */     
/*  43 */     if (fieldLength > 2147483647L) {
/*  44 */       fieldLength = 2147483647L;
/*     */     }
/*     */     
/*  47 */     return (int)fieldLength;
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
/*     */   private static final boolean isDecimalType(int type) {
/*  59 */     switch (type) {
/*     */       case -7:
/*     */       case -6:
/*     */       case -5:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*  70 */         return true;
/*     */     } 
/*     */     
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   public ResultSetMetaData(Field[] fields, boolean useOldAliasBehavior, ExceptionInterceptor exceptionInterceptor) {
/*  77 */     this.useOldAliasBehavior = false;
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
/*  88 */     this.fields = fields;
/*  89 */     this.useOldAliasBehavior = useOldAliasBehavior;
/*  90 */     this.exceptionInterceptor = exceptionInterceptor;
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
/*     */   public String getCatalogName(int column) throws SQLException {
/* 105 */     Field f = getField(column);
/*     */     
/* 107 */     String database = f.getDatabaseName();
/*     */     
/* 109 */     return (database == null) ? "" : database;
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
/*     */   
/*     */   public String getColumnCharacterEncoding(int column) throws SQLException {
/* 126 */     String mysqlName = getColumnCharacterSet(column);
/*     */     
/* 128 */     String javaName = null;
/*     */     
/* 130 */     if (mysqlName != null) {
/* 131 */       javaName = CharsetMapping.getJavaEncodingForMysqlEncoding(mysqlName, null);
/*     */     }
/*     */ 
/*     */     
/* 135 */     return javaName;
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
/* 150 */   public String getColumnCharacterSet(int column) throws SQLException { return getField(column).getCharacterSet(); }
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
/*     */   public String getColumnClassName(int column) throws SQLException {
/* 176 */     Field f = getField(column);
/*     */     
/* 178 */     return getClassNameForJavaType(f.getSQLType(), f.isUnsigned(), f.getMysqlType(), (f.isBinary() || f.isBlob()), f.isOpaqueBinary());
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
/*     */   
/* 194 */   public int getColumnCount() throws SQLException { return this.fields.length; }
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
/*     */   public int getColumnDisplaySize(int column) throws SQLException {
/* 209 */     Field f = getField(column);
/*     */     
/* 211 */     int lengthInBytes = clampedGetLength(f);
/*     */     
/* 213 */     return lengthInBytes / f.getMaxBytesPerCharacter();
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
/*     */   public String getColumnLabel(int column) throws SQLException {
/* 228 */     if (this.useOldAliasBehavior) {
/* 229 */       return getColumnName(column);
/*     */     }
/*     */     
/* 232 */     return getField(column).getColumnLabel();
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
/*     */   public String getColumnName(int column) throws SQLException {
/* 247 */     if (this.useOldAliasBehavior) {
/* 248 */       return getField(column).getName();
/*     */     }
/*     */     
/* 251 */     String name = getField(column).getNameNoAliases();
/*     */     
/* 253 */     if (name != null && name.length() == 0) {
/* 254 */       return getField(column).getName();
/*     */     }
/*     */     
/* 257 */     return name;
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
/*     */ 
/*     */   
/* 274 */   public int getColumnType(int column) throws SQLException { return getField(column).getSQLType(); }
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
/*     */   public String getColumnTypeName(int column) throws SQLException {
/* 289 */     Field field = getField(column);
/*     */     
/* 291 */     int mysqlType = field.getMysqlType();
/* 292 */     int jdbcType = field.getSQLType();
/*     */     
/* 294 */     switch (mysqlType) {
/*     */       case 16:
/* 296 */         return "BIT";
/*     */       case 0:
/*     */       case 246:
/* 299 */         return field.isUnsigned() ? "DECIMAL UNSIGNED" : "DECIMAL";
/*     */       
/*     */       case 1:
/* 302 */         return field.isUnsigned() ? "TINYINT UNSIGNED" : "TINYINT";
/*     */       
/*     */       case 2:
/* 305 */         return field.isUnsigned() ? "SMALLINT UNSIGNED" : "SMALLINT";
/*     */       
/*     */       case 3:
/* 308 */         return field.isUnsigned() ? "INT UNSIGNED" : "INT";
/*     */       
/*     */       case 4:
/* 311 */         return field.isUnsigned() ? "FLOAT UNSIGNED" : "FLOAT";
/*     */       
/*     */       case 5:
/* 314 */         return field.isUnsigned() ? "DOUBLE UNSIGNED" : "DOUBLE";
/*     */       
/*     */       case 6:
/* 317 */         return "NULL";
/*     */       
/*     */       case 7:
/* 320 */         return "TIMESTAMP";
/*     */       
/*     */       case 8:
/* 323 */         return field.isUnsigned() ? "BIGINT UNSIGNED" : "BIGINT";
/*     */       
/*     */       case 9:
/* 326 */         return field.isUnsigned() ? "MEDIUMINT UNSIGNED" : "MEDIUMINT";
/*     */       
/*     */       case 10:
/* 329 */         return "DATE";
/*     */       
/*     */       case 11:
/* 332 */         return "TIME";
/*     */       
/*     */       case 12:
/* 335 */         return "DATETIME";
/*     */       
/*     */       case 249:
/* 338 */         return "TINYBLOB";
/*     */       
/*     */       case 250:
/* 341 */         return "MEDIUMBLOB";
/*     */       
/*     */       case 251:
/* 344 */         return "LONGBLOB";
/*     */       
/*     */       case 252:
/* 347 */         if (getField(column).isBinary()) {
/* 348 */           return "BLOB";
/*     */         }
/*     */         
/* 351 */         return "TEXT";
/*     */       
/*     */       case 15:
/* 354 */         return "VARCHAR";
/*     */       
/*     */       case 253:
/* 357 */         if (jdbcType == -3) {
/* 358 */           return "VARBINARY";
/*     */         }
/*     */         
/* 361 */         return "VARCHAR";
/*     */       
/*     */       case 254:
/* 364 */         if (jdbcType == -2) {
/* 365 */           return "BINARY";
/*     */         }
/*     */         
/* 368 */         return "CHAR";
/*     */       
/*     */       case 247:
/* 371 */         return "ENUM";
/*     */       
/*     */       case 13:
/* 374 */         return "YEAR";
/*     */       
/*     */       case 248:
/* 377 */         return "SET";
/*     */       
/*     */       case 255:
/* 380 */         return "GEOMETRY";
/*     */     } 
/*     */     
/* 383 */     return "UNKNOWN";
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
/*     */   protected Field getField(int columnIndex) throws SQLException {
/* 399 */     if (columnIndex < 1 || columnIndex > this.fields.length) {
/* 400 */       throw SQLError.createSQLException(Messages.getString("ResultSetMetaData.46"), "S1002", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 404 */     return this.fields[columnIndex - 1];
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
/*     */   public int getPrecision(int column) throws SQLException {
/* 419 */     Field f = getField(column);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     if (isDecimalType(f.getSQLType())) {
/* 426 */       if (f.getDecimals() > 0) {
/* 427 */         return clampedGetLength(f) - 1 + f.getPrecisionAdjustFactor();
/*     */       }
/*     */       
/* 430 */       return clampedGetLength(f) + f.getPrecisionAdjustFactor();
/*     */     } 
/*     */     
/* 433 */     switch (f.getMysqlType()) {
/*     */       case 249:
/*     */       case 250:
/*     */       case 251:
/*     */       case 252:
/* 438 */         return clampedGetLength(f);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     return clampedGetLength(f) / f.getMaxBytesPerCharacter();
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
/*     */   
/*     */   public int getScale(int column) throws SQLException {
/* 462 */     Field f = getField(column);
/*     */     
/* 464 */     if (isDecimalType(f.getSQLType())) {
/* 465 */       return f.getDecimals();
/*     */     }
/*     */     
/* 468 */     return 0;
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
/*     */ 
/*     */   
/* 485 */   public String getSchemaName(int column) throws SQLException { return ""; }
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
/*     */   public String getTableName(int column) throws SQLException {
/* 500 */     if (this.useOldAliasBehavior) {
/* 501 */       return getField(column).getTableName();
/*     */     }
/*     */     
/* 504 */     return getField(column).getTableNameNoAliases();
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
/*     */   public boolean isAutoIncrement(int column) {
/* 519 */     Field f = getField(column);
/*     */     
/* 521 */     return f.isAutoIncrement();
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
/*     */   public boolean isCaseSensitive(int column) {
/*     */     String collationName;
/* 536 */     Field field = getField(column);
/*     */     
/* 538 */     int sqlType = field.getSQLType();
/*     */     
/* 540 */     switch (sqlType) {
/*     */       case -7:
/*     */       case -6:
/*     */       case -5:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 91:
/*     */       case 92:
/*     */       case 93:
/* 552 */         return false;
/*     */ 
/*     */       
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 558 */         if (field.isBinary()) {
/* 559 */           return true;
/*     */         }
/*     */         
/* 562 */         collationName = field.getCollation();
/*     */         
/* 564 */         return (collationName != null && !collationName.endsWith("_ci"));
/*     */     } 
/*     */     
/* 567 */     return true;
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
/*     */   
/* 583 */   public boolean isCurrency(int column) { return false; }
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
/* 598 */   public boolean isDefinitelyWritable(int column) { return isWritable(column); }
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
/*     */   public int isNullable(int column) throws SQLException {
/* 613 */     if (!getField(column).isNotNull()) {
/* 614 */       return 1;
/*     */     }
/*     */     
/* 617 */     return 0;
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
/* 632 */   public boolean isReadOnly(int column) { return getField(column).isReadOnly(); }
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
/* 651 */   public boolean isSearchable(int column) { return true; }
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
/*     */   public boolean isSigned(int column) {
/* 666 */     Field f = getField(column);
/* 667 */     int sqlType = f.getSQLType();
/*     */     
/* 669 */     switch (sqlType) {
/*     */       case -6:
/*     */       case -5:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/* 679 */         return !f.isUnsigned();
/*     */       
/*     */       case 91:
/*     */       case 92:
/*     */       case 93:
/* 684 */         return false;
/*     */     } 
/*     */     
/* 687 */     return false;
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
/*     */   
/* 703 */   public boolean isWritable(int column) { return !isReadOnly(column); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 712 */     StringBuffer toStringBuf = new StringBuffer();
/* 713 */     toStringBuf.append(super.toString());
/* 714 */     toStringBuf.append(" - Field level information: ");
/*     */     
/* 716 */     for (int i = 0; i < this.fields.length; i++) {
/* 717 */       toStringBuf.append("\n\t");
/* 718 */       toStringBuf.append(this.fields[i].toString());
/*     */     } 
/*     */     
/* 721 */     return toStringBuf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getClassNameForJavaType(int javaType, boolean isUnsigned, int mysqlTypeIfKnown, boolean isBinaryOrBlob, boolean isOpaqueBinary) {
/* 728 */     switch (javaType) {
/*     */       case -7:
/*     */       case 16:
/* 731 */         return "java.lang.Boolean";
/*     */ 
/*     */       
/*     */       case -6:
/* 735 */         if (isUnsigned) {
/* 736 */           return "java.lang.Integer";
/*     */         }
/*     */         
/* 739 */         return "java.lang.Integer";
/*     */ 
/*     */       
/*     */       case 5:
/* 743 */         if (isUnsigned) {
/* 744 */           return "java.lang.Integer";
/*     */         }
/*     */         
/* 747 */         return "java.lang.Integer";
/*     */ 
/*     */       
/*     */       case 4:
/* 751 */         if (!isUnsigned || mysqlTypeIfKnown == 9)
/*     */         {
/* 753 */           return "java.lang.Integer";
/*     */         }
/*     */         
/* 756 */         return "java.lang.Long";
/*     */ 
/*     */       
/*     */       case -5:
/* 760 */         if (!isUnsigned) {
/* 761 */           return "java.lang.Long";
/*     */         }
/*     */         
/* 764 */         return "java.math.BigInteger";
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 768 */         return "java.math.BigDecimal";
/*     */       
/*     */       case 7:
/* 771 */         return "java.lang.Float";
/*     */       
/*     */       case 6:
/*     */       case 8:
/* 775 */         return "java.lang.Double";
/*     */       
/*     */       case -1:
/*     */       case 1:
/*     */       case 12:
/* 780 */         if (!isOpaqueBinary) {
/* 781 */           return "java.lang.String";
/*     */         }
/*     */         
/* 784 */         return "[B";
/*     */ 
/*     */       
/*     */       case -4:
/*     */       case -3:
/*     */       case -2:
/* 790 */         if (mysqlTypeIfKnown == 255)
/* 791 */           return "[B"; 
/* 792 */         if (isBinaryOrBlob) {
/* 793 */           return "[B";
/*     */         }
/* 795 */         return "java.lang.String";
/*     */ 
/*     */       
/*     */       case 91:
/* 799 */         return "java.sql.Date";
/*     */       
/*     */       case 92:
/* 802 */         return "java.sql.Time";
/*     */       
/*     */       case 93:
/* 805 */         return "java.sql.Timestamp";
/*     */     } 
/*     */     
/* 808 */     return "java.lang.Object";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 830 */   public boolean isWrapperFor(Class iface) throws SQLException { return iface.isInstance(this); }
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
/*     */   public Object unwrap(Class iface) throws SQLException {
/*     */     try {
/* 851 */       return Util.cast(iface, this);
/* 852 */     } catch (ClassCastException cce) {
/* 853 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ResultSetMetaData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */