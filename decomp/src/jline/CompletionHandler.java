package jline;

import java.io.IOException;
import java.util.List;

public interface CompletionHandler {
  boolean complete(ConsoleReader paramConsoleReader, List paramList, int paramInt) throws IOException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\CompletionHandler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */