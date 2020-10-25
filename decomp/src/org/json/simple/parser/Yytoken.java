package org.json.simple.parser;

public class Yytoken {
  public static final int TYPE_VALUE = 0;
  
  public static final int TYPE_LEFT_BRACE = 1;
  
  public static final int TYPE_RIGHT_BRACE = 2;
  
  public static final int TYPE_LEFT_SQUARE = 3;
  
  public static final int TYPE_RIGHT_SQUARE = 4;
  
  public static final int TYPE_COMMA = 5;
  
  public static final int TYPE_COLON = 6;
  
  public static final int TYPE_EOF = -1;
  
  public int type = 0;
  
  public Object value = null;
  
  public Yytoken(int paramInt, Object paramObject) {
    this.type = paramInt;
    this.value = paramObject;
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    switch (this.type) {
      case 0:
        stringBuffer.append("VALUE(").append(this.value).append(")");
        break;
      case 1:
        stringBuffer.append("LEFT BRACE({)");
        break;
      case 2:
        stringBuffer.append("RIGHT BRACE(})");
        break;
      case 3:
        stringBuffer.append("LEFT SQUARE([)");
        break;
      case 4:
        stringBuffer.append("RIGHT SQUARE(])");
        break;
      case 5:
        stringBuffer.append("COMMA(,)");
        break;
      case 6:
        stringBuffer.append("COLON(:)");
        break;
      case -1:
        stringBuffer.append("END OF FILE");
        break;
    } 
    return stringBuffer.toString();
  }
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\json\simple\parser\Yytoken.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.0.4
 */