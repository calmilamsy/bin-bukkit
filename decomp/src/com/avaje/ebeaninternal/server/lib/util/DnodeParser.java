/*     */ package com.avaje.ebeaninternal.server.lib.util;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DnodeParser
/*     */   extends DefaultHandler
/*     */ {
/*     */   Dnode root;
/*     */   Dnode currentNode;
/*     */   StringBuffer buffer;
/*  51 */   Stack<Dnode> stack = new Stack();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   Class<?> nodeClass = Dnode.class;
/*     */   
/*  59 */   int depth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean trimWhitespace = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String contentName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int contentDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public boolean isTrimWhitespace() { return this.trimWhitespace; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void setTrimWhitespace(boolean trimWhitespace) { this.trimWhitespace = trimWhitespace; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public Dnode getRoot() { return this.root; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public void setNodeClass(Class<?> nodeClass) { this.nodeClass = nodeClass; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Dnode createNewNode() {
/*     */     try {
/* 110 */       return (Dnode)this.nodeClass.newInstance();
/* 111 */     } catch (Exception ex) {
/* 112 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 123 */     super.startElement(uri, localName, qName, attributes);
/* 124 */     this.depth++;
/*     */     
/* 126 */     boolean isContent = (this.contentName != null);
/*     */     
/* 128 */     if (isContent) {
/*     */       
/* 130 */       this.buffer.append("<").append(localName);
/* 131 */       for (int i = 0; i < attributes.getLength(); i++) {
/* 132 */         String key = attributes.getLocalName(i);
/* 133 */         String val = attributes.getValue(i);
/* 134 */         this.buffer.append(" ").append(key).append("='").append(val).append("'");
/*     */       } 
/* 136 */       this.buffer.append(">");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 141 */     this.buffer = new StringBuffer();
/* 142 */     Dnode node = createNewNode();
/* 143 */     node.setNodeName(localName);
/* 144 */     for (int i = 0; i < attributes.getLength(); i++) {
/* 145 */       String key = attributes.getLocalName(i);
/* 146 */       String val = attributes.getValue(i);
/* 147 */       node.setAttribute(key, val);
/* 148 */       if ("type".equalsIgnoreCase(key) && "content".equalsIgnoreCase(val)) {
/*     */ 
/*     */         
/* 151 */         this.contentName = localName;
/* 152 */         this.contentDepth = this.depth - 1;
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     if (this.root == null) {
/* 157 */       this.root = node;
/*     */     }
/* 159 */     if (this.currentNode != null) {
/* 160 */       this.currentNode.addChild(node);
/*     */     }
/* 162 */     this.stack.push(node);
/* 163 */     this.currentNode = node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 171 */     super.characters(ch, start, length);
/* 172 */     String s = new String(ch, start, length);
/* 173 */     int p = s.indexOf('\r');
/* 174 */     int p2 = s.indexOf('\n');
/* 175 */     if (p == -1 && p2 > -1)
/*     */     {
/*     */       
/* 178 */       s = StringHelper.replaceString(s, "\n", "\r\n");
/*     */     }
/* 180 */     this.buffer.append(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 187 */     super.endElement(uri, localName, qName);
/* 188 */     this.depth--;
/*     */     
/* 190 */     if (this.contentName != null) {
/*     */       
/* 192 */       if (this.contentName.equals(localName) && this.contentDepth == this.depth) {
/* 193 */         this.contentName = null;
/*     */       }
/*     */       else {
/*     */         
/* 197 */         this.buffer.append("</").append(localName).append(">");
/*     */       } 
/*     */       return;
/*     */     } 
/* 201 */     String content = this.buffer.toString();
/* 202 */     if (content.length() > 0) {
/* 203 */       if (this.trimWhitespace) {
/* 204 */         content = content.trim();
/*     */       }
/* 206 */       if (content.length() > 0) {
/* 207 */         this.currentNode.setNodeContent(content);
/*     */       }
/*     */     } 
/* 210 */     this.stack.pop();
/* 211 */     if (!this.stack.isEmpty()) {
/*     */       
/* 213 */       this.currentNode = (Dnode)this.stack.pop();
/* 214 */       this.stack.push(this.currentNode);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\DnodeParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */