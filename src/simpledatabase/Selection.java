package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {

		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();

	}
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple;
		Operator findTableClass = child;
		while((findTableClass.getChild())!=null)
			findTableClass = findTableClass.getChild();
		if(!(findTableClass.from.equals(whereTablePredicate)))
			return child.next();
		
		while((tuple=child.next())!=null){
		attributeList = child.getAttributeList();
			for (int cntAttribute =0;cntAttribute<attributeList.size();cntAttribute++)
				if(tuple.getAttributeName(cntAttribute).equals(whereAttributePredicate))
					if(tuple.getAttributeValue(cntAttribute).equals(whereValuePredicate))
					
						return tuple;
		}
			
			
		return null;	
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}