package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;
import com.aoa.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * aoa_newFilter.
 */
public class AOAFilter extends Response<String> {
    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
