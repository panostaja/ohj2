package fxLaturi;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.*;

/**
 * Luokka laturin käyttöliittymän tapahtumien hoitamiseksi.
 * @author plammi
 * @version 12.2.2022
 */
public class LaturiGUIController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
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
        Dialogs.showMessageDialog("Ei osata vielä avata ohjeita");
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
       Dialogs.showMessageDialog("Ei osata vielä lisätä");
   }
    
    /**
     * Käsitellään uuden ajoneuvon lisääminen
     */
    @FXML private void handleUusiAjoneuvo() {
        Dialogs.showMessageDialog("Ei osata vielä lisätä");
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
     * Tulostus
     */
    @FXML private void handleTietoja() {
        Dialogs.showMessageDialog("Ei osata vielä näyttää tietoja");
   }
    

}