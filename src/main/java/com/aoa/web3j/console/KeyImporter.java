package com.aoa.web3j.console;


import com.aoa.web3j.core.utils.Files;
import com.aoa.web3j.crypto.CipherException;
import com.aoa.web3j.crypto.Credentials;
import com.aoa.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

import static com.aoa.web3j.codegen.Console.exitError;
import static com.aoa.web3j.crypto.Keys.PRIVATE_KEY_LENGTH_IN_HEX;


/**
 * Create Aurora wallet file from a provided private key.
 */
public class KeyImporter extends WalletManager {

    public KeyImporter() {
    }

    public KeyImporter(IODevice console) {
        super(console);
    }

//    public static void main(String[] args) {
//        if (args.length == 1) {
//            new KeyImporter().run(args[0]);
//        } else {
//            new KeyImporter().run();
//        }
//    }


    public static void run(String[] args) {
        if (args.length == 1) {
            new KeyImporter().run(args[0]);
        } else {
            new KeyImporter().run();
        }
    }

    static void main(IODevice console) {
        new KeyImporter(console).run();
    }

    private void run(String input) {
        File keyFile = new File(input);

        if (keyFile.isFile()) {
            String privateKey = null;
            try {
                privateKey = Files.readString(keyFile);
            } catch (IOException e) {
                exitError("Unable to read file " + input);
            }

            createWalletFile(privateKey.trim());
        } else {
            createWalletFile(input.trim());
        }
    }

    private void run() {
        String input = console.readLine(
            "Please enter the hex encoded private key or key file location: ").trim();
        run(input);
    }

    private void createWalletFile(String privateKey) {
        if (!WalletUtils.isValidPrivateKey(privateKey)) {
            exitError("Invalid private key specified, must be "
                      + PRIVATE_KEY_LENGTH_IN_HEX
                      + " digit hex value");
        }

        Credentials credentials = Credentials.create(privateKey);
        String password = getPassword("Please enter a wallet file password: ");

        String destinationDir = getDestinationDir();
        File destination = createDir(destinationDir);

        try {
            String walletFileName = WalletUtils.generateWalletFile(
                password, credentials.getEcKeyPair(), destination, true);
            console.printf("Wallet file " + walletFileName
                           + " successfully created in: " + destinationDir + "\n");
        } catch (CipherException | IOException e) {
            exitError(e);
        }
    }
}
