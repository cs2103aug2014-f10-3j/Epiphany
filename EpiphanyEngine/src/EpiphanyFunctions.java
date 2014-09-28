import java.util.ArrayList;

public class EpiphanyFunctions {
	// Default holding project is used for floating tasks i.e. tasks with no specified deadlines/project names
	ArrayList<Task> Default = new ArrayList<Task>();
	
	public ArrayList<Task> addTask(String i, String d, String p) {
		if (d == null && p == null) {
		Default.add(new Task(i, null, null));
		}
		
		return Default;
	}
}