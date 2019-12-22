package com.myhotel.powerAutomation;

import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import com.myhotel.Building;
import com.myhotel.criteria.CorridorBasedElectronicPowerStatusMap;
import com.myhotel.criteria.ElectronicPowerSwitchActionMap;
import com.myhotel.criteria.MovementStatus;
import com.myhotel.criteria.PerFloorCriteria;
import com.myhotel.electronics.ElectronicItem;
import com.myhotel.electronics.ElectronicItemType;
import com.myhotel.floor.Corridor;
import com.myhotel.floor.CorridorType;
import com.myhotel.floor.Floor;
import com.myhotel.utils.Logger;
import com.myhotel.utils.Utils;

public class PowerController implements IPowerController, PowerControllerSensorInterface {

	private Building buildingInstance;
	private static IPowerController powerControllerInstance;
	private PerFloorCriteria criteria;
	private Logger logger;

	private PowerController() {
		buildingInstance = Building.getBuildingInstance();
		this.criteria = PerFloorCriteria.initializeCriteriaAndGetInstance();
		logger = Logger.getLoggerInstance();
	}

	public static IPowerController getInstance() {
		if(powerControllerInstance == null)
		{
			powerControllerInstance = new PowerController();
		}
		return powerControllerInstance;
	}

	// This captures the signal from sensor. 
	// Adjust power based on the criteria rule.
	@Override
	public void captureSensorSignal(int floorNumber, CorridorType corridorType, int corridorNumber, boolean hasMovementDetected, int minutesWithNoMomentsDetected)
	{
		
		if(floorNumber < 1 || floorNumber> buildingInstance.getFloors().size())
		{
			throw new IllegalArgumentException("Invalid floor number.."+ floorNumber);
		}
		handleCorridorMovements(buildingInstance.getFloors().get(floorNumber-1),corridorType, corridorNumber, hasMovementDetected, minutesWithNoMomentsDetected);
	}

	//This checks for the active hours for power controller and based on movement detected, it will retrive the criteria plan
	private void handleCorridorMovements(Floor floor, CorridorType corridorType, int corridorNumber, boolean hasMovementDetected,
			int minutesWithNoMovements) {

		int availableCorridors = (corridorType == CorridorType.SUB ? floor.subCorridors.size(): floor.mainCorridors.size());

		if(corridorNumber < 1 || corridorNumber> availableCorridors)
		{
			throw new IllegalArgumentException("Invalid corridor number ["+corridorNumber+"] in "+corridorType.name()+" corridor");
		}
		
		if(Utils.isActiveHoursForPowerController())//if isNightTimeSlot() else do nothing.
		{
			EnumMap<MovementStatus, CorridorBasedElectronicPowerStatusMap> movementBasedRule =
					(corridorType == CorridorType.SUB)? this.criteria.getSubCorridorPlan(): this.criteria.getMainCorridorPlan();

			if(hasMovementDetected)
			{
				CorridorBasedElectronicPowerStatusMap rule = movementBasedRule.get(MovementStatus.MOVEMENT);
				if(rule!=null)
				{
					adjustPower(floor, corridorType, corridorNumber, rule);
				}
				//else. do nothing.
			}
			else
			{
				if(minutesWithNoMovements >= this.criteria.getNumberOfMinutesWithoutMovementsConfig())
				{
					CorridorBasedElectronicPowerStatusMap rule = movementBasedRule.get(MovementStatus.NO_MOVEMENT);
					if(rule!=null)
					{
						adjustPower(floor, corridorType, corridorNumber, rule);
					}
					//else do nothing.
				}
				//else do nothing.
			}

		}
	}

	//This adjusts the power based on the Corridor based plan
	// returns power consumption after the power adjustment. 
	@Override
	public int adjustPower(Floor floor, CorridorType corridorType, int corridorNumber, CorridorBasedElectronicPowerStatusMap rule) {

		//List<Corridor> movementDetectedCorridor = (corridorType == CorridorType.SUB)? floor.subCorridors : floor.mainCorridors;
		EnumMap<CorridorType, ElectronicPowerSwitchActionMap> corridorBasedElectornicPowerStatusMap = rule.getCorridorBasedElectornicPowerStatusMap();

		for(Entry<CorridorType, ElectronicPowerSwitchActionMap> entry: corridorBasedElectornicPowerStatusMap.entrySet())
		{
			CorridorType corridorTypeToActOnAsPerRule =  entry.getKey();
			ElectronicPowerSwitchActionMap electronicPowerSwitchActionMap = entry.getValue();
			EnumMap<ElectronicItemType,Integer> electronicPowerSwitchActionMapRaw;
			List<Corridor> corridorsToControl = floor.getCorridors(corridorTypeToActOnAsPerRule);
			if(electronicPowerSwitchActionMap!=null)
			{
				electronicPowerSwitchActionMapRaw = electronicPowerSwitchActionMap.getElectronicPowerSwitchActionMap();
				for(Entry<ElectronicItemType, Integer> powerSwitchRuleEntry: electronicPowerSwitchActionMapRaw.entrySet())
				{
					ElectronicItemType electronicTypeToActOn = powerSwitchRuleEntry.getKey();
					if(electronicTypeToActOn!=null)
					{
						Integer actionFlag = powerSwitchRuleEntry.getValue();
						Boolean status = controlElectronic(corridorsToControl, corridorNumber, actionFlag, electronicTypeToActOn);
						//if status is null -> Invalid action flag.
						// status is true -> switched off or switched on as requested.
						// status is false ->not able to switch on(off) as it is already switched off(on)
					}
				}
			}
		}
		return floor.getPowerConsumed();
	}

	//Based on the ActionFlag, this handles the electronic both power on and power off generically. 
	private Boolean controlElectronic(List<Corridor> corridorsToControl, int corridorNumber, Integer actionFlag, ElectronicItemType electronicTypeToActOn) {
		Boolean turnItOn = null, status = null;
		if((actionFlag & ActionConstants.SAME_CORRRIDOR) == ActionConstants.SAME_CORRRIDOR)
		{
			if((actionFlag & ActionConstants.OFF_TO_ON) == ActionConstants.OFF_TO_ON)
			{
				turnItOn = true;
			}
			else if((actionFlag & ActionConstants.ON_TO_OFF) == ActionConstants.ON_TO_OFF)
			{
				turnItOn = false;
			}

			status= corridorsToControl.get(corridorNumber-1).switchOnOrOff(electronicTypeToActOn,turnItOn);
		}
		if((actionFlag & ActionConstants.DIFFERENT_OR_SAME_CORRIDOR) == ActionConstants.DIFFERENT_OR_SAME_CORRIDOR)
		{
			if((actionFlag & ActionConstants.OFF_TO_ON) == ActionConstants.OFF_TO_ON)
			{
				turnItOn = true;
			}
			else if((actionFlag & ActionConstants.ON_TO_OFF) == ActionConstants.ON_TO_OFF)
			{
				turnItOn = false;
			}
			for(int i =0; i< corridorsToControl.size() ;i++)
			{
				if(i== (corridorNumber-1))
				{
					//same corridor. skip for now. First we will try to switch off different corridor.
					continue;
				}
				if(corridorsToControl.get(i).switchOnOrOff(electronicTypeToActOn, turnItOn))
				{
					return true;
				}
			}
			//falling back to same corridor.
			status= corridorsToControl.get(corridorNumber-1).switchOnOrOff(electronicTypeToActOn,turnItOn);
		}
		return status;
	}

	public void printFloorWiseReport()
	{
		List<Floor> floors = buildingInstance.getFloors();

		for(int floorCursor = 0;floorCursor< floors.size();floorCursor++)
		{
			logger.logLine("--------Floor "+ (floorCursor+1)+"----------");
			List<Corridor> mainCorridors = floors.get(floorCursor).getCorridors(CorridorType.MAIN);
			List<Corridor> subCorridors = floors.get(floorCursor).getCorridors(CorridorType.SUB);
			for(int mainCorridorCursor = 0; mainCorridorCursor<mainCorridors.size() ; mainCorridorCursor++)
			{
				logger.log("Main Corridor "+ (mainCorridorCursor+1) +" ");
				List<ElectronicItem> electronicItemsInMainCorridor = mainCorridors.get(mainCorridorCursor).getElectronicItems();
				for(int electronicsItemsInMainCorridorCursor =0 ; electronicsItemsInMainCorridorCursor <electronicItemsInMainCorridor.size();electronicsItemsInMainCorridorCursor++)
				{
					logger.log(" "+electronicItemsInMainCorridor.get(electronicsItemsInMainCorridorCursor).getElectronicItemType().name() + " : "+(electronicItemsInMainCorridor.get(electronicsItemsInMainCorridorCursor).isPoweredOn()?"ON":"OFF"));
				}

				logger.logLine("");//new line.
			}

			for(int subCorridorCursor = 0; subCorridorCursor<subCorridors.size() ; subCorridorCursor++)
			{
				logger.log("Sub Corridor "+ (subCorridorCursor+1) +" ");
				List<ElectronicItem> electronicsInSubCorridor = subCorridors.get(subCorridorCursor).getElectronicItems();
				for(int electronicsItemsInsubCorridorCursor=0 ; electronicsItemsInsubCorridorCursor <electronicsInSubCorridor.size();electronicsItemsInsubCorridorCursor++)
				{
					logger.log(" "+electronicsInSubCorridor.get(electronicsItemsInsubCorridorCursor).getElectronicItemType().name() + " : "+(electronicsInSubCorridor.get(electronicsItemsInsubCorridorCursor).isPoweredOn()?"ON":"OFF"));
				}

				logger.logLine("");//new line.
			}
			logger.logLine("");//new line.
		}
	}



}
