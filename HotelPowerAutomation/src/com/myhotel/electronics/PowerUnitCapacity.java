package com.myhotel.electronics;

public class PowerUnitCapacity {

	static PowerUnitCapacity instance;
	
	private PowerUnitCapacity() {
		
	}
	
	public static PowerUnitCapacity getInstance()
	{
		if(instance == null)
		{
			instance = new PowerUnitCapacity();
		}
		return instance;
	}
	
	int getPowerUnitCapacity(ElectronicItemType itemType)
	{
		switch(itemType)
		{
		case LIGHT: return PowerConstants.LIGHT;
		case AC: return PowerConstants.AC;
		}
		return 0;
	}
}
