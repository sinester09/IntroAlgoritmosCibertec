package tareasalgo;

import javax.swing.*;  // Importación de las clases necesarias para la interfaz gráfica de usuario (JFrame, JButton, etc.)
import javax.swing.table.DefaultTableModel;  // Importación para poder manejar tablas con modelo por defecto
import java.awt.*;  // Importación para trabajar con componentes gráficos
import java.awt.event.*;  // Importación para poder manejar eventos, como clics de botones

// Clase principal que extiende JFrame para crear la interfaz gráfica
public class Intento extends JFrame {
    // Declaración de componentes de la interfaz gráfica
    static JTabbedPane pestañas;  // Pestañas para cambiar entre secciones
    static JTextField txtNumeroCamiseta, txtNombre;  // Campos para ingresar datos del jugador
    static JComboBox<String> comboPosicion, comboJugadores;  // Combo para seleccionar la posición y el jugador
    static JTextField txtMinutos, txtTries, txtConversiones, txtPenales, txtDropGoals, txtFaltas;  // Campos para ingresar estadísticas de un partido
    static JTable tablaJugadores;  // Tabla para mostrar los jugadores
    static DefaultTableModel modeloTabla;  // Modelo de la tabla de jugadores
    static JTextArea txtAreaResultados;  // Área de texto para mostrar resultados de consultas

    // Datos de jugadores y otras variables
    static final int MAX_JUGADORES = 100;  // Máximo número de jugadores en el sistema
    static int totalJugadores = 0;  // Contador para el número de jugadores registrados
    static Jugador[] jugadores = new Jugador[MAX_JUGADORES];  // Arreglo de jugadores
    static int partidoActual = 1;  // Contador de partidos registrados

    // Constructor que inicializa la ventana principal y los componentes
    public Intento() {
        setTitle("Sistema de Estadísticas de Rugby");  // Título de la ventana
        setSize(800, 600);  // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cierra la aplicación al cerrar la ventana
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla

        inicializarComponentes();  // Llama al método que crea los componentes y los agrega a la ventana

        setVisible(true);  // Hace visible la ventana
    }

    // Método que inicializa los componentes y los agrega a las pestañas
    private void inicializarComponentes() {
        pestañas = new JTabbedPane();  // Crea el componente JTabbedPane para manejar las pestañas

        JPanel panelJugadores = crearPanelJugadores();  // Crea el panel para ingresar jugadores
        JPanel panelEstadisticas = crearPanelEstadisticas();  // Crea el panel para registrar estadísticas
        JPanel panelConsultas = crearPanelConsultas();  // Crea el panel para hacer consultas

        // Agrega las pestañas a la interfaz, cada una con su respectivo panel
        pestañas.addTab("Jugadores", panelJugadores);
        pestañas.addTab("Estadísticas", panelEstadisticas);
        pestañas.addTab("Consultas", panelConsultas);

        // Agrega el componente JTabbedPane a la ventana principal
        add(pestañas);
    }

    // Método que crea el panel de ingreso de jugadores
    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new BorderLayout());  // Panel principal con layout de BorderLayout

        // Panel para ingresar los datos del jugador
        JPanel formulario = new JPanel(new GridLayout(4, 2));
        txtNumeroCamiseta = new JTextField();  // Campo de texto para el número de camiseta
        txtNombre = new JTextField();  // Campo de texto para el nombre
        comboPosicion = new JComboBox<>(new String[]{"Pilar", "Hooker", "Segunda línea", "Flanker", "Número 8", "Medio scrum", "Apertura", "Centro", "Wing", "Fullback"});  // Combo para seleccionar la posición

        // Añade los elementos al formulario
        formulario.add(new JLabel("Número de camiseta:"));
        formulario.add(txtNumeroCamiseta);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Posición:"));
        formulario.add(comboPosicion);

        // Botón para agregar un jugador
        JButton btnAgregar = new JButton("Agregar jugador");
        formulario.add(btnAgregar);  // Agrega el botón al formulario

        panel.add(formulario, BorderLayout.NORTH);  // Añade el formulario al panel en la parte superior

        // Modelo de tabla para mostrar los jugadores
        modeloTabla = new DefaultTableModel(new String[]{"Camiseta", "Nombre", "Posición"}, 0);  // Define las columnas de la tabla
        tablaJugadores = new JTable(modeloTabla);  // Crea la tabla con el modelo definido
        panel.add(new JScrollPane(tablaJugadores), BorderLayout.CENTER);  // Añade la tabla al panel en el centro, dentro de un JScrollPane

        // Acción del botón para agregar un jugador
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtiene los valores de los campos de texto y combo
                int numero = Integer.parseInt(txtNumeroCamiseta.getText());  // Número de camiseta
                String nombre = txtNombre.getText();  // Nombre del jugador
                String posicion = (String) comboPosicion.getSelectedItem();  // Posición del jugador

                // Verifica si el número de camiseta ya está registrado
                if (buscarJugador(numero) != -1) {
                    JOptionPane.showMessageDialog(null, "Número de camiseta ya registrado");
                    return;
                }

                // Agrega el jugador al arreglo y a la tabla
                agregarJugador(numero, nombre, posicion);
                modeloTabla.addRow(new Object[]{numero, nombre, posicion});
                comboJugadores.addItem(numero + " - " + nombre);  // Añade el jugador al combo de selección
            }
        });

        return panel;  // Retorna el panel creado
    }

    // Método que crea el panel para registrar las estadísticas de los jugadores
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());  // Panel principal

        // Panel para ingresar las estadísticas
        JPanel formulario = new JPanel(new GridLayout(9, 2));
        comboJugadores = new JComboBox<>();  // Combo para seleccionar al jugador
        txtMinutos = new JTextField();  // Campo para los minutos jugados
        txtTries = new JTextField();  // Campo para los tries
        txtConversiones = new JTextField();  // Campo para las conversiones
        txtPenales = new JTextField();  // Campo para los penales
        txtDropGoals = new JTextField();  // Campo para los drop goals
        txtFaltas = new JTextField();  // Campo para las faltas

        // Añade los campos al formulario
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

        // Botón para registrar las estadísticas
        JButton btnRegistrar = new JButton("Registrar estadísticas");
        formulario.add(new JLabel("Partido #" + partidoActual));
        formulario.add(btnRegistrar);

        panel.add(formulario, BorderLayout.NORTH);  // Añade el formulario al panel

        // Acción del botón para registrar las estadísticas
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtiene el índice del jugador seleccionado
                int index = comboJugadores.getSelectedIndex();
                if (index < 0) return;  // Si no hay jugador seleccionado, retorna

                Jugador jugador = jugadores[index];  // Obtiene el jugador

                // Obtiene las estadísticas ingresadas
                int minutos = Integer.parseInt(txtMinutos.getText());
                int tries = Integer.parseInt(txtTries.getText());
                int conv = Integer.parseInt(txtConversiones.getText());
                int pen = Integer.parseInt(txtPenales.getText());
                int drop = Integer.parseInt(txtDropGoals.getText());
                int faltas = Integer.parseInt(txtFaltas.getText());

                // Verifica si los valores ingresados son válidos
                if (minutos < 0 || tries < 0 || conv < 0 || pen < 0 || drop < 0 || faltas < 0) {
                    JOptionPane.showMessageDialog(null, "Todos los valores deben ser positivos");
                    return;
                }

                // Registra las estadísticas del jugador
                registrarEstadisticas(jugador.numeroCamiseta, minutos, tries, conv, pen, drop, faltas);
                partidoActual++;  // Incrementa el número del partido
            }
        });

        return panel;  // Retorna el panel creado
    }

    // Método que crea el panel para mostrar las consultas
    private JPanel crearPanelConsultas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel para los controles de búsqueda y consulta
        JPanel controles = new JPanel(new GridLayout(2, 2));
        JTextField txtBuscar = new JTextField();  // Campo para ingresar el número de camiseta a buscar
        JButton btnVerEstadisticas = new JButton("Ver estadísticas jugador");  // Botón para ver estadísticas de un jugador
        JButton btnRanking = new JButton("Ranking por puntos");  // Botón para ver el ranking de los jugadores
        JButton btnGlobal = new JButton("Estadísticas globales");  // Botón para ver las estadísticas globales

        // Añade los controles al panel
        controles.add(new JLabel("N° Camiseta:"));
        controles.add(txtBuscar);
        controles.add(btnVerEstadisticas);
        controles.add(btnRanking);

        // Área de texto para mostrar los resultados de las consultas
        txtAreaResultados = new JTextArea();
        panel.add(controles, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtAreaResultados), BorderLayout.CENTER);  // Añade el área de resultados al centro
        panel.add(btnGlobal, BorderLayout.SOUTH);  // Añade el botón de estadísticas globales al sur

        // Acción del botón para ver las estadísticas del jugador
        btnVerEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int num = Integer.parseInt(txtBuscar.getText());  // Obtiene el número de camiseta
                mostrarEstadisticasJugador(num);  // Muestra las estadísticas del jugador
            }
        });

        // Acción del botón para ver el ranking por puntos
        btnRanking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ordena a los jugadores por puntos de manera manual (sin usar 'for')
                String texto = "";
                int i = 0;
                while (i < totalJugadores - 1) {
                    int j = i + 1;
                    while (j < totalJugadores) {
                        if (jugadores[j].puntos > jugadores[i].puntos) {
                            Jugador temp = jugadores[i];
                            jugadores[i] = jugadores[j];
                            jugadores[j] = temp;
                        }
                        j++;
                    }
                    i++;
                }

                int k = 0;
                while (k < totalJugadores) {
                    texto += jugadores[k].nombre + ": " + jugadores[k].puntos + " puntos\n";
                    k++;
                }

                txtAreaResultados.setText(texto);  // Muestra el ranking en el área de texto
            }
        });

        // Acción del botón para mostrar las estadísticas globales del equipo
        btnGlobal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para encontrar al jugador más efectivo en cada categoría sin usar 'for'
                Jugador mejorTries = null, mejorConversiones = null, mejorPenales = null, mejorDropGoals = null;
                int maxTries = 0, maxConversiones = 0, maxPenales = 0, maxDropGoals = 0;

                int i = 0;
                while (i < totalJugadores) {
                    // Encuentra al jugador con más tries, conversiones, penales y drop goals
                    if (jugadores[i].tries > maxTries) {
                        mejorTries = jugadores[i];
                        maxTries = jugadores[i].tries;
                    }
                    if (jugadores[i].conversiones > maxConversiones) {
                        mejorConversiones = jugadores[i];
                        maxConversiones = jugadores[i].conversiones;
                    }
                    if (jugadores[i].penales > maxPenales) {
                        mejorPenales = jugadores[i];
                        maxPenales = jugadores[i].penales;
                    }
                    if (jugadores[i].dropGoals > maxDropGoals) {
                        mejorDropGoals = jugadores[i];
                        maxDropGoals = jugadores[i].dropGoals;
                    }
                    i++;  // Incrementamos el índice manualmente
                }

                // Construye el texto con los resultados
                String texto = "Jugador más efectivo por categoría:\n";
                texto += "Más Tries: " + (mejorTries != null ? mejorTries.nombre : "Ninguno") + " (" + maxTries + " tries)\n";
                texto += "Más Conversiones: " + (mejorConversiones != null ? mejorConversiones.nombre : "Ninguno") + " (" + maxConversiones + " conversiones)\n";
                texto += "Más Penales: " + (mejorPenales != null ? mejorPenales.nombre : "Ninguno") + " (" + maxPenales + " penales)\n";
                texto += "Más Drop Goals: " + (mejorDropGoals != null ? mejorDropGoals.nombre : "Ninguno") + " (" + maxDropGoals + " drop goals)\n";

                // Calcula las estadísticas globales del equipo
                int totalPuntos = 0;
                int totalMinutos = 0;
                int totalFaltas = 0;
                int j = 0;
                while (j < totalJugadores) {
                    totalPuntos += jugadores[j].puntos;
                    totalMinutos += jugadores[j].minutosJugados;
                    totalFaltas += jugadores[j].faltas;
                    j++;  // Incrementamos el índice manualmente
                }

                // Añade las estadísticas globales
                texto += "\nEstadísticas globales del equipo:\n";
                texto += "Total puntos equipo: " + totalPuntos + "\n";
                texto += "Minutos totales: " + totalMinutos + "\n";
                texto += "Faltas totales: " + totalFaltas;

                // Muestra el texto en el área de resultados
                txtAreaResultados.setText(texto);
            }
        });

        return panel;  // Retorna el panel creado
    }

    // ============================ LÓGICA ==============================

    // Método para agregar un jugador al arreglo
    public void agregarJugador(int numeroCamiseta, String nombre, String posicion) {
        if (totalJugadores < MAX_JUGADORES) {
            jugadores[totalJugadores] = new Jugador(numeroCamiseta, nombre, posicion);
            totalJugadores++;
        }
    }

    // Método para registrar las estadísticas de un jugador
    public void registrarEstadisticas(int numeroCamiseta, int minutos, int tries, int conversiones, int penales, int dropGoals, int faltas) {
        int index = buscarJugador(numeroCamiseta);
        if (index == -1) return;  // Si el jugador no se encuentra, retorna

        Jugador j = jugadores[index];
        j.minutosJugados += minutos;
        j.tries += tries;
        j.conversiones += conversiones;
        j.penales += penales;
        j.dropGoals += dropGoals;
        j.faltas += faltas;
        j.partidos++;  // Incrementa el número de partidos jugados
        j.puntos = calcularPuntosTotales(j.numeroCamiseta);  // Calcula los puntos del jugador
    }

    // Método para mostrar las estadísticas de un jugador específico
    public void mostrarEstadisticasJugador(int numeroCamiseta) {
        int index = buscarJugador(numeroCamiseta);
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Jugador no encontrado");
            return;
        }

        Jugador jugador = jugadores[index];
        String resultado = "Estadísticas de " + jugador.nombre + " (Camiseta #" + jugador.numeroCamiseta + "):\n";
        resultado += "Minutos jugados: " + jugador.minutosJugados + "\n";
        resultado += "Tries: " + jugador.tries + "\n";
        resultado += "Conversiones: " + jugador.conversiones + "\n";
        resultado += "Penales: " + jugador.penales + "\n";
        resultado += "Drop Goals: " + jugador.dropGoals + "\n";
        resultado += "Faltas: " + jugador.faltas + "\n";
        resultado += "Puntos: " + jugador.puntos + "\n";

        txtAreaResultados.setText(resultado);  // Muestra las estadísticas en el área de texto
    }

    // Método para buscar a un jugador por su número de camiseta
    public int buscarJugador(int numeroCamiseta) {
        int i = 0;
        while (i < totalJugadores) {
            if (jugadores[i].numeroCamiseta == numeroCamiseta) {
                return i;  // Retorna el índice del jugador si lo encuentra
            }
            i++;
        }
        return -1;  // Retorna -1 si no lo encuentra
    }

    // Método para calcular los puntos totales de un jugador
    public int calcularPuntosTotales(int numeroCamiseta) {
        int index = buscarJugador(numeroCamiseta);
        if (index == -1) return 0;  // Si el jugador no se encuentra, retorna 0

        Jugador jugador = jugadores[index];
        int puntos = jugador.tries * 5 + jugador.conversiones * 2 + jugador.penales * 3 + jugador.dropGoals * 3;  // Fórmula de puntos
        return puntos;  // Retorna el total de puntos
    }

    // Clase que representa a un jugador con sus datos
    public static class Jugador {
        int numeroCamiseta;
        String nombre;
        String posicion;
        int minutosJugados;
        int tries;
        int conversiones;
        int penales;
        int dropGoals;
        int faltas;
        int puntos;
        int partidos;

        // Constructor para crear un nuevo jugador
        public Jugador(int numeroCamiseta, String nombre, String posicion) {
            this.numeroCamiseta = numeroCamiseta;
            this.nombre = nombre;
            this.posicion = posicion;
            this.minutosJugados = 0;
            this.tries = 0;
            this.conversiones = 0;
            this.penales = 0;
            this.dropGoals = 0;
            this.faltas = 0;
            this.puntos = 0;
            this.partidos = 0;
        }
    }

    // Método main para iniciar el programa
    public static void main(String[] args) {
        // Ejecuta la interfaz gráfica en el hilo de eventos
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Intento();  // Crea una nueva instancia de la aplicación
            }
        });
    }
}
