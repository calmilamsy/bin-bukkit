/*    */ package org.bukkit.craftbukkit.block;
/*    */ 
/*    */ import net.minecraft.server.TileEntityNote;
/*    */ import org.bukkit.Instrument;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Note;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.NoteBlock;
/*    */ import org.bukkit.craftbukkit.CraftWorld;
/*    */ 
/*    */ public class CraftNoteBlock
/*    */   extends CraftBlockState implements NoteBlock {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityNote note;
/*    */   
/*    */   public CraftNoteBlock(Block block) {
/* 17 */     super(block);
/*    */     
/* 19 */     this.world = (CraftWorld)block.getWorld();
/* 20 */     this.note = (TileEntityNote)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */ 
/*    */   
/* 24 */   public Note getNote() { return new Note(this.note.note); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public byte getRawNote() { return this.note.note; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void setNote(Note n) { this.note.note = n.getId(); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public void setRawNote(byte n) { this.note.note = n; }
/*    */ 
/*    */   
/*    */   public boolean play() {
/* 40 */     Block block = getBlock();
/*    */     
/* 42 */     synchronized (block) {
/* 43 */       if (block.getType() == Material.NOTE_BLOCK) {
/* 44 */         this.note.play(this.world.getHandle(), getX(), getY(), getZ());
/* 45 */         return true;
/*    */       } 
/* 47 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean play(byte instrument, byte note) {
/* 53 */     Block block = getBlock();
/*    */     
/* 55 */     synchronized (block) {
/* 56 */       if (block.getType() == Material.NOTE_BLOCK) {
/* 57 */         this.world.getHandle().playNote(getX(), getY(), getZ(), instrument, note);
/* 58 */         return true;
/*    */       } 
/* 60 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean play(Instrument instrument, Note note) {
/* 66 */     Block block = getBlock();
/*    */     
/* 68 */     synchronized (block) {
/* 69 */       if (block.getType() == Material.NOTE_BLOCK) {
/* 70 */         this.world.getHandle().playNote(getX(), getY(), getZ(), instrument.getType(), note.getId());
/* 71 */         return true;
/*    */       } 
/* 73 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\block\CraftNoteBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */