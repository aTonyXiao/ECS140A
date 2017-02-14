/* *** This file is given as part of the programming assignment. *** */

public class Parser {
    
    
    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    private void scan() {
        tok = scanner.scan();
    }
    
    private Scan scanner;
    Parser(Scan scanner) {
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
        declaration_list();
        statement_list();
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
        mustbe(TK.ID);
        while( is(TK.COMMA) ) {
            mustbe(TK.COMMA);
            mustbe(TK.ID);
        }
    }
    
    // statement
    private void statement_list() { // statement_list
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
        expr();
    }
    
    // ref_id
    private void ref_id() {
        if(is(TK.TILDE)){
            mustbe(TK.TILDE);
            if(is(TK.NUM))
                mustbe(TK.NUM);
        }
        mustbe(TK.ID);
    }
    // print function
    private void print() {
        mustbe(TK.PRINT);
        expr();
    }
    
    // Do
    private void DO() {
        mustbe(TK.DO);
        guarded_command();
        mustbe(TK.ENDDO);
    }
    // IF
    private void IF() {
        mustbe(TK.IF);
        guarded_command();
        while(is(TK.ELSEIF)) {
            mustbe(TK.ELSEIF);
            guarded_command();
        }
        if(is(TK.ELSE)) {
            mustbe(TK.ELSE);
            block();
        }
        mustbe(TK.ENDIF);
    }
    //    guarded_command
    private void guarded_command() {
        expr();
        mustbe(TK.THEN);
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
    //    term()
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
            mustbe(TK.TIMES);
            return true;
        }
        else if(is(TK.DIVIDE))
        {
            mustbe(TK.DIVIDE);
            return true;
        }
        else
            return false;
    }
    
    //    addop
    private boolean addop() {
        if(is(TK.PLUS))
        {
            mustbe(TK.PLUS);
            return true;
        }
        else if(is(TK.MINUS))
        {
            mustbe(TK.MINUS);
            return true;
        }
        else
            return false;
    }
    
    //    factor
    private void factor() {
        if(is(TK.LPAREN))
        {
            mustbe(TK.LPAREN);
            expr();
            mustbe(TK.RPAREN);
        }
        else if(is(TK.TILDE)||is(TK.ID))
        {
            ref_id();
        }
        else if(is(TK.NUM))
        {
            mustbe(TK.NUM);
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
        scan();
    }
    
    private void parse_error(String msg) {
        System.err.println( "can't parse: line "
                           + tok.lineNumber + " " + msg );
        System.exit(1);
    }
}
