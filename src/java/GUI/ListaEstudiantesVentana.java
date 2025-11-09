package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ListaEstudiantesVentana extends JFrame {

    public ListaEstudiantesVentana() {
        super("Lista de Estudiantes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Estudiantes Registrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // Crear modelo de lista
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        cargarNombresDesdeJSON(modeloLista);

        // Crear JList con los nombres
        JList<String> listaNombres = new JList<>(modeloLista);
        listaNombres.setFont(new Font("Arial", Font.PLAIN, 16));
        listaNombres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNombres.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Scroll pane para la lista
        JScrollPane scrollPane = new JScrollPane(listaNombres);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Total: " + modeloLista.getSize() + " estudiantes"
        ));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botón
        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVolver.setPreferredSize(new Dimension(200, 35));
        btnVolver.addActionListener(e -> dispose());

        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void cargarNombresDesdeJSON(DefaultListModel<String> modelo) {
        try (FileReader reader = new FileReader("data/estudiantes.json")) {
            JsonArray estudiantes = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement elemento : estudiantes) {
                JsonObject estudiante = elemento.getAsJsonObject();

                String nombre = estudiante.get("nombre").getAsString();
                int id = estudiante.get("id").getAsInt();
                int grupo = estudiante.get("grupo").getAsInt();

                // Opción 1: Solo nombre
                modelo.addElement(nombre);

                // Opción 2: Con ID y grupo (comenta la línea anterior y descomenta esta)
                // modelo.addElement(nombre + " (ID: " + id + " - Grupo: " + grupo + ")");
            }

            if (estudiantes.size() == 0) {
                modelo.addElement("No hay estudiantes registrados");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo encontrar el archivo estudiantes.json",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            modelo.addElement("Error: Archivo no encontrado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar estudiantes: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            modelo.addElement("Error al cargar los datos");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListaEstudiantesVentana());
    }
}