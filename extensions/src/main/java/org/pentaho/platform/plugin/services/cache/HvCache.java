package org.pentaho.platform.plugin.services.cache;

import org.hibernate.Cache;

import java.util.Set;

public interface HvCache extends Cache {
  Set getAllKeysFromRegionCache( String region );
}
