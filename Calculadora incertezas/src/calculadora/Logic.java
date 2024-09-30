/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculadora;



/**
 *
 * @author claud
 */

import java.util.regex.*;




public class Logic {
    
    
    public String humanProofDebugger(String text){
    
        //FILTRADO DE TEXTO
        //eliminar espacios
        //agregar los ±, cambiar los [] por () y las , por .
        String aux="";
        for (int i = 0; i < text.length(); i++) {
            char let=text.charAt(i);
            
            if(let!=' ')
            {
                if(let==',')
                {
                    let='.';
                }
                else if(let=='x'|| let=='X')
                    let='*';

                aux+=let;
            }

        }
        return aux;
    }
    
    public String debuggerThree(String text){
    
    String aux="";
        
        boolean caso=true;
        while(caso){
            boolean encontrado=false;
            aux="";
            for (int i = 0; i < text.length() && !encontrado; i++) {
                //caso ((a))
                char let=text.charAt(i);
                if(let=='(' && text.charAt(i+1)=='('  && matchPar(text, i)-1 == matchPar(text,i+1))
                {
                    encontrado=true;
                    int parD=matchPar(text,i); //copia el contenido dentro y cuenta operadores
                    for (int j = i+1; j < parD; j++) {
                        aux+=text.charAt(j);
                    }

                    String back=backText(text,i);
                    String ahead=forthText(text,parD);
                    //quita parentesis
                    text=back+aux+ahead;
                }
            }
            if(!encontrado)
                caso=false;

        }
        return text;
    }
    
    public String debuggerTwo(String text){
        String aux;

        //eliminar caso (a)
        for (int i = 0; i < text.length(); i++) 
        { 
            if(text.charAt(i) == '(') //caso (a)
            {
                aux="";
                int parD=matchPar(text,i); //copia el contenido dentro y cuenta operadores
                for (int j = i+1; j < parD; j++) {
                    aux+=text.charAt(j);
                }
                if(symbolsCounter(aux)==0) //se cumple
                {
                    String back=backText(text,i);
                    String ahead=forthText(text,parD);
                    //quita parentesis
                    text=back+aux+ahead;
                }
            }
        }
    
    return text;
    }
    
    
    
    
    
    public String debugger(String text){ // -(
        //Esta función se encarga transformar los - en +
        // y de agregar multiplicaciones a los parentesis solo con nums
        char[] ignore={'*','/','('};
        char[] indicators={'1','2','3','4','5','6','7','8','9'};
        //AGREGAR *()*
        int i = 0;
        while (i<text.length()) {
            String back="";
            String ahead="";
            switch(text.charAt(i))
            {
                case '-':
                    if(i+3<text.length() && text.charAt(i+1)=='(')
                    {
                        //cambio inicial -( --> (-
                        int pos_parD=matchPar(text,i+1);
                        back=backText(text,i);
                        ahead=forthText(text,i+1);
                        
                        int j=0;
                        if(i>0 && text.charAt(i-1)!='*' && text.charAt(i-1)!='/')
                        {
                            text = back+'+'+'('+'-'+ahead;
                            j=i+3;
                        }
                        else
                            {
                            text = back+'('+'-'+ahead;
                            j=i+2;
                        }
                        //ciclado para ir cambiando signos
                        
                        for (; j < pos_parD; j++) {
                            
                            switch (text.charAt(j)) {
                                case '(':
                                    j=matchPar(text,j);
                                    break;
                            //cambiar por +
                                case '-':
                                    back=backText(text,j);
                                    ahead=forthText(text,j);
                                    text = back+'+'+ahead;
                                    break;
                            //cambiar por -
                                case '+':
                                    back=backText(text,j);
                                    ahead=forthText(text,j);
                                    text = back+'-'+ahead;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    else{
                        //copiar texto adelante
                        ahead=forthText(text,i);
                        //copiar texto para atras
                        back=backText(text,i);
                        if(i>0 && !match(text.charAt(i-1),ignore))
                        {
                            text=back+'+'+'-'+ahead;
                            i++;
                        }
                    }
                    break;
                case '(':
                    if(i>0 && match(text.charAt(i-1),indicators)){
                        back=backText(text,i);
                        ahead=forthText(text,i);
                        text=back+'*'+'('+ahead;
                        i++;
                    }
                    break;
                case ')':
                    if(i<text.length()-1 && match(text.charAt(i+1),indicators)){
                        back=backText(text,i);
                        ahead=forthText(text,i);
                        text=back+')'+'*'+ahead;
                        i++;
                    }
                    break;
            }
            i++;
        }
        return text;
    }
    
    
    public String findNumber(String text,int pos){
        //dada una posicion devuelve el numero inmediatamente siguiente a esa posicion
        
        char[] operators={'+','-','*','/','(',')'};
        String number="";

        while(pos<text.length() &&  match(text.charAt(pos++),operators));//hace 2 comparaciones 1 true otra false pero incrementa igualmente;
        pos--;
        
        int pos_refer=pos;
        while(pos<text.length() && !match(text.charAt(pos),operators))
            number+=text.charAt(pos++);
        
        if(pos_refer>0 && text.charAt(pos_refer-1)=='-'){
            number='-'+number;
        }
        return number;
    }
    
    
    public int matchPar(String text,int pos){
        //dada una pos de un parentesis devuelve la pos del parentesis con que matchea
        //deja una copia del contenido del parentesis en result
        int count=0;
        String result="";
        boolean notMatch=true;
        
        if(text.charAt(pos)!='(') return -2; //error solo se deben aceptar parentesis
        
        int i=pos+1;
        while(notMatch && i < text.length()){
            if(count==0 && text.charAt(i)==')'){
                notMatch=false;
                return i;//return result; cambiar a string la func si se quiere el texto dentro del parentesis
            }
            else{
                if(text.charAt(i)=='(')
                    count++;
                if(text.charAt(i)==')')
                    count--;
                
                result=result+text.charAt(i);
            }
            i++;
        }

        if(notMatch)
            result="error"; //en un futuro eliminar el parentesis que lo llamÃ³, ver despuÃ©s para ) no cerrado
        
        return -1;
    }
    
    
    public boolean match(char letter,char[] arr){
        //dado un arreglo y una letra, devuelve true si la letra está dentro del arreglo
        boolean match=false;
        
        for (int i = 0; i < arr.length; i++) {
            if(letter==arr[i])
                match=true;
        }
        
        return match;
    }
    
    
    public String backText(String text,int pos){
        //dada una pos devuelve el texto de atras desde pos 
        String aux="";
        for (int i = 0; i < text.length() && i<pos; i++) {
            aux+=text.charAt(i);
        }
        return aux;
    }
    
    public String forthText(String text,int pos){
    //dada una pos devuelve el texto delante de pos 
    String aux="";
        for (int i = pos+1; i < text.length(); i++) {
            aux+=text.charAt(i);
        }
        return aux;
    }
    
    public String betweenText(String text,int ini,int fin){
    //dada una pos devuelve el texto entre ini+1 y fin-1 
    String aux="";
        for (int i = ini+1; i < fin; i++) {
            aux+=text.charAt(i);
        }
        return aux;
    }
    
    public int symbolsCounter(String text){
        
        //dado un texto cuenta los operadores definidos abajo 
        char[] operators={'+','*','/'};
        int count=0;
        for (int i = 0; i < text.length(); i++) {
            if(match(text.charAt(i),operators))
                count++;
        }
        return count;
    }
    
    
    
    
    
    
    
    
    
    
}
