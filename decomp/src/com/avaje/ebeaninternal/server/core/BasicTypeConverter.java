/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.Date;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BasicTypeConverter
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7691463236204070311L;
/*     */   public static final int UTIL_CALENDAR = -999998986;
/*     */   public static final int UTIL_DATE = -999998988;
/*     */   public static final int MATH_BIGINTEGER = -999998987;
/*     */   public static final int ENUM = -999998989;
/*     */   
/*     */   public static Object convert(Object value, int toDataType) {
/*     */     try {
/*  75 */       switch (toDataType) {
/*     */         case -999998988:
/*  77 */           return toUtilDate(value);
/*     */         
/*     */         case -999998986:
/*  80 */           return toCalendar(value);
/*     */         
/*     */         case -5:
/*  83 */           return toLong(value);
/*     */         
/*     */         case 4:
/*  86 */           return toInteger(value);
/*     */         
/*     */         case -7:
/*  89 */           return toBoolean(value);
/*     */         
/*     */         case -6:
/*  92 */           return toByte(value);
/*     */         
/*     */         case 5:
/*  95 */           return toShort(value);
/*     */         
/*     */         case 2:
/*  98 */           return toBigDecimal(value);
/*     */         
/*     */         case 3:
/* 101 */           return toBigDecimal(value);
/*     */         
/*     */         case 7:
/* 104 */           return toFloat(value);
/*     */         
/*     */         case 8:
/* 107 */           return toDouble(value);
/*     */         
/*     */         case 6:
/* 110 */           return toDouble(value);
/*     */         
/*     */         case 16:
/* 113 */           return toBoolean(value);
/*     */         
/*     */         case 93:
/* 116 */           return toTimestamp(value);
/*     */         
/*     */         case 91:
/* 119 */           return toDate(value);
/*     */         
/*     */         case 12:
/* 122 */           return toString(value);
/*     */         
/*     */         case 1:
/* 125 */           return toString(value);
/*     */         
/*     */         case 1111:
/* 128 */           return value;
/*     */         
/*     */         case 2000:
/* 131 */           return value;
/*     */         
/*     */         case -4:
/*     */         case -2:
/*     */         case 2004:
/* 136 */           return value;
/*     */         
/*     */         case -1:
/*     */         case 2005:
/* 140 */           return value;
/*     */       } 
/*     */       
/* 143 */       String msg = "Unhandled data type [" + toDataType + "] converting [" + value + "]";
/* 144 */       throw new RuntimeException(msg);
/*     */     
/*     */     }
/* 147 */     catch (ClassCastException e) {
/* 148 */       String m = "ClassCastException converting to data type [" + toDataType + "] value [" + value + "]";
/* 149 */       throw new RuntimeException(m);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(Object value) {
/* 158 */     if (value == null) {
/* 159 */       return null;
/*     */     }
/* 161 */     if (value instanceof String) {
/* 162 */       return (String)value;
/*     */     }
/* 164 */     if (value instanceof char[]) {
/* 165 */       return String.valueOf((char[])value);
/*     */     }
/*     */     
/* 168 */     return value.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Boolean toBoolean(Object value, String dbTrueValue) {
/* 177 */     if (value == null) {
/* 178 */       return null;
/*     */     }
/* 180 */     if (value instanceof Boolean) {
/* 181 */       return (Boolean)value;
/*     */     }
/* 183 */     String s = value.toString();
/* 184 */     return Boolean.valueOf(s.equalsIgnoreCase(dbTrueValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Boolean toBoolean(Object value) {
/* 193 */     if (value == null) {
/* 194 */       return null;
/*     */     }
/* 196 */     if (value instanceof Boolean) {
/* 197 */       return (Boolean)value;
/*     */     }
/*     */     
/* 200 */     return Boolean.valueOf(value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UUID toUUID(Object value) {
/* 208 */     if (value == null) {
/* 209 */       return null;
/*     */     }
/* 211 */     if (value instanceof String) {
/* 212 */       return UUID.fromString((String)value);
/*     */     }
/* 214 */     return (UUID)value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigDecimal toBigDecimal(Object value) {
/* 223 */     if (value == null) {
/* 224 */       return null;
/*     */     }
/* 226 */     if (value instanceof BigDecimal) {
/* 227 */       return (BigDecimal)value;
/*     */     }
/* 229 */     return new BigDecimal(value.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static Float toFloat(Object value) {
/* 234 */     if (value == null) {
/* 235 */       return null;
/*     */     }
/* 237 */     if (value instanceof Float) {
/* 238 */       return (Float)value;
/*     */     }
/* 240 */     if (value instanceof Number) {
/* 241 */       return Float.valueOf(((Number)value).floatValue());
/*     */     }
/* 243 */     return Float.valueOf(value.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static Short toShort(Object value) {
/* 248 */     if (value == null) {
/* 249 */       return null;
/*     */     }
/* 251 */     if (value instanceof Short) {
/* 252 */       return (Short)value;
/*     */     }
/* 254 */     if (value instanceof Number) {
/* 255 */       return Short.valueOf(((Number)value).shortValue());
/*     */     }
/* 257 */     return Short.valueOf(value.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static Byte toByte(Object value) {
/* 262 */     if (value == null) {
/* 263 */       return null;
/*     */     }
/* 265 */     if (value instanceof Byte) {
/* 266 */       return (Byte)value;
/*     */     }
/* 268 */     return Byte.valueOf(value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Integer toInteger(Object value) {
/* 277 */     if (value == null) {
/* 278 */       return null;
/*     */     }
/* 280 */     if (value instanceof Integer) {
/* 281 */       return (Integer)value;
/*     */     }
/* 283 */     if (value instanceof Number) {
/* 284 */       return Integer.valueOf(((Number)value).intValue());
/*     */     }
/* 286 */     return Integer.valueOf(value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Long toLong(Object value) {
/* 294 */     if (value == null) {
/* 295 */       return null;
/*     */     }
/* 297 */     if (value instanceof Long) {
/* 298 */       return (Long)value;
/*     */     }
/* 300 */     if (value instanceof String) {
/* 301 */       return Long.valueOf((String)value);
/*     */     }
/* 303 */     if (value instanceof Number) {
/* 304 */       return Long.valueOf(((Number)value).longValue());
/*     */     }
/* 306 */     if (value instanceof Date) {
/* 307 */       return Long.valueOf(((Date)value).getTime());
/*     */     }
/* 309 */     if (value instanceof Calendar) {
/* 310 */       return Long.valueOf(((Calendar)value).getTime().getTime());
/*     */     }
/* 312 */     return Long.valueOf(value.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static BigInteger toMathBigInteger(Object value) {
/* 317 */     if (value == null) {
/* 318 */       return null;
/*     */     }
/* 320 */     if (value instanceof BigInteger) {
/* 321 */       return (BigInteger)value;
/*     */     }
/* 323 */     return new BigInteger(value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Double toDouble(Object value) {
/* 331 */     if (value == null) {
/* 332 */       return null;
/*     */     }
/* 334 */     if (value instanceof Double) {
/* 335 */       return (Double)value;
/*     */     }
/* 337 */     if (value instanceof Number) {
/* 338 */       return Double.valueOf(((Number)value).doubleValue());
/*     */     }
/* 340 */     return Double.valueOf(value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Timestamp toTimestamp(Object value) {
/* 349 */     if (value == null) {
/* 350 */       return null;
/*     */     }
/* 352 */     if (value instanceof Timestamp) {
/* 353 */       return (Timestamp)value;
/*     */     }
/* 355 */     if (value instanceof Date)
/*     */     {
/* 357 */       return new Timestamp(((Date)value).getTime());
/*     */     }
/* 359 */     if (value instanceof Calendar) {
/* 360 */       return new Timestamp(((Calendar)value).getTime().getTime());
/*     */     }
/* 362 */     if (value instanceof String) {
/* 363 */       return Timestamp.valueOf((String)value);
/*     */     }
/* 365 */     if (value instanceof Number) {
/* 366 */       return new Timestamp(((Number)value).longValue());
/*     */     }
/*     */     
/* 369 */     String msg = "Unable to convert [" + value.getClass().getName() + "] into a Timestamp.";
/* 370 */     throw new RuntimeException(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Time toTime(Object value) {
/* 376 */     if (value == null) {
/* 377 */       return null;
/*     */     }
/* 379 */     if (value instanceof Time) {
/* 380 */       return (Time)value;
/*     */     }
/* 382 */     if (value instanceof String) {
/* 383 */       return Time.valueOf((String)value);
/*     */     }
/*     */     
/* 386 */     String m = "Unable to convert [" + value.getClass().getName() + "] into a java.sql.Date.";
/* 387 */     throw new RuntimeException(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date toDate(Object value) {
/* 396 */     if (value == null) {
/* 397 */       return null;
/*     */     }
/* 399 */     if (value instanceof Date) {
/* 400 */       return (Date)value;
/*     */     }
/* 402 */     if (value instanceof Date) {
/* 403 */       return new Date(((Date)value).getTime());
/*     */     }
/* 405 */     if (value instanceof Calendar) {
/* 406 */       return new Date(((Calendar)value).getTime().getTime());
/*     */     }
/* 408 */     if (value instanceof String) {
/* 409 */       return Date.valueOf((String)value);
/*     */     }
/* 411 */     if (value instanceof Number) {
/* 412 */       return new Date(((Number)value).longValue());
/*     */     }
/*     */     
/* 415 */     String m = "Unable to convert [" + value.getClass().getName() + "] into a java.sql.Date.";
/* 416 */     throw new RuntimeException(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Date toUtilDate(Object value) {
/* 425 */     if (value == null) {
/* 426 */       return null;
/*     */     }
/* 428 */     if (value instanceof Timestamp)
/*     */     {
/* 430 */       return new Date(((Timestamp)value).getTime());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 435 */     if (value instanceof Date) {
/* 436 */       return new Date(((Date)value).getTime());
/*     */     }
/* 438 */     if (value instanceof Date) {
/* 439 */       return (Date)value;
/*     */     }
/* 441 */     if (value instanceof Calendar) {
/* 442 */       return ((Calendar)value).getTime();
/*     */     }
/* 444 */     if (value instanceof String) {
/* 445 */       return new Date(Timestamp.valueOf((String)value).getTime());
/*     */     }
/* 447 */     if (value instanceof Number) {
/* 448 */       return new Date(((Number)value).longValue());
/*     */     }
/*     */     
/* 451 */     throw new RuntimeException("Unable to convert [" + value.getClass().getName() + "] into a java.util.Date");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Calendar toCalendar(Object value) {
/* 460 */     if (value == null) {
/* 461 */       return null;
/*     */     }
/* 463 */     if (value instanceof Calendar) {
/* 464 */       return (Calendar)value;
/*     */     }
/* 466 */     if (value instanceof Date) {
/* 467 */       Date date = (Date)value;
/* 468 */       return toCalendarFromDate(date);
/*     */     } 
/* 470 */     if (value instanceof String) {
/* 471 */       Date date = toUtilDate(value);
/* 472 */       return toCalendarFromDate(date);
/*     */     } 
/* 474 */     if (value instanceof Number) {
/* 475 */       long timeMillis = ((Number)value).longValue();
/* 476 */       Date date = new Date(timeMillis);
/* 477 */       return toCalendarFromDate(date);
/*     */     } 
/*     */     
/* 480 */     String m = "Unable to convert [" + value.getClass().getName() + "] into a java.util.Date";
/* 481 */     throw new RuntimeException(m);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Calendar toCalendarFromDate(Date date) {
/* 487 */     Calendar cal = Calendar.getInstance();
/* 488 */     cal.setTime(date);
/*     */     
/* 490 */     return cal;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\BasicTypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */