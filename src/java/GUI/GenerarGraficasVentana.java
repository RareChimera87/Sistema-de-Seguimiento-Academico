package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GenerarGraficasVentana extends JFrame {

    private JTextArea txtConsola;
    private JButton btnGenerar, btnVerGraficas, btnCerrar;
    private JProgressBar progressBar;
    private boolean graficasGeneradas = false;

    public GenerarGraficasVentana() {
        super("Generar Gráficas Académicas");

        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Generador de Gráficas Estadísticas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central con información
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));

        JLabel lblInfo1 = new JLabel("Este módulo generará 4 gráficas estadísticas:");
        JLabel lblInfo2 = new JLabel("  1. Promedio de notas por estudiante (Matplotlib)");
        JLabel lblInfo3 = new JLabel("  2. Participación vs Promedio (Matplotlib)");
        JLabel lblInfo4 = new JLabel("  3. Heatmap de asistencias (Seaborn)");
        JLabel lblInfo5 = new JLabel("  4. Distribución de notas por grupo (Seaborn)");
        JLabel lblInfo6 = new JLabel(" ");
        JLabel lblInfo7 = new JLabel("Las gráficas se guardarán en: data/graficas/");

        lblInfo1.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblInfo2.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblInfo3.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblInfo4.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblInfo5.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblInfo6.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblInfo7.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblInfo7.setFont(new Font("Arial", Font.BOLD, 12));

        panelInfo.add(lblInfo1);
        panelInfo.add(lblInfo2);
        panelInfo.add(lblInfo3);
        panelInfo.add(lblInfo4);
        panelInfo.add(lblInfo5);
        panelInfo.add(lblInfo6);
        panelInfo.add(lblInfo7);

        // Consola de salida
        txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        txtConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtConsola.setBackground(Color.BLACK);
        txtConsola.setForeground(Color.GREEN);
        JScrollPane scrollConsola = new JScrollPane(txtConsola);
        scrollConsola.setBorder(BorderFactory.createTitledBorder("Consola de Python"));

        // Barra de progreso
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Listo para generar gráficas");

        panelCentral.add(panelInfo, BorderLayout.NORTH);
        panelCentral.add(scrollConsola, BorderLayout.CENTER);
        panelCentral.add(progressBar, BorderLayout.SOUTH);

        add(panelCentral, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnGenerar = new JButton("Generar Gráficas");
        btnGenerar.setPreferredSize(new Dimension(180, 40));
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerar.setBackground(new Color(40, 167, 69));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.addActionListener(e -> generarGraficas());

        btnVerGraficas = new JButton("Ver Gráficas");
        btnVerGraficas.setPreferredSize(new Dimension(150, 40));
        btnVerGraficas.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVerGraficas.setEnabled(false);
        btnVerGraficas.addActionListener(e -> verGraficas());

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(150, 40));
        btnCerrar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnGenerar);
        panelBotones.add(btnVerGraficas);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void generarGraficas() {
        // Verificar que exista el archivo JSON
        if (!Files.exists(Paths.get("data/estudiantes.json"))) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el archivo data/estudiantes.json\n" +
                            "Por favor, registre estudiantes primero.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Deshabilitar botón mientras se ejecuta
        btnGenerar.setEnabled(false);
        btnVerGraficas.setEnabled(false);
        txtConsola.setText("");
        progressBar.setValue(0);
        progressBar.setString("Generando gráficas...");

        // Ejecutar en un hilo separado para no bloquear la UI
        SwingWorker<Boolean, String> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    // Determinar el comando de Python según el sistema operativo
                    String pythonCmd = System.getProperty("os.name").toLowerCase().contains("win")
                            ? "python" : "python3";

                    // Ruta al script de Python
                    String scriptPath = "src/python/generar_graficas.py";

                    publish("Iniciando generación de gráficas...\n");
                    publish("Ejecutando: " + pythonCmd + " " + scriptPath + "\n");
                    publish("=".repeat(60) + "\n");

                    // Crear el proceso
                    ProcessBuilder processBuilder = new ProcessBuilder(pythonCmd, scriptPath);
                    processBuilder.redirectErrorStream(true);

                    Process process = processBuilder.start();

                    // Leer la salida del proceso
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    String line;
                    int progress = 0;
                    while ((line = reader.readLine()) != null) {
                        publish(line + "\n");

                        // Actualizar barra de progreso
                        if (line.contains("Gráfica 1")) progress = 25;
                        else if (line.contains("Gráfica 2")) progress = 50;
                        else if (line.contains("Gráfica 3")) progress = 75;
                        else if (line.contains("Gráfica 4")) progress = 90;
                        else if (line.contains("COMPLETADO")) progress = 100;

                        int finalProgress = progress;
                        SwingUtilities.invokeLater(() -> progressBar.setValue(finalProgress));
                    }

                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        publish("\n✓ Proceso completado exitosamente\n");
                        return true;
                    } else {
                        publish("\n✗ Error: El proceso terminó con código " + exitCode + "\n");
                        return false;
                    }

                } catch (IOException e) {
                    publish("\n✗ Error de IO: " + e.getMessage() + "\n");
                    publish("\nPosibles soluciones:\n");
                    publish("1. Verifique que Python esté instalado\n");
                    publish("2. Instale las dependencias: pip install -r src/python/requirements.txt\n");
                    publish("3. Verifique la ruta del script de Python\n");
                    return false;
                } catch (InterruptedException e) {
                    publish("\n✗ Proceso interrumpido\n");
                    return false;
                }
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String chunk : chunks) {
                    txtConsola.append(chunk);
                }
                txtConsola.setCaretPosition(txtConsola.getDocument().getLength());
            }

            @Override
            protected void done() {
                try {
                    boolean exito = get();

                    if (exito) {
                        progressBar.setString("Gráficas generadas exitosamente");
                        graficasGeneradas = true;
                        btnVerGraficas.setEnabled(true);

                        JOptionPane.showMessageDialog(GenerarGraficasVentana.this,
                                "Las gráficas se han generado exitosamente.\n" +
                                        "Haga clic en 'Ver Gráficas' para visualizarlas.",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        progressBar.setString("Error al generar gráficas");

                        JOptionPane.showMessageDialog(GenerarGraficasVentana.this,
                                "Hubo un error al generar las gráficas.\n" +
                                        "Revise la consola para más detalles.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception e) {
                    progressBar.setString("Error");
                    JOptionPane.showMessageDialog(GenerarGraficasVentana.this,
                            "Error inesperado: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                btnGenerar.setEnabled(true);
            }
        };

        worker.execute();
    }

    private void verGraficas() {
        new VisualizadorGraficas();
    }

    // Clase interna para visualizar las gráficas
    private class VisualizadorGraficas extends JFrame {

        public VisualizadorGraficas() {
            super("Visualizador de Gráficas");

            setSize(1200, 800);
            setLocationRelativeTo(GenerarGraficasVentana.this);
            setLayout(new BorderLayout());

            // Panel con pestañas para cada gráfica
            JTabbedPane tabbedPane = new JTabbedPane();

            // Agregar cada gráfica como una pestaña
            agregarGrafica(tabbedPane, "Promedio por Estudiante", "data/graficas/grafica1_promedios.png");
            agregarGrafica(tabbedPane, "Participación vs Promedio", "data/graficas/grafica2_participacion.png");
            agregarGrafica(tabbedPane, "Heatmap de Asistencias", "data/graficas/grafica3_heatmap_asistencias.png");
            agregarGrafica(tabbedPane, "Distribución por Grupos", "data/graficas/grafica4_distribucion_grupos.png");

            add(tabbedPane, BorderLayout.CENTER);

            // Botón cerrar
            JButton btnCerrarVisor = new JButton("Cerrar");
            btnCerrarVisor.addActionListener(e -> dispose());
            JPanel panelBoton = new JPanel();
            panelBoton.add(btnCerrarVisor);
            add(panelBoton, BorderLayout.SOUTH);

            setVisible(true);
        }

        private void agregarGrafica(JTabbedPane tabbedPane, String titulo, String rutaImagen) {
            try {
                if (Files.exists(Paths.get(rutaImagen))) {
                    ImageIcon icon = new ImageIcon(rutaImagen);

                    // Escalar imagen si es muy grande
                    Image img = icon.getImage();
                    Image scaledImg = img.getScaledInstance(1100, 700, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImg);

                    JLabel lblImagen = new JLabel(scaledIcon);
                    JScrollPane scrollPane = new JScrollPane(lblImagen);

                    tabbedPane.addTab(titulo, scrollPane);
                } else {
                    JLabel lblError = new JLabel("No se encontró la imagen: " + rutaImagen, SwingConstants.CENTER);
                    tabbedPane.addTab(titulo, lblError);
                }
            } catch (Exception e) {
                JLabel lblError = new JLabel("Error al cargar: " + e.getMessage(), SwingConstants.CENTER);
                tabbedPane.addTab(titulo, lblError);
            }
        }
    }
}