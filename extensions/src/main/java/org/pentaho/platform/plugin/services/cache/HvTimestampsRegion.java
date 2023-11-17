package org.pentaho.platform.plugin.services.cache;

import org.hibernate.cache.ehcache.internal.StorageAccessImpl;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.support.StorageAccess;
import org.hibernate.cache.spi.support.TimestampsRegionTemplate;

public class HvTimestampsRegion extends TimestampsRegionTemplate {
  StorageAccess storageAccess;

  public HvTimestampsRegion( String name, RegionFactory regionFactory, StorageAccess storageAccess) {
    super( name, regionFactory, storageAccess );
  }

  public StorageAccessImpl getStorageAccess() {
    return (StorageAccessImpl) super.getStorageAccess();
  }
}
