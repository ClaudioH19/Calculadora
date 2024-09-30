/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author claud
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    @FXML
    private Button zero;
    @FXML
    private Button leftBracket;
    @FXML
    private Button rightBracket;
    @FXML
    private Button plus;
    @FXML
    private Button minus;
    @FXML
    private Button division;
    @FXML
    private Button multiplication;
    @FXML
    private Button equals;
    @FXML
    private Button clearAll;
    @FXML
    private Button erase;
    @FXML
    private Button error;
    @FXML
    private TextArea text;
    @FXML
    private TextArea result;
    @FXML
    private Menu help;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {

        Button[] actions = {this.one,
            this.two, this.three, this.four,
            this.five, this.six, this.seven,
            this.eight, this.nine, this.zero,
            this.plus, this.minus, this.multiplication,
            this.division, this.equals, this.clearAll,
            this.erase, this.leftBracket, this.rightBracket, this.error};

        for (int i = 0; i < actions.length; i++) {

            if (event.getSource().equals(actions[i])) {
                //two options write or delete something
                //delete or clearAll
                if (event.getSource().equals(this.clearAll)) {
                    this.text.setText("");
                } else if (event.getSource().equals(this.erase)) {
                    String textAux = "";
                    //copy text until last char
                    for (int j = 0; j < this.text.getText().length() - 1; j++) {
                        textAux += this.text.getText().charAt(j);
                    }
                    
                    this.text.setText(textAux);
                }//write
                else {
                    
                    //result
                    if (event.getSource().equals(this.equals)) {

                    } else if (event.getSource().equals(this.error)) {
                        String aux="";
                        for (int j = 0; j < this.text.getText().length(); j++) {
                            aux+=this.text.getText().charAt(j);
                            if(j==this.text.getCaretPosition()-1){
                                aux+='±';
                            }
                        }
                        this.text.setText(aux);
                    } else {
                        String aux="";
                        if(this.text.getText().isEmpty())
                            aux+=actions[i].getText();
                        else{
                            for (int j = 0; j < this.text.getText().length(); j++) {
                                aux+=this.text.getText().charAt(j);
                                if(j==this.text.getCaretPosition()-1){
                                    aux+=actions[i].getText();
                                }
                            }
                        }
                        this.text.setText(aux);
                    }
                }
            }
        }
    }
    
    @FXML
    public void getResult(ActionEvent event){
    
        if(this.text.getText().isEmpty())
        { 
            this.result.setText("");
            return;
        }
         Logic l= new Logic();
         Nodo tool=new Nodo("ImaTool");
        //String texto="0123456789
        //String texto="-1231+21/4-7218*(432/(312-53))-0";
        //String texto="134+141*1/2-653";
        //String texto="-1231/((123+43)-23*(24/(431)-1))";
        //String texto="14323-(23423/((342+76)5)-1)*-3";
        
        String texto=this.text.getText();
        
        //2 CAPAS FILTRADO
        texto=l.humanProofDebugger(texto);
        texto=l.debuggerThree(texto);
        
        texto=l.debugger(texto); //filtra ordenación matematica
        texto=l.debuggerTwo(texto);

        //((3±0.1)/(2±0.1))*(7±3/(4±3(5±2+3)))
        
        //CREACIÓN DEL ÁRBOL
        Nodo raiz=new Nodo(texto);
        raiz=tool.unRoller(raiz);

        
        tool.treePrinter(raiz,"");
        
        //RESOLVER E IMPRIMIR
        this.result.setText(String.valueOf(tool.resolveFor(raiz)));
    }
    
    

  
    @FXML//NOTA PARA EL FUTURO, PARA NO AÑADIR UN LISTENER EN EL CONTROLLADOR O EN LA SCENA, 
    //IR AL .FXML Y AÑADIR EL EVENTO CON EL NOMBRE DE LA FUNCIÓN A LLAMAR EJ:onKeyPressed="#KeyPressed" encima de todo
    public void KeyPressed(KeyEvent event) {

    }
    
    @FXML
    public void showHelp(ActionEvent event){
        this.help.setText("Solo se permiten los símbolos presentes en la calculadora");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
