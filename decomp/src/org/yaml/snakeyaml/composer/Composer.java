/*     */ package org.yaml.snakeyaml.composer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.events.AliasEvent;
/*     */ import org.yaml.snakeyaml.events.Event;
/*     */ import org.yaml.snakeyaml.events.MappingStartEvent;
/*     */ import org.yaml.snakeyaml.events.NodeEvent;
/*     */ import org.yaml.snakeyaml.events.ScalarEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceStartEvent;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.parser.Parser;
/*     */ import org.yaml.snakeyaml.resolver.Resolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Composer
/*     */ {
/*     */   private final Parser parser;
/*     */   private final Resolver resolver;
/*     */   private final Map<String, Node> anchors;
/*     */   private final Set<Node> recursiveNodes;
/*     */   
/*     */   public Composer(Parser parser, Resolver resolver) {
/*  55 */     this.parser = parser;
/*  56 */     this.resolver = resolver;
/*  57 */     this.anchors = new HashMap();
/*  58 */     this.recursiveNodes = new HashSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkNode() {
/*  68 */     if (this.parser.checkEvent(Event.ID.StreamStart)) {
/*  69 */       this.parser.getEvent();
/*     */     }
/*     */     
/*  72 */     return !this.parser.checkEvent(Event.ID.StreamEnd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getNode() {
/*  83 */     if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
/*  84 */       return composeDocument();
/*     */     }
/*  86 */     return (Node)null;
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
/*     */   public Node getSingleNode() {
/* 101 */     this.parser.getEvent();
/*     */     
/* 103 */     Node document = null;
/* 104 */     if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
/* 105 */       document = composeDocument();
/*     */     }
/*     */     
/* 108 */     if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
/* 109 */       Event event = this.parser.getEvent();
/* 110 */       throw new ComposerException("expected a single document in the stream", document.getStartMark(), "but found another document", event.getStartMark());
/*     */     } 
/*     */ 
/*     */     
/* 114 */     this.parser.getEvent();
/* 115 */     return document;
/*     */   }
/*     */ 
/*     */   
/*     */   private Node composeDocument() {
/* 120 */     this.parser.getEvent();
/*     */     
/* 122 */     Node node = composeNode(null, null);
/*     */     
/* 124 */     this.parser.getEvent();
/* 125 */     this.anchors.clear();
/* 126 */     this.recursiveNodes.clear();
/* 127 */     return node;
/*     */   }
/*     */   
/*     */   private Node composeNode(Node parent, Object index) {
/* 131 */     this.recursiveNodes.add(parent);
/* 132 */     if (this.parser.checkEvent(Event.ID.Alias)) {
/* 133 */       AliasEvent event = (AliasEvent)this.parser.getEvent();
/* 134 */       String anchor = event.getAnchor();
/* 135 */       if (!this.anchors.containsKey(anchor)) {
/* 136 */         throw new ComposerException(null, null, "found undefined alias " + anchor, event.getStartMark());
/*     */       }
/*     */       
/* 139 */       Node result = (Node)this.anchors.get(anchor);
/* 140 */       if (this.recursiveNodes.remove(result)) {
/* 141 */         result.setTwoStepsConstruction(true);
/*     */       }
/* 143 */       return result;
/*     */     } 
/* 145 */     NodeEvent event = (NodeEvent)this.parser.peekEvent();
/* 146 */     String anchor = null;
/* 147 */     anchor = event.getAnchor();
/* 148 */     if (anchor != null && this.anchors.containsKey(anchor)) {
/* 149 */       throw new ComposerException("found duplicate anchor " + anchor + "; first occurence", ((Node)this.anchors.get(anchor)).getStartMark(), "second occurence", event.getStartMark());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 154 */     Node node = null;
/* 155 */     if (this.parser.checkEvent(Event.ID.Scalar)) {
/* 156 */       node = composeScalarNode(anchor);
/* 157 */     } else if (this.parser.checkEvent(Event.ID.SequenceStart)) {
/* 158 */       node = composeSequenceNode(anchor);
/*     */     } else {
/* 160 */       node = composeMappingNode(anchor);
/*     */     } 
/*     */     
/* 163 */     this.recursiveNodes.remove(parent);
/* 164 */     return node;
/*     */   }
/*     */   private Node composeScalarNode(String anchor) {
/*     */     Tag nodeTag;
/* 168 */     ScalarEvent ev = (ScalarEvent)this.parser.getEvent();
/* 169 */     String tag = ev.getTag();
/* 170 */     boolean resolved = false;
/*     */     
/* 172 */     if (tag == null || tag.equals("!")) {
/* 173 */       nodeTag = this.resolver.resolve(NodeId.scalar, ev.getValue(), ev.getImplicit().isFirst());
/* 174 */       resolved = true;
/*     */     } else {
/* 176 */       nodeTag = new Tag(tag);
/*     */     } 
/* 178 */     ScalarNode scalarNode = new ScalarNode(nodeTag, resolved, ev.getValue(), ev.getStartMark(), ev.getEndMark(), ev.getStyle());
/*     */     
/* 180 */     if (anchor != null) {
/* 181 */       this.anchors.put(anchor, scalarNode);
/*     */     }
/* 183 */     return scalarNode;
/*     */   }
/*     */   private Node composeSequenceNode(String anchor) {
/*     */     Tag nodeTag;
/* 187 */     SequenceStartEvent startEvent = (SequenceStartEvent)this.parser.getEvent();
/* 188 */     String tag = startEvent.getTag();
/*     */     
/* 190 */     boolean resolved = false;
/* 191 */     if (tag == null || tag.equals("!")) {
/* 192 */       nodeTag = this.resolver.resolve(NodeId.sequence, null, startEvent.getImplicit());
/* 193 */       resolved = true;
/*     */     } else {
/* 195 */       nodeTag = new Tag(tag);
/*     */     } 
/* 197 */     SequenceNode node = new SequenceNode(nodeTag, resolved, new ArrayList(), startEvent.getStartMark(), null, startEvent.getFlowStyle());
/*     */     
/* 199 */     if (anchor != null) {
/* 200 */       this.anchors.put(anchor, node);
/*     */     }
/* 202 */     int index = 0;
/* 203 */     while (!this.parser.checkEvent(Event.ID.SequenceEnd)) {
/* 204 */       node.getValue().add(composeNode(node, Integer.valueOf(index)));
/* 205 */       index++;
/*     */     } 
/* 207 */     Event endEvent = this.parser.getEvent();
/* 208 */     node.setEndMark(endEvent.getEndMark());
/* 209 */     return node;
/*     */   }
/*     */   private Node composeMappingNode(String anchor) {
/*     */     Tag nodeTag;
/* 213 */     MappingStartEvent startEvent = (MappingStartEvent)this.parser.getEvent();
/* 214 */     String tag = startEvent.getTag();
/*     */     
/* 216 */     boolean resolved = false;
/* 217 */     if (tag == null || tag.equals("!")) {
/* 218 */       nodeTag = this.resolver.resolve(NodeId.mapping, null, startEvent.getImplicit());
/* 219 */       resolved = true;
/*     */     } else {
/* 221 */       nodeTag = new Tag(tag);
/*     */     } 
/* 223 */     MappingNode node = new MappingNode(nodeTag, resolved, new ArrayList(), startEvent.getStartMark(), null, startEvent.getFlowStyle());
/*     */     
/* 225 */     if (anchor != null) {
/* 226 */       this.anchors.put(anchor, node);
/*     */     }
/* 228 */     while (!this.parser.checkEvent(Event.ID.MappingEnd)) {
/* 229 */       Node itemKey = composeNode(node, null);
/* 230 */       Node itemValue = composeNode(node, itemKey);
/* 231 */       node.getValue().add(new NodeTuple(itemKey, itemValue));
/*     */     } 
/* 233 */     Event endEvent = this.parser.getEvent();
/* 234 */     node.setEndMark(endEvent.getEndMark());
/* 235 */     return node;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\composer\Composer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */