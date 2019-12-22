# HotelAutomation
 Automatic power control in hotel building based on per floor plan

## Use case:
Hotel Management wants to optimise the power consumption without any disturbances to customers. Sensors are deployed at corridors and on default state of the electronics are fed to the system. On Movement, it should switch on/off the electronic item(s) and maintain a required power consumption at any point of time. 

### Entities 
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
 
