package com.aoa.web3j.core.protocol.admin.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

/**
 * personal_unlockAccount.
 */
public class PersonalUnlockAccount extends Response<Boolean> {
    public Boolean accountUnlocked() {
        return getResult();
    }
}