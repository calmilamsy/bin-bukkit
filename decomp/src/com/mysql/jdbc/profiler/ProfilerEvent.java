/*     */ package com.mysql.jdbc.profiler;
/*     */ 
/*     */ import com.mysql.jdbc.Util;
/*     */ import java.util.Date;
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
/*     */ public class ProfilerEvent
/*     */ {
/*     */   public static final byte TYPE_WARN = 0;
/*     */   public static final byte TYPE_OBJECT_CREATION = 1;
/*     */   public static final byte TYPE_PREPARE = 2;
/*     */   public static final byte TYPE_QUERY = 3;
/*     */   public static final byte TYPE_EXECUTE = 4;
/*     */   public static final byte TYPE_FETCH = 5;
/*     */   public static final byte TYPE_SLOW_QUERY = 6;
/*     */   protected byte eventType;
/*     */   protected long connectionId;
/*     */   protected int statementId;
/*     */   protected int resultSetId;
/*     */   protected long eventCreationTime;
/*     */   protected long eventDuration;
/*     */   protected String durationUnits;
/*     */   protected int hostNameIndex;
/*     */   protected String hostName;
/*     */   protected int catalogIndex;
/*     */   protected String catalog;
/*     */   protected int eventCreationPointIndex;
/*     */   protected Throwable eventCreationPoint;
/*     */   protected String eventCreationPointDesc;
/*     */   protected String message;
/*     */   
/*     */   public ProfilerEvent(byte eventType, String hostName, String catalog, long connectionId, int statementId, int resultSetId, long eventCreationTime, long eventDuration, String durationUnits, String eventCreationPointDesc, Throwable eventCreationPoint, String message) {
/* 181 */     this.eventType = eventType;
/* 182 */     this.connectionId = connectionId;
/* 183 */     this.statementId = statementId;
/* 184 */     this.resultSetId = resultSetId;
/* 185 */     this.eventCreationTime = eventCreationTime;
/* 186 */     this.eventDuration = eventDuration;
/* 187 */     this.durationUnits = durationUnits;
/* 188 */     this.eventCreationPoint = eventCreationPoint;
/* 189 */     this.eventCreationPointDesc = eventCreationPointDesc;
/* 190 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEventCreationPointAsString() {
/* 199 */     if (this.eventCreationPointDesc == null) {
/* 200 */       this.eventCreationPointDesc = Util.stackTraceToString(this.eventCreationPoint);
/*     */     }
/*     */ 
/*     */     
/* 204 */     return this.eventCreationPointDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 213 */     StringBuffer buf = new StringBuffer(32);
/*     */     
/* 215 */     switch (this.eventType) {
/*     */       case 4:
/* 217 */         buf.append("EXECUTE");
/*     */         break;
/*     */       
/*     */       case 5:
/* 221 */         buf.append("FETCH");
/*     */         break;
/*     */       
/*     */       case 1:
/* 225 */         buf.append("CONSTRUCT");
/*     */         break;
/*     */       
/*     */       case 2:
/* 229 */         buf.append("PREPARE");
/*     */         break;
/*     */       
/*     */       case 3:
/* 233 */         buf.append("QUERY");
/*     */         break;
/*     */       
/*     */       case 0:
/* 237 */         buf.append("WARN");
/*     */         break;
/*     */       case 6:
/* 240 */         buf.append("SLOW QUERY");
/*     */         break;
/*     */       default:
/* 243 */         buf.append("UNKNOWN");
/*     */         break;
/*     */     } 
/* 246 */     buf.append(" created: ");
/* 247 */     buf.append(new Date(this.eventCreationTime));
/* 248 */     buf.append(" duration: ");
/* 249 */     buf.append(this.eventDuration);
/* 250 */     buf.append(" connection: ");
/* 251 */     buf.append(this.connectionId);
/* 252 */     buf.append(" statement: ");
/* 253 */     buf.append(this.statementId);
/* 254 */     buf.append(" resultset: ");
/* 255 */     buf.append(this.resultSetId);
/*     */     
/* 257 */     if (this.message != null) {
/* 258 */       buf.append(" message: ");
/* 259 */       buf.append(this.message);
/*     */     } 
/*     */ 
/*     */     
/* 263 */     if (this.eventCreationPointDesc != null) {
/* 264 */       buf.append("\n\nEvent Created at:\n");
/* 265 */       buf.append(this.eventCreationPointDesc);
/*     */     } 
/*     */     
/* 268 */     return buf.toString();
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
/*     */   public static ProfilerEvent unpack(byte[] buf) throws Exception {
/* 281 */     int pos = 0;
/*     */     
/* 283 */     byte eventType = buf[pos++];
/* 284 */     long connectionId = readInt(buf, pos);
/* 285 */     pos += 8;
/* 286 */     int statementId = readInt(buf, pos);
/* 287 */     pos += 4;
/* 288 */     int resultSetId = readInt(buf, pos);
/* 289 */     pos += 4;
/* 290 */     long eventCreationTime = readLong(buf, pos);
/* 291 */     pos += 8;
/* 292 */     long eventDuration = readLong(buf, pos);
/* 293 */     pos += 4;
/*     */     
/* 295 */     byte[] eventDurationUnits = readBytes(buf, pos);
/* 296 */     pos += 4;
/*     */     
/* 298 */     if (eventDurationUnits != null) {
/* 299 */       pos += eventDurationUnits.length;
/*     */     }
/*     */     
/* 302 */     int eventCreationPointIndex = readInt(buf, pos);
/* 303 */     pos += 4;
/* 304 */     byte[] eventCreationAsBytes = readBytes(buf, pos);
/* 305 */     pos += 4;
/*     */     
/* 307 */     if (eventCreationAsBytes != null) {
/* 308 */       pos += eventCreationAsBytes.length;
/*     */     }
/*     */     
/* 311 */     byte[] message = readBytes(buf, pos);
/* 312 */     pos += 4;
/*     */     
/* 314 */     if (message != null) {
/* 315 */       pos += message.length;
/*     */     }
/*     */     
/* 318 */     return new ProfilerEvent(eventType, "", "", connectionId, statementId, resultSetId, eventCreationTime, eventDuration, new String(eventDurationUnits, "ISO8859_1"), new String(eventCreationAsBytes, "ISO8859_1"), null, new String(message, "ISO8859_1"));
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
/*     */   public byte[] pack() throws Exception {
/* 334 */     int len = 29;
/*     */     
/* 336 */     byte[] eventCreationAsBytes = null;
/*     */     
/* 338 */     getEventCreationPointAsString();
/*     */     
/* 340 */     if (this.eventCreationPointDesc != null) {
/* 341 */       eventCreationAsBytes = this.eventCreationPointDesc.getBytes("ISO8859_1");
/*     */       
/* 343 */       len += 4 + eventCreationAsBytes.length;
/*     */     } else {
/* 345 */       len += 4;
/*     */     } 
/*     */     
/* 348 */     byte[] messageAsBytes = null;
/*     */     
/* 350 */     if (messageAsBytes != null) {
/* 351 */       messageAsBytes = this.message.getBytes("ISO8859_1");
/* 352 */       len += 4 + messageAsBytes.length;
/*     */     } else {
/* 354 */       len += 4;
/*     */     } 
/*     */     
/* 357 */     byte[] durationUnitsAsBytes = null;
/*     */     
/* 359 */     if (this.durationUnits != null) {
/* 360 */       durationUnitsAsBytes = this.durationUnits.getBytes("ISO8859_1");
/* 361 */       len += 4 + durationUnitsAsBytes.length;
/*     */     } else {
/* 363 */       len += 4;
/*     */     } 
/*     */     
/* 366 */     byte[] buf = new byte[len];
/*     */     
/* 368 */     int pos = 0;
/*     */     
/* 370 */     buf[pos++] = this.eventType;
/* 371 */     pos = writeLong(this.connectionId, buf, pos);
/* 372 */     pos = writeInt(this.statementId, buf, pos);
/* 373 */     pos = writeInt(this.resultSetId, buf, pos);
/* 374 */     pos = writeLong(this.eventCreationTime, buf, pos);
/* 375 */     pos = writeLong(this.eventDuration, buf, pos);
/* 376 */     pos = writeBytes(durationUnitsAsBytes, buf, pos);
/* 377 */     pos = writeInt(this.eventCreationPointIndex, buf, pos);
/*     */     
/* 379 */     if (eventCreationAsBytes != null) {
/* 380 */       pos = writeBytes(eventCreationAsBytes, buf, pos);
/*     */     } else {
/* 382 */       pos = writeInt(0, buf, pos);
/*     */     } 
/*     */     
/* 385 */     if (messageAsBytes != null) {
/* 386 */       pos = writeBytes(messageAsBytes, buf, pos);
/*     */     } else {
/* 388 */       pos = writeInt(0, buf, pos);
/*     */     } 
/*     */     
/* 391 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int writeInt(int i, byte[] buf, int pos) {
/* 396 */     buf[pos++] = (byte)(i & 0xFF);
/* 397 */     buf[pos++] = (byte)(i >>> 8);
/* 398 */     buf[pos++] = (byte)(i >>> 16);
/* 399 */     buf[pos++] = (byte)(i >>> 24);
/*     */     
/* 401 */     return pos;
/*     */   }
/*     */   
/*     */   private static int writeLong(long l, byte[] buf, int pos) {
/* 405 */     buf[pos++] = (byte)(int)(l & 0xFFL);
/* 406 */     buf[pos++] = (byte)(int)(l >>> 8);
/* 407 */     buf[pos++] = (byte)(int)(l >>> 16);
/* 408 */     buf[pos++] = (byte)(int)(l >>> 24);
/* 409 */     buf[pos++] = (byte)(int)(l >>> 32);
/* 410 */     buf[pos++] = (byte)(int)(l >>> 40);
/* 411 */     buf[pos++] = (byte)(int)(l >>> 48);
/* 412 */     buf[pos++] = (byte)(int)(l >>> 56);
/*     */     
/* 414 */     return pos;
/*     */   }
/*     */   
/*     */   private static int writeBytes(byte[] msg, byte[] buf, int pos) {
/* 418 */     pos = writeInt(msg.length, buf, pos);
/*     */     
/* 420 */     System.arraycopy(msg, 0, buf, pos, msg.length);
/*     */     
/* 422 */     return pos + msg.length;
/*     */   }
/*     */ 
/*     */   
/* 426 */   private static int readInt(byte[] buf, int pos) { return buf[pos++] & 0xFF | (buf[pos++] & 0xFF) << 8 | (buf[pos++] & 0xFF) << 16 | (buf[pos++] & 0xFF) << 24; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 432 */   private static long readLong(byte[] buf, int pos) { return (buf[pos++] & 0xFF) | (buf[pos++] & 0xFF) << 8 | (buf[pos++] & 0xFF) << 16 | (buf[pos++] & 0xFF) << 24 | (buf[pos++] & 0xFF) << 32 | (buf[pos++] & 0xFF) << 40 | (buf[pos++] & 0xFF) << 48 | (buf[pos++] & 0xFF) << 56; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readBytes(byte[] buf, int pos) {
/* 442 */     int length = readInt(buf, pos);
/*     */     
/* 444 */     pos += 4;
/*     */     
/* 446 */     byte[] msg = new byte[length];
/* 447 */     System.arraycopy(buf, pos, msg, 0, length);
/*     */     
/* 449 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 458 */   public String getCatalog() { return this.catalog; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 467 */   public long getConnectionId() { return this.connectionId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 477 */   public Throwable getEventCreationPoint() { return this.eventCreationPoint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 487 */   public long getEventCreationTime() { return this.eventCreationTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 496 */   public long getEventDuration() { return this.eventDuration; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 503 */   public String getDurationUnits() { return this.durationUnits; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 512 */   public byte getEventType() { return this.eventType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 521 */   public int getResultSetId() { return this.resultSetId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 530 */   public int getStatementId() { return this.statementId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 539 */   public String getMessage() { return this.message; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\profiler\ProfilerEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */