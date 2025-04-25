package clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class Arroz extends JFrame {
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
 
   public Arroz() {
       // Configuración básica de la ventana
       setTitle("Arroz");
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
       lblTitulo = new JLabel("Arroz", SwingConstants.CENTER);
       lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
       lblTitulo.setBounds(100, 20, 400, 30);
       add(lblTitulo);
 
       // Etiquetas
       lblCodigo = new JLabel("Marca de arroz:");
       lblCodigo.setBounds(50, 70, 150, 25);
       add(lblCodigo);
 
       lblCantidad = new JLabel("Cantidad de bolsas:");
       lblCantidad.setBounds(50, 110, 150, 25);
       add(lblCantidad);
 
       lblResultado = new JLabel("Resultados:");
       lblResultado.setBounds(50, 190, 150, 25);
       add(lblResultado);
 
       // Campos de entrada
       cmbCodigo = new JComboBox<>();
       cmbCodigo.addItem("Costeño - S/14.50");
       cmbCodigo.addItem("Paisana del Norte - S/12.60");
       cmbCodigo.addItem("Tropical Superior - S/10.00");
       cmbCodigo.addItem("Norte Superior - S/12.80");
       cmbCodigo.setBounds(200, 70, 200, 25);
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
           // Obtener la marca del arroz
           int MarcaArroz = cmbCodigo.getSelectedIndex() + 1;
 
           // Obtener la cantidad de bolsas
           int cantidad = Integer.parseInt(txtCantidad.getText());
 
           if (cantidad <= 0) {
               JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero", 
                       "Error", JOptionPane.ERROR_MESSAGE);
               return;
           }
 
           // Variables para los cálculos
           double precioUnitario = 0;
           double importeCompra = 0;
           double porcentajeDescuento = 0;
           double importeDescuento = 0;
           double importePagar = 0;
           int Obsequio = 0;
           int Obsequio2 = 0;
 
           // Determinar el precio según el código usando switch
           switch (MarcaArroz) {
               case 1:
                   precioUnitario = 14.5;
                   break;
               case 2:
                   precioUnitario = 12.6;
                   break;
               case 3:
                   precioUnitario = 10;
                   break;
               case 4:
                   precioUnitario = 12.8;
                   break;
           }
 
           // Calcular el importe de la compra
           importeCompra = cantidad * precioUnitario;
 
           // Determinar el porcentaje de descuento usando if-else
           if (cantidad < 4) {
               porcentajeDescuento = 9;
           } else if (cantidad >= 4 && cantidad < 7) {
               porcentajeDescuento = 11;
           } else if (cantidad >= 7 && cantidad < 10) {
               porcentajeDescuento = 13;
           } else {
               porcentajeDescuento = 15;
           }
 
           // Calcular el importe del descuento
           importeDescuento = importeCompra * porcentajeDescuento / 100;
 
           // Calcular el importe a pagar
           importePagar = importeCompra - importeDescuento;
 
           
        // Determinar los caramelos de regalo
           if (MarcaArroz == 0) {
               Obsequio = 3;              
           } 
           else if (MarcaArroz == 1) {
        	   Obsequio = 2;
           }
           else if (MarcaArroz == 2) {
        	   Obsequio = 2*cantidad;
           }
           else {
               Obsequio = cantidad;
           }
            
                     
           
           // Determinar las gomitas de obsequio
           if (importePagar < 50) {
               Obsequio2 = 5;              
           } 
           else if (importePagar >= 50 && importePagar < 100) {
        	   Obsequio2 = 10;
           }
           else if (importePagar >= 100 && importePagar <150) {
        	   Obsequio2 = 15;
           }
           else {
               Obsequio2 = 20;
           }
 
           // Formatear números a dos decimales
           importeCompra = Math.round(importeCompra * 100.0) / 100.0;
           importeDescuento = Math.round(importeDescuento * 100.0) / 100.0;
           importePagar = Math.round(importePagar * 100.0) / 100.0;
 
           // Mostrar los resultados
           String resultado = "RESULTADO DE LA COMPRA\n";
           resultado += "=======================\n\n";
           resultado += "Marca de arroz:" + MarcaArroz + "\n";
           resultado += "Precio Unitario: S/. " + precioUnitario + "\n";
           resultado += "Cantidad: " + cantidad + " botellas\n\n";
           resultado += "Importe de Compra: S/. " + importeCompra + "\n";
           resultado += "Porcentaje de Descuento: " + porcentajeDescuento + "%\n";
           resultado += "Importe del Descuento: S/. " + importeDescuento + "\n";
           resultado += "Importe a Pagar: S/. " + importePagar + "\n\n";
           resultado += "Obsequios: Caramelos " + Obsequio + " Gomitas " + Obsequio2 + "\n";
 
           txtResultado.setText(resultado);
 
       } catch (NumberFormatException ex) {
           JOptionPane.showMessageDialog(this, "Por favor, ingrese una cantidad válida", 
                   "Error", JOptionPane.ERROR_MESSAGE);
       }
   }
 
   private void limpiar() {
       cmbCodigo.setSelectedIndex(0);
       txtCantidad.setText("");
       txtResultado.setText("");
       txtCantidad.requestFocus();
   }
 
   public static void main(String[] args) {
       // Crear y mostrar la aplicación
       SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               new Arroz();
           }
       });
   }
}
