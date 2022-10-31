# Registration Event Listener

This is a custom event listener for Keycloak events. 
Its goal is to notify an admin about Keycloak operations (admin or user events) via email.

## How to build the artifact?

```
mvn clean install
```

## How to add the jar in Keycloak?

Copy the jar in the target folder to the `/providers` folder.


## How to configure the event listener in Keycloak?

Set the event listener

1. Open up Keycloak administration console and select your realm
2. Go to events in the left side bar under Manage
3. Open the config [tab]
4. click in the input box next to event listeners, a dropdown with all available event listeners is shown
5. select our custom_event_listener

### SMTP Server

In order to test the whole workflow locally, [Mailhog](https://github.com/mailhog/MailHog) is set up and can be configured as SMTP server that catches all outgoing mails. These are the SMTP configurations:

- Host: localhost
- Port: 1025

All catched emails can be visited at [http://localhost:8025/](http://localhost:8025/).

## How to test it?

After you have setup everything and configured the listener do the following:

1. Open the web interface of Mailhog at http://localhost:8085
2. Register a new user on keycloak or perform other operations that a event listener is listening on. 
3. Go to Mailhog and check that it catched a mail for the admin
