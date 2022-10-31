# Keycloak Extensions

This repository includes custom policies to exetend Keycloak's functionalities. 

## Table of content

- [Setup](#setup)
- [Custom Event Listener](#custom-event-listener)
- [Custom Password Policy](#custom-password-policy)

## Setup
- For development and testing, set up Keycloak locally with: `docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev
`


## Custom Event Listener
- Emails an admin on User and Admin Events.

## Custom Password Policy
- Enforces 24 hours as the minimum lifetime for passwords.
Lets build a custom event listener, that sends an email to an admin on every new registration.

Follow this [link](custom-event-listener/README.md) for more details.

## Instructions to download and use:

The current .jar file can be downloaded directly from [here](./assets/password-min-time-policy-current.jar)
```xml
<dependency>
  <groupId>org.keycloak.policy</groupId>
  <artifactId>password-min-time-policy</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```
```bash
mvn install
```
