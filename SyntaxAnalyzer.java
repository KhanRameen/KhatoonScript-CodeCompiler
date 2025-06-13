import java.util.ArrayList;
// import ScriptLexicalAnalyzer.TokenType;

public class SyntaxAnalyzer {
    static int index=0;
    ArrayList<Object[]> TS;  //Get Tokens from LexicalAnalyer.
    ScriptLexicalAnalyzer.TokenType TokenType;

    SyntaxAnalyzer(ArrayList<Object[]> tokens){
        this.TS=tokens;       
        // if (Script()){
        //     if(this.TS(index)[0]=="$"){
        //         System.out.println("Valid Syntax. Code Parsed!");
        //         return;
        //     }
        //     System.out.println("MissingEndMarker");
        //     return;
        //   }
        // System.out.println("Invalid Syntax at line: "+TS(index)[2]);   
    } 
    boolean Run(){
        if (Script()){
            if(TS.get(index)[0].equals(TokenType.$)){
                System.out.println("Valid Syntax. Code Parsed!");
                return true;
            }
            System.out.println("MissingEndMarker");
            return true;
          }
        System.out.println("Invalid Syntax at line: "+TS.get(index)[2]); 
        return false;
    }



//CFGS
    boolean Script(){
            if(HasInterface()){
                if(MainClass()){
                    if(HasMultiClass()){
                        return true;
                    }
               }
            }
            else if( TS.get(index)[0].equals(TokenType.$)){
                return true;
            }
    
            //else         
            else{System.out.println("No check");}
            return false;

      }//fnEnd
     
  

//HasInterface
    boolean HasInterface(){        
        if(Interface()){
                if(HasInterface()){
                    return true;
                }
            }

       else if(TS.get(index)[0].equals(TokenType.Class) 
       || (TS.get(index)[1].equals("blogBaji") && (index+1<TS.size()) && TS.get(index+1)[0].equals(TokenType.Class))){
           System.out.println("Inside HasInterface Follow");
           return true;
       }
            
        return false;
    }

//Interface
boolean Interface(){
    if(isPublic()){  
        System.out.println("Inside Interface Public");    
        if(TS.get(index)[0].equals(TokenType.Interface)){                       
            System.out.println("Inside Interface Interface");    
            index++;
            if(TS.get(index)[0].equals(TokenType.ID)){
                System.out.println("Inside Interface ID");    
                index++;
                if(Extends()){
                    System.out.println("Inside Interface under Extends");    
                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                        System.out.println("Inside Interface {");    
                        index++;
                        if(InterfaceBody()){
                            System.out.println("Inside Interface under InterfaceBody");    
                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                System.out.println("Inside Interface under }");    
                                index++;
                                return true;
                            }
                        }
                    }
                }
             }   
        }
            
        }//ifEnd 
        return false;     
    }//fnEnd

            //isPublic
            boolean isPublic(){
                if(TS.get(index)[1].equals("blogBaji")){
                    System.out.println("Inside Public");
                    index++;
                    
                    return true;
                }
                else if(TS.get(index)[0].equals(TokenType.Class) || TS.get(index)[0].equals(TokenType.Interface)){
                    System.out.println("Inside isPublic Follow");
                    
                    return true;
                }
                return false;
            }

            //Extends
            boolean Extends(){
                System.out.println("Inside Extends");
                if(TS.get(index)[0].equals(TokenType.Extends)){
                        System.out.println("Inside Extends Token");
                        index++;
                        if(TS.get(index)[0].equals(TokenType.ID)){
                            System.out.println("Inside Extends ID");
                            index++;
                            return true;
                        }
                    }
                    else if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                        System.out.println("Extends Follow. Index: "+ index);
                        return true;
                    }
                return false;
            }

            //InterfaceBody
            boolean InterfaceBody(){
                System.out.println("Inside InterfaceBody");
                if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                    System.out.println("Inside interfaceBody Follow");
                    return true;
                }
                else if(InterfaceOptions()){
                    System.out.println("Inside InterfaceBody Under Options");
                    if(InterfaceBody()){
                        System.out.println("Inside InterfaceBody LoopOver");
                        return true;
                    }
                }
                return false;
            }

            //InterfaceOptions
            boolean InterfaceOptions(){
                System.out.println("Inside InterfaceOptions");
                if(//Feilds Syntax Check
                    ((TS.get(index)[0].equals(TokenType.Datatype) ||TS.get(index)[0].equals(TokenType.Void)) 
                    && TS.get(index+1)[0].equals(TokenType.ID) 
                    && ( TS.get(index+2)[0].equals(TokenType.Equal)
                    || TS.get(index+2)[0].equals(TokenType.OpenSqrBrkt)))
                ){  System.out.println("Inside InterfaceOption Fields Call");
                if(InterfaceFeilds()){
                    return true;
                    }
                }
                //InterfaceMethods Check
                else if(
                    TS.get(index)[0].equals(TokenType.Static) ||
                    ((TS.get(index)[0].equals(TokenType.Datatype) ||TS.get(index)[0].equals(TokenType.Void)) 
                    && TS.get(index+1)[0].equals(TokenType.ID)
                    && TS.get(index+2)[0].equals(TokenType.OpenRoundBrkt)) 
                ){
                    if(InterfaceMethods()){
                        System.out.println("Inside InterfaceOption Methods Call");
                        return true;
                    }
                }
                    //Nested Interface
                else if(Interface()){
                    System.out.println("Inside InterfaceOption Interface Call");
                    return true;
                }                
                    return false;
            }

        boolean InterfaceFeilds(){
            System.out.println("Inside InterfaceFeilds");
            if(TS.get(index)[0].equals(TokenType.Datatype)){
                index++;
                System.out.println("Inside InterfaceFeilds DataType");
                if(IFeildsCases()){
                    System.out.println("Inside InterfaceFeilds Under IFCases");
                    return true;
                }
            }
            return false;
        }
        
        boolean IFeildsCases(){
            System.out.println("Inside IFCases");
            if(TS.get(index)[0].equals(TokenType.ID)){
                System.out.println("Inside IFCases ID");
                index++;
                if(TS.get(index)[0].equals(TokenType.Equal)){
                    System.out.println("Inside IFCases =");
                    index++;
                    if(TS.get(index)[0].equals(TokenType.IntConst)
                    ||TS.get(index)[0].equals(TokenType.StringConst)
                    || TS.get(index)[0].equals(TokenType.BoolConst)
                    || TS.get(index)[0].equals(TokenType.CharConst)
                    || TS.get(index)[0].equals(TokenType.FloatConst)){
                        index++;    
                        System.out.println("Inside IFCases Const");
                        if(TS.get(index)[0].equals(TokenType.SemiColon)){
                            System.out.println("Inside IFCases ;");
                            return true;
                        }
                    }
                }
            }
            else if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                System.out.println("Inside IFCases [");
                index++;
                if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                    System.out.println("Inside IFCases ]");
                    index++;
                    if(TS.get(index)[0].equals(TokenType.ID)){
                        System.out.println("Inside IFCases ID");
                        index++;
                        if(TS.get(index)[0].equals(TokenType.Equal)){
                            System.out.println("Inside IFCases =");
                            index++;
                            if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                System.out.println("Inside IFCases {");
                                index++;
                                
                                    if(ArrayList()){
                                        System.out.println("Inside IFCases under ArraList");
                                        if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                                            System.out.println("Inside IFCases }");
                                            index++;
                                            return true;
                                        }
                                    }
                                
                            }
                        }
                    }
                }
            }
            return false;
        }

        boolean ArrayList(){
            System.out.println("Inside ArrayList");            
            if(TS.get(index)[0].equals(TokenType.IntConst)
                ||TS.get(index)[0].equals(TokenType.StringConst)
                || TS.get(index)[0].equals(TokenType.BoolConst)
                || TS.get(index)[0].equals(TokenType.CharConst)
                || TS.get(index)[0].equals(TokenType.FloatConst)){
                    index++;
                    System.out.println("Inside ArrayList const");
                    if(ArrayListAddOn()){
                        System.out.println("Inside ArrayList under Addons");
                        return true;
                    }                    
                    
                }
            
            else if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                System.out.println("Inside ArrayList }");
                
                return true;
            }
         return false;
        }

        boolean ArrayListAddOn(){
            System.out.println("Inside ArrayListAddOn");
            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                System.out.println("Inside ArrayListaddOn follow");
                
                return true;
            }
            else if(TS.get(index)[0].equals(TokenType.Comma)){
                System.out.println("Inside ,");
                index++;
                if(TS.get(index)[0].equals(TokenType.IntConst)
                ||TS.get(index)[0].equals(TokenType.StringConst)
                || TS.get(index)[0].equals(TokenType.BoolConst)
                || TS.get(index)[0].equals(TokenType.CharConst)
                || TS.get(index)[0].equals(TokenType.FloatConst)){
                    index++;
                    System.out.println("Inside ArrayListAddOn const");
                    if(ArrayListAddOn()){
                        System.out.println("Inside ArrayListaddOn Loop");
                        return true;
                    }
                    
                    
                }
            }
         return false;
        }

        boolean InterfaceMethods(){
            System.out.println("Inside InterfacMethod");
            if(ReturnType()){
                System.out.println("Inside InterfaceMethod Under Return");
                if(TS.get(index)[0].equals(TokenType.ID)){
                    System.out.println("Inside InterfaceMethod ID");
                    index++;
                    if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                        System.out.println("Inside InterfaceMethod (");
                        index++;
                        if(params()){
                            if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    System.out.println("Inside InterfaceMethod )");
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.SemiColon)){
                                        index++;
                                        System.out.println("Inside InterfaceMethod ;");
                                        return true;
                                    }
                                    
                                }
                            }
                        }
                    }
                }
                else if (TS.get(index)[0].equals(TokenType.Static)){
                    index++;
                    System.out.println("Inside InterfaceMethod Static");
                    if(ReturnType()){
                        System.out.println("Inside InterfaceMethod Under ReturnType");
                    if(TS.get(index)[0].equals(TokenType.ID)){
                        index++;
                        System.out.println("Inside InterfaceMethod StaticID");
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            System.out.println("Inside InterfaceMethod Static (");
                            index++;
                            if(params()){
                                System.out.println("Inside InterfaceMethod Static under Params");
                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    System.out.println("Inside InterfaceMethod Static )");
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                        System.out.println("Inside InterfaceMethod Static {");
                                        index++;
                                        if(MST()){
                                            System.out.println("Inside InterfaceMethod Static under MST");
                                            if(ReturnSt()){
                                                System.out.println("Inside InterfaceMethod Static under MST");
                                                if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                    System.out.println("Inside InterfaceMethod Static }");
                                                    index++;
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        // boolean ReturnType(){
        //     if(TS.get(index)[0].equals(TokenType.Void)){
        //         index++;
        //         return true;
        //     }
        //     else if(TS.get(index)[0].equals(TokenType.ID)){
        //         index++;
        //         return true;
        //     }
        //     else if(TS.get(index)[0].equals(TokenType.Datatype)){
        //         index++;
        //         if(isArray()){
        //             return true;
        //         }
        //     }

        // }

        // boolean isArray(){
        //     if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
        //         index++;
        //         if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
        //             index++;
        //             return true;
        //         }
        //     }
        //     else if(TS.get(index)[0].equals(TokenType.ID)){
        //         return true;
        //     }
        //     return false;
        // }



//MainClass
boolean MainClass(){
       if(isPublic()){
        System.out.println("Inside Class under Public");
        if(TS.get(index)[0].equals(TokenType.Class)){
            index++;
            System.out.println("Inside Main Class Class");
            if(TS.get(index)[0].equals(TokenType.ID)){
                index++;
                System.out.println("Inside Main Class Under ID");
                if(ClassOptions()){
                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                        index++;
                        System.out.println("Inside Class open {");
                        if(MainMethod()){
                            System.out.println("Inside Class under MainMethod {");
                            if(ClassBody()){
                                System.out.println("Inside Class under ClassBody");
                                if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            
            }
        }
       } 
       return false;}

       //ClassOptions
       boolean ClassOptions(){
        System.out.println("Inside class Options");
        if(Extends()){
            if(Implements()){
                return true;
            }
        }
        else if(Implements()){
            if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                index++;
                return true;
            }
        }
        return false;
       }

            //Implements
            boolean Implements(){
                System.out.println("Inside Implements");
                if(TS.get(index)[0].equals(TokenType.Implements)){
                    index++;
                    if(TS.get(index)[0].equals(TokenType.ID)){
                        index++;
                        if(ImplementOptions()){
                            return true;
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                    return true;
                }
                return false;
            }

                    //ImplemetOptions
                    boolean ImplementOptions(){
                        if(TS.get(index)[0].equals(TokenType.Comma)){
                            index++;
                            if(TS.get(index)[0].equals(TokenType.ID)){
                                index++;
                                if(ImplementOptions()){
                                    return true;
                                }
                            }
                        }
                        else if (TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                            return true;
                        }
                        return false;
                    }

       //MainMethod
       boolean MainMethod(){

        System.out.println("Inside Main Method");

            if(TS.get(index)[1].equals("blogBaji")){
                index++;
                System.out.println("Inside Main Method blogBaji");

                if(TS.get(index)[0].equals(TokenType.Static)){
                    index++;
                    System.out.println("Inside Main Method Static");
                    if(TS.get(index)[0].equals(TokenType.Void)){
                        index++;
                        System.out.println("Inside Main Method Void");
                        if(TS.get(index)[1].equals("main")){
                            index++;
                            System.out.println("Inside Main Method main");
                            if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                                index++;
                                System.out.println("Inside Main Method (");
                                if(TS.get(index)[0].equals(TokenType.Datatype)){
                                    index++;
                                    System.out.println("Inside Main Method DT");
                                    if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                                        index++;
                                        System.out.println("Inside Main Method [");
                                        if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                                            index++;
                                            System.out.println("Inside Main Method ]");
                                            if(TS.get(index)[1].equals("args")){
                                                index++;
                                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                                    index++;
                                                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                                        index++;
                                                        if(MST()){
                                                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                                index++;
                                                                return true;
                                                            }
                                                        }
                                                    }
                                            
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
    }

   




        //ClassBody
        boolean ClassBody(){
            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                System.out.println("inside ClassBody Follow ");
                return true;
            }
            else if(AccessModifier()){
                System.out.println("inside ClassBody after accessModifier");                
                if(ClassBodyOptions()){
                    System.out.println("inside ClassBody after Options");
                    //if(!(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt))){
                        if(ClassBody()){
                            System.out.println("inside ClassBody LoopOver");
                            return true;
                        }
                    //}
                }

            }
            // else if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){     
            //     System.out.println("inside ClassBody Follow");
            //     return true;     
            // }

            return false;
        }

            //ClassBodyOptions
            boolean ClassBodyOptions(){
                System.out.println("inside ClassBodyOptions");

                if(TS.get(index)[0].equals(TokenType.ID) && TS.get(index+1)[0].equals(TokenType.OpenRoundBrkt)){
                    if(Constructor()){
                        System.out.println("inside ClassBodyOptions Constructor");                    
                        return true;
                    }
                }

                else if (Class()){
                    System.out.println("inside ClassBodyOptions Class");
                    return true;
                }
                else if(isStatic()){
                    System.out.println("inside ClassBodyOptions isStatic");
                    if(ClassBodyBlock()){
                        System.out.println("inside ClassBody classBodyBlock");
                        return true;
                    }
                }
                // else{
                //     System.out.println("Class BodyOption False for token: "+TS.get(index)[0]);
                // }  
                else if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                    return true;
                }
                return false;
            }

                //isStatic  
                boolean isStatic(){
                    if(TS.get(index)[0].equals(TokenType.Static)){
                        index++;
                        return true;
                    }
                    else if(TS.get(index)[0].equals(TokenType.Void)
                    ||TS.get(index)[0].equals(TokenType.ID)
                    || TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)
                    || TS.get(index)[0].equals(TokenType.Final)
                    ||TS.get(index)[0].equals(TokenType.Datatype)
                    ){
                        return true;
                    }
                   
                    return false;
                }

                //ClassBodyBlock
                boolean ClassBodyBlock(){
                    System.out.println("inside ClassBodyBlock");                    
                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                        index++;
                        System.out.println("inside ClassBodyBlock abstract");
                        if(MST()){
                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                index++;
                                return true;
                            }
                        }
                    }
                    else if(
                        (TS.get(index)[0].equals(TokenType.Datatype)
                        || TS.get(index)[0].equals(TokenType.Void) ||  TS.get(index)[0].equals(TokenType.ID) )
                        && (TS.get(index+1)[0].equals(TokenType.ID) && TS.get(index+2)[0].equals(TokenType.OpenRoundBrkt) ) ||(TS.get(index+1)[0].equals(TokenType.OpenSqrBrkt) && TS.get(index+2)[0].equals(TokenType.CloseSqtBrkt) && TS.get(index+3)[0].equals(TokenType.ID) && TS.get(index+4)[0].equals(TokenType.OpenRoundBrkt))
                                            
                        ){
                            System.out.println("inside ClassBodyBlock Method");
                            if(Method()){
                                return true;
                            }
                        }
                        else if(Feilds()){
                        System.out.println("inside ClassBodyBlock Feilds");
                        return true;
                    }
                 
                    return false;
                }
            //AccessModifier
            boolean AccessModifier(){
                System.out.println("Inside AccessModifier");
                if(TS.get(index)[0].equals(TokenType.AccessModifier)){
                    index++;
                    return true;
                }
                else if(TS.get(index)[0].equals(TokenType.Void)
                ||TS.get(index)[0].equals(TokenType.Class)
                ||TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)
                ||TS.get(index)[0].equals(TokenType.Datatype)
                || TS.get(index)[0].equals(TokenType.Final)
                ||TS.get(index)[0].equals(TokenType.ID)
                || TS.get(index)[0].equals(TokenType.CloseCurlyBrkt))
                {
                    return true;
                }
                return false;
            }

            //Method
            boolean Method(){
                System.out.println("Inside Class Method");
                if(ReturnType()){
                    System.out.println("Inside Class Method under ReturnType");
                    if(TS.get(index)[0].equals(TokenType.ID)){
                        index++;
                        System.out.println("Inside Class Method ID");
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            System.out.println("Inside Class Method (");
                            index++;
                            if(params()){
                                System.out.println("Inside Class Method params");
                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    System.out.println("Inside Class Method )");
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                        System.out.println("Inside Class Method {");
                                        index++;
                                        if(MST()){
                                            System.out.println("Inside Class Method under MST");
                                            if(ReturnSt()){
                                                System.out.println("Inside Class Method under ReturnST");
                                                if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                    System.out.println("Inside Class Method }");
                                                    index++;
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }   
                //ReturnType
                boolean ReturnType(){
                    System.out.println("Inside Returntype");
                    if(TS.get(index)[0].equals(TokenType.Void)){
                        System.out.println("Inside Returntype Void");
                        index++;
                        return true;
                    }
                    else if(TS.get(index)[0].equals(TokenType.ID)){
                        System.out.println("Inside Returntype ID");
                        index++;
                        if(isArray()){
                            System.out.println("Inside Returntype Under IsArray");
                            return true;
                        }
                        return true;
                    }
                    else if(TS.get(index)[0].equals(TokenType.Datatype)){
                        System.out.println("Inside Returntype DataType");
                        index++;
                        if(isArray()){
                            System.out.println("Inside Returntype Under IsArray");
                            return true;
                        }
                    }
                    return false;
                }
                
                boolean isArray(){
                    System.out.println("Inside IsArray");
                    if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                            System.out.println("Inside IsArray [");
                            index++;
                            if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                                index++;
                                System.out.println("Inside IsArray]");
                                return true;
                            }
                        }
                        else if (TS.get(index)[0].equals(TokenType.ID)){
                            System.out.println("Inside IsArray Follow");
                            return true;
                        }
                        return false;
                    }
                //ReturnSt
                    boolean ReturnSt(){
                        if(TS.get(index)[0].equals(TokenType.Return)){
                            index++;
                            if(ReturnValue()){
                                if(TS.get(index)[0].equals(TokenType.SemiColon)){
                                    index++;
                                    return true;
                                }
                            }       
                        }
                        return false;
                    }
                //ReturnValue
                    boolean ReturnValue(){
                        if(TS.get(index)[0].equals(TokenType.Null)){ 
                            return true;
                        }
                        else if(TS.get(index)[0].equals(TokenType.IntConst)||TS.get(index)[0].equals(TokenType.BoolConst)||TS.get(index)[0].equals(TokenType.StringConst)||TS.get(index)[0].equals(TokenType.CharConst)){
                            return true;
                        }
                        else if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            index++;
                            if(expression()){
                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    index++;
                                    return true;
                                }
                            }
                        }
                        else if(TS()){
                            if(TS.get(index)[0].equals(TokenType.ID)){
                                index++;
                                if(ReturnCases()){
                                    return true;
                                }
                            }
                        }
                        else if(TS.get(index)[0].equals(TokenType.New)){
                            index++;
                            if(newOptions()){
                                return true;
                            }
                        }
                        return false;

                    }
                        // Boolean TS(){
                        //     if(TS.get(index)[0].equals(TokenType.This)){
                        //         index++;
                        //         if(TS.get(index)[0].equals(TokenType.Dot)){
                        //             index++;
                        //             return true;
                        //         }
                        //     }
                        //     else if(TS.get(index)[0].equals(TokenType.Super)){
                        //         index++;
                        //         if(TS.get(index)[0].equals(TokenType.Dot)){
                        //             index++;
                        //             return true;
                        //         }
                        //     }
                        //     else if(TS.get(index)[0].equals(TokenType.ID)){
                        //         return true;
                        //     }
                        //     return false;
                        // }

                        boolean newOptions(){
                            if(TS.get(index)[0].equals(TokenType.ID)){
                                index++;
                                if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                                    index++;
                                    if(arguments()){
                                        if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                            }
                            else if(TS.get(index)[0].equals(TokenType.Datatype)){
                                if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                                        index++;
                                        if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                            index++;
                                            if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                                index++;
                                                if(ArrayList()){
                                                    if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                        index++;
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                }
                            }
                            return false;
                        }
                        boolean ReturnCases(){
                            if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                                index++;
                                if(arguments()){
                                    if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                        index++;
                                        return true;
                                    }
                                }
                            }
                            else if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                                index++;
                                if(arrayArguments()){
                                    if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                                        index++;
                                        if(dotCases()){
                                            if(Options()){
                                                index++;
                                            }
                                        }
                                    }
                                }
                            }
                            else if(TS.get(index)[0].equals(TokenType.SemiColon)){
                                return true;
                            }
                            return false;
                        }

                      
           
           
            //Feild
            boolean Feilds(){
                System.out.println("inside Feilds");
                if(isFinal()){
                    System.out.println("inside Feilds under Final");
                    if(DeclartionOptions()){
                        return true;
                    }
                }
                return false;
            }
            boolean isFinal(){
                System.out.println("inside Feilds isFinal");                
                if(TS.get(index)[0].equals(TokenType.Final)){
                    index++;
                    return true;
                }
                return false;
            }
            Boolean DeclartionOptions(){
                System.out.println("inside Feilds DeclarationOptions");
                if(Dec()){
                    if(TS.get(index)[0].equals(TokenType.SemiColon)){
                        index++;
                        return true;
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                    index++;
                    if(MultiDec()){
                        if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                            index++;
                            return true;
                        }
                    }
                }
                return false;
            } 

            Boolean MultiDec(){
                if(Dec()){
                    if(TS.get(index)[0].equals(TokenType.SemiColon)){
                        index++;
                        return true;
                    }
                }
                else if (TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                    return true;
                }
                return false;
            }   

            //constructor
            boolean Constructor(){
                System.out.println("Inside Contructor");
                if(TS.get(index)[0].equals(TokenType.ID)){
                    System.out.println("Inside Contructor ID");
                    index++;
                    if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                        System.out.println("Inside Contructor (");
                        index++;
                        if(params()){
                            System.out.println("Inside Contructor under params");
                            if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                System.out.println("Inside Contructor )");
                                index++;
                                if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                    index++;
                                    System.out.println("Inside Contructor {");
                                    if(TSNT()){
                                        System.out.println("Inside Contructor under TSNT");
                                        if(MST()){
                                            System.out.println("Inside Contructor under MST");
                                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                System.out.println("Inside Contructor }");
                                                index++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
            
            boolean TSNT(){

                System.out.println("inside TSNT");
                
                if(TS.get(index)[0].equals(TokenType.This)){
                        System.out.println("inside TSNT This ");
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            System.out.println("inside TSNT This (");
                            index++;
                            if(arguments()){
                                System.out.println("inside TSNT This under args");
                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    index++;
                                    System.out.println("inside TSNT This )");
                                    if(TS.get(index)[0].equals(TokenType.SemiColon)){
                                        index++;
                                        System.out.println("inside TSNT This ;");
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                else if(TS.get(index)[0].equals(TokenType.Super)){
                    System.out.println("inside TSNT Super");
                    index++;
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            System.out.println("inside TSNT Super (");
                            index++;
                            if(arguments()){
                                System.out.println("inside TSNT Super under args");
                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    System.out.println("inside TSNT Super )");
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.SemiColon)){
                                        System.out.println("inside TSNT Super ;");
                                        index++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                else if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)
                        ||TS.get(index)[0].equals(TokenType.ID)
                        ||TS.get(index)[0].equals(TokenType.If)
                        ||TS.get(index)[0].equals(TokenType.For)
                        ||TS.get(index)[0].equals(TokenType.Do)
                        ||TS.get(index)[0].equals(TokenType.Switch)
                        ||TS.get(index)[0].equals(TokenType.Super)
                        ||TS.get(index)[0].equals(TokenType.This)
                        ||TS.get(index)[0].equals(TokenType.Try)
                        ||TS.get(index)[0].equals(TokenType.Throw)                              
                        ||TS.get(index)[0].equals(TokenType.Datatype)                              
                ){
                    System.out.println("inside tsnt Follow");
                    return true;
                }

                return false;
            }
    

    
//HasMultiClass
    boolean HasMultiClass(){
        System.out.println("HasMulti Classes");
        if(Class()){
            System.out.println("Multi Class");
            if(HasMultiClass()){
                return true;
            }
        }
        
        else if(TS.get(index)[0].equals(TokenType.$)){       
            return true;
        }
        
        return false;
    }
    
    boolean Class(){
        System.out.println("Inside Class");
        if(TS.get(index)[0].equals(TokenType.Class)){
            System.out.println("Inside Class keyword");
            index++;
            if(TS.get(index)[0].equals(TokenType.ID)){
                System.out.println("Inside Class id");
                index++;
                if(ClassOptions()){
                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                        System.out.println("Inside Class {");
                        index++;
                        if(ClassBody()){
                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                System.out.println("Inside Class }");
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
       
        return false;
    }

    
    // boolean arguments(){return true;}
    // boolean MST(){return true;}
    // boolean params(){return true;}
    // boolean dotCases(){return true;}
    // boolean arrayArguments(){return true;}
    // boolean Options(){return true;}
    // boolean Dec(){return true;}
    // boolean expression(){return true;}


    public boolean SST(){
        System.out.println("Inside SST");
        if(TS.get(index)[0].equals(TokenType.ID)){
            
                if(IDStatement()){
                    
                    if(TS.get(index)[0].equals(TokenType.SemiColon)){
                        index++;
                        
                        return true;
                    }

                }
        }
        else if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super)){
            if(TSIDStatement()){
                if(TS.get(index)[0].equals(TokenType.SemiColon)){
                    index++;
                    return true;
                }
            }

        }
        else if(TS.get(index)[0].equals(TokenType.If)){
            if(If_Else()){
               
                return true;
            }

        }
        else if(TS.get(index)[0].equals(TokenType.Do)){
            if(Do_While()){
                return true;
            }

        }
        else if(TS.get(index)[0].equals(TokenType.Switch)){
            if(Switch()){
                return true;
            }
        }
        else if(TS.get(index)[0].equals(TokenType.Datatype)){
            if(Dec()){
                if(TS.get(index)[0].equals(TokenType.SemiColon)){
                    index++;
                    return true;
                }
            }

        }
        else if(TS.get(index)[0].equals(TokenType.Try)){
            if(Try_Catch()){
                return true;
            }

        }
        else if(TS.get(index)[0].equals(TokenType.For)){
            if(For_Loop()){               
                return true;
            }

        }
        else if(TS.get(index)[1].equals("panchait")){
            if(Throw()){
                if(TS.get(index)[0].equals(TokenType.SemiColon)){
                    index++;
                    return true;
                }
            }

        }
       
        return false;
        }

        
        public boolean MST(){
            System.out.println("Inside MST");
            
            
            if(TS.get(index)[0].equals(TokenType.Return) || TS.get(index)[0].equals(TokenType.Break) || TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){              
                return true;
            }
            // if(TS.get(index)[0].equals(TokenType.ID) || TS.get(index)[0].equals(TokenType.If) || TS.get(index)[0].equals(TokenType.For) || TS.get(index)[0].equals(TokenType.Do) || TS.get(index)[0].equals(TokenType.Switch) || TS.get(index)[0].equals(TokenType.Try) || TS.get(index)[0].equals(TokenType.Datatype) || TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[1].equals("panchait")){
                
            else if(SST()){
                System.out.println("Inside MST under MST");                   
                    if(MST()){
                        System.out.println("Inside MST Recursion");
                        return true;
                    }
                }
            // }
            
            return false;
        }

        public boolean IDStatement(){
            System.out.println("Inside IDStatement");
            if(TS.get(index)[0].equals(TokenType.ID)){
                if(TS.get(index)[0].equals(TokenType.ID)){
                    
                    System.out.println("Inside IDStatement ID ");
                    index++;
                    if(IDList()){
                        System.out.println("Inside IDStatement under IdList ");
                        
                        return true;
                    }
                }
                
            }
            return false;
        }
        public boolean IDList(){
            System.out.println("Inside IDList ");
            
            if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt) && (TS.get(index+1)[0].equals(TokenType.IntConst) || TS.get(index+1)[0].equals(TokenType.IncDec)||TS.get(index+1)[0].equals(TokenType.This) || TS.get(index+1)[0].equals(TokenType.Super) || TS.get(index+1)[0].equals(TokenType.ID))){
                
                if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                    System.out.println("Inside IDList [");
                    index++;
                    if(arrayArguments()){
                     
                    if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                        index++;
                     
                         return true;
                    }}}}
           
            else  if(TS.get(index)[0].equals(TokenType.Equal) || TS.get(index)[0].equals(TokenType.CompoundAssign) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.OpenSqrBrkt) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.Dot)){
                if(dotCases()){
                    if(Options()){
                        return true;
                    }
                }
            }
                   
                
                
                
            
            else if(TS.get(index)[0].equals(TokenType.ID)){
                System.out.println("true 1");
                if(TS.get(index)[0].equals(TokenType.ID)){
                    index++;
                    if(TS.get(index)[0].equals(TokenType.Equal)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.New)){
                            index++;
                            if(TS.get(index)[0].equals(TokenType.ID)){
                                index++;
                                if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                                    index++;
                                    if(arguments()){
                                        if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
           return false;
            }
            public boolean Options(){
                System.out.println("Inside Options");
                if(TS.get(index)[0].equals(TokenType.IncDec)){
                    if(TS.get(index)[0].equals(TokenType.IncDec)){
                        System.out.println("Inside Options Inc Dec");
                        index++;
                        return true;
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.Equal) || TS.get(index)[0].equals(TokenType.CompoundAssign)){
                    if(TS.get(index)[0].equals(TokenType.Equal) || TS.get(index)[0].equals(TokenType.CompoundAssign)){
                    
                        if(Assign()){
                            System.out.println("Inside Options Inc Dec");
                            return true;
                    }
                    }
                }
                return false;
            }
            public boolean TSIDStatement(){
                if(TS.get(index)[0].equals(TokenType.This)){
                    if(TS.get(index)[0].equals(TokenType.This)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.Dot)){
                            index++;
                        if(TS.get(index)[0].equals(TokenType.ID)){
                            index++;
                            if(TSIDList()){
                                return true;
                            }
                        }
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.Super)){
                    if(TS.get(index)[0].equals(TokenType.Super)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.ID)){
                            index++;
                            if(TSIDList()){
                                return true;
                            }
                        }
                    }
                }
                return true;
            }
            public boolean TSIDList(){
                if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt) && (TS.get(index+1)[0].equals(TokenType.IntConst) || TS.get(index+1)[0].equals(TokenType.IncDec)||TS.get(index+1)[0].equals(TokenType.This) || TS.get(index+1)[0].equals(TokenType.Super) || TS.get(index+1)[0].equals(TokenType.ID))){
                if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                    index++;
                    if(arrayArguments()){
                    if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                        index++;
                         return true;
                    }
                   
                }
                }
                
            }
                
             else  if(TS.get(index)[0].equals(TokenType.Equal) || TS.get(index)[0].equals(TokenType.CompoundAssign) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.OpenSqrBrkt) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.Dot)){
                if(dotCases()){
                    if(Options()){
                        return true;
                    }
                }
            }
            return false;
            }
            
            public boolean Assign(){
                System.out.println("Inside Assign: Token "+TS.get(index)[0]);
                if(TS.get(index)[0].equals(TokenType.Equal) || TS.get(index)[0].equals(TokenType.CompoundAssign)){
                    index++;                    
                        System.out.println("Inside Assign under Assign OP");
                        if(Value()){
                            System.out.println("Inside Assign under Value");
                            return true;
                        }
                    
                }
                return false;
            }

            public boolean Value(){
                System.out.println("Inside Value");
                if(TS.get(index)[0].equals(TokenType.ID)){
                    if(TS.get(index)[0].equals(TokenType.ID)){
                        index++;
                        System.out.println("Inside Value ID");
                        if(IDList2()){
                            System.out.println("Inside Value ID Under ID List 2 ");
                            return true;
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.BoolConst)){
                    if(TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.BoolConst)){
                        index++;
                        System.out.println("Inside Value Const");
                        return true;
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                    index++;
                    System.out.println("Inside Value (");
                    if(expression()){
                        return true;
                    }
                }
                return false;
            }
                    // public boolean IDList2(){
                        
                    // }
            public boolean AssignOP(){
                if(TS.get(index)[0].equals(TokenType.Equal)){
                    if(TS.get(index)[0].equals(TokenType.Equal)){
                        index++;
                        return true;
                    }

                }
                else if(TS.get(index)[0].equals(TokenType.CompoundAssign)){
                    if(TS.get(index)[0].equals(TokenType.CompoundAssign)){
                        index++;
                        return true;
                    }
                }
                return false;
            }
            public boolean arrayArguments(){
                
                if(TS.get(index)[0].equals(TokenType.IntConst)){
                    if(TS.get(index)[0].equals(TokenType.IntConst)){
                        index++;
                        
                        return true;
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.IncDec)){
                    if(TS.get(index)[0].equals(TokenType.IncDec)){
                        index++;
                        return true;
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID)){
                    if(TS()){
                        if(TS.get(index)[0].equals(TokenType.ID)){
                            index++;
                            if(subCase()){
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
            public boolean subCase(){
                if(TS.get(index)[0].equals(TokenType.IncDec)){
                    if(TS.get(index)[0].equals(TokenType.IncDec)){
                        index++;
                        return true;
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                    return true;
                }
                return false;
            }
            
            // public boolean dotCases(){
            //     System.out.println("Inside DotCase");
                
            //     if(TS.get(index)[0].equals(TokenType.Equal) || TS.get(index)[0].equals(TokenType.CompoundAssign) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.SemiColon) ){
            //         System.out.println("Inside DotCase Follow");
            //         return true;

            //     }
            //     else if(TS.get(index)[0].equals(TokenType.Dot)){
            //         if(TS.get(index)[0].equals(TokenType.Dot)){
            //             System.out.println("Inside DotCase .");
            //             index++;
            //             if(TS.get(index)[0].equals(TokenType.ID)){
            //                 System.out.println("Inside DotCase . ID");
            //                 index++;
            //                 if(dotCases()){
            //                     System.out.println("Inside DotCase . Loop");
            //                     return true;
            //                 }
            //             }
            //         }
            //     }
            //     else if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
            //         if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
            //             System.out.println("Inside DotCase [");
            //             index++;
            //             if(arrayArguments()){
            //                 System.out.println("Inside DotCase [ under array Args");
            //                 if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
            //                     System.out.println("Inside DotCase [ ]");
            //                     index++;
            //                     if(dotCases()){
            //                         System.out.println("Inside DotCase [ Lo");
            //                         return true;
            //                     }
            //                 }
            //             }
            //         }
            //     }
            //     else if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
            //         if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
            //             System.out.println("Inside DotCase (");
            //             index++;
            //             if(arguments()){
            //                 System.out.println("Inside DotCase ( under arguments");
            //                 if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
            //                     System.out.println("Inside DotCase ( )");
            //                     index++;
            //                     if(dotSubcases()){
            //                         System.out.println("Inside DotCase ( Loop");
            //                         return true;
            //                     }
            //                 }
            //             }
            //         }
            //     }
            // return false;
            // }

            boolean dotCases(){
                System.out.println("Inside DotCases");

                if(TS.get(index)[0].equals(TokenType.Equal) 
                || TS.get(index)[0].equals(TokenType.CompoundAssign) 
                || TS.get(index)[0].equals(TokenType.IncDec) 
                || TS.get(index)[0].equals(TokenType.SemiColon))
                {
                    System.out.println("Inside DotCases Follow");
                    return true;
                }
                else if(TS.get(index)[0].equals(TokenType.Dot)){
                    System.out.println("Inside DotCases .");
                    index++;
                    if(TS.get(index)[0].equals(TokenType.ID)){
                        System.out.println("Inside DotCases . ID");
                        index++;
                        if(dotCases()){
                            System.out.println("Inside DotCases . Loop");
                            return true;
                        }
                    }                    
                }
                else if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                    index++;
                    System.out.println("Inside DotCases [");
                    if(arrayArguments()){
                        System.out.println("Inside DotCases [ under Arguments");
                        if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                            System.out.println("Inside DotCases under ]");
                            index++;
                            if(dotCases()){
                                System.out.println("Inside DotCases [ Loop");
                                return true;
                            }
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                    System.out.println("Inside DotCases (");
                    index++;
                    if(arguments()){
                        System.out.println("Inside DotCases ( under Arguments");
                        if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                            System.out.println("Inside DotCases ( )");
                            index++;
                            if(dotCases()){
                                System.out.println("Inside DotCases ( Loop");
                                return true;
                            }
                        }
                    }
                }
               
                return false;
            }

            public boolean dotSubcases(){
                System.out.println("Inside DotSubCase ");
                if(TS.get(index)[0].equals(TokenType.Equal) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.CompoundAssign)  || TS.get(index)[0].equals(TokenType.SemiColon)){
                    System.out.println("Inside DotSubCase Follow");
                    return true;
                }

                else if(TS.get(index)[0].equals(TokenType.Dot)){
                    System.out.println("Inside DotSubCase .");
                    if(TS.get(index)[0].equals(TokenType.Dot)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.ID)){
                            System.out.println("Inside DotSubCase . ID");
                            index++;
                            if(dotCases()){
                                System.out.println("Inside DotSubCase Loop");
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
            public boolean Dec(){
                System.out.println("Inside Declaration");
                if(TS.get(index)[0].equals(TokenType.Datatype)){
                    if(TS.get(index)[0].equals(TokenType.Datatype)){
                        System.out.println("Inside Declartion DataType");
                        index++;
                        if(DecType()){
                            System.out.println("Inside Declaration under DecType");
                            return true;
                        }
                    }
                }
                return false;
            }
            public boolean DecType(){
                System.out.println("Inside DecType");
                if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                    if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                        System.out.println("Inside DecType [");
                        index++;
                        if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                            System.out.println("Inside DecType ]");
                            index++;
                            if(TS.get(index)[0].equals(TokenType.ID)){
                                System.out.println("Inside DecType ID");
                                index++;
                                if(arrayInit()){
                                    System.out.println("Inside DecType under ArrayInit");
                                    return true;
                                }
                            }
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.ID)){
                    if(TS.get(index)[0].equals(TokenType.ID)){
                        index++;
                        System.out.println("Inside DecType =");
                        if(Init()){
                            System.out.println("Inside DecType = under init");
                            return true;
                        }
                    }
                }
                return false;
            }

            public boolean arrayInit(){
                System.out.println("Inside ArrayInit");
                if(TS.get(index)[0].equals(TokenType.Equal)){
                    System.out.println("Inside ArrayInit =");
                    index++;
                    if(ArrayType()){
                            System.out.println("Inside Arrayinit under ArrayType ");
                            return true;
                        }
                    } 
                    else if(TS.get(index)[0].equals(TokenType.SemiColon)){
                        System.out.println("Inside Arrayinit Follow ");
                        return true;
                    }
                    
                
                return false;
            }

            public boolean ArrayType(){
                System.out.println("Inside Arraytype");
                if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                        System.out.println("Inside Arraytype {");
                        index++;
                        if(ArrayList()){
                            System.out.println("Inside Arraytype Under ArraList");
                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                System.out.println("Inside Arraytype }");
                                index++;
                                return true;
                            }

                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.New)){
                    if(TS.get(index)[0].equals(TokenType.New)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.Datatype)){
                            index++;
                            if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                                index++;
                                if(TS.get(index)[0].equals(TokenType.IntConst)){
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                                        index++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
            public boolean Init(){
                System.out.println("Inside Init");
                if(TS.get(index)[0].equals(TokenType.Comma)){
                    System.out.println("Inside Init ,");
                    if(TS.get(index)[0].equals(TokenType.Comma)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.ID)){
                            System.out.println("Inside Init ID");
                            index++;
                            if(Init()){
                                System.out.println("Inside Init Loop");
                                return true;
                            }
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.Equal)){
                    if(TS.get(index)[0].equals(TokenType.Equal)){
                        System.out.println("Inside Init =");
                        index++;
                        if(expression()){
                            System.out.println("Inside Init under Expresision");
                            return true;
                        }
                    }
                    else if(TS.get(index)[0].equals(TokenType.SemiColon)){
                        System.out.println("Inside Init Follow");
                        return true;
                    }
                }
                return false;
            }
            public boolean If_Else(){
                if(TS.get(index)[0].equals(TokenType.If)){
                    if(TS.get(index)[0].equals(TokenType.If)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            index++;
                            if(expression()){
                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                        index++;
                                        if(MST()){
                                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                index++;
                                                if(elseOptions()){
                                                    return true;
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
            public boolean elseOptions(){
                if(TS.get(index)[0].equals(TokenType.Else)){
                   if(ELSE()){
                    return true;
                   }
                }
                else if(TS.get(index)[0].equals(TokenType.Elseif)){
                    if(elseIf()){
                        if(elseOptions()){
                            return true;
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt) || TS.get(index)[0].equals(TokenType.Return) || TS.get(index)[0].equals(TokenType.Break) || MST()){
                    return true;
                }
                return false;
            }
            public boolean elseIf(){
                if(TS.get(index)[0].equals(TokenType.Elseif)){
                    if(TS.get(index)[0].equals(TokenType.Elseif)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            index++;
                            if(expression()){
                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                        index++;
                                        if(MST()){
                                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                index++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
            public boolean ELSE(){
                if(TS.get(index)[0].equals(TokenType.Else)){
                    if(TS.get(index)[0].equals(TokenType.Else)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                            index++;
                            if(MST()){
                                if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
            
            public boolean For_Loop(){
                if(TS.get(index)[0].equals(TokenType.For)){
                    if(TS.get(index)[0].equals(TokenType.For)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            index++;
                            if(F1()){
                                if(TS.get(index)[0].equals(TokenType.SemiColon)){
                                    index++;
                                    if(F2()){
                                        if(TS.get(index)[0].equals(TokenType.SemiColon)){
                                            index++;
                                            if(F3()){
                                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                                    index++;
                                                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                                        index++;
                                                        if(MST()){
                                                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                                index++;
                                                                System.out.println("For Loop }");
                                                                return true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
                return false;
            }
            public boolean F1(){
                if(TS.get(index)[0].equals(TokenType.Datatype)){
                    if(Dec()){
                        return true;
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID)){
                    if(TS()){
                        if(TS.get(index)[0].equals(TokenType.ID)){
                            index++;
                            if(dotCases()){
                                if(Assign()){
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
            public boolean F2(){
                if(TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.NOT) || TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID)){
                        if(expression()){
                            return true;
                        }
                }
                else if(TS.get(index)[0].equals(TokenType.SemiColon)){
                    return true;
                }
                return false;
            }
            public boolean F3(){
                if(TS.get(index)[0].equals(TokenType.IncDec)){
                    if(TS.get(index)[0].equals(TokenType.IncDec)){
                        index++;
                        if(TS()){
                            if(TS.get(index)[0].equals(TokenType.ID)){
                                index++;
                                if(dotCases()){
                                    return true;
                                }
                            }
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID)){
                    if(TS()){
                        if(TS.get(index)[0].equals(TokenType.ID)){
                            index++;
                            if(dotCases()){
                                if(TS.get(index)[0].equals(TokenType.IncDec)){
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
            public boolean arguments(){
                 System.out.println("Inside Arguments");
                // if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID) || TS.get(index)[0].equals(TokenType.NOT) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst) ){
                    if(expression()){
                         System.out.println("Inside Arguments under Expression");
                         if(addArgs()){
                            System.out.println("Inside Arguments under addArgs");
                            return true;
                        }
                    // }
                }
                else if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                    return true;
                }
                return false;
            }
            public boolean addArgs(){
                System.out.println("Inside addArgs");
                if(TS.get(index)[0].equals(TokenType.Comma)){
                    if(TS.get(index)[0].equals(TokenType.Comma)){
                        System.out.println("Inside addArgs comma");
                        index++;
                        if(expression()){
                            System.out.println("Inside addArgs under expression");
                            if(addArgs()){
                                System.out.println("Inside addArgs under loop");
                                return true;
                            }
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                    return true;
                }
                return  false;
            }


            public boolean expression(){
                System.out.println("Inside Expression: Token: "+TS.get(index)[0]);
                
                if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID) || TS.get(index)[0].equals(TokenType.NOT) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst) ){
                    if(and()){
                        System.out.println("Inside Expression And");
                        if(or2()){
                            System.out.println("Inside Expression Under Or2");
                            return true;
                        }
                    }
                }
                return false;
            }
            public boolean or2(){
                System.out.println("Inside Or2");                            
                
                if(TS.get(index)[0].equals(TokenType.OR)){
                    System.out.println("Inside Or2 OR");                            
                    if(TS.get(index)[0].equals(TokenType.OR)){
                        index++;
                        if(and()){
                        System.out.println("Inside Or2 Under And");                            
                            if(or2()){
                                System.out.println("Inside Or2 Under And Or2");                            
                                return true;
                            }
                    }
                }
                }
                else if(TS.get(index)[0].equals(TokenType.Comma) || TS.get(index)[0].equals(TokenType.CloseRoundBrkt) || TS.get(index)[0].equals(TokenType.SemiColon) || TS.get(index)[0].equals(TokenType.Colon) ){
                    System.out.println("Inside Or2 Follow");
                    return true;
                }
                return false;
            }
            public boolean and(){
              
                  if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID) || TS.get(index)[0].equals(TokenType.NOT) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst) ){
                        if(RO2()){
                            if(and2()){
                                return true;
                            }
                        }
                  }
                  return false;
            }
             public boolean and2(){
               
                if(TS.get(index)[0].equals(TokenType.AND)){
                     if(TS.get(index)[0].equals(TokenType.AND)){
                    index++;
                    if(RO2()){
                        if(and2()){
                            return true;
                        }
                    }
                }
                }
                else if(TS.get(index)[0].equals(TokenType.Comma) || TS.get(index)[0].equals(TokenType.CloseRoundBrkt) || TS.get(index)[0].equals(TokenType.OR) || TS.get(index)[0].equals(TokenType.SemiColon) || TS.get(index)[0].equals(TokenType.Colon) ){
                    return true;
                }
                return false;
            }
             
            public boolean RO2(){
                
                  if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID) || TS.get(index)[0].equals(TokenType.NOT) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst) ){
                        if(RO1()){
                            if(RO2second()){
                                return true;
                            }
                        }
                  }
                  return false;
            }
             public boolean RO2second(){
                
                if(TS.get(index)[0].equals(TokenType.RelationOp2)){
                    if(TS.get(index)[0].equals(TokenType.RelationOp2)){
                        index++;
                    if(RO1()){
                        if(RO2second()){
                            return true;
                        }
                    }
                } }
                else if(TS.get(index)[0].equals(TokenType.Comma) || TS.get(index)[0].equals(TokenType.CloseRoundBrkt) || TS.get(index)[0].equals(TokenType.AND)|| TS.get(index)[0].equals(TokenType.OR) || TS.get(index)[0].equals(TokenType.SemiColon) || TS.get(index)[0].equals(TokenType.Colon) ){
                    return true;
                }
                return false;
            }
            public boolean RO1(){
                
                  if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID) || TS.get(index)[0].equals(TokenType.NOT) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst) ){
                        if(PM()){
                            if(RO1second()){
                                return true;
                            }
                        }
                  }
                  return false;
            }
             public boolean RO1second(){
              
                if(TS.get(index)[0].equals(TokenType.RelationOp1)){
                    if(TS.get(index)[0].equals(TokenType.RelationOp1)){
                    index++;
                    if(PM()){
                        if(RO1second()){
                            return true;
                        }
                    }
                } }
                else if(TS.get(index)[0].equals(TokenType.Comma) || TS.get(index)[0].equals(TokenType.CloseRoundBrkt) || TS.get(index)[0].equals(TokenType.RelationOp2)|| TS.get(index)[0].equals(TokenType.AND)|| TS.get(index)[0].equals(TokenType.OR) || TS.get(index)[0].equals(TokenType.SemiColon) || TS.get(index)[0].equals(TokenType.Colon) ){
                    return true;
                }
                return false;
            }
            public boolean PM(){
              
                  if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID) || TS.get(index)[0].equals(TokenType.NOT) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst) ){
                        if(MDM()){
                            if(PM2()){
                                return true;
                            }
                        }
                  }
                  return false;
            }
             public boolean PM2(){
                
                if(TS.get(index)[0].equals(TokenType.AddSub)){
                      if(TS.get(index)[0].equals(TokenType.AddSub)){
                    index++;
                    
                    if(MDM()){
                        if(PM2()){
                            return true;
                        }
                    }
                }}
                else if(TS.get(index)[0].equals(TokenType.Comma) || TS.get(index)[0].equals(TokenType.CloseRoundBrkt)  || TS.get(index)[0].equals(TokenType.RelationOp1)|| TS.get(index)[0].equals(TokenType.RelationOp2)|| TS.get(index)[0].equals(TokenType.AND)|| TS.get(index)[0].equals(TokenType.OR) || TS.get(index)[0].equals(TokenType.SemiColon) || TS.get(index)[0].equals(TokenType.Colon) ){
                    return true;
                }
                return false;
            }
            public boolean MDM(){
                
                  if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID) || TS.get(index)[0].equals(TokenType.NOT) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.IncDec) || TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst) ){
                        if(variable()){
                             
                            if(MDM2()){
                                return true;
                            }
                        }
                  }
                  return false;
            }
             public boolean MDM2(){
                 
                if(TS.get(index)[0].equals(TokenType.MulDivMod)){
                    if(TS.get(index)[0].equals(TokenType.MulDivMod)){
                    index++;
                    if(variable()){
                        if(MDM2()){
                            return true;
                        }
                    }
                }}
                else if(TS.get(index)[0].equals(TokenType.Comma) || TS.get(index)[0].equals(TokenType.CloseRoundBrkt) || TS.get(index)[0].equals(TokenType.AddSub)  || TS.get(index)[0].equals(TokenType.RelationOp1)|| TS.get(index)[0].equals(TokenType.RelationOp2)|| TS.get(index)[0].equals(TokenType.AND)|| TS.get(index)[0].equals(TokenType.OR) || TS.get(index)[0].equals(TokenType.SemiColon) || TS.get(index)[0].equals(TokenType.Colon) ){
                    return true;
                }
                return false;
            }
            

          public boolean variable(){
           
            if (TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst)) {
                if(TS.get(index)[0].equals(TokenType.IntConst) || TS.get(index)[0].equals(TokenType.FloatConst) || TS.get(index)[0].equals(TokenType.CharConst) || TS.get(index)[0].equals(TokenType.StringConst) || TS.get(index)[0].equals(TokenType.BoolConst)) {
                    index++;
                    return true;
                }
            }
          
            else if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt) ){
                if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt) ){
                    index++;
                    if(expression()){
                        return true;
                    }
                }
            }
            else if(TS.get(index)[0].equals(TokenType.NOT)){
                if(TS.get(index)[0].equals(TokenType.NOT)){
                    index++;
                    if(variable()){
                        return true;
                    }
                }
            }
             else if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID)){

                if(TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.ID)){
                    index++;
                    if(ref()){
                       
                         return true;
                    }
                   
                }
                
            }
            else if(TS.get(index)[0].equals(TokenType.IncDec) ){
                if(TS.get(index)[0].equals(TokenType.IncDec) ){
                    index++;
                    if(TS()){
                        if(TS.get(index)[0].equals(TokenType.ID) ){
                            index++;
                            if(ref2()){
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
          }
          public boolean ref2(){
            System.out.println("Inside Ref2");
            if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                    System.out.println("Inside Ref2 (");
                    index++;
                    if(arguments()){
                        return true;
                    }
                }
            }
            else if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                System.out.println("Inside Ref2 [");
                if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                    index++;
                    if(arrayArguments()){
                        System.out.println("Inside Ref2 under arrayArguments");
                        if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                            System.out.println("Inside Ref2 ]");
                            index++;
                            return true;
                        }
                    }
                }
            }
            else if(TS.get(index)[0].equals(TokenType.Comma)|| TS.get(index)[0].equals(TokenType.MulDivMod) || TS.get(index)[0].equals(TokenType.CloseRoundBrkt) || TS.get(index)[0].equals(TokenType.AddSub)  || TS.get(index)[0].equals(TokenType.RelationOp1)|| TS.get(index)[0].equals(TokenType.RelationOp2)|| TS.get(index)[0].equals(TokenType.AND)|| TS.get(index)[0].equals(TokenType.OR) || TS.get(index)[0].equals(TokenType.SemiColon) || TS.get(index)[0].equals(TokenType.Colon)){
                System.out.println("Inside Ref2 Follow");
                return true;
            }
            return false;
        }
        public boolean ref(){
            System.out.println("Inside Ref ");
            
            if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt) && (TS.get(index+1)[0].equals(TokenType.This) || TS.get(index+1)[0].equals(TokenType.Super) || TS.get(index+1)[0].equals(TokenType.ID) || TS.get(index+1)[0].equals(TokenType.NOT) || TS.get(index+1)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index+1)[0].equals(TokenType.IncDec) || TS.get(index+1)[0].equals(TokenType.IntConst) || TS.get(index+1)[0].equals(TokenType.FloatConst) || TS.get(index+1)[0].equals(TokenType.CharConst) || TS.get(index+1)[0].equals(TokenType.StringConst) || TS.get(index+1)[0].equals(TokenType.BoolConst))){
                if(ref2()){
                       System.out.println("Inside Ref Under Ref2 ");
                       return true;
                    }
                    
                }
                else if(TS.get(index)[0].equals(TokenType.IncDec)){
                   
                    index++;
                    return true;
                         
                }
                // else if(TS.get(index)[0].equals(TokenType.Dot) || TS.get(index)[0].equals(TokenType.OpenRoundBrkt) || TS.get(index)[0].equals(TokenType.OpenSqrBrkt) || TS.get(index)[0].equals(TokenType.IncDec)){
                //     if(dotCases()){
                //             System.out.println("Inside Ref Under Ref2 ");
                //             if(TS.get(index)[0].equals(TokenType.IncDec)){
                //                 index++;
                //                 return true;
                //             }
                //         }
                // }

                else if(TS.get(index)[0].equals(TokenType.Comma)|| TS.get(index)[0].equals(TokenType.MulDivMod) || TS.get(index)[0].equals(TokenType.CloseRoundBrkt) || TS.get(index)[0].equals(TokenType.AddSub)  || TS.get(index)[0].equals(TokenType.RelationOp1)|| TS.get(index)[0].equals(TokenType.RelationOp2)|| TS.get(index)[0].equals(TokenType.AND)|| TS.get(index)[0].equals(TokenType.OR) || TS.get(index)[0].equals(TokenType.SemiColon) || TS.get(index)[0].equals(TokenType.Colon)){
                 
                return true;
            }
                return false;
          }
          public boolean TS(){
            if(TS.get(index)[0].equals(TokenType.This)){
                if(TS.get(index)[0].equals(TokenType.This)){
                    index++;
                    if(TS.get(index)[0].equals(TokenType.Dot)){
                        index++;
                        return true;
                    }
                }
            }
            else if(TS.get(index)[0].equals(TokenType.Super)){
                if(TS.get(index)[0].equals(TokenType.Super)){
                    index++;
                    if(TS.get(index)[0].equals(TokenType.Dot)){
                        index++;
                        return true;
                    }
                }
            }
            else if(TS.get(index)[0].equals(TokenType.ID)){
                return true;
                        
            }
            return false;
          }
         
          public boolean IDList2(){
            if(Assign()){
                System.out.println("Inside IDList2 Under Assign");
                return true;
            }
            else if(dotCases()){
                System.out.println("Inside IDList2 Under DotCase");
                return true;
            }
            else if(TS.get(index)[0].equals(TokenType.SemiColon)){
                System.out.println("Inside IDList2 Follow True");
                return true;
            }
            return false;
        }
        //   public boolean ArrayList(){
        //     return true;
        //   }
          
        public boolean params(){
            System.out.println("Inside Params");
            if(TS.get(index)[0].equals(TokenType.Datatype))
            {
                    System.out.println("Inside Params Datatype");
                    index++;
                    if(TS.get(index)[0].equals(TokenType.ID)){
                        System.out.println("Inside Params ID");
                        index++;
                        if(paramList()){
                            System.out.println("Inside Under Paramllist");
                            return true;
                        }}}
                        
            else if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                System.out.println("Inside Params Follow");
                 return true;
            }
            return false;
        }

        public boolean paramList(){
            System.out.println("Inside Params ParamList");
            if(TS.get(index)[0].equals(TokenType.Dot)){
                System.out.println("Inside Params DotNotation");
                    if(TS.get(index)[0].equals(TokenType.Dot)){
                             index++;
                    if(TS.get(index)[0].equals(TokenType.Dot)){
                            index++;
                    if(TS.get(index)[0].equals(TokenType.Dot)){
                             index++;
                             return true;
                    }}}}
           
            else if(paramType()){
                System.out.println("Inside Params Under ParamType");
                if(addParam()){
                    System.out.println("Inside Params Under AddParam");
                    return true;
                }}
            return false;
        }

        public boolean paramType(){
            System.out.println("Inside ParamType");
            if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                if(TS.get(index)[0].equals(TokenType.OpenSqrBrkt)){
                    System.out.println("Inside Params [");
                    index++;
                    if(TS.get(index)[0].equals(TokenType.CloseSqtBrkt)){
                        System.out.println("Inside Params ]");
                        index++;
                        return true;
                    }
                }
            }
            else if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt) || TS.get(index)[0].equals(TokenType.Comma)  ){ 
                System.out.println("Inside ParamsType Follow");
                return true;
            }
            return false;
        }
        
        public boolean addParam(){
            if(TS.get(index)[0].equals(TokenType.Comma)){
                if(TS.get(index)[0].equals(TokenType.Comma)){
                    index++;
                if(TS.get(index)[0].equals(TokenType.Datatype)){
                        index++;
                if(TS.get(index)[0].equals(TokenType.ID)){
                            index++;
                if(paramType()){
                if(addParam()){
                            return true;
                }}}}}}
            else if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                return true;
            }
            return false;
        }
        public boolean Switch(){
                if(TS.get(index)[0].equals(TokenType.Switch)){
                    if(TS.get(index)[0].equals(TokenType.Switch)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            index++;
                            if(expression()){
                                if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                        index++;
                                        if(SwitchBody()){
                                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                index++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
            public boolean SwitchBody(){
                if(TS.get(index)[0].equals(TokenType.Case)){
                    if(Cases()){
                        if(SwitchBody()){
                            return true;
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.Default)){
                    if(DEFAULT()){
                        return true;
                    }
                }
                return false;
            }
            public boolean Cases(){
                if(TS.get(index)[0].equals(TokenType.Case)){
                    if(TS.get(index)[0].equals(TokenType.Case)){
                        index++;
                        if(expression()){
                            if(TS.get(index)[0].equals(TokenType.Colon)){
                                index++;
                                if(MST()){
                                    if(TS.get(index)[0].equals(TokenType.Break)){
                                        index++;
                                        if(TS.get(index)[0].equals(TokenType.SemiColon)){
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
            public boolean DEFAULT(){
                if(TS.get(index)[0].equals(TokenType.Default)){
                    if(TS.get(index)[0].equals(TokenType.Default)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.Colon)){
                            index++;
                            if(MST()){
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
            public boolean Do_While(){
                if(TS.get(index)[0].equals(TokenType.Do)){
                    if(TS.get(index)[0].equals(TokenType.Do)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                            index++;
                            if(MST()){
                                if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.While)){
                                        index++;
                                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                                            index++;
                                             if(expression()){
                                           
                                        
                                            if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                                index++;
                                                return true;
                                            }
                                        }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
}
            public boolean Try_Catch(){
                if(TS.get(index)[0].equals(TokenType.Try)){
                    if(TS.get(index)[0].equals(TokenType.Try)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                            index++;
                            if(MST()){
                                if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                    index++;
                                    if(CATCH()){
                                        if(tryCatchOptions()){
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                    
                }
                return false;
            }
            public boolean CATCH(){
                if(TS.get(index)[0].equals(TokenType.Catch)){
                    if(TS.get(index)[0].equals(TokenType.Catch)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                            index++;
                            if(TS.get(index)[0].equals(TokenType.ID)){
                                index++;
                                if(TS.get(index)[0].equals(TokenType.ID)){
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                    index++;
                                    if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                        index++;
                                        if(MST()){
                                            if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                                index++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                                }
                                
                            }
                        }
                    }
                }
                return false;
            }
            public boolean tryCatchOptions(){
                if(TS.get(index)[0].equals(TokenType.Catch)){
                    if(CATCH()){
                        if(tryCatchOptions()){
                            return true;
                        }
                    }
                }
                else if(TS.get(index)[0].equals(TokenType.Finally)){
                    if(TS.get(index)[0].equals(TokenType.Finally)){
                            index++;
                            if(TS.get(index)[0].equals(TokenType.OpenCurlyBrkt)){
                                index++;
                                if(MST()){
                                    if(TS.get(index)[0].equals(TokenType.CloseCurlyBrkt)){
                                        index++;
                                        return true;
                                    }
                                }
                            }
                    }
                    else if((TS.get(index)[0].equals(TokenType.ID)|| TS.get(index)[0].equals(TokenType.For) || TS.get(index)[0].equals(TokenType.If) || TS.get(index)[0].equals(TokenType.Do) || TS.get(index)[0].equals(TokenType.Switch) || TS.get(index)[0].equals(TokenType.Datatype) || TS.get(index)[0].equals(TokenType.This) || TS.get(index)[0].equals(TokenType.Super) || TS.get(index)[0].equals(TokenType.Try) || TS.get(index)[0].equals(TokenType.Throw) || TS.get(index)[0].equals(TokenType.Return) || TS.get(index)[0].equals(TokenType.CloseCurlyBrkt) || TS.get(index)[0].equals(TokenType.Break)  )){
                        return true;
                    }
                }
                return false;
            }
            public boolean Throw(){
                if(TS.get(index)[0].equals(TokenType.Throw)){
                    if(TS.get(index)[0].equals(TokenType.Throw)){
                        index++;
                        if(TS.get(index)[0].equals(TokenType.New)){
                            index++;
                            if(TS.get(index)[0].equals(TokenType.ID)){
                                index++;
                                if(TS.get(index)[0].equals(TokenType.OpenRoundBrkt)){
                                    index++;
                                    if(arguments()){
                                        if(TS.get(index)[0].equals(TokenType.CloseRoundBrkt)){
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
        


}





