package symboltable;

import syntaxtree.*;

public class VarInfo
{
        public TType type;
        public Symbol name;    
    
        public VarInfo(TType t, Symbol s)
        {
                super();                
                type = t;
                name = s;                
        }
}
