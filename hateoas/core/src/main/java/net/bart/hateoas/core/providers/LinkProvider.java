package net.bart.hateoas.core.providers;

import net.bart.hateoas.core.builders.AbstractLinkBuilder;
import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;

public interface LinkProvider {

    AbstractLinkBuilder provideSelfLink();

    AbstractResourceLinkBuilder provideResourceLink();

}
