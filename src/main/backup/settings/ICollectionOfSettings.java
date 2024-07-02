package com.nickcoblentz.montoya.settings;

import java.util.Collection;

public interface ICollectionOfSettings<T> extends ISetting {
    void addItem(T item);
    void clear();
    void removeItem(T item);
    boolean isMultiSelect();
    Collection<T> getValue();
    Collection<T> getOptions();
}
