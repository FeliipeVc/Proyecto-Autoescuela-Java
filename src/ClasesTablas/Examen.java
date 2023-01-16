package ClasesTablas;

import java.time.LocalDate;

public class Examen {

    private int idExamen;
    private LocalDate fecha;
    private boolean aprobado;

    public Examen(int idExamen, LocalDate fecha, boolean aprobado) {
        this.idExamen = idExamen;
        this.fecha = fecha;
        this.aprobado = aprobado;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    @Override
    public String toString() {
        return "Identificador del examen: " + idExamen
                + "\nFecha de realización: " + fecha
                + "\n¿Aprobado?" + aprobado;
    }

}
