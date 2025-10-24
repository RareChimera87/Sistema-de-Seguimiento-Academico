package Logic;

import java.util.ArrayList;
import java.util.List;

import Model.Estudiante;


public class RegistroEstudiante {
    private ArrayList<Estudiante> alumnos;

    public void RegistroEstudiante() {
        this.alumnos = new ArrayList<>();

    }

    public void registrar(Estudiante est){
        alumnos.add(est);
    }


    public List<Estudiante> getEstudiantes() {
        return alumnos;
    }

}
