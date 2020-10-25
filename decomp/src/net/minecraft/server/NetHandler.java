/*     */ package net.minecraft.server;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class NetHandler
/*     */ {
/*     */   public abstract boolean c();
/*     */   
/*     */   public void a(Packet51MapChunk paramPacket51MapChunk) {}
/*     */   
/*     */   public void a(Packet paramPacket) {}
/*     */   
/*     */   public void a(String paramString, Object[] paramArrayOfObject) {}
/*     */   
/*  18 */   public void a(Packet255KickDisconnect paramPacket255KickDisconnect) { a(paramPacket255KickDisconnect); }
/*     */ 
/*     */ 
/*     */   
/*  22 */   public void a(Packet1Login paramPacket1Login) { a(paramPacket1Login); }
/*     */ 
/*     */ 
/*     */   
/*  26 */   public void a(Packet10Flying paramPacket10Flying) { a(paramPacket10Flying); }
/*     */ 
/*     */ 
/*     */   
/*  30 */   public void a(Packet52MultiBlockChange paramPacket52MultiBlockChange) { a(paramPacket52MultiBlockChange); }
/*     */ 
/*     */ 
/*     */   
/*  34 */   public void a(Packet14BlockDig paramPacket14BlockDig) { a(paramPacket14BlockDig); }
/*     */ 
/*     */ 
/*     */   
/*  38 */   public void a(Packet53BlockChange paramPacket53BlockChange) { a(paramPacket53BlockChange); }
/*     */ 
/*     */ 
/*     */   
/*  42 */   public void a(Packet50PreChunk paramPacket50PreChunk) { a(paramPacket50PreChunk); }
/*     */ 
/*     */ 
/*     */   
/*  46 */   public void a(Packet20NamedEntitySpawn paramPacket20NamedEntitySpawn) { a(paramPacket20NamedEntitySpawn); }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public void a(Packet30Entity paramPacket30Entity) { a(paramPacket30Entity); }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public void a(Packet34EntityTeleport paramPacket34EntityTeleport) { a(paramPacket34EntityTeleport); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public void a(Packet15Place paramPacket15Place) { a(paramPacket15Place); }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public void a(Packet16BlockItemSwitch paramPacket16BlockItemSwitch) { a(paramPacket16BlockItemSwitch); }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public void a(Packet29DestroyEntity paramPacket29DestroyEntity) { a(paramPacket29DestroyEntity); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public void a(Packet21PickupSpawn paramPacket21PickupSpawn) { a(paramPacket21PickupSpawn); }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public void a(Packet22Collect paramPacket22Collect) { a(paramPacket22Collect); }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void a(Packet3Chat paramPacket3Chat) { a(paramPacket3Chat); }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public void a(Packet23VehicleSpawn paramPacket23VehicleSpawn) { a(paramPacket23VehicleSpawn); }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public void a(Packet18ArmAnimation paramPacket18ArmAnimation) { a(paramPacket18ArmAnimation); }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void a(Packet19EntityAction paramPacket19EntityAction) { a(paramPacket19EntityAction); }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public void a(Packet2Handshake paramPacket2Handshake) { a(paramPacket2Handshake); }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public void a(Packet24MobSpawn paramPacket24MobSpawn) { a(paramPacket24MobSpawn); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public void a(Packet4UpdateTime paramPacket4UpdateTime) { a(paramPacket4UpdateTime); }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void a(Packet6SpawnPosition paramPacket6SpawnPosition) { a(paramPacket6SpawnPosition); }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void a(Packet28EntityVelocity paramPacket28EntityVelocity) { a(paramPacket28EntityVelocity); }
/*     */ 
/*     */ 
/*     */   
/* 114 */   public void a(Packet40EntityMetadata paramPacket40EntityMetadata) { a(paramPacket40EntityMetadata); }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void a(Packet39AttachEntity paramPacket39AttachEntity) { a(paramPacket39AttachEntity); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void a(Packet7UseEntity paramPacket7UseEntity) { a(paramPacket7UseEntity); }
/*     */ 
/*     */ 
/*     */   
/* 126 */   public void a(Packet38EntityStatus paramPacket38EntityStatus) { a(paramPacket38EntityStatus); }
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void a(Packet8UpdateHealth paramPacket8UpdateHealth) { a(paramPacket8UpdateHealth); }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public void a(Packet9Respawn paramPacket9Respawn) { a(paramPacket9Respawn); }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public void a(Packet60Explosion paramPacket60Explosion) { a(paramPacket60Explosion); }
/*     */ 
/*     */ 
/*     */   
/* 142 */   public void a(Packet100OpenWindow paramPacket100OpenWindow) { a(paramPacket100OpenWindow); }
/*     */ 
/*     */ 
/*     */   
/* 146 */   public void a(Packet101CloseWindow paramPacket101CloseWindow) { a(paramPacket101CloseWindow); }
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void a(Packet102WindowClick paramPacket102WindowClick) { a(paramPacket102WindowClick); }
/*     */ 
/*     */ 
/*     */   
/* 154 */   public void a(Packet103SetSlot paramPacket103SetSlot) { a(paramPacket103SetSlot); }
/*     */ 
/*     */ 
/*     */   
/* 158 */   public void a(Packet104WindowItems paramPacket104WindowItems) { a(paramPacket104WindowItems); }
/*     */ 
/*     */ 
/*     */   
/* 162 */   public void a(Packet130UpdateSign paramPacket130UpdateSign) { a(paramPacket130UpdateSign); }
/*     */ 
/*     */ 
/*     */   
/* 166 */   public void a(Packet105CraftProgressBar paramPacket105CraftProgressBar) { a(paramPacket105CraftProgressBar); }
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void a(Packet5EntityEquipment paramPacket5EntityEquipment) { a(paramPacket5EntityEquipment); }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public void a(Packet106Transaction paramPacket106Transaction) { a(paramPacket106Transaction); }
/*     */ 
/*     */ 
/*     */   
/* 178 */   public void a(Packet25EntityPainting paramPacket25EntityPainting) { a(paramPacket25EntityPainting); }
/*     */ 
/*     */ 
/*     */   
/* 182 */   public void a(Packet54PlayNoteBlock paramPacket54PlayNoteBlock) { a(paramPacket54PlayNoteBlock); }
/*     */ 
/*     */ 
/*     */   
/* 186 */   public void a(Packet200Statistic paramPacket200Statistic) { a(paramPacket200Statistic); }
/*     */ 
/*     */ 
/*     */   
/* 190 */   public void a(Packet17 paramPacket17) { a(paramPacket17); }
/*     */ 
/*     */ 
/*     */   
/* 194 */   public void a(Packet27 paramPacket27) { a(paramPacket27); }
/*     */ 
/*     */ 
/*     */   
/* 198 */   public void a(Packet70Bed paramPacket70Bed) { a(paramPacket70Bed); }
/*     */ 
/*     */ 
/*     */   
/* 202 */   public void a(Packet71Weather paramPacket71Weather) { a(paramPacket71Weather); }
/*     */ 
/*     */ 
/*     */   
/* 206 */   public void a(Packet131 paramPacket131) { a(paramPacket131); }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public void a(Packet61 paramPacket61) { a(paramPacket61); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NetHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */