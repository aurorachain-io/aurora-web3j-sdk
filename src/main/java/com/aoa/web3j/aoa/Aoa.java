package com.aoa.web3j.aoa;


import com.aoa.web3j.aoa.response.PersonalEcRecover;
import com.aoa.web3j.aoa.response.PersonalImportRawKey;
import com.aoa.web3j.core.protocol.Web3jService;
import com.aoa.web3j.core.protocol.admin.Admin;
import com.aoa.web3j.core.protocol.admin.methods.response.BooleanResponse;
import com.aoa.web3j.core.protocol.admin.methods.response.PersonalSign;
import com.aoa.web3j.core.protocol.core.Request;

/**
 * JSON-RPC Request object building factory for Aoa.
 */
public interface Aoa extends Admin {
    static Aoa build(Web3jService web3jService) {
        return new JsonRpc2_0Aoa(web3jService);
    }
        
    public Request<?, PersonalImportRawKey> personalImportRawKey(String keydata, String password);
    
    public Request<?, BooleanResponse> personalLockAccount(String accountId);
    
    public Request<?, PersonalSign> personalSign(String message, String accountId, String password);
    
    public Request<?, PersonalEcRecover> personalEcRecover(String message, String signiture);
    
}
