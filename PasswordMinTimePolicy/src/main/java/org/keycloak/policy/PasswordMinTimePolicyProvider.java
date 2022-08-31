package org.keycloak.policy;

import org.jboss.logging.Logger;
import org.keycloak.common.util.Time;
import org.keycloak.credential.PasswordCredentialProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class PasswordMinTimePolicyProvider implements PasswordPolicyProvider {

    private static final Logger logger = Logger.getLogger(HistoryPasswordPolicyProvider.class);
    private static final String ERROR_MESSAGE = "invalidPasswordMinimumLengthMessage";

    private KeycloakSession session;

    public PasswordMinTimePolicyProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public PolicyError validate(String username, String password) {
        RealmModel realm = session.getContext().getRealm();
        UserModel user = session.users().getUserByUsername(realm, username);
        return validate(realm, user, password);
    }

    @Override
    public PolicyError validate(RealmModel realm, UserModel user, String password) {

        PasswordCredentialProvider passwordCredentialProvider = new PasswordCredentialProvider(session);
        PasswordCredentialModel passwordCredentialModel = passwordCredentialProvider.getPassword(realm, user);

        long createdTime=passwordCredentialModel.getCreatedDate();

        long currentTime = Time.currentTimeMillis();

        long timeElapsed= currentTime-createdTime;

        PasswordPolicy policy = session.getContext().getRealm().getPasswordPolicy();
        int passwordMinLifeValue = policy.getPolicyConfig(PasswordMinTimePolicyProviderFactory.MINIMAL_PASSWORD_LIFE_ID);

        long passwordMinLifeValueInMillis = TimeUnit.DAYS.toMillis(passwordMinLifeValue);

        if(passwordMinLifeValueInMillis<=timeElapsed){
            return null;
        }else{
            return new PolicyError(ERROR_MESSAGE, passwordMinLifeValue);
        }
    }

    @Override
    public Object parseConfig(String value) {
        return parseInteger(value, PasswordMinTimePolicyProviderFactory.DEFAULT_VALUE);
    }

    @Override
    public void close() {
    }

}