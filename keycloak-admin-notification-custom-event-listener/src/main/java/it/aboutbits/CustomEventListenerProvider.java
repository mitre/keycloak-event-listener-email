package it.aboutbits;
import org.jboss.logging.Logger;
import org.keycloak.email.DefaultEmailSenderProvider;
import org.keycloak.email.EmailException;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

public class CustomEventListenerProvider implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);

    private final KeycloakSession session;
    private final RealmProvider model;

    public CustomEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {
        if (
        //account creation    
        EventType.REGISTER.equals(event.getType())
        //account modification 
        || EventType.UPDATE_EMAIL.equals(event.getType())
        || EventType.UPDATE_PROFILE.equals(event.getType())
        || EventType.UPDATE_PASSWORD.equals(event.getType())
        || EventType.UPDATE_PROFILE.equals(event.getType())
        || EventType.UPDATE_PASSWORD.equals(event.getType())
        ) {
            log.infof("## NEW %s EVENT", event.getType());
            log.info("-----------------------------------------------------------");

            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel user = this.session.users().getUserById(event.getUserId(), realm);

            String emailPlainContent = "User action\n\n" +
                    "Action: "+ event.getType() + "\n" +
                    "Session Id: " + event.getSessionId() + "\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Username: " + user.getUsername() + "\n" +
                    "Client: " + event.getClientId();

            String emailHtmlContent = "<h1>User action</h1>" +
                    "<ul>" +
                    "<li>Action: " + event.getType() + "</li>" +
                    "<li>Session Id: " + event.getSessionId() + "</li>" +
                    "<li>Email: " + user.getEmail() + "</li>" +
                    "<li>Username: " + user.getUsername() + "</li>" +
                    "<li>Client: " + event.getClientId() + "</li>" +
                    "</ul>";

            DefaultEmailSenderProvider senderProvider = new DefaultEmailSenderProvider(session);

            try {
                senderProvider.send(session.getContext().getRealm().getSmtpConfig(), new AdminUser(), "Keycloak - New User Action", emailPlainContent, emailHtmlContent);
            } catch (EmailException e) {
                log.error("Failed to send email", e);
            }
            log.info("-----------------------------------------------------------");
        }

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

        if (OperationType.CREATE.equals(adminEvent.getOperationType()) ||
        OperationType.UPDATE.equals(adminEvent.getOperationType()) ||
        OperationType.DELETE.equals(adminEvent.getOperationType()) ||
        OperationType.ACTION.equals(adminEvent.getOperationType())
        ) {
            log.infof("## NEW ADMIN %s EVENT", adminEvent.getOperationType());
            log.info("-----------------------------------------------------------");

            String emailPlainContent = "Admin - Account operation\n\n" +
                    "Operation: " + adminEvent.getOperationType() + "\n" +
                    "Resource Path: " + adminEvent.getResourcePath() + "\n" +
                    "Resource Type: " + adminEvent.getResourceTypeAsString();

            String emailHtmlContent = "<h1>Admin - Account operation</h1>" +
                    "<ul>" +
                    "<li>Operation: " + adminEvent.getOperationType() + "</li>" +
                    "<li>Resource Path: " + adminEvent.getResourcePath() + "</li>" +
                    "<li>Resource Type: " + adminEvent.getResourceTypeAsString() + "</li>" +
                    "</ul>";

            DefaultEmailSenderProvider senderProvider = new DefaultEmailSenderProvider(session);

            try {
                senderProvider.send(session.getContext().getRealm().getSmtpConfig(), new AdminUser(), "Keycloak - Account Operation", emailPlainContent, emailHtmlContent);
            } catch (EmailException e) {
                log.error("Failed to send email", e);
            }
            log.info("-----------------------------------------------------------");
        }
    }

    @Override
    public void close() {

    }
}
