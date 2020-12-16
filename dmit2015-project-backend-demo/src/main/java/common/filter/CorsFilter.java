package common.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Cross Origin Resource Sharing (CORS)
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
 *
 * To inform the browser which origins are allowed and what request headers the client can pass to the server.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        headers.add("Access-Control-Allow-Headers","Origin, Content-Type, Accept, Authorization");
        headers.add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD");

    }
}

 