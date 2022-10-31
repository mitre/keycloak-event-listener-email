package org.keycloak.policy;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
* @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
*/
public class PasswordMinTimePolicyProviderFactory implements PasswordPolicyProviderFactory {

   //24 hours as minimum time
   public static final Integer DEFAULT_VALUE = 24;
   public static final String MINIMAL_PASSWORD_LIFE_ID="minimumPasswordLife";

   @Override
   public String getId() {
       return MINIMAL_PASSWORD_LIFE_ID;
   }

   @Override
   public PasswordPolicyProvider create(KeycloakSession session) {
       return new PasswordMinTimePolicyProvider(session);
   }

   @Override
   public void init(Config.Scope config) {
   }

   @Override
   public void postInit(KeycloakSessionFactory factory) {
   }

   @Override
   public String getDisplayName() {
       return "Minimum Life Time";
   }

   @Override
   public String getConfigType() {
       return PasswordPolicyProvider.INT_CONFIG_TYPE;
   }

   @Override
   public String getDefaultConfigValue() {
       return String.valueOf(PasswordMinTimePolicyProviderFactory.DEFAULT_VALUE);
   }

   @Override
   public boolean isMultiplSupported() {
       return false;
   }

   @Override
   public void close() {
   }

}