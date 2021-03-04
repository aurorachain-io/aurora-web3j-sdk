package com.aoa.web3j.core.protocol.core;


import com.aoa.web3j.core.protocol.core.methods.request.ShhFilter;
import com.aoa.web3j.core.protocol.core.methods.response.*;

import java.math.BigInteger;

/**
 * Core Aurora JSON-RPC API.
 */
public interface Aurora {

    Request<?, Web3ClientVersion> web3ClientVersion();

    Request<?, Web3Sha3> web3Sha3(String data);

    Request<?, NetVersion> netVersion();

    Request<?, NetListening> netListening();

    Request<?, NetPeerCount> netPeerCount();

    Request<?, AOAProtocolVersion> aoaProtocolVersion();

    Request<?, AOASyncing> aoaSyncing();

    Request<?, AOAGasPrice> aoaGasPrice();

    Request<?, AOAAccounts> aoaAccounts();

    Request<?, AOABlockNumber> aoaBlockNumber();

    Request<?, AOAGetVotesNumber> aoaGetVotesNumber(String address);

    Request<?, AOAGetDelegate> aoaGetDelegate(String address);

    Request<?, AOAGetDelegateList> aoaGetDelegateList();

    Request<?, AOAGetBalance> aoaGetBalance(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, AOAGetBalance> aoaGetAssetBalance(String address,String assetId,DefaultBlockParameter defaultBlockParameter);

    Request<?, AOAGetStorageAt> aoaGetStorageAt(String address, BigInteger position,
                                                DefaultBlockParameter defaultBlockParameter);

    Request<?, AOAGetTransactionCount> aoaGetTransactionCount(String address,
                                                              DefaultBlockParameter defaultBlockParameter);

    Request<?, AOAGetTransactionCountIncludePending> aoaGetTransactionCountIncludePending(String address);

    Request<?, AOAGetBlockTransactionCountByHash> aoaGetBlockTransactionCountByHash(String blockHash);

    Request<?, AOAGetBlockTransactionCountByNumber> aoaGetBlockTransactionCountByNumber(
        DefaultBlockParameter defaultBlockParameter);

    Request<?, AOAGetCode> aoaGetCode(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, AOASign> aoaSign(String address, String sha3HashOfDataToSign);

    Request<?, AOASendTransaction> aoaSendTransaction(
        com.aoa.web3j.core.protocol.core.methods.request.Transaction transaction);

    Request<?, AOASendTransaction> aoaSendRawTransaction(String signedTransactionData);

    Request<?, AOACall> aoaCall(com.aoa.web3j.core.protocol.core.methods.request.Transaction transaction,
                                DefaultBlockParameter defaultBlockParameter);

    Request<?, AOAEstimateGas> aoaEstimateGas(com.aoa.web3j.core.protocol.core.methods.request.Transaction transaction);

    Request<?, AOABlock> aoaGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, AOABlock> aoaGetBlockByNumber(DefaultBlockParameter defaultBlockParameter,
                                             boolean returnFullTransactionObjects);

    Request<?, AOATransaction> aoaGetTransactionByHash(String transactionHash);

    Request<?, AOATransaction> aoaGetTransactionByBlockHashAndIndex(String blockHash, BigInteger transactionIndex);

    Request<?, AOATransaction> aoaGetTransactionByBlockNumberAndIndex(DefaultBlockParameter defaultBlockParameter,
                                                                      BigInteger transactionIndex);

    Request<?, AOAGetTransactionReceipt> aoaGetTransactionReceipt(String transactionHash);

    Request<?, AOAGetCompilers> aoaGetCompilers();

    Request<?, AOACompileLLL> aoaCompileLLL(String sourceCode);

    Request<?, AOACompileSolidity> aoaCompileSolidity(String sourceCode);

    Request<?, AOACompileSerpent> aoaCompileSerpent(String sourceCode);

    Request<?, AOAFilter> aoaNewFilter(com.aoa.web3j.core.protocol.core.methods.request.AOAFilter AOAFilter);

    Request<?, AOAFilter> aoaNewBlockFilter();

    Request<?, AOAFilter> aoaNewPendingTransactionFilter();

    Request<?, AOAUninstallFilter> aoaUninstallFilter(BigInteger filterId);

    Request<?, AOALog> aoaGetFilterChanges(BigInteger filterId);

    Request<?, AOALog> aoaGetFilterLogs(BigInteger filterId);

    Request<?, AOALog> aoaGetLogs(com.aoa.web3j.core.protocol.core.methods.request.AOAFilter AOAFilter);

    Request<?, DbPutString> dbPutString(String databaseName, String keyName, String stringToStore);

    Request<?, DbGetString> dbGetString(String databaseName, String keyName);

    Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore);

    Request<?, DbGetHex> dbGetHex(String databaseName, String keyName);

    Request<?, com.aoa.web3j.core.protocol.core.methods.response.ShhPost> shhPost(
        com.aoa.web3j.core.protocol.core.methods.request.ShhPost shhPost);

    Request<?, ShhVersion> shhVersion();

    Request<?, ShhNewIdentity> shhNewIdentity();

    Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress);

    Request<?, ShhNewGroup> shhNewGroup();

    Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress);

    Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter);

    Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId);

    Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId);

    Request<?, ShhMessages> shhGetMessages(BigInteger filterId);
}
