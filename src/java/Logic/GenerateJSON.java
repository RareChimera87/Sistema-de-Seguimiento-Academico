package Logic;
import Model.Estudiante;
import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateJSON {

    public static boolean readJson(String fileRoute){
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileRoute));
            br.close();
            return true;
        } catch (Exception e){
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
            return false;
        }
    }

    public static String generateJSON(List<Estudiante> escolares){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(escolares);
    }

    public static void generateFile(String fileRoute, List<Estudiante> escolares){
        try{
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File(fileRoute);

            file.getParentFile().mkdirs();

            List<Estudiante> listaCompleta = new ArrayList<>();

            if (file.exists() && file.length() > 0) {
                try (FileReader reader = new FileReader(file)) {
                    JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

                    for (JsonElement elemento : jsonArray) {
                        Estudiante est = gson.fromJson(elemento, Estudiante.class);
                        listaCompleta.add(est);
                    }

                    System.out.println("Estudiantes existentes cargados: " + listaCompleta.size());
                } catch (Exception e) {
                    System.out.println("Advertencia: No se pudieron leer estudiantes existentes. Se creará archivo nuevo.");
                }
            }

            listaCompleta.addAll(escolares);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                String json = gson.toJson(listaCompleta);
                bw.write(json);
                System.out.println("Archivo JSON actualizado correctamente en: " + fileRoute);
                System.out.println("Total de estudiantes en el archivo: " + listaCompleta.size());
            }
            System.out.println("Archivo JSON generado correctamente en la ruta: " + fileRoute);
        } catch (Exception e){
            System.out.println("Error al generar el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static List<Estudiante> leerEstudiantes(String fileRoute) {
        List<Estudiante> estudiantes = new ArrayList<>();

        try {
            File file = new File(fileRoute);
            if (!file.exists()) {
                return estudiantes;
            }

            Gson gson = new Gson();
            try (FileReader reader = new FileReader(file)) {
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

                for (JsonElement elemento : jsonArray) {
                    Estudiante est = gson.fromJson(elemento, Estudiante.class);
                    estudiantes.add(est);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al leer estudiantes: " + e.getMessage());
        }

        return estudiantes;
    }
    public static void guardarListaCompleta(String fileRoute, List<Estudiante> estudiantes) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File(fileRoute);

            file.getParentFile().mkdirs();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                String json = gson.toJson(estudiantes);
                bw.write(json);
                System.out.println("Archivo JSON actualizado correctamente");
            }

        } catch (Exception e) {
            System.out.println("Error al guardar archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
