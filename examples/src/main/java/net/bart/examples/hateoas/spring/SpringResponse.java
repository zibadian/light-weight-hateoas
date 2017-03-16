package net.bart.examples.hateoas.spring;

import java.util.ArrayList;
import java.util.List;

public class SpringResponse {

    private final String text;
    private final List<SpringAuthor> authors = new ArrayList<>();

    public SpringResponse(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<SpringAuthor> getAuthors() {
        return authors;
    }

}
