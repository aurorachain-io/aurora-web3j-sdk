package com.aoa.web3j.abi.datatypes.generated;

import com.aoa.web3j.abi.datatypes.Int;

import java.math.BigInteger;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Int40 extends Int {
    public static final Int40 DEFAULT = new Int40(BigInteger.ZERO);

    public Int40(BigInteger value) {
        super(40, value);
    }

    public Int40(long value) {
        this(BigInteger.valueOf(value));
    }
}
