package com.aoa.web3j.abi.datatypes.generated;

import com.aoa.web3j.abi.datatypes.StaticArray;
import com.aoa.web3j.abi.datatypes.Type;

import java.util.List;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray3<T extends Type> extends StaticArray<T> {
    public StaticArray3(List<T> values) {
        super(3, values);
    }

    @SafeVarargs
    public StaticArray3(T... values) {
        super(3, values);
    }
}
