/*     */ package org.yaml.snakeyaml.constructor;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.composer.Composer;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.PropertyUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseConstructor
/*     */ {
/*  51 */   protected final Map<NodeId, Construct> yamlClassConstructors = new EnumMap(NodeId.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   protected final Map<Tag, Construct> yamlConstructors = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   protected final Map<String, Construct> yamlMultiConstructors = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Composer composer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private final Map<Node, Object> constructedObjects = new HashMap();
/*  79 */   private final Set<Node> recursiveObjects = new HashSet();
/*  80 */   private final ArrayList<RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>>> maps2fill = new ArrayList();
/*  81 */   private final ArrayList<RecursiveTuple<Set<Object>, Object>> sets2fill = new ArrayList();
/*  82 */   protected Tag rootTag = null;
/*     */   
/*     */   private PropertyUtils propertyUtils;
/*     */   private boolean explicitPropertyUtils = false;
/*     */   
/*  87 */   public void setComposer(Composer composer) { this.composer = composer; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public boolean checkData() { return this.composer.checkNode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getData() {
/* 107 */     this.composer.checkNode();
/* 108 */     Node node = this.composer.getNode();
/* 109 */     if (this.rootTag != null) {
/* 110 */       node.setTag(this.rootTag);
/*     */     }
/* 112 */     return constructDocument(node);
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
/*     */   public Object getSingleData() {
/* 124 */     Node node = this.composer.getSingleNode();
/* 125 */     if (node != null) {
/* 126 */       if (this.rootTag != null) {
/* 127 */         node.setTag(this.rootTag);
/*     */       }
/* 129 */       return constructDocument(node);
/*     */     } 
/* 131 */     return null;
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
/*     */   private Object constructDocument(Node node) {
/* 143 */     Object data = constructObject(node);
/* 144 */     fillRecursive();
/* 145 */     this.constructedObjects.clear();
/* 146 */     this.recursiveObjects.clear();
/* 147 */     return data;
/*     */   }
/*     */   
/*     */   private void fillRecursive() {
/* 151 */     if (!this.maps2fill.isEmpty()) {
/* 152 */       for (RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>> entry : this.maps2fill) {
/* 153 */         RecursiveTuple<Object, Object> key_value = (RecursiveTuple)entry._2();
/* 154 */         ((Map)entry._1()).put(key_value._1(), key_value._2());
/*     */       } 
/* 156 */       this.maps2fill.clear();
/*     */     } 
/* 158 */     if (!this.sets2fill.isEmpty()) {
/* 159 */       for (RecursiveTuple<Set<Object>, Object> value : this.sets2fill) {
/* 160 */         ((Set)value._1()).add(value._2());
/*     */       }
/* 162 */       this.sets2fill.clear();
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
/*     */   protected Object constructObject(Node node) {
/* 175 */     if (this.constructedObjects.containsKey(node)) {
/* 176 */       return this.constructedObjects.get(node);
/*     */     }
/* 178 */     if (this.recursiveObjects.contains(node)) {
/* 179 */       throw new ConstructorException(null, null, "found unconstructable recursive node", node.getStartMark());
/*     */     }
/*     */     
/* 182 */     this.recursiveObjects.add(node);
/* 183 */     Construct constructor = getConstructor(node);
/* 184 */     Object data = constructor.construct(node);
/* 185 */     this.constructedObjects.put(node, data);
/* 186 */     this.recursiveObjects.remove(node);
/* 187 */     if (node.isTwoStepsConstruction()) {
/* 188 */       constructor.construct2ndStep(node, data);
/*     */     }
/* 190 */     return data;
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
/*     */   protected Construct getConstructor(Node node) {
/* 203 */     if (node.useClassConstructor()) {
/* 204 */       return (Construct)this.yamlClassConstructors.get(node.getNodeId());
/*     */     }
/* 206 */     Construct constructor = (Construct)this.yamlConstructors.get(node.getTag());
/* 207 */     if (constructor == null) {
/* 208 */       for (String prefix : this.yamlMultiConstructors.keySet()) {
/* 209 */         if (node.getTag().startsWith(prefix)) {
/* 210 */           return (Construct)this.yamlMultiConstructors.get(prefix);
/*     */         }
/*     */       } 
/* 213 */       return (Construct)this.yamlConstructors.get(null);
/*     */     } 
/* 215 */     return constructor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 220 */   protected Object constructScalar(ScalarNode node) { return node.getValue(); }
/*     */ 
/*     */ 
/*     */   
/* 224 */   protected List<Object> createDefaultList(int initSize) { return new ArrayList(initSize); }
/*     */ 
/*     */ 
/*     */   
/* 228 */   protected Set<Object> createDefaultSet(int initSize) { return new LinkedHashSet(initSize); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 233 */   protected <T> T[] createArray(Class<T> type, int size) { return (T[])(Object[])Array.newInstance(type.getComponentType(), size); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<? extends Object> constructSequence(SequenceNode node) {
/*     */     List list;
/* 239 */     if (List.class.isAssignableFrom(node.getType()) && !node.getType().isInterface()) {
/*     */       
/*     */       try {
/* 242 */         list = (List)node.getType().newInstance();
/* 243 */       } catch (Exception e) {
/* 244 */         throw new YAMLException(e);
/*     */       } 
/*     */     } else {
/* 247 */       list = createDefaultList(node.getValue().size());
/*     */     } 
/* 249 */     constructSequenceStep2(node, list);
/* 250 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Set<? extends Object> constructSet(SequenceNode node) {
/*     */     Set set;
/* 257 */     if (!node.getType().isInterface()) {
/*     */       
/*     */       try {
/* 260 */         set = (Set)node.getType().newInstance();
/* 261 */       } catch (Exception e) {
/* 262 */         throw new YAMLException(e);
/*     */       } 
/*     */     } else {
/* 265 */       set = createDefaultSet(node.getValue().size());
/*     */     } 
/* 267 */     constructSequenceStep2(node, set);
/* 268 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 273 */   protected Object constructArray(SequenceNode node) { return constructArrayStep2(node, createArray(node.getType(), node.getValue().size())); }
/*     */ 
/*     */   
/*     */   protected void constructSequenceStep2(SequenceNode node, Collection<Object> collection) {
/* 277 */     for (Node child : node.getValue()) {
/* 278 */       collection.add(constructObject(child));
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object constructArrayStep2(SequenceNode node, Object array) {
/* 283 */     int index = 0;
/* 284 */     for (Node child : node.getValue()) {
/* 285 */       Array.set(array, index++, constructObject(child));
/*     */     }
/* 287 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 292 */   protected Map<Object, Object> createDefaultMap() { return new LinkedHashMap(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   protected Set<Object> createDefaultSet() { return new LinkedHashSet(); }
/*     */ 
/*     */   
/*     */   protected Set<Object> constructSet(MappingNode node) {
/* 301 */     Set<Object> set = createDefaultSet();
/* 302 */     constructSet2ndStep(node, set);
/* 303 */     return set;
/*     */   }
/*     */   
/*     */   protected Map<Object, Object> constructMapping(MappingNode node) {
/* 307 */     Map<Object, Object> mapping = createDefaultMap();
/* 308 */     constructMapping2ndStep(node, mapping);
/* 309 */     return mapping;
/*     */   }
/*     */   
/*     */   protected void constructMapping2ndStep(MappingNode node, Map<Object, Object> mapping) {
/* 313 */     List<NodeTuple> nodeValue = node.getValue();
/* 314 */     for (NodeTuple tuple : nodeValue) {
/* 315 */       Node keyNode = tuple.getKeyNode();
/* 316 */       Node valueNode = tuple.getValueNode();
/* 317 */       Object key = constructObject(keyNode);
/* 318 */       if (key != null) {
/*     */         try {
/* 320 */           key.hashCode();
/* 321 */         } catch (Exception e) {
/* 322 */           throw new ConstructorException("while constructing a mapping", node.getStartMark(), "found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 327 */       Object value = constructObject(valueNode);
/* 328 */       if (keyNode.isTwoStepsConstruction()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 335 */         this.maps2fill.add(0, new RecursiveTuple(mapping, new RecursiveTuple(key, value)));
/*     */         
/*     */         continue;
/*     */       } 
/* 339 */       mapping.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void constructSet2ndStep(MappingNode node, Set<Object> set) {
/* 345 */     List<NodeTuple> nodeValue = node.getValue();
/* 346 */     for (NodeTuple tuple : nodeValue) {
/* 347 */       Node keyNode = tuple.getKeyNode();
/* 348 */       Object key = constructObject(keyNode);
/* 349 */       if (key != null) {
/*     */         try {
/* 351 */           key.hashCode();
/* 352 */         } catch (Exception e) {
/* 353 */           throw new ConstructorException("while constructing a Set", node.getStartMark(), "found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
/*     */         } 
/*     */       }
/*     */       
/* 357 */       if (keyNode.isTwoStepsConstruction()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 364 */         this.sets2fill.add(0, new RecursiveTuple(set, key)); continue;
/*     */       } 
/* 366 */       set.add(key);
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
/*     */ 
/*     */   
/*     */   public void setPropertyUtils(PropertyUtils propertyUtils) {
/* 384 */     this.propertyUtils = propertyUtils;
/* 385 */     this.explicitPropertyUtils = true;
/*     */   }
/*     */   
/*     */   public final PropertyUtils getPropertyUtils() {
/* 389 */     if (this.propertyUtils == null) {
/* 390 */       this.propertyUtils = new PropertyUtils();
/*     */     }
/* 392 */     return this.propertyUtils;
/*     */   }
/*     */   
/*     */   private static class RecursiveTuple<T, K> extends Object {
/*     */     private final T _1;
/*     */     private final K _2;
/*     */     
/*     */     public RecursiveTuple(T _1, K _2) {
/* 400 */       this._1 = _1;
/* 401 */       this._2 = _2;
/*     */     }
/*     */ 
/*     */     
/* 405 */     public K _2() { return (K)this._2; }
/*     */ 
/*     */ 
/*     */     
/* 409 */     public T _1() { return (T)this._1; }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 414 */   public final boolean isExplicitPropertyUtils() { return this.explicitPropertyUtils; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\constructor\BaseConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */