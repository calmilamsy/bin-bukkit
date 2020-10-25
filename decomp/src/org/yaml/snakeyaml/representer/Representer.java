/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.beans.IntrospectionException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ public class Representer
/*     */   extends SafeRepresenter
/*     */ {
/*  43 */   public Representer() { this.representers.put(null, new RepresentJavaBean()); }
/*     */   
/*     */   protected class RepresentJavaBean
/*     */     implements Represent {
/*     */     public Node representData(Object data) {
/*     */       try {
/*  49 */         return Representer.this.representJavaBean(Representer.this.getProperties(data.getClass()), data);
/*  50 */       } catch (IntrospectionException e) {
/*  51 */         throw new YAMLException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
/*  71 */     List<NodeTuple> value = new ArrayList<NodeTuple>(properties.size());
/*     */     
/*  73 */     Tag customTag = (Tag)this.classTags.get(javaBean.getClass());
/*  74 */     Tag tag = (customTag != null) ? customTag : new Tag(javaBean.getClass());
/*     */     
/*  76 */     MappingNode node = new MappingNode(tag, value, null);
/*  77 */     this.representedObjects.put(this.objectToRepresent, node);
/*  78 */     boolean bestStyle = true;
/*  79 */     for (Property property : properties) {
/*  80 */       Object memberValue = property.get(javaBean);
/*  81 */       NodeTuple tuple = representJavaBeanProperty(javaBean, property, memberValue, customTag);
/*  82 */       if (tuple == null) {
/*     */         continue;
/*     */       }
/*  85 */       if (((ScalarNode)tuple.getKeyNode()).getStyle() != null) {
/*  86 */         bestStyle = false;
/*     */       }
/*  88 */       Node nodeValue = tuple.getValueNode();
/*  89 */       if (!(nodeValue instanceof ScalarNode) || ((ScalarNode)nodeValue).getStyle() != null) {
/*  90 */         bestStyle = false;
/*     */       }
/*  92 */       value.add(tuple);
/*     */     } 
/*  94 */     if (this.defaultFlowStyle != null) {
/*  95 */       node.setFlowStyle(this.defaultFlowStyle);
/*     */     } else {
/*  97 */       node.setFlowStyle(Boolean.valueOf(bestStyle));
/*     */     } 
/*  99 */     return node;
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
/*     */ 
/*     */   
/*     */   protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
/* 118 */     ScalarNode nodeKey = (ScalarNode)representData(property.getName());
/* 119 */     boolean hasAlias = false;
/* 120 */     if (this.representedObjects.containsKey(propertyValue))
/*     */     {
/* 122 */       hasAlias = true;
/*     */     }
/* 124 */     Node nodeValue = representData(propertyValue);
/*     */     
/* 126 */     if (nodeValue instanceof MappingNode && !hasAlias) {
/*     */       
/* 128 */       if (!java.util.Map.class.isAssignableFrom(propertyValue.getClass()))
/*     */       {
/* 130 */         if (customTag == null && !nodeValue.getTag().equals(Tag.SET))
/*     */         {
/* 132 */           if (property.getType() == propertyValue.getClass())
/*     */           {
/*     */             
/* 135 */             nodeValue.setTag(Tag.MAP);
/*     */           }
/*     */         }
/*     */       }
/* 139 */     } else if (propertyValue != null && Enum.class.isAssignableFrom(propertyValue.getClass())) {
/* 140 */       nodeValue.setTag(Tag.STR);
/*     */     } 
/* 142 */     if (nodeValue.getNodeId() != NodeId.scalar && !hasAlias)
/*     */     {
/* 144 */       checkGlobalTag(property, nodeValue, propertyValue);
/*     */     }
/* 146 */     return new NodeTuple(nodeKey, nodeValue);
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
/*     */   protected void checkGlobalTag(Property property, Node node, Object object) {
/* 162 */     Class[] arguments = property.getActualTypeArguments();
/* 163 */     if (arguments != null) {
/* 164 */       if (node.getNodeId() == NodeId.sequence) {
/*     */         Iterable<Object> memberList;
/* 166 */         Class<? extends Object> t = arguments[0];
/* 167 */         SequenceNode snode = (SequenceNode)node;
/*     */         
/* 169 */         if (object.getClass().isArray()) {
/* 170 */           memberList = Arrays.asList((Object[])object);
/*     */         } else {
/*     */           
/* 173 */           memberList = (Iterable)object;
/*     */         } 
/* 175 */         Iterator<Object> iter = memberList.iterator();
/* 176 */         for (Node childNode : snode.getValue()) {
/* 177 */           Object member = iter.next();
/* 178 */           if (member != null && t.equals(member.getClass()) && childNode.getNodeId() == NodeId.mapping)
/*     */           {
/* 180 */             childNode.setTag(Tag.MAP);
/*     */           }
/*     */         } 
/* 183 */       } else if (object instanceof Set) {
/* 184 */         Class t = arguments[0];
/* 185 */         MappingNode mnode = (MappingNode)node;
/* 186 */         Iterator<NodeTuple> iter = mnode.getValue().iterator();
/* 187 */         Set set = (Set)object;
/* 188 */         for (Object member : set) {
/* 189 */           NodeTuple tuple = (NodeTuple)iter.next();
/* 190 */           if (t.equals(member.getClass()) && tuple.getKeyNode().getNodeId() == NodeId.mapping)
/*     */           {
/* 192 */             tuple.getKeyNode().setTag(Tag.MAP);
/*     */           }
/*     */         } 
/* 195 */       } else if (node.getNodeId() == NodeId.mapping) {
/* 196 */         Class keyType = arguments[0];
/* 197 */         Class valueType = arguments[1];
/* 198 */         MappingNode mnode = (MappingNode)node;
/* 199 */         for (NodeTuple tuple : mnode.getValue()) {
/* 200 */           resetTag(keyType, tuple.getKeyNode());
/* 201 */           resetTag(valueType, tuple.getValueNode());
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void resetTag(Class<? extends Object> type, Node node) {
/* 208 */     Tag tag = node.getTag();
/* 209 */     if (tag.matches(type)) {
/* 210 */       if (Enum.class.isAssignableFrom(type)) {
/* 211 */         node.setTag(Tag.STR);
/*     */       } else {
/* 213 */         node.setTag(Tag.MAP);
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
/*     */   
/* 228 */   protected Set<Property> getProperties(Class<? extends Object> type) throws IntrospectionException { return getPropertyUtils().getProperties(type); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\representer\Representer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */