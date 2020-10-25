/*      */ package jline;
/*      */ 
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.datatransfer.UnsupportedFlavorException;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.SortedMap;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ public class ConsoleReader
/*      */   implements ConsoleOperations {
/*      */   static final int TAB_WIDTH = 4;
/*      */   String prompt;
/*   36 */   public static final String CR = System.getProperty("line.separator"); private boolean useHistory;
/*      */   private boolean usePagination;
/*   38 */   private static ResourceBundle loc = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName());
/*      */   
/*      */   public static SortedMap KEYMAP_NAMES;
/*      */   
/*      */   private final short[] keybindings;
/*      */   private boolean bellEnabled;
/*      */   private Character mask;
/*      */   
/*      */   static  {
/*   47 */     names = new TreeMap();
/*      */     
/*   49 */     names.put("MOVE_TO_BEG", new Short(-1));
/*   50 */     names.put("MOVE_TO_END", new Short(-3));
/*   51 */     names.put("PREV_CHAR", new Short(-4));
/*   52 */     names.put("NEWLINE", new Short(-6));
/*   53 */     names.put("KILL_LINE", new Short(-7));
/*   54 */     names.put("PASTE", new Short(-60));
/*   55 */     names.put("CLEAR_SCREEN", new Short(-8));
/*   56 */     names.put("NEXT_HISTORY", new Short(-9));
/*   57 */     names.put("PREV_HISTORY", new Short(-11));
/*   58 */     names.put("START_OF_HISTORY", new Short(-61));
/*   59 */     names.put("END_OF_HISTORY", new Short(-62));
/*   60 */     names.put("REDISPLAY", new Short(-13));
/*   61 */     names.put("KILL_LINE_PREV", new Short(-15));
/*   62 */     names.put("DELETE_PREV_WORD", new Short(-16));
/*   63 */     names.put("NEXT_CHAR", new Short(-19));
/*   64 */     names.put("REPEAT_PREV_CHAR", new Short(-20));
/*   65 */     names.put("SEARCH_PREV", new Short(-21));
/*   66 */     names.put("REPEAT_NEXT_CHAR", new Short(-24));
/*   67 */     names.put("SEARCH_NEXT", new Short(-25));
/*   68 */     names.put("PREV_SPACE_WORD", new Short(-27));
/*   69 */     names.put("TO_END_WORD", new Short(-29));
/*   70 */     names.put("REPEAT_SEARCH_PREV", new Short(-34));
/*   71 */     names.put("PASTE_PREV", new Short(-36));
/*   72 */     names.put("REPLACE_MODE", new Short(-37));
/*   73 */     names.put("SUBSTITUTE_LINE", new Short(-38));
/*   74 */     names.put("TO_PREV_CHAR", new Short(-39));
/*   75 */     names.put("NEXT_SPACE_WORD", new Short(-40));
/*   76 */     names.put("DELETE_PREV_CHAR", new Short(-41));
/*   77 */     names.put("ADD", new Short(-42));
/*   78 */     names.put("PREV_WORD", new Short(-43));
/*   79 */     names.put("CHANGE_META", new Short(-44));
/*   80 */     names.put("DELETE_META", new Short(-45));
/*   81 */     names.put("END_WORD", new Short(-46));
/*   82 */     names.put("NEXT_CHAR", new Short(-19));
/*   83 */     names.put("INSERT", new Short(-48));
/*   84 */     names.put("REPEAT_SEARCH_NEXT", new Short(-49));
/*   85 */     names.put("PASTE_NEXT", new Short(-50));
/*   86 */     names.put("REPLACE_CHAR", new Short(-51));
/*   87 */     names.put("SUBSTITUTE_CHAR", new Short(-52));
/*   88 */     names.put("TO_NEXT_CHAR", new Short(-53));
/*   89 */     names.put("UNDO", new Short(-54));
/*   90 */     names.put("NEXT_WORD", new Short(-55));
/*   91 */     names.put("DELETE_NEXT_CHAR", new Short(-56));
/*   92 */     names.put("CHANGE_CASE", new Short(-57));
/*   93 */     names.put("COMPLETE", new Short(-58));
/*   94 */     names.put("EXIT", new Short(-59));
/*   95 */     names.put("CLEAR_LINE", new Short(-63));
/*      */     
/*   97 */     KEYMAP_NAMES = new TreeMap(Collections.unmodifiableMap(names));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  118 */     NULL_MASK = new Character(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Character NULL_MASK;
/*      */ 
/*      */ 
/*      */   
/*      */   private int autoprintThreshhold;
/*      */ 
/*      */ 
/*      */   
/*      */   private final Terminal terminal;
/*      */ 
/*      */ 
/*      */   
/*      */   private CompletionHandler completionHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   InputStream in;
/*      */ 
/*      */   
/*      */   final Writer out;
/*      */ 
/*      */   
/*      */   final CursorBuffer buf;
/*      */ 
/*      */   
/*      */   static PrintWriter debugger;
/*      */ 
/*      */   
/*      */   History history;
/*      */ 
/*      */   
/*      */   final List completors;
/*      */ 
/*      */   
/*      */   private Character echoCharacter;
/*      */ 
/*      */   
/*      */   private Map triggeredActions;
/*      */ 
/*      */ 
/*      */   
/*  165 */   public void addTriggeredAction(char c, ActionListener listener) { this.triggeredActions.put(new Character(c), listener); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  174 */   public ConsoleReader() throws IOException { this(new FileInputStream(FileDescriptor.in), new PrintWriter(new OutputStreamWriter(System.out, System.getProperty("jline.WindowsTerminal.output.encoding", System.getProperty("file.encoding"))))); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   public ConsoleReader(InputStream in, Writer out) throws IOException { this(in, out, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  191 */   public ConsoleReader(InputStream in, Writer out, InputStream bindings) throws IOException { this(in, out, bindings, Terminal.getTerminal()); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConsoleReader(InputStream in, Writer out, InputStream bindings, Terminal term) throws IOException {
/*      */     this.useHistory = true;
/*      */     this.usePagination = false;
/*      */     this.bellEnabled = true;
/*      */     this.mask = null;
/*      */     this.autoprintThreshhold = Integer.getInteger("jline.completion.threshold", 100).intValue();
/*      */     this.completionHandler = new CandidateListCompletionHandler();
/*      */     this.buf = new CursorBuffer();
/*      */     this.history = new History();
/*      */     this.completors = new LinkedList();
/*      */     this.echoCharacter = null;
/*      */     this.triggeredActions = new HashMap();
/*  208 */     this.terminal = term;
/*  209 */     setInput(in);
/*  210 */     this.out = out;
/*  211 */     if (bindings == null) {
/*      */       try {
/*  213 */         String bindingFile = System.getProperty("jline.keybindings", (new File(System.getProperty("user.home", ".jlinebindings.properties"))).getAbsolutePath());
/*      */ 
/*      */ 
/*      */         
/*  217 */         if ((new File(bindingFile)).isFile()) {
/*  218 */           bindings = new FileInputStream(new File(bindingFile));
/*      */         }
/*  220 */       } catch (Exception e) {
/*      */         
/*  222 */         if (debugger != null) {
/*  223 */           e.printStackTrace(debugger);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  228 */     if (bindings == null) {
/*  229 */       bindings = this.terminal.getDefaultBindings();
/*      */     }
/*      */     
/*  232 */     this.keybindings = new short[131070];
/*      */     
/*  234 */     Arrays.fill(this.keybindings, (short)-99);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  241 */     if (bindings != null) {
/*  242 */       Properties p = new Properties();
/*  243 */       p.load(bindings);
/*  244 */       bindings.close();
/*      */       
/*  246 */       for (Iterator i = p.keySet().iterator(); i.hasNext(); ) {
/*  247 */         String val = (String)i.next();
/*      */         
/*      */         try {
/*  250 */           Short code = new Short(val);
/*  251 */           String op = p.getProperty(val);
/*      */           
/*  253 */           Short opval = (Short)KEYMAP_NAMES.get(op);
/*      */           
/*  255 */           if (opval != null) {
/*  256 */             this.keybindings[code.shortValue()] = opval.shortValue();
/*      */           }
/*  258 */         } catch (NumberFormatException nfe) {
/*  259 */           consumeException(nfe);
/*      */         } 
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
/*  272 */   public Terminal getTerminal() { return this.terminal; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  279 */   public void setDebug(PrintWriter debugger) { ConsoleReader.debugger = debugger; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  286 */   public void setInput(InputStream in) { this.in = in; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  293 */   public InputStream getInput() { return this.in; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  300 */   public String readLine() throws IOException { return readLine((String)null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  308 */   public String readLine(Character mask) throws IOException { return readLine(null, mask); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  317 */   public void setBellEnabled(boolean bellEnabled) { this.bellEnabled = bellEnabled; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  324 */   public boolean getBellEnabled() { return this.bellEnabled; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  334 */   public int getTermwidth() { return Terminal.setupTerminal().getTerminalWidth(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  345 */   public int getTermheight() { return Terminal.setupTerminal().getTerminalHeight(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  353 */   public void setAutoprintThreshhold(int autoprintThreshhold) { this.autoprintThreshhold = autoprintThreshhold; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  360 */   public int getAutoprintThreshhold() { return this.autoprintThreshhold; }
/*      */ 
/*      */   
/*      */   int getKeyForAction(short logicalAction) {
/*  364 */     for (int i = 0; i < this.keybindings.length; i++) {
/*  365 */       if (this.keybindings[i] == logicalAction) {
/*  366 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  370 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int clearEcho(int c) throws IOException {
/*  378 */     if (!this.terminal.getEcho()) {
/*  379 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  383 */     int num = countEchoCharacters((char)c);
/*  384 */     back(num);
/*  385 */     drawBuffer(num);
/*      */     
/*  387 */     return num;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int countEchoCharacters(char c) {
/*  393 */     if (c == '\t') {
/*  394 */       int tabstop = 8;
/*  395 */       int position = getCursorPosition();
/*      */       
/*  397 */       return tabstop - position % tabstop;
/*      */     } 
/*      */     
/*  400 */     return getPrintableCharacters(c).length();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   StringBuffer getPrintableCharacters(char ch) {
/*  409 */     StringBuffer sbuff = new StringBuffer();
/*      */     
/*  411 */     if (ch >= ' ') {
/*  412 */       if (ch < '') {
/*  413 */         sbuff.append(ch);
/*  414 */       } else if (ch == '') {
/*  415 */         sbuff.append('^');
/*  416 */         sbuff.append('?');
/*      */       } else {
/*  418 */         sbuff.append('M');
/*  419 */         sbuff.append('-');
/*      */         
/*  421 */         if (ch >= ' ') {
/*  422 */           if (ch < 'ÿ') {
/*  423 */             sbuff.append((char)(ch - ''));
/*      */           } else {
/*  425 */             sbuff.append('^');
/*  426 */             sbuff.append('?');
/*      */           } 
/*      */         } else {
/*  429 */           sbuff.append('^');
/*  430 */           sbuff.append((char)(ch - '' + '@'));
/*      */         } 
/*      */       } 
/*      */     } else {
/*  434 */       sbuff.append('^');
/*  435 */       sbuff.append((char)(ch + '@'));
/*      */     } 
/*      */     
/*  438 */     return sbuff;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  444 */   int getCursorPosition() { return ((this.prompt == null) ? 0 : this.prompt.length()) + this.buf.cursor; }
/*      */ 
/*      */ 
/*      */   
/*  448 */   public String readLine(String prompt) throws IOException { return readLine(prompt, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  455 */   public void setDefaultPrompt(String prompt) { this.prompt = prompt; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  462 */   public String getDefaultPrompt() throws IOException { return this.prompt; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String readLine(String prompt, Character mask) throws IOException {
/*  476 */     this.mask = mask;
/*  477 */     if (prompt != null) {
/*  478 */       this.prompt = prompt;
/*      */     }
/*      */     try {
/*  481 */       this.terminal.beforeReadLine(this, this.prompt, mask);
/*      */       
/*  483 */       if (this.prompt != null && this.prompt.length() > 0) {
/*  484 */         this.out.write(this.prompt);
/*  485 */         this.out.flush();
/*      */       } 
/*      */ 
/*      */       
/*  489 */       if (!this.terminal.isSupported()) {
/*  490 */         return readLine(this.in);
/*      */       }
/*      */       
/*      */       while (true) {
/*  494 */         int[] next = readBinding();
/*      */         
/*  496 */         if (next == null) {
/*  497 */           return null;
/*      */         }
/*      */         
/*  500 */         int c = next[0];
/*  501 */         int code = next[1];
/*      */         
/*  503 */         if (c == -1) {
/*  504 */           return null;
/*      */         }
/*      */         
/*  507 */         boolean success = true;
/*      */         
/*  509 */         switch (code) {
/*      */           
/*      */           case -59:
/*  512 */             if (this.buf.buffer.length() == 0) {
/*  513 */               return null;
/*      */             }
/*      */             break;
/*      */           
/*      */           case -58:
/*  518 */             success = complete();
/*      */             break;
/*      */           
/*      */           case -1:
/*  522 */             success = setCursorPosition(0);
/*      */             break;
/*      */           
/*      */           case -7:
/*  526 */             success = killLine();
/*      */             break;
/*      */           
/*      */           case -8:
/*  530 */             success = clearScreen();
/*      */             break;
/*      */           
/*      */           case -15:
/*  534 */             success = resetLine();
/*      */             break;
/*      */           
/*      */           case -6:
/*  538 */             moveToEnd();
/*  539 */             printNewline();
/*  540 */             return finishBuffer();
/*      */           
/*      */           case -41:
/*  543 */             success = backspace();
/*      */             break;
/*      */           
/*      */           case -56:
/*  547 */             success = deleteCurrentCharacter();
/*      */             break;
/*      */           
/*      */           case -3:
/*  551 */             success = moveToEnd();
/*      */             break;
/*      */           
/*      */           case -4:
/*  555 */             success = (moveCursor(-1) != 0);
/*      */             break;
/*      */           
/*      */           case -19:
/*  559 */             success = (moveCursor(1) != 0);
/*      */             break;
/*      */           
/*      */           case -9:
/*  563 */             success = moveHistory(true);
/*      */             break;
/*      */           
/*      */           case -11:
/*  567 */             success = moveHistory(false);
/*      */             break;
/*      */           
/*      */           case -13:
/*      */             break;
/*      */           
/*      */           case -60:
/*  574 */             success = paste();
/*      */             break;
/*      */           
/*      */           case -16:
/*  578 */             success = deletePreviousWord();
/*      */             break;
/*      */           
/*      */           case -43:
/*  582 */             success = previousWord();
/*      */             break;
/*      */           
/*      */           case -55:
/*  586 */             success = nextWord();
/*      */             break;
/*      */           
/*      */           case -61:
/*  590 */             success = this.history.moveToFirstEntry();
/*  591 */             if (success) {
/*  592 */               setBuffer(this.history.current());
/*      */             }
/*      */             break;
/*      */           case -62:
/*  596 */             success = this.history.moveToLastEntry();
/*  597 */             if (success) {
/*  598 */               setBuffer(this.history.current());
/*      */             }
/*      */             break;
/*      */           case -63:
/*  602 */             moveInternal(-this.buf.buffer.length());
/*  603 */             killLine();
/*      */             break;
/*      */           
/*      */           case -48:
/*  607 */             this.buf.setOvertyping(!this.buf.isOvertyping());
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/*  612 */             if (c != 0) {
/*  613 */               ActionListener action = (ActionListener)this.triggeredActions.get(new Character((char)c));
/*  614 */               if (action != null) {
/*  615 */                 action.actionPerformed(null); break;
/*      */               } 
/*  617 */               putChar(c, true); break;
/*      */             } 
/*  619 */             success = false;
/*      */             break;
/*      */         } 
/*  622 */         if (!success) {
/*  623 */           beep();
/*      */         }
/*      */         
/*  626 */         flushConsole();
/*      */       } 
/*      */     } finally {
/*  629 */       this.terminal.afterReadLine(this, this.prompt, mask);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String readLine(InputStream in) throws IOException {
/*  634 */     StringBuffer buf = new StringBuffer();
/*      */     
/*      */     while (true) {
/*  637 */       int i = in.read();
/*      */       
/*  639 */       if (i == -1 || i == 10 || i == 13) {
/*  640 */         return buf.toString();
/*      */       }
/*      */       
/*  643 */       buf.append((char)i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] readBinding() throws IOException {
/*  654 */     int c = readVirtualKey();
/*      */     
/*  656 */     if (c == -1) {
/*  657 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  661 */     short code = this.keybindings[c];
/*      */     
/*  663 */     if (debugger != null) {
/*  664 */       debug("    translated: " + c + ": " + code);
/*      */     }
/*      */     
/*  667 */     return new int[] { c, code };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean moveHistory(boolean next) throws IOException {
/*  677 */     if (next && !this.history.next())
/*  678 */       return false; 
/*  679 */     if (!next && !this.history.previous()) {
/*  680 */       return false;
/*      */     }
/*      */     
/*  683 */     setBuffer(this.history.current());
/*      */     
/*  685 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean paste() {
/*      */     Clipboard clipboard;
/*      */     try {
/*  696 */       clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/*  697 */     } catch (Exception e) {
/*  698 */       return false;
/*      */     } 
/*      */     
/*  701 */     if (clipboard == null) {
/*  702 */       return false;
/*      */     }
/*      */     
/*  705 */     Transferable transferable = clipboard.getContents(null);
/*      */     
/*  707 */     if (transferable == null) {
/*  708 */       return false;
/*      */     }
/*      */     try {
/*      */       String value;
/*  712 */       Object content = transferable.getTransferData(DataFlavor.plainTextFlavor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  721 */       if (content == null) {
/*      */         try {
/*  723 */           content = (new DataFlavor()).getReaderForText(transferable);
/*  724 */         } catch (Exception e) {}
/*      */       }
/*      */ 
/*      */       
/*  728 */       if (content == null) {
/*  729 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  734 */       if (content instanceof Reader) {
/*      */ 
/*      */         
/*  737 */         value = "";
/*      */         
/*  739 */         String line = null;
/*      */         
/*  741 */         BufferedReader read = new BufferedReader((Reader)content);
/*  742 */         while ((line = read.readLine()) != null) {
/*  743 */           if (value.length() > 0) {
/*  744 */             value = value + "\n";
/*      */           }
/*      */           
/*  747 */           value = value + line;
/*      */         } 
/*      */       } else {
/*  750 */         value = content.toString();
/*      */       } 
/*      */       
/*  753 */       if (value == null) {
/*  754 */         return true;
/*      */       }
/*      */       
/*  757 */       putString(value);
/*      */       
/*  759 */       return true;
/*  760 */     } catch (UnsupportedFlavorException ufe) {
/*  761 */       if (debugger != null) {
/*  762 */         debug(ufe + "");
/*      */       }
/*  764 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean killLine() {
/*  774 */     int cp = this.buf.cursor;
/*  775 */     int len = this.buf.buffer.length();
/*      */     
/*  777 */     if (cp >= len) {
/*  778 */       return false;
/*      */     }
/*      */     
/*  781 */     int num = this.buf.buffer.length() - cp;
/*  782 */     clearAhead(num);
/*      */     
/*  784 */     for (int i = 0; i < num; i++) {
/*  785 */       this.buf.buffer.deleteCharAt(len - i - 1);
/*      */     }
/*      */     
/*  788 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean clearScreen() {
/*  795 */     if (!this.terminal.isANSISupported()) {
/*  796 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  800 */     printString("\033[2J");
/*  801 */     flushConsole();
/*      */ 
/*      */     
/*  804 */     printString("\033[1;1H");
/*  805 */     flushConsole();
/*      */     
/*  807 */     redrawLine();
/*      */     
/*  809 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean complete() {
/*      */     Completor comp;
/*  819 */     if (this.completors.size() == 0) {
/*  820 */       return false;
/*      */     }
/*      */     
/*  823 */     List candidates = new LinkedList();
/*  824 */     String bufstr = this.buf.buffer.toString();
/*  825 */     int cursor = this.buf.cursor;
/*      */     
/*  827 */     int position = -1;
/*      */     
/*  829 */     Iterator i = this.completors.iterator(); do {
/*  830 */       comp = (Completor)i.next();
/*      */     }
/*  832 */     while (i.hasNext() && (position = comp.complete(bufstr, cursor, candidates)) == -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  838 */     if (candidates.size() == 0) {
/*  839 */       return false;
/*      */     }
/*      */     
/*  842 */     return this.completionHandler.complete(this, candidates, position);
/*      */   }
/*      */ 
/*      */   
/*  846 */   public CursorBuffer getCursorBuffer() { return this.buf; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printColumns(Collection stuff) throws IOException {
/*      */     int showLines;
/*  856 */     if (stuff == null || stuff.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  860 */     int width = getTermwidth();
/*  861 */     int maxwidth = 0;
/*      */     
/*  863 */     for (i = stuff.iterator(); i.hasNext(); maxwidth = Math.max(maxwidth, i.next().toString().length()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     StringBuffer line = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/*  872 */     if (this.usePagination) {
/*  873 */       showLines = getTermheight() - 1;
/*      */     } else {
/*  875 */       showLines = Integer.MAX_VALUE;
/*      */     } 
/*  877 */     for (Iterator i = stuff.iterator(); i.hasNext(); ) {
/*  878 */       String cur = (String)i.next();
/*      */       
/*  880 */       if (line.length() + maxwidth > width) {
/*  881 */         printString(line.toString().trim());
/*  882 */         printNewline();
/*  883 */         line.setLength(0);
/*  884 */         if (--showLines == 0) {
/*  885 */           printString(loc.getString("display-more"));
/*  886 */           flushConsole();
/*  887 */           int c = readVirtualKey();
/*  888 */           if (c == 13 || c == 10) {
/*  889 */             showLines = 1;
/*  890 */           } else if (c != 113) {
/*  891 */             showLines = getTermheight() - 1;
/*      */           } 
/*  893 */           back(loc.getString("display-more").length());
/*  894 */           if (c == 113) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*  899 */       pad(cur, maxwidth + 3, line);
/*      */     } 
/*      */     
/*  902 */     if (line.length() > 0) {
/*  903 */       printString(line.toString().trim());
/*  904 */       printNewline();
/*  905 */       line.setLength(0);
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
/*      */ 
/*      */ 
/*      */   
/*      */   private final void pad(String toPad, int len, StringBuffer appendTo) {
/*  923 */     appendTo.append(toPad);
/*      */     
/*  925 */     for (int i = 0; i < len - toPad.length(); ) { i++; appendTo.append(' '); }
/*      */   
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
/*  939 */   public boolean addCompletor(Completor completor) { return this.completors.add(completor); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  951 */   public boolean removeCompletor(Completor completor) { return this.completors.remove(completor); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  958 */   public Collection getCompletors() { return Collections.unmodifiableList(this.completors); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean resetLine() {
/*  967 */     if (this.buf.cursor == 0) {
/*  968 */       return false;
/*      */     }
/*      */     
/*  971 */     backspaceAll();
/*      */     
/*  973 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  981 */   public final boolean setCursorPosition(int position) throws IOException { return (moveCursor(position - this.buf.cursor) != 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void setBuffer(String buffer) {
/*  993 */     if (buffer.equals(this.buf.buffer.toString())) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  998 */     int sameIndex = 0;
/*      */     
/* 1000 */     int i = 0, l1 = buffer.length(), l2 = this.buf.buffer.length();
/* 1001 */     for (; i < l1 && i < l2 && 
/* 1002 */       buffer.charAt(i) == this.buf.buffer.charAt(i); i++) {
/* 1003 */       sameIndex++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1009 */     int diff = this.buf.buffer.length() - sameIndex;
/*      */     
/* 1011 */     backspace(diff);
/* 1012 */     killLine();
/* 1013 */     this.buf.buffer.setLength(sameIndex);
/* 1014 */     putString(buffer.substring(sameIndex));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void redrawLine() throws IOException {
/* 1021 */     printCharacter(13);
/* 1022 */     flushConsole();
/* 1023 */     drawLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawLine() throws IOException {
/* 1030 */     if (this.prompt != null) {
/* 1031 */       printString(this.prompt);
/*      */     }
/*      */     
/* 1034 */     printString(this.buf.buffer.toString());
/*      */     
/* 1036 */     if (this.buf.length() != this.buf.cursor) {
/* 1037 */       back(this.buf.length() - this.buf.cursor);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void printNewline() throws IOException {
/* 1044 */     printString(CR);
/* 1045 */     flushConsole();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final String finishBuffer() throws IOException {
/* 1054 */     String str = this.buf.buffer.toString();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1059 */     if (str.length() > 0) {
/* 1060 */       if (this.mask == null && this.useHistory) {
/* 1061 */         this.history.addToHistory(str);
/*      */       } else {
/* 1063 */         this.mask = null;
/*      */       } 
/*      */     }
/*      */     
/* 1067 */     this.history.moveToEnd();
/*      */     
/* 1069 */     this.buf.buffer.setLength(0);
/* 1070 */     this.buf.cursor = 0;
/*      */     
/* 1072 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void putString(String str) {
/* 1079 */     this.buf.write(str);
/* 1080 */     printString(str);
/* 1081 */     drawBuffer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1088 */   public final void printString(String str) { printCharacters(str.toCharArray()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void putChar(int c, boolean print) throws IOException {
/* 1096 */     this.buf.write((char)c);
/*      */     
/* 1098 */     if (print) {
/*      */       
/* 1100 */       if (this.mask == null) {
/* 1101 */         printCharacter(c);
/*      */       
/*      */       }
/* 1104 */       else if (this.mask.charValue() != '\000') {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1109 */         printCharacter(this.mask.charValue());
/*      */       } 
/*      */       
/* 1112 */       drawBuffer();
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
/*      */   private final void drawBuffer(int clear) {
/* 1125 */     char[] chars = this.buf.buffer.substring(this.buf.cursor).toCharArray();
/* 1126 */     if (this.mask != null) {
/* 1127 */       Arrays.fill(chars, this.mask.charValue());
/*      */     }
/* 1129 */     printCharacters(chars);
/*      */     
/* 1131 */     clearAhead(clear);
/* 1132 */     back(chars.length);
/* 1133 */     flushConsole();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1141 */   private final void drawBuffer() throws IOException { drawBuffer(0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void clearAhead(int num) {
/* 1148 */     if (num == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1155 */     printCharacters(' ', num);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1160 */     flushConsole();
/*      */ 
/*      */     
/* 1163 */     back(num);
/*      */     
/* 1165 */     flushConsole();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void back(int num) {
/* 1172 */     printCharacters('\b', num);
/* 1173 */     flushConsole();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void beep() throws IOException {
/* 1180 */     if (!getBellEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/* 1184 */     printCharacter(7);
/*      */     
/* 1186 */     flushConsole();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void printCharacter(int c) {
/* 1194 */     if (c == 9) {
/* 1195 */       char[] cbuf = new char[4];
/* 1196 */       Arrays.fill(cbuf, ' ');
/* 1197 */       this.out.write(cbuf);
/*      */       
/*      */       return;
/*      */     } 
/* 1201 */     this.out.write(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void printCharacters(char[] c) throws IOException {
/*      */     char[] cbuf;
/* 1209 */     int len = 0;
/* 1210 */     for (int i = 0; i < c.length; i++) {
/* 1211 */       if (c[i] == '\t') {
/* 1212 */         len += 4;
/*      */       } else {
/* 1214 */         len++;
/*      */       } 
/*      */     } 
/* 1217 */     if (len == c.length) {
/* 1218 */       cbuf = c;
/*      */     } else {
/* 1220 */       cbuf = new char[len];
/* 1221 */       int pos = 0;
/* 1222 */       for (int i = 0; i < c.length; i++) {
/* 1223 */         if (c[i] == '\t') {
/* 1224 */           Arrays.fill(cbuf, pos, pos + 4, ' ');
/* 1225 */           pos += 4;
/*      */         } else {
/* 1227 */           cbuf[pos] = c[i];
/* 1228 */           pos++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1233 */     this.out.write(cbuf);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void printCharacters(char c, int num) throws IOException {
/* 1238 */     if (num == 1) {
/* 1239 */       printCharacter(c);
/*      */     } else {
/* 1241 */       char[] chars = new char[num];
/* 1242 */       Arrays.fill(chars, c);
/* 1243 */       printCharacters(chars);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1253 */   public final void flushConsole() throws IOException { this.out.flush(); }
/*      */ 
/*      */ 
/*      */   
/* 1257 */   private final int backspaceAll() { return backspace(2147483647); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int backspace(int num) throws IOException {
/* 1266 */     if (this.buf.cursor == 0) {
/* 1267 */       return 0;
/*      */     }
/*      */     
/* 1270 */     int count = 0;
/*      */     
/* 1272 */     count = moveCursor(-1 * num) * -1;
/*      */     
/* 1274 */     this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + count);
/* 1275 */     drawBuffer(count);
/*      */     
/* 1277 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1286 */   public final boolean backspace() { return (backspace(1) == 1); }
/*      */ 
/*      */   
/*      */   private final boolean moveToEnd() {
/* 1290 */     if (moveCursor(1) == 0) {
/* 1291 */       return false;
/*      */     }
/*      */     
/* 1294 */     while (moveCursor(1) != 0);
/*      */ 
/*      */ 
/*      */     
/* 1298 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean deleteCurrentCharacter() {
/* 1306 */     boolean success = (this.buf.buffer.length() > 0);
/* 1307 */     if (!success) {
/* 1308 */       return false;
/*      */     }
/*      */     
/* 1311 */     if (this.buf.cursor == this.buf.buffer.length()) {
/* 1312 */       return false;
/*      */     }
/*      */     
/* 1315 */     this.buf.buffer.deleteCharAt(this.buf.cursor);
/* 1316 */     drawBuffer(1);
/* 1317 */     return true;
/*      */   }
/*      */   
/*      */   private final boolean previousWord() {
/* 1321 */     while (isDelimiter(this.buf.current()) && moveCursor(-1) != 0);
/*      */ 
/*      */ 
/*      */     
/* 1325 */     while (!isDelimiter(this.buf.current()) && moveCursor(-1) != 0);
/*      */ 
/*      */ 
/*      */     
/* 1329 */     return true;
/*      */   }
/*      */   
/*      */   private final boolean nextWord() {
/* 1333 */     while (isDelimiter(this.buf.current()) && moveCursor(1) != 0);
/*      */ 
/*      */ 
/*      */     
/* 1337 */     while (!isDelimiter(this.buf.current()) && moveCursor(1) != 0);
/*      */ 
/*      */ 
/*      */     
/* 1341 */     return true;
/*      */   }
/*      */   
/*      */   private final boolean deletePreviousWord() {
/* 1345 */     while (isDelimiter(this.buf.current()) && backspace());
/*      */ 
/*      */ 
/*      */     
/* 1349 */     while (!isDelimiter(this.buf.current()) && backspace());
/*      */ 
/*      */ 
/*      */     
/* 1353 */     return true;
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
/*      */   public final int moveCursor(int num) throws IOException {
/* 1366 */     int where = num;
/*      */     
/* 1368 */     if (this.buf.cursor == 0 && where < 0) {
/* 1369 */       return 0;
/*      */     }
/*      */     
/* 1372 */     if (this.buf.cursor == this.buf.buffer.length() && where > 0) {
/* 1373 */       return 0;
/*      */     }
/*      */     
/* 1376 */     if (this.buf.cursor + where < 0) {
/* 1377 */       where = -this.buf.cursor;
/* 1378 */     } else if (this.buf.cursor + where > this.buf.buffer.length()) {
/* 1379 */       where = this.buf.buffer.length() - this.buf.cursor;
/*      */     } 
/*      */     
/* 1382 */     moveInternal(where);
/*      */     
/* 1384 */     return where;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void debug(String str) {
/* 1394 */     if (debugger != null) {
/* 1395 */       debugger.println(str);
/* 1396 */       debugger.flush();
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
/*      */   private final void moveInternal(int where) {
/*      */     char c;
/* 1412 */     this.buf.cursor += where;
/*      */ 
/*      */ 
/*      */     
/* 1416 */     if (where < 0) {
/* 1417 */       int len = 0;
/* 1418 */       for (int i = this.buf.cursor; i < this.buf.cursor - where; i++) {
/* 1419 */         if (this.buf.getBuffer().charAt(i) == '\t') {
/* 1420 */           len += 4;
/*      */         } else {
/* 1422 */           len++;
/*      */         } 
/*      */       } 
/* 1425 */       char[] cbuf = new char[len];
/* 1426 */       Arrays.fill(cbuf, '\b');
/* 1427 */       this.out.write(cbuf);
/*      */       return;
/*      */     } 
/* 1430 */     if (this.buf.cursor == 0)
/*      */       return; 
/* 1432 */     if (this.mask != null) {
/* 1433 */       c = this.mask.charValue();
/*      */     } else {
/* 1435 */       printCharacters(this.buf.buffer.substring(this.buf.cursor - where, this.buf.cursor).toCharArray());
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1440 */     if (NULL_MASK.equals(this.mask)) {
/*      */       return;
/*      */     }
/*      */     
/* 1444 */     printCharacters(c, Math.abs(where));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int readVirtualKey() {
/* 1453 */     int c = this.terminal.readVirtualKey(this.in);
/*      */     
/* 1455 */     if (debugger != null) {
/* 1456 */       debug("keystroke: " + c + "");
/*      */     }
/*      */ 
/*      */     
/* 1460 */     clearEcho(c);
/*      */     
/* 1462 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int readCharacter(char[] allowed) throws IOException {
/* 1470 */     Arrays.sort(allowed);
/*      */     char c;
/* 1472 */     while (Arrays.binarySearch(allowed, c = (char)readVirtualKey()) < 0);
/*      */ 
/*      */     
/* 1475 */     return c;
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
/*      */   private final int delete(int num) throws IOException {
/* 1491 */     this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + 1);
/* 1492 */     drawBuffer(1);
/*      */     
/* 1494 */     return 1;
/*      */   }
/*      */   
/*      */   public final boolean replace(int num, String replacement) {
/* 1498 */     this.buf.buffer.replace(this.buf.cursor - num, this.buf.cursor, replacement);
/*      */     try {
/* 1500 */       moveCursor(-num);
/* 1501 */       drawBuffer(Math.max(0, num - replacement.length()));
/* 1502 */       moveCursor(replacement.length());
/* 1503 */     } catch (IOException e) {
/* 1504 */       e.printStackTrace();
/* 1505 */       return false;
/*      */     } 
/* 1507 */     return true;
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
/* 1518 */   public final boolean delete() { return (delete(1) == 1); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1523 */   public void setHistory(History history) { this.history = history; }
/*      */ 
/*      */ 
/*      */   
/* 1527 */   public History getHistory() { return this.history; }
/*      */ 
/*      */ 
/*      */   
/* 1531 */   public void setCompletionHandler(CompletionHandler completionHandler) { this.completionHandler = completionHandler; }
/*      */ 
/*      */ 
/*      */   
/* 1535 */   public CompletionHandler getCompletionHandler() { return this.completionHandler; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1569 */   public void setEchoCharacter(Character echoCharacter) { this.echoCharacter = echoCharacter; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1576 */   public Character getEchoCharacter() { return this.echoCharacter; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void consumeException(Throwable e) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1594 */   private boolean isDelimiter(char c) { return !Character.isLetterOrDigit(c); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1601 */   public void setUseHistory(boolean useHistory) { this.useHistory = useHistory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1608 */   public boolean getUseHistory() { return this.useHistory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1616 */   public void setUsePagination(boolean usePagination) { this.usePagination = usePagination; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1624 */   public boolean getUsePagination() { return this.usePagination; }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\ConsoleReader.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */