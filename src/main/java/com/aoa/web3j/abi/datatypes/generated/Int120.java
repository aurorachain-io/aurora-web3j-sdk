package com.aoa.web3j.abi.datatypes.generated;

import com.aoa.web3j.abi.datatypes.Int;

import java.math.BigInteger;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Int120 extends Int {
    public static final Int120 DEFAULT = new Int120(BigInteger.ZERO);

    public Int120(BigInteger value) {
        super(120, value);
    }

    public Int120(long value) {
        this(BigInteger.valueOf(value));
    }
}
