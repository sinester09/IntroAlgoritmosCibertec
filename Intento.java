package tareasalgo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Intento extends JFrame {
    static JTabbedPane pestañas;
    static JTextField txtNumeroCamiseta, txtNombre;
    static JComboBox<String> comboPosicion, comboJugadores;
    static JTextField txtMinutos, txtTries, txtConversiones, txtPenales, txtDropGoals, txtFaltas;
    static JTable tablaJugadores;
    static DefaultTableModel modeloTabla;
    static JTextArea txtAreaResultados;

    // Datos de jugadores
    static final int MAX_JUGADORES = 100; // Máximo de jugadores que pueden registrarse
    static int totalJugadores = 0; // Contador de jugadores
    static Jugador[] jugadores = new Jugador[MAX_JUGADORES]; // Arreglo para almacenar los jugadores
    static int partidoActual = 1; // Número del partido actual

    public Intento() {
        setTitle("Sistema de Estadísticas de Rugby");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {
        pestañas = new JTabbedPane();

        // Crear y agregar paneles a las pestañas
        JPanel panelJugadores = crearPanelJugadores();
        JPanel panelEstadisticas = crearPanelEstadisticas();
        JPanel panelConsultas = crearPanelConsultas();

        pestañas.addTab("Jugadores", panelJugadores);
        pestañas.addTab("Estadísticas", panelEstadisticas);
        pestañas.addTab("Consultas", panelConsultas);

        add(pestañas); // Agregar pestañas al marco
    }

    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new BorderLayout());

        // Formulario para ingresar datos del jugador
        JPanel formulario = new JPanel(new GridLayout(4, 2));
        txtNumeroCamiseta = new JTextField();
        txtNombre = new JTextField();
        comboPosicion = new JComboBox<>(new String[]{"Pilar", "Hooker", "Segunda línea", "Flanker", "Número 8", "Medio scrum", "Apertura", "Centro", "Wing", "Fullback"});

        formulario.add(new JLabel("Número de camiseta:"));
        formulario.add(txtNumeroCamiseta);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Posición:"));
        formulario.add(comboPosicion);

        JButton btnAgregar = new JButton("Agregar jugador");
        formulario.add(btnAgregar);

        panel.add(formulario, BorderLayout.NORTH);

        // Crear la tabla de jugadores
        modeloTabla = new DefaultTableModel(new String[]{"Camiseta", "Nombre", "Posición"}, 0);
        tablaJugadores = new JTable(modeloTabla);
        panel.add(new JScrollPane(tablaJugadores), BorderLayout.CENTER);

        // Acción para agregar un jugador
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numero = Integer.parseInt(txtNumeroCamiseta.getText()); // Obtener el número de camiseta
                String nombre = txtNombre.getText(); // Obtener el nombre
                String posicion = (String) comboPosicion.getSelectedItem(); // Obtener la posición

                // Verificar si el jugador ya existe (por su número de camiseta)
                if (buscarJugador(numero) != -1) {
                    JOptionPane.showMessageDialog(null, "Número de camiseta ya registrado");
                    return;
                }

                // Verificar si la posición ya está ocupada por otro jugador
                for (int i = 0; i < totalJugadores; i++) {
                    if (jugadores[i].posicion.equals(posicion)) {
                        JOptionPane.showMessageDialog(null, "La posición ya está ocupada por otro jugador");
                        return;
                    }
                }

                agregarJugador(numero, nombre, posicion); // Agregar el jugador al sistema
                modeloTabla.addRow(new Object[]{numero, nombre, posicion}); // Mostrarlo en la tabla
                comboJugadores.addItem(numero + " - " + nombre); // Agregarlo al combo de jugadores
            }
        });

        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(9, 2));
        comboJugadores = new JComboBox<>();
        txtMinutos = new JTextField();
        txtTries = new JTextField();
        txtConversiones = new JTextField();
        txtPenales = new JTextField();
        txtDropGoals = new JTextField();
        txtFaltas = new JTextField();

        formulario.add(new JLabel("Jugador:"));
        formulario.add(comboJugadores);
        formulario.add(new JLabel("Minutos jugados:"));
        formulario.add(txtMinutos);
        formulario.add(new JLabel("Tries:"));
        formulario.add(txtTries);
        formulario.add(new JLabel("Conversiones:"));
        formulario.add(txtConversiones);
        formulario.add(new JLabel("Penales:"));
        formulario.add(txtPenales);
        formulario.add(new JLabel("Drop goals:"));
        formulario.add(txtDropGoals);
        formulario.add(new JLabel("Faltas:"));
        formulario.add(txtFaltas);

        JButton btnRegistrar = new JButton("Registrar estadísticas");
        formulario.add(new JLabel("Partido #" + partidoActual));
        formulario.add(btnRegistrar);

        panel.add(formulario, BorderLayout.NORTH);

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboJugadores.getSelectedIndex(); // Obtener el jugador seleccionado
                if (index < 0) return; // Si no hay jugador seleccionado, no hacer nada

                Jugador jugador = jugadores[index];

                // Obtener las estadísticas ingresadas
                int minutos = Integer.parseInt(txtMinutos.getText());
                int tries = Integer.parseInt(txtTries.getText());
                int conv = Integer.parseInt(txtConversiones.getText());
                int pen = Integer.parseInt(txtPenales.getText());
                int drop = Integer.parseInt(txtDropGoals.getText());
                int faltas = Integer.parseInt(txtFaltas.getText());

                // Validar que los valores sean positivos
                if (minutos < 0 || tries < 0 || conv < 0 || pen < 0 || drop < 0 || faltas < 0) {
                    JOptionPane.showMessageDialog(null, "Todos los valores deben ser positivos");
                    return;
                }

                registrarEstadisticas(jugador.numeroCamiseta, minutos, tries, conv, pen, drop, faltas); // Registrar las estadísticas
                partidoActual++; // Incrementar el número del partido
            }
        });

        return panel;
    }

    private JPanel crearPanelConsultas() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel controles = new JPanel(new GridLayout(2, 2));
        JTextField txtBuscar = new JTextField();
        JButton btnVerEstadisticas = new JButton("Ver estadísticas jugador");
        JButton btnRanking = new JButton("Ranking por puntos");
        JButton btnGlobal = new JButton("Estadísticas globales");

        controles.add(new JLabel("N° Camiseta:"));
        controles.add(txtBuscar);
        controles.add(btnVerEstadisticas);
        controles.add(btnRanking);

        txtAreaResultados = new JTextArea();
        panel.add(controles, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaResultados), BorderLayout.CENTER);
        panel.add(btnGlobal, BorderLayout.SOUTH);

        // Acción para ver estadísticas del jugador
        btnVerEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int num = Integer.parseInt(txtBuscar.getText());
                mostrarEstadisticasJugador(num); // Mostrar las estadísticas del jugador
            }
        });

        // Acción para mostrar el ranking de jugadores
        btnRanking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String texto = "";
                // Ordenar los jugadores por puntos
                for (int i = 0; i < totalJugadores - 1; i++) {
                    for (int j = i + 1; j < totalJugadores; j++) {
                        if (jugadores[j].puntos > jugadores[i].puntos) {
                            Jugador temp = jugadores[i];
                            jugadores[i] = jugadores[j];
                            jugadores[j] = temp;
                        }
                    }
                }

                // Mostrar el ranking
                for (int i = 0; i < totalJugadores; i++) {
                    texto += jugadores[i].nombre + ": " + jugadores[i].puntos + " puntos\n";
                }

                txtAreaResultados.setText(texto); // Mostrar el ranking en el área de resultados
            }
        });

        // Acción para mostrar estadísticas globales del equipo
        btnGlobal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalPuntos = 0;
                int totalMinutos = 0;
                int totalFaltas = 0;
                // Calcular los totales
                for (int i = 0; i < totalJugadores; i++) {
                    totalPuntos += jugadores[i].puntos;
                    totalMinutos += jugadores[i].minutosJugados;
                    totalFaltas += jugadores[i].faltas;
                }

                // Mostrar las estadísticas globales
                txtAreaResultados.setText("Total puntos equipo: " + totalPuntos +
                        "\nMinutos totales: " + totalMinutos +
                        "\nFaltas totales: " + totalFaltas);
            }
        });

        return panel;
    }

    // ============================ LÓGICA ==============================

    public void agregarJugador(int numeroCamiseta, String nombre, String posicion) {
        if (totalJugadores < MAX_JUGADORES) {
            jugadores[totalJugadores] = new Jugador(numeroCamiseta, nombre, posicion);
            totalJugadores++;
        }
    }

    public void registrarEstadisticas(int numeroCamiseta, int minutos, int tries, int conversiones, int penales, int dropGoals, int faltas) {
        int index = buscarJugador(numeroCamiseta);
        if (index == -1) return;

        Jugador j = jugadores[index];
        j.minutosJugados += minutos;
        j.tries += tries;
        j.conversiones += conversiones;
        j.penales += penales;
        j.dropGoals += dropGoals;
        j.faltas += faltas;
        j.partidos++;
        j.puntos = calcularPuntosTotales(j.numeroCamiseta);
    }

    public void mostrarEstadisticasJugador(int numeroCamiseta) {
        int index = buscarJugador(numeroCamiseta);
        if (index == -1) {
            txtAreaResultados.setText("Jugador no encontrado.");
            return;
        }

        Jugador j = jugadores[index];
        String texto = "Jugador: " + j.nombre +
                "\nPosición: " + j.posicion +
                "\nMinutos: " + j.minutosJugados +
                "\nTries: " + j.tries +
                "\nConversiones: " + j.conversiones +
                "\nPenales: " + j.penales +
                "\nDrop Goals: " + j.dropGoals +
                "\nFaltas: " + j.faltas +
                "\nPuntos: " + j.puntos +
                "\nPartidos: " + j.partidos +
                "\nPromedio minutos: " + calcularPromedioMinutos(j.numeroCamiseta);

        txtAreaResultados.setText(texto);
    }

    public int calcularPuntosTotales(int numeroCamiseta) {
        int i = buscarJugador(numeroCamiseta);
        if (i == -1) return 0;

        Jugador j = jugadores[i];
        return j.tries * 5 + j.conversiones * 2 + j.penales * 3 + j.dropGoals * 3;
    }

    public double calcularPromedioMinutos(int numeroCamiseta) {
        int i = buscarJugador(numeroCamiseta);
        if (i == -1 || jugadores[i].partidos == 0) return 0;

        return (double) jugadores[i].minutosJugados / jugadores[i].partidos;
    }

    private int buscarJugador(int numeroCamiseta) {
        for (int i = 0; i < totalJugadores; i++) {
            if (jugadores[i].numeroCamiseta == numeroCamiseta) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Intento());
    }

    // Clase interna para representar a un jugador
    class Jugador {
        int numeroCamiseta;
        String nombre, posicion;
        int minutosJugados = 0, tries = 0, conversiones = 0, penales = 0, dropGoals = 0, faltas = 0, partidos = 0, puntos = 0;

        public Jugador(int numero, String nombre, String posicion) {
            this.numeroCamiseta = numero;
            this.nombre = nombre;
            this.posicion = posicion;
        }
    }
}
