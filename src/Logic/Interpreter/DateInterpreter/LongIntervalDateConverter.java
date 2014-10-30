package Logic.Interpreter.DateInterpreter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Logic.Exceptions.InvalidCommandException;

public class LongIntervalDateConverter {

	public static void convert(String input, ArrayList<Date> d) throws InvalidCommandException {
		d.clear();
		String[] tokens = input.split(" to ");
		ArrayList<Date> dFrom = new ArrayList<Date>();
		ArrayList<Date> dTo = new ArrayList<Date>();
		if(tokens.length==2){
			DeadlineDateConverter.convert(tokens[0], dFrom);
			DeadlineDateConverter.convert(tokens[1], dTo);
		}
		if(dFrom.size()==1 && dTo.size()==1){
			if(dTo.get(0).before(dFrom.get(0))){
				Calendar cal = Calendar.getInstance();
				cal.setTime(dTo.get(0));
				cal.add(Calendar.MONTH, 1);
				d.add(dFrom.get(0));
				d.add(cal.getTime());
			} else {
				d.add(dFrom.get(0));
				d.add(dTo.get(0));
			}
		}
	}


	public LongIntervalDateConverter() throws UnsupportedOperationException{
		throw new UnsupportedOperationException("cannot instantiate");
	}
}
