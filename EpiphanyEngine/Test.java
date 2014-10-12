import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import Engine.customComparator;

public void testSort() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date one = sdf.parse("31-4-2014");
			Date two = sdf.parse("10-10-2014");
			Date three = sdf.parse("31-12-2014");
			Date four = sdf.parse("17-6-2014");
			testDate1.add(one);
			testDate1.add(two);
			testDate1.add(three);
			testDate1.add(four);
			Collections.sort(testDate1, new customComparator());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}