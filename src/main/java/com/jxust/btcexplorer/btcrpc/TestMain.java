package com.jxust.btcexplorer.btcrpc;


import com.jxust.btcexplorer.btcrpc.entity.Block;
import com.jxust.btcexplorer.btcrpc.service.BTCService;

/**
 * this is a test class to test the BTCService method
 */
public class TestMain {

    public static void main(String[] args) {
        //0、create a new instance
        BTCService service = new BTCService();

        //1、getblockchaininfo
        String info = service.getBlockChainInfo().toString();
        System.out.println("information：" + info);

        //2、getblockhash
        String hash = service.getBlockHash(0);
        System.out.println("hash :" + hash);

        //3、getblock
        Block block = service.getBlock(hash);
        if (block != null) {
            System.out.println(block.getChainwork());
            System.out.println(block.getDifficulty());
            System.out.println(block.getConfirmations());
        } else {
            System.out.println(" can't get block info ");
        }

        //4、getdifficulty
        double difficulty = service.getDifficulty();
        System.out.println("blockchain difficulty is " + difficulty);

    }

}
