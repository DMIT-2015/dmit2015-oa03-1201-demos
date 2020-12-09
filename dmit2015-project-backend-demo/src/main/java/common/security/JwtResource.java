package common.security;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Optional;

/**
 *
 * Microprofile JWT Authentication Steps
 * Step 1: Generate the public/private key pair for signing and verification.
 * 			Use the openssl command to generate the private key file named private.pem
 openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048
 cp private.pem src/main/resources/

 * Export the public key that will be included in the deployment
 openssl rsa -in private.pem -pubout -out src/main/resources/META-INF/public.pem

 * Step 2: Add a utility (TokenUtil.java) to generate JWT tokens.
 *
 * Step 3: Activate Microprofile JWT by adding the @LoginConfig annotation as follows:
 @ApplicationPath("/webapi")
 @LoginConfig(authMethod="MP-JWT", realmName="MP JWT Realm")
 @DeclareRoles({"ROLE1","ROLE2","ROLE3"})
 public class App extends Application { }

  * Step 4: Add/update the Microprofile Config properties file 'microprofile-config.properties' file that is located in the
  * 'src/main/resources/META-INF' folder with the following content
 mp.jwt.verify.publickey.location=META-INF/public.pem
 mp.jwt.verify.issuer=quickstart-jwt-issuer

curl -i -X POST http://localhost:8080/dmit2015-project-backend-start/webapi/jwt/formLogin/ \
	-d 'j_username=user2015&j_password=Password2015' \
	-H 'Content-Type:application/x-www-form-urlencoded'

 curl -i http://localhost:8080/dmit2015-project-backend-start/webapi/jwt/jsonLogin/ \
	-d '{"username":"user2015","password":"Password2015"}' \
	-H 'Content-Type:application/json'

 curl -i http://localhost:8080/dmit2015-project-backend-start/webapi/jwt/jsonLogin/ \
	-d '{"username":"admin2015","password":"Password2015"}' \
	-H 'Content-Type:application/json'

 curl -i http://localhost:8080/dmit2015-project-backend-start/webapi/jwt/jsonLogin/ \
 -d '{"username":"useradmin2015","password":"Password2015"}' \
 -H 'Content-Type:application/json'

  *
 * 

 *
 */

@RequestScoped
@Path("jwt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JwtResource {

	@Inject
	private CallerUserRepository callerUserRepository;

	@Inject
	private Pbkdf2PasswordHash passwordHash;

	@Inject
	private JsonWebToken token;		// CDI managed bean must be @RequestScoped to inject JWT token

	@Path("formLogin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@POST
	public Response formLogin(
		@FormParam("j_username") String username,
		@FormParam("j_password") String password) {
		JsonObject credential = Json.createObjectBuilder()
			.add("username", username)
			.add("password", password)
			.build();
		return jsonLogin(credential);
	}

	@POST
	@Path("jsonLogin")
	public Response jsonLogin(JsonObject credential) {
		String username = credential.getString("username");
		String password = credential.getString("password");

		Optional<CallerUser> optionalCallerUser = callerUserRepository.findById(username);
		if (optionalCallerUser.isPresent()) {
			CallerUser existingCallerUser = optionalCallerUser.get();
			if (passwordHash.verify(password.toCharArray(), existingCallerUser.getPassword())) {
				String[] groups = existingCallerUser.getGroups().toArray(String[]::new);
				try {
					String token = TokenUtil.generateJWT(username, groups);
					return Response.ok(token).build();
				} catch (Exception e) {
					e.printStackTrace();
					return Response.serverError().build();
				}
			}
		}

		String message = "Incorrect username and/or password.";
		return Response.status(Status.BAD_REQUEST).entity(message).build();
	}

}