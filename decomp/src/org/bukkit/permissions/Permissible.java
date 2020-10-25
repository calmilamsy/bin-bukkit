package org.bukkit.permissions;

import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface Permissible extends ServerOperator {
  boolean isPermissionSet(String paramString);
  
  boolean isPermissionSet(Permission paramPermission);
  
  boolean hasPermission(String paramString);
  
  boolean hasPermission(Permission paramPermission);
  
  PermissionAttachment addAttachment(Plugin paramPlugin, String paramString, boolean paramBoolean);
  
  PermissionAttachment addAttachment(Plugin paramPlugin);
  
  PermissionAttachment addAttachment(Plugin paramPlugin, String paramString, boolean paramBoolean, int paramInt);
  
  PermissionAttachment addAttachment(Plugin paramPlugin, int paramInt);
  
  void removeAttachment(PermissionAttachment paramPermissionAttachment);
  
  void recalculatePermissions();
  
  Set<PermissionAttachmentInfo> getEffectivePermissions();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\permissions\Permissible.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */