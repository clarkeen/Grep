package udc.lap.grep;

public class IllegalArgumentEx extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codigoError;
	private static String mensaje = "\"Grep requiere \"grep [OPTIONS] PATTERN [FILE...] \"";

	public IllegalArgumentEx(int codigoError){
        super();
        this.codigoError = codigoError;
    }
     
    @Override
    public String getMessage(){
         
    	String caso;
         
        switch(codigoError){
            case 1:
                caso = "Error, nesecitas Almenos 2 argumentos";
                break;
            case 2:
                caso = "Error, nesesita el archivo \"[FILE...]\"";
                break;
            case 3:
                caso = "Error, nesesita el archivo \"PATTERN\"";
                break;
            default:
            	caso = "";
            	break;
        }
         
        return (caso+"\n"+mensaje);
         
    }

}
