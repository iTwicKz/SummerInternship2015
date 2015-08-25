import java.util.Scanner;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class OpenFile{

	JFileChooser fileChooser = new JFileChooser();
	String sb = "";

	public void PickMe() throws Exception{

		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){

			File file = fileChooser.getSelectedFile();
			sb = file.getName();
		}
		else{
			sb = "No file was selected";
		}
	}
}

class convertCSV{
	public static void main(String[] args) throws IOException{

		InputOutput gui = new InputOutput();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(500,250);
		gui.setTitle("Writer");
		gui.setVisible(true);
		gui.setResizable(false);

		JButton executeButton = new JButton("Click here");

		executeButton.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e){
			/*
			if(gui.outputFileName.equals("") && gui.originTagFileName.equals("") && gui.levelSetFileName.equals("")){
				JOptionPane.showMessageDialog(null, "Please fill in all text", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			*/
			//int n = JOptionPane.showConfirmDialog(null,"Choose one", "Choose One", JOptionPane.YES_NO_OPTION);

			//if(n == JOptionPane.YES_OPTION){
				try{
					File outFile = new File(gui.outputFileName);
					FileWriter fWriter = new FileWriter(outFile);		//output writer setup
					PrintWriter pWriter = new PrintWriter(fWriter);

					//String originTagFileName = "FCORest.csv";
					File originTag = new File(gui.originTagFileName);		//input CSV from Historian Excel
					Scanner input = new Scanner(originTag);

					File levelSet = new File(gui.levelSetFileName);
					Scanner inpInfo = new Scanner(levelSet);

				
					boolean firstLine = true;	//checks for first line of CSV
					int errorCount = 0;			//counts errors
					boolean firstUnit = true;	//formatting condition

					String[] lvlSetData = new String[11];
					//String nothing = inpInfo.nextLine();
					//nothing = inpInfo.nextLine();

					int i;
					for(i = 0; i < lvlSetData.length - 3; i++){
						String lvlLine = inpInfo.nextLine();
						int j = 0;
						while(lvlLine.charAt(j) != '='){
							j++;
						}
						lvlSetData[i] = lvlLine.substring(j+2);
					}
					lvlSetData[i] = gui.outputFileName;
					lvlSetData[i+1] = gui.originTagFileName;
					lvlSetData[i+2] = gui.levelSetFileName;

					String checkMessage = "\nFrom the level set file " + lvlSetData[10] + "\n  Provision Version: \"" + lvlSetData[0] + "\"" 
										+ "\n  IncludeHistorianName: \"" + lvlSetData[1] + "\"" 
										+ "\n  Enterprise Name: \"" + lvlSetData[2] + "\" Description: " + lvlSetData[3]
										+ "\n  Site Name: \"" + lvlSetData[4] + "\" Description: " + lvlSetData[5]
										+ "\n  Area Name: \"" + lvlSetData[6] + "\" Description: " + lvlSetData[7]
										+ "\n\nIs this information correct? (Y/N)";

					int n = JOptionPane.showConfirmDialog(null, checkMessage, "Choose One", JOptionPane.YES_NO_OPTION);
					/*System.out.println("\nFrom the level set file " + lvlSet[10]);
					System.out.println("  Provision Version: \"" + lvlSet[0] + "\"");
					System.out.println("  IncludeHistorianName: \"" + lvlSet[1] + "\"");
					System.out.println("  Enterprise Name: \"" + lvlSet[2] + "\" Description: " + lvlSet[3]);
					System.out.println("  Site Name: \"" + lvlSet[4] + "\" Description: " + lvlSet[5]);
					System.out.println("  Area Name: \"" + lvlSet[6] + "\" Description: " + lvlSet[7]);

					System.out.println("\nInput: " + lvlSet[9]);
					System.out.println("\nOutput: " + lvlSet[8]);

					Scanner sc = new Scanner(System.in);
					System.out.println("\nIs this information correct? (Y/N)");
*/
					
					if(n == JOptionPane.YES_OPTION){

						pWriter.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<Provision Version=\"" + lvlSetData[0] + "\" IncludeHistorianName=\"" + lvlSetData[1] + "\">"
						 + "\n" + "\t<Equipment>" //No Description
						 + "\n" + "\t<Enterprise Name=\"" + lvlSetData[2] + "\" Description=\"" + lvlSetData[3] + "\">"
						 + "\n" + "\t  <Site Name=\"" + lvlSetData[4] + "\" Description=\"" + lvlSetData[5] + "\">"
						 + "\n" + "\t\t<Area Name=\"" + lvlSetData[6] + "\" Description=\"" + lvlSetData[7] + "\">");
			/*
							pWriter.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<Provision Version=\"5.5\" IncludeHistorianName=\"False\">"
											 + "\n" + "\t<Equipment>" //No Description
											 + "\n" + "\t\t<Enterprise Name=\"Enterprise\">"
											 + "\n" + "\t\t\t<Site Name=\"FCO\">"
											 + "\n" + "\t\t\t\t<Area Name=\"FCO-Shop\">");
			*/

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

								//checks for new equipment
								if(wholeLine.substring(0,8).equals("ProdUnit")){
									if(!firstUnit) pWriter.println("\t\t  </ProcessCell>");
									pWriter.println("\t\t  <ProcessCell " + "Name=\"" + wholeLine.substring(9, wholeLine.length() - 2) + "\" Description=\"\">");
									firstUnit = false;
									continue;
								}

								//splits wholeLine string to array
								String[] propVals = wholeLine.split(",");

								if(propVals.length != 3){
									pWriter.println("ERROR: More than two commas. Please ensure that there are no commas in the text and only two delimiting");
									errorCount++;
									continue;
								}

								//converts data type from Provisioning to Proficy
								propVals[2] = transType(propVals[2]);

								String serverName = "TEWP09805"; //OPC Server Name

								pWriter.println("\t\t\t<Property Name=\"" + propVals[0] + "\" Description=\"" + propVals[1] + 
												"\" Value=\"\" ValueType=" + propVals[2] + " Unit=\"\" Bind=\"" + serverName  + "." + propVals[0] + 
												"\" BindValueType=\"current\" />" );

							}
							pWriter.println("\t\t  </ProcessCell>\n\t\t</Area>\n\t  </Site>\n\t</Enterprise>\n  </Equipment>\n</Provision>");
							System.out.println("\nProgram executed");

							Desktop.getDesktop().open(outFile);
							System.exit(0);
					}
					else{
						System.out.println("Program was not executed");
					}
					
					pWriter.close();
				}
				catch(IOException exc){
					exc.printStackTrace();
				}
			//}

		}
	});

gui.add(executeButton);

	

		

	}
	

	static boolean beginFormat(String[] lvlSet){
		System.out.println("\nFrom the level set file " + lvlSet[10]);
		System.out.println("  Provision Version: \"" + lvlSet[0] + "\"");
		System.out.println("  IncludeHistorianName: \"" + lvlSet[1] + "\"");
		System.out.println("  Enterprise Name: \"" + lvlSet[2] + "\" Description: " + lvlSet[3]);
		System.out.println("  Site Name: \"" + lvlSet[4] + "\" Description: " + lvlSet[5]);
		System.out.println("  Area Name: \"" + lvlSet[6] + "\" Description: " + lvlSet[7]);

		System.out.println("\nInput: " + lvlSet[9]);
		System.out.println("\nOutput: " + lvlSet[8]);

		Scanner sc = new Scanner(System.in);
		System.out.println("\nIs this information correct? (Y/N)");
		if(sc.nextLine().charAt(0) == 'Y'){
			return true;
		}
		return false;

	}

	static String transType(String origDataType){
		switch (origDataType){
		case "SingleInteger":
			return"\"Int16\"";
		case "UnsignedSingleInteger":
			return"\"Unsigned Single Integer\"";
		case "VariableString":
			return"\"String\"";
		case "DoubleInteger":
			return"\"Int32\"";
		case "SingleFloat":
			return"\"Single\"";
		case "Scaled":
			return"\"Single\"";
		case "Boolean":
			return"\"Boolean\"";
		default:
			return"ERROR: Unknown type of " + origDataType;
		}
	}

}
