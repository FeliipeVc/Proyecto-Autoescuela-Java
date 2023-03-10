package ClasesTablas;

public class Direccion {

    private String calle;
    private int numero;
    private int plantra;
    private char letra;
    private int codigopostal;

    public Direccion(String calle, int numero, int plantra, char letra, int codigopostal) {
        this.calle = calle;
        this.numero = numero;
        this.plantra = plantra;
        this.letra = letra;
        this.codigopostal = codigopostal;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public int getPlantra() {
        return plantra;
    }

    public char getLetra() {
        return letra;
    }

    public int getCodigopostal() {
        return codigopostal;
    }

    @Override
    public String toString() {
        return "Calle: " + calle
                + "\nNúmero: " + numero
                + "\nPiso: " + plantra
                + "\nLetra: " + letra
                + "\nCódigo postal: " + codigopostal;
    }

}
