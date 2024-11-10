package excepciones;

public class FilaNoValidaException extends RuntimeException {
    public FilaNoValidaException(String mensaje) {
        super(mensaje);
    }
}
