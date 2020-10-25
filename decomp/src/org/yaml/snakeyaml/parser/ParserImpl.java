/*     */ package org.yaml.snakeyaml.parser;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.yaml.snakeyaml.error.Mark;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.events.AliasEvent;
/*     */ import org.yaml.snakeyaml.events.DocumentEndEvent;
/*     */ import org.yaml.snakeyaml.events.DocumentStartEvent;
/*     */ import org.yaml.snakeyaml.events.Event;
/*     */ import org.yaml.snakeyaml.events.ImplicitTuple;
/*     */ import org.yaml.snakeyaml.events.MappingEndEvent;
/*     */ import org.yaml.snakeyaml.events.MappingStartEvent;
/*     */ import org.yaml.snakeyaml.events.ScalarEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceEndEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceStartEvent;
/*     */ import org.yaml.snakeyaml.events.StreamEndEvent;
/*     */ import org.yaml.snakeyaml.events.StreamStartEvent;
/*     */ import org.yaml.snakeyaml.reader.StreamReader;
/*     */ import org.yaml.snakeyaml.scanner.Scanner;
/*     */ import org.yaml.snakeyaml.scanner.ScannerImpl;
/*     */ import org.yaml.snakeyaml.tokens.AliasToken;
/*     */ import org.yaml.snakeyaml.tokens.AnchorToken;
/*     */ import org.yaml.snakeyaml.tokens.BlockEntryToken;
/*     */ import org.yaml.snakeyaml.tokens.DirectiveToken;
/*     */ import org.yaml.snakeyaml.tokens.ScalarToken;
/*     */ import org.yaml.snakeyaml.tokens.StreamEndToken;
/*     */ import org.yaml.snakeyaml.tokens.StreamStartToken;
/*     */ import org.yaml.snakeyaml.tokens.TagToken;
/*     */ import org.yaml.snakeyaml.tokens.TagTuple;
/*     */ import org.yaml.snakeyaml.tokens.Token;
/*     */ import org.yaml.snakeyaml.util.ArrayStack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ParserImpl
/*     */   implements Parser
/*     */ {
/* 120 */   private static final Map<String, String> DEFAULT_TAGS = new HashMap();
/*     */   static  {
/* 122 */     DEFAULT_TAGS.put("!", "!");
/* 123 */     DEFAULT_TAGS.put("!!", "tag:yaml.org,2002:");
/*     */   }
/*     */   
/*     */   private final Scanner scanner;
/*     */   private Event currentEvent;
/*     */   private List<Integer> yamlVersion;
/*     */   private Map<String, String> tagHandles;
/*     */   private final ArrayStack<Production> states;
/*     */   private final ArrayStack<Mark> marks;
/*     */   private Production state;
/*     */   
/*     */   public ParserImpl(StreamReader reader) {
/* 135 */     this.scanner = new ScannerImpl(reader);
/* 136 */     this.currentEvent = null;
/* 137 */     this.yamlVersion = null;
/* 138 */     this.tagHandles = new HashMap();
/* 139 */     this.states = new ArrayStack(100);
/* 140 */     this.marks = new ArrayStack(10);
/* 141 */     this.state = new ParseStreamStart(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkEvent(Event.ID choices) {
/* 148 */     peekEvent();
/* 149 */     if (this.currentEvent != null && 
/* 150 */       this.currentEvent.is(choices)) {
/* 151 */       return true;
/*     */     }
/*     */     
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Event peekEvent() {
/* 161 */     if (this.currentEvent == null && 
/* 162 */       this.state != null) {
/* 163 */       this.currentEvent = this.state.produce();
/*     */     }
/*     */     
/* 166 */     return this.currentEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Event getEvent() {
/* 173 */     peekEvent();
/* 174 */     Event value = this.currentEvent;
/* 175 */     this.currentEvent = null;
/* 176 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ParseStreamStart
/*     */     implements Production
/*     */   {
/*     */     private ParseStreamStart() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Event produce() {
/* 189 */       StreamStartToken token = (StreamStartToken)ParserImpl.this.scanner.getToken();
/* 190 */       StreamStartEvent streamStartEvent = new StreamStartEvent(token.getStartMark(), token.getEndMark());
/*     */       
/* 192 */       ParserImpl.this.state = new ParserImpl.ParseImplicitDocumentStart(ParserImpl.this, null);
/* 193 */       return streamStartEvent;
/*     */     } }
/*     */   
/*     */   private class ParseImplicitDocumentStart implements Production {
/*     */     private ParseImplicitDocumentStart() {}
/*     */     
/*     */     public Event produce() {
/* 200 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd })) {
/* 201 */         ParserImpl.this.tagHandles = DEFAULT_TAGS;
/* 202 */         Token token = ParserImpl.this.scanner.peekToken();
/* 203 */         Mark startMark = token.getStartMark();
/* 204 */         Mark endMark = startMark;
/* 205 */         DocumentStartEvent documentStartEvent = new DocumentStartEvent(startMark, endMark, false, null, null);
/*     */         
/* 207 */         ParserImpl.this.states.push(new ParserImpl.ParseDocumentEnd(ParserImpl.this, null));
/* 208 */         ParserImpl.this.state = new ParserImpl.ParseBlockNode(ParserImpl.this, null);
/* 209 */         return documentStartEvent;
/*     */       } 
/* 211 */       Production p = new ParserImpl.ParseDocumentStart(ParserImpl.this, null);
/* 212 */       return p.produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseDocumentStart implements Production {
/*     */     private ParseDocumentStart() {}
/*     */     
/*     */     public Event produce() {
/*     */       StreamEndEvent streamEndEvent;
/* 221 */       while (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentEnd })) {
/* 222 */         ParserImpl.this.scanner.getToken();
/*     */       }
/*     */ 
/*     */       
/* 226 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.StreamEnd })) {
/* 227 */         Integer[] versionInteger; Token token = ParserImpl.this.scanner.peekToken();
/* 228 */         Mark startMark = token.getStartMark();
/* 229 */         List<Object> version_tags = ParserImpl.this.processDirectives();
/* 230 */         List<Object> version = (List)version_tags.get(0);
/* 231 */         Map<String, String> tags = (Map)version_tags.get(1);
/* 232 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentStart })) {
/* 233 */           throw new ParserException(null, null, "expected '<document start>', but found " + ParserImpl.this.scanner.peekToken().getTokenId(), ParserImpl.this.scanner.peekToken().getStartMark());
/*     */         }
/*     */         
/* 236 */         token = ParserImpl.this.scanner.getToken();
/* 237 */         Mark endMark = token.getEndMark();
/*     */         
/* 239 */         if (version != null) {
/* 240 */           versionInteger = new Integer[2];
/* 241 */           versionInteger = (Integer[])version.toArray(versionInteger);
/*     */         } else {
/* 243 */           versionInteger = null;
/*     */         } 
/* 245 */         streamEndEvent = new DocumentStartEvent(startMark, endMark, true, versionInteger, tags);
/* 246 */         ParserImpl.this.states.push(new ParserImpl.ParseDocumentEnd(ParserImpl.this, null));
/* 247 */         ParserImpl.this.state = new ParserImpl.ParseDocumentContent(ParserImpl.this, null);
/*     */       } else {
/*     */         
/* 250 */         StreamEndToken token = (StreamEndToken)ParserImpl.this.scanner.getToken();
/* 251 */         streamEndEvent = new StreamEndEvent(token.getStartMark(), token.getEndMark());
/* 252 */         if (!ParserImpl.this.states.isEmpty()) {
/* 253 */           throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.this.states);
/*     */         }
/* 255 */         if (!ParserImpl.this.marks.isEmpty()) {
/* 256 */           throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.this.marks);
/*     */         }
/* 258 */         ParserImpl.this.state = null;
/*     */       } 
/* 260 */       return streamEndEvent;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseDocumentEnd implements Production { private ParseDocumentEnd() {}
/*     */     
/*     */     public Event produce() {
/* 267 */       Token token = ParserImpl.this.scanner.peekToken();
/* 268 */       Mark startMark = token.getStartMark();
/* 269 */       Mark endMark = startMark;
/* 270 */       boolean explicit = false;
/* 271 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentEnd })) {
/* 272 */         token = ParserImpl.this.scanner.getToken();
/* 273 */         endMark = token.getEndMark();
/* 274 */         explicit = true;
/*     */       } 
/* 276 */       DocumentEndEvent documentEndEvent = new DocumentEndEvent(startMark, endMark, explicit);
/*     */       
/* 278 */       ParserImpl.this.state = new ParserImpl.ParseDocumentStart(ParserImpl.this, null);
/* 279 */       return documentEndEvent;
/*     */     } }
/*     */   
/*     */   private class ParseDocumentContent implements Production {
/*     */     private ParseDocumentContent() {}
/*     */     
/*     */     public Event produce() {
/* 286 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd })) {
/*     */         
/* 288 */         Event event = ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
/* 289 */         ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 290 */         return event;
/*     */       } 
/* 292 */       Production p = new ParserImpl.ParseBlockNode(ParserImpl.this, null);
/* 293 */       return p.produce();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Object> processDirectives() {
/* 300 */     this.yamlVersion = null;
/* 301 */     this.tagHandles = new HashMap();
/* 302 */     while (this.scanner.checkToken(new Token.ID[] { Token.ID.Directive })) {
/* 303 */       DirectiveToken token = (DirectiveToken)this.scanner.getToken();
/* 304 */       if (token.getName().equals("YAML")) {
/* 305 */         if (this.yamlVersion != null) {
/* 306 */           throw new ParserException(null, null, "found duplicate YAML directive", token.getStartMark());
/*     */         }
/*     */         
/* 309 */         List<Integer> value = token.getValue();
/* 310 */         Integer major = (Integer)value.get(0);
/* 311 */         if (major.intValue() != 1) {
/* 312 */           throw new ParserException(null, null, "found incompatible YAML document (version 1.* is required)", token.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 316 */         this.yamlVersion = token.getValue(); continue;
/* 317 */       }  if (token.getName().equals("TAG")) {
/* 318 */         List<String> value = token.getValue();
/* 319 */         String handle = (String)value.get(0);
/* 320 */         String prefix = (String)value.get(1);
/* 321 */         if (this.tagHandles.containsKey(handle)) {
/* 322 */           throw new ParserException(null, null, "duplicate tag handle " + handle, token.getStartMark());
/*     */         }
/*     */         
/* 325 */         this.tagHandles.put(handle, prefix);
/*     */       } 
/*     */     } 
/* 328 */     List<Object> value = new ArrayList<Object>(2);
/* 329 */     value.add(this.yamlVersion);
/* 330 */     if (!this.tagHandles.isEmpty()) {
/* 331 */       value.add(new HashMap(this.tagHandles));
/*     */     } else {
/* 333 */       value.add(new HashMap());
/*     */     } 
/* 335 */     for (String key : DEFAULT_TAGS.keySet()) {
/* 336 */       if (!this.tagHandles.containsKey(key)) {
/* 337 */         this.tagHandles.put(key, DEFAULT_TAGS.get(key));
/*     */       }
/*     */     } 
/* 340 */     return value;
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
/*     */   private class ParseBlockNode
/*     */     implements Production
/*     */   {
/*     */     private ParseBlockNode() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     public Event produce() { return ParserImpl.this.parseNode(true, false); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 370 */   private Event parseFlowNode() { return parseNode(false, false); }
/*     */ 
/*     */ 
/*     */   
/* 374 */   private Event parseBlockNodeOrIndentlessSequence() { return parseNode(true, true); }
/*     */ 
/*     */   
/*     */   private Event parseNode(boolean block, boolean indentlessSequence) {
/*     */     ScalarEvent scalarEvent;
/* 379 */     Mark startMark = null;
/* 380 */     Mark endMark = null;
/* 381 */     Mark tagMark = null;
/* 382 */     if (this.scanner.checkToken(new Token.ID[] { Token.ID.Alias })) {
/* 383 */       AliasToken token = (AliasToken)this.scanner.getToken();
/* 384 */       scalarEvent = new AliasEvent(token.getValue(), token.getStartMark(), token.getEndMark());
/* 385 */       this.state = (Production)this.states.pop();
/*     */     } else {
/* 387 */       String anchor = null;
/* 388 */       TagTuple tagTokenTag = null;
/* 389 */       if (this.scanner.checkToken(new Token.ID[] { Token.ID.Anchor })) {
/* 390 */         AnchorToken token = (AnchorToken)this.scanner.getToken();
/* 391 */         startMark = token.getStartMark();
/* 392 */         endMark = token.getEndMark();
/* 393 */         anchor = token.getValue();
/* 394 */         if (this.scanner.checkToken(new Token.ID[] { Token.ID.Tag })) {
/* 395 */           TagToken tagToken = (TagToken)this.scanner.getToken();
/* 396 */           tagMark = tagToken.getStartMark();
/* 397 */           endMark = tagToken.getEndMark();
/* 398 */           tagTokenTag = tagToken.getValue();
/*     */         } 
/*     */       } else {
/* 401 */         TagToken tagToken = (TagToken)this.scanner.getToken();
/* 402 */         startMark = tagToken.getStartMark();
/* 403 */         tagMark = startMark;
/* 404 */         endMark = tagToken.getEndMark();
/* 405 */         tagTokenTag = tagToken.getValue();
/* 406 */         if (this.scanner.checkToken(new Token.ID[] { Token.ID.Tag }) && this.scanner.checkToken(new Token.ID[] { Token.ID.Anchor })) {
/* 407 */           AnchorToken token = (AnchorToken)this.scanner.getToken();
/* 408 */           endMark = token.getEndMark();
/* 409 */           anchor = token.getValue();
/*     */         } 
/*     */       } 
/* 412 */       String tag = null;
/* 413 */       if (tagTokenTag != null) {
/* 414 */         String handle = tagTokenTag.getHandle();
/* 415 */         String suffix = tagTokenTag.getSuffix();
/* 416 */         if (handle != null) {
/* 417 */           if (!this.tagHandles.containsKey(handle)) {
/* 418 */             throw new ParserException("while parsing a node", startMark, "found undefined tag handle " + handle, tagMark);
/*     */           }
/*     */           
/* 421 */           tag = (String)this.tagHandles.get(handle) + suffix;
/*     */         } else {
/* 423 */           tag = suffix;
/*     */         } 
/*     */       } 
/* 426 */       if (startMark == null) {
/* 427 */         startMark = this.scanner.peekToken().getStartMark();
/* 428 */         endMark = startMark;
/*     */       } 
/* 430 */       scalarEvent = null;
/* 431 */       boolean implicit = (tag == null || tag.equals("!"));
/* 432 */       if (indentlessSequence && this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 433 */         endMark = this.scanner.peekToken().getEndMark();
/* 434 */         SequenceStartEvent sequenceStartEvent = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
/*     */         
/* 436 */         this.state = new ParseIndentlessSequenceEntry(null);
/*     */       }
/* 438 */       else if (this.scanner.checkToken(new Token.ID[] { Token.ID.Scalar })) {
/* 439 */         ImplicitTuple implicitValues; ScalarToken token = (ScalarToken)this.scanner.getToken();
/* 440 */         endMark = token.getEndMark();
/*     */         
/* 442 */         if ((token.getPlain() && tag == null) || "!".equals(tag)) {
/* 443 */           implicitValues = new ImplicitTuple(true, false);
/* 444 */         } else if (tag == null) {
/* 445 */           implicitValues = new ImplicitTuple(false, true);
/*     */         } else {
/* 447 */           implicitValues = new ImplicitTuple(false, false);
/*     */         } 
/* 449 */         ScalarEvent scalarEvent1 = new ScalarEvent(anchor, tag, implicitValues, token.getValue(), startMark, endMark, Character.valueOf(token.getStyle()));
/*     */         
/* 451 */         this.state = (Production)this.states.pop();
/* 452 */       } else if (this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceStart })) {
/* 453 */         endMark = this.scanner.peekToken().getEndMark();
/* 454 */         SequenceStartEvent sequenceStartEvent = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.TRUE);
/*     */         
/* 456 */         this.state = new ParseFlowSequenceFirstEntry(null);
/* 457 */       } else if (this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingStart })) {
/* 458 */         endMark = this.scanner.peekToken().getEndMark();
/* 459 */         MappingStartEvent mappingStartEvent = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.TRUE);
/*     */         
/* 461 */         this.state = new ParseFlowMappingFirstKey(null);
/* 462 */       } else if (block && this.scanner.checkToken(new Token.ID[] { Token.ID.BlockSequenceStart })) {
/* 463 */         endMark = this.scanner.peekToken().getStartMark();
/* 464 */         SequenceStartEvent sequenceStartEvent = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
/*     */         
/* 466 */         this.state = new ParseBlockSequenceFirstEntry(null);
/* 467 */       } else if (block && this.scanner.checkToken(new Token.ID[] { Token.ID.BlockMappingStart })) {
/* 468 */         endMark = this.scanner.peekToken().getStartMark();
/* 469 */         MappingStartEvent mappingStartEvent = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
/*     */         
/* 471 */         this.state = new ParseBlockMappingFirstKey(null);
/* 472 */       } else if (anchor != null || tag != null) {
/*     */ 
/*     */         
/* 475 */         scalarEvent = new ScalarEvent(anchor, tag, new ImplicitTuple(implicit, false), "", startMark, endMark, Character.valueOf(false));
/*     */         
/* 477 */         this.state = (Production)this.states.pop();
/*     */       } else {
/*     */         String node;
/* 480 */         if (block) {
/* 481 */           node = "block";
/*     */         } else {
/* 483 */           node = "flow";
/*     */         } 
/* 485 */         Token token = this.scanner.peekToken();
/* 486 */         throw new ParserException("while parsing a " + node + " node", startMark, "expected the node content, but found " + token.getTokenId(), token.getStartMark());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 492 */     return scalarEvent;
/*     */   }
/*     */   
/*     */   private class ParseBlockSequenceFirstEntry
/*     */     implements Production {
/*     */     private ParseBlockSequenceFirstEntry() {}
/*     */     
/*     */     public Event produce() {
/* 500 */       Token token = ParserImpl.this.scanner.getToken();
/* 501 */       ParserImpl.this.marks.push(token.getStartMark());
/* 502 */       return (new ParserImpl.ParseBlockSequenceEntry(ParserImpl.this, null)).produce();
/*     */     } }
/*     */   
/*     */   private class ParseBlockSequenceEntry implements Production { private ParseBlockSequenceEntry() {}
/*     */     
/*     */     public Event produce() {
/* 508 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 509 */         BlockEntryToken token = (BlockEntryToken)ParserImpl.this.scanner.getToken();
/* 510 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry, Token.ID.BlockEnd })) {
/* 511 */           ParserImpl.this.states.push(new ParseBlockSequenceEntry(ParserImpl.this));
/* 512 */           return (new ParserImpl.ParseBlockNode(ParserImpl.this, null)).produce();
/*     */         } 
/* 514 */         ParserImpl.this.state = new ParseBlockSequenceEntry(ParserImpl.this);
/* 515 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       } 
/*     */       
/* 518 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEnd })) {
/* 519 */         Token token = ParserImpl.this.scanner.peekToken();
/* 520 */         throw new ParserException("while parsing a block collection", (Mark)ParserImpl.this.marks.pop(), "expected <block end>, but found " + token.getTokenId(), token.getStartMark());
/*     */       } 
/*     */ 
/*     */       
/* 524 */       Token token = ParserImpl.this.scanner.getToken();
/* 525 */       SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 526 */       ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 527 */       ParserImpl.this.marks.pop();
/* 528 */       return sequenceEndEvent;
/*     */     } }
/*     */ 
/*     */   
/*     */   private class ParseIndentlessSequenceEntry implements Production {
/*     */     private ParseIndentlessSequenceEntry() {}
/*     */     
/*     */     public Event produce() {
/* 536 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 537 */         Token token = ParserImpl.this.scanner.getToken();
/* 538 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/*     */           
/* 540 */           ParserImpl.this.states.push(new ParseIndentlessSequenceEntry(ParserImpl.this));
/* 541 */           return (new ParserImpl.ParseBlockNode(ParserImpl.this, null)).produce();
/*     */         } 
/* 543 */         ParserImpl.this.state = new ParseIndentlessSequenceEntry(ParserImpl.this);
/* 544 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       } 
/*     */       
/* 547 */       Token token = ParserImpl.this.scanner.peekToken();
/* 548 */       SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 549 */       ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 550 */       return sequenceEndEvent;
/*     */     } }
/*     */   
/*     */   private class ParseBlockMappingFirstKey implements Production { private ParseBlockMappingFirstKey() {}
/*     */     
/*     */     public Event produce() {
/* 556 */       Token token = ParserImpl.this.scanner.getToken();
/* 557 */       ParserImpl.this.marks.push(token.getStartMark());
/* 558 */       return (new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null)).produce();
/*     */     } }
/*     */   
/*     */   private class ParseBlockMappingKey implements Production { private ParseBlockMappingKey() {}
/*     */     
/*     */     public Event produce() {
/* 564 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 565 */         Token token = ParserImpl.this.scanner.getToken();
/* 566 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/* 567 */           ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingValue(ParserImpl.this, null));
/* 568 */           return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
/*     */         } 
/* 570 */         ParserImpl.this.state = new ParserImpl.ParseBlockMappingValue(ParserImpl.this, null);
/* 571 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       } 
/*     */       
/* 574 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEnd })) {
/* 575 */         Token token = ParserImpl.this.scanner.peekToken();
/* 576 */         throw new ParserException("while parsing a block mapping", (Mark)ParserImpl.this.marks.pop(), "expected <block end>, but found " + token.getTokenId(), token.getStartMark());
/*     */       } 
/*     */ 
/*     */       
/* 580 */       Token token = ParserImpl.this.scanner.getToken();
/* 581 */       MappingEndEvent mappingEndEvent = new MappingEndEvent(token.getStartMark(), token.getEndMark());
/* 582 */       ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 583 */       ParserImpl.this.marks.pop();
/* 584 */       return mappingEndEvent;
/*     */     } }
/*     */   
/*     */   private class ParseBlockMappingValue implements Production { private ParseBlockMappingValue() {}
/*     */     
/*     */     public Event produce() {
/* 590 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 591 */         Token token = ParserImpl.this.scanner.getToken();
/* 592 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/* 593 */           ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null));
/* 594 */           return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
/*     */         } 
/* 596 */         ParserImpl.this.state = new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null);
/* 597 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       } 
/*     */       
/* 600 */       ParserImpl.this.state = new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null);
/* 601 */       Token token = ParserImpl.this.scanner.peekToken();
/* 602 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ParseFlowSequenceFirstEntry
/*     */     implements Production
/*     */   {
/*     */     private ParseFlowSequenceFirstEntry() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Event produce() {
/* 621 */       Token token = ParserImpl.this.scanner.getToken();
/* 622 */       ParserImpl.this.marks.push(token.getStartMark());
/* 623 */       return (new ParserImpl.ParseFlowSequenceEntry(ParserImpl.this, true)).produce();
/*     */     } }
/*     */   
/*     */   private class ParseFlowSequenceEntry implements Production {
/*     */     public ParseFlowSequenceEntry(boolean first) {
/* 628 */       this.first = false;
/*     */ 
/*     */       
/* 631 */       this.first = first;
/*     */     }
/*     */     private boolean first;
/*     */     public Event produce() {
/* 635 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceEnd })) {
/* 636 */         if (!this.first) {
/* 637 */           if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry })) {
/* 638 */             ParserImpl.this.scanner.getToken();
/*     */           } else {
/* 640 */             Token token = ParserImpl.this.scanner.peekToken();
/* 641 */             throw new ParserException("while parsing a flow sequence", (Mark)ParserImpl.this.marks.pop(), "expected ',' or ']', but got " + token.getTokenId(), token.getStartMark());
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 646 */         if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 647 */           Token token = ParserImpl.this.scanner.peekToken();
/* 648 */           MappingStartEvent mappingStartEvent = new MappingStartEvent(null, null, true, token.getStartMark(), token.getEndMark(), Boolean.TRUE);
/*     */           
/* 650 */           ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingKey(ParserImpl.this, null);
/* 651 */           return mappingStartEvent;
/* 652 */         }  if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceEnd })) {
/* 653 */           ParserImpl.this.states.push(new ParseFlowSequenceEntry(ParserImpl.this, false));
/* 654 */           return ParserImpl.this.parseFlowNode();
/*     */         } 
/*     */       } 
/* 657 */       Token token = ParserImpl.this.scanner.getToken();
/* 658 */       SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 659 */       ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 660 */       ParserImpl.this.marks.pop();
/* 661 */       return sequenceEndEvent;
/*     */     } }
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingKey implements Production { private ParseFlowSequenceEntryMappingKey() {}
/*     */     
/*     */     public Event produce() {
/* 667 */       Token token = ParserImpl.this.scanner.getToken();
/* 668 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd })) {
/* 669 */         ParserImpl.this.states.push(new ParserImpl.ParseFlowSequenceEntryMappingValue(ParserImpl.this, null));
/* 670 */         return ParserImpl.this.parseFlowNode();
/*     */       } 
/* 672 */       ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingValue(ParserImpl.this, null);
/* 673 */       return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */     } }
/*     */ 
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingValue implements Production { private ParseFlowSequenceEntryMappingValue() {}
/*     */     
/*     */     public Event produce() {
/* 680 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 681 */         Token token = ParserImpl.this.scanner.getToken();
/* 682 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry, Token.ID.FlowSequenceEnd })) {
/* 683 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null));
/* 684 */           return ParserImpl.this.parseFlowNode();
/*     */         } 
/* 686 */         ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null);
/* 687 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       } 
/*     */       
/* 690 */       ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null);
/* 691 */       Token token = ParserImpl.this.scanner.peekToken();
/* 692 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     } }
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingEnd implements Production {
/*     */     private ParseFlowSequenceEntryMappingEnd() {}
/*     */     
/*     */     public Event produce() {
/* 699 */       ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntry(ParserImpl.this, false);
/* 700 */       Token token = ParserImpl.this.scanner.peekToken();
/* 701 */       return new MappingEndEvent(token.getStartMark(), token.getEndMark());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ParseFlowMappingFirstKey
/*     */     implements Production
/*     */   {
/*     */     private ParseFlowMappingFirstKey() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Event produce() {
/* 716 */       Token token = ParserImpl.this.scanner.getToken();
/* 717 */       ParserImpl.this.marks.push(token.getStartMark());
/* 718 */       return (new ParserImpl.ParseFlowMappingKey(ParserImpl.this, true)).produce();
/*     */     } }
/*     */   
/*     */   private class ParseFlowMappingKey implements Production {
/*     */     public ParseFlowMappingKey(boolean first) {
/* 723 */       this.first = false;
/*     */ 
/*     */       
/* 726 */       this.first = first;
/*     */     }
/*     */     private boolean first;
/*     */     public Event produce() {
/* 730 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingEnd })) {
/* 731 */         if (!this.first) {
/* 732 */           if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry })) {
/* 733 */             ParserImpl.this.scanner.getToken();
/*     */           } else {
/* 735 */             Token token = ParserImpl.this.scanner.peekToken();
/* 736 */             throw new ParserException("while parsing a flow mapping", (Mark)ParserImpl.this.marks.pop(), "expected ',' or '}', but got " + token.getTokenId(), token.getStartMark());
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 741 */         if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 742 */           Token token = ParserImpl.this.scanner.getToken();
/* 743 */           if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd })) {
/*     */             
/* 745 */             ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingValue(ParserImpl.this, null));
/* 746 */             return ParserImpl.this.parseFlowNode();
/*     */           } 
/* 748 */           ParserImpl.this.state = new ParserImpl.ParseFlowMappingValue(ParserImpl.this, null);
/* 749 */           return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */         } 
/* 751 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingEnd })) {
/* 752 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingEmptyValue(ParserImpl.this, null));
/* 753 */           return ParserImpl.this.parseFlowNode();
/*     */         } 
/*     */       } 
/* 756 */       Token token = ParserImpl.this.scanner.getToken();
/* 757 */       MappingEndEvent mappingEndEvent = new MappingEndEvent(token.getStartMark(), token.getEndMark());
/* 758 */       ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 759 */       ParserImpl.this.marks.pop();
/* 760 */       return mappingEndEvent;
/*     */     } }
/*     */   
/*     */   private class ParseFlowMappingValue implements Production { private ParseFlowMappingValue() {}
/*     */     
/*     */     public Event produce() {
/* 766 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 767 */         Token token = ParserImpl.this.scanner.getToken();
/* 768 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry, Token.ID.FlowMappingEnd })) {
/* 769 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false));
/* 770 */           return ParserImpl.this.parseFlowNode();
/*     */         } 
/* 772 */         ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
/* 773 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       } 
/*     */       
/* 776 */       ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
/* 777 */       Token token = ParserImpl.this.scanner.peekToken();
/* 778 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     } }
/*     */   
/*     */   private class ParseFlowMappingEmptyValue implements Production {
/*     */     private ParseFlowMappingEmptyValue() {}
/*     */     
/*     */     public Event produce() {
/* 785 */       ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
/* 786 */       return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
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
/* 799 */   private Event processEmptyScalar(Mark mark) { return new ScalarEvent(null, null, new ImplicitTuple(true, false), "", mark, mark, Character.valueOf(false)); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\parser\ParserImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */