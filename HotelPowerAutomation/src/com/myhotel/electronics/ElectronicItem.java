package com.myhotel.electronics;

public class ElectronicItem {

	private PowerUnitCapacity powerUnitCapacityInstance; 
	private ElectronicItemType itemType;
	
	//package access modifier, so that only ElectronicItemFactory can create instance of this.
	ElectronicItem(ElectronicItemType itemType) {
		powerUnitCapacityInstance = PowerUnitCapacity.getInstance();
		this.itemType = itemType;
	}
	
	private boolean isPoweredOn = false;	
	
	public void switchOn()
	{
		isPoweredOn = true;
	}
	
	public void switchOff()
	{
		isPoweredOn = false;
	}
	
	public boolean isPoweredOn()
	{
		return isPoweredOn;
	}
	
	public ElectronicItemType getElectronicItemType()
	{
		return this.itemType;
	}
	
	public int getConsumedPower()
	{
		if(isPoweredOn)
		{
			return powerUnitCapacityInstance.getPowerUnitCapacity(itemType);
		}
		return 0;
	}
	
}
