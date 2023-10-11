package org.pentaho.platform.plugin.services.security.userrole.keycloak;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

public class DefaultKeyCloakProvider implements InitializingBean{
  public SecurityFilterChain configure( HttpSecurity http ) throws Exception {
    http
      .oauth2Client()
      .and()
      .oauth2Login()
      .tokenEndpoint()
      .and()
      .userInfoEndpoint();

    http
      .sessionManagement()
      .sessionCreationPolicy( SessionCreationPolicy.ALWAYS );

    http
      .authorizeRequests()
      .antMatchers( "/login/**" ).permitAll()
      .antMatchers( "/oauth2/**" ).permitAll()
      .antMatchers( "/unauthenticated" ).permitAll()

      .and()
      .logout()
      .logoutSuccessUrl(
        "http://10.0.1.66:8080/realms/pentaho/protocol/openid-connect/logout?redirect_uri=http://10.0.1.3:8081/" );

    return http.build();
  }

  @Override public void afterPropertiesSet() throws Exception {

  }
}
