package syntaxtree;

import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class TIdentifier {
	public String s;

	public TIdentifier(String as) {
		s = as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public TType accept(TypeVisitor v) {
		return v.visit(this);
	}

	public String toString() {
		return s;
	}

	public translate.Exp accept(ExpVisitor v) {
		return v.visit(this);
	}
}
