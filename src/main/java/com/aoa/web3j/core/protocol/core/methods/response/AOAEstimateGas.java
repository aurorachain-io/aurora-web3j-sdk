package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;
import com.aoa.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * aoa_estimateGas.
 */
public class AOAEstimateGas extends Response<String> {
    public BigInteger getAmountUsed() {
        return Numeric.decodeQuantity(getResult());
    }
}
