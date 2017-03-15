package net.bart.hateoas.core.builders.urls;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HostPartTest {

    private HostPart fixture;

    @Before
    public void setup() {
        fixture = new HostPart("localhost");
    }

    @Test
    public void only_host() {
        assertEquals("http://localhost", fixture.getHref());
    }

    @Test
    public void host_and_port() {
        fixture.setPort(8080);
        assertEquals("http://localhost:8080", fixture.getHref());
    }

    @Test
    public void host_and_port_different_scheme() {
        fixture.setPort(8080).setScheme("https");
        assertEquals("https://localhost:8080", fixture.getHref());
    }

    @Test
    public void host_and_authorization_info() {
        fixture.setAuthenticationInfo("test:pw");
        assertEquals("http://test:pw@localhost", fixture.getHref());
    }

    @Test
    public void host_and_authorization_info_2() {
        fixture.setAuthenticationInfo("test", "pw");
        assertEquals("http://test:pw@localhost", fixture.getHref());
    }

    @Test
    public void empty_host() {
        fixture = new HostPart(null);
        assertEquals("", fixture.getHref());
    }

    @Test(expected = IllegalArgumentException.class)
    public void port_too_low() {
        fixture = new HostPart("localhost").setPort(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void port_too_high() {
        fixture = new HostPart("localhost").setPort(4567894);
    }

    @Test
    public void null_port() {
        fixture = new HostPart("localhost").setPort(null);

        assertNull(fixture.getPort());
    }

}