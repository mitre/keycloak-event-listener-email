# Keycloak Extensions

This repository shows how you can extend Keycloak's features.

## Table of content

- [Prerequisites](#prerequesites)
- [Setup](#setup)
- [Custom Event Listener](#custom-event-listener)

## Setup

### Build the artefact of the custom event listener

If you want to test the example event listener, than you have to build it before you start the keycloak server.


### SMTP Server

In order to test the whole workflow locally, [Mailhog](https://github.com/mailhog/MailHog) is set up and can be configured as SMTP server that catches all outgoing mails. These are the SMTP configurations:

- Host: localhost
- Port: 1025

All catched emails can be visited at [http://localhost:8025/](http://localhost:8025/).

## Custom Event Listener

Lets build a custom event listener, that sends an email to an admin on every new registration.

Follow this [link](custom-event-listener/README.md) for more details.
