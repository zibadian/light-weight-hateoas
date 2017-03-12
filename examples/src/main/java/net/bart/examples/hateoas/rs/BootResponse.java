package net.bart.examples.hateoas.rs;

import java.util.ArrayList;
import java.util.List;

public class BootResponse {

    private final String text;
    private final List<Author> authors = new ArrayList<>();

    public BootResponse(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
