package fxLaturi;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import laturi.Ajoneuvo;
 
/**
 * Ajoneuvon käsittely
 * @author panos
 * @version 3.4.2022
 *
 */
public class AjoneuvoDialogController implements ModalControllerInterface<Ajoneuvo>, Initializable {
 

    
    
    @FXML private void handleOK() {
         if (ajoneuvoKohdalla != null && ajoneuvoKohdalla.getRekisteriTunnus().trim().equals("")) {
             naytaVirhe("Rekisteritunnus ei saa olla tyhjä");
             return;
         }
         ModalController.closeStage(labelVirhe);
    }
    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }

    @FXML private void handleCancel() {
        ModalController.closeStage(editAjo);
        ajoneuvoKohdalla = null;
    }
    
    @FXML TextField editAjo;
    @FXML TextField editMerkki;
    @FXML TextField editMalli;
    @FXML TextField editAkku;
    @FXML Label labelVirhe;
    @FXML GridPane gridAjoneuvo;
    @FXML ScrollPane panelAjoneuvo;
    
    
    
    
   // _____________________________________________________________________________________________
    
    private Ajoneuvo ajoneuvoKohdalla;
    private static  TextField[] edits;
    private static Ajoneuvo apuajoneuvo = new Ajoneuvo();
    private int kentta = 0;  // mikä kenttä aktivoidaan kun dialogi aukaistaan
    
    /**
     * Näytetään ajoneuvon tiedor Textfield komponetteihin
     * @param edits taulukko jossa tekstikentät
     * @param ajoneuvo näytettävä ajoneuvo
     */
    public static void naytaAjoneuvo(TextField [] edits, Ajoneuvo ajoneuvo) {
        if (ajoneuvo == null) return;
        for (int k = ajoneuvo.ekaKentta(); k < ajoneuvo.getKenttia(); k++) {
            edits[k].setText(ajoneuvo.anna(k));
        }
        
    }
    
    /**
     * 
     * Luodaan Ajoneuvon kysymysdialogi ja palautetaan
     * @param modalityStage Stage jolle ollaan modaalisia
     * @param oletus   mitä dataan näytettäään oletuksena
     * @param kentta mikä kenttä on fokuksessa kun näytetään
     * @return null jos Cancel, muuten tietue
     
     */
    public static Ajoneuvo kysyAjoneuvo(Stage modalityStage, Ajoneuvo oletus, int kentta) {
        return ModalController.<Ajoneuvo, AjoneuvoDialogController>showModal(
                  AjoneuvoDialogController.class.getResource("AjoneuvoDialogView.fxml"), 
                  "Ajoneuvo", 
                  modalityStage, oletus,
                  ctrl -> ctrl.setKentta(kentta));

    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }
    
    public void alusta () {
        edits =luoKentat(gridAjoneuvo);
        for (TextField edit : edits)
            if(edit != null)
                edit.setOnKeyReleased(e -> kasitteleMuutosAjoneuvoon((TextField)(e.getSource())));
        panelAjoneuvo.setFitToHeight(true);
    }

    public static TextField[] luoKentat (GridPane gridAjoneuvo) {
        gridAjoneuvo.getChildren().clear();
        
        TextField[] edits = new TextField[apuajoneuvo.getKenttia()];
        for (int i=0, k = apuajoneuvo.ekaKentta(); k < apuajoneuvo.getKenttia(); k++, i++) {
            Label label = new Label(apuajoneuvo.getKysymys(k));
            gridAjoneuvo.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridAjoneuvo.add(edit, 1, i);
           // 
        }
        return edits;
        
    }

    
    /**
     * Palautetaan komponentin id:stä saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mikä arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna 
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }

    
    
    private void kasitteleMuutosAjoneuvoon(TextField edit) {
        if (ajoneuvoKohdalla == null) return;
        String s = edit.getText();
        int k = getFieldId(edit, apuajoneuvo.ekaKentta());
        String virhe = ajoneuvoKohdalla.aseta(k, s);
        
         if (virhe != null ) {
           Dialogs.setToolTipText(edit, virhe);
           edit.getStyleClass().add("virhe");
           naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(null);
        }
    }

    @Override
    public Ajoneuvo getResult() {
        
        return ajoneuvoKohdalla;
    }

    @Override
    public void handleShown() {
        kentta = Math.max(apuajoneuvo.ekaKentta(), Math.min(kentta, apuajoneuvo.getKenttia()-1)); 
        edits[kentta].requestFocus(); 

        
    }

    @Override
    public void setDefault(Ajoneuvo oletus) {
        this.ajoneuvoKohdalla = oletus;
        naytaAjoneuvo(edits, ajoneuvoKohdalla);
        
    }
    
   
    private void setKentta(int kentta) {
        this.kentta = kentta; 
     }

    

}
