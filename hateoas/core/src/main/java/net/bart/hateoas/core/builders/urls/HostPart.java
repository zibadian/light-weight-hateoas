package net.bart.hateoas.core.builders.urls;

import net.bart.hateoas.core.builders.UrlPart;

public final class HostPart implements UrlPart {

    private String scheme;
    private String authenticationInfo;
    private final String host;
    private Integer port;

    public HostPart(final String host) {
        this.host = host == null ? "" : host;
        scheme = "http";
        authenticationInfo = "";
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public HostPart setPort(final Integer port) {
        if (port != null && (port < 1 || port > 65535)) {
            throw new IllegalArgumentException("Illegal port number");
        }
        this.port = port;
        return this;
    }

    public HostPart setScheme(final String scheme) {
        this.scheme = scheme == null ? "http" : scheme;
        return this;
    }

    public String getAuthenticationInfo() {
        return authenticationInfo;
    }

    public HostPart setAuthenticationInfo(final String authenticationInfo) {
        this.authenticationInfo = authenticationInfo == null ? "" : authenticationInfo;
        return this;
    }

    public HostPart setAuthenticationInfo(final String username, final String password) {
        this.authenticationInfo = username + ':' + password;
        return this;
    }

    @Override
    public String getHref() {
        if (host.equals("")) {
            return "";
        }
        final StringBuilder result = new StringBuilder()
                .append(scheme)
                .append("://");
        if (!authenticationInfo.isEmpty()) {
            result.append(authenticationInfo).append('@');
        }
        result.append(host);
        if (port != null) {
            result.append(':').append(port);
        }
        return result.toString();
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
