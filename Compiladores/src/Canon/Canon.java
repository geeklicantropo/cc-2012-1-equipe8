package Canon;

import IRvisitor.IntVisitor;
import IRvisitor.StringVisitor;
import IRvisitor.TempVisitor;

class MoveCall extends IRTree.Stm {
	IRTree.TEMP dst;
	IRTree.CALL src;

	MoveCall(IRTree.TEMP d, IRTree.CALL s) {
		dst = d;
		src = s;
	}

	public IRTree.ExpList kids() {
		return src.kids();
	}

	public IRTree.Stm build(IRTree.ExpList kids) {
		return new IRTree.MOVE(dst, src.build(kids));
	}

	@Override
	public String accept(StringVisitor v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(IntVisitor v, int d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(TempVisitor v) {
		// TODO Auto-generated method stub
		
	}
}

class ExpCall extends IRTree.Stm {
	IRTree.CALL call;

	ExpCall(IRTree.CALL c) {
		call = c;
	}

	public IRTree.ExpList kids() {
		return call.kids();
	}

	public IRTree.Stm build(IRTree.ExpList kids) {
		return new IRTree.EXP1(call.build(kids));
	}

	@Override
	public String accept(StringVisitor v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(IntVisitor v, int d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accept(TempVisitor v) {
		// TODO Auto-generated method stub

	}
}

class StmExpList {
	IRTree.Stm stm;
	IRTree.ExpList exps;

	StmExpList(IRTree.Stm s, IRTree.ExpList e) {
		stm = s;
		exps = e;
	}
}

public class Canon {

	static boolean isNop(IRTree.Stm a) {
		return a instanceof IRTree.EXP1
				&& ((IRTree.EXP1) a).exp instanceof IRTree.CONST;
	}

	static IRTree.Stm seq(IRTree.Stm a, IRTree.Stm b) {
		if (isNop(a))
			return b;
		else if (isNop(b))
			return a;
		else
			return new IRTree.SEQ(a, b);
	}

	static boolean commute(IRTree.Stm a, IRTree.Exp b) {
		return isNop(a) || b instanceof IRTree.NAME
				|| b instanceof IRTree.CONST;
	}

	static IRTree.Stm do_stm(IRTree.SEQ s) {
		return seq(do_stm(s.left), do_stm(s.right));
	}

	static IRTree.Stm do_stm(IRTree.MOVE s) {
		if (s.dst instanceof IRTree.TEMP && s.src instanceof IRTree.CALL)
			return reorder_stm(new MoveCall((IRTree.TEMP) s.dst,
					(IRTree.CALL) s.src));
		else if (s.dst instanceof IRTree.ESEQ)
			return do_stm(new IRTree.SEQ(((IRTree.ESEQ) s.dst).stm,
					new IRTree.MOVE(((IRTree.ESEQ) s.dst).exp, s.src)));
		else
			return reorder_stm(s);
	}

	static IRTree.Stm do_stm(IRTree.EXP1 s) {
		if (s.exp instanceof IRTree.CALL)
			return reorder_stm(new ExpCall((IRTree.CALL) s.exp));
		else
			return reorder_stm(s);
	}

	static IRTree.Stm do_stm(IRTree.Stm s) {
		if (s instanceof IRTree.SEQ)
			return do_stm((IRTree.SEQ) s);
		else if (s instanceof IRTree.MOVE)
			return do_stm((IRTree.MOVE) s);
		else if (s instanceof IRTree.EXP1)
			return do_stm((IRTree.EXP1) s);
		else
			return reorder_stm(s);
	}

	static IRTree.Stm reorder_stm(IRTree.Stm s) {
		StmExpList x = reorder(s.kids());
		return seq(x.stm, s.build(x.exps));
	}

	static IRTree.ESEQ do_exp(IRTree.ESEQ e) {
		IRTree.Stm stms = do_stm(e.stm);
		IRTree.ESEQ b = do_exp(e.exp);
		return new IRTree.ESEQ(seq(stms, b.stm), b.exp);
	}

	static IRTree.ESEQ do_exp(IRTree.Exp e) {
		if (e instanceof IRTree.ESEQ)
			return do_exp((IRTree.ESEQ) e);
		else
			return reorder_exp(e);
	}

	static IRTree.ESEQ reorder_exp(IRTree.Exp e) {
		StmExpList x = reorder(e.kids());
		return new IRTree.ESEQ(x.stm, e.build(x.exps));
	}

	static StmExpList nopNull = new StmExpList(new IRTree.EXP1(
			new IRTree.CONST(0)), null);

	static StmExpList reorder(IRTree.ExpList exps) {
		if (exps == null)
			return nopNull;
		else {
			IRTree.Exp a = exps.head;
			if (a instanceof IRTree.CALL) {
				temp.Temp t = new temp.Temp();
				IRTree.Exp e = new IRTree.ESEQ(new IRTree.MOVE(new IRTree.TEMP(
						t), a), new IRTree.TEMP(t));
				return reorder(new IRTree.ExpList(e, exps.tail));
			} else {
				IRTree.ESEQ aa = do_exp(a);
				StmExpList bb = reorder(exps.tail);
				if (commute(bb.stm, aa.exp))
					return new StmExpList(seq(aa.stm, bb.stm),
							new IRTree.ExpList(aa.exp, bb.exps));
				else {
					temp.Temp t = new temp.Temp();
					return new StmExpList(seq(aa.stm, seq(new IRTree.MOVE(
							new IRTree.TEMP(t), aa.exp), bb.stm)),
							new IRTree.ExpList(new IRTree.TEMP(t), bb.exps));
				}
			}
		}
	}

	static IRTree.StmList linear(IRTree.SEQ s, IRTree.StmList l) {
		return linear(s.left, linear(s.right, l));
	}

	static IRTree.StmList linear(IRTree.Stm s, IRTree.StmList l) {
		if (s instanceof IRTree.SEQ)
			return linear((IRTree.SEQ) s, l);
		else
			return new IRTree.StmList(s, l);
	}

	static public IRTree.StmList linearize(IRTree.Stm s) {
		return linear(do_stm(s), null);
	}
}
