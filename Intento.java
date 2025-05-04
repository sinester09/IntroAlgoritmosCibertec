package ejercicios;

import javax.swing.*;  
import javax.swing.table.DefaultTableModel;  
import java.awt.*;  
import java.awt.event.*;  

public class Intento3 extends JFrame {
    // Componentes gráficos globales
    private static JTabbedPane pestanas;
    private static JPanel panelJugadores, panelEstadisticas, panelConsultas;

    // Arrays para almacenar datos de jugadores
    private static int[] numerosCamiseta = new int[25];
    private static String[] nombres = new String[25];
    private static String[] posiciones = new String[25];

    // Arrays para contadores
    private static int[] partidosJugados = new int[25];
    private static int[] tries = new int[25];
    private static int[] conversiones = new int[25];
    private static int[] penales = new int[25];
    private static int[] dropGoals = new int[25];
    private static int[] faltas = new int[25];

    // Arrays para acumuladores
    private static int[] minutosJugados = new int[25];

    // Contadores globales
    private static int cantidadJugadores = 0;
    private static int totalPartidos = 0;

    // ComboBox para jugadores y comboBox para partidos
    private static JComboBox<String> comboJugadoresEstadisticas;
    private static JComboBox<String> comboPartidos;

    public Intento3() {
        setTitle("Estadísticas del club Los Halcones");  
        setSize(800, 600);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLocationRelativeTo(null);  

        inicializarComponentes();  

        setVisible(true);  
    }

    private void inicializarComponentes() {
        pestanas = new JTabbedPane();  

        panelJugadores = crearPanelJugadores();  
        panelEstadisticas = crearPanelEstadisticas();  
        panelConsultas = crearPanelConsultas();  

        pestanas.addTab("Jugadores", panelJugadores);
        pestanas.addTab("Estadísticas", panelEstadisticas);
        pestanas.addTab("Consultas", panelConsultas);

        add(pestanas);
    }

    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new BorderLayout());  

        JPanel formulario = new JPanel(new GridLayout(4, 2));
        JTextField txtNumeroCamiseta = new JTextField();  
        JTextField txtNombre = new JTextField();  
        JComboBox<String> comboPosicion = new JComboBox<>(new String[]{"Pilar", "Hooker", "Segunda línea", "Flanker", "Número 8", "Medio scrum", "Apertura", "Centro", "Wing", "Fullback"});  

        formulario.add(new JLabel("Número de camiseta:"));
        formulario.add(txtNumeroCamiseta);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Posición:"));
        formulario.add(comboPosicion);

        JButton btnAgregar = new JButton("Agregar jugador");
        formulario.add(btnAgregar);  

        DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"Camiseta", "Nombre", "Posición"}, 0);  
        JTable tablaJugadores = new JTable(modeloTabla);  
        panel.add(formulario, BorderLayout.NORTH);  
        panel.add(new JScrollPane(tablaJugadores), BorderLayout.CENTER);  

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numero = Integer.parseInt(txtNumeroCamiseta.getText());  
                String nombre = txtNombre.getText();  
                String posicion = (String) comboPosicion.getSelectedItem();  

                if (buscarJugador(numero) != -1) {
                    JOptionPane.showMessageDialog(null, "Número de camiseta ya registrado");
                    return;
                }

                agregarJugador(numero, nombre, posicion);
                modeloTabla.addRow(new Object[]{numero, nombre, posicion});
                comboJugadoresEstadisticas.addItem(numero + " - " + nombre);  // Actualizar el combo box de estadísticas
            }
        });

        return panel;  
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());  

        JPanel formulario = new JPanel(new GridLayout(10, 2));  
        comboJugadoresEstadisticas = new JComboBox<>();  
        JTextField txtMinutos = new JTextField();  
        JTextField txtTries = new JTextField();  
        JTextField txtConversiones = new JTextField();  
        JTextField txtPenales = new JTextField();  
        JTextField txtDropGoals = new JTextField();  
        JTextField txtFaltas = new JTextField();  

        formulario.add(new JLabel("Jugador:"));
        formulario.add(comboJugadoresEstadisticas);
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

        JButton btnRegistrarPartido = new JButton("Registrar Partido");
        formulario.add(btnRegistrarPartido);

        JButton btnRegistrar = new JButton("Registrar estadísticas");
        formulario.add(btnRegistrar);  // Mover el botón de registrar estadísticas más abajo

        // Label y combo box para el número de partido
        JLabel lblPartido = new JLabel("Partido:");
        comboPartidos = new JComboBox<>();
        formulario.add(lblPartido);
        formulario.add(comboPartidos);

        panel.add(formulario, BorderLayout.NORTH);  

        btnRegistrarPartido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                totalPartidos++;  
                comboPartidos.addItem(String.valueOf(totalPartidos));  // Agregar el número de partido al combo box
                JOptionPane.showMessageDialog(null, "Partido registrado. Ahora es el partido #" + totalPartidos);
            }
        });

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboJugadoresEstadisticas.getSelectedIndex();
                if (index < 0) return;  

                int minutos = Integer.parseInt(txtMinutos.getText());
                int triesCount = Integer.parseInt(txtTries.getText());
                int conversionesCount = Integer.parseInt(txtConversiones.getText());
                int penalesCount = Integer.parseInt(txtPenales.getText());
                int dropGoalsCount = Integer.parseInt(txtDropGoals.getText());
                int faltasCount = Integer.parseInt(txtFaltas.getText());

                if (minutos < 0 || triesCount < 0 || conversionesCount < 0 || penalesCount < 0 || dropGoalsCount < 0 || faltasCount < 0) {
                    JOptionPane.showMessageDialog(null, "Todos los valores deben ser positivos");
                    return;
                }

                registrarEstadisticas(index, minutos, triesCount, conversionesCount, penalesCount, dropGoalsCount, faltasCount);
                totalPartidos++;  
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

        JTextArea txtAreaResultados = new JTextArea();
        panel.add(controles, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaResultados), BorderLayout.CENTER);  
        panel.add(btnGlobal, BorderLayout.SOUTH);  

        btnVerEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int num = Integer.parseInt(txtBuscar.getText());  
                mostrarEstadisticasJugador(num, txtAreaResultados);  
            }
        });

        btnRanking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String texto = "";
                ordenarJugadoresPorPuntos();
                for (int k = 0; k < cantidadJugadores; k++) {
                    texto += nombres[k] + ": " + calcularPuntosTotales(k) + " puntos\n";
                }
                txtAreaResultados.setText(texto);  
            }
        });

        btnGlobal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarEstadisticasGlobales(txtAreaResultados);
            }
        });

        return panel;  
    }

    private void agregarJugador(int numeroCamiseta, String nombre, String posicion) {
        if (cantidadJugadores < numerosCamiseta.length) {
            numerosCamiseta[cantidadJugadores] = numeroCamiseta;
            nombres[cantidadJugadores] = nombre;
            posiciones[cantidadJugadores] = posicion;
            cantidadJugadores++;
        }
    }

    private void registrarEstadisticas(int index, int minutos, int triesCount, int conversionesCount, int penalesCount, int dropGoalsCount, int faltasCount) {
        minutosJugados[index] += minutos;
        tries[index] += triesCount;
        conversiones[index] += conversionesCount;
        penales[index] += penalesCount;
        dropGoals[index] += dropGoalsCount;
        faltas[index] += faltasCount;
        partidosJugados[index]++;  
    }

    private void mostrarEstadisticasJugador(int numeroCamiseta, JTextArea txtAreaResultados) {
        int index = buscarJugador(numeroCamiseta);
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Jugador no encontrado");
            return;
        }

        String resultado = "Estadísticas de " + nombres[index] + " (Camiseta #" + numerosCamiseta[index] + "):\n";
        resultado += "Minutos jugados: " + minutosJugados[index] + "\n";
        resultado += "Tries: " + tries[index] + "\n";
        resultado += "Conversiones: " + conversiones[index] + "\n";
        resultado += "Penales: " + penales[index] + "\n";
        resultado += "Drop Goals: " + dropGoals[index] + "\n";
        resultado += "Faltas: " + faltas[index] + "\n";

        txtAreaResultados.setText(resultado);  
    }

    private int buscarJugador(int numeroCamiseta) {
        for (int i = 0; i < cantidadJugadores; i++) {
            if (numerosCamiseta[i] == numeroCamiseta) {
                return i;  
            }
        }
        return -1;  
    }

    private int calcularPuntosTotales(int index) {
        return tries[index] * 5 + conversiones[index] * 2 + penales[index] * 3 + dropGoals[index] * 3;  
    }

    private void ordenarJugadoresPorPuntos() {
        for (int i = 0; i < cantidadJugadores - 1; i++) {
            for (int j = i + 1; j < cantidadJugadores; j++) {
                if (calcularPuntosTotales(j) > calcularPuntosTotales(i)) {
                    // Intercambiar datos
                    int tempNum = numerosCamiseta[i];
                    String tempNom = nombres[i];
                    String tempPos = posiciones[i];
                    int tempTries = tries[i];
                    int tempConv = conversiones[i];
                    int tempPen = penales[i];
                    int tempDrop = dropGoals[i];
                    int tempFaltas = faltas[i];
                    int tempMin = minutosJugados[i];

                    numerosCamiseta[i] = numerosCamiseta[j];
                    nombres[i] = nombres[j];
                    posiciones[i] = posiciones[j];
                    tries[i] = tries[j];
                    conversiones[i] = conversiones[j];
                    penales[i] = penales[j];
                    dropGoals[i] = dropGoals[j];
                    faltas[i] = faltas[j];
                    minutosJugados[i] = minutosJugados[j];

                    numerosCamiseta[j] = tempNum;
                    nombres[j] = tempNom;
                    posiciones[j] = tempPos;
                    tries[j] = tempTries;
                    conversiones[j] = tempConv;
                    penales[j] = tempPen;
                    dropGoals[j] = tempDrop;
                    faltas[j] = tempFaltas;
                    minutosJugados[j] = tempMin;
                }
            }
        }
    }

    private void mostrarEstadisticasGlobales(JTextArea txtAreaResultados) {
        String texto = "Estadísticas globales del equipo:\n";
        int totalPuntos = 0;
        int totalFaltas = 0;
        int totalMinutos = 0;

        for (int j = 0; j < cantidadJugadores; j++) {
            totalPuntos += calcularPuntosTotales(j);
            totalMinutos += minutosJugados[j];
            totalFaltas += faltas[j];
        }

        texto += "Total puntos equipo: " + totalPuntos + "\n";
        texto += "Minutos totales: " + totalMinutos + "\n";
        texto += "Faltas totales: " + totalFaltas;

        txtAreaResultados.setText(texto);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Intento3();  
            }
        });
    }
}
