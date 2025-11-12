package GUI;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Control.ControlEstudiantes;
import Model.Estudiante;
import Model.TipoAsistencias;
import Model.TipoMateria;

public class ModificarEstudiante extends JFrame {

    private JTextField txtNombre, txtId, txtGrupo, txtParticipacion;
    private JTextArea txtComentarios;
    private JButton btnBuscar, btnGuardar, btnCancelar, btnAgregarMateria, btnEliminarMateria;
    private JList<String> listaMaterias;
    private DefaultListModel<String> modeloMaterias;
    private ControlEstudiantes control;
    private Estudiante estudianteActual;
    private List<TipoMateria> materiasModificadas;

    public ModificarEstudiante(ControlEstudiantes control) {
        super("Modificar Estudiante");
        this.control = control;
        this.materiasModificadas = new ArrayList<>();

        setSize(700, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Modificar Estudiante", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar Estudiante"));
        panelBusqueda.add(new JLabel("ID del Estudiante:"));
        JTextField txtBuscarId = new JTextField(10);
        panelBusqueda.add(txtBuscarId);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarEstudiante(txtBuscarId.getText().trim()));
        panelBusqueda.add(btnBuscar);

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
        txtNombre.setEnabled(false);
        panelFormulario.add(txtNombre, gbc);

        // ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        txtId = new JTextField(20);
        txtId.setEnabled(false);
        panelFormulario.add(txtId, gbc);

        // Grupo
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Grupo:"), gbc);

        gbc.gridx = 1;
        txtGrupo = new JTextField(20);
        txtGrupo.setEnabled(false);
        panelFormulario.add(txtGrupo, gbc);

        // Participación
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Participación:"), gbc);

        gbc.gridx = 1;
        txtParticipacion = new JTextField(20);
        txtParticipacion.setEnabled(false);
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
        txtComentarios.setEnabled(false);
        JScrollPane scrollComentarios = new JScrollPane(txtComentarios);
        panelFormulario.add(scrollComentarios, gbc);

        // Materias
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(new JLabel("Materias:"), gbc);

        gbc.gridx = 1;
        JPanel panelBotonesMaterias = new JPanel();
        btnAgregarMateria = new JButton("Agregar");
        btnAgregarMateria.setEnabled(false);
        btnAgregarMateria.addActionListener(e -> agregarMateria());

        btnEliminarMateria = new JButton("Eliminar");
        btnEliminarMateria.setEnabled(false);
        btnEliminarMateria.addActionListener(e -> eliminarMateria());

        panelBotonesMaterias.add(btnAgregarMateria);
        panelBotonesMaterias.add(btnEliminarMateria);
        panelFormulario.add(panelBotonesMaterias, gbc);

        // Lista de materias
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        modeloMaterias = new DefaultListModel<>();
        listaMaterias = new JList<>(modeloMaterias);
        JScrollPane scrollMaterias = new JScrollPane(listaMaterias);
        scrollMaterias.setPreferredSize(new Dimension(300, 100));
        panelFormulario.add(scrollMaterias, gbc);

        // Panel principal que contiene búsqueda y formulario
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setPreferredSize(new Dimension(150, 35));
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(e -> guardarCambios());

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(150, 35));
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void buscarEstudiante(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            estudianteActual = control.buscarEstudiantePorId(id);

            if (estudianteActual == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró un estudiante con ID: " + id,
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Cargar datos en los campos
            txtNombre.setText(estudianteActual.getNombre());
            txtId.setText(String.valueOf(estudianteActual.getId()));
            txtGrupo.setText(String.valueOf(estudianteActual.getGrupo()));
            txtParticipacion.setText(String.valueOf(estudianteActual.getParticipacion()));
            txtComentarios.setText(estudianteActual.getComentarios());

            // Cargar materias
            materiasModificadas = new ArrayList<>(estudianteActual.getMaterias());
            actualizarListaMaterias();

            // Habilitar campos para edición
            habilitarCampos(true);

            JOptionPane.showMessageDialog(this,
                    "Estudiante encontrado. Puede modificar los datos.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un ID válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void habilitarCampos(boolean habilitar) {
        txtNombre.setEnabled(habilitar);
        txtGrupo.setEnabled(habilitar);
        txtParticipacion.setEnabled(habilitar);
        txtComentarios.setEnabled(habilitar);
        btnAgregarMateria.setEnabled(habilitar);
        btnEliminarMateria.setEnabled(habilitar);
        btnGuardar.setEnabled(habilitar);
    }

    private void actualizarListaMaterias() {
        modeloMaterias.clear();
        for (TipoMateria materia : materiasModificadas) {
            modeloMaterias.addElement(materia.getMateria() + " - " +
                    materia.getNotas().size() + " notas, " +
                    materia.getAsistencia().size() + " asistencias");
        }
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

                ArrayList<Double> notas = new ArrayList<>();
                String[] notasArray = txtNotas.getText().split(",");
                for (String nota : notasArray) {
                    if (!nota.trim().isEmpty()) {
                        notas.add(Double.parseDouble(nota.trim()));
                    }
                }

                TipoMateria nuevaMateria = TipoMateria.crearMateria(nombreMateria, notas, asistencias);
                materiasModificadas.add(nuevaMateria);
                actualizarListaMaterias();

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

    private void eliminarMateria() {
        int selectedIndex = listaMaterias.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una materia para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar esta materia?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            materiasModificadas.remove(selectedIndex);
            actualizarListaMaterias();
        }
    }

    private void guardarCambios() {
        try {
            String nombre = txtNombre.getText().trim().toLowerCase();
            int grupo = Integer.parseInt(txtGrupo.getText().trim());
            int participacion = Integer.parseInt(txtParticipacion.getText().trim());
            String comentarios = txtComentarios.getText().trim().toLowerCase();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
                return;
            }

            if (materiasModificadas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe tener al menos una materia");
                return;
            }

            // Actualizar estudiante
            estudianteActual.setNombre(nombre);
            estudianteActual.setGrupo(grupo);
            estudianteActual.setParticipacion(participacion);
            estudianteActual.setComentarios(comentarios);
            estudianteActual.setMaterias(materiasModificadas);

            // Guardar cambios en el JSON
            control.actualizarEstudiante(estudianteActual);

            JOptionPane.showMessageDialog(this,
                    "Estudiante actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Error en los datos numéricos. Verifique Grupo y Participación");
        }
    }
}