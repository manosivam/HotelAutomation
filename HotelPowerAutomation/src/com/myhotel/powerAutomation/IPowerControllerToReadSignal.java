package com.myhotel.powerAutomation;

import com.myhotel.floor.CorridorType;

public interface IPowerControllerToReadSignal {

	void captureSensorSignal(int floorNumber, CorridorType corridorType, int corridorNumber, boolean movementDetected, int minutesWithNoMomentsDetected);
	
}
