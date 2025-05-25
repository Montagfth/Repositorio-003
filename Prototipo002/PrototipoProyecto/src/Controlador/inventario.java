/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.producto;
import java.util.*;

/**
 *
 * @author Fabrizio
 */
public class inventario {

    private ArrayList<producto> productos = new ArrayList<>();

    public void agregarProducto(producto p) {
        productos.add(p);
    }

    public producto buscar(String codigo) {
        for (producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<producto> getProductos() {
        return productos;
    }
}
