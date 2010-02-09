/*
 * JPAContainer
 * Copyright (C) 2010 Oy IT Mill Ltd
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
package com.vaadin.addons.jpacontainer;

import com.vaadin.data.Buffered;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

/**
 * Interface defining the Items that are contained in a {@link EntityContainer}. Each
 * item is always backed by an underlying Entity instance that normally is a POJO.
 * <p>
 * By default, whenever a Property of the item is changed, the corresponding property
 * of the Entity is updated accordingly. However, it is also possible to buffer
 * all the changes by setting {@link #setWriteThrough(boolean)} to false. In this mode,
 * the underlying Entity will remain untouched until {@link #commit() } is called.
 * Please note, that this has nothing to do with the buffering of the {@link EntityContainer}.
 *
 * @author Petter Holmström (IT Mill)
 * @since 1.0
 */
public interface EntityItem<T> extends Item, Buffered,
        Property.ValueChangeNotifier {

    /**
     * Gets the item ID of the item. This ID can be used to uniquely identify
     * the item inside the container. It may not necessarily be the same as
     * the entity ID.
     * <p>
     * If the item ID is null, the entity item was created by a container,
     * but not yet added to it.
     * @see EntityContainer#createEntityItem(java.lang.Object)
     *
     * @return the item ID or null if the item is not yet inside a container.
     */
    public Object getItemId();

    /**
     * Gets the underlying entity instance that contains the actual data
     * being accessed by this item.
     *
     * @return the entity (never null).
     */
    public T getEntity();

    /**
     * Checks if the underlying entity ({@link #getEntity() }) is persistent (i.e.
     * fetched from a persistence storage) or transient (created and buffered by
     * the container). This method always returns false if {@link #getItemId() } is null,
     * even if the underlying entity actually is persistent.
     *
     * @return true if the underlying entity is persistent, false if it is transient.
     */
    public boolean isPersistent();

    /**
     * Checks whether the underlying entity ({@link #getEntity() }) has been modified
     * after it was fetched from the entity provider. When the changes have been
     * persisted, this flag will be reset.
     * <p>
     * Note, that this is not the same as the {@link #isModified() } flag. It is possible
     * for an entity item to be dirty and not modified and vice versa. For example,
     * if the item has changes that have not yet been committed, the item will be
     * modified but not dirty, as the underlying entity object has not yet been altered.
     * After the item is committed, the item will be dirty but not modified.
     * <p>
     * If the item is not persistent, this method always returns false.
     *
     * @return true if the underlying entity has been modified, false if not.
     */
    public boolean isDirty();

    /**
     * Checks whether this item has been marked for deletion. This method can only
     * return true if {@link #isPersistent() } is true and the container is running
     * in buffered mode.
     *
     * @return true if the item has been deleted, false otherwise.
     */
    public boolean isDeleted();

    /**
     * Gets the container that contains this item. If {@link #getItemId() }
     * is null, the container created the item but does not yet contain it.
     * 
     * @return the container (never null).
     */
    public EntityContainer<T> getContainer();

    /**
     * Registers a new value change listener for all the properties of this item.
     *
     * @param listener the new listener to be registered.
     */
    @Override
    public void addListener(Property.ValueChangeListener listener);

    /**
     * Removes a previously registered value change listener.
     * The listener will be unregistered from all the properties of this item.
     *
     * @param listener listener to be removed.
     */
    @Override
    public void removeListener(Property.ValueChangeListener listener);
}
