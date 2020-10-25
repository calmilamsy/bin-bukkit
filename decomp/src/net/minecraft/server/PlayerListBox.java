/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import javax.swing.JList;
/*    */ 
/*    */ public class PlayerListBox extends JList implements IUpdatePlayerListBox {
/*    */   private MinecraftServer a;
/*    */   private int b;
/*    */   
/*    */   public PlayerListBox(MinecraftServer paramMinecraftServer) {
/* 11 */     this.b = 0;
/*    */ 
/*    */     
/* 14 */     this.a = paramMinecraftServer;
/* 15 */     paramMinecraftServer.a(this);
/*    */   }
/*    */   
/*    */   public void a() {
/* 19 */     if (this.b++ % 20 == 0) {
/* 20 */       Vector vector = new Vector();
/* 21 */       for (byte b1 = 0; b1 < this.a.serverConfigurationManager.players.size(); b1++) {
/* 22 */         vector.add(((EntityPlayer)this.a.serverConfigurationManager.players.get(b1)).name);
/*    */       }
/* 24 */       setListData(vector);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PlayerListBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */