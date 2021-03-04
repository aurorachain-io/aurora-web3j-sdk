package com.aoa.web3j.core.protocol;

import com.aoa.web3j.core.protocol.core.Aurora;
import com.aoa.web3j.core.protocol.core.JsonRpc2_0Web3j;
import com.aoa.web3j.core.protocol.rx.Web3jRx;

import java.util.concurrent.ScheduledExecutorService;

/**
 * JSON-RPC Request object building factory.
 */
public interface Web3j extends Aurora, Web3jRx {

    /**
     * Construct a new Web3j instance.
     *
     * @param web3jService web3j service instance - i.e. HTTP or IPC
     * @return new Web3j instance
     */
    static Web3j build(Web3jService web3jService) {
        return new JsonRpc2_0Web3j(web3jService);
    }

    /**
     * Construct a new Web3j instance.
     *
     * @param web3jService web3j service instance - i.e. HTTP or IPC
     * @param pollingInterval polling interval for responses from network nodes
     * @param scheduledExecutorService executor service to use for scheduled tasks.
     *                                 <strong>You are responsible for terminating this thread
     *                                 pool</strong>
     * @return new Web3j instance
     */
    static Web3j build(
        Web3jService web3jService, long pollingInterval,
        ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0Web3j(web3jService, pollingInterval, scheduledExecutorService);
    }
}
