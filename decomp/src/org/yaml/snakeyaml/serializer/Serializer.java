/*     */ package org.yaml.snakeyaml.serializer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.emitter.Emitter;
/*     */ import org.yaml.snakeyaml.events.AliasEvent;
/*     */ import org.yaml.snakeyaml.events.DocumentEndEvent;
/*     */ import org.yaml.snakeyaml.events.DocumentStartEvent;
/*     */ import org.yaml.snakeyaml.events.ImplicitTuple;
/*     */ import org.yaml.snakeyaml.events.MappingEndEvent;
/*     */ import org.yaml.snakeyaml.events.MappingStartEvent;
/*     */ import org.yaml.snakeyaml.events.ScalarEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceEndEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceStartEvent;
/*     */ import org.yaml.snakeyaml.events.StreamEndEvent;
/*     */ import org.yaml.snakeyaml.events.StreamStartEvent;
/*     */ import org.yaml.snakeyaml.nodes.CollectionNode;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
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
/*     */ public final class Serializer
/*     */ {
/*     */   private final Emitter emitter;
/*     */   private final Resolver resolver;
/*     */   private boolean explicitStart;
/*     */   private boolean explicitEnd;
/*     */   private Integer[] useVersion;
/*     */   private Map<String, String> useTags;
/*     */   private Set<Node> serializedNodes;
/*     */   private Map<Node, String> anchors;
/*     */   private int lastAnchorId;
/*     */   private Boolean closed;
/*     */   private Tag explicitRoot;
/*     */   
/*     */   public Serializer(Emitter emitter, Resolver resolver, DumperOptions opts) {
/*  67 */     this.emitter = emitter;
/*  68 */     this.resolver = resolver;
/*  69 */     this.explicitStart = opts.isExplicitStart();
/*  70 */     this.explicitEnd = opts.isExplicitEnd();
/*  71 */     if (opts.getVersion() != null) {
/*  72 */       this.useVersion = opts.getVersion().getArray();
/*     */     }
/*  74 */     this.useTags = opts.getTags();
/*  75 */     this.serializedNodes = new HashSet();
/*  76 */     this.anchors = new HashMap();
/*  77 */     this.lastAnchorId = 0;
/*  78 */     this.closed = null;
/*  79 */     this.explicitRoot = opts.getExplicitRoot();
/*     */   }
/*     */   
/*     */   public void open() throws IOException {
/*  83 */     if (this.closed == null)
/*  84 */     { this.emitter.emit(new StreamStartEvent(null, null));
/*  85 */       this.closed = Boolean.FALSE; }
/*  86 */     else { if (Boolean.TRUE.equals(this.closed)) {
/*  87 */         throw new SerializerException("serializer is closed");
/*     */       }
/*  89 */       throw new SerializerException("serializer is already opened"); }
/*     */   
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*  94 */     if (this.closed == null)
/*  95 */       throw new SerializerException("serializer is not opened"); 
/*  96 */     if (!Boolean.TRUE.equals(this.closed)) {
/*  97 */       this.emitter.emit(new StreamEndEvent(null, null));
/*  98 */       this.closed = Boolean.TRUE;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serialize(Node node) throws IOException {
/* 103 */     if (this.closed == null)
/* 104 */       throw new SerializerException("serializer is not opened"); 
/* 105 */     if (this.closed.booleanValue()) {
/* 106 */       throw new SerializerException("serializer is closed");
/*     */     }
/* 108 */     this.emitter.emit(new DocumentStartEvent(null, null, this.explicitStart, this.useVersion, this.useTags));
/*     */     
/* 110 */     anchorNode(node);
/* 111 */     if (this.explicitRoot != null) {
/* 112 */       node.setTag(this.explicitRoot);
/*     */     }
/* 114 */     serializeNode(node, null, null);
/* 115 */     this.emitter.emit(new DocumentEndEvent(null, null, this.explicitEnd));
/* 116 */     this.serializedNodes.clear();
/* 117 */     this.anchors.clear();
/* 118 */     this.lastAnchorId = 0;
/*     */   }
/*     */   
/*     */   private void anchorNode(Node node) throws IOException {
/* 122 */     if (this.anchors.containsKey(node)) {
/* 123 */       String anchor = (String)this.anchors.get(node);
/* 124 */       if (null == anchor) {
/* 125 */         anchor = generateAnchor();
/* 126 */         this.anchors.put(node, anchor);
/*     */       } 
/*     */     } else {
/* 129 */       List<NodeTuple> map; MappingNode mnode; List<Node> list; SequenceNode seqNode; this.anchors.put(node, null);
/* 130 */       switch (node.getNodeId()) {
/*     */         case sequence:
/* 132 */           seqNode = (SequenceNode)node;
/* 133 */           list = seqNode.getValue();
/* 134 */           for (Node item : list) {
/* 135 */             anchorNode(item);
/*     */           }
/*     */           break;
/*     */         case mapping:
/* 139 */           mnode = (MappingNode)node;
/* 140 */           map = mnode.getValue();
/* 141 */           for (NodeTuple object : map) {
/* 142 */             Node key = object.getKeyNode();
/* 143 */             Node value = object.getValueNode();
/* 144 */             anchorNode(key);
/* 145 */             anchorNode(value);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String generateAnchor() {
/* 153 */     this.lastAnchorId++;
/* 154 */     NumberFormat format = NumberFormat.getNumberInstance();
/* 155 */     format.setMinimumIntegerDigits(3);
/* 156 */     format.setGroupingUsed(false);
/* 157 */     String anchorId = format.format(this.lastAnchorId);
/* 158 */     return "id" + anchorId;
/*     */   }
/*     */   
/*     */   private void serializeNode(Node node, Node parent, Object index) throws IOException {
/* 162 */     String tAlias = (String)this.anchors.get(node);
/* 163 */     if (this.serializedNodes.contains(node)) {
/* 164 */       this.emitter.emit(new AliasEvent(tAlias, null, null));
/*     */     } else {
/* 166 */       List<Node> list; int indexCounter; boolean implicitS; SequenceNode seqNode; ScalarEvent event; ImplicitTuple tuple; Tag defaultTag, detectedTag; ScalarNode scalarNode; this.serializedNodes.add(node);
/*     */       
/* 168 */       switch (node.getNodeId()) {
/*     */         case scalar:
/* 170 */           scalarNode = (ScalarNode)node;
/* 171 */           detectedTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), true);
/* 172 */           defaultTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), false);
/* 173 */           tuple = new ImplicitTuple(node.getTag().equals(detectedTag), node.getTag().equals(defaultTag));
/*     */           
/* 175 */           event = new ScalarEvent(tAlias, node.getTag().getValue(), tuple, scalarNode.getValue(), null, null, scalarNode.getStyle());
/*     */           
/* 177 */           this.emitter.emit(event);
/*     */           return;
/*     */         case sequence:
/* 180 */           seqNode = (SequenceNode)node;
/* 181 */           implicitS = node.getTag().equals(this.resolver.resolve(NodeId.sequence, null, true));
/*     */           
/* 183 */           this.emitter.emit(new SequenceStartEvent(tAlias, node.getTag().getValue(), implicitS, null, null, seqNode.getFlowStyle()));
/*     */           
/* 185 */           indexCounter = 0;
/* 186 */           list = seqNode.getValue();
/* 187 */           for (Node item : list) {
/* 188 */             serializeNode(item, node, Integer.valueOf(indexCounter));
/* 189 */             indexCounter++;
/*     */           } 
/* 191 */           this.emitter.emit(new SequenceEndEvent(null, null));
/*     */           return;
/*     */       } 
/* 194 */       Tag implicitTag = this.resolver.resolve(NodeId.mapping, null, true);
/* 195 */       boolean implicitM = node.getTag().equals(implicitTag);
/* 196 */       this.emitter.emit(new MappingStartEvent(tAlias, node.getTag().getValue(), implicitM, null, null, ((CollectionNode)node).getFlowStyle()));
/*     */       
/* 198 */       MappingNode mnode = (MappingNode)node;
/* 199 */       List<NodeTuple> map = mnode.getValue();
/* 200 */       for (NodeTuple row : map) {
/* 201 */         Node key = row.getKeyNode();
/* 202 */         Node value = row.getValueNode();
/* 203 */         serializeNode(key, mnode, null);
/* 204 */         serializeNode(value, mnode, key);
/*     */       } 
/* 206 */       this.emitter.emit(new MappingEndEvent(null, null));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\serializer\Serializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */