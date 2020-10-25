/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Set;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SqlBeanLoad
/*     */ {
/*     */   private final DbReadContext ctx;
/*     */   private final Object bean;
/*     */   private final Class<?> type;
/*     */   private final Object originalOldValues;
/*     */   private final boolean isLazyLoad;
/*     */   private final Set<String> excludes;
/*     */   private final boolean setOriginalOldValues;
/*     */   private final boolean rawSql;
/*     */   
/*     */   public SqlBeanLoad(DbReadContext ctx, Class<?> type, Object bean, SpiQuery.Mode queryMode) {
/*  58 */     this.ctx = ctx;
/*  59 */     this.rawSql = ctx.isRawSql();
/*  60 */     this.type = type;
/*  61 */     this.isLazyLoad = queryMode.equals(SpiQuery.Mode.LAZYLOAD_BEAN);
/*  62 */     this.bean = bean;
/*     */     
/*  64 */     if (bean instanceof EntityBean) {
/*  65 */       EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
/*     */       
/*  67 */       this.excludes = this.isLazyLoad ? ebi.getLoadedProps() : null;
/*  68 */       if (this.excludes != null) {
/*     */ 
/*     */ 
/*     */         
/*  72 */         this.originalOldValues = ebi.getOldValues();
/*     */       } else {
/*  74 */         this.originalOldValues = null;
/*     */       } 
/*  76 */       this.setOriginalOldValues = (this.originalOldValues != null);
/*     */     } else {
/*  78 */       this.excludes = null;
/*  79 */       this.originalOldValues = null;
/*  80 */       this.setOriginalOldValues = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public boolean isLazyLoad() { return this.isLazyLoad; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public void loadIgnore(int increment) { this.ctx.getDataReader().incrementPos(increment); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object load(BeanProperty prop) throws SQLException {
/* 100 */     if (!this.rawSql && prop.isTransient()) {
/* 101 */       return null;
/*     */     }
/*     */     
/* 104 */     if (this.bean == null || (this.excludes != null && this.excludes.contains(prop.getName())) || (this.type != null && !prop.isAssignableFrom(this.type))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       prop.loadIgnore(this.ctx);
/* 114 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/* 118 */       Object dbVal = prop.read(this.ctx);
/* 119 */       if (this.isLazyLoad) {
/* 120 */         prop.setValue(this.bean, dbVal);
/*     */       } else {
/* 122 */         prop.setValueIntercept(this.bean, dbVal);
/*     */       } 
/* 124 */       if (this.setOriginalOldValues)
/*     */       {
/* 126 */         prop.setValue(this.originalOldValues, dbVal);
/*     */       }
/* 128 */       return dbVal;
/*     */     }
/* 130 */     catch (Exception e) {
/* 131 */       String msg = "Error loading on " + prop.getFullBeanName();
/* 132 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadAssocMany(BeanPropertyAssocMany<?> prop) {}
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\SqlBeanLoad.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */