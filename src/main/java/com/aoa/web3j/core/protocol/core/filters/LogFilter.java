package com.aoa.web3j.core.protocol.core.filters;

import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.core.Request;
import com.aoa.web3j.core.protocol.core.methods.response.AOAFilter;
import com.aoa.web3j.core.protocol.core.methods.response.AOALog;
import com.aoa.web3j.core.protocol.core.methods.response.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Log filter handler.
 */
public class LogFilter extends Filter<Log> {

    private final com.aoa.web3j.core.protocol.core.methods.request.AOAFilter AOAFilter;

    public LogFilter(
            Web3j web3j, Callback<Log> callback,
            com.aoa.web3j.core.protocol.core.methods.request.AOAFilter AOAFilter) {
        super(web3j, callback);
        this.AOAFilter = AOAFilter;
    }


    @Override
    AOAFilter sendRequest() throws IOException {
        return web3j.aoaNewFilter(AOAFilter).send();
    }

    @Override
    void process(List<AOALog.LogResult> logResults) {
        for (AOALog.LogResult logResult : logResults) {
            if (logResult instanceof AOALog.LogObject) {
                Log log = ((AOALog.LogObject) logResult).get();
                callback.onEvent(log);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + " required LogObject");
            }
        }
    }

    @Override
    protected Optional<Request<?, AOALog>> getFilterLogs(BigInteger filterId) {
        return Optional.of(web3j.aoaGetFilterLogs(filterId));
    }
}
