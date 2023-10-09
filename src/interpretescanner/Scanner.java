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
    
    private String lexema;
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        int estado = 0;
        this.lexema = "";
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);

            switch (estado){
                 case 0:
                    if (c != '\0') {
                       if(c =='<'){
                           estado = 1;
                           lexema += c;
                        } 
                        else if(c == '='){
                           estado = 2;
                           lexema += c;
                        }
                        else if(c =='>'){
                           estado = 3;
                           lexema += c;
                        }
                        else if(c == '!'){
                           estado = 4;
                           lexema += c;
                        }
                        else if(c == '('){
                           estado = 5;
                           lexema += c;
                        }
                        else if(c ==')'){
                           estado = 6;
                           lexema += c;
                        }
                        else if(c =='{'){
                           estado = 7;
                           lexema += c;
                        }
                        else if(c =='}'){
                           estado = 8;
                           lexema += c;
                        }
                       else if(c =='"'){
                           estado = 9;
                           lexema += c;
                        }
                        else if(c >= '0' && c <='9'){
                           estado = 10;
                           lexema += c;
                        }
                        else if(c == '+'){
                           estado = 16;
                           lexema += c;
                        }
                       else if(c == '-'){
                           estado = 17;
                           lexema += c;
                        }
                       else if(c == '*'){
                           estado = 18;
                           lexema += c;
                        }
                       else if(c == '/'){
                           estado = 19;
                           lexema += c;
                        }
                        else if(c >= 'a' && c <='z' || c >= 'A' && c <='Z' ){
                           estado = 20;
                           lexema += c;
                        }
                        else if(c ==' '){
                           estado = 21;
                           lexema += c;
                        }
                        else if(c ==';'){
                           lexema += c;
                           AddToTokenList (TipoToken.SEMICOLON,lexema);
                        }
                       else if(c ==','){
                           lexema += c;
                           AddToTokenList (TipoToken.COMMA,lexema);
                        }
                       else if(c =='.'){
                           lexema += c;
                           AddToTokenList (TipoToken.DOT,lexema);
                        }
                       else{
                           throw new RuntimeException("Error relacionado con:" + c);
                       }
                    }
                break;
                //OPERADORES R//
                case 1:
                    estado=0;
                    if(c=='='){
                        lexema += c;
                        AddToTokenList (TipoToken.LESS_EQUAL,lexema);
                    }
                    else{
                        i--;
                        AddToTokenList (TipoToken.LESS,lexema);
                    }
                break;
                
                case 2:
                    estado=0;
                    if(c=='='){
                        lexema += c;
                        AddToTokenList (TipoToken.EQUAL_EQUAL,lexema);
                    }
                    else{
                        i--;
                        AddToTokenList (TipoToken.EQUAL,lexema);
                    }  
                break;
                
                case 3:
                    estado=0;
                    if(c=='='){
                        lexema += c;
                        AddToTokenList (TipoToken.GREATER_EQUAL,lexema);
                    }
                    else{
                        i--;
                        AddToTokenList (TipoToken.GREATER,lexema);
                    }
                break;
                
                case 4:
                    estado=0;
                    if(c=='='){
                        lexema += c;
                        AddToTokenList (TipoToken.BANG_EQUAL,lexema);
                    }
                    else{
                        i--;
                        AddToTokenList (TipoToken.BANG,lexema);
                    }
                break;
                
                case 5:
                    i--;
                    estado=0;
                    AddToTokenList (TipoToken.LEFT_PAREN,lexema);    
                break;
                
                case 6:
                    i--;
                    estado=0;
                    AddToTokenList (TipoToken.RIGHT_PAREN,lexema);
                break;
                          
                case 7:
                    i--;
                    estado=0;
                    AddToTokenList (TipoToken.LEFT_BRACE,lexema);  
                break;
                
                case 8:
                    i--;
                    estado=0;
                    AddToTokenList (TipoToken.RIGHT_BRACE,lexema);   
                break;
                
                case 9:
                    if(c != '"' && c != '\0'){
                        lexema += c;
                    }
                    else if (c == '\0'){
                        throw new RuntimeException("Error relacionado con:" + lexema);
                    }
                    else{
                        estado=0;
                        lexema += c;
                        AddToTokenList(TipoToken.STRING, lexema,lexema.substring(1,lexema.length()-1));
                    }
                break;
                
                case 10:
                    if(c >= '0' && c <= '9'){
                        lexema += c;
                    }
                    else if(c == '.'){
                        estado =11;
                        lexema += c;
                    }
                    else if(c == 'e' || c == 'E'){
                        estado = 13;
                        lexema += c;
                    } 
                    else{
                        i--;
                        estado=0;
                        AddToTokenList(TipoToken.NUMBER,lexema,Integer.parseInt(lexema));
                    }
                break;
                
                case 11:
                    if(c >= '0' && c <= '9'){
                        estado=12;
                        lexema += c;
                    }
                    else{
                        throw new RuntimeException("Error relacionado con:" + lexema);
                    }
                break;
                
                case 12:
                    if(c >= '0' && c <= '9'){
                        lexema += c;
                    }
                    else if(c == 'e' || c == 'E'){
                        estado=13;
                        lexema += c;
                    }
                    else{
                    i--;
                    estado=0;
                    AddToTokenList(TipoToken.NUMBER, lexema,Float.parseFloat(lexema));
                    }
                break;
                
                case 13:
                    if(c >= '0' && c <= '9'){
                        estado = 15;
                        lexema += c;
                    }
                    else if(c == '+' || c == '-'){
                        estado=14;
                        lexema += c;
                    }
                    else{
                        throw new RuntimeException("Error relacionado con:" + lexema);
                    }
                break;
                
                case 14:
                    if(c >= '0' && c <= '9'){
                        estado = 15;
                        lexema += c;
                    }
                    else{
                        throw new RuntimeException("Error relacionado con:" + lexema);
                    }
                break;
                
                case 15:
                    if(c >= '0' && c <= '9'){
                        lexema += c;
                    }
                    else{
                    i--;
                    estado=0;
                    AddToTokenList(TipoToken.NUMBER, lexema,Double.parseDouble(lexema));
                    }
                break;
                
                case 16:
                        i--;
                        estado=0;
                        AddToTokenList (TipoToken.PLUS,lexema);
                break;
                
                case 17:
                    i--;
                    estado=0;
                    AddToTokenList (TipoToken.MINUS,lexema);                  
                break;
                
                case 18:                    
                    i--;
                    estado=0;
                    AddToTokenList (TipoToken.STAR,lexema);            
                break;
                //COMENTARIOS
                case 19:
                    estado=0;
                    if (c =='/'){
                      estado=22;
                      lexema += c;
                    }
                    else if(c == '*'){
                        estado=23;
                        lexema += c;
                    }
                break;
                //IDENTIFICADOR
                case 20:
                    if(c >= 'a' && c <='z' || c >= 'A' && c <='Z' || c >= '0' && c <='9'){
                        lexema += c;
                    }
                    else{
                        i--;
                        estado=0;
                        AddToTokenList (TipoToken.IDENTIFIER,lexema);
                    }
                break;

                
                case 21:
                    if(c ==' '){
                        lexema += c;
                    }
                    else{
                        i--;
                        estado = 0;
                        lexema = eliminarCaracteres(lexema, 0, lexema.length());
                    }
                break;
                
                //19->this
                case 22:
                    if(c != '\n'){
                        lexema += c;
                    }
                    else{
                        i--;
                        estado = 0;
                        lexema = eliminarCaracteres(lexema, 0, lexema.length());
                    }
                    
                break;
                
                //19->this
                case 23:
                    if(c == '*'){
                        estado=24;
                    }
                    else{
                        lexema += c;
                    }
                break;
                
                case 24:
                    if(c == '/'){
                        estado = 25;
                    }
                    else{
                        lexema += c;
                    }
                break;
                //SIN TOKEN Y ELIMINAR VALORES DEL LEXEMA                
                case 25:
                    i--;
                    estado=0;
                    lexema = eliminarCaracteres(lexema, 0, lexema.length());
                break;
                
                default:
                    throw new RuntimeException("Error relacionado con:" + c);
            }
        }
        tokens.add(new Token(TipoToken.EOF, "", null));
        return tokens;
    }
    
    private static String eliminarCaracteres(String entrada, int indiceInicio, int cantidad) {
        if (indiceInicio < 0 || indiceInicio >= entrada.length()) {
            return entrada;
        }
        int indiceFin = indiceInicio + cantidad;
        if (indiceFin > entrada.length()) {
            indiceFin = entrada.length();
        }
        String parte1 = entrada.substring(0, indiceInicio);
        String parte2 = entrada.substring(indiceFin);
        
        return parte1 + parte2;
    }
    
    private void AddToTokenList(TipoToken tipo, String lexema) {
        if(tipo == TipoToken.IDENTIFIER){
            tipo = palabrasReservadas.getOrDefault(lexema, TipoToken.IDENTIFIER);
        }
        tokens.add(new Token(tipo, lexema, null));
        this.lexema = eliminarCaracteres(lexema, 0, lexema.length());
    }
    private void AddToTokenList(TipoToken tipo, String lexema, Object literal){
        tokens.add(new Token(tipo, lexema, literal));
        this.lexema = eliminarCaracteres(lexema, 0, lexema.length());
    }
    
}