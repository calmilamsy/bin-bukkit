/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTimeoutException;
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import com.mysql.jdbc.util.ReadAheadInputStream;
/*      */ import com.mysql.jdbc.util.ResultSetUtil;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.math.BigInteger;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.Socket;
/*      */ import java.net.SocketException;
/*      */ import java.net.URL;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.zip.Deflater;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MysqlIO
/*      */ {
/*      */   private static final int UTF8_CHARSET_INDEX = 33;
/*      */   private static final String CODE_PAGE_1252 = "Cp1252";
/*      */   protected static final int NULL_LENGTH = -1;
/*      */   protected static final int COMP_HEADER_LENGTH = 3;
/*      */   protected static final int MIN_COMPRESS_LEN = 50;
/*      */   protected static final int HEADER_LENGTH = 4;
/*      */   protected static final int AUTH_411_OVERHEAD = 33;
/*   80 */   private static int maxBufferSize = 65535;
/*      */   
/*      */   private static final int CLIENT_COMPRESS = 32;
/*      */   
/*      */   protected static final int CLIENT_CONNECT_WITH_DB = 8;
/*      */   
/*      */   private static final int CLIENT_FOUND_ROWS = 2;
/*      */   
/*      */   private static final int CLIENT_LOCAL_FILES = 128;
/*      */   
/*      */   private static final int CLIENT_LONG_FLAG = 4;
/*      */   
/*      */   private static final int CLIENT_LONG_PASSWORD = 1;
/*      */   
/*      */   private static final int CLIENT_PROTOCOL_41 = 512;
/*      */   
/*      */   private static final int CLIENT_INTERACTIVE = 1024;
/*      */   
/*      */   protected static final int CLIENT_SSL = 2048;
/*      */   
/*      */   private static final int CLIENT_TRANSACTIONS = 8192;
/*      */   protected static final int CLIENT_RESERVED = 16384;
/*      */   protected static final int CLIENT_SECURE_CONNECTION = 32768;
/*      */   private static final int CLIENT_MULTI_QUERIES = 65536;
/*      */   private static final int CLIENT_MULTI_RESULTS = 131072;
/*      */   private static final int SERVER_STATUS_IN_TRANS = 1;
/*      */   private static final int SERVER_STATUS_AUTOCOMMIT = 2;
/*      */   static final int SERVER_MORE_RESULTS_EXISTS = 8;
/*      */   private static final int SERVER_QUERY_NO_GOOD_INDEX_USED = 16;
/*      */   private static final int SERVER_QUERY_NO_INDEX_USED = 32;
/*      */   private static final int SERVER_QUERY_WAS_SLOW = 2048;
/*      */   private static final int SERVER_STATUS_CURSOR_EXISTS = 64;
/*      */   private static final String FALSE_SCRAMBLE = "xxxxxxxx";
/*      */   protected static final int MAX_QUERY_SIZE_TO_LOG = 1024;
/*      */   protected static final int MAX_QUERY_SIZE_TO_EXPLAIN = 1048576;
/*      */   protected static final int INITIAL_PACKET_SIZE = 1024;
/*  116 */   private static String jvmPlatformCharset = null;
/*      */   protected static final String ZERO_DATE_VALUE_MARKER = "0000-00-00";
/*      */   protected static final String ZERO_DATETIME_VALUE_MARKER = "0000-00-00 00:00:00"; private static final int MAX_PACKET_DUMP_LENGTH = 1024; private boolean packetSequenceReset; protected int serverCharsetIndex; private Buffer reusablePacket; private Buffer sendPacket; private Buffer sharedSendPacket; protected BufferedOutputStream mysqlOutput; protected MySQLConnection connection; private Deflater deflater; protected InputStream mysqlInput; private LinkedList packetDebugRingBuffer; private RowData streamingData; protected Socket mysqlConnection; private SocketFactory socketFactory; private SoftReference loadFileBufRef; private SoftReference splitBufRef; protected String host; protected String seed; private String serverVersion; private String socketFactoryClassName; private byte[] packetHeaderBuf; private boolean colDecimalNeedsBump; private boolean hadWarnings; private boolean has41NewNewProt; private boolean hasLongColumnInfo; private boolean isInteractiveClient; private boolean logSlowQueries; private boolean platformDbCharsetMatches; private boolean profileSql; private boolean queryBadIndexUsed; private boolean queryNoIndexUsed; private boolean serverQueryWasSlow; private boolean use41Extensions; private boolean useCompression; private boolean useNewLargePackets; private boolean useNewUpdateCounts; private byte packetSequence; private byte readPacketSequence; private boolean checkPacketSequence; private byte protocolVersion; private int maxAllowedPacket; protected int maxThreeBytes; protected int port; protected int serverCapabilities; private int serverMajorVersion; private int serverMinorVersion; private int oldServerStatus; private int serverStatus; private int serverSubMinorVersion; private int warningCount; protected long clientParam; protected long lastPacketSentTimeMs; protected long lastPacketReceivedTimeMs; private boolean traceProtocol; private boolean enablePacketDebug; private Calendar sessionCalendar; private boolean useConnectWithDb; private boolean needToGrabQueryFromPacket; private boolean autoGenerateTestcaseScript; private long threadId; private boolean useNanosForElapsedTime; private long slowQueryThreshold; private String queryTimingUnits; private boolean useDirectRowUnpack; private int useBufferRowSizeThreshold; private int commandCount; private List statementInterceptors; private ExceptionInterceptor exceptionInterceptor; private int statementExecutionDepth; private boolean useAutoSlowLog; public boolean hasLongColumnInfo() { return this.hasLongColumnInfo; } protected boolean isDataAvailable() { try { return (this.mysqlInput.available() > 0); } catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor()); }  } protected long getLastPacketSentTimeMs() { return this.lastPacketSentTimeMs; } protected long getLastPacketReceivedTimeMs() { return this.lastPacketReceivedTimeMs; } protected ResultSetImpl getResultSet(StatementImpl callingStatement, long columnCount, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, boolean isBinaryEncoded, Field[] metadataFromCache) throws SQLException { Field[] fields = null; if (metadataFromCache == null) { fields = new Field[(int)columnCount]; for (int i = 0; i < columnCount; i++) { Buffer fieldPacket = null; fieldPacket = readPacket(); fields[i] = unpackField(fieldPacket, false); }  } else { for (int i = 0; i < columnCount; i++)
/*      */         skipPacket();  }
/*      */      Buffer packet = reuseAndReadPacket(this.reusablePacket); readServerStatusForResultSets(packet); if (this.connection.versionMeetsMinimum(5, 0, 2) && this.connection.getUseCursorFetch() && isBinaryEncoded && callingStatement != null && callingStatement.getFetchSize() != 0 && callingStatement.getResultSetType() == 1003) { ServerPreparedStatement prepStmt = (ServerPreparedStatement)callingStatement; boolean usingCursor = true; if (this.connection.versionMeetsMinimum(5, 0, 5))
/*      */         usingCursor = ((this.serverStatus & 0x40) != 0);  if (usingCursor) { RowData rows = new RowDataCursor(this, prepStmt, fields); ResultSetImpl rs = buildResultSetWithRows(callingStatement, catalog, fields, rows, resultSetType, resultSetConcurrency, isBinaryEncoded); if (usingCursor)
/*      */           rs.setFetchSize(callingStatement.getFetchSize());  return rs; }
/*      */        }
/*      */      RowData rowData = null; if (!streamResults) { rowData = readSingleRowSet(columnCount, maxRows, resultSetConcurrency, isBinaryEncoded, (metadataFromCache == null) ? fields : metadataFromCache); }
/*      */     else { rowData = new RowDataDynamic(this, (int)columnCount, (metadataFromCache == null) ? fields : metadataFromCache, isBinaryEncoded); this.streamingData = rowData; }
/*  126 */      return buildResultSetWithRows(callingStatement, catalog, (metadataFromCache == null) ? fields : metadataFromCache, rowData, resultSetType, resultSetConcurrency, isBinaryEncoded); } static  { outWriter = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  134 */     try { outWriter = new OutputStreamWriter(new ByteArrayOutputStream());
/*  135 */       jvmPlatformCharset = outWriter.getEncoding(); }
/*      */     finally
/*      */     { try {
/*  138 */         if (outWriter != null) {
/*  139 */           outWriter.close();
/*      */         }
/*  141 */       } catch (IOException ioEx) {} }  }
/*      */   protected final void forceClose() { try { try { if (this.mysqlInput != null) this.mysqlInput.close();  } finally { if (!this.mysqlConnection.isClosed() && !this.mysqlConnection.isInputShutdown()) this.mysqlConnection.shutdownInput();  }  } catch (IOException ioEx) { this.mysqlInput = null; }  try { try { if (this.mysqlOutput != null) this.mysqlOutput.close();  } finally { if (!this.mysqlConnection.isClosed() && !this.mysqlConnection.isOutputShutdown()) this.mysqlConnection.shutdownOutput();  }  } catch (IOException ioEx) { this.mysqlOutput = null; }  try { if (this.mysqlConnection != null) this.mysqlConnection.close();  } catch (IOException ioEx) { this.mysqlConnection = null; }  }
/*      */   protected final void skipPacket() { try { int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4); if (lengthRead < 4) { forceClose(); throw new IOException(Messages.getString("MysqlIO.1")); }  int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16); if (this.traceProtocol) { StringBuffer traceMessageBuf = new StringBuffer(); traceMessageBuf.append(Messages.getString("MysqlIO.2")); traceMessageBuf.append(packetLength); traceMessageBuf.append(Messages.getString("MysqlIO.3")); traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4)); this.connection.getLog().logTrace(traceMessageBuf.toString()); }  byte multiPacketSeq = this.packetHeaderBuf[3]; if (!this.packetSequenceReset) { if (this.enablePacketDebug && this.checkPacketSequence) checkPacketSequencing(multiPacketSeq);  } else { this.packetSequenceReset = false; }  this.readPacketSequence = multiPacketSeq; skipFully(this.mysqlInput, packetLength); } catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor()); } catch (OutOfMemoryError oom) { try { this.connection.realClose(false, false, true, oom); } finally { throw oom; }  }  }
/*      */   protected final Buffer readPacket() throws SQLException { try { int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4); if (lengthRead < 4) { forceClose(); throw new IOException(Messages.getString("MysqlIO.1")); }  int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16); if (packetLength > this.maxAllowedPacket) throw new PacketTooBigException(packetLength, this.maxAllowedPacket);  if (this.traceProtocol) { StringBuffer traceMessageBuf = new StringBuffer(); traceMessageBuf.append(Messages.getString("MysqlIO.2")); traceMessageBuf.append(packetLength); traceMessageBuf.append(Messages.getString("MysqlIO.3")); traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4)); this.connection.getLog().logTrace(traceMessageBuf.toString()); }  byte multiPacketSeq = this.packetHeaderBuf[3]; if (!this.packetSequenceReset) { if (this.enablePacketDebug && this.checkPacketSequence) checkPacketSequencing(multiPacketSeq);  } else { this.packetSequenceReset = false; }  this.readPacketSequence = multiPacketSeq; byte[] buffer = new byte[packetLength + 1]; int numBytesRead = readFully(this.mysqlInput, buffer, 0, packetLength); if (numBytesRead != packetLength)
/*      */         throw new IOException("Short read, expected " + packetLength + " bytes, only read " + numBytesRead);  buffer[packetLength] = 0; Buffer packet = new Buffer(buffer); packet.setBufLength(packetLength + 1); if (this.traceProtocol) { StringBuffer traceMessageBuf = new StringBuffer(); traceMessageBuf.append(Messages.getString("MysqlIO.4")); traceMessageBuf.append(getPacketDumpToLog(packet, packetLength)); this.connection.getLog().logTrace(traceMessageBuf.toString()); }  if (this.enablePacketDebug)
/*      */         enqueuePacketForDebugging(false, false, 0, this.packetHeaderBuf, packet);  if (this.connection.getMaintainTimeStats())
/*      */         this.lastPacketReceivedTimeMs = System.currentTimeMillis();  return packet; } catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor()); } catch (OutOfMemoryError oom) { try { this.connection.realClose(false, false, true, oom); } finally { throw oom; }  while (true); }  }
/*      */   protected final Field unpackField(Buffer packet, boolean extractDefaultValues) throws SQLException { if (this.use41Extensions) { if (this.has41NewNewProt) { int catalogNameStart = packet.getPosition() + 1; int catalogNameLength = packet.fastSkipLenString(); catalogNameStart = adjustStartForFieldLength(catalogNameStart, catalogNameLength); }  int databaseNameStart = packet.getPosition() + 1; int databaseNameLength = packet.fastSkipLenString(); databaseNameStart = adjustStartForFieldLength(databaseNameStart, databaseNameLength); int tableNameStart = packet.getPosition() + 1; int tableNameLength = packet.fastSkipLenString(); tableNameStart = adjustStartForFieldLength(tableNameStart, tableNameLength); int originalTableNameStart = packet.getPosition() + 1; int originalTableNameLength = packet.fastSkipLenString(); originalTableNameStart = adjustStartForFieldLength(originalTableNameStart, originalTableNameLength); int nameStart = packet.getPosition() + 1; int nameLength = packet.fastSkipLenString(); nameStart = adjustStartForFieldLength(nameStart, nameLength); int originalColumnNameStart = packet.getPosition() + 1; int originalColumnNameLength = packet.fastSkipLenString(); originalColumnNameStart = adjustStartForFieldLength(originalColumnNameStart, originalColumnNameLength); packet.readByte(); short charSetNumber = (short)packet.readInt(); long colLength = 0L; if (this.has41NewNewProt) { colLength = packet.readLong(); } else { colLength = packet.readLongInt(); }  int colType = packet.readByte() & 0xFF; short colFlag = 0; if (this.hasLongColumnInfo) { colFlag = (short)packet.readInt(); } else { colFlag = (short)(packet.readByte() & 0xFF); }  int colDecimals = packet.readByte() & 0xFF; int defaultValueStart = -1; int defaultValueLength = -1; if (extractDefaultValues) { defaultValueStart = packet.getPosition() + 1; defaultValueLength = packet.fastSkipLenString(); }  return new Field(this.connection, packet.getByteBuffer(), databaseNameStart, databaseNameLength, tableNameStart, tableNameLength, originalTableNameStart, originalTableNameLength, nameStart, nameLength, originalColumnNameStart, originalColumnNameLength, colLength, colType, colFlag, colDecimals, defaultValueStart, defaultValueLength, charSetNumber); }  int tableNameStart = packet.getPosition() + 1; int tableNameLength = packet.fastSkipLenString(); tableNameStart = adjustStartForFieldLength(tableNameStart, tableNameLength); int nameStart = packet.getPosition() + 1; int nameLength = packet.fastSkipLenString(); nameStart = adjustStartForFieldLength(nameStart, nameLength); int colLength = packet.readnBytes(); int colType = packet.readnBytes(); packet.readByte(); short colFlag = 0; if (this.hasLongColumnInfo) { colFlag = (short)packet.readInt(); } else { colFlag = (short)(packet.readByte() & 0xFF); }  int colDecimals = packet.readByte() & 0xFF; if (this.colDecimalNeedsBump)
/*  149 */       colDecimals++;  return new Field(this.connection, packet.getByteBuffer(), nameStart, nameLength, tableNameStart, tableNameLength, colLength, colType, colFlag, colDecimals); } public MysqlIO(String host, int port, Properties props, String socketFactoryClassName, MySQLConnection conn, int socketTimeout, int useBufferRowSizeThreshold) throws IOException, SQLException { this.packetSequenceReset = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  157 */     this.reusablePacket = null;
/*  158 */     this.sendPacket = null;
/*  159 */     this.sharedSendPacket = null;
/*      */ 
/*      */     
/*  162 */     this.mysqlOutput = null;
/*      */     
/*  164 */     this.deflater = null;
/*  165 */     this.mysqlInput = null;
/*  166 */     this.packetDebugRingBuffer = null;
/*  167 */     this.streamingData = null;
/*      */ 
/*      */     
/*  170 */     this.mysqlConnection = null;
/*  171 */     this.socketFactory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  187 */     this.host = null;
/*      */     
/*  189 */     this.serverVersion = null;
/*  190 */     this.socketFactoryClassName = null;
/*  191 */     this.packetHeaderBuf = new byte[4];
/*  192 */     this.colDecimalNeedsBump = false;
/*  193 */     this.hadWarnings = false;
/*  194 */     this.has41NewNewProt = false;
/*      */ 
/*      */     
/*  197 */     this.hasLongColumnInfo = false;
/*  198 */     this.isInteractiveClient = false;
/*  199 */     this.logSlowQueries = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  205 */     this.platformDbCharsetMatches = true;
/*  206 */     this.profileSql = false;
/*  207 */     this.queryBadIndexUsed = false;
/*  208 */     this.queryNoIndexUsed = false;
/*  209 */     this.serverQueryWasSlow = false;
/*      */ 
/*      */     
/*  212 */     this.use41Extensions = false;
/*  213 */     this.useCompression = false;
/*  214 */     this.useNewLargePackets = false;
/*  215 */     this.useNewUpdateCounts = false;
/*  216 */     this.packetSequence = 0;
/*  217 */     this.readPacketSequence = -1;
/*  218 */     this.checkPacketSequence = false;
/*  219 */     this.protocolVersion = 0;
/*  220 */     this.maxAllowedPacket = 1048576;
/*  221 */     this.maxThreeBytes = 16581375;
/*  222 */     this.port = 3306;
/*      */     
/*  224 */     this.serverMajorVersion = 0;
/*  225 */     this.serverMinorVersion = 0;
/*  226 */     this.oldServerStatus = 0;
/*  227 */     this.serverStatus = 0;
/*  228 */     this.serverSubMinorVersion = 0;
/*  229 */     this.warningCount = 0;
/*  230 */     this.clientParam = 0L;
/*  231 */     this.lastPacketSentTimeMs = 0L;
/*  232 */     this.lastPacketReceivedTimeMs = 0L;
/*  233 */     this.traceProtocol = false;
/*  234 */     this.enablePacketDebug = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  243 */     this.useDirectRowUnpack = true;
/*      */     
/*  245 */     this.commandCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2005 */     this.statementExecutionDepth = 0; this.connection = conn; if (this.connection.getEnablePacketDebug()) this.packetDebugRingBuffer = new LinkedList();  this.traceProtocol = this.connection.getTraceProtocol(); this.useAutoSlowLog = this.connection.getAutoSlowLog(); this.useBufferRowSizeThreshold = useBufferRowSizeThreshold; this.useDirectRowUnpack = this.connection.getUseDirectRowUnpack(); this.logSlowQueries = this.connection.getLogSlowQueries(); this.reusablePacket = new Buffer('Ѐ'); this.sendPacket = new Buffer('Ѐ'); this.port = port; this.host = host; this.socketFactoryClassName = socketFactoryClassName; this.socketFactory = createSocketFactory(); this.exceptionInterceptor = this.connection.getExceptionInterceptor(); try { this.mysqlConnection = this.socketFactory.connect(this.host, this.port, props); if (socketTimeout != 0) try { this.mysqlConnection.setSoTimeout(socketTimeout); } catch (Exception ex) {}  this.mysqlConnection = this.socketFactory.beforeHandshake(); if (this.connection.getUseReadAheadInput()) { this.mysqlInput = new ReadAheadInputStream(this.mysqlConnection.getInputStream(), '䀀', this.connection.getTraceProtocol(), this.connection.getLog()); } else if (this.connection.useUnbufferedInput()) { this.mysqlInput = this.mysqlConnection.getInputStream(); } else { this.mysqlInput = new BufferedInputStream(this.mysqlConnection.getInputStream(), '䀀'); }  this.mysqlOutput = new BufferedOutputStream(this.mysqlConnection.getOutputStream(), '䀀'); this.isInteractiveClient = this.connection.getInteractiveClient(); this.profileSql = this.connection.getProfileSql(); this.sessionCalendar = Calendar.getInstance(); this.autoGenerateTestcaseScript = this.connection.getAutoGenerateTestcaseScript(); this.needToGrabQueryFromPacket = (this.profileSql || this.logSlowQueries || this.autoGenerateTestcaseScript); if (this.connection.getUseNanosForElapsedTime() && Util.nanoTimeAvailable()) { this.useNanosForElapsedTime = true; this.queryTimingUnits = Messages.getString("Nanoseconds"); } else { this.queryTimingUnits = Messages.getString("Milliseconds"); }  if (this.connection.getLogSlowQueries()) calculateSlowQueryThreshold();  } catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, 0L, 0L, ioEx, getExceptionInterceptor()); }  } private int adjustStartForFieldLength(int nameStart, int nameLength) { if (nameLength < 251) return nameStart;  if (nameLength >= 251 && nameLength < 65536) return nameStart + 2;  if (nameLength >= 65536 && nameLength < 16777216) return nameStart + 3;  return nameStart + 8; } protected boolean isSetNeededForAutoCommitMode(boolean autoCommitFlag) { if (this.use41Extensions && this.connection.getElideSetAutoCommits()) { boolean autoCommitModeOnServer = ((this.serverStatus & 0x2) != 0); if (!autoCommitFlag && versionMeetsMinimum(5, 0, 0)) { boolean inTransactionOnServer = ((this.serverStatus & true) != 0); return !inTransactionOnServer; }  return (autoCommitModeOnServer != autoCommitFlag); }  return true; } protected boolean inTransactionOnServer() { return ((this.serverStatus & true) != 0); } protected void changeUser(String userName, String password, String database) throws SQLException { this.packetSequence = -1; int passwordLength = 16; int userLength = (userName != null) ? userName.length() : 0; int databaseLength = (database != null) ? database.length() : 0; int packLength = (userLength + passwordLength + databaseLength) * 2 + 7 + 4 + 33; if ((this.serverCapabilities & 0x8000) != 0) { Buffer changeUserPacket = new Buffer(packLength + 1); changeUserPacket.writeByte((byte)17); if (versionMeetsMinimum(4, 1, 1)) { secureAuth411(changeUserPacket, packLength, userName, password, database, false); } else { secureAuth(changeUserPacket, packLength, userName, password, database, false); }  } else { Buffer packet = new Buffer(packLength); packet.writeByte((byte)17); packet.writeString(userName); if (this.protocolVersion > 9) { packet.writeString(Util.newCrypt(password, this.seed)); } else { packet.writeString(Util.oldCrypt(password, this.seed)); }  boolean localUseConnectWithDb = (this.useConnectWithDb && database != null && database.length() > 0); if (localUseConnectWithDb) packet.writeString(database);  send(packet, packet.getPosition()); checkErrorPacket(); if (!localUseConnectWithDb) changeDatabaseTo(database);  }  } protected Buffer checkErrorPacket() throws SQLException { return checkErrorPacket(-1); }
/*      */   protected void checkForCharsetMismatch() { if (this.connection.getUseUnicode() && this.connection.getEncoding() != null) { String encodingToCheck = jvmPlatformCharset; if (encodingToCheck == null) encodingToCheck = System.getProperty("file.encoding");  if (encodingToCheck == null) { this.platformDbCharsetMatches = false; } else { this.platformDbCharsetMatches = encodingToCheck.equals(this.connection.getEncoding()); }  }  }
/*      */   protected void clearInputStream() { try { int len = this.mysqlInput.available(); while (len > 0) { this.mysqlInput.skip(len); len = this.mysqlInput.available(); }  } catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor()); }  }
/*      */   protected void resetReadPacketSequence() { this.readPacketSequence = 0; }
/* 2009 */   protected boolean shouldIntercept() { return (this.statementInterceptors != null); } protected void dumpPacketRingBuffer() { if (this.packetDebugRingBuffer != null && this.connection.getEnablePacketDebug()) { StringBuffer dumpBuffer = new StringBuffer(); dumpBuffer.append("Last " + this.packetDebugRingBuffer.size() + " packets received from server, from oldest->newest:\n"); dumpBuffer.append("\n"); Iterator ringBufIter = this.packetDebugRingBuffer.iterator(); while (ringBufIter.hasNext()) { dumpBuffer.append((StringBuffer)ringBufIter.next()); dumpBuffer.append("\n"); }  this.connection.getLog().logTrace(dumpBuffer.toString()); }  } protected void explainSlowQuery(byte[] querySQL, String truncatedQuery) throws SQLException { if (StringUtils.startsWithIgnoreCaseAndWs(truncatedQuery, "SELECT")) { PreparedStatement stmt = null; ResultSet rs = null; try { stmt = (PreparedStatement)this.connection.clientPrepareStatement("EXPLAIN ?"); stmt.setBytesNoEscapeNoQuotes(1, querySQL); rs = stmt.executeQuery(); StringBuffer explainResults = new StringBuffer(Messages.getString("MysqlIO.8") + truncatedQuery + Messages.getString("MysqlIO.9")); ResultSetUtil.appendResultSetSlashGStyle(explainResults, rs); this.connection.getLog().logWarn(explainResults.toString()); } catch (SQLException sqlEx) {  } finally { if (rs != null)
/*      */           rs.close();  if (stmt != null)
/*      */           stmt.close();  }  }  } static int getMaxBuf() { return maxBufferSize; } final int getServerMajorVersion() { return this.serverMajorVersion; } final int getServerMinorVersion() { return this.serverMinorVersion; } final int getServerSubMinorVersion() { return this.serverSubMinorVersion; }
/*      */   String getServerVersion() { return this.serverVersion; }
/*      */   void doHandshake(String user, String password, String database) throws SQLException { this.checkPacketSequence = false; this.readPacketSequence = 0; Buffer buf = readPacket(); this.protocolVersion = buf.readByte(); if (this.protocolVersion == -1) { try { this.mysqlConnection.close(); } catch (Exception e) {} int errno = 2000; errno = buf.readInt(); String serverErrorMessage = buf.readString("ASCII", getExceptionInterceptor()); StringBuffer errorBuf = new StringBuffer(Messages.getString("MysqlIO.10")); errorBuf.append(serverErrorMessage); errorBuf.append("\""); String xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes()); throw SQLError.createSQLException(SQLError.get(xOpen) + ", " + errorBuf.toString(), xOpen, errno, getExceptionInterceptor()); }  this.serverVersion = buf.readString("ASCII", getExceptionInterceptor()); int point = this.serverVersion.indexOf('.'); if (point != -1) { try { int n = Integer.parseInt(this.serverVersion.substring(0, point)); this.serverMajorVersion = n; } catch (NumberFormatException NFE1) {} String remaining = this.serverVersion.substring(point + 1, this.serverVersion.length()); point = remaining.indexOf('.'); if (point != -1) { try { int n = Integer.parseInt(remaining.substring(0, point)); this.serverMinorVersion = n; } catch (NumberFormatException nfe) {} remaining = remaining.substring(point + 1, remaining.length()); int pos = 0; while (pos < remaining.length() && remaining.charAt(pos) >= '0' && remaining.charAt(pos) <= '9')
/*      */           pos++;  try { int n = Integer.parseInt(remaining.substring(0, pos)); this.serverSubMinorVersion = n; } catch (NumberFormatException nfe) {} }  }  if (versionMeetsMinimum(4, 0, 8)) { this.maxThreeBytes = 16777215; this.useNewLargePackets = true; } else { this.maxThreeBytes = 16581375; this.useNewLargePackets = false; }  this.colDecimalNeedsBump = versionMeetsMinimum(3, 23, 0); this.colDecimalNeedsBump = !versionMeetsMinimum(3, 23, 15); this.useNewUpdateCounts = versionMeetsMinimum(3, 22, 5); this.threadId = buf.readLong(); this.seed = buf.readString("ASCII", getExceptionInterceptor()); this.serverCapabilities = 0; if (buf.getPosition() < buf.getBufLength())
/*      */       this.serverCapabilities = buf.readInt();  if (versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)) { int position = buf.getPosition(); this.serverCharsetIndex = buf.readByte() & 0xFF; this.serverStatus = buf.readInt(); checkTransactionState(0); buf.setPosition(position + 16); String seedPart2 = buf.readString("ASCII", getExceptionInterceptor()); StringBuffer newSeed = new StringBuffer(20); newSeed.append(this.seed); newSeed.append(seedPart2); this.seed = newSeed.toString(); }  if ((this.serverCapabilities & 0x20) != 0 && this.connection.getUseCompression())
/*      */       this.clientParam |= 0x20L;  this.useConnectWithDb = (database != null && database.length() > 0 && !this.connection.getCreateDatabaseIfNotExist()); if (this.useConnectWithDb)
/*      */       this.clientParam |= 0x8L;  if ((this.serverCapabilities & 0x800) == 0 && this.connection.getUseSSL()) { if (this.connection.getRequireSSL()) { this.connection.close(); forceClose(); throw SQLError.createSQLException(Messages.getString("MysqlIO.15"), "08001", getExceptionInterceptor()); }  this.connection.setUseSSL(false); }  if ((this.serverCapabilities & 0x4) != 0) { this.clientParam |= 0x4L; this.hasLongColumnInfo = true; }  if (!this.connection.getUseAffectedRows())
/*      */       this.clientParam |= 0x2L;  if (this.connection.getAllowLoadLocalInfile())
/*      */       this.clientParam |= 0x80L;  if (this.isInteractiveClient)
/*      */       this.clientParam |= 0x400L;  if (this.protocolVersion > 9) { this.clientParam |= 0x1L; } else { this.clientParam &= 0xFFFFFFFFFFFFFFFEL; }  if (versionMeetsMinimum(4, 1, 0) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x4000) != 0)) { if (versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)) { this.clientParam |= 0x200L; this.has41NewNewProt = true; this.clientParam |= 0x2000L; this.clientParam |= 0x20000L; if (this.connection.getAllowMultiQueries())
/*      */           this.clientParam |= 0x10000L;  } else { this.clientParam |= 0x4000L; this.has41NewNewProt = false; }  this.use41Extensions = true; }  int passwordLength = 16; int userLength = (user != null) ? user.length() : 0; int databaseLength = (database != null) ? database.length() : 0; int packLength = (userLength + passwordLength + databaseLength) * 2 + 7 + 4 + 33; Buffer packet = null; if (!this.connection.getUseSSL()) { if ((this.serverCapabilities & 0x8000) != 0) { this.clientParam |= 0x8000L; if (versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)) { secureAuth411(null, packLength, user, password, database, true); } else { secureAuth(null, packLength, user, password, database, true); }  } else { packet = new Buffer(packLength); if ((this.clientParam & 0x4000L) != 0L) { if (versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)) { packet.writeLong(this.clientParam); packet.writeLong(this.maxThreeBytes); packet.writeByte((byte)8); packet.writeBytesNoNull(new byte[23]); } else { packet.writeLong(this.clientParam); packet.writeLong(this.maxThreeBytes); }  } else { packet.writeInt((int)this.clientParam); packet.writeLongInt(this.maxThreeBytes); }  packet.writeString(user, "Cp1252", this.connection); if (this.protocolVersion > 9) { packet.writeString(Util.newCrypt(password, this.seed), "Cp1252", this.connection); } else { packet.writeString(Util.oldCrypt(password, this.seed), "Cp1252", this.connection); }  if (this.useConnectWithDb)
/*      */           packet.writeString(database, "Cp1252", this.connection);  send(packet, packet.getPosition()); }  }
/*      */     else { negotiateSSLConnection(user, password, database, packLength); }
/*      */      if (!versionMeetsMinimum(4, 1, 1) && this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0)
/*      */       checkErrorPacket();  if ((this.serverCapabilities & 0x20) != 0 && this.connection.getUseCompression()) { this.deflater = new Deflater(); this.useCompression = true; this.mysqlInput = new CompressedInputStream(this.connection, this.mysqlInput); }
/*      */      if (!this.useConnectWithDb)
/*      */       changeDatabaseTo(database);  try { this.mysqlConnection = this.socketFactory.afterHandshake(); }
/*      */     catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor()); }
/*      */      }
/*      */   private void changeDatabaseTo(String database) throws SQLException { if (database == null || database.length() == 0)
/*      */       return;  try { sendCommand(2, database, null, false, null, 0); }
/*      */     catch (Exception ex) { if (this.connection.getCreateDatabaseIfNotExist()) { sendCommand(3, "CREATE DATABASE IF NOT EXISTS " + database, null, false, null, 0); sendCommand(2, database, null, false, null, 0); }
/*      */       else { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ex, getExceptionInterceptor()); }
/*      */        }
/*      */      }
/* 2036 */   final ResultSetInternalMethods sqlQueryDirect(StatementImpl callingStatement, String query, String characterEncoding, Buffer queryPacket, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Field[] cachedMetadata) throws Exception { this.statementExecutionDepth++;
/*      */ 
/*      */     
/* 2039 */     try { if (this.statementInterceptors != null) {
/* 2040 */         ResultSetInternalMethods interceptedResults = invokeStatementInterceptorsPre(query, callingStatement, false);
/*      */ 
/*      */         
/* 2043 */         if (interceptedResults != null) {
/* 2044 */           return interceptedResults;
/*      */         }
/*      */       } 
/*      */       
/* 2048 */       long queryStartTime = 0L;
/* 2049 */       long queryEndTime = 0L;
/*      */       
/* 2051 */       if (query != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2056 */         int packLength = 5 + query.length() * 2 + 2;
/*      */         
/* 2058 */         String statementComment = this.connection.getStatementComment();
/*      */         
/* 2060 */         byte[] commentAsBytes = null;
/*      */         
/* 2062 */         if (statementComment != null) {
/* 2063 */           commentAsBytes = StringUtils.getBytes(statementComment, null, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2068 */           packLength += commentAsBytes.length;
/* 2069 */           packLength += 6;
/*      */         } 
/*      */         
/* 2072 */         if (this.sendPacket == null) {
/* 2073 */           this.sendPacket = new Buffer(packLength);
/*      */         } else {
/* 2075 */           this.sendPacket.clear();
/*      */         } 
/*      */         
/* 2078 */         this.sendPacket.writeByte((byte)3);
/*      */         
/* 2080 */         if (commentAsBytes != null) {
/* 2081 */           this.sendPacket.writeBytesNoNull(Constants.SLASH_STAR_SPACE_AS_BYTES);
/* 2082 */           this.sendPacket.writeBytesNoNull(commentAsBytes);
/* 2083 */           this.sendPacket.writeBytesNoNull(Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
/*      */         } 
/*      */         
/* 2086 */         if (characterEncoding != null) {
/* 2087 */           if (this.platformDbCharsetMatches) {
/* 2088 */             this.sendPacket.writeStringNoNull(query, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 2093 */           else if (StringUtils.startsWithIgnoreCaseAndWs(query, "LOAD DATA")) {
/* 2094 */             this.sendPacket.writeBytesNoNull(query.getBytes());
/*      */           } else {
/* 2096 */             this.sendPacket.writeStringNoNull(query, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2104 */           this.sendPacket.writeStringNoNull(query);
/*      */         } 
/*      */         
/* 2107 */         queryPacket = this.sendPacket;
/*      */       } 
/*      */       
/* 2110 */       byte[] queryBuf = null;
/* 2111 */       int oldPacketPosition = 0;
/*      */       
/* 2113 */       if (this.needToGrabQueryFromPacket) {
/* 2114 */         queryBuf = queryPacket.getByteBuffer();
/*      */ 
/*      */         
/* 2117 */         oldPacketPosition = queryPacket.getPosition();
/*      */         
/* 2119 */         queryStartTime = getCurrentTimeNanosOrMillis();
/*      */       } 
/*      */       
/* 2122 */       if (this.autoGenerateTestcaseScript) {
/* 2123 */         String testcaseQuery = null;
/*      */         
/* 2125 */         if (query != null) {
/* 2126 */           testcaseQuery = query;
/*      */         } else {
/* 2128 */           testcaseQuery = new String(queryBuf, 5, oldPacketPosition - 5);
/*      */         } 
/*      */ 
/*      */         
/* 2132 */         StringBuffer debugBuf = new StringBuffer(testcaseQuery.length() + 32);
/* 2133 */         this.connection.generateConnectionCommentBlock(debugBuf);
/* 2134 */         debugBuf.append(testcaseQuery);
/* 2135 */         debugBuf.append(';');
/* 2136 */         this.connection.dumpTestcaseQuery(debugBuf.toString());
/*      */       } 
/*      */ 
/*      */       
/* 2140 */       Buffer resultPacket = sendCommand(3, null, queryPacket, false, null, 0);
/*      */ 
/*      */       
/* 2143 */       long fetchBeginTime = 0L;
/* 2144 */       long fetchEndTime = 0L;
/*      */       
/* 2146 */       String profileQueryToLog = null;
/*      */       
/* 2148 */       boolean queryWasSlow = false;
/*      */       
/* 2150 */       if (this.profileSql || this.logSlowQueries) {
/* 2151 */         queryEndTime = System.currentTimeMillis();
/*      */         
/* 2153 */         boolean shouldExtractQuery = false;
/*      */         
/* 2155 */         if (this.profileSql) {
/* 2156 */           shouldExtractQuery = true;
/* 2157 */         } else if (this.logSlowQueries) {
/* 2158 */           long queryTime = queryEndTime - queryStartTime;
/*      */           
/* 2160 */           boolean logSlow = false;
/*      */           
/* 2162 */           if (this.useAutoSlowLog) {
/* 2163 */             logSlow = (queryTime > this.connection.getSlowQueryThresholdMillis());
/*      */           } else {
/* 2165 */             logSlow = this.connection.isAbonormallyLongQuery(queryTime);
/*      */             
/* 2167 */             this.connection.reportQueryTime(queryTime);
/*      */           } 
/*      */           
/* 2170 */           if (logSlow) {
/* 2171 */             shouldExtractQuery = true;
/* 2172 */             queryWasSlow = true;
/*      */           } 
/*      */         } 
/*      */         
/* 2176 */         if (shouldExtractQuery) {
/*      */           
/* 2178 */           boolean truncated = false;
/*      */           
/* 2180 */           int extractPosition = oldPacketPosition;
/*      */           
/* 2182 */           if (oldPacketPosition > this.connection.getMaxQuerySizeToLog()) {
/* 2183 */             extractPosition = this.connection.getMaxQuerySizeToLog() + 5;
/* 2184 */             truncated = true;
/*      */           } 
/*      */           
/* 2187 */           profileQueryToLog = new String(queryBuf, 5, extractPosition - 5);
/*      */ 
/*      */           
/* 2190 */           if (truncated) {
/* 2191 */             profileQueryToLog = profileQueryToLog + Messages.getString("MysqlIO.25");
/*      */           }
/*      */         } 
/*      */         
/* 2195 */         fetchBeginTime = queryEndTime;
/*      */       } 
/*      */       
/* 2198 */       ResultSetInternalMethods rs = readAllResults(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, resultPacket, false, -1L, cachedMetadata);
/*      */ 
/*      */ 
/*      */       
/* 2202 */       if (queryWasSlow && !this.serverQueryWasSlow) {
/* 2203 */         StringBuffer mesgBuf = new StringBuffer(48 + profileQueryToLog.length());
/*      */ 
/*      */         
/* 2206 */         mesgBuf.append(Messages.getString("MysqlIO.SlowQuery", new Object[] { new Long(this.slowQueryThreshold), this.queryTimingUnits, new Long(queryEndTime - queryStartTime) }));
/*      */ 
/*      */ 
/*      */         
/* 2210 */         mesgBuf.append(profileQueryToLog);
/*      */         
/* 2212 */         ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */         
/* 2214 */         eventSink.consumeEvent(new ProfilerEvent(6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), (int)(queryEndTime - queryStartTime), this.queryTimingUnits, null, new Throwable(), mesgBuf.toString()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2221 */         if (this.connection.getExplainSlowQueries()) {
/* 2222 */           if (oldPacketPosition < 1048576) {
/* 2223 */             explainSlowQuery(queryPacket.getBytes(5, oldPacketPosition - 5), profileQueryToLog);
/*      */           } else {
/*      */             
/* 2226 */             this.connection.getLog().logWarn(Messages.getString("MysqlIO.28") + 1048576 + Messages.getString("MysqlIO.29"));
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2234 */       if (this.logSlowQueries) {
/*      */         
/* 2236 */         ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */         
/* 2238 */         if (this.queryBadIndexUsed && this.profileSql) {
/* 2239 */           eventSink.consumeEvent(new ProfilerEvent(6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.33") + profileQueryToLog));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2252 */         if (this.queryNoIndexUsed && this.profileSql) {
/* 2253 */           eventSink.consumeEvent(new ProfilerEvent(6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.35") + profileQueryToLog));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2266 */         if (this.serverQueryWasSlow && this.profileSql) {
/* 2267 */           eventSink.consumeEvent(new ProfilerEvent(6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.ServerSlowQuery") + profileQueryToLog));
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2281 */       if (this.profileSql) {
/* 2282 */         fetchEndTime = getCurrentTimeNanosOrMillis();
/*      */         
/* 2284 */         ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */         
/* 2286 */         eventSink.consumeEvent(new ProfilerEvent(3, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), profileQueryToLog));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2294 */         eventSink.consumeEvent(new ProfilerEvent(5, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), fetchEndTime - fetchBeginTime, this.queryTimingUnits, null, new Throwable(), null));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2303 */       if (this.hadWarnings) {
/* 2304 */         scanForAndThrowDataTruncation();
/*      */       }
/*      */       
/* 2307 */       if (this.statementInterceptors != null) {
/* 2308 */         ResultSetInternalMethods interceptedResults = invokeStatementInterceptorsPost(query, callingStatement, rs, false, null);
/*      */ 
/*      */         
/* 2311 */         if (interceptedResults != null) {
/* 2312 */           rs = interceptedResults;
/*      */         }
/*      */       } 
/*      */       
/* 2316 */       return rs; }
/* 2317 */     catch (SQLException sqlEx)
/* 2318 */     { if (this.statementInterceptors != null) {
/* 2319 */         invokeStatementInterceptorsPost(query, callingStatement, null, false, sqlEx);
/*      */       }
/*      */ 
/*      */       
/* 2323 */       if (callingStatement != null) {
/* 2324 */         synchronized (callingStatement.cancelTimeoutMutex) {
/* 2325 */           if (callingStatement.wasCancelled) {
/* 2326 */             MySQLStatementCancelledException mySQLStatementCancelledException = null;
/*      */             
/* 2328 */             if (callingStatement.wasCancelledByTimeout) {
/* 2329 */               MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException();
/*      */             } else {
/* 2331 */               mySQLStatementCancelledException = new MySQLStatementCancelledException();
/*      */             } 
/*      */             
/* 2334 */             callingStatement.resetCancelledState();
/*      */             
/* 2336 */             throw mySQLStatementCancelledException;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 2341 */       throw sqlEx; }
/*      */     finally
/* 2343 */     { this.statementExecutionDepth--; }  } final ResultSetRow nextRow(Field[] fields, int columnCount, boolean isBinaryEncoded, int resultSetConcurrency, boolean useBufferRowIfPossible, boolean useBufferRowExplicit, boolean canReuseRowPacketForBufferRow, Buffer existingRowPacket) throws SQLException { if (this.useDirectRowUnpack && existingRowPacket == null && !isBinaryEncoded && !useBufferRowIfPossible && !useBufferRowExplicit) return nextRowFast(fields, columnCount, isBinaryEncoded, resultSetConcurrency, useBufferRowIfPossible, useBufferRowExplicit, canReuseRowPacketForBufferRow);  Buffer rowPacket = null; if (existingRowPacket == null) { rowPacket = checkErrorPacket(); if (!useBufferRowExplicit && useBufferRowIfPossible && rowPacket.getBufLength() > this.useBufferRowSizeThreshold) useBufferRowExplicit = true;  } else { rowPacket = existingRowPacket; checkErrorPacket(existingRowPacket); }  if (!isBinaryEncoded) { rowPacket.setPosition(rowPacket.getPosition() - 1); if (!rowPacket.isLastDataPacket()) { if (resultSetConcurrency == 1008 || (!useBufferRowIfPossible && !useBufferRowExplicit)) { byte[][] rowData = new byte[columnCount][]; for (int i = 0; i < columnCount; i++) rowData[i] = rowPacket.readLenByteArray(0);  return new ByteArrayRow(rowData, getExceptionInterceptor()); }  if (!canReuseRowPacketForBufferRow) this.reusablePacket = new Buffer(rowPacket.getBufLength());  return new BufferRow(rowPacket, fields, false, getExceptionInterceptor()); }  readServerStatusForResultSets(rowPacket); return null; }  if (!rowPacket.isLastDataPacket()) { if (resultSetConcurrency == 1008 || (!useBufferRowIfPossible && !useBufferRowExplicit)) return unpackBinaryResultSetRow(fields, rowPacket, resultSetConcurrency);  if (!canReuseRowPacketForBufferRow) this.reusablePacket = new Buffer(rowPacket.getBufLength());  return new BufferRow(rowPacket, fields, true, getExceptionInterceptor()); }  rowPacket.setPosition(rowPacket.getPosition() - 1); readServerStatusForResultSets(rowPacket); return null; }
/*      */   final ResultSetRow nextRowFast(Field[] fields, int columnCount, boolean isBinaryEncoded, int resultSetConcurrency, boolean useBufferRowIfPossible, boolean useBufferRowExplicit, boolean canReuseRowPacket) throws SQLException { try { int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4); if (lengthRead < 4) { forceClose(); throw new RuntimeException(Messages.getString("MysqlIO.43")); }  int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16); if (packetLength == this.maxThreeBytes) { reuseAndReadPacket(this.reusablePacket, packetLength); return nextRow(fields, columnCount, isBinaryEncoded, resultSetConcurrency, useBufferRowIfPossible, useBufferRowExplicit, canReuseRowPacket, this.reusablePacket); }  if (packetLength > this.useBufferRowSizeThreshold) { reuseAndReadPacket(this.reusablePacket, packetLength); return nextRow(fields, columnCount, isBinaryEncoded, resultSetConcurrency, true, true, false, this.reusablePacket); }  int remaining = packetLength; boolean firstTime = true; byte[][] rowData = (byte[][])null; for (int i = 0; i < columnCount; i++) { int sw = this.mysqlInput.read() & 0xFF; remaining--; if (firstTime) { if (sw == 255) { Buffer errorPacket = new Buffer(packetLength + 4); errorPacket.setPosition(0); errorPacket.writeByte(this.packetHeaderBuf[0]); errorPacket.writeByte(this.packetHeaderBuf[1]); errorPacket.writeByte(this.packetHeaderBuf[2]); errorPacket.writeByte((byte)1); errorPacket.writeByte((byte)sw); readFully(this.mysqlInput, errorPacket.getByteBuffer(), 5, packetLength - 1); errorPacket.setPosition(4); checkErrorPacket(errorPacket); }  if (sw == 254 && packetLength < 9) { if (this.use41Extensions) { this.warningCount = this.mysqlInput.read() & 0xFF | (this.mysqlInput.read() & 0xFF) << 8; remaining -= 2; if (this.warningCount > 0) this.hadWarnings = true;  this.oldServerStatus = this.serverStatus; this.serverStatus = this.mysqlInput.read() & 0xFF | (this.mysqlInput.read() & 0xFF) << 8; checkTransactionState(this.oldServerStatus); remaining -= 2; if (remaining > 0) skipFully(this.mysqlInput, remaining);  }  return null; }  rowData = new byte[columnCount][]; firstTime = false; }  int len = 0; switch (sw) { case 251: len = -1; break;case 252: len = this.mysqlInput.read() & 0xFF | (this.mysqlInput.read() & 0xFF) << 8; remaining -= 2; break;case 253: len = this.mysqlInput.read() & 0xFF | (this.mysqlInput.read() & 0xFF) << 8 | (this.mysqlInput.read() & 0xFF) << 16; remaining -= 3; break;case 254: len = (int)((this.mysqlInput.read() & 0xFF) | (this.mysqlInput.read() & 0xFF) << 8 | (this.mysqlInput.read() & 0xFF) << 16 | (this.mysqlInput.read() & 0xFF) << 24 | (this.mysqlInput.read() & 0xFF) << 32 | (this.mysqlInput.read() & 0xFF) << 40 | (this.mysqlInput.read() & 0xFF) << 48 | (this.mysqlInput.read() & 0xFF) << 56); remaining -= 8; break;default: len = sw; break; }  if (len == -1) { rowData[i] = null; } else if (len == 0) { rowData[i] = Constants.EMPTY_BYTE_ARRAY; } else { rowData[i] = new byte[len]; int bytesRead = readFully(this.mysqlInput, rowData[i], 0, len); if (bytesRead != len) throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException(Messages.getString("MysqlIO.43")), getExceptionInterceptor());  remaining -= bytesRead; }  }  if (remaining > 0) skipFully(this.mysqlInput, remaining);  return new ByteArrayRow(rowData, getExceptionInterceptor()); } catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor()); }  }
/*      */   final void quit() { try { if (!this.mysqlConnection.isClosed())
/*      */         this.mysqlConnection.shutdownInput();  } catch (IOException ioEx) { this.connection.getLog().logWarn("Caught while disconnecting...", ioEx); } finally { forceClose(); }  }
/*      */   Buffer getSharedSendPacket() throws SQLException { if (this.sharedSendPacket == null)
/*      */       this.sharedSendPacket = new Buffer('Ѐ');  return this.sharedSendPacket; }
/* 2349 */   ResultSetInternalMethods invokeStatementInterceptorsPre(String sql, Statement interceptedStatement, boolean forceExecute) throws SQLException { ResultSetInternalMethods previousResultSet = null;
/*      */     
/* 2351 */     Iterator interceptors = this.statementInterceptors.iterator();
/*      */     
/* 2353 */     while (interceptors.hasNext()) {
/* 2354 */       StatementInterceptorV2 interceptor = (StatementInterceptorV2)interceptors.next();
/*      */ 
/*      */       
/* 2357 */       boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
/* 2358 */       boolean shouldExecute = ((executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute)) || !executeTopLevelOnly);
/*      */ 
/*      */       
/* 2361 */       if (shouldExecute) {
/* 2362 */         String sqlToInterceptor = sql;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2369 */         ResultSetInternalMethods interceptedResultSet = interceptor.preProcess(sqlToInterceptor, interceptedStatement, this.connection);
/*      */ 
/*      */ 
/*      */         
/* 2373 */         if (interceptedResultSet != null) {
/* 2374 */           previousResultSet = interceptedResultSet;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2379 */     return previousResultSet; } void closeStreamer(RowData streamer) throws SQLException { if (this.streamingData == null) throw SQLError.createSQLException(Messages.getString("MysqlIO.17") + streamer + Messages.getString("MysqlIO.18"), getExceptionInterceptor());  if (streamer != this.streamingData) throw SQLError.createSQLException(Messages.getString("MysqlIO.19") + streamer + Messages.getString("MysqlIO.20") + Messages.getString("MysqlIO.21") + Messages.getString("MysqlIO.22"), getExceptionInterceptor());  this.streamingData = null; } boolean tackOnMoreStreamingResults(ResultSetImpl addingTo) throws SQLException { if ((this.serverStatus & 0x8) != 0) { boolean moreRowSetsExist = true; ResultSetImpl currentResultSet = addingTo; boolean firstTime = true; while (moreRowSetsExist && (firstTime || !currentResultSet.reallyResult())) { firstTime = false; Buffer fieldPacket = checkErrorPacket(); fieldPacket.setPosition(0); Statement owningStatement = addingTo.getStatement(); int maxRows = owningStatement.getMaxRows(); ResultSetImpl newResultSet = readResultsForQueryOrUpdate((StatementImpl)owningStatement, maxRows, owningStatement.getResultSetType(), owningStatement.getResultSetConcurrency(), true, owningStatement.getConnection().getCatalog(), fieldPacket, addingTo.isBinaryEncoded, -1L, null); currentResultSet.setNextResultSet(newResultSet); currentResultSet = newResultSet; moreRowSetsExist = ((this.serverStatus & 0x8) != 0); if (!currentResultSet.reallyResult() && !moreRowSetsExist) return false;  }  return true; }  return false; }
/*      */   ResultSetImpl readAllResults(StatementImpl callingStatement, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Buffer resultPacket, boolean isBinaryEncoded, long preSentColumnCount, Field[] metadataFromCache) throws SQLException { resultPacket.setPosition(resultPacket.getPosition() - 1); ResultSetImpl topLevelResultSet = readResultsForQueryOrUpdate(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, resultPacket, isBinaryEncoded, preSentColumnCount, metadataFromCache); ResultSetImpl currentResultSet = topLevelResultSet; boolean checkForMoreResults = ((this.clientParam & 0x20000L) != 0L); boolean serverHasMoreResults = ((this.serverStatus & 0x8) != 0); if (serverHasMoreResults && streamResults) { if (topLevelResultSet.getUpdateCount() != -1L) tackOnMoreStreamingResults(topLevelResultSet);  reclaimLargeReusablePacket(); return topLevelResultSet; }  boolean moreRowSetsExist = checkForMoreResults & serverHasMoreResults; while (moreRowSetsExist) { Buffer fieldPacket = checkErrorPacket(); fieldPacket.setPosition(0); ResultSetImpl newResultSet = readResultsForQueryOrUpdate(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, fieldPacket, isBinaryEncoded, preSentColumnCount, metadataFromCache); currentResultSet.setNextResultSet(newResultSet); currentResultSet = newResultSet; moreRowSetsExist = ((this.serverStatus & 0x8) != 0); }  if (!streamResults) clearInputStream();  reclaimLargeReusablePacket(); return topLevelResultSet; }
/*      */   void resetMaxBuf() { this.maxAllowedPacket = this.connection.getMaxAllowedPacket(); }
/*      */   final Buffer sendCommand(int command, String extraData, Buffer queryPacket, boolean skipCheck, String extraDataCharEncoding, int timeoutMillis) throws SQLException { this.commandCount++; this.enablePacketDebug = this.connection.getEnablePacketDebug(); this.readPacketSequence = 0; int oldTimeout = 0; if (timeoutMillis != 0) try { oldTimeout = this.mysqlConnection.getSoTimeout(); this.mysqlConnection.setSoTimeout(timeoutMillis); } catch (SocketException e) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, e, getExceptionInterceptor()); }   try { checkForOutstandingStreamingData(); this.oldServerStatus = this.serverStatus; this.serverStatus = 0; this.hadWarnings = false; this.warningCount = 0; this.queryNoIndexUsed = false; this.queryBadIndexUsed = false; this.serverQueryWasSlow = false; if (this.useCompression) { int bytesLeft = this.mysqlInput.available(); if (bytesLeft > 0) this.mysqlInput.skip(bytesLeft);  }  try { clearInputStream(); if (queryPacket == null) { int packLength = 8 + ((extraData != null) ? extraData.length() : 0) + 2; if (this.sendPacket == null)
/*      */             this.sendPacket = new Buffer(packLength);  this.packetSequence = -1; this.readPacketSequence = 0; this.checkPacketSequence = true; this.sendPacket.clear(); this.sendPacket.writeByte((byte)command); if (command == 2 || command == 5 || command == 6 || command == 3 || command == 22) { if (extraDataCharEncoding == null) { this.sendPacket.writeStringNoNull(extraData); } else { this.sendPacket.writeStringNoNull(extraData, extraDataCharEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection); }  } else if (command == 12) { long id = Long.parseLong(extraData); this.sendPacket.writeLong(id); }  send(this.sendPacket, this.sendPacket.getPosition()); } else { this.packetSequence = -1; send(queryPacket, queryPacket.getPosition()); }  } catch (SQLException sqlEx) { throw sqlEx; } catch (Exception ex) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ex, getExceptionInterceptor()); }  Buffer returnPacket = null; if (!skipCheck) { if (command == 23 || command == 26) { this.readPacketSequence = 0; this.packetSequenceReset = true; }  returnPacket = checkErrorPacket(command); }  return returnPacket; } catch (IOException ioEx) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor()); } finally { if (timeoutMillis != 0)
/*      */         try { this.mysqlConnection.setSoTimeout(oldTimeout); } catch (SocketException e) { throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, e, getExceptionInterceptor()); }   }  }
/* 2385 */   ResultSetInternalMethods invokeStatementInterceptorsPost(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, boolean forceExecute, SQLException statementException) throws SQLException { Iterator interceptors = this.statementInterceptors.iterator();
/*      */     
/* 2387 */     while (interceptors.hasNext()) {
/* 2388 */       StatementInterceptorV2 interceptor = (StatementInterceptorV2)interceptors.next();
/*      */ 
/*      */       
/* 2391 */       boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
/* 2392 */       boolean shouldExecute = ((executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute)) || !executeTopLevelOnly);
/*      */ 
/*      */       
/* 2395 */       if (shouldExecute) {
/* 2396 */         String sqlToInterceptor = sql;
/*      */         
/* 2398 */         ResultSetInternalMethods interceptedResultSet = interceptor.postProcess(sqlToInterceptor, interceptedStatement, originalResultSet, this.connection, this.warningCount, this.queryNoIndexUsed, this.queryBadIndexUsed, statementException);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2403 */         if (interceptedResultSet != null) {
/* 2404 */           originalResultSet = interceptedResultSet;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2409 */     return originalResultSet; }
/*      */ 
/*      */   
/*      */   private void calculateSlowQueryThreshold() {
/* 2413 */     this.slowQueryThreshold = this.connection.getSlowQueryThresholdMillis();
/*      */     
/* 2415 */     if (this.connection.getUseNanosForElapsedTime()) {
/* 2416 */       long nanosThreshold = this.connection.getSlowQueryThresholdNanos();
/*      */       
/* 2418 */       if (nanosThreshold != 0L) {
/* 2419 */         this.slowQueryThreshold = nanosThreshold;
/*      */       } else {
/* 2421 */         this.slowQueryThreshold *= 1000000L;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected long getCurrentTimeNanosOrMillis() {
/* 2427 */     if (this.useNanosForElapsedTime) {
/* 2428 */       return Util.getCurrentTimeNanosOrMillis();
/*      */     }
/*      */     
/* 2431 */     return System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2440 */   String getHost() { return this.host; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2455 */   boolean isVersion(int major, int minor, int subminor) { return (major == getServerMajorVersion() && minor == getServerMinorVersion() && subminor == getServerSubMinorVersion()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean versionMeetsMinimum(int major, int minor, int subminor) {
/* 2471 */     if (getServerMajorVersion() >= major) {
/* 2472 */       if (getServerMajorVersion() == major) {
/* 2473 */         if (getServerMinorVersion() >= minor) {
/* 2474 */           if (getServerMinorVersion() == minor) {
/* 2475 */             return (getServerSubMinorVersion() >= subminor);
/*      */           }
/*      */ 
/*      */           
/* 2479 */           return true;
/*      */         } 
/*      */ 
/*      */         
/* 2483 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 2487 */       return true;
/*      */     } 
/*      */     
/* 2490 */     return false;
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
/*      */   private static final String getPacketDumpToLog(Buffer packetToDump, int packetLength) {
/* 2504 */     if (packetLength < 1024) {
/* 2505 */       return packetToDump.dump(packetLength);
/*      */     }
/*      */     
/* 2508 */     StringBuffer packetDumpBuf = new StringBuffer('က');
/* 2509 */     packetDumpBuf.append(packetToDump.dump(1024));
/* 2510 */     packetDumpBuf.append(Messages.getString("MysqlIO.36"));
/* 2511 */     packetDumpBuf.append(1024);
/* 2512 */     packetDumpBuf.append(Messages.getString("MysqlIO.37"));
/*      */     
/* 2514 */     return packetDumpBuf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private final int readFully(InputStream in, byte[] b, int off, int len) throws IOException {
/* 2519 */     if (len < 0) {
/* 2520 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/* 2523 */     int n = 0;
/*      */     
/* 2525 */     while (n < len) {
/* 2526 */       int count = in.read(b, off + n, len - n);
/*      */       
/* 2528 */       if (count < 0) {
/* 2529 */         throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[] { new Integer(len), new Integer(n) }));
/*      */       }
/*      */ 
/*      */       
/* 2533 */       n += count;
/*      */     } 
/*      */     
/* 2536 */     return n;
/*      */   }
/*      */   
/*      */   private final long skipFully(InputStream in, long len) throws IOException {
/* 2540 */     if (len < 0L) {
/* 2541 */       throw new IOException("Negative skip length not allowed");
/*      */     }
/*      */     
/* 2544 */     long n = 0L;
/*      */     
/* 2546 */     while (n < len) {
/* 2547 */       long count = in.skip(len - n);
/*      */       
/* 2549 */       if (count < 0L) {
/* 2550 */         throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[] { new Long(len), new Long(n) }));
/*      */       }
/*      */ 
/*      */       
/* 2554 */       n += count;
/*      */     } 
/*      */     
/* 2557 */     return n;
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
/*      */   protected final ResultSetImpl readResultsForQueryOrUpdate(StatementImpl callingStatement, int maxRows, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Buffer resultPacket, boolean isBinaryEncoded, long preSentColumnCount, Field[] metadataFromCache) throws SQLException {
/* 2585 */     long columnCount = resultPacket.readFieldLength();
/*      */     
/* 2587 */     if (columnCount == 0L)
/* 2588 */       return buildResultSetWithUpdates(callingStatement, resultPacket); 
/* 2589 */     if (columnCount == -1L) {
/* 2590 */       String charEncoding = null;
/*      */       
/* 2592 */       if (this.connection.getUseUnicode()) {
/* 2593 */         charEncoding = this.connection.getEncoding();
/*      */       }
/*      */       
/* 2596 */       String fileName = null;
/*      */       
/* 2598 */       if (this.platformDbCharsetMatches) {
/* 2599 */         fileName = (charEncoding != null) ? resultPacket.readString(charEncoding, getExceptionInterceptor()) : resultPacket.readString();
/*      */       }
/*      */       else {
/*      */         
/* 2603 */         fileName = resultPacket.readString();
/*      */       } 
/*      */       
/* 2606 */       return sendFileToServer(callingStatement, fileName);
/*      */     } 
/* 2608 */     return getResultSet(callingStatement, columnCount, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, isBinaryEncoded, metadataFromCache);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2618 */   private int alignPacketSize(int a, int l) { return a + l - 1 & (l - 1 ^ 0xFFFFFFFF); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResultSetImpl buildResultSetWithRows(StatementImpl callingStatement, String catalog, Field[] fields, RowData rows, int resultSetType, int resultSetConcurrency, boolean isBinaryEncoded) throws SQLException {
/* 2626 */     ResultSetImpl rs = null;
/*      */     
/* 2628 */     switch (resultSetConcurrency) {
/*      */       case 1007:
/* 2630 */         rs = ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, false);
/*      */ 
/*      */         
/* 2633 */         if (isBinaryEncoded) {
/* 2634 */           rs.setBinaryEncoded();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2650 */         rs.setResultSetType(resultSetType);
/* 2651 */         rs.setResultSetConcurrency(resultSetConcurrency);
/*      */         
/* 2653 */         return rs;case 1008: rs = ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, true); rs.setResultSetType(resultSetType); rs.setResultSetConcurrency(resultSetConcurrency); return rs;
/*      */     } 
/*      */     return ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, false);
/*      */   }
/*      */   
/*      */   private ResultSetImpl buildResultSetWithUpdates(StatementImpl callingStatement, Buffer resultPacket) throws SQLException {
/* 2659 */     long updateCount = -1L;
/* 2660 */     long updateID = -1L;
/* 2661 */     String info = null;
/*      */     
/*      */     try {
/* 2664 */       if (this.useNewUpdateCounts) {
/* 2665 */         updateCount = resultPacket.newReadLength();
/* 2666 */         updateID = resultPacket.newReadLength();
/*      */       } else {
/* 2668 */         updateCount = resultPacket.readLength();
/* 2669 */         updateID = resultPacket.readLength();
/*      */       } 
/*      */       
/* 2672 */       if (this.use41Extensions) {
/*      */         
/* 2674 */         this.serverStatus = resultPacket.readInt();
/*      */         
/* 2676 */         checkTransactionState(this.oldServerStatus);
/*      */         
/* 2678 */         this.warningCount = resultPacket.readInt();
/*      */         
/* 2680 */         if (this.warningCount > 0) {
/* 2681 */           this.hadWarnings = true;
/*      */         }
/*      */         
/* 2684 */         resultPacket.readByte();
/*      */         
/* 2686 */         setServerSlowQueryFlags();
/*      */       } 
/*      */       
/* 2689 */       if (this.connection.isReadInfoMsgEnabled()) {
/* 2690 */         info = resultPacket.readString(this.connection.getErrorMessageEncoding(), getExceptionInterceptor());
/*      */       }
/* 2692 */     } catch (Exception ex) {
/* 2693 */       SQLException sqlEx = SQLError.createSQLException(SQLError.get("S1000"), "S1000", -1, getExceptionInterceptor());
/*      */       
/* 2695 */       sqlEx.initCause(ex);
/*      */       
/* 2697 */       throw sqlEx;
/*      */     } 
/*      */     
/* 2700 */     ResultSetInternalMethods updateRs = ResultSetImpl.getInstance(updateCount, updateID, this.connection, callingStatement);
/*      */ 
/*      */     
/* 2703 */     if (info != null) {
/* 2704 */       ((ResultSetImpl)updateRs).setServerInfo(info);
/*      */     }
/*      */     
/* 2707 */     return (ResultSetImpl)updateRs;
/*      */   }
/*      */   
/*      */   private void setServerSlowQueryFlags() {
/* 2711 */     this.queryBadIndexUsed = ((this.serverStatus & 0x10) != 0);
/*      */     
/* 2713 */     this.queryNoIndexUsed = ((this.serverStatus & 0x20) != 0);
/*      */     
/* 2715 */     this.serverQueryWasSlow = ((this.serverStatus & 0x800) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkForOutstandingStreamingData() {
/* 2720 */     if (this.streamingData != null) {
/* 2721 */       boolean shouldClobber = this.connection.getClobberStreamingResults();
/*      */       
/* 2723 */       if (!shouldClobber) {
/* 2724 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.39") + this.streamingData + Messages.getString("MysqlIO.40") + Messages.getString("MysqlIO.41") + Messages.getString("MysqlIO.42"), getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2732 */       this.streamingData.getOwner().realClose(false);
/*      */ 
/*      */       
/* 2735 */       clearInputStream();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Buffer compressPacket(Buffer packet, int offset, int packetLen, int headerLength) throws SQLException {
/* 2741 */     packet.writeLongInt(packetLen - headerLength);
/* 2742 */     packet.writeByte((byte)0);
/*      */     
/* 2744 */     int lengthToWrite = 0;
/* 2745 */     int compressedLength = 0;
/* 2746 */     byte[] bytesToCompress = packet.getByteBuffer();
/* 2747 */     byte[] compressedBytes = null;
/* 2748 */     int offsetWrite = 0;
/*      */     
/* 2750 */     if (packetLen < 50) {
/* 2751 */       lengthToWrite = packetLen;
/* 2752 */       compressedBytes = packet.getByteBuffer();
/* 2753 */       compressedLength = 0;
/* 2754 */       offsetWrite = offset;
/*      */     } else {
/* 2756 */       compressedBytes = new byte[bytesToCompress.length * 2];
/*      */       
/* 2758 */       this.deflater.reset();
/* 2759 */       this.deflater.setInput(bytesToCompress, offset, packetLen);
/* 2760 */       this.deflater.finish();
/*      */       
/* 2762 */       int compLen = this.deflater.deflate(compressedBytes);
/*      */       
/* 2764 */       if (compLen > packetLen) {
/* 2765 */         lengthToWrite = packetLen;
/* 2766 */         compressedBytes = packet.getByteBuffer();
/* 2767 */         compressedLength = 0;
/* 2768 */         offsetWrite = offset;
/*      */       } else {
/* 2770 */         lengthToWrite = compLen;
/* 2771 */         headerLength += 3;
/* 2772 */         compressedLength = packetLen;
/*      */       } 
/*      */     } 
/*      */     
/* 2776 */     Buffer compressedPacket = new Buffer(packetLen + headerLength);
/*      */     
/* 2778 */     compressedPacket.setPosition(0);
/* 2779 */     compressedPacket.writeLongInt(lengthToWrite);
/* 2780 */     compressedPacket.writeByte(this.packetSequence);
/* 2781 */     compressedPacket.writeLongInt(compressedLength);
/* 2782 */     compressedPacket.writeBytesNoNull(compressedBytes, offsetWrite, lengthToWrite);
/*      */ 
/*      */     
/* 2785 */     return compressedPacket;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void readServerStatusForResultSets(Buffer rowPacket) throws SQLException {
/* 2790 */     if (this.use41Extensions) {
/* 2791 */       rowPacket.readByte();
/*      */       
/* 2793 */       this.warningCount = rowPacket.readInt();
/*      */       
/* 2795 */       if (this.warningCount > 0) {
/* 2796 */         this.hadWarnings = true;
/*      */       }
/*      */       
/* 2799 */       this.oldServerStatus = this.serverStatus;
/* 2800 */       this.serverStatus = rowPacket.readInt();
/* 2801 */       checkTransactionState(this.oldServerStatus);
/*      */       
/* 2803 */       setServerSlowQueryFlags();
/*      */     } 
/*      */   }
/*      */   
/*      */   private SocketFactory createSocketFactory() throws SQLException {
/*      */     try {
/* 2809 */       if (this.socketFactoryClassName == null) {
/* 2810 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.75"), "08001", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */       
/* 2814 */       return (SocketFactory)Class.forName(this.socketFactoryClassName).newInstance();
/*      */     }
/* 2816 */     catch (Exception ex) {
/* 2817 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("MysqlIO.76") + this.socketFactoryClassName + Messages.getString("MysqlIO.77"), "08001", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2822 */       sqlEx.initCause(ex);
/*      */       
/* 2824 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void enqueuePacketForDebugging(boolean isPacketBeingSent, boolean isPacketReused, int sendLength, byte[] header, Buffer packet) throws SQLException {
/* 2831 */     if (this.packetDebugRingBuffer.size() + 1 > this.connection.getPacketDebugBufferSize()) {
/* 2832 */       this.packetDebugRingBuffer.removeFirst();
/*      */     }
/*      */     
/* 2835 */     StringBuffer packetDump = null;
/*      */     
/* 2837 */     if (!isPacketBeingSent) {
/* 2838 */       int bytesToDump = Math.min(1024, packet.getBufLength());
/*      */ 
/*      */       
/* 2841 */       Buffer packetToDump = new Buffer(4 + bytesToDump);
/*      */       
/* 2843 */       packetToDump.setPosition(0);
/* 2844 */       packetToDump.writeBytesNoNull(header);
/* 2845 */       packetToDump.writeBytesNoNull(packet.getBytes(0, bytesToDump));
/*      */       
/* 2847 */       String packetPayload = packetToDump.dump(bytesToDump);
/*      */       
/* 2849 */       packetDump = new StringBuffer(96 + packetPayload.length());
/*      */       
/* 2851 */       packetDump.append("Server ");
/*      */       
/* 2853 */       if (isPacketReused) {
/* 2854 */         packetDump.append("(re-used)");
/*      */       } else {
/* 2856 */         packetDump.append("(new)");
/*      */       } 
/*      */       
/* 2859 */       packetDump.append(" ");
/* 2860 */       packetDump.append(packet.toSuperString());
/* 2861 */       packetDump.append(" --------------------> Client\n");
/* 2862 */       packetDump.append("\nPacket payload:\n\n");
/* 2863 */       packetDump.append(packetPayload);
/*      */       
/* 2865 */       if (bytesToDump == 1024) {
/* 2866 */         packetDump.append("\nNote: Packet of " + packet.getBufLength() + " bytes truncated to " + 'Ѐ' + " bytes.\n");
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2871 */       int bytesToDump = Math.min(1024, sendLength);
/*      */       
/* 2873 */       String packetPayload = packet.dump(bytesToDump);
/*      */       
/* 2875 */       packetDump = new StringBuffer(68 + packetPayload.length());
/*      */       
/* 2877 */       packetDump.append("Client ");
/* 2878 */       packetDump.append(packet.toSuperString());
/* 2879 */       packetDump.append("--------------------> Server\n");
/* 2880 */       packetDump.append("\nPacket payload:\n\n");
/* 2881 */       packetDump.append(packetPayload);
/*      */       
/* 2883 */       if (bytesToDump == 1024) {
/* 2884 */         packetDump.append("\nNote: Packet of " + sendLength + " bytes truncated to " + 'Ѐ' + " bytes.\n");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2890 */     this.packetDebugRingBuffer.addLast(packetDump);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RowData readSingleRowSet(long columnCount, int maxRows, int resultSetConcurrency, boolean isBinaryEncoded, Field[] fields) throws SQLException {
/* 2897 */     ArrayList rows = new ArrayList();
/*      */     
/* 2899 */     boolean useBufferRowExplicit = useBufferRowExplicit(fields);
/*      */ 
/*      */     
/* 2902 */     ResultSetRow row = nextRow(fields, (int)columnCount, isBinaryEncoded, resultSetConcurrency, false, useBufferRowExplicit, false, null);
/*      */ 
/*      */     
/* 2905 */     int rowCount = 0;
/*      */     
/* 2907 */     if (row != null) {
/* 2908 */       rows.add(row);
/* 2909 */       rowCount = 1;
/*      */     } 
/*      */     
/* 2912 */     while (row != null) {
/* 2913 */       row = nextRow(fields, (int)columnCount, isBinaryEncoded, resultSetConcurrency, false, useBufferRowExplicit, false, null);
/*      */ 
/*      */       
/* 2916 */       if (row != null && (
/* 2917 */         maxRows == -1 || rowCount < maxRows)) {
/* 2918 */         rows.add(row);
/* 2919 */         rowCount++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2924 */     return new RowDataStatic(rows);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean useBufferRowExplicit(Field[] fields) {
/* 2930 */     if (fields == null) {
/* 2931 */       return false;
/*      */     }
/*      */     
/* 2934 */     for (int i = 0; i < fields.length; i++) {
/* 2935 */       switch (fields[i].getSQLType()) {
/*      */         case -4:
/*      */         case -1:
/*      */         case 2004:
/*      */         case 2005:
/* 2940 */           return true;
/*      */       } 
/*      */     
/*      */     } 
/* 2944 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reclaimLargeReusablePacket() {
/* 2951 */     if (this.reusablePacket != null && this.reusablePacket.getCapacity() > 1048576)
/*      */     {
/* 2953 */       this.reusablePacket = new Buffer('Ѐ');
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
/* 2968 */   private final Buffer reuseAndReadPacket(Buffer reuse) throws SQLException { return reuseAndReadPacket(reuse, -1); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Buffer reuseAndReadPacket(Buffer reuse, int existingPacketLength) throws SQLException {
/*      */     try {
/* 2975 */       reuse.setWasMultiPacket(false);
/* 2976 */       int packetLength = 0;
/*      */       
/* 2978 */       if (existingPacketLength == -1) {
/* 2979 */         int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */         
/* 2982 */         if (lengthRead < 4) {
/* 2983 */           forceClose();
/* 2984 */           throw new IOException(Messages.getString("MysqlIO.43"));
/*      */         } 
/*      */         
/* 2987 */         packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */       }
/*      */       else {
/*      */         
/* 2991 */         packetLength = existingPacketLength;
/*      */       } 
/*      */       
/* 2994 */       if (this.traceProtocol) {
/* 2995 */         StringBuffer traceMessageBuf = new StringBuffer();
/*      */         
/* 2997 */         traceMessageBuf.append(Messages.getString("MysqlIO.44"));
/* 2998 */         traceMessageBuf.append(packetLength);
/* 2999 */         traceMessageBuf.append(Messages.getString("MysqlIO.45"));
/* 3000 */         traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
/*      */ 
/*      */         
/* 3003 */         this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */       } 
/*      */       
/* 3006 */       byte multiPacketSeq = this.packetHeaderBuf[3];
/*      */       
/* 3008 */       if (!this.packetSequenceReset) {
/* 3009 */         if (this.enablePacketDebug && this.checkPacketSequence) {
/* 3010 */           checkPacketSequencing(multiPacketSeq);
/*      */         }
/*      */       } else {
/* 3013 */         this.packetSequenceReset = false;
/*      */       } 
/*      */       
/* 3016 */       this.readPacketSequence = multiPacketSeq;
/*      */ 
/*      */       
/* 3019 */       reuse.setPosition(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3027 */       if (reuse.getByteBuffer().length <= packetLength) {
/* 3028 */         reuse.setByteBuffer(new byte[packetLength + 1]);
/*      */       }
/*      */ 
/*      */       
/* 3032 */       reuse.setBufLength(packetLength);
/*      */ 
/*      */       
/* 3035 */       int numBytesRead = readFully(this.mysqlInput, reuse.getByteBuffer(), 0, packetLength);
/*      */ 
/*      */       
/* 3038 */       if (numBytesRead != packetLength) {
/* 3039 */         throw new IOException("Short read, expected " + packetLength + " bytes, only read " + numBytesRead);
/*      */       }
/*      */ 
/*      */       
/* 3043 */       if (this.traceProtocol) {
/* 3044 */         StringBuffer traceMessageBuf = new StringBuffer();
/*      */         
/* 3046 */         traceMessageBuf.append(Messages.getString("MysqlIO.46"));
/* 3047 */         traceMessageBuf.append(getPacketDumpToLog(reuse, packetLength));
/*      */ 
/*      */         
/* 3050 */         this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */       } 
/*      */       
/* 3053 */       if (this.enablePacketDebug) {
/* 3054 */         enqueuePacketForDebugging(false, true, 0, this.packetHeaderBuf, reuse);
/*      */       }
/*      */ 
/*      */       
/* 3058 */       boolean isMultiPacket = false;
/*      */       
/* 3060 */       if (packetLength == this.maxThreeBytes) {
/* 3061 */         reuse.setPosition(this.maxThreeBytes);
/*      */         
/* 3063 */         int packetEndPoint = packetLength;
/*      */ 
/*      */         
/* 3066 */         isMultiPacket = true;
/*      */         
/* 3068 */         packetLength = readRemainingMultiPackets(reuse, multiPacketSeq, packetEndPoint);
/*      */       } 
/*      */ 
/*      */       
/* 3072 */       if (!isMultiPacket) {
/* 3073 */         reuse.getByteBuffer()[packetLength] = 0;
/*      */       }
/*      */       
/* 3076 */       if (this.connection.getMaintainTimeStats()) {
/* 3077 */         this.lastPacketReceivedTimeMs = System.currentTimeMillis();
/*      */       }
/*      */       
/* 3080 */       return reuse;
/* 3081 */     } catch (IOException ioEx) {
/* 3082 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     }
/* 3084 */     catch (OutOfMemoryError oom) {
/*      */       
/*      */       try {
/* 3087 */         clearInputStream();
/*      */       } finally {
/*      */         try {
/* 3090 */           this.connection.realClose(false, false, true, oom);
/*      */         } finally {
/* 3092 */           throw oom;
/*      */         } 
/*      */       } 
/*      */       while (true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readRemainingMultiPackets(Buffer reuse, byte multiPacketSeq, int packetEndPoint) throws IOException, SQLException {
/* 3103 */     int lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */     
/* 3106 */     if (lengthRead < 4) {
/* 3107 */       forceClose();
/* 3108 */       throw new IOException(Messages.getString("MysqlIO.47"));
/*      */     } 
/*      */     
/* 3111 */     int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */ 
/*      */ 
/*      */     
/* 3115 */     Buffer multiPacket = new Buffer(packetLength);
/* 3116 */     boolean firstMultiPkt = true;
/*      */     
/*      */     while (true) {
/* 3119 */       if (!firstMultiPkt) {
/* 3120 */         lengthRead = readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
/*      */ 
/*      */         
/* 3123 */         if (lengthRead < 4) {
/* 3124 */           forceClose();
/* 3125 */           throw new IOException(Messages.getString("MysqlIO.48"));
/*      */         } 
/*      */ 
/*      */         
/* 3129 */         packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
/*      */       }
/*      */       else {
/*      */         
/* 3133 */         firstMultiPkt = false;
/*      */       } 
/*      */       
/* 3136 */       if (!this.useNewLargePackets && packetLength == 1) {
/* 3137 */         clearInputStream();
/*      */         break;
/*      */       } 
/* 3140 */       if (packetLength < this.maxThreeBytes) {
/* 3141 */         byte newPacketSeq = this.packetHeaderBuf[3];
/*      */         
/* 3143 */         if (newPacketSeq != multiPacketSeq + 1) {
/* 3144 */           throw new IOException(Messages.getString("MysqlIO.49"));
/*      */         }
/*      */ 
/*      */         
/* 3148 */         multiPacketSeq = newPacketSeq;
/*      */ 
/*      */         
/* 3151 */         multiPacket.setPosition(0);
/*      */ 
/*      */         
/* 3154 */         multiPacket.setBufLength(packetLength);
/*      */ 
/*      */         
/* 3157 */         byte[] byteBuf = multiPacket.getByteBuffer();
/* 3158 */         int lengthToWrite = packetLength;
/*      */         
/* 3160 */         int bytesRead = readFully(this.mysqlInput, byteBuf, 0, packetLength);
/*      */ 
/*      */         
/* 3163 */         if (bytesRead != lengthToWrite) {
/* 3164 */           throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, SQLError.createSQLException(Messages.getString("MysqlIO.50") + lengthToWrite + Messages.getString("MysqlIO.51") + bytesRead + ".", getExceptionInterceptor()), getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3174 */         reuse.writeBytesNoNull(byteBuf, 0, lengthToWrite);
/*      */         
/* 3176 */         packetEndPoint += lengthToWrite;
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 3181 */       byte newPacketSeq = this.packetHeaderBuf[3];
/*      */       
/* 3183 */       if (newPacketSeq != multiPacketSeq + 1) {
/* 3184 */         throw new IOException(Messages.getString("MysqlIO.53"));
/*      */       }
/*      */ 
/*      */       
/* 3188 */       multiPacketSeq = newPacketSeq;
/*      */ 
/*      */       
/* 3191 */       multiPacket.setPosition(0);
/*      */ 
/*      */       
/* 3194 */       multiPacket.setBufLength(packetLength);
/*      */ 
/*      */       
/* 3197 */       byte[] byteBuf = multiPacket.getByteBuffer();
/* 3198 */       int lengthToWrite = packetLength;
/*      */       
/* 3200 */       int bytesRead = readFully(this.mysqlInput, byteBuf, 0, packetLength);
/*      */ 
/*      */       
/* 3203 */       if (bytesRead != lengthToWrite) {
/* 3204 */         throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, SQLError.createSQLException(Messages.getString("MysqlIO.54") + lengthToWrite + Messages.getString("MysqlIO.55") + bytesRead + ".", getExceptionInterceptor()), getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3213 */       reuse.writeBytesNoNull(byteBuf, 0, lengthToWrite);
/*      */       
/* 3215 */       packetEndPoint += lengthToWrite;
/*      */     } 
/*      */     
/* 3218 */     reuse.setPosition(0);
/* 3219 */     reuse.setWasMultiPacket(true);
/* 3220 */     return packetLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkPacketSequencing(byte multiPacketSeq) throws SQLException {
/* 3229 */     if (multiPacketSeq == Byte.MIN_VALUE && this.readPacketSequence != Byte.MAX_VALUE) {
/* 3230 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # -128, but received packet # " + multiPacketSeq), getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3236 */     if (this.readPacketSequence == -1 && multiPacketSeq != 0) {
/* 3237 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # -1, but received packet # " + multiPacketSeq), getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3243 */     if (multiPacketSeq != Byte.MIN_VALUE && this.readPacketSequence != -1 && multiPacketSeq != this.readPacketSequence + 1)
/*      */     {
/* 3245 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # " + (this.readPacketSequence + 1) + ", but received packet # " + multiPacketSeq), getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void enableMultiQueries() {
/* 3254 */     Buffer buf = getSharedSendPacket();
/*      */     
/* 3256 */     buf.clear();
/* 3257 */     buf.writeByte((byte)27);
/* 3258 */     buf.writeInt(0);
/* 3259 */     sendCommand(27, null, buf, false, null, 0);
/*      */   }
/*      */   
/*      */   void disableMultiQueries() {
/* 3263 */     Buffer buf = getSharedSendPacket();
/*      */     
/* 3265 */     buf.clear();
/* 3266 */     buf.writeByte((byte)27);
/* 3267 */     buf.writeInt(1);
/* 3268 */     sendCommand(27, null, buf, false, null, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void send(Buffer packet, int packetLen) throws SQLException {
/*      */     try {
/* 3274 */       if (this.maxAllowedPacket > 0 && packetLen > this.maxAllowedPacket) {
/* 3275 */         throw new PacketTooBigException(packetLen, this.maxAllowedPacket);
/*      */       }
/*      */       
/* 3278 */       if (this.serverMajorVersion >= 4 && packetLen >= this.maxThreeBytes) {
/*      */         
/* 3280 */         sendSplitPackets(packet);
/*      */       } else {
/* 3282 */         this.packetSequence = (byte)(this.packetSequence + 1);
/*      */         
/* 3284 */         Buffer packetToSend = packet;
/*      */         
/* 3286 */         packetToSend.setPosition(0);
/*      */         
/* 3288 */         if (this.useCompression) {
/* 3289 */           int originalPacketLen = packetLen;
/*      */           
/* 3291 */           packetToSend = compressPacket(packet, 0, packetLen, 4);
/*      */           
/* 3293 */           packetLen = packetToSend.getPosition();
/*      */           
/* 3295 */           if (this.traceProtocol) {
/* 3296 */             StringBuffer traceMessageBuf = new StringBuffer();
/*      */             
/* 3298 */             traceMessageBuf.append(Messages.getString("MysqlIO.57"));
/* 3299 */             traceMessageBuf.append(getPacketDumpToLog(packetToSend, packetLen));
/*      */             
/* 3301 */             traceMessageBuf.append(Messages.getString("MysqlIO.58"));
/* 3302 */             traceMessageBuf.append(getPacketDumpToLog(packet, originalPacketLen));
/*      */ 
/*      */             
/* 3305 */             this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */           } 
/*      */         } else {
/* 3308 */           packetToSend.writeLongInt(packetLen - 4);
/* 3309 */           packetToSend.writeByte(this.packetSequence);
/*      */           
/* 3311 */           if (this.traceProtocol) {
/* 3312 */             StringBuffer traceMessageBuf = new StringBuffer();
/*      */             
/* 3314 */             traceMessageBuf.append(Messages.getString("MysqlIO.59"));
/* 3315 */             traceMessageBuf.append("host: '");
/* 3316 */             traceMessageBuf.append(this.host);
/* 3317 */             traceMessageBuf.append("' threadId: '");
/* 3318 */             traceMessageBuf.append(this.threadId);
/* 3319 */             traceMessageBuf.append("'\n");
/* 3320 */             traceMessageBuf.append(packetToSend.dump(packetLen));
/*      */             
/* 3322 */             this.connection.getLog().logTrace(traceMessageBuf.toString());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 3327 */         this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
/*      */         
/* 3329 */         this.mysqlOutput.flush();
/*      */       } 
/*      */       
/* 3332 */       if (this.enablePacketDebug) {
/* 3333 */         enqueuePacketForDebugging(true, false, packetLen + 5, this.packetHeaderBuf, packet);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3340 */       if (packet == this.sharedSendPacket) {
/* 3341 */         reclaimLargeSharedSendPacket();
/*      */       }
/*      */       
/* 3344 */       if (this.connection.getMaintainTimeStats()) {
/* 3345 */         this.lastPacketSentTimeMs = System.currentTimeMillis();
/*      */       }
/* 3347 */     } catch (IOException ioEx) {
/* 3348 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
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
/*      */   private final ResultSetImpl sendFileToServer(StatementImpl callingStatement, String fileName) throws SQLException {
/* 3366 */     Buffer filePacket = (this.loadFileBufRef == null) ? null : (Buffer)this.loadFileBufRef.get();
/*      */ 
/*      */     
/* 3369 */     int bigPacketLength = Math.min(this.connection.getMaxAllowedPacket() - 12, alignPacketSize(this.connection.getMaxAllowedPacket() - 16, 4096) - 12);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3374 */     int oneMeg = 1048576;
/*      */     
/* 3376 */     int smallerPacketSizeAligned = Math.min(oneMeg - 12, alignPacketSize(oneMeg - 16, 4096) - 12);
/*      */ 
/*      */     
/* 3379 */     int packetLength = Math.min(smallerPacketSizeAligned, bigPacketLength);
/*      */     
/* 3381 */     if (filePacket == null) {
/*      */       try {
/* 3383 */         filePacket = new Buffer(packetLength + 4);
/* 3384 */         this.loadFileBufRef = new SoftReference(filePacket);
/* 3385 */       } catch (OutOfMemoryError oom) {
/* 3386 */         throw SQLError.createSQLException("Could not allocate packet of " + packetLength + " bytes required for LOAD DATA LOCAL INFILE operation." + " Try increasing max heap allocation for JVM or decreasing server variable " + "'max_allowed_packet'", "S1001", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3394 */     filePacket.clear();
/* 3395 */     send(filePacket, 0);
/*      */     
/* 3397 */     byte[] fileBuf = new byte[packetLength];
/*      */     
/* 3399 */     BufferedInputStream fileIn = null;
/*      */     
/*      */     try {
/* 3402 */       if (!this.connection.getAllowLoadLocalInfile()) {
/* 3403 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.LoadDataLocalNotAllowed"), "S1000", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3408 */       InputStream hookedStream = null;
/*      */       
/* 3410 */       if (callingStatement != null) {
/* 3411 */         hookedStream = callingStatement.getLocalInfileInputStream();
/*      */       }
/*      */       
/* 3414 */       if (hookedStream != null) {
/* 3415 */         fileIn = new BufferedInputStream(hookedStream);
/* 3416 */       } else if (!this.connection.getAllowUrlInLocalInfile()) {
/* 3417 */         fileIn = new BufferedInputStream(new FileInputStream(fileName));
/*      */       
/*      */       }
/* 3420 */       else if (fileName.indexOf(':') != -1) {
/*      */         try {
/* 3422 */           URL urlFromFileName = new URL(fileName);
/* 3423 */           fileIn = new BufferedInputStream(urlFromFileName.openStream());
/* 3424 */         } catch (MalformedURLException badUrlEx) {
/*      */           
/* 3426 */           fileIn = new BufferedInputStream(new FileInputStream(fileName));
/*      */         } 
/*      */       } else {
/*      */         
/* 3430 */         fileIn = new BufferedInputStream(new FileInputStream(fileName));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3435 */       int bytesRead = 0;
/*      */       
/* 3437 */       while ((bytesRead = fileIn.read(fileBuf)) != -1) {
/* 3438 */         filePacket.clear();
/* 3439 */         filePacket.writeBytesNoNull(fileBuf, 0, bytesRead);
/* 3440 */         send(filePacket, filePacket.getPosition());
/*      */       } 
/* 3442 */     } catch (IOException ioEx) {
/* 3443 */       StringBuffer messageBuf = new StringBuffer(Messages.getString("MysqlIO.60"));
/*      */ 
/*      */       
/* 3446 */       if (!this.connection.getParanoid()) {
/* 3447 */         messageBuf.append("'");
/*      */         
/* 3449 */         if (fileName != null) {
/* 3450 */           messageBuf.append(fileName);
/*      */         }
/*      */         
/* 3453 */         messageBuf.append("'");
/*      */       } 
/*      */       
/* 3456 */       messageBuf.append(Messages.getString("MysqlIO.63"));
/*      */       
/* 3458 */       if (!this.connection.getParanoid()) {
/* 3459 */         messageBuf.append(Messages.getString("MysqlIO.64"));
/* 3460 */         messageBuf.append(Util.stackTraceToString(ioEx));
/*      */       } 
/*      */       
/* 3463 */       throw SQLError.createSQLException(messageBuf.toString(), "S1009", getExceptionInterceptor());
/*      */     } finally {
/*      */       
/* 3466 */       if (fileIn != null) {
/*      */         try {
/* 3468 */           fileIn.close();
/* 3469 */         } catch (Exception ex) {
/* 3470 */           SQLException sqlEx = SQLError.createSQLException(Messages.getString("MysqlIO.65"), "S1000", getExceptionInterceptor());
/*      */           
/* 3472 */           sqlEx.initCause(ex);
/*      */           
/* 3474 */           throw sqlEx;
/*      */         } 
/*      */         
/* 3477 */         fileIn = null;
/*      */       } else {
/*      */         
/* 3480 */         filePacket.clear();
/* 3481 */         send(filePacket, filePacket.getPosition());
/* 3482 */         checkErrorPacket();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3487 */     filePacket.clear();
/* 3488 */     send(filePacket, filePacket.getPosition());
/*      */     
/* 3490 */     Buffer resultPacket = checkErrorPacket();
/*      */     
/* 3492 */     return buildResultSetWithUpdates(callingStatement, resultPacket);
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
/*      */   private Buffer checkErrorPacket(int command) throws SQLException {
/* 3507 */     int statusCode = 0;
/* 3508 */     Buffer resultPacket = null;
/* 3509 */     this.serverStatus = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3516 */       resultPacket = reuseAndReadPacket(this.reusablePacket);
/* 3517 */     } catch (SQLException sqlEx) {
/*      */       
/* 3519 */       throw sqlEx;
/* 3520 */     } catch (Exception fallThru) {
/* 3521 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, fallThru, getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */     
/* 3525 */     checkErrorPacket(resultPacket);
/*      */     
/* 3527 */     return resultPacket;
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkErrorPacket(Buffer resultPacket) throws SQLException {
/* 3532 */     int statusCode = resultPacket.readByte();
/*      */ 
/*      */     
/* 3535 */     if (statusCode == -1) {
/*      */       
/* 3537 */       int errno = 2000;
/*      */       
/* 3539 */       if (this.protocolVersion > 9) {
/* 3540 */         errno = resultPacket.readInt();
/*      */         
/* 3542 */         String xOpen = null;
/*      */         
/* 3544 */         String serverErrorMessage = resultPacket.readString(this.connection.getErrorMessageEncoding(), getExceptionInterceptor());
/*      */ 
/*      */         
/* 3547 */         if (serverErrorMessage.charAt(0) == '#') {
/*      */ 
/*      */           
/* 3550 */           if (serverErrorMessage.length() > 6) {
/* 3551 */             xOpen = serverErrorMessage.substring(1, 6);
/* 3552 */             serverErrorMessage = serverErrorMessage.substring(6);
/*      */             
/* 3554 */             if (xOpen.equals("HY000")) {
/* 3555 */               xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
/*      */             }
/*      */           } else {
/*      */             
/* 3559 */             xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
/*      */           } 
/*      */         } else {
/*      */           
/* 3563 */           xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
/*      */         } 
/*      */ 
/*      */         
/* 3567 */         clearInputStream();
/*      */         
/* 3569 */         StringBuffer errorBuf = new StringBuffer();
/*      */         
/* 3571 */         String xOpenErrorMessage = SQLError.get(xOpen);
/*      */         
/* 3573 */         if (!this.connection.getUseOnlyServerErrorMessages() && 
/* 3574 */           xOpenErrorMessage != null) {
/* 3575 */           errorBuf.append(xOpenErrorMessage);
/* 3576 */           errorBuf.append(Messages.getString("MysqlIO.68"));
/*      */         } 
/*      */ 
/*      */         
/* 3580 */         errorBuf.append(serverErrorMessage);
/*      */         
/* 3582 */         if (!this.connection.getUseOnlyServerErrorMessages() && 
/* 3583 */           xOpenErrorMessage != null) {
/* 3584 */           errorBuf.append("\"");
/*      */         }
/*      */ 
/*      */         
/* 3588 */         appendInnodbStatusInformation(xOpen, errorBuf);
/*      */         
/* 3590 */         if (xOpen != null && xOpen.startsWith("22")) {
/* 3591 */           throw new MysqlDataTruncation(errorBuf.toString(), false, true, false, false, false, errno);
/*      */         }
/* 3593 */         throw SQLError.createSQLException(errorBuf.toString(), xOpen, errno, false, getExceptionInterceptor(), this.connection);
/*      */       } 
/*      */ 
/*      */       
/* 3597 */       String serverErrorMessage = resultPacket.readString(this.connection.getErrorMessageEncoding(), getExceptionInterceptor());
/*      */       
/* 3599 */       clearInputStream();
/*      */       
/* 3601 */       if (serverErrorMessage.indexOf(Messages.getString("MysqlIO.70")) != -1) {
/* 3602 */         throw SQLError.createSQLException(SQLError.get("S0022") + ", " + serverErrorMessage, "S0022", -1, false, getExceptionInterceptor(), this.connection);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3609 */       StringBuffer errorBuf = new StringBuffer(Messages.getString("MysqlIO.72"));
/*      */       
/* 3611 */       errorBuf.append(serverErrorMessage);
/* 3612 */       errorBuf.append("\"");
/*      */       
/* 3614 */       throw SQLError.createSQLException(SQLError.get("S1000") + ", " + errorBuf.toString(), "S1000", -1, false, getExceptionInterceptor(), this.connection);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendInnodbStatusInformation(String xOpen, StringBuffer errorBuf) throws SQLException {
/* 3622 */     if (this.connection.getIncludeInnodbStatusInDeadlockExceptions() && xOpen != null && (xOpen.startsWith("40") || xOpen.startsWith("41")) && this.streamingData == null) {
/*      */ 
/*      */ 
/*      */       
/* 3626 */       ResultSet rs = null;
/*      */       
/*      */       try {
/* 3629 */         rs = sqlQueryDirect(null, "SHOW ENGINE INNODB STATUS", this.connection.getEncoding(), null, -1, 1003, 1007, false, this.connection.getCatalog(), null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3635 */         if (rs.next()) {
/* 3636 */           errorBuf.append("\n\n");
/* 3637 */           errorBuf.append(rs.getString("Status"));
/*      */         } else {
/* 3639 */           errorBuf.append("\n\n");
/* 3640 */           errorBuf.append(Messages.getString("MysqlIO.NoInnoDBStatusFound"));
/*      */         }
/*      */       
/* 3643 */       } catch (Exception ex) {
/* 3644 */         errorBuf.append("\n\n");
/* 3645 */         errorBuf.append(Messages.getString("MysqlIO.InnoDBStatusFailed"));
/*      */         
/* 3647 */         errorBuf.append("\n\n");
/* 3648 */         errorBuf.append(Util.stackTraceToString(ex));
/*      */       } finally {
/* 3650 */         if (rs != null) {
/* 3651 */           rs.close();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void sendSplitPackets(Buffer packet) throws SQLException {
/*      */     try {
/* 3677 */       Buffer headerPacket = (this.splitBufRef == null) ? null : (Buffer)this.splitBufRef.get();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3685 */       if (headerPacket == null) {
/* 3686 */         headerPacket = new Buffer(this.maxThreeBytes + 4);
/*      */         
/* 3688 */         this.splitBufRef = new SoftReference(headerPacket);
/*      */       } 
/*      */       
/* 3691 */       int len = packet.getPosition();
/* 3692 */       int splitSize = this.maxThreeBytes;
/* 3693 */       int originalPacketPos = 4;
/* 3694 */       byte[] origPacketBytes = packet.getByteBuffer();
/* 3695 */       byte[] headerPacketBytes = headerPacket.getByteBuffer();
/*      */       
/* 3697 */       while (len >= this.maxThreeBytes) {
/* 3698 */         this.packetSequence = (byte)(this.packetSequence + 1);
/*      */         
/* 3700 */         headerPacket.setPosition(0);
/* 3701 */         headerPacket.writeLongInt(splitSize);
/*      */         
/* 3703 */         headerPacket.writeByte(this.packetSequence);
/* 3704 */         System.arraycopy(origPacketBytes, originalPacketPos, headerPacketBytes, 4, splitSize);
/*      */ 
/*      */         
/* 3707 */         int packetLen = splitSize + 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3713 */         if (!this.useCompression) {
/* 3714 */           this.mysqlOutput.write(headerPacketBytes, 0, splitSize + 4);
/*      */           
/* 3716 */           this.mysqlOutput.flush();
/*      */         }
/*      */         else {
/*      */           
/* 3720 */           headerPacket.setPosition(0);
/* 3721 */           Buffer packetToSend = compressPacket(headerPacket, 4, splitSize, 4);
/*      */           
/* 3723 */           packetLen = packetToSend.getPosition();
/*      */           
/* 3725 */           this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
/*      */           
/* 3727 */           this.mysqlOutput.flush();
/*      */         } 
/*      */         
/* 3730 */         originalPacketPos += splitSize;
/* 3731 */         len -= splitSize;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3737 */       headerPacket.clear();
/* 3738 */       headerPacket.setPosition(0);
/* 3739 */       headerPacket.writeLongInt(len - 4);
/* 3740 */       this.packetSequence = (byte)(this.packetSequence + 1);
/* 3741 */       headerPacket.writeByte(this.packetSequence);
/*      */       
/* 3743 */       if (len != 0) {
/* 3744 */         System.arraycopy(origPacketBytes, originalPacketPos, headerPacketBytes, 4, len - 4);
/*      */       }
/*      */ 
/*      */       
/* 3748 */       int packetLen = len - 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3754 */       if (!this.useCompression) {
/* 3755 */         this.mysqlOutput.write(headerPacket.getByteBuffer(), 0, len);
/* 3756 */         this.mysqlOutput.flush();
/*      */       }
/*      */       else {
/*      */         
/* 3760 */         headerPacket.setPosition(0);
/* 3761 */         Buffer packetToSend = compressPacket(headerPacket, 4, packetLen, 4);
/*      */         
/* 3763 */         packetLen = packetToSend.getPosition();
/*      */         
/* 3765 */         this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
/*      */         
/* 3767 */         this.mysqlOutput.flush();
/*      */       } 
/* 3769 */     } catch (IOException ioEx) {
/* 3770 */       throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void reclaimLargeSharedSendPacket() {
/* 3776 */     if (this.sharedSendPacket != null && this.sharedSendPacket.getCapacity() > 1048576)
/*      */     {
/* 3778 */       this.sharedSendPacket = new Buffer('Ѐ');
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 3783 */   boolean hadWarnings() { return this.hadWarnings; }
/*      */ 
/*      */   
/*      */   void scanForAndThrowDataTruncation() {
/* 3787 */     if (this.streamingData == null && versionMeetsMinimum(4, 1, 0) && this.connection.getJdbcCompliantTruncation() && this.warningCount > 0)
/*      */     {
/* 3789 */       SQLError.convertShowWarningsToSQLWarnings(this.connection, this.warningCount, true);
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
/*      */ 
/*      */ 
/*      */   
/*      */   private void secureAuth(Buffer packet, int packLength, String user, String password, String database, boolean writeClientParams) throws SQLException {
/* 3810 */     if (packet == null) {
/* 3811 */       packet = new Buffer(packLength);
/*      */     }
/*      */     
/* 3814 */     if (writeClientParams) {
/* 3815 */       if (this.use41Extensions) {
/* 3816 */         if (versionMeetsMinimum(4, 1, 1)) {
/* 3817 */           packet.writeLong(this.clientParam);
/* 3818 */           packet.writeLong(this.maxThreeBytes);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3823 */           packet.writeByte((byte)8);
/*      */ 
/*      */           
/* 3826 */           packet.writeBytesNoNull(new byte[23]);
/*      */         } else {
/* 3828 */           packet.writeLong(this.clientParam);
/* 3829 */           packet.writeLong(this.maxThreeBytes);
/*      */         } 
/*      */       } else {
/* 3832 */         packet.writeInt((int)this.clientParam);
/* 3833 */         packet.writeLongInt(this.maxThreeBytes);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 3838 */     packet.writeString(user, "Cp1252", this.connection);
/*      */     
/* 3840 */     if (password.length() != 0) {
/*      */       
/* 3842 */       packet.writeString("xxxxxxxx", "Cp1252", this.connection);
/*      */     } else {
/*      */       
/* 3845 */       packet.writeString("", "Cp1252", this.connection);
/*      */     } 
/*      */     
/* 3848 */     if (this.useConnectWithDb) {
/* 3849 */       packet.writeString(database, "Cp1252", this.connection);
/*      */     }
/*      */     
/* 3852 */     send(packet, packet.getPosition());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3857 */     if (password.length() > 0) {
/* 3858 */       Buffer b = readPacket();
/*      */       
/* 3860 */       b.setPosition(0);
/*      */       
/* 3862 */       byte[] replyAsBytes = b.getByteBuffer();
/*      */       
/* 3864 */       if (replyAsBytes.length == 25 && replyAsBytes[0] != 0)
/*      */       {
/* 3866 */         if (replyAsBytes[0] != 42) {
/*      */           
/*      */           try {
/* 3869 */             byte[] buff = Security.passwordHashStage1(password);
/*      */ 
/*      */             
/* 3872 */             byte[] passwordHash = new byte[buff.length];
/* 3873 */             System.arraycopy(buff, 0, passwordHash, 0, buff.length);
/*      */ 
/*      */             
/* 3876 */             passwordHash = Security.passwordHashStage2(passwordHash, replyAsBytes);
/*      */ 
/*      */             
/* 3879 */             byte[] packetDataAfterSalt = new byte[replyAsBytes.length - 5];
/*      */ 
/*      */             
/* 3882 */             System.arraycopy(replyAsBytes, 4, packetDataAfterSalt, 0, replyAsBytes.length - 5);
/*      */ 
/*      */             
/* 3885 */             byte[] mysqlScrambleBuff = new byte[20];
/*      */ 
/*      */             
/* 3888 */             Security.passwordCrypt(packetDataAfterSalt, mysqlScrambleBuff, passwordHash, 20);
/*      */ 
/*      */ 
/*      */             
/* 3892 */             Security.passwordCrypt(mysqlScrambleBuff, buff, buff, 20);
/*      */             
/* 3894 */             Buffer packet2 = new Buffer(25);
/* 3895 */             packet2.writeBytesNoNull(buff);
/*      */             
/* 3897 */             this.packetSequence = (byte)(this.packetSequence + 1);
/*      */             
/* 3899 */             send(packet2, 24);
/* 3900 */           } catch (NoSuchAlgorithmException nse) {
/* 3901 */             throw SQLError.createSQLException(Messages.getString("MysqlIO.91") + Messages.getString("MysqlIO.92"), "S1000", getExceptionInterceptor());
/*      */           } 
/*      */         } else {
/*      */ 
/*      */           
/*      */           try {
/*      */             
/* 3908 */             byte[] passwordHash = Security.createKeyFromOldPassword(password);
/*      */ 
/*      */             
/* 3911 */             byte[] netReadPos4 = new byte[replyAsBytes.length - 5];
/*      */             
/* 3913 */             System.arraycopy(replyAsBytes, 4, netReadPos4, 0, replyAsBytes.length - 5);
/*      */ 
/*      */             
/* 3916 */             byte[] mysqlScrambleBuff = new byte[20];
/*      */ 
/*      */             
/* 3919 */             Security.passwordCrypt(netReadPos4, mysqlScrambleBuff, passwordHash, 20);
/*      */ 
/*      */ 
/*      */             
/* 3923 */             String scrambledPassword = Util.scramble(new String(mysqlScrambleBuff), password);
/*      */ 
/*      */             
/* 3926 */             Buffer packet2 = new Buffer(packLength);
/* 3927 */             packet2.writeString(scrambledPassword, "Cp1252", this.connection);
/* 3928 */             this.packetSequence = (byte)(this.packetSequence + 1);
/*      */             
/* 3930 */             send(packet2, 24);
/* 3931 */           } catch (NoSuchAlgorithmException nse) {
/* 3932 */             throw SQLError.createSQLException(Messages.getString("MysqlIO.93") + Messages.getString("MysqlIO.94"), "S1000", getExceptionInterceptor());
/*      */           } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void secureAuth411(Buffer packet, int packLength, String user, String password, String database, boolean writeClientParams) throws SQLException {
/* 3974 */     if (packet == null) {
/* 3975 */       packet = new Buffer(packLength);
/*      */     }
/*      */     
/* 3978 */     if (writeClientParams) {
/* 3979 */       if (this.use41Extensions) {
/* 3980 */         if (versionMeetsMinimum(4, 1, 1)) {
/* 3981 */           packet.writeLong(this.clientParam);
/* 3982 */           packet.writeLong(this.maxThreeBytes);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3987 */           packet.writeByte((byte)33);
/*      */ 
/*      */           
/* 3990 */           packet.writeBytesNoNull(new byte[23]);
/*      */         } else {
/* 3992 */           packet.writeLong(this.clientParam);
/* 3993 */           packet.writeLong(this.maxThreeBytes);
/*      */         } 
/*      */       } else {
/* 3996 */         packet.writeInt((int)this.clientParam);
/* 3997 */         packet.writeLongInt(this.maxThreeBytes);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 4002 */     packet.writeString(user, "utf-8", this.connection);
/*      */     
/* 4004 */     if (password.length() != 0) {
/* 4005 */       packet.writeByte((byte)20);
/*      */       
/*      */       try {
/* 4008 */         packet.writeBytesNoNull(Security.scramble411(password, this.seed, this.connection));
/* 4009 */       } catch (NoSuchAlgorithmException nse) {
/* 4010 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), "S1000", getExceptionInterceptor());
/*      */       
/*      */       }
/* 4013 */       catch (UnsupportedEncodingException e) {
/* 4014 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), "S1000", getExceptionInterceptor());
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4020 */       packet.writeByte((byte)0);
/*      */     } 
/*      */     
/* 4023 */     if (this.useConnectWithDb) {
/* 4024 */       packet.writeString(database, "utf-8", this.connection);
/*      */     }
/*      */     
/* 4027 */     send(packet, packet.getPosition());
/*      */     
/* 4029 */     byte savePacketSequence = this.packetSequence = (byte)(this.packetSequence + 1);
/*      */     
/* 4031 */     Buffer reply = checkErrorPacket();
/*      */     
/* 4033 */     if (reply.isLastDataPacket()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4038 */       this.packetSequence = savePacketSequence = (byte)(savePacketSequence + 1);
/* 4039 */       packet.clear();
/*      */       
/* 4041 */       String seed323 = this.seed.substring(0, 8);
/* 4042 */       packet.writeString(Util.newCrypt(password, seed323));
/* 4043 */       send(packet, packet.getPosition());
/*      */ 
/*      */       
/* 4046 */       checkErrorPacket();
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
/*      */   private final ResultSetRow unpackBinaryResultSetRow(Field[] fields, Buffer binaryData, int resultSetConcurrency) throws SQLException {
/* 4063 */     int numFields = fields.length;
/*      */     
/* 4065 */     byte[][] unpackedRowData = new byte[numFields][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4072 */     int nullCount = (numFields + 9) / 8;
/*      */     
/* 4074 */     byte[] nullBitMask = new byte[nullCount];
/*      */     
/* 4076 */     for (i = 0; i < nullCount; i++) {
/* 4077 */       nullBitMask[i] = binaryData.readByte();
/*      */     }
/*      */     
/* 4080 */     int nullMaskPos = 0;
/* 4081 */     int bit = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4088 */     for (int i = 0; i < numFields; i++) {
/* 4089 */       if ((nullBitMask[nullMaskPos] & bit) != 0) {
/* 4090 */         unpackedRowData[i] = null;
/*      */       }
/* 4092 */       else if (resultSetConcurrency != 1008) {
/* 4093 */         extractNativeEncodedColumn(binaryData, fields, i, unpackedRowData);
/*      */       } else {
/*      */         
/* 4096 */         unpackNativeEncodedColumn(binaryData, fields, i, unpackedRowData);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4101 */       if ((bit <<= 1 & 0xFF) == 0) {
/* 4102 */         bit = 1;
/*      */         
/* 4104 */         nullMaskPos++;
/*      */       } 
/*      */     } 
/*      */     
/* 4108 */     return new ByteArrayRow(unpackedRowData, getExceptionInterceptor());
/*      */   }
/*      */   
/*      */   private final void extractNativeEncodedColumn(Buffer binaryData, Field[] fields, int columnIndex, byte[][] unpackedRowData) throws SQLException
/*      */   {
/*      */     int length;
/* 4114 */     Field curField = fields[columnIndex];
/*      */     
/* 4116 */     switch (curField.getMysqlType()) {
/*      */       case 6:
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 4122 */         new byte[1][0] = binaryData.readByte(); unpackedRowData[columnIndex] = new byte[1];
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 13:
/* 4128 */         unpackedRowData[columnIndex] = binaryData.getBytes(2);
/*      */         break;
/*      */       
/*      */       case 3:
/*      */       case 9:
/* 4133 */         unpackedRowData[columnIndex] = binaryData.getBytes(4);
/*      */         break;
/*      */       
/*      */       case 8:
/* 4137 */         unpackedRowData[columnIndex] = binaryData.getBytes(8);
/*      */         break;
/*      */       
/*      */       case 4:
/* 4141 */         unpackedRowData[columnIndex] = binaryData.getBytes(4);
/*      */         break;
/*      */       
/*      */       case 5:
/* 4145 */         unpackedRowData[columnIndex] = binaryData.getBytes(8);
/*      */         break;
/*      */       
/*      */       case 11:
/* 4149 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4151 */         unpackedRowData[columnIndex] = binaryData.getBytes(length);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 10:
/* 4156 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4158 */         unpackedRowData[columnIndex] = binaryData.getBytes(length);
/*      */         break;
/*      */       
/*      */       case 7:
/*      */       case 12:
/* 4163 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4165 */         unpackedRowData[columnIndex] = binaryData.getBytes(length);
/*      */         break;
/*      */       case 0:
/*      */       case 15:
/*      */       case 246:
/*      */       case 249:
/*      */       case 250:
/*      */       case 251:
/*      */       case 252:
/*      */       case 253:
/*      */       case 254:
/*      */       case 255:
/* 4177 */         unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
/*      */         break;
/*      */       
/*      */       case 16:
/* 4181 */         unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
/*      */         break;
/*      */       
/*      */       default:
/* 4185 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + curField.getMysqlType() + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"), "S1000", getExceptionInterceptor());
/*      */     }  } private final void unpackNativeEncodedColumn(Buffer binaryData, Field[] fields, int columnIndex, byte[][] unpackedRowData) throws SQLException { int j, nanosOffset; byte[] datetimeAsBytes, nanosAsBytes; int stringLength, nanos, after100, after1000; byte[] dateAsBytes;
/*      */     int day, month, year;
/*      */     byte[] timeAsBytes;
/*      */     int seconds, minute, hour, length;
/*      */     double doubleVal;
/*      */     BigInteger asBigInteger;
/*      */     float floatVal;
/*      */     long longVal;
/*      */     int intVal;
/*      */     short shortVal;
/*      */     byte tinyVal;
/* 4197 */     Field curField = fields[columnIndex];
/*      */     
/* 4199 */     switch (curField.getMysqlType()) {
/*      */       case 6:
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 4205 */         tinyVal = binaryData.readByte();
/*      */         
/* 4207 */         if (!curField.isUnsigned()) {
/* 4208 */           unpackedRowData[columnIndex] = String.valueOf(tinyVal).getBytes();
/*      */           break;
/*      */         } 
/* 4211 */         unsignedTinyVal = (short)(tinyVal & 0xFF);
/*      */         
/* 4213 */         unpackedRowData[columnIndex] = String.valueOf(unsignedTinyVal).getBytes();
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 13:
/* 4222 */         shortVal = (short)binaryData.readInt();
/*      */         
/* 4224 */         if (!curField.isUnsigned()) {
/* 4225 */           unpackedRowData[columnIndex] = String.valueOf(shortVal).getBytes();
/*      */           break;
/*      */         } 
/* 4228 */         unsignedShortVal = shortVal & 0xFFFF;
/*      */         
/* 4230 */         unpackedRowData[columnIndex] = String.valueOf(unsignedShortVal).getBytes();
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 9:
/* 4239 */         intVal = (int)binaryData.readLong();
/*      */         
/* 4241 */         if (!curField.isUnsigned()) {
/* 4242 */           unpackedRowData[columnIndex] = String.valueOf(intVal).getBytes();
/*      */           break;
/*      */         } 
/* 4245 */         longVal = intVal & 0xFFFFFFFFL;
/*      */         
/* 4247 */         unpackedRowData[columnIndex] = String.valueOf(longVal).getBytes();
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 4255 */         longVal = binaryData.readLongLong();
/*      */         
/* 4257 */         if (!curField.isUnsigned()) {
/* 4258 */           unpackedRowData[columnIndex] = String.valueOf(longVal).getBytes();
/*      */           break;
/*      */         } 
/* 4261 */         asBigInteger = ResultSetImpl.convertLongToUlong(longVal);
/*      */         
/* 4263 */         unpackedRowData[columnIndex] = asBigInteger.toString().getBytes();
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 4271 */         floatVal = Float.intBitsToFloat(binaryData.readIntAsLong());
/*      */         
/* 4273 */         unpackedRowData[columnIndex] = String.valueOf(floatVal).getBytes();
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 4279 */         doubleVal = Double.longBitsToDouble(binaryData.readLongLong());
/*      */         
/* 4281 */         unpackedRowData[columnIndex] = String.valueOf(doubleVal).getBytes();
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 11:
/* 4287 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4289 */         hour = 0;
/* 4290 */         minute = 0;
/* 4291 */         seconds = 0;
/*      */         
/* 4293 */         if (length != 0) {
/* 4294 */           binaryData.readByte();
/* 4295 */           binaryData.readLong();
/* 4296 */           hour = binaryData.readByte();
/* 4297 */           minute = binaryData.readByte();
/* 4298 */           seconds = binaryData.readByte();
/*      */           
/* 4300 */           if (length > 8) {
/* 4301 */             binaryData.readLong();
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 4306 */         timeAsBytes = new byte[8];
/*      */         
/* 4308 */         timeAsBytes[0] = (byte)Character.forDigit(hour / 10, 10);
/* 4309 */         timeAsBytes[1] = (byte)Character.forDigit(hour % 10, 10);
/*      */         
/* 4311 */         timeAsBytes[2] = 58;
/*      */         
/* 4313 */         timeAsBytes[3] = (byte)Character.forDigit(minute / 10, 10);
/*      */         
/* 4315 */         timeAsBytes[4] = (byte)Character.forDigit(minute % 10, 10);
/*      */ 
/*      */         
/* 4318 */         timeAsBytes[5] = 58;
/*      */         
/* 4320 */         timeAsBytes[6] = (byte)Character.forDigit(seconds / 10, 10);
/*      */         
/* 4322 */         timeAsBytes[7] = (byte)Character.forDigit(seconds % 10, 10);
/*      */ 
/*      */         
/* 4325 */         unpackedRowData[columnIndex] = timeAsBytes;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/* 4331 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4333 */         year = 0;
/* 4334 */         month = 0;
/* 4335 */         day = 0;
/*      */         
/* 4337 */         hour = 0;
/* 4338 */         minute = 0;
/* 4339 */         seconds = 0;
/*      */         
/* 4341 */         if (length != 0) {
/* 4342 */           year = binaryData.readInt();
/* 4343 */           month = binaryData.readByte();
/* 4344 */           day = binaryData.readByte();
/*      */         } 
/*      */         
/* 4347 */         if (year == 0 && month == 0 && day == 0) {
/* 4348 */           if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
/*      */             
/* 4350 */             unpackedRowData[columnIndex] = null;
/*      */             break;
/*      */           } 
/* 4353 */           if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */           {
/* 4355 */             throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Date", "S1009", getExceptionInterceptor());
/*      */           }
/*      */ 
/*      */           
/* 4359 */           year = 1;
/* 4360 */           month = 1;
/* 4361 */           day = 1;
/*      */         } 
/*      */ 
/*      */         
/* 4365 */         dateAsBytes = new byte[10];
/*      */         
/* 4367 */         dateAsBytes[0] = (byte)Character.forDigit(year / 1000, 10);
/*      */ 
/*      */         
/* 4370 */         after1000 = year % 1000;
/*      */         
/* 4372 */         dateAsBytes[1] = (byte)Character.forDigit(after1000 / 100, 10);
/*      */ 
/*      */         
/* 4375 */         after100 = after1000 % 100;
/*      */         
/* 4377 */         dateAsBytes[2] = (byte)Character.forDigit(after100 / 10, 10);
/*      */         
/* 4379 */         dateAsBytes[3] = (byte)Character.forDigit(after100 % 10, 10);
/*      */ 
/*      */         
/* 4382 */         dateAsBytes[4] = 45;
/*      */         
/* 4384 */         dateAsBytes[5] = (byte)Character.forDigit(month / 10, 10);
/*      */         
/* 4386 */         dateAsBytes[6] = (byte)Character.forDigit(month % 10, 10);
/*      */ 
/*      */         
/* 4389 */         dateAsBytes[7] = 45;
/*      */         
/* 4391 */         dateAsBytes[8] = (byte)Character.forDigit(day / 10, 10);
/* 4392 */         dateAsBytes[9] = (byte)Character.forDigit(day % 10, 10);
/*      */         
/* 4394 */         unpackedRowData[columnIndex] = dateAsBytes;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/*      */       case 12:
/* 4401 */         length = (int)binaryData.readFieldLength();
/*      */         
/* 4403 */         year = 0;
/* 4404 */         month = 0;
/* 4405 */         day = 0;
/*      */         
/* 4407 */         hour = 0;
/* 4408 */         minute = 0;
/* 4409 */         seconds = 0;
/*      */         
/* 4411 */         nanos = 0;
/*      */         
/* 4413 */         if (length != 0) {
/* 4414 */           year = binaryData.readInt();
/* 4415 */           month = binaryData.readByte();
/* 4416 */           day = binaryData.readByte();
/*      */           
/* 4418 */           if (length > 4) {
/* 4419 */             hour = binaryData.readByte();
/* 4420 */             minute = binaryData.readByte();
/* 4421 */             seconds = binaryData.readByte();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4429 */         if (year == 0 && month == 0 && day == 0) {
/* 4430 */           if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
/*      */             
/* 4432 */             unpackedRowData[columnIndex] = null;
/*      */             break;
/*      */           } 
/* 4435 */           if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */           {
/* 4437 */             throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Timestamp", "S1009", getExceptionInterceptor());
/*      */           }
/*      */ 
/*      */           
/* 4441 */           year = 1;
/* 4442 */           month = 1;
/* 4443 */           day = 1;
/*      */         } 
/*      */ 
/*      */         
/* 4447 */         stringLength = 19;
/*      */         
/* 4449 */         nanosAsBytes = Integer.toString(nanos).getBytes();
/*      */         
/* 4451 */         stringLength += 1 + nanosAsBytes.length;
/*      */         
/* 4453 */         datetimeAsBytes = new byte[stringLength];
/*      */         
/* 4455 */         datetimeAsBytes[0] = (byte)Character.forDigit(year / 1000, 10);
/*      */ 
/*      */         
/* 4458 */         after1000 = year % 1000;
/*      */         
/* 4460 */         datetimeAsBytes[1] = (byte)Character.forDigit(after1000 / 100, 10);
/*      */ 
/*      */         
/* 4463 */         after100 = after1000 % 100;
/*      */         
/* 4465 */         datetimeAsBytes[2] = (byte)Character.forDigit(after100 / 10, 10);
/*      */         
/* 4467 */         datetimeAsBytes[3] = (byte)Character.forDigit(after100 % 10, 10);
/*      */ 
/*      */         
/* 4470 */         datetimeAsBytes[4] = 45;
/*      */         
/* 4472 */         datetimeAsBytes[5] = (byte)Character.forDigit(month / 10, 10);
/*      */         
/* 4474 */         datetimeAsBytes[6] = (byte)Character.forDigit(month % 10, 10);
/*      */ 
/*      */         
/* 4477 */         datetimeAsBytes[7] = 45;
/*      */         
/* 4479 */         datetimeAsBytes[8] = (byte)Character.forDigit(day / 10, 10);
/*      */         
/* 4481 */         datetimeAsBytes[9] = (byte)Character.forDigit(day % 10, 10);
/*      */ 
/*      */         
/* 4484 */         datetimeAsBytes[10] = 32;
/*      */         
/* 4486 */         datetimeAsBytes[11] = (byte)Character.forDigit(hour / 10, 10);
/*      */         
/* 4488 */         datetimeAsBytes[12] = (byte)Character.forDigit(hour % 10, 10);
/*      */ 
/*      */         
/* 4491 */         datetimeAsBytes[13] = 58;
/*      */         
/* 4493 */         datetimeAsBytes[14] = (byte)Character.forDigit(minute / 10, 10);
/*      */         
/* 4495 */         datetimeAsBytes[15] = (byte)Character.forDigit(minute % 10, 10);
/*      */ 
/*      */         
/* 4498 */         datetimeAsBytes[16] = 58;
/*      */         
/* 4500 */         datetimeAsBytes[17] = (byte)Character.forDigit(seconds / 10, 10);
/*      */         
/* 4502 */         datetimeAsBytes[18] = (byte)Character.forDigit(seconds % 10, 10);
/*      */ 
/*      */         
/* 4505 */         datetimeAsBytes[19] = 46;
/*      */         
/* 4507 */         nanosOffset = 20;
/*      */         
/* 4509 */         for (j = 0; j < nanosAsBytes.length; j++) {
/* 4510 */           datetimeAsBytes[nanosOffset + j] = nanosAsBytes[j];
/*      */         }
/*      */         
/* 4513 */         unpackedRowData[columnIndex] = datetimeAsBytes;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/*      */       case 15:
/*      */       case 16:
/*      */       case 246:
/*      */       case 249:
/*      */       case 250:
/*      */       case 251:
/*      */       case 252:
/*      */       case 253:
/*      */       case 254:
/* 4528 */         unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/* 4533 */         throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + curField.getMysqlType() + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"), "S1000", getExceptionInterceptor());
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void negotiateSSLConnection(String user, String password, String database, int packLength) throws SQLException {
/* 4556 */     if (!ExportControlled.enabled()) {
/* 4557 */       throw new ConnectionFeatureNotAvailableException(this.connection, this.lastPacketSentTimeMs, null);
/*      */     }
/*      */ 
/*      */     
/* 4561 */     boolean doSecureAuth = false;
/*      */     
/* 4563 */     if ((this.serverCapabilities & 0x8000) != 0) {
/* 4564 */       this.clientParam |= 0x8000L;
/* 4565 */       doSecureAuth = true;
/*      */     } 
/*      */     
/* 4568 */     this.clientParam |= 0x800L;
/*      */     
/* 4570 */     Buffer packet = new Buffer(packLength);
/*      */     
/* 4572 */     if (this.use41Extensions) {
/* 4573 */       packet.writeLong(this.clientParam);
/*      */     } else {
/* 4575 */       packet.writeInt((int)this.clientParam);
/*      */     } 
/*      */     
/* 4578 */     send(packet, packet.getPosition());
/*      */     
/* 4580 */     ExportControlled.transformSocketToSSLSocket(this);
/*      */     
/* 4582 */     packet.clear();
/*      */     
/* 4584 */     if (doSecureAuth) {
/* 4585 */       if (versionMeetsMinimum(4, 1, 1)) {
/* 4586 */         secureAuth411(null, packLength, user, password, database, true);
/*      */       } else {
/* 4588 */         secureAuth411(null, packLength, user, password, database, true);
/*      */       } 
/*      */     } else {
/* 4591 */       if (this.use41Extensions) {
/* 4592 */         packet.writeLong(this.clientParam);
/* 4593 */         packet.writeLong(this.maxThreeBytes);
/*      */       } else {
/* 4595 */         packet.writeInt((int)this.clientParam);
/* 4596 */         packet.writeLongInt(this.maxThreeBytes);
/*      */       } 
/*      */ 
/*      */       
/* 4600 */       packet.writeString(user);
/*      */       
/* 4602 */       if (this.protocolVersion > 9) {
/* 4603 */         packet.writeString(Util.newCrypt(password, this.seed));
/*      */       } else {
/* 4605 */         packet.writeString(Util.oldCrypt(password, this.seed));
/*      */       } 
/*      */       
/* 4608 */       if ((this.serverCapabilities & 0x8) != 0 && database != null && database.length() > 0)
/*      */       {
/* 4610 */         packet.writeString(database);
/*      */       }
/*      */       
/* 4613 */       send(packet, packet.getPosition());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 4618 */   protected int getServerStatus() { return this.serverStatus; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List fetchRowsViaCursor(List fetchedRows, long statementId, Field[] columnTypes, int fetchSize, boolean useBufferRowExplicit) throws SQLException {
/* 4624 */     if (fetchedRows == null) {
/* 4625 */       fetchedRows = new ArrayList(fetchSize);
/*      */     } else {
/* 4627 */       fetchedRows.clear();
/*      */     } 
/*      */     
/* 4630 */     this.sharedSendPacket.clear();
/*      */     
/* 4632 */     this.sharedSendPacket.writeByte((byte)28);
/* 4633 */     this.sharedSendPacket.writeLong(statementId);
/* 4634 */     this.sharedSendPacket.writeLong(fetchSize);
/*      */     
/* 4636 */     sendCommand(28, null, this.sharedSendPacket, true, null, 0);
/*      */ 
/*      */     
/* 4639 */     ResultSetRow row = null;
/*      */ 
/*      */     
/* 4642 */     while ((row = nextRow(columnTypes, columnTypes.length, true, 'ϯ', false, useBufferRowExplicit, false, null)) != null) {
/* 4643 */       fetchedRows.add(row);
/*      */     }
/*      */     
/* 4646 */     return fetchedRows;
/*      */   }
/*      */ 
/*      */   
/* 4650 */   protected long getThreadId() { return this.threadId; }
/*      */ 
/*      */ 
/*      */   
/* 4654 */   protected boolean useNanosForElapsedTime() { return this.useNanosForElapsedTime; }
/*      */ 
/*      */ 
/*      */   
/* 4658 */   protected long getSlowQueryThreshold() { return this.slowQueryThreshold; }
/*      */ 
/*      */ 
/*      */   
/* 4662 */   protected String getQueryTimingUnits() { return this.queryTimingUnits; }
/*      */ 
/*      */ 
/*      */   
/* 4666 */   protected int getCommandCount() { return this.commandCount; }
/*      */ 
/*      */   
/*      */   private void checkTransactionState(int oldStatus) throws SQLException {
/* 4670 */     boolean previouslyInTrans = ((oldStatus & true) != 0);
/* 4671 */     boolean currentlyInTrans = ((this.serverStatus & true) != 0);
/*      */     
/* 4673 */     if (previouslyInTrans && !currentlyInTrans) {
/* 4674 */       this.connection.transactionCompleted();
/* 4675 */     } else if (!previouslyInTrans && currentlyInTrans) {
/* 4676 */       this.connection.transactionBegun();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 4681 */   protected void setStatementInterceptors(List statementInterceptors) { this.statementInterceptors = statementInterceptors; }
/*      */ 
/*      */ 
/*      */   
/* 4685 */   protected ExceptionInterceptor getExceptionInterceptor() { return this.exceptionInterceptor; }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\MysqlIO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */