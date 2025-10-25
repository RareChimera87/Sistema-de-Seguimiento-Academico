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
    @Override
    public String toString() {
        return "\n==== Materias ====" +
                "\nMateria: " + materia +
                "\nNotas: " + notas +
                "\nAsistencias: " + asistencia +
                "\n===================";
    }
}
