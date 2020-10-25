/*     */ package org.yaml.snakeyaml.constructor;
/*     */ 
/*     */ import java.beans.IntrospectionException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import org.yaml.snakeyaml.TypeDescription;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.Property;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
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
/*     */ public class Constructor
/*     */   extends SafeConstructor
/*     */ {
/*     */   private final Map<Tag, Class<? extends Object>> typeTags;
/*     */   private final Map<Class<? extends Object>, TypeDescription> typeDefinitions;
/*     */   
/*  56 */   public Constructor() { this(Object.class); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Constructor(Class<? extends Object> theRoot) {
/*  66 */     if (theRoot == null) {
/*  67 */       throw new NullPointerException("Root type must be provided.");
/*     */     }
/*  69 */     this.yamlConstructors.put(null, new ConstructYamlObject(null));
/*  70 */     if (!Object.class.equals(theRoot)) {
/*  71 */       this.rootTag = new Tag(theRoot);
/*     */     }
/*  73 */     this.typeTags = new HashMap();
/*  74 */     this.typeDefinitions = new HashMap();
/*  75 */     this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalar());
/*  76 */     this.yamlClassConstructors.put(NodeId.mapping, new ConstructMapping(null));
/*  77 */     this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequence(null));
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
/*  90 */   public Constructor(String theRoot) throws ClassNotFoundException { this(Class.forName(check(theRoot))); }
/*     */ 
/*     */   
/*     */   private static final String check(String s) {
/*  94 */     if (s == null) {
/*  95 */       throw new NullPointerException("Root type must be provided.");
/*     */     }
/*  97 */     if (s.trim().length() == 0) {
/*  98 */       throw new YAMLException("Root type must be provided.");
/*     */     }
/* 100 */     return s;
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
/*     */   public TypeDescription addTypeDescription(TypeDescription definition) {
/* 114 */     if (definition == null) {
/* 115 */       throw new NullPointerException("TypeDescription is required.");
/*     */     }
/* 117 */     if (this.rootTag == null && definition.isRoot()) {
/* 118 */       this.rootTag = new Tag(definition.getType());
/*     */     }
/* 120 */     Tag tag = definition.getTag();
/* 121 */     this.typeTags.put(tag, definition.getType());
/* 122 */     return (TypeDescription)this.typeDefinitions.put(definition.getType(), definition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ConstructMapping
/*     */     implements Construct
/*     */   {
/*     */     private ConstructMapping() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object construct(Node node) {
/* 141 */       MappingNode mnode = (MappingNode)node;
/* 142 */       if (Properties.class.isAssignableFrom(node.getType())) {
/* 143 */         Properties properties = new Properties();
/* 144 */         if (!node.isTwoStepsConstruction()) {
/* 145 */           Constructor.this.constructMapping2ndStep(mnode, properties);
/*     */         } else {
/* 147 */           throw new YAMLException("Properties must not be recursive.");
/*     */         } 
/* 149 */         return properties;
/* 150 */       }  if (SortedMap.class.isAssignableFrom(node.getType())) {
/* 151 */         SortedMap<Object, Object> map = new TreeMap<Object, Object>();
/* 152 */         if (!node.isTwoStepsConstruction()) {
/* 153 */           Constructor.this.constructMapping2ndStep(mnode, map);
/*     */         }
/* 155 */         return map;
/* 156 */       }  if (Map.class.isAssignableFrom(node.getType())) {
/* 157 */         if (node.isTwoStepsConstruction()) {
/* 158 */           return Constructor.this.createDefaultMap();
/*     */         }
/* 160 */         return Constructor.this.constructMapping(mnode);
/*     */       } 
/* 162 */       if (SortedSet.class.isAssignableFrom(node.getType())) {
/* 163 */         SortedSet<Object> set = new TreeSet<Object>();
/*     */ 
/*     */         
/* 166 */         Constructor.this.constructSet2ndStep(mnode, set);
/*     */         
/* 168 */         return set;
/* 169 */       }  if (java.util.Collection.class.isAssignableFrom(node.getType())) {
/* 170 */         if (node.isTwoStepsConstruction()) {
/* 171 */           return Constructor.this.createDefaultSet();
/*     */         }
/* 173 */         return Constructor.this.constructSet(mnode);
/*     */       } 
/*     */       
/* 176 */       if (node.isTwoStepsConstruction()) {
/* 177 */         return createEmptyJavaBean(mnode);
/*     */       }
/* 179 */       return constructJavaBean2ndStep(mnode, createEmptyJavaBean(mnode));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 186 */       if (Map.class.isAssignableFrom(node.getType())) {
/* 187 */         Constructor.this.constructMapping2ndStep((MappingNode)node, (Map)object);
/* 188 */       } else if (Set.class.isAssignableFrom(node.getType())) {
/* 189 */         Constructor.this.constructSet2ndStep((MappingNode)node, (Set)object);
/*     */       } else {
/* 191 */         constructJavaBean2ndStep((MappingNode)node, object);
/*     */       } 
/*     */     }
/*     */     
/*     */     private Object createEmptyJavaBean(MappingNode node) {
/*     */       try {
/* 197 */         Class<? extends Object> type = node.getType();
/* 198 */         if (Modifier.isAbstract(type.getModifiers())) {
/* 199 */           node.setType(Constructor.this.getClassForNode(node));
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 209 */         Constructor<?> c = node.getType().getDeclaredConstructor(new Class[0]);
/* 210 */         c.setAccessible(true);
/* 211 */         return c.newInstance(new Object[0]);
/* 212 */       } catch (Exception e) {
/* 213 */         throw new YAMLException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private Object constructJavaBean2ndStep(MappingNode node, Object object) {
/* 219 */       Class<? extends Object> beanType = node.getType();
/* 220 */       List<NodeTuple> nodeValue = node.getValue();
/* 221 */       for (NodeTuple tuple : nodeValue) {
/*     */         ScalarNode keyNode;
/* 223 */         if (tuple.getKeyNode() instanceof ScalarNode) {
/*     */           
/* 225 */           keyNode = (ScalarNode)tuple.getKeyNode();
/*     */         } else {
/* 227 */           throw new YAMLException("Keys must be scalars but found: " + tuple.getKeyNode());
/*     */         } 
/* 229 */         Node valueNode = tuple.getValueNode();
/*     */         
/* 231 */         keyNode.setType(String.class);
/* 232 */         String key = (String)Constructor.this.constructObject(keyNode);
/*     */         try {
/* 234 */           Property property = getProperty(beanType, key);
/* 235 */           valueNode.setType(property.getType());
/* 236 */           TypeDescription memberDescription = (TypeDescription)Constructor.this.typeDefinitions.get(beanType);
/* 237 */           boolean typeDetected = false;
/* 238 */           if (memberDescription != null) {
/* 239 */             Class<? extends Object> keyType; MappingNode mnode; Class<? extends Object> memberType; SequenceNode snode; switch (Constructor.null.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[valueNode.getNodeId().ordinal()]) {
/*     */               case 1:
/* 241 */                 snode = (SequenceNode)valueNode;
/* 242 */                 memberType = memberDescription.getListPropertyType(key);
/*     */                 
/* 244 */                 if (memberType != null) {
/* 245 */                   snode.setListType(memberType);
/* 246 */                   typeDetected = true; break;
/* 247 */                 }  if (property.getType().isArray()) {
/* 248 */                   snode.setListType(property.getType().getComponentType());
/* 249 */                   typeDetected = true;
/*     */                 } 
/*     */                 break;
/*     */               case 2:
/* 253 */                 mnode = (MappingNode)valueNode;
/* 254 */                 keyType = memberDescription.getMapKeyType(key);
/* 255 */                 if (keyType != null) {
/* 256 */                   mnode.setKeyType(keyType);
/* 257 */                   mnode.setValueType(memberDescription.getMapValueType(key));
/* 258 */                   typeDetected = true;
/*     */                 } 
/*     */                 break;
/*     */             } 
/*     */           } 
/* 263 */           if (!typeDetected && valueNode.getNodeId() != NodeId.scalar) {
/*     */             
/* 265 */             Class[] arguments = property.getActualTypeArguments();
/* 266 */             if (arguments != null)
/*     */             {
/*     */               
/* 269 */               if (valueNode.getNodeId() == NodeId.sequence) {
/* 270 */                 Class t = arguments[0];
/* 271 */                 SequenceNode snode = (SequenceNode)valueNode;
/* 272 */                 snode.setListType(t);
/* 273 */               } else if (valueNode.getTag().equals(Tag.SET)) {
/* 274 */                 Class t = arguments[0];
/* 275 */                 MappingNode mnode = (MappingNode)valueNode;
/* 276 */                 mnode.setKeyType(t);
/* 277 */                 mnode.setUseClassConstructor(Boolean.valueOf(true));
/* 278 */               } else if (valueNode.getNodeId() == NodeId.mapping) {
/* 279 */                 Class ketType = arguments[0];
/* 280 */                 Class valueType = arguments[1];
/* 281 */                 MappingNode mnode = (MappingNode)valueNode;
/* 282 */                 mnode.setKeyType(ketType);
/* 283 */                 mnode.setValueType(valueType);
/* 284 */                 mnode.setUseClassConstructor(Boolean.valueOf(true));
/*     */               } 
/*     */             }
/*     */           } 
/* 288 */           Object value = Constructor.this.constructObject(valueNode);
/* 289 */           property.set(object, value);
/* 290 */         } catch (Exception e) {
/* 291 */           throw new YAMLException("Cannot create property=" + key + " for JavaBean=" + object + "; " + e.getMessage(), e);
/*     */         } 
/*     */       } 
/*     */       
/* 295 */       return object;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 300 */     private Property getProperty(Class<? extends Object> type, String name) throws IntrospectionException { return Constructor.this.getPropertyUtils().getProperty(type, name); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ConstructYamlObject
/*     */     implements Construct
/*     */   {
/*     */     private ConstructYamlObject() {}
/*     */ 
/*     */ 
/*     */     
/*     */     private Construct getConstructor(Node node) {
/* 314 */       Class cl = Constructor.this.getClassForNode(node);
/* 315 */       node.setType(cl);
/*     */       
/* 317 */       return (Construct)Constructor.this.yamlClassConstructors.get(node.getNodeId());
/*     */     }
/*     */ 
/*     */     
/*     */     public Object construct(Node node) {
/* 322 */       Object result = null;
/*     */       try {
/* 324 */         result = getConstructor(node).construct(node);
/* 325 */       } catch (Exception e) {
/* 326 */         throw new ConstructorException(null, null, "Can't construct a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */       } 
/*     */       
/* 329 */       return result;
/*     */     }
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/*     */       try {
/* 334 */         getConstructor(node).construct2ndStep(node, object);
/* 335 */       } catch (Exception e) {
/* 336 */         throw new ConstructorException(null, null, "Can't construct a second step for a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class ConstructScalar
/*     */     extends AbstractConstruct
/*     */   {
/*     */     public Object construct(Node nnode) {
/*     */       Object result;
/* 350 */       ScalarNode node = (ScalarNode)nnode;
/* 351 */       Class type = node.getType();
/*     */       
/* 353 */       if (type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type) || type == Boolean.class || Date.class.isAssignableFrom(type) || type == Character.class || type == BigInteger.class || type == BigDecimal.class || Enum.class.isAssignableFrom(type) || Tag.BINARY.equals(node.getTag()) || java.util.Calendar.class.isAssignableFrom(type)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 359 */         result = constructStandardJavaInstance(type, node);
/*     */       } else {
/*     */         Object argument;
/* 362 */         Constructor[] javaConstructors = type.getConstructors();
/* 363 */         int oneArgCount = 0;
/* 364 */         Constructor javaConstructor = null;
/* 365 */         for (Constructor c : javaConstructors) {
/* 366 */           if (c.getParameterTypes().length == 1) {
/* 367 */             oneArgCount++;
/* 368 */             javaConstructor = c;
/*     */           } 
/*     */         } 
/*     */         
/* 372 */         if (javaConstructor == null)
/* 373 */           throw new YAMLException("No single argument constructor found for " + type); 
/* 374 */         if (oneArgCount == 1) {
/* 375 */           argument = constructStandardJavaInstance(javaConstructor.getParameterTypes()[0], node);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 384 */           argument = Constructor.this.constructScalar(node);
/*     */           try {
/* 386 */             javaConstructor = type.getConstructor(new Class[] { String.class });
/* 387 */           } catch (Exception e) {
/* 388 */             throw new ConstructorException(null, null, "Can't construct a java object for scalar " + node.getTag() + "; No String constructor found. Exception=" + e.getMessage(), node.getStartMark(), e);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 395 */           result = javaConstructor.newInstance(new Object[] { argument });
/* 396 */         } catch (Exception e) {
/* 397 */           throw new ConstructorException(null, null, "Can't construct a java object for scalar " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 402 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     private Object constructStandardJavaInstance(Class type, ScalarNode node) {
/*     */       Object result;
/* 408 */       if (type == String.class) {
/* 409 */         Construct stringConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.STR);
/* 410 */         result = stringConstructor.construct(node);
/* 411 */       } else if (type == Boolean.class || type == boolean.class) {
/* 412 */         Construct boolConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.BOOL);
/* 413 */         result = boolConstructor.construct(node);
/* 414 */       } else if (type == Character.class || type == char.class) {
/* 415 */         Construct charConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.STR);
/* 416 */         String ch = (String)charConstructor.construct(node);
/* 417 */         if (ch.length() == 0)
/* 418 */         { result = null; }
/* 419 */         else { if (ch.length() != 1) {
/* 420 */             throw new YAMLException("Invalid node Character: '" + ch + "'; length: " + ch.length());
/*     */           }
/*     */           
/* 423 */           result = new Character(ch.charAt(0)); }
/*     */       
/* 425 */       } else if (Date.class.isAssignableFrom(type)) {
/* 426 */         Construct dateConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.TIMESTAMP);
/* 427 */         Date date = (Date)dateConstructor.construct(node);
/* 428 */         if (type == Date.class) {
/* 429 */           result = date;
/*     */         } else {
/*     */           try {
/* 432 */             Constructor<?> constr = type.getConstructor(new Class[] { long.class });
/* 433 */             result = constr.newInstance(new Object[] { Long.valueOf(date.getTime()) });
/* 434 */           } catch (Exception e) {
/* 435 */             throw new YAMLException("Cannot construct: '" + type + "'");
/*     */           } 
/*     */         } 
/* 438 */       } else if (type == Float.class || type == Double.class || type == float.class || type == double.class || type == BigDecimal.class) {
/*     */         
/* 440 */         if (type == BigDecimal.class) {
/* 441 */           result = new BigDecimal(node.getValue());
/*     */         } else {
/* 443 */           Construct doubleConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.FLOAT);
/* 444 */           result = doubleConstructor.construct(node);
/* 445 */           if (type == Float.class || type == float.class) {
/* 446 */             result = new Float(((Double)result).doubleValue());
/*     */           }
/*     */         } 
/* 449 */       } else if (type == Byte.class || type == Short.class || type == Integer.class || type == Long.class || type == BigInteger.class || type == byte.class || type == short.class || type == int.class || type == long.class) {
/*     */ 
/*     */         
/* 452 */         Construct intConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.INT);
/* 453 */         result = intConstructor.construct(node);
/* 454 */         if (type == Byte.class || type == byte.class) {
/* 455 */           result = new Byte(result.toString());
/* 456 */         } else if (type == Short.class || type == short.class) {
/* 457 */           result = new Short(result.toString());
/* 458 */         } else if (type == Integer.class || type == int.class) {
/* 459 */           result = new Integer(result.toString());
/* 460 */         } else if (type == Long.class || type == long.class) {
/* 461 */           result = new Long(result.toString());
/*     */         } else {
/*     */           
/* 464 */           result = new BigInteger(result.toString());
/*     */         } 
/* 466 */       } else if (Enum.class.isAssignableFrom(type)) {
/* 467 */         String enumValueName = node.getValue();
/*     */         try {
/* 469 */           result = Enum.valueOf(type, enumValueName);
/* 470 */         } catch (Exception ex) {
/* 471 */           throw new YAMLException("Unable to find enum value '" + enumValueName + "' for enum class: " + type.getName());
/*     */         }
/*     */       
/* 474 */       } else if (java.util.Calendar.class.isAssignableFrom(type)) {
/* 475 */         SafeConstructor.ConstructYamlTimestamp contr = new SafeConstructor.ConstructYamlTimestamp(Constructor.this);
/* 476 */         contr.construct(node);
/* 477 */         result = contr.getCalendar();
/*     */       } else {
/* 479 */         throw new YAMLException("Unsupported class: " + type);
/*     */       } 
/* 481 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ConstructSequence
/*     */     implements Construct
/*     */   {
/*     */     private ConstructSequence() {}
/*     */     
/*     */     public Object construct(Node node) {
/* 492 */       SequenceNode snode = (SequenceNode)node;
/* 493 */       if (Set.class.isAssignableFrom(node.getType())) {
/* 494 */         if (node.isTwoStepsConstruction()) {
/* 495 */           throw new YAMLException("Set cannot be recursive.");
/*     */         }
/* 497 */         return Constructor.this.constructSet(snode);
/*     */       } 
/* 499 */       if (java.util.Collection.class.isAssignableFrom(node.getType())) {
/* 500 */         if (node.isTwoStepsConstruction()) {
/* 501 */           return Constructor.this.createDefaultList(snode.getValue().size());
/*     */         }
/* 503 */         return Constructor.this.constructSequence(snode);
/*     */       } 
/* 505 */       if (node.getType().isArray()) {
/* 506 */         if (node.isTwoStepsConstruction()) {
/* 507 */           return Constructor.this.createArray(node.getType(), snode.getValue().size());
/*     */         }
/* 509 */         return Constructor.this.constructArray(snode);
/*     */       } 
/*     */ 
/*     */       
/* 513 */       List<Constructor> possibleConstructors = new ArrayList<Constructor>(snode.getValue().size());
/*     */       
/* 515 */       for (Constructor constructor : node.getType().getConstructors()) {
/* 516 */         if (snode.getValue().size() == constructor.getParameterTypes().length) {
/* 517 */           possibleConstructors.add(constructor);
/*     */         }
/*     */       } 
/* 520 */       if (!possibleConstructors.isEmpty()) {
/* 521 */         if (possibleConstructors.size() == 1) {
/* 522 */           Object[] argumentList = new Object[snode.getValue().size()];
/* 523 */           Constructor c = (Constructor)possibleConstructors.get(0);
/* 524 */           int index = 0;
/* 525 */           for (Node argumentNode : snode.getValue()) {
/* 526 */             Class type = c.getParameterTypes()[index];
/*     */             
/* 528 */             argumentNode.setType(type);
/* 529 */             argumentList[index++] = Constructor.this.constructObject(argumentNode);
/*     */           } 
/*     */           
/*     */           try {
/* 533 */             return c.newInstance(argumentList);
/* 534 */           } catch (Exception e) {
/* 535 */             throw new YAMLException(e);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 540 */         List<Object> argumentList = Constructor.this.constructSequence(snode);
/* 541 */         Class[] parameterTypes = new Class[argumentList.size()];
/* 542 */         int index = 0;
/* 543 */         for (Object parameter : argumentList) {
/* 544 */           parameterTypes[index] = parameter.getClass();
/* 545 */           index++;
/*     */         } 
/*     */         
/* 548 */         for (Constructor c : possibleConstructors) {
/* 549 */           Class[] argTypes = c.getParameterTypes();
/* 550 */           boolean foundConstructor = true;
/* 551 */           for (i = 0; i < argTypes.length; i++) {
/* 552 */             if (!wrapIfPrimitive(argTypes[i]).isAssignableFrom(parameterTypes[i])) {
/* 553 */               foundConstructor = false;
/*     */               break;
/*     */             } 
/*     */           } 
/* 557 */           if (foundConstructor) {
/*     */             try {
/* 559 */               return c.newInstance(argumentList.toArray());
/* 560 */             } catch (Exception i) {
/* 561 */               Exception e; throw new YAMLException(e);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 566 */       throw new YAMLException("No suitable constructor with " + String.valueOf(snode.getValue().size()) + " arguments found for " + node.getType());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class<? extends Object> wrapIfPrimitive(Class<?> clazz) {
/* 574 */       if (!clazz.isPrimitive()) {
/* 575 */         return clazz;
/*     */       }
/* 577 */       if (clazz == int.class) {
/* 578 */         return Integer.class;
/*     */       }
/* 580 */       if (clazz == float.class) {
/* 581 */         return Float.class;
/*     */       }
/* 583 */       if (clazz == double.class) {
/* 584 */         return Double.class;
/*     */       }
/* 586 */       if (clazz == boolean.class) {
/* 587 */         return Boolean.class;
/*     */       }
/* 589 */       if (clazz == long.class) {
/* 590 */         return Long.class;
/*     */       }
/* 592 */       if (clazz == char.class) {
/* 593 */         return Character.class;
/*     */       }
/* 595 */       if (clazz == short.class) {
/* 596 */         return Short.class;
/*     */       }
/* 598 */       if (clazz == byte.class) {
/* 599 */         return Byte.class;
/*     */       }
/* 601 */       throw new YAMLException("Unexpected primitive " + clazz);
/*     */     }
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 606 */       SequenceNode snode = (SequenceNode)node;
/* 607 */       if (List.class.isAssignableFrom(node.getType())) {
/* 608 */         List<Object> list = (List)object;
/* 609 */         Constructor.this.constructSequenceStep2(snode, list);
/* 610 */       } else if (node.getType().isArray()) {
/* 611 */         Constructor.this.constructArrayStep2(snode, object);
/*     */       } else {
/* 613 */         throw new YAMLException("Immutable objects cannot be recursive.");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected Class<?> getClassForNode(Node node) {
/* 619 */     Class<? extends Object> classForTag = (Class)this.typeTags.get(node.getTag());
/* 620 */     if (classForTag == null) {
/* 621 */       Class<?> cl; String name = node.getTag().getClassName();
/*     */       
/*     */       try {
/* 624 */         cl = getClassForName(name);
/* 625 */       } catch (ClassNotFoundException e) {
/* 626 */         throw new YAMLException("Class not found: " + name);
/*     */       } 
/* 628 */       this.typeTags.put(node.getTag(), cl);
/* 629 */       return cl;
/*     */     } 
/* 631 */     return classForTag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 636 */   protected Class<?> getClassForName(String name) throws ClassNotFoundException { return Class.forName(name); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\constructor\Constructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */