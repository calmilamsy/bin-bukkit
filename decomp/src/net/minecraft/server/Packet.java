/*     */ package net.minecraft.server;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class Packet {
/*  14 */   private static Map a = new HashMap();
/*  15 */   private static Map b = new HashMap();
/*  16 */   private static Set c = new HashSet();
/*  17 */   private static Set d = new HashSet();
/*  18 */   public final long timestamp = System.currentTimeMillis();
/*     */   
/*     */   public boolean k = false;
/*     */   
/*     */   private static HashMap e;
/*     */   private static int f;
/*     */   
/*     */   static void a(int i, boolean flag, boolean flag1, Class oclass) {
/*  26 */     if (a.containsKey(Integer.valueOf(i)))
/*  27 */       throw new IllegalArgumentException("Duplicate packet id:" + i); 
/*  28 */     if (b.containsKey(oclass)) {
/*  29 */       throw new IllegalArgumentException("Duplicate packet class:" + oclass);
/*     */     }
/*  31 */     a.put(Integer.valueOf(i), oclass);
/*  32 */     b.put(oclass, Integer.valueOf(i));
/*  33 */     if (flag) {
/*  34 */       c.add(Integer.valueOf(i));
/*     */     }
/*     */     
/*  37 */     if (flag1) {
/*  38 */       d.add(Integer.valueOf(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Packet a(int i) {
/*     */     try {
/*  45 */       Class oclass = (Class)a.get(Integer.valueOf(i));
/*     */       
/*  47 */       return (oclass == null) ? null : (Packet)oclass.newInstance();
/*  48 */     } catch (Exception exception) {
/*  49 */       exception.printStackTrace();
/*  50 */       System.out.println("Skipping packet with id " + i);
/*  51 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  56 */   public final int b() { return ((Integer)b.get(getClass())).intValue(); }
/*     */ 
/*     */   
/*     */   public static Packet a(DataInputStream datainputstream, boolean flag) throws IOException {
/*     */     int i;
/*  61 */     boolean flag1 = false;
/*  62 */     Packet packet = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  67 */       i = datainputstream.read();
/*  68 */       if (i == -1) {
/*  69 */         return null;
/*     */       }
/*     */       
/*  72 */       if ((flag && !d.contains(Integer.valueOf(i))) || (!flag && !c.contains(Integer.valueOf(i)))) {
/*  73 */         throw new IOException("Bad packet id " + i);
/*     */       }
/*     */       
/*  76 */       packet = a(i);
/*  77 */       if (packet == null) {
/*  78 */         throw new IOException("Bad packet id " + i);
/*     */       }
/*     */       
/*  81 */       packet.a(datainputstream);
/*  82 */     } catch (EOFException eofexception) {
/*  83 */       System.out.println("Reached end of stream");
/*  84 */       return null;
/*     */ 
/*     */     
/*     */     }
/*  88 */     catch (SocketTimeoutException exception) {
/*  89 */       System.out.println("Read timed out");
/*  90 */       return null;
/*  91 */     } catch (SocketException exception) {
/*  92 */       System.out.println("Connection reset");
/*  93 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     PacketCounter packetcounter = (PacketCounter)e.get(Integer.valueOf(i));
/*     */     
/*  99 */     if (packetcounter == null) {
/* 100 */       packetcounter = new PacketCounter((EmptyClass1)null);
/* 101 */       e.put(Integer.valueOf(i), packetcounter);
/*     */     } 
/*     */     
/* 104 */     packetcounter.a(packet.a());
/* 105 */     f++;
/* 106 */     if (f % 1000 == 0);
/*     */ 
/*     */ 
/*     */     
/* 110 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void a(Packet packet, DataOutputStream dataoutputstream) throws IOException {
/* 115 */     dataoutputstream.write(packet.b());
/* 116 */     packet.a(dataoutputstream);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void a(String s, DataOutputStream dataoutputstream) throws IOException {
/* 121 */     if (s.length() > 32767) {
/* 122 */       throw new IOException("String too big");
/*     */     }
/* 124 */     dataoutputstream.writeShort(s.length());
/* 125 */     dataoutputstream.writeChars(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String a(DataInputStream datainputstream, int i) throws IOException {
/* 131 */     short short1 = datainputstream.readShort();
/*     */     
/* 133 */     if (short1 > i)
/* 134 */       throw new IOException("Received string length longer than maximum allowed (" + short1 + " > " + i + ")"); 
/* 135 */     if (short1 < 0) {
/* 136 */       throw new IOException("Received string length is less than zero! Weird string!");
/*     */     }
/* 138 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 140 */     for (int j = 0; j < short1; j++) {
/* 141 */       stringbuilder.append(datainputstream.readChar());
/*     */     }
/*     */     
/* 144 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void a(DataInputStream paramDataInputStream) throws IOException;
/*     */   
/*     */   public abstract void a(DataOutputStream paramDataOutputStream) throws IOException;
/*     */   
/*     */   public abstract void a(NetHandler paramNetHandler);
/*     */   
/*     */   public abstract int a();
/*     */   
/*     */   static  {
/* 157 */     a(0, true, true, Packet0KeepAlive.class);
/* 158 */     a(1, true, true, Packet1Login.class);
/* 159 */     a(2, true, true, Packet2Handshake.class);
/* 160 */     a(3, true, true, Packet3Chat.class);
/* 161 */     a(4, true, false, Packet4UpdateTime.class);
/* 162 */     a(5, true, false, Packet5EntityEquipment.class);
/* 163 */     a(6, true, false, Packet6SpawnPosition.class);
/* 164 */     a(7, false, true, Packet7UseEntity.class);
/* 165 */     a(8, true, false, Packet8UpdateHealth.class);
/* 166 */     a(9, true, true, Packet9Respawn.class);
/* 167 */     a(10, true, true, Packet10Flying.class);
/* 168 */     a(11, true, true, Packet11PlayerPosition.class);
/* 169 */     a(12, true, true, Packet12PlayerLook.class);
/* 170 */     a(13, true, true, Packet13PlayerLookMove.class);
/* 171 */     a(14, false, true, Packet14BlockDig.class);
/* 172 */     a(15, false, true, Packet15Place.class);
/* 173 */     a(16, false, true, Packet16BlockItemSwitch.class);
/* 174 */     a(17, true, false, Packet17.class);
/* 175 */     a(18, true, true, Packet18ArmAnimation.class);
/* 176 */     a(19, false, true, Packet19EntityAction.class);
/* 177 */     a(20, true, false, Packet20NamedEntitySpawn.class);
/* 178 */     a(21, true, false, Packet21PickupSpawn.class);
/* 179 */     a(22, true, false, Packet22Collect.class);
/* 180 */     a(23, true, false, Packet23VehicleSpawn.class);
/* 181 */     a(24, true, false, Packet24MobSpawn.class);
/* 182 */     a(25, true, false, Packet25EntityPainting.class);
/* 183 */     a(27, false, false, Packet27.class);
/* 184 */     a(28, true, false, Packet28EntityVelocity.class);
/* 185 */     a(29, true, false, Packet29DestroyEntity.class);
/* 186 */     a(30, true, false, Packet30Entity.class);
/* 187 */     a(31, true, false, Packet31RelEntityMove.class);
/* 188 */     a(32, true, false, Packet32EntityLook.class);
/* 189 */     a(33, true, false, Packet33RelEntityMoveLook.class);
/* 190 */     a(34, true, false, Packet34EntityTeleport.class);
/* 191 */     a(38, true, false, Packet38EntityStatus.class);
/* 192 */     a(39, true, false, Packet39AttachEntity.class);
/* 193 */     a(40, true, false, Packet40EntityMetadata.class);
/* 194 */     a(50, true, false, Packet50PreChunk.class);
/* 195 */     a(51, true, false, Packet51MapChunk.class);
/* 196 */     a(52, true, false, Packet52MultiBlockChange.class);
/* 197 */     a(53, true, false, Packet53BlockChange.class);
/* 198 */     a(54, true, false, Packet54PlayNoteBlock.class);
/* 199 */     a(60, true, false, Packet60Explosion.class);
/* 200 */     a(61, true, false, Packet61.class);
/* 201 */     a(70, true, false, Packet70Bed.class);
/* 202 */     a(71, true, false, Packet71Weather.class);
/* 203 */     a(100, true, false, Packet100OpenWindow.class);
/* 204 */     a(101, true, true, Packet101CloseWindow.class);
/* 205 */     a(102, false, true, Packet102WindowClick.class);
/* 206 */     a(103, true, false, Packet103SetSlot.class);
/* 207 */     a(104, true, false, Packet104WindowItems.class);
/* 208 */     a(105, true, false, Packet105CraftProgressBar.class);
/* 209 */     a(106, true, true, Packet106Transaction.class);
/* 210 */     a(130, true, true, Packet130UpdateSign.class);
/* 211 */     a(131, true, false, Packet131.class);
/* 212 */     a(200, true, false, Packet200Statistic.class);
/* 213 */     a(255, true, true, Packet255KickDisconnect.class);
/* 214 */     e = new HashMap();
/* 215 */     f = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */