package IRTree;

import IRvisitor.IntVisitor;
import IRvisitor.StringVisitor;
import IRvisitor.TempVisitor;
import IRTree.ExpList;
import IRTree.Stm;

abstract public class Stm {

	abstract public ExpList kids();

	abstract public Stm build(ExpList kids);

	abstract public String accept(StringVisitor v);

	abstract public void accept(IntVisitor v, int d);

	abstract public void accept(TempVisitor v);
}
