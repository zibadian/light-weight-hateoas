package net.bart.examples.hateoas.rs;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.annotations.Hateoas;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;
import java.net.URL;

@Path("/testclass")
public class BootController {

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public HateoasContext test(@Hateoas HateoasContext context) throws MalformedURLException {
        final BootResponse content = new BootResponse("Hello world");
        final Author test = new Author("Test");
        content.getAuthors().add(test);
        test.addSelfLink();
        return context
                .content(content)
                .addLink("search", "http://www.google.com")
                .addLink("report", new URL("http://www.politie.nl"))
                .addLink("test2", context.resource(BootController.class).test2(context));
    }

    @GET
    @Path("test2")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public BootResponse test2(@Hateoas HateoasContext context) {
        return new BootResponse("Hello world");
    }

}