package IRTree;

import IRvisitor.IntVisitor;
import IRvisitor.StringVisitor;
import IRvisitor.TempVisitor;
import temp.Temp;
import IRTree.Exp;
import IRTree.ExpList;

public class CONST extends Exp
{
  public int value;

  public CONST(int v)
  {
    value = v;
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
