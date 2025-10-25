package Model;

import java.util.ArrayList;
import Model.TipoEstudiante;

public class Estudiante extends TipoEstudiante{


    public float genPromedio(ArrayList<Float> notas) {
        float suma = 0f;
        int cantidad = 0;
        for (int i = 0; i < notas.size(); i++) {
            suma = suma + notas.get(i);
            cantidad += 1;
        }
        float prom = (float) (suma / cantidad);
        return prom;
    }

    public Estudiante(String nombre, int id, ArrayList<Float> notas, int grupo, int asistencia, int participacion,
                      String comentarios) {
        this.nombre = nombre;
        this.id = id;
        this.notas = notas;
        this.promedio = genPromedio(notas);
        this.grupo = grupo;
        this.asistencia = asistencia;
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

    public ArrayList<Float> getNotas() {
        return notas;
    }

    public float getPromedio() {
        return promedio;
    }

    public int getAsistencia() {
        return asistencia;
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
                "\nNotas: " + notas +
                "\nPromedio: " + promedio +
                "\nAsistencia: " + asistencia +
                "\nParticipación: " + participacion +
                "\nComentarios: " + comentarios +
                "\n===================";
    }
}
