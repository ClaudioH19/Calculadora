/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import  calculadora.Logic;

/**
 *
 * @author claud
 */
public class Nodo {
    
    Logic l;
    String key;
    Nodo hI;
    Nodo hD;
    
    
    public Nodo(String key) {
        this.key=key;
        this.l=new Logic();
    }
    
    public Nodo unRoller(Nodo nodo){
        
        if(l.symbolsCounter(nodo.key)!=0)//desarrollar
        {
           
            //revisar si hay () en pos 0 y final
            if(nodo.key.charAt(0)=='(' && nodo.key.charAt(nodo.key.length()-1)==')' && nodo.key.length()-1==l.matchPar(nodo.key,0))
            {
                nodo.key=l.betweenText(nodo.key, 0, nodo.key.length()-1);
            }
            
            //crear nodos...
            nodo=nodeMaking(nodo.key,nodo);
            
            //llamadas recursivas
            nodo.hI=unRoller(nodo.hI);
            nodo.hD=unRoller(nodo.hD);
            
        }
        return nodo;
    }

    
    public Nodo nodeMaking(String text,Nodo nodo){  
        char[] operators={'+','*','/'};
        //buscar el primer operador ordenado por prioridad
        for (int i = 0; i < operators.length; i++) {
            for (int j = 0; j < text.length(); j++) {
                
                //si hay parentesis se salta y va agregando para no leer lo de dentro
                if(text.charAt(j)=='(')
                    j=l.matchPar(text,j); //deja en la pos del par
                if(operators[i]==text.charAt(j) && nodo!=null){
                //crear raiz;
                nodo.key=text.charAt(j)+"";
                
                nodo.hI=new Nodo(l.backText(text,j));
                nodo.hD=new Nodo(l.forthText(text,j));
                
                return nodo;
                }
            }  
        }
        
        
        return nodo;
    }
    
    public void treePrinter(Nodo nodo,String reminder){
        if(nodo==null);
        else
        {
            treePrinter(nodo.hI,reminder+"   ");
            System.out.println(reminder+nodo.key);
            treePrinter(nodo.hD,reminder+"   ");
        }
    
    }
    
    
    public String resolveFor(Nodo nodo){
        //se supone que los nodos tiene un solo op o num  
        char[] operators={'+','*','/'};
        Nodo tool= new Nodo("Iamtool");
        String result="";
         
         
         if(l.match(nodo.key.charAt(0), operators))
         {
             switch(nodo.key.charAt(0))
             {
                 case '+':
                     if(nodo.hI !=null && nodo.hD!=null && !nodo.hI.key.isEmpty() && !nodo.hD.key.isEmpty())
                     {
                         String hI=resolveFor(nodo.hI);
                         String hD=resolveFor(nodo.hD);
                         
                         double real=Double.parseDouble(tool.getReal(hI)) + Double.parseDouble(tool.getReal(hD));
                         double error=Double.parseDouble(tool.getError(hI)) + Double.parseDouble(tool.getError(hD));
                         result=String.valueOf(real)+'±'+String.valueOf(error);
                         return result;
                     }
                     else if (nodo.hI !=null && !nodo.hI.key.isEmpty())
                     {
                         return result=resolveFor(nodo.hI);
                     }
                     else if (nodo.hD!=null && !nodo.hD.key.isEmpty())
                     {
                         return result=resolveFor(nodo.hD);
                     }
                 case '*':
                         String hI=resolveFor(nodo.hI);
                         String hD=resolveFor(nodo.hD);
                         
                         double real=Double.parseDouble(tool.getReal(hI))*Double.parseDouble(tool.getReal(hD));
                         double error=Math.abs(real)*(Math.abs(Double.parseDouble(tool.getError(hI))/Double.parseDouble(tool.getReal(hI))) + Math.abs(Double.parseDouble(tool.getError(hD))/Double.parseDouble(tool.getReal(hD))));
                         result=String.valueOf(real)+'±'+String.valueOf(error);
                         return result;
                 case '/':
                         String HI=resolveFor(nodo.hI);
                         String HD=resolveFor(nodo.hD);
                         
                         double reall=Double.parseDouble(tool.getReal(HI))/Double.parseDouble(tool.getReal(HD));
                         double errorr=Math.abs(reall)*(Math.abs((Double.parseDouble(tool.getError(HI))/Double.parseDouble(tool.getReal(HI)))) + Math.abs(Double.parseDouble(tool.getError(HD))/Double.parseDouble(tool.getReal(HD))));
                         result=String.valueOf(reall)+'±'+String.valueOf(errorr);
                         return result;
             }
         }
         else
         {
             return nodo.key;
         }
         
        return result;
    
    }
    
    public String getReal(String text){
        String realPart="";
        for (int i = 0; i < text.length() && text.charAt(i)!='±'; i++) {
            realPart+=text.charAt(i);
        }
        return realPart;
    }
    
    public String getError(String text){
        String errorPart="";
        
        int i = 0;
        while( i < text.length() && text.charAt(i++)!='±');
        
        for (; i < text.length() && text.charAt(i)!='±'; i++) {
            errorPart+=text.charAt(i);
        }
        
        if(errorPart.isEmpty()){
            return "0";
        }

        //(7 ± 0.4) - (2 ± 0.1) * (3 ± 0.2) 
        return errorPart;
    }
    
    
    
    
    
    
    
    
}
