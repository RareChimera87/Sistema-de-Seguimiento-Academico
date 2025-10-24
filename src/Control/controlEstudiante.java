package Control;

import Logic.RegistroEstudiante;
import Model.Estudiante;
import java.util.ArrayList;

public class ControlEstudiante {
    private RegistroEstudiante registro;

    public ControlEstudiante() {

        this.registro = new RegistroEstudiante();
    }

    public void registrarEstudiante(String nombre, int id, ArrayList<Float> notas, int grupo, int asistencia, int participacion, String comentarios) {
        try {
            Estudiante e = new Estudiante(nombre, id, notas, grupo, asistencia, participacion, comentarios);
            registro.registrar(e);
            System.out.println("Estudiante Registrado: " + e.getNombre());

        } catch (IllegalArgumentException ex) {
            System.out.println("Erro al registrar el estudiante: " + ex.getMessage());
        }
    }

    public void getEstudiantes(){
        System.out.println(registro.getEstudiantes());
    }

}