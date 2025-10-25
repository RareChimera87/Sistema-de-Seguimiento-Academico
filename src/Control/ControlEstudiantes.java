package Control;

import java.util.List;
import java.util.ArrayList;
import Logic.RegistroEstudiante;
import Model.Estudiante;
import Logic.GenerateJSON;

public class ControlEstudiantes {
    private RegistroEstudiante registro;
    private String fileRoute = "data/estudiantes.json";

    public ControlEstudiantes() {

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
        List<Estudiante> estudiantes = registro.getEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            System.out.println("=== Lista de Estudiantes ===");
            for (Estudiante e : estudiantes) {
                System.out.println(e); // Esto llamará al toString()
            }
        }
    }


    public void elimnarEstudiantes(){
        registro.getEstudiantes().clear();
    }

    public void generateJSONFile(){
        String es = GenerateJSON.generateJSON(registro.getEstudiantes());
        GenerateJSON.generateFile(fileRoute, es);
    }
}
