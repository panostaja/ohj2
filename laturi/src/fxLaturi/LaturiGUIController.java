package fxLaturi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import laturi.Ajoneuvo;
import laturi.Lataus;
import laturi.Laturi;
import laturi.SailoException;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.*;
import java.util.List; 

/**
 * Luokka laturin käyttöliittymän tapahtumien hoitamiseksi.
 * @author plammi
 * @version 15.2.2022
 */
public class LaturiGUIController implements Initializable {
    
    @FXML private ListChooser<Ajoneuvo> chooserAjoneuvot;
    @FXML private ScrollPane panelAjoneuvo;
    

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
        
    }
     
    /**
     * @return false jos painetaan cancel
     */
    
    public boolean avaa()
    {
        String uusinimi = LaturinNimiController.kysyNimi(null, "Humppavaara");
        if (uusinimi == null) return false;
        // lueTiedosto(uusinimi);
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
   
   /**
    * Käsitellään tallennuskäsky
    */
   @FXML private void handleTallenna() {
       tallenna();
   }

   /**
    * Käsitellään uuden lataksen lisääminen
    */
   @FXML private void handleUusiLataus() {
      // Dialogs.showMessageDialog("Ei osata vielä lisätä");
      uusiLataus();
   }
    
    /**
     * Käsitellään uuden ajoneuvon lisääminen
     */
    @FXML private void handleUusiAjoneuvo() {
       // ModalController.showModal(LaturiGUIController.class.getResource("UusiAjoneuvoGUIView.fxml"), "Ajoneuvo", null, "");;
       uusiAjoneuvo();    
    }
    
    /**
     * Käsitellään vanhan ajoneuvon tietojen esittäminen
     */
    @FXML private void handelVanhaAjoneuvo() {
        ModalController.showModal(LaturiGUIController.class.getResource("VanhaAjoneuvoGUIView.fxml"), "Ajoneuvo", null, "");;
    }
    
    /**
     * Käsitellään avauskäsky
     */
    @FXML private void handleAvaa() {
        avaa();
    }
    
    
    /**
     * Käsitellään lopetuskäsky
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }

    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
    }
    
    /**
     * Tulostus
     */
    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }
    
    /**
     * Lataukssen poisto
     */
    @FXML private void handlePoistaLataus() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa latausta");
   }
    
    /**
     * Ajoneuvon poisto
     */
    @FXML private void handlePoistaAjoneuvo() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa ajoneuvoa");
    }
    
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
    
    
    
    /**
     * Ohjeet
     */
    @FXML private void handleOhjeet() {
        webohje();
            }
    
    /**
     * Käsitellään vanhan ajoneuvon tietojen esittäminen
     */
    @FXML private void handleTietoja() {
        ModalController.showModal(LaturiGUIController.class.getResource("LaturinTietojaView.fxml"), "Ajoneuvo", null, "");;
    }

  //===========================================================================================    
 // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia    

    private Laturi laturi;
    private Ajoneuvo ajoneuvoKohdalla;
    
    private TextArea areaAjoneuvo = new TextArea(); // TODO: poista tämä lopuksi
    
    private void alusta() {
        panelAjoneuvo.setContent(areaAjoneuvo);
        areaAjoneuvo.setFont(new Font("Courier New", 12));
        panelAjoneuvo.setFitToHeight(true);
        
        chooserAjoneuvot.clear();
        chooserAjoneuvot.addSelectionListener(e -> naytaAjoneuvo());

    }
    
    /**
     * Luo uuden ajoneuvon jota aletaan editoimaan 
     */
    private void uusiAjoneuvo() {
        Ajoneuvo uusi = new Ajoneuvo();
        uusi.rekisteroi();
        uusi.taytaTiedoilla();  //TODO: korvataan dialogilla kun aika
        try {
            laturi.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        } 
        hae(uusi.getTunnusNro());
    }
    
    /** 
     * Tekee uuden tyhjän latauksen editointia varten 
     */ 
    public void uusiLataus() { 
        if ( ajoneuvoKohdalla == null ) return;  
        Lataus lat = new Lataus();  
        lat.rekisteroi();  
        lat.taytaLatausTiedoilla(ajoneuvoKohdalla.getTunnusNro());  
        laturi.lisaa(lat);  
        hae(ajoneuvoKohdalla.getTunnusNro());          
    } 

    
    
    /**
     * Hakee ajoneuvojen tiedot listaan
     * @param anro ajoneuvon numero, joka aktivoidaan haun jälkeen
     */
    private void hae(int anro) {
        chooserAjoneuvot.clear();

        int index = 0;
        for (int i = 0; i < laturi.getAjoneuvoja(); i++) {
            Ajoneuvo ajoneuvo = laturi.annaAjoneuvo(i);
            if (ajoneuvo.getTunnusNro() == anro) index = i;
            chooserAjoneuvot.add(ajoneuvo.getRekisteriTunnus(), ajoneuvo);
        }
        chooserAjoneuvot.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää ajoneuvon
    }

    /**
     * Näyttää listasta valitun ajoneuvon tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaAjoneuvo() {
        ajoneuvoKohdalla = chooserAjoneuvot.getSelectedObject();

        if (ajoneuvoKohdalla == null) return;

        areaAjoneuvo.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAjoneuvo)) {
           // ajoneuvoKohdalla.tulosta(os);
           tulosta(os, ajoneuvoKohdalla);
        }
    }

    private void tulosta(PrintStream os, Ajoneuvo ajoneuvo) {
        os.println("---------------------------------------------------");
        ajoneuvo.tulosta(os);
        List<Lataus> lataukset = laturi.annaLataukset(ajoneuvo);
        for (Lataus lat: lataukset) 
            lat.tulosta(os);
    }
    
    /**
     * Asetetaan käytettävä laturi
     * @param laturi Laturi jota käytetään käyttöliittymässä
     */
    public void setLaturi(Laturi laturi) {
        this.laturi = laturi;
        
        
    }
    
    

}