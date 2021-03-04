package com.aoa.web3j.core.protocol.admin.methods.response;
import com.aoa.web3j.core.protocol.core.Response;

/**
 * personal_sign
 * parity_signMessage.
 */
public class PersonalSign extends Response<String> {
    public String getSignedMessage() {
        return getResult();
    }
}
