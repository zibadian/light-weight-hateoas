package net.bart.examples.hateoas.spring;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.annotations.Hateoas;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/testclass")
public class SpringController {

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public HateoasContext test(@Hateoas HateoasContext context) throws MalformedURLException {
        SpringResponse content = new SpringResponse("Hello world");
        return context
                .content(content)
                .addLink("search", "http://www.google.com")
                .addLink("report", new URL("http://www.politie.nl"))
                .addLink("test2", context.resource(SpringController.class).test2(context, "Thundersub"));
    }

    @GetMapping(value = "test2/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public SpringResponse test2(@Hateoas HateoasContext context, @PathVariable("name") String name) {
        return new SpringResponse("Hello " + name);
    }

}
