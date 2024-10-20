package excepciones;

public class TipoNoSoportadoException extends RuntimeException {
    public TipoNoSoportadoException(String mensaje){
        super(mensaje);
    }

}
