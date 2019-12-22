package com.myhotel.electronics;

import com.myhotel.criteria.CorridorBasedElectronicPowerStatusMap;
import com.myhotel.criteria.ElectronicPowerSwitchActionMap;
import com.myhotel.floor.CorridorType;
import com.myhotel.powerAutomation.ActionConstants;

public class ElectronicItemFactory {

	static CorridorBasedElectronicPowerStatusMap defaultElectronicState;

	static
	{
		//Assuming the program always run in night time slot.
		//Default rule are fed here inside a static initializer. This can also be initialized by reading from config file.
		defaultElectronicState = new CorridorBasedElectronicPowerStatusMap();
		// FOR main corridor.
		ElectronicPowerSwitchActionMap electronicPowerStatus = new ElectronicPowerSwitchActionMap();
		electronicPowerStatus.put(ElectronicItemType.LIGHT, ActionConstants.DEFAULT_ON);
		electronicPowerStatus.put(ElectronicItemType.AC, ActionConstants.DEFAULT_ON);
		defaultElectronicState.put(CorridorType.MAIN, electronicPowerStatus);

		electronicPowerStatus = new ElectronicPowerSwitchActionMap();
		electronicPowerStatus.put(ElectronicItemType.LIGHT, ActionConstants.DEFAULT_OFF);
		electronicPowerStatus.put(ElectronicItemType.AC, ActionConstants.DEFAULT_ON);
		defaultElectronicState.put(CorridorType.SUB, electronicPowerStatus);
	}

	public static ElectronicItem createLightElectronicInstance(CorridorType corridorType)
	{
		ElectronicItem lightInstance =  new ElectronicItem(ElectronicItemType.LIGHT);

		Integer defaultState = getDefaultStateOfElectronic(ElectronicItemType.LIGHT,corridorType);
		if(defaultState == ActionConstants.DEFAULT_ON)
		{
			lightInstance.switchOn();
		}
		else
		{
			lightInstance.switchOff();
		}
		return lightInstance;
	}

	private static Integer getDefaultStateOfElectronic(ElectronicItemType itemType, CorridorType corridorType)
	{
		ElectronicPowerSwitchActionMap electronicPowerStatus = defaultElectronicState.get(corridorType);
		return electronicPowerStatus.get(itemType);
	}

	public static ElectronicItem createACElectronicInstance(CorridorType corridorType)
	{
		ElectronicItem acInstance = new ElectronicItem(ElectronicItemType.AC);

		Integer defaultState = getDefaultStateOfElectronic(ElectronicItemType.AC,corridorType);
		if(defaultState == ActionConstants.DEFAULT_ON)
		{
			acInstance.switchOn();
		}
		else
		{
			acInstance.switchOff();
		}

		return acInstance;
	}


}
