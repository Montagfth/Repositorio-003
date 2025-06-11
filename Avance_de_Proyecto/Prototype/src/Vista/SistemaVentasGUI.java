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
        setSize(850, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Colores
        Color colorFondoVentana = new Color(230, 240, 255);
        Color colorPaneles = new Color(200, 220, 240);
        Color colorBoton = new Color(100, 149, 237);
        Color colorTextoBoton = Color.WHITE;
        Color colorLabel = new Color(25, 25, 112);

        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 14);
        Font fuenteNegrita = new Font("Segoe UI", Font.BOLD, 14);

        getContentPane().setBackground(colorFondoVentana);

        // Panel superior - Agregar Producto
        JPanel panelArriba = new JPanel(new GridLayout(5, 2, 10, 10));
        panelArriba.setBorder(BorderFactory.createTitledBorder("Agregar Producto"));
        panelArriba.setBackground(colorPaneles);

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtCantidad = new JTextField();
        txtMinimo = new JTextField();
        JButton btnAgregar = new JButton("Agregar Producto");

        JLabel lblCodigo = new JLabel("Código:");
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblCantidad = new JLabel("Cantidad:");
        JLabel lblMinimo = new JLabel("Mínimo:");

        for (JLabel lbl : new JLabel[]{lblCodigo, lblNombre, lblCantidad, lblMinimo}) {
            lbl.setFont(fuenteNegrita);
            lbl.setForeground(colorLabel);
        }

        btnAgregar.setBackground(colorBoton);
        btnAgregar.setForeground(colorTextoBoton);
        btnAgregar.setFont(fuenteNegrita);

        panelArriba.add(lblCodigo);
        panelArriba.add(txtCodigo);
        panelArriba.add(lblNombre);
        panelArriba.add(txtNombre);
        panelArriba.add(lblCantidad);
        panelArriba.add(txtCantidad);
        panelArriba.add(lblMinimo);
        panelArriba.add(txtMinimo);
        panelArriba.add(new JLabel(""));
        panelArriba.add(btnAgregar);

        add(panelArriba, BorderLayout.NORTH);

        // Panel centro - Ventas
        JPanel panelCentro = new JPanel(new GridLayout(4, 1, 10, 10));
        panelCentro.setBorder(BorderFactory.createTitledBorder("Ventas"));
        panelCentro.setBackground(colorPaneles);

        txtBuscarVenta = new JTextField();
        JButton btnVender = new JButton("Registrar Venta");
        JButton btnVerCola = new JButton("Ver Productos a Reabastecer");
        JButton btnVerTodos = new JButton("Ver Todos los Productos");

        for (JButton btn : new JButton[]{btnVender, btnVerCola, btnVerTodos}) {
            btn.setBackground(colorBoton);
            btn.setForeground(colorTextoBoton);
            btn.setFont(fuenteNegrita);
        }

        panelCentro.add(txtBuscarVenta);
        panelCentro.add(btnVender);
        panelCentro.add(btnVerCola);
        panelCentro.add(btnVerTodos);

        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior - Cola
        areaCola = new JTextArea(10, 30);
        areaCola.setEditable(false);
        areaCola.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaCola);
        scroll.setBorder(BorderFactory.createTitledBorder("Cola de Reabastecimiento"));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBackground(colorPaneles);

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

        // Acción botón Ver Todos los Productos
        btnVerTodos.addActionListener(e -> {
            if (inventario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos registrados.");
            } else {
                StringBuilder sb = new StringBuilder("Lista de productos registrados:\n\n");
                for (Producto p : inventario) {
                    sb.append("Código: ").append(p.codigo)
                            .append(" | Nombre: ").append(p.nombre)
                            .append(" | Stock: ").append(p.cantidad)
                            .append(" | Mínimo: ").append(p.minimo).append("\n");
                }
                JTextArea area = new JTextArea(sb.toString());
                area.setEditable(false);
                area.setFont(new Font("Monospaced", Font.PLAIN, 13));
                area.setBackground(colorPaneles);
                area.setForeground(colorLabel);

                JScrollPane panel = new JScrollPane(area);
                panel.setPreferredSize(new Dimension(700, 400));
                JOptionPane.showMessageDialog(this, panel, "Inventario Completo", JOptionPane.INFORMATION_MESSAGE);
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
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // default
        }

        SwingUtilities.invokeLater(() -> new SistemaVentasGUI().setVisible(true));
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
