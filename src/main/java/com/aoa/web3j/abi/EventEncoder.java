package com.aoa.web3j.abi;


import com.aoa.web3j.abi.datatypes.Event;
import com.aoa.web3j.abi.datatypes.Type;
import com.aoa.web3j.utils.Numeric;
import com.aoa.web3j.utils.crypto.Hash;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Ethereum filter encoding.
 * Further limited details are available
 * <a href="https://github.com/ethereum/wiki/wiki/Ethereum-Contract-ABI#events">here</a>.
 * </p>
 */
public class EventEncoder {

    private EventEncoder() { }

    public static String encode(Event function) {
        List<TypeReference<Type>> indexedParameters = function.getIndexedParameters();
        List<TypeReference<Type>> nonIndexedParameters = function.getNonIndexedParameters();

        String methodSignature = buildMethodSignature(function.getName(),
                indexedParameters, nonIndexedParameters);

        return buildEventSignature(methodSignature);
    }

    static <T extends Type> String buildMethodSignature(
            String methodName, List<TypeReference<T>> indexParameters,
            List<TypeReference<T>> nonIndexedParameters) {

        List<TypeReference<T>> parameters = new ArrayList<>(indexParameters);
        parameters.addAll(nonIndexedParameters);

        StringBuilder result = new StringBuilder();
        result.append(methodName);
        result.append("(");
        String params = parameters.stream()
                .map(p -> Utils.getTypeName(p))
                .collect(Collectors.joining(","));
        result.append(params);
        result.append(")");
        return result.toString();
    }

    public static String buildEventSignature(String methodSignature) {
        byte[] input = methodSignature.getBytes();
        byte[] hash = Hash.sha3(input);
        return Numeric.toHexString(hash);
    }
}
