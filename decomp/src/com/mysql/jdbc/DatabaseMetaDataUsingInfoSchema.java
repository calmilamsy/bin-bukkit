/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DatabaseMetaDataUsingInfoSchema
/*      */   extends DatabaseMetaData
/*      */ {
/*      */   private boolean hasReferentialConstraintsView;
/*      */   private final boolean hasParametersView;
/*      */   
/*      */   protected DatabaseMetaDataUsingInfoSchema(MySQLConnection connToSet, String databaseToSet) throws SQLException {
/*   44 */     super(connToSet, databaseToSet);
/*      */     
/*   46 */     this.hasReferentialConstraintsView = this.conn.versionMeetsMinimum(5, 1, 10);
/*      */ 
/*      */     
/*   49 */     ResultSet rs = null;
/*      */     
/*      */     try {
/*   52 */       rs = super.getTables("INFORMATION_SCHEMA", null, "PARAMETERS", new String[0]);
/*      */       
/*   54 */       this.hasParametersView = rs.next();
/*      */     } finally {
/*   56 */       if (rs != null) {
/*   57 */         rs.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ResultSet executeMetadataQuery(PreparedStatement pStmt) throws SQLException {
/*   64 */     ResultSet rs = pStmt.executeQuery();
/*   65 */     ((ResultSetInternalMethods)rs).setOwningStatement(null);
/*      */     
/*   67 */     return rs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
/*  108 */     if (columnNamePattern == null) {
/*  109 */       if (this.conn.getNullNamePatternMatchesAll()) {
/*  110 */         columnNamePattern = "%";
/*      */       } else {
/*  112 */         throw SQLError.createSQLException("Column name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  118 */     if (catalog == null && 
/*  119 */       this.conn.getNullCatalogMeansCurrent()) {
/*  120 */       catalog = this.database;
/*      */     }
/*      */ 
/*      */     
/*  124 */     String sql = "SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME,COLUMN_NAME, NULL AS GRANTOR, GRANTEE, PRIVILEGE_TYPE AS PRIVILEGE, IS_GRANTABLE FROM INFORMATION_SCHEMA.COLUMN_PRIVILEGES WHERE TABLE_SCHEMA LIKE ? AND TABLE_NAME =? AND COLUMN_NAME LIKE ? ORDER BY COLUMN_NAME, PRIVILEGE_TYPE";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  131 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/*  134 */       pStmt = prepareMetaDataSafeStatement(sql);
/*      */       
/*  136 */       if (catalog != null) {
/*  137 */         pStmt.setString(1, catalog);
/*      */       } else {
/*  139 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/*  142 */       pStmt.setString(2, table);
/*  143 */       pStmt.setString(3, columnNamePattern);
/*      */       
/*  145 */       ResultSet rs = executeMetadataQuery(pStmt);
/*  146 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(new Field[] { new Field("", "TABLE_CAT", true, 64), new Field("", "TABLE_SCHEM", true, true), new Field("", "TABLE_NAME", true, 64), new Field("", "COLUMN_NAME", true, 64), new Field("", "GRANTOR", true, 77), new Field("", "GRANTEE", true, 77), new Field("", "PRIVILEGE", true, 64), new Field("", "IS_GRANTABLE", true, 3) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  156 */       return rs;
/*      */     } finally {
/*  158 */       if (pStmt != null) {
/*  159 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getColumns(String catalog, String schemaPattern, String tableName, String columnNamePattern) throws SQLException {
/*  210 */     if (columnNamePattern == null) {
/*  211 */       if (this.conn.getNullNamePatternMatchesAll()) {
/*  212 */         columnNamePattern = "%";
/*      */       } else {
/*  214 */         throw SQLError.createSQLException("Column name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  220 */     if (catalog == null && 
/*  221 */       this.conn.getNullCatalogMeansCurrent()) {
/*  222 */       catalog = this.database;
/*      */     }
/*      */ 
/*      */     
/*  226 */     StringBuffer sqlBuf = new StringBuffer("SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM,TABLE_NAME,COLUMN_NAME,");
/*      */ 
/*      */     
/*  229 */     MysqlDefs.appendJdbcTypeMappingQuery(sqlBuf, "DATA_TYPE");
/*      */     
/*  231 */     sqlBuf.append(" AS DATA_TYPE, ");
/*      */     
/*  233 */     if (this.conn.getCapitalizeTypeNames()) {
/*  234 */       sqlBuf.append("UPPER(CASE WHEN LOCATE('unsigned', COLUMN_TYPE) != 0 AND LOCATE('unsigned', DATA_TYPE) = 0 THEN CONCAT(DATA_TYPE, ' unsigned') ELSE DATA_TYPE END) AS TYPE_NAME,");
/*      */     } else {
/*  236 */       sqlBuf.append("CASE WHEN LOCATE('unsigned', COLUMN_TYPE) != 0 AND LOCATE('unsigned', DATA_TYPE) = 0 THEN CONCAT(DATA_TYPE, ' unsigned') ELSE DATA_TYPE END AS TYPE_NAME,");
/*      */     } 
/*      */     
/*  239 */     sqlBuf.append("CASE WHEN LCASE(DATA_TYPE)='date' THEN 10 WHEN LCASE(DATA_TYPE)='time' THEN 8 WHEN LCASE(DATA_TYPE)='datetime' THEN 19 WHEN LCASE(DATA_TYPE)='timestamp' THEN 19 WHEN CHARACTER_MAXIMUM_LENGTH IS NULL THEN NUMERIC_PRECISION WHEN CHARACTER_MAXIMUM_LENGTH > 2147483647 THEN 2147483647 ELSE CHARACTER_MAXIMUM_LENGTH END AS COLUMN_SIZE, " + MysqlIO.getMaxBuf() + " AS BUFFER_LENGTH," + "NUMERIC_SCALE AS DECIMAL_DIGITS," + "10 AS NUM_PREC_RADIX," + "CASE WHEN IS_NULLABLE='NO' THEN " + Character.MIN_VALUE + " ELSE CASE WHEN IS_NULLABLE='YES' THEN " + '\001' + " ELSE " + '\002' + " END END AS NULLABLE," + "COLUMN_COMMENT AS REMARKS," + "COLUMN_DEFAULT AS COLUMN_DEF," + "0 AS SQL_DATA_TYPE," + "0 AS SQL_DATETIME_SUB," + "CASE WHEN CHARACTER_OCTET_LENGTH > " + 2147483647 + " THEN " + 2147483647 + " ELSE CHARACTER_OCTET_LENGTH END AS CHAR_OCTET_LENGTH," + "ORDINAL_POSITION," + "IS_NULLABLE," + "NULL AS SCOPE_CATALOG," + "NULL AS SCOPE_SCHEMA," + "NULL AS SCOPE_TABLE," + "NULL AS SOURCE_DATA_TYPE," + "IF (EXTRA LIKE '%auto_increment%','YES','NO') AS IS_AUTOINCREMENT " + "FROM INFORMATION_SCHEMA.COLUMNS WHERE " + "TABLE_SCHEMA LIKE ? AND " + "TABLE_NAME LIKE ? AND COLUMN_NAME LIKE ? " + "ORDER BY TABLE_SCHEMA, TABLE_NAME, ORDINAL_POSITION");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  263 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/*  266 */       pStmt = prepareMetaDataSafeStatement(sqlBuf.toString());
/*      */       
/*  268 */       if (catalog != null) {
/*  269 */         pStmt.setString(1, catalog);
/*      */       } else {
/*  271 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/*  274 */       pStmt.setString(2, tableName);
/*  275 */       pStmt.setString(3, columnNamePattern);
/*      */       
/*  277 */       ResultSet rs = executeMetadataQuery(pStmt);
/*      */       
/*  279 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(createColumnsFields());
/*  280 */       return rs;
/*      */     } finally {
/*  282 */       if (pStmt != null) {
/*  283 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
/*  358 */     if (primaryTable == null) {
/*  359 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/*  363 */     if (primaryCatalog == null && 
/*  364 */       this.conn.getNullCatalogMeansCurrent()) {
/*  365 */       primaryCatalog = this.database;
/*      */     }
/*      */ 
/*      */     
/*  369 */     if (foreignCatalog == null && 
/*  370 */       this.conn.getNullCatalogMeansCurrent()) {
/*  371 */       foreignCatalog = this.database;
/*      */     }
/*      */ 
/*      */     
/*  375 */     String sql = "SELECT A.REFERENCED_TABLE_SCHEMA AS PKTABLE_CAT,NULL AS PKTABLE_SCHEM,A.REFERENCED_TABLE_NAME AS PKTABLE_NAME,A.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME,A.TABLE_SCHEMA AS FKTABLE_CAT,NULL AS FKTABLE_SCHEM,A.TABLE_NAME AS FKTABLE_NAME, A.COLUMN_NAME AS FKCOLUMN_NAME, A.ORDINAL_POSITION AS KEY_SEQ," + generateUpdateRuleClause() + " AS UPDATE_RULE," + generateDeleteRuleClause() + " AS DELETE_RULE," + "A.CONSTRAINT_NAME AS FK_NAME," + "(SELECT CONSTRAINT_NAME FROM" + " INFORMATION_SCHEMA.TABLE_CONSTRAINTS" + " WHERE TABLE_SCHEMA = A.REFERENCED_TABLE_SCHEMA AND" + " TABLE_NAME = A.REFERENCED_TABLE_NAME AND" + " CONSTRAINT_TYPE IN ('UNIQUE','PRIMARY KEY') LIMIT 1)" + " AS PK_NAME," + '\007' + " AS DEFERRABILITY " + "FROM " + "INFORMATION_SCHEMA.KEY_COLUMN_USAGE A JOIN " + "INFORMATION_SCHEMA.TABLE_CONSTRAINTS B " + "USING (TABLE_SCHEMA, TABLE_NAME, CONSTRAINT_NAME) " + generateOptionalRefContraintsJoin() + "WHERE " + "B.CONSTRAINT_TYPE = 'FOREIGN KEY' " + "AND A.REFERENCED_TABLE_SCHEMA LIKE ? AND A.REFERENCED_TABLE_NAME=? " + "AND A.TABLE_SCHEMA LIKE ? AND A.TABLE_NAME=? " + "ORDER BY " + "A.TABLE_SCHEMA, A.TABLE_NAME, A.ORDINAL_POSITION";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  409 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/*  412 */       pStmt = prepareMetaDataSafeStatement(sql);
/*  413 */       if (primaryCatalog != null) {
/*  414 */         pStmt.setString(1, primaryCatalog);
/*      */       } else {
/*  416 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/*  419 */       pStmt.setString(2, primaryTable);
/*      */       
/*  421 */       if (foreignCatalog != null) {
/*  422 */         pStmt.setString(3, foreignCatalog);
/*      */       } else {
/*  424 */         pStmt.setString(3, "%");
/*      */       } 
/*      */       
/*  427 */       pStmt.setString(4, foreignTable);
/*      */       
/*  429 */       ResultSet rs = executeMetadataQuery(pStmt);
/*  430 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(createFkMetadataFields());
/*      */       
/*  432 */       return rs;
/*      */     } finally {
/*  434 */       if (pStmt != null) {
/*  435 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
/*  503 */     if (table == null) {
/*  504 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/*  508 */     if (catalog == null && 
/*  509 */       this.conn.getNullCatalogMeansCurrent()) {
/*  510 */       catalog = this.database;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  516 */     String sql = "SELECT A.REFERENCED_TABLE_SCHEMA AS PKTABLE_CAT,NULL AS PKTABLE_SCHEM,A.REFERENCED_TABLE_NAME AS PKTABLE_NAME, A.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME, A.TABLE_SCHEMA AS FKTABLE_CAT,NULL AS FKTABLE_SCHEM,A.TABLE_NAME AS FKTABLE_NAME,A.COLUMN_NAME AS FKCOLUMN_NAME, A.ORDINAL_POSITION AS KEY_SEQ," + generateUpdateRuleClause() + " AS UPDATE_RULE," + generateDeleteRuleClause() + " AS DELETE_RULE," + "A.CONSTRAINT_NAME AS FK_NAME," + "(SELECT CONSTRAINT_NAME FROM" + " INFORMATION_SCHEMA.TABLE_CONSTRAINTS" + " WHERE TABLE_SCHEMA = A.REFERENCED_TABLE_SCHEMA AND" + " TABLE_NAME = A.REFERENCED_TABLE_NAME AND" + " CONSTRAINT_TYPE IN ('UNIQUE','PRIMARY KEY') LIMIT 1)" + " AS PK_NAME," + '\007' + " AS DEFERRABILITY " + "FROM " + "INFORMATION_SCHEMA.KEY_COLUMN_USAGE A JOIN " + "INFORMATION_SCHEMA.TABLE_CONSTRAINTS B " + "USING (TABLE_SCHEMA, TABLE_NAME, CONSTRAINT_NAME) " + generateOptionalRefContraintsJoin() + "WHERE " + "B.CONSTRAINT_TYPE = 'FOREIGN KEY' " + "AND A.REFERENCED_TABLE_SCHEMA LIKE ? AND A.REFERENCED_TABLE_NAME=? " + "ORDER BY A.TABLE_SCHEMA, A.TABLE_NAME, A.ORDINAL_POSITION";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  549 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/*  552 */       pStmt = prepareMetaDataSafeStatement(sql);
/*      */       
/*  554 */       if (catalog != null) {
/*  555 */         pStmt.setString(1, catalog);
/*      */       } else {
/*  557 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/*  560 */       pStmt.setString(2, table);
/*      */       
/*  562 */       ResultSet rs = executeMetadataQuery(pStmt);
/*      */       
/*  564 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(createFkMetadataFields());
/*      */       
/*  566 */       return rs;
/*      */     } finally {
/*  568 */       if (pStmt != null) {
/*  569 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  576 */   private String generateOptionalRefContraintsJoin() { return this.hasReferentialConstraintsView ? "JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS R ON (R.CONSTRAINT_NAME = B.CONSTRAINT_NAME AND R.TABLE_NAME = B.TABLE_NAME AND R.CONSTRAINT_SCHEMA = B.TABLE_SCHEMA) " : ""; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  584 */   private String generateDeleteRuleClause() { return this.hasReferentialConstraintsView ? ("CASE WHEN R.DELETE_RULE='CASCADE' THEN " + String.valueOf(0) + " WHEN R.DELETE_RULE='SET NULL' THEN " + String.valueOf(2) + " WHEN R.DELETE_RULE='SET DEFAULT' THEN " + String.valueOf(4) + " WHEN R.DELETE_RULE='RESTRICT' THEN " + String.valueOf(1) + " WHEN R.DELETE_RULE='NO ACTION' THEN " + String.valueOf(3) + " ELSE " + String.valueOf(3) + " END ") : String.valueOf(1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  594 */   private String generateUpdateRuleClause() { return this.hasReferentialConstraintsView ? ("CASE WHEN R.UPDATE_RULE='CASCADE' THEN " + String.valueOf(0) + " WHEN R.UPDATE_RULE='SET NULL' THEN " + String.valueOf(2) + " WHEN R.UPDATE_RULE='SET DEFAULT' THEN " + String.valueOf(4) + " WHEN R.UPDATE_RULE='RESTRICT' THEN " + String.valueOf(1) + " WHEN R.UPDATE_RULE='NO ACTION' THEN " + String.valueOf(3) + " ELSE " + String.valueOf(3) + " END ") : String.valueOf(1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
/*  664 */     if (table == null) {
/*  665 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/*  669 */     if (catalog == null && 
/*  670 */       this.conn.getNullCatalogMeansCurrent()) {
/*  671 */       catalog = this.database;
/*      */     }
/*      */ 
/*      */     
/*  675 */     String sql = "SELECT A.REFERENCED_TABLE_SCHEMA AS PKTABLE_CAT,NULL AS PKTABLE_SCHEM,A.REFERENCED_TABLE_NAME AS PKTABLE_NAME,A.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME,A.TABLE_SCHEMA AS FKTABLE_CAT,NULL AS FKTABLE_SCHEM,A.TABLE_NAME AS FKTABLE_NAME, A.COLUMN_NAME AS FKCOLUMN_NAME, A.ORDINAL_POSITION AS KEY_SEQ," + generateUpdateRuleClause() + " AS UPDATE_RULE," + generateDeleteRuleClause() + " AS DELETE_RULE," + "A.CONSTRAINT_NAME AS FK_NAME," + "(SELECT CONSTRAINT_NAME FROM" + " INFORMATION_SCHEMA.TABLE_CONSTRAINTS" + " WHERE TABLE_SCHEMA = A.REFERENCED_TABLE_SCHEMA AND" + " TABLE_NAME = A.REFERENCED_TABLE_NAME AND" + " CONSTRAINT_TYPE IN ('UNIQUE','PRIMARY KEY') LIMIT 1)" + " AS PK_NAME," + '\007' + " AS DEFERRABILITY " + "FROM " + "INFORMATION_SCHEMA.KEY_COLUMN_USAGE A " + "JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS B USING " + "(CONSTRAINT_NAME, TABLE_NAME) " + generateOptionalRefContraintsJoin() + "WHERE " + "B.CONSTRAINT_TYPE = 'FOREIGN KEY' " + "AND A.TABLE_SCHEMA LIKE ? " + "AND A.TABLE_NAME=? " + "AND A.REFERENCED_TABLE_SCHEMA IS NOT NULL " + "ORDER BY " + "A.REFERENCED_TABLE_SCHEMA, A.REFERENCED_TABLE_NAME, " + "A.ORDINAL_POSITION";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  712 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/*  715 */       pStmt = prepareMetaDataSafeStatement(sql);
/*      */       
/*  717 */       if (catalog != null) {
/*  718 */         pStmt.setString(1, catalog);
/*      */       } else {
/*  720 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/*  723 */       pStmt.setString(2, table);
/*      */       
/*  725 */       ResultSet rs = executeMetadataQuery(pStmt);
/*      */       
/*  727 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(createFkMetadataFields());
/*      */       
/*  729 */       return rs;
/*      */     } finally {
/*  731 */       if (pStmt != null) {
/*  732 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
/*  797 */     StringBuffer sqlBuf = new StringBuffer("SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM,TABLE_NAME,NON_UNIQUE,TABLE_SCHEMA AS INDEX_QUALIFIER,INDEX_NAME,3 AS TYPE,SEQ_IN_INDEX AS ORDINAL_POSITION,COLUMN_NAME,COLLATION AS ASC_OR_DESC,CARDINALITY,NULL AS PAGES,NULL AS FILTER_CONDITION FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA LIKE ? AND TABLE_NAME LIKE ?");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  808 */     if (unique) {
/*  809 */       sqlBuf.append(" AND NON_UNIQUE=0 ");
/*      */     }
/*      */     
/*  812 */     sqlBuf.append("ORDER BY NON_UNIQUE, INDEX_NAME, SEQ_IN_INDEX");
/*      */     
/*  814 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/*  817 */       if (catalog == null && 
/*  818 */         this.conn.getNullCatalogMeansCurrent()) {
/*  819 */         catalog = this.database;
/*      */       }
/*      */ 
/*      */       
/*  823 */       pStmt = prepareMetaDataSafeStatement(sqlBuf.toString());
/*      */       
/*  825 */       if (catalog != null) {
/*  826 */         pStmt.setString(1, catalog);
/*      */       } else {
/*  828 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/*  831 */       pStmt.setString(2, table);
/*      */       
/*  833 */       ResultSet rs = executeMetadataQuery(pStmt);
/*      */       
/*  835 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(createIndexInfoFields());
/*      */       
/*  837 */       return rs;
/*      */     } finally {
/*  839 */       if (pStmt != null) {
/*  840 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
/*  873 */     if (catalog == null && 
/*  874 */       this.conn.getNullCatalogMeansCurrent()) {
/*  875 */       catalog = this.database;
/*      */     }
/*      */ 
/*      */     
/*  879 */     if (table == null) {
/*  880 */       throw SQLError.createSQLException("Table not specified.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/*  884 */     String sql = "SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, COLUMN_NAME, SEQ_IN_INDEX AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA LIKE ? AND TABLE_NAME LIKE ? AND INDEX_NAME='PRIMARY' ORDER BY TABLE_SCHEMA, TABLE_NAME, INDEX_NAME, SEQ_IN_INDEX";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  889 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/*  892 */       pStmt = prepareMetaDataSafeStatement(sql);
/*      */       
/*  894 */       if (catalog != null) {
/*  895 */         pStmt.setString(1, catalog);
/*      */       } else {
/*  897 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/*  900 */       pStmt.setString(2, table);
/*      */       
/*  902 */       ResultSet rs = executeMetadataQuery(pStmt);
/*  903 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(new Field[] { new Field("", "TABLE_CAT", true, 'ÿ'), new Field("", "TABLE_SCHEM", true, false), new Field("", "TABLE_NAME", true, 'ÿ'), new Field("", "COLUMN_NAME", true, 32), new Field("", "KEY_SEQ", 5, 5), new Field("", "PK_NAME", true, 32) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  911 */       return rs;
/*      */     } finally {
/*  913 */       if (pStmt != null) {
/*  914 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
/*  962 */     if (procedureNamePattern == null || procedureNamePattern.length() == 0)
/*      */     {
/*  964 */       if (this.conn.getNullNamePatternMatchesAll()) {
/*  965 */         procedureNamePattern = "%";
/*      */       } else {
/*  967 */         throw SQLError.createSQLException("Procedure name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  973 */     String db = null;
/*      */     
/*  975 */     if (catalog == null) {
/*  976 */       if (this.conn.getNullCatalogMeansCurrent()) {
/*  977 */         db = this.database;
/*      */       }
/*      */     } else {
/*  980 */       db = catalog;
/*      */     } 
/*      */     
/*  983 */     String sql = "SELECT ROUTINE_SCHEMA AS PROCEDURE_CAT, NULL AS PROCEDURE_SCHEM, ROUTINE_NAME AS PROCEDURE_NAME, NULL AS RESERVED_1, NULL AS RESERVED_2, NULL AS RESERVED_3, ROUTINE_COMMENT AS REMARKS, CASE WHEN ROUTINE_TYPE = 'PROCEDURE' THEN 1 WHEN ROUTINE_TYPE='FUNCTION' THEN 2 ELSE 0 END AS PROCEDURE_TYPE FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA LIKE ? AND ROUTINE_NAME LIKE ? ORDER BY ROUTINE_SCHEMA, ROUTINE_NAME";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  996 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/*  999 */       pStmt = prepareMetaDataSafeStatement(sql);
/*      */       
/* 1001 */       if (db != null) {
/* 1002 */         pStmt.setString(1, db);
/*      */       } else {
/* 1004 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/* 1007 */       pStmt.setString(2, procedureNamePattern);
/*      */       
/* 1009 */       ResultSet rs = executeMetadataQuery(pStmt);
/* 1010 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(new Field[] { new Field("", "PROCEDURE_CAT", true, false), new Field("", "PROCEDURE_SCHEM", true, false), new Field("", "PROCEDURE_NAME", true, false), new Field("", "reserved1", true, false), new Field("", "reserved2", true, false), new Field("", "reserved3", true, false), new Field("", "REMARKS", true, false), new Field("", "PROCEDURE_TYPE", 5, false) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1020 */       return rs;
/*      */     } finally {
/* 1022 */       if (pStmt != null) {
/* 1023 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
/* 1126 */     if (!this.conn.versionMeetsMinimum(5, 4, 0)) {
/* 1127 */       return super.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern);
/*      */     }
/*      */ 
/*      */     
/* 1131 */     if (!this.hasParametersView) {
/* 1132 */       return super.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern);
/*      */     }
/*      */     
/* 1135 */     if (functionNamePattern == null || functionNamePattern.length() == 0)
/*      */     {
/* 1137 */       if (this.conn.getNullNamePatternMatchesAll()) {
/* 1138 */         functionNamePattern = "%";
/*      */       } else {
/* 1140 */         throw SQLError.createSQLException("Procedure name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1146 */     String db = null;
/*      */     
/* 1148 */     if (catalog == null) {
/* 1149 */       if (this.conn.getNullCatalogMeansCurrent()) {
/* 1150 */         db = this.database;
/*      */       }
/*      */     } else {
/* 1153 */       db = catalog;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1163 */     StringBuffer sqlBuf = new StringBuffer("SELECT SPECIFIC_SCHEMA AS FUNCTION_CAT, NULL AS `FUNCTION_SCHEM`, SPECIFIC_NAME AS `FUNCTION_NAME`, PARAMETER_NAME AS `COLUMN_NAME`, CASE WHEN PARAMETER_MODE = 'IN' THEN 1 WHEN PARAMETER_MODE='OUT' THEN 3 WHEN PARAMETER_MODE='INOUT' THEN 2 WHEN ORDINAL_POSITION=0 THEN 4 ELSE 0 END AS `COLUMN_TYPE`, ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1175 */     MysqlDefs.appendJdbcTypeMappingQuery(sqlBuf, "DATA_TYPE");
/*      */     
/* 1177 */     sqlBuf.append(" AS `DATA_TYPE`, ");
/*      */ 
/*      */     
/* 1180 */     if (this.conn.getCapitalizeTypeNames()) {
/* 1181 */       sqlBuf.append("UPPER(CASE WHEN LOCATE('unsigned', DATA_TYPE) != 0 AND LOCATE('unsigned', DATA_TYPE) = 0 THEN CONCAT(DATA_TYPE, ' unsigned') ELSE DATA_TYPE END) AS `TYPE_NAME`,");
/*      */     } else {
/* 1183 */       sqlBuf.append("CASE WHEN LOCATE('unsigned', DATA_TYPE) != 0 AND LOCATE('unsigned', DATA_TYPE) = 0 THEN CONCAT(DATA_TYPE, ' unsigned') ELSE DATA_TYPE END AS `TYPE_NAME`,");
/*      */     } 
/*      */ 
/*      */     
/* 1187 */     sqlBuf.append("NUMERIC_PRECISION AS `PRECISION`, ");
/*      */     
/* 1189 */     sqlBuf.append("CASE WHEN LCASE(DATA_TYPE)='date' THEN 10 WHEN LCASE(DATA_TYPE)='time' THEN 8 WHEN LCASE(DATA_TYPE)='datetime' THEN 19 WHEN LCASE(DATA_TYPE)='timestamp' THEN 19 WHEN CHARACTER_MAXIMUM_LENGTH IS NULL THEN NUMERIC_PRECISION WHEN CHARACTER_MAXIMUM_LENGTH > 2147483647 THEN 2147483647 ELSE CHARACTER_MAXIMUM_LENGTH END AS LENGTH, ");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1194 */     sqlBuf.append("NUMERIC_SCALE AS `SCALE`, ");
/*      */     
/* 1196 */     sqlBuf.append("10 AS RADIX,");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1203 */     sqlBuf.append("2 AS `NULLABLE`,  NULL AS `REMARKS`, CHARACTER_OCTET_LENGTH AS `CHAR_OCTET_LENGTH`,  ORDINAL_POSITION, '' AS `IS_NULLABLE`, SPECIFIC_NAME FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA LIKE ? AND SPECIFIC_NAME LIKE ? AND (PARAMETER_NAME LIKE ? OR PARAMETER_NAME IS NULL) AND ROUTINE_TYPE='FUNCTION' ORDER BY SPECIFIC_SCHEMA, SPECIFIC_NAME, ORDINAL_POSITION");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1213 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/* 1216 */       pStmt = prepareMetaDataSafeStatement(sqlBuf.toString());
/*      */       
/* 1218 */       if (db != null) {
/* 1219 */         pStmt.setString(1, db);
/*      */       } else {
/* 1221 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/* 1224 */       pStmt.setString(2, functionNamePattern);
/* 1225 */       pStmt.setString(3, columnNamePattern);
/*      */       
/* 1227 */       ResultSet rs = executeMetadataQuery(pStmt);
/* 1228 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(createFunctionColumnsFields());
/*      */       
/* 1230 */       return rs;
/*      */     } finally {
/* 1232 */       if (pStmt != null) {
/* 1233 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
/* 1304 */     if (!this.conn.versionMeetsMinimum(5, 4, 0)) {
/* 1305 */       return super.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern);
/*      */     }
/*      */ 
/*      */     
/* 1309 */     if (!this.hasParametersView) {
/* 1310 */       return super.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern);
/*      */     }
/*      */     
/* 1313 */     if (procedureNamePattern == null || procedureNamePattern.length() == 0)
/*      */     {
/* 1315 */       if (this.conn.getNullNamePatternMatchesAll()) {
/* 1316 */         procedureNamePattern = "%";
/*      */       } else {
/* 1318 */         throw SQLError.createSQLException("Procedure name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1324 */     String db = null;
/*      */     
/* 1326 */     if (catalog == null) {
/* 1327 */       if (this.conn.getNullCatalogMeansCurrent()) {
/* 1328 */         db = this.database;
/*      */       }
/*      */     } else {
/* 1331 */       db = catalog;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1350 */     StringBuffer sqlBuf = new StringBuffer("SELECT SPECIFIC_SCHEMA AS PROCEDURE_CAT, NULL AS `PROCEDURE_SCHEM`, SPECIFIC_NAME AS `PROCEDURE_NAME`, PARAMETER_NAME AS `COLUMN_NAME`, CASE WHEN PARAMETER_MODE = 'IN' THEN 1 WHEN PARAMETER_MODE='OUT' THEN 4 WHEN PARAMETER_MODE='INOUT' THEN 2 WHEN ORDINAL_POSITION=0 THEN 5 ELSE 0 END AS `COLUMN_TYPE`, ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1362 */     MysqlDefs.appendJdbcTypeMappingQuery(sqlBuf, "DATA_TYPE");
/*      */     
/* 1364 */     sqlBuf.append(" AS `DATA_TYPE`, ");
/*      */ 
/*      */     
/* 1367 */     if (this.conn.getCapitalizeTypeNames()) {
/* 1368 */       sqlBuf.append("UPPER(CASE WHEN LOCATE('unsigned', DATA_TYPE) != 0 AND LOCATE('unsigned', DATA_TYPE) = 0 THEN CONCAT(DATA_TYPE, ' unsigned') ELSE DATA_TYPE END) AS `TYPE_NAME`,");
/*      */     } else {
/* 1370 */       sqlBuf.append("CASE WHEN LOCATE('unsigned', DATA_TYPE) != 0 AND LOCATE('unsigned', DATA_TYPE) = 0 THEN CONCAT(DATA_TYPE, ' unsigned') ELSE DATA_TYPE END AS `TYPE_NAME`,");
/*      */     } 
/*      */ 
/*      */     
/* 1374 */     sqlBuf.append("NUMERIC_PRECISION AS `PRECISION`, ");
/*      */     
/* 1376 */     sqlBuf.append("CASE WHEN LCASE(DATA_TYPE)='date' THEN 10 WHEN LCASE(DATA_TYPE)='time' THEN 8 WHEN LCASE(DATA_TYPE)='datetime' THEN 19 WHEN LCASE(DATA_TYPE)='timestamp' THEN 19 WHEN CHARACTER_MAXIMUM_LENGTH IS NULL THEN NUMERIC_PRECISION WHEN CHARACTER_MAXIMUM_LENGTH > 2147483647 THEN 2147483647 ELSE CHARACTER_MAXIMUM_LENGTH END AS LENGTH, ");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1381 */     sqlBuf.append("NUMERIC_SCALE AS `SCALE`, ");
/*      */     
/* 1383 */     sqlBuf.append("10 AS RADIX,");
/* 1384 */     sqlBuf.append("2 AS `NULLABLE`,  NULL AS `REMARKS` FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA LIKE ? AND SPECIFIC_NAME LIKE ? AND (PARAMETER_NAME LIKE ? OR PARAMETER_NAME IS NULL) ORDER BY SPECIFIC_SCHEMA, SPECIFIC_NAME, ORDINAL_POSITION");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1390 */     PreparedStatement pStmt = null;
/*      */     
/*      */     try {
/* 1393 */       pStmt = prepareMetaDataSafeStatement(sqlBuf.toString());
/*      */       
/* 1395 */       if (db != null) {
/* 1396 */         pStmt.setString(1, db);
/*      */       } else {
/* 1398 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/* 1401 */       pStmt.setString(2, procedureNamePattern);
/* 1402 */       pStmt.setString(3, columnNamePattern);
/*      */       
/* 1404 */       ResultSet rs = executeMetadataQuery(pStmt);
/* 1405 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(createProcedureColumnsFields());
/*      */       
/* 1407 */       return rs;
/*      */     } finally {
/* 1409 */       if (pStmt != null) {
/* 1410 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
/*      */     String tableNamePat;
/* 1453 */     if (catalog == null && 
/* 1454 */       this.conn.getNullCatalogMeansCurrent()) {
/* 1455 */       catalog = this.database;
/*      */     }
/*      */ 
/*      */     
/* 1459 */     if (tableNamePattern == null) {
/* 1460 */       if (this.conn.getNullNamePatternMatchesAll()) {
/* 1461 */         tableNamePattern = "%";
/*      */       } else {
/* 1463 */         throw SQLError.createSQLException("Table name pattern can not be NULL or empty.", "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1470 */     List parseList = StringUtils.splitDBdotName(tableNamePattern, "", this.quotedId, this.conn.isNoBackslashEscapesSet());
/*      */ 
/*      */     
/* 1473 */     if (parseList.size() == 2) {
/* 1474 */       tableNamePat = (String)parseList.get(1);
/*      */     } else {
/* 1476 */       tableNamePat = tableNamePattern;
/*      */     } 
/*      */     
/* 1479 */     PreparedStatement pStmt = null;
/*      */     
/* 1481 */     String sql = "SELECT TABLE_SCHEMA AS TABLE_CAT, NULL AS TABLE_SCHEM, TABLE_NAME, CASE WHEN TABLE_TYPE='BASE TABLE' THEN 'TABLE' WHEN TABLE_TYPE='TEMPORARY' THEN 'LOCAL_TEMPORARY' ELSE TABLE_TYPE END AS TABLE_TYPE, TABLE_COMMENT AS REMARKS FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA LIKE ? AND TABLE_NAME LIKE ? AND TABLE_TYPE IN (?,?,?) ORDER BY TABLE_TYPE, TABLE_SCHEMA, TABLE_NAME";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1489 */       pStmt = prepareMetaDataSafeStatement(sql);
/*      */       
/* 1491 */       if (catalog != null) {
/* 1492 */         pStmt.setString(1, catalog);
/*      */       } else {
/* 1494 */         pStmt.setString(1, "%");
/*      */       } 
/*      */       
/* 1497 */       pStmt.setString(2, tableNamePat);
/*      */ 
/*      */ 
/*      */       
/* 1501 */       if (types == null || types.length == 0) {
/* 1502 */         pStmt.setString(3, "BASE TABLE");
/* 1503 */         pStmt.setString(4, "VIEW");
/* 1504 */         pStmt.setString(5, "TEMPORARY");
/*      */       } else {
/* 1506 */         pStmt.setNull(3, 12);
/* 1507 */         pStmt.setNull(4, 12);
/* 1508 */         pStmt.setNull(5, 12);
/*      */         
/* 1510 */         for (int i = 0; i < types.length; i++) {
/* 1511 */           if ("TABLE".equalsIgnoreCase(types[i])) {
/* 1512 */             pStmt.setString(3, "BASE TABLE");
/*      */           }
/*      */           
/* 1515 */           if ("VIEW".equalsIgnoreCase(types[i])) {
/* 1516 */             pStmt.setString(4, "VIEW");
/*      */           }
/*      */           
/* 1519 */           if ("LOCAL TEMPORARY".equalsIgnoreCase(types[i])) {
/* 1520 */             pStmt.setString(5, "TEMPORARY");
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1525 */       ResultSet rs = executeMetadataQuery(pStmt);
/*      */       
/* 1527 */       ((ResultSetInternalMethods)rs).redefineFieldsForDBMD(new Field[] { new Field("", "TABLE_CAT", 12, (catalog == null) ? 0 : catalog.length()), new Field("", "TABLE_SCHEM", 12, false), new Field("", "TABLE_NAME", 12, 'ÿ'), new Field("", "TABLE_TYPE", 12, 5), new Field("", "REMARKS", 12, false) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1535 */       return rs;
/*      */     } finally {
/* 1537 */       if (pStmt != null) {
/* 1538 */         pStmt.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1544 */   public boolean gethasParametersView() { return this.hasParametersView; }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\DatabaseMetaDataUsingInfoSchema.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */