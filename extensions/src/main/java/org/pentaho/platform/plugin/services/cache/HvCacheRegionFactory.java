package org.pentaho.platform.plugin.services.cache;

import org.hibernate.cache.ehcache.internal.SingletonEhcacheRegionFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cache.spi.support.TimestampsRegionTemplate;
import org.hibernate.engine.spi.SessionFactoryImplementor;

public class HvCacheRegionFactory extends SingletonEhcacheRegionFactory {
  @Override
  public TimestampsRegion buildTimestampsRegion(
    String regionName, SessionFactoryImplementor sessionFactory) {
    verifyStarted();
    return new HvTimestampsRegion(
      regionName, this, createTimestampsRegionStorageAccess( regionName, sessionFactory ) );
  }
}
