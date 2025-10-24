
package src.GUI;

import java.util.Scanner;

import src.Control.controlEstudiante;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        controlEstudiante control  = new controlEstudiante();
        ArrayList<Float> notas = new ArrayList<>();

        RegistroEstudiante reg = new RegistroEstudiante();

        System.out.println("Bienvenido Al Sistema de Gestion de Estudiantes");

        System.out.println("Ingrese el Nombre");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese el ID");
        int id = entrada.nextInt();
        System.out.println("Ingrese el Grupo del Estudiante");
        int grupo = entrada.nextInt();
        System.out.println("Ingrese el nota de asistencia");
        int asistencia = entrada.nextInt();
        System.out.println("Ingrese el nota de participacion");
        int participacion = entrada.nextInt();
        System.out.println("Ingrese algun comentario");
        String comentario = entrada.nextLine();
        System.out.println("Ingrese notas para el estudiante (-1 para detenerse)");
        while (true) {

            System.out.println("Ingrese Nota (-1 para salir)");
            float nota = entrada.nextFloat();
            if (nota == -1) {
                break;
            }
            notas.add(nota);


        }
        control.registrarEstudiante(nombre, id, notas, grupo, asistencia, participacion, comentario);

        control.getEstudiantes()

        entrada.close();

    }

}

}
