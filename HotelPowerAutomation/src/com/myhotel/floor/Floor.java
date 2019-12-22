package com.myhotel.floor;

import java.util.ArrayList;
import java.util.List;

public class Floor {

	public List<Corridor> mainCorridors;
	public List<Corridor> subCorridors;

	public Floor(int numberOfMainCorridors, int numberOfSubCorridors)
	{
		createMainCorridors(numberOfMainCorridors);
		createSubCorridors(numberOfSubCorridors);
	}

	private void createSubCorridors(int numberOfSubCorridors) {
		subCorridors = new ArrayList<Corridor>();
		for(int i=0;i<numberOfSubCorridors;i++)
		{
			subCorridors.add(new Corridor(CorridorType.SUB));
		}
	}

	private void createMainCorridors(int numberOfMainCorridors) {
		mainCorridors = new ArrayList<Corridor>();
		for(int i=0;i<numberOfMainCorridors;i++)
		{
			mainCorridors.add(new Corridor(CorridorType.MAIN));
		}
	}

	public int getPowerConsumed()
	{
		int totalPower = 0;

		for(int mainCorridorIterator =0; mainCorridorIterator < mainCorridors.size();mainCorridorIterator++)
		{
			totalPower+=mainCorridors.get(mainCorridorIterator).getPowerConsumed();
		}

		for(int subCorridorIterator =0; subCorridorIterator < subCorridors.size();subCorridorIterator++)
		{
			totalPower+=subCorridors.get(subCorridorIterator).getPowerConsumed();
		}
		return totalPower;
	}

	public List<Corridor> getCorridors(CorridorType type)
	{
		return type == CorridorType.SUB? subCorridors: mainCorridors;
	}

}
