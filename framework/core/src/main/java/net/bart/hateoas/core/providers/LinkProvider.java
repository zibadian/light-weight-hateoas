package net.bart.hateoas.core.providers;

import net.bart.hateoas.core.builders.LinkBuilder;

public interface LinkProvider {

    LinkBuilder provideSelfLink();

}
