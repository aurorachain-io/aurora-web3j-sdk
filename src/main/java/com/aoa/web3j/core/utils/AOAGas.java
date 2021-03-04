package com.aoa.web3j.core.utils;

import com.aoa.web3j.utils.Convert;

import java.math.BigDecimal;

public class AOAGas {

    public static BigDecimal defaultGasPrice = Convert.toWei("4", Convert.Unit.GWEI);
    public static BigDecimal defaultTrxGas = Convert.toWei("25000", Convert.Unit.WEI);
    public static BigDecimal defaultAssetPublishGas = Convert.toWei("100000", Convert.Unit.WEI);
    public static BigDecimal defaultCallErc20Gas = Convert.toWei("70000", Convert.Unit.WEI);

}
