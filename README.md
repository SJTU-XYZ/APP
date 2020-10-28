# APP Structure

## assets

- final.xls: all data

- typical.xls: typical day data

## java

### bluesample

#### adapter

The data showing of bluetooth device in the device list

#### operation

the operation of bluetooth device

- CharacteristicListFragment: the characteristic of bluetooth device
- ServiceListFragment: service list
- CharacteristicOperationFragment: 
  - read and write operation
  - add, delete and setting of each appliance
  - emulate by hour
  - show the ON-OFF state of each appliance
  - show the bill and net photovoltaic power generation when a day is over
- OperationActivity: manage the fragments above

#### control

- Appliance: the data of appliance
- ApplianceManager: manage all the appliances and emulate
- MyAdapter: The data showing of appliance in the appliance list



#### javaClass

- Chart & ReadExcel: read the data from excel files and draw the charts of these data
- Point: the data of each point in any chart

#### ui

##### slideshow

- SlideshowFragment: the operation of scanning  and connecting bluetooth device
- CreateAddAppDialog: pop-up window when creating appliance
- SettingAppDialog: pop-up window when setting appliance
- EmulateFinishDialog: pop-up window after 24 hours in emulating

##### home

chats of data in excel

#### MianActivity

play the music and navigate

## res

### layout

all layout files

### raw

Look on down from the bridge.mp3

## Dependencies

- FastBleLib & bluesample: bluetooth communication

  ```
  https://github.com/Jasonchenlijian/FastBle
  ```

- MPChartLib: draw chart
- jxl: read the excel files