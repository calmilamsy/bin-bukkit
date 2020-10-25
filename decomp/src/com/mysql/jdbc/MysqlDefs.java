/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MysqlDefs
/*     */ {
/*     */   static final int COM_BINLOG_DUMP = 18;
/*     */   static final int COM_CHANGE_USER = 17;
/*     */   static final int COM_CLOSE_STATEMENT = 25;
/*     */   static final int COM_CONNECT_OUT = 20;
/*     */   static final int COM_END = 29;
/*     */   static final int COM_EXECUTE = 23;
/*     */   static final int COM_FETCH = 28;
/*     */   static final int COM_LONG_DATA = 24;
/*     */   static final int COM_PREPARE = 22;
/*     */   static final int COM_REGISTER_SLAVE = 21;
/*     */   static final int COM_RESET_STMT = 26;
/*     */   static final int COM_SET_OPTION = 27;
/*     */   static final int COM_TABLE_DUMP = 19;
/*     */   static final int CONNECT = 11;
/*     */   static final int CREATE_DB = 5;
/*     */   static final int DEBUG = 13;
/*     */   static final int DELAYED_INSERT = 16;
/*     */   static final int DROP_DB = 6;
/*     */   static final int FIELD_LIST = 4;
/*     */   static final int FIELD_TYPE_BIT = 16;
/*     */   public static final int FIELD_TYPE_BLOB = 252;
/*     */   static final int FIELD_TYPE_DATE = 10;
/*     */   static final int FIELD_TYPE_DATETIME = 12;
/*     */   static final int FIELD_TYPE_DECIMAL = 0;
/*     */   static final int FIELD_TYPE_DOUBLE = 5;
/*     */   static final int FIELD_TYPE_ENUM = 247;
/*     */   static final int FIELD_TYPE_FLOAT = 4;
/*     */   static final int FIELD_TYPE_GEOMETRY = 255;
/*     */   static final int FIELD_TYPE_INT24 = 9;
/*     */   static final int FIELD_TYPE_LONG = 3;
/*     */   static final int FIELD_TYPE_LONG_BLOB = 251;
/*     */   static final int FIELD_TYPE_LONGLONG = 8;
/*     */   static final int FIELD_TYPE_MEDIUM_BLOB = 250;
/*     */   static final int FIELD_TYPE_NEW_DECIMAL = 246;
/*     */   static final int FIELD_TYPE_NEWDATE = 14;
/*     */   static final int FIELD_TYPE_NULL = 6;
/*     */   static final int FIELD_TYPE_SET = 248;
/*     */   static final int FIELD_TYPE_SHORT = 2;
/*     */   static final int FIELD_TYPE_STRING = 254;
/*     */   static final int FIELD_TYPE_TIME = 11;
/*     */   static final int FIELD_TYPE_TIMESTAMP = 7;
/*     */   static final int FIELD_TYPE_TINY = 1;
/*     */   static final int FIELD_TYPE_TINY_BLOB = 249;
/*     */   static final int FIELD_TYPE_VAR_STRING = 253;
/*     */   static final int FIELD_TYPE_VARCHAR = 15;
/*     */   static final int FIELD_TYPE_YEAR = 13;
/*     */   static final int INIT_DB = 2;
/*     */   static final long LENGTH_BLOB = 65535L;
/*     */   static final long LENGTH_LONGBLOB = 4294967295L;
/*     */   static final long LENGTH_MEDIUMBLOB = 16777215L;
/*     */   static final long LENGTH_TINYBLOB = 255L;
/*     */   static final int MAX_ROWS = 50000000;
/*     */   public static final int NO_CHARSET_INFO = -1;
/*     */   static final byte OPEN_CURSOR_FLAG = 1;
/*     */   static final int PING = 14;
/*     */   static final int PROCESS_INFO = 10;
/*     */   static final int PROCESS_KILL = 12;
/*     */   static final int QUERY = 3;
/*     */   static final int QUIT = 1;
/*     */   static final int RELOAD = 7;
/*     */   static final int SHUTDOWN = 8;
/*     */   static final int SLEEP = 0;
/*     */   static final int STATISTICS = 9;
/*     */   static final int TIME = 15;
/*     */   
/*     */   static int mysqlToJavaType(int mysqlType) {
/* 194 */     switch (mysqlType) {
/*     */       case 0:
/*     */       case 246:
/* 197 */         return 3;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 202 */         return -6;
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 207 */         return 5;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 212 */         return 4;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 217 */         return 7;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 222 */         return 8;
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 227 */         return 0;
/*     */ 
/*     */ 
/*     */       
/*     */       case 7:
/* 232 */         return 93;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 237 */         return -5;
/*     */ 
/*     */ 
/*     */       
/*     */       case 9:
/* 242 */         return 4;
/*     */ 
/*     */ 
/*     */       
/*     */       case 10:
/* 247 */         return 91;
/*     */ 
/*     */ 
/*     */       
/*     */       case 11:
/* 252 */         return 92;
/*     */ 
/*     */ 
/*     */       
/*     */       case 12:
/* 257 */         return 93;
/*     */ 
/*     */ 
/*     */       
/*     */       case 13:
/* 262 */         return 91;
/*     */ 
/*     */ 
/*     */       
/*     */       case 14:
/* 267 */         return 91;
/*     */ 
/*     */ 
/*     */       
/*     */       case 247:
/* 272 */         return 1;
/*     */ 
/*     */ 
/*     */       
/*     */       case 248:
/* 277 */         return 1;
/*     */ 
/*     */ 
/*     */       
/*     */       case 249:
/* 282 */         return -3;
/*     */ 
/*     */ 
/*     */       
/*     */       case 250:
/* 287 */         return -4;
/*     */ 
/*     */ 
/*     */       
/*     */       case 251:
/* 292 */         return -4;
/*     */ 
/*     */ 
/*     */       
/*     */       case 252:
/* 297 */         return -4;
/*     */ 
/*     */ 
/*     */       
/*     */       case 15:
/*     */       case 253:
/* 303 */         return 12;
/*     */ 
/*     */ 
/*     */       
/*     */       case 254:
/* 308 */         return 1;
/*     */ 
/*     */       
/*     */       case 255:
/* 312 */         return -2;
/*     */ 
/*     */       
/*     */       case 16:
/* 316 */         return -7;
/*     */     } 
/*     */ 
/*     */     
/* 320 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int mysqlToJavaType(String mysqlType) {
/* 330 */     if (mysqlType.equalsIgnoreCase("BIT"))
/* 331 */       return mysqlToJavaType(16); 
/* 332 */     if (mysqlType.equalsIgnoreCase("TINYINT"))
/* 333 */       return mysqlToJavaType(1); 
/* 334 */     if (mysqlType.equalsIgnoreCase("SMALLINT"))
/* 335 */       return mysqlToJavaType(2); 
/* 336 */     if (mysqlType.equalsIgnoreCase("MEDIUMINT"))
/* 337 */       return mysqlToJavaType(9); 
/* 338 */     if (mysqlType.equalsIgnoreCase("INT") || mysqlType.equalsIgnoreCase("INTEGER"))
/* 339 */       return mysqlToJavaType(3); 
/* 340 */     if (mysqlType.equalsIgnoreCase("BIGINT"))
/* 341 */       return mysqlToJavaType(8); 
/* 342 */     if (mysqlType.equalsIgnoreCase("INT24"))
/* 343 */       return mysqlToJavaType(9); 
/* 344 */     if (mysqlType.equalsIgnoreCase("REAL"))
/* 345 */       return mysqlToJavaType(5); 
/* 346 */     if (mysqlType.equalsIgnoreCase("FLOAT"))
/* 347 */       return mysqlToJavaType(4); 
/* 348 */     if (mysqlType.equalsIgnoreCase("DECIMAL"))
/* 349 */       return mysqlToJavaType(0); 
/* 350 */     if (mysqlType.equalsIgnoreCase("NUMERIC"))
/* 351 */       return mysqlToJavaType(0); 
/* 352 */     if (mysqlType.equalsIgnoreCase("DOUBLE"))
/* 353 */       return mysqlToJavaType(5); 
/* 354 */     if (mysqlType.equalsIgnoreCase("CHAR"))
/* 355 */       return mysqlToJavaType(254); 
/* 356 */     if (mysqlType.equalsIgnoreCase("VARCHAR"))
/* 357 */       return mysqlToJavaType(253); 
/* 358 */     if (mysqlType.equalsIgnoreCase("DATE"))
/* 359 */       return mysqlToJavaType(10); 
/* 360 */     if (mysqlType.equalsIgnoreCase("TIME"))
/* 361 */       return mysqlToJavaType(11); 
/* 362 */     if (mysqlType.equalsIgnoreCase("YEAR"))
/* 363 */       return mysqlToJavaType(13); 
/* 364 */     if (mysqlType.equalsIgnoreCase("TIMESTAMP"))
/* 365 */       return mysqlToJavaType(7); 
/* 366 */     if (mysqlType.equalsIgnoreCase("DATETIME"))
/* 367 */       return mysqlToJavaType(12); 
/* 368 */     if (mysqlType.equalsIgnoreCase("TINYBLOB"))
/* 369 */       return -2; 
/* 370 */     if (mysqlType.equalsIgnoreCase("BLOB"))
/* 371 */       return -4; 
/* 372 */     if (mysqlType.equalsIgnoreCase("MEDIUMBLOB"))
/* 373 */       return -4; 
/* 374 */     if (mysqlType.equalsIgnoreCase("LONGBLOB"))
/* 375 */       return -4; 
/* 376 */     if (mysqlType.equalsIgnoreCase("TINYTEXT"))
/* 377 */       return 12; 
/* 378 */     if (mysqlType.equalsIgnoreCase("TEXT"))
/* 379 */       return -1; 
/* 380 */     if (mysqlType.equalsIgnoreCase("MEDIUMTEXT"))
/* 381 */       return -1; 
/* 382 */     if (mysqlType.equalsIgnoreCase("LONGTEXT"))
/* 383 */       return -1; 
/* 384 */     if (mysqlType.equalsIgnoreCase("ENUM"))
/* 385 */       return mysqlToJavaType(247); 
/* 386 */     if (mysqlType.equalsIgnoreCase("SET"))
/* 387 */       return mysqlToJavaType(248); 
/* 388 */     if (mysqlType.equalsIgnoreCase("GEOMETRY"))
/* 389 */       return mysqlToJavaType(255); 
/* 390 */     if (mysqlType.equalsIgnoreCase("BINARY"))
/* 391 */       return -2; 
/* 392 */     if (mysqlType.equalsIgnoreCase("VARBINARY"))
/* 393 */       return -3; 
/* 394 */     if (mysqlType.equalsIgnoreCase("BIT")) {
/* 395 */       return mysqlToJavaType(16);
/*     */     }
/*     */ 
/*     */     
/* 399 */     return 1111;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String typeToName(int mysqlType) {
/* 407 */     switch (mysqlType) {
/*     */       case 0:
/* 409 */         return "FIELD_TYPE_DECIMAL";
/*     */       
/*     */       case 1:
/* 412 */         return "FIELD_TYPE_TINY";
/*     */       
/*     */       case 2:
/* 415 */         return "FIELD_TYPE_SHORT";
/*     */       
/*     */       case 3:
/* 418 */         return "FIELD_TYPE_LONG";
/*     */       
/*     */       case 4:
/* 421 */         return "FIELD_TYPE_FLOAT";
/*     */       
/*     */       case 5:
/* 424 */         return "FIELD_TYPE_DOUBLE";
/*     */       
/*     */       case 6:
/* 427 */         return "FIELD_TYPE_NULL";
/*     */       
/*     */       case 7:
/* 430 */         return "FIELD_TYPE_TIMESTAMP";
/*     */       
/*     */       case 8:
/* 433 */         return "FIELD_TYPE_LONGLONG";
/*     */       
/*     */       case 9:
/* 436 */         return "FIELD_TYPE_INT24";
/*     */       
/*     */       case 10:
/* 439 */         return "FIELD_TYPE_DATE";
/*     */       
/*     */       case 11:
/* 442 */         return "FIELD_TYPE_TIME";
/*     */       
/*     */       case 12:
/* 445 */         return "FIELD_TYPE_DATETIME";
/*     */       
/*     */       case 13:
/* 448 */         return "FIELD_TYPE_YEAR";
/*     */       
/*     */       case 14:
/* 451 */         return "FIELD_TYPE_NEWDATE";
/*     */       
/*     */       case 247:
/* 454 */         return "FIELD_TYPE_ENUM";
/*     */       
/*     */       case 248:
/* 457 */         return "FIELD_TYPE_SET";
/*     */       
/*     */       case 249:
/* 460 */         return "FIELD_TYPE_TINY_BLOB";
/*     */       
/*     */       case 250:
/* 463 */         return "FIELD_TYPE_MEDIUM_BLOB";
/*     */       
/*     */       case 251:
/* 466 */         return "FIELD_TYPE_LONG_BLOB";
/*     */       
/*     */       case 252:
/* 469 */         return "FIELD_TYPE_BLOB";
/*     */       
/*     */       case 253:
/* 472 */         return "FIELD_TYPE_VAR_STRING";
/*     */       
/*     */       case 254:
/* 475 */         return "FIELD_TYPE_STRING";
/*     */       
/*     */       case 15:
/* 478 */         return "FIELD_TYPE_VARCHAR";
/*     */       
/*     */       case 255:
/* 481 */         return "FIELD_TYPE_GEOMETRY";
/*     */     } 
/*     */     
/* 484 */     return " Unknown MySQL Type # " + mysqlType;
/*     */   }
/*     */ 
/*     */   
/* 488 */   private static Map mysqlToJdbcTypesMap = new HashMap();
/*     */   
/*     */   static  {
/* 491 */     mysqlToJdbcTypesMap.put("BIT", Constants.integerValueOf(mysqlToJavaType(16)));
/*     */ 
/*     */     
/* 494 */     mysqlToJdbcTypesMap.put("TINYINT", Constants.integerValueOf(mysqlToJavaType(1)));
/*     */     
/* 496 */     mysqlToJdbcTypesMap.put("SMALLINT", Constants.integerValueOf(mysqlToJavaType(2)));
/*     */     
/* 498 */     mysqlToJdbcTypesMap.put("MEDIUMINT", Constants.integerValueOf(mysqlToJavaType(9)));
/*     */     
/* 500 */     mysqlToJdbcTypesMap.put("INT", Constants.integerValueOf(mysqlToJavaType(3)));
/*     */     
/* 502 */     mysqlToJdbcTypesMap.put("INTEGER", Constants.integerValueOf(mysqlToJavaType(3)));
/*     */     
/* 504 */     mysqlToJdbcTypesMap.put("BIGINT", Constants.integerValueOf(mysqlToJavaType(8)));
/*     */     
/* 506 */     mysqlToJdbcTypesMap.put("INT24", Constants.integerValueOf(mysqlToJavaType(9)));
/*     */     
/* 508 */     mysqlToJdbcTypesMap.put("REAL", Constants.integerValueOf(mysqlToJavaType(5)));
/*     */     
/* 510 */     mysqlToJdbcTypesMap.put("FLOAT", Constants.integerValueOf(mysqlToJavaType(4)));
/*     */     
/* 512 */     mysqlToJdbcTypesMap.put("DECIMAL", Constants.integerValueOf(mysqlToJavaType(0)));
/*     */     
/* 514 */     mysqlToJdbcTypesMap.put("NUMERIC", Constants.integerValueOf(mysqlToJavaType(0)));
/*     */     
/* 516 */     mysqlToJdbcTypesMap.put("DOUBLE", Constants.integerValueOf(mysqlToJavaType(5)));
/*     */     
/* 518 */     mysqlToJdbcTypesMap.put("CHAR", Constants.integerValueOf(mysqlToJavaType(254)));
/*     */     
/* 520 */     mysqlToJdbcTypesMap.put("VARCHAR", Constants.integerValueOf(mysqlToJavaType(253)));
/*     */     
/* 522 */     mysqlToJdbcTypesMap.put("DATE", Constants.integerValueOf(mysqlToJavaType(10)));
/*     */     
/* 524 */     mysqlToJdbcTypesMap.put("TIME", Constants.integerValueOf(mysqlToJavaType(11)));
/*     */     
/* 526 */     mysqlToJdbcTypesMap.put("YEAR", Constants.integerValueOf(mysqlToJavaType(13)));
/*     */     
/* 528 */     mysqlToJdbcTypesMap.put("TIMESTAMP", Constants.integerValueOf(mysqlToJavaType(7)));
/*     */     
/* 530 */     mysqlToJdbcTypesMap.put("DATETIME", Constants.integerValueOf(mysqlToJavaType(12)));
/*     */     
/* 532 */     mysqlToJdbcTypesMap.put("TINYBLOB", Constants.integerValueOf(-2));
/* 533 */     mysqlToJdbcTypesMap.put("BLOB", Constants.integerValueOf(-4));
/*     */     
/* 535 */     mysqlToJdbcTypesMap.put("MEDIUMBLOB", Constants.integerValueOf(-4));
/*     */     
/* 537 */     mysqlToJdbcTypesMap.put("LONGBLOB", Constants.integerValueOf(-4));
/*     */     
/* 539 */     mysqlToJdbcTypesMap.put("TINYTEXT", Constants.integerValueOf(12));
/*     */     
/* 541 */     mysqlToJdbcTypesMap.put("TEXT", Constants.integerValueOf(-1));
/*     */     
/* 543 */     mysqlToJdbcTypesMap.put("MEDIUMTEXT", Constants.integerValueOf(-1));
/*     */     
/* 545 */     mysqlToJdbcTypesMap.put("LONGTEXT", Constants.integerValueOf(-1));
/*     */     
/* 547 */     mysqlToJdbcTypesMap.put("ENUM", Constants.integerValueOf(mysqlToJavaType(247)));
/*     */     
/* 549 */     mysqlToJdbcTypesMap.put("SET", Constants.integerValueOf(mysqlToJavaType(248)));
/*     */     
/* 551 */     mysqlToJdbcTypesMap.put("GEOMETRY", Constants.integerValueOf(mysqlToJavaType(255)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final void appendJdbcTypeMappingQuery(StringBuffer buf, String mysqlTypeColumnName) {
/* 557 */     buf.append("CASE ");
/* 558 */     Map typesMap = new HashMap();
/* 559 */     typesMap.putAll(mysqlToJdbcTypesMap);
/* 560 */     typesMap.put("BINARY", Constants.integerValueOf(-2));
/* 561 */     typesMap.put("VARBINARY", Constants.integerValueOf(-3));
/*     */     
/* 563 */     Iterator mysqlTypes = typesMap.keySet().iterator();
/*     */     
/* 565 */     while (mysqlTypes.hasNext()) {
/* 566 */       String mysqlTypeName = (String)mysqlTypes.next();
/* 567 */       buf.append(" WHEN ");
/* 568 */       buf.append(mysqlTypeColumnName);
/* 569 */       buf.append("='");
/* 570 */       buf.append(mysqlTypeName);
/* 571 */       buf.append("' THEN ");
/* 572 */       buf.append(typesMap.get(mysqlTypeName));
/*     */       
/* 574 */       if (mysqlTypeName.equalsIgnoreCase("DOUBLE") || mysqlTypeName.equalsIgnoreCase("FLOAT") || mysqlTypeName.equalsIgnoreCase("DECIMAL") || mysqlTypeName.equalsIgnoreCase("NUMERIC")) {
/*     */ 
/*     */ 
/*     */         
/* 578 */         buf.append(" WHEN ");
/* 579 */         buf.append(mysqlTypeColumnName);
/* 580 */         buf.append("='");
/* 581 */         buf.append(mysqlTypeName);
/* 582 */         buf.append(" unsigned' THEN ");
/* 583 */         buf.append(typesMap.get(mysqlTypeName));
/*     */       } 
/*     */     } 
/*     */     
/* 587 */     buf.append(" ELSE ");
/* 588 */     buf.append(1111);
/* 589 */     buf.append(" END ");
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\MysqlDefs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */