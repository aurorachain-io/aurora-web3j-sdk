package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.core.protocol.ObjectMapperFactory;
import com.aoa.web3j.core.protocol.core.Response;
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
import java.util.Iterator;
import java.util.List;

import lombok.Data;

/**
 * Block object returned by:
 * <ul>
 * <li>aoa_getBlockByHash</li>
 * <li>aoa_getBlockByNumber</li>
 * <li>aoa_getUncleByBlockHashAndIndex</li>
 * <li>aoa_getUncleByBlockNumberAndIndex</li>
 * </ul>
 *
 * <p>See
 * <a href="https://github.com/ethereum/wiki/wiki/JSON-RPC#aoa_gettransactionbyhash">docs</a>
 * for further details.</p>
 *
 * <p>See the following <a href="https://github.com/ethcore/parity/issues/2401">issue</a> for
 * details on additional Parity fields present in AOABlock.</p>
 */

public class AOABlock extends Response<AOABlock.Block> {

    @Override
    @JsonDeserialize(using = AOABlock.ResponseDeserialiser.class)
    public void setResult(Block result) {
        super.setResult(result);
    }

    public Block getBlock() {
        return getResult();
    }

    @Data
    public static class Block {
        private String number;
        private String hash;
        private String parentHash;
        private String transactionsRoot;
        private String stateRoot;
        private String receiptsRoot;
        private String delegateRoot;
        private String validatorAddress;
        private String validator;
        private String shuffleBlockNumber;
        private String shuffleDelegateListHash;
        private String extraData;
        private String size;
        private String gasLimit;
        private String gasUsed;
        private String timestamp;
        private List<TransactionResult> transactions;

        public Block() {
        }

        public Block(String number, String hash, String parentHash, String transactionsRoot,
                     String stateRoot, String receiptsRoot, String extraData,
                     String size, String gasLimit, String gasUsed, String timestamp,
                     List<TransactionResult> transactions, String delegateRoot, String validator,
                     String validatorAddress, String shuffleBlockNumber, String shuffleDelegateListHash) {
            this.number = number;
            this.hash = hash;
            this.parentHash = parentHash;
            this.transactionsRoot = transactionsRoot;
            this.stateRoot = stateRoot;
            this.receiptsRoot = receiptsRoot;
            this.extraData = extraData;
            this.size = size;
            this.gasLimit = gasLimit;
            this.gasUsed = gasUsed;
            this.timestamp = timestamp;
            this.delegateRoot = delegateRoot;
            this.validator = validator;
            this.validatorAddress = validatorAddress;
            this.shuffleBlockNumber = shuffleBlockNumber;
            this.shuffleDelegateListHash = shuffleDelegateListHash;
            this.transactions = transactions;

        }

        public BigInteger getNumber() {
            if(number.startsWith("0x")){
                return Numeric.decodeQuantity(number);
            }
            return new BigInteger(number);
        }

        public String getNumberRaw() {
            return number;
        }

        public BigInteger getShuffleBlockNumber() {
            return Numeric.decodeQuantity(shuffleBlockNumber);
        }

        public String getShuffleBlockNumberRaw() {
            return shuffleBlockNumber;
        }

        public BigInteger getSize() {
            if(size.startsWith("0x")){
                return Numeric.decodeQuantity(size);
            }
            return new BigInteger(size);
        }

        public String getSizeRaw() {
            return size;
        }

        public BigInteger getGasLimit() {
            if(gasLimit.startsWith("0x")){
                return Numeric.decodeQuantity(gasLimit);
            }
            return new BigInteger(gasLimit);
        }

        public String getGasLimitRaw() {
            return gasLimit;
        }

        public BigInteger getGasUsed() {
            if(gasUsed.startsWith("0x")){
                return Numeric.decodeQuantity(gasUsed);
            }
            return new BigInteger(gasUsed);
        }

        public String getGasUsedRaw() {
            return gasUsed;
        }

        public BigInteger getTimestamp() {
            if(timestamp.startsWith("0x")){
                return Numeric.decodeQuantity(timestamp);
            }
            return new BigInteger(timestamp);
        }

        public String getTimestampRaw() {
            return timestamp;
        }

        @JsonDeserialize(using = ResultTransactionDeserialiser.class)
        public void setTransactions(List<TransactionResult> transactions) {
            this.transactions = transactions;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Block)) {
                return false;
            }

            Block block = (Block) o;

            if (getNumberRaw() != null
                ? !getNumberRaw().equals(block.getNumberRaw()) : block.getNumberRaw() != null) {
                return false;
            }

            if (getShuffleBlockNumberRaw() != null
                ? !getShuffleBlockNumberRaw().equals(block.getShuffleBlockNumberRaw()) :
                block.getShuffleBlockNumberRaw() != null) {
                return false;
            }
            if (getHash() != null ? !getHash().equals(block.getHash()) : block.getHash() != null) {
                return false;
            }
            if (getParentHash() != null
                ? !getParentHash().equals(block.getParentHash())
                : block.getParentHash() != null) {
                return false;
            }
            if (getTransactionsRoot() != null
                ? !getTransactionsRoot().equals(block.getTransactionsRoot())
                : block.getTransactionsRoot() != null) {
                return false;
            }
            if (getStateRoot() != null
                ? !getStateRoot().equals(block.getStateRoot())
                : block.getStateRoot() != null) {
                return false;
            }
            if (getReceiptsRoot() != null
                ? !getReceiptsRoot().equals(block.getReceiptsRoot())
                : block.getReceiptsRoot() != null) {
                return false;
            }

            if (getDelegateRoot() != null
                ? !getDelegateRoot().equals(block.getDelegateRoot())
                : block.getDelegateRoot() != null) {
                return false;
            }

            if (getExtraData() != null
                ? !getExtraData().equals(block.getExtraData())
                : block.getExtraData() != null) {
                return false;
            }
            if (getSizeRaw() != null
                ? !getSizeRaw().equals(block.getSizeRaw())
                : block.getSizeRaw() != null) {
                return false;
            }
            if (getGasLimitRaw() != null
                ? !getGasLimitRaw().equals(block.getGasLimitRaw())
                : block.getGasLimitRaw() != null) {
                return false;
            }
            if (getGasUsedRaw() != null
                ? !getGasUsedRaw().equals(block.getGasUsedRaw())
                : block.getGasUsedRaw() != null) {
                return false;
            }
            if (getTimestampRaw() != null
                ? !getTimestampRaw().equals(block.getTimestampRaw())
                : block.getTimestampRaw() != null) {
                return false;
            }
            if (getTransactions() != null
                ? !getTransactions().equals(block.getTransactions())
                : block.getTransactions() != null) {
                return false;
            }

            if (getShuffleDelegateListHash() != null
                ? !getShuffleDelegateListHash().equals(block.getShuffleDelegateListHash())
                : block.getShuffleDelegateListHash() != null) {
                return false;
            }

            if (getShuffleDelegateListHash() != null
                ? !getShuffleDelegateListHash().equals(block.getShuffleDelegateListHash())
                : block.getShuffleDelegateListHash() != null) {
                return false;
            }

            if (getValidator() != null
                ? !getValidator().equals(block.getValidator())
                : block.getValidator() != null) {
                return false;
            }

            if (getValidatorAddress() != null
                ? !getValidatorAddress().equals(block.getValidatorAddress())
                : block.getValidatorAddress() != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = getNumberRaw() != null ? getNumberRaw().hashCode() : 0;
            result = 31 * result + (getShuffleBlockNumberRaw() != null ? getShuffleBlockNumberRaw().hashCode() : 0);
            result = 31 * result + (getHash() != null ? getHash().hashCode() : 0);
            result = 31 * result + (getParentHash() != null ? getParentHash().hashCode() : 0);
            result = 31 * result + (getTransactionsRoot() != null ? getTransactionsRoot().hashCode() : 0);
            result = 31 * result + (getStateRoot() != null ? getStateRoot().hashCode() : 0);
            result = 31 * result + (getReceiptsRoot() != null ? getReceiptsRoot().hashCode() : 0);
            result = 31 * result + (getDelegateRoot() != null ? getDelegateRoot().hashCode() : 0);
            result = 31 * result + (getShuffleDelegateListHash() != null ? getShuffleDelegateListHash().hashCode() : 0);
            result = 31 * result + (getValidatorAddress() != null ? getValidatorAddress().hashCode() : 0);
            result = 31 * result + (getValidator() != null ? getValidator().hashCode() : 0);
            result = 31 * result + (getExtraData() != null ? getExtraData().hashCode() : 0);
            result = 31 * result + (getSizeRaw() != null ? getSizeRaw().hashCode() : 0);
            result = 31 * result + (getGasLimitRaw() != null ? getGasLimitRaw().hashCode() : 0);
            result = 31 * result + (getGasUsedRaw() != null ? getGasUsedRaw().hashCode() : 0);
            result = 31 * result + (getTimestampRaw() != null ? getTimestampRaw().hashCode() : 0);
            result = 31 * result + (getTransactions() != null ? getTransactions().hashCode() : 0);
            return result;
        }
    }

    public interface TransactionResult<T> {
        T get();
    }

    public static class TransactionHash implements TransactionResult<String> {
        private String value;

        public TransactionHash() {
        }

        public TransactionHash(String value) {
            this.value = value;
        }

        @Override
        public String get() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TransactionHash)) {
                return false;
            }

            TransactionHash that = (TransactionHash) o;

            return value != null ? value.equals(that.value) : that.value == null;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }

    public static class TransactionObject extends Transaction
        implements TransactionResult<Transaction> {
        public TransactionObject() {
        }

        public TransactionObject(String hash, String nonce, String blockHash, String blockNumber,
                                 String transactionIndex, String from, String to, String value,
                                 String gasPrice, String gas, String input, String creates,
                                 String publicKey, String raw, String r, String s, int v) {
            super(hash, nonce, blockHash, blockNumber, transactionIndex, from, to, value,
                  gasPrice, gas, input, creates, publicKey, raw, r, s, Integer.toString(v));
        }

        @Override
        public Transaction get() {
            return this;
        }
    }

    public static class ResultTransactionDeserialiser
        extends JsonDeserializer<List<TransactionResult>> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public List<TransactionResult> deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {

            List<TransactionResult> transactionResults = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<TransactionObject> transactionObjectIterator =
                    objectReader.readValues(jsonParser, TransactionObject.class);
                while (transactionObjectIterator.hasNext()) {
                    transactionResults.add(transactionObjectIterator.next());
                }
            } else if (nextToken == JsonToken.VALUE_STRING) {
                jsonParser.getValueAsString();

                Iterator<TransactionHash> transactionHashIterator =
                    objectReader.readValues(jsonParser, TransactionHash.class);
                while (transactionHashIterator.hasNext()) {
                    transactionResults.add(transactionHashIterator.next());
                }
            }

            return transactionResults;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<Block> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Block deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Block.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
