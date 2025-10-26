package Logic;
import Model.Estudiante;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class GenerateJSON {
    public static String generateJSON(List<Estudiante> escolares){
        Gson gson = new Gson();
        return gson.toJson(escolares);
    }

    public static void generateFile(String fileRoute, String json){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileRoute, true))){
            bw.write(json);
            System.out.println("Archivo JSON generado correctamente en la ruta: " + fileRoute);
        } catch (Exception e){
            System.out.println("Error al generar el archivo JSON: " + e.getMessage());
        }
    }
}
