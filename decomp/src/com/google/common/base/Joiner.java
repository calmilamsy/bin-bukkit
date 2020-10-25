/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.IOException;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ @GwtCompatible
/*     */ public class Joiner
/*     */ {
/*     */   private final String separator;
/*     */   
/*  58 */   public static Joiner on(String separator) { return new Joiner(separator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static Joiner on(char separator) { return new Joiner(String.valueOf(separator)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private Joiner(String separator) { this.separator = (String)Preconditions.checkNotNull(separator); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   private Joiner(Joiner prototype) { this.separator = prototype.separator; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
/*  85 */     Preconditions.checkNotNull(appendable);
/*  86 */     Iterator<?> iterator = parts.iterator();
/*  87 */     if (iterator.hasNext()) {
/*  88 */       appendable.append(toString(iterator.next()));
/*  89 */       while (iterator.hasNext()) {
/*  90 */         appendable.append(this.separator);
/*  91 */         appendable.append(toString(iterator.next()));
/*     */       } 
/*     */     } 
/*  94 */     return appendable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public final <A extends Appendable> A appendTo(A appendable, Object[] parts) throws IOException { return (A)appendTo(appendable, Arrays.asList(parts)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public final <A extends Appendable> A appendTo(A appendable, @Nullable Object first, @Nullable Object second, Object... rest) throws IOException { return (A)appendTo(appendable, iterable(first, second, rest)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final StringBuilder appendTo(StringBuilder builder, Iterable<?> parts) {
/*     */     try {
/* 125 */       appendTo(builder, parts);
/* 126 */     } catch (IOException impossible) {
/* 127 */       throw new AssertionError(impossible);
/*     */     } 
/* 129 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public final StringBuilder appendTo(StringBuilder builder, Object[] parts) { return appendTo(builder, Arrays.asList(parts)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public final StringBuilder appendTo(StringBuilder builder, @Nullable Object first, @Nullable Object second, Object... rest) { return appendTo(builder, iterable(first, second, rest)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public final String join(Iterable<?> parts) { return appendTo(new StringBuilder(), parts).toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public final String join(Object[] parts) { return join(Arrays.asList(parts)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public final String join(@Nullable Object first, @Nullable Object second, Object... rest) { return join(iterable(first, second, rest)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Joiner useForNull(final String nullText) {
/* 182 */     Preconditions.checkNotNull(nullText);
/* 183 */     return new Joiner(this)
/*     */       {
/* 185 */         CharSequence toString(Object part) { return (part == null) ? nullText : Joiner.this.toString(part); }
/*     */         
/*     */         public Joiner useForNull(String nullText) {
/* 188 */           Preconditions.checkNotNull(nullText);
/*     */           
/* 190 */           throw new UnsupportedOperationException("already specified useForNull");
/*     */         }
/*     */         
/* 193 */         public Joiner skipNulls() { throw new UnsupportedOperationException("already specified useForNull"); }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Joiner skipNulls() {
/* 203 */     return new Joiner(this)
/*     */       {
/*     */         public <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
/* 206 */           Preconditions.checkNotNull(appendable, "appendable");
/* 207 */           Preconditions.checkNotNull(parts, "parts");
/* 208 */           Iterator<?> iterator = parts.iterator();
/* 209 */           while (iterator.hasNext()) {
/* 210 */             Object part = iterator.next();
/* 211 */             if (part != null) {
/* 212 */               appendable.append(Joiner.this.toString(part));
/*     */               break;
/*     */             } 
/*     */           } 
/* 216 */           while (iterator.hasNext()) {
/* 217 */             Object part = iterator.next();
/* 218 */             if (part != null) {
/* 219 */               appendable.append(Joiner.this.separator);
/* 220 */               appendable.append(Joiner.this.toString(part));
/*     */             } 
/*     */           } 
/* 223 */           return appendable;
/*     */         }
/*     */         public Joiner useForNull(String nullText) {
/* 226 */           Preconditions.checkNotNull(nullText);
/* 227 */           throw new UnsupportedOperationException("already specified skipNulls");
/*     */         }
/*     */         public MapJoiner withKeyValueSeparator(String kvs) {
/* 230 */           Preconditions.checkNotNull(kvs);
/* 231 */           throw new UnsupportedOperationException("can't use .skipNulls() with maps");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   public MapJoiner withKeyValueSeparator(String keyValueSeparator) { return new MapJoiner(this, (String)Preconditions.checkNotNull(keyValueSeparator), null); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MapJoiner
/*     */   {
/*     */     private Joiner joiner;
/*     */     
/*     */     private String keyValueSeparator;
/*     */ 
/*     */     
/*     */     private MapJoiner(Joiner joiner, String keyValueSeparator) {
/* 254 */       this.joiner = joiner;
/* 255 */       this.keyValueSeparator = keyValueSeparator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A extends Appendable> A appendTo(A appendable, Map<?, ?> map) throws IOException {
/* 265 */       Preconditions.checkNotNull(appendable);
/* 266 */       Iterator<? extends Map.Entry<?, ?>> iterator = map.entrySet().iterator();
/* 267 */       if (iterator.hasNext()) {
/* 268 */         Map.Entry<?, ?> entry = (Map.Entry)iterator.next();
/* 269 */         appendable.append(this.joiner.toString(entry.getKey()));
/* 270 */         appendable.append(this.keyValueSeparator);
/* 271 */         appendable.append(this.joiner.toString(entry.getValue()));
/* 272 */         while (iterator.hasNext()) {
/* 273 */           appendable.append(this.joiner.separator);
/* 274 */           Map.Entry<?, ?> e = (Map.Entry)iterator.next();
/* 275 */           appendable.append(this.joiner.toString(e.getKey()));
/* 276 */           appendable.append(this.keyValueSeparator);
/* 277 */           appendable.append(this.joiner.toString(e.getValue()));
/*     */         } 
/*     */       } 
/* 280 */       return appendable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
/*     */       try {
/* 291 */         appendTo(builder, map);
/* 292 */       } catch (IOException impossible) {
/* 293 */         throw new AssertionError(impossible);
/*     */       } 
/* 295 */       return builder;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     public String join(Map<?, ?> map) { return appendTo(new StringBuilder(), map).toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     public MapJoiner useForNull(String nullText) { return new MapJoiner(this.joiner.useForNull(nullText), this.keyValueSeparator); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 318 */   CharSequence toString(Object part) { return (part instanceof CharSequence) ? (CharSequence)part : part.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Iterable<Object> iterable(final Object first, final Object second, final Object[] rest) {
/* 325 */     Preconditions.checkNotNull(rest);
/* 326 */     return new AbstractList<Object>()
/*     */       {
/* 328 */         public int size() { return rest.length + 2; }
/*     */         
/*     */         public Object get(int index) {
/* 331 */           switch (index) {
/*     */             case 0:
/* 333 */               return first;
/*     */             case 1:
/* 335 */               return second;
/*     */           } 
/* 337 */           return rest[index - 2];
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Joiner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */