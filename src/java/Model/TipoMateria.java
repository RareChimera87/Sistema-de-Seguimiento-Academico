package Model;

import java.util.ArrayList;

public class TipoMateria{
    public String materia;
    public ArrayList<Double> notas;
    public ArrayList<TipoAsistencias> asistencia;


    public static TipoMateria crearMateria(String materia, ArrayList<Double> notas, ArrayList<TipoAsistencias> asistencia) {
        TipoMateria nuevaMateria = new TipoMateria();
        nuevaMateria.materia = materia;
        nuevaMateria.notas = notas;
        nuevaMateria.asistencia = asistencia;
        return nuevaMateria;
    }

    public String getMateria() {
        return materia;
    }


    public ArrayList<Double> getNotas() {
        return notas;
    }

    public ArrayList<TipoAsistencias> getAsistencia() {
        return asistencia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }


    public void setNotas(ArrayList<Double> notas) {
        this.notas = notas;
    }

    public void setAsistencia(ArrayList<TipoAsistencias> asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public String toString() {
        return "\n==== Materias ====" +
                "\nMateria: " + materia +
                "\nNotas: " + notas +
                "\nAsistencias: " + asistencia +
                "\n===================";
    }
}
