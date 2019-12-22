package com.myhotel.criteria;

import java.util.EnumMap;

import com.myhotel.electronics.ElectronicItemType;
import com.myhotel.floor.CorridorType;
import com.myhotel.powerAutomation.ActionConstants;

public class PerFloorCriteria {

	private static PerFloorCriteria criteriaInstance;
	
	int numberOfMinutesWithNoMovement = 1; // this can be read from config file. 
	
	private EnumMap<MovementStatus, CorridorBasedElectronicPowerStatusMap> subCorridorMovementPlan; 
	private EnumMap<MovementStatus, CorridorBasedElectronicPowerStatusMap> mainCorridorMovementPlan;
	
	private PerFloorCriteria() {
		subCorridorMovementPlan = new EnumMap<MovementStatus, CorridorBasedElectronicPowerStatusMap>(MovementStatus.class);
		mainCorridorMovementPlan = new EnumMap<MovementStatus,CorridorBasedElectronicPowerStatusMap>(MovementStatus.class);
		initPlanFromConfigurationFile();
	}
	
	public static PerFloorCriteria initializeCriteriaAndGetInstance()
	{
		if(criteriaInstance == null)
		{
			criteriaInstance = new PerFloorCriteria();
		}
		return criteriaInstance;
	}
	
	
	void initPlanFromConfigurationFile()
	{
		/*
		 * We can read the plan from a file. 
		 * As per the assignment, the plan is as below.
		 * Movement in Subcorridor --> Control Subcorridor  -> Light - same corridor: OFF to ON  
		 * 													-> AC - different or same corridor: ON to OFF 
		 * 
		 * No movement in subcorridor  --> control subcorridor -> Light - same corridor: ON TO OFF 
		 * 													   -> AC - different or same corridor: OFF TO ON 
		 * 
		 * Movement in MainCorridor -> do nothing. 
		 * 
		 * No Movement in Main corridor -> do Nothing. 
		 * 
		 * ABOVE plan will fulfill the (numberOfMainCorridor * 15 + number of sub corridor * 10 ) power consumption condition.
		 *
		 */
		mainCorridorMovementPlan.put(MovementStatus.MOVEMENT, null);
		mainCorridorMovementPlan.put(MovementStatus.NO_MOVEMENT, null);
		
		/*
		 * If there is a movement in subcorridor, switch on light, switch off AC of the subcorridor.
		 */
		
		CorridorBasedElectronicPowerStatusMap movementCriteria = new CorridorBasedElectronicPowerStatusMap();
		ElectronicPowerSwitchActionMap electronicCriteria = new ElectronicPowerSwitchActionMap();
		electronicCriteria.put(ElectronicItemType.LIGHT, ActionConstants.SAME_CORRRIDOR | ActionConstants.OFF_TO_ON); // same corridor | off to on = 1 | 8 = 9 
		electronicCriteria.put(ElectronicItemType.AC, ActionConstants.DIFFERENT_OR_SAME_CORRIDOR | ActionConstants.ON_TO_OFF); // different or same corridor | on to off = 2 | 4 = 6
		movementCriteria.put(CorridorType.SUB, electronicCriteria);
		
		/*
		 * If there is no movement in subcorridor for x minx, switch off light, switch on AC of the subcorridor.
		 */
		
		CorridorBasedElectronicPowerStatusMap noMovementCriteria = new CorridorBasedElectronicPowerStatusMap();
		ElectronicPowerSwitchActionMap electronicCriteria2 = new ElectronicPowerSwitchActionMap();
		electronicCriteria2.put(ElectronicItemType.LIGHT, ActionConstants.SAME_CORRRIDOR | ActionConstants.ON_TO_OFF);
		electronicCriteria2.put(ElectronicItemType.AC, ActionConstants.DIFFERENT_OR_SAME_CORRIDOR | ActionConstants.OFF_TO_ON);
		noMovementCriteria.put(CorridorType.SUB, electronicCriteria2);
		
		subCorridorMovementPlan.put(MovementStatus.MOVEMENT, movementCriteria);
		subCorridorMovementPlan.put(MovementStatus.NO_MOVEMENT, noMovementCriteria);
		
	}
	
	public EnumMap<MovementStatus, CorridorBasedElectronicPowerStatusMap> getSubCorridorPlan()
	{
		return subCorridorMovementPlan;
	}
	
	public EnumMap<MovementStatus, CorridorBasedElectronicPowerStatusMap> getMainCorridorPlan()
	{
		return mainCorridorMovementPlan;
	}
	
	public int getNumberOfMinutesWithoutMovementsConfig()
	{
		return numberOfMinutesWithNoMovement;
	}
}