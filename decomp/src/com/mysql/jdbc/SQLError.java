/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.exceptions.MySQLDataException;
/*      */ import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
/*      */ import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
/*      */ import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTransactionRollbackException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTransientConnectionException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.sql.DataTruncation;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SQLError
/*      */ {
/*      */   static final int ER_WARNING_NOT_COMPLETE_ROLLBACK = 1196;
/*      */   private static Map mysqlToSql99State;
/*      */   private static Map mysqlToSqlState;
/*      */   public static final String SQL_STATE_BASE_TABLE_NOT_FOUND = "S0002";
/*      */   public static final String SQL_STATE_BASE_TABLE_OR_VIEW_ALREADY_EXISTS = "S0001";
/*      */   public static final String SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND = "42S02";
/*      */   public static final String SQL_STATE_COLUMN_ALREADY_EXISTS = "S0021";
/*      */   public static final String SQL_STATE_COLUMN_NOT_FOUND = "S0022";
/*      */   public static final String SQL_STATE_COMMUNICATION_LINK_FAILURE = "08S01";
/*      */   public static final String SQL_STATE_CONNECTION_FAIL_DURING_TX = "08007";
/*      */   public static final String SQL_STATE_CONNECTION_IN_USE = "08002";
/*      */   public static final String SQL_STATE_CONNECTION_NOT_OPEN = "08003";
/*      */   public static final String SQL_STATE_CONNECTION_REJECTED = "08004";
/*      */   public static final String SQL_STATE_DATE_TRUNCATED = "01004";
/*      */   public static final String SQL_STATE_DATETIME_FIELD_OVERFLOW = "22008";
/*      */   public static final String SQL_STATE_DEADLOCK = "41000";
/*      */   public static final String SQL_STATE_DISCONNECT_ERROR = "01002";
/*      */   public static final String SQL_STATE_DIVISION_BY_ZERO = "22012";
/*      */   public static final String SQL_STATE_DRIVER_NOT_CAPABLE = "S1C00";
/*      */   public static final String SQL_STATE_ERROR_IN_ROW = "01S01";
/*      */   public static final String SQL_STATE_GENERAL_ERROR = "S1000";
/*      */   public static final String SQL_STATE_ILLEGAL_ARGUMENT = "S1009";
/*      */   public static final String SQL_STATE_INDEX_ALREADY_EXISTS = "S0011";
/*      */   public static final String SQL_STATE_INDEX_NOT_FOUND = "S0012";
/*      */   public static final String SQL_STATE_INSERT_VALUE_LIST_NO_MATCH_COL_LIST = "21S01";
/*      */   public static final String SQL_STATE_INVALID_AUTH_SPEC = "28000";
/*      */   public static final String SQL_STATE_INVALID_CHARACTER_VALUE_FOR_CAST = "22018";
/*      */   public static final String SQL_STATE_INVALID_COLUMN_NUMBER = "S1002";
/*      */   public static final String SQL_STATE_INVALID_CONNECTION_ATTRIBUTE = "01S00";
/*      */   public static final String SQL_STATE_MEMORY_ALLOCATION_FAILURE = "S1001";
/*      */   public static final String SQL_STATE_MORE_THAN_ONE_ROW_UPDATED_OR_DELETED = "01S04";
/*      */   public static final String SQL_STATE_NO_DEFAULT_FOR_COLUMN = "S0023";
/*      */   public static final String SQL_STATE_NO_ROWS_UPDATED_OR_DELETED = "01S03";
/*      */   public static final String SQL_STATE_NUMERIC_VALUE_OUT_OF_RANGE = "22003";
/*      */   public static final String SQL_STATE_PRIVILEGE_NOT_REVOKED = "01006";
/*      */   public static final String SQL_STATE_SYNTAX_ERROR = "42000";
/*      */   public static final String SQL_STATE_TIMEOUT_EXPIRED = "S1T00";
/*      */   public static final String SQL_STATE_TRANSACTION_RESOLUTION_UNKNOWN = "08007";
/*      */   public static final String SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE = "08001";
/*      */   public static final String SQL_STATE_WRONG_NO_OF_PARAMETERS = "07001";
/*      */   public static final String SQL_STATE_INVALID_TRANSACTION_TERMINATION = "2D000";
/*      */   private static Map sqlStateMessages;
/*      */   private static final long DEFAULT_WAIT_TIMEOUT_SECONDS = 28800L;
/*      */   private static final int DUE_TO_TIMEOUT_FALSE = 0;
/*      */   private static final int DUE_TO_TIMEOUT_MAYBE = 2;
/*      */   private static final int DUE_TO_TIMEOUT_TRUE = 1;
/*      */   private static final Constructor JDBC_4_COMMUNICATIONS_EXCEPTION_CTOR;
/*      */   private static Method THROWABLE_INIT_CAUSE_METHOD;
/*      */   
/*      */   static  {
/*  153 */     if (Util.isJdbc4()) {
/*      */       try {
/*  155 */         JDBC_4_COMMUNICATIONS_EXCEPTION_CTOR = Class.forName("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException").getConstructor(new Class[] { MySQLConnection.class, long.class, long.class, Exception.class });
/*      */ 
/*      */       
/*      */       }
/*  159 */       catch (SecurityException e) {
/*  160 */         throw new RuntimeException(e);
/*  161 */       } catch (NoSuchMethodException e) {
/*  162 */         throw new RuntimeException(t);
/*  163 */       } catch (ClassNotFoundException e) {
/*  164 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*  167 */       JDBC_4_COMMUNICATIONS_EXCEPTION_CTOR = null;
/*      */     } 
/*      */     
/*      */     try {
/*  171 */       THROWABLE_INIT_CAUSE_METHOD = Throwable.class.getMethod("initCause", new Class[] { Throwable.class });
/*  172 */     } catch (Throwable e) {
/*      */       
/*  174 */       THROWABLE_INIT_CAUSE_METHOD = null;
/*      */     } 
/*      */     
/*  177 */     sqlStateMessages = new HashMap();
/*  178 */     sqlStateMessages.put("01002", Messages.getString("SQLError.35"));
/*      */     
/*  180 */     sqlStateMessages.put("01004", Messages.getString("SQLError.36"));
/*      */     
/*  182 */     sqlStateMessages.put("01006", Messages.getString("SQLError.37"));
/*      */     
/*  184 */     sqlStateMessages.put("01S00", Messages.getString("SQLError.38"));
/*      */     
/*  186 */     sqlStateMessages.put("01S01", Messages.getString("SQLError.39"));
/*      */     
/*  188 */     sqlStateMessages.put("01S03", Messages.getString("SQLError.40"));
/*      */     
/*  190 */     sqlStateMessages.put("01S04", Messages.getString("SQLError.41"));
/*      */     
/*  192 */     sqlStateMessages.put("07001", Messages.getString("SQLError.42"));
/*      */     
/*  194 */     sqlStateMessages.put("08001", Messages.getString("SQLError.43"));
/*      */     
/*  196 */     sqlStateMessages.put("08002", Messages.getString("SQLError.44"));
/*      */     
/*  198 */     sqlStateMessages.put("08003", Messages.getString("SQLError.45"));
/*      */     
/*  200 */     sqlStateMessages.put("08004", Messages.getString("SQLError.46"));
/*      */     
/*  202 */     sqlStateMessages.put("08007", Messages.getString("SQLError.47"));
/*      */     
/*  204 */     sqlStateMessages.put("08S01", Messages.getString("SQLError.48"));
/*      */     
/*  206 */     sqlStateMessages.put("21S01", Messages.getString("SQLError.49"));
/*      */     
/*  208 */     sqlStateMessages.put("22003", Messages.getString("SQLError.50"));
/*      */     
/*  210 */     sqlStateMessages.put("22008", Messages.getString("SQLError.51"));
/*      */     
/*  212 */     sqlStateMessages.put("22012", Messages.getString("SQLError.52"));
/*      */     
/*  214 */     sqlStateMessages.put("41000", Messages.getString("SQLError.53"));
/*      */     
/*  216 */     sqlStateMessages.put("28000", Messages.getString("SQLError.54"));
/*      */     
/*  218 */     sqlStateMessages.put("42000", Messages.getString("SQLError.55"));
/*      */     
/*  220 */     sqlStateMessages.put("42S02", Messages.getString("SQLError.56"));
/*      */     
/*  222 */     sqlStateMessages.put("S0001", Messages.getString("SQLError.57"));
/*      */     
/*  224 */     sqlStateMessages.put("S0002", Messages.getString("SQLError.58"));
/*      */     
/*  226 */     sqlStateMessages.put("S0011", Messages.getString("SQLError.59"));
/*      */     
/*  228 */     sqlStateMessages.put("S0012", Messages.getString("SQLError.60"));
/*      */     
/*  230 */     sqlStateMessages.put("S0021", Messages.getString("SQLError.61"));
/*      */     
/*  232 */     sqlStateMessages.put("S0022", Messages.getString("SQLError.62"));
/*      */     
/*  234 */     sqlStateMessages.put("S0023", Messages.getString("SQLError.63"));
/*      */     
/*  236 */     sqlStateMessages.put("S1000", Messages.getString("SQLError.64"));
/*      */     
/*  238 */     sqlStateMessages.put("S1001", Messages.getString("SQLError.65"));
/*      */     
/*  240 */     sqlStateMessages.put("S1002", Messages.getString("SQLError.66"));
/*      */     
/*  242 */     sqlStateMessages.put("S1009", Messages.getString("SQLError.67"));
/*      */     
/*  244 */     sqlStateMessages.put("S1C00", Messages.getString("SQLError.68"));
/*      */     
/*  246 */     sqlStateMessages.put("S1T00", Messages.getString("SQLError.69"));
/*      */ 
/*      */     
/*  249 */     mysqlToSqlState = new Hashtable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  260 */     mysqlToSqlState.put(Constants.integerValueOf(1040), "08004");
/*  261 */     mysqlToSqlState.put(Constants.integerValueOf(1042), "08004");
/*  262 */     mysqlToSqlState.put(Constants.integerValueOf(1043), "08004");
/*  263 */     mysqlToSqlState.put(Constants.integerValueOf(1047), "08S01");
/*      */     
/*  265 */     mysqlToSqlState.put(Constants.integerValueOf(1081), "08S01");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  270 */     mysqlToSqlState.put(Constants.integerValueOf(1129), "08004");
/*  271 */     mysqlToSqlState.put(Constants.integerValueOf(1130), "08004");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  278 */     mysqlToSqlState.put(Constants.integerValueOf(1045), "28000");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  295 */     mysqlToSqlState.put(Constants.integerValueOf(1037), "S1001");
/*      */     
/*  297 */     mysqlToSqlState.put(Constants.integerValueOf(1038), "S1001");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  306 */     mysqlToSqlState.put(Constants.integerValueOf(1064), "42000");
/*  307 */     mysqlToSqlState.put(Constants.integerValueOf(1065), "42000");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  334 */     mysqlToSqlState.put(Constants.integerValueOf(1055), "S1009");
/*  335 */     mysqlToSqlState.put(Constants.integerValueOf(1056), "S1009");
/*  336 */     mysqlToSqlState.put(Constants.integerValueOf(1057), "S1009");
/*  337 */     mysqlToSqlState.put(Constants.integerValueOf(1059), "S1009");
/*  338 */     mysqlToSqlState.put(Constants.integerValueOf(1060), "S1009");
/*  339 */     mysqlToSqlState.put(Constants.integerValueOf(1061), "S1009");
/*  340 */     mysqlToSqlState.put(Constants.integerValueOf(1062), "S1009");
/*  341 */     mysqlToSqlState.put(Constants.integerValueOf(1063), "S1009");
/*  342 */     mysqlToSqlState.put(Constants.integerValueOf(1066), "S1009");
/*  343 */     mysqlToSqlState.put(Constants.integerValueOf(1067), "S1009");
/*  344 */     mysqlToSqlState.put(Constants.integerValueOf(1068), "S1009");
/*  345 */     mysqlToSqlState.put(Constants.integerValueOf(1069), "S1009");
/*  346 */     mysqlToSqlState.put(Constants.integerValueOf(1070), "S1009");
/*  347 */     mysqlToSqlState.put(Constants.integerValueOf(1071), "S1009");
/*  348 */     mysqlToSqlState.put(Constants.integerValueOf(1072), "S1009");
/*  349 */     mysqlToSqlState.put(Constants.integerValueOf(1073), "S1009");
/*  350 */     mysqlToSqlState.put(Constants.integerValueOf(1074), "S1009");
/*  351 */     mysqlToSqlState.put(Constants.integerValueOf(1075), "S1009");
/*  352 */     mysqlToSqlState.put(Constants.integerValueOf(1082), "S1009");
/*  353 */     mysqlToSqlState.put(Constants.integerValueOf(1083), "S1009");
/*  354 */     mysqlToSqlState.put(Constants.integerValueOf(1084), "S1009");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  359 */     mysqlToSqlState.put(Constants.integerValueOf(1058), "21S01");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  395 */     mysqlToSqlState.put(Constants.integerValueOf(1051), "42S02");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  400 */     mysqlToSqlState.put(Constants.integerValueOf(1054), "S0022");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  412 */     mysqlToSqlState.put(Constants.integerValueOf(1205), "41000");
/*  413 */     mysqlToSqlState.put(Constants.integerValueOf(1213), "41000");
/*      */     
/*  415 */     mysqlToSql99State = new HashMap();
/*      */     
/*  417 */     mysqlToSql99State.put(Constants.integerValueOf(1205), "41000");
/*  418 */     mysqlToSql99State.put(Constants.integerValueOf(1213), "41000");
/*  419 */     mysqlToSql99State.put(Constants.integerValueOf(1022), "23000");
/*      */     
/*  421 */     mysqlToSql99State.put(Constants.integerValueOf(1037), "HY001");
/*      */     
/*  423 */     mysqlToSql99State.put(Constants.integerValueOf(1038), "HY001");
/*      */     
/*  425 */     mysqlToSql99State.put(Constants.integerValueOf(1040), "08004");
/*      */     
/*  427 */     mysqlToSql99State.put(Constants.integerValueOf(1042), "08S01");
/*      */     
/*  429 */     mysqlToSql99State.put(Constants.integerValueOf(1043), "08S01");
/*      */     
/*  431 */     mysqlToSql99State.put(Constants.integerValueOf(1044), "42000");
/*      */     
/*  433 */     mysqlToSql99State.put(Constants.integerValueOf(1045), "28000");
/*      */     
/*  435 */     mysqlToSql99State.put(Constants.integerValueOf(1050), "42S01");
/*      */     
/*  437 */     mysqlToSql99State.put(Constants.integerValueOf(1051), "42S02");
/*      */     
/*  439 */     mysqlToSql99State.put(Constants.integerValueOf(1052), "23000");
/*      */     
/*  441 */     mysqlToSql99State.put(Constants.integerValueOf(1053), "08S01");
/*      */     
/*  443 */     mysqlToSql99State.put(Constants.integerValueOf(1054), "42S22");
/*      */     
/*  445 */     mysqlToSql99State.put(Constants.integerValueOf(1055), "42000");
/*      */     
/*  447 */     mysqlToSql99State.put(Constants.integerValueOf(1056), "42000");
/*      */     
/*  449 */     mysqlToSql99State.put(Constants.integerValueOf(1057), "42000");
/*      */     
/*  451 */     mysqlToSql99State.put(Constants.integerValueOf(1058), "21S01");
/*      */     
/*  453 */     mysqlToSql99State.put(Constants.integerValueOf(1059), "42000");
/*      */     
/*  455 */     mysqlToSql99State.put(Constants.integerValueOf(1060), "42S21");
/*      */     
/*  457 */     mysqlToSql99State.put(Constants.integerValueOf(1061), "42000");
/*      */     
/*  459 */     mysqlToSql99State.put(Constants.integerValueOf(1062), "23000");
/*      */     
/*  461 */     mysqlToSql99State.put(Constants.integerValueOf(1063), "42000");
/*      */     
/*  463 */     mysqlToSql99State.put(Constants.integerValueOf(1064), "42000");
/*      */     
/*  465 */     mysqlToSql99State.put(Constants.integerValueOf(1065), "42000");
/*      */     
/*  467 */     mysqlToSql99State.put(Constants.integerValueOf(1066), "42000");
/*      */     
/*  469 */     mysqlToSql99State.put(Constants.integerValueOf(1067), "42000");
/*      */     
/*  471 */     mysqlToSql99State.put(Constants.integerValueOf(1068), "42000");
/*      */     
/*  473 */     mysqlToSql99State.put(Constants.integerValueOf(1069), "42000");
/*      */     
/*  475 */     mysqlToSql99State.put(Constants.integerValueOf(1070), "42000");
/*      */     
/*  477 */     mysqlToSql99State.put(Constants.integerValueOf(1071), "42000");
/*      */     
/*  479 */     mysqlToSql99State.put(Constants.integerValueOf(1072), "42000");
/*      */     
/*  481 */     mysqlToSql99State.put(Constants.integerValueOf(1073), "42000");
/*      */     
/*  483 */     mysqlToSql99State.put(Constants.integerValueOf(1074), "42000");
/*      */     
/*  485 */     mysqlToSql99State.put(Constants.integerValueOf(1075), "42000");
/*      */     
/*  487 */     mysqlToSql99State.put(Constants.integerValueOf(1080), "08S01");
/*      */     
/*  489 */     mysqlToSql99State.put(Constants.integerValueOf(1081), "08S01");
/*      */     
/*  491 */     mysqlToSql99State.put(Constants.integerValueOf(1082), "42S12");
/*      */     
/*  493 */     mysqlToSql99State.put(Constants.integerValueOf(1083), "42000");
/*      */     
/*  495 */     mysqlToSql99State.put(Constants.integerValueOf(1084), "42000");
/*      */     
/*  497 */     mysqlToSql99State.put(Constants.integerValueOf(1090), "42000");
/*      */     
/*  499 */     mysqlToSql99State.put(Constants.integerValueOf(1091), "42000");
/*      */     
/*  501 */     mysqlToSql99State.put(Constants.integerValueOf(1101), "42000");
/*      */     
/*  503 */     mysqlToSql99State.put(Constants.integerValueOf(1102), "42000");
/*      */     
/*  505 */     mysqlToSql99State.put(Constants.integerValueOf(1103), "42000");
/*      */     
/*  507 */     mysqlToSql99State.put(Constants.integerValueOf(1104), "42000");
/*      */     
/*  509 */     mysqlToSql99State.put(Constants.integerValueOf(1106), "42000");
/*      */     
/*  511 */     mysqlToSql99State.put(Constants.integerValueOf(1107), "42000");
/*      */     
/*  513 */     mysqlToSql99State.put(Constants.integerValueOf(1109), "42S02");
/*      */     
/*  515 */     mysqlToSql99State.put(Constants.integerValueOf(1110), "42000");
/*      */     
/*  517 */     mysqlToSql99State.put(Constants.integerValueOf(1112), "42000");
/*      */     
/*  519 */     mysqlToSql99State.put(Constants.integerValueOf(1113), "42000");
/*      */     
/*  521 */     mysqlToSql99State.put(Constants.integerValueOf(1115), "42000");
/*      */     
/*  523 */     mysqlToSql99State.put(Constants.integerValueOf(1118), "42000");
/*      */     
/*  525 */     mysqlToSql99State.put(Constants.integerValueOf(1120), "42000");
/*      */     
/*  527 */     mysqlToSql99State.put(Constants.integerValueOf(1121), "42000");
/*      */     
/*  529 */     mysqlToSql99State.put(Constants.integerValueOf(1131), "42000");
/*      */     
/*  531 */     mysqlToSql99State.put(Constants.integerValueOf(1132), "42000");
/*      */     
/*  533 */     mysqlToSql99State.put(Constants.integerValueOf(1133), "42000");
/*      */     
/*  535 */     mysqlToSql99State.put(Constants.integerValueOf(1136), "21S01");
/*      */     
/*  537 */     mysqlToSql99State.put(Constants.integerValueOf(1138), "42000");
/*      */     
/*  539 */     mysqlToSql99State.put(Constants.integerValueOf(1139), "42000");
/*      */     
/*  541 */     mysqlToSql99State.put(Constants.integerValueOf(1140), "42000");
/*      */     
/*  543 */     mysqlToSql99State.put(Constants.integerValueOf(1141), "42000");
/*      */     
/*  545 */     mysqlToSql99State.put(Constants.integerValueOf(1142), "42000");
/*      */     
/*  547 */     mysqlToSql99State.put(Constants.integerValueOf(1143), "42000");
/*      */     
/*  549 */     mysqlToSql99State.put(Constants.integerValueOf(1144), "42000");
/*      */     
/*  551 */     mysqlToSql99State.put(Constants.integerValueOf(1145), "42000");
/*      */     
/*  553 */     mysqlToSql99State.put(Constants.integerValueOf(1146), "42S02");
/*      */     
/*  555 */     mysqlToSql99State.put(Constants.integerValueOf(1147), "42000");
/*      */     
/*  557 */     mysqlToSql99State.put(Constants.integerValueOf(1148), "42000");
/*      */     
/*  559 */     mysqlToSql99State.put(Constants.integerValueOf(1149), "42000");
/*      */     
/*  561 */     mysqlToSql99State.put(Constants.integerValueOf(1152), "08S01");
/*      */     
/*  563 */     mysqlToSql99State.put(Constants.integerValueOf(1153), "08S01");
/*      */     
/*  565 */     mysqlToSql99State.put(Constants.integerValueOf(1154), "08S01");
/*      */     
/*  567 */     mysqlToSql99State.put(Constants.integerValueOf(1155), "08S01");
/*      */     
/*  569 */     mysqlToSql99State.put(Constants.integerValueOf(1156), "08S01");
/*      */     
/*  571 */     mysqlToSql99State.put(Constants.integerValueOf(1157), "08S01");
/*      */     
/*  573 */     mysqlToSql99State.put(Constants.integerValueOf(1158), "08S01");
/*      */     
/*  575 */     mysqlToSql99State.put(Constants.integerValueOf(1159), "08S01");
/*      */     
/*  577 */     mysqlToSql99State.put(Constants.integerValueOf(1160), "08S01");
/*      */     
/*  579 */     mysqlToSql99State.put(Constants.integerValueOf(1161), "08S01");
/*      */     
/*  581 */     mysqlToSql99State.put(Constants.integerValueOf(1162), "42000");
/*      */     
/*  583 */     mysqlToSql99State.put(Constants.integerValueOf(1163), "42000");
/*      */     
/*  585 */     mysqlToSql99State.put(Constants.integerValueOf(1164), "42000");
/*      */ 
/*      */ 
/*      */     
/*  589 */     mysqlToSql99State.put(Constants.integerValueOf(1166), "42000");
/*      */     
/*  591 */     mysqlToSql99State.put(Constants.integerValueOf(1167), "42000");
/*      */     
/*  593 */     mysqlToSql99State.put(Constants.integerValueOf(1169), "23000");
/*      */     
/*  595 */     mysqlToSql99State.put(Constants.integerValueOf(1170), "42000");
/*      */     
/*  597 */     mysqlToSql99State.put(Constants.integerValueOf(1171), "42000");
/*      */     
/*  599 */     mysqlToSql99State.put(Constants.integerValueOf(1172), "42000");
/*      */     
/*  601 */     mysqlToSql99State.put(Constants.integerValueOf(1173), "42000");
/*      */     
/*  603 */     mysqlToSql99State.put(Constants.integerValueOf(1177), "42000");
/*      */     
/*  605 */     mysqlToSql99State.put(Constants.integerValueOf(1178), "42000");
/*      */     
/*  607 */     mysqlToSql99State.put(Constants.integerValueOf(1179), "25000");
/*      */ 
/*      */     
/*  610 */     mysqlToSql99State.put(Constants.integerValueOf(1184), "08S01");
/*      */     
/*  612 */     mysqlToSql99State.put(Constants.integerValueOf(1189), "08S01");
/*      */     
/*  614 */     mysqlToSql99State.put(Constants.integerValueOf(1190), "08S01");
/*      */     
/*  616 */     mysqlToSql99State.put(Constants.integerValueOf(1203), "42000");
/*      */     
/*  618 */     mysqlToSql99State.put(Constants.integerValueOf(1207), "25000");
/*      */     
/*  620 */     mysqlToSql99State.put(Constants.integerValueOf(1211), "42000");
/*      */     
/*  622 */     mysqlToSql99State.put(Constants.integerValueOf(1213), "40001");
/*      */     
/*  624 */     mysqlToSql99State.put(Constants.integerValueOf(1216), "23000");
/*      */     
/*  626 */     mysqlToSql99State.put(Constants.integerValueOf(1217), "23000");
/*      */     
/*  628 */     mysqlToSql99State.put(Constants.integerValueOf(1218), "08S01");
/*      */     
/*  630 */     mysqlToSql99State.put(Constants.integerValueOf(1222), "21000");
/*      */ 
/*      */     
/*  633 */     mysqlToSql99State.put(Constants.integerValueOf(1226), "42000");
/*      */     
/*  635 */     mysqlToSql99State.put(Constants.integerValueOf(1230), "42000");
/*      */     
/*  637 */     mysqlToSql99State.put(Constants.integerValueOf(1231), "42000");
/*      */     
/*  639 */     mysqlToSql99State.put(Constants.integerValueOf(1232), "42000");
/*      */     
/*  641 */     mysqlToSql99State.put(Constants.integerValueOf(1234), "42000");
/*      */     
/*  643 */     mysqlToSql99State.put(Constants.integerValueOf(1235), "42000");
/*      */     
/*  645 */     mysqlToSql99State.put(Constants.integerValueOf(1239), "42000");
/*      */     
/*  647 */     mysqlToSql99State.put(Constants.integerValueOf(1241), "21000");
/*      */     
/*  649 */     mysqlToSql99State.put(Constants.integerValueOf(1242), "21000");
/*      */     
/*  651 */     mysqlToSql99State.put(Constants.integerValueOf(1247), "42S22");
/*      */     
/*  653 */     mysqlToSql99State.put(Constants.integerValueOf(1248), "42000");
/*      */     
/*  655 */     mysqlToSql99State.put(Constants.integerValueOf(1249), "01000");
/*      */     
/*  657 */     mysqlToSql99State.put(Constants.integerValueOf(1250), "42000");
/*      */     
/*  659 */     mysqlToSql99State.put(Constants.integerValueOf(1251), "08004");
/*      */     
/*  661 */     mysqlToSql99State.put(Constants.integerValueOf(1252), "42000");
/*      */     
/*  663 */     mysqlToSql99State.put(Constants.integerValueOf(1253), "42000");
/*      */     
/*  665 */     mysqlToSql99State.put(Constants.integerValueOf(1261), "01000");
/*      */     
/*  667 */     mysqlToSql99State.put(Constants.integerValueOf(1262), "01000");
/*      */     
/*  669 */     mysqlToSql99State.put(Constants.integerValueOf(1263), "01000");
/*      */     
/*  671 */     mysqlToSql99State.put(Constants.integerValueOf(1264), "01000");
/*      */     
/*  673 */     mysqlToSql99State.put(Constants.integerValueOf(1265), "01000");
/*      */     
/*  675 */     mysqlToSql99State.put(Constants.integerValueOf(1280), "42000");
/*      */     
/*  677 */     mysqlToSql99State.put(Constants.integerValueOf(1281), "42000");
/*      */     
/*  679 */     mysqlToSql99State.put(Constants.integerValueOf(1286), "42000");
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
/*  699 */   static SQLWarning convertShowWarningsToSQLWarnings(Connection connection) throws SQLException { return convertShowWarningsToSQLWarnings(connection, 0, false); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static SQLWarning convertShowWarningsToSQLWarnings(Connection connection, int warningCountIfKnown, boolean forTruncationOnly) throws SQLException {
/*  724 */     Statement stmt = null;
/*  725 */     ResultSet warnRs = null;
/*      */     
/*  727 */     SQLWarning currentWarning = null;
/*      */     
/*      */     try {
/*  730 */       if (warningCountIfKnown < 100) {
/*  731 */         stmt = connection.createStatement();
/*      */         
/*  733 */         if (stmt.getMaxRows() != 0) {
/*  734 */           stmt.setMaxRows(0);
/*      */         }
/*      */       } else {
/*      */         
/*  738 */         stmt = connection.createStatement(1003, 1007);
/*      */ 
/*      */         
/*  741 */         stmt.setFetchSize(-2147483648);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  751 */       warnRs = stmt.executeQuery("SHOW WARNINGS");
/*      */       
/*  753 */       while (warnRs.next()) {
/*  754 */         int code = warnRs.getInt("Code");
/*      */         
/*  756 */         if (forTruncationOnly) {
/*  757 */           if (code == 1265 || code == 1264) {
/*  758 */             DataTruncation newTruncation = new MysqlDataTruncation(warnRs.getString("Message"), false, false, false, false, false, code);
/*      */ 
/*      */             
/*  761 */             if (currentWarning == null) {
/*  762 */               currentWarning = newTruncation; continue;
/*      */             } 
/*  764 */             currentWarning.setNextWarning(newTruncation);
/*      */           } 
/*      */           continue;
/*      */         } 
/*  768 */         String level = warnRs.getString("Level");
/*  769 */         String message = warnRs.getString("Message");
/*      */         
/*  771 */         SQLWarning newWarning = new SQLWarning(message, mysqlToSqlState(code, connection.getUseSqlStateCodes()), code);
/*      */ 
/*      */ 
/*      */         
/*  775 */         if (currentWarning == null) {
/*  776 */           currentWarning = newWarning; continue;
/*      */         } 
/*  778 */         currentWarning.setNextWarning(newWarning);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  783 */       if (forTruncationOnly && currentWarning != null) {
/*  784 */         throw currentWarning;
/*      */       }
/*      */       
/*  787 */       return currentWarning;
/*      */     } finally {
/*  789 */       SQLException reThrow = null;
/*      */       
/*  791 */       if (warnRs != null) {
/*      */         try {
/*  793 */           warnRs.close();
/*  794 */         } catch (SQLException sqlEx) {
/*  795 */           reThrow = sqlEx;
/*      */         } 
/*      */       }
/*      */       
/*  799 */       if (stmt != null) {
/*      */         try {
/*  801 */           stmt.close();
/*  802 */         } catch (SQLException sqlEx) {
/*      */ 
/*      */ 
/*      */           
/*  806 */           reThrow = sqlEx;
/*      */         } 
/*      */       }
/*      */       
/*  810 */       if (reThrow != null) {
/*  811 */         throw reThrow;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void dumpSqlStatesMappingsAsXml() {
/*  817 */     allErrorNumbers = new TreeMap();
/*  818 */     Map mysqlErrorNumbersToNames = new HashMap();
/*      */     
/*  820 */     Integer errorNumber = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  826 */     mysqlErrorNumbers = mysqlToSql99State.keySet().iterator();
/*  827 */     while (mysqlErrorNumbers.hasNext()) {
/*  828 */       errorNumber = (Integer)mysqlErrorNumbers.next();
/*  829 */       allErrorNumbers.put(errorNumber, errorNumber);
/*      */     } 
/*      */     
/*  832 */     mysqlErrorNumbers = mysqlToSqlState.keySet().iterator();
/*  833 */     while (mysqlErrorNumbers.hasNext()) {
/*  834 */       errorNumber = (Integer)mysqlErrorNumbers.next();
/*  835 */       allErrorNumbers.put(errorNumber, errorNumber);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  841 */     Field[] possibleFields = MysqlErrorNumbers.class.getDeclaredFields();
/*      */ 
/*      */     
/*  844 */     for (i = 0; i < possibleFields.length; i++) {
/*  845 */       String fieldName = possibleFields[i].getName();
/*      */       
/*  847 */       if (fieldName.startsWith("ER_")) {
/*  848 */         mysqlErrorNumbersToNames.put(possibleFields[i].get(null), fieldName);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  853 */     System.out.println("<ErrorMappings>");
/*      */     
/*  855 */     Iterator allErrorNumbersIter = allErrorNumbers.keySet().iterator();
/*  856 */     while (allErrorNumbersIter.hasNext()) {
/*  857 */       errorNumber = (Integer)allErrorNumbersIter.next();
/*      */       
/*  859 */       String sql92State = mysqlToSql99(errorNumber.intValue());
/*  860 */       String oldSqlState = mysqlToXOpen(errorNumber.intValue());
/*      */       
/*  862 */       System.out.println("   <ErrorMapping mysqlErrorNumber=\"" + errorNumber + "\" mysqlErrorName=\"" + mysqlErrorNumbersToNames.get(errorNumber) + "\" legacySqlState=\"" + ((oldSqlState == null) ? "" : oldSqlState) + "\" sql92SqlState=\"" + ((sql92State == null) ? "" : sql92State) + "\"/>");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  871 */     System.out.println("</ErrorMappings>");
/*      */   }
/*      */ 
/*      */   
/*  875 */   static String get(String stateCode) { return (String)sqlStateMessages.get(stateCode); }
/*      */ 
/*      */   
/*      */   private static String mysqlToSql99(int errno) {
/*  879 */     Integer err = Constants.integerValueOf(errno);
/*      */     
/*  881 */     if (mysqlToSql99State.containsKey(err)) {
/*  882 */       return (String)mysqlToSql99State.get(err);
/*      */     }
/*      */     
/*  885 */     return "HY000";
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
/*      */   static String mysqlToSqlState(int errno, boolean useSql92States) {
/*  897 */     if (useSql92States) {
/*  898 */       return mysqlToSql99(errno);
/*      */     }
/*      */     
/*  901 */     return mysqlToXOpen(errno);
/*      */   }
/*      */   
/*      */   private static String mysqlToXOpen(int errno) {
/*  905 */     Integer err = Constants.integerValueOf(errno);
/*      */     
/*  907 */     if (mysqlToSqlState.containsKey(err)) {
/*  908 */       return (String)mysqlToSqlState.get(err);
/*      */     }
/*      */     
/*  911 */     return "S1000";
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
/*  927 */   public static SQLException createSQLException(String message, String sqlState, ExceptionInterceptor interceptor) { return createSQLException(message, sqlState, 0, interceptor); }
/*      */ 
/*      */ 
/*      */   
/*  931 */   public static SQLException createSQLException(String message, ExceptionInterceptor interceptor) { return createSQLException(message, interceptor, null); }
/*      */   
/*      */   public static SQLException createSQLException(String message, ExceptionInterceptor interceptor, Connection conn) {
/*  934 */     SQLException sqlEx = new SQLException(message);
/*      */     
/*  936 */     if (interceptor != null) {
/*  937 */       SQLException interceptedEx = interceptor.interceptException(sqlEx, conn);
/*      */       
/*  939 */       if (interceptedEx != null) {
/*  940 */         return interceptedEx;
/*      */       }
/*      */     } 
/*      */     
/*  944 */     return sqlEx;
/*      */   }
/*      */ 
/*      */   
/*  948 */   public static SQLException createSQLException(String message, String sqlState, Throwable cause, ExceptionInterceptor interceptor) { return createSQLException(message, sqlState, cause, interceptor, null); }
/*      */ 
/*      */   
/*      */   public static SQLException createSQLException(String message, String sqlState, Throwable cause, ExceptionInterceptor interceptor, Connection conn) {
/*  952 */     if (THROWABLE_INIT_CAUSE_METHOD == null && 
/*  953 */       cause != null) {
/*  954 */       message = message + " due to " + cause.toString();
/*      */     }
/*      */ 
/*      */     
/*  958 */     SQLException sqlEx = createSQLException(message, sqlState, interceptor);
/*      */     
/*  960 */     if (cause != null && THROWABLE_INIT_CAUSE_METHOD != null) {
/*      */       try {
/*  962 */         THROWABLE_INIT_CAUSE_METHOD.invoke(sqlEx, new Object[] { cause });
/*  963 */       } catch (Throwable t) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  969 */     if (interceptor != null) {
/*  970 */       SQLException interceptedEx = interceptor.interceptException(sqlEx, conn);
/*      */       
/*  972 */       if (interceptedEx != null) {
/*  973 */         return interceptedEx;
/*      */       }
/*      */     } 
/*      */     
/*  977 */     return sqlEx;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  982 */   public static SQLException createSQLException(String message, String sqlState, int vendorErrorCode, ExceptionInterceptor interceptor) { return createSQLException(message, sqlState, vendorErrorCode, false, interceptor); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  987 */   public static SQLException createSQLException(String message, String sqlState, int vendorErrorCode, boolean isTransient, ExceptionInterceptor interceptor) { return createSQLException(message, sqlState, vendorErrorCode, false, interceptor, null); }
/*      */ 
/*      */   
/*      */   public static SQLException createSQLException(String message, String sqlState, int vendorErrorCode, boolean isTransient, ExceptionInterceptor interceptor, Connection conn) {
/*      */     try {
/*  992 */       SQLException sqlEx = null;
/*      */       
/*  994 */       if (sqlState != null) {
/*  995 */         if (sqlState.startsWith("08")) {
/*  996 */           if (isTransient) {
/*  997 */             if (!Util.isJdbc4()) {
/*  998 */               MySQLTransientConnectionException mySQLTransientConnectionException = new MySQLTransientConnectionException(message, sqlState, vendorErrorCode);
/*      */             } else {
/*      */               
/* 1001 */               sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLTransientConnectionException", new Class[] { String.class, String.class, int.class }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1009 */           else if (!Util.isJdbc4()) {
/* 1010 */             MySQLNonTransientConnectionException mySQLNonTransientConnectionException = new MySQLNonTransientConnectionException(message, sqlState, vendorErrorCode);
/*      */           } else {
/*      */             
/* 1013 */             sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException", new Class[] { String.class, String.class, int.class }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/* 1020 */         else if (sqlState.startsWith("22")) {
/* 1021 */           if (!Util.isJdbc4()) {
/* 1022 */             MySQLDataException mySQLDataException = new MySQLDataException(message, sqlState, vendorErrorCode);
/*      */           } else {
/*      */             
/* 1025 */             sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLDataException", new Class[] { String.class, String.class, int.class }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1033 */         else if (sqlState.startsWith("23")) {
/*      */           
/* 1035 */           if (!Util.isJdbc4()) {
/* 1036 */             MySQLIntegrityConstraintViolationException mySQLIntegrityConstraintViolationException = new MySQLIntegrityConstraintViolationException(message, sqlState, vendorErrorCode);
/*      */           } else {
/*      */             
/* 1039 */             sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException", new Class[] { String.class, String.class, int.class }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1047 */         else if (sqlState.startsWith("42")) {
/* 1048 */           if (!Util.isJdbc4()) {
/* 1049 */             MySQLSyntaxErrorException mySQLSyntaxErrorException = new MySQLSyntaxErrorException(message, sqlState, vendorErrorCode);
/*      */           } else {
/*      */             
/* 1052 */             sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException", new Class[] { String.class, String.class, int.class }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/* 1059 */         else if (sqlState.startsWith("40")) {
/* 1060 */           if (!Util.isJdbc4()) {
/* 1061 */             MySQLTransactionRollbackException mySQLTransactionRollbackException = new MySQLTransactionRollbackException(message, sqlState, vendorErrorCode);
/*      */           } else {
/*      */             
/* 1064 */             sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException", new Class[] { String.class, String.class, int.class }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1073 */           sqlEx = new SQLException(message, sqlState, vendorErrorCode);
/*      */         } 
/*      */       } else {
/* 1076 */         sqlEx = new SQLException(message, sqlState, vendorErrorCode);
/*      */       } 
/*      */       
/* 1079 */       if (interceptor != null) {
/* 1080 */         SQLException interceptedEx = interceptor.interceptException(sqlEx, conn);
/*      */         
/* 1082 */         if (interceptedEx != null) {
/* 1083 */           return interceptedEx;
/*      */         }
/*      */       } 
/*      */       
/* 1087 */       return sqlEx;
/* 1088 */     } catch (SQLException sqlEx) {
/* 1089 */       SQLException unexpectedEx = new SQLException("Unable to create correct SQLException class instance, error class/codes may be incorrect. Reason: " + Util.stackTraceToString(sqlEx), "S1000");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1094 */       if (interceptor != null) {
/* 1095 */         SQLException interceptedEx = interceptor.interceptException(unexpectedEx, conn);
/*      */         
/* 1097 */         if (interceptedEx != null) {
/* 1098 */           return interceptedEx;
/*      */         }
/*      */       } 
/*      */       
/* 1102 */       return unexpectedEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SQLException createCommunicationsException(MySQLConnection conn, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Exception underlyingException, ExceptionInterceptor interceptor) {
/* 1109 */     SQLException exToReturn = null;
/*      */     
/* 1111 */     if (!Util.isJdbc4()) {
/* 1112 */       exToReturn = new CommunicationsException(conn, lastPacketSentTimeMs, lastPacketReceivedTimeMs, underlyingException);
/*      */     } else {
/*      */       
/*      */       try {
/* 1116 */         exToReturn = (SQLException)Util.handleNewInstance(JDBC_4_COMMUNICATIONS_EXCEPTION_CTOR, new Object[] { conn, Constants.longValueOf(lastPacketSentTimeMs), Constants.longValueOf(lastPacketReceivedTimeMs), underlyingException }, interceptor);
/*      */       }
/* 1118 */       catch (SQLException sqlEx) {
/*      */ 
/*      */         
/* 1121 */         return sqlEx;
/*      */       } 
/*      */     } 
/*      */     
/* 1125 */     if (THROWABLE_INIT_CAUSE_METHOD != null && underlyingException != null) {
/*      */       try {
/* 1127 */         THROWABLE_INIT_CAUSE_METHOD.invoke(exToReturn, new Object[] { underlyingException });
/* 1128 */       } catch (Throwable t) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1134 */     if (interceptor != null) {
/* 1135 */       SQLException interceptedEx = interceptor.interceptException(exToReturn, conn);
/*      */       
/* 1137 */       if (interceptedEx != null) {
/* 1138 */         return interceptedEx;
/*      */       }
/*      */     } 
/*      */     
/* 1142 */     return exToReturn;
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
/*      */   public static String createLinkFailureMessageBasedOnHeuristics(MySQLConnection conn, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Exception underlyingException, boolean streamingResultSetInPlay) {
/* 1162 */     long serverTimeoutSeconds = 0L;
/* 1163 */     boolean isInteractiveClient = false;
/*      */     
/* 1165 */     if (conn != null) {
/* 1166 */       isInteractiveClient = conn.getInteractiveClient();
/*      */       
/* 1168 */       String serverTimeoutSecondsStr = null;
/*      */       
/* 1170 */       if (isInteractiveClient) {
/* 1171 */         serverTimeoutSecondsStr = conn.getServerVariable("interactive_timeout");
/*      */       } else {
/*      */         
/* 1174 */         serverTimeoutSecondsStr = conn.getServerVariable("wait_timeout");
/*      */       } 
/*      */ 
/*      */       
/* 1178 */       if (serverTimeoutSecondsStr != null) {
/*      */         try {
/* 1180 */           serverTimeoutSeconds = Long.parseLong(serverTimeoutSecondsStr);
/*      */         }
/* 1182 */         catch (NumberFormatException nfe) {
/* 1183 */           serverTimeoutSeconds = 0L;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1188 */     StringBuffer exceptionMessageBuf = new StringBuffer();
/*      */     
/* 1190 */     if (lastPacketSentTimeMs == 0L) {
/* 1191 */       lastPacketSentTimeMs = System.currentTimeMillis();
/*      */     }
/*      */     
/* 1194 */     long timeSinceLastPacket = (System.currentTimeMillis() - lastPacketSentTimeMs) / 1000L;
/* 1195 */     long timeSinceLastPacketMs = System.currentTimeMillis() - lastPacketSentTimeMs;
/* 1196 */     long timeSinceLastPacketReceivedMs = System.currentTimeMillis() - lastPacketReceivedTimeMs;
/*      */     
/* 1198 */     int dueToTimeout = 0;
/*      */     
/* 1200 */     StringBuffer timeoutMessageBuf = null;
/*      */     
/* 1202 */     if (streamingResultSetInPlay) {
/* 1203 */       exceptionMessageBuf.append(Messages.getString("CommunicationsException.ClientWasStreaming"));
/*      */     } else {
/*      */       
/* 1206 */       if (serverTimeoutSeconds != 0L) {
/* 1207 */         if (timeSinceLastPacket > serverTimeoutSeconds) {
/* 1208 */           dueToTimeout = 1;
/*      */           
/* 1210 */           timeoutMessageBuf = new StringBuffer();
/*      */           
/* 1212 */           timeoutMessageBuf.append(Messages.getString("CommunicationsException.2"));
/*      */ 
/*      */           
/* 1215 */           if (!isInteractiveClient) {
/* 1216 */             timeoutMessageBuf.append(Messages.getString("CommunicationsException.3"));
/*      */           } else {
/*      */             
/* 1219 */             timeoutMessageBuf.append(Messages.getString("CommunicationsException.4"));
/*      */           }
/*      */         
/*      */         }
/*      */       
/* 1224 */       } else if (timeSinceLastPacket > 28800L) {
/* 1225 */         dueToTimeout = 2;
/*      */         
/* 1227 */         timeoutMessageBuf = new StringBuffer();
/*      */         
/* 1229 */         timeoutMessageBuf.append(Messages.getString("CommunicationsException.5"));
/*      */         
/* 1231 */         timeoutMessageBuf.append(Messages.getString("CommunicationsException.6"));
/*      */         
/* 1233 */         timeoutMessageBuf.append(Messages.getString("CommunicationsException.7"));
/*      */         
/* 1235 */         timeoutMessageBuf.append(Messages.getString("CommunicationsException.8"));
/*      */       } 
/*      */ 
/*      */       
/* 1239 */       if (dueToTimeout == 1 || dueToTimeout == 2) {
/*      */ 
/*      */         
/* 1242 */         if (lastPacketReceivedTimeMs != 0L) {
/* 1243 */           Object[] timingInfo = { new Long(timeSinceLastPacketReceivedMs), new Long(timeSinceLastPacketMs) };
/*      */ 
/*      */ 
/*      */           
/* 1247 */           exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfo", timingInfo));
/*      */         }
/*      */         else {
/*      */           
/* 1251 */           exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfoNoRecv", new Object[] { new Long(timeSinceLastPacketMs) }));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1256 */         if (timeoutMessageBuf != null) {
/* 1257 */           exceptionMessageBuf.append(timeoutMessageBuf);
/*      */         }
/*      */         
/* 1260 */         exceptionMessageBuf.append(Messages.getString("CommunicationsException.11"));
/*      */         
/* 1262 */         exceptionMessageBuf.append(Messages.getString("CommunicationsException.12"));
/*      */         
/* 1264 */         exceptionMessageBuf.append(Messages.getString("CommunicationsException.13"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1273 */       else if (underlyingException instanceof java.net.BindException) {
/* 1274 */         if (conn.getLocalSocketAddress() != null && !Util.interfaceExists(conn.getLocalSocketAddress())) {
/*      */ 
/*      */           
/* 1277 */           exceptionMessageBuf.append(Messages.getString("CommunicationsException.LocalSocketAddressNotAvailable"));
/*      */         }
/*      */         else {
/*      */           
/* 1281 */           exceptionMessageBuf.append(Messages.getString("CommunicationsException.TooManyClientConnections"));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1288 */     if (exceptionMessageBuf.length() == 0) {
/*      */       
/* 1290 */       exceptionMessageBuf.append(Messages.getString("CommunicationsException.20"));
/*      */ 
/*      */       
/* 1293 */       if (THROWABLE_INIT_CAUSE_METHOD == null && underlyingException != null) {
/*      */         
/* 1295 */         exceptionMessageBuf.append(Messages.getString("CommunicationsException.21"));
/*      */         
/* 1297 */         exceptionMessageBuf.append(Util.stackTraceToString(underlyingException));
/*      */       } 
/*      */ 
/*      */       
/* 1301 */       if (conn != null && conn.getMaintainTimeStats() && !conn.getParanoid()) {
/*      */         
/* 1303 */         exceptionMessageBuf.append("\n\n");
/* 1304 */         if (lastPacketReceivedTimeMs != 0L) {
/* 1305 */           Object[] timingInfo = { new Long(timeSinceLastPacketReceivedMs), new Long(timeSinceLastPacketMs) };
/*      */ 
/*      */ 
/*      */           
/* 1309 */           exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfo", timingInfo));
/*      */         }
/*      */         else {
/*      */           
/* 1313 */           exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfoNoRecv", new Object[] { new Long(timeSinceLastPacketMs) }));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1320 */     return exceptionMessageBuf.toString();
/*      */   }
/*      */   
/*      */   public static SQLException notImplemented() {
/* 1324 */     if (Util.isJdbc4()) {
/*      */       try {
/* 1326 */         return (SQLException)Class.forName("java.sql.SQLFeatureNotSupportedException").newInstance();
/*      */       
/*      */       }
/* 1329 */       catch (Throwable t) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1334 */     return new NotImplemented();
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\SQLError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */