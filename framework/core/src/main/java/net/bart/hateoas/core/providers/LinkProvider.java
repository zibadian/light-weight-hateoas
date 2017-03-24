package net.bart.hateoas.core.providers;

import net.bart.hateoas.core.builders.LinkBuilder;
import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;

public interface LinkProvider {

    LinkBuilder provideSelfLink();

}
