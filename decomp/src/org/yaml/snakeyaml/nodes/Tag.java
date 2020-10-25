/*     */ package org.yaml.snakeyaml.nodes;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.util.UriEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Tag
/*     */   extends Object
/*     */   implements Comparable<Tag>
/*     */ {
/*     */   public static final String PREFIX = "tag:yaml.org,2002:";
/*  34 */   public static final Tag YAML = new Tag("tag:yaml.org,2002:yaml");
/*  35 */   public static final Tag VALUE = new Tag("tag:yaml.org,2002:value");
/*  36 */   public static final Tag MERGE = new Tag("tag:yaml.org,2002:merge");
/*  37 */   public static final Tag SET = new Tag("tag:yaml.org,2002:set");
/*  38 */   public static final Tag PAIRS = new Tag("tag:yaml.org,2002:pairs");
/*  39 */   public static final Tag OMAP = new Tag("tag:yaml.org,2002:omap");
/*  40 */   public static final Tag BINARY = new Tag("tag:yaml.org,2002:binary");
/*  41 */   public static final Tag INT = new Tag("tag:yaml.org,2002:int");
/*  42 */   public static final Tag FLOAT = new Tag("tag:yaml.org,2002:float");
/*  43 */   public static final Tag TIMESTAMP = new Tag("tag:yaml.org,2002:timestamp");
/*  44 */   public static final Tag BOOL = new Tag("tag:yaml.org,2002:bool");
/*  45 */   public static final Tag NULL = new Tag("tag:yaml.org,2002:null");
/*  46 */   public static final Tag STR = new Tag("tag:yaml.org,2002:str");
/*  47 */   public static final Tag SEQ = new Tag("tag:yaml.org,2002:seq");
/*  48 */   public static final Tag MAP = new Tag("tag:yaml.org,2002:map");
/*     */ 
/*     */   
/*  51 */   public static final Map<Tag, Set<Class<?>>> COMPATIBILITY_MAP = new HashMap(); static  {
/*  52 */     floatSet = new HashSet();
/*  53 */     floatSet.add(Double.class);
/*  54 */     floatSet.add(Float.class);
/*  55 */     floatSet.add(java.math.BigDecimal.class);
/*  56 */     COMPATIBILITY_MAP.put(FLOAT, floatSet);
/*     */     
/*  58 */     Set<Class<?>> intSet = new HashSet<Class<?>>();
/*  59 */     intSet.add(Integer.class);
/*  60 */     intSet.add(Long.class);
/*  61 */     intSet.add(java.math.BigInteger.class);
/*  62 */     COMPATIBILITY_MAP.put(INT, intSet);
/*     */     
/*  64 */     Set<Class<?>> timestampSet = new HashSet<Class<?>>();
/*  65 */     timestampSet.add(java.util.Date.class);
/*  66 */     timestampSet.add(java.sql.Date.class);
/*  67 */     timestampSet.add(java.sql.Timestamp.class);
/*  68 */     COMPATIBILITY_MAP.put(TIMESTAMP, timestampSet);
/*     */   }
/*     */   
/*     */   private final String value;
/*     */   
/*     */   public Tag(String tag) {
/*  74 */     if (tag == null)
/*  75 */       throw new NullPointerException("Tag must be provided."); 
/*  76 */     if (tag.length() == 0)
/*  77 */       throw new IllegalArgumentException("Tag must not be empty."); 
/*  78 */     if (tag.trim().length() != tag.length()) {
/*  79 */       throw new IllegalArgumentException("Tag must not contain leading or trailing spaces.");
/*     */     }
/*  81 */     this.value = UriEncoder.encode(tag);
/*     */   }
/*     */   
/*     */   public Tag(Class<? extends Object> clazz) {
/*  85 */     if (clazz == null) {
/*  86 */       throw new NullPointerException("Class for tag must be provided.");
/*     */     }
/*  88 */     this.value = "tag:yaml.org,2002:" + UriEncoder.encode(clazz.getName());
/*     */   }
/*     */   
/*     */   public Tag(URI uri) {
/*  92 */     if (uri == null) {
/*  93 */       throw new NullPointerException("URI for tag must be provided.");
/*     */     }
/*  95 */     this.value = uri.toASCIIString();
/*     */   }
/*     */ 
/*     */   
/*  99 */   public String getValue() { return this.value; }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public boolean startsWith(String prefix) { return this.value.startsWith(prefix); }
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 107 */     if (!this.value.startsWith("tag:yaml.org,2002:")) {
/* 108 */       throw new YAMLException("Invalid tag: " + this.value);
/*     */     }
/* 110 */     return UriEncoder.decode(this.value.substring("tag:yaml.org,2002:".length()));
/*     */   }
/*     */ 
/*     */   
/* 114 */   public int getLength() { return this.value.length(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public String toString() { return this.value; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 124 */     if (obj == null) {
/* 125 */       return false;
/*     */     }
/* 127 */     if (obj instanceof Tag)
/* 128 */       return this.value.equals(((Tag)obj).getValue()); 
/* 129 */     if (obj instanceof String && 
/* 130 */       this.value.equals(obj.toString())) {
/*     */       
/* 132 */       System.err.println("Comparing Tag and String is deprecated.");
/* 133 */       return true;
/*     */     } 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public int hashCode() { return this.value.hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Class<?> clazz) {
/* 154 */     Set<Class<?>> set = (Set)COMPATIBILITY_MAP.get(this);
/* 155 */     if (set != null) {
/* 156 */       return set.contains(clazz);
/*     */     }
/* 158 */     return false;
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
/* 170 */   public boolean matches(Class<? extends Object> clazz) { return this.value.equals("tag:yaml.org,2002:" + clazz.getName()); }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public int compareTo(Tag o) { return this.value.compareTo(o.getValue()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\nodes\Tag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */