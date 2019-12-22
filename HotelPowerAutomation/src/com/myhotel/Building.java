package com.myhotel;

import java.util.ArrayList;
import java.util.List;

import com.myhotel.floor.Floor;

public class Building {

	private static Building buildingInstance;

	private List<Floor> floors;

	private Building() {}

	//assuming all floors having same number of main and sub corridors..
	public static Building createFloorsAndInitBuilding(int nFloors, int nMainCorridors, int nSubCorridors)
	{
		buildingInstance = new Building();
		buildingInstance.floors = new ArrayList<Floor>();
		for(int floorCreatorIterator = 0; floorCreatorIterator <nFloors; floorCreatorIterator++)
		{
			buildingInstance.floors.add(new Floor(nMainCorridors, nSubCorridors));
		}
		return buildingInstance;
	}

	public static Building getBuildingInstance()
	{
		return buildingInstance;
	}
	
	public List<Floor> getFloors()
	{
		return floors;
	}
}
