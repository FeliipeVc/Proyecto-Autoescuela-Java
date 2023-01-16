package ClasesTablas;

import ClasesTablas.Direccion;

import java.time.LocalDate;
import java.util.Vector;


public class Alumno extends Persona {

    Vector<Examen> examenes;

    public Alumno(String dni, String nombre, String apellidos, LocalDate fechanacimiento, String telefono, String correo, LocalDate fechaAlta, ClasesTablas.Direccion direccion, Vector<ClasesTablas.Examen> examenes) {
        super(dni, nombre, apellidos, fechanacimiento, telefono, correo, fechaAlta, direccion);
        this.examenes = examenes;
    }

    public boolean realizarPractico() {
        for (int i = 0; i < examenes.size(); i++) {
            if (examenes.get(i) instanceof Teorico) {
                if (examenes.get(i).isAprobado() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean practicoRealizado() {
        for (int i = 0; i < examenes.size(); i++) {
            if (examenes.get(i) instanceof Practico) {
                if (examenes.get(i).isAprobado() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public Vector<Examen> getExamenes() {
        return examenes;
    }

    @Override
    public Direccion getDireccion() {
        return super.getDireccion(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCorreo() {
        return super.getCorreo(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTelefono() {
        return super.getTelefono(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LocalDate getFechanacimiento() {
        return super.getFechanacimiento(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getApellidos() {
        return super.getApellidos(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        return super.getNombre(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDni() {
        return super.getDni(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "[ALUMNO]+-+-+-+-+-+-+-+-+-+-+-+\n"
                + super.toString()
                + "\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";
    }
}
