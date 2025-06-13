import java.util.*;
// import SyntaxAnalyzer;

public class ScriptLexicalAnalyzer {    
    //public ArrayList<Object[]> TS;
    ScriptLexicalAnalyzer(){}
    
    // Enhanced Token types to include error type
    static enum TokenType {
        Keyword, ID, Datatype, 

        IntConst, FloatConst, CharConst, StringConst , BoolConst, Void,
        
        If, Else, Elseif,
        Switch, Case, 
        Default, Break, Continue,        
        Do, While, For,
        Return, 

        Class, Interface, 
        AccessModifier, Static, 
        Extends, Implements,
        This, Super, New, Final,

        Try, Catch, Finally, Throw,
        Null ,

        Comma, Colon, SemiColon, DBQoute, Qoute,
        OpenRoundBrkt, CloseRoundBrkt, OpenSqrBrkt, CloseSqtBrkt, OpenCurlyBrkt, CloseCurlyBrkt,
        Dot,

        AddSub, IncDec, MulDivMod, 
        RelationOp1, RelationOp2,
        Equal, CompoundAssign,
        AND, OR,NOT,

        InvalidInput,
        
        $        
    }

    // Java keywords
    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
        "chokidarAunty", "fomoBeti", "blogBaji", "channel", "kittyParty", "ammaOf", "kitty", "agar", "magar", "dekhoBaji", "agarMagar",
        "yaTo", "warna", "apHoAo", "Ao", "Ghumo", "rukBehn" , "chaloYar" , "yeLo", "pakdam", "pakdai", "yeToHoga", "panchait", 
        "zadaPanchait", "digitDidi", "pointyDidi", "hanKNa","han", "na", "alphaDidi", "khali", "chillKro", "yeh", "maharani","nayi",
        "betaDidi", "kuchNahi", "pakka"
        ));
        
        
        static class WordBreaker{
            String line;
            ArrayList<String> temps = new ArrayList<>();
            int lineNumber;
            static int Flag = 0;  //getValues to get the values
            boolean toggle=false;//dot breaker


        //constructor
        public WordBreaker(String line,  int lineNumber) {
            this.line=line;        
            this.lineNumber=lineNumber;
        }

        public ArrayList<String> getTemps(){
            StringBuilder str = new StringBuilder();
            char[] breakers={':',';',',','(',')','[',']','{','}','\n','\\'};
            char[] OP={'<','>','=','*','/','!'};
            
            
            //CommentBreaker!!!!!!
           
            // Loop through each character
            char[] chars = line.toCharArray();
            int lineLen=line.length();
            int index=0;
                     

            

            while(index<lineLen){
                char ch=chars[index]; 
                
               
                boolean endCmnt=false; 
            while(Flag==1 && index<lineLen) { 
                          
                  
                    if(ch=='<' && index+1<lineLen && chars[index+1]=='3'){
                        Flag=0;
                        index=index+2;
                        endCmnt=true;
                       
                        break;
                        
                    }
                    else{
                        index++;
                        ch=chars[index]; 
                    } 
                    // System.out.println("Flag: " + Flag + ", index: " + index + ", char: " + ch);
                    

                }
                if(endCmnt==true)break;
            

              //LoopOver Array Elements:
              boolean foundBreaker = false;               
              boolean foundROP = false; 
              
              for (char check : OP) {
                  if (check==ch) {
                  foundROP = true;
                  break;
                  }
              }
              for (char check : breakers) {
                  if (check==ch) {
                  foundBreaker = true;
                  break;
                  }
                 
              }
            //  if(ch=='.' && index+1<lineLen && index-1>-1){                
            //         char bfr=chars[index-1];
            //         char aft=chars[index+1];
            //         toggle=true;
            //         if(!Character.isDigit(bfr) && !Character.isDigit(aft) && toggle== true){
            //             toggle=false;
            //             foundBreaker = true;                  
                  
            //     }
            //   }
              
            
              
              
           
                
               //ignore Space
                if(ch==' '){
                    if(!str.isEmpty()){
                         //store previous Nonempty string in temp
                        temps.add(str.toString());
                        str.setLength(0);  //empty str              
                    }
                    index++;
                    continue; 
                }

               //ignore Comments
               //SingleLine
                else if(ch==':' && index+1 < lineLen && chars[index+1] == ')'){
                        if(!str.isEmpty()){
                            //store previous Nonempty string in temp
                           temps.add(str.toString());
                           str.setLength(0);  //empty str              
                       }     

                    //    index=lineLen-1;
                        break;
                    }   
                

               //MultiLine
               else if(ch=='<' && index+1 < lineLen && chars[index+1]=='3'){
                        Flag=1;
                        // index+=2; //<3 2 index place
                        // ch=chars[index];
                        if(!str.isEmpty()){
                            //store previous Nonempty string in temp
                           temps.add(str.toString());
                           str.setLength(0);  //empty str              
                       }     
                    // index=lineLen;
                     break;
                    }
                
                 //String
                
                 else if(ch=='\"'){
                    
                    if(!str.isEmpty()){
                        temps.add(str.toString());
                        str.setLength(0);//empty the string
                    }       
                    str.append(ch);
                    
                    while(index+1<lineLen && chars[index+1]!='\"'){
                        index++;
                        ch=chars[index];
                        str.append(ch);
                        if(ch=='\\' && index+1<lineLen && chars[index+1]=='\"'){
                            index++;
                            ch=chars[index];
                            str.append(ch);
                        }
                                               
                    }                    
                    if(index+1<lineLen && chars[index+1]=='\"'){
                        index++;
                        ch=chars[index];
                        str.append(ch);
                    }

                    temps.add(str.toString()); //add prev char to list
                    str.setLength(0); //clear str
                    }
                    
                 //char
                 else if(ch=='\''){

                    if(!str.isEmpty()){
                        temps.add(str.toString());
                        str.setLength(0);//empty the string
                    }   
                    str.append(ch);
                    
                    
                    int charCount=1;   
                    while(index+1<lineLen  && charCount<=2){
                        index++;
                        ch=chars[index];
                        str.append(ch);
                        if(ch=='\\' && index+1<lineLen && chars[index+1]=='\'' && charCount==1){
                            index++;
                            ch=chars[index];
                            str.append(ch);
                            charCount--;

                        }
                        else if(ch=='\\' && charCount==1){
                            
                            charCount--;
                            if(index+1<lineLen && chars[index+1]=='\\' && index+2<lineLen && chars[index+2]=='\''){
                                index++;
                                ch=chars[index];
                                str.append(ch);
                                index++;
                                ch=chars[index];
                                str.append(ch);
                                break;
                                
                            }

                        }
                        charCount++;
                                               
                    }                    
                    
                    temps.add(str.toString()); //add prev char to list
                    str.setLength(0); //clear str
                    
                }                
                //  else if(ch=='\''){

                //     if(!str.isEmpty()){
                //         temps.add(str.toString());
                //         str.setLength(0);//empty the string
                //     }   
                //     str.append(ch);
                    
                        
                //     while(index+1<lineLen && chars[index+1]!='\''){
                //         index++;
                //         ch=chars[index];
                //         str.append(ch);
                //         if(ch=='\\' && index+1<lineLen && chars[index+1]=='\''){
                //             index++;
                //             ch=chars[index];
                //             str.append(ch);
                //         }
                                               
                //     }                    
                //     if(index+1<lineLen && chars[index+1]=='\''){
                //         index++;
                //         ch=chars[index];
                //         str.append(ch);
                //     }

                //     temps.add(str.toString()); //add prev char to list
                //     str.setLength(0); //clear str
                    
                // }                

            //Relations Operators 
               else if(foundROP){
 
                 //store previous string in temp
                 if(!str.isEmpty()){
                     temps.add(str.toString());
                     str.setLength(0);//empty the string
                 }
                 
                str.append(ch);//add this char to the string
               
                if(index+1 < lineLen && chars[index+1]=='='){  //check
                    index++;
                    ch=chars[index];  //next char
                    str.append(ch); //join
                    temps.add(str.toString()); //add to list
                    str.setLength(0); //clear str
                }

                else{
                    temps.add(str.toString()); //add prev char to list
                    str.setLength(0); //clear str
                    }              
             }
            
            //++,--,+,-, +=,-=
                else if(ch=='+'||ch=='-'){
 
                 //store previous string in temp
                 if(!str.isEmpty()){
                     temps.add(str.toString());
                     str.setLength(0);//empty the string
                 }
                 str.append(ch);//add this char to the string
                 char store=ch;                 
               
                 if(chars[index+1]=='=' && index+1<lineLen){  //check
                     index++;
                     ch=chars[index];  //next char
                     str.append(ch); //join
                     temps.add(str.toString()); //add to list
                     str.setLength(0); //clear str
                 }
                
                //only ++ or Only -- not +- or -+
                else if(chars[index+1]==store && index+1<lineLen){  //check
                    index++;
                    ch=chars[index];    
                    str.append(ch); //join
                    temps.add(str.toString()); //add to list
                    str.setLength(0); //clear str
                }           

                else{
                    temps.add(str.toString()); //add prev char to list
                    str.setLength(0); //clear str
                    }     
                         
            }

            //&& ||
                else if(ch=='&'||ch=='|'){
 
                //store previous string in temp
                if(!str.isEmpty()){
                    temps.add(str.toString());
                    str.setLength(0);//empty the string
                }           
                //only && not &| and viceversa
                
                char store=ch;                 
                str.append(ch);//add this char to the string
               
                if(chars[index+1]==store && index+1<lineLen){  //check
                     index++;
                     ch=chars[index];  //next char  
                     str.append(ch); //join
                     temps.add(str.toString()); //add to list
                     str.setLength(0); //clear str
                 }     
            
                else{
                    temps.add(str.toString()); //add prev char to list
                    str.setLength(0); //clear str
                }
            }

               //float= 488.98.78; ERROR!
                //Dot
                else if(ch=='.'){
                    System.out.println("inside dot breaker check");
                    char prev='N';
                    char next='N';
                    if((index+1)<=lineLen-1 && index-1>=0){
                    prev=chars[index-1];
                    next=chars[index+1];
                    // if(toggle=false){
                    //     toggle=true;
                    // }
                    }
                        
                    if(Character.isDigit(prev) && Character.isDigit(next)){
                        str.append(ch);         
                                           
                    }                  
                    else{
                        if(!str.isEmpty()){
                            temps.add(str.toString());
                            str.setLength(0);//empty the string
                        }
                        str.append(ch);
                        temps.add(str.toString()); 
                        str.setLength(0);
                    
                }
                }
                  //punctuators
                  else if(foundBreaker){
 
                    //store previous string in temp
                    if(!str.isEmpty()){
                        temps.add(str.toString());
                        str.setLength(0);//empty the string
                   }
                    
                       str.append(ch);//add this char to the string
                   
                       temps.add(str.toString()); //add prev char to list
                       str.setLength(0); //clear str                
                                             
                   }
                
                else{
                    str.append(ch);   
                }
                                  
                
                if(index==lineLen-1 && !str.isEmpty()){
                    temps.add(str.toString());
                    str.setLength(0);
                    break;
                }
                index++;

            }        
        
            return temps; }
                }


    // Tokenize input method
    public static ArrayList<Object[]> tokenize( ArrayList<String> input,int lineNum) {
        ArrayList<String> tokens = new ArrayList<>();
        int lineNumber = lineNum;                               
        TokenType classPart= null;
        StringBuilder newToken=new StringBuilder();
        ArrayList<Object[]> TOKENS= new ArrayList<>();
             // Try to match tokens
            for (String match: input) {
                    
                // Determine token type   

                    //EndMarker
                    if (match.contains("$")) {                      
                        classPart = TokenType.$; }

                    //Constants
                    
                    else if (match.matches("^[\\+-]?[0-9]+\\.[0-9]+")) {  //-?0-9*.0-9+                        
                        classPart = TokenType.FloatConst; }

                    // else if( match.matches("(0-9a-zA-Z)*") ){
                    //     classPart = TokenType.InvalidInput;
                    // }

                    else if (match.matches("^[\\+-]?[0-9]+")) {                        
                            classPart = TokenType.IntConst; }
                            
                    // match.matches("'(\\\\)(\")'")  
                    else if(match.matches("^'.'")||match.matches("'(\\\\n)'")||match.matches("'(\\\\r)'")||match.matches("'(\\\\b)'")||match.matches("'(\\\\f)'")||match.matches("'(\\\\')'")||match.matches("'(\\\\)(\")'")||match.matches("'(\\\\)(\\\\)'")){
                            match= match.substring(1, match.length() - 1);
                            classPart = TokenType.CharConst;
                        }

                    else if (match.matches("^\".*\"")) {
                        //remove qoutes
                        match= match.substring(1, match.length() - 1);
                        classPart = TokenType.StringConst; }
                                 

                    //KeyWords 
                    else if (KEYWORDS.contains(match)) {
                        switch (match) {
                            case "chokidarAunty":
                            case "fomoBeti":
                            case "blogBaji":
                                classPart=TokenType.AccessModifier;
                                break;
                            case "channel":
                                classPart=TokenType.Class;
                                break;
                            case "kittyParty":
                                classPart=TokenType.Interface;
                                break;
                            case "ammaOf":
                                classPart=TokenType.Extends;
                                break;
                            case "kitty":
                                classPart=TokenType.Implements;
                                break;
                            case "agar":
                                classPart=TokenType.If;
                                break;
                            case "magar":
                                classPart=TokenType.Else;
                                break;
                            case "agarMagar":
                                classPart=TokenType.Elseif;
                                break;
                            case "dekhoBaji":
                                classPart=TokenType.Switch;
                                break;
                            case "yaTo":
                                classPart=TokenType.Case;
                                break;
                            case "warna":
                                classPart=TokenType.Default;
                                break;
                            case "apHoAo":
                                classPart=TokenType.For;
                                break;
                            case "Ao":
                                classPart=TokenType.Do;
                                break;
                            case "Ghumo":
                                classPart=TokenType.While;
                                break;
                            case "rukBehn":
                                classPart=TokenType.Break;
                                break;
                            case "chaloYar":
                                classPart=TokenType.Continue;
                                break;
                            case "yeLo":
                                classPart=TokenType.Return;
                                break;
                            case "pakdam":
                                classPart=TokenType.Try;
                                break;
                            case "pakdai":
                                classPart=TokenType.Catch;
                                break;
                            case "yeToHoga":
                                classPart=TokenType.Finally;
                                break;
                            case "panchait":
                            // case "zadaPanchait":
                                classPart=TokenType.Throw;
                                break;
                            case "chillKro":
                                classPart=TokenType.Static;
                                break;
                            case "digitDidi":
                            case "pointyDidi":
                            case "hanKNa":
                            case "alphaDidi":
                                classPart=TokenType.Datatype;
                                break;
                            case "han":
                            case "na":
                                classPart=TokenType.BoolConst;
                                break;
                            case "betaDidi":
                                classPart=TokenType.Datatype;
                                break;
                            case "yeh":
                                classPart=TokenType.This;
                                break;
                            case "maharani":
                                classPart=TokenType.Super;
                                break;
                            case "nayi":
                                classPart=TokenType.New;
                                break;
                            case "kuchNahi":
                                classPart=TokenType.Null;
                                break;
                            case "pakka":
                                classPart=TokenType.Final;
                                break;
                            case "khali":
                                classPart=TokenType.Void;
                                break;
                            default:
                                classPart=TokenType.InvalidInput;
                                break;
                        }
                       
                        
                    } 
                    
                    //Operators
                    else if (match.matches("^[+\\-*/%=<>!&|^~]+")) {
                        if(match.matches("[+-]")) {
                            classPart=TokenType.AddSub;
                        } else if (match.matches("\\+\\+|--")) {
                            classPart=TokenType.IncDec;
                        }else if(match.matches("[*/%]")){
                            classPart=TokenType.MulDivMod;
                        }else if(match.matches("=")){
                            classPart=TokenType.Equal;
                        }else if (match.matches("(&&)")){
                            classPart=TokenType.AND;
                        }else if (match.matches("(\\|\\|)")){
                            classPart=TokenType.OR;
                        }else if (match.matches ("!")){
                            classPart=TokenType.NOT;
                        } 
                        else if (match.matches("(<=|>=|<|>)")){
                            classPart=TokenType.RelationOp1;
                        } 
                        else if (match.matches("(!=|==)")){
                            classPart=TokenType.RelationOp2;
                        }
                        else if (match.matches("(\\+=|-=|\\*=|/=|%=)")){
                            classPart=TokenType.CompoundAssign;
                            // match.matches("(\\+=|-=|\\*=|/=|%=)")
                        }
                        else{
                            classPart=TokenType.InvalidInput;
                        } 
                    }

                    //Punctuators
                    else if (match.matches("^[(){}\\[\\]\':;,.]")){
                        if (match.matches("[\\\"]")){
                            classPart=TokenType.Comma;
                        }
                        else if (match.matches("[:]")){
                            classPart=TokenType.Colon;
                        }
                        else if (match.matches("[;]")){
                            classPart=TokenType.SemiColon;
                        }
                        else if (match.matches("[\\\']")){
                            classPart=TokenType.Qoute;
                        }
                        else if (match.matches("[\\\"]")){
                            classPart=TokenType.DBQoute;
                        }
                        else if (match.matches("[\\(]")){
                            classPart=TokenType.OpenRoundBrkt;
                        }
                        else if (match.matches("[\\)]")){
                            classPart=TokenType.CloseRoundBrkt;
                        }
                        else if (match.matches("[\\[]")){
                            classPart=TokenType.OpenSqrBrkt;
                        }
                        else if (match.matches("[\\]]")){
                            classPart=TokenType.CloseSqtBrkt;
                        }
                        else if (match.matches("[\\{]")){
                            classPart=TokenType.OpenCurlyBrkt;
                        }
                        else if (match.matches("[\\}]")){
                            classPart=TokenType.CloseCurlyBrkt;
                        }
                        else if (match.matches("[\\.]")){
                            classPart=TokenType.Dot;
                        }
                        else if (match.matches("[,]")){
                            classPart=TokenType.Comma;
                        }
                        else{
                            classPart=TokenType.InvalidInput;
                        }
                       
                    } 

                    //Idendtifiers
                            //   "^[a-zA-Z][a-zA-Z0-9~]*[0-9]$"
                    else if (match.matches("^[a-zA-Z][a-zA-Z0-9~]*[0-9]$")) {
                        classPart = TokenType.ID;
                    } 


                    // If no pattern matches, create an error token
                    else {                        
                        classPart = TokenType.InvalidInput;
                    }
                    
                    // Add token
                    // tokens.add(new Token(classPart , match, lineNumber));      
                    newToken.append("( ").append(classPart).append(" , ").append(match).append(" , ").append(lineNumber).append(" )"); 
                    tokens.add(newToken.toString());
                    newToken.setLength(0);    
 
                    TOKENS.add(new Object[]{classPart,match,lineNumber});                       
                                 
                }

        return TOKENS;
    }; 

    
}

//javac ScriptLexicalAnalyzer.java
//java ScriptLexicalAnalyzer.java
//java ScriptLexicalAnalyzer input2.txt

