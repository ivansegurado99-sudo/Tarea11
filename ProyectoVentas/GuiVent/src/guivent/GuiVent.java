package guivent;

import cadventas.CadVentas;
import cadventas.Cliente;
import cadventas.ExcepcionVentas;
import cadventas.Pedido;
import cadventas.Producto;
import java.util.Scanner;

/**
 * Aplicacion de consola que integra el componente CadVentas y permite
 * comprobar sus tres operaciones: insertar un pedido, eliminar un producto y
 * leer un cliente.
 *
 * @author Ivan Segurado
 * @version 1.0
 */
public class GuiVent {

    private static final Scanner TECLADO = new Scanner(System.in);

    /**
     * Punto de entrada de la aplicacion. Muestra un menu por consola hasta
     * que el usuario elige salir.
     * <p>
     * Sin argumentos se utiliza la conexion por defecto de CadVentas. Tambien
     * se pueden indicar tres argumentos: URL JDBC, usuario y contrasena.
     *
     * @param args vacio, o bien URL JDBC, usuario y contrasena
     */
    public static void main(String[] args) {
        CadVentas cad;
        try {
            if (args.length == 3) {
                cad = new CadVentas(args[0], args[1], args[2]);
            } else {
                cad = new CadVentas();
            }
        } catch (ExcepcionVentas ex) {
            mostrarError(ex);
            return;
        }

        String opcion;
        do {
            System.out.println();
            System.out.println("========== GUIVENT ==========");
            System.out.println("1. Insertar pedido");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Leer cliente");
            System.out.println("0. Salir");
            opcion = leerTexto("Opcion");
            if (opcion == null) {
                opcion = "";
            }
            try {
                switch (opcion) {
                    case "1":
                        insertarPedido(cad);
                        break;
                    case "2":
                        eliminarProducto(cad);
                        break;
                    case "3":
                        leerCliente(cad);
                        break;
                    case "0":
                        System.out.println("Fin del programa.");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (ExcepcionVentas ex) {
                mostrarError(ex);
            }
        } while (!opcion.equals("0"));
    }

    /**
     * Pide los datos de un pedido y lo inserta mediante CadVentas.
     *
     * @param cad componente de acceso a datos
     * @throws ExcepcionVentas si CadVentas detecta un error
     */
    private static void insertarPedido(CadVentas cad) throws ExcepcionVentas {
        System.out.println("--- Insertar pedido (deje un dato en blanco para enviarlo nulo) ---");
        Cliente cliente = new Cliente();
        cliente.setClienteId(leerEntero("Identificador del cliente"));
        Producto producto = new Producto();
        producto.setProductoId(leerEntero("Identificador del producto"));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProducto(producto);
        pedido.setCodigo(leerTexto("Codigo del pedido"));
        pedido.setCantidad(leerEntero("Cantidad"));

        Integer registros = cad.insertarPedido(pedido);
        System.out.println("Registros insertados: " + registros);
    }

    /**
     * Pide el identificador de un producto y lo elimina mediante CadVentas.
     *
     * @param cad componente de acceso a datos
     * @throws ExcepcionVentas si CadVentas detecta un error
     */
    private static void eliminarProducto(CadVentas cad) throws ExcepcionVentas {
        System.out.println("--- Eliminar producto ---");
        Integer registros = cad.eliminarProducto(leerEntero("Identificador del producto"));
        if (registros == 0) {
            System.out.println("Registros eliminados: 0 (no existe ningun producto con ese identificador)");
        } else {
            System.out.println("Registros eliminados: " + registros);
        }
    }

    /**
     * Pide el identificador de un cliente y lo lee mediante CadVentas.
     *
     * @param cad componente de acceso a datos
     * @throws ExcepcionVentas si CadVentas detecta un error
     */
    private static void leerCliente(CadVentas cad) throws ExcepcionVentas {
        System.out.println("--- Leer cliente ---");
        Cliente cliente = cad.leerCliente(leerEntero("Identificador del cliente"));
        if (cliente == null) {
            System.out.println("No existe ningun cliente con ese identificador.");
        } else {
            System.out.println("Identificador: " + cliente.getClienteId());
            System.out.println("Nombre:        " + cliente.getNombre());
            System.out.println("DNI:           " + cliente.getDni());
            System.out.println("Telefono:      " + cliente.getTelefono());
        }
    }

    /**
     * Muestra por consola toda la informacion de un error de CadVentas.
     *
     * @param ex excepcion producida
     */
    private static void mostrarError(ExcepcionVentas ex) {
        System.out.println("ERROR: " + ex.getMensajeUsuario());
        System.out.println("  Codigo de error BD:  " + ex.getCodigoErrorBD());
        System.out.println("  Mensaje de error BD: " + ex.getMensajeErrorBD());
        System.out.println("  Sentencia SQL:       " + ex.getSentenciaSQL());
    }

    /**
     * Lee una linea de texto del teclado.
     *
     * @param etiqueta texto que se muestra al pedir el dato
     * @return el texto introducido, o <code>null</code> si se deja en blanco
     */
    private static String leerTexto(String etiqueta) {
        System.out.print(etiqueta + ": ");
        if (!TECLADO.hasNextLine()) {
            return "0";
        }
        String linea = TECLADO.nextLine().trim();
        return linea.isEmpty() ? null : linea;
    }

    /**
     * Lee un numero entero del teclado, repitiendo la peticion hasta que el
     * valor sea valido.
     *
     * @param etiqueta texto que se muestra al pedir el dato
     * @return el numero introducido, o <code>null</code> si se deja en blanco
     */
    private static Integer leerEntero(String etiqueta) {
        while (true) {
            String texto = leerTexto(etiqueta);
            if (texto == null) {
                return null;
            }
            try {
                return Integer.valueOf(texto);
            } catch (NumberFormatException ex) {
                System.out.println("Debe introducir un numero entero.");
            }
        }
    }
}
