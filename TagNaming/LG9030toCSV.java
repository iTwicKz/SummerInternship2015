import java.util.Scanner;
import java.io.*;

class LGtoCSV{
	public static void main(String[] args) throws IOException{


		String fileName = "FILENAME";
		File outFile = new File("output" + fileName + ".CSV");
		FileWriter fWriter = new FileWriter(outFile);
		PrintWriter pWriter = new PrintWriter(fWriter);

		File originTag = new File(fileName + ".txt");

		Scanner input = new Scanner(originTag);

		boolean start = false;

		pWriter.println("Tagname,Address,Description");

		while(input.hasNextLine()){

			String line = input.nextLine();
			if(line.contains("==========  INPUT  (%I)  GLOBAL CROSS REFERENCES")) start = true;

			if(!start) continue;
			if(line.equals("")) continue;

			if(line.charAt(0) == '%'){
				String address = "";
				address = line.substring(0,7);

				String tagName;
				if(line.substring(18,20).equals("  ")) tagName = "No name prvded";
				else{
					int i = 18;
					for(; i < line.length(); i++){
						if(line.charAt(i) == ' ') break;
					}
					tagName = line.substring(18, i);
				}

				String description = line.substring(27);

				pWriter.println(tagName + "," + address + "," + description);

			}

		}
		pWriter.close();
	}

}
