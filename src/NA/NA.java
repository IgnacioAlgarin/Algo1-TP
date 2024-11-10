package NA;

/**
 * Clase singleton que representa un valor "No Disponible" (NA) en una tabla de datos.
 * Esta clase es utilizada para indicar que un valor específico no está disponible.
 * Es un singleton, por lo que solo existe una instancia de esta clase.
 */
public class NA {
    private static final NA instance = new NA();

    /**
     * Constructor privado para evitar la creación de instancias externas.
     * Utiliza el patrón singleton para asegurar que solo existe una instancia de NA.
     */
    private NA() {}

    /**
     * Obtiene la única instancia de la clase NA.
     * @return La instancia única de NA.
     */
    public static NA getInstance() {
        return instance;
    }

    /**
     * Genera una representación en cadena del objeto NA.
     * @return La cadena "NA" que representa la instancia.
     */
    @Override
    public String toString() {
        return "NA";
    }
}
