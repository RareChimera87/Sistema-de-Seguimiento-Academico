package Model;

import java.util.ArrayList;
import java.util.List;

public class Estudiante extends TipoEstudiante{



    public Estudiante(String nombre, int id, int grupo, List<TipoMateria> materias, int participacion, String comentarios) {
        this.nombre = nombre;
        this.id = id;
        this.grupo = grupo;
        this.materias = materias;
        this.participacion = participacion;
        this.comentarios = comentarios;

    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public int getGrupo() {
        return grupo;
    }



    public int getParticipacion() {
        return participacion;
    }

    public String getComentaios() {
        return comentarios;
    }

    @Override
    public String toString() {
        return "\n==== Estudiante ====" +
                "\nNombre: " + nombre +
                "\nID: " + id +
                "\nGrupo: " + grupo +
                "\nMaterias: " + materias +
                "\nParticipación: " + participacion +
                "\nComentarios: " + comentarios +
                "\n===================";
    }
}
