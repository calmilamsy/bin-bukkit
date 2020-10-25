/*     */ package org.yaml.snakeyaml.introspector;
/*     */ 
/*     */ import java.beans.IntrospectionException;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
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
/*     */ public class PropertyUtils
/*     */ {
/*  35 */   private final Map<Class<?>, Map<String, Property>> propertiesCache = new HashMap();
/*  36 */   private final Map<Class<?>, Set<Property>> readableProperties = new HashMap();
/*  37 */   private BeanAccess beanAccess = BeanAccess.DEFAULT;
/*     */   
/*     */   private boolean allowReadOnlyProperties = false;
/*     */   
/*     */   private Map<String, Property> getPropertiesMap(Class<?> type, BeanAccess bAccess) throws IntrospectionException {
/*  42 */     if (this.propertiesCache.containsKey(type)) {
/*  43 */       return (Map)this.propertiesCache.get(type);
/*     */     }
/*     */     
/*  46 */     Map<String, Property> properties = new HashMap<String, Property>();
/*  47 */     switch (bAccess) {
/*     */       case FIELD:
/*  49 */         for (c = type; c != null; c = c.getSuperclass()) {
/*  50 */           for (Field field : c.getDeclaredFields()) {
/*  51 */             int modifiers = field.getModifiers();
/*  52 */             if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers) && !properties.containsKey(field.getName()))
/*     */             {
/*     */               
/*  55 */               properties.put(field.getName(), new FieldProperty(field));
/*     */             }
/*     */           } 
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/*  64 */         for (PropertyDescriptor property : Introspector.getBeanInfo(type).getPropertyDescriptors()) {
/*  65 */           Method readMethod = property.getReadMethod();
/*  66 */           if (readMethod == null || !readMethod.getName().equals("getClass"))
/*     */           {
/*  68 */             properties.put(property.getName(), new MethodProperty(property));
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  74 */         for (Field field : type.getFields()) {
/*  75 */           int modifiers = field.getModifiers();
/*  76 */           if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers))
/*     */           {
/*  78 */             properties.put(field.getName(), new FieldProperty(field));
/*     */           }
/*     */         } 
/*     */         break;
/*     */     } 
/*     */     
/*  84 */     if (properties.isEmpty()) {
/*  85 */       throw new YAMLException("No JavaBean properties found in " + type.getName());
/*     */     }
/*     */     
/*  88 */     this.propertiesCache.put(type, properties);
/*  89 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public Set<Property> getProperties(Class<? extends Object> type) throws IntrospectionException { return getProperties(type, this.beanAccess); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Property> getProperties(Class<? extends Object> type, BeanAccess bAccess) throws IntrospectionException {
/*  99 */     if (this.readableProperties.containsKey(type)) {
/* 100 */       return (Set)this.readableProperties.get(type);
/*     */     }
/* 102 */     Set<Property> properties = new TreeSet<Property>();
/* 103 */     Collection<Property> props = getPropertiesMap(type, bAccess).values();
/* 104 */     for (Property property : props) {
/* 105 */       if (property.isReadable() && (this.allowReadOnlyProperties || property.isWritable()))
/*     */       {
/* 107 */         properties.add(property);
/*     */       }
/*     */     } 
/*     */     
/* 111 */     if (properties.isEmpty()) {
/* 112 */       throw new YAMLException("No JavaBean properties found in " + type.getName());
/*     */     }
/*     */ 
/*     */     
/* 116 */     this.readableProperties.put(type, properties);
/* 117 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public Property getProperty(Class<? extends Object> type, String name) throws IntrospectionException { return getProperty(type, name, this.beanAccess); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Property getProperty(Class<? extends Object> type, String name, BeanAccess bAccess) throws IntrospectionException {
/* 127 */     Map<String, Property> properties = getPropertiesMap(type, bAccess);
/* 128 */     Property property = (Property)properties.get(name);
/* 129 */     if (property == null || !property.isWritable()) {
/* 130 */       throw new YAMLException("Unable to find property '" + name + "' on class: " + type.getName());
/*     */     }
/*     */     
/* 133 */     return property;
/*     */   }
/*     */   
/*     */   public void setBeanAccess(BeanAccess beanAccess) {
/* 137 */     if (this.beanAccess != beanAccess) {
/* 138 */       this.beanAccess = beanAccess;
/* 139 */       this.propertiesCache.clear();
/* 140 */       this.readableProperties.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAllowReadOnlyProperties(boolean allowReadOnlyProperties) {
/* 145 */     if (this.allowReadOnlyProperties != allowReadOnlyProperties) {
/* 146 */       this.allowReadOnlyProperties = allowReadOnlyProperties;
/* 147 */       this.readableProperties.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\introspector\PropertyUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */