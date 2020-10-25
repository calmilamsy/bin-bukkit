/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.PropertyUtils;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.serializer.Serializer;
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
/*     */ public abstract class BaseRepresenter
/*     */ {
/*  48 */   protected final Map<Class, Represent> representers = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Represent nullRepresenter;
/*     */ 
/*     */ 
/*     */   
/*  57 */   protected final Map<Class, Represent> multiRepresenters = new LinkedHashMap();
/*     */   private Character defaultStyle;
/*     */   protected Boolean defaultFlowStyle;
/*  60 */   protected final Map<Object, Node> representedObjects = new IdentityHashMap();
/*  61 */   private final Set<Object> objectKeeper = new HashSet();
/*     */   protected Object objectToRepresent;
/*     */   private PropertyUtils propertyUtils;
/*     */   private boolean explicitPropertyUtils = false;
/*     */   
/*     */   public void represent(Serializer serializer, Object data) throws IOException {
/*  67 */     Node node = representData(data);
/*  68 */     serializer.serialize(node);
/*  69 */     this.representedObjects.clear();
/*  70 */     this.objectKeeper.clear();
/*  71 */     this.objectToRepresent = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Node representData(Object data) {
/*  76 */     this.objectToRepresent = data;
/*  77 */     if (!ignoreAliases(data))
/*     */     {
/*  79 */       if (this.representedObjects.containsKey(this.objectToRepresent)) {
/*  80 */         return (Node)this.representedObjects.get(this.objectToRepresent);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*  85 */     if (data == null) {
/*  86 */       return this.nullRepresenter.representData(data);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  91 */     Class clazz = data.getClass();
/*  92 */     if (this.representers.containsKey(clazz)) {
/*  93 */       Represent representer = (Represent)this.representers.get(clazz);
/*  94 */       node = representer.representData(data);
/*     */     } else {
/*     */       
/*  97 */       for (Class repr : this.multiRepresenters.keySet()) {
/*  98 */         if (repr.isInstance(data)) {
/*  99 */           Represent representer = (Represent)this.multiRepresenters.get(repr);
/* 100 */           return representer.representData(data);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 105 */       if (clazz.isArray()) {
/* 106 */         throw new YAMLException("Arrays of primitives are not fully supported.");
/*     */       }
/*     */       
/* 109 */       if (this.multiRepresenters.containsKey(null)) {
/* 110 */         Represent representer = (Represent)this.multiRepresenters.get(null);
/* 111 */         node = representer.representData(data);
/*     */       } else {
/* 113 */         Represent representer = (Represent)this.representers.get(null);
/* 114 */         node = representer.representData(data);
/*     */       } 
/*     */     } 
/* 117 */     return node;
/*     */   }
/*     */   
/*     */   protected Node representScalar(Tag tag, String value, Character style) {
/* 121 */     if (style == null) {
/* 122 */       style = this.defaultStyle;
/*     */     }
/* 124 */     ScalarNode scalarNode = new ScalarNode(tag, value, null, null, style);
/* 125 */     this.representedObjects.put(this.objectToRepresent, scalarNode);
/* 126 */     return scalarNode;
/*     */   }
/*     */ 
/*     */   
/* 130 */   protected Node representScalar(Tag tag, String value) { return representScalar(tag, value, null); }
/*     */ 
/*     */   
/*     */   protected Node representSequence(Tag tag, Iterable<? extends Object> sequence, Boolean flowStyle) {
/* 134 */     int size = 10;
/* 135 */     if (sequence instanceof List) {
/* 136 */       size = ((List)sequence).size();
/*     */     }
/* 138 */     List<Node> value = new ArrayList<Node>(size);
/* 139 */     SequenceNode node = new SequenceNode(tag, value, flowStyle);
/* 140 */     this.representedObjects.put(this.objectToRepresent, node);
/* 141 */     boolean bestStyle = true;
/* 142 */     for (Object item : sequence) {
/* 143 */       Node nodeItem = representData(item);
/* 144 */       if (!(nodeItem instanceof ScalarNode) || ((ScalarNode)nodeItem).getStyle() != null) {
/* 145 */         bestStyle = false;
/*     */       }
/* 147 */       value.add(nodeItem);
/*     */     } 
/* 149 */     if (flowStyle == null) {
/* 150 */       if (this.defaultFlowStyle != null) {
/* 151 */         node.setFlowStyle(this.defaultFlowStyle);
/*     */       } else {
/* 153 */         node.setFlowStyle(Boolean.valueOf(bestStyle));
/*     */       } 
/*     */     }
/* 156 */     return node;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Node representMapping(Tag tag, Map<? extends Object, Object> mapping, Boolean flowStyle) {
/* 161 */     List<NodeTuple> value = new ArrayList<NodeTuple>(mapping.size());
/* 162 */     MappingNode node = new MappingNode(tag, value, flowStyle);
/* 163 */     this.representedObjects.put(this.objectToRepresent, node);
/* 164 */     boolean bestStyle = true;
/* 165 */     for (Object itemKey : mapping.keySet()) {
/* 166 */       Object itemValue = mapping.get(itemKey);
/* 167 */       Node nodeKey = representData(itemKey);
/* 168 */       Node nodeValue = representData(itemValue);
/* 169 */       if (!(nodeKey instanceof ScalarNode) || ((ScalarNode)nodeKey).getStyle() != null) {
/* 170 */         bestStyle = false;
/*     */       }
/* 172 */       if (!(nodeValue instanceof ScalarNode) || ((ScalarNode)nodeValue).getStyle() != null) {
/* 173 */         bestStyle = false;
/*     */       }
/* 175 */       value.add(new NodeTuple(nodeKey, nodeValue));
/*     */     } 
/* 177 */     if (flowStyle == null) {
/* 178 */       if (this.defaultFlowStyle != null) {
/* 179 */         node.setFlowStyle(this.defaultFlowStyle);
/*     */       } else {
/* 181 */         node.setFlowStyle(Boolean.valueOf(bestStyle));
/*     */       } 
/*     */     }
/* 184 */     return node;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean ignoreAliases(Object paramObject);
/*     */   
/* 190 */   public void setDefaultScalarStyle(DumperOptions.ScalarStyle defaultStyle) { this.defaultStyle = defaultStyle.getChar(); }
/*     */ 
/*     */ 
/*     */   
/* 194 */   public void setDefaultFlowStyle(DumperOptions.FlowStyle defaultFlowStyle) { this.defaultFlowStyle = defaultFlowStyle.getStyleBoolean(); }
/*     */ 
/*     */   
/*     */   public void setPropertyUtils(PropertyUtils propertyUtils) {
/* 198 */     this.propertyUtils = propertyUtils;
/* 199 */     this.explicitPropertyUtils = true;
/*     */   }
/*     */   
/*     */   public final PropertyUtils getPropertyUtils() {
/* 203 */     if (this.propertyUtils == null) {
/* 204 */       this.propertyUtils = new PropertyUtils();
/*     */     }
/* 206 */     return this.propertyUtils;
/*     */   }
/*     */ 
/*     */   
/* 210 */   public final boolean isExplicitPropertyUtils() { return this.explicitPropertyUtils; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\representer\BaseRepresenter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */