/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.deploy.DetermineManyType;
/*     */ import com.avaje.ebeaninternal.server.deploy.ManyType;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertySimpleCollection;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundType;
/*     */ import com.avaje.ebeaninternal.server.type.ScalaOptionTypeConverter;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.persistence.Transient;
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
/*     */ public class DeployCreateProperties
/*     */ {
/*  59 */   private static final Logger logger = Logger.getLogger(DeployCreateProperties.class.getName());
/*     */ 
/*     */   
/*     */   private final Class<?> scalaOptionClass;
/*     */ 
/*     */   
/*     */   private final ScalarTypeConverter scalaOptionTypeConverter;
/*     */ 
/*     */   
/*     */   private final DetermineManyType determineManyType;
/*     */   
/*     */   private final TypeManager typeManager;
/*     */ 
/*     */   
/*     */   public DeployCreateProperties(TypeManager typeManager) {
/*  74 */     this.typeManager = typeManager;
/*     */     
/*  76 */     Class<?> tmpOptionClass = DetectScala.getScalaOptionClass();
/*     */     
/*  78 */     if (tmpOptionClass == null) {
/*  79 */       this.scalaOptionClass = null;
/*  80 */       this.scalaOptionTypeConverter = null;
/*     */     } else {
/*  82 */       this.scalaOptionClass = tmpOptionClass;
/*  83 */       this.scalaOptionTypeConverter = new ScalaOptionTypeConverter();
/*     */     } 
/*     */     
/*  86 */     this.determineManyType = new DetermineManyType((tmpOptionClass != null) ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createProperties(DeployBeanDescriptor<?> desc) {
/*  94 */     createProperties(desc, desc.getBeanType(), 0);
/*  95 */     desc.sortProperties();
/*     */ 
/*     */     
/*  98 */     Iterator<DeployBeanProperty> it = desc.propertiesAll();
/*     */     
/* 100 */     while (it.hasNext()) {
/* 101 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/* 102 */       if (prop.isTransient()) {
/* 103 */         if (prop.getWriteMethod() == null || prop.getReadMethod() == null) {
/*     */           
/* 105 */           logger.finest("... transient: " + prop.getFullBeanName());
/*     */           continue;
/*     */         } 
/* 108 */         String msg = Message.msg("deploy.property.nofield", desc.getFullName(), prop.getName());
/* 109 */         logger.warning(msg);
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
/*     */   private boolean ignoreFieldByName(String fieldName) {
/* 122 */     if (fieldName.startsWith("_ebean_"))
/*     */     {
/* 124 */       return true;
/*     */     }
/* 126 */     if (fieldName.startsWith("ajc$instance$"))
/*     */     {
/* 128 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createProperties(DeployBeanDescriptor<?> desc, Class<?> beanType, int level) {
/* 141 */     boolean scalaObject = desc.isScalaObject();
/*     */     
/*     */     try {
/* 144 */       Method[] declaredMethods = beanType.getDeclaredMethods();
/* 145 */       Field[] fields = beanType.getDeclaredFields();
/*     */       
/* 147 */       for (i = 0; i < fields.length; i++) {
/*     */         
/* 149 */         Field field = fields[i];
/* 150 */         if (!Modifier.isStatic(field.getModifiers()))
/*     */         {
/*     */           
/* 153 */           if (Modifier.isTransient(field.getModifiers())) {
/*     */             
/* 155 */             logger.finer("Skipping transient field " + field.getName() + " in " + beanType.getName());
/*     */           }
/* 157 */           else if (!ignoreFieldByName(field.getName())) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 162 */             String fieldName = getFieldName(field, beanType);
/* 163 */             String initFieldName = initCap(fieldName);
/*     */             
/* 165 */             Method getter = findGetter(field, initFieldName, declaredMethods, scalaObject);
/* 166 */             Method setter = findSetter(field, initFieldName, declaredMethods, scalaObject);
/*     */             
/* 168 */             DeployBeanProperty prop = createProp(level, desc, field, beanType, getter, setter);
/*     */ 
/*     */             
/* 171 */             int sortOverride = prop.getSortOverride();
/* 172 */             prop.setSortOrder(level * 10000 + 100 - i + sortOverride);
/*     */             
/* 174 */             DeployBeanProperty replaced = desc.addBeanProperty(prop);
/* 175 */             if (replaced != null && 
/* 176 */               !replaced.isTransient()) {
/*     */ 
/*     */               
/* 179 */               String msg = "Huh??? property " + prop.getFullBeanName() + " being defined twice";
/* 180 */               msg = msg + " but replaced property was not transient? This is not expected?";
/* 181 */               logger.warning(msg);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 187 */       Class<?> superClass = beanType.getSuperclass();
/*     */       
/* 189 */       if (!superClass.equals(Object.class))
/*     */       {
/*     */         
/* 192 */         createProperties(desc, superClass, level + 1);
/*     */       }
/*     */     }
/* 195 */     catch (PersistenceException ex) {
/* 196 */       throw ex;
/*     */     }
/* 198 */     catch (Exception ex) {
/* 199 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String initCap(String str) {
/* 207 */     if (str.length() > 1) {
/* 208 */       return Character.toUpperCase(str.charAt(0)) + str.substring(1);
/*     */     }
/*     */     
/* 211 */     return str.toUpperCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFieldName(Field field, Class<?> beanType) {
/* 220 */     String name = field.getName();
/*     */     
/* 222 */     if ((Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) && name.startsWith("is") && name.length() > 2) {
/*     */ 
/*     */ 
/*     */       
/* 226 */       char c = name.charAt(2);
/* 227 */       if (Character.isUpperCase(c)) {
/* 228 */         String msg = "trimming off 'is' from boolean field name " + name + " in class " + beanType.getName();
/* 229 */         logger.log(Level.INFO, msg);
/*     */         
/* 231 */         return name.substring(2);
/*     */       } 
/*     */     } 
/* 234 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Method findGetter(Field field, String initFieldName, Method[] declaredMethods, boolean scalaObject) {
/* 242 */     String methGetName = "get" + initFieldName;
/* 243 */     String methIsName = "is" + initFieldName;
/* 244 */     String scalaGet = field.getName();
/*     */     
/* 246 */     for (int i = 0; i < declaredMethods.length; i++) {
/* 247 */       Method m = declaredMethods[i];
/* 248 */       if ((scalaObject && m.getName().equals(scalaGet)) || m.getName().equals(methGetName) || m.getName().equals(methIsName)) {
/*     */ 
/*     */         
/* 251 */         Class[] params = m.getParameterTypes();
/* 252 */         if (params.length == 0 && 
/* 253 */           field.getType().equals(m.getReturnType())) {
/* 254 */           int modifiers = m.getModifiers();
/* 255 */           if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers))
/*     */           {
/* 257 */             return m;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Method findSetter(Field field, String initFieldName, Method[] declaredMethods, boolean scalaObject) {
/* 271 */     String methSetName = "set" + initFieldName;
/* 272 */     String scalaSetName = field.getName() + "_$eq";
/*     */     
/* 274 */     for (int i = 0; i < declaredMethods.length; i++) {
/* 275 */       Method m = declaredMethods[i];
/*     */       
/* 277 */       if ((scalaObject && m.getName().equals(scalaSetName)) || m.getName().equals(methSetName)) {
/*     */ 
/*     */         
/* 280 */         Class[] params = m.getParameterTypes();
/* 281 */         if (params.length == 1 && field.getType().equals(params[0]) && 
/* 282 */           void.class.equals(m.getReturnType())) {
/* 283 */           int modifiers = m.getModifiers();
/* 284 */           if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
/* 285 */             return m;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DeployBeanProperty createManyType(DeployBeanDescriptor<?> desc, Class<?> targetType, ManyType manyType) {
/* 297 */     ScalarType<?> scalarType = this.typeManager.getScalarType(targetType);
/* 298 */     if (scalarType != null) {
/* 299 */       return new DeployBeanPropertySimpleCollection(desc, targetType, scalarType, manyType);
/*     */     }
/*     */     
/* 302 */     return new DeployBeanPropertyAssocMany(desc, targetType, manyType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DeployBeanProperty createProp(DeployBeanDescriptor<?> desc, Field field) {
/* 308 */     Class<?> propertyType = field.getType();
/* 309 */     Class<?> innerType = propertyType;
/* 310 */     ScalarTypeConverter<?, ?> typeConverter = null;
/*     */     
/* 312 */     if (propertyType.equals(this.scalaOptionClass)) {
/* 313 */       innerType = determineTargetType(field);
/* 314 */       typeConverter = this.scalaOptionTypeConverter;
/*     */     } 
/*     */ 
/*     */     
/* 318 */     ManyType manyType = this.determineManyType.getManyType(propertyType);
/*     */     
/* 320 */     if (manyType != null) {
/*     */       
/* 322 */       Class<?> targetType = determineTargetType(field);
/* 323 */       if (targetType == null) {
/* 324 */         logger.warning("Could not find parameter type (via reflection) on " + desc.getFullName() + " " + field.getName());
/*     */       }
/* 326 */       return createManyType(desc, targetType, manyType);
/*     */     } 
/*     */     
/* 329 */     if (innerType.isEnum() || innerType.isPrimitive()) {
/* 330 */       return new DeployBeanProperty(desc, propertyType, null, typeConverter);
/*     */     }
/*     */     
/* 333 */     ScalarType<?> scalarType = this.typeManager.getScalarType(innerType);
/* 334 */     if (scalarType != null) {
/* 335 */       return new DeployBeanProperty(desc, propertyType, scalarType, typeConverter);
/*     */     }
/*     */     
/* 338 */     CtCompoundType<?> compoundType = this.typeManager.getCompoundType(innerType);
/* 339 */     if (compoundType != null) {
/* 340 */       return new DeployBeanPropertyCompound(desc, propertyType, compoundType, typeConverter);
/*     */     }
/*     */     
/* 343 */     if (!isTransientField(field)) {
/*     */       try {
/* 345 */         CheckImmutableResponse checkImmutable = this.typeManager.checkImmutable(innerType);
/* 346 */         if (checkImmutable.isImmutable()) {
/* 347 */           if (checkImmutable.isCompoundType()) {
/*     */             
/* 349 */             this.typeManager.recursiveCreateScalarDataReader(innerType);
/* 350 */             compoundType = this.typeManager.getCompoundType(innerType);
/* 351 */             if (compoundType != null) {
/* 352 */               return new DeployBeanPropertyCompound(desc, propertyType, compoundType, typeConverter);
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 357 */             scalarType = this.typeManager.recursiveCreateScalarTypes(innerType);
/* 358 */             return new DeployBeanProperty(desc, propertyType, scalarType, typeConverter);
/*     */           } 
/*     */         }
/* 361 */       } catch (Exception e) {
/* 362 */         logger.log(Level.SEVERE, "Error with " + desc + " field:" + field.getName(), e);
/*     */       } 
/*     */     }
/*     */     
/* 366 */     return new DeployBeanPropertyAssocOne(desc, propertyType);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTransientField(Field field) {
/* 371 */     Transient t = (Transient)field.getAnnotation(Transient.class);
/* 372 */     return (t != null);
/*     */   }
/*     */ 
/*     */   
/*     */   private DeployBeanProperty createProp(int level, DeployBeanDescriptor<?> desc, Field field, Class<?> beanType, Method getter, Method setter) {
/* 377 */     DeployBeanProperty prop = createProp(desc, field);
/*     */ 
/*     */     
/* 380 */     prop.setOwningType(beanType);
/* 381 */     prop.setName(field.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     prop.setReadMethod(getter);
/* 387 */     prop.setWriteMethod(setter);
/*     */     
/* 389 */     prop.setField(field);
/*     */     
/* 391 */     return prop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> determineTargetType(Field field) {
/* 400 */     Type genType = field.getGenericType();
/* 401 */     if (genType instanceof ParameterizedType) {
/* 402 */       ParameterizedType ptype = (ParameterizedType)genType;
/*     */       
/* 404 */       Type[] typeArgs = ptype.getActualTypeArguments();
/* 405 */       if (typeArgs.length == 1) {
/*     */         
/* 407 */         if (typeArgs[0] instanceof Class) {
/* 408 */           return (Class)typeArgs[0];
/*     */         }
/* 410 */         throw new RuntimeException("Unexpected Parameterised Type? " + typeArgs[0]);
/*     */       } 
/* 412 */       if (typeArgs.length == 2)
/*     */       {
/* 414 */         return (Class)typeArgs[1];
/*     */       }
/*     */     } 
/*     */     
/* 418 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\DeployCreateProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */