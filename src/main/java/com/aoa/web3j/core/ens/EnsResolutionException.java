package com.aoa.web3j.core.ens;

/**
 * ENS resolution exception.
 */
public class EnsResolutionException extends RuntimeException {
    public EnsResolutionException(String message) {
        super(message);
    }

    public EnsResolutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
