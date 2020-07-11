package essentialsapi.utils.exception;

import essentialsapi.utils.exception.PlayerException;

public class UnkownPlayerException extends PlayerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 397367956102634347L;

	public UnkownPlayerException(String message) {
		super(message);
	}
}