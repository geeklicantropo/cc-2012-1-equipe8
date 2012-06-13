package IRTree;

import IRTree.Stm;
import IRTree.StmList;
import util.List;

/**
 * @author m
 *
 */
public class StmList {

	public Stm     head;
	  public StmList tail;

	  /**
	 * @param h
	 * @param t
	 */
	public StmList(Stm h, StmList t)
	  {
	    head = h;
	    tail = t;
	  }

}