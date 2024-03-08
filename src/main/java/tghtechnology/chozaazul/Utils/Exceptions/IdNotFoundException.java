package tghtechnology.chozaazul.Utils.Exceptions;

public class IdNotFoundException extends DataMismatchException{

	private static final long serialVersionUID = 1L;

	public IdNotFoundException(String type) {
		super(type, "Id no encontrada");
	}
	
	public void printWarn() {
		System.err.printf("[%s]: %s\n", var, msg);
	}

}
