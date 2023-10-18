# OIDC Login Example (Confidental)

This example will show you how to establish an authentication mechanism using Apache APISIX and 
Open ID Connect confidential client type.

## Steps

- Run Keycloak and Apache APISIX with `docker-compose up -d` command in this directory.
- Login to Keycloak and create a realm called "myrealm". Keycloak credentials are written in the docker-compose.yaml file.
- Navigate to create a client. Set the client id to "apisix". Click Next.
- Switch the client authentication to "On". This makes our client type confidential. Click Next.
- In Login Settings page, add a new url in Valid redirect URIs section called "http://127.0.0.1:9080/anything/callback". Click Save.
- You'll see the client details page. In Credentials tab, copy the client secret.
- Paste the client secret it in "client_secret: ..." section in the <b>apisix-conf.yaml</b> file.
- Restart the APISIX container so it can get the correct client secret.
- Create a new user. Username will be "zorkov".
- You'll see the user details page. In Credentials tab, set a password for the user. The password will be "asdzxc". Switch the temporary
option to "Off" so you don't have to change the password.
- You are good to go. Authenticate Keycloak to get the JWT. We will provide the grant type, username, password, client secret, client id. Replace \<client-secret> with your client secret.
```
curl -XPOST "http://127.0.0.1:9876/realms/myrealm/protocol/openid-connect/token" -d "grant_type=password&username=zorkov&client_id=apisix&client_secret=<client-secret>&password=asdzxc"
```
- Copy the access token from the response (Yes it's long).
- Make a new request to the API with the access token. Replace \<access-token> with your access token.
```
curl http://127.0.0.1:9080/anything/test -H "Authorization: Bearer <access-token>"
```
- If you see a big JSON containing information about your request, it means that your authentication is successful and you are seeing the
response from httpbin.org website, the target of the APISIX route.

## Explanation
In Keycloak part of the application, you have a realm, an OIDC client and a user. In APISIX part, you have a route for path /anything/* and it has
an openid-connect plugin. That means in order to go to a path under /anything, like /anything/test, you have to authenticate to Keycloak first.

## Troubleshooting
### 401 Authorization Required error
Your JWT is invalid. It might be expired or you made an error while doing the copy-paste.

### Invalid client or Invalid client credentials
Your client id or client secret is wrong. The client secret in the apisix-conf.yaml file and in your authentication request must be the same.
Make sure you restarted the APISIX container after setting the client secret, so it can detect the change. Also check the client secret in Keycloak.

### Invalid user credentials
Your username or password is wrong.

### Account is not fully set up
You probably forgot to turn off the "Temporary" option while creating the user password.

## Note
Confidential client type is used for server to server communication where the client secret can be safely stored.
For client to server communication, you should use the public client type.
