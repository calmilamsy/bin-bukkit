/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.util.Base64Coder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SafeRepresenter
/*     */   extends BaseRepresenter
/*     */ {
/*     */   protected Map<Class<? extends Object>, Tag> classTags;
/*     */   
/*     */   public SafeRepresenter() {
/*  46 */     this.nullRepresenter = new RepresentNull();
/*  47 */     this.representers.put(String.class, new RepresentString());
/*  48 */     this.representers.put(Boolean.class, new RepresentBoolean());
/*  49 */     this.representers.put(Character.class, new RepresentString());
/*  50 */     this.representers.put(byte[].class, new RepresentByteArray());
/*  51 */     this.multiRepresenters.put(Map.class, new RepresentMap());
/*  52 */     this.multiRepresenters.put(Number.class, new RepresentNumber());
/*  53 */     this.multiRepresenters.put(Set.class, new RepresentSet());
/*     */ 
/*     */     
/*  56 */     this.multiRepresenters.put(Iterable.class, new RepresentIterable());
/*  57 */     this.multiRepresenters.put(Iterator.class, new RepresentIterator());
/*  58 */     this.multiRepresenters.put((new Object[0]).getClass(), new RepresentArray());
/*  59 */     this.multiRepresenters.put(Date.class, new RepresentDate());
/*  60 */     this.multiRepresenters.put(Enum.class, new RepresentEnum());
/*  61 */     this.multiRepresenters.put(Calendar.class, new RepresentDate());
/*  62 */     this.classTags = new HashMap();
/*     */   }
/*     */   
/*     */   protected Tag getTag(Class<?> clazz, Tag defaultTag) {
/*  66 */     if (this.classTags.containsKey(clazz)) {
/*  67 */       return (Tag)this.classTags.get(clazz);
/*     */     }
/*  69 */     return defaultTag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean ignoreAliases(Object data) {
/*  75 */     if (data == null) {
/*  76 */       return true;
/*     */     }
/*  78 */     if (data instanceof Object[]) {
/*  79 */       Object[] array = (Object[])data;
/*  80 */       return (array.length == 0);
/*     */     } 
/*  82 */     return (data instanceof String || data instanceof Boolean || data instanceof Integer || data instanceof Long || data instanceof Float || data instanceof Double || data instanceof Enum);
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
/*  99 */   public Tag addClassTag(Class<? extends Object> clazz, String tag) { return addClassTag(clazz, new Tag(tag)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tag addClassTag(Class<? extends Object> clazz, Tag tag) {
/* 113 */     if (tag == null) {
/* 114 */       throw new NullPointerException("Tag must be provided.");
/*     */     }
/* 116 */     return (Tag)this.classTags.put(clazz, tag);
/*     */   }
/*     */   
/*     */   protected class RepresentNull
/*     */     implements Represent {
/* 121 */     public Node representData(Object data) { return SafeRepresenter.this.representScalar(Tag.NULL, "null"); }
/*     */   }
/*     */ 
/*     */   
/* 125 */   public static Pattern BINARY_PATTERN = Pattern.compile("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]");
/*     */   
/*     */   protected class RepresentString implements Represent {
/*     */     public Node representData(Object data) {
/* 129 */       Tag tag = Tag.STR;
/* 130 */       Character style = null;
/* 131 */       String value = data.toString();
/* 132 */       if (SafeRepresenter.BINARY_PATTERN.matcher(value).find()) {
/* 133 */         tag = Tag.BINARY;
/*     */         
/* 135 */         char[] binary = Base64Coder.encode(value.getBytes());
/* 136 */         value = String.valueOf(binary);
/* 137 */         style = Character.valueOf('|');
/*     */       } 
/* 139 */       return SafeRepresenter.this.representScalar(tag, value, style);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentBoolean implements Represent {
/*     */     public Node representData(Object data) {
/*     */       String value;
/* 146 */       if (Boolean.TRUE.equals(data)) {
/* 147 */         value = "true";
/*     */       } else {
/* 149 */         value = "false";
/*     */       } 
/* 151 */       return SafeRepresenter.this.representScalar(Tag.BOOL, value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentNumber implements Represent {
/*     */     public Node representData(Object data) {
/*     */       String value;
/*     */       Tag tag;
/* 159 */       if (data instanceof Byte || data instanceof Short || data instanceof Integer || data instanceof Long || data instanceof java.math.BigInteger) {
/*     */         
/* 161 */         tag = Tag.INT;
/* 162 */         value = data.toString();
/*     */       } else {
/* 164 */         Number number = (Number)data;
/* 165 */         tag = Tag.FLOAT;
/* 166 */         if (number.equals(Double.valueOf(NaND))) {
/* 167 */           value = ".NaN";
/* 168 */         } else if (number.equals(Double.valueOf(Double.POSITIVE_INFINITY))) {
/* 169 */           value = ".inf";
/* 170 */         } else if (number.equals(Double.valueOf(Double.NEGATIVE_INFINITY))) {
/* 171 */           value = "-.inf";
/*     */         } else {
/* 173 */           value = number.toString();
/*     */         } 
/*     */       } 
/* 176 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentIterable
/*     */     implements Represent
/*     */   {
/* 183 */     public Node representData(Object data) { return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), (Iterable)data, null); }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class RepresentIterator
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 191 */       Iterator<Object> iter = (Iterator)data;
/* 192 */       return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), new SafeRepresenter.IteratorWrapper(SafeRepresenter.this, iter), null);
/*     */     }
/*     */   }
/*     */   
/*     */   private class IteratorWrapper
/*     */     extends Object
/*     */     implements Iterable<Object> {
/*     */     private Iterator<Object> iter;
/*     */     
/* 201 */     public IteratorWrapper(Iterator<Object> iter) { this.iter = iter; }
/*     */ 
/*     */ 
/*     */     
/* 205 */     public Iterator<Object> iterator() { return this.iter; }
/*     */   }
/*     */   
/*     */   protected class RepresentArray
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/* 211 */       Object[] array = (Object[])data;
/* 212 */       List<Object> list = Arrays.asList(array);
/* 213 */       return SafeRepresenter.this.representSequence(Tag.SEQ, list, null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentMap
/*     */     implements Represent
/*     */   {
/* 220 */     public Node representData(Object data) { return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.MAP), (Map)data, null); }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class RepresentSet
/*     */     implements Represent
/*     */   {
/*     */     public Node representData(Object data) {
/* 228 */       Map<Object, Object> value = new LinkedHashMap<Object, Object>();
/* 229 */       Set<Object> set = (Set)data;
/* 230 */       for (Object key : set) {
/* 231 */         value.put(key, null);
/*     */       }
/* 233 */       return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.SET), value, null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentDate
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/*     */       Calendar calendar;
/* 241 */       if (data instanceof Calendar) {
/* 242 */         calendar = (Calendar)data;
/*     */       } else {
/* 244 */         calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 245 */         calendar.setTime((Date)data);
/*     */       } 
/* 247 */       int years = calendar.get(1);
/* 248 */       int months = calendar.get(2) + 1;
/* 249 */       int days = calendar.get(5);
/* 250 */       int hour24 = calendar.get(11);
/* 251 */       int minutes = calendar.get(12);
/* 252 */       int seconds = calendar.get(13);
/* 253 */       int millis = calendar.get(14);
/* 254 */       StringBuilder buffer = new StringBuilder(String.valueOf(years));
/* 255 */       buffer.append("-");
/* 256 */       if (months < 10) {
/* 257 */         buffer.append("0");
/*     */       }
/* 259 */       buffer.append(String.valueOf(months));
/* 260 */       buffer.append("-");
/* 261 */       if (days < 10) {
/* 262 */         buffer.append("0");
/*     */       }
/* 264 */       buffer.append(String.valueOf(days));
/* 265 */       buffer.append("T");
/* 266 */       if (hour24 < 10) {
/* 267 */         buffer.append("0");
/*     */       }
/* 269 */       buffer.append(String.valueOf(hour24));
/* 270 */       buffer.append(":");
/* 271 */       if (minutes < 10) {
/* 272 */         buffer.append("0");
/*     */       }
/* 274 */       buffer.append(String.valueOf(minutes));
/* 275 */       buffer.append(":");
/* 276 */       if (seconds < 10) {
/* 277 */         buffer.append("0");
/*     */       }
/* 279 */       buffer.append(String.valueOf(seconds));
/* 280 */       if (millis > 0) {
/* 281 */         if (millis < 10) {
/* 282 */           buffer.append(".00");
/* 283 */         } else if (millis < 100) {
/* 284 */           buffer.append(".0");
/*     */         } else {
/* 286 */           buffer.append(".");
/*     */         } 
/* 288 */         buffer.append(String.valueOf(millis));
/*     */       } 
/* 290 */       if (TimeZone.getTimeZone("UTC").equals(calendar.getTimeZone())) {
/* 291 */         buffer.append("Z");
/*     */       } else {
/*     */         
/* 294 */         int gmtOffset = calendar.getTimeZone().getOffset(calendar.get(0), calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), calendar.get(14));
/*     */ 
/*     */ 
/*     */         
/* 298 */         int minutesOffset = gmtOffset / 60000;
/* 299 */         int hoursOffset = minutesOffset / 60;
/* 300 */         int partOfHour = minutesOffset % 60;
/* 301 */         buffer.append(((hoursOffset > 0) ? "+" : "") + hoursOffset + ":" + ((partOfHour < 10) ? ("0" + partOfHour) : Integer.valueOf(partOfHour)));
/*     */       } 
/*     */       
/* 304 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentEnum implements Represent {
/*     */     public Node representData(Object data) {
/* 310 */       Tag tag = new Tag(data.getClass());
/* 311 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), data.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentByteArray implements Represent {
/*     */     public Node representData(Object data) {
/* 317 */       char[] binary = Base64Coder.encode((byte[])data);
/* 318 */       return SafeRepresenter.this.representScalar(Tag.BINARY, String.valueOf(binary), Character.valueOf('|'));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\representer\SafeRepresenter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */