package Vista;

import controlador.ColaReabastecimiento;
import controlador.Inventario;
import modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SistemaVentasGUI extends JFrame {

    private final Inventario inventario = new Inventario();
    private final ColaReabastecimiento cola = new ColaReabastecimiento();

    private JTextField txtCodigo, txtNombre, txtCantidad, txtMinimo, txtBuscarVenta;
    private JTextArea areaCola;

    public SistemaVentasGUI() {
        setTitle("Sistema de Ventas y Reabastecimiento");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Colores y fuente
        Color fondo = new Color(245, 245, 245);
        Color panelColor = new Color(220, 230, 241);
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
        getContentPane().setBackground(fondo);

        // Panel de ingreso
        JPanel panelArriba = crearPanelIngreso(panelColor, fuente);
        add(panelArriba, BorderLayout.NORTH);

        // Panel de acciones
        JPanel panelCentro = crearPanelVentas(fuente);
        add(panelCentro, BorderLayout.CENTER);

        // Panel de salida
        areaCola = new JTextArea(10, 30);
        areaCola.setEditable(false);
        areaCola.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaCola);
        scroll.setBorder(BorderFactory.createTitledBorder("Cola de Reabastecimiento"));
        add(scroll, BorderLayout.SOUTH);
    }

    private JPanel crearPanelIngreso(Color fondo, Font fuente) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Agregar Producto"));
        panel.setBackground(fondo);

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtCantidad = new JTextField();
        txtMinimo = new JTextField();
        JButton btnAgregar = new JButton("Agregar Producto");

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);
        panel.add(new JLabel("Mínimo:"));
        panel.add(txtMinimo);
        panel.add(new JLabel(""));
        panel.add(btnAgregar);

        btnAgregar.addActionListener(e -> agregarProducto());

        return panel;
    }

    private JPanel crearPanelVentas(Font fuente) {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Ventas"));

        txtBuscarVenta = new JTextField();
        JButton btnVender = new JButton("Registrar Venta");
        JButton btnVerCola = new JButton("Ver Productos a Reabastecer");

        panel.add(txtBuscarVenta);
        panel.add(btnVender);
        panel.add(btnVerCola);

        btnVender.addActionListener(e -> venderProducto());
        btnVerCola.addActionListener(e -> mostrarCola());

        return panel;
    }

    private void agregarProducto() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            int minimo = Integer.parseInt(txtMinimo.getText().trim());

            if (codigo.isEmpty() || nombre.isEmpty()) {
                throw new IllegalArgumentException("Código y nombre no pueden estar vacíos.");
            }

            Producto nuevo = new Producto(codigo, nombre, cantidad, minimo);
            inventario.agregarProducto(nuevo);
            cola.actualizarProducto(nuevo);

            JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.");
            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void venderProducto() {
        String codigo = txtBuscarVenta.getText().trim();
        Producto p = inventario.buscar(codigo);

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }

        try {
            p.vender(1);
            cola.actualizarProducto(p);
            JOptionPane.showMessageDialog(this, "Venta registrada correctamente.");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void mostrarCola() {
        areaCola.setText(cola.mostrarCola());
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
        txtMinimo.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 565, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
