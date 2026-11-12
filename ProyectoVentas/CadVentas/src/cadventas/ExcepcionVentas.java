package cadventas;

/**
 * Excepcion propia del componente CadVentas. Encapsula toda la informacion
 * de un error producido al trabajar con la base de datos de ventas para que
 * la aplicacion que utiliza el componente decida que hacer con ella.
 *
 * @author Ivan Segurado
 * @version 1.0
 */
public class ExcepcionVentas extends Exception {

    private static final long serialVersionUID = 1L;

    private Integer codigoErrorBD;
    private String mensajeErrorBD;
    private String mensajeUsuario;
    private String sentenciaSQL;

    /**
     * Constructor vacio.
     */
    public ExcepcionVentas() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param codigoErrorBD codigo de error devuelto por la base de datos
     * @param mensajeErrorBD mensaje de error devuelto por la base de datos
     * @param mensajeUsuario mensaje pensado para mostrar al usuario final
     * @param sentenciaSQL sentencia SQL que ha provocado el error
     */
    public ExcepcionVentas(Integer codigoErrorBD, String mensajeErrorBD,
            String mensajeUsuario, String sentenciaSQL) {
        this.codigoErrorBD = codigoErrorBD;
        this.mensajeErrorBD = mensajeErrorBD;
        this.mensajeUsuario = mensajeUsuario;
        this.sentenciaSQL = sentenciaSQL;
    }

    /**
     * Obtiene el codigo de error de la base de datos.
     *
     * @return codigo de error de la base de datos
     */
    public Integer getCodigoErrorBD() {
        return codigoErrorBD;
    }

    /**
     * Establece el codigo de error de la base de datos.
     *
     * @param codigoErrorBD codigo de error de la base de datos
     */
    public void setCodigoErrorBD(Integer codigoErrorBD) {
        this.codigoErrorBD = codigoErrorBD;
    }

    /**
     * Obtiene el mensaje de error de la base de datos.
     *
     * @return mensaje de error de la base de datos
     */
    public String getMensajeErrorBD() {
        return mensajeErrorBD;
    }

    /**
     * Establece el mensaje de error de la base de datos.
     *
     * @param mensajeErrorBD mensaje de error de la base de datos
     */
    public void setMensajeErrorBD(String mensajeErrorBD) {
        this.mensajeErrorBD = mensajeErrorBD;
    }

    /**
     * Obtiene el mensaje pensado para el usuario final.
     *
     * @return mensaje para el usuario
     */
    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    /**
     * Establece el mensaje pensado para el usuario final.
     *
     * @param mensajeUsuario mensaje para el usuario
     */
    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    /**
     * Obtiene la sentencia SQL que ha provocado el error.
     *
     * @return sentencia SQL
     */
    public String getSentenciaSQL() {
        return sentenciaSQL;
    }

    /**
     * Establece la sentencia SQL que ha provocado el error.
     *
     * @param sentenciaSQL sentencia SQL
     */
    public void setSentenciaSQL(String sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }

    /**
     * Devuelve el mensaje para el usuario como mensaje de la excepcion.
     *
     * @return mensaje para el usuario
     */
    @Override
    public String getMessage() {
        return mensajeUsuario;
    }

    /**
     * Devuelve el contenido del objeto en forma de texto.
     *
     * @return texto con el valor de todos los atributos
     */
    @Override
    public String toString() {
        return "ExcepcionVentas{" + "codigoErrorBD=" + codigoErrorBD
                + ", mensajeErrorBD=" + mensajeErrorBD
                + ", mensajeUsuario=" + mensajeUsuario
                + ", sentenciaSQL=" + sentenciaSQL + '}';
    }
}
