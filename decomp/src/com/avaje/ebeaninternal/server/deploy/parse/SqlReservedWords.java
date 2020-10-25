/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import java.util.HashSet;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SqlReservedWords
/*    */ {
/*    */   private static final String baseKeyWords = "ALIAS,ALTER,ADD,ALL,ARE,AND,ANY,ARRAY,AS,ASC,AT,AVG,BEGIN,BETWEEN,BIGINT,BINARY,BIT,BIT_LENGTH,BLOB,BOOLEAN,BOTH,BY,CALL,CALLED,CASCADE,CASCADED,CASE,CAST,CATALOG,CHAR,CHARACTER,CHECK,CLOB,CLOSE,COALESCE,COLLATE,COLLATION,COLUMN,COMMIT,CONDITION,CONNECT,CONNECTION,CONSTRAINT,CONSTRAINTS,CONSTRUCTOR,CONTAINS,CONTINUE,CONVERT,COUNT,CREATE,CROSS,CURRENT_DATE,CURRENT_PATH,CURRENT_ROLE,CURRENT_TIME,CURRENT_TIMESTAMP,CURRENT_USER,CURSOR,DATE,DAY,DEC,DECIMAL,DECLARE,DEFAULT,DELETE,DESC,DISTINCT,DO,DOUBLE,DROP,ELSE,ELSEIF,END,EQUALS,EXEC,EXIT,EXISTS,EXTRACT,FLOAT,FROM,FOR,FREE,GET,GLOBAL,GO,GOTO,GRANT,GROUP,HAVING,HOUR,IF,IN,INNER,INOUT,INSERT,INT,INTEGER,INTO,IS,JOIN,LAST,LIKE,LIMIT,MAX,MIN,NCHAR,NCLOB,NOT,NULL,NULLIF,NUMERIC,,OR,ORDER,OUTER,REAL,REF,REFERENCES,RETURN,RETURNS,SELECT,SSL,SMALLINT,,SYSTEM,SYSTEM_USER,TABLE,TO,TRIGGER,UNION,UNIQUE,UPDATE,USER,VARCHAR,VIEW,WHEN,WHERE,WITH";
/* 48 */   private static HashSet<String> keywords = new HashSet();
/*    */   
/*    */   static  {
/* 51 */     initialKeywords = "ALIAS,ALTER,ADD,ALL,ARE,AND,ANY,ARRAY,AS,ASC,AT,AVG,BEGIN,BETWEEN,BIGINT,BINARY,BIT,BIT_LENGTH,BLOB,BOOLEAN,BOTH,BY,CALL,CALLED,CASCADE,CASCADED,CASE,CAST,CATALOG,CHAR,CHARACTER,CHECK,CLOB,CLOSE,COALESCE,COLLATE,COLLATION,COLUMN,COMMIT,CONDITION,CONNECT,CONNECTION,CONSTRAINT,CONSTRAINTS,CONSTRUCTOR,CONTAINS,CONTINUE,CONVERT,COUNT,CREATE,CROSS,CURRENT_DATE,CURRENT_PATH,CURRENT_ROLE,CURRENT_TIME,CURRENT_TIMESTAMP,CURRENT_USER,CURSOR,DATE,DAY,DEC,DECIMAL,DECLARE,DEFAULT,DELETE,DESC,DISTINCT,DO,DOUBLE,DROP,ELSE,ELSEIF,END,EQUALS,EXEC,EXIT,EXISTS,EXTRACT,FLOAT,FROM,FOR,FREE,GET,GLOBAL,GO,GOTO,GRANT,GROUP,HAVING,HOUR,IF,IN,INNER,INOUT,INSERT,INT,INTEGER,INTO,IS,JOIN,LAST,LIKE,LIMIT,MAX,MIN,NCHAR,NCLOB,NOT,NULL,NULLIF,NUMERIC,,OR,ORDER,OUTER,REAL,REF,REFERENCES,RETURN,RETURNS,SELECT,SSL,SMALLINT,,SYSTEM,SYSTEM_USER,TABLE,TO,TRIGGER,UNION,UNIQUE,UPDATE,USER,VARCHAR,VIEW,WHEN,WHERE,WITH".split(",");
/* 52 */     for (int i = 0; i < initialKeywords.length; i++) {
/* 53 */       keywords.add(initialKeywords[i].trim());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isKeyword(String keyword) {
/* 63 */     String s = keyword.trim().toUpperCase();
/* 64 */     return keywords.contains(s);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void addKeyword(String keyword) {
/* 71 */     if (keyword != null) {
/* 72 */       keyword = keyword.trim().toUpperCase();
/* 73 */       if (keyword.length() > 0)
/* 74 */         keywords.add(keyword); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\SqlReservedWords.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */