/*     */ package org.yaml.snakeyaml.resolver;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Resolver
/*     */ {
/*  35 */   public static final Pattern BOOL = Pattern.compile("^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE|on|On|ON|off|Off|OFF)$");
/*     */   
/*  37 */   public static final Pattern FLOAT = Pattern.compile("^(?:[-+]?(?:[0-9][0-9_]*)\\.[0-9_]*(?:[eE][-+][0-9]+)?|[-+]?(?:[0-9][0-9_]*)?\\.[0-9_]+(?:[eE][-+][0-9]+)?|[-+]?[0-9][0-9_]*(?::[0-5]?[0-9])+\\.[0-9_]*|[-+]?\\.(?:inf|Inf|INF)|\\.(?:nan|NaN|NAN))$");
/*     */   
/*  39 */   public static final Pattern INT = Pattern.compile("^(?:[-+]?0b[0-1_]+|[-+]?0[0-7_]+|[-+]?(?:0|[1-9][0-9_]*)|[-+]?0x[0-9a-fA-F_]+|[-+]?[1-9][0-9_]*(?::[0-5]?[0-9])+)$");
/*     */   
/*  41 */   public static final Pattern MERGE = Pattern.compile("^(?:<<)$");
/*  42 */   public static final Pattern NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");
/*  43 */   public static final Pattern EMPTY = Pattern.compile("^$");
/*  44 */   public static final Pattern TIMESTAMP = Pattern.compile("^(?:[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]|[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?(?:[Tt]|[ \t]+)[0-9][0-9]?:[0-9][0-9]:[0-9][0-9](?:\\.[0-9]*)?(?:[ \t]*(?:Z|[-+][0-9][0-9]?(?::[0-9][0-9])?))?)$");
/*     */   
/*  46 */   public static final Pattern VALUE = Pattern.compile("^(?:=)$");
/*  47 */   public static final Pattern YAML = Pattern.compile("^(?:!|&|\\*)$");
/*     */   
/*  49 */   protected Map<Character, List<ResolverTuple>> yamlImplicitResolvers = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Resolver(boolean respectDefaultImplicitScalars) {
/*  59 */     if (respectDefaultImplicitScalars) {
/*  60 */       addImplicitResolvers();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void addImplicitResolvers() {
/*  65 */     addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
/*  66 */     addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
/*  67 */     addImplicitResolver(Tag.INT, INT, "-+0123456789");
/*  68 */     addImplicitResolver(Tag.MERGE, MERGE, "<");
/*  69 */     addImplicitResolver(Tag.NULL, NULL, "~nN\000");
/*  70 */     addImplicitResolver(Tag.NULL, EMPTY, null);
/*  71 */     addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789");
/*  72 */     addImplicitResolver(Tag.VALUE, VALUE, "=");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     addImplicitResolver(Tag.YAML, YAML, "!&*");
/*     */   }
/*     */ 
/*     */   
/*  81 */   public Resolver() { this(true); }
/*     */ 
/*     */   
/*     */   public void addImplicitResolver(Tag tag, Pattern regexp, String first) {
/*  85 */     if (first == null) {
/*  86 */       List<ResolverTuple> curr = (List)this.yamlImplicitResolvers.get(null);
/*  87 */       if (curr == null) {
/*  88 */         curr = new ArrayList<ResolverTuple>();
/*  89 */         this.yamlImplicitResolvers.put(null, curr);
/*     */       } 
/*  91 */       curr.add(new ResolverTuple(tag, regexp));
/*     */     } else {
/*  93 */       char[] chrs = first.toCharArray();
/*  94 */       for (int i = 0, j = chrs.length; i < j; i++) {
/*  95 */         Character theC = new Character(chrs[i]);
/*  96 */         if (theC.charValue() == '\000')
/*     */         {
/*  98 */           theC = null;
/*     */         }
/* 100 */         List<ResolverTuple> curr = (List)this.yamlImplicitResolvers.get(theC);
/* 101 */         if (curr == null) {
/* 102 */           curr = new ArrayList<ResolverTuple>();
/* 103 */           this.yamlImplicitResolvers.put(theC, curr);
/*     */         } 
/* 105 */         curr.add(new ResolverTuple(tag, regexp));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Tag resolve(NodeId kind, String value, boolean implicit) {
/* 111 */     if (kind == NodeId.scalar && implicit) {
/* 112 */       List<ResolverTuple> resolvers = null;
/* 113 */       if ("".equals(value)) {
/* 114 */         resolvers = (List)this.yamlImplicitResolvers.get(Character.valueOf(false));
/*     */       } else {
/* 116 */         resolvers = (List)this.yamlImplicitResolvers.get(Character.valueOf(value.charAt(0)));
/*     */       } 
/* 118 */       if (resolvers != null) {
/* 119 */         for (ResolverTuple v : resolvers) {
/* 120 */           Tag tag = v.getTag();
/* 121 */           Pattern regexp = v.getRegexp();
/* 122 */           if (regexp.matcher(value).matches()) {
/* 123 */             return tag;
/*     */           }
/*     */         } 
/*     */       }
/* 127 */       if (this.yamlImplicitResolvers.containsKey(null)) {
/* 128 */         for (ResolverTuple v : (List)this.yamlImplicitResolvers.get(null)) {
/* 129 */           Tag tag = v.getTag();
/* 130 */           Pattern regexp = v.getRegexp();
/* 131 */           if (regexp.matcher(value).matches()) {
/* 132 */             return tag;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 137 */     switch (kind) {
/*     */       case scalar:
/* 139 */         return Tag.STR;
/*     */       case sequence:
/* 141 */         return Tag.SEQ;
/*     */     } 
/* 143 */     return Tag.MAP;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\resolver\Resolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */