package NA;

public class NA {
    private static final NA instance = new NA();

    private NA() {}

    public static NA getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "NA";
    }

    // public void ignorarValidarTipo() {
    // }
    // public void completarNA(){
    
    // }
    // @Override
    // public String toString(){
    //     return "Na";
    // }
}
