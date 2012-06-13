package IRTree;

import IRvisitor.IntVisitor;
import IRvisitor.StringVisitor;
import IRvisitor.TempVisitor;
import IRTree.Exp;
import IRTree.ExpList;
import IRTree.JUMP;
import IRTree.NAME;
import IRTree.Stm;
import temp.Label;
import temp.LabelList;;

public class JUMP extends Stm
{
  public Exp       exp;
  public LabelList targets;

  public JUMP(Exp e, LabelList t)
  {
    exp = e;
    targets = t;
  }

  public JUMP(Label target)
  {
    this(new NAME(target), new LabelList(target, null));
  }

  public ExpList kids()
  {
    return new ExpList(exp, null);
  }

  public Stm build(ExpList kids)
  {
    return new JUMP(kids.head, targets);
  }

  public String accept(StringVisitor v)
  {
    return v.visit(this);
  }

  public void accept(IntVisitor v, int d)
  {
    v.visit(this, d);
  }

  public void accept(TempVisitor v)
  {
    v.visit(this);
  }

}
