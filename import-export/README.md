# Import / Export Example

You may want to import and export your realm when you are working with Keycloak.
For production environments, please use Keycloak with PostgreSQL. For development purposes, let's continue.

<a href="https://www.keycloak.org/server/importExport">Official Documentation</a>

## Export
After creating a realm and setting it up, you can export it with the command below inside the container:
```
sh /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --realm myrealm
```
- Since this is a container, running kc.sh is a little different from the official docs.
- The directory can be any directory you want, but the one in the example is easier to import later.
- You can delete default values in your exported realm file.
- You also need to configure a volume mapping in docker-compose.yaml file like this:
```
    volumes:
    - ./exported-realm:/opt/keycloak/data/import
```
## Import
To import the realm automatically when the Keycloak container starts, you must add the <b>--import-realm</b> command
after the <b>start</b> command in the docker-compose.yaml file.
```
    command:
    - start-dev --import-realm
```
- When you delete the container and start it again, your exported realm will be inside <b>/opt/keycloak/data/import</b>
directory, thanks to the volume mapping.
- The <b>--import-realm</b> command automatically looks inside that directory and Keycloak will import any realm inside it.
