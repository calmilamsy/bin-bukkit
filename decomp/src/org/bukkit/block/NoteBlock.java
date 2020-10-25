package org.bukkit.block;

import org.bukkit.Instrument;
import org.bukkit.Note;

public interface NoteBlock extends BlockState {
  Note getNote();
  
  byte getRawNote();
  
  void setNote(Note paramNote);
  
  void setRawNote(byte paramByte);
  
  boolean play();
  
  boolean play(byte paramByte1, byte paramByte2);
  
  boolean play(Instrument paramInstrument, Note paramNote);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\block\NoteBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */