package net.bart.examples.hateoas.spring;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import net.bart.examples.hateoas.rs.Author;
import net.bart.examples.hateoas.rs.BootResponse;
import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.annotations.Hateoas;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
//@RequestMapping("/testclass")
public class SpringController {

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public HateoasContext test(@Hateoas HateoasContext context) throws MalformedURLException {
        BootResponse content = new BootResponse("Hello world");
        final Author test = new Author("Test");
        content.getAuthors().add(test);
        test.addSelfLink();
        return context
                .content(content)
                .addLink("search", "http://www.google.com")
                .addLink("report", new URL("http://www.politie.nl"))
                .addLink("test2", context.resource(SpringController.class).test2(context));
    }

    @GetMapping(value = "test2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public BootResponse test2(@Hateoas HateoasContext context) {
        return new BootResponse("Hello world");
    }

}
