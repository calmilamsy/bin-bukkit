/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.DeployInheritInfo;
/*     */ import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
/*     */ import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ public class InheritInfo
/*     */ {
/*     */   private final String discriminatorStringValue;
/*     */   private final Object discriminatorValue;
/*     */   private final String discriminatorColumn;
/*     */   private final int discriminatorType;
/*     */   private final int discriminatorLength;
/*     */   private final String where;
/*     */   private final Class<?> type;
/*     */   private final ArrayList<InheritInfo> children;
/*     */   private final HashMap<String, InheritInfo> discMap;
/*     */   private final HashMap<String, InheritInfo> typeMap;
/*     */   private final InheritInfo parent;
/*     */   private final InheritInfo root;
/*     */   private BeanDescriptor<?> descriptor;
/*     */   
/*     */   public InheritInfo(InheritInfo r, InheritInfo parent, DeployInheritInfo deploy) {
/*  53 */     this.children = new ArrayList();
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
/*  73 */     this.parent = parent;
/*  74 */     this.type = deploy.getType();
/*  75 */     this.discriminatorColumn = InternString.intern(deploy.getDiscriminatorColumn(parent));
/*  76 */     this.discriminatorValue = deploy.getDiscriminatorObjectValue();
/*  77 */     this.discriminatorStringValue = deploy.getDiscriminatorStringValue();
/*     */     
/*  79 */     this.discriminatorType = deploy.getDiscriminatorType(parent);
/*  80 */     this.discriminatorLength = deploy.getDiscriminatorLength(parent);
/*  81 */     this.where = InternString.intern(deploy.getWhere());
/*     */     
/*  83 */     if (r == null) {
/*     */       
/*  85 */       this.root = this;
/*  86 */       this.discMap = new HashMap();
/*  87 */       this.typeMap = new HashMap();
/*  88 */       registerWithRoot(this);
/*     */     } else {
/*     */       
/*  91 */       this.root = r;
/*     */       
/*  93 */       this.discMap = null;
/*  94 */       this.typeMap = null;
/*  95 */       this.root.registerWithRoot(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitChildren(InheritInfoVisitor visitor) {
/* 104 */     for (int i = 0; i < this.children.size(); i++) {
/* 105 */       InheritInfo child = (InheritInfo)this.children.get(i);
/* 106 */       visitor.visit(child);
/* 107 */       child.visitChildren(visitor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public boolean isSaveRecurseSkippable() { return this.root.isNodeSaveRecurseSkippable(); }
/*     */ 
/*     */   
/*     */   private boolean isNodeSaveRecurseSkippable() {
/* 120 */     if (!this.descriptor.isSaveRecurseSkippable()) {
/* 121 */       return false;
/*     */     }
/* 123 */     for (int i = 0; i < this.children.size(); i++) {
/* 124 */       InheritInfo child = (InheritInfo)this.children.get(i);
/* 125 */       if (!child.isNodeSaveRecurseSkippable()) {
/* 126 */         return false;
/*     */       }
/*     */     } 
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public boolean isDeleteRecurseSkippable() { return this.root.isNodeDeleteRecurseSkippable(); }
/*     */ 
/*     */   
/*     */   private boolean isNodeDeleteRecurseSkippable() {
/* 141 */     if (!this.descriptor.isDeleteRecurseSkippable()) {
/* 142 */       return false;
/*     */     }
/* 144 */     for (int i = 0; i < this.children.size(); i++) {
/* 145 */       InheritInfo child = (InheritInfo)this.children.get(i);
/* 146 */       if (!child.isNodeDeleteRecurseSkippable()) {
/* 147 */         return false;
/*     */       }
/*     */     } 
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public void setDescriptor(BeanDescriptor<?> descriptor) { this.descriptor = descriptor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public BeanDescriptor<?> getBeanDescriptor() { return this.descriptor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty findSubTypeProperty(String propertyName) {
/* 173 */     BeanProperty prop = null;
/*     */     
/* 175 */     for (int i = 0, x = this.children.size(); i < x; i++) {
/* 176 */       InheritInfo childInfo = (InheritInfo)this.children.get(i);
/*     */ 
/*     */       
/* 179 */       prop = childInfo.getBeanDescriptor().findBeanProperty(propertyName);
/*     */       
/* 181 */       if (prop != null) {
/* 182 */         return prop;
/*     */       }
/*     */     } 
/*     */     
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChildrenProperties(SqlTreeProperties selectProps) {
/* 194 */     for (int i = 0, x = this.children.size(); i < x; i++) {
/* 195 */       InheritInfo childInfo = (InheritInfo)this.children.get(i);
/* 196 */       selectProps.add(childInfo.descriptor.propertiesLocal());
/*     */       
/* 198 */       childInfo.addChildrenProperties(selectProps);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo readType(DbReadContext ctx) throws SQLException {
/* 207 */     String discValue = ctx.getDataReader().getString();
/* 208 */     return readType(discValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo readType(String discValue) {
/* 216 */     if (discValue == null) {
/* 217 */       return null;
/*     */     }
/*     */     
/* 220 */     InheritInfo typeInfo = this.root.getType(discValue);
/* 221 */     if (typeInfo == null) {
/* 222 */       String m = "Inheritance type for discriminator value [" + discValue + "] was not found?";
/* 223 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 226 */     return typeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo readType(Class<?> beanType) {
/* 234 */     InheritInfo typeInfo = this.root.getTypeByClass(beanType);
/* 235 */     if (typeInfo == null) {
/* 236 */       String m = "Inheritance type for bean type [" + beanType.getName() + "] was not found?";
/* 237 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 240 */     return typeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   public Object createBean(boolean vanillaMode) { return this.descriptor.createBean(vanillaMode); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 254 */   public IdBinder getIdBinder() { return this.descriptor.getIdBinder(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public Class<?> getType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   public InheritInfo getRoot() { return this.root; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 278 */   public InheritInfo getParent() { return this.parent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 285 */   public boolean isAbstract() { return (this.discriminatorValue == null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public boolean isRoot() { return (this.parent == null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 299 */   public InheritInfo getType(String discValue) { return (InheritInfo)this.discMap.get(discValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InheritInfo getTypeByClass(Class<?> beanType) {
/* 306 */     String clsName = SubClassUtil.getSuperClassName(beanType.getName());
/* 307 */     return (InheritInfo)this.typeMap.get(clsName);
/*     */   }
/*     */   
/*     */   private void registerWithRoot(InheritInfo info) {
/* 311 */     if (info.getDiscriminatorStringValue() != null) {
/* 312 */       String stringDiscValue = info.getDiscriminatorStringValue();
/* 313 */       this.discMap.put(stringDiscValue, info);
/*     */     } 
/* 315 */     String clsName = SubClassUtil.getSuperClassName(info.getType().getName());
/* 316 */     this.typeMap.put(clsName, info);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 323 */   public void addChild(InheritInfo childInfo) { this.children.add(childInfo); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   public String getWhere() { return this.where; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 338 */   public String getDiscriminatorColumn() { return this.discriminatorColumn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 345 */   public int getDiscriminatorType() { return this.discriminatorType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 353 */   public int getDiscriminatorLength() { return this.discriminatorLength; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 360 */   public String getDiscriminatorStringValue() { return this.discriminatorStringValue; }
/*     */ 
/*     */ 
/*     */   
/* 364 */   public Object getDiscriminatorValue() { return this.discriminatorValue; }
/*     */ 
/*     */ 
/*     */   
/* 368 */   public String toString() { return "InheritInfo[" + this.type.getName() + "] disc[" + this.discriminatorStringValue + "]"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\InheritInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */