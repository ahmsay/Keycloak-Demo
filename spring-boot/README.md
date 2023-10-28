# Spring Boot Resource Server

This example will show you how to establish an authentication and authorization mechanism using Spring Boot and
Keycloak. The Spring Boot application is a resource server. That means it's not responsible for authentication. It only
validates the incoming JWT by connecting Keycloak. Authentication is done directly with Keycloak.

## Steps

- Run Keycloak with `docker-compose up -d` command in this directory.
- Login to Keycloak and create a realm called "myrealm". Keycloak credentials are written in the docker-compose.yaml
  file.
- Navigate to Clients page and create a client. Set the client id to "spring-boot". Click Next and Next again.
- In Login Settings page, add a new url in Valid redirect URIs section called "http://localhost:8080/*". Click Save.
- Create a new user. Username will be "zorkov".
- You'll see the user details page. In Credentials tab, set a password for the user. The password will be "asdzxc".
  Switch the temporary
  option to "Off" so you don't have to change the password.
- Start the Spring Boot application.
- You are good to go. Authenticate Keycloak to get the JWT. We will provide the grant type, username, password and
  client id.

```
curl -XPOST "http://127.0.0.1:9876/realms/myrealm/protocol/openid-connect/token" -d "grant_type=password&username=zorkov&client_id=spring-boot&password=asdzxc"
```

- Copy the access token from the response (Yes it's long).
- Make a new request to the Spring Boot application's /regular route with the access token. Replace \<access-token> with
  your access token.

```
curl -i http://localhost:8080/regular -H "Authorization: Bearer <access-token>"
```

- You'll see a "Hello, zorkov!" prompt at the end of the response. That's because you are authenticated successfully and
  the /regular route doesn't have an additional role for a user.
- Now try to send a request to /premium path.
- You'll see a 403 code and a message like this: "The request requires higher privileges than provided by the access
  token.". That's because /premium route requires a role called "PREMIUM".
- Go back to Keycloak and create a new role called "PREMIUM" under Realm Roles page.
- Navigate to Users page and open the details page of your user.
- Under Role Mapping tab, assign the PREMIUM role you created to the user.
- Login to Keycloak again so your new JWT can obtain the role you just assigned.
- Send the same request again to /premium path with your new JWT. Now you should see a "Hello, premium zorkov!" message.
