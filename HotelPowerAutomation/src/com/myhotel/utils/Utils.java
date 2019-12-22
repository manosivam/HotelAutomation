package com.myhotel.utils;

import com.myhotel.floor.CorridorType;

public class Utils {

	public static String parseSensorInputToReadableFormat(String input)
	{
		StringBuilder output = new StringBuilder("Sensor says: ");
		String[] sArray = input.split(":");
		if(sArray.length >=2)
		{
			if(sArray[0].startsWith("M"))
			{
				output.append("Movement in Floor "+Integer.valueOf(sArray[0].substring(1))+" ");
			}
			else//NM case
			{
				output.append("No movement in Floor "+Integer.valueOf(sArray[0].substring(2))+" ");
			}

			output.append((sArray[1].startsWith("S")?CorridorType.SUB.name() : CorridorType.MAIN.name())+" corridor " +Integer.valueOf(sArray[1].substring(1)));
		}	
		if(sArray.length == 3)
		{
			output.append(" for "+Integer.valueOf(sArray[2])+" minute(s)");
		}
		return output.toString();
	}
	
	
	public static boolean isActiveHoursForPowerController() {
		//isNightTimeSlot returning true always.
		return true;
	}
}
