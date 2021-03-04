package com.aoa.web3j.core.protocol.core.methods.request;

import com.aoa.web3j.core.protocol.core.methods.response.AssetInfo;
import com.aoa.web3j.crypto.Action;
import com.aoa.web3j.crypto.Vote;
import com.aoa.web3j.utils.Numeric;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Transaction request object used the below methods.
 * <ol>
 * <li>aoa_call</li>
 * <li>aoa_sendTransaction</li>
 * <li>aoa_estimateGas</li>
 * </ol>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
public class Transaction {
    // default as per https://github.com/ethereum/wiki/wiki/JSON-RPC#aoa_sendtransaction
    public static final BigInteger DEFAULT_GAS = BigInteger.valueOf(9000);

    private String from;
    private String to;
    private BigInteger gas;
    private BigInteger gasPrice;
    private BigInteger value;
    private String data;
    private BigInteger nonce;  // nonce field is not present on aoa_call/aoa_estimateGas
    private int action;
    private String nickname;
    private String asset;

    private List<Vote> vote;

    private AssetInfo assetInfo;
    private String subAddress;

    public Transaction() {
    }

    public Transaction(String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                       String to, BigInteger value, String data, int action, String nickname, String asset,
                       List<Vote> vote,
                       AssetInfo assetInfo) {
        this.from = from;
        this.to = to;
        this.gas = gasLimit;
        this.gasPrice = gasPrice;
        this.value = value;

        if (data != null) {
            this.data = Numeric.prependHexPrefix(data);
        }
        this.nonce = nonce;
        this.action = action;
        this.nickname = nickname;
        this.asset = asset;
        this.vote = vote;
        this.assetInfo = assetInfo;

    }

    public Transaction(String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                       String to, BigInteger value, String data, int action) {
        this.from = from;
        this.to = to;
        this.gas = gasLimit;
        this.gasPrice = gasPrice;
        this.value = value;

        if (data != null) {
            this.data = Numeric.prependHexPrefix(data);
        }

        this.nonce = nonce;
        this.action = action;
    }

//    public static Transaction createContractTransaction(
//        String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
//        BigInteger value, String init) {
//
//        return new Transaction(from, nonce, gasPrice, gasLimit, null, value, init,
//                               com.aoa.web3j.core.protocol.core.methods.response.Transaction.AOAAction
//                                   .CONTRACT_CREATE_TRX
//                                   .getValue());
//    }

//    public static Transaction createContractTransaction(
//        String from, BigInteger nonce, BigInteger gasPrice, String init) {
//
//        return createContractTransaction(from, nonce, gasPrice, null, null, init);
//    }

//    public static Transaction createAOATransaction(
//        String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
//        BigInteger value) {
//
//        return new Transaction(from, nonce, gasPrice, gasLimit, to, value, null,
//                               com.aoa.web3j.core.protocol.core.methods.response.Transaction.AOAAction.ORDINARY_TRX
//                                   .getValue());
//    }


    /**
     * 发送AOA交易
     */
    public static Transaction createAOATransaction(String from, String to, BigInteger nonce,
                                                   BigInteger value,
                                                   BigInteger gas, BigInteger gasPrice) {
        String subAddress = "";
        if (!org.springframework.util.StringUtils.isEmpty(to) && to.length() > 43) {
            subAddress = to;
            to = to.substring(0, to.length() - 32);

        }
        return Transaction.builder()
                .from(from)
                .to(to)
                .subAddress(subAddress)
                .nonce(nonce)
                .action(Action.ORDINARY_TRX.getValue())
                .value(value)
                .gas(gas)
                .gasPrice(gasPrice)
                .build();
    }

    /**
     * 发送注册代理交易
     */
    public static Transaction createRegisterAgentTransaction(String from, BigInteger nonce,
                                                             BigInteger gas, BigInteger gasPrice, String nickname) {
        return Transaction.builder()
                .from(from)
                .nonce(nonce)
                .action(Action.REGISTER_TRX.getValue())
                .gas(gas)
                .gasPrice(gasPrice)
                .nickname(nickname)
                .build();
    }

    /**
     * 发送投票交易
     */
    public static Transaction createVoteTransaction(String from, BigInteger nonce,
                                                    BigInteger gas, BigInteger gasPrice,
                                                    List<Vote> votes) {
        return Transaction.builder()
                .from(from)
                .nonce(nonce)
                .action(Action.VOTE_TRX.getValue())
                .gas(gas)
                .gasPrice(gasPrice)
                .vote(votes)
                .build();
    }

    /**
     * 发布多资产
     */
    public static Transaction createAssetPublishTransaction(String from, BigInteger nonce,
                                                            BigInteger gas, BigInteger gasPrice,
                                                            AssetInfo assetInfo) {
        return Transaction.builder()
                .from(from)
                .nonce(nonce)
                .action(Action.ASSET_PUBLISH_TRX.getValue())
                .gas(gas)
                .gasPrice(gasPrice)
                .assetInfo(assetInfo)
                .build();
    }

    /**
     * 多资产转账
     */
    public static Transaction createAssetTransaction(String from, BigInteger nonce,
                                                     BigInteger gas, BigInteger gasPrice, BigInteger value, String to,
                                                     String asset) {
        String subAddress = "";
        if (!org.springframework.util.StringUtils.isEmpty(to) && to.length() > 43) {
            subAddress = to;
            to = to.substring(0, to.length() - 32);

        }
        return Transaction.builder()
                .from(from)
                .to(to)
                .subAddress(subAddress)
                .nonce(nonce)
                .action(Action.ORDINARY_TRX.getValue())
                .value(value)
                .gas(gas)
                .gasPrice(gasPrice)
                .asset(asset)
                .build();
    }

    /**
     * 创建合约
     */
    public static Transaction createContractTransaction(String from, BigInteger nonce, BigInteger gasPrice,
                                                        String input) {

        String data = null;
        if (input != null && !StringUtils.isEmpty(input)) {
            data = Numeric.prependHexPrefix(input);
        }

        return Transaction.builder()
                .from(from)
                .nonce(nonce)
                .action(Action.CONTRACT_CREATE_TRX.getValue())
                .gasPrice(gasPrice)
                .data(data)
                .build();
    }

    public static Transaction createContractTransaction(String from, BigInteger nonce, BigInteger gasPrice,
                                                        String input, BigInteger value, String asset) {
        String data = null;
        if (input != null && !StringUtils.isEmpty(input)) {
            data = Numeric.prependHexPrefix(input);
        }

        return Transaction.builder()
                .from(from)
                .nonce(nonce)
                .action(Action.CONTRACT_CREATE_TRX.getValue())
                .gasPrice(gasPrice)
                .data(data)
                .value(value)
                .asset(asset)
                .build();
    }

    /**
     * 调用合约
     *
     * @param value value可为null
     */
    public static Transaction createFunctionCallTransaction(String from, BigInteger nonce, BigInteger gasPrice,
                                                            BigInteger gasLimit, String to,
                                                            BigInteger value, String data) {
        return Transaction.builder()
                .from(from)
                .nonce(nonce)
                .action(Action.CONTRACT_CALL_TRX.getValue())
                .gasPrice(gasPrice)
                .gas(gasLimit)
                .data(data)
                .to(to)
                .value(value)
                .build();
    }


//    public static Transaction createFunctionCallTransaction(
//        String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
//        BigInteger value, String data) {
//
//        return new Transaction(from, nonce, gasPrice, gasLimit, to, value, data,
//                               com.aoa.web3j.core.protocol.core.methods.response.Transaction.AOAAction
// .CONTRACT_CALL_TRX
//                                   .getValue());
//    }
//
//    public static Transaction createFunctionCallTransaction(
//        String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
//        String data) {
//
//        return new Transaction(from, nonce, gasPrice, gasLimit, to, null, data,
//                               com.aoa.web3j.core.protocol.core.methods.response.Transaction.AOAAction
// .CONTRACT_CALL_TRX
//                                   .getValue());
//    }

    public static Transaction createAOACallTransaction(String from, String to, String data) {

        return new Transaction(from, null, null, null, to, null, data,
                Action.CONTRACT_CALL_TRX.getValue());
    }


    public String getGas() {
        return convert(gas);
    }

    public String getGasPrice() {
        return convert(gasPrice);
    }

    public String getValue() {
        return convert(value);
    }

    public String getNonce() {
        return convert(nonce);
    }


    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null;  // we don't want the field to be encoded if not present
        }
    }


}
