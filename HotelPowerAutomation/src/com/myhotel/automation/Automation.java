package com.myhotel.automation;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import com.myhotel.Building;
import com.myhotel.ISensor;
import com.myhotel.Sensor;
import com.myhotel.powerAutomation.IPowerController;
import com.myhotel.powerAutomation.PowerController;
import com.myhotel.utils.Logger;
import com.myhotel.utils.Utils;

public class Automation {

	private Logger logger;
	private IPowerController pController;
	private ISensor sensor;
	private Scanner scanner;

	Automation()
	{
		logger = Logger.getLoggerInstance();
	}

	//Once building is initialized, init pController and sensor.
	void init(int numberOfFloors, int numberOfMainCorridors,int subCorridorsNumber)
	{
		Building.createFloorsAndInitBuilding(numberOfFloors, numberOfMainCorridors, subCorridorsNumber);
		pController = PowerController.getInstance();
		sensor = new Sensor();
	}

	public static void main(String[] args) throws FileNotFoundException {
		Automation automation = new Automation();
		automation.readInputFromStubFileAndAutomate();
	}

	public void readInputFromStubFileAndAutomate() throws FileNotFoundException
	{

		URL url = getClass().getResource("MockDataFile");

		File file = new File(url.getPath());
		scanner = new Scanner(file);

		int numberOfFloors =  Integer.valueOf(scanner.nextLine());
		logger.logLine("number of floors: "+ numberOfFloors);

		int numberOfMainCorridors =  Integer.valueOf(scanner.nextLine());
		logger.logLine("Main corridors per floor: "+ numberOfMainCorridors);

		int numberOfSubCorridors =  Integer.valueOf(scanner.nextLine());
		logger.logLine("Sub corridors per floor: "+ numberOfSubCorridors);

		
		init(numberOfFloors,numberOfMainCorridors, numberOfSubCorridors);

		logger.logLine("\nDefault state (when the program is first run)");
		pController.printFloorWiseReport();

		while(scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if(inputLine!=null && (inputLine.startsWith("#") || inputLine.trim().length() ==0))
				continue;//skip the comments.
			sensor.sendSignal(inputLine);
			
			logger.logLine(Utils.parseSensorInputToReadableFormat(inputLine));
			
			pController.printFloorWiseReport();
		}
	}

}
