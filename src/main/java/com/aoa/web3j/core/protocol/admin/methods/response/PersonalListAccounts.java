package com.aoa.web3j.core.protocol.admin.methods.response;

import com.aoa.web3j.core.protocol.core.Response;

import java.util.List;

/**
 * personal_listAccounts.
 */
public class PersonalListAccounts extends Response<List<String>> {
    public List<String> getAccountIds() {
        return getResult();
    }
}
