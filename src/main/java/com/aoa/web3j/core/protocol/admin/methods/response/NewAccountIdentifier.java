package com.aoa.web3j.core.protocol.admin.methods.response;


import com.aoa.web3j.core.protocol.core.Response;

/**
 * personal_newAccount
 * parity_newAccountFromPhrase
 * parity_newAccountFromSecret
 * parity_newAccountFromWallet.
 */
public class NewAccountIdentifier extends Response<String> {
    public String getAccountId() {
        return getResult();
    }    
}
