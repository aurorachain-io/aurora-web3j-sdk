package com.aoa.web3j;

import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.core.DefaultBlockParameter;
import com.aoa.web3j.core.protocol.core.Request;
import com.aoa.web3j.core.protocol.core.methods.response.AOABlock;
import com.aoa.web3j.core.protocol.core.methods.response.AOABlockNumber;
import com.aoa.web3j.core.protocol.core.methods.response.AOAGetBlockTransactionCountByHash;
import com.aoa.web3j.core.protocol.core.methods.response.AOAGetBlockTransactionCountByNumber;
import com.aoa.web3j.core.protocol.http.HttpService;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

/**
 * @author yujian    2020/05/20
 */
public class RpcMethodBlockTests {

    private Web3j web3j;

    @Before
    public void init() {
        web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
    }

    @Test
    public void blockNumber() throws Exception {
        // curl -X POST -H "Content-Type:application/json" --data '{"jsonrpc":"2.0","method":"aoa_blockNumber",
        // "params":[],"id":67}' "172.16.20.76:8545"
        Request<?, AOABlockNumber> aoaBlockNumberRequest = web3j.aoaBlockNumber();
        AOABlockNumber result = aoaBlockNumberRequest.sendAsync().get();
        System.err.printf("blockNumber: %d\n", result.getBlockNumber());
    }

    @Test
    public void getBlockByNumber() throws Exception {
        int blockNumber = 2;
        Request<?, AOABlock> aoaBlockRequest =
            web3j.aoaGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)), false);
        AOABlock aoaBlock = aoaBlockRequest.sendAsync().get();
        System.out.println(aoaBlock.getBlock());
    }

    @Test
    public void getBlockByHash() throws Exception {
        String blockHash = "0xfb027c1623b540ba6d6fd5caf121ae47bc03170d21dff55007b7aacb846a8d4f";
        Request<?, AOABlock> aoaBlockRequest = web3j.aoaGetBlockByHash(blockHash, false);
        AOABlock aoaBlock = aoaBlockRequest.sendAsync().get();
        System.out.println(aoaBlock.getBlock());
    }

    @Test
    public void getBlockTransactionCountByHash() throws Exception {
        String blockHash = "0xfb027c1623b540ba6d6fd5caf121ae47bc03170d21dff55007b7aacb846a8d4f";
        Request<?, AOAGetBlockTransactionCountByHash> request =
            web3j.aoaGetBlockTransactionCountByHash(blockHash);
        AOAGetBlockTransactionCountByHash result = request.sendAsync().get();
        System.err.printf("transactionCount:%d\n", result.getTransactionCount());
    }

    @Test
    public void getBlockTransactionCountByNumber() throws Exception {
        int blockNumber = 3;
        Request<?, AOAGetBlockTransactionCountByNumber> request =
            web3j.aoaGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)));
        AOAGetBlockTransactionCountByNumber result = request.sendAsync().get();
        System.err.printf("transactionCount:%d\n", result.getTransactionCount());
    }


    @Test
    public void blockSubscribe() throws Exception {
        web3j.blockObservable(true).subscribe(block -> {
            if (block.getResult() != null) {
                System.err.printf("blockReceive blockNumber:%d\n", block.getBlock().getNumber());
            }
        });
    }

    public static void main(String[] args) {

        Web3j web3j = Web3j.build(new HttpService("http://172.16.20.76:8545"));
        // 通过订阅模式来监听新块的产生
        web3j.blockObservable(true).subscribe(block -> {
            AOABlock.Block block1 = block.getBlock();
            List<AOABlock.TransactionResult> transactions = block1.getTransactions();
            System.err.printf("blockReceive blockNumber:%d\n", block.getBlock().getNumber());
        });
    }

}
