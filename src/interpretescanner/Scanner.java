package interpretescanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jesus
 */
public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        int estado = 0;
        String lexema = "";
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);

            switch (estado){
                //Reconocimiento de Letras y Numeros.
                case 0:
                    if(Character.isLetter(c)){
                        estado = 1;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 2;
                        lexema += c;
                    }
                    break;

                case 1:
                    if(Character.isLetterOrDigit(c)){
                        estado = 1;
                        lexema += c;
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        }
                        else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;

                    }
                    break;

                case 2:
                    if(Character.isDigit(c)){
                        estado = 2;
                        lexema += c;
                    }
                    else if(c == '.'){

                    }
                    else if(c == 'E'){

                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
            
                case 3:
                    
                    if (c != '\0') {
                       if(c =='<'){
                            lexema = ""+c;
                            Token t = new Token(TipoToken.LESS,lexema);
                            tokens.add(t);
                        } 
                        else if(c == '='){
                            lexema = ""+c;
                            Token t = new Token(TipoToken.EQUAL,lexema);
                            tokens.add(t);                              
                        }
                        else if(c =='>'){
                            lexema = ""+c;
                            Token t = new Token(TipoToken.GREATER,lexema);
                            tokens.add(t);                              
                        }
                        else if(c == '!'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.BANG,lexema);
                            tokens.add(t);                           
                        }
                        else if(c == '('){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.RIGHT_PAREN,lexema);
                            tokens.add(t);                           
                        }
                        else if(c ==')'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.LEFT_PAREN,lexema);
                            tokens.add(t);                           
                        }
                       else if(c =='{'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.RIGHT_BRACE,lexema);
                            tokens.add(t);                           
                        }
                         else if(c =='}'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.LEFT_BRACE,lexema);
                            tokens.add(t);                           
                        }
//                       else if(c =='"'){
//                           estado = 11;                           
//                        }
                        else if(c == '+'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.PLUS,lexema);
                            tokens.add(t);                           
                        }
                       else if(c == '-'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.MINUS,lexema);
                            tokens.add(t);                           
                        }
                       else if(c == '*'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.STAR,lexema);
                            tokens.add(t);                           
                        }
                       else if(c == '/'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.SLASH,lexema);
                            tokens.add(t);                           
                        }
//                       else if(c == '<='){
//                           lexema = ""+c;
//                            Token t = new Token(TipoToken.LESS,lexema);
//                            tokens.add(t);                           
//                        }
//                       else if(c == '>='){
//                           lexema = ""+c;
//                            Token t = new Token(TipoToken.LESS,lexema);
//                            tokens.add(t);                           
//                        }
//                       else if(c == '=='){
//                           lexema = ""+c;
//                            Token t = new Token(TipoToken.LESS,lexema);
//                            tokens.add(t);                           
//                        }
//                       else if(c == '!='){
//                           lexema = ""+c;
//                            Token t = new Token(TipoToken.LESS,lexema);
//                            tokens.add(t);                           
//                        }
                       else if(c == '.'){
                           lexema = ""+c;
                            Token t = new Token(TipoToken.LESS,lexema);
                            tokens.add(t);                           
                        }
                        else if(c ==';'){
                           
                           
                        }
                       else if(c ==','){
                           
                           
                        }
                       else if(c =='.'){
                           
                           
                        }                       
                       break;
                       
                       
             
                }
            }       
        }
        return tokens;
    }
}