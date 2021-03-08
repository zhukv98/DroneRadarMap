# Drone Radar Map

---

Design Document

Kevin Zhu

Storm Hamilton

Edward Greenlee 

Lily Powell

Jeanette Kootin-Sanwu

## Introduction
Searching for the best place to play with your drones without any trouble? Drone Radar Map can help you:
- Finding parks and attractions and record them in your search
- Locate restrictions zones
- Monitor the weather at your current position

Use your Android Device to search any parks and attractions near your position where you can play with drones. For example, Summit Park is one of the best places to play with them because of its massive open area. Receive warnings on being under a restriction zone and show the its level. Noftify you when rain, ice/snow, or high winds are moving to your current position.
## Storyboard
![IMG_6443](https://projects.invisionapp.com/prototype/ckm160fd2009u1l011v32ghcr/play)
![IMG_6444](https://user-images.githubusercontent.com/51676111/106401773-e24f9080-63f3-11eb-9cae-33b5df8f1bac.jpeg)
![image](https://user-images.githubusercontent.com/47064092/110392833-936ec980-8037-11eb-8e46-21a8ea95ab49.png)
## Function Requirements
#### Scenario
As a user interested in launching drones, I want to search any nearby parks and attractions that are the best places to launch them without any difficulties so that it won't pose a threat to aircraft that are in the air. When I select a park or attraction, I want to see its zone restriction levels and the current weather there.

#### Dependencies
Parks and Attractions search data are available and accessible.

The device has GPS capabilities and the user has granted location access.

All restriction areas and levels are present on the map.
#### Assumptions
The terrain on the map is flat.

Security sensitive locations are highlighted in different colors to show their restriction levels.

The weather is clear in the user's location.

#### Examples
1.1

#### Given GPS details and weather patterns are available 
#### When I search "Ault Park"
#### Then I should receive

Name: Ault Park

Category: Park

Drone Restriction Level(s): 3-4

Current Weather: Overcast

1.2

#### Given GPS details and weather patterns are available 
#### When I search "Summit Park"
#### Then I should receive

Name: Summit Park

Category: Park

Drone Restriction Level(s): 1

Current Weather: Overcast
## Class Diagram
 ![DroneUML](https://user-images.githubusercontent.com/55035232/106397933-f8525680-63dd-11eb-88f1-f94e0094307a.png)
## Class Diagram Description
- MainActivity: A map with the weather radar to show the current positions of rain, ice/snow, or winds depending on the setting
- MainViewModel: A screen that shows the main overview of the map
- MapView: A screen that shows the overview of the map
- Park: Noun class that represents a park
- SpecificPark: Noun class that represents a specific park
- IParkDAO: Interface to gather the details of the park information
- ISpecificDAO: Interface to gather weather and geolocation data of the specific park 
- OutsideWeatherFeed: Output database of the weather that is outputted
- ParkDatabase: Database of the different parks

## Product Backlog (Located in Projects on GitHub)
 - Current Location and Weather
 - Location Lookup
## Scrum Roles
- DevOps/Product Owner/Scrum Master: Kevin Zhu
- Frontend Developer: Lily Powell, Edward Greenlee
- Integration: Jeanette Kootin-Sanwu, Storm Hamilton

## Weekly Meetings
Thursdays @ 7:00 PM and Sundays @ 6:00 PM through Teams.
