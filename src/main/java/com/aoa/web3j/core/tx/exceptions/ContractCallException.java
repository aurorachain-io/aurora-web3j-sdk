package com.aoa.web3j.core.tx.exceptions;

/**
 * Exception resulting from issues calling methods on Smart Contracts.
 */
public class ContractCallException extends RuntimeException {

    public ContractCallException(String message) {
        super(message);
    }

    public ContractCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
