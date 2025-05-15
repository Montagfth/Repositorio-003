/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author Fabrizio
 */
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

    // Lista de productos
    private ArrayList<Producto> inventario = new ArrayList<>();
    private PriorityQueue<Producto> colaReabastecimiento = new PriorityQueue<>(
        Comparator.comparingInt(p -> p.cantidad - p.minimo)
    );

    // Componentes de la GUI
    private JTextField txtCodigo, txtNombre, txtCantidad, txtMinimo, txtBuscarVenta;
    private JTextArea areaCola;

    public SistemaVentasGUI() {
        setTitle("Sistema de Ventas y Reabastecimiento");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Panel de ingreso de producto
        JPanel panelArriba = new JPanel(new GridLayout(5, 2));
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

        // Panel de ventas
        JPanel panelCentro = new JPanel(new GridLayout(3, 1));
        txtBuscarVenta = new JTextField();
        JButton btnVender = new JButton("Registrar Venta");
        JButton btnVerCola = new JButton("Ver Cola de Reabastecimiento");

        panelCentro.add(txtBuscarVenta);
        panelCentro.add(btnVender);
        panelCentro.add(btnVerCola);

        add(panelCentro, BorderLayout.CENTER);

        // Área para mostrar cola
        areaCola = new JTextArea();
        areaCola.setEditable(false);
        add(new JScrollPane(areaCola), BorderLayout.SOUTH);

        // ACCIONES

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
}
