package Control;

import java.util.List;
import java.util.ArrayList;
import Logic.RegistroEstudiante;
import Logic.RegistroMateria;
import Model.Estudiante;
import Logic.GenerateJSON;
import Model.TipoAsistencias;
import Model.TipoMateria;

public class ControlEstudiantes {
    private final RegistroEstudiante registro;
    private final RegistroMateria materi;
    private String fileRoute = "data/estudiantes.json";

    public ControlEstudiantes() {

        this.registro = new RegistroEstudiante();
        this.materi = new RegistroMateria();
    }

    public void registrarEstudiante(String nombre, int id, List<TipoMateria> materias, int grupo, int participacion, String comentarios) {
        try {
            Estudiante e = new Estudiante(nombre, id, grupo, materias, participacion, comentarios);
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

    public void registrarMateria(String nombre, ArrayList<Double> notas, ArrayList<TipoAsistencias> asistencias){
        try {
            TipoMateria m = TipoMateria.crearMateria(nombre, notas, asistencias);
            materi.registrar(m);
            System.out.println("Materia Registrada: " + m.materia);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error al registrar la materia: " + ex.getMessage());
        }
    }

    public List<TipoMateria> getMaterias(){
        return materi.getMaterias();
    }


    public void elimnarEstudiantes(){
        registro.getEstudiantes().clear();
    }

    public void generateJSONFile(){
        String es = GenerateJSON.generateJSON(registro.getEstudiantes());
        GenerateJSON.generateFile(fileRoute, es);
    }
}
