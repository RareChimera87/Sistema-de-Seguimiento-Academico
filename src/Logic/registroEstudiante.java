package src.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.Model.estudiante


public class RegistroEstudiante {
    private ArrayList<List> Alumnos;

    public void RegistroEstudiante() {
        this.Alumnos = new ArrayList<>();

    }

    public void registrar(estudiante est){
        Alumnos.add(est)
    }
    public List<Alumnos> getEstudiantes() {
        return Alumnos
    }

}
