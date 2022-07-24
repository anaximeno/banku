package com.groupnine.banku.businesslogic;

public abstract class SequentiableFactory {
    private int counter = 0;
    public static int ID_PLACES = 13;

    private void increaseCounter()
    /* Aumenta o valor do counter. */
    {
        counter += 1;
    }

    public static String encodeNumber(int number, int places)
    /* Retorna um valor normalizado com zeros dependendo do places.
     * */
    {
        String num = String.valueOf(number);
        double decimalPlaces = Math.pow(10d, places - 1);

        for (int x = number ; x < decimalPlaces ; decimalPlaces /= 10) {
            num = "0".concat(num);
        }

        return num;
    }

    protected String getEncodedSequentialId()
    /* Retorna um valor normalizado para ser usado como um
     * id sequencial.
     * */
    {
        increaseCounter();
        return encodeNumber(counter, ID_PLACES);
    }
}
