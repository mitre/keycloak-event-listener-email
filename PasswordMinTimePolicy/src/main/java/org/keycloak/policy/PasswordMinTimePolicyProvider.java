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
        return null;
    }

    @Override
    public PolicyError validate(RealmModel realm, UserModel user, String password) {

        logger.info("---------------validate called-------------------");
        PasswordCredentialProvider passwordCredentialProvider = new PasswordCredentialProvider(session);
        PasswordCredentialModel passwordCredentialModel = passwordCredentialProvider.getPassword(realm, user);

        logger.info("-----------------credential created time---------------");
        long createdTime=passwordCredentialModel.getCreatedDate();
        logger.info(createdTime);

        logger.info("-----------------current time---------------");
        long currentTime = Time.currentTimeMillis();
        logger.info(currentTime);

        logger.info("-----------------elapsed time---------------");
        long timeElapsed= currentTime-createdTime;
        logger.info(timeElapsed);

        PasswordPolicy policy = session.getContext().getRealm().getPasswordPolicy();
        int passwordMinLifeValue = policy.getPolicyConfig(PasswordMinTimePolicyProviderFactory.MINIMAL_PASSWORD_LIFE_ID);
        logger.info("----------------Configured Minimum Life Time (in hours)------------------");
        logger.info(passwordMinLifeValue);

        logger.info("------------Configured Minimum Life Time (in milliseconds)");
        long passwordMinLifeValueInMillis = TimeUnit.DAYS.toMillis(passwordMinLifeValue);
        logger.info(passwordMinLifeValueInMillis);

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