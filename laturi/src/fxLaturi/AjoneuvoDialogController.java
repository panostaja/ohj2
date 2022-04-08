package fxLaturi;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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
        //
    }
    
    @FXML private void handleCancel() {
        ModalController.closeStage(editAjo);
    }
    
    @FXML TextField editAjo;
    @FXML TextField editMerkki;
    @FXML TextField editMalli;
    @FXML TextField editAkku;
    
    
    
    
   // _____________________________________________________________________________________________
    
    private Ajoneuvo ajoneuvoKohdalla;
    
    private void naytaAjoneuvo(Ajoneuvo ajoneuvo) {
        if (ajoneuvo == null) return;
        editAjo.setText(ajoneuvo.getRekisteriTunnus());
        editMerkki.setText(ajoneuvo.getMerkki());
        editMalli.setText(ajoneuvo.getMalli());
        editAkku.setText(ajoneuvo.getAkku().toString());
    }
    
    /**
     * 
     * Luodaan Ajoneuvon kysymysdialogi ja palautetaan
     * @param modalityStage Stage jolle ollaan modaalisia
     * @param oletus   mitä dataan näytettäään oletuksena
     * @return null jos Cancel, muuten tietue
     
     */
    public static Ajoneuvo kysyAjoneuvo(Stage modalityStage, Ajoneuvo oletus) {
    return ModalController.showModal(LaturiGUIController.class.getResource("AjoneuvoDialogView.fxml"), "Ajoneuvo", modalityStage, oletus);       
    
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Ajoneuvo getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(Ajoneuvo oletus) {
        this.ajoneuvoKohdalla = oletus;
        naytaAjoneuvo(ajoneuvoKohdalla);
        
    }
    
   
    
    

}
