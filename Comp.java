import java.util.*;
// hello ti
class Comp implements Comparator<Woman>{
	@override
	public int compare(Woman w1, Woman w2){
		// return -1 if w1 < w2
		if(w1.dilation < w2.dilation){
			return -1;
		} else if(w1.dilation > w2.dilation){
			return 1;
		} else{
			// dilation values are equal. need to order by who came first.
			//w1 came first.
			if(!w1.name.equals(w2.name)){
				// different people w1 should get priority.
				return -1;
			}
		}

	}
}