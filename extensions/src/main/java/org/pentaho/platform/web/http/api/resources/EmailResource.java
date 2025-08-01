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


package org.pentaho.platform.web.http.api.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.email.IEmailConfiguration;
import org.pentaho.platform.api.email.IEmailService;
import org.pentaho.platform.api.engine.IAuthorizationPolicy;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.plugin.services.email.EmailConfiguration;
import org.pentaho.platform.plugin.services.email.EmailService;
import org.pentaho.platform.security.policy.rolebased.actions.AdministerSecurityAction;
import org.pentaho.platform.security.policy.rolebased.actions.RepositoryCreateAction;
import org.pentaho.platform.security.policy.rolebased.actions.RepositoryReadAction;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * Configures and manage the Email configuration in the platform
 * @author rmansoor
 *
 */

@org.codehaus.enunciate.XmlTransient
@Path( "/emailconfig/" )
public class EmailResource extends AbstractJaxRSResource {
  /**
   * The logger for this class
   */
  private static final Log logger = LogFactory.getLog( EmailResource.class );

  private IEmailService emailService = null;
  private IAuthorizationPolicy policy;

  /**
   * Constructs an instance of this class using the default email service
   * 
   * @throws IllegalArgumentException
   *           Indicates that the default location for the email configuration file is invalid
   */
  public EmailResource() throws IllegalArgumentException {

    emailService = PentahoSystem.get( IEmailService.class, "IEmailService", PentahoSessionHolder.getSession() );

    if ( emailService == null ) {
      emailService = new EmailService();
    }
    init( emailService );
  }

  /**
   * Constructs an instance of this class using the default email service
   * 
   * @throws IllegalArgumentException
   *           Indicates that the default location for the email configuration file is invalid
   */
  public EmailResource( final IEmailService emailService ) throws IllegalArgumentException {
    init( emailService );
  }

  private void init( final IEmailService emailService ) {
    if ( emailService == null ) {
      throw new IllegalArgumentException();
    }
    this.emailService = emailService;
    try {
      policy = PentahoSystem.get( IAuthorizationPolicy.class );
    } catch ( Exception ex ) {
      logger.warn( "Unable to get IAuthorizationPolicy: " + ex.getMessage() );
    }
  }

  /**
   * Delete the stored email configuration from the platform.
   * @param emailConfiguration <code> EmailConfiguration </code> 
   * @return
   */
  @GET
  @Path( "/resetEmailConfig" )
  @Produces( { MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON } )
  public Response deleteEmailConfig() {
    if ( canAdminister() ) {
      try {
        emailService.setEmailConfig( new EmailConfiguration() );
        return Response.ok().build();
      } catch ( Exception e ) {
        return Response.serverError().build();
      }
    } else {
      return Response.status( UNAUTHORIZED ).build();
    }
  }

  /**
   * Stores the email configuration in the platform
   * @param emailConfiguration <code> EmailConfiguration </code>
   * @return
   */
  @PUT
  @Path( "/setEmailConfig" )
  @Consumes( { MediaType.APPLICATION_JSON } )
  @Produces( { MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON } )
  public Response setEmailConfig( EmailConfiguration emailConfiguration ) {
    if ( canAdminister() ) {
      try {
        emailService.setEmailConfig( emailConfiguration );
        return Response.ok().build();
      } catch ( Exception e ) {
        return Response.serverError().build();
      }
    } else {
      return Response.status( UNAUTHORIZED ).build();
    }

  }

  /**
   * Retrieves the email configuration
   * @return emailConfiguration <code> EmailConfiguration </code>
   */
  @GET
  @Path( "/getEmailConfig" )
  @Produces( { MediaType.APPLICATION_JSON } )
  public IEmailConfiguration getEmailConfig() {
    if ( canAdminister() ) {
      try {
        return emailService.getEmailConfig();
      } catch ( Exception e ) {
        return new EmailConfiguration();
      }
    } else {
      return new EmailConfiguration();
    }
  }

  /**
   * Process the current email configuration
   * @param emailConfiguration <code> EmailConfiguration </code>
   * @return
   * @throws Exception
   */
  @PUT
  @Path( "/sendEmailTest" )
  @Consumes( { MediaType.APPLICATION_JSON } )
  @Produces( { MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON } )
  public Response sendEmailTest( EmailConfiguration emailConfiguration ) throws Exception {
    if ( canAdminister() ) {
      return Response.ok( emailService.sendEmailTest( emailConfiguration ) ).build();
    } else {
      return Response.status( UNAUTHORIZED ).build();
    }
  }
  /**
   * Checks whether the current email configuration is valid
   * @return ("true" or "false")
   */
  @GET
  @Path( "/isValid" )
  @Produces( { MediaType.TEXT_PLAIN } )
  public Response isValid() {
    try {
      if ( emailService.isValid() ) {
        return Response.ok( "true" ).build();
      }
    } catch ( Exception e ) {
      //ignore
    }
    return Response.ok( "false" ).build();
  }

  /**
   * Check if user has the rights to administrator
   * 
   */
  private boolean canAdminister() {
    return policy.isAllowed( RepositoryReadAction.NAME ) && policy.isAllowed( RepositoryCreateAction.NAME )
        && ( policy.isAllowed( AdministerSecurityAction.NAME ) );

  }
}
