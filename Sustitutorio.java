package Ejercicios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sustitutorio extends JFrame {
    // Componentes de la interfaz gráfica
    private JLabel lblTitulo;
    private JLabel lblAccesorio;
    private JLabel lblCantidad;
    private JLabel lblResultado;

    private JComboBox<String> cmbAccesorio;
    private JTextField txtCantidad;
    private JButton btnProcesar;
    private JButton btnLimpiar;
    private JTextArea txtResultado;

    // Variables globales acumuladoras
   double imptot0 = 0, imptot1 = 0, imptot2 = 0, imptot3 = 0;
   int canTotal0 = 0, canTotal1 = 0, canTotal2 = 0, canTotal3 = 0;
   double totalPagar0 = 0, totalPagar1 = 0, totalPagar2 = 0, totalPagar3 = 0;
   double impPagar = 0;
   String obsequio = "";
    

    public Sustitutorio() {
        setTitle("Artículos deportivos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        inicializarComponentes();
        agregarEventos();
        setVisible(true);
    }

    private void inicializarComponentes() {
        lblTitulo = new JLabel("Artículos deportivos Manuel", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(100, 20, 400, 30);
        add(lblTitulo);

        lblAccesorio = new JLabel("Tipo de accesorio:");
        lblAccesorio.setBounds(50, 70, 150, 25);
        add(lblAccesorio);

        lblCantidad = new JLabel("Cantidad de accesorios:");
        lblCantidad.setBounds(50, 110, 150, 25);
        add(lblCantidad);

        lblResultado = new JLabel("Resultados:");
        lblResultado.setBounds(50, 190, 150, 25);
        add(lblResultado);

        cmbAccesorio = new JComboBox<>();
        cmbAccesorio.addItem("Disco pilates - S/65.50");
        cmbAccesorio.addItem("Mancuernas - S/54.50");
        cmbAccesorio.addItem("Bandas elásticas - S/39.50");
        cmbAccesorio.addItem("Step - S/76.50");
        cmbAccesorio.setBounds(200, 70, 200, 25);
        add(cmbAccesorio);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(200, 110, 200, 25);
        add(txtCantidad);

        btnProcesar = new JButton("Calcular");
        btnProcesar.setBounds(150, 150, 100, 30);
        add(btnProcesar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(280, 150, 100, 30);
        add(btnLimpiar);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResultado);
        scrollPane.setBounds(50, 220, 500, 200);
        add(scrollPane);
    }

    private void agregarEventos() {
        btnProcesar.addActionListener(e -> calcular());

        btnLimpiar.addActionListener(e -> limpiar());

        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    private double obtenerPrecioUnitario(int tipo) {
        switch (tipo) {
            case 1: return 65.5;
            case 2: return 54.5;
            case 3: return 39.5;
            case 4: return 76.5;
            default: return 0;
        }
    }

    private double calcularImporteCompra(int cantidad, double precio) {
        return cantidad * precio;
    }

    private double calcularDescuento(double importe, int cantidad) {
        double porcentaje;
        if (cantidad < 3) porcentaje = 0;
        else if (cantidad < 7 && cantidad >=3) porcentaje = 5.8;
        else if (cantidad < 10 && cantidad >=7 ) porcentaje = 7.5;
        else porcentaje = 11.7;
        return importe * porcentaje / 100;
    }

    private String obsequio (int tipo) {
        if (tipo == 1) return "soga para saltar";
        else if (tipo == 3) return "cono con orificio";
        else return "varilla de entrenamiento";
    }

    // Método para actualizar acumuladores
    private void efectuarIncrementos(int tipo, int can, double ip) {
        switch (tipo) {
            case 0:
                imptot0 += ip;
                canTotal0 += can;
                totalPagar0++;
                break;
            case 1:
                imptot1 += ip;
                canTotal1 += can;
                totalPagar1++;
                break;
            case 2:
                imptot2 += ip;
                canTotal2 += can;
                totalPagar2++;
                break;
            default:
                imptot3 += ip;
                canTotal3 += can;
                totalPagar3++;
                break;
        }
    }

    private void calcular() {
        try {
            int tipo = cmbAccesorio.getSelectedIndex();
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio = obtenerPrecioUnitario(tipo + 1); // Incrementamos para coincidir con el índice de la marca
            double importeCompra = calcularImporteCompra(cantidad, precio);
            double importeDescuento = calcularDescuento(importeCompra, cantidad);
            double importePagar = importeCompra - importeDescuento;
            String obsequio = obsequio(tipo);
            

            // Llamamos a la función de actualización de acumuladores
            efectuarIncrementos(tipo, cantidad, importePagar);

            // Mostrar resultados
            String resultado = "RESULTADO DE LA COMPRA\n";
            resultado += "=======================\n\n";
            resultado += "Tipo de accesorio: " + cmbAccesorio.getSelectedItem().toString() + "\n";
            resultado += "Importe a pagar por compra: S/. " + String.format("%.2f", importePagar) + "\n";
            resultado += "Obsequio: " + obsequio + "\n\n";

            resultado += "--- ACUMULADO  ---\n";
            resultado += "Cantidad de accesorios e importe de pago acumulado:\n";
            resultado += "  Disco pilates: " + canTotal0 + " artículo(s) - S/. " + String.format("%.2f", imptot0) + "\n";
            resultado += "  Mancuernas: " + canTotal1 + " artículo(s) - S/. " + String.format("%.2f", imptot1) + "\n";
            resultado += "  Bandas elásticas: " + canTotal2 + " artículo(s) - S/. " + String.format("%.2f", imptot2) + "\n";
            resultado += "  Step: " + canTotal3 + " artículo(s) - S/. " + String.format("%.2f", imptot3) + "\n";

            txtResultado.setText(resultado);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        cmbAccesorio.setSelectedIndex(0);
        txtCantidad.setText("");
        txtResultado.setText("");
        txtCantidad.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Sustitutorio());
    }
}
