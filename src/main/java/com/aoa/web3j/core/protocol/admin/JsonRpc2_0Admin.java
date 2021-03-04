package com.aoa.web3j.core.protocol.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aoa.web3j.core.protocol.Web3jService;
import com.aoa.web3j.core.protocol.admin.methods.response.NewAccountIdentifier;
import com.aoa.web3j.core.protocol.admin.methods.response.PersonalListAccounts;
import com.aoa.web3j.core.protocol.admin.methods.response.PersonalUnlockAccount;
import com.aoa.web3j.core.protocol.core.JsonRpc2_0Web3j;
import com.aoa.web3j.core.protocol.core.Request;
import com.aoa.web3j.core.protocol.core.methods.request.Transaction;
import com.aoa.web3j.core.protocol.core.methods.response.AOASendTransaction;

/**
 * JSON-RPC 2.0 factory implementation for common Parity and Aoa.
 */
public class JsonRpc2_0Admin extends JsonRpc2_0Web3j implements Admin {

    public JsonRpc2_0Admin(Web3jService web3jService) {
        super(web3jService);
    }

    @Override
    public Request<?, PersonalListAccounts> personalListAccounts() {
        return new Request<>(
            "personal_listAccounts",
            Collections.<String>emptyList(),
            web3jService,
            PersonalListAccounts.class);
    }

    @Override
    public Request<?, NewAccountIdentifier> personalNewAccount(String password) {
        return new Request<>(
            "personal_newAccount",
            Arrays.asList(password),
            web3jService,
            NewAccountIdentifier.class);
    }

    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
        String accountId, String password,
        BigInteger duration) {
        List<Object> attributes = new ArrayList<>(3);
        attributes.add(accountId);
        attributes.add(password);

        if (duration != null) {
            // Parity has a bug where it won't support a duration
            // See https://github.com/ethcore/parity/issues/1215
            attributes.add(duration.longValue());
        } else {
            // we still need to include the null value, otherwise Parity rejects request
            attributes.add(null);
        }

        return new Request<>(
            "personal_unlockAccount",
            attributes,
            web3jService,
            PersonalUnlockAccount.class);
    }

    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
        String accountId, String password) {

        return personalUnlockAccount(accountId, password, null);
    }

    @Override
    public Request<?, AOASendTransaction> personalSendTransaction(
        Transaction transaction, String passphrase) {
        return new Request<>(
            "personal_sendTransaction",
            Arrays.asList(transaction, passphrase),
            web3jService,
            AOASendTransaction.class);
    }

}
