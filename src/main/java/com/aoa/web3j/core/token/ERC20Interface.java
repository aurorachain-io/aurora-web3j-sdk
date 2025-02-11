package com.aoa.web3j.core.token;

import com.aoa.web3j.core.protocol.core.DefaultBlockParameter;
import com.aoa.web3j.core.protocol.core.RemoteCall;
import com.aoa.web3j.core.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

import rx.Observable;

/**
 * The Aurora ERC-20 token standard.
 * <p>
 *     Implementations should provide the concrete <code>ApprovalEventResponse</code> and
 *     <code>TransferEventResponse</code> from their token as the generic types "R" amd "T".
 * </p>
 *
 * @see <a href="https://github.com/ethereum/EIPs/blob/master/EIPS/eip-20-token-standard.md">EIPs/EIPS/eip-20-token-standard.md</a>
 * @see <a href="https://github.com/ethereum/EIPs/issues/20">ERC: Token standard #20</a>
 */
@SuppressWarnings("unused")
public interface ERC20Interface<R, T> extends ERC20BasicInterface<T> {

    RemoteCall<BigInteger> allowance(String owner, String spender);

    RemoteCall<TransactionReceipt> approve(String spender, BigInteger value);

    RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger value);

    List<R> getApprovalEvents(TransactionReceipt transactionReceipt);

    Observable<R> approvalEventObservable(DefaultBlockParameter startBlock,
                                          DefaultBlockParameter endBlock);

}
