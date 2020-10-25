package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.tokens.Token;

public interface Scanner {
  boolean checkToken(Token.ID... paramVarArgs);
  
  Token peekToken();
  
  Token getToken();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\scanner\Scanner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */