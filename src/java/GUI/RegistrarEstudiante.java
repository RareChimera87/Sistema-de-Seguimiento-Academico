package GUI;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Control.ControlEstudiantes;
import Model.TipoAsistencias;
import Model.TipoMateria;
import java.util.List;

public class RegistrarEstudiante extends JFrame {

    private JTextField txtNombre, txtId, txtGrupo, txtParticipacion;
    private JTextArea txtComentarios;
    private JButton btnAgregarMateria, btnGuardar, btnCancelar;
    private JList<String> listaMaterias;
    private DefaultListModel<String> modeloMaterias;
    private ControlEstudiantes control;

    public RegistrarEstudiante(ControlEstudiantes control) {
        super("Registrar Nuevo Estudiante");
        this.control = control;

        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Registrar Nuevo Estudiante", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        // ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        txtId = new JTextField(20);
        panelFormulario.add(txtId, gbc);

        // Grupo
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Grupo:"), gbc);

        gbc.gridx = 1;
        txtGrupo = new JTextField(20);
        panelFormulario.add(txtGrupo, gbc);

        // Participación
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Participación:"), gbc);

        gbc.gridx = 1;
        txtParticipacion = new JTextField(20);
        panelFormulario.add(txtParticipacion, gbc);

        // Comentarios
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTH;
        panelFormulario.add(new JLabel("Comentarios:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        txtComentarios = new JTextArea(3, 20);
        txtComentarios.setLineWrap(true);
        JScrollPane scrollComentarios = new JScrollPane(txtComentarios);
        panelFormulario.add(scrollComentarios, gbc);

        // Materias
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(new JLabel("Materias:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        btnAgregarMateria = new JButton("Agregar Materia");
        btnAgregarMateria.addActionListener(e -> agregarMateria());
        panelFormulario.add(btnAgregarMateria, gbc);

        // Lista de materias agregadas
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        modeloMaterias = new DefaultListModel<>();
        listaMaterias = new JList<>(modeloMaterias);
        JScrollPane scrollMaterias = new JScrollPane(listaMaterias);
        scrollMaterias.setPreferredSize(new Dimension(300, 100));
        panelFormulario.add(scrollMaterias, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnGuardar = new JButton("Guardar Estudiante");
        btnGuardar.setPreferredSize(new Dimension(150, 35));
        btnGuardar.addActionListener(e -> guardarEstudiante());

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(150, 35));
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void agregarMateria() {
        JDialog dialogMateria = new JDialog(this, "Agregar Materia", true);
        dialogMateria.setSize(500, 450);
        dialogMateria.setLocationRelativeTo(this);
        dialogMateria.setLayout(new BorderLayout());

        JPanel panelMateria = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre de la materia
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelMateria.add(new JLabel("Nombre de la Materia:"), gbc);

        gbc.gridx = 1;
        JTextField txtNombreMateria = new JTextField(15);
        panelMateria.add(txtNombreMateria, gbc);

        // Notas
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelMateria.add(new JLabel("Notas (separadas por coma):"), gbc);

        gbc.gridx = 1;
        JTextField txtNotas = new JTextField(15);
        panelMateria.add(txtNotas, gbc);

        // Asistencias
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelMateria.add(new JLabel("Asistencias:"), gbc);

        gbc.gridx = 1;
        JButton btnAgregarAsistencia = new JButton("Agregar Asistencia");
        panelMateria.add(btnAgregarAsistencia, gbc);

        // Lista de asistencias
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        DefaultListModel<String> modeloAsistencias = new DefaultListModel<>();
        JList<String> listaAsistencias = new JList<>(modeloAsistencias);
        JScrollPane scrollAsistencias = new JScrollPane(listaAsistencias);
        panelMateria.add(scrollAsistencias, gbc);

        ArrayList<TipoAsistencias> asistencias = new ArrayList<>();

        btnAgregarAsistencia.addActionListener(e -> {
            JDialog dialogAsistencia = new JDialog(dialogMateria, "Agregar Asistencia", true);
            dialogAsistencia.setSize(350, 200);
            dialogAsistencia.setLocationRelativeTo(dialogMateria);
            dialogAsistencia.setLayout(new GridBagLayout());

            GridBagConstraints gbcAsis = new GridBagConstraints();
            gbcAsis.insets = new Insets(10, 10, 10, 10);
            gbcAsis.fill = GridBagConstraints.HORIZONTAL;

            gbcAsis.gridx = 0;
            gbcAsis.gridy = 0;
            dialogAsistencia.add(new JLabel("Fecha (dd/MM/yyyy):"), gbcAsis);

            gbcAsis.gridx = 1;
            JTextField txtFecha = new JTextField(15);
            dialogAsistencia.add(txtFecha, gbcAsis);

            gbcAsis.gridx = 0;
            gbcAsis.gridy = 1;
            dialogAsistencia.add(new JLabel("Asistió:"), gbcAsis);

            gbcAsis.gridx = 1;
            JCheckBox chkAsistio = new JCheckBox("Sí");
            dialogAsistencia.add(chkAsistio, gbcAsis);

            gbcAsis.gridx = 0;
            gbcAsis.gridy = 2;
            gbcAsis.gridwidth = 2;
            JButton btnAceptarAsis = new JButton("Agregar");
            btnAceptarAsis.addActionListener(ev -> {
                try {
                    Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText());
                    TipoAsistencias asistencia = TipoAsistencias.registrarAsistencia(chkAsistio.isSelected(), fecha);
                    asistencias.add(asistencia);
                    modeloAsistencias.addElement((chkAsistio.isSelected() ? "✓" : "✗") + " - " + txtFecha.getText());
                    dialogAsistencia.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogAsistencia, "Fecha inválida. Use formato dd/MM/yyyy");
                }
            });
            dialogAsistencia.add(btnAceptarAsis, gbcAsis);

            dialogAsistencia.setVisible(true);
        });

        dialogMateria.add(panelMateria, BorderLayout.CENTER);

        // Botones del diálogo
        JPanel panelBotonesMateria = new JPanel();
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            try {
                String nombreMateria = txtNombreMateria.getText().trim().toLowerCase();

                if (nombreMateria.isEmpty()) {
                    JOptionPane.showMessageDialog(dialogMateria, "Ingrese el nombre de la materia");
                    return;
                }

                // Procesar notas
                ArrayList<Double> notas = new ArrayList<>();
                String[] notasArray = txtNotas.getText().split(",");
                for (String nota : notasArray) {
                    if (!nota.trim().isEmpty()) {
                        notas.add(Double.parseDouble(nota.trim()));
                    }
                }

                // Registrar materia usando el método del control (igual que en el Main)
                control.registrarMateria(nombreMateria, notas, asistencias);

                modeloMaterias.addElement(nombreMateria + " - " + notas.size() + " notas, " + asistencias.size() + " asistencias");

                dialogMateria.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogMateria, "Error en las notas. Use números separados por comas");
            }
        });

        JButton btnCancelarMateria = new JButton("Cancelar");
        btnCancelarMateria.addActionListener(e -> dialogMateria.dispose());

        panelBotonesMateria.add(btnAceptar);
        panelBotonesMateria.add(btnCancelarMateria);
        dialogMateria.add(panelBotonesMateria, BorderLayout.SOUTH);

        dialogMateria.setVisible(true);
    }

    private void guardarEstudiante() {
        try {
            String nombre = txtNombre.getText().trim().toLowerCase();
            int id = Integer.parseInt(txtId.getText().trim());
            int grupo = Integer.parseInt(txtGrupo.getText().trim());
            int participacion = Integer.parseInt(txtParticipacion.getText().trim());
            String comentarios = txtComentarios.getText().trim().toLowerCase();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del estudiante");
                return;
            }

            List<TipoMateria> materiasRegistradas = control.getMaterias();

            if (materiasRegistradas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Agregue al menos una materia");
                return;
            }

            // Igual que en el Main
            control.registrarEstudiante(nombre, id, materiasRegistradas, grupo, participacion, comentarios);
            control.generateJSONFile();

            JOptionPane.showMessageDialog(this, "Estudiante registrado exitosamente");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en los datos numéricos. Verifique ID, Grupo y Participación");
        }
    }
}