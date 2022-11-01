# Keycloak Custom Modules for SRG hardening

This repository includes custom policies to exetend Keycloak's functionalities to support STIG-ready content for securing Keycloak against the [AAA SRG](https://dl.dod.cyber.mil/wp-content/uploads/stigs/zip/U_AAA_Services_V1R2_SRG.zip).

Content available on the Maven Central Repository.

See the subdirectory READMEs for info on using the modules/inserting them into your Keycloak installation

## Custom Event Listener
- Emails an admin on User and Admin Events.

## Custom Password Policy
- Enforces 24 hours as the minimum lifetime for passwords.
