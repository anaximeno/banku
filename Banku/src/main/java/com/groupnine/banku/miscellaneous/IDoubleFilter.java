package com.groupnine.banku.miscellaneous;

public interface IDoubleFilter<T, U, V> {
    boolean passesFilter(T input, U entry1, V entry2);
}