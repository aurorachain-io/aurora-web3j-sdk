package com.aoa.web3j.core.protocol.core.filters;

import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.core.Request;
import com.aoa.web3j.core.protocol.core.methods.response.AOAFilter;
import com.aoa.web3j.core.protocol.core.methods.response.AOALog;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Handler for working with transaction filter requests.
 */
public class PendingTransactionFilter extends Filter<String> {

    public PendingTransactionFilter(Web3j web3j, Callback<String> callback) {
        super(web3j, callback);
    }

    @Override
    AOAFilter sendRequest() throws IOException {
        return web3j.aoaNewPendingTransactionFilter().send();
    }

    @Override
    void process(List<AOALog.LogResult> logResults) {
        for (AOALog.LogResult logResult : logResults) {
            if (logResult instanceof AOALog.Hash) {
                String blockHash = ((AOALog.Hash) logResult).get();
                callback.onEvent(blockHash);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + ", required Hash");
            }
        }
    }

    /**
     * Since the pending transaction filter does not support historic filters,
     * the filterId is ignored and an empty optional is returned
     * @param filterId
     * Id of the filter for which the historic log should be retrieved
     * @return
     * Optional.empty()
     */
    @Override
    protected Optional<Request<?, AOALog>> getFilterLogs(BigInteger filterId) {
        return Optional.empty();
    }
}

