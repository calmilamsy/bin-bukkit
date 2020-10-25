/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BootupClasses;
/*     */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.persistence.DiscriminatorColumn;
/*     */ import javax.persistence.DiscriminatorType;
/*     */ import javax.persistence.DiscriminatorValue;
/*     */ import javax.persistence.Inheritance;
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
/*     */ public class DeployInherit
/*     */ {
/*     */   private final Map<Class<?>, DeployInheritInfo> deployMap;
/*     */   private final Map<Class<?>, InheritInfo> finalMap;
/*     */   private final BootupClasses bootupClasses;
/*     */   
/*     */   public DeployInherit(BootupClasses bootupClasses) {
/*  43 */     this.deployMap = new LinkedHashMap();
/*     */     
/*  45 */     this.finalMap = new LinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     this.bootupClasses = bootupClasses;
/*  54 */     initialise();
/*     */   }
/*     */   
/*     */   public void process(DeployBeanDescriptor<?> desc) {
/*  58 */     InheritInfo inheritInfo = (InheritInfo)this.finalMap.get(desc.getBeanType());
/*  59 */     desc.setInheritInfo(inheritInfo);
/*     */   }
/*     */   
/*     */   private void initialise() {
/*  63 */     List<Class<?>> entityList = this.bootupClasses.getEntities();
/*     */     
/*  65 */     findInheritClasses(entityList);
/*  66 */     buildDeployTree();
/*  67 */     buildFinalTree();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void findInheritClasses(List<Class<?>> entityList) {
/*  73 */     Iterator<Class<?>> it = entityList.iterator();
/*  74 */     while (it.hasNext()) {
/*  75 */       Class<?> cls = (Class)it.next();
/*  76 */       if (isInheritanceClass(cls)) {
/*  77 */         DeployInheritInfo info = createInfo(cls);
/*  78 */         this.deployMap.put(cls, info);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildDeployTree() {
/*  84 */     Iterator<DeployInheritInfo> it = this.deployMap.values().iterator();
/*  85 */     while (it.hasNext()) {
/*  86 */       DeployInheritInfo info = (DeployInheritInfo)it.next();
/*  87 */       if (!info.isRoot()) {
/*  88 */         DeployInheritInfo parent = getInfo(info.getParent());
/*  89 */         parent.addChild(info);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildFinalTree() {
/*  96 */     Iterator<DeployInheritInfo> it = this.deployMap.values().iterator();
/*  97 */     while (it.hasNext()) {
/*  98 */       DeployInheritInfo deploy = (DeployInheritInfo)it.next();
/*  99 */       if (deploy.isRoot())
/*     */       {
/* 101 */         createFinalInfo(null, null, deploy);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InheritInfo createFinalInfo(InheritInfo root, InheritInfo parent, DeployInheritInfo deploy) {
/* 110 */     InheritInfo node = new InheritInfo(root, parent, deploy);
/* 111 */     if (parent != null) {
/* 112 */       parent.addChild(node);
/*     */     }
/* 114 */     this.finalMap.put(node.getType(), node);
/*     */     
/* 116 */     if (root == null) {
/* 117 */       root = node;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 122 */     Iterator<DeployInheritInfo> it = deploy.children();
/*     */     
/* 124 */     while (it.hasNext()) {
/* 125 */       DeployInheritInfo childDeploy = (DeployInheritInfo)it.next();
/*     */       
/* 127 */       createFinalInfo(root, node, childDeploy);
/*     */     } 
/*     */     
/* 130 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   private DeployInheritInfo getInfo(Class<?> cls) { return (DeployInheritInfo)this.deployMap.get(cls); }
/*     */ 
/*     */ 
/*     */   
/*     */   private DeployInheritInfo createInfo(Class<?> cls) {
/* 142 */     DeployInheritInfo info = new DeployInheritInfo(cls);
/*     */     
/* 144 */     Class<?> parent = findParent(cls);
/* 145 */     if (parent != null) {
/* 146 */       info.setParent(parent);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 151 */     Inheritance ia = (Inheritance)cls.getAnnotation(Inheritance.class);
/* 152 */     if (ia != null) {
/* 153 */       ia.strategy();
/*     */     }
/* 155 */     DiscriminatorColumn da = (DiscriminatorColumn)cls.getAnnotation(DiscriminatorColumn.class);
/* 156 */     if (da != null) {
/* 157 */       info.setDiscriminatorColumn(da.name());
/* 158 */       DiscriminatorType discriminatorType = da.discriminatorType();
/* 159 */       if (discriminatorType.equals(DiscriminatorType.INTEGER)) {
/* 160 */         info.setDiscriminatorType(4);
/*     */       } else {
/* 162 */         info.setDiscriminatorType(12);
/*     */       } 
/* 164 */       info.setDiscriminatorLength(da.length());
/*     */     } 
/*     */     
/* 167 */     DiscriminatorValue dv = (DiscriminatorValue)cls.getAnnotation(DiscriminatorValue.class);
/* 168 */     if (dv != null) {
/* 169 */       info.setDiscriminatorValue(dv.value());
/*     */     }
/*     */     
/* 172 */     return info;
/*     */   }
/*     */   
/*     */   private Class<?> findParent(Class<?> cls) {
/* 176 */     Class<?> superCls = cls.getSuperclass();
/* 177 */     if (isInheritanceClass(superCls)) {
/* 178 */       return superCls;
/*     */     }
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isInheritanceClass(Class<?> cls) {
/* 185 */     if (cls.equals(Object.class)) {
/* 186 */       return false;
/*     */     }
/* 188 */     Annotation a = cls.getAnnotation(Inheritance.class);
/* 189 */     if (a != null) {
/* 190 */       return true;
/*     */     }
/*     */     
/* 193 */     return isInheritanceClass(cls.getSuperclass());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\DeployInherit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */