package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SistemaVentasGUI extends JFrame {

    class Producto {
        String codigo;
        String nombre;
        int cantidad;
        int minimo;

        public Producto(String codigo, String nombre, int cantidad, int minimo) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.minimo = minimo;
        }

        public boolean necesitaReabastecimiento() {
            return cantidad <= minimo;
        }

        public void vender(int cantidadVendida) {
            this.cantidad -= cantidadVendida;
        }

        @Override
        public String toString() {
            return nombre + " (" + cantidad + ")";
        }
    }

    private ArrayList<Producto> inventario = new ArrayList<>();
    private PriorityQueue<Producto> colaReabastecimiento = new PriorityQueue<>(
            Comparator.comparingInt(p -> p.cantidad - p.minimo)
    );

    private JTextField txtCodigo, txtNombre, txtCantidad, txtMinimo, txtBuscarVenta;
    private JTextArea areaCola;

    public SistemaVentasGUI() {
        setTitle("Sistema de Ventas y Reabastecimiento");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Colores y fuente general
        Color fondo = new Color(245, 245, 245);
        Color panelColor = new Color(220, 230, 241);
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
        getContentPane().setBackground(fondo);

        // Panel superior - Ingreso de productos
        JPanel panelArriba = new JPanel(new GridLayout(5, 2, 10, 10));
        panelArriba.setBorder(BorderFactory.createTitledBorder("Agregar Producto"));
        panelArriba.setBackground(panelColor);

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtCantidad = new JTextField();
        txtMinimo = new JTextField();
        JButton btnAgregar = new JButton("Agregar Producto");

        panelArriba.add(new JLabel("Código:"));
        panelArriba.add(txtCodigo);
        panelArriba.add(new JLabel("Nombre:"));
        panelArriba.add(txtNombre);
        panelArriba.add(new JLabel("Cantidad:"));
        panelArriba.add(txtCantidad);
        panelArriba.add(new JLabel("Mínimo:"));
        panelArriba.add(txtMinimo);
        panelArriba.add(new JLabel(""));
        panelArriba.add(btnAgregar);

        add(panelArriba, BorderLayout.NORTH);

        // Panel centro - Ventas y acciones
        JPanel panelCentro = new JPanel(new GridLayout(3, 1, 5, 5));
        panelCentro.setBorder(BorderFactory.createTitledBorder("Ventas"));
        panelCentro.setBackground(fondo);

        txtBuscarVenta = new JTextField();
        JButton btnVender = new JButton("Registrar Venta");
        JButton btnVerCola = new JButton("Ver Productos a Reabastecer");

        panelCentro.add(txtBuscarVenta);
        panelCentro.add(btnVender);
        panelCentro.add(btnVerCola);

        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior - Cola
        areaCola = new JTextArea(10, 30);
        areaCola.setEditable(false);
        areaCola.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaCola);
        scroll.setBorder(BorderFactory.createTitledBorder("Cola de Reabastecimiento"));
        add(scroll, BorderLayout.SOUTH);

        // Acción botón Agregar
        btnAgregar.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText();
                String nombre = txtNombre.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                int minimo = Integer.parseInt(txtMinimo.getText());

                Producto p = new Producto(codigo, nombre, cantidad, minimo);
                inventario.add(p);

                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.");

                txtCodigo.setText("");
                txtNombre.setText("");
                txtCantidad.setText("");
                txtMinimo.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese valores válidos.");
            }
        });

        // Acción botón Venta
        btnVender.addActionListener(e -> {
            String codigo = txtBuscarVenta.getText();
            Producto p = buscarProducto(codigo);
            if (p != null) {
                p.vender(1);
                if (p.necesitaReabastecimiento()) {
                    colaReabastecimiento.add(p);
                }
                JOptionPane.showMessageDialog(this, "Venta registrada.");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        });

        // Acción botón Ver Cola
        btnVerCola.addActionListener(e -> {
            areaCola.setText("Productos en cola de reabastecimiento:\n");
            for (Producto p : colaReabastecimiento) {
                areaCola.append(p.nombre + " (Stock: " + p.cantidad + ")\n");
            }
        });
    }

    private Producto buscarProducto(String codigo) {
        for (Producto p : inventario) {
            if (p.codigo.equals(codigo)) return p;
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaVentasGUI().setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 563, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
