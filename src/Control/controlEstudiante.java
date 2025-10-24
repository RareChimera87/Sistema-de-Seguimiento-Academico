package src.Control;

import src.Logic.registroEstudiante;
import src.Model.estudiante;

public class controlEstudiante {
    private registroEstudiante registro;

    public controlEstudiante() {

        this.registro = new registroEstudiante();
    }

    public void registrarEstudiante(String nombre, int id, ArrayList<Float> notas, int grupo, int asistencia, int participacion, String comentarios) {
        try {
            estudiante e = new estudiante(nombre, id, notas, grupo, asistencia, participacion, comentario);
            registro.registrar(e);
            System.out.println("Estudiante Registrado: " + e.getNombre());

        } catch (IllegalArgumentException ex) {
            System.out.println("Erro al registrar el estudiante: " + ex.getMessage());
        }
    }

    public void getEstudiantes(){
        System.out.println(registro.getEstudiantes())
    }

}