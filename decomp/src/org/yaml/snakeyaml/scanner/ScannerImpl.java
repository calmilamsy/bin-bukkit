/*      */ package org.yaml.snakeyaml.scanner;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.charset.CharacterCodingException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Pattern;
/*      */ import org.yaml.snakeyaml.error.Mark;
/*      */ import org.yaml.snakeyaml.error.YAMLException;
/*      */ import org.yaml.snakeyaml.reader.StreamReader;
/*      */ import org.yaml.snakeyaml.tokens.AliasToken;
/*      */ import org.yaml.snakeyaml.tokens.AnchorToken;
/*      */ import org.yaml.snakeyaml.tokens.BlockEndToken;
/*      */ import org.yaml.snakeyaml.tokens.BlockEntryToken;
/*      */ import org.yaml.snakeyaml.tokens.BlockMappingStartToken;
/*      */ import org.yaml.snakeyaml.tokens.BlockSequenceStartToken;
/*      */ import org.yaml.snakeyaml.tokens.DirectiveToken;
/*      */ import org.yaml.snakeyaml.tokens.DocumentEndToken;
/*      */ import org.yaml.snakeyaml.tokens.DocumentStartToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowEntryToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowMappingEndToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowMappingStartToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowSequenceEndToken;
/*      */ import org.yaml.snakeyaml.tokens.FlowSequenceStartToken;
/*      */ import org.yaml.snakeyaml.tokens.KeyToken;
/*      */ import org.yaml.snakeyaml.tokens.ScalarToken;
/*      */ import org.yaml.snakeyaml.tokens.StreamEndToken;
/*      */ import org.yaml.snakeyaml.tokens.StreamStartToken;
/*      */ import org.yaml.snakeyaml.tokens.TagToken;
/*      */ import org.yaml.snakeyaml.tokens.TagTuple;
/*      */ import org.yaml.snakeyaml.tokens.Token;
/*      */ import org.yaml.snakeyaml.tokens.Token.ID;
/*      */ import org.yaml.snakeyaml.tokens.ValueToken;
/*      */ import org.yaml.snakeyaml.util.ArrayStack;
/*      */ import org.yaml.snakeyaml.util.UriEncoder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ScannerImpl
/*      */   implements Scanner
/*      */ {
/*   88 */   private static final Pattern NOT_HEXA = Pattern.compile("[^0-9A-Fa-f]");
/*   89 */   public static final Map<Character, String> ESCAPE_REPLACEMENTS = new HashMap();
/*   90 */   public static final Map<Character, Integer> ESCAPE_CODES = new HashMap(); private final StreamReader reader; private boolean done; private int flowLevel; private List<Token> tokens;
/*      */   
/*      */   static  {
/*   93 */     ESCAPE_REPLACEMENTS.put(new Character(48), "\000");
/*   94 */     ESCAPE_REPLACEMENTS.put(new Character(97), "\007");
/*   95 */     ESCAPE_REPLACEMENTS.put(new Character(98), "\b");
/*   96 */     ESCAPE_REPLACEMENTS.put(new Character(116), "\t");
/*   97 */     ESCAPE_REPLACEMENTS.put(new Character(110), "\n");
/*   98 */     ESCAPE_REPLACEMENTS.put(new Character(118), "\013");
/*   99 */     ESCAPE_REPLACEMENTS.put(new Character(102), "\f");
/*  100 */     ESCAPE_REPLACEMENTS.put(new Character(114), "\r");
/*  101 */     ESCAPE_REPLACEMENTS.put(new Character(101), "\033");
/*  102 */     ESCAPE_REPLACEMENTS.put(new Character(32), " ");
/*  103 */     ESCAPE_REPLACEMENTS.put(new Character(34), "\"");
/*  104 */     ESCAPE_REPLACEMENTS.put(new Character(92), "\\");
/*  105 */     ESCAPE_REPLACEMENTS.put(new Character(78), "");
/*  106 */     ESCAPE_REPLACEMENTS.put(new Character(95), " ");
/*  107 */     ESCAPE_REPLACEMENTS.put(new Character(76), " ");
/*  108 */     ESCAPE_REPLACEMENTS.put(new Character(80), " ");
/*      */     
/*  110 */     ESCAPE_CODES.put(new Character(120), Integer.valueOf(2));
/*  111 */     ESCAPE_CODES.put(new Character(117), Integer.valueOf(4));
/*  112 */     ESCAPE_CODES.put(new Character(85), Integer.valueOf(8));
/*      */   }
/*      */   private int tokensTaken; private int indent; private ArrayStack<Integer> indents; private boolean allowSimpleKey; private Map<Integer, SimpleKey> possibleSimpleKeys;
/*      */   public ScannerImpl(StreamReader reader) {
/*  116 */     this.done = false;
/*      */ 
/*      */ 
/*      */     
/*  120 */     this.flowLevel = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  126 */     this.tokensTaken = 0;
/*      */ 
/*      */     
/*  129 */     this.indent = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  158 */     this.allowSimpleKey = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  170 */     this.reader = reader;
/*  171 */     this.tokens = new ArrayList(100);
/*  172 */     this.indents = new ArrayStack(10);
/*      */     
/*  174 */     this.possibleSimpleKeys = new LinkedHashMap();
/*  175 */     fetchStreamStart();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkToken(ID... choices) {
/*  182 */     while (needMoreTokens()) {
/*  183 */       fetchMoreTokens();
/*      */     }
/*  185 */     if (!this.tokens.isEmpty()) {
/*  186 */       if (choices.length == 0) {
/*  187 */         return true;
/*      */       }
/*  189 */       Token first = (Token)this.tokens.get(0);
/*  190 */       for (Token.ID id : choices) {
/*  191 */         if (first.getTokenId() == id) {
/*  192 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  196 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Token peekToken() {
/*  203 */     while (needMoreTokens()) {
/*  204 */       fetchMoreTokens();
/*      */     }
/*  206 */     return (Token)this.tokens.get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Token getToken() {
/*  213 */     if (!this.tokens.isEmpty()) {
/*  214 */       this.tokensTaken++;
/*  215 */       return (Token)this.tokens.remove(0);
/*      */     } 
/*  217 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needMoreTokens() {
/*  223 */     if (this.done) {
/*  224 */       return false;
/*      */     }
/*  226 */     if (this.tokens.isEmpty()) {
/*  227 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  231 */     stalePossibleSimpleKeys();
/*  232 */     return (nextPossibleSimpleKey() == this.tokensTaken);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchMoreTokens() {
/*  237 */     scanToNextToken();
/*      */     
/*  239 */     stalePossibleSimpleKeys();
/*      */ 
/*      */     
/*  242 */     unwindIndent(this.reader.getColumn());
/*      */     
/*  244 */     char ch = this.reader.peek();
/*  245 */     switch (ch) {
/*      */       
/*      */       case '\000':
/*  248 */         fetchStreamEnd();
/*      */         return;
/*      */       
/*      */       case '%':
/*  252 */         if (checkDirective()) {
/*  253 */           fetchDirective();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case '-':
/*  259 */         if (checkDocumentStart()) {
/*  260 */           fetchDocumentStart();
/*      */           return;
/*      */         } 
/*  263 */         if (checkBlockEntry()) {
/*  264 */           fetchBlockEntry();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case '.':
/*  270 */         if (checkDocumentEnd()) {
/*  271 */           fetchDocumentEnd();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case '[':
/*  278 */         fetchFlowSequenceStart();
/*      */         return;
/*      */       
/*      */       case '{':
/*  282 */         fetchFlowMappingStart();
/*      */         return;
/*      */       
/*      */       case ']':
/*  286 */         fetchFlowSequenceEnd();
/*      */         return;
/*      */       
/*      */       case '}':
/*  290 */         fetchFlowMappingEnd();
/*      */         return;
/*      */       
/*      */       case ',':
/*  294 */         fetchFlowEntry();
/*      */         return;
/*      */ 
/*      */       
/*      */       case '?':
/*  299 */         if (checkKey()) {
/*  300 */           fetchKey();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case ':':
/*  306 */         if (checkValue()) {
/*  307 */           fetchValue();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case '*':
/*  313 */         fetchAlias();
/*      */         return;
/*      */       
/*      */       case '&':
/*  317 */         fetchAnchor();
/*      */         return;
/*      */       
/*      */       case '!':
/*  321 */         fetchTag();
/*      */         return;
/*      */       
/*      */       case '|':
/*  325 */         if (this.flowLevel == 0) {
/*  326 */           fetchLiteral();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case '>':
/*  332 */         if (this.flowLevel == 0) {
/*  333 */           fetchFolded();
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case '\'':
/*  339 */         fetchSingle();
/*      */         return;
/*      */       
/*      */       case '"':
/*  343 */         fetchDouble();
/*      */         return;
/*      */     } 
/*      */     
/*  347 */     if (checkPlain()) {
/*  348 */       fetchPlain();
/*      */       
/*      */       return;
/*      */     } 
/*  352 */     String chRepresentation = String.valueOf(ch);
/*  353 */     for (Character s : ESCAPE_REPLACEMENTS.keySet()) {
/*  354 */       String v = (String)ESCAPE_REPLACEMENTS.get(s);
/*  355 */       if (v.equals(chRepresentation)) {
/*  356 */         chRepresentation = "\\" + s;
/*      */         break;
/*      */       } 
/*      */     } 
/*  360 */     throw new ScannerException("while scanning for the next token", null, "found character " + ch + "'" + chRepresentation + "' that cannot start any token", this.reader.getMark());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nextPossibleSimpleKey() {
/*  375 */     Iterator<SimpleKey> iter = this.possibleSimpleKeys.values().iterator();
/*  376 */     if (iter.hasNext()) {
/*  377 */       SimpleKey key = (SimpleKey)iter.next();
/*  378 */       return key.getTokenNumber();
/*      */     } 
/*  380 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void stalePossibleSimpleKeys() {
/*  395 */     Set<Integer> toRemove = null;
/*  396 */     for (Integer level : this.possibleSimpleKeys.keySet()) {
/*  397 */       SimpleKey key = (SimpleKey)this.possibleSimpleKeys.get(level);
/*  398 */       if (key.getLine() != this.reader.getLine() || this.reader.getIndex() - key.getIndex() > 1024) {
/*  399 */         if (key.isRequired()) {
/*  400 */           throw new ScannerException("while scanning a simple key", key.getMark(), "could not found expected ':'", this.reader.getMark());
/*      */         }
/*      */         
/*  403 */         if (toRemove == null) {
/*  404 */           toRemove = new HashSet<Integer>();
/*      */         }
/*  406 */         toRemove.add(level);
/*      */       } 
/*      */     } 
/*      */     
/*  410 */     if (toRemove != null) {
/*  411 */       for (Integer level : toRemove) {
/*  412 */         this.possibleSimpleKeys.remove(level);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void savePossibleSimpleKey() {
/*  428 */     boolean required = (this.flowLevel == 0 && this.indent == this.reader.getColumn());
/*      */     
/*  430 */     if (this.allowSimpleKey || !required) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  441 */       if (this.allowSimpleKey) {
/*  442 */         removePossibleSimpleKey();
/*  443 */         int tokenNumber = this.tokensTaken + this.tokens.size();
/*  444 */         SimpleKey key = new SimpleKey(tokenNumber, required, this.reader.getIndex(), this.reader.getLine(), this.reader.getColumn(), this.reader.getMark());
/*      */         
/*  446 */         this.possibleSimpleKeys.put(Integer.valueOf(this.flowLevel), key);
/*      */       } 
/*      */       return;
/*      */     } 
/*      */     throw new YAMLException("A simple key is required only if it is the first token in the current line");
/*      */   }
/*      */   
/*      */   private void removePossibleSimpleKey() {
/*  454 */     SimpleKey key = (SimpleKey)this.possibleSimpleKeys.get(Integer.valueOf(this.flowLevel));
/*  455 */     if (key != null && key.isRequired()) {
/*  456 */       throw new ScannerException("while scanning a simple key", key.getMark(), "could not found expected ':'", this.reader.getMark());
/*      */     }
/*      */     
/*  459 */     this.possibleSimpleKeys.remove(Integer.valueOf(this.flowLevel));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unwindIndent(int col) {
/*  477 */     if (this.flowLevel != 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  482 */     while (this.indent > col) {
/*  483 */       Mark mark = this.reader.getMark();
/*  484 */       this.indent = ((Integer)this.indents.pop()).intValue();
/*  485 */       this.tokens.add(new BlockEndToken(mark, mark));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addIndent(int column) {
/*  493 */     if (this.indent < column) {
/*  494 */       this.indents.push(Integer.valueOf(this.indent));
/*  495 */       this.indent = column;
/*  496 */       return true;
/*      */     } 
/*  498 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchStreamStart() {
/*  509 */     Mark mark = this.reader.getMark();
/*      */ 
/*      */     
/*  512 */     StreamStartToken streamStartToken = new StreamStartToken(mark, mark);
/*  513 */     this.tokens.add(streamStartToken);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchStreamEnd() {
/*  518 */     unwindIndent(-1);
/*      */ 
/*      */     
/*  521 */     removePossibleSimpleKey();
/*  522 */     this.allowSimpleKey = false;
/*  523 */     this.possibleSimpleKeys.clear();
/*      */ 
/*      */     
/*  526 */     Mark mark = this.reader.getMark();
/*      */ 
/*      */     
/*  529 */     StreamEndToken streamEndToken = new StreamEndToken(mark, mark);
/*  530 */     this.tokens.add(streamEndToken);
/*      */ 
/*      */     
/*  533 */     this.done = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchDirective() {
/*  538 */     unwindIndent(-1);
/*      */ 
/*      */     
/*  541 */     removePossibleSimpleKey();
/*  542 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  545 */     Token tok = scanDirective();
/*  546 */     this.tokens.add(tok);
/*      */   }
/*      */ 
/*      */   
/*  550 */   private void fetchDocumentStart() { fetchDocumentIndicator(true); }
/*      */ 
/*      */ 
/*      */   
/*  554 */   private void fetchDocumentEnd() { fetchDocumentIndicator(false); }
/*      */ 
/*      */   
/*      */   private void fetchDocumentIndicator(boolean isDocumentStart) {
/*      */     DocumentEndToken documentEndToken;
/*  559 */     unwindIndent(-1);
/*      */ 
/*      */ 
/*      */     
/*  563 */     removePossibleSimpleKey();
/*  564 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  567 */     Mark startMark = this.reader.getMark();
/*  568 */     this.reader.forward(3);
/*  569 */     Mark endMark = this.reader.getMark();
/*      */     
/*  571 */     if (isDocumentStart) {
/*  572 */       documentEndToken = new DocumentStartToken(startMark, endMark);
/*      */     } else {
/*  574 */       documentEndToken = new DocumentEndToken(startMark, endMark);
/*      */     } 
/*  576 */     this.tokens.add(documentEndToken);
/*      */   }
/*      */ 
/*      */   
/*  580 */   private void fetchFlowSequenceStart() { fetchFlowCollectionStart(false); }
/*      */ 
/*      */ 
/*      */   
/*  584 */   private void fetchFlowMappingStart() { fetchFlowCollectionStart(true); }
/*      */ 
/*      */   
/*      */   private void fetchFlowCollectionStart(boolean isMappingStart) {
/*      */     FlowSequenceStartToken flowSequenceStartToken;
/*  589 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  592 */     this.flowLevel++;
/*      */ 
/*      */     
/*  595 */     this.allowSimpleKey = true;
/*      */ 
/*      */     
/*  598 */     Mark startMark = this.reader.getMark();
/*  599 */     this.reader.forward(1);
/*  600 */     Mark endMark = this.reader.getMark();
/*      */     
/*  602 */     if (isMappingStart) {
/*  603 */       flowSequenceStartToken = new FlowMappingStartToken(startMark, endMark);
/*      */     } else {
/*  605 */       flowSequenceStartToken = new FlowSequenceStartToken(startMark, endMark);
/*      */     } 
/*  607 */     this.tokens.add(flowSequenceStartToken);
/*      */   }
/*      */ 
/*      */   
/*  611 */   private void fetchFlowSequenceEnd() { fetchFlowCollectionEnd(false); }
/*      */ 
/*      */ 
/*      */   
/*  615 */   private void fetchFlowMappingEnd() { fetchFlowCollectionEnd(true); }
/*      */ 
/*      */   
/*      */   private void fetchFlowCollectionEnd(boolean isMappingEnd) {
/*      */     FlowSequenceEndToken flowSequenceEndToken;
/*  620 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  623 */     this.flowLevel--;
/*      */ 
/*      */     
/*  626 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  629 */     Mark startMark = this.reader.getMark();
/*  630 */     this.reader.forward();
/*  631 */     Mark endMark = this.reader.getMark();
/*      */     
/*  633 */     if (isMappingEnd) {
/*  634 */       flowSequenceEndToken = new FlowMappingEndToken(startMark, endMark);
/*      */     } else {
/*  636 */       flowSequenceEndToken = new FlowSequenceEndToken(startMark, endMark);
/*      */     } 
/*  638 */     this.tokens.add(flowSequenceEndToken);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchFlowEntry() {
/*  643 */     this.allowSimpleKey = true;
/*      */ 
/*      */     
/*  646 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  649 */     Mark startMark = this.reader.getMark();
/*  650 */     this.reader.forward();
/*  651 */     Mark endMark = this.reader.getMark();
/*  652 */     FlowEntryToken flowEntryToken = new FlowEntryToken(startMark, endMark);
/*  653 */     this.tokens.add(flowEntryToken);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchBlockEntry() {
/*  658 */     if (this.flowLevel == 0) {
/*      */       
/*  660 */       if (!this.allowSimpleKey) {
/*  661 */         throw new ScannerException(null, null, "sequence entries are not allowed here", this.reader.getMark());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  666 */       if (addIndent(this.reader.getColumn())) {
/*  667 */         Mark mark = this.reader.getMark();
/*  668 */         this.tokens.add(new BlockSequenceStartToken(mark, mark));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  675 */     this.allowSimpleKey = true;
/*      */ 
/*      */     
/*  678 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  681 */     Mark startMark = this.reader.getMark();
/*  682 */     this.reader.forward();
/*  683 */     Mark endMark = this.reader.getMark();
/*  684 */     BlockEntryToken blockEntryToken = new BlockEntryToken(startMark, endMark);
/*  685 */     this.tokens.add(blockEntryToken);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchKey() {
/*  690 */     if (this.flowLevel == 0) {
/*      */       
/*  692 */       if (!this.allowSimpleKey) {
/*  693 */         throw new ScannerException(null, null, "mapping keys are not allowed here", this.reader.getMark());
/*      */       }
/*      */ 
/*      */       
/*  697 */       if (addIndent(this.reader.getColumn())) {
/*  698 */         Mark mark = this.reader.getMark();
/*  699 */         this.tokens.add(new BlockMappingStartToken(mark, mark));
/*      */       } 
/*      */     } 
/*      */     
/*  703 */     this.allowSimpleKey = (this.flowLevel == 0);
/*      */ 
/*      */     
/*  706 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  709 */     Mark startMark = this.reader.getMark();
/*  710 */     this.reader.forward();
/*  711 */     Mark endMark = this.reader.getMark();
/*  712 */     KeyToken keyToken = new KeyToken(startMark, endMark);
/*  713 */     this.tokens.add(keyToken);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchValue() {
/*  718 */     if (this.possibleSimpleKeys.keySet().contains(Integer.valueOf(this.flowLevel))) {
/*      */       
/*  720 */       SimpleKey key = (SimpleKey)this.possibleSimpleKeys.get(Integer.valueOf(this.flowLevel));
/*  721 */       this.possibleSimpleKeys.remove(Integer.valueOf(this.flowLevel));
/*  722 */       this.tokens.add(key.getTokenNumber() - this.tokensTaken, new KeyToken(key.getMark(), key.getMark()));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  727 */       if (this.flowLevel == 0 && 
/*  728 */         addIndent(key.getColumn())) {
/*  729 */         this.tokens.add(key.getTokenNumber() - this.tokensTaken, new BlockMappingStartToken(key.getMark(), key.getMark()));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  734 */       this.allowSimpleKey = false;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  740 */       if (this.flowLevel == 0)
/*      */       {
/*      */ 
/*      */         
/*  744 */         if (!this.allowSimpleKey) {
/*  745 */           throw new ScannerException(null, null, "mapping values are not allowed here", this.reader.getMark());
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  753 */       if (this.flowLevel == 0 && 
/*  754 */         addIndent(this.reader.getColumn())) {
/*  755 */         Mark mark = this.reader.getMark();
/*  756 */         this.tokens.add(new BlockMappingStartToken(mark, mark));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  761 */       this.allowSimpleKey = (this.flowLevel == 0);
/*      */ 
/*      */       
/*  764 */       removePossibleSimpleKey();
/*      */     } 
/*      */     
/*  767 */     Mark startMark = this.reader.getMark();
/*  768 */     this.reader.forward();
/*  769 */     Mark endMark = this.reader.getMark();
/*  770 */     ValueToken valueToken = new ValueToken(startMark, endMark);
/*  771 */     this.tokens.add(valueToken);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchAlias() {
/*  776 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  779 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  782 */     Token tok = scanAnchor(false);
/*  783 */     this.tokens.add(tok);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchAnchor() {
/*  788 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  791 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  794 */     Token tok = scanAnchor(true);
/*  795 */     this.tokens.add(tok);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchTag() {
/*  800 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  803 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  806 */     Token tok = scanTag();
/*  807 */     this.tokens.add(tok);
/*      */   }
/*      */ 
/*      */   
/*  811 */   private void fetchLiteral() { fetchBlockScalar('|'); }
/*      */ 
/*      */ 
/*      */   
/*  815 */   private void fetchFolded() { fetchBlockScalar('>'); }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchBlockScalar(char style) {
/*  820 */     this.allowSimpleKey = true;
/*      */ 
/*      */     
/*  823 */     removePossibleSimpleKey();
/*      */ 
/*      */     
/*  826 */     Token tok = scanBlockScalar(style);
/*  827 */     this.tokens.add(tok);
/*      */   }
/*      */ 
/*      */   
/*  831 */   private void fetchSingle() { fetchFlowScalar('\''); }
/*      */ 
/*      */ 
/*      */   
/*  835 */   private void fetchDouble() { fetchFlowScalar('"'); }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fetchFlowScalar(char style) {
/*  840 */     savePossibleSimpleKey();
/*      */ 
/*      */     
/*  843 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  846 */     Token tok = scanFlowScalar(style);
/*  847 */     this.tokens.add(tok);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fetchPlain() {
/*  852 */     savePossibleSimpleKey();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  857 */     this.allowSimpleKey = false;
/*      */ 
/*      */     
/*  860 */     Token tok = scanPlain();
/*  861 */     this.tokens.add(tok);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  869 */   private boolean checkDirective() { return (this.reader.getColumn() == 0); }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkDocumentStart() {
/*  874 */     if (this.reader.getColumn() == 0 && 
/*  875 */       "---".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) {
/*  876 */       return true;
/*      */     }
/*      */     
/*  879 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean checkDocumentEnd() {
/*  884 */     if (this.reader.getColumn() == 0 && 
/*  885 */       "...".equals(this.reader.prefix(3)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))) {
/*  886 */       return true;
/*      */     }
/*      */     
/*  889 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  894 */   private boolean checkBlockEntry() { return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1)); }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkKey() {
/*  899 */     if (this.flowLevel != 0) {
/*  900 */       return true;
/*      */     }
/*      */     
/*  903 */     return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkValue() {
/*  909 */     if (this.flowLevel != 0) {
/*  910 */       return true;
/*      */     }
/*      */     
/*  913 */     return Constant.NULL_BL_T_LINEBR.has(this.reader.peek(1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkPlain() {
/*  934 */     char ch = this.reader.peek();
/*  935 */     return (Constant.NULL_BL_T_LINEBR.hasNo(ch, "-?:,[]{}#&*!|>'\"%@`") || (Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(1)) && (ch == '-' || (this.flowLevel == 0 && "?:".indexOf(ch) != -1))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void scanToNextToken() {
/*  964 */     if (this.reader.getIndex() == 0 && this.reader.peek() == '﻿') {
/*  965 */       this.reader.forward();
/*      */     }
/*  967 */     boolean found = false;
/*  968 */     while (!found) {
/*  969 */       while (this.reader.peek() == ' ') {
/*  970 */         this.reader.forward();
/*      */       }
/*  972 */       if (this.reader.peek() == '#') {
/*  973 */         while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek())) {
/*  974 */           this.reader.forward();
/*      */         }
/*      */       }
/*  977 */       if (scanLineBreak().length() != 0) {
/*  978 */         if (this.flowLevel == 0)
/*  979 */           this.allowSimpleKey = true; 
/*      */         continue;
/*      */       } 
/*  982 */       found = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Token scanDirective() {
/*  990 */     Mark endMark, startMark = this.reader.getMark();
/*      */     
/*  992 */     this.reader.forward();
/*  993 */     String name = scanDirectiveName(startMark);
/*  994 */     List<?> value = null;
/*  995 */     if ("YAML".equals(name)) {
/*  996 */       value = scanYamlDirectiveValue(startMark);
/*  997 */       endMark = this.reader.getMark();
/*  998 */     } else if ("TAG".equals(name)) {
/*  999 */       value = scanTagDirectiveValue(startMark);
/* 1000 */       endMark = this.reader.getMark();
/*      */     } else {
/* 1002 */       endMark = this.reader.getMark();
/* 1003 */       while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek())) {
/* 1004 */         this.reader.forward();
/*      */       }
/*      */     } 
/* 1007 */     scanDirectiveIgnoredLine(startMark);
/* 1008 */     return new DirectiveToken(name, value, startMark, endMark);
/*      */   }
/*      */ 
/*      */   
/*      */   private String scanDirectiveName(Mark startMark) {
/* 1013 */     int length = 0;
/* 1014 */     char ch = this.reader.peek(length);
/* 1015 */     while ("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_".indexOf(ch) != -1) {
/* 1016 */       length++;
/* 1017 */       ch = this.reader.peek(length);
/*      */     } 
/* 1019 */     if (length == 0) {
/* 1020 */       throw new ScannerException("while scanning a directive", startMark, "expected alphabetic or numeric character, but found " + ch + "(" + ch + ")", this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1024 */     String value = this.reader.prefix(length);
/* 1025 */     this.reader.forward(length);
/* 1026 */     ch = this.reader.peek();
/* 1027 */     if (Constant.NULL_BL_LINEBR.hasNo(ch)) {
/* 1028 */       throw new ScannerException("while scanning a directive", startMark, "expected alphabetic or numeric character, but found " + ch + "(" + ch + ")", this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1032 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   private List<Integer> scanYamlDirectiveValue(Mark startMark) {
/* 1037 */     while (this.reader.peek() == ' ') {
/* 1038 */       this.reader.forward();
/*      */     }
/* 1040 */     Integer major = scanYamlDirectiveNumber(startMark);
/* 1041 */     if (this.reader.peek() != '.') {
/* 1042 */       throw new ScannerException("while scanning a directive", startMark, "expected a digit or '.', but found " + this.reader.peek() + "(" + this.reader.peek() + ")", this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1046 */     this.reader.forward();
/* 1047 */     Integer minor = scanYamlDirectiveNumber(startMark);
/* 1048 */     if (Constant.NULL_BL_LINEBR.hasNo(this.reader.peek())) {
/* 1049 */       throw new ScannerException("while scanning a directive", startMark, "expected a digit or ' ', but found " + this.reader.peek() + "(" + this.reader.peek() + ")", this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1053 */     List<Integer> result = new ArrayList<Integer>(2);
/* 1054 */     result.add(major);
/* 1055 */     result.add(minor);
/* 1056 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private Integer scanYamlDirectiveNumber(Mark startMark) {
/* 1061 */     char ch = this.reader.peek();
/* 1062 */     if (!Character.isDigit(ch)) {
/* 1063 */       throw new ScannerException("while scanning a directive", startMark, "expected a digit, but found " + ch + "(" + ch + ")", this.reader.getMark());
/*      */     }
/*      */     
/* 1066 */     int length = 0;
/* 1067 */     while (Character.isDigit(this.reader.peek(length))) {
/* 1068 */       length++;
/*      */     }
/* 1070 */     Integer value = new Integer(this.reader.prefix(length));
/* 1071 */     this.reader.forward(length);
/* 1072 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   private List<String> scanTagDirectiveValue(Mark startMark) {
/* 1077 */     while (this.reader.peek() == ' ') {
/* 1078 */       this.reader.forward();
/*      */     }
/* 1080 */     String handle = scanTagDirectiveHandle(startMark);
/* 1081 */     while (this.reader.peek() == ' ') {
/* 1082 */       this.reader.forward();
/*      */     }
/* 1084 */     String prefix = scanTagDirectivePrefix(startMark);
/* 1085 */     List<String> result = new ArrayList<String>(2);
/* 1086 */     result.add(handle);
/* 1087 */     result.add(prefix);
/* 1088 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private String scanTagDirectiveHandle(Mark startMark) {
/* 1093 */     String value = scanTagHandle("directive", startMark);
/* 1094 */     char ch = this.reader.peek();
/* 1095 */     if (ch != ' ') {
/* 1096 */       throw new ScannerException("while scanning a directive", startMark, "expected ' ', but found " + this.reader.peek() + "(" + ch + ")", this.reader.getMark());
/*      */     }
/*      */     
/* 1099 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   private String scanTagDirectivePrefix(Mark startMark) {
/* 1104 */     String value = scanTagUri("directive", startMark);
/* 1105 */     if (Constant.NULL_BL_LINEBR.hasNo(this.reader.peek())) {
/* 1106 */       throw new ScannerException("while scanning a directive", startMark, "expected ' ', but found " + this.reader.peek() + "(" + this.reader.peek() + ")", this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1110 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   private String scanDirectiveIgnoredLine(Mark startMark) {
/* 1115 */     while (this.reader.peek() == ' ') {
/* 1116 */       this.reader.forward();
/*      */     }
/* 1118 */     if (this.reader.peek() == '#') {
/* 1119 */       while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek())) {
/* 1120 */         this.reader.forward();
/*      */       }
/*      */     }
/* 1123 */     char ch = this.reader.peek();
/* 1124 */     if (Constant.NULL_OR_LINEBR.hasNo(ch)) {
/* 1125 */       throw new ScannerException("while scanning a directive", startMark, "expected a comment or a line break, but found " + ch + "(" + ch + ")", this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1129 */     return scanLineBreak();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Token scanAnchor(boolean isAnchor) {
/*      */     AliasToken aliasToken;
/* 1145 */     Mark startMark = this.reader.getMark();
/* 1146 */     char indicator = this.reader.peek();
/* 1147 */     String name = (indicator == '*') ? "alias" : "anchor";
/* 1148 */     this.reader.forward();
/* 1149 */     int length = 0;
/* 1150 */     char ch = this.reader.peek(length);
/* 1151 */     while ("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_".indexOf(ch) != -1) {
/* 1152 */       length++;
/* 1153 */       ch = this.reader.peek(length);
/*      */     } 
/* 1155 */     if (length == 0) {
/* 1156 */       throw new ScannerException("while scanning an " + name, startMark, "expected alphabetic or numeric character, but found but found " + ch, this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1160 */     String value = this.reader.prefix(length);
/* 1161 */     this.reader.forward(length);
/* 1162 */     ch = this.reader.peek();
/* 1163 */     if (Constant.NULL_BL_T_LINEBR.hasNo(ch, "?:,]}%@`")) {
/* 1164 */       throw new ScannerException("while scanning an " + name, startMark, "expected alphabetic or numeric character, but found " + ch + "(" + this.reader.peek() + ")", this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1168 */     Mark endMark = this.reader.getMark();
/*      */     
/* 1170 */     if (isAnchor) {
/* 1171 */       aliasToken = new AnchorToken(value, startMark, endMark);
/*      */     } else {
/* 1173 */       aliasToken = new AliasToken(value, startMark, endMark);
/*      */     } 
/* 1175 */     return aliasToken;
/*      */   }
/*      */ 
/*      */   
/*      */   private Token scanTag() {
/* 1180 */     Mark startMark = this.reader.getMark();
/* 1181 */     char ch = this.reader.peek(1);
/* 1182 */     String handle = null;
/* 1183 */     String suffix = null;
/* 1184 */     if (ch == '<') {
/* 1185 */       this.reader.forward(2);
/* 1186 */       suffix = scanTagUri("tag", startMark);
/* 1187 */       if (this.reader.peek() != '>') {
/* 1188 */         throw new ScannerException("while scanning a tag", startMark, "expected '>', but found '" + this.reader.peek() + "' (" + this.reader.peek() + ")", this.reader.getMark());
/*      */       }
/*      */ 
/*      */       
/* 1192 */       this.reader.forward();
/* 1193 */     } else if (Constant.NULL_BL_T_LINEBR.has(ch)) {
/* 1194 */       suffix = "!";
/* 1195 */       this.reader.forward();
/*      */     } else {
/* 1197 */       int length = 1;
/* 1198 */       boolean useHandle = false;
/* 1199 */       while (Constant.NULL_BL_LINEBR.hasNo(ch)) {
/* 1200 */         if (ch == '!') {
/* 1201 */           useHandle = true;
/*      */           break;
/*      */         } 
/* 1204 */         length++;
/* 1205 */         ch = this.reader.peek(length);
/*      */       } 
/* 1207 */       handle = "!";
/* 1208 */       if (useHandle) {
/* 1209 */         handle = scanTagHandle("tag", startMark);
/*      */       } else {
/* 1211 */         handle = "!";
/* 1212 */         this.reader.forward();
/*      */       } 
/* 1214 */       suffix = scanTagUri("tag", startMark);
/*      */     } 
/* 1216 */     ch = this.reader.peek();
/* 1217 */     if (Constant.NULL_BL_LINEBR.hasNo(ch)) {
/* 1218 */       throw new ScannerException("while scanning a tag", startMark, "expected ' ', but found '" + ch + "' (" + ch + ")", this.reader.getMark());
/*      */     }
/*      */     
/* 1221 */     TagTuple value = new TagTuple(handle, suffix);
/* 1222 */     Mark endMark = this.reader.getMark();
/* 1223 */     return new TagToken(value, startMark, endMark);
/*      */   }
/*      */   
/*      */   private Token scanBlockScalar(char style) {
/*      */     Mark mark;
/*      */     boolean folded;
/* 1229 */     if (style == '>') {
/* 1230 */       folded = true;
/*      */     } else {
/* 1232 */       folded = false;
/*      */     } 
/* 1234 */     StringBuilder chunks = new StringBuilder();
/* 1235 */     Mark startMark = this.reader.getMark();
/*      */     
/* 1237 */     this.reader.forward();
/* 1238 */     Chomping chompi = scanBlockScalarIndicators(startMark);
/* 1239 */     int increment = chompi.getIncrement();
/* 1240 */     scanBlockScalarIgnoredLine(startMark);
/*      */ 
/*      */     
/* 1243 */     int minIndent = this.indent + 1;
/* 1244 */     if (minIndent < 1) {
/* 1245 */       minIndent = 1;
/*      */     }
/* 1247 */     String breaks = null;
/* 1248 */     int maxIndent = 0;
/* 1249 */     int indent = 0;
/*      */     
/* 1251 */     if (increment == -1) {
/* 1252 */       Object[] brme = scanBlockScalarIndentation();
/* 1253 */       breaks = (String)brme[0];
/* 1254 */       maxIndent = ((Integer)brme[1]).intValue();
/* 1255 */       mark = (Mark)brme[2];
/* 1256 */       indent = Math.max(minIndent, maxIndent);
/*      */     } else {
/* 1258 */       indent = minIndent + increment - 1;
/* 1259 */       Object[] brme = scanBlockScalarBreaks(indent);
/* 1260 */       breaks = (String)brme[0];
/* 1261 */       mark = (Mark)brme[1];
/*      */     } 
/*      */     
/* 1264 */     String lineBreak = "";
/*      */ 
/*      */     
/* 1267 */     while (this.reader.getColumn() == indent && this.reader.peek() != '\000') {
/* 1268 */       chunks.append(breaks);
/* 1269 */       boolean leadingNonSpace = (" \t".indexOf(this.reader.peek()) == -1);
/* 1270 */       int length = 0;
/* 1271 */       while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek(length))) {
/* 1272 */         length++;
/*      */       }
/* 1274 */       chunks.append(this.reader.prefix(length));
/* 1275 */       this.reader.forward(length);
/* 1276 */       lineBreak = scanLineBreak();
/* 1277 */       Object[] brme = scanBlockScalarBreaks(indent);
/* 1278 */       breaks = (String)brme[0];
/* 1279 */       mark = (Mark)brme[1];
/* 1280 */       if (this.reader.getColumn() == indent && this.reader.peek() != '\000') {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1285 */         if (folded && "\n".equals(lineBreak) && leadingNonSpace && " \t".indexOf(this.reader.peek()) == -1) {
/*      */           
/* 1287 */           if (breaks.length() == 0)
/* 1288 */             chunks.append(" "); 
/*      */           continue;
/*      */         } 
/* 1291 */         chunks.append(lineBreak);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1300 */     if (chompi.chompTailIsNotFalse()) {
/* 1301 */       chunks.append(lineBreak);
/*      */     }
/* 1303 */     if (chompi.chompTailIsTrue()) {
/* 1304 */       chunks.append(breaks);
/*      */     }
/*      */     
/* 1307 */     return new ScalarToken(chunks.toString(), false, startMark, mark, style);
/*      */   }
/*      */ 
/*      */   
/*      */   private Chomping scanBlockScalarIndicators(Mark startMark) {
/* 1312 */     Boolean chomping = null;
/* 1313 */     int increment = -1;
/* 1314 */     char ch = this.reader.peek();
/* 1315 */     if (ch == '-' || ch == '+') {
/* 1316 */       if (ch == '+') {
/* 1317 */         chomping = Boolean.TRUE;
/*      */       } else {
/* 1319 */         chomping = Boolean.FALSE;
/*      */       } 
/* 1321 */       this.reader.forward();
/* 1322 */       ch = this.reader.peek();
/* 1323 */       if (Character.isDigit(ch)) {
/* 1324 */         increment = Integer.parseInt(String.valueOf(ch));
/* 1325 */         if (increment == 0) {
/* 1326 */           throw new ScannerException("while scanning a block scalar", startMark, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark());
/*      */         }
/*      */ 
/*      */         
/* 1330 */         this.reader.forward();
/*      */       } 
/* 1332 */     } else if (Character.isDigit(ch)) {
/* 1333 */       increment = Integer.parseInt(String.valueOf(ch));
/* 1334 */       if (increment == 0) {
/* 1335 */         throw new ScannerException("while scanning a block scalar", startMark, "expected indentation indicator in the range 1-9, but found 0", this.reader.getMark());
/*      */       }
/*      */ 
/*      */       
/* 1339 */       this.reader.forward();
/* 1340 */       ch = this.reader.peek();
/* 1341 */       if (ch == '-' || ch == '+') {
/* 1342 */         if (ch == '+') {
/* 1343 */           chomping = Boolean.TRUE;
/*      */         } else {
/* 1345 */           chomping = Boolean.FALSE;
/*      */         } 
/* 1347 */         this.reader.forward();
/*      */       } 
/*      */     } 
/* 1350 */     ch = this.reader.peek();
/* 1351 */     if (Constant.NULL_BL_LINEBR.hasNo(ch)) {
/* 1352 */       throw new ScannerException("while scanning a block scalar", startMark, "expected chomping or indentation indicators, but found " + ch, this.reader.getMark());
/*      */     }
/*      */ 
/*      */     
/* 1356 */     return new Chomping(chomping, increment);
/*      */   }
/*      */ 
/*      */   
/*      */   private String scanBlockScalarIgnoredLine(Mark startMark) {
/* 1361 */     while (this.reader.peek() == ' ') {
/* 1362 */       this.reader.forward();
/*      */     }
/* 1364 */     if (this.reader.peek() == '#') {
/* 1365 */       while (Constant.NULL_OR_LINEBR.hasNo(this.reader.peek())) {
/* 1366 */         this.reader.forward();
/*      */       }
/*      */     }
/* 1369 */     char ch = this.reader.peek();
/* 1370 */     if (Constant.NULL_OR_LINEBR.hasNo(ch)) {
/* 1371 */       throw new ScannerException("while scanning a block scalar", startMark, "expected a comment or a line break, but found " + ch, this.reader.getMark());
/*      */     }
/*      */     
/* 1374 */     return scanLineBreak();
/*      */   }
/*      */ 
/*      */   
/*      */   private Object[] scanBlockScalarIndentation() {
/* 1379 */     StringBuilder chunks = new StringBuilder();
/* 1380 */     int maxIndent = 0;
/* 1381 */     Mark endMark = this.reader.getMark();
/* 1382 */     while (Constant.LINEBR.has(this.reader.peek(), " \r")) {
/* 1383 */       if (this.reader.peek() != ' ') {
/* 1384 */         chunks.append(scanLineBreak());
/* 1385 */         endMark = this.reader.getMark(); continue;
/*      */       } 
/* 1387 */       this.reader.forward();
/* 1388 */       if (this.reader.getColumn() > maxIndent) {
/* 1389 */         maxIndent = this.reader.getColumn();
/*      */       }
/*      */     } 
/*      */     
/* 1393 */     return new Object[] { chunks.toString(), Integer.valueOf(maxIndent), endMark };
/*      */   }
/*      */ 
/*      */   
/*      */   private Object[] scanBlockScalarBreaks(int indent) {
/* 1398 */     StringBuilder chunks = new StringBuilder();
/* 1399 */     Mark endMark = this.reader.getMark();
/* 1400 */     while (this.reader.getColumn() < indent && this.reader.peek() == ' ') {
/* 1401 */       this.reader.forward();
/*      */     }
/* 1403 */     while (Constant.FULL_LINEBR.has(this.reader.peek())) {
/* 1404 */       chunks.append(scanLineBreak());
/* 1405 */       endMark = this.reader.getMark();
/* 1406 */       while (this.reader.getColumn() < indent && this.reader.peek() == ' ') {
/* 1407 */         this.reader.forward();
/*      */       }
/*      */     } 
/* 1410 */     return new Object[] { chunks.toString(), endMark };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Token scanFlowScalar(char style) {
/*      */     boolean _double;
/* 1425 */     if (style == '"') {
/* 1426 */       _double = true;
/*      */     } else {
/* 1428 */       _double = false;
/*      */     } 
/* 1430 */     StringBuilder chunks = new StringBuilder();
/* 1431 */     Mark startMark = this.reader.getMark();
/* 1432 */     char quote = this.reader.peek();
/* 1433 */     this.reader.forward();
/* 1434 */     chunks.append(scanFlowScalarNonSpaces(_double, startMark));
/* 1435 */     while (this.reader.peek() != quote) {
/* 1436 */       chunks.append(scanFlowScalarSpaces(startMark));
/* 1437 */       chunks.append(scanFlowScalarNonSpaces(_double, startMark));
/*      */     } 
/* 1439 */     this.reader.forward();
/* 1440 */     Mark endMark = this.reader.getMark();
/* 1441 */     return new ScalarToken(chunks.toString(), false, startMark, endMark, style);
/*      */   }
/*      */ 
/*      */   
/*      */   private String scanFlowScalarNonSpaces(boolean _double, Mark startMark) {
/* 1446 */     StringBuilder chunks = new StringBuilder();
/*      */     while (true) {
/* 1448 */       int length = 0;
/* 1449 */       while (Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(length), "'\"\\")) {
/* 1450 */         length++;
/*      */       }
/* 1452 */       if (length != 0) {
/* 1453 */         chunks.append(this.reader.prefix(length));
/* 1454 */         this.reader.forward(length);
/*      */       } 
/* 1456 */       char ch = this.reader.peek();
/* 1457 */       if (!_double && ch == '\'' && this.reader.peek(1) == '\'') {
/* 1458 */         chunks.append("'");
/* 1459 */         this.reader.forward(2); continue;
/* 1460 */       }  if ((_double && ch == '\'') || (!_double && "\"\\".indexOf(ch) != -1)) {
/* 1461 */         chunks.append(ch);
/* 1462 */         this.reader.forward(); continue;
/* 1463 */       }  if (_double && ch == '\\') {
/* 1464 */         this.reader.forward();
/* 1465 */         ch = this.reader.peek();
/* 1466 */         if (ESCAPE_REPLACEMENTS.containsKey(new Character(ch))) {
/* 1467 */           chunks.append((String)ESCAPE_REPLACEMENTS.get(new Character(ch)));
/* 1468 */           this.reader.forward(); continue;
/* 1469 */         }  if (ESCAPE_CODES.containsKey(new Character(ch))) {
/* 1470 */           length = ((Integer)ESCAPE_CODES.get(new Character(ch))).intValue();
/* 1471 */           this.reader.forward();
/* 1472 */           String hex = this.reader.prefix(length);
/* 1473 */           if (NOT_HEXA.matcher(hex).find()) {
/* 1474 */             throw new ScannerException("while scanning a double-quoted scalar", startMark, "expected escape sequence of " + length + " hexadecimal numbers, but found: " + hex, this.reader.getMark());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1479 */           char unicode = (char)Integer.parseInt(hex, 16);
/* 1480 */           chunks.append(unicode);
/* 1481 */           this.reader.forward(length); continue;
/* 1482 */         }  if (Constant.FULL_LINEBR.has(ch)) {
/* 1483 */           scanLineBreak();
/* 1484 */           chunks.append(scanFlowScalarBreaks(startMark)); continue;
/*      */         } 
/* 1486 */         throw new ScannerException("while scanning a double-quoted scalar", startMark, "found unknown escape character " + ch + "(" + ch + ")", this.reader.getMark());
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/* 1491 */     return chunks.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanFlowScalarSpaces(Mark startMark) {
/* 1498 */     StringBuilder chunks = new StringBuilder();
/* 1499 */     int length = 0;
/* 1500 */     while (" \t".indexOf(this.reader.peek(length)) != -1) {
/* 1501 */       length++;
/*      */     }
/* 1503 */     String whitespaces = this.reader.prefix(length);
/* 1504 */     this.reader.forward(length);
/* 1505 */     char ch = this.reader.peek();
/* 1506 */     if (ch == '\000') {
/* 1507 */       throw new ScannerException("while scanning a quoted scalar", startMark, "found unexpected end of stream", this.reader.getMark());
/*      */     }
/* 1509 */     if (Constant.FULL_LINEBR.has(ch)) {
/* 1510 */       String lineBreak = scanLineBreak();
/* 1511 */       String breaks = scanFlowScalarBreaks(startMark);
/* 1512 */       if (!"\n".equals(lineBreak)) {
/* 1513 */         chunks.append(lineBreak);
/* 1514 */       } else if (breaks.length() == 0) {
/* 1515 */         chunks.append(" ");
/*      */       } 
/* 1517 */       chunks.append(breaks);
/*      */     } else {
/* 1519 */       chunks.append(whitespaces);
/*      */     } 
/* 1521 */     return chunks.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private String scanFlowScalarBreaks(Mark startMark) {
/* 1526 */     StringBuilder chunks = new StringBuilder();
/*      */ 
/*      */     
/*      */     while (true) {
/* 1530 */       String prefix = this.reader.prefix(3);
/* 1531 */       if (("---".equals(prefix) || "...".equals(prefix)) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3)))
/*      */       {
/* 1533 */         throw new ScannerException("while scanning a quoted scalar", startMark, "found unexpected document separator", this.reader.getMark());
/*      */       }
/*      */       
/* 1536 */       while (" \t".indexOf(this.reader.peek()) != -1) {
/* 1537 */         this.reader.forward();
/*      */       }
/* 1539 */       if (Constant.FULL_LINEBR.has(this.reader.peek())) {
/* 1540 */         chunks.append(scanLineBreak()); continue;
/*      */       }  break;
/* 1542 */     }  return chunks.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Token scanPlain() {
/* 1557 */     StringBuilder chunks = new StringBuilder();
/* 1558 */     Mark startMark = this.reader.getMark();
/* 1559 */     Mark endMark = startMark;
/* 1560 */     int indent = this.indent + 1;
/* 1561 */     String spaces = "";
/*      */     do {
/*      */       char ch;
/* 1564 */       int length = 0;
/* 1565 */       if (this.reader.peek() == '#') {
/*      */         break;
/*      */       }
/*      */       while (true) {
/* 1569 */         ch = this.reader.peek(length);
/* 1570 */         if (Constant.NULL_BL_T_LINEBR.has(ch) || (this.flowLevel == 0 && ch == ':' && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(length + 1))) || (this.flowLevel != 0 && ",:?[]{}".indexOf(ch) != -1)) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1576 */         length++;
/*      */       } 
/*      */       
/* 1579 */       if (this.flowLevel != 0 && ch == ':' && Constant.NULL_BL_T_LINEBR.hasNo(this.reader.peek(length + 1), ",[]{}")) {
/*      */         
/* 1581 */         this.reader.forward(length);
/* 1582 */         throw new ScannerException("while scanning a plain scalar", startMark, "found unexpected ':'", this.reader.getMark(), "Please check http://pyyaml.org/wiki/YAMLColonInFlowContext for details.");
/*      */       } 
/*      */ 
/*      */       
/* 1586 */       if (length == 0) {
/*      */         break;
/*      */       }
/* 1589 */       this.allowSimpleKey = false;
/* 1590 */       chunks.append(spaces);
/* 1591 */       chunks.append(this.reader.prefix(length));
/* 1592 */       this.reader.forward(length);
/* 1593 */       endMark = this.reader.getMark();
/* 1594 */       spaces = scanPlainSpaces();
/* 1595 */     } while (!"".equals(spaces) && this.reader.peek() != '#' && (this.flowLevel != 0 || this.reader.getColumn() >= indent));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1600 */     return new ScalarToken(chunks.toString(), startMark, endMark, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanPlainSpaces() {
/* 1611 */     StringBuilder chunks = new StringBuilder();
/* 1612 */     int length = 0;
/* 1613 */     while (this.reader.peek(length) == ' ') {
/* 1614 */       length++;
/*      */     }
/* 1616 */     String whitespaces = this.reader.prefix(length);
/* 1617 */     this.reader.forward(length);
/* 1618 */     char ch = this.reader.peek();
/* 1619 */     if (Constant.FULL_LINEBR.has(ch)) {
/* 1620 */       String lineBreak = scanLineBreak();
/* 1621 */       this.allowSimpleKey = true;
/* 1622 */       String prefix = this.reader.prefix(3);
/* 1623 */       if ("---".equals(prefix) || ("...".equals(prefix) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))))
/*      */       {
/* 1625 */         return "";
/*      */       }
/* 1627 */       StringBuilder breaks = new StringBuilder();
/* 1628 */       while (Constant.LINEBR.has(this.reader.peek(), " \r")) {
/* 1629 */         if (this.reader.peek() == ' ') {
/* 1630 */           this.reader.forward(); continue;
/*      */         } 
/* 1632 */         breaks.append(scanLineBreak());
/* 1633 */         prefix = this.reader.prefix(3);
/* 1634 */         if ("---".equals(prefix) || ("...".equals(prefix) && Constant.NULL_BL_T_LINEBR.has(this.reader.peek(3))))
/*      */         {
/* 1636 */           return "";
/*      */         }
/*      */       } 
/*      */       
/* 1640 */       if (!"\n".equals(lineBreak)) {
/* 1641 */         chunks.append(lineBreak);
/* 1642 */       } else if (breaks == null || breaks.toString().equals("")) {
/* 1643 */         chunks.append(" ");
/*      */       } 
/* 1645 */       chunks.append(breaks);
/*      */     } else {
/* 1647 */       chunks.append(whitespaces);
/*      */     } 
/* 1649 */     return chunks.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanTagHandle(String name, Mark startMark) {
/* 1660 */     char ch = this.reader.peek();
/* 1661 */     if (ch != '!') {
/* 1662 */       throw new ScannerException("while scanning a " + name, startMark, "expected '!', but found " + ch + "(" + ch + ")", this.reader.getMark());
/*      */     }
/*      */     
/* 1665 */     int length = 1;
/* 1666 */     ch = this.reader.peek(length);
/* 1667 */     if (ch != ' ') {
/* 1668 */       while ("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_".indexOf(ch) != -1) {
/* 1669 */         length++;
/* 1670 */         ch = this.reader.peek(length);
/*      */       } 
/* 1672 */       if (ch != '!') {
/* 1673 */         this.reader.forward(length);
/* 1674 */         throw new ScannerException("while scanning a " + name, startMark, "expected '!', but found " + ch + "(" + ch + ")", this.reader.getMark());
/*      */       } 
/*      */       
/* 1677 */       length++;
/*      */     } 
/* 1679 */     String value = this.reader.prefix(length);
/* 1680 */     this.reader.forward(length);
/* 1681 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanTagUri(String name, Mark startMark) {
/* 1687 */     StringBuilder chunks = new StringBuilder();
/* 1688 */     int length = 0;
/* 1689 */     char ch = this.reader.peek(length);
/* 1690 */     while (Constant.URI_CHARS.has(ch)) {
/* 1691 */       if (ch == '%') {
/* 1692 */         chunks.append(this.reader.prefix(length));
/* 1693 */         this.reader.forward(length);
/* 1694 */         length = 0;
/* 1695 */         chunks.append(scanUriEscapes(name, startMark));
/*      */       } else {
/* 1697 */         length++;
/*      */       } 
/* 1699 */       ch = this.reader.peek(length);
/*      */     } 
/* 1701 */     if (length != 0) {
/* 1702 */       chunks.append(this.reader.prefix(length));
/* 1703 */       this.reader.forward(length);
/* 1704 */       length = 0;
/*      */     } 
/* 1706 */     if (chunks.length() == 0) {
/* 1707 */       throw new ScannerException("while scanning a " + name, startMark, "expected URI, but found " + ch + "(" + ch + ")", this.reader.getMark());
/*      */     }
/*      */     
/* 1710 */     return chunks.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanUriEscapes(String name, Mark startMark) {
/* 1718 */     Mark beginningMark = this.reader.getMark();
/* 1719 */     ByteBuffer buff = ByteBuffer.allocate(256);
/* 1720 */     while (this.reader.peek() == '%') {
/* 1721 */       this.reader.forward();
/*      */       try {
/* 1723 */         byte code = (byte)Integer.parseInt(this.reader.prefix(2), 16);
/* 1724 */         buff.put(code);
/* 1725 */       } catch (NumberFormatException nfe) {
/* 1726 */         throw new ScannerException("while scanning a " + name, startMark, "expected URI escape sequence of 2 hexadecimal numbers, but found " + this.reader.peek() + "(" + this.reader.peek() + ") and " + this.reader.peek(1) + "(" + this.reader.peek(1) + ")", this.reader.getMark());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1732 */       this.reader.forward(2);
/*      */     } 
/* 1734 */     buff.flip();
/*      */     try {
/* 1736 */       return UriEncoder.decode(buff);
/* 1737 */     } catch (CharacterCodingException e) {
/* 1738 */       throw new ScannerException("while scanning a " + name, startMark, "expected URI in UTF-8: " + e.getMessage(), beginningMark);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String scanLineBreak() {
/* 1750 */     char ch = this.reader.peek();
/* 1751 */     if ("\r\n".indexOf(ch) != -1) {
/* 1752 */       if ("\r\n".equals(this.reader.prefix(2))) {
/* 1753 */         this.reader.forward(2);
/*      */       } else {
/* 1755 */         this.reader.forward();
/*      */       } 
/* 1757 */       return "\n";
/* 1758 */     }  if ("  ".indexOf(ch) != -1) {
/* 1759 */       this.reader.forward();
/* 1760 */       return String.valueOf(ch);
/*      */     } 
/* 1762 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   private class Chomping
/*      */   {
/*      */     private final Boolean value;
/*      */     
/*      */     private final int increment;
/*      */     
/*      */     public Chomping(Boolean value, int increment) {
/* 1773 */       this.value = value;
/* 1774 */       this.increment = increment;
/*      */     }
/*      */ 
/*      */     
/* 1778 */     public boolean chompTailIsNotFalse() { return (this.value == null || this.value.booleanValue()); }
/*      */ 
/*      */ 
/*      */     
/* 1782 */     public boolean chompTailIsTrue() { return (this.value != null && this.value.booleanValue()); }
/*      */ 
/*      */ 
/*      */     
/* 1786 */     public int getIncrement() { return this.increment; }
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\scanner\ScannerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */