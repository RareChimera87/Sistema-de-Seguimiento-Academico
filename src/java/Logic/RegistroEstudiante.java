package Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import Model.Estudiante;


public class RegistroEstudiante {
    private ArrayList<Estudiante> alumnos;

    public RegistroEstudiante() {
        this.alumnos = new ArrayList<>();

    }

    public void registrar(Estudiante est){
        alumnos.add(est);
    }

    public void vaciar(){
        alumnos.clear();
    }


    public List<Estudiante> getEstudiantes() {
        List<Estudiante> alum = new ArrayList<>(this.alumnos);
        vaciar();
        return alum;
    }
}
