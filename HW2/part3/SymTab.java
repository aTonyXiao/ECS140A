import java.util.Stack;
import java.util.ArrayList;

public class SymTab {
    
    public Stack<ArrayList<Token>> SymbolTable;
    
    public boolean contains(Token tok, int scope, boolean flag)
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
}
