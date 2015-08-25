import java.util.Scanner;
import java.io.*;

class XMLtoPAS{
	public static void main(String[] args) throws IOException{

		File outFile = new File("outputFileforPAS.xml");
		FileWriter fWriter = new FileWriter(outFile);
		PrintWriter pWriter = new PrintWriter(fWriter);

		File originTag = new File("NDT_TR_FIN.csv");

		Scanner input = new Scanner(originTag);
		boolean firstLine = true;
		int errorCount = 0;
		boolean firstUnit = true;

		while(input.hasNextLine()){

			String wholeLine = input.nextLine();

			if(firstLine && wholeLine.equals("Tagname,Description,DataType") ){	//Checks of correct fields were selected
				firstLine = false;
				continue;
			}
			else if(firstLine){  												//Error Handling
				System.out.println("ERROR: Incorrect Field Input. \nPlease export 'Tagname','Description', and 'DataType' in Excel"); 
				firstLine = false;
				break;
			}

			if(wholeLine.substring(0,8).equals("ProdUnit")){
				if(!firstUnit) pWriter.println("\t\t\t\t\t</ProductionUnit>");
				pWriter.println("\t\t\t\t\t<ProductionUnit " + "Name=\"" + wholeLine.substring(11, wholeLine.length() - 2) + "\" Description=\"\">");
				firstUnit = false;
				continue;
			}

			String[] propVals = wholeLine.split(",");

			if(propVals.length != 3){
				pWriter.println("ERROR: More than two commas. Please ensure that there are no commas in the text and only two delimiting");
				errorCount++;
				continue;
			}

			switch (propVals[2]){
				case "SingleInteger":
					propVals[2] = "\"Int16\"";
					break;
				case "UnsignedSingleInterger":
					propVals[2] = "\"Unsigned Single Integer\"";
					break;
				case "VariableString":
					propVals[2] = "\"String\"";
					break;
				case "DoubleInteger":
					propVals[2] = "\"Int32\"";
					break;
				default:
					propVals[2] = "ERROR: Unknown type of " + propVals[2];
			}
			String serverName = "TEWP09805"; //OPC Server Name


			pWriter.println("\t\t\t\t\t\t<Property PropertyName=\"" + propVals[0] + "\" Value=\"" + propVals[0] + 
							"\" UnitOfMeasure=\"\" Description=\"" + propVals[1] + "\" StorageServer=\"" + serverName +  "\" ScadaTag=\"\" DataType=\"" + "String" + "\"/>" );


		}
//		pWriter.println("\t\t\t\t\t</ProductionUnit>\n\t\t\t\t</Area>\n\t\t\t</Site>\n\t\t</Enterprise>\n\t</Equipment>\n</Provision>");






		pWriter.close();
	}

}
