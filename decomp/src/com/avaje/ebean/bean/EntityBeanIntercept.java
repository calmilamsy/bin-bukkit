/*     */ package com.avaje.ebean.bean;
/*     */ 
/*     */ import com.avaje.ebean.Ebean;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
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
/*     */ public final class EntityBeanIntercept
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3664031775464862648L;
/*     */   public static final int DEFAULT = 0;
/*     */   public static final int UPDATE = 1;
/*     */   public static final int READONLY = 2;
/*     */   public static final int SHARED = 3;
/*     */   private NodeUsageCollector nodeUsageCollector;
/*     */   private PropertyChangeSupport pcs;
/*     */   private PersistenceContext persistenceContext;
/*     */   private BeanLoader beanLoader;
/*     */   private int beanLoaderIndex;
/*     */   private String ebeanServerName;
/*     */   private EntityBean owner;
/*     */   private Object parentBean;
/*     */   private boolean loaded;
/*     */   private boolean disableLazyLoad;
/*     */   private boolean intercepting;
/*     */   private int state;
/*     */   private boolean useCache;
/*     */   private Object oldValues;
/*     */   private Set<String> loadedProps;
/*     */   private HashSet<String> changedProps;
/*     */   private String lazyLoadProperty;
/*     */   
/* 144 */   public EntityBeanIntercept(Object owner) { this.owner = (EntityBean)owner; }
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
/* 155 */   public void setState(int parentState) { this.state = parentState; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public int getState() { return this.state; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyStateTo(EntityBeanIntercept dest) {
/* 169 */     dest.loadedProps = this.loadedProps;
/* 170 */     dest.ebeanServerName = this.ebeanServerName;
/*     */     
/* 172 */     if (this.loaded) {
/* 173 */       dest.setLoaded();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public EntityBean getOwner() { return this.owner; }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 185 */     if (!this.loaded) {
/* 186 */       return "Reference...";
/*     */     }
/* 188 */     return "OldValues: " + this.oldValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public PersistenceContext getPersistenceContext() { return this.persistenceContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public void setPersistenceContext(PersistenceContext persistenceContext) { this.persistenceContext = persistenceContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyChangeListener(PropertyChangeListener listener) {
/* 209 */     if (this.pcs == null) {
/* 210 */       this.pcs = new PropertyChangeSupport(this.owner);
/*     */     }
/* 212 */     this.pcs.addPropertyChangeListener(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
/* 219 */     if (this.pcs == null) {
/* 220 */       this.pcs = new PropertyChangeSupport(this.owner);
/*     */     }
/* 222 */     this.pcs.addPropertyChangeListener(propertyName, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener listener) {
/* 229 */     if (this.pcs != null) {
/* 230 */       this.pcs.removePropertyChangeListener(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
/* 238 */     if (this.pcs != null) {
/* 239 */       this.pcs.removePropertyChangeListener(propertyName, listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   public void setNodeUsageCollector(NodeUsageCollector usageCollector) { this.nodeUsageCollector = usageCollector; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 254 */   public Object getParentBean() { return this.parentBean; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   public void setParentBean(Object parentBean) { this.parentBean = parentBean; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 269 */   public int getBeanLoaderIndex() { return this.beanLoaderIndex; }
/*     */ 
/*     */   
/*     */   public void setBeanLoader(int index, BeanLoader ctx) {
/* 273 */     this.beanLoaderIndex = index;
/* 274 */     this.beanLoader = ctx;
/* 275 */     this.ebeanServerName = ctx.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 284 */     if (this.oldValues != null) {
/* 285 */       return true;
/*     */     }
/*     */     
/* 288 */     return this.owner._ebean_isEmbeddedNewOrDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 295 */   public boolean isNew() { return (!this.intercepting && !this.loaded); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 302 */   public boolean isNewOrDirty() { return (isNew() || isDirty()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 309 */   public boolean isReference() { return (this.intercepting && !this.loaded); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReference() {
/* 316 */     this.loaded = false;
/* 317 */     this.intercepting = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 324 */   public Object getOldValues() { return this.oldValues; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   public boolean isUseCache() { return this.useCache; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 338 */   public void setUseCache(boolean loadFromCache) { this.useCache = loadFromCache; }
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
/* 350 */   public boolean isSharedInstance() { return (this.state == 3); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 358 */   public void setSharedInstance() { this.state = 3; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 366 */   public boolean isReadOnly() { return (this.state >= 2); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadOnly(boolean readOnly) {
/* 374 */     if (this.state == 3) {
/* 375 */       if (!readOnly) {
/* 376 */         throw new IllegalStateException("sharedInstance so must remain readOnly");
/*     */       }
/*     */     } else {
/* 379 */       this.state = readOnly ? 2 : 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 390 */   public boolean isIntercepting() { return this.intercepting; }
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
/* 402 */   public void setIntercepting(boolean intercepting) { this.intercepting = intercepting; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 409 */   public boolean isLoaded() { return this.loaded; }
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
/*     */   public void setLoaded() {
/* 424 */     this.loaded = true;
/* 425 */     this.oldValues = null;
/* 426 */     this.intercepting = true;
/* 427 */     this.owner._ebean_setEmbeddedLoaded();
/* 428 */     this.lazyLoadProperty = null;
/* 429 */     this.changedProps = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadedLazy() {
/* 437 */     this.loaded = true;
/* 438 */     this.intercepting = true;
/* 439 */     this.lazyLoadProperty = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 446 */   public boolean isDisableLazyLoad() { return this.disableLazyLoad; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 456 */   public void setDisableLazyLoad(boolean disableLazyLoad) { this.disableLazyLoad = disableLazyLoad; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmbeddedLoaded(Object embeddedBean) {
/* 463 */     if (embeddedBean instanceof EntityBean) {
/* 464 */       EntityBean eb = (EntityBean)embeddedBean;
/* 465 */       eb._ebean_getIntercept().setLoaded();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmbeddedNewOrDirty(Object embeddedBean) {
/* 474 */     if (embeddedBean == null)
/*     */     {
/*     */       
/* 477 */       return false;
/*     */     }
/* 479 */     if (embeddedBean instanceof EntityBean) {
/* 480 */       return ((EntityBean)embeddedBean)._ebean_getIntercept().isNewOrDirty();
/*     */     }
/*     */ 
/*     */     
/* 484 */     return true;
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
/* 495 */   public void setLoadedProps(Set<String> loadedPropertyNames) { this.loadedProps = loadedPropertyNames; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 502 */   public Set<String> getLoadedProps() { return this.loadedProps; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 509 */   public Set<String> getChangedProps() { return this.changedProps; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 516 */   public String getLazyLoadProperty() { return this.lazyLoadProperty; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadBean(String loadProperty) {
/* 524 */     synchronized (this) {
/* 525 */       if (this.disableLazyLoad) {
/* 526 */         this.loaded = true;
/*     */         
/*     */         return;
/*     */       } 
/* 530 */       if (this.lazyLoadProperty == null) {
/* 531 */         if (this.beanLoader == null) {
/* 532 */           this.beanLoader = (BeanLoader)Ebean.getServer(this.ebeanServerName);
/*     */         }
/*     */         
/* 535 */         if (this.beanLoader == null) {
/* 536 */           String msg = "Lazy loading but InternalEbean is null? The InternalEbean needs to be set after deserialization to support lazy loading.";
/*     */ 
/*     */           
/* 539 */           throw new PersistenceException(msg);
/*     */         } 
/*     */         
/* 542 */         this.lazyLoadProperty = loadProperty;
/*     */         
/* 544 */         if (this.nodeUsageCollector != null) {
/* 545 */           this.nodeUsageCollector.setLoadProperty(this.lazyLoadProperty);
/*     */         }
/*     */         
/* 548 */         this.beanLoader.loadBean(this);
/*     */       } 
/*     */     } 
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
/*     */   protected void createOldValues() {
/* 563 */     this.oldValues = this.owner._ebean_createCopy();
/*     */     
/* 565 */     if (this.nodeUsageCollector != null) {
/* 566 */       this.nodeUsageCollector.setModified();
/*     */     }
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
/*     */   public Object writeReplaceIntercept() {
/* 582 */     if (!SerializeControl.isVanillaBeans()) {
/* 583 */       return this.owner;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 588 */     return this.owner._ebean_createCopy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean areEqual(Object obj1, Object obj2) {
/* 596 */     if (obj1 == null) {
/* 597 */       return (obj2 == null);
/*     */     }
/* 599 */     if (obj2 == null) {
/* 600 */       return false;
/*     */     }
/* 602 */     if (obj1 == obj2) {
/* 603 */       return true;
/*     */     }
/* 605 */     if (obj1 instanceof java.math.BigDecimal) {
/*     */ 
/*     */       
/* 608 */       if (obj2 instanceof java.math.BigDecimal) {
/* 609 */         Comparable com1 = (Comparable)obj1;
/* 610 */         return (com1.compareTo(obj2) == 0);
/*     */       } 
/*     */       
/* 613 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 617 */     if (obj1 instanceof java.net.URL)
/*     */     {
/* 619 */       return obj1.toString().equals(obj2.toString());
/*     */     }
/* 621 */     return obj1.equals(obj2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preGetter(String propertyName) {
/* 631 */     if (!this.intercepting) {
/*     */       return;
/*     */     }
/*     */     
/* 635 */     if (!this.loaded) {
/* 636 */       loadBean(propertyName);
/* 637 */     } else if (this.loadedProps != null && !this.loadedProps.contains(propertyName)) {
/* 638 */       loadBean(propertyName);
/*     */     } 
/*     */     
/* 641 */     if (this.nodeUsageCollector != null && this.loaded) {
/* 642 */       this.nodeUsageCollector.addUsed(propertyName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postSetter(PropertyChangeEvent event) {
/* 651 */     if (this.pcs != null && event != null) {
/* 652 */       this.pcs.firePropertyChange(event);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postSetter(PropertyChangeEvent event, Object newValue) {
/* 662 */     if (this.pcs != null && event != null) {
/* 663 */       if (newValue != null && newValue.equals(event.getNewValue())) {
/* 664 */         this.pcs.firePropertyChange(event);
/*     */       } else {
/* 666 */         this.pcs.firePropertyChange(event.getPropertyName(), event.getOldValue(), newValue);
/*     */       } 
/*     */     }
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
/*     */   public PropertyChangeEvent preSetterMany(boolean interceptField, String propertyName, Object oldValue, Object newValue) {
/* 680 */     if (this.pcs != null) {
/* 681 */       return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
/*     */     }
/* 683 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final void addDirty(String propertyName) {
/* 689 */     if (!this.intercepting) {
/*     */       return;
/*     */     }
/* 692 */     if (this.state >= 2) {
/* 693 */       throw new IllegalStateException("This bean is readOnly");
/*     */     }
/*     */     
/* 696 */     if (this.loaded) {
/* 697 */       if (this.oldValues == null)
/*     */       {
/* 699 */         createOldValues();
/*     */       }
/* 701 */       if (this.changedProps == null) {
/* 702 */         this.changedProps = new HashSet();
/*     */       }
/* 704 */       this.changedProps.add(propertyName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, Object oldValue, Object newValue) {
/* 714 */     boolean changed = !areEqual(oldValue, newValue);
/*     */     
/* 716 */     if (intercept && changed) {
/* 717 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 720 */     if (changed && this.pcs != null) {
/* 721 */       return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
/*     */     }
/*     */     
/* 724 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, boolean oldValue, boolean newValue) {
/* 732 */     boolean changed = (oldValue != newValue);
/*     */     
/* 734 */     if (intercept && changed) {
/* 735 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 738 */     if (changed && this.pcs != null) {
/* 739 */       return new PropertyChangeEvent(this.owner, propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
/*     */     }
/*     */     
/* 742 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, int oldValue, int newValue) {
/* 750 */     boolean changed = (oldValue != newValue);
/*     */     
/* 752 */     if (intercept && changed) {
/* 753 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 756 */     if (changed && this.pcs != null) {
/* 757 */       return new PropertyChangeEvent(this.owner, propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
/*     */     }
/* 759 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, long oldValue, long newValue) {
/* 767 */     boolean changed = (oldValue != newValue);
/*     */     
/* 769 */     if (intercept && changed) {
/* 770 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 773 */     if (changed && this.pcs != null) {
/* 774 */       return new PropertyChangeEvent(this.owner, propertyName, Long.valueOf(oldValue), Long.valueOf(newValue));
/*     */     }
/* 776 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, double oldValue, double newValue) {
/* 784 */     boolean changed = (oldValue != newValue);
/*     */     
/* 786 */     if (intercept && changed) {
/* 787 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 790 */     if (changed && this.pcs != null) {
/* 791 */       return new PropertyChangeEvent(this.owner, propertyName, Double.valueOf(oldValue), Double.valueOf(newValue));
/*     */     }
/* 793 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, float oldValue, float newValue) {
/* 801 */     boolean changed = (oldValue != newValue);
/*     */     
/* 803 */     if (intercept && changed) {
/* 804 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 807 */     if (changed && this.pcs != null) {
/* 808 */       return new PropertyChangeEvent(this.owner, propertyName, Float.valueOf(oldValue), Float.valueOf(newValue));
/*     */     }
/* 810 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, short oldValue, short newValue) {
/* 818 */     boolean changed = (oldValue != newValue);
/*     */     
/* 820 */     if (intercept && changed) {
/* 821 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 824 */     if (changed && this.pcs != null) {
/* 825 */       return new PropertyChangeEvent(this.owner, propertyName, Short.valueOf(oldValue), Short.valueOf(newValue));
/*     */     }
/* 827 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, char oldValue, char newValue) {
/* 835 */     boolean changed = (oldValue != newValue);
/*     */     
/* 837 */     if (intercept && changed) {
/* 838 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 841 */     if (changed && this.pcs != null) {
/* 842 */       return new PropertyChangeEvent(this.owner, propertyName, Character.valueOf(oldValue), Character.valueOf(newValue));
/*     */     }
/* 844 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, byte oldValue, byte newValue) {
/* 852 */     boolean changed = (oldValue != newValue);
/*     */     
/* 854 */     if (intercept && changed) {
/* 855 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 858 */     if (changed && this.pcs != null) {
/* 859 */       return new PropertyChangeEvent(this.owner, propertyName, Byte.valueOf(oldValue), Byte.valueOf(newValue));
/*     */     }
/* 861 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, char[] oldValue, char[] newValue) {
/* 869 */     boolean changed = !areEqualChars(oldValue, newValue);
/*     */     
/* 871 */     if (intercept && changed) {
/* 872 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 875 */     if (changed && this.pcs != null) {
/* 876 */       return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
/*     */     }
/* 878 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeEvent preSetter(boolean intercept, String propertyName, byte[] oldValue, byte[] newValue) {
/* 886 */     boolean changed = !areEqualBytes(oldValue, newValue);
/*     */     
/* 888 */     if (intercept && changed) {
/* 889 */       addDirty(propertyName);
/*     */     }
/*     */     
/* 892 */     if (changed && this.pcs != null) {
/* 893 */       return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
/*     */     }
/* 895 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean areEqualBytes(byte[] b1, byte[] b2) {
/* 901 */     if (b1 == null) {
/* 902 */       return (b2 == null);
/*     */     }
/* 904 */     if (b2 == null) {
/* 905 */       return false;
/*     */     }
/* 907 */     if (b1 == b2) {
/* 908 */       return true;
/*     */     }
/* 910 */     if (b1.length != b2.length) {
/* 911 */       return false;
/*     */     }
/* 913 */     for (int i = 0; i < b1.length; i++) {
/* 914 */       if (b1[i] != b2[i]) {
/* 915 */         return false;
/*     */       }
/*     */     } 
/* 918 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean areEqualChars(char[] b1, char[] b2) {
/* 922 */     if (b1 == null) {
/* 923 */       return (b2 == null);
/*     */     }
/* 925 */     if (b2 == null) {
/* 926 */       return false;
/*     */     }
/* 928 */     if (b1 == b2) {
/* 929 */       return true;
/*     */     }
/* 931 */     if (b1.length != b2.length) {
/* 932 */       return false;
/*     */     }
/* 934 */     for (int i = 0; i < b1.length; i++) {
/* 935 */       if (b1[i] != b2[i]) {
/* 936 */         return false;
/*     */       }
/*     */     } 
/* 939 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\bean\EntityBeanIntercept.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */