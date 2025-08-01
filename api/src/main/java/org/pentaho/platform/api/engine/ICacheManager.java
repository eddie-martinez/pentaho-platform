/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.platform.api.engine;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface ICacheManager extends ILogoutListener {
  public static final String SESSION = "SESSION"; //$NON-NLS-1$
  public static final String GLOBAL = "GLOBAL"; //$NON-NLS-1$

  /**
   * Stops the cache by calling the cacheProvider stop method. This method should be called either when the VM goes
   * away, or when the web context goes away. This performs required cleanup in the underlying cache
   * implementation. In some cases, failure to stop the cache will cause cached items saved to disk to be
   * un-recoverable.
   * 
   */
  public void cacheStop();

  /**
   * Removes any session-based data for the specified <code>IPentahoSession</code>.
   * 
   * @param session
   *          The session whose objects needs to be removed from the cache.
   */
  public void killSessionCache( IPentahoSession session );

  /**
   * Removes all cached items that are session-based.
   * 
   */
  public void killSessionCaches();

  /**
   * Puts an object in the session-specific cache. The session specified must have a valid session id.
   * <p>
   * Take special care that, in a TestCase, you don't have multiple StandaloneSession objects with the same session
   * key. Consider using <code>UUIDUtil</code> to generate a unique sessionId for each standalone session.
   * 
   * @param session
   *          The users IPentahoSession
   * @param key
   *          The key by which you want to retrieve the data back out
   * @param value
   *          The data item to place into the cache
   */
  public void putInSessionCache( IPentahoSession session, String key, Object value );

  /**
   * Entirely clears the cache.
   * 
   */
  public void clearCache();

  /**
   * Removes a data item from the user session specific cache
   * 
   * @param session
   *          The users IPentahoSession
   * @param key
   *          The key that maps to the value needing removed
   */
  public void removeFromSessionCache( IPentahoSession session, String key );

  /**
   * Gets an object from the user session specific cache. If the object doesn't exist in the cache, null is
   * returned.
   * 
   * @param session
   *          The users IPentahoSession
   * @param key
   *          The key that maps to the data to get from the cache
   * @return Object that was stored in the cache
   */
  public Object getFromSessionCache( IPentahoSession session, String key );

  /**
   * Returns the enablement state of the cache.
   * 
   * @return true if the cache has been initialized and is ready for use.
   */
  public boolean cacheEnabled();

  // ~======= Support for global cache (non-session based)

  /**
   * Puts an object directly into the cache without translating the passed in key.
   * <p>
   * Important note - most cache implementations require both the key and the value to be serializable.
   * <p>
   * 
   * @param key
   *          Object by which the data is indexed
   * @param value
   *          The data to store in the cache.
   */
  public void putInGlobalCache( Object key, Object value );

  /**
   * Gets an object from the cache without translating the passed in key.
   * 
   * @param key
   *          The key that the data object was stored with
   * @return The corresponding data object
   */
  public Object getFromGlobalCache( Object key );

  /**
   * Removes an object from the cache
   * 
   * @param key
   *          The key that the data object was stored with
   */
  public void removeFromGlobalCache( Object key );

  /**
   * Returns the enablement state of the cache.
   * 
   * @return true if the cache has been initialized and is ready for use.
   */
  public boolean cacheEnabled( String region );

  public void onLogout( IPentahoSession session );

  public boolean addCacheRegion( String region );

  public boolean addCacheRegion( String region, Properties cacheProperties );

  /**
   * Clears any data for the specified for a specific region(For example region could be session, global etc)
   * 
   * @param region
   *          The region whose objects needs to be removed from the cache.
   */
  public void clearRegionCache( String region );

  /**
   * Clears any data for the specified for a specific region(For example region could be session, global etc) and
   * removed the region from the map
   * 
   * @param region
   *          The region whose objects needs to be removed from the cache.
   */
  public void removeRegionCache( String region );

  /**
   * Puts an object directly into the cache of a specific region
   * <p>
   * Important note - most cache implementations require both the key and the value to be serializable.
   * <p>
   * 
   * @param region
   *          the region where the object will be put in the cache
   * @param key
   *          Object by which the data is indexed
   * @param value
   *          The data to store in the cache.
   * 
   */
  public void putInRegionCache( String region, Object key, Object value );

  /**
   * Gets an object from the cache within a specific region
   * 
   * @param region
   *          the region where the object was put in the cache
   * @param key
   *          The key that the data object was stored with
   * @return The corresponding data object
   */

  public Object getFromRegionCache( String region, Object key );

  /**
   * Get a Set of Map.Entry objects from the cache within a specific region
   * 
   * @param region
   *          the region where the object was put in the cache
   * @return The corresponding list of objects
   */
  @SuppressWarnings( "rawtypes" )
  public Set getAllEntriesFromRegionCache( String region );

  /**
   * Get a Set of Key objects from the cache within a specific region
   * 
   * @param region
   *          the region where the object was put in the cache
   * @return The corresponding list of objects
   */
  @SuppressWarnings( "rawtypes" )
  public Set getAllKeysFromRegionCache( String region );

  /**
   * Get a list of values from the cache within a specific region
   * 
   * @param region
   *          the region where the object was put in the cache
   * @return The corresponding list of objects
   */
  @SuppressWarnings( "rawtypes" )
  public List getAllValuesFromRegionCache( String region );

  /**
   * Removes an object from the cache within a specific region
   * 
   * @param region
   *          the region where the object was put in the cache
   * @param key
   *          The key that the data object was stored with
   **/

  public void removeFromRegionCache( String region, Object key );

  // Removing the Cache entries count methods because
  //      1. These methods not being used
  //      2. Hibernate + JCache doesn't have a straight forward way to get the number
}
