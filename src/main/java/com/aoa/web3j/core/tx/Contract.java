package com.aoa.web3j.core.tx;



import com.aoa.web3j.abi.EventEncoder;
import com.aoa.web3j.abi.EventValues;
import com.aoa.web3j.abi.FunctionEncoder;
import com.aoa.web3j.abi.FunctionReturnDecoder;
import com.aoa.web3j.abi.TypeReference;
import com.aoa.web3j.abi.datatypes.Address;
import com.aoa.web3j.abi.datatypes.Event;
import com.aoa.web3j.abi.datatypes.Function;
import com.aoa.web3j.abi.datatypes.Type;
import com.aoa.web3j.core.protocol.Web3j;
import com.aoa.web3j.core.protocol.core.DefaultBlockParameterName;
import com.aoa.web3j.core.protocol.core.RemoteCall;
import com.aoa.web3j.core.protocol.core.methods.request.Transaction;
import com.aoa.web3j.core.protocol.core.methods.response.AOACall;
import com.aoa.web3j.core.protocol.core.methods.response.AOAGetCode;
import com.aoa.web3j.core.protocol.core.methods.response.Log;
import com.aoa.web3j.core.protocol.core.methods.response.TransactionReceipt;
import com.aoa.web3j.core.protocol.exceptions.TransactionException;
import com.aoa.web3j.core.tx.exceptions.ContractCallException;
import com.aoa.web3j.crypto.Credentials;
import com.aoa.web3j.utils.Numeric;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Solidity contract type abstraction for interacting with smart contracts via native Java types.
 */
@SuppressWarnings("WeakerAccess")
public abstract class Contract extends ManagedTransaction {

    // https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    protected final String contractBinary;
    protected String contractAddress;
    protected BigInteger gasPrice;
    protected BigInteger gasLimit;
    protected TransactionReceipt transactionReceipt;
    protected Map<String, String> deployedAddresses;

    protected Contract(String contractBinary, String contractAddress,
                       Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit) {
        super(web3j, transactionManager);

        this.contractAddress = ensResolver.resolve(contractAddress);

        this.contractBinary = contractBinary;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
    }

    protected Contract(String contractBinary, String contractAddress,
                       Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit) {
        this(contractBinary, contractAddress, web3j, new RawTransactionManager(web3j, credentials),
                gasPrice, gasLimit);
    }

    @Deprecated
    protected Contract(String contractAddress,
                       Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit) {
        this("", contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    @Deprecated
    protected Contract(String contractAddress,
                       Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit) {
        this("", contractAddress, web3j, new RawTransactionManager(web3j, credentials),
                gasPrice, gasLimit);
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    public String getContractBinary() {
        return contractBinary;
    }
    
    /**
     * Allow {@code gasPrice} to be set.
     * @param newPrice gas price to use for subsequent transactions
     */
    public void setGasPrice(BigInteger newPrice) {
        this.gasPrice = newPrice;
    }

    /**
     * Get the current {@code gasPrice} value this contract uses when executing transactions.
     * @return the gas price set on this contract
     */
    public BigInteger getGasPrice() {
        return gasPrice;
    }

    /**
     * Check that the contract deployed at the address associated with this smart contract wrapper
     * is in fact the contract you believe it is.
     *
     * <p>This method uses the
     * <a href="https://github.com/ethereum/wiki/wiki/JSON-RPC#aoa_getcode">aoa_getCode</a> method
     * to get the contract byte code and validates it against the byte code stored in this smart
     * contract wrapper.
     *
     * @return true if the contract is valid
     * @throws IOException if unable to connect to web3j node
     */
    public boolean isValid() throws IOException {
        if (contractAddress.equals("")) {
            throw new UnsupportedOperationException(
                    "Contract binary not present, you will need to regenerate your smart "
                            + "contract wrapper with web3j v2.2.0+");
        }

        AOAGetCode ethGetCode = web3j
                .aoaGetCode(contractAddress, DefaultBlockParameterName.LATEST)
                .send();
        if (ethGetCode.hasError()) {
            return false;
        }

        String code = Numeric.cleanHexOrAoaPrefix(ethGetCode.getCode());
        // There may be multiple contracts in the Solidity bytecode, hence we only check for a
        // match with a subset
        return !code.isEmpty() && contractBinary.contains(code);
    }

    /**
     * If this Contract instance was created at deployment, the TransactionReceipt associated
     * with the initial creation will be provided, e.g. via a <em>deploy</em> method. This will
     * not persist for Contracts instances constructed via a <em>load</em> method.
     *
     * @return the TransactionReceipt generated at contract deployment
     */
    public Optional<TransactionReceipt> getTransactionReceipt() {
        return Optional.ofNullable(transactionReceipt);
    }

    /**
     * Execute constant function call - i.e. a call that does not change state of the contract
     *
     * @param function to call
     * @return {@link List} of values returned by function call
     */
    private List<Type> executeCall(
            Function function) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        AOACall ethCall = web3j.aoaCall(
                Transaction.createAOACallTransaction(
                        transactionManager.getFromAddress(), contractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .send();

        String value = ethCall.getValue();
        return FunctionReturnDecoder.decode(value, function.getOutputParameters());
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type> T executeCallSingleValueReturn(
            Function function) throws IOException {
        List<Type> values = executeCall(function);
        if (!values.isEmpty()) {
            return (T) values.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type, R> R executeCallSingleValueReturn(
            Function function, Class<R> returnType) throws IOException {
        T result = executeCallSingleValueReturn(function);
        if (result == null) {
            throw new ContractCallException("Empty value (0x) returned from contract");
        }

        Object value = result.getValue();
        if (returnType.isAssignableFrom(value.getClass())) {
            return (R) value;
        } else if (result.getClass().equals(Address.class) && returnType.equals(String.class)) {
            return (R) result.toString();  // cast isn't necessary
        } else {
            throw new ContractCallException(
                    "Unable to convert response: " + value
                            + " to expected type: " + returnType.getSimpleName());
        }
    }

    protected List<Type> executeCallMultipleValueReturn(
            Function function) throws IOException {
        return executeCall(function);
    }

    protected TransactionReceipt executeTransaction(
            Function function)
            throws IOException, TransactionException {
        return executeTransaction(function, BigInteger.ZERO);
    }

    private TransactionReceipt executeTransaction(
            Function function, BigInteger weiValue)
            throws IOException, TransactionException {
        return executeTransaction(FunctionEncoder.encode(function), weiValue);
    }

    /**
     * Given the duration required to execute a transaction.
     *
     * @param data  to send in transaction
     * @param weiValue in Wei to send in transaction
     * @return {@link Optional} containing our transaction receipt
     * @throws IOException                 if the call to the node fails
     * @throws TransactionException if the transaction was not mined while waiting
     */
    TransactionReceipt executeTransaction(
            String data, BigInteger weiValue)
            throws TransactionException, IOException {

        return send(contractAddress, data, weiValue, gasPrice, gasLimit);
    }

    protected <T extends Type> RemoteCall<T> executeRemoteCallSingleValueReturn(Function function) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function));
    }

    protected <T> RemoteCall<T> executeRemoteCallSingleValueReturn(
            Function function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function, returnType));
    }

    protected RemoteCall<List<Type>> executeRemoteCallMultipleValueReturn(Function function) {
        return new RemoteCall<>(() -> executeCallMultipleValueReturn(function));
    }

    protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(Function function) {
        return new RemoteCall<>(() -> executeTransaction(function));
    }

    protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(
            Function function, BigInteger weiValue) {
        return new RemoteCall<>(() -> executeTransaction(function, weiValue));
    }

    private static <T extends Contract> T create(
            T contract, String binary, String encodedConstructor, BigInteger value)
            throws IOException, TransactionException {
        TransactionReceipt transactionReceipt =
                contract.executeTransaction(binary + encodedConstructor, value);

        String contractAddress = transactionReceipt.getContractAddress();
        if (contractAddress == null) {
            throw new RuntimeException("Empty contract address returned");
        }
        contract.setContractAddress(contractAddress);
        contract.setTransactionReceipt(transactionReceipt);

        return contract;
    }

    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger value) throws
            IOException, TransactionException {

        try {
            Constructor<T> constructor = type.getDeclaredConstructor(
                    String.class,
                    Web3j.class, Credentials.class,
                    BigInteger.class, BigInteger.class);
            constructor.setAccessible(true);

            // we want to use null here to ensure that "to" parameter on message is not populated
            T contract = constructor.newInstance(null, web3j, credentials, gasPrice, gasLimit);

            return create(contract, binary, encodedConstructor, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger value)
            throws IOException, TransactionException {

        try {
            Constructor<T> constructor = type.getDeclaredConstructor(
                    String.class,
                    Web3j.class, TransactionManager.class,
                    BigInteger.class, BigInteger.class);
            constructor.setAccessible(true);

            // we want to use null here to ensure that "to" parameter on message is not populated
            T contract = constructor.newInstance(
                    null, web3j, transactionManager, gasPrice, gasLimit);
            return create(contract, binary, encodedConstructor, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger value) {
        return new RemoteCall<>(() -> deploy(
                type, web3j, credentials, gasPrice, gasLimit, binary,
                encodedConstructor, value));
    }

    protected static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor) {
        return deployRemoteCall(
                type, web3j, credentials, gasPrice, gasLimit,
                binary, encodedConstructor, BigInteger.ZERO);
    }

    protected static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor, BigInteger value) {
        return new RemoteCall<>(() -> deploy(
                type, web3j, transactionManager, gasPrice, gasLimit, binary,
                encodedConstructor, value));
    }

    protected static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit,
            String binary, String encodedConstructor) {
        return deployRemoteCall(
                type, web3j, transactionManager, gasPrice, gasLimit, binary,
                encodedConstructor, BigInteger.ZERO);
    }

    public static EventValues staticExtractEventParameters(
        Event event, Log log) {

        List<String> topics = log.getTopics();
        String encodedEventSignature = EventEncoder.encode(event);
        if (!topics.get(0).equals(encodedEventSignature)) {
            return null;
        }

        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues = FunctionReturnDecoder.decode(
                log.getData(), event.getNonIndexedParameters());

        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value = FunctionReturnDecoder.decodeIndexedValue(
                    topics.get(i + 1), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }

    protected EventValues extractEventParameters(Event event, Log log) {
        return staticExtractEventParameters(event, log);
    }

    protected List<EventValues> extractEventParameters(
            Event event, TransactionReceipt transactionReceipt) {
        return transactionReceipt.getLogs().stream()
                .map(log -> extractEventParameters(event, log))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected EventValuesWithLog extractEventParametersWithLog(Event event, Log log) {
        final EventValues eventValues = staticExtractEventParameters(event, log);
        return (eventValues == null) ? null : new EventValuesWithLog(eventValues, log);
    }

    protected List<EventValuesWithLog> extractEventParametersWithLog(
            Event event, TransactionReceipt transactionReceipt) {
        return transactionReceipt.getLogs().stream()
                .map(log -> extractEventParametersWithLog(event, log))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Subclasses should implement this method to return pre-existing addresses for deployed
     * contracts.
     *
     * @param networkId the network id, for example "1" for the main-net, "3" for ropsten, etc.
     * @return the deployed address of the contract, if known, and null otherwise.
     */
    protected String getStaticDeployedAddress(String networkId) {
        return null;
    }

    public final void setDeployedAddress(String networkId, String address) {
        if (deployedAddresses == null) {
            deployedAddresses = new HashMap<>();
        }
        deployedAddresses.put(networkId, address);
    }

    public final String getDeployedAddress(String networkId) {
        String addr = null;
        if (deployedAddresses != null) {
            addr = deployedAddresses.get(networkId);
        }
        return addr == null ? getStaticDeployedAddress(networkId) : addr;
    }

    /**
     * Adds a log field to {@link EventValues}.
     */
    public static class EventValuesWithLog {
        private final EventValues eventValues;
        private final Log log;

        private EventValuesWithLog(EventValues eventValues, Log log) {
            this.eventValues = eventValues;
            this.log = log;
        }

        public List<Type> getIndexedValues() {
            return eventValues.getIndexedValues();
        }

        public List<Type> getNonIndexedValues() {
            return eventValues.getNonIndexedValues();
        }

        public Log getLog() {
            return log;
        }
    }

    @SuppressWarnings("unchecked")
    protected static <S extends Type, T> 
            List<T> convertToNative(List<S> arr) {
        List<T> out = new ArrayList<T>();
        for (Iterator<S> it = arr.iterator(); it.hasNext(); ) {
            out.add((T)it.next().getValue());
        }
        return out;
    }
}
