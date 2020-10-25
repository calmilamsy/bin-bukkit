/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   protected static Method systemNanoTimeMethod;
/*     */   private static Method CAST_METHOD;
/*     */   private static final TimeZone DEFAULT_TIMEZONE;
/*     */   private static Util enclosingInstance;
/*     */   private static boolean isJdbc4;
/*     */   private static boolean isColdFusion;
/*     */   
/*     */   static  {
/*     */     try {
/*  55 */       systemNanoTimeMethod = System.class.getMethod("nanoTime", (Class[])null);
/*  56 */     } catch (SecurityException e) {
/*  57 */       systemNanoTimeMethod = null;
/*  58 */     } catch (NoSuchMethodException e) {
/*  59 */       systemNanoTimeMethod = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     DEFAULT_TIMEZONE = TimeZone.getDefault();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     enclosingInstance = new Util();
/*     */     
/*  90 */     isJdbc4 = false;
/*     */     
/*  92 */     isColdFusion = false;
/*     */ 
/*     */     
/*     */     try {
/*  96 */       CAST_METHOD = Class.class.getMethod("cast", new Class[] { Object.class });
/*     */     }
/*  98 */     catch (Throwable e) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 103 */       Class.forName("java.sql.NClob");
/* 104 */       isJdbc4 = true;
/* 105 */     } catch (Throwable e) {
/* 106 */       isJdbc4 = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     String str = stackTraceToString(new Throwable());
/*     */     
/* 118 */     if (str != null) {
/* 119 */       isColdFusion = (str.indexOf("coldfusion") != -1);
/*     */     } else {
/* 121 */       isColdFusion = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean nanoTimeAvailable() { return (systemNanoTimeMethod != null); }
/*     */ 
/*     */   
/* 129 */   public static boolean isJdbc4() { return isJdbc4; }
/*     */   static final TimeZone getDefaultTimeZone() { return (TimeZone)DEFAULT_TIMEZONE.clone(); }
/*     */   class RandStructcture {
/*     */     long maxValue; double maxValueDbl; long seed1; long seed2; }
/* 133 */   public static boolean isColdFusion() { return isColdFusion; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String newCrypt(String password, String seed) {
/* 141 */     if (password == null || password.length() == 0) {
/* 142 */       return password;
/*     */     }
/*     */     
/* 145 */     long[] pw = newHash(seed);
/* 146 */     long[] msg = newHash(password);
/* 147 */     long max = 1073741823L;
/* 148 */     long seed1 = (pw[0] ^ msg[0]) % max;
/* 149 */     long seed2 = (pw[1] ^ msg[1]) % max;
/* 150 */     char[] chars = new char[seed.length()];
/*     */     
/* 152 */     for (i = 0; i < seed.length(); i++) {
/* 153 */       seed1 = (seed1 * 3L + seed2) % max;
/* 154 */       seed2 = (seed1 + seed2 + 33L) % max;
/* 155 */       double d = seed1 / max;
/* 156 */       byte b = (byte)(int)Math.floor(d * 31.0D + 64.0D);
/* 157 */       chars[i] = (char)b;
/*     */     } 
/*     */     
/* 160 */     seed1 = (seed1 * 3L + seed2) % max;
/* 161 */     seed2 = (seed1 + seed2 + 33L) % max;
/* 162 */     double d = seed1 / max;
/* 163 */     byte b = (byte)(int)Math.floor(d * 31.0D);
/*     */     
/* 165 */     for (int i = 0; i < seed.length(); i++) {
/* 166 */       chars[i] = (char)(chars[i] ^ (char)b);
/*     */     }
/*     */     
/* 169 */     return new String(chars);
/*     */   }
/*     */   
/*     */   static long[] newHash(String password) {
/* 173 */     long nr = 1345345333L;
/* 174 */     long add = 7L;
/* 175 */     long nr2 = 305419889L;
/*     */ 
/*     */     
/* 178 */     for (int i = 0; i < password.length(); i++) {
/* 179 */       if (password.charAt(i) != ' ' && password.charAt(i) != '\t') {
/*     */ 
/*     */ 
/*     */         
/* 183 */         long tmp = (0xFF & password.charAt(i));
/* 184 */         nr ^= ((nr & 0x3FL) + add) * tmp + (nr << 8);
/* 185 */         nr2 += (nr2 << 8 ^ nr);
/* 186 */         add += tmp;
/*     */       } 
/*     */     } 
/* 189 */     long[] result = new long[2];
/* 190 */     result[0] = nr & 0x7FFFFFFFL;
/* 191 */     result[1] = nr2 & 0x7FFFFFFFL;
/*     */     
/* 193 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String oldCrypt(String password, String seed) {
/* 201 */     long max = 33554431L;
/*     */ 
/*     */ 
/*     */     
/* 205 */     if (password == null || password.length() == 0) {
/* 206 */       return password;
/*     */     }
/*     */     
/* 209 */     long hp = oldHash(seed);
/* 210 */     long hm = oldHash(password);
/*     */     
/* 212 */     long nr = hp ^ hm;
/* 213 */     nr %= max;
/* 214 */     long s1 = nr;
/* 215 */     long s2 = nr / 2L;
/*     */     
/* 217 */     char[] chars = new char[seed.length()];
/*     */     
/* 219 */     for (int i = 0; i < seed.length(); i++) {
/* 220 */       s1 = (s1 * 3L + s2) % max;
/* 221 */       s2 = (s1 + s2 + 33L) % max;
/* 222 */       double d = s1 / max;
/* 223 */       byte b = (byte)(int)Math.floor(d * 31.0D + 64.0D);
/* 224 */       chars[i] = (char)b;
/*     */     } 
/*     */     
/* 227 */     return new String(chars);
/*     */   }
/*     */   
/*     */   static long oldHash(String password) {
/* 231 */     long nr = 1345345333L;
/* 232 */     long nr2 = 7L;
/*     */ 
/*     */     
/* 235 */     for (int i = 0; i < password.length(); i++) {
/* 236 */       if (password.charAt(i) != ' ' && password.charAt(i) != '\t') {
/*     */ 
/*     */ 
/*     */         
/* 240 */         long tmp = password.charAt(i);
/* 241 */         nr ^= ((nr & 0x3FL) + nr2) * tmp + (nr << 8);
/* 242 */         nr2 += tmp;
/*     */       } 
/*     */     } 
/* 245 */     return nr & 0x7FFFFFFFL;
/*     */   }
/*     */   
/*     */   private static RandStructcture randomInit(long seed1, long seed2) {
/* 249 */     enclosingInstance.getClass(); RandStructcture randStruct = new RandStructcture(enclosingInstance);
/*     */     
/* 251 */     randStruct.maxValue = 1073741823L;
/* 252 */     randStruct.maxValueDbl = randStruct.maxValue;
/* 253 */     randStruct.seed1 = seed1 % randStruct.maxValue;
/* 254 */     randStruct.seed2 = seed2 % randStruct.maxValue;
/*     */     
/* 256 */     return randStruct;
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
/*     */   public static Object readObject(ResultSet resultSet, int index) throws Exception {
/* 274 */     ObjectInputStream objIn = new ObjectInputStream(resultSet.getBinaryStream(index));
/*     */     
/* 276 */     Object obj = objIn.readObject();
/* 277 */     objIn.close();
/*     */     
/* 279 */     return obj;
/*     */   }
/*     */   
/*     */   private static double rnd(RandStructcture randStruct) {
/* 283 */     randStruct.seed1 = (randStruct.seed1 * 3L + randStruct.seed2) % randStruct.maxValue;
/*     */     
/* 285 */     randStruct.seed2 = (randStruct.seed1 + randStruct.seed2 + 33L) % randStruct.maxValue;
/*     */ 
/*     */     
/* 288 */     return randStruct.seed1 / randStruct.maxValueDbl;
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
/*     */   public static String scramble(String message, String password) {
/* 304 */     byte[] to = new byte[8];
/* 305 */     String val = "";
/*     */     
/* 307 */     message = message.substring(0, 8);
/*     */     
/* 309 */     if (password != null && password.length() > 0) {
/* 310 */       long[] hashPass = newHash(password);
/* 311 */       long[] hashMessage = newHash(message);
/*     */       
/* 313 */       RandStructcture randStruct = randomInit(hashPass[0] ^ hashMessage[0], hashPass[1] ^ hashMessage[1]);
/*     */ 
/*     */       
/* 316 */       int msgPos = 0;
/* 317 */       int msgLength = message.length();
/* 318 */       int toPos = 0;
/*     */       
/* 320 */       while (msgPos++ < msgLength) {
/* 321 */         to[toPos++] = (byte)(int)(Math.floor(rnd(randStruct) * 31.0D) + 64.0D);
/*     */       }
/*     */ 
/*     */       
/* 325 */       byte extra = (byte)(int)Math.floor(rnd(randStruct) * 31.0D);
/*     */       
/* 327 */       for (int i = 0; i < to.length; i++) {
/* 328 */         to[i] = (byte)(to[i] ^ extra);
/*     */       }
/*     */       
/* 331 */       val = new String(to);
/*     */     } 
/*     */     
/* 334 */     return val;
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
/*     */   public static String stackTraceToString(Throwable ex) {
/* 350 */     StringBuffer traceBuf = new StringBuffer();
/* 351 */     traceBuf.append(Messages.getString("Util.1"));
/*     */     
/* 353 */     if (ex != null) {
/* 354 */       traceBuf.append(ex.getClass().getName());
/*     */       
/* 356 */       String message = ex.getMessage();
/*     */       
/* 358 */       if (message != null) {
/* 359 */         traceBuf.append(Messages.getString("Util.2"));
/* 360 */         traceBuf.append(message);
/*     */       } 
/*     */       
/* 363 */       StringWriter out = new StringWriter();
/*     */       
/* 365 */       PrintWriter printOut = new PrintWriter(out);
/*     */       
/* 367 */       ex.printStackTrace(printOut);
/*     */       
/* 369 */       traceBuf.append(Messages.getString("Util.3"));
/* 370 */       traceBuf.append(out.toString());
/*     */     } 
/*     */     
/* 373 */     traceBuf.append(Messages.getString("Util.4"));
/*     */     
/* 375 */     return traceBuf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getInstance(String className, Class[] argTypes, Object[] args, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*     */     try {
/* 382 */       return handleNewInstance(Class.forName(className).getConstructor(argTypes), args, exceptionInterceptor);
/*     */     }
/* 384 */     catch (SecurityException e) {
/* 385 */       throw SQLError.createSQLException("Can't instantiate required class", "S1000", e, exceptionInterceptor);
/*     */     
/*     */     }
/* 388 */     catch (NoSuchMethodException e) {
/* 389 */       throw SQLError.createSQLException("Can't instantiate required class", "S1000", e, exceptionInterceptor);
/*     */     
/*     */     }
/* 392 */     catch (ClassNotFoundException e) {
/* 393 */       throw SQLError.createSQLException("Can't instantiate required class", "S1000", e, exceptionInterceptor);
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
/*     */   
/*     */   public static final Object handleNewInstance(Constructor ctor, Object[] args, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*     */     try {
/* 407 */       return ctor.newInstance(args);
/* 408 */     } catch (IllegalArgumentException e) {
/* 409 */       throw SQLError.createSQLException("Can't instantiate required class", "S1000", e, exceptionInterceptor);
/*     */     
/*     */     }
/* 412 */     catch (InstantiationException e) {
/* 413 */       throw SQLError.createSQLException("Can't instantiate required class", "S1000", e, exceptionInterceptor);
/*     */     
/*     */     }
/* 416 */     catch (IllegalAccessException e) {
/* 417 */       throw SQLError.createSQLException("Can't instantiate required class", "S1000", e, exceptionInterceptor);
/*     */     
/*     */     }
/* 420 */     catch (InvocationTargetException e) {
/* 421 */       Throwable target = e.getTargetException();
/*     */       
/* 423 */       if (target instanceof SQLException) {
/* 424 */         throw (SQLException)target;
/*     */       }
/*     */       
/* 427 */       if (target instanceof ExceptionInInitializerError) {
/* 428 */         target = ((ExceptionInInitializerError)target).getException();
/*     */       }
/*     */       
/* 431 */       throw SQLError.createSQLException(target.toString(), "S1000", exceptionInterceptor);
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
/*     */ 
/*     */   
/*     */   public static boolean interfaceExists(String hostname) {
/*     */     try {
/* 446 */       Class networkInterfaceClass = Class.forName("java.net.NetworkInterface"); return 
/*     */         
/* 448 */         (networkInterfaceClass.getMethod("getByName", (Class[])null).invoke(networkInterfaceClass, new Object[] { hostname }) != null);
/*     */     }
/* 450 */     catch (Throwable t) {
/* 451 */       return false;
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
/*     */   
/*     */   public static Object cast(Object invokeOn, Object toCast) {
/* 464 */     if (CAST_METHOD != null) {
/*     */       try {
/* 466 */         return CAST_METHOD.invoke(invokeOn, new Object[] { toCast });
/* 467 */       } catch (Throwable t) {
/* 468 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 472 */     return null;
/*     */   }
/*     */   
/*     */   public static long getCurrentTimeNanosOrMillis() {
/* 476 */     if (systemNanoTimeMethod != null) {
/*     */       try {
/* 478 */         return ((Long)systemNanoTimeMethod.invoke(null, (Object[])null)).longValue();
/*     */       }
/* 480 */       catch (IllegalArgumentException e) {
/*     */       
/* 482 */       } catch (IllegalAccessException e) {
/*     */       
/* 484 */       } catch (InvocationTargetException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 489 */     return System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resultSetToMap(Map mappedValues, ResultSet rs) throws SQLException {
/* 494 */     while (rs.next()) {
/* 495 */       mappedValues.put(rs.getObject(1), rs.getObject(2));
/*     */     }
/*     */   }
/*     */   
/*     */   public static Map calculateDifferences(Map map1, Map map2) {
/* 500 */     Map diffMap = new HashMap();
/*     */     
/* 502 */     Iterator map1Entries = map1.entrySet().iterator();
/*     */     
/* 504 */     while (map1Entries.hasNext()) {
/* 505 */       Map.Entry entry = (Map.Entry)map1Entries.next();
/* 506 */       Object key = entry.getKey();
/*     */       
/* 508 */       Number value1 = null;
/* 509 */       Number value2 = null;
/*     */       
/* 511 */       if (entry.getValue() instanceof Number) {
/*     */         
/* 513 */         value1 = (Number)entry.getValue();
/* 514 */         value2 = (Number)map2.get(key);
/*     */       } else {
/*     */         try {
/* 517 */           value1 = new Double(entry.getValue().toString());
/* 518 */           value2 = new Double(map2.get(key).toString());
/* 519 */         } catch (NumberFormatException nfe) {
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */       
/* 524 */       if (value1.equals(value2)) {
/*     */         continue;
/*     */       }
/*     */       
/* 528 */       if (value1 instanceof Byte) {
/* 529 */         diffMap.put(key, new Byte((byte)(((Byte)value2).byteValue() - ((Byte)value1).byteValue())));
/*     */         continue;
/*     */       } 
/* 532 */       if (value1 instanceof Short) {
/* 533 */         diffMap.put(key, new Short((short)(((Short)value2).shortValue() - ((Short)value1).shortValue()))); continue;
/*     */       } 
/* 535 */       if (value1 instanceof Integer) {
/* 536 */         diffMap.put(key, new Integer(((Integer)value2).intValue() - ((Integer)value1).intValue()));
/*     */         continue;
/*     */       } 
/* 539 */       if (value1 instanceof Long) {
/* 540 */         diffMap.put(key, new Long(((Long)value2).longValue() - ((Long)value1).longValue()));
/*     */         continue;
/*     */       } 
/* 543 */       if (value1 instanceof Float) {
/* 544 */         diffMap.put(key, new Float(((Float)value2).floatValue() - ((Float)value1).floatValue())); continue;
/*     */       } 
/* 546 */       if (value1 instanceof Double) {
/* 547 */         diffMap.put(key, new Double((((Double)value2).shortValue() - ((Double)value1).shortValue())));
/*     */         continue;
/*     */       } 
/* 550 */       if (value1 instanceof BigDecimal) {
/* 551 */         diffMap.put(key, ((BigDecimal)value2).subtract((BigDecimal)value1)); continue;
/*     */       } 
/* 553 */       if (value1 instanceof BigInteger) {
/* 554 */         diffMap.put(key, ((BigInteger)value2).subtract((BigInteger)value1));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 559 */     return diffMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List loadExtensions(Connection conn, Properties props, String extensionClassNames, String errorMessageKey, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/* 565 */     List extensionList = new LinkedList();
/*     */     
/* 567 */     List interceptorsToCreate = StringUtils.split(extensionClassNames, ",", true);
/*     */ 
/*     */     
/* 570 */     Iterator iter = interceptorsToCreate.iterator();
/*     */     
/* 572 */     String className = null;
/*     */     
/*     */     try {
/* 575 */       while (iter.hasNext()) {
/* 576 */         className = iter.next().toString();
/* 577 */         Extension extensionInstance = (Extension)Class.forName(className).newInstance();
/*     */         
/* 579 */         extensionInstance.init(conn, props);
/*     */         
/* 581 */         extensionList.add(extensionInstance);
/*     */       } 
/* 583 */     } catch (Throwable t) {
/* 584 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString(errorMessageKey, new Object[] { className }), exceptionInterceptor);
/*     */       
/* 586 */       sqlEx.initCause(t);
/*     */       
/* 588 */       throw sqlEx;
/*     */     } 
/*     */     
/* 591 */     return extensionList;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */