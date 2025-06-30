package controlador;

import modelo.producto;
import java.util.PriorityQueue;
import java.util.Comparator;

public class colaReabastecimiento {

    private PriorityQueue<producto> cola;

    public colaReabastecimiento() {
        cola = new PriorityQueue<>(Comparator.comparingInt(
            p -> p.getCantidad() - p.getMinimo()
        ));
    }

    public void insertar(producto p) {
        if (!cola.contains(p) && p.necesitaReabastecimiento()) {
            cola.add(p);
        }
    }

    public void eliminarProducto(producto p) {
        cola.remove(p);
    }

    public void actualizarProducto(producto p) {
        // Lo eliminamos si ya estaba
        cola.remove(p);
        // Lo volvemos a insertar si aún necesita reabastecimiento
        if (p.necesitaReabastecimiento()) {
            cola.add(p);
        }
    }

    public String mostrarCola() {
        StringBuilder sb = new StringBuilder();
        for (producto p : cola) {
            sb.append(p.getNombre())
              .append(" - Stock: ")
              .append(p.getCantidad())
              .append("\n");
        }
        return sb.toString();
    }
}


