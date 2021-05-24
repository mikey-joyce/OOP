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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import java.util.Timer;
import java.util.TimerTask;


/**
 * FXML Controller class
 *
 * @author mikeyJoyce
 * @reference https://www.youtube.com/watch?v=qzRKa8I36Ww&ab_channel=CodingMaster-ProgrammingTutorials
 */
public class HomePageController extends Switchable implements Initializable {

    //function bar
    @FXML
    private ComboBox<Crypto> cryptoPicker;
    
    @FXML
    private Button addDelButton;
    
    @FXML
    private ImageView aboutPage;
    
    @FXML
    private Text user;
    
    @FXML
    private Text errorText;
    
    //first row
    @FXML
    private ImageView crypto1;
    
    @FXML
    private Text price1;
    
    @FXML
    private Text name1;
    
    //second row
    @FXML
    private ImageView crypto2;
    
    @FXML
    private Text price2;
    
    @FXML
    private Text name2;
    
    //third row
    @FXML
    private ImageView crypto3;
    
    @FXML
    private Text price3;
    
    @FXML
    private Text name3;
    
    //fourth row
    @FXML
    private ImageView crypto4;
    
    @FXML
    private Text price4;
    
    @FXML
    private Text name4;
    
    //fifth row
    @FXML
    private ImageView crypto5;
    
    @FXML
    private Text price5;
    
    @FXML
    private Text name5;
    
    //sixth row
    @FXML
    private ImageView crypto6;
    
    @FXML
    private Text price6;
    
    @FXML
    private Text name6;
    
    private CryptoManager myScreen;
    private int globalToken = 0;
    
    //for switching from login scene to homepage scene
    public Text getUser(){
        return user;
    }
    
    @FXML
    public void addDelCrypto(Event event) throws IOException{
        //Check to see if this user has saved anything if not then let them proceed without loading their file
        if(globalToken != 1){
            globalToken = myScreen.hasUserSaved(user.getText());
        }
        
        if(globalToken == 1 ){
            int column=-1;
            Crypto myCrypto = cryptoPicker.getValue();

            if(myCrypto != null){
                for(int i=0; i<6; i++){
                    if(myScreen.getCryptosOnScreen()[i] != null){
                        if(myCrypto.getName().equals(myScreen.getCryptosOnScreen()[i].getName())){
                            String name = myScreen.getGenericName();
                            String image = myScreen.getGenericImage();
                            String price = myScreen.getGenericPrice();
                            
                            myScreen.deleteCryptoFromUser(myScreen.getCryptosOnScreen()[i].getName(), user.getText()+".txt");
                            myScreen.getCryptosOnScreen()[i] = null;
                            
                            switch(i){
                                case 0:
                                    name1.setText(name);
                                    crypto1.setImage(new Image(getClass().getResourceAsStream(image)));
                                    price1.setText(price);
                                    return;
                                case 1:
                                    name2.setText(name);
                                    crypto2.setImage(new Image(getClass().getResourceAsStream(image)));
                                    price2.setText(price);
                                    return;
                                case 2:
                                    name3.setText(name);
                                    crypto3.setImage(new Image(getClass().getResourceAsStream(image)));
                                    price3.setText(price);
                                    return;
                                case 3:
                                    name4.setText(name);
                                    crypto4.setImage(new Image(getClass().getResourceAsStream(image)));
                                    price4.setText(price);
                                    return;
                                case 4:
                                    name5.setText(name);
                                    crypto5.setImage(new Image(getClass().getResourceAsStream(image)));
                                    price5.setText(price);
                                    return;
                                case 5:
                                    name6.setText(name);
                                    crypto6.setImage(new Image(getClass().getResourceAsStream(image)));
                                    price6.setText(price);
                                    return;
                                default:
                                    break;
                            }
                        }
                    }
                }

                File myFile = new File(user.getText() + ".txt");

                if(name1.getText().equals("Name")){
                    name1.setText(myCrypto.getName());
                    crypto1.setImage(new Image(getClass().getResourceAsStream(myCrypto.getImage())));
                    price1.setText(myScreen.callApi(myCrypto));
                    column = 0;
                }
                else if(name2.getText().equals("Name")){
                    name2.setText(myCrypto.getName());
                    crypto2.setImage(new Image(getClass().getResourceAsStream(myCrypto.getImage())));
                    price2.setText(myScreen.callApi(myCrypto));
                    column = 1;
                }
                else if(name3.getText().equals("Name")){
                    name3.setText(myCrypto.getName());
                    crypto3.setImage(new Image(getClass().getResourceAsStream(myCrypto.getImage())));
                    price3.setText(myScreen.callApi(myCrypto));
                    column = 2;
                }
                else if(name4.getText().equals("Name")){
                    name4.setText(myCrypto.getName());
                    crypto4.setImage(new Image(getClass().getResourceAsStream(myCrypto.getImage())));
                    price4.setText(myScreen.callApi(myCrypto));
                    column = 3;
                }
                else if(name5.getText().equals("Name")){
                    name5.setText(myCrypto.getName());
                    crypto5.setImage(new Image(getClass().getResourceAsStream(myCrypto.getImage())));
                    price5.setText(myScreen.callApi(myCrypto));
                    column = 4;
                }
                else if(name6.getText().equals("Name")){
                    name6.setText(myCrypto.getName());
                    crypto6.setImage(new Image(getClass().getResourceAsStream(myCrypto.getImage())));
                    price6.setText(myScreen.callApi(myCrypto));
                    column = 5;
                }

                if(column != -1){
                    myScreen.getCryptosOnScreen()[column] = myCrypto;
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(myFile, true))){
                        bw.write(myCrypto.getName());
                        bw.newLine();
                    } 
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        else{
            errorText.setText("Load user before adding cryptos");
        }
    }
    
    public void loadUser(){
        globalToken = 1;
        errorText.setText("");
        
        int count=0;
        
        try (FileInputStream myStream = new FileInputStream(user.getText() + ".txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(myStream));
            br.readLine();
            br.readLine();
            
            String myString="";
            while((myString = br.readLine()) != null){
                for(int i=0; i<100; i++){
                    if(myScreen.getCryptoArray()[i].getName().equals(myString)){
                        
                        myScreen.getCryptosOnScreen()[count] = new Crypto(myScreen.getCryptoArray()[i].getName(),
                                myScreen.getCryptoArray()[i].getID(), 
                                myScreen.getCryptoArray()[i].getImage());
                        
                        switch(count){
                            case 0:
                                name1.setText(myScreen.getCryptosOnScreen()[count].getName());
                                crypto1.setImage(new Image(getClass().getResourceAsStream(myScreen.getCryptosOnScreen()[count].getImage())));
                                price1.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[count]));
                                break;
                            case 1:
                                name2.setText(myScreen.getCryptosOnScreen()[count].getName());
                                crypto2.setImage(new Image(getClass().getResourceAsStream(myScreen.getCryptosOnScreen()[count].getImage())));
                                price2.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[count]));
                                break;
                            case 2:
                                name3.setText(myScreen.getCryptosOnScreen()[count].getName());
                                crypto3.setImage(new Image(getClass().getResourceAsStream(myScreen.getCryptosOnScreen()[count].getImage())));
                                price3.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[count]));
                                break;
                            case 3:
                                name4.setText(myScreen.getCryptosOnScreen()[count].getName());
                                crypto4.setImage(new Image(getClass().getResourceAsStream(myScreen.getCryptosOnScreen()[count].getImage())));
                                price4.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[count]));
                                break;
                            case 4:
                                name5.setText(myScreen.getCryptosOnScreen()[count].getName());
                                crypto5.setImage(new Image(getClass().getResourceAsStream(myScreen.getCryptosOnScreen()[count].getImage())));
                                price5.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[count]));
                                break;
                            case 5:
                                name6.setText(myScreen.getCryptosOnScreen()[count].getName());
                                crypto6.setImage(new Image(getClass().getResourceAsStream(myScreen.getCryptosOnScreen()[count].getImage())));
                                price6.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[count]));
                                break;
                            default:
                                break;
                        }
                    }
                }
                count++;
            }
            
        }catch (FileNotFoundException f){
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goToAbout(Event event){
        Switchable.switchTo("About");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        myScreen = new CryptoManager();
        
        cryptoPicker.setItems(FXCollections.observableArrayList(
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
        ));
        
        cryptoPicker.setConverter(new StringConverter<Crypto>(){
            @Override
            public String toString(Crypto myCrypto){
                return myCrypto.getName();
            }
            
            @Override
            public Crypto fromString(String string){
                return null;
            }
        });
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(int i=0; i<6; i++){
                    if(myScreen.getCryptosOnScreen()[i] != null){
                        switch(i){
                            case 0:
                                price1.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[i]));
                                break;
                            case 1:
                                price2.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[i]));
                                break;
                            case 2:
                                price3.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[i]));
                                break;
                            case 3:
                                price4.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[i]));
                                break;
                            case 4:
                                price5.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[i]));
                                break;
                            case 5: 
                                price6.setText(myScreen.callApi(myScreen.getCryptosOnScreen()[i]));
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }, 0, 60000);
    }    
    
}
