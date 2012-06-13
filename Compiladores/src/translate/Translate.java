package translate;

import syntaxtree.*;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

import java.util.HashMap;
import Frame.Access;
import temp.Label;
import temp.Temp;
import IRTree.ESEQ;
import IRTree.ExpList;
import IRTree.MOVE;
import IRTree.TEMP;

public class Translate implements ExpVisitor {
	IRTree.Exp e1 = null;

	private Frag frags = null;
	private Frag frags_tail = null;
	private Frame.Frame currFrame = null;
	private IRTree.Exp objPtr = null;
	private int offset;
	private HashMap<String, Integer> fieldVars = null;
	private HashMap<String, Access> vars = null;

	public Translate(TProgram p, Frame.Frame f) {
		currFrame = f;
		p.accept(this);
	}

	public void procEntryExit(IRTree.Stm body) {
		ProcFrag newfrag = new ProcFrag(body, currFrame);
		if (frags == null)
			frags = newfrag;
		else
			frags_tail.next = newfrag;
		frags_tail = newfrag;

	}

	public Frag getResults() {
		return frags;
	}

	public String printResults() {
		IRTree.Print p = new IRTree.Print(System.out);
		Frag f = frags;
		String saida = "";
		while (f != null) {
			saida+="\n";
			saida+="Function: "+ ((ProcFrag) f).frame.name.toString()+"";
			p.prStm(((ProcFrag) f).body);
			saida+=p.getSaida();
			p.clean();
			f = f.next;
		}
		return saida;
	}

	public Exp visit(TProgram n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++)
			n.cl.elementAt(i).accept(this);
		return null;
	}

	public Exp visit(TMainClass n) {
		Frame.Frame newFrame = currFrame.newFrame("main", 1);
		Frame.Frame oldFrame = currFrame;
		currFrame = newFrame;

		IRTree.Stm s = (n.s.accept(this)).unNx();

		IRTree.Exp retExp = new IRTree.CONST(0);
		IRTree.Stm body = new IRTree.MOVE(new IRTree.TEMP(currFrame.RV()),
				new IRTree.ESEQ(s, retExp));

		procEntryExit(body);
		currFrame = oldFrame;

		return null;
	}

	public Exp visit(TClassDeclSimple n) {
		for (int i = 0; i < n.ml.size(); i++)
			n.ml.elementAt(i).accept(this);
		return null;
	}

	public Exp visit(TClassDeclExtends n) {
		for (int i = 0; i < n.ml.size(); i++)
			n.ml.elementAt(i).accept(this);

		return null;
	}

	public Exp visit(TVarDecl n) {
		Access ac = currFrame.allocLocal(false);
		return new Nx(new IRTree.MOVE(ac.exp(new TEMP(currFrame.FP())),
				new IRTree.CONST(0)));
	}

	public Exp visit(TMethodDecl n) {
		Frame.Frame newFrame = currFrame.newFrame(n.i.toString(),
				n.fl.size() + 1);
		Frame.Frame oldFrame = currFrame;
		currFrame = newFrame;

		for (int i = 0; i < n.vl.size(); i++)
			n.vl.elementAt(i).accept(this);

		/*
		 * ADD CODE: move formals to fresh temps and add them to the HashMap
		 * vars
		 */

		for (int i = 0; i < n.sl.size(); i++)
			n.sl.elementAt(i).accept(this);

		/*
		 * ADD CODE: set value of IRTree.Exp objPtr Recall that objPtr is a
		 * pointer to the address in memory at which instance variables are
		 * stored for the current class (i.e., it is "this"). In the MiniJava
		 * compiler, it is passed as an argument during all calls to MiniJava
		 * methods.
		 */

		/*
		 * ADD CODE: visit each statement in method body, creating new
		 * IRTree.SEQ nodes as needed
		 */
		IRTree.Stm body = null; // FILL IN

		/*
		 * ADD CODE: get return expression and group with statements of body,
		 * then create IRTree.MOVE to store return value
		 */

		/* create new procedure fragment for method and add to list */
		procEntryExit(body);
		currFrame = oldFrame;
		vars = null;
		objPtr = null;

		return null;
	}

	public Exp visit(TFormal n) {
		return new Ex(new IRTree.CONST(0));
	}

	public Exp visit(TTypeIntegerArray n) {
		return new Ex(new IRTree.CONST(0));
	}

	public Exp visit(TTypeBoolean n) {
		return new Ex(new IRTree.CONST(0));
	}

	public Exp visit(TTypeInteger n) {
		return new Ex(new IRTree.CONST(0));
	}

	public Exp visit(TTypeIdentifier n) {
		return new Ex(new IRTree.CONST(0));
	}

	public Exp visit(TStatementBlock n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		return null;
		// return new Nx(stm);
	}

	public Exp visit(TStatementIf n) {
		Label T = new Label();
		Label F = new Label();
		Label D = new Label();
		Exp exp = n.e.accept(this);
		Exp stmT = n.s1.accept(this);
		Exp stmF = n.s2.accept(this);
		return new Nx(
				new IRTree.SEQ(new IRTree.SEQ(new IRTree.SEQ(new IRTree.SEQ(
						new IRTree.CJUMP(IRTree.CJUMP.EQ, exp.unEx(),
								new IRTree.CONST(1), T, F), new IRTree.SEQ(
								new IRTree.LABEL(T), stmT.unNx())),
						new IRTree.JUMP(D)), new IRTree.SEQ(
						new IRTree.LABEL(F), stmF.unNx())), new IRTree.LABEL(D)));

	}

	public Exp visit(TStatementWhile n) {
		Label test = new Label();
		Label T = new Label();
		Label F = new Label();
		Exp exp = n.e.accept(this);
		Exp body = n.s.accept(this);

		return new Nx(new IRTree.SEQ(new IRTree.SEQ(new IRTree.SEQ(
				new IRTree.LABEL(test), (new IRTree.CJUMP(IRTree.CJUMP.EQ, exp
						.unEx(), new IRTree.CONST(1), T, F))), (new IRTree.SEQ(
				new IRTree.LABEL(T), body.unNx()))), new IRTree.LABEL(F)));

	}

	public Exp visit(TStatementPrint n) {
		if (e1 != null)

		{
			e1 = (n.e.accept(this)).unEx();
		}
		return new Ex(currFrame.externalCall("printInt", new IRTree.ExpList(e1,
				null)));
	}

	public Exp visit(TStatementAssign n) {
		IRTree.Exp i = n.i.accept(this).unEx();
		IRTree.Exp e = n.e.accept(this).unEx();

		return new Nx(new IRTree.MOVE(i, e));
	}

	public Exp visit(TStatementArrayAssign n) {
		IRTree.Exp e1 = (n.e1.accept(this)).unEx();
		IRTree.Exp e2 = (n.e2.accept(this)).unEx();
		IRTree.Exp expId = (n.i.accept(this)).unEx();
		return new Nx(new IRTree.MOVE(new IRTree.BINOP(IRTree.BINOP.PLUS,
				new IRTree.MEM(expId), new IRTree.BINOP(IRTree.BINOP.MUL, e1,
						new IRTree.CONST(4))), e2));
	}

	public Exp visit(TExpOpAnd n) {
		Temp t1 = new Temp();
		Label done = new Label();
		Label ok1 = new Label();
		Label ok2 = new Label();

		IRTree.Exp left = n.e1.accept(this).unEx();
		IRTree.Exp right = n.e2.accept(this).unEx();
		return new Ex(new IRTree.ESEQ(new IRTree.SEQ(new IRTree.SEQ(
				new IRTree.SEQ(new IRTree.SEQ(new IRTree.SEQ(new IRTree.MOVE(
						new IRTree.TEMP(t1), new IRTree.CONST(0)),
						new IRTree.CJUMP(IRTree.CJUMP.EQ, left,
								new IRTree.CONST(1), ok1, done)),
						new IRTree.SEQ(new IRTree.LABEL(ok1), new IRTree.CJUMP(
								IRTree.CJUMP.EQ, right, new IRTree.CONST(1),
								ok2, done))), new IRTree.SEQ(new IRTree.LABEL(
						ok2), new IRTree.MOVE(new IRTree.TEMP(t1),
						new IRTree.CONST(1)))), new IRTree.JUMP(done)),
				new IRTree.LABEL(done)), new IRTree.TEMP(t1)));

	}

	public Exp visit(TExpOpLessThan n) {
		Exp expl = n.e1.accept(this);
		Exp expr = n.e2.accept(this);
		Label T = new Label();
		Label F = new Label();
		Temp t = new Temp();
		return new Ex(new IRTree.ESEQ(new IRTree.SEQ(new IRTree.SEQ(
				new IRTree.SEQ(new IRTree.MOVE(new IRTree.TEMP(t),
						new IRTree.CONST(0)), new IRTree.CJUMP(IRTree.CJUMP.LT,
						expl.unEx(), expr.unEx(), T, F)), new IRTree.SEQ(
						new IRTree.LABEL(T), new IRTree.MOVE(
								new IRTree.TEMP(t), new IRTree.CONST(1)))),
				new IRTree.LABEL(F)), new IRTree.TEMP(t)));
	}

	public Exp visit(TExpOpPlus n) {
		IRTree.Exp exp1 = (n.e1.accept(this)).unEx();
		IRTree.Exp exp2 = (n.e2.accept(this)).unEx();
		return new Ex(new IRTree.BINOP(IRTree.BINOP.PLUS, exp1, exp2));
	}

	public Exp visit(TExpOpMinus n) {
		IRTree.Exp exp1 = (n.e1.accept(this)).unEx();
		IRTree.Exp exp2 = (n.e2.accept(this)).unEx();
		return new Ex(new IRTree.BINOP(IRTree.BINOP.MINUS, exp1, exp2));
	}

	public Exp visit(TExpOpTimes n) {
		IRTree.Exp exp1 = (n.e1.accept(this)).unEx();
		IRTree.Exp exp2 = (n.e2.accept(this)).unEx();
		return new Ex(new IRTree.BINOP(IRTree.BINOP.MUL, exp1, exp2));
	}

	public Exp visit(TExpArrayLookup n) {
		Temp t_index = new Temp();
		Temp t_size = new Temp();
		IRTree.Exp e1 = n.e1.accept(this).unEx();
		IRTree.Exp e2 = n.e2.accept(this).unEx();

		Label F = new Label();
		Label T = new Label();

		IRTree.ExpList args1 = new ExpList(e2, null);

		IRTree.Stm s1 = new IRTree.SEQ(new IRTree.SEQ(new IRTree.SEQ(
				new IRTree.SEQ(new IRTree.SEQ(new IRTree.MOVE(new IRTree.TEMP(
						t_index), new IRTree.BINOP(IRTree.BINOP.MUL, e2,
						new IRTree.CONST(4))), new IRTree.MOVE(new IRTree.TEMP(
						t_size), new IRTree.MEM(e1))), new IRTree.CJUMP(
						IRTree.CJUMP.GE, new IRTree.TEMP(t_index),
						new IRTree.TEMP(t_size), T, F)), new IRTree.LABEL(T)),
				new IRTree.MOVE(new IRTree.TEMP(new Temp()), new IRTree.CALL(
						new IRTree.NAME(new Label("_error")), args1))),
				new IRTree.LABEL(F));

		Temp t = new Temp();
		IRTree.Stm s2 = new IRTree.SEQ(s1, new IRTree.MOVE(new IRTree.TEMP(t),
				new IRTree.MEM(new IRTree.BINOP(IRTree.BINOP.PLUS, e1,
						new IRTree.BINOP(IRTree.BINOP.PLUS, new IRTree.BINOP(
								IRTree.BINOP.MUL, e2, new IRTree.CONST(4)),
								new IRTree.CONST(4))))));
		return new Ex(new IRTree.ESEQ(s2, new IRTree.TEMP(t)));

	}

	public Exp visit(TExpArrayLength n) {
		n.e.accept(this);

		return null;
	}

	// TODO: checar retorno
	public Exp visit(TExpCall n) {
		ExpList el = null;
		for (int i = 0; i < n.el.size(); i++) {
			Exp ex = n.el.elementAt(i).accept(this);
			el = new ExpList(ex.unEx(), el);
		}

		return new Ex(new IRTree.CALL(new IRTree.NAME(new Label(n.e
				.accept(this).toString())), el));
	}

	public Exp visit(TExpIntegerLiteral n) {
		return new Ex(new IRTree.CONST(n.i));
	}

	public Exp visit(TExpTrue n) {
		return new Ex(new IRTree.CONST(1));
	}

	public Exp visit(TExpFalse n) {
		return new Ex(new IRTree.CONST(0));
	}

	public Exp visit(TExpId n) {
		return new Ex(getIdTree(n.s));
	}

	public Exp visit(TExpThis n) {
		return new Ex(objPtr);
	}

	public Exp visit(TExpNewArray n) {
		IRTree.Exp e = n.e.accept(this).unEx();
		ExpList params = new ExpList(e, null);
		Temp t = new Temp();

		return new Ex(new ESEQ(new MOVE(new TEMP(t), currFrame.externalCall(
				"newArray", params)), new TEMP(t)));
	}

	public Exp visit(TExpNewObject n) {
		// TODO
		return null;
	}

	public Exp visit(TExpNot n) {
		return new Ex(new IRTree.BINOP(IRTree.BINOP.MINUS, new IRTree.CONST(1),
				(n.e.accept(this)).unEx()));
	}

	public Exp visit(TIdentifier n) {
		return new Ex(new TEMP(currFrame.FP()));
	}

	private IRTree.Exp getIdTree(String id) {
		Frame.Access a = null;

		try {
			a = vars.get(id);

			if (a == null) {
				int offset = fieldVars.get(id).intValue();
				return new IRTree.MEM(new IRTree.BINOP(IRTree.BINOP.PLUS,
						objPtr, new IRTree.CONST(offset)));
			}

			return a.exp(new IRTree.TEMP(currFrame.FP()));
		} catch (Exception ex) {
			return new IRTree.MEM(new IRTree.BINOP(IRTree.BINOP.PLUS, objPtr,
					new IRTree.CONST(0)));
		}
	}
}
