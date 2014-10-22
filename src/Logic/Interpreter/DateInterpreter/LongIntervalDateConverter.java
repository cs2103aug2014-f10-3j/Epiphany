package Logic.Interpreter.DateInterpreter;

import java.util.ArrayList;
import java.util.Date;

public class LongIntervalDateConverter {
	
    public static void convert(String input, ArrayList<Date> d) {
    	d.clear();
    	String[] tokens = input.split(" to ");
    	ArrayList<Date> dFrom = new ArrayList<Date>();
    	ArrayList<Date> dTo = new ArrayList<Date>();
    	DeadlineDateConverter.convert(tokens[0], dFrom);
    	DeadlineDateConverter.convert(tokens[1], dTo);
    	if(dFrom.size()==1 && dTo.size()==1){
    		d.add(dFrom.get(0));
    		d.add(dTo.get(0));
    	}
    }
    

    private LongIntervalDateConverter() {
        throw new UnsupportedOperationException("cannot instantiate");
    }
}
