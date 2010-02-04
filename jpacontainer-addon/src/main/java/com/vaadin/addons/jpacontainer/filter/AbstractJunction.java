/*
 * JPAContainer
 * Copyright (C) 2009 Oy IT Mill Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.vaadin.addons.jpacontainer.filter;

import com.vaadin.addons.jpacontainer.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for {@link Junction}-implementations.
 *
 * @author Petter Holmström (IT Mill)
 * @since 1.0
 */
public abstract class AbstractJunction implements Junction {

    private List<Filter> filters = new ArrayList<Filter>();

    protected AbstractJunction(Filter... filters) {
        assert filters != null : "filters must not be null";

        for (Filter f : filters) {
            this.filters.add(f);
        }
    }

    protected AbstractJunction(List<Filter> filters) {
        assert filters != null : "filters must not be null";
        // We do not copy the filters instance directly, as we have
        // to make sure the internal list instance is writable, etc.
        for (Filter f : filters) {
            this.filters.add(f);
        }
    }

    @Override
    public Junction add(Filter filter) {
        assert filter != null : "filter must not be null";
        filters.add(filter);
        return this;
    }

    @Override
    public Junction remove(Filter filter) {
        assert filter != null : "filter must not be null";
        filters.remove(filter);
        return this;
    }

    @Override
    public List<Filter> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object obj) {
        return obj.getClass() == getClass() && ((AbstractJunction) obj).filters.equals(filters);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 7 * filters.hashCode();
    }
}