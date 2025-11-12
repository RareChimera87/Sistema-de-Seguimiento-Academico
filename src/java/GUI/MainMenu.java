package GUI;

import javax.swing.*;
import java.awt.*;
import Control.ControlEstudiantes;

public class MainMenu extends JFrame {
    public MainMenu() {
        super("Sistema de Gestion y Registro de Estudiantes");

        ControlEstudiantes control = new ControlEstudiantes();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título principal
        JLabel titulo = new JLabel("Sistema de Gestión de Estudiantes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central con el menú
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta de instrucción
        JLabel lblInstruccion = new JLabel("¿Qué desea hacer?", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        panelCentral.add(lblInstruccion, gbc);

        // Botones del menú
        Dimension tamañoBoton = new Dimension(300, 45);
        gbc.insets = new Insets(8, 0, 8, 0);

        JButton btnRegistrar = crearBoton("Registrar Nuevo Estudiante", tamañoBoton);
        gbc.gridy = 1;
        panelCentral.add(btnRegistrar, gbc);

        JButton btnIngresarNotas = crearBoton("Ingresar Notas de Estudiantes", tamañoBoton);
        gbc.gridy = 2;
        panelCentral.add(btnIngresarNotas, gbc);

        JButton btnVerEstudiante = crearBoton("Ver Información de Estudiante", tamañoBoton);
        gbc.gridy = 3;
        panelCentral.add(btnVerEstudiante, gbc);

        JButton btnListarEstudiantes = crearBoton("Listar Todos los Estudiantes", tamañoBoton);
        gbc.gridy = 4;
        panelCentral.add(btnListarEstudiantes, gbc);

        JButton btnModificar = crearBoton("Modificar Estudiante", tamañoBoton);
        gbc.gridy = 5;
        panelCentral.add(btnModificar, gbc);

        JButton btnEliminar = crearBoton("Eliminar Estudiante", tamañoBoton);
        gbc.gridy = 6;
        panelCentral.add(btnEliminar, gbc);

        JButton btnSalir = crearBoton("Salir", tamañoBoton);
        btnSalir.setBackground(new Color(220, 53, 69));
        btnSalir.setForeground(Color.WHITE);
        gbc.gridy = 7;
        gbc.insets = new Insets(20, 0, 0, 0);
        panelCentral.add(btnSalir, gbc);

        add(panelCentral, BorderLayout.CENTER);

        // Action Listeners
        btnRegistrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abriendo formulario de registro...");
            new RegistrarEstudiante(control);
        });

        btnIngresarNotas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abriendo ingreso de notas...");
            // Aquí abrirías la ventana de notas
        });

        btnVerEstudiante.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abriendo búsqueda de estudiante...");
            // Aquí abrirías la ventana de búsqueda
        });

        btnListarEstudiantes.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Mostrando lista de estudiantes...");
            new ListaEstudiantesVentana();
        });

        btnModificar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abriendo modificación de estudiante...");
            new ModificarEstudiante(control);
        });

        btnEliminar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de que desea eliminar un estudiante?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Abriendo eliminación de estudiante...");
                new EliminarEstudiante(control);
            }
        });

        btnSalir.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de que desea salir?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private JButton crearBoton(String texto, Dimension tamaño) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(tamaño);
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}