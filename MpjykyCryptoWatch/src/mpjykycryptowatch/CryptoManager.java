/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpjykycryptowatch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mikeyJoyce
 * @reference https://www.youtube.com/watch?v=qzRKa8I36Ww&ab_channel=CodingMaster-ProgrammingTutorials
 * @reference https://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it
 * 
 */
public class CryptoManager implements Manager{
    private Crypto[] cryptosOnScreen; 
    private static HttpURLConnection connection;
    private final Crypto[] cryptoArray;
    private final String genericName;
    private final String genericImage;
    private final String genericPrice;
    
    public CryptoManager(){
        cryptosOnScreen = new Crypto[6];
        
        genericName = "Name";
        genericImage = "noCoin.png";
        genericPrice = "$0.00";
        
        cryptoArray = new Crypto[]{
            new Crypto("Bitcoin", "bitcoin", "bitcoin.png"),
            new Crypto("Ethereum", "ethereum", "eth.png"),
            new Crypto("Binance Coin", "binancecoin", "bnb.png"),
            new Crypto("Dogecoin", "dogecoin", "doge.png"),
            new Crypto("XRP", "ripple", "xrp.png"),
            new Crypto("Tether", "tether", "tether.png"),
            new Crypto("Cardano", "cardano", "cardano.png"),
            new Crypto("Polkadot", "polkadot", "pdot.png"),
            new Crypto("Uniswap", "uniswap", "uni.png"),
            new Crypto("Litecoin", "litecoin", "ltc.png"),
            new Crypto("Chainlink", "chainlink", "chainlink.png"),
            new Crypto("Bitcoin Cash", "bitcoin-cash", "btccash.png"),
            new Crypto("USD Coin", "usd-coin", "usdc.png"),
            new Crypto("VeChain", "vechain", "vechain.png"),
            new Crypto("Solana", "solana", "sol.png"),
            new Crypto("Stellar", "stellar", "stellar.png"),
            new Crypto("Theta Network", "theta-token", "theta.png"),
            new Crypto("OKB", "okb", "okb.png"),
            new Crypto("Filecoin", "filecoin", "filecoin.png"),
            new Crypto("Wrapped Bitcoin", "wrapped-bitcoin", "wbtc.png"),
            new Crypto("TRON", "tron", "tron.png"),
            new Crypto("Ethereum Classic", "ethereum-classic", "ethclassic.png"),
            new Crypto("Binance USD", "binance-usd", "bnbusd.png"),
            new Crypto("Monero", "monero", "monero.png"),
            new Crypto("NEO", "neo", "neo.png"),
            new Crypto("Bitcoin SV", "bitcoin-cash-sv", "btcsv.png"),
            new Crypto("Terra", "terra-luna", "terra.png"),
            new Crypto("EOS", "eos", "eos.png"),
            new Crypto("Klaytn", "klay-token", "klay.png"),
            new Crypto("PancakeSwap", "pancakeswap-token", "cake.png"),
            new Crypto("Aave", "aave", "aave.png"),
            new Crypto("IOTA", "iota", "iota.png"),
            new Crypto("Cosmos", "cosmos", "cosmos.png"),
            new Crypto("cETH", "compound-ether", "ethcomp.png"),
            new Crypto("Maker", "maker", "maker.png"),
            new Crypto("FTX Token", "ftx-token", "ftx.png"),
            new Crypto("cUSDC", "compound-usd-coin", "cusdc.png"),
            new Crypto("BitTorrent", "bittorrent-2", "bittor.png"),
            new Crypto("Huobi Token", "huobi-token", "huobi.png"),
            new Crypto("Crypto.com Coin", "crypto-com-chain", "cryptocom.png"),
            new Crypto("Tezos", "tezos", "tezos.png"),
            new Crypto("Polygon", "matic-network", "poly.png"),
            new Crypto("Avalanche", "avalanche-2", "aval.png"),
            new Crypto("THORChain", "thorchain", "thorchain.png"),
            new Crypto("Algorand", "algorand", "algo.png"),
            new Crypto("Dai", "dai", "dai.png"),
            new Crypto("Compound", "compound-coin", "comp.png"),
            new Crypto("Dash", "dash", "dash.png"),
            new Crypto("Waves", "waves", "waves.png"),
            new Crypto("cDAI", "cdai", "cdai.png"),
            new Crypto("Kusama", "kusama", "kusama.png"),
            new Crypto("Elrond", "elrond-erd-2", "elrond.png"),
            new Crypto("NEM", "nem", "nem.png"),
            new Crypto("Zcash", "zcash", "zcash.png"),
            new Crypto("Chiliz", "chiliz", "chiliz.png"),
            new Crypto("Synthetix Network Token", "havven", "syn.png"),
            new Crypto("Holo", "holotoken", "holo.png"),
            new Crypto("Decred", "decred", "decred.png"),
            new Crypto("Zilliqa", "zilliqa", "zilli.png"),
            new Crypto("Hedera Hashgraph", "hedera-hashgraph", "hedera.png"),
            new Crypto("LEO Token", "leo-token", "leo.png"),
            new Crypto("Celsius Network", "celsius-degree-token", "celsius.png"),
            new Crypto("Enjin Coin", "enjincoin", "enjin.png"),
            new Crypto("Stacks", "blockstack", "stacks.png"),
            new Crypto("Sushi", "sushi", "sushi.png"),
            new Crypto("Bitcoin Gold", "bitcoin-gold", "btcgold.png"),
            new Crypto("NEXO", "nexo", "nexo.png"),
            new Crypto("Amp", "amp-token", "amp.png"),
            new Crypto("TerraUSD", "terrausd", "terrusd.png"),
            new Crypto("DigiByte", "digibyte", "digi.png"),
            new Crypto("yearn.finance", "yearn-finance", "yearn.png"),
            new Crypto("The Graph", "the-graph", "graph.png"),
            new Crypto("Near", "near", "near.png"),
            new Crypto("Siacoin", "siacoin", "sia.png"),
            new Crypto("Basic Attention Token", "basic-attention-token", "bat.png"),
            new Crypto("Decentraland", "decentraland", "decent.png"),
            new Crypto("Fantom", "fantom", "fantom.png"),
            new Crypto("Huobi BTC", "huobi-btc", "hbtc.png"),
            new Crypto("UMA", "uma", "uma.png"),
            new Crypto("Liquity USD", "liquidity-usd", "liquid.png"),
            new Crypto("Qtum", "qtum", "qtum.png"),
            new Crypto("Ontology", "ontology", "ont.png"),
            new Crypto("Ravencoin", "ravencoin", "raven.png"),
            new Crypto("Bancor Network Token", "bancor", "bancor.png"),
            new Crypto("ICON", "icon", "icon.png"),
            new Crypto("0x", "0x", "0x.png"),
            new Crypto("Helium", "helium", "helium.png"),
            new Crypto("SwissBorg", "swissborg", "swiss.png"),
            new Crypto("IOST", "iostoken", "iost.png"),
            new Crypto("Flow", "flow", "flow.png"),
            new Crypto("OMG Network", "omisego", "omg.png"),
            new Crypto("Paxos Standard", "paxos-standard", "paxos.png"),
            new Crypto("Horizen", "zencash", "zen.png"),
            new Crypto("Pirate Chain", "pirate-chain", "pirate.png"),
            new Crypto("Nano", "nano", "nano.png"),
            new Crypto("Harmony", "harmony", "harmony.png"),
            new Crypto("Ankr", "ankr", "ankr.png"),
            new Crypto("KuCoin Token", "kucoin-shares", "kucoin.png"),
            new Crypto("Arweave", "arweave", "arweave.png"),
            new Crypto("Venus", "venus", "venus.png")
        };
    }
    
    public Crypto[] getCryptosOnScreen(){
        return cryptosOnScreen;
    }
    
    public Crypto[] getCryptoArray(){
        return cryptoArray;
    }
    
    public String getGenericName(){
        return genericName;
    }
    
    public String getGenericImage(){
        return genericImage;
    }
    
    public String getGenericPrice(){
        return genericPrice;
    }
    
    @Override
    public int hasUserSaved(String user){
        try (FileInputStream myStream = new FileInputStream(user + ".txt")) {
            int count=0;
            BufferedReader br = new BufferedReader(new InputStreamReader(myStream));
            br.readLine();
            br.readLine();
            
            while(br.readLine() != null){
                count++;
            }
            
            if(count==0){
                return 1;
            }
        }catch (FileNotFoundException f){
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    @Override
    public String parsePrice(String id, String response){
        int constant = 11 + (id.length());
        String price = "$" + response.substring(constant);
        
        StringBuffer myBuffer = new StringBuffer(price);
        myBuffer.deleteCharAt(myBuffer.length()-1);
        myBuffer.deleteCharAt(myBuffer.length()-1);
        
        return myBuffer.toString();
    }
    
    @Override
    public String callApi(Crypto myCrypto){
        //Reference from https://www.youtube.com/watch?v=qzRKa8I36Ww&ab_channel=CodingMaster-ProgrammingTutorials
        BufferedReader reader;
        String line;
        StringBuffer response = new StringBuffer();
        String price="";

        try {
            URL url = new URL("https://api.coingecko.com/api/v3/simple/price?ids=" + myCrypto.getID() + "&vs_currencies=usd");
            connection = (HttpURLConnection) url.openConnection();

            //Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();
            }

            price = parsePrice(myCrypto.getID(), response.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            connection.disconnect();
        }
        //Reference from https://www.youtube.com/watch?v=qzRKa8I36Ww&ab_channel=CodingMaster-ProgrammingTutorials

        return price;
    }
    
    //Start of reference from https://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it
    @Override
    public void deleteCryptoFromUser(String crypto, String file) throws FileNotFoundException, IOException{
        File inputFile = new File(file);
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(crypto)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close(); 
        reader.close(); 
        boolean successful = tempFile.renameTo(inputFile);
    }
    //End of reference from https://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it
}
