/*      */ package org.yaml.snakeyaml.emitter;public final class Emitter { public static final int MIN_INDENT = 1; public static final int MAX_INDENT = 10; private static final Map<String, String> DEFAULT_TAG_PREFIXES;
/*      */   private final Writer stream;
/*      */   private final ArrayStack<EmitterState> states;
/*      */   private EmitterState state;
/*      */   private final Queue<Event> events;
/*      */   private Event event;
/*      */   private final ArrayStack<Integer> indents;
/*      */   private Integer indent;
/*      */   private int flowLevel;
/*      */   private boolean rootContext;
/*      */   private boolean mappingContext;
/*      */   private boolean simpleKeyContext;
/*      */   private int column;
/*      */   private boolean whitespace;
/*      */   private boolean indention;
/*      */   private boolean openEnded;
/*      */   private Boolean canonical;
/*      */   private Boolean prettyFlow;
/*      */   private boolean allowUnicode;
/*      */   private int bestIndent;
/*      */   private int bestWidth;
/*      */   private String bestLineBreak;
/*      */   private Map<String, String> tagPrefixes;
/*      */   private String preparedAnchor;
/*      */   private String preparedTag;
/*      */   private ScalarAnalysis analysis;
/*      */   private Character style;
/*      */   private DumperOptions options;
/*      */   private static final Pattern HANDLE_FORMAT;
/*      */   private static final Pattern ANCHOR_FORMAT;
/*      */   
/*      */   public Emitter(Writer stream, DumperOptions opts) {
/*      */     this.stream = stream;
/*      */     this.states = new ArrayStack(100);
/*      */     this.state = new ExpectStreamStart(null);
/*      */     this.events = new ArrayBlockingQueue(100);
/*      */     this.event = null;
/*      */     this.indents = new ArrayStack(10);
/*      */     this.indent = null;
/*      */     this.flowLevel = 0;
/*      */     this.mappingContext = false;
/*      */     this.simpleKeyContext = false;
/*      */     this.column = 0;
/*      */     this.whitespace = true;
/*      */     this.indention = true;
/*      */     this.openEnded = false;
/*      */     this.canonical = Boolean.valueOf(opts.isCanonical());
/*      */     this.prettyFlow = Boolean.valueOf(opts.isPrettyFlow());
/*      */     this.allowUnicode = opts.isAllowUnicode();
/*      */     this.bestIndent = 2;
/*      */     if (opts.getIndent() > 1 && opts.getIndent() < 10)
/*      */       this.bestIndent = opts.getIndent(); 
/*      */     this.bestWidth = 80;
/*      */     if (opts.getWidth() > this.bestIndent * 2)
/*      */       this.bestWidth = opts.getWidth(); 
/*      */     this.bestLineBreak = opts.getLineBreak().getString();
/*      */     this.tagPrefixes = new LinkedHashMap();
/*      */     this.preparedAnchor = null;
/*      */     this.preparedTag = null;
/*      */     this.analysis = null;
/*      */     this.style = null;
/*      */     this.options = opts;
/*      */   }
/*      */   
/*   65 */   private static final Map<Character, String> ESCAPE_REPLACEMENTS = new HashMap(); public void emit(Event event) throws IOException { this.events.add(event); while (!needMoreEvents()) { this.event = (Event)this.events.poll(); this.state.expect(); this.event = null; }  } private boolean needMoreEvents() { if (this.events.isEmpty()) return true;  Event event = (Event)this.events.peek(); if (event instanceof DocumentStartEvent) return needEvents(1);  if (event instanceof SequenceStartEvent) return needEvents(2);  if (event instanceof MappingStartEvent) return needEvents(3);  return false; } private boolean needEvents(int count) { int level = 0; Iterator<Event> iter = this.events.iterator(); iter.next(); while (iter.hasNext()) { Event event = (Event)iter.next(); if (event instanceof DocumentStartEvent || event instanceof CollectionStartEvent) { level++; } else if (event instanceof DocumentEndEvent || event instanceof org.yaml.snakeyaml.events.CollectionEndEvent) { level--; } else if (event instanceof org.yaml.snakeyaml.events.StreamEndEvent) { level = -1; }  if (level < 0) return false;  }  return (this.events.size() < count + 1); } private void increaseIndent(boolean flow, boolean indentless) { this.indents.push(this.indent); if (this.indent == null) { if (flow) { this.indent = Integer.valueOf(this.bestIndent); } else { this.indent = Integer.valueOf(0); }  } else if (!indentless) { Emitter emitter = this; emitter.indent = Integer.valueOf(emitter.indent.intValue() + this.bestIndent); }  } private class ExpectStreamStart implements EmitterState {
/*      */     private ExpectStreamStart() {} public void expect() throws IOException { if (Emitter.this.event instanceof org.yaml.snakeyaml.events.StreamStartEvent) { Emitter.this.writeStreamStart(); Emitter.this.state = new Emitter.ExpectFirstDocumentStart(Emitter.this, null); } else { throw new EmitterException("expected StreamStartEvent, but got " + Emitter.this.event); }  } } private class ExpectNothing implements EmitterState {
/*      */     private ExpectNothing() {} public void expect() throws IOException { throw new EmitterException("expecting nothing, but got " + Emitter.this.event); } } private class ExpectFirstDocumentStart implements EmitterState {
/*      */     private ExpectFirstDocumentStart() {} public void expect() throws IOException { (new Emitter.ExpectDocumentStart(Emitter.this, true)).expect(); } } private class ExpectDocumentStart implements EmitterState {
/*      */     private boolean first; public ExpectDocumentStart(boolean first) { this.first = first; } public void expect() throws IOException { if (Emitter.this.event instanceof DocumentStartEvent) { DocumentStartEvent ev = (DocumentStartEvent)Emitter.this.event; if ((ev.getVersion() != null || ev.getTags() != null) && Emitter.this.openEnded) { Emitter.this.writeIndicator("...", true, false, false); Emitter.this.writeIndent(); }  if (ev.getVersion() != null) { String versionText = Emitter.this.prepareVersion(ev.getVersion()); Emitter.this.writeVersionDirective(versionText); }  Emitter.this.tagPrefixes = new LinkedHashMap(DEFAULT_TAG_PREFIXES); if (ev.getTags() != null) { Set<String> handles = new TreeSet<String>(ev.getTags().keySet()); for (String handle : handles) { String prefix = (String)ev.getTags().get(handle); Emitter.this.tagPrefixes.put(prefix, handle); String handleText = Emitter.this.prepareTagHandle(handle); String prefixText = Emitter.this.prepareTagPrefix(prefix); Emitter.this.writeTagDirective(handleText, prefixText); }  }  boolean implicit = (this.first && !ev.getExplicit() && !Emitter.this.canonical.booleanValue() && ev.getVersion() == null && ev.getTags() == null && !Emitter.this.checkEmptyDocument()); if (!implicit) { Emitter.this.writeIndent(); Emitter.this.writeIndicator("---", true, false, false); if (Emitter.this.canonical.booleanValue()) Emitter.this.writeIndent();  }  Emitter.this.state = new Emitter.ExpectDocumentRoot(Emitter.this, null); } else if (Emitter.this.event instanceof org.yaml.snakeyaml.events.StreamEndEvent) { Emitter.this.writeStreamEnd(); Emitter.this.state = new Emitter.ExpectNothing(Emitter.this, null); } else { throw new EmitterException("expected DocumentStartEvent, but got " + Emitter.this.event); }  } } private class ExpectDocumentEnd implements EmitterState {
/*   70 */     private ExpectDocumentEnd() {} public void expect() throws IOException { if (Emitter.this.event instanceof DocumentEndEvent) { Emitter.this.writeIndent(); if (((DocumentEndEvent)Emitter.this.event).getExplicit()) { Emitter.this.writeIndicator("...", true, false, false); Emitter.this.writeIndent(); }  Emitter.this.flushStream(); Emitter.this.state = new Emitter.ExpectDocumentStart(Emitter.this, false); } else { throw new EmitterException("expected DocumentEndEvent, but got " + Emitter.this.event); }  } } private class ExpectDocumentRoot implements EmitterState { private ExpectDocumentRoot() {} public void expect() throws IOException { Emitter.this.states.push(new Emitter.ExpectDocumentEnd(Emitter.this, null)); Emitter.this.expectNode(true, false, false, false); } } static  { ESCAPE_REPLACEMENTS.put(new Character(false), "0");
/*   71 */     ESCAPE_REPLACEMENTS.put(new Character(7), "a");
/*   72 */     ESCAPE_REPLACEMENTS.put(new Character(8), "b");
/*   73 */     ESCAPE_REPLACEMENTS.put(new Character(9), "t");
/*   74 */     ESCAPE_REPLACEMENTS.put(new Character(10), "n");
/*   75 */     ESCAPE_REPLACEMENTS.put(new Character(11), "v");
/*   76 */     ESCAPE_REPLACEMENTS.put(new Character(12), "f");
/*   77 */     ESCAPE_REPLACEMENTS.put(new Character(13), "r");
/*   78 */     ESCAPE_REPLACEMENTS.put(new Character(27), "e");
/*   79 */     ESCAPE_REPLACEMENTS.put(new Character(34), "\"");
/*   80 */     ESCAPE_REPLACEMENTS.put(new Character(92), "\\");
/*   81 */     ESCAPE_REPLACEMENTS.put(new Character(''), "N");
/*   82 */     ESCAPE_REPLACEMENTS.put(new Character(' '), "_");
/*   83 */     ESCAPE_REPLACEMENTS.put(new Character(' '), "L");
/*   84 */     ESCAPE_REPLACEMENTS.put(new Character(' '), "P");
/*      */ 
/*      */     
/*   87 */     DEFAULT_TAG_PREFIXES = new LinkedHashMap();
/*      */     
/*   89 */     DEFAULT_TAG_PREFIXES.put("!", "!");
/*   90 */     DEFAULT_TAG_PREFIXES.put("tag:yaml.org,2002:", "!!");
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
/*  832 */     HANDLE_FORMAT = Pattern.compile("^![-_\\w]*!$");
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
/*  902 */     ANCHOR_FORMAT = Pattern.compile("^[-_\\w]*$"); }
/*      */   private void expectNode(boolean root, boolean sequence, boolean mapping, boolean simpleKey) throws IOException { this.rootContext = root; this.mappingContext = mapping; this.simpleKeyContext = simpleKey; if (this.event instanceof org.yaml.snakeyaml.events.AliasEvent) { expectAlias(); } else if (this.event instanceof ScalarEvent || this.event instanceof CollectionStartEvent) { processAnchor("&"); processTag(); if (this.event instanceof ScalarEvent) { expectScalar(); } else if (this.event instanceof SequenceStartEvent) { if (this.flowLevel != 0 || this.canonical.booleanValue() || ((SequenceStartEvent)this.event).getFlowStyle().booleanValue() || checkEmptySequence()) { expectFlowSequence(); } else { expectBlockSequence(); }  } else if (this.flowLevel != 0 || this.canonical.booleanValue() || ((MappingStartEvent)this.event).getFlowStyle().booleanValue() || checkEmptyMapping()) { expectFlowMapping(); } else { expectBlockMapping(); }  } else { throw new EmitterException("expected NodeEvent, but got " + this.event); }  }
/*      */   private void expectAlias() throws IOException { if (((NodeEvent)this.event).getAnchor() == null) throw new EmitterException("anchor is not specified for alias");  processAnchor("*"); this.state = (EmitterState)this.states.pop(); } private void expectScalar() throws IOException { increaseIndent(true, false); processScalar(); this.indent = (Integer)this.indents.pop(); this.state = (EmitterState)this.states.pop(); } private void expectFlowSequence() throws IOException { writeIndicator("[", true, true, false); this.flowLevel++; increaseIndent(true, false); if (this.prettyFlow.booleanValue()) writeIndent();  this.state = new ExpectFirstFlowSequenceItem(null); } private class ExpectFirstFlowSequenceItem implements EmitterState {
/*  905 */     private ExpectFirstFlowSequenceItem() {} public void expect() throws IOException { if (Emitter.this.event instanceof org.yaml.snakeyaml.events.SequenceEndEvent) { Emitter.this.indent = (Integer)Emitter.this.indents.pop(); Emitter.this.flowLevel--; Emitter.this.writeIndicator("]", false, false, false); Emitter.this.state = (EmitterState)Emitter.this.states.pop(); } else { if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth || Emitter.this.prettyFlow.booleanValue()) Emitter.this.writeIndent();  Emitter.this.states.push(new Emitter.ExpectFlowSequenceItem(Emitter.this, null)); Emitter.this.expectNode(false, true, false, false); }  } } static String prepareAnchor(String anchor) { if (anchor == null || "".equals(anchor)) {
/*  906 */       throw new EmitterException("anchor must not be empty");
/*      */     }
/*  908 */     if (!ANCHOR_FORMAT.matcher(anchor).matches()) {
/*  909 */       throw new EmitterException("invalid character in the anchor: " + anchor);
/*      */     }
/*  911 */     return anchor; } private class ExpectFlowSequenceItem implements EmitterState {
/*      */     private ExpectFlowSequenceItem() {} public void expect() throws IOException { if (Emitter.this.event instanceof org.yaml.snakeyaml.events.SequenceEndEvent) { Emitter.this.indent = (Integer)Emitter.this.indents.pop(); Emitter.this.flowLevel--; if (Emitter.this.canonical.booleanValue()) { Emitter.this.writeIndicator(",", false, false, false); Emitter.this.writeIndent(); }  Emitter.this.writeIndicator("]", false, false, false); if (Emitter.this.prettyFlow.booleanValue()) Emitter.this.writeIndent();  Emitter.this.state = (EmitterState)Emitter.this.states.pop(); } else { Emitter.this.writeIndicator(",", false, false, false); if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth || Emitter.this.prettyFlow.booleanValue()) Emitter.this.writeIndent();  Emitter.this.states.push(new ExpectFlowSequenceItem(Emitter.this)); Emitter.this.expectNode(false, true, false, false); }  } } private void expectFlowMapping() throws IOException { writeIndicator("{", true, true, false); this.flowLevel++; increaseIndent(true, false); if (this.prettyFlow.booleanValue()) writeIndent();  this.state = new ExpectFirstFlowMappingKey(null); } private class ExpectFirstFlowMappingKey implements EmitterState {
/*      */     private ExpectFirstFlowMappingKey() {} public void expect() throws IOException { if (Emitter.this.event instanceof org.yaml.snakeyaml.events.MappingEndEvent) { Emitter.this.indent = (Integer)Emitter.this.indents.pop(); Emitter.this.flowLevel--; Emitter.this.writeIndicator("}", false, false, false); Emitter.this.state = (EmitterState)Emitter.this.states.pop(); } else { if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth || Emitter.this.prettyFlow.booleanValue()) Emitter.this.writeIndent();  if (!Emitter.this.canonical.booleanValue() && Emitter.this.checkSimpleKey()) { Emitter.this.states.push(new Emitter.ExpectFlowMappingSimpleValue(Emitter.this, null)); Emitter.this.expectNode(false, false, true, true); } else { Emitter.this.writeIndicator("?", true, false, false); Emitter.this.states.push(new Emitter.ExpectFlowMappingValue(Emitter.this, null)); Emitter.this.expectNode(false, false, true, false); }  }  } } private class ExpectFlowMappingKey implements EmitterState {
/*      */     private ExpectFlowMappingKey() {} public void expect() throws IOException { if (Emitter.this.event instanceof org.yaml.snakeyaml.events.MappingEndEvent) { Emitter.this.indent = (Integer)Emitter.this.indents.pop(); Emitter.this.flowLevel--; if (Emitter.this.canonical.booleanValue()) { Emitter.this.writeIndicator(",", false, false, false); Emitter.this.writeIndent(); }  if (Emitter.this.prettyFlow.booleanValue()) Emitter.this.writeIndent();  Emitter.this.writeIndicator("}", false, false, false); Emitter.this.state = (EmitterState)Emitter.this.states.pop(); } else { Emitter.this.writeIndicator(",", false, false, false); if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth || Emitter.this.prettyFlow.booleanValue()) Emitter.this.writeIndent();  if (!Emitter.this.canonical.booleanValue() && Emitter.this.checkSimpleKey()) { Emitter.this.states.push(new Emitter.ExpectFlowMappingSimpleValue(Emitter.this, null)); Emitter.this.expectNode(false, false, true, true); } else { Emitter.this.writeIndicator("?", true, false, false); Emitter.this.states.push(new Emitter.ExpectFlowMappingValue(Emitter.this, null)); Emitter.this.expectNode(false, false, true, false); }  }  } } private class ExpectFlowMappingSimpleValue implements EmitterState {
/*      */     private ExpectFlowMappingSimpleValue() {} public void expect() throws IOException { Emitter.this.writeIndicator(":", false, false, false); Emitter.this.states.push(new Emitter.ExpectFlowMappingKey(Emitter.this, null)); Emitter.this.expectNode(false, false, true, false); } } private class ExpectFlowMappingValue implements EmitterState {
/*  916 */     private ExpectFlowMappingValue() {} public void expect() throws IOException { if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth || Emitter.this.prettyFlow.booleanValue()) Emitter.this.writeIndent();  Emitter.this.writeIndicator(":", true, false, false); Emitter.this.states.push(new Emitter.ExpectFlowMappingKey(Emitter.this, null)); Emitter.this.expectNode(false, false, true, false); } } private void expectBlockSequence() throws IOException { boolean indentless = (this.mappingContext && !this.indention); increaseIndent(false, indentless); this.state = new ExpectFirstBlockSequenceItem(null); } private class ExpectFirstBlockSequenceItem implements EmitterState { private ExpectFirstBlockSequenceItem() {} public void expect() throws IOException { (new Emitter.ExpectBlockSequenceItem(Emitter.this, true)).expect(); } } private class ExpectBlockSequenceItem implements EmitterState { private boolean first; public ExpectBlockSequenceItem(boolean first) { this.first = first; } public void expect() throws IOException { if (!this.first && Emitter.this.event instanceof org.yaml.snakeyaml.events.SequenceEndEvent) { Emitter.this.indent = (Integer)Emitter.this.indents.pop(); Emitter.this.state = (EmitterState)Emitter.this.states.pop(); } else { Emitter.this.writeIndent(); Emitter.this.writeIndicator("-", true, false, true); Emitter.this.states.push(new ExpectBlockSequenceItem(Emitter.this, false)); Emitter.this.expectNode(false, true, false, false); }  } } private void expectBlockMapping() throws IOException { increaseIndent(false, false); this.state = new ExpectFirstBlockMappingKey(null); } private class ExpectFirstBlockMappingKey implements EmitterState { private ExpectFirstBlockMappingKey() {} public void expect() throws IOException { (new Emitter.ExpectBlockMappingKey(Emitter.this, true)).expect(); } } private class ExpectBlockMappingKey implements EmitterState { private boolean first; public ExpectBlockMappingKey(boolean first) { this.first = first; } public void expect() throws IOException { if (!this.first && Emitter.this.event instanceof org.yaml.snakeyaml.events.MappingEndEvent) { Emitter.this.indent = (Integer)Emitter.this.indents.pop(); Emitter.this.state = (EmitterState)Emitter.this.states.pop(); } else { Emitter.this.writeIndent(); if (Emitter.this.checkSimpleKey()) { Emitter.this.states.push(new Emitter.ExpectBlockMappingSimpleValue(Emitter.this, null)); Emitter.this.expectNode(false, false, true, true); } else { Emitter.this.writeIndicator("?", true, false, true); Emitter.this.states.push(new Emitter.ExpectBlockMappingValue(Emitter.this, null)); Emitter.this.expectNode(false, false, true, false); }  }  } } private class ExpectBlockMappingSimpleValue implements EmitterState { private ExpectBlockMappingSimpleValue() {} public void expect() throws IOException { Emitter.this.writeIndicator(":", false, false, false); Emitter.this.states.push(new Emitter.ExpectBlockMappingKey(Emitter.this, false)); Emitter.this.expectNode(false, false, true, false); } } private class ExpectBlockMappingValue implements EmitterState { private ExpectBlockMappingValue() {} public void expect() throws IOException { Emitter.this.writeIndent(); Emitter.this.writeIndicator(":", true, false, true); Emitter.this.states.push(new Emitter.ExpectBlockMappingKey(Emitter.this, false)); Emitter.this.expectNode(false, false, true, false); } } private boolean checkEmptySequence() { return (this.event instanceof SequenceStartEvent && !this.events.isEmpty() && this.events.peek() instanceof org.yaml.snakeyaml.events.SequenceEndEvent); } private ScalarAnalysis analyzeScalar(String scalar) { if (scalar == null || "".equals(scalar)) {
/*  917 */       return new ScalarAnalysis(scalar, true, false, false, true, true, true, false);
/*      */     }
/*      */     
/*  920 */     boolean blockIndicators = false;
/*  921 */     boolean flowIndicators = false;
/*  922 */     boolean lineBreaks = false;
/*  923 */     boolean specialCharacters = false;
/*      */ 
/*      */     
/*  926 */     boolean leadingSpace = false;
/*  927 */     boolean leadingBreak = false;
/*  928 */     boolean trailingSpace = false;
/*  929 */     boolean trailingBreak = false;
/*  930 */     boolean breakSpace = false;
/*  931 */     boolean spaceBreak = false;
/*      */ 
/*      */     
/*  934 */     if (scalar.startsWith("---") || scalar.startsWith("...")) {
/*  935 */       blockIndicators = true;
/*  936 */       flowIndicators = true;
/*      */     } 
/*      */     
/*  939 */     boolean preceededByWhitespace = true;
/*  940 */     boolean followedByWhitespace = (scalar.length() == 1 || Constant.NULL_BL_T_LINEBR.has(scalar.charAt(1)));
/*      */ 
/*      */     
/*  943 */     boolean previousSpace = false;
/*      */ 
/*      */     
/*  946 */     boolean previousBreak = false;
/*      */     
/*  948 */     int index = 0;
/*      */     
/*  950 */     while (index < scalar.length()) {
/*  951 */       char ch = scalar.charAt(index);
/*      */       
/*  953 */       if (index == 0) {
/*      */         
/*  955 */         if ("#,[]{}&*!|>'\"%@`".indexOf(ch) != -1) {
/*  956 */           flowIndicators = true;
/*  957 */           blockIndicators = true;
/*      */         } 
/*  959 */         if (ch == '?' || ch == ':') {
/*  960 */           flowIndicators = true;
/*  961 */           if (followedByWhitespace) {
/*  962 */             blockIndicators = true;
/*      */           }
/*      */         } 
/*  965 */         if (ch == '-' && followedByWhitespace) {
/*  966 */           flowIndicators = true;
/*  967 */           blockIndicators = true;
/*      */         } 
/*      */       } else {
/*      */         
/*  971 */         if (",?[]{}".indexOf(ch) != -1) {
/*  972 */           flowIndicators = true;
/*      */         }
/*  974 */         if (ch == ':') {
/*  975 */           flowIndicators = true;
/*  976 */           if (followedByWhitespace) {
/*  977 */             blockIndicators = true;
/*      */           }
/*      */         } 
/*  980 */         if (ch == '#' && preceededByWhitespace) {
/*  981 */           flowIndicators = true;
/*  982 */           blockIndicators = true;
/*      */         } 
/*      */       } 
/*      */       
/*  986 */       if (Constant.LINEBR.has(ch)) {
/*  987 */         lineBreaks = true;
/*      */       }
/*  989 */       if (ch != '\n' && (' ' > ch || ch > '~')) {
/*  990 */         if ((ch == '' || (' ' <= ch && ch <= '퟿') || ('' <= ch && ch <= '�')) && ch != '﻿') {
/*      */ 
/*      */           
/*  993 */           if (!this.allowUnicode) {
/*  994 */             specialCharacters = true;
/*      */           }
/*      */         } else {
/*  997 */           specialCharacters = true;
/*      */         } 
/*      */       }
/*      */       
/* 1001 */       if (ch == ' ') {
/* 1002 */         if (index == 0) {
/* 1003 */           leadingSpace = true;
/*      */         }
/* 1005 */         if (index == scalar.length() - 1) {
/* 1006 */           trailingSpace = true;
/*      */         }
/* 1008 */         if (previousBreak) {
/* 1009 */           breakSpace = true;
/*      */         }
/* 1011 */         previousSpace = true;
/* 1012 */         previousBreak = false;
/* 1013 */       } else if (Constant.LINEBR.has(ch)) {
/* 1014 */         if (index == 0) {
/* 1015 */           leadingBreak = true;
/*      */         }
/* 1017 */         if (index == scalar.length() - 1) {
/* 1018 */           trailingBreak = true;
/*      */         }
/* 1020 */         if (previousSpace) {
/* 1021 */           spaceBreak = true;
/*      */         }
/* 1023 */         previousSpace = false;
/* 1024 */         previousBreak = true;
/*      */       } else {
/* 1026 */         previousSpace = false;
/* 1027 */         previousBreak = false;
/*      */       } 
/*      */ 
/*      */       
/* 1031 */       index++;
/* 1032 */       preceededByWhitespace = Constant.NULL_BL_T_LINEBR.has(ch);
/* 1033 */       followedByWhitespace = (index + 1 >= scalar.length() || Constant.NULL_BL_T_LINEBR.has(scalar.charAt(index + 1)));
/*      */     } 
/*      */ 
/*      */     
/* 1037 */     boolean allowFlowPlain = true;
/* 1038 */     boolean allowBlockPlain = true;
/* 1039 */     boolean allowSingleQuoted = true;
/* 1040 */     boolean allowDoubleQuoted = true;
/* 1041 */     boolean allowBlock = true;
/*      */     
/* 1043 */     if (leadingSpace || leadingBreak || trailingSpace || trailingBreak) {
/* 1044 */       allowFlowPlain = allowBlockPlain = false;
/*      */     }
/*      */     
/* 1047 */     if (trailingSpace) {
/* 1048 */       allowBlock = false;
/*      */     }
/*      */ 
/*      */     
/* 1052 */     if (breakSpace) {
/* 1053 */       allowFlowPlain = allowBlockPlain = allowSingleQuoted = false;
/*      */     }
/*      */ 
/*      */     
/* 1057 */     if (spaceBreak || specialCharacters) {
/* 1058 */       allowFlowPlain = allowBlockPlain = allowSingleQuoted = allowBlock = false;
/*      */     }
/*      */ 
/*      */     
/* 1062 */     if (lineBreaks) {
/* 1063 */       allowFlowPlain = allowBlockPlain = false;
/*      */     }
/*      */     
/* 1066 */     if (flowIndicators) {
/* 1067 */       allowFlowPlain = false;
/*      */     }
/*      */     
/* 1070 */     if (blockIndicators) {
/* 1071 */       allowBlockPlain = false;
/*      */     }
/*      */     
/* 1074 */     return new ScalarAnalysis(scalar, false, lineBreaks, allowFlowPlain, allowBlockPlain, allowSingleQuoted, allowDoubleQuoted, allowBlock); } private boolean checkEmptyMapping() { return (this.event instanceof MappingStartEvent && !this.events.isEmpty() && this.events.peek() instanceof org.yaml.snakeyaml.events.MappingEndEvent); } private boolean checkEmptyDocument() { if (!(this.event instanceof DocumentStartEvent) || this.events.isEmpty()) return false;  Event event = (Event)this.events.peek(); if (event instanceof ScalarEvent) { ScalarEvent e = (ScalarEvent)event; return (e.getAnchor() == null && e.getTag() == null && e.getImplicit() != null && e.getValue() == ""); }  return false; }
/*      */   private boolean checkSimpleKey() { int length = 0; if (this.event instanceof NodeEvent && ((NodeEvent)this.event).getAnchor() != null) { if (this.preparedAnchor == null) this.preparedAnchor = prepareAnchor(((NodeEvent)this.event).getAnchor());  length += this.preparedAnchor.length(); }  String tag = null; if (this.event instanceof ScalarEvent) { tag = ((ScalarEvent)this.event).getTag(); } else if (this.event instanceof CollectionStartEvent) { tag = ((CollectionStartEvent)this.event).getTag(); }  if (tag != null) { if (this.preparedTag == null) this.preparedTag = prepareTag(tag);  length += this.preparedTag.length(); }  if (this.event instanceof ScalarEvent) { if (this.analysis == null) this.analysis = analyzeScalar(((ScalarEvent)this.event).getValue());  length += this.analysis.scalar.length(); }  return (length < 128 && (this.event instanceof org.yaml.snakeyaml.events.AliasEvent || (this.event instanceof ScalarEvent && !this.analysis.empty && !this.analysis.multiline) || checkEmptySequence() || checkEmptyMapping())); }
/*      */   private void processAnchor(String indicator) throws IOException { NodeEvent ev = (NodeEvent)this.event; if (ev.getAnchor() == null) { this.preparedAnchor = null; return; }  if (this.preparedAnchor == null) this.preparedAnchor = prepareAnchor(ev.getAnchor());  if (this.preparedAnchor != null && !"".equals(this.preparedAnchor)) writeIndicator(indicator + this.preparedAnchor, true, false, false);  this.preparedAnchor = null; }
/*      */   private void processTag() throws IOException { String tag = null; if (this.event instanceof ScalarEvent) { ScalarEvent ev = (ScalarEvent)this.event; tag = ev.getTag(); if (this.style == null) this.style = chooseScalarStyle();  if ((!this.canonical.booleanValue() || tag == null) && ((this.style == null && ev.getImplicit().isFirst()) || (this.style != null && ev.getImplicit().isSecond()))) { this.preparedTag = null; return; }  if (ev.getImplicit().isFirst() && tag == null) { tag = "!"; this.preparedTag = null; }  } else { CollectionStartEvent ev = (CollectionStartEvent)this.event; tag = ev.getTag(); if ((!this.canonical.booleanValue() || tag == null) && ev.getImplicit()) { this.preparedTag = null; return; }  }  if (tag == null)
/*      */       throw new EmitterException("tag is not specified");  if (this.preparedTag == null)
/*      */       this.preparedTag = prepareTag(tag);  if (this.preparedTag != null && !"".equals(this.preparedTag))
/*      */       writeIndicator(this.preparedTag, true, false, false);  this.preparedTag = null; }
/* 1081 */   void flushStream() throws IOException { this.stream.flush(); } private Character chooseScalarStyle() { ScalarEvent ev = (ScalarEvent)this.event; if (this.analysis == null) this.analysis = analyzeScalar(ev.getValue());  if ((ev.getStyle() != null && ev.getStyle().charValue() == '"') || this.canonical.booleanValue()) return Character.valueOf('"');  if (ev.getStyle() == null && ev.getImplicit().isFirst() && (!this.simpleKeyContext || (!this.analysis.empty && !this.analysis.multiline)) && ((this.flowLevel != 0 && this.analysis.allowFlowPlain) || (this.flowLevel == 0 && this.analysis.allowBlockPlain))) return null;  if (ev.getStyle() != null && (ev.getStyle().charValue() == '|' || ev.getStyle().charValue() == '>') && this.flowLevel == 0 && !this.simpleKeyContext && this.analysis.allowBlock) return ev.getStyle();  if ((ev.getStyle() == null || ev.getStyle().charValue() == '\'') && this.analysis.allowSingleQuoted && (!this.simpleKeyContext || !this.analysis.multiline)) return Character.valueOf('\'');  return Character.valueOf('"'); } private void processScalar() throws IOException { ScalarEvent ev = (ScalarEvent)this.event; if (this.analysis == null) this.analysis = analyzeScalar(ev.getValue());  if (this.style == null) this.style = chooseScalarStyle();  this.style = this.options.calculateScalarStyle(this.analysis, DumperOptions.ScalarStyle.createStyle(this.style)).getChar(); boolean split = !this.simpleKeyContext; if (this.style == null) { writePlain(this.analysis.scalar, split); } else { switch (this.style.charValue()) { case '"': writeDoubleQuoted(this.analysis.scalar, split); break;case '\'': writeSingleQuoted(this.analysis.scalar, split); break;case '>': writeFolded(this.analysis.scalar); break;case '|': writeLiteral(this.analysis.scalar); break; }  }  this.analysis = null; this.style = null; }
/*      */   private String prepareVersion(Integer[] version) { Integer major = version[0]; Integer minor = version[1]; if (major.intValue() != 1) throw new EmitterException("unsupported YAML version: " + version[0] + "." + version[1]);  return major.toString() + "." + minor.toString(); }
/*      */   private String prepareTagHandle(String handle) { if (handle == null || "".equals(handle)) throw new EmitterException("tag handle must not be empty");  if (handle.charAt(0) != '!' || handle.charAt(handle.length() - 1) != '!') throw new EmitterException("tag handle must start and end with '!': " + handle);  if (!"!".equals(handle) && !HANDLE_FORMAT.matcher(handle).matches()) throw new EmitterException("invalid character in the tag handle: " + handle);  return handle; }
/*      */   private String prepareTagPrefix(String prefix) { if (prefix == null || "".equals(prefix)) throw new EmitterException("tag prefix must not be empty");  StringBuilder chunks = new StringBuilder(); int start = 0; int end = 0; if (prefix.charAt(0) == '!') end = 1;  while (end < prefix.length()) end++;  if (start < end) chunks.append(prefix.substring(start, end));  return chunks.toString(); }
/*      */   private String prepareTag(String tag) { if (tag == null || "".equals(tag)) throw new EmitterException("tag must not be empty");  if ("!".equals(tag)) return tag;  for (i = 0; i < tag.length(); i++) { char ch = tag.charAt(i); if (Constant.URI_CHARS.hasNo(ch)) throw new YAMLException("Tag (URI) may not contain non-ASCII characters.");  }  String handle = null; String suffix = tag; for (String prefix : this.tagPrefixes.keySet()) { if (tag.startsWith(prefix) && ("!".equals(prefix) || prefix.length() < tag.length())) { handle = (String)this.tagPrefixes.get(prefix); suffix = tag.substring(prefix.length()); }  }  StringBuilder chunks = new StringBuilder(); int start = 0; int end = 0; while (end < suffix.length()) end++;  if (start < end)
/*      */       chunks.append(suffix.substring(start, end));  String suffixText = chunks.toString(); if (handle != null)
/*      */       return handle + suffixText;  return "!<" + suffixText + ">"; }
/*      */   void writeStreamStart() throws IOException {}
/* 1089 */   void writeStreamEnd() throws IOException { flushStream(); }
/*      */ 
/*      */ 
/*      */   
/*      */   void writeIndicator(String indicator, boolean needWhitespace, boolean whitespace, boolean indentation) throws IOException {
/* 1094 */     String data = null;
/* 1095 */     if (this.whitespace || !needWhitespace) {
/* 1096 */       data = indicator;
/*      */     } else {
/* 1098 */       data = " " + indicator;
/*      */     } 
/* 1100 */     this.whitespace = whitespace;
/* 1101 */     this.indention = (this.indention && indentation);
/* 1102 */     this.column += data.length();
/* 1103 */     this.openEnded = false;
/* 1104 */     this.stream.write(data);
/*      */   }
/*      */   
/*      */   void writeIndent() throws IOException {
/*      */     int indent;
/* 1109 */     if (this.indent != null) {
/* 1110 */       indent = this.indent.intValue();
/*      */     } else {
/* 1112 */       indent = 0;
/*      */     } 
/*      */     
/* 1115 */     if (!this.indention || this.column > indent || (this.column == indent && !this.whitespace)) {
/* 1116 */       writeLineBreak(null);
/*      */     }
/*      */     
/* 1119 */     if (this.column < indent) {
/* 1120 */       this.whitespace = true;
/* 1121 */       StringBuilder data = new StringBuilder();
/* 1122 */       for (int i = 0; i < indent - this.column; i++) {
/* 1123 */         data.append(" ");
/*      */       }
/* 1125 */       this.column = indent;
/* 1126 */       this.stream.write(data.toString());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeLineBreak(String data) throws IOException {
/* 1131 */     if (data == null) {
/* 1132 */       data = this.bestLineBreak;
/*      */     }
/* 1134 */     this.whitespace = true;
/* 1135 */     this.indention = true;
/* 1136 */     this.column = 0;
/* 1137 */     this.stream.write(data);
/*      */   }
/*      */   
/*      */   void writeVersionDirective(String versionText) throws IOException {
/* 1141 */     this.stream.write("%YAML " + versionText);
/* 1142 */     writeLineBreak(null);
/*      */   }
/*      */   
/*      */   void writeTagDirective(String handleText, String prefixText) throws IOException {
/* 1146 */     this.stream.write("%TAG " + handleText + " " + prefixText);
/* 1147 */     writeLineBreak(null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeSingleQuoted(String text, boolean split) throws IOException {
/* 1152 */     writeIndicator("'", true, false, false);
/* 1153 */     boolean spaces = false;
/* 1154 */     boolean breaks = false;
/* 1155 */     int start = 0, end = 0;
/*      */     
/* 1157 */     while (end <= text.length()) {
/* 1158 */       char ch = Character.MIN_VALUE;
/* 1159 */       if (end < text.length()) {
/* 1160 */         ch = text.charAt(end);
/*      */       }
/* 1162 */       if (spaces) {
/* 1163 */         if (ch == '\000' || ch != ' ') {
/* 1164 */           if (start + 1 == end && this.column > this.bestWidth && split && start != 0 && end != text.length()) {
/*      */             
/* 1166 */             writeIndent();
/*      */           } else {
/* 1168 */             String data = text.substring(start, end);
/* 1169 */             this.column += data.length();
/* 1170 */             this.stream.write(data);
/*      */           } 
/* 1172 */           start = end;
/*      */         } 
/* 1174 */       } else if (breaks) {
/* 1175 */         if (ch == '\000' || Constant.LINEBR.hasNo(ch)) {
/* 1176 */           if (text.charAt(start) == '\n') {
/* 1177 */             writeLineBreak(null);
/*      */           }
/* 1179 */           String data = text.substring(start, end);
/* 1180 */           for (char br : data.toCharArray()) {
/* 1181 */             if (br == '\n') {
/* 1182 */               writeLineBreak(null);
/*      */             } else {
/* 1184 */               writeLineBreak(String.valueOf(br));
/*      */             } 
/*      */           } 
/* 1187 */           writeIndent();
/* 1188 */           start = end;
/*      */         }
/*      */       
/* 1191 */       } else if (Constant.LINEBR.has(ch, "\000 '") && 
/* 1192 */         start < end) {
/* 1193 */         String data = text.substring(start, end);
/* 1194 */         this.column += data.length();
/* 1195 */         this.stream.write(data);
/* 1196 */         start = end;
/*      */       } 
/*      */ 
/*      */       
/* 1200 */       if (ch == '\'') {
/* 1201 */         String data = "''";
/* 1202 */         this.column += 2;
/* 1203 */         this.stream.write(data);
/* 1204 */         start = end + 1;
/*      */       } 
/* 1206 */       if (ch != '\000') {
/* 1207 */         spaces = (ch == ' ');
/* 1208 */         breaks = Constant.LINEBR.has(ch);
/*      */       } 
/* 1210 */       end++;
/*      */     } 
/* 1212 */     writeIndicator("'", false, false, false);
/*      */   }
/*      */   
/*      */   private void writeDoubleQuoted(String text, boolean split) throws IOException {
/* 1216 */     writeIndicator("\"", true, false, false);
/* 1217 */     int start = 0;
/* 1218 */     int end = 0;
/* 1219 */     while (end <= text.length()) {
/* 1220 */       Character ch = null;
/* 1221 */       if (end < text.length()) {
/* 1222 */         ch = Character.valueOf(text.charAt(end));
/*      */       }
/* 1224 */       if (ch == null || "\"\\  ﻿".indexOf(ch.charValue()) != -1 || ' ' > ch.charValue() || ch.charValue() > '~') {
/*      */         
/* 1226 */         if (start < end) {
/* 1227 */           String data = text.substring(start, end);
/* 1228 */           this.column += data.length();
/* 1229 */           this.stream.write(data);
/* 1230 */           start = end;
/*      */         } 
/* 1232 */         if (ch != null) {
/*      */           String data;
/* 1234 */           if (ESCAPE_REPLACEMENTS.containsKey(new Character(ch.charValue()))) {
/* 1235 */             data = "\\" + (String)ESCAPE_REPLACEMENTS.get(new Character(ch.charValue()));
/* 1236 */           } else if (!this.allowUnicode) {
/*      */ 
/*      */             
/* 1239 */             if (ch.charValue() <= 'ÿ') {
/* 1240 */               String s = "0" + Integer.toString(ch.charValue(), 16);
/* 1241 */               data = "\\x" + s.substring(s.length() - 2);
/*      */             } else {
/* 1243 */               String s = "000" + Integer.toString(ch.charValue(), 16);
/* 1244 */               data = "\\u" + s.substring(s.length() - 4);
/*      */             } 
/*      */           } else {
/* 1247 */             data = String.valueOf(ch);
/*      */           } 
/* 1249 */           this.column += data.length();
/* 1250 */           this.stream.write(data);
/* 1251 */           start = end + 1;
/*      */         } 
/*      */       } 
/* 1254 */       if (0 < end && end < text.length() - 1 && (ch.charValue() == ' ' || start >= end) && this.column + end - start > this.bestWidth && split) {
/*      */         String data;
/*      */         
/* 1257 */         if (start >= end) {
/* 1258 */           data = "\\";
/*      */         } else {
/* 1260 */           data = text.substring(start, end) + "\\";
/*      */         } 
/* 1262 */         if (start < end) {
/* 1263 */           start = end;
/*      */         }
/* 1265 */         this.column += data.length();
/* 1266 */         this.stream.write(data);
/* 1267 */         writeIndent();
/* 1268 */         this.whitespace = false;
/* 1269 */         this.indention = false;
/* 1270 */         if (text.charAt(start) == ' ') {
/* 1271 */           data = "\\";
/* 1272 */           this.column += data.length();
/* 1273 */           this.stream.write(data);
/*      */         } 
/*      */       } 
/* 1276 */       end++;
/*      */     } 
/* 1278 */     writeIndicator("\"", false, false, false);
/*      */   }
/*      */   
/*      */   private String determineBlockHints(String text) {
/* 1282 */     StringBuilder hints = new StringBuilder();
/* 1283 */     if (text != null && text.length() > 0) {
/* 1284 */       if (Constant.LINEBR.has(text.charAt(0), " ")) {
/* 1285 */         hints.append(this.bestIndent);
/*      */       }
/* 1287 */       char ch1 = text.charAt(text.length() - 1);
/* 1288 */       if (Constant.LINEBR.hasNo(ch1)) {
/* 1289 */         hints.append("-");
/* 1290 */       } else if (text.length() == 1 || Constant.LINEBR.has(text.charAt(text.length() - 2))) {
/* 1291 */         hints.append("+");
/*      */       } 
/*      */     } 
/* 1294 */     return hints.toString();
/*      */   }
/*      */   
/*      */   void writeFolded(String text) throws IOException {
/* 1298 */     String hints = determineBlockHints(text);
/* 1299 */     writeIndicator(">" + hints, true, false, false);
/* 1300 */     if (hints.length() > 0 && hints.charAt(hints.length() - 1) == '+') {
/* 1301 */       this.openEnded = true;
/*      */     }
/* 1303 */     writeLineBreak(null);
/* 1304 */     boolean leadingSpace = true;
/* 1305 */     boolean spaces = false;
/* 1306 */     boolean breaks = true;
/* 1307 */     int start = 0, end = 0;
/* 1308 */     while (end <= text.length()) {
/* 1309 */       char ch = Character.MIN_VALUE;
/* 1310 */       if (end < text.length()) {
/* 1311 */         ch = text.charAt(end);
/*      */       }
/* 1313 */       if (breaks) {
/* 1314 */         if (ch == '\000' || Constant.LINEBR.hasNo(ch)) {
/* 1315 */           if (!leadingSpace && ch != '\000' && ch != ' ' && text.charAt(start) == '\n') {
/* 1316 */             writeLineBreak(null);
/*      */           }
/* 1318 */           leadingSpace = (ch == ' ');
/* 1319 */           String data = text.substring(start, end);
/* 1320 */           for (char br : data.toCharArray()) {
/* 1321 */             if (br == '\n') {
/* 1322 */               writeLineBreak(null);
/*      */             } else {
/* 1324 */               writeLineBreak(String.valueOf(br));
/*      */             } 
/*      */           } 
/* 1327 */           if (ch != '\000') {
/* 1328 */             writeIndent();
/*      */           }
/* 1330 */           start = end;
/*      */         } 
/* 1332 */       } else if (spaces) {
/* 1333 */         if (ch != ' ') {
/* 1334 */           if (start + 1 == end && this.column > this.bestWidth) {
/* 1335 */             writeIndent();
/*      */           } else {
/* 1337 */             String data = text.substring(start, end);
/* 1338 */             this.column += data.length();
/* 1339 */             this.stream.write(data);
/*      */           } 
/* 1341 */           start = end;
/*      */         }
/*      */       
/* 1344 */       } else if (Constant.LINEBR.has(ch, "\000 ")) {
/* 1345 */         String data = text.substring(start, end);
/* 1346 */         this.column += data.length();
/* 1347 */         this.stream.write(data);
/* 1348 */         if (ch == '\000') {
/* 1349 */           writeLineBreak(null);
/*      */         }
/* 1351 */         start = end;
/*      */       } 
/*      */       
/* 1354 */       if (ch != '\000') {
/* 1355 */         breaks = Constant.LINEBR.has(ch);
/* 1356 */         spaces = (ch == ' ');
/*      */       } 
/* 1358 */       end++;
/*      */     } 
/*      */   }
/*      */   
/*      */   void writeLiteral(String text) throws IOException {
/* 1363 */     String hints = determineBlockHints(text);
/* 1364 */     writeIndicator("|" + hints, true, false, false);
/* 1365 */     if (hints.length() > 0 && hints.charAt(hints.length() - 1) == '+') {
/* 1366 */       this.openEnded = true;
/*      */     }
/* 1368 */     writeLineBreak(null);
/* 1369 */     boolean breaks = true;
/* 1370 */     int start = 0, end = 0;
/* 1371 */     while (end <= text.length()) {
/* 1372 */       char ch = Character.MIN_VALUE;
/* 1373 */       if (end < text.length()) {
/* 1374 */         ch = text.charAt(end);
/*      */       }
/* 1376 */       if (breaks) {
/* 1377 */         if (ch == '\000' || Constant.LINEBR.hasNo(ch)) {
/* 1378 */           String data = text.substring(start, end);
/* 1379 */           for (char br : data.toCharArray()) {
/* 1380 */             if (br == '\n') {
/* 1381 */               writeLineBreak(null);
/*      */             } else {
/* 1383 */               writeLineBreak(String.valueOf(br));
/*      */             } 
/*      */           } 
/* 1386 */           if (ch != '\000') {
/* 1387 */             writeIndent();
/*      */           }
/* 1389 */           start = end;
/*      */         }
/*      */       
/* 1392 */       } else if (ch == '\000' || Constant.LINEBR.has(ch)) {
/* 1393 */         String data = text.substring(start, end);
/* 1394 */         this.stream.write(data);
/* 1395 */         if (ch == '\000') {
/* 1396 */           writeLineBreak(null);
/*      */         }
/* 1398 */         start = end;
/*      */       } 
/*      */       
/* 1401 */       if (ch != '\000') {
/* 1402 */         breaks = Constant.LINEBR.has(ch);
/*      */       }
/* 1404 */       end++;
/*      */     } 
/*      */   }
/*      */   
/*      */   void writePlain(String text, boolean split) throws IOException {
/* 1409 */     if (this.rootContext) {
/* 1410 */       this.openEnded = true;
/*      */     }
/* 1412 */     if (text == null || "".equals(text)) {
/*      */       return;
/*      */     }
/* 1415 */     if (!this.whitespace) {
/* 1416 */       String data = " ";
/* 1417 */       this.column += data.length();
/* 1418 */       this.stream.write(data);
/*      */     } 
/* 1420 */     this.whitespace = false;
/* 1421 */     this.indention = false;
/* 1422 */     boolean spaces = false;
/* 1423 */     boolean breaks = false;
/* 1424 */     int start = 0, end = 0;
/* 1425 */     while (end <= text.length()) {
/* 1426 */       char ch = Character.MIN_VALUE;
/* 1427 */       if (end < text.length()) {
/* 1428 */         ch = text.charAt(end);
/*      */       }
/* 1430 */       if (spaces) {
/* 1431 */         if (ch != ' ') {
/* 1432 */           if (start + 1 == end && this.column > this.bestWidth && split) {
/* 1433 */             writeIndent();
/* 1434 */             this.whitespace = false;
/* 1435 */             this.indention = false;
/*      */           } else {
/* 1437 */             String data = text.substring(start, end);
/* 1438 */             this.column += data.length();
/* 1439 */             this.stream.write(data);
/*      */           } 
/* 1441 */           start = end;
/*      */         } 
/* 1443 */       } else if (breaks) {
/* 1444 */         if (Constant.LINEBR.hasNo(ch)) {
/* 1445 */           if (text.charAt(start) == '\n') {
/* 1446 */             writeLineBreak(null);
/*      */           }
/* 1448 */           String data = text.substring(start, end);
/* 1449 */           for (char br : data.toCharArray()) {
/* 1450 */             if (br == '\n') {
/* 1451 */               writeLineBreak(null);
/*      */             } else {
/* 1453 */               writeLineBreak(String.valueOf(br));
/*      */             } 
/*      */           } 
/* 1456 */           writeIndent();
/* 1457 */           this.whitespace = false;
/* 1458 */           this.indention = false;
/* 1459 */           start = end;
/*      */         }
/*      */       
/* 1462 */       } else if (ch == '\000' || Constant.LINEBR.has(ch)) {
/* 1463 */         String data = text.substring(start, end);
/* 1464 */         this.column += data.length();
/* 1465 */         this.stream.write(data);
/* 1466 */         start = end;
/*      */       } 
/*      */       
/* 1469 */       if (ch != '\000') {
/* 1470 */         spaces = (ch == ' ');
/* 1471 */         breaks = Constant.LINEBR.has(ch);
/*      */       } 
/* 1473 */       end++;
/*      */     } 
/*      */   } }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\emitter\Emitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */