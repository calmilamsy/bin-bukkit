/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SqlTreeProperties
/*     */ {
/*     */   Set<String> includedProps;
/*     */   boolean readOnly;
/*     */   boolean includeId = true;
/*  35 */   TableJoin[] tableJoins = new TableJoin[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   List<BeanProperty> propsList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   LinkedHashSet<String> propNames = new LinkedHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public boolean containsProperty(String propName) { return this.propNames.contains(propName); }
/*     */ 
/*     */   
/*     */   public void add(BeanProperty[] props) {
/*  56 */     for (BeanProperty beanProperty : props) {
/*  57 */       this.propsList.add(beanProperty);
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(BeanProperty prop) {
/*  62 */     this.propsList.add(prop);
/*  63 */     this.propNames.add(prop.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public BeanProperty[] getProps() { return (BeanProperty[])this.propsList.toArray(new BeanProperty[this.propsList.size()]); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public boolean isIncludeId() { return this.includeId; }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public void setIncludeId(boolean includeId) { this.includeId = includeId; }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public boolean isPartialObject() { return (this.includedProps != null); }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public Set<String> getIncludedProperties() { return this.includedProps; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void setIncludedProperties(Set<String> includedProps) { this.includedProps = includedProps; }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public boolean isReadOnly() { return this.readOnly; }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public void setReadOnly(boolean readOnly) { this.readOnly = readOnly; }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public TableJoin[] getTableJoins() { return this.tableJoins; }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public void setTableJoins(TableJoin[] tableJoins) { this.tableJoins = tableJoins; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlTreeProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */