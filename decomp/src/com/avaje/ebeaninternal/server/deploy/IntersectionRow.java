/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.SqlUpdate;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.expression.IdInExpression;
/*     */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class IntersectionRow
/*     */ {
/*     */   private final String tableName;
/*     */   private final LinkedHashMap<String, Object> values;
/*     */   
/*     */   public IntersectionRow(String tableName) {
/*  20 */     this.values = new LinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  26 */     this.tableName = tableName;
/*     */   }
/*     */   
/*     */   private ArrayList<Object> excludeIds;
/*     */   private BeanDescriptor<?> excludeDescriptor;
/*     */   
/*     */   public void setExcludeIds(ArrayList<Object> excludeIds, BeanDescriptor<?> excludeDescriptor) {
/*  33 */     this.excludeIds = excludeIds;
/*  34 */     this.excludeDescriptor = excludeDescriptor;
/*     */   }
/*     */ 
/*     */   
/*  38 */   public void put(String key, Object value) { this.values.put(key, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlUpdate createInsert(EbeanServer server) {
/*  46 */     BindParams bindParams = new BindParams();
/*     */     
/*  48 */     StringBuilder sb = new StringBuilder();
/*  49 */     sb.append("insert into ").append(this.tableName).append(" (");
/*     */     
/*  51 */     int count = 0;
/*  52 */     Iterator<Map.Entry<String, Object>> it = this.values.entrySet().iterator();
/*  53 */     while (it.hasNext()) {
/*  54 */       if (count++ > 0) {
/*  55 */         sb.append(", ");
/*     */       }
/*     */       
/*  58 */       Map.Entry<String, Object> entry = (Map.Entry)it.next();
/*  59 */       sb.append((String)entry.getKey());
/*     */       
/*  61 */       bindParams.setParameter(count, entry.getValue());
/*     */     } 
/*     */     
/*  64 */     sb.append(") values (");
/*  65 */     for (int i = 0; i < count; i++) {
/*  66 */       if (i > 0) {
/*  67 */         sb.append(", ");
/*     */       }
/*  69 */       sb.append("?");
/*     */     } 
/*  71 */     sb.append(")");
/*     */     
/*  73 */     return new DefaultSqlUpdate(server, sb.toString(), bindParams);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlUpdate createDelete(EbeanServer server) {
/*  79 */     BindParams bindParams = new BindParams();
/*     */ 
/*     */     
/*  82 */     StringBuilder sb = new StringBuilder();
/*  83 */     sb.append("delete from ").append(this.tableName).append(" where ");
/*     */     
/*  85 */     int count = 0;
/*  86 */     Iterator<Map.Entry<String, Object>> it = this.values.entrySet().iterator();
/*  87 */     while (it.hasNext()) {
/*  88 */       if (count++ > 0) {
/*  89 */         sb.append(" and ");
/*     */       }
/*     */       
/*  92 */       Map.Entry<String, Object> entry = (Map.Entry)it.next();
/*     */       
/*  94 */       sb.append((String)entry.getKey());
/*  95 */       sb.append(" = ?");
/*     */       
/*  97 */       bindParams.setParameter(count, entry.getValue());
/*     */     } 
/*  99 */     if (this.excludeIds != null) {
/* 100 */       IdInExpression idIn = new IdInExpression(this.excludeIds);
/*     */       
/* 102 */       DefaultExpressionRequest er = new DefaultExpressionRequest(this.excludeDescriptor);
/* 103 */       idIn.addSqlNoAlias(er);
/* 104 */       idIn.addBindValues(er);
/*     */       
/* 106 */       sb.append(" and not ( ");
/* 107 */       sb.append(er.getSql());
/* 108 */       sb.append(" ) ");
/*     */       
/* 110 */       ArrayList<Object> bindValues = er.getBindValues();
/* 111 */       for (int i = 0; i < bindValues.size(); i++) {
/* 112 */         bindParams.setParameter(++count, bindValues.get(i));
/*     */       }
/*     */     } 
/*     */     
/* 116 */     return new DefaultSqlUpdate(server, sb.toString(), bindParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public SqlUpdate createDeleteChildren(EbeanServer server) {
/* 121 */     BindParams bindParams = new BindParams();
/*     */     
/* 123 */     StringBuilder sb = new StringBuilder();
/* 124 */     sb.append("delete from ").append(this.tableName).append(" where ");
/*     */     
/* 126 */     int count = 0;
/* 127 */     Iterator<Map.Entry<String, Object>> it = this.values.entrySet().iterator();
/* 128 */     while (it.hasNext()) {
/* 129 */       if (count++ > 0) {
/* 130 */         sb.append(" and ");
/*     */       }
/*     */       
/* 133 */       Map.Entry<String, Object> entry = (Map.Entry)it.next();
/*     */       
/* 135 */       sb.append((String)entry.getKey());
/* 136 */       sb.append(" = ?");
/*     */       
/* 138 */       bindParams.setParameter(count, entry.getValue());
/*     */     } 
/*     */     
/* 141 */     return new DefaultSqlUpdate(server, sb.toString(), bindParams);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\IntersectionRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */