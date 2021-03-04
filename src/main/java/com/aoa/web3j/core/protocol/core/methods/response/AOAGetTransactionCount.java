package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;
import com.aoa.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * aoa_getTransactionCount.
 */
public class AOAGetTransactionCount extends Response<String> {
    public BigInteger getTransactionCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
