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

    public boolean existJson(){
        return GenerateJSON.readJson(fileRoute);
    }

    public void generateJSONFile(){

        // Obtener los estudiantes registrados en esta sesión
        List<Estudiante> estudiantesNuevos = registro.getEstudiantes();

        // Guardar en el archivo (se agregará a los existentes)
        GenerateJSON.generateFile(fileRoute, estudiantesNuevos);

        // Limpiar el registro temporal después de guardar
        registro.getEstudiantes().clear();

        // Limpiar las materias temporales
        materi.getMaterias().clear();
    }
    public List<Estudiante> cargarEstudiantesDesdeJSON() {
        return GenerateJSON.leerEstudiantes(fileRoute);
    }
    public Estudiante buscarEstudiantePorId(int id) {
        List<Estudiante> estudiantes = GenerateJSON.leerEstudiantes(fileRoute);
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getId() == id) {
                return estudiante;
            }
        }
        return null;
    }

    /**
     * Actualiza un estudiante existente en el JSON
     */
    public void actualizarEstudiante(Estudiante estudianteModificado) {
        List<Estudiante> estudiantes = GenerateJSON.leerEstudiantes(fileRoute);

        // Buscar y reemplazar el estudiante
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getId() == estudianteModificado.getId()) {
                estudiantes.set(i, estudianteModificado);
                break;
            }
        }

        // Guardar la lista actualizada
        GenerateJSON.guardarListaCompleta(fileRoute, estudiantes);
    }
    public boolean eliminarEstudiante(int id) {
        List<Estudiante> estudiantes = GenerateJSON.leerEstudiantes(fileRoute);

        boolean eliminado = estudiantes.removeIf(estudiante -> estudiante.getId() == id);

        if (eliminado) {
            GenerateJSON.guardarListaCompleta(fileRoute, estudiantes);
            System.out.println("Estudiante con ID " + id + " eliminado correctamente");
        }

        return eliminado;
    }
}
