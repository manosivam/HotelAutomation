# HotelAutomation
 Automatic power control in hotel building based on per floor plan

## Use case:
Hotel Management wants to optimise the power consumption without any disturbances to customers. Sensors are deployed at corridors and on default state of the electronics are fed to the system. On Movement, it should switch on/off the electronic item(s) and maintain a required power consumption at any point of time. 

#### Entities 
- [Building](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/Building.java) Singleton class
- [Floor](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/floor/Floor.java)
- [Corridor](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/floor/Corridor.java) 
  - [CorridorType](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/floor/CorridorType.java) enum for corridor types
- [ElectronicItem](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/electronics/ElectronicItem.java) holds properties and behaviour of an electronicItem
  - [ElectronicItemType](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/electronics/ElectronicItemType.java) enum for Electronic Items
- [Sensor](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/Sensor.java)
- [PowerController](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/powerAutomation/PowerController.java) Singleton class for power automation.

###### Helper classes
- [Logger](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/utils/Logger.java) Singleton class
- [Utils](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/utils/Utils.java) for utility functions

### Interfaces: 

- [ISensor](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/ISensor.java)(com/myhotel/ISensor.java)

- [PowerControllerSensorInterface](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/powerAutomation/PowerControllerSensorInterface.java)(com/myhotel/powerAutomation/PowerControllerSensorInterface.java)
  - `Interface between powerController and Sensor`
  - ```
    void captureSensorSignal(int floorNumber, CorridorType corridorType, int corridorNumber, boolean movementDetected, int minutesWithNoMomentsDetected);
    ```
- [IPowerController](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/powerAutomation/IPowerController.java)
  - ```int adjustPower(Floor floor, CorridorType corridorType, int corridorNumber,CorridorBasedElectronicPowerStatusMap rule);```
  - `void printFloorWiseReport();`
 

### Implementation details 

```
- Idea behind Criteria: If there is a movement in sub/main corridor, there can actions defined to take in sub or(and) main corridor based on the electronicItem whether to switch it off or switch it on in the same corridor or different(same corridor if there is no different corridors to take action on) corridor. 
- Based on the movement, there is a per floor criteria plan for subcorridor and main corridor
- If there is a movement on subcorridor, the plan can be to take action on electronic either on main/sub corridor. 
- If the criteria is to take action on sub corridor, there can be actions based on electronic items- like switch on to off, switch off to on

```
**Please refer the below classes for more details.** 
_they are ordered in sequence to understand the flow_

- [PerFloorCriteria](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/criteria/PerFloorCriteria.java)
   ```
    private EnumMap<MovementStatus, CorridorBasedElectronicPowerStatusMap> subCorridorMovementPlan; 
	private EnumMap<MovementStatus, CorridorBasedElectronicPowerStatusMap> mainCorridorMovementPlan;
    ```
  - The plan can be loaded from the file as well. But I have initialized the given plan into system. 
  
- [ElectronicPowerSwitchActionMap](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/criteria/ElectronicPowerSwitchActionMap.java)
   ```
    private EnumMap<ElectronicItemType,Integer> electronicPowerStatus;
    ```
  - ElectronicItemType is an enum of electronics items, while Integer contains flags based on which action are defined as shown below. 
     ```
      1  denotes perform action on same corridor.
      2  denotes perform action on different or same corridor. [Try different corridor, if not possible act on same corridor]
      4  ON -> OFF .. If switched On, toggle it to OFF.
      8  OFF -> ON .. IF switched off, toggle it to ON.

      16 DEFAULT_ON for electronicItem.
      32 DEFAULT OFF for electronicItem;
      ```
- [CorridorBasedElectronicPowerStatusMap](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/criteria/CorridorBasedElectronicPowerStatusMap.java)
  ```
  private EnumMap<CorridorType, ElectronicPowerSwitchActionMap> corridorBasedElectornicPowerStatusMap; 
  ```
  - It contains corridorType based ElectronicPowerSwitchActionMap enum map.
  
 
### Automation and mock data classes for unit testing. 



### sample output. 



