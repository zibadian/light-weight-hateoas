package net.bart.hateoas.core.builders.urls;

public class FragmentPart extends AbstractUrlPart {

    public FragmentPart(final String fragment) {
        super(fragment);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getHref() {
        return '#' + super.getHref();
    }
}
