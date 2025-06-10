package controlador;

import modelo.Producto;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ColaReabastecimiento {

    private PriorityQueue<Producto> cola;

    public ColaReabastecimiento() {
        cola = new PriorityQueue<>(Comparator.comparingInt(
            p -> p.getCantidad() - p.getMinimo()
        ));
    }

    public void insertar(Producto p) {
        if (!cola.contains(p) && p.necesitaReabastecimiento()) {
            cola.add(p);
        }
    }

    public void eliminarProducto(Producto p) {
        cola.remove(p);
    }

    public void actualizarProducto(Producto p) {
        // Lo eliminamos si ya estaba
        cola.remove(p);
        // Lo volvemos a insertar si aún necesita reabastecimiento
        if (p.necesitaReabastecimiento()) {
            cola.add(p);
        }
    }

    public String mostrarCola() {
        StringBuilder sb = new StringBuilder();
        for (Producto p : cola) {
            sb.append(p.getNombre())
              .append(" - Stock: ")
              .append(p.getCantidad())
              .append("\n");
        }
        return sb.toString();
    }
}

