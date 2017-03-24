package net.bart.hateoas.core;

import net.bart.hateoas.core.builders.LinkBuilder;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import net.bart.hateoas.core.providers.HateoasProviderHelperTest;
import net.bart.hateoas.core.providers.LinkProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HateoasContextTest {

    @InjectMocks
    private HateoasContext fixture;

    @Mock
    private LinkProvider linkProviderMock;

    @BeforeClass
    public static void setupClass() {
        new HateoasProviderHelper(HateoasLinksTest.TestLinkBuilder.class);
    }

    @Test
    public void set_content_self_without_adding_self() throws Exception {
        final Field selfField = fixture.getClass().getDeclaredField("self");
        selfField.setAccessible(true);

        fixture.content("test");

        assertEquals("test", fixture.getContent());
        assertEquals(linkProviderMock, selfField.get(fixture));
        verify(linkProviderMock, never()).provideSelfLink();
        assertNull(fixture.getLinks().getLinks());
    }

    @Test
    public void add_self() throws Exception {
        final Field selfField = fixture.getClass().getDeclaredField("self");
        selfField.setAccessible(true);
        when(linkProviderMock.provideSelfLink()).thenReturn(new LinkBuilder()
                .addPart(new UrlPathPart("test")));

        fixture.addSelfLink();

        assertEquals(linkProviderMock, selfField.get(fixture));
        verify(linkProviderMock).provideSelfLink();
        assertNotNull(fixture.getLinks().getLinks());
        assertEquals(1, fixture.getLinks().getLinks().size());
    }

    public static class TestResourceClass {

        public HateoasContext test() {
            return null;
        }
    }

}