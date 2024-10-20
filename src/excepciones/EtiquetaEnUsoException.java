package excepciones;

public class EtiquetaEnUsoException extends RuntimeException{
    public EtiquetaEnUsoException(String mensaje){
        super(mensaje);
    }

}
