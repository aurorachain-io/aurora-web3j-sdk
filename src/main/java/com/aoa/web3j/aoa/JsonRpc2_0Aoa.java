package com.aoa.web3j.aoa;

import com.aoa.web3j.aoa.response.PersonalEcRecover;
import com.aoa.web3j.aoa.response.PersonalImportRawKey;
import com.aoa.web3j.core.protocol.Web3jService;
import com.aoa.web3j.core.protocol.admin.JsonRpc2_0Admin;
import com.aoa.web3j.core.protocol.admin.methods.response.BooleanResponse;
import com.aoa.web3j.core.protocol.admin.methods.response.PersonalSign;
import com.aoa.web3j.core.protocol.core.Request;


import java.util.Arrays;

/**
 * JSON-RPC 2.0 factory implementation for Aoa.
 */
class JsonRpc2_0Aoa extends JsonRpc2_0Admin implements Aoa {

    public JsonRpc2_0Aoa(Web3jService web3jService) {
        super(web3jService);
    }
    
    @Override
    public Request<?, PersonalImportRawKey> personalImportRawKey(
            String keydata, String password) {
        return new Request<>(
                "personal_importRawKey",
                Arrays.asList(keydata, password),
                web3jService,
                PersonalImportRawKey.class);
    }

    @Override
    public Request<?, BooleanResponse> personalLockAccount(String accountId) {
        return new Request<>(
                "personal_lockAccount",
                Arrays.asList(accountId),
                web3jService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, PersonalSign> personalSign(
            String message, String accountId, String password) {
        return new Request<>(
                "personal_sign",
                Arrays.asList(message,accountId,password),
                web3jService,
                PersonalSign.class);
    }

    @Override
    public Request<?, PersonalEcRecover> personalEcRecover(
            String hexMessage, String signedMessage) {
        return new Request<>(
                "personal_ecRecover",
                Arrays.asList(hexMessage,signedMessage),
                web3jService,
                PersonalEcRecover.class);
    } 
    
}
