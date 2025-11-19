package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Control.ControlEstudiantes;
import Model.Estudiante;

public class EliminarEstudiante extends JFrame {

    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar, btnVolver;
    private ControlEstudiantes control;

    public EliminarEstudiante(ControlEstudiantes control) {
        super("Eliminar Estudiante");
        this.control = control;

        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JLabel titulo = new JLabel("Eliminar Estudiante", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);


        JPanel panelInstrucciones = new JPanel();
        panelInstrucciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblInstrucciones = new JLabel("Seleccione un estudiante de la tabla y haga clic en Eliminar");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInstrucciones.add(lblInstrucciones);

        String[] columnas = {"ID", "Nombre", "Grupo", "Participación", "Materias"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaEstudiantes.setRowHeight(25);
        tablaEstudiantes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaEstudiantes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaEstudiantes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaEstudiantes.getColumnModel().getColumn(2).setPreferredWidth(80);
        tablaEstudiantes.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablaEstudiantes.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Estudiantes Registrados"));

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelInstrucciones, BorderLayout.NORTH);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);


        cargarEstudiantes();


        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnEliminar = new JButton("Eliminar Estudiante");
        btnEliminar.setPreferredSize(new Dimension(170, 35));
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.addActionListener(e -> eliminarEstudiante());

        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(150, 35));
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVolver.addActionListener(e -> dispose());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void cargarEstudiantes() {
        modeloTabla.setRowCount(0);

        List<Estudiante> estudiantes = control.cargarEstudiantesDesdeJSON();

        if (estudiantes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay estudiantes registrados",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            btnEliminar.setEnabled(false);
            return;
        }

        for (Estudiante estudiante : estudiantes) {
            Object[] fila = {
                    estudiante.getId(),
                    estudiante.getNombre(),
                    estudiante.getGrupo(),
                    estudiante.getParticipacion(),
                    estudiante.getMaterias().size() + " materias"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void eliminarEstudiante() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un estudiante de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al estudiante?\n\n" +
                        "ID: " + id + "\n" +
                        "Nombre: " + nombre + "\n\n" +
                        "Esta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = control.eliminarEstudiante(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this,
                        "Estudiante eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                cargarEstudiantes();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar el estudiante",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}