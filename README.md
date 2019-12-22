# HotelAutomation
 Automatic power control in hotel building based on per floor plan

## Use case:
Hotel Management wants to optimise the power consumption without any disturbances to customers. Sensors are deployed at corridors and on default state of the electronics are fed to the system. On Movement, it should switch on/off the electronic item(s) and maintain a required power consumption at any point of time. 

### Interfaces: 

- [ISensor](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/ISensor.java)(com/myhotel/ISensor.java)

- [PowerControllerSensorInterface](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/powerAutomation/PowerControllerSensorInterface.java)(com/myhotel/powerAutomation/PowerControllerSensorInterface.java)
  - `Interface between powerController and Sensor`
  - ```
    void captureSensorSignal(int floorNumber, CorridorType corridorType, int corridorNumber, boolean movementDetected, int minutesWithNoMomentsDetected);
    ```
- [IPowerController](https://github.com/manosivam/HotelAutomation/blob/master/HotelPowerAutomation/src/com/myhotel/powerAutomation/IPowerController.java)
  - `int adjustPower(Floor floor, CorridorType corridorType, int corridorNumber,CorridorBasedElectronicPowerStatusMap rule);`
  - `void printFloorWiseReport();`

###Important Classes 

-   
