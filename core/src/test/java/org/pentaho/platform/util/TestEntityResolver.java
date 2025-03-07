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


package org.pentaho.platform.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.pentaho.platform.api.engine.IDocumentResourceLoader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestEntityResolver implements IDocumentResourceLoader {

  private static TestEntityResolver instance;

  public static TestEntityResolver getInstance() {
    if ( TestEntityResolver.instance == null ) {
      TestEntityResolver.instance = new TestEntityResolver();
    }
    return TestEntityResolver.instance;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
   */
  public InputSource resolveEntity( final String publicId, final String systemId ) throws SAXException, IOException {

    int idx = systemId.lastIndexOf( '/' );
    String dtdName = systemId.substring( idx + 1 );

    try {
      InputStream xslIS = getClass().getClassLoader().getResourceAsStream( "system/dtd/" + dtdName ); //$NON-NLS-1$
      if ( xslIS != null ) {
        InputSource source = new InputSource( xslIS );
        return source;
      }
    } catch ( Exception e ) {
      // ignored
    }
    return null;
  }

  public Source resolve( final String href, final String base ) {
    try {
      InputStream xslIS = new FileInputStream( "src/test/resources/solution/" + href );
      StreamSource xslSrc = new StreamSource( xslIS );
      return xslSrc;
    } catch ( Exception e ) {
      // ignored
    }
    return null;

  }

  public InputStream loadXsl( final String name ) {

    try {
      InputStream xslIS = new FileInputStream( name );
      return xslIS;
    } catch ( Exception e ) {
      // ignored
    }
    return null;
  }

}
