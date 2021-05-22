package Battleship;

/**
 * An enum that represents all the valid coordinates input and it's representation in the board (2D array of the game)
 */
enum Coordinates {
    A1("00"), A2("01"), A3("02"), A4("03"), A5("04"), A6("05"), A7("06"), A8("07"), A9("08"), A10("09"),
    B1("10"), B2("11"), B3("12"), B4("13"), B5("14"), B6("15"), B7("16"), B8("17"), B9("18"), B10("19"),
    C1("20"), C2("21"), C3("22"), C4("23"), C5("24"), C6("25"), C7("26"), C8("27"), C9("28"), C10("29"),
    D1("30"),D2("31"), D3("32"), D4("33"), D5("34"), D6("35"), D7("36"), D8("37"), D9("38"), D10("39"),
    E1("40"), E2("41"), E3("42"), E4("43"), E5("44"), E6("45"), E7("46"), E8("47"), E9("48"), E10("49"),
    F1("50"), F2("51"), F3("52"), F4("53"), F5("54"), F6("55"), F7("56"), F8("57"), F9("58"), F10("59"),
    G1("60"), G2("61"), G3("62"), G4("63"), G5("64"), G6("65"), G7("66"), G8("67"), G9("68"), G10("69"),
    H1("70"), H2("71"), H3("72"), H4("73"), H5("74"), H6("75"), H7("76"), H8("77"), H9("78"), H10("79"),
    I1("80"), I2("81"), I3("82"), I4("83"), I5("84"), I6("85"), I7("86"), I8("87"), I9("88"), I10("89"),
    J1("90"), J2("91"), J3("92"), J4("93"), J5("94"), J6("95"), J7("96"), J8("97"), J9("98"), J10("99");

    private final String coordinates;

    Coordinates(String coordinates) {
        this.coordinates = coordinates;
    }
    
    static String getCoordinates(String val) {
        String result;
        try {
            final var coordinates = Coordinates.valueOf(val);
            result = coordinates.coordinates;
        } catch (IllegalArgumentException | NullPointerException  e) {
            result = null;
        }
        return result;
    }

}
