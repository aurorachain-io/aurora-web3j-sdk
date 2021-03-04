package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.core.Response;
import com.aoa.web3j.utils.Numeric;

import java.math.BigInteger;

public class AOAGetVotesNumber extends Response<String> {
    public BigInteger getVotesNumber() {
        return Numeric.decodeQuantity(getResult());
    }
}
