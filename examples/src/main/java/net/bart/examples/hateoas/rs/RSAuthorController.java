package net.bart.examples.hateoas.rs;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.annotations.Hateoas;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/authors")
public class RSAuthorController {

    public static final List<RSAuthor> authors = Arrays.asList(new RSAuthor(1, "John Smith"));

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response list(@Hateoas HateoasContext context) {
        return Response.ok(context.addSelfLinks().content(authors)).build();
    }

    @GET
    @Path("/author/{id}/?test={extra}")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response author(@PathParam("id") int id, @Hateoas HateoasContext context, @QueryParam("extra") @DefaultValue("default") String extra) {
        return Response.ok("").build();
    }

}
