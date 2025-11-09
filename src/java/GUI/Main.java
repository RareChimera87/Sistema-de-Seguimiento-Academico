
package GUI;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Control.ControlEstudiantes;
import Logic.GenerateJSON;
import Model.TipoAsistencias;
import Model.TipoMateria;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ControlEstudiantes control = new ControlEstudiantes();
        System.out.println("Bienvenido Al Sistema de Gestion de Estudiantes");
        System.out.println(GenerateJSON.leerEstudiantes("data/estudiantes.json"));

        while (true) {
            ArrayList<Double> notas = new ArrayList<>();
            ArrayList<TipoMateria> materias = new ArrayList<>();


            System.out.println("Ingrese el Nombre: ");
            String nombre = entrada.nextLine().toLowerCase();

            System.out.println("Ingrese el ID: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            System.out.println("Ingrese el Grupo del Estudiante: ");
            int grupo = entrada.nextInt();
            entrada.nextLine();


            System.out.println("Ingrese el nota de participacion: ");
            int participacion = entrada.nextInt();
            entrada.nextLine();

            System.out.println("Ingrese algun comentario: ");
            String comentario = entrada.nextLine().toLowerCase();


            while (true) {
                System.out.println("Ingrese el nombre de la materia: ");
                String nombreMateria = entrada.nextLine().toLowerCase();
                ArrayList<TipoAsistencias> asis = new ArrayList<>();

                System.out.println("Ingrese Asistencias para el estudiante " + nombre + " en la materia " + nombreMateria + "(-1 para detenerse)");
                while (true) {
                    System.out.println("Ingrese fecha de la clase en formato dd/MM/yy: ");
                    String respuesta = entrada.nextLine().trim();

                    try {
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(respuesta);
                        System.out.println(respuesta);
                        System.out.println("Ingrese si la clase fue asistida (1 si fue asistida y 0 si no fue asistida): ");
                        int asistio = entrada.nextInt();
                        entrada.nextLine();
                        TipoAsistencias nuevaAsistencia = TipoAsistencias.registrarAsistencia(asistio == 1, date);
                        asis.add(nuevaAsistencia);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Insete cualquier tecla para seguir añadiendo asistencias o -1 para detenerse: ");
                    String rs = entrada.nextLine().trim();
                    try {
                        int decision = Integer.parseInt(rs);
                        if (decision == -1) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                    }


                }

                System.out.println("Ingrese notas para el estudiante " + nombre + " en la materia " + nombreMateria + "(-1 para detenerse)");
                while (true) {
                    System.out.println("Ingrese Nota (-1 para salir): ");
                    double nota = entrada.nextDouble();
                    entrada.nextLine();
                    if (nota == -1) {
                        break;
                    }
                    notas.add(nota);
                }
                control.registrarMateria(nombreMateria, notas, asis);
                System.out.println("Insete cualquier tecla para seguir añadiendo materias o -1 para detenerse: ");
                String respuesta = entrada.nextLine().trim();
                entrada.nextLine();
                try {
                    int decision = Integer.parseInt(respuesta);
                    if (decision == -1) {
                        break;
                    }
                } catch (NumberFormatException e) {
                }


            }
            List<TipoMateria> materiasRegistradas = control.getMaterias();
            control.registrarEstudiante(nombre, id, materiasRegistradas, grupo, participacion, comentario);

            //control.getEstudiantes();

            System.out.println("Ingrese -1 para salir o cualquier otra cosa para seguir: ");
            String respuesta = entrada.nextLine().trim();
            try {
                int decision = Integer.parseInt(respuesta);
                if (decision == -1) {
                    break;
                }
            } catch (NumberFormatException e) {
            }


        }
        entrada.close();
        control.generateJSONFile();

    }

}


