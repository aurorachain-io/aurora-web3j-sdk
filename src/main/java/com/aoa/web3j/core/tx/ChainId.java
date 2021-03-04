package com.aoa.web3j.core.tx;

/**
 * Aurora chain ids as per
 * <a href="https://github.com/ethereum/EIPs/blob/master/EIPS/eip-155.md">EIP-155</a>.
 */
public class ChainId {
    public static final byte NONE = -1;
    public static final byte MAINNET = 1;
    public static final byte TESTNET = 3;
    public static final byte DEVNET = 60;
}
