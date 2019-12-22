package com.myhotel.floor;

import java.util.ArrayList;
import java.util.List;

import com.myhotel.electronics.ElectronicItem;
import com.myhotel.electronics.ElectronicItemFactory;
import com.myhotel.electronics.ElectronicItemType;

public class Corridor {

	private CorridorType type;

	private List<ElectronicItem> electronicItems;

	public Corridor(CorridorType type)
	{
		electronicItems = new ArrayList<ElectronicItem>();
		this.type = type;
		electronicItems.add(ElectronicItemFactory.createLightElectronicInstance(type));
		electronicItems.add(ElectronicItemFactory.createACElectronicInstance(type));
	}

	public int getPowerConsumed()
	{
		int totalPower =0;
		for(int i=0;i<electronicItems.size();i++)
		{
			totalPower+= electronicItems.get(i).getConsumedPower();
		}
		return totalPower;
	}

	public List<ElectronicItem> getElectronicItems()
	{
		return electronicItems;
	}

	public CorridorType getCorridorType()
	{
		return this.type;
	}
	/*
	 *returns true - when specified operation is done.[either turn on or off]
	 *return false - when all the electronic items of the given types are on(when turnItOn = true) and off (when turnItOn is false)
	 */
	public Boolean switchOnOrOff(ElectronicItemType itemType, Boolean turnItOn)
	{
		boolean returnStatus = false;
		for(ElectronicItem iteratorOnElectronicItem : electronicItems)
		{
			//find first electronic which is off, then switch it on.
			if(iteratorOnElectronicItem.getElectronicItemType() == itemType)
			{
				if(turnItOn)
				{
					if(iteratorOnElectronicItem.isPoweredOn() == false)
					{
						iteratorOnElectronicItem.switchOn();
						returnStatus = true;
						break;
					}
				}
				else
				{

					if(iteratorOnElectronicItem.isPoweredOn())
					{
						iteratorOnElectronicItem.switchOff();
						returnStatus = true;
						break;
					}
				}
			}
		}
		return returnStatus;
	}

}
