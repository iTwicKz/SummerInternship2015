import java.util.Scanner;
import java.io.*;

class Rename{
	public static void main(String[] args) throws IOException{

		File outFile = new File("output.txt");
		FileWriter fWriter = new FileWriter(outFile);
		PrintWriter pWriter = new PrintWriter(fWriter);

		File originTag = new File("originalTags.txt");

		Scanner input = new Scanner(originTag);

		while(input.hasNextLine()){
			String tag = input.nextLine();
	        
	        tag = tag.replaceAll(" ", "_").toUpperCase();

			String building = "";
			String machine = "";
			String number = "";
			String tagLine = "";

			if(tag.charAt(0) == 'T'){
				tag = tag.substring(10);
				//System.out.println(tag);
			}

			
			

			if(tag.charAt(0) <=57 && tag.charAt(0) >= 48){
				number = tag.substring(0,2);
				tag = tag.substring(3);
			}
	        
	        String buildTemp = tag.substring(0,3);
			if(buildTemp.equals("LOCATIONC")){
				building = "LOCATIONC";
	            tag = tag.substring(3);
			}
			else{
				building = "LOCATIONM";
			}
	 //System.out.println(tag);
			int store = 0;
			for(int i = 0; i < tag.length(); i++){
				if(tag.charAt(i) == '.'){
	                //System.out.println(i);
					store = i;
					break;
				}
			}

	        
			machine = tag.substring(0,store);
	        if(machine.contains("_PLC"))
	            machine = machine.substring(0,machine.length()-4);

			tag = tag.substring(store+1);
			 store = 0;
			for(int i = 0; i < tag.length(); i++){
				if(tag.charAt(i) == '.'){
					store = i;
					tag = tag.substring(i+1);
					break;
				}
			}
		    //pWriter.println(building);
		    //pWriter.println(machine);
	       //pWriter.println(tag);
	    //System.out.println(tag);
	       	if(!machine.equals(""))
	       		if(machine.charAt(0) == '_') machine = machine.substring(1);

	       	if(!tag.equals(""))
	       		if(tag.charAt(0) == '_') tag = tag.substring(1);
	       	
			String fullTag = "";
			if(machine.equals(""))
				fullTag = building + "_" + tag;
			else if(number.equals(""))
				fullTag = building + "_" + machine + "_" + tag;
			//else if (buildin.equals("LOCATIONC")) fullTag = building +  "_" + tag;
			else fullTag = building + "_" + machine + "_" + number + "_" + tag;

			pWriter.println(fullTag);


		}
		pWriter.close();
	}

}
