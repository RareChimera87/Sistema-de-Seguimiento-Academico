
package GUI;

import java.util.Scanner;

import Control.ControlEstudiantes;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ControlEstudiantes control  = new ControlEstudiantes();

        while(true){
            ArrayList<Float> notas = new ArrayList<>();

            System.out.println("Bienvenido Al Sistema de Gestion de Estudiantes");

            System.out.println("Ingrese el Nombre: ");
            String nombre = entrada.nextLine();

            System.out.println("Ingrese el ID: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            System.out.println("Ingrese el Grupo del Estudiante: ");
            int grupo = entrada.nextInt();
            entrada.nextLine();

            System.out.println("Ingrese el nota de asistencia: ");
            int asistencia = entrada.nextInt();
            entrada.nextLine();

            System.out.println("Ingrese el nota de participacion: ");
            int participacion = entrada.nextInt();
            entrada.nextLine();

            System.out.println("Ingrese algun comentario: ");
            String comentario = entrada.nextLine();

            System.out.println("Ingrese notas para el estudiante (-1 para detenerse)");
            while (true) {

                System.out.println("Ingrese Nota (-1 para salir): ");
                float nota = entrada.nextFloat();
                if (nota == -1) {
                    break;
                }
                notas.add(nota);


            }
            control.registrarEstudiante(nombre, id, notas, grupo, asistencia, participacion, comentario);

            control.getEstudiantes();
            entrada.nextLine();
            System.out.println("Ingrese -1 para salir o cualquier otra cosa para seguir: ");
            String respuesta = entrada.nextLine().trim();
            try {
                int decision = Integer.parseInt(respuesta);
                if (decision == -1) {
                    break;
                }
            } catch (NumberFormatException e) {}



        }
        entrada.close();
        control.generateJSONFile();

    }

}


