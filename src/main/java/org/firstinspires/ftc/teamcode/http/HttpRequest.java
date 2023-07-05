package org.firstinspires.ftc.teamcode.http;

import java.net.URI;
import java.util.Map;

public class HttpRequest {

    private String method;
    private String version;
    private URI uri;
    private Map<String, String> attributes;

    protected HttpRequest(String method, String version, URI uri, Map<String, String> attributes) {
        this.method = method;
        this.version = version;
        this.uri = uri;
        this.attributes = attributes;
    }

    public String getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public URI getURI() {
        return uri;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
