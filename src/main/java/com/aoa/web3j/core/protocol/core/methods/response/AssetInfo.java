package com.aoa.web3j.core.protocol.core.methods.response;

import com.aoa.web3j.utils.Numeric;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;


@Data
public class AssetInfo implements Serializable {

    private static final long serialVersionUID = 3643015059444703638L;


    /**
     * asset name
     */
    private String name;
    /**
     * the short name of asset
     */
    private String symbol;

    /**
     * the total supply of asset, the unit is wei
     */
    private BigInteger supply;
    /**
     * the description of asset
     */
    private String desc;

    public AssetInfo() {
    }

    public AssetInfo(String name, String symbol, BigInteger supply, String desc) {
        this.name = name;
        this.symbol = symbol;
        this.supply = supply;
        this.desc = desc;
    }

    public String getSupply() {
        return convert(supply);
    }

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null;  // we don't want the field to be encoded if not present
        }
    }


}
