package com.aoa.web3j.core.protocol.core;

import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.Web3jService;
import com.aoa.web3j.core.protocol.core.methods.request.ShhFilter;
import com.aoa.web3j.core.protocol.core.methods.request.ShhPost;
import com.aoa.web3j.core.protocol.core.methods.request.Transaction;
import com.aoa.web3j.core.protocol.core.methods.response.*;
import com.aoa.web3j.core.protocol.rx.JsonRpc2_0Rx;
import com.aoa.web3j.core.utils.Async;
import com.aoa.web3j.utils.Numeric;
import rx.Observable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;


/**
 * JSON-RPC 2.0 factory implementation.
 */
public class JsonRpc2_0Web3j implements Web3j {

    public static final int DEFAULT_BLOCK_TIME = 10 * 1000;

    protected final Web3jService web3jService;
    private final JsonRpc2_0Rx web3jRx;
    private final long blockTime;

    public JsonRpc2_0Web3j(Web3jService web3jService) {
        this(web3jService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService());
    }

    public JsonRpc2_0Web3j(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        this.web3jService = web3jService;
        this.web3jRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
    }

    @Override
    public Request<?, Web3ClientVersion> web3ClientVersion() {
        return new Request<>(
                "web3_clientVersion",
                Collections.<String>emptyList(),
                web3jService,
                Web3ClientVersion.class);
    }

    @Override
    public Request<?, Web3Sha3> web3Sha3(String data) {
        return new Request<>(
                "web3_sha3",
                Collections.singletonList(data),
                web3jService,
                Web3Sha3.class);
    }

    @Override
    public Request<?, NetVersion> netVersion() {
        return new Request<>(
                "net_version",
                Collections.<String>emptyList(),
                web3jService,
                NetVersion.class);
    }

    @Override
    public Request<?, NetListening> netListening() {
        return new Request<>(
                "net_listening",
                Collections.<String>emptyList(),
                web3jService,
                NetListening.class);
    }

    @Override
    public Request<?, NetPeerCount> netPeerCount() {
        return new Request<>(
                "net_peerCount",
                Collections.<String>emptyList(),
                web3jService,
                NetPeerCount.class);
    }

    @Override
    public Request<?, AOAProtocolVersion> aoaProtocolVersion() {
        return new Request<>(
                "aoa_protocolVersion",
                Collections.<String>emptyList(),
                web3jService,
                AOAProtocolVersion.class);
    }

    @Override
    public Request<?, AOASyncing> aoaSyncing() {
        return new Request<>(
                "aoa_syncing",
                Collections.<String>emptyList(),
                web3jService,
                AOASyncing.class);
    }


    @Override
    public Request<?, AOAGasPrice> aoaGasPrice() {
        return new Request<>(
                "aoa_gasPrice",
                Collections.<String>emptyList(),
                web3jService,
                AOAGasPrice.class);
    }

    @Override
    public Request<?, AOAAccounts> aoaAccounts() {
        return new Request<>(
                "aoa_accounts",
                Collections.<String>emptyList(),
                web3jService,
                AOAAccounts.class);
    }

    @Override
    public Request<?, AOABlockNumber> aoaBlockNumber() {
        return new Request<>(
                "aoa_blockNumber",
                Collections.<String>emptyList(),
                web3jService,
                AOABlockNumber.class);
    }

    @Override
    public Request<?, AOAGetVotesNumber> aoaGetVotesNumber(String address) {
        return new Request<>(
                "aoa_getVotesNumber",
                Collections.singletonList(address),
                web3jService,
                AOAGetVotesNumber.class);
    }

    @Override
    public Request<?, AOAGetDelegate> aoaGetDelegate(String address) {
        return new Request<>(
                "aoa_getDelegate",
                Collections.singletonList(address),
                web3jService,
                AOAGetDelegate.class
        );
    }

    @Override
    public Request<?, AOAGetDelegateList> aoaGetDelegateList() {
        return new Request<>(
                "aoa_getDelegateList",
                new ArrayList<>(),
                web3jService,
                AOAGetDelegateList.class
        );
    }

    @Override
    public Request<?, AOAGetBalance> aoaGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "aoa_getBalance",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                AOAGetBalance.class);
    }

    @Override
    public Request<?, AOAGetBalance> aoaGetAssetBalance(String address, String assetId, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "aoa_getAssetBalance",
                Arrays.asList(address, assetId, defaultBlockParameter.getValue()),
                web3jService,
                AOAGetBalance.class);
    }

    @Override
    public Request<?, AOAGetStorageAt> aoaGetStorageAt(
            String address, BigInteger position, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "aoa_getStorageAt",
                Arrays.asList(
                        address,
                        Numeric.encodeQuantity(position),
                        defaultBlockParameter.getValue()),
                web3jService,
                AOAGetStorageAt.class);
    }

    @Override
    public Request<?, AOAGetTransactionCount> aoaGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "aoa_getTransactionCount",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                AOAGetTransactionCount.class);
    }

    @Override
    public Request<?, AOAGetTransactionCountIncludePending> aoaGetTransactionCountIncludePending(String address) {
        return new Request<>(
                "aoa_getTransactionCountIncludePending",
                Collections.singletonList(address),
                web3jService,
                AOAGetTransactionCountIncludePending.class
        );
    }


    @Override
    public Request<?, AOAGetBlockTransactionCountByHash> aoaGetBlockTransactionCountByHash(
            String blockHash) {
        return new Request<>(
                "aoa_getBlockTransactionCountByHash",
                Collections.singletonList(blockHash),
                web3jService,
                AOAGetBlockTransactionCountByHash.class);
    }

    @Override
    public Request<?, AOAGetBlockTransactionCountByNumber> aoaGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "aoa_getBlockTransactionCountByNumber",
                Collections.singletonList(defaultBlockParameter.getValue()),
                web3jService,
                AOAGetBlockTransactionCountByNumber.class);
    }


    @Override
    public Request<?, AOAGetCode> aoaGetCode(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "aoa_getCode",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                AOAGetCode.class);
    }

    @Override
    public Request<?, AOASign> aoaSign(String address, String sha3HashOfDataToSign) {
        return new Request<>(
                "aoa_sign",
                Arrays.asList(address, sha3HashOfDataToSign),
                web3jService,
                AOASign.class);
    }

    @Override
    public Request<?, AOASendTransaction>
    aoaSendTransaction(
            Transaction transaction) {
        return new Request<>(
                "aoa_sendTransaction",
                Collections.singletonList(transaction),
                web3jService,
                AOASendTransaction.class);
    }

    @Override
    public Request<?, AOASendTransaction>
    aoaSendRawTransaction(
            String signedTransactionData) {
        return new Request<>(
                "aoa_sendRawTransaction",
                Collections.singletonList(signedTransactionData),
                web3jService,
                AOASendTransaction.class);
    }

    @Override
    public Request<?, AOACall> aoaCall(
            Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "aoa_call",
                Arrays.asList(transaction, defaultBlockParameter),
                web3jService,
                AOACall.class);
    }

    @Override
    public Request<?, AOAEstimateGas> aoaEstimateGas(Transaction transaction) {
        return new Request<>(
                "aoa_estimateGas",
                Collections.singletonList(transaction),
                web3jService,
                AOAEstimateGas.class);
    }

    @Override
    public Request<?, AOABlock> aoaGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "aoa_getBlockByHash",
                Arrays.asList(
                        blockHash,
                        returnFullTransactionObjects),
                web3jService,
                AOABlock.class);
    }

    @Override
    public Request<?, AOABlock> aoaGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects) {
        return new Request<>(
                "aoa_getBlockByNumber",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        returnFullTransactionObjects),
                web3jService,
                AOABlock.class);
    }

    @Override
    public Request<?, AOATransaction> aoaGetTransactionByHash(String transactionHash) {
        return new Request<>(
                "aoa_getTransactionByHash",
                Collections.singletonList(transactionHash),
                web3jService,
                AOATransaction.class);
    }

    @Override
    public Request<?, AOATransaction> aoaGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "aoa_getTransactionByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                AOATransaction.class);
    }

    @Override
    public Request<?, AOATransaction> aoaGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) {
        return new Request<>(
                "aoa_getTransactionByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                AOATransaction.class);
    }

    @Override
    public Request<?, AOAGetTransactionReceipt> aoaGetTransactionReceipt(String transactionHash) {
        return new Request<>(
                "aoa_getTransactionReceipt",
                Collections.singletonList(transactionHash),
                web3jService,
                AOAGetTransactionReceipt.class);
    }

    @Override
    public Request<?, AOAGetCompilers> aoaGetCompilers() {
        return new Request<>(
                "aoa_getCompilers",
                Collections.<String>emptyList(),
                web3jService,
                AOAGetCompilers.class);
    }

    @Override
    public Request<?, AOACompileLLL> aoaCompileLLL(String sourceCode) {
        return new Request<>(
                "aoa_compileLLL",
                Collections.singletonList(sourceCode),
                web3jService,
                AOACompileLLL.class);
    }

    @Override
    public Request<?, AOACompileSolidity> aoaCompileSolidity(String sourceCode) {
        return new Request<>(
                "aoa_compileSolidity",
                Collections.singletonList(sourceCode),
                web3jService,
                AOACompileSolidity.class);
    }

    @Override
    public Request<?, AOACompileSerpent> aoaCompileSerpent(String sourceCode) {
        return new Request<>(
                "aoa_compileSerpent",
                Collections.singletonList(sourceCode),
                web3jService,
                AOACompileSerpent.class);
    }

    @Override
    public Request<?, AOAFilter> aoaNewFilter(
            com.aoa.web3j.core.protocol.core.methods.request.AOAFilter AOAFilter) {
        return new Request<>(
                "aoa_newFilter",
                Collections.singletonList(AOAFilter),
                web3jService,
                AOAFilter.class);
    }

    @Override
    public Request<?, AOAFilter> aoaNewBlockFilter() {
        return new Request<>(
                "aoa_newBlockFilter",
                Collections.<String>emptyList(),
                web3jService,
                AOAFilter.class);
    }

    @Override
    public Request<?, AOAFilter> aoaNewPendingTransactionFilter() {
        return new Request<>(
                "aoa_newPendingTransactionFilter",
                Collections.<String>emptyList(),
                web3jService,
                AOAFilter.class);
    }

    @Override
    public Request<?, AOAUninstallFilter> aoaUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "aoa_uninstallFilter",
                Collections.singletonList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                AOAUninstallFilter.class);
    }

    @Override
    public Request<?, AOALog> aoaGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "aoa_getFilterChanges",
                Collections.singletonList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                AOALog.class);
    }

    @Override
    public Request<?, AOALog> aoaGetFilterLogs(BigInteger filterId) {
        return new Request<>(
                "aoa_getFilterLogs",
                Collections.singletonList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                AOALog.class);
    }

    @Override
    public Request<?, AOALog> aoaGetLogs(
            com.aoa.web3j.core.protocol.core.methods.request.AOAFilter AOAFilter) {
        return new Request<>(
                "aoa_getLogs",
                Collections.singletonList(AOAFilter),
                web3jService,
                AOALog.class);
    }

    @Override
    public Request<?, DbPutString> dbPutString(
            String databaseName, String keyName, String stringToStore) {
        return new Request<>(
                "db_putString",
                Arrays.asList(databaseName, keyName, stringToStore),
                web3jService,
                DbPutString.class);
    }

    @Override
    public Request<?, DbGetString> dbGetString(String databaseName, String keyName) {
        return new Request<>(
                "db_getString",
                Arrays.asList(databaseName, keyName),
                web3jService,
                DbGetString.class);
    }

    @Override
    public Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore) {
        return new Request<>(
                "db_putHex",
                Arrays.asList(databaseName, keyName, dataToStore),
                web3jService,
                DbPutHex.class);
    }

    @Override
    public Request<?, DbGetHex> dbGetHex(String databaseName, String keyName) {
        return new Request<>(
                "db_getHex",
                Arrays.asList(databaseName, keyName),
                web3jService,
                DbGetHex.class);
    }

    @Override
    public Request<?, com.aoa.web3j.core.protocol.core.methods.response.ShhPost> shhPost(ShhPost shhPost) {
        return new Request<>(
                "shh_post",
                Collections.singletonList(shhPost),
                web3jService,
                com.aoa.web3j.core.protocol.core.methods.response.ShhPost.class);
    }

    @Override
    public Request<?, ShhVersion> shhVersion() {
        return new Request<>(
                "shh_version",
                Collections.<String>emptyList(),
                web3jService,
                ShhVersion.class);
    }

    @Override
    public Request<?, ShhNewIdentity> shhNewIdentity() {
        return new Request<>(
                "shh_newIdentity",
                Collections.<String>emptyList(),
                web3jService,
                ShhNewIdentity.class);
    }

    @Override
    public Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress) {
        return new Request<>(
                "shh_hasIdentity",
                Collections.singletonList(identityAddress),
                web3jService,
                ShhHasIdentity.class);
    }

    @Override
    public Request<?, ShhNewGroup> shhNewGroup() {
        return new Request<>(
                "shh_newGroup",
                Collections.<String>emptyList(),
                web3jService,
                ShhNewGroup.class);
    }

    @Override
    public Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress) {
        return new Request<>(
                "shh_addToGroup",
                Collections.singletonList(identityAddress),
                web3jService,
                ShhAddToGroup.class);
    }

    @Override
    public Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter) {
        return new Request<>(
                "shh_newFilter",
                Collections.singletonList(shhFilter),
                web3jService,
                ShhNewFilter.class);
    }

    @Override
    public Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "shh_uninstallFilter",
                Collections.singletonList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhUninstallFilter.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "shh_getFilterChanges",
                Collections.singletonList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhMessages.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetMessages(BigInteger filterId) {
        return new Request<>(
                "shh_getMessages",
                Collections.singletonList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhMessages.class);
    }

    @Override
    public Observable<String> ethBlockHashObservable() {
        return web3jRx.ethBlockHashObservable(blockTime);
    }

    @Override
    public Observable<String> ethPendingTransactionHashObservable() {
        return web3jRx.ethPendingTransactionHashObservable(blockTime);
    }

    @Override
    public Observable<Log> ethLogObservable(
            com.aoa.web3j.core.protocol.core.methods.request.AOAFilter AOAFilter) {
        return web3jRx.ethLogObservable(AOAFilter, blockTime);
    }

    @Override
    public Observable<com.aoa.web3j.core.protocol.core.methods.response.Transaction>
    transactionObservable() {
        return web3jRx.transactionObservable(blockTime);
    }

    @Override
    public Observable<com.aoa.web3j.core.protocol.core.methods.response.Transaction>
    pendingTransactionObservable() {
        return web3jRx.pendingTransactionObservable(blockTime);
    }

    @Override
    public Observable<AOABlock> blockObservable(boolean fullTransactionObjects) {
        return web3jRx.blockObservable(fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<AOABlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return web3jRx.replayBlocksObservable(startBlock, endBlock, fullTransactionObjects);
    }

    @Override
    public Observable<AOABlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {
        return web3jRx.replayBlocksObservable(startBlock, endBlock,
                fullTransactionObjects, ascending);
    }

    @Override
    public Observable<com.aoa.web3j.core.protocol.core.methods.response.Transaction>
    replayTransactionsObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return web3jRx.replayTransactionsObservable(startBlock, endBlock);
    }

    @Override
    public Observable<AOABlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Observable<AOABlock> onCompleteObservable) {
        return web3jRx.catchUpToLatestBlockObservable(
                startBlock, fullTransactionObjects, onCompleteObservable);
    }

    @Override
    public Observable<AOABlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.catchUpToLatestBlockObservable(startBlock, fullTransactionObjects);
    }

    @Override
    public Observable<com.aoa.web3j.core.protocol.core.methods.response.Transaction>
    catchUpToLatestTransactionObservable(DefaultBlockParameter startBlock) {
        return web3jRx.catchUpToLatestTransactionObservable(startBlock);
    }

    @Override
    public Observable<AOABlock> catchUpToLatestAndSubscribeToNewBlocksObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.catchUpToLatestAndSubscribeToNewBlocksObservable(
                startBlock, fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<com.aoa.web3j.core.protocol.core.methods.response.Transaction>
    catchUpToLatestAndSubscribeToNewTransactionsObservable(
            DefaultBlockParameter startBlock) {
        return web3jRx.catchUpToLatestAndSubscribeToNewTransactionsObservable(
                startBlock, blockTime);
    }
}
