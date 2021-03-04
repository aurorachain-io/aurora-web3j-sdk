package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;
import com.aoa.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * aoa_gasPrice.
 */
public class AOAGasPrice extends Response<String> {
    public BigInteger getGasPrice() {
        return Numeric.decodeQuantity(getResult());
    }
}
