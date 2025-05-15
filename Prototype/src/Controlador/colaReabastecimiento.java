/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        if (!cola.contains(p)) {
            cola.add(p);
        }
    }

    public String mostrarCola() {
        StringBuilder sb = new StringBuilder();
        for (producto p : cola) {
            sb.append(p.getNombre()).append(" - Stock: ").append(p.getCantidad()).append("\n");
        }
        return sb.toString();
    }
}
