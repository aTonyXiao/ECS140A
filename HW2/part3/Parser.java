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
        block();
    }
    
    private void block(){
        SymbolTable.push(new ArrayList<Token>());
        declaration_list();
        statement_list();
        SymbolTable.pop();
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
    
    private void declaration() {
        mustbe(TK.DECLARE);
        // mustbe(TK.ID);
        do {
            scan();
            mustbe(TK.ID);
            Token prev = tok;
            scan();
            
            if (find(prev, 0, false))
                System.err.println("redeclaration of variable " + prev.string);
            else
                SymbolTable.peek().add(prev);
            
        } while(is(TK.COMMA));
    }
    // find if it in the stack
    public boolean find(Token tok, int scope, boolean flag)
    {
        if(scope == -1)
            scope = SymbolTable.size() - 1;
        
        // for (int i = 0; i < (SymbolTable.size() - scope); i++)
        for (int i = SymbolTable.size() - 1 - scope; i >= 0; i--)
        {
            for(int j = 0; j < SymbolTable.elementAt(i).size(); j++)
                if(tok.string.equals(SymbolTable.elementAt(i).get(j).string))
                    return true;
            
            if(!flag)
                break;
        }
        
        return false;
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
            else
                break;
        }
    }
    
    // assignment
    private void assignment() {
        ref_id();
        mustbe(TK.ASSIGN);
        scan();
        expr();
    }
    
    
    //ref id
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
        
        if (!find(prev, scope, !isTilde)) {
            if (isTilde){
                if(scope == -1)
                    System.err.println("no such variable ~" + prev.string + " on line " + prev.lineNumber);
                else
                    System.err.println("no such variable ~" + scope + prev.string + " on line " + prev.lineNumber);
                
            }else {
                System.err.println(prev.string + " is an undeclared variable on line " + prev.lineNumber);
                System.exit(1);
            }
        }
    }
    
    
    
    // print function
    private void print() {
        // mustbe(TK.PRINT);
        scan();
        expr();
    }
    
    // Do
    private void DO() {
        // mustbe(TK.DO);
        scan();
        guarded_command();
        mustbe(TK.ENDDO);
        scan();
    }
    
    //IF
    private void IF() {
        // mustbe(TK.IF);
        scan();
        guarded_command();
        while(is(TK.ELSEIF)) {
            scan();
            guarded_command();
        }
        if(is(TK.ELSE)) {
            scan();
            block();
        }
        mustbe(TK.ENDIF);
        scan();
    }
    //    guarded_command
    private void guarded_command() {
        expr();
        mustbe(TK.THEN);
        scan();
        block();
    }
    
    //    expr()
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
            scan();
            return true;
        }
        else if(is(TK.DIVIDE))
        {
            // mustbe(TK.DIVIDE);
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
            scan();
            return true;
        }
        else if(is(TK.MINUS))
        {
            // mustbe(TK.MINUS);
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
            expr();
            mustbe(TK.RPAREN);
            scan();
        }
        else if(is(TK.TILDE)||is(TK.ID))
        {
            ref_id();
        }
        else
        {
            mustbe(TK.NUM);
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
