package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;

public interface Parser {
  boolean checkEvent(Event.ID paramID);
  
  Event peekEvent();
  
  Event getEvent();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\parser\Parser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */