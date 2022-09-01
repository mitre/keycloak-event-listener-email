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
