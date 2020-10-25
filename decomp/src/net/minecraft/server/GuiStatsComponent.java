/*    */ package net.minecraft.server;
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.Timer;
/*    */ 
/*    */ public class GuiStatsComponent extends JComponent {
/*    */   private int[] a;
/*    */   
/*    */   public GuiStatsComponent() {
/* 12 */     this.a = new int[256];
/* 13 */     this.b = 0;
/* 14 */     this.c = new String[10];
/*    */ 
/*    */     
/* 17 */     setPreferredSize(new Dimension('Ā', 'Ä'));
/* 18 */     setMinimumSize(new Dimension('Ā', 'Ä'));
/* 19 */     setMaximumSize(new Dimension('Ā', 'Ä'));
/* 20 */     (new Timer('Ǵ', new GuiStatsListener(this))).start();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 25 */     setBackground(Color.BLACK);
/*    */   }
/*    */   private int b; private String[] c;
/*    */   private void a() {
/* 29 */     long l = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
/* 30 */     System.gc();
/* 31 */     this.c[0] = "Memory use: " + (l / 1024L / 1024L) + " mb (" + (Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory()) + "% free)";
/* 32 */     this.c[1] = "Threads: " + NetworkManager.b + " + " + NetworkManager.c;
/* 33 */     this.a[this.b++ & 0xFF] = (int)(l * 100L / Runtime.getRuntime().maxMemory());
/* 34 */     repaint();
/*    */   }
/*    */   
/*    */   public void paint(Graphics paramGraphics) {
/* 38 */     paramGraphics.setColor(new Color(16777215));
/* 39 */     paramGraphics.fillRect(0, 0, 256, 192);
/*    */     int i;
/* 41 */     for (i = 0; i < 256; i++) {
/* 42 */       int j = this.a[i + this.b & 0xFF];
/* 43 */       paramGraphics.setColor(new Color(j + 28 << 16));
/* 44 */       paramGraphics.fillRect(i, 100 - j, 1, j);
/*    */     } 
/* 46 */     paramGraphics.setColor(Color.BLACK);
/* 47 */     for (i = 0; i < this.c.length; i++) {
/* 48 */       String str = this.c[i];
/* 49 */       if (str != null) paramGraphics.drawString(str, 32, 116 + i * 16); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\GuiStatsComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */