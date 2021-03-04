package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.ObjectMapperFactory;
import com.aoa.web3j.crypto.Action;
import com.aoa.web3j.utils.Numeric;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Transaction object used by both {@link AOATransaction} and {@link AOABlock}.
 */
@Data
@Builder
@AllArgsConstructor
public class Transaction {
    private String hash;
    private String nonce;
    private String blockHash;
    private String blockNumber;
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    private String gasPrice;
    private String gas;
    private String input;
    private String creates;
    private String publicKey;
    private String raw;
    private String r;
    private String s;
    private String v;  // see https://github.com/web3j/web3j/issues/44

    private int action;
    private String nickname;
    private String asset;
    private String subAddress;
    private String abi;

    private List<VoteInterface> votes;

    private AoAAssetInfo.AssetInfo assetInfo;

    public Transaction() {
    }


    public Transaction(String hash, String nonce, String blockHash, String blockNumber,
                       String transactionIndex, String from, String to, String value,
                       String gas, String gasPrice, String input, String creates,
                       String publicKey, String raw, String r, String s, String v) {
        this.hash = hash;
        this.nonce = nonce;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.transactionIndex = transactionIndex;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gasPrice = gasPrice;
        this.gas = gas;
        this.input = input;
        this.creates = creates;
        this.publicKey = publicKey;
        this.raw = raw;
        this.r = r;
        this.s = s;
        this.v = v;
    }


    public Transaction(String hash, String nonce, String blockHash, String blockNumber,
                       String transactionIndex, String from, String to, String value,
                       String gas, String gasPrice, String input, String creates,
                       String publicKey, String raw, String r, String s, String v, int action, String nickname,
                       List<VoteInterface> votes, String asset, String subAddress, AoAAssetInfo.AssetInfo assetInfo,
                       String abi) {
        this.hash = hash;
        this.nonce = nonce;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.transactionIndex = transactionIndex;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gasPrice = gasPrice;
        this.gas = gas;
        this.input = input;
        this.creates = creates;
        this.publicKey = publicKey;
        this.raw = raw;
        this.r = r;
        this.s = s;
        this.v = v;
        this.action = action;
        this.nickname = nickname;
        this.votes = votes;
        this.asset = asset;
        this.subAddress = subAddress;
        this.assetInfo = assetInfo;
        this.abi = abi;
    }


    public BigInteger getNonce() {
        if(nonce.startsWith("0x")){
            return Numeric.decodeQuantity(nonce);
        }
        return new BigInteger(nonce);
    }

    public String getNonceRaw() {
        return nonce;
    }


    public BigInteger getBlockNumber() {
        if(blockNumber.startsWith("0x")){
            return Numeric.decodeQuantity(blockNumber);
        }
        return new BigInteger(blockNumber);
    }

    public String getBlockNumberRaw() {
        return blockNumber;
    }


    public BigInteger getTransactionIndex() {
        if(transactionIndex.startsWith("0x")){
            return Numeric.decodeQuantity(transactionIndex);
        }
        return new BigInteger(transactionIndex);
    }

    public String getTransactionIndexRaw() {
        return transactionIndex;
    }


    public BigInteger getValue() {
        if(value.startsWith("0x")){
            return Numeric.decodeQuantity(value);
        }
        return new BigInteger(value);
    }

    public String getValueRaw() {
        return value;
    }


    public BigInteger getGasPrice() {
        if(gasPrice.startsWith("0x")){
            return Numeric.decodeQuantity(gasPrice);
        }
        return new BigInteger(gasPrice);
    }

    public String getGasPriceRaw() {
        return gasPrice;
    }


    public BigInteger getGas() {
        if(gas.startsWith("0x")){
            return Numeric.decodeQuantity(gas);
        }
        return new BigInteger(gas);
    }

    public String getGasRaw() {
        return gas;
    }

    public Action getTransactionAction() {
        return Action.getAction(action);
    }



    @JsonDeserialize(using = ResultVoteDeserialiser.class)
    public void setVotes(List<VoteInterface> votes) {
        this.votes = votes;
    }

    // public void setV(byte v) {
    //     this.v = v;
    // }

    // Workaround until Aoa & Parity return consistent values. At present
    // Parity returns a byte value, Aoa returns a hex-encoded string
    // https://github.com/ethereum/go-ethereum/issues/3339
//    public void setV(Object v) {
//        if (v instanceof String) {
//            this.v = Numeric.toBigInt((String) v).intValueExact();
//        } else {
//            this.v = ((Integer) v);
//        }
//    }

    public interface VoteInterface<T> {
        T get();
    }


    public static class ResultVoteDeserialiser
        extends JsonDeserializer<List<VoteInterface>> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public List<VoteInterface> deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {

            List<VoteInterface> list = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<VoteOperationObject> delegateObjectIterator =
                    objectReader.readValues(jsonParser, VoteOperationObject.class);
                while (delegateObjectIterator.hasNext()) {
                    list.add(delegateObjectIterator.next());
                }
            } else if (nextToken == JsonToken.VALUE_STRING) {
                jsonParser.getValueAsString();

                Iterator<VoteOperationObject> delegateObjectIterator =
                    objectReader.readValues(jsonParser, VoteOperationObject.class);
                while (delegateObjectIterator.hasNext()) {
                    list.add(delegateObjectIterator.next());
                }
            }

            return list;
        }
    }


    public static class VoteOperationObject extends AOAVoteOperation.VoteOperation
        implements VoteInterface<AOAVoteOperation.VoteOperation> {
        public VoteOperationObject() {
        }

        public VoteOperationObject(String candidate, int operation) {
            super(candidate, operation);
        }

        @Override
        public AOAVoteOperation.VoteOperation get() {
            return this;
        }
    }



}


