/*!
 *
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 *
 * Copyright (c) 2002-2018 Hitachi Vantara. All rights reserved.
 *
 */

package org.pentaho.platform.plugin.services.cache;

//removed in the change from Hibernate 3 to Hibernate 4
//possibly use net.sf.ehcache.config.CacheConfiguration.PoolUsage.Cache
import net.sf.ehcache.Ehcache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.UnknownEntityTypeException;
import org.hibernate.cache.ehcache.internal.StorageAccessImpl;
import org.hibernate.cache.spi.DirectAccessRegion;
import org.hibernate.cache.spi.access.EntityDataAccess;
import org.hibernate.cache.spi.support.StorageAccess;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.metamodel.spi.MetamodelImplementor;
import org.hibernate.persister.entity.EntityPersister;
import org.pentaho.platform.api.cache.ILastModifiedCacheItem;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * User: rfellows Date: 10/25/11 Time: 3:53 PM
 */
public class LastModifiedCache implements ILastModifiedCacheItem, HvCache {
  //private Cache cache;
  private DirectAccessRegion cache;
  private SessionFactory sessionFactory;
  private long lastModified;
  protected static final Log LOGGER = LogFactory.getLog( LastModifiedCache.class );
  //private static final CoreMessageLogger LOG = CoreLogging.messageLogger( LastModifiedCache.class );

  public LastModifiedCache( DirectAccessRegion cache, SessionFactory sessionFactory ) {
    this.cache = cache;
    this.sessionFactory = sessionFactory;
    setLastModified();
  }

  public LastModifiedCache( DirectAccessRegion cache, SessionFactory sessionFactory, long lastModified ) {
    this.cache = cache;
    this.sessionFactory = sessionFactory;
    this.lastModified = lastModified;
  }

  @Override
  public long getLastModified() {
    return lastModified;
  }

  @Override public String getCacheKey() {
    return null;
  }

  public void setLastModified( long lastModified ) {
    this.lastModified = lastModified;
  }

  protected void setLastModified() {
    this.lastModified = new Date().getTime();
  }

//  @Override
//  public String getCacheKey() {
//    return cache.getRegionName();
//  }
//
//  @Override
//  public Object read( Object o ) throws CacheException {
//    return cache.read( o );
//  }
//
//  @Override
//  public Object get( Object o ) throws CacheException {
//    return cache.get( o );
//  }
//
//  @Override
//  public void put( Object o, Object o1 ) throws CacheException {
//    cache.put( o, o1 );
//    setLastModified();
//  }
//
//  @Override
//  public void update( Object o, Object o1 ) throws CacheException {
//    cache.update( o, o1 );
//    setLastModified();
//  }
//
//  @Override
//  public void remove( Object o ) throws CacheException {
//    cache.remove( o );
//    setLastModified();
//  }
//
//  @Override
//  public void clear() throws CacheException {
//    cache.clear();
//    setLastModified();
//  }
//
//  @Override
//  public void destroy() throws CacheException {
//    cache.destroy();
//    setLastModified();
//  }
//
//  @Override
//  public void lock( Object o ) throws CacheException {
//    cache.lock( o );
//  }
//
//  @Override
//  public void unlock( Object o ) throws CacheException {
//    cache.unlock( o );
//  }
//
//  @Override
//  public long nextTimestamp() {
//    return cache.nextTimestamp();
//  }
//
//  @Override
//  public int getTimeout() {
//    return cache.getTimeout();
//  }
//
//  @Override
//  public String getRegionName() {
//    return cache.getRegionName();
//  }
//
//  @Override
//  public long getSizeInMemory() {
//    return cache.getSizeInMemory();
//  }
//
//  @Override
//  public long getElementCountInMemory() {
//    return cache.getElementCountInMemory();
//  }
//
//  @Override
//  public long getElementCountOnDisk() {
//    return cache.getElementCountOnDisk();
//  }
//
//  @Override
//  public Map toMap() {
//    try {
//      return cache.toMap();
//    } catch ( Exception e ) {
//      return null;
//    }
//  }

  @Override public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  @Override public boolean containsEntity( Class entityClass, Serializable identifier ) {
    return false;
  }

  @Override public boolean containsEntity( String entityName, Serializable identifier ) {
    return false;
  }

  @Override public void evictEntityData( Class entityClass, Serializable identifier ) {
    int x = 0;
  }

  @Override public void evictEntityData( String entityName, Serializable identifier ) {
    int x = 0;
  }

  @Override public void evictEntityData( Class entityClass ) {
    int x = 0;
  }

  @Override public void evictEntityData( String entityName ) {
    //
    //Object o = (MetamodelImplementor ) getSessionFactory().getMetamodel();
    try {
      evictEntityData(
        ( (MetamodelImplementor) getSessionFactory().getMetamodel() ).locateEntityPersister( entityName ) );
    } catch ( UnknownEntityTypeException e) {
      //Nothing to do if the entry is not there.
    }
  }

  @Override public void evictEntityData() {
    int x = 0;
  }

  @Override public void evictNaturalIdData( Class entityClass ) {
    int x = 0;
  }

  @Override public void evictNaturalIdData( String entityName ) {
    int x = 0;
  }

  @Override public void evictNaturalIdData() {
    int x = 0;
  }

  @Override public boolean containsCollection( String role, Serializable ownerIdentifier ) {
    return false;
  }

  @Override public void evictCollectionData( String role, Serializable ownerIdentifier ) {
    int x = 0;
  }

  @Override public void evictCollectionData( String role ) {
    int x = 0;
  }

  @Override public void evictCollectionData() {
    int x = 0;
  }

  @Override public boolean containsQuery( String regionName ) {
    return false;
  }

  @Override public void evictDefaultQueryRegion() {
    int x = 0;
  }

  @Override public void evictQueryRegion( String regionName ) {
    int x = 0;
  }

  @Override public void evictQueryRegions() {
    int x = 0;
  }

  @Override public void evictRegion( String regionName ) {
    int x = 0;
  }

  @Override public boolean contains( Class aClass, Object o ) {
    return false;
  }

  @Override public void evict( Class aClass, Object o ) {
    int x = 0;
  }

  @Override public void evict( Class aClass ) {
    int x = 0;
  }

  @Override public <T> T unwrap( Class<T> aClass ) {
    return null;
  }

  @Override public Set getAllKeysFromRegionCache( String region ) {
    return null;
  }

  @Override public DirectAccessRegion getDirectAccessRegion() {
    return cache;
  }

  private void evictEntityData( EntityPersister entityDescriptor ) {
    EntityPersister rootEntityDescriptor = entityDescriptor;
    if ( entityDescriptor.isInherited()
      && !entityDescriptor.getEntityName().equals( entityDescriptor.getRootEntityName() ) ) {
      rootEntityDescriptor = ( (MetamodelImplementor) getSessionFactory().getMetamodel() ).locateEntityPersister(
        entityDescriptor.getRootEntityName() );
    }

    evictEntityData( rootEntityDescriptor.getNavigableRole(), rootEntityDescriptor.getCacheAccessStrategy() );

  }

  private void evictEntityData( NavigableRole navigableRole, EntityDataAccess cacheAccess) {
    if ( cacheAccess == null ) {
      return;
    }

    if ( LOGGER.isDebugEnabled() ) {
      LOGGER.debug( String.format( "Evicting entity cache: %s", navigableRole.getFullPath() ) );
    }

    cacheAccess.evictAll();
  }

  @Override public StorageAccessImpl getStorageAccess(){
    return ( (HvTimestampsRegion) cache ).getStorageAccess();
  }

  @Override public Ehcache getCache() {
    return getStorageAccess().getCache();
  }
}
