package com.groupnine.banku.miscellaneous;


public interface IBasicFilter<T, U> {
    boolean passesFilter(T input, U entry);
}