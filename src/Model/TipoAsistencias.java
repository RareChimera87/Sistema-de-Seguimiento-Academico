package Model;

import java.util.Date;

public class TipoAsistencias {
    boolean asistencia;
    Date fecha;
    public static TipoAsistencias registrarAsistencia(boolean asistencia, Date fecha) {
        TipoAsistencias nuevaAsistencia = new TipoAsistencias();
        nuevaAsistencia.asistencia = asistencia;
        nuevaAsistencia.fecha = fecha;
        return nuevaAsistencia;
    }
}
