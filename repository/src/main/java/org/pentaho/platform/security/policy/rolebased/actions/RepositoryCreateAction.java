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

package org.pentaho.platform.security.policy.rolebased.actions;

public class RepositoryCreateAction extends AbstractAuthorizationAction {
  public static final String NAME = "org.pentaho.repository.create";

  @Override
  public String getName() {
    return NAME;
  }
}
