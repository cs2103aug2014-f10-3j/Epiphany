//@author A0118905A
package Logic.Interpreter.DateInterpreter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Logic.Exceptions.InvalidCommandException;

public class ShortIntervalDateConverter {
	private static final List<Matcher> fromMatchers;

	//List of date matcher classes that we will exhaustively use to determine whether
	//the input contains a short interval style date.
	static {
		fromMatchers = new LinkedList<Matcher>();

		//WITH TIME SPECIFICATIONS
		fromMatchers.add(new SoonMatcherWithTime());
		fromMatchers.add(new DayMatcherWithTime());
		fromMatchers.add(new ExtendedDayMatcherWithTime());
		fromMatchers.add(new OnlyDateMatcherWithTime());
		//Matchers which follow default java date formats
		fromMatchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd.MM.yyyy' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd/MM/yyyy' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd-MM-yyyy' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd MM yyyy' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd MMM' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd/MM' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'st' MMM' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'nd' MMM' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'rd' MMM' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'th' MMM' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'st'' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'nd'' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'rd'' from 'HH:mm")));
		fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'th'' from 'HH:mm")));
	}

	public static void convert(String input, ArrayList<Date> d) throws InvalidCommandException {
		//the format for short interval dates is "<date>from<HH:mm>to<HH:mm>" or "<date>"
		d.clear();
		String toInterpret = input;
		if(toInterpret.matches("(.*)\\d+:\\d\\d")&& !(toInterpret.contains(" to "))){
			//a short interval command cannot contain only a start time. A start time must be accompanied with an end time.
			throw new InvalidCommandException();
		}
		//First we search for the format "on<date>from<HH:mm>to<HH:mm>"
		for (Matcher matcher : fromMatchers) {
			//We try to convert the from part of the date (everything before the word to is the from part)
			Date date = matcher.tryConvert(input.split(" to ")[0]);
			if (date != null) {
				//if we get something to match for the from part, then try the to part.
				d.add(date);
				String toTimeString = input.split(" to ")[1];
				try {
					Calendar cal = Calendar.getInstance();
					cal.setTime(d.get(0));
					Date toDate = (new SimpleDateFormat("HH:mm")).parse(toTimeString);
					cal.set(Calendar.HOUR_OF_DAY, toDate.getHours());
					cal.set(Calendar.MINUTE, toDate.getMinutes());
					d.add(cal.getTime());
				} catch (ParseException ex) {
					//If the to part is not of the form HH:mm, then it is not of short interval form.
					d.clear();
					return;
				}
				return;
			}
		}
		//If we get nothing, then we search for the format "on<date>"
		DeadlineDateConverter.convert(toInterpret, d);
		if(!d.isEmpty()){
			//If that passes, then we use the date, and make the interval to be that day from 0000 to 2359
			Calendar cal = Calendar.getInstance();
			cal.setTime(d.get(0));
			d.clear();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			d.add(cal.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			d.add(cal.getTime());
		}
	}


	public ShortIntervalDateConverter() throws UnsupportedOperationException{
		throw new UnsupportedOperationException("cannot instantiate");
	}
}
