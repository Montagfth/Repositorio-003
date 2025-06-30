package modelo;

import java.util.Objects;

public class producto {

    private final String codigo;
    private String nombre;
    private int cantidad;
    private int minimo;

    public producto(String codigo, String nombre, int cantidad, int minimo) {
        if (codigo == null || codigo.isBlank() || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Código y nombre no pueden estar vacíos.");
        }
        if (cantidad < 0 || minimo < 0) {
            throw new IllegalArgumentException("Cantidad y mínimo deben ser mayores o iguales a cero.");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.minimo = minimo;
    }

    public boolean necesitaReabastecimiento() {
        return cantidad <= minimo;
    }

    public void vender(int cantidadVendida) {
        if (cantidadVendida <= 0) {
            throw new IllegalArgumentException("La cantidad vendida debe ser positiva.");
        }
        if (cantidadVendida > cantidad) {
            throw new IllegalStateException("No hay suficiente stock para vender.");
        }
        cantidad -= cantidadVendida;
    }

    public void reabastecer(int cantidadNueva) {
        if (cantidadNueva <= 0) {
            throw new IllegalArgumentException("La cantidad a reabastecer debe ser positiva.");
        }
        cantidad += cantidadNueva;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public void setMinimo(int minimo) {
        if (minimo < 0) {
            throw new IllegalArgumentException("El mínimo no puede ser negativo.");
        }
        this.minimo = minimo;
    }

    @Override
    public String toString() {
        return nombre + " (Stock: " + cantidad + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof producto)) return false;
        producto producto = (producto) o;
        return codigo.equals(producto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
