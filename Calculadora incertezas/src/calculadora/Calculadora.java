/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author claud
 */
public class Calculadora extends Application {

    @Override
    public void start(Stage stage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("calculadora.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Calculadora.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Algo falló");
        }
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Calculadora");
        stage.setScene(scene);
        stage.show();
    }
    
    


    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
   //TAREAS VER CASO
    //2+-1/3, QUE EL /3 SOLO TOME AL UNO

}
