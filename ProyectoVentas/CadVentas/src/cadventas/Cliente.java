package cadventas;

/**
 * Representa un registro de la tabla CLIENTE de la base de datos de ventas.
 *
 * @author Ivan Segurado
 * @version 1.0
 */
public class Cliente {

    private Integer clienteId;
    private String nombre;
    private String dni;
    private String telefono;

    /**
     * Constructor vacio.
     */
    public Cliente() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param clienteId identificador del cliente (primary key)
     * @param nombre nombre del cliente (obligatorio y unico)
     * @param dni DNI del cliente (obligatorio y unico)
     * @param telefono telefono del cliente (obligatorio; empieza por 6, 7 o 9)
     */
    public Cliente(Integer clienteId, String nombre, String dni, String telefono) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
    }

    /**
     * Obtiene el identificador del cliente.
     *
     * @return identificador del cliente
     */
    public Integer getClienteId() {
        return clienteId;
    }

    /**
     * Establece el identificador del cliente.
     *
     * @param clienteId identificador del cliente
     */
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el DNI del cliente.
     *
     * @return DNI del cliente
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del cliente.
     *
     * @param dni DNI del cliente
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el telefono del cliente.
     *
     * @return telefono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el telefono del cliente.
     *
     * @param telefono telefono del cliente
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve el contenido del objeto en forma de texto.
     *
     * @return texto con el valor de todos los atributos
     */
    @Override
    public String toString() {
        return "Cliente{" + "clienteId=" + clienteId + ", nombre=" + nombre
                + ", dni=" + dni + ", telefono=" + telefono + '}';
    }
}
