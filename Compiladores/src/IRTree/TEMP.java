package IRTree;

import IRvisitor.*;
import IRTree.Exp;
import IRTree.ExpList;
import temp.Temp;

public class TEMP extends Exp {
  
	public Temp temp;

	  public TEMP(Temp t)
	  {
	    temp = t;
	  }

	  public ExpList kids()
	  {
	    return null;
	  }

	  public Exp build(ExpList kids)
	  {
	    return this;
	  }

	  public String accept(StringVisitor v)
	  {
	    return v.visit(this);
	  }

	  public void accept(IntVisitor v, int d)
	  {
	    v.visit(this, d);
	  }

	  public Temp accept(TempVisitor v)
	  {
	    return v.visit(this);
	  }
}

