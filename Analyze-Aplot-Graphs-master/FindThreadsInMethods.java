import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;

public class FindThreadsInMethods 
{
   public static void main(String[] args) throws Exception
   {
	   String filePath = "/home/pavan/workspace/AnalyzeAplotGraphs/All_methods_Invoked_By_Input.txt";
	   BufferedReader br = new BufferedReader(new FileReader(filePath));
	   String base_folder_path = "/home/pavan/linuxdcpp_core_code";
	   File base_folder = new File (base_folder_path);
	   File[] input_folders = base_folder.listFiles();
	   String line = "";
	   String method ="";
	   String curr_cpp_file="";
	   // looping over each input specific method
	   while((line=br.readLine())!=null)
	   {
		   try{
		   method = line.split(" ")[1];
		   }
		   catch(Exception e)
		   {
			 System.out.println(line); 
		   }
		   // looping over folder in linuxdcpp
		   for (File folder : input_folders) 
			{
			    if(!folder.isDirectory())
			    	continue;
				File[] cpp_files = folder.listFiles();
				curr_cpp_file = folder.toString();
				// looping over each cpp file in curr folder
				for (File cpp_file : cpp_files) 
				{
					// have to look up to only one level
					if(cpp_file.isDirectory())
						continue;
					// adding all the methods in current file to a arraylist
					BufferedReader br2 = new BufferedReader(new FileReader(cpp_file));
					int bracket_count=1;
					boolean isCompleted = false;
					while ((line = br2.readLine()) != null) 
					{
						// search for method definition 
						if(line.contains(method))
						{   
							if(countOccurances(line, '{')==1) 
								bracket_count =1;
							else
								bracket_count=0;
							line=br2.readLine();
							// read the metho definition
							while(!(bracket_count==0 && isCompleted) && line!=null)
							{
								//System.out.println(line);
								bracket_count  =  bracket_count + countOccurances(line, '{');
								bracket_count  =  bracket_count - countOccurances(line, '}');
								if(containKeyWords(line))
								{
									System.out.println(method + "\t" + cpp_file.getCanonicalPath().replace(base_folder_path, ""));
									break;
								}
								if(bracket_count ==0)
								 isCompleted = true;
								
								line = br2.readLine();
							}
							break;
						}
					}
					br2.close();
				}
			}
		   
	   }
	   br.close();
	
   }
   public static int countOccurances(String line, char chr)
   {
 	  int count = 0;
 	  for(int i = 0; i< line.length(); i+=1)
 	  {
 		  if(line.charAt(i)==chr)
 		  {
 			  count+=1;
 		  }
 	  }
 	  return count;
   }
   public static boolean containKeyWords(String line)
   {
	   String[] keyWords = {"lock", "thread", "mutex"};
	   for (String keyWord : keyWords) 
	   {
		if(line.contains(keyWord))
		{
			//System.out.println(line);
			return true;
		}
			
	   }
	   return false;
   }
}
