package com.myhotel;

import com.myhotel.floor.CorridorType;
import com.myhotel.powerAutomation.IPowerControllerToReadSignal;
import com.myhotel.powerAutomation.PowerController;

public class Sensor implements ISensor {

	private boolean hasMovementDetected = false;
	private int numberOfMinWithNoMovement;
	private int floorNumber, corridorNumber;
	private CorridorType corridorType;
	private IPowerControllerToReadSignal pController;

	public Sensor() {
		pController = (IPowerControllerToReadSignal) PowerController.getInstance();
	}

	@Override
	public void sendSignal(String inputLine) {
		parseSensorInput(inputLine);
		pController.captureSensorSignal(floorNumber, corridorType, corridorNumber, hasMovementDetected, numberOfMinWithNoMovement);
	}

	private void parseSensorInput(String input)
	{
		String[] sArray = input.split(":");
		if(sArray.length >=2)
		{
			if(sArray[0].startsWith("M"))
			{
				hasMovementDetected = true;
				floorNumber = Integer.valueOf(sArray[0].substring(1));
			}
			else//NM case
			{
				hasMovementDetected = false;
				floorNumber = Integer.valueOf(sArray[0].substring(2));
			}

			corridorType = sArray[1].startsWith("S")?CorridorType.SUB : CorridorType.MAIN;
			corridorNumber = Integer.valueOf(sArray[1].substring(1));
		}
		if(sArray.length == 3)
		{
			numberOfMinWithNoMovement = Integer.valueOf(sArray[2]);
		}
	}
}
