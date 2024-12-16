public enum Dias {
    LUNES, MARTES, MIERCOLES , SABADOD, DOMINGO;

    boolean finDeSemana(Dias dia) {
        return (dia == SABADOD || dia == DOMINGO);
    }
}
