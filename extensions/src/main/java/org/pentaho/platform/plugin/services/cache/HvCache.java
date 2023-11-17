package org.pentaho.platform.plugin.services.cache;

import net.sf.ehcache.Ehcache;
import org.hibernate.Cache;
import org.hibernate.cache.ehcache.internal.StorageAccessImpl;
import org.hibernate.cache.spi.DirectAccessRegion;

import java.util.Set;

public interface HvCache extends Cache {
  Set getAllKeysFromRegionCache( String region );

  DirectAccessRegion getDirectAccessRegion();

  StorageAccessImpl getStorageAccess();

  /**
   * Exposes the underlaying EhCache associated with this HvCache
   * @return The EhCache
   */
  Ehcache getCache();

  //Set toMap( );
}
