package controlador;

import modelo.producto;
import java.util.ArrayList;

public class inventario {

    private ArrayList<producto> productos = new ArrayList<>();

    public void agregarProducto(producto p) {
        productos.add(p);
    }

    public producto buscar(String codigo) {
        for (producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<producto> getProductos() {
        return productos;
    }
}
