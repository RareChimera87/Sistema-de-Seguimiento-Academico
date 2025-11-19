package Logic;


import Model.TipoMateria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistroMateria {
    private List<TipoMateria> materias;

    public RegistroMateria() {
        this.materias = new ArrayList<>();

    }

    public void registrar(TipoMateria mate){
        materias.add(mate);
    }

    public void vaciar(){
        materias.clear();
    }


    public List<TipoMateria> getMaterias() {
        List<TipoMateria> ma = new ArrayList<>(this.materias);
        vaciar();
        return ma;
    }
}
