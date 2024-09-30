/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author claud
 */

class Nodo{

    float real;
    float error;
    
    String trash;
    String operator;
    
    Nodo Childleft;
    Nodo Childright;

}


public class Matchparent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
            //filtrar ((text))->text;(text)->text
            
            
            
            //String barra="(kjsb((kasjdl(ak)sdn)sdf))";
            //String barra="(kjsb((sdf)))";
            String barra="(kjsb*(sdf/kasd)+kasd)*300";
            String aux="";
            
            for (int i = 0; i < barra.length(); i++) {
                if(barra.charAt(i)=='('){
                    aux=matchPar(barra,i);
                System.out.println(aux);}
        }
    }
    
    
    static public String matchPar(String text,int pos){
        int count=0;
        String result="";
        boolean notMatch=true;
        
        for (int i = pos+1;notMatch && i < text.length(); i++) {
            if(count==0 && text.charAt(i)==')'){
                notMatch=false;
                return result;
            }
            else{
                
                if(text.charAt(i)=='(')
                    count++;
                if(text.charAt(i)==')')
                    count--;
                
                result=result+text.charAt(i);
            }
        }
        
        if(notMatch)
            result="error"; //en un futuro eliminar el parentesis que lo llamó, ver después para ) no cerrado
        return result;
        
    }
    
    
    
    
    
    
    
    
}
