package com.jxust.btcexplorer.btcrpc.service;

import com.alibaba.fastjson.*;
import com.jxust.btcexplorer.btcrpc.entity.*;
import com.jxust.btcexplorer.btcrpc.httputils.RPCRequest;

import java.util.ArrayList;

/**
 * BTCService provide some functions that these func invoke bitcoin core rpc
 * service by one to one
 */
public class BTCService {
    //get block hash by height of block
    private static final String GETBLOCKHASH = "getblockhash";
    private static final String GETBLOCK = "getblock";//rpc command
    private static final String GETBESTBLOCKHASH = "getbestblockhash";
    private static final String GETBLOCKCHAININFO = "getblockchaininfo";
    private static final String GETNEWADDRESS = "getnewaddress";
    private static final String GETBLOCKCOUNT = "getblockcount";
    private static final String GETDIFFICULTY = "getdifficulty";
    private static final String GETRPCINFO = "getrpcinfo";
    private static final String CREATEWALLET = "createwallet";
    private static final String PING = "ping";
    private static final String SETNETWORKACTIVE = "setnetworkactive";
    private static final String LISTWALLETS = "listwallets";
    private static final String GETMININGINFO = "getmininginfo";
    private static final String VALIDATEADDRESS = "validateaddress";
    private static final String GETWALLETINFO = "getwalletinfo";

    /**
     * this method aims to get the block hash by block number
     *
     * @param number height of block，such as 0，1，2
     * @return block hash
     */
    public String getBlockHash(int number) {
        String result = RPCRequest.rpcPost(GETBLOCKHASH, number);

//        BlockHash blockHash = JSONObject.parseObject(result, BlockHash.class);
//        if (blockHash.getResult() != null) {
//            return blockHash.getResult();
//        }
        BaseEntity<String> baseEntity = JSONObject.parseObject(result,
                new TypeReference<BaseEntity<String>>() {
                });
        return baseEntity.getResult();
    }

    /**
     * I want to define a new func， the new func can invoke btc rpc service named
     * getblock, and get the info about block
     */
    public Block getBlock(String hash) {
        String result = RPCRequest.rpcPost(GETBLOCK, hash);
        //2、parse result(is json structure) to a java object in memory
        BaseEntity<Block> baseEntity = JSONObject.parseObject(result,
                new TypeReference<BaseEntity<Block>>() {
                });
        return baseEntity.getResult();
    }

    /**
     * get the last block hash
     *
     * @return block hash
     */
    public String getBestBlockHash() {
        String result = RPCRequest.rpcPost(GETBESTBLOCKHASH);
        BaseEntity<String> baseEntity = JSONObject.parseObject(result,
                new TypeReference<BaseEntity<String>>() {
                });
        return baseEntity.getResult();
    }

    public BlockChainInfo getBlockChainInfo() {
        String result = RPCRequest.rpcPost(GETBLOCKCHAININFO);
        BaseEntity<BlockChainInfo> chainInfoEntity = JSONObject.parseObject(
                result,
                new TypeReference<BaseEntity<BlockChainInfo>>() {
                }
        );
        return chainInfoEntity.getResult();
    }

    //address is a symbol to ensure one identity, both any two address no same

    /**
     * this method can generate  a new address by bitcoin core
     *
     * @param label        The label name for the address to be linked to.
     *                     It can also be set to the empty string "" to represent the default
     *                     label. The label does not need to exist,
     *                     it will be created if there is no label by the given name
     * @param address_type The address type to use. Options are "legacy",
     *                     "p2sh-segwit", and "bech32".
     * @return new address
     */
    public String getNewAddress(String label, String address_type) {
        //json string
        /**
         * id
         * error
         * result: data we want get
         */
        String address = RPCRequest.rpcPost(GETNEWADDRESS, label, address_type);
        BaseEntity<String> baseEntity = JSONObject.parseObject(address,
                new TypeReference<BaseEntity<String>>() {
                });
        return baseEntity.getResult();
    }

    /**
     * Question: I want to get block count by rpc request, what should i do ?
     */
    public int getBlockCount() {
        String blockCount = RPCRequest.rpcPost(GETBLOCKCOUNT);
        //if type result is basic data type, we can't input the basic type
        /**
         * 8 basic types: char、byte、short、int、float、double、long、boolean
         */
        /**
         * 8 warapped class types:
         *  int ----> Integer
         *  short ----> Short
         *  float ----> Float
         *  double ----> Double
         *  long -----> Long
         */
        BaseEntity<Integer> baseEntity = JSONObject.parseObject(blockCount,
                new TypeReference<BaseEntity<Integer>>() {
                });
        return baseEntity.getResult().intValue();
    }

    public double getDifficulty() {
        String result = RPCRequest.rpcPost(GETDIFFICULTY);
        BaseEntity<Double> baseEntity = JSONObject.parseObject(result,
                new TypeReference<BaseEntity<Double>>() {
                });
        double difficulty = baseEntity.getResult().doubleValue();
        if (difficulty > 0) {
            //the real difficulty
            return difficulty;
        }
        //if we don't add  this if conditional，we can't differentiate the difficulty
        //is real difficulty or error value
        //begining difficulty should be 0, it can't be < 0
        return -1;
    }

    public RpcInfo getrpcinfo() {
        String result = RPCRequest.rpcPost(GETRPCINFO).replaceAll("\\[", " ")
                .replaceAll("]", " ").trim();
        BaseEntity<RpcInfo> rpcInfoBaseEntity = JSONObject.parseObject(result,
                new TypeReference<BaseEntity<RpcInfo>>() {
                });
        return rpcInfoBaseEntity.getResult();
    }

    public CreateWallet createWallet(String name) {
        String result = RPCRequest.rpcPost(CREATEWALLET, name);
        BaseEntity<CreateWallet> createWalletBaseEntity = JSONObject.parseObject(result,
                    new TypeReference<BaseEntity<CreateWallet>>() {
                    });

        return createWalletBaseEntity.getResult();
    }

    public String ping(){
        RPCRequest.rpcPost(PING);

        return "A ping has been sent to all other nodes";
    }

    public String setNetworkActive(Boolean state){
        String result = RPCRequest.rpcPost(SETNETWORKACTIVE, state);
        JSONObject networkState = JSONObject.parseObject(result);
//        System.out.println(networkState.getString("result"));
        return networkState.getString("result");
    }

    public ArrayList<String> getListwallets() {
        String result = RPCRequest.rpcPost(LISTWALLETS);
        System.out.println(result);
        JSONObject listWalletObject = JSONObject.parseObject(result);
        JSONArray listWalletArray = listWalletObject.getJSONArray("result");

        ArrayList<String> listwallets = new ArrayList<String>();
        if (listWalletArray != null) {
            for (int i=0;i<listWalletArray.size();i++){
//                System.out.println(listWalletArray.getString(i));
                listwallets.add(listWalletArray.getString(i));
            }
        }
        return listwallets;
    }

    public MiningInfo miningInfo() {
        String result = RPCRequest.rpcPost(GETMININGINFO);
        BaseEntity<MiningInfo> miningInfoBaseEntity = JSONObject.parseObject(result,
                new TypeReference<BaseEntity<MiningInfo>>() {
                });

        return miningInfoBaseEntity.getResult();
    }

    public ValidateAddress validateAddress(String address) {
        String result = RPCRequest.rpcPost(VALIDATEADDRESS, address);
        BaseEntity<ValidateAddress> validateAddressBaseEntity = JSONObject.parseObject(result,
                new TypeReference<BaseEntity<ValidateAddress>>() {
                });

        return validateAddressBaseEntity.getResult();
    }

    public GetWalletInfo getWalletInfo() {
        String result = RPCRequest.rpcPost(GETWALLETINFO);
        BaseEntity<GetWalletInfo> getWalletInfoBaseEntity = JSONObject.parseObject(result,
                new TypeReference<BaseEntity<GetWalletInfo>>() {
                });

        return getWalletInfoBaseEntity.getResult();
    }




}
