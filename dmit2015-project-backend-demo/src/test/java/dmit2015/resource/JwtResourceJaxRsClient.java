package dmit2015.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.Optional;

public class JwtResourceJaxRsClient {

    static final String BASE_URI_JWT = "http://localhost:8080/dmit2015-project-backend-start/webapi/jwt";
    Client jaxRsClient = ClientBuilder.newClient();

    public Optional<String> login(String username, String password) {
        Optional<String> optionalToken = Optional.empty();
        WebTarget jwtResource = jaxRsClient
                .target(BASE_URI_JWT)
                .path("formLogin");
        Form loginForm = new Form();
        loginForm.param("j_username", username);
        loginForm.param("j_password", password);
        Entity<Form> formEntity = Entity.form(loginForm);
        Response jwtResponse = jwtResource
                .request()
                .post(formEntity);
        if (jwtResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            String token = jwtResponse.readEntity(String.class);
            optionalToken = Optional.of(token);
        }
        return optionalToken;
    }
}
