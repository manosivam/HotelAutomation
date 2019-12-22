package com.myhotel.criteria;

import java.util.EnumMap;

import com.myhotel.floor.CorridorType;

public class CorridorBasedElectronicPowerStatusMap {

	private EnumMap<CorridorType, ElectronicPowerSwitchActionMap> corridorBasedElectornicPowerStatusMap; 
	
	public CorridorBasedElectronicPowerStatusMap() {
		corridorBasedElectornicPowerStatusMap = new EnumMap<CorridorType, ElectronicPowerSwitchActionMap>(CorridorType.class);
	}
	
	public void put(CorridorType corridorType, ElectronicPowerSwitchActionMap electronicPowerStatusMap)
	{
		corridorBasedElectornicPowerStatusMap.put(corridorType, electronicPowerStatusMap);
	}
	
	public ElectronicPowerSwitchActionMap get(CorridorType type)
	{
		return corridorBasedElectornicPowerStatusMap.get(type);
	}
	
	public EnumMap<CorridorType, ElectronicPowerSwitchActionMap> getCorridorBasedElectornicPowerStatusMap()
	{
		return corridorBasedElectornicPowerStatusMap;
	}
	
}
