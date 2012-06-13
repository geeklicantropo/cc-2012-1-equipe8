package translate;

import temp.Label;
import IRTree.CJUMP;
import IRTree.CONST;
import IRTree.Stm;

public class Ex extends Exp
{
    IRTree.Exp exp;
    
    Ex(IRTree.Exp e)
    {
        super();        
        exp = e;
    }
    
     public   IRTree.Exp unEx()
    {    
        return exp;
    }

    
     public  Stm unNx()
    {
        return new IRTree.ExpStm(exp);
    }

    
     public Stm unCx(Label t, Label f)
    {
        return new CJUMP(CJUMP.EQ, exp, new CONST(0), f, t);
    }


}
