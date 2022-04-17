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
import kanta.Tietue;
 
/**
 * Ajoneuvon käsittely
 * @author panos
 * @version 3.4.2022
 * @param <TYPE> Minkä tyyppistä alkiota käsitellään
 *
 */
public class TietueDialogController<TYPE extends Tietue> implements ModalControllerInterface<TYPE>, Initializable {
 

    
    
    @FXML private void handleOK() {
         if (tietueKohdalla != null && tietueKohdalla.anna(tietueKohdalla.ekaKentta()).trim().equals("")) {
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
        tietueKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML TextField editAjo;
    @FXML TextField editMerkki;
    @FXML TextField editMalli;
    @FXML TextField editAkku;
    @FXML Label labelVirhe;
    @FXML GridPane gridTietue;
    @FXML ScrollPane panelTietue;
    
    
    
    
   // _____________________________________________________________________________________________
    
    private TYPE tietueKohdalla;
    private static  TextField[] edits;
   
    private int kentta = 0;  // mikä kenttä aktivoidaan kun dialogi aukaistaan
    
    /**
     * Näytetään ajoneuvon tiedor Textfield komponetteihin
     * @param edits taulukko jossa tekstikentät
     * @param tietue näytettävä tietue
    */
    public static void naytaTietue(@SuppressWarnings("hiding") TextField[] edits, Tietue tietue) {
        if (tietue == null) return;
        for (int k = tietue.ekaKentta(); k < tietue.getKenttia(); k++) {
            edits[k].setText(tietue.anna(k));
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
    public static<TYPE extends Tietue> TYPE kysyTietue(Stage modalityStage, TYPE oletus, int kentta) {
        return ModalController.<TYPE, TietueDialogController<TYPE>>showModal(
                TietueDialogController.class.getResource("TietueDialogView.fxml"),
                "Laturi",
                modalityStage, oletus,
                ctrl -> ctrl.setKentta(kentta) 
                );


    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
      //  alusta();
        
    }
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa tietueen tiedot.
     */
    public void alusta () {
        edits =luoKentat(gridTietue, tietueKohdalla);
        for (TextField edit : edits)
            if(edit != null)
                edit.setOnKeyReleased(e -> kasitteleMuutosTietueeseen((TextField)(e.getSource())));
        panelTietue.setFitToHeight(true);
    }

    
    
    /**
     * Luodaan GridPaneen tietueen tiedot
     * @param gridTietue mihin tiedot luodaan
     * @param aputietue malli josta tiedot otetaan
     * @return luodut tekstikentät
     */
  public static<TYPE extends Tietue> TextField[] luoKentat(GridPane gridTietue, TYPE aputietue) {
        gridTietue.getChildren().clear();
        @SuppressWarnings("hiding")
        TextField[] edits = new TextField[aputietue.getKenttia()];
        
        for (int i=0, k = aputietue.ekaKentta(); k < aputietue.getKenttia(); k++, i++) {
            Label label = new Label(aputietue.getKysymys(k));
            gridTietue.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridTietue.add(edit, 1, i);
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

    
    
    private void kasitteleMuutosTietueeseen(TextField edit) {
        if (tietueKohdalla == null) return;
        String s = edit.getText();
        int k = getFieldId(edit, tietueKohdalla.ekaKentta());
        String virhe = tietueKohdalla.aseta(k, s);
        
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
    public TYPE getResult() {
        
        return tietueKohdalla;
    }

    @Override
    public void handleShown() {
        kentta = Math.max(tietueKohdalla.ekaKentta(), Math.min(kentta, tietueKohdalla.getKenttia()-1)); 
        edits[kentta].requestFocus(); 

        
    }

    @Override
    public void setDefault(TYPE oletus) {
        this.tietueKohdalla = oletus;
        alusta();
        naytaTietue(edits, tietueKohdalla);
        
    }
    
   
    private void setKentta(int kentta) {
        this.kentta = kentta; 
     }

    

}
