/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ @GwtCompatible
/*     */ public final class Objects
/*     */ {
/*  53 */   public static boolean equal(@Nullable Object a, @Nullable Object b) { return (a == b || (a != null && a.equals(b))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static int hashCode(Object... objects) { return Arrays.hashCode(objects); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*  98 */   public static ToStringHelper toStringHelper(Object object) { return new ToStringHelper(object, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/* 114 */   public static <T> T firstNonNull(@Nullable T first, @Nullable T second) { return (first != null) ? first : Preconditions.checkNotNull(second); }
/*     */ 
/*     */   
/*     */   @Beta
/*     */   public static class ToStringHelper
/*     */   {
/*     */     private final List<String> fieldString;
/*     */     
/*     */     private final Object instance;
/*     */     
/*     */     private ToStringHelper(Object instance) {
/* 125 */       this.fieldString = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       this.instance = Preconditions.checkNotNull(instance);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     public ToStringHelper add(String name, @Nullable Object value) { return addValue((String)Preconditions.checkNotNull(name) + "=" + value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ToStringHelper addValue(@Nullable Object value) {
/* 151 */       this.fieldString.add(String.valueOf(value));
/* 152 */       return this;
/*     */     }
/*     */     
/* 155 */     private static final Joiner JOINER = Joiner.on(", ");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 161 */       StringBuilder builder = (new StringBuilder(100)).append(simpleName(this.instance.getClass())).append('{');
/*     */ 
/*     */       
/* 164 */       return JOINER.appendTo(builder, this.fieldString).append('}').toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @VisibleForTesting
/*     */     static String simpleName(Class<?> clazz) {
/* 175 */       String name = clazz.getName();
/*     */ 
/*     */       
/* 178 */       int start = name.lastIndexOf('$');
/*     */ 
/*     */ 
/*     */       
/* 182 */       if (start == -1) {
/* 183 */         start = name.lastIndexOf('.');
/*     */       }
/* 185 */       return name.substring(start + 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Objects.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */