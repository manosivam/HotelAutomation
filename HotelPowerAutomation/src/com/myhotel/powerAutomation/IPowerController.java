package com.myhotel.powerAutomation;

import com.myhotel.criteria.CorridorBasedElectronicPowerStatusMap;
import com.myhotel.floor.CorridorType;
import com.myhotel.floor.Floor;

public interface IPowerController {

	int adjustPower(Floor floor, CorridorType corridorType, int corridorNumber,
			CorridorBasedElectronicPowerStatusMap rule);

	void printFloorWiseReport();
}