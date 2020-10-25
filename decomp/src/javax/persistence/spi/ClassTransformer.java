package javax.persistence.spi;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public interface ClassTransformer {
  byte[] transform(ClassLoader paramClassLoader, String paramString, Class<?> paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfByte) throws IllegalClassFormatException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\spi\ClassTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */