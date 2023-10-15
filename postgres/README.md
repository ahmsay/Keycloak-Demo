# Setup with External DB

A more production ready approach would be to use an external db such as PostgreSQL. For production purposes, you can
store the db and keycloak credentials more securely.

For simplicity, hostname and https is disabled. You can change them by removing <b>--http-enabled</b> and <b>--hostname-strict</b> parameters
in the docker-compose.yaml file and apply a host name an ssl certificate.

If you don't want to lose your Keycloak data when the containers are gone, you can do a volume mapping as well. You can do this by uncommenting 
volumes line in the docker-compose.yaml file.

To run:
```
docker-compose up -d
```