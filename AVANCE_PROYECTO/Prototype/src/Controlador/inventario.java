package controlador;

import modelo.producto;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;


public class inventario {

    private final Map<String, producto> productos = new HashMap<>();

    public void agregarProducto(producto p) {
        if (productos.containsKey(p.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un producto con ese código.");
        }
        productos.put(p.getCodigo(), p);
    }

    public producto buscar(String codigo) {
        return productos.get(codigo);
    }

    public Collection<producto> getProductos() {
        return new ArrayList<>(productos.values());
    }
}
