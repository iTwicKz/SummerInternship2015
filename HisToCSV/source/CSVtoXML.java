import java.util.Scanner;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Image;


class convertCSV{
	public static void main(String[] args) throws IOException{

		InputOutput gui = new InputOutput();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(550,210);
		gui.setTitle("Historian to Provision Converter");
		gui.setVisible(true);
		gui.setResizable(false);
		//Image image = new ImageIcon(gui.class.getResource("/Double-J-Design-Origami-Colored-Pencil-Green-tag.ico")).getImage();
		//gui.setIconImage(image);

		JButton executeButton = new JButton("Convert");
			gui.add(executeButton);
		executeButton.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e){

			gui.originTagFileName = gui.tf2.getText();
			gui.outputFileName = gui.tf3.getText();
			/*
			if(gui.outputFileName.equals("") && gui.originTagFileName.equals("") && gui.levelSetFileName.equals("")){
				JOptionPane.showMessageDialog(null, "Please fill in all text", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			*/
			//int n = JOptionPane.showConfirmDialog(null,"Choose one", "Choose One", JOptionPane.YES_NO_OPTION);

			//if(n == JOptionPane.YES_OPTION){
				try{
					//String outputFileName = "outputFileCOMPORest.xml";
					File outFile = new File(gui.outputFileName);
					FileWriter fWriter = new FileWriter(outFile);		//output writer setup
					PrintWriter pWriter = new PrintWriter(fWriter);

					//String originTagFileName = "COMPORest.csv";
					File originTag = new File(gui.originTagFileName);		//input CSV from Historian Excel
					Scanner input = new Scanner(originTag);

					//String levelSetFileName = "ProvisionLevelSetMANU.txt";
					//File levelSet = new File(gui.levelSetFileName);
					//Scanner inpInfo = new Scanner(levelSet);

				
					boolean firstLine = true;	//checks for first line of CSV
					int errorCount = 0;			//counts errors
					boolean firstUnit = true;	//formatting condition

					String[] lvlSetData = new String[11];
					//String nothing = inpInfo.nextLine();
					//nothing = inpInfo.nextLine();
					String[] tempLevels = levelSetString(gui.levelSetFileName);
					int i;
					for(i = 0; i < lvlSetData.length - 3; i++){
						/*
						String lvlLine = inpInfo.nextLine();
						int j = 0;
						while(lvlLine.charAt(j) != '='){
							j++;
						} */
						lvlSetData[i] = tempLevels[i];
					}
					lvlSetData[i] = gui.outputFileName;
					lvlSetData[i+1] = gui.originTagFileName;
					lvlSetData[i+2] = gui.levelSetFileName;

					String levelSetFileName = gui.levelSetFileName;

					String checkMessage = "";

					if(levelSetFileName.equals("Util")){
						checkMessage = "\nFor " + lvlSetData[10] + "\n  Provision Version: \"" + lvlSetData[0] + "\"" 
							+ "\n  IncludeHistorianName: \"" + lvlSetData[1] + "\"" 
							+ "\n  Enterprise Name: \"" + lvlSetData[2] + "\" Description: " + lvlSetData[3]
							+ "\n  Site Name: \"" + lvlSetData[4] + "\" Description: " + lvlSetData[5]
							+ "\n\nIs this information correct? (Y/N)";	
					}
					else{
						checkMessage = "\nFor " + lvlSetData[10] + "\n  Provision Version: \"" + lvlSetData[0] + "\"" 
							+ "\n  IncludeHistorianName: \"" + lvlSetData[1] + "\"" 
							+ "\n  Enterprise Name: \"" + lvlSetData[2] + "\" Description: " + lvlSetData[3]
							+ "\n  Site Name: \"" + lvlSetData[4] + "\" Description: " + lvlSetData[5]
							+ "\n  Area Name: \"" + lvlSetData[6] + "\" Description: " + lvlSetData[7]
							+ "\n\nIs this information correct? (Y/N)";
					}

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
						 + "\n" + "\t  <Site Name=\"" + lvlSetData[4] + "\" Description=\"" + lvlSetData[5] + "\">");
						if(!(levelSetFileName.equals("ProvisionLevelSetUtil.txt"))){									//if it is not utilities
						 	pWriter.println("\t\t<Area Name=\"" + lvlSetData[6] + "\" Description=\"" + lvlSetData[7] + "\">");
						}

						while(input.hasNextLine()){

							String wholeLine = input.nextLine();

							if(firstLine && wholeLine.equals("Tagname,Description,DataType") ){	//Checks of correct fields were selected
								firstLine = false;
								continue;
							}
							else if(firstLine){  												//Error Handling
								JOptionPane.showMessageDialog(null, "ERROR: Incorrect Field Input. \nPlease export 'Tagname','Description', and 'DataType' in Excel", "Excel Fields Error", JOptionPane.ERROR_MESSAGE);
								//System.out.println("ERROR: Incorrect Field Input. \nPlease export 'Tagname','Description', and 'DataType' in Excel"); 
								firstLine = false;
								break;
							}

							//checks for new equipment
							if(wholeLine.substring(0,8).equals("ProdUnit")){
								String utilOrNo = "ProcessCell";
								if(levelSetFileName.equals("ProvisionLevelSetUtil.txt")) utilOrNo = "Area";
								if(!firstUnit) pWriter.println("\t\t  </" + utilOrNo + ">");
								pWriter.println("\t\t  <" + utilOrNo + " " + "Name=\"" + wholeLine.substring(9, wholeLine.length() - 2) + "\" Description=\"\">");
								firstUnit = false;
								continue;
							}

							//splits wholeLine string to array
							String[] propVals = wholeLine.split(",");

							if(propVals.length != 3){
								pWriter.println("ERROR OCCURED: More than two commas. Please ensure that there are no commas in the text and only two delimiting");
								errorCount++;
								continue;
							}

							if(propVals[1].contains(".")){
								boolean firstPeriod = true;
								for(int q = 0; q < propVals[1].length(); q++){
									if(propVals[1].charAt(q) == '.'){
										if(firstPeriod){
											firstPeriod = false;
										}
										else propVals[1] = propVals[1].substring(q+1);
									}
								}
							}

							if(propVals[1].equals("")){
								propVals[1] = propVals[0];
							}
							//converts data type from Provisioning to Proficy
							propVals[2] = transType(propVals[2]);

							String serverName = "TEWP09805"; //OPC Server Name

							pWriter.println("\t\t\t<Property Name=\"" + propVals[1] + "\" Description=\"" + propVals[0] + 
											"\" Value=\"\" ValueType=" + propVals[2] + " Unit=\"\" Bind=\"" + serverName  + "." + propVals[0] + 
											"\" BindValueType=\"current\" />" );

						}
						
						if(!(levelSetFileName.equals("ProvisionLevelSetUtil.txt"))){									//if it is not utilities
							pWriter.println("\t\t  </ProcessCell>");
						}
						pWriter.print("\n\t\t</Area>");
						pWriter.print("\n\t  </Site>\n\t</Enterprise>\n  </Equipment>\n</Provision>");

						System.out.println("\nProgram executed");

						if(gui.openThatFile){
							Desktop.getDesktop().open(outFile);
						}
						//System.exit(3);
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
			return"\"Int16\"";			//Could be "Unsigned Single Integer" or "UInt16". Both of these were tried and failed
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

	static String[] levelSetString(String location){
		String[] levelSetInfo = new String[8];
		levelSetInfo[0] = "5.5";
		levelSetInfo[1] = "True";
		levelSetInfo[2] = "ENTERPRISE";
		levelSetInfo[3] = "";
		switch (location){
			case "MANU": 	levelSetInfo[4] = "MANU";
							levelSetInfo[5] = "";
							levelSetInfo[6] = "MANU-Shop";
							levelSetInfo[7] = "Provisioning Tool Imported";
							break;

			case "COMPO": 	levelSetInfo[4] = "COMPO";
							levelSetInfo[5] = "";
							levelSetInfo[6] = "COMPO-Shop";
							levelSetInfo[7] = "Provisioning Tool Imported";
							break;

			case "SUPP": 	levelSetInfo[4] = "SUPP";
							levelSetInfo[5] = "";
							levelSetInfo[6] = "SUPP-Shop";
							levelSetInfo[7] = "Provisioning Tool Imported";
							break;

			case "Util": 	levelSetInfo[4] = "Utilities";
							levelSetInfo[5] = "";
							levelSetInfo[6] = "DeviceName";
							levelSetInfo[7] = "";
							break;
	}
		return levelSetInfo;
	}

}
