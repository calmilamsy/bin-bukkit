/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.BeanState;
/*    */ import com.avaje.ebean.bean.EntityBean;
/*    */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultBeanState
/*    */   implements BeanState
/*    */ {
/*    */   final EntityBean entityBean;
/*    */   final EntityBeanIntercept intercept;
/*    */   
/*    */   public DefaultBeanState(EntityBean entityBean) {
/* 21 */     this.entityBean = entityBean;
/* 22 */     this.intercept = entityBean._ebean_getIntercept();
/*    */   }
/*    */ 
/*    */   
/* 26 */   public boolean isReference() { return this.intercept.isReference(); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public boolean isSharedInstance() { return this.intercept.isSharedInstance(); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public boolean isNew() { return this.intercept.isNew(); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public boolean isNewOrDirty() { return this.intercept.isNewOrDirty(); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public boolean isDirty() { return this.intercept.isDirty(); }
/*    */ 
/*    */   
/*    */   public Set<String> getLoadedProps() {
/* 46 */     Set<String> props = this.intercept.getLoadedProps();
/* 47 */     return (props == null) ? null : Collections.unmodifiableSet(props);
/*    */   }
/*    */   
/*    */   public Set<String> getChangedProps() {
/* 51 */     Set<String> props = this.intercept.getChangedProps();
/* 52 */     return (props == null) ? null : Collections.unmodifiableSet(props);
/*    */   }
/*    */ 
/*    */   
/* 56 */   public boolean isReadOnly() { return this.intercept.isReadOnly(); }
/*    */ 
/*    */ 
/*    */   
/* 60 */   public void setReadOnly(boolean readOnly) { this.intercept.setReadOnly(readOnly); }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public void addPropertyChangeListener(PropertyChangeListener listener) { this.entityBean.addPropertyChangeListener(listener); }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public void removePropertyChangeListener(PropertyChangeListener listener) { this.entityBean.removePropertyChangeListener(listener); }
/*    */ 
/*    */   
/*    */   public void setLoaded(Set<String> loadedProperties) {
/* 72 */     this.intercept.setLoadedProps(loadedProperties);
/* 73 */     this.intercept.setLoaded();
/*    */   }
/*    */ 
/*    */   
/* 77 */   public void setReference() { this.intercept.setReference(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DefaultBeanState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */