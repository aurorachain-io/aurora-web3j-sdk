package com.aoa.web3j.console;

import com.aoa.web3j.codegen.Console;
import com.aoa.web3j.codegen.SolidityFunctionWrapperGenerator;
import com.aoa.web3j.codegen.TruffleJsonFunctionWrapperGenerator;
import com.aoa.web3j.core.utils.Version;


import static com.aoa.web3j.core.utils.Collection.tail;


/**
 * Main entry point for running command line utilities.
 */
public class Runner {

    private static String USAGE = "Usage: web3j version|wallet|solidity ...";

    private static String LOGO = "\n" // generated at http://patorjk.com/software/taag
            + "              _      _____ _     _        \n"
            + "             | |    |____ (_)   (_)       \n"
            + "__      _____| |__      / /_     _   ___  \n"
            + "\\ \\ /\\ / / _ \\ '_ \\     \\ \\ |   | | / _ \\ \n"
            + " \\ V  V /  __/ |_) |.___/ / | _ | || (_) |\n"
            + "  \\_/\\_/ \\___|_.__/ \\____/| |(_)|_| \\___/ \n"
            + "                         _/ |             \n"
            + "                        |__/              \n";

//    public static void main(String[] args) throws Exception {
//        System.out.println(LOGO);
//
//        if (args.length < 1) {
//            Console.exitError(USAGE);
//        } else {
//            switch (args[0]) {
//                case "wallet":
//                    WalletRunner.run(tail(args));
//                    break;
//                case "solidity":
//                    SolidityFunctionWrapperGenerator.run(tail(args));
//                    break;
//                case "truffle":
//                    TruffleJsonFunctionWrapperGenerator.run(tail(args));
//                    break;
//                case "version":
//                    Console.exitSuccess("Version: " + Version.getVersion() + "\n"
//                                        + "Build timestamp: " + Version.getTimestamp());
//                    break;
//                default:
//                    Console.exitError(USAGE);
//            }
//        }
//    }
}
