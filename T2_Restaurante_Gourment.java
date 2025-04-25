package clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Restaurante_Gourmet extends JFrame {
    // Componentes de la interfaz gráfica
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblCantidad;
    private JLabel lblResultado;

    private JComboBox<String> cmbCodigo;
    private JTextField txtCantidad;
    private JButton btnCalcular;
    private JButton btnLimpiar;
    private JTextArea txtResultado;

    public Restaurante_Gourmet() {
        // Configuración básica de la ventana
        setTitle("Restaurante Gourmet");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Usamos posicionamiento absoluto para simplificar

        // Inicialización y posicionamiento de componentes
        inicializarComponentes();

        // Agregar eventos
        agregarEventos();

        // Mostrar la ventana
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Título
        lblTitulo = new JLabel("Restaurante Gourmet", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(100, 20, 400, 30);
        add(lblTitulo);

        // Etiquetas
        lblCodigo = new JLabel("Tipo de combo:");
        lblCodigo.setBounds(50, 70, 150, 25);
        add(lblCodigo);

        lblCantidad = new JLabel("Cantidad de combos:");
        lblCantidad.setBounds(50, 110, 150, 25);
        add(lblCantidad);

        lblResultado = new JLabel("Resultados:");
        lblResultado.setBounds(50, 190, 150, 25);
        add(lblResultado);

        // Campos de entrada
        cmbCodigo = new JComboBox<>();
        cmbCodigo.addItem("Combo Ejecutivo - S/39.90");
        cmbCodigo.addItem("Combo Vegetariano - S/34.50");
        cmbCodigo.addItem("Combo Premium - S/49.00");
        cmbCodigo.addItem("Combo Clásico - S/. 29.90");
        cmbCodigo.setBounds(200, 70, 200, 25);
        cmbCodigo.setSelectedIndex(-1); // 👈 No seleccionar nada al inicio
        add(cmbCodigo);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(200, 110, 200, 25);
        add(txtCantidad);

        // Botones
        btnCalcular = new JButton("Calcular");
        btnCalcular.setBounds(150, 150, 100, 30);
        add(btnCalcular);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(280, 150, 100, 30);
        add(btnLimpiar);

        // Área de resultados
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResultado);
        scrollPane.setBounds(50, 220, 500, 120);
        add(scrollPane);
    }

    private void agregarEventos() {
        // Evento para el botón Calcular
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcular();
            }
        });

        // Evento para el botón Limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });

        // Validación para aceptar solo números en el campo cantidad
        txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignora caracteres que no sean dígitos
                }
            }
        });
    }

    private void calcular() {
        try {
            int tipoCombo = cmbCodigo.getSelectedIndex();

            // Validar selección del combo
            if (tipoCombo == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un tipo de chocolate",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la cantidad
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Variables para los cálculos
            double precioUnitario = 0;
            double importeCompra, porcentajeDescuento, importeDescuento, importePagar;
            int obsequio;

            // Determinar el precio según el tipo de combo
            switch (tipoCombo) {
                case 0: precioUnitario = 39.90; break;
                case 1: precioUnitario = 34.50; break;
                case 2: precioUnitario = 49; break;
                case 3: precioUnitario = 29.90; break;
            }

            importeCompra = cantidad * precioUnitario;

            if (cantidad < 3) {
                porcentajeDescuento = 0;
            } else if (cantidad >= 3 && cantidad < 6) {
                porcentajeDescuento = 5;
            } else if (cantidad >= 6 && cantidad < 10) {
                porcentajeDescuento = 10;
            } else {
                porcentajeDescuento = 15;
            }

            importeDescuento = importeCompra * porcentajeDescuento / 100;
            importePagar = importeCompra - importeDescuento;

            if (tipoCombo == 0) {
                obsequio = cantidad;
            } else if (tipoCombo == 1) {
            	obsequio = cantidad*2;
            } else if (tipoCombo == 2) {
            	obsequio = cantidad;            	
            }            
            else {
                obsequio = cantidad/2;
            }

            // Redondear a dos decimales
            importeCompra = Math.round(importeCompra * 100.0) / 100.0;
            importeDescuento = Math.round(importeDescuento * 100.0) / 100.0;
            importePagar = Math.round(importePagar * 100.0) / 100.0;

            // Mostrar los resultados
            String resultado = "RESULTADO DE LA COMPRA\n";
            resultado += "=======================\n\n";
            resultado += "Importe de Compra:     S/. " + importeCompra + "\n";
            resultado += "Importe del Descuento: S/. " + importeDescuento + "\n";
            resultado += "Importe a Pagar:       S/. " + importePagar + "\n";
            resultado += "Bebidas de regalo: " + obsequio;

            txtResultado.setText(resultado);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una cantidad válida",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        cmbCodigo.setSelectedIndex(-1); // Vuelve a dejar vacío el combo
        txtCantidad.setText("");
        txtResultado.setText("");
        txtCantidad.requestFocus();
    }

    public static void main(String[] args) {
        // Crear y mostrar la aplicación
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Restaurante_Gourmet();
            }
        });
    }
}
