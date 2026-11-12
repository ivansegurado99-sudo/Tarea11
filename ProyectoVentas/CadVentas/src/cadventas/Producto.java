package cadventas;

import java.math.BigDecimal;

/**
 * Representa un registro de la tabla PRODUCTO de la base de datos de ventas.
 *
 * @author Ivan Segurado
 * @version 1.0
 */
public class Producto {

    private Integer productoId;
    private String nombre;
    private String referencia;
    private BigDecimal precio;
    private Integer stock;
    private String categoria;

    /**
     * Constructor vacio.
     */
    public Producto() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param productoId identificador del producto (primary key)
     * @param nombre nombre del producto (obligatorio y unico)
     * @param referencia referencia del producto (obligatoria y unica)
     * @param precio precio del producto (obligatorio y mayor que 0)
     * @param stock unidades en almacen (obligatorio y mayor o igual que 0)
     * @param categoria categoria del producto (obligatoria)
     */
    public Producto(Integer productoId, String nombre, String referencia,
            BigDecimal precio, Integer stock, String categoria) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.referencia = referencia;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return identificador del producto
     */
    public Integer getProductoId() {
        return productoId;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param productoId identificador del producto
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la referencia del producto.
     *
     * @return referencia del producto
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Establece la referencia del producto.
     *
     * @param referencia referencia del producto
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return precio del producto
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio precio del producto
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el stock del producto.
     *
     * @return unidades en almacen
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * Establece el stock del producto.
     *
     * @param stock unidades en almacen
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * Obtiene la categoria del producto.
     *
     * @return categoria del producto
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoria del producto.
     *
     * @param categoria categoria del producto
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Devuelve el contenido del objeto en forma de texto.
     *
     * @return texto con el valor de todos los atributos
     */
    @Override
    public String toString() {
        return "Producto{" + "productoId=" + productoId + ", nombre=" + nombre
                + ", referencia=" + referencia + ", precio=" + precio
                + ", stock=" + stock + ", categoria=" + categoria + '}';
    }
}
