/* *** This file is given as part of the programming assignment. *** */
import java.util.Stack;
import java.util.ArrayList;
// improt SymTab.java;

public class Parser {
    private Stack<ArrayList<Token>> SymbolTable;
    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    private Scan scanner;
    
    private void scan() {
        tok = scanner.scan();
    }
    
    
    private void log(String a)
    {
        System.out.println(a);
    }
    
    
    Parser(Scan scanner) {
        SymbolTable = new Stack<ArrayList<Token>>();
        
        //  SymTab SymbolTable = new SymTab() ;
        this.scanner = scanner;
        scan();
        program();
        if( tok.kind != TK.EOF )
            parse_error("junk after logical end of program");
    }
    
    private void program() {
        log("#include <stdio.h>");
        log("\nint main()");
        
        block();
    }
    
    private void block(){
        log("{");
        SymbolTable.push(new ArrayList<Token>());
        declaration_list();
        statement_list();
        SymbolTable.pop();
        log("}");
    }
    
    private void declaration_list() {
        // below checks whether tok is in first set of declaration.
        // here, that's easy since there's only one token kind in the set.
        // in other places, though, there might be more.
        // so, you might want to write a general function to handle that.
        while( is(TK.DECLARE) ) {
            declaration();
        }
    }
    //    declaration
    private void declaration() {
        mustbe(TK.DECLARE);
        // mustbe(TK.ID);
        log("int");
        int count = 0;
        do {
            scan();
            mustbe(TK.ID);
            Token prev = tok;
            scan();
            
            if (find(prev, 0, false) != -1)
                System.err.println("redeclaration of variable " + prev.string);
            else{
                
                SymbolTable.peek().add(prev);
                
                if(count == 1)
                    log(",");
                else
                    count = 1;
                
                log(" x_" + prev.string + (SymbolTable.size() - 1));
            }
            
        } while(is(TK.COMMA));
        log(";");
    }
    //    find the scope of tok
    public int find(Token tok, int scope, boolean flag)
    {
        if(scope == -1)
            scope = SymbolTable.size() - 1;
        
        // for (int i = 0; i < (SymbolTable.size() - scope); i++)
        for (int i = SymbolTable.size() - 1 - scope; i >= 0; i--)
        {
            for(int j = 0; j < SymbolTable.elementAt(i).size(); j++)
                if(tok.string.equals(SymbolTable.elementAt(i).get(j).string))
                    return i;
            
            if(!flag)
                return -1;
        }
        
        return -1;
    }
    
    
    // statement
    private void statement_list() {
        while(true){
            if(is(TK.ID) || is(TK.TILDE))
                assignment();
            else if(is(TK.PRINT))
                print();
            else if(is(TK.DO))
                DO();
            else if(is(TK.IF))
                IF();
            else if(is(TK.FOR))
                FOR();
            else
                break;
        }
    }
    //    for loop
    private void FOR() {
        scan();
        log("for (");
        ref_id();
        mustbe(TK.ASSIGN);
        scan();
        log(" = ");
        expr();
        mustbe(TK.SEPERAT); // check of ^
        log(";");
        scan();
        expr();
        log(" >= 0"); // default x > = 0
        mustbe(TK.SEPERAT); // check of ^
        log(";");
        scan();
        ref_id();
        mustbe(TK.ASSIGN);
        scan();
        log(" = ");
        expr();
        log(")");
        mustbe(TK.THEN);
        scan();
        block();
        mustbe(TK.ENDFOR);
        scan();
        
    }
    //    assignment
    private void assignment() {
        
        ref_id();
        mustbe(TK.ASSIGN);
        scan();
        log(" = ");
        expr();
        log(";");
    }
    
    
    // ref_id
    private void ref_id() {
        
        int scope = 0;
        boolean isTilde = is(TK.TILDE);
        
        if (isTilde)
        {
            scan();
            
            if (is(TK.NUM)) {
                scope = Integer.parseInt(tok.string);
                scan();
            }
            else
                scope = -1;
        }
        
        Token prev = tok;
        mustbe(TK.ID);
        scan();
        
        if (find(prev, scope, !isTilde) == -1) {
            if (isTilde)
                if (scope != -1)
                    System.err.println("no such variable ~" + scope + prev.string + " on line " + prev.lineNumber);
                else
                    System.err.println("no such variable ~" + prev.string + " on line " + prev.lineNumber);
                else
                    System.err.println(prev.string + " is an undeclared variable on line " + prev.lineNumber);
            
            System.exit(1);
        }
        else
            log("x_" + prev.string + find(prev, scope, !isTilde));
    }
    
    
    // print function
    private void print() {
        // mustbe(TK.PRINT);
        scan();
        log("printf(\"%d\\n\", ");
        expr();
        log(");");
    }
    
    // Do
    private void DO() {
        // mustbe(TK.DO);
        scan();
        log("while");
        log("(");
        guarded_command();
        mustbe(TK.ENDDO);
        scan();
    }
    
    //    if
    private void IF() {
        // mustbe(TK.IF);
        scan();
        log("if");
        log("(");
        guarded_command();
        while(is(TK.ELSEIF)) {
            scan();
            log("else if");
            log("(");
            guarded_command();
            
        }
        if(is(TK.ELSE)) {
            log("else");
            scan();
            block();
        }
        mustbe(TK.ENDIF);
        scan();
    }
    
    //    guarded_command
    private void guarded_command() {
        //
        expr();
        log(" <=0)");
        mustbe(TK.THEN);
        scan();
        block();
    }
    
    //    expr
    private void expr() {
        term();
        while(addop())
        {
            term();
        }
    }
    
    //    term
    private void term() {
        factor();
        while(multop())
        {
            factor();
        }
    }
    
    //    multop
    private boolean multop() {
        if(is(TK.TIMES))
        {
            // mustbe(TK.TIMES);
            log(" * ");
            scan();
            return true;
        }
        else if(is(TK.DIVIDE))
        {
            // mustbe(TK.DIVIDE);
            log(" / ");
            scan();
            return true;
        }
        else
            return false;
    }
    
    //    addop
    private boolean addop() {
        if(is(TK.PLUS))
        {
            // mustbe(TK.PLUS);
            log("  + ");
            scan();
            return true;
        }
        else if(is(TK.MINUS))
        {
            // mustbe(TK.MINUS);
            log(" - ");
            scan();
            return true;
        }
        else
            return false;
    }
    
    //    factor
    private void factor() {
        if(is(TK.LPAREN))
        {
            scan();
            log("(");
            expr();
            mustbe(TK.RPAREN);
            scan();
            log(")");
        }
        else if(is(TK.TILDE)||is(TK.ID))
        {
            {
                int scope = 0;
                boolean isTilde = is(TK.TILDE);
                
                if (isTilde)
                {
                    scan();
                    
                    if (is(TK.NUM)) {
                        scope = Integer.parseInt(tok.string);
                        scan();
                    }
                    else
                        scope = -1;
                }
                
                Token prev = tok;
                mustbe(TK.ID);
                scan();
                
                if (find(prev, scope, !isTilde) == -1) {
                    if (isTilde)
                        if (scope != -1)
                            System.err.println("no such variable ~" + scope + prev.string + " on line " + prev.lineNumber);
                        else
                            System.err.println("no such variable ~" + prev.string + " on line " + prev.lineNumber);
                        else
                            System.err.println(prev.string + " is an undeclared variable on line " + prev.lineNumber);
                    
                    System.exit(1);
                }
                else
                    log("x_" + prev.string + find(prev, scope, !isTilde));
            }
        }
        else{
            mustbe(TK.NUM);
            log(tok.string);
            scan();
            
            
        }
    }
    
    
    // is current token what we want?
    private boolean is(TK tk) {
        return tk == tok.kind;
    }
    
    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
        if( tok.kind != tk ) {
            System.err.println( "mustbe: want " + tk + ", got " +
                               tok);
            parse_error( "missing token (mustbe)" );
        }
        // scan();
    }
    
    private void parse_error(String msg) {
        System.err.println( "can't parse: line "
                           + tok.lineNumber + " " + msg );
        System.exit(1);
    }
}
