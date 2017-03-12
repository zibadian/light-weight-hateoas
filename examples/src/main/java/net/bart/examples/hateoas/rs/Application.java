package net.bart.examples.hateoas.rs;

import net.bart.hateoas.rs.configuration.RSHateoasConfiguration;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Application {

    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9999).build();
        ResourceConfig config = new ResourceConfig()
            .register(new RSHateoasConfiguration().setBaseUri(baseUri))
            .packages(BootResponse.class.getPackage().getName());
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

        server.start();
        System.in.read();
        server.shutdownNow();
    }

}

