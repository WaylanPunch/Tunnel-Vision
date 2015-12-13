package com.way.tunnelvision.adapter.section;

/**
 * Created by pc on 2015/12/13.
 * An interface for objects that belong to a named category.
 */
public interface Categorizable {
    /**
     * Gets the name of the category that this item belongs to.
     *
     * @return the name <code>String</code>
     */
    String getCategory();
}
