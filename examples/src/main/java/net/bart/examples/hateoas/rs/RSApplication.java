package net.bart.examples.hateoas.rs;

import net.bart.examples.hateoas.rs.visitors.RSAuthorVisitor;
import net.bart.hateoas.core.builders.HateoasVisitorManager;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.rs.configuration.RSHateoasConfiguration;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class RSApplication {

    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9999).build();
        ResourceConfig config = new ResourceConfig()
                .register(new RSHateoasConfiguration().setBaseUri(new UrlPathPart(baseUri.toString())))
                .packages(RSResponse.class.getPackage().getName());
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

        server.start();
        HateoasVisitorManager.getInstance().register(RSAuthor.class, RSAuthorVisitor.class);
        System.in.read();
        server.shutdownNow();
    }

}

