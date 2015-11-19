package simpledatabase;
import java.util.ArrayList;
import java.util.Comparator;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	private boolean sorted = false;
	private int cntTuple = -1;
	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		if (!sorted){
		while((child.next())!=null){
			tuplesResult.add(new Tuple(new ArrayList<Attribute>(child.getAttributeList())));
		}

		tuplesResult.sort(new Comparator<Tuple>(){
			@Override
			public int compare(Tuple t1, Tuple t2) {
				for(int cntAttribute =0;cntAttribute<tuplesResult.get(0).getAttributeList().size();cntAttribute++)
				if(t1.getAttributeName(cntAttribute).equals(orderPredicate)&&t2.getAttributeName(cntAttribute).equals(orderPredicate)){
					switch(t1.getAttributeType(cntAttribute).type){
					case INTEGER:
						return ((Integer)t1.getAttributeValue(cntAttribute)).compareTo((Integer)t2.getAttributeValue(cntAttribute));
					case DOUBLE:
						return ((Double)t1.getAttributeValue(cntAttribute)).compareTo((Double)t2.getAttributeValue(cntAttribute));
					case LONG:
						return ((Long)t1.getAttributeValue(cntAttribute)).compareTo((Long)t2.getAttributeValue(cntAttribute));
						
					case SHORT:
						return ((Short)t1.getAttributeValue(cntAttribute)).compareTo((Short)t2.getAttributeValue(cntAttribute));
					
					case FLOAT:
						return ((Float)t1.getAttributeValue(cntAttribute)).compareTo((Float)t2.getAttributeValue(cntAttribute));
	
					case STRING:
						return ((String)t1.getAttributeValue(cntAttribute)).compareTo((String)t2.getAttributeValue(cntAttribute));
						
					case BOOLEAN:
						return ((Boolean)t1.getAttributeValue(cntAttribute)).compareTo((Boolean)t2.getAttributeValue(cntAttribute));
						
					case CHAR:
						return ((String)t1.getAttributeValue(cntAttribute)).compareTo((String)t2.getAttributeValue(cntAttribute));
					
					case BYTE:
						return ((Byte)t1.getAttributeValue(cntAttribute)).compareTo((Byte)t2.getAttributeValue(cntAttribute));
					}		
				}
				return 0;
				
			}
		});
		sorted=true;
		
		}
				
		if(cntTuple!=tuplesResult.size()-1){
			cntTuple++;
			newAttributeList.clear();
			newAttributeList.addAll(tuplesResult.get(cntTuple).getAttributeList());
			child.getAttributeList().clear();
			child.getAttributeList().addAll(newAttributeList);
			return tuplesResult.get(cntTuple);
		}
		return null;
		
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}