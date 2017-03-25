package net.bart.hateoas.core.configuration;

import net.bart.hateoas.core.builders.AbstractResourceLinkProvider;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HateoasConfigurationTest {

    private HateoasConfiguration fixture;

    @Before
    public void setup() {
        fixture = new HateoasConfiguration() {
            @Override
            protected Class<? extends AbstractResourceLinkProvider> getResourceLinkBuilderClass() {
                return null;
            }
        };
    }

    @Test
    public void set_base_uri() {
        final UrlPart expected = new UrlPathPart("test");

        fixture.setBaseUri(expected);

        assertEquals(expected, HateoasProviderHelper.getLinkProperty(HateoasProviderHelper.BASE_URI_KEY));
    }

}