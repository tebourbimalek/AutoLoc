package tn.esprit.autoloc.exception;

import java.io.Serial;

/**
 * Levee lorsqu'une ressource demandee est absente de la base.
 */
public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
