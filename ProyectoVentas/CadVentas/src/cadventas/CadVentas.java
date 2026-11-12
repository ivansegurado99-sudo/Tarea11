package cadventas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * Componente de Acceso a Datos (CAD) de la base de datos de ventas.
 * <p>
 * Ofrece las operaciones sobre la base de datos sin mostrar nada por pantalla
 * ni depender de ninguna interfaz de usuario: los resultados se devuelven
 * como valores de retorno y los errores como {@link ExcepcionVentas}.
 * <p>
 * Cada operacion abre su propia conexion y la cierra al terminar.
 *
 * @author Ivan Segurado
 * @version 1.0
 */
public class CadVentas {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL_POR_DEFECTO = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USUARIO_POR_DEFECTO = "ventas";
    private static final String CONTRASENA_POR_DEFECTO = "kk";

    private static final String MENSAJE_ERROR_GENERAL
            = "Error general del sistema. Consulte con el administrador.";

    private final String url;
    private final String usuario;
    private final String contrasena;

    /**
     * Crea el componente con los datos de conexion por defecto
     * (<code>jdbc:oracle:thin:@localhost:1521:xe</code>, usuario
     * <code>ventas</code>).
     *
     * @throws ExcepcionVentas si no se puede cargar el driver JDBC de Oracle
     */
    public CadVentas() throws ExcepcionVentas {
        this(URL_POR_DEFECTO, USUARIO_POR_DEFECTO, CONTRASENA_POR_DEFECTO);
    }

    /**
     * Crea el componente con los datos de conexion indicados.
     *
     * @param url URL JDBC de la base de datos, por ejemplo
     * <code>jdbc:oracle:thin:@localhost:1521:xe</code>
     * @param usuario usuario de la base de datos
     * @param contrasena contrasena del usuario
     * @throws ExcepcionVentas si no se puede cargar el driver JDBC de Oracle
     */
    public CadVentas(String url, String usuario, String contrasena) throws ExcepcionVentas {
        this.url = url;
        this.usuario = usuario;
        this.contrasena = contrasena;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new ExcepcionVentas(null, ex.getMessage(), MENSAJE_ERROR_GENERAL, null);
        }
    }

    /**
     * Abre una conexion con la base de datos.
     *
     * @return conexion abierta
     * @throws SQLException si no se puede establecer la conexion
     */
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, usuario, contrasena);
    }

    /**
     * Inserta un pedido en la base de datos.
     * <p>
     * La primary key no se toma del objeto recibido: la genera la secuencia
     * <code>pedido_seq</code> dentro del procedimiento almacenado
     * <code>insertar_pedido</code>, al que se llama con un
     * <code>CallableStatement</code>.
     *
     * @param pedido pedido a insertar. Se utilizan el identificador de su
     * cliente, el identificador de su producto, el codigo y la cantidad
     * @return cantidad de registros insertados (1 si el pedido se ha insertado)
     * @throws ExcepcionVentas si se produce un error: falta algun dato
     * obligatorio, el cliente o el producto no existen, el codigo esta
     * repetido, la cantidad no es mayor que 0, algun dato excede la longitud
     * permitida o se produce cualquier otro error de base de datos
     */
    public Integer insertarPedido(Pedido pedido) throws ExcepcionVentas {
        String llamada = "{call insertar_pedido(?,?,?,?,?)}";
        if (pedido == null) {
            pedido = new Pedido();
        }
        Integer clienteId = pedido.getCliente() == null ? null : pedido.getCliente().getClienteId();
        Integer productoId = pedido.getProducto() == null ? null : pedido.getProducto().getProductoId();

        try (Connection conexion = conectar();
                CallableStatement sentencia = conexion.prepareCall(llamada)) {
            sentencia.setObject(1, clienteId, Types.INTEGER);
            sentencia.setObject(2, productoId, Types.INTEGER);
            sentencia.setString(3, pedido.getCodigo());
            sentencia.setObject(4, pedido.getCantidad(), Types.INTEGER);
            sentencia.registerOutParameter(5, Types.INTEGER);
            sentencia.execute();
            return sentencia.getInt(5);
        } catch (SQLException ex) {
            String mensajeUsuario;
            switch (ex.getErrorCode()) {
                case 1400:
                    mensajeUsuario = "El cliente, el producto, el codigo y la cantidad son obligatorios.";
                    break;
                case 2291:
                    mensajeUsuario = "El cliente o el producto indicados no existen.";
                    break;
                case 1:
                    mensajeUsuario = "Ya existe un pedido con ese codigo.";
                    break;
                case 2290:
                    mensajeUsuario = "La cantidad debe ser mayor que 0.";
                    break;
                case 1438:
                    mensajeUsuario = "El identificador del cliente, el del producto o la cantidad "
                            + "exceden el numero de digitos permitido "
                            + "(cliente: 5, producto: 6, cantidad: 4).";
                    break;
                case 12899:
                    mensajeUsuario = "El codigo no puede tener mas de 20 caracteres.";
                    break;
                default:
                    mensajeUsuario = MENSAJE_ERROR_GENERAL;
            }
            throw new ExcepcionVentas(ex.getErrorCode(), ex.getMessage(), mensajeUsuario, llamada);
        }
    }

    /**
     * Elimina un producto de la base de datos mediante un
     * <code>PreparedStatement</code>.
     *
     * @param productoId identificador (primary key) del producto a eliminar
     * @return cantidad de registros eliminados (0 si no existe ningun
     * producto con ese identificador)
     * @throws ExcepcionVentas si se produce un error: el producto tiene
     * pedidos asociados o se produce cualquier otro error de base de datos
     */
    public Integer eliminarProducto(Integer productoId) throws ExcepcionVentas {
        String dml = "delete from producto where producto_id = ?";

        try (Connection conexion = conectar();
                PreparedStatement sentencia = conexion.prepareStatement(dml)) {
            sentencia.setObject(1, productoId, Types.INTEGER);
            return sentencia.executeUpdate();
        } catch (SQLException ex) {
            String mensajeUsuario;
            switch (ex.getErrorCode()) {
                case 2292:
                    mensajeUsuario = "No se puede eliminar el producto porque tiene pedidos asociados.";
                    break;
                default:
                    mensajeUsuario = MENSAJE_ERROR_GENERAL;
            }
            throw new ExcepcionVentas(ex.getErrorCode(), ex.getMessage(), mensajeUsuario, dml);
        }
    }

    /**
     * Lee un cliente de la base de datos mediante un <code>Statement</code>.
     * <p>
     * La tabla CLIENTE no tiene tablas antecesoras (no tiene claves ajenas),
     * por lo que se devuelven todos los datos del propio cliente.
     *
     * @param clienteId identificador (primary key) del cliente a leer
     * @return el cliente con todos sus datos, o <code>null</code> si no
     * existe ningun cliente con ese identificador
     * @throws ExcepcionVentas si se produce cualquier error de base de datos
     */
    public Cliente leerCliente(Integer clienteId) throws ExcepcionVentas {
        String dql = "select cliente_id, nombre, dni, telefono from cliente where cliente_id = " + clienteId;
        Cliente cliente = null;

        try (Connection conexion = conectar();
                Statement sentencia = conexion.createStatement();
                ResultSet resultado = sentencia.executeQuery(dql)) {
            if (resultado.next()) {
                cliente = new Cliente();
                cliente.setClienteId(resultado.getInt("cliente_id"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setDni(resultado.getString("dni"));
                cliente.setTelefono(resultado.getString("telefono"));
            }
            return cliente;
        } catch (SQLException ex) {
            throw new ExcepcionVentas(ex.getErrorCode(), ex.getMessage(), MENSAJE_ERROR_GENERAL, dql);
        }
    }
}
