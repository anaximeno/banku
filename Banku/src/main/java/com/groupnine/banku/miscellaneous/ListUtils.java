package com.groupnine.banku.miscellaneous;

import java.util.List;

public class ListUtils {
    public static<T extends Number> double meanOf(final List<T> list)
    /* Retorna a média de uma lista de números. */
    {
        double mean = 0d;

        for (T num : list) {
            mean += num.doubleValue() / list.size();
        }

        return mean;
    }

    public static<T extends Number> T maxOf(final List<T> list)
    /* Retorna o máximo de uma lista de números. O tamanho a lista tem que
     * ser maior que zero.
     * */
    {
        assert list.size() > 0; // The size of the list must be > 0
        T max = list.get(0);
        T num;

        for (int i = 1 ; i < list.size() ; i++) {
            num = list.get(i);
            if (num.doubleValue() > max.doubleValue()) {
                max = num;
            }
        }

        return max;
    }

    public static<T extends Number> T minOf(final List<T> list)
    /* Retorna o mínimo de uma lista de números. O tamanho a lista tem que
    * ser maior que zero.
    * */
    {
        assert list.size() > 0; // The size of the list must be > 0
        T min = list.get(0);
        T num;

        for (int i = 1 ; i < list.size() ; i++) {
            num = list.get(i);
            if (num.doubleValue() < min.doubleValue()) {
                min = num;
            }
        }

        return min;
    }
}
