package controlador;

import modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private final List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto p) {
        if (buscar(p.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un producto con ese código.");
        }
        productos.add(p);
    }

    public Producto buscar(String codigo) {
        return productos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos); // Retorna una copia para evitar modificaciones externas
    }
}

