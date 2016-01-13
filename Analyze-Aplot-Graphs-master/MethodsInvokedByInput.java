import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class MethodsInvokedByInput 
{
  public static void main(String[] args) throws Exception
  {
  	String file_path = "/home/pavan/analyzing_Aprof/aprof-outputs/";
	File base_folder = new File(file_path);
	File[] input_folders = base_folder.listFiles();
	String line;
	String method;
	String curr_folder;
	
	LinkedHashMap<String, HashSet<String>> input_methods_mapping = new LinkedHashMap<>();
	HashSet<String> temp = new HashSet<>();
	// opening each folder in base folder
	for (File folder : input_folders) 
	{
		// opening each file in current folder 
		File[] aprof_output = folder.listFiles();
		curr_folder = folder.toString();
		
		//We are removing the path before adding it to the LinkedHashMap, so that the output looks
		//good.
		curr_folder = curr_folder.replace(file_path, "");
		temp= new HashSet<>();
		input_methods_mapping.put(curr_folder, temp);
		for (File aprof_file : aprof_output) 
		{
			// adding all the methods in current file to a arraylist
			BufferedReader br = new BufferedReader(new FileReader(aprof_file));
			while ((line = br.readLine()) != null) 
			{
				if(line.charAt(0)=='r')
				{
					//Only take methods that are in our code base, eliminating the ones obtained
					//from third party.
					if(line.contains("linuxdcpp/linuxdcpp") && !line.contains("<"))
					{
						method = line.split("\"")[1];
						
						//Splitting the argumnets and taking only the method names.
						method = method.split("\\(")[0];
						method = method.replace("dcpp::","");
						//method = method.substring(1, method.length()-1);
						if(!method.isEmpty()){
							input_methods_mapping.get(curr_folder).add(method);
							//System.out.println(method);
						}
					}
					
				}
			}
			br.close();
		}
	}
	
	// Finding All smethods for a given input
	PrintWriter writer = new PrintWriter("All_methods_Invoked_By_Input.txt");
	HashSet<String> methods = new HashSet<>();
	boolean isUniqueMethod = true;
	// looping over each folder
	for (String input1 : input_methods_mapping.keySet()) 
	{
		// looping over each method in current folder say folder1
		for (String method1 : input_methods_mapping.get(input1)) 
		{
			//If the Hashset already contains this method, then do not write it to the output file.
			if(!methods.contains(method1))
			{
			//else write the name of the method to the output file, and also add the method
			//to the hashset.
				methods.add(method1);
				writer.println(input1 + " " + method1);
			}
		}
	}
	writer.close();
	System.out.println("done");
  }
}
