package com.aoa.web3j.crypto;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aoa.web3j.core.protocol.core.methods.request.AssetInfo;
import com.aoa.web3j.core.protocol.core.methods.request.Transaction;
import com.aoa.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Transaction class used for signing transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">
 * yellow paper</a>.
 */
@Data
public class RawTransaction {

    private BigInteger nonce;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String to;
    private BigInteger value;
    private String data = "";
    private BigInteger action;
    private byte[] vote = new byte[0];
    private byte[] nickname = new byte[0];
    private String asset;
    private byte[] assetInfo = new byte[0];
    private String subAddress = "";
    private String abi = "";

    public RawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                          BigInteger value, String data, String asset, Action action) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        if (!org.springframework.util.StringUtils.isEmpty(to) && to.length() > 43) {
            this.subAddress = to;
            this.to = to.substring(0,to.length() - 32);

        }
        this.value = value;
        this.action = BigInteger.valueOf(action.getValue());
        if (data != null) {
            this.data = Numeric.cleanHexOrAoaPrefix(data);
        }
        this.asset = asset;
    }

    public RawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                          BigInteger value, String data, Action action, byte[] vote, byte[] nickname, String asset,
                          AssetInfo assetInfo, String abi) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        if (!org.springframework.util.StringUtils.isEmpty(to) && to.length() > 43) {
            this.subAddress = to;
            this.to = to.substring(0,to.length() - 32);

        }
        this.value = Optional.ofNullable(value).orElse(BigInteger.ZERO);
        this.action = BigInteger.valueOf(action.getValue());
        if (vote != null && vote.length > 0) {
            this.vote = vote;
        }
        if (nickname != null && nickname.length > 0) {
            this.nickname = nickname;
        }
        this.asset = asset;
        if (data != null) {
            this.data = Numeric.cleanHexOrAoaPrefix(data);
        }
        if (assetInfo != null) {
            // byte[] assetInfoBytes = JSON.toJSONBytes(assetInfo, SerializerFeature.NotWriteDefaultValue);
            //this.assetInfo = JSON.toJSONBytes(assetInfo, SerializerFeature.NotWriteDefaultValue);
            this.assetInfo = JSON.toJSONString(assetInfo, SerializerFeature.NotWriteDefaultValue).getBytes();
        }
        this.abi = Optional.ofNullable(abi).orElse("");
        this.subAddress = "";
    }

    /**
     * 发送AOA交易
     */
    public static RawTransaction createAOATransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                                                      String to, BigInteger value) {
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, "", "", Action.ORDINARY_TRX);
    }

    /**
     * 发送注册代理交易
     */
    public static RawTransaction createRegisterAgentTransaction(BigInteger nonce, BigInteger gasPrice,
                                                                BigInteger gasLimit, String nickname) {
        return new RawTransaction(nonce, gasPrice, gasLimit, null, null, null, Action.REGISTER_TRX, null,
                                  nickname.getBytes(), null, null, null);
    }

    /**
     * 发送投票交易
     */
    public static RawTransaction createVoteTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                                                       List<Vote> voteList) {
        byte[] voteBytes = JSON.toJSONBytes(voteList, SerializerFeature.NotWriteDefaultValue);
        return new RawTransaction(nonce, gasPrice, gasLimit, null, null, "", Action.VOTE_TRX, voteBytes, null, null,
                                  null, null);
    }

    /**
     * 发布多资产
     */
    public static RawTransaction createAssetPublishTransaction(BigInteger nonce, BigInteger gasPrice,
                                                               BigInteger gasLimit, AssetInfo assetInfo) {
        //byte[] assetInfoBytes = JSON.toJSONBytes(assetInfo, SerializerFeature.NotWriteDefaultValue);
        return new RawTransaction(nonce, gasPrice, gasLimit, null, null, "", Action.ASSET_PUBLISH_TRX, null, null, null,
                                  assetInfo, null);
    }

    /**
     * 多资产转账
     */
    public static RawTransaction createAssetTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                                                        String to, BigInteger value, String assetID) {
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, "", assetID, Action.ORDINARY_TRX);
    }

    /**
     * 合约创建
     */
    public static RawTransaction createContractTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                                                           String data) {
        return new RawTransaction(nonce, gasPrice, gasLimit, "", null, data, Action.CONTRACT_CREATE_TRX, null, null,
                                  null, null, null);
    }

    /**
     * 合约调用
     */
    public static RawTransaction createFunctionCallTransaction(BigInteger nonce, BigInteger gasPrice,
                                                               BigInteger gasLimit, String to, BigInteger value,
                                                               String data) {
        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, Action.CONTRACT_CALL_TRX, null, null,
                                  null, null, null);
    }


    public static RawTransaction createTransaction(
        BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
        BigInteger value, String data) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, null, Action.ORDINARY_TRX);
    }


}




