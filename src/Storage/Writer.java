package Storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Logic.Engine.Task;


/**
 * This class belongs to the Storage component of the overall architecture.
 * Responsible for writing tasks to txt file.
 * @author amit
 *
 */
public class Writer {
/*****************Attributes***********************/
	private String fileName;
	private ArrayList<Task> dLineList;
	private ArrayList<Task> interList;
	private ArrayList<Task> floatList;
	
/*****************Constructor***********************/

	public Writer(String fileName, ArrayList<Task> dLineList, ArrayList<Task> interList, ArrayList<Task> floatList){
		this.fileName = fileName;
		this.dLineList = dLineList;
		this.interList = interList;
		this.floatList = floatList;
	}
	
/*****************Methods***********************/
	
	/**
	 * Does the actual writing to .txt file.
	 * @param dLineList
	 * @param interList
	 * @param floatList
	 * @throws IOException
	 * @author amit
	 */
	public void writeToFile() throws IOException{
		File file = new File("../Epiphany/src/Logic/Engine/Projects/" + fileName);
		FileWriter f = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(f);
		
		writer.write("");
		
			// for deadline
			for(Task t : dLineList){
				writer.write(t.toString());
				writer.newLine();
				writer.flush();
			}
			
			//for interval
			for(Task t : interList){
				writer.write(t.toString());
				writer.newLine();
				writer.flush();
			}
			
			//for floating
			for(Task t : floatList){
				writer.write(t.toString());
				writer.newLine();
				writer.flush();
			}
		

		writer.close();
	}
	
	
	
	
}

