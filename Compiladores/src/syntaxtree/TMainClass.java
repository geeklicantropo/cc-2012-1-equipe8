package syntaxtree;

import CheckType.TypeCheckVisitor;
import symboltable.Symbol;
import symboltable.Table;
import symboltable.TableVisitor;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class TMainClass extends TExp {
	public TIdentifier i1, i2;
	public TStatement s;

	public TMainClass(TIdentifier ai1, TIdentifier ai2, TStatement as) {
		i1 = ai1;
		i2 = ai2;
		s = as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public TType accept(TypeVisitor v) {
		return v.visit(this);
	}

	public Table accept(TableVisitor tableVisitor, Symbol symbol) {
		return new Table(symbol);
	}

	public void accept(TypeCheckVisitor typeCheckVisitor) {

		typeCheckVisitor.visit(this);
	}

//	public translate.Exp accept(ExpVisitor v) {
//		return v.visit(this);
//	}
}
