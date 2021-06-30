package com.jxust.btcexplorer.controller;

import com.jxust.btcexplorer.btcrpc.entity.Block;
import com.jxust.btcexplorer.btcrpc.entity.GetWalletInfo;
import com.jxust.btcexplorer.btcrpc.service.BTCService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class BTCRPCController {

    BTCService service = new BTCService();

    @RequestMapping("/getblockhash")
    public String getBlockHash(@RequestParam("height") int height, ModelMap model) {
        String hash = service.getBlockHash(height);
        System.out.println("Block hash is : " + hash);
        model.addAttribute("hash", hash);
        return "index::getblockhashresult";
    }

    @RequestMapping("/getblockchaininfo")
    public String getBlockChainInfo(@RequestParam("blockchaininfoparam") String param, ModelMap model) {
        String result = null;
        if (param != null) {
            switch (param) {
                case "chain":
                    result = service.getBlockChainInfo().getChain();
                    break;
                case "blocks":
                    result = String.valueOf(service.getBlockChainInfo().getBlocks());
                    break;
                case "headers":
                    result = String.valueOf(service.getBlockChainInfo().getHeaders());
                    break;
                case "bestblockhash":
                    result = String.valueOf(service.getBlockChainInfo().getBestblockhash());
                    break;
                case "difficulty":
                    result = String.valueOf(service.getBlockChainInfo().getDifficulty());
                    break;
                case "mediantime":
                    result = String.valueOf(service.getBlockChainInfo().getMediantime());
                    break;
                case "verificationprogress":
                    result = String.valueOf(service.getBlockChainInfo().getVerificationprogress());
                    break;
                case "chainwork":
                    result = String.valueOf(service.getBlockChainInfo().getChainwork());
                    break;
                case "sizeondisk":
                    result = String.valueOf(service.getBlockChainInfo().getSize_on_disk());
                    break;
                case "warnings":
                    result = String.valueOf(service.getBlockChainInfo().getWarnings());
                    break;

            }
        }
        if (result == null) {
            result = "Null";
        }
        System.out.println("Result for " + param + " is: " + result);
        model.addAttribute("getblockchaininforesult", result);
        return "index::getblockchaininforesult";
    }

    @RequestMapping("/getblock")
    public String getBlock(@RequestParam("hash") String hash, ModelMap modelMap) {
        System.out.println(hash);
        Block block = service.getBlock(hash);
        if (block != null) {
            System.out.println(block.getChainwork());
            System.out.println(block.getDifficulty());
            System.out.println(block.getConfirmations());

            modelMap.addAttribute("getblockchainwork", block.getChainwork());
            modelMap.addAttribute("getblockdifficulty", block.getDifficulty());
            modelMap.addAttribute("getblockconfirmation", block.getConfirmations());
            modelMap.addAttribute("getmerkleroot", block.getMerkleroot());
        } else {
            modelMap.addAttribute("getblockchainwork", "NULL");
            modelMap.addAttribute("getblockdifficulty", "NULL");
            modelMap.addAttribute("getblockconfirmation", "NULL");
            modelMap.addAttribute("getmerkleroot", "NULL");
            System.out.println("Can't get block info");
        }
        return "index::getblockresult";
    }
    @RequestMapping("/getdifficulty")
    public String getDifficulty(@RequestParam("dummy") String dummy, ModelMap modelMap){
        double difficulty = service.getDifficulty();
        System.out.println("blockchain difficulty is " + difficulty);
        modelMap.addAttribute("getdifficulty", difficulty);
        return "index::getdifficultyresult";
    }

    @RequestMapping("/getrpcinfo")
    public String getRpcInfo(@RequestParam("dummy") String dummy, ModelMap modelMap){
        String logpath  =  service.getrpcinfo().getLogpath();
        modelMap.addAttribute("rpcinfo_logpath", logpath);
        //TODO Access Inner Class
        return "index::getrpcinforesult";
    }
    @RequestMapping("/getbestblockhash")
    public String getBestBlockHash(@RequestParam("dummy") String dummy, ModelMap modelMap){
        String bestHash = service.getBestBlockHash();
        modelMap.addAttribute("getbestblockhash", bestHash);
        return "index::getbestblockhashresult";
    }

    @RequestMapping("/getnewaddress")
    public String getNewAddress(@RequestParam("label") String label, @RequestParam("addr_type") String addr_type, ModelMap modelMap){
        if (addr_type == null){
            addr_type = "";
        }
        String newAddress = service.getNewAddress(label, addr_type);
        modelMap.addAttribute("getnewaddress", newAddress);
        return "index::getnewaddressresult";
    }

    @RequestMapping("/createwallet")
    public String CreateWallet(@RequestParam("wallet_name") String name, ModelMap modelMap){
        String walletResult = service.createWallet(name).getName();
        if (walletResult != null){
            modelMap.addAttribute("walletresult", walletResult + " has been Created");
        }
        else {
            modelMap.addAttribute("walletresult", walletResult + " failed to Create");
        }

        return "index::createwalletresult";
    }

    @RequestMapping("/ping")
    public String SendPing(@RequestParam("dummy") String dummy, ModelMap modelMap){
        String pingResponse = service.ping();
        modelMap.addAttribute("ping_result", pingResponse);
        return "index::pingresult";
    }

    @RequestMapping("/netstate")
    public String SetNetworkActive(@RequestParam("netowrkstate") Boolean state, ModelMap modelMap){
        String networkActiveResponse = service.setNetworkActive(state);
//        System.out.println(networkActiveResponse);
        modelMap.addAttribute("netstate_result", "Network state has been set to " + networkActiveResponse);
        return "index::netstateresult";
    }

    @RequestMapping("/listwallets")
    public String ListWallets(@RequestParam("dummy") String dummy, ModelMap modelMap){
        ArrayList<String> listwallets = service.getListwallets();
        modelMap.addAttribute("wallets", listwallets);
        return "index::listwalletsresult";
    }

    @RequestMapping("/getmininginfo")
    public String getMiningInfo(@RequestParam("dummy") String dummy, ModelMap modelMap){
        String miningInfoGetBlocks = String.valueOf(service.miningInfo().getBlocks());
        String miningInfoGetDifficulty = String.valueOf(service.miningInfo().getDifficulty());
        String miningInfoGetChain = String.valueOf(service.miningInfo().getChain());
        String miningInfoGetNetHash = String.valueOf(service.miningInfo().getNetworkhashups());
        String miningInfoGetPool = String.valueOf(service.miningInfo().getPooledtx());
        String miningInfoGetWarning = String.valueOf(service.miningInfo().getWarnings());
        modelMap.addAttribute("miningInfoGetBlocks", miningInfoGetBlocks);
        modelMap.addAttribute("miningInfoGetDifficulty", miningInfoGetDifficulty);
        modelMap.addAttribute("miningInfoGetChain", miningInfoGetChain);
        modelMap.addAttribute("miningInfoGetNetHash", miningInfoGetNetHash);
        modelMap.addAttribute("miningInfoGetPool", miningInfoGetPool);
        modelMap.addAttribute("miningInfoGetWarning", miningInfoGetWarning);
        return "index::getmininginforesult";
    }

    @RequestMapping("/validateaddress")
    public String getAddressValidity(@RequestParam("address") String address, ModelMap modelMap){
        String addressV = String.valueOf(service.validateAddress(address).getAddress());
        String isValid = String.valueOf(service.validateAddress(address).isIsvalid());
        String scriptPubKey = String.valueOf(service.validateAddress(address).getScriptPubKey());
        String isScript = String.valueOf(service.validateAddress(address).isIsscript());
        String isWitness = String.valueOf(service.validateAddress(address).isIswitness());
        String witnessVersion = String.valueOf(service.validateAddress(address).getWitness_version());
        String witnessProgram = String.valueOf(service.validateAddress(address).getWitness_program());
        modelMap.addAttribute("addressV", addressV);
        modelMap.addAttribute("isValid", isValid);
        modelMap.addAttribute("scriptPubKey", scriptPubKey);
        modelMap.addAttribute("isScript", isScript);
        modelMap.addAttribute("isWitness", isWitness);
        modelMap.addAttribute("witnessVersion", witnessVersion);
        modelMap.addAttribute("witnessProgram", witnessProgram);
        return "index::validateaddressresult";
    }
    @RequestMapping("/getblockcount")
    public String getBlockCount(@RequestParam("dummy") String dummy, ModelMap modelMap){
        String blockCount = String.valueOf(service.getBlockCount());
        modelMap.addAttribute("getblockcount", blockCount);
        return "index::getblockcountresult";
    }

    @RequestMapping("/getwalletinfo")
    public String getWalletInfo(@RequestParam("dummy") String dummy, ModelMap modelMap){
        GetWalletInfo getWalletInfo = service.getWalletInfo();
        String walletName = String.valueOf(getWalletInfo.getWalletname());
        String walletVersion = String.valueOf(getWalletInfo.getWalletversion());
        String format = String.valueOf(getWalletInfo.getFormat());
        String balance = String.valueOf(getWalletInfo.getBalance());
        String unconfirmed_balance = String.valueOf(getWalletInfo.getUnconfirmed_balance());
        String immature_balance = String.valueOf(getWalletInfo.getImmature_balance());
        String txcount = String.valueOf(getWalletInfo.getTxcount());
        String keypoololdest = String.valueOf(getWalletInfo.getKeypoololdest());
        String keypoolsize = String.valueOf(getWalletInfo.getKeypoolsize());
        String hdseedid = String.valueOf(getWalletInfo.getHdseedid());
        String keypoolsize_hd_internal = String.valueOf(getWalletInfo.getKeypoolsize_hd_internal());
        String paytxfee = String.valueOf(getWalletInfo.getPaytxfee());
        String private_keys_enabled = String.valueOf(getWalletInfo.isPrivate_keys_enabled());
        String avoid_reuse = String.valueOf(getWalletInfo.isAvoid_reuse());
        String scanning = String.valueOf(getWalletInfo.isScanning());
        String descriptors = String.valueOf(getWalletInfo.isDescriptors());
        modelMap.addAttribute("walletName", walletName);
        modelMap.addAttribute("walletVersion", walletVersion);
        modelMap.addAttribute("format", format);
        modelMap.addAttribute("balance", balance);
        modelMap.addAttribute("unconfirmed_balance", unconfirmed_balance);
        modelMap.addAttribute("immature_balance", immature_balance);
        modelMap.addAttribute("txcount", txcount);
        modelMap.addAttribute("keypoololdest", keypoololdest);
        modelMap.addAttribute("keypoolsize", keypoolsize);
        modelMap.addAttribute("hdseedid", hdseedid);
        modelMap.addAttribute("keypoolsize_hd_internal", keypoolsize_hd_internal);
        modelMap.addAttribute("paytxfee", paytxfee);
        modelMap.addAttribute("private_keys_enabled", private_keys_enabled);
        modelMap.addAttribute("avoid_reuse", avoid_reuse);
        modelMap.addAttribute("scanning", scanning);
        modelMap.addAttribute("descriptors", descriptors);
        return "index::getwalletinforesult";
    }

}
