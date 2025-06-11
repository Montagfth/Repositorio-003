package modelo;

public class producto {

    private String codigo;
    private String nombre;
    private int cantidad;
    private int minimo;

    public producto(String codigo, String nombre, int cantidad, int minimo) {
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

    public void reabastecer(int cantidadNueva) {
        this.cantidad += cantidadNueva;
    }

    @Override
    public String toString() {
        return nombre + " (" + cantidad + ")";
    }
}
