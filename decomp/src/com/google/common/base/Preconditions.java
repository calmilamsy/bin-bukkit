/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public final class Preconditions
/*     */ {
/*     */   public static void checkArgument(boolean expression) {
/*  71 */     if (!expression) {
/*  72 */       throw new IllegalArgumentException();
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
/*     */   
/*     */   public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
/*  87 */     if (!expression) {
/*  88 */       throw new IllegalArgumentException(String.valueOf(errorMessage));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkArgument(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
/* 114 */     if (!expression) {
/* 115 */       throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
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
/*     */   public static void checkState(boolean expression) {
/* 128 */     if (!expression) {
/* 129 */       throw new IllegalStateException();
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
/*     */   
/*     */   public static void checkState(boolean expression, @Nullable Object errorMessage) {
/* 144 */     if (!expression) {
/* 145 */       throw new IllegalStateException(String.valueOf(errorMessage));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkState(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
/* 171 */     if (!expression) {
/* 172 */       throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
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
/*     */   public static <T> T checkNotNull(T reference) {
/* 186 */     if (reference == null) {
/* 187 */       throw new NullPointerException();
/*     */     }
/* 189 */     return reference;
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
/*     */   public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
/* 203 */     if (reference == null) {
/* 204 */       throw new NullPointerException(String.valueOf(errorMessage));
/*     */     }
/* 206 */     return reference;
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
/*     */   public static <T> T checkNotNull(T reference, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
/* 229 */     if (reference == null)
/*     */     {
/* 231 */       throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
/*     */     }
/*     */     
/* 234 */     return reference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   public static int checkElementIndex(int index, int size) { return checkElementIndex(index, size, "index"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int checkElementIndex(int index, int size, @Nullable String desc) {
/* 302 */     if (index < 0 || index >= size) {
/* 303 */       throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
/*     */     }
/* 305 */     return index;
/*     */   }
/*     */   
/*     */   private static String badElementIndex(int index, int size, String desc) {
/* 309 */     if (index < 0)
/* 310 */       return format("%s (%s) must not be negative", new Object[] { desc, Integer.valueOf(index) }); 
/* 311 */     if (size < 0) {
/* 312 */       throw new IllegalArgumentException("negative size: " + size);
/*     */     }
/* 314 */     return format("%s (%s) must be less than size (%s)", new Object[] { desc, Integer.valueOf(index), Integer.valueOf(size) });
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
/* 332 */   public static int checkPositionIndex(int index, int size) { return checkPositionIndex(index, size, "index"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int checkPositionIndex(int index, int size, @Nullable String desc) {
/* 352 */     if (index < 0 || index > size) {
/* 353 */       throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
/*     */     }
/* 355 */     return index;
/*     */   }
/*     */   
/*     */   private static String badPositionIndex(int index, int size, String desc) {
/* 359 */     if (index < 0)
/* 360 */       return format("%s (%s) must not be negative", new Object[] { desc, Integer.valueOf(index) }); 
/* 361 */     if (size < 0) {
/* 362 */       throw new IllegalArgumentException("negative size: " + size);
/*     */     }
/* 364 */     return format("%s (%s) must not be greater than size (%s)", new Object[] { desc, Integer.valueOf(index), Integer.valueOf(size) });
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
/*     */   public static void checkPositionIndexes(int start, int end, int size) {
/* 385 */     if (start < 0 || end < start || end > size) {
/* 386 */       throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
/*     */     }
/*     */   }
/*     */   
/*     */   private static String badPositionIndexes(int start, int end, int size) {
/* 391 */     if (start < 0 || start > size) {
/* 392 */       return badPositionIndex(start, size, "start index");
/*     */     }
/* 394 */     if (end < 0 || end > size) {
/* 395 */       return badPositionIndex(end, size, "end index");
/*     */     }
/*     */     
/* 398 */     return format("end index (%s) must not be less than start index (%s)", new Object[] { Integer.valueOf(end), Integer.valueOf(start) });
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
/*     */   @VisibleForTesting
/*     */   static String format(String template, @Nullable Object... args) {
/* 416 */     template = String.valueOf(template);
/*     */ 
/*     */     
/* 419 */     StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
/*     */     
/* 421 */     int templateStart = 0;
/* 422 */     int i = 0;
/* 423 */     while (i < args.length) {
/* 424 */       int placeholderStart = template.indexOf("%s", templateStart);
/* 425 */       if (placeholderStart == -1) {
/*     */         break;
/*     */       }
/* 428 */       builder.append(template.substring(templateStart, placeholderStart));
/* 429 */       builder.append(args[i++]);
/* 430 */       templateStart = placeholderStart + 2;
/*     */     } 
/* 432 */     builder.append(template.substring(templateStart));
/*     */ 
/*     */     
/* 435 */     if (i < args.length) {
/* 436 */       builder.append(" [");
/* 437 */       builder.append(args[i++]);
/* 438 */       while (i < args.length) {
/* 439 */         builder.append(", ");
/* 440 */         builder.append(args[i++]);
/*     */       } 
/* 442 */       builder.append("]");
/*     */     } 
/*     */     
/* 445 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Preconditions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */