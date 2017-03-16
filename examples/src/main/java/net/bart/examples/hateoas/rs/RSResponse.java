package net.bart.examples.hateoas.rs;

import java.util.ArrayList;
import java.util.List;

public class RSResponse {

    private final String text;
    private final List<RSAuthor> authors = new ArrayList<>();

    public RSResponse(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<RSAuthor> getAuthors() {
        return authors;
    }

}
