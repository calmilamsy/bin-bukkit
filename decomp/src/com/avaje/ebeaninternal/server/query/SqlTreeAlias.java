/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ public class SqlTreeAlias
/*     */ {
/*     */   private int counter;
/*     */   private int manyWhereCounter;
/*     */   private TreeSet<String> joinProps;
/*     */   private HashSet<String> embeddedPropertyJoins;
/*     */   private TreeSet<String> manyWhereJoinProps;
/*     */   private HashMap<String, String> aliasMap;
/*     */   private HashMap<String, String> manyWhereAliasMap;
/*     */   private final String rootTableAlias;
/*     */   
/*     */   public SqlTreeAlias(String rootTableAlias) {
/*  25 */     this.joinProps = new TreeSet();
/*     */ 
/*     */ 
/*     */     
/*  29 */     this.manyWhereJoinProps = new TreeSet();
/*     */     
/*  31 */     this.aliasMap = new HashMap();
/*     */     
/*  33 */     this.manyWhereAliasMap = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     this.rootTableAlias = rootTableAlias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addManyWhereJoins(Set<String> manyWhereJoins) {
/*  46 */     if (manyWhereJoins != null) {
/*  47 */       for (String include : manyWhereJoins) {
/*  48 */         addPropertyJoin(include, this.manyWhereJoinProps);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void addEmbeddedPropertyJoin(String embProp) {
/*  54 */     if (this.embeddedPropertyJoins == null) {
/*  55 */       this.embeddedPropertyJoins = new HashSet();
/*     */     }
/*  57 */     this.embeddedPropertyJoins.add(embProp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addJoin(Set<String> propJoins, BeanDescriptor<?> desc) {
/*  64 */     if (propJoins != null) {
/*  65 */       for (String propJoin : propJoins) {
/*  66 */         ElPropertyDeploy elProp = desc.getElPropertyDeploy(propJoin);
/*  67 */         if (elProp != null && elProp.getBeanProperty().isEmbedded()) {
/*  68 */           String[] split = SplitName.split(propJoin);
/*  69 */           addPropertyJoin(split[0], this.joinProps);
/*  70 */           addEmbeddedPropertyJoin(propJoin);
/*     */           continue;
/*     */         } 
/*  73 */         addPropertyJoin(propJoin, this.joinProps);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addPropertyJoin(String include, TreeSet<String> set) {
/*  82 */     if (set.add(include)) {
/*  83 */       String[] split = SplitName.split(include);
/*  84 */       if (split[false] != null) {
/*  85 */         addPropertyJoin(split[0], set);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildAlias() {
/*  96 */     Iterator<String> i = this.joinProps.iterator();
/*  97 */     while (i.hasNext()) {
/*  98 */       calcAlias((String)i.next());
/*     */     }
/*     */     
/* 101 */     i = this.manyWhereJoinProps.iterator();
/* 102 */     while (i.hasNext()) {
/* 103 */       calcAliasManyWhere((String)i.next());
/*     */     }
/*     */     
/* 106 */     mapEmbeddedPropertyAlias();
/*     */   }
/*     */   
/*     */   private void mapEmbeddedPropertyAlias() {
/* 110 */     if (this.embeddedPropertyJoins != null) {
/* 111 */       for (String propJoin : this.embeddedPropertyJoins) {
/* 112 */         String[] split = SplitName.split(propJoin);
/*     */         
/* 114 */         String alias = getTableAlias(split[0]);
/* 115 */         this.aliasMap.put(propJoin, alias);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private String calcAlias(String prefix) {
/* 122 */     String alias = nextTableAlias();
/* 123 */     this.aliasMap.put(prefix, alias);
/* 124 */     return alias;
/*     */   }
/*     */ 
/*     */   
/*     */   private String calcAliasManyWhere(String prefix) {
/* 129 */     String alias = nextManyWhereTableAlias();
/* 130 */     this.manyWhereAliasMap.put(prefix, alias);
/* 131 */     return alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTableAlias(String prefix) {
/* 138 */     if (prefix == null) {
/* 139 */       return this.rootTableAlias;
/*     */     }
/* 141 */     String s = (String)this.aliasMap.get(prefix);
/* 142 */     if (s == null) {
/* 143 */       return calcAlias(prefix);
/*     */     }
/* 145 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTableAliasManyWhere(String prefix) {
/* 153 */     if (prefix == null) {
/* 154 */       return this.rootTableAlias;
/*     */     }
/* 156 */     String s = (String)this.manyWhereAliasMap.get(prefix);
/* 157 */     if (s == null) {
/* 158 */       s = (String)this.aliasMap.get(prefix);
/*     */     }
/* 160 */     if (s == null) {
/* 161 */       String msg = "Could not determine table alias for [" + prefix + "] manyMap[" + this.manyWhereAliasMap + "] aliasMap[" + this.aliasMap + "]";
/*     */       
/* 163 */       throw new RuntimeException(msg);
/*     */     } 
/* 165 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String parseWhere(String clause) {
/* 172 */     clause = parseRootAlias(clause);
/* 173 */     clause = parseAliasMap(clause, this.manyWhereAliasMap);
/* 174 */     return parseAliasMap(clause, this.aliasMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String parse(String clause) {
/* 181 */     clause = parseRootAlias(clause);
/* 182 */     return parseAliasMap(clause, this.aliasMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String parseRootAlias(String clause) {
/* 190 */     if (this.rootTableAlias == null) {
/* 191 */       return clause.replace("${}", "");
/*     */     }
/* 193 */     return clause.replace("${}", this.rootTableAlias + ".");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String parseAliasMap(String clause, HashMap<String, String> parseAliasMap) {
/* 202 */     Iterator<Map.Entry<String, String>> i = parseAliasMap.entrySet().iterator();
/* 203 */     while (i.hasNext()) {
/* 204 */       Map.Entry<String, String> e = (Map.Entry)i.next();
/* 205 */       String k = "${" + (String)e.getKey() + "}";
/* 206 */       clause = clause.replace(k, (String)e.getValue() + ".");
/*     */     } 
/*     */     
/* 209 */     return clause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   private String nextTableAlias() { return "t" + ++this.counter; }
/*     */ 
/*     */ 
/*     */   
/* 221 */   private String nextManyWhereTableAlias() { return "u" + ++this.manyWhereCounter; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlTreeAlias.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */