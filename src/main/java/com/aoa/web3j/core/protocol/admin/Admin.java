package com.aoa.web3j.core.protocol.admin;



import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.Web3jService;
import com.aoa.web3j.core.protocol.admin.methods.response.NewAccountIdentifier;
import com.aoa.web3j.core.protocol.admin.methods.response.PersonalListAccounts;
import com.aoa.web3j.core.protocol.admin.methods.response.PersonalUnlockAccount;
import com.aoa.web3j.core.protocol.core.Request;
import com.aoa.web3j.core.protocol.core.methods.request.Transaction;
import com.aoa.web3j.core.protocol.core.methods.response.AOASendTransaction;

import java.math.BigInteger;

/**
 * JSON-RPC Request object building factory for common Parity and Aoa.
 */
public interface Admin extends Web3j {

    static Admin build(Web3jService web3jService) {
        return new JsonRpc2_0Admin(web3jService);
    }
    
    public Request<?, PersonalListAccounts> personalListAccounts();
    
    public Request<?, NewAccountIdentifier> personalNewAccount(String password);
    
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
        String address, String passphrase, BigInteger duration);

    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
        String address, String passphrase);

    public Request<?, AOASendTransaction> personalSendTransaction(
        Transaction transaction, String password);

}   
