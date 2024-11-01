package excepciones;

import java.io.IOException;

public class ArchivoException extends IOException{
    public ArchivoException (String mensaje){
        super(mensaje);
    }
}
