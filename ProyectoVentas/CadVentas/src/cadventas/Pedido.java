package cadventas;

/**
 * Representa un registro de la tabla PEDIDO de la base de datos de ventas.
 * Las claves ajenas se representan mediante los objetos {@link Cliente} y
 * {@link Producto} a los que pertenece el pedido.
 *
 * @author Ivan Segurado
 * @version 1.0
 */
public class Pedido {

    private Integer pedidoId;
    private Cliente cliente;
    private Producto producto;
    private String codigo;
    private Integer cantidad;

    /**
     * Constructor vacio.
     */
    public Pedido() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param pedidoId identificador del pedido (primary key, la genera una
     * secuencia de la base de datos al insertar)
     * @param cliente cliente que realiza el pedido (obligatorio)
     * @param producto producto pedido (obligatorio)
     * @param codigo codigo del pedido (obligatorio y unico)
     * @param cantidad unidades pedidas (obligatorio y mayor que 0)
     */
    public Pedido(Integer pedidoId, Cliente cliente, Producto producto,
            String codigo, Integer cantidad) {
        this.pedidoId = pedidoId;
        this.cliente = cliente;
        this.producto = producto;
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return identificador del pedido
     */
    public Integer getPedidoId() {
        return pedidoId;
    }

    /**
     * Establece el identificador del pedido.
     *
     * @param pedidoId identificador del pedido
     */
    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    /**
     * Obtiene el cliente que realiza el pedido.
     *
     * @return cliente del pedido
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente que realiza el pedido.
     *
     * @param cliente cliente del pedido
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el producto pedido.
     *
     * @return producto del pedido
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto pedido.
     *
     * @param producto producto del pedido
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Obtiene el codigo del pedido.
     *
     * @return codigo del pedido
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo del pedido.
     *
     * @param codigo codigo del pedido
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la cantidad pedida.
     *
     * @return unidades pedidas
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad pedida.
     *
     * @param cantidad unidades pedidas
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el contenido del objeto en forma de texto.
     *
     * @return texto con el valor de todos los atributos
     */
    @Override
    public String toString() {
        return "Pedido{" + "pedidoId=" + pedidoId + ", cliente=" + cliente
                + ", producto=" + producto + ", codigo=" + codigo
                + ", cantidad=" + cantidad + '}';
    }
}
