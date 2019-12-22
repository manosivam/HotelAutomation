package com.myhotel.criteria;

import java.util.EnumMap;

import com.myhotel.electronics.ElectronicItemType;


/*
 * <Electronic item, Integer> Map.
 * Electronic enum denotes electronic like light/Ac,..
 * Integer denotes flag which guides us with action as below
 *  1  denotes perform action on same corridor.
 *  2  denotes perform action on different or same corridor. [Try different corridor, if not possible act on same corridor]
 *  4  ON -> OFF .. If switched On, toggle it to OFF.
 *  8  OFF -> ON .. IF switched off, toggle it to ON.
 *  
 *  16 DEFAULT_ON for electronicItem.
 *  32 DEFAULT OFF for electronicItem;
 */
public class ElectronicPowerSwitchActionMap {

	private EnumMap<ElectronicItemType,Integer> electronicPowerStatus;

	public ElectronicPowerSwitchActionMap()
	{
		electronicPowerStatus = new EnumMap<ElectronicItemType, Integer>(ElectronicItemType.class);
	}

	public void put(ElectronicItemType itemType, int powerStatus)
	{
		electronicPowerStatus.put(itemType, powerStatus);
	}

	public Integer get(ElectronicItemType itemType)
	{
		return electronicPowerStatus.get(itemType);
	}
	public EnumMap<ElectronicItemType,Integer> getElectronicPowerSwitchActionMap()
	{
		return electronicPowerStatus;
	}

}
