package dmit2015.resource;

import dmit2015.service.MailBean;

import java.net.URI;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * RESTful Web services for sending mail.
 *
 * 	URI					Http Method		Request Body									Description
 * 	----------------	-----------		--------------------------------				------------------------------------------
 *	/mail/				POST			{"mailToAddresses":"username@nait.ca",			Send a email to the recipient with the subject and text
 *										"mailSubject":"DMIT2015 JavaMail Test",
 *										"mailText";"Guess JavaMail is working!"}
 *
 * curl command to send mail to username@nait.ca (change username@nait.ca an actual email address) for webapp running on HTTP and @ApplicationPath("webapi") in JAXRSConfiguration.java

curl -i -X POST http://localhost:8080/webapi/mail \
	-d '{"mailToAddresses":"usename@nait.ca", "mailSubject": "DMIT2015 WildFly mail subsystem", "mailText": "This email was sent using the Wildfly mail subsystem and a statless session bean."},' \
	-H 'Content-Type:application/json'


curl -i -X POST http://localhost:8080/webapi/mail \
	 -d '{"mailToAddresses":"ian.ghettuba@gmail.com", "mailSubject": "DMIT2015 WildFly mail subsystem", "mailText": "This email was sent using the Wildfly mail subsystem and a statless session bean."},' \
 	-H 'Content-Type:application/json'

 * curl command to send mail to username@nait.ca (change username@nait.ca an actual email address) for webapp running on HTTP and @ApplicationPath("webapi") in JAXRSConfiguration.java

curl -k -i -X POST https://localhost:8443/webapi/mail \
	-d '{"mailToAddresses":"usename@nait.ca", "mailSubject": "DMIT2015 JavaMail Mail Bomb", "mailText": "Guess JavaMail is working!"},' \
	-H 'Content-Type:application/json'
 *
 *
 */
@Path("mail")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MailResource {

	@Inject
	private MailBean mailBean;

	@POST
	public Response sendMail(JsonObject mailJsonObject, @Context UriInfo uriInfo) {

		try {
			mailBean.sendMail(
				mailJsonObject.getString("mailToAddresses"),
				mailJsonObject.getString("mailSubject"),
				mailJsonObject.getString("mailText")
			);
			URI location = uriInfo.getAbsolutePathBuilder()
					.build();
			return Response
					.created(location)
					.build();
		} catch (Exception ex) {
			// Return a HTTP status of "500 Internal Server Error" containing the exception message
			return Response
					.serverError()
					.entity(ex.getMessage())
					.build();
		}
	}
}