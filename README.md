## web3j: Web3 Java Aurora Ðapp API
--------

### Features
--------

* Complete implementation of Aurora's `JSON-RPC <https://github.com/aoaio/wiki/wiki/JSON-RPC>`_
  client API over HTTP and IPC
* Aurora wallet support
* Android compatible

### Quickstart
----------

 all sample example is available in *src/test/java* dir,including:
 
 * create address (show in __AuroraAddressCreateTest.java__)
 * query block info by block number or block hash(show in __AuroraWeb3jBlockTest.java__)
 * dynamic receive changes from blockchain node(show in __AuroraWeb3jFilterTest.java__)
 * query transaction info by trx hash,query transaction receipt info by trx hash,Send different types of transactions online(show in __AuroraWeb3jTransactionTest.java__) 
 * send different types of transactions offline(show in __AuroraWeb3jRawTransactionTest.java__)

### Start a client

Start up an Aurora client if you don't already have one running, such as aoa:

```
  ./aoa --rpc
 
```

### Start sending requests

To send synchronous requests:
 
```

 Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
 Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
 String clientVersion = web3ClientVersion.getWeb3ClientVersion();
 
```
 
To send asynchronous requests using a CompletableFuture: 

```

Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
String clientVersion = web3ClientVersion.getWeb3ClientVersion();

```

### Filters

web3j functional-reactive nature makes it really simple to setup observers that notify subscribers
of events taking place on the blockchain.

To receive all new blocks as they are added to the blockchain:


```

Subscription subscription = web3j.blockObservable(false).subscribe(block -> {
       ...
   });

```

To receive all new transactions as they are added to the blockchain:

```

   Subscription subscription = web3j.transactionObservable().subscribe(tx -> {
       ...
   });

```

To receive all pending transactions as they are submitted to the network (i.e. before they have
been grouped into a block together):

```

Subscription subscription = web3j.pendingTransactionObservable().subscribe(tx -> {
       ...
   });
   

```


### Transactions

web3j provides support for both working with Aurora wallet files (recommended) and Aurora
client admin commands for sending transactions.

To send Aoa to another party using your Aurora wallet file:


```

 Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
   TransactionReceipt transactionReceipt = Transfer.sendFunds(
           web3, credentials, "AOA<address>|<ensName>",
           BigDecimal.valueOf(1.0), Convert.Unit.AOA)
           .send();

```

Or if you wish to create your own custom transaction:

```
   Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
   Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

   // get the next available nonce
   AOAGetTransactionCount aoaGetTransactionCount = web3j.aoaGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();
   BigInteger nonce = aoaGetTransactionCount.getTransactionCount();

   // create our transaction
   RawTransaction rawTransaction = RawTransaction.createAOATransaction(nonce, gasPrice, gas, to, value);
   // sign & send our transaction
   byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,ChainId.DEVNET, credentials);
   String hexValue = Numeric.toHexString(signedMessage);
   AOASendTransaction aoaSendTransaction = web3j.aoaSendRawTransaction(hexValue).send();
   
```

 
### Contribution

Thank you for considering to help out with the source code! 
We welcome contributions from anyone on the internet, and are grateful for even the smallest of fixes!

