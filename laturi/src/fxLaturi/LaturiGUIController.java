package fxLaturi;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List; 
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import laturi.Ajoneuvo;
import laturi.Lataus;
import laturi.Laturi;
import laturi.SailoException;

/**
 * Luokka laturin käyttöliittymän tapahtumien hoitamiseksi.
 * 
 * @author plammi
 * @version 1.3.2022
 * version htv6
 */
public class LaturiGUIController implements Initializable {
    
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ListChooser<Ajoneuvo> chooserAjoneuvot;
    @FXML private ScrollPane panelAjoneuvo;
    
    @FXML TextField editAjo;
    @FXML TextField editMerkki;
    @FXML TextField editMalli;
    @FXML TextField editAkku;
    
    private String laturinnimi = "humppavaara";

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    @FXML private void handleHakuehto() {
        String hakukentta = cbKentat.getSelectedText();
        String ehto = hakuehto.getText(); 
        if ( ehto.isEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
    }
    

    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    @FXML private void handleAvaa() {
        avaa();
    }
    
    
    @FXML private void handleTulosta() {
        TulostusController.tulosta(null); 
    }
    
    
   @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }

   
  @FXML private void handleUusiAjoneuvo() {
      // ModalController.showModal(LaturiGUIController.class.getResource("UusiAjoneuvoGUIView.fxml"), "Ajoneuvo", null, "");
      uusiAjoneuvo();    
   }
   
  
  @FXML private void handleMuokkaaAjoneuvoa() {
      muokkaa();
   }
   
 
  @FXML private void handlePoistaAjoneuvo() {
      Dialogs.showMessageDialog("Ei osata vielä poistaa ajoneuvoa");
  }
  
  
  @FXML private void handleUusiLataus() {
      uusiLataus();
  }
   
  @FXML private void handleMuokkaaLatausta() {
      ModalController.showModal(LaturiGUIController.class.getResource("VanhaLatausGUIView.fxml"), "Lataus", null, "");
  }

  
  
 @FXML private void handlePoistaLataus() {
      Dialogs.showMessageDialog("Ei osata vielä poistaa latausta");
 }
  
  
 @FXML private void handleOhjeet() {
     webohje();
 }
 
 
  @FXML private void handleTietoja() {
      //
     ModalController.showModal(LaturiGUIController.class.getResource("LaturinTietojaView.fxml"), "Ajoneuvo", null, "");
 }
    
  
  //===========================================================================================    
  // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia    
  
    private Laturi laturi;
 
    
    
    private void alusta() {
      
        panelAjoneuvo.setFitToHeight(true);
        chooserAjoneuvot.clear();
        chooserAjoneuvot.addSelectionListener(e -> naytaAjoneuvo());
    }
  
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }

    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    
    
    /**
     * Alustaa laturin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta laturin tiedot luetaan
     */
    private void lueTiedosto(String nimi) {
        laturinnimi = nimi;
        setTitle("Laturi - " + laturinnimi);
        try {
            laturi.lueTiedostosta(nimi);
            hae(0);
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }

      
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return false jos painetaan cancel
     */
    public boolean avaa() {
        String uusinimi = LaturinNimiController.kysyNimi(null, "humppavaara");
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
   
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        try {
            laturi.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }


    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }

    
    /**
     * Avaa webselaimeen avustussivuston
     */
    public void webohje() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("http:www.google.com");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
   
    
    private void naytaAjoneuvo() {
        Ajoneuvo ajoneuvoKohdalla = chooserAjoneuvot.getSelectedObject();

        if (ajoneuvoKohdalla == null) return;
        editAjo.setText(ajoneuvoKohdalla.getRekisteriTunnus());
        

       
    }
   

    @SuppressWarnings("unused")  //TODO: otetaan myöhemmin käyttöön
    private void tulosta(PrintStream os, Ajoneuvo ajoneuvo) {
        os.println("---------------------------------------------------");
        ajoneuvo.tulosta(os);
        os.println("---------------------------------------------------");
        List<Lataus> lataukset = laturi.annaLataukset(ajoneuvo);
        for (Lataus lat: lataukset) 
            lat.tulosta(os);
        os.println("---------------------------------------------------");
    }
   
    
    /**
     * Asetetaan käytettävä laturi
     * @param laturi Laturi jota käytetään käyttöliittymässä
     */
    public void setLaturi(Laturi laturi) {
        this.laturi = laturi;
    }
    
    
    private void hae(int anro) {
        chooserAjoneuvot.clear();

        int index = 0;
        for (int i=0; i < laturi.getAjoneuvoja(); i++) {
            Ajoneuvo ajoneuvo = laturi.annaAjoneuvo(i);
            if (ajoneuvo.getTunnusNro() == anro) index = i;
            chooserAjoneuvot.add(ajoneuvo.getRekisteriTunnus(), ajoneuvo);
        }
        chooserAjoneuvot.setSelectedIndex(index); 
    }
    
    
    /**
     * Lisätään laturiin uusi ajoneuvo
     */
    private void uusiAjoneuvo() {
        Ajoneuvo uusi = new Ajoneuvo();
        uusi.rekisteroi();
        uusi.taytaTiedoilla();  //TODO: korvataan dialogilla
        try {
            laturi.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        } 
        hae(uusi.getTunnusNro());
    }
    
    
    private void muokkaa() {
   //   ModalController.showModal(LaturiGUIController.class.getResource("AjoneuvoDialogView.fxml"), "Ajoneuvo", null, "");
        Ajoneuvo ajoneuvoKohdalla = chooserAjoneuvot.getSelectedObject(); 
        if (ajoneuvoKohdalla == null) return;
        AjoneuvoDialogController.kysyAjoneuvo(null, ajoneuvoKohdalla);
    }
    
    
    /** 
     * Tekee uuden tyhjän latauksen editointia varten 
     */ 
    public void uusiLataus() { 
        Ajoneuvo ajoneuvoKohdalla = chooserAjoneuvot.getSelectedObject();
        if ( ajoneuvoKohdalla == null ) return;  
        Lataus lat = new Lataus();  
        lat.rekisteroi();  
        lat.taytaLatausTiedoilla(ajoneuvoKohdalla.getTunnusNro());  
        laturi.lisaa(lat);  
        hae(ajoneuvoKohdalla.getTunnusNro());          
    } 

    
    
    

    

   
    
    
    
    
    /**
     * Tulostaa listassa olevat ajoneuvot tekstialueeseen
     * @param text alue johon tulostetaan
    
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki ajoneuvot");
            for (int i = 0; i < laturi.getAjoneuvoja(); i++) {
                Ajoneuvo ajoneuvo = laturi.annaAjoneuvo(i);
                tulosta(os, ajoneuvo);
                os.println("\n\n");
            }
        }
    }
 */
    //------
    // loput käyttöliittymästä   
    /**
     * Yleisraportti
     */
    @FXML private void handleYleisraportti() {
        Dialogs.showMessageDialog("Ei osata vielä näyttää yleisraporttia");
    }
    
    /**
     * Raportti ajoneuvosta
     */
    @FXML private void handleAjoneuvoRaportti() {
        Dialogs.showMessageDialog("Ei osata vielä näyttää raporttia ajoneuvosta");
    }
    
  

}