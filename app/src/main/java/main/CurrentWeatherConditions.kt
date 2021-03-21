package main

class CurrentWeatherConditions {

    //Wind speed < 3mph, 0% chance of rain, Cloud coverage < overcast = Great flying conditions

    //best weather condition (green): 75 degrees Fahrenheit, wind speed: less than 10 mph, 0-10% chance of rain
    //good weather condition (yellow): 32 - 75 degrees Fahrenheit or 75 - 104 degrees Fahrenheit, wind speed: 10 - 30 mph, 20 - 50% chance of rain
    //bad weather condition (red): below 32 degrees Fahrenheit, wind speed: over 30 mph, over 50% chance of rain

    var temperature = 75
    var windSpeed = 10
    var chanceOfRain =

            if (temperature = 75 && windSpeed = 10 && chanceOfRain = 0) {
                println("best weather condition")
            } else if (temperature > 75 || temperature < 32) {
                println("good weather condition")
            } else if (temperature < 75 || temperature < 104) {
                println("good weather condition")
            } else {
                println("bad weather condition")
            }

            //if (windSpeed < 10) {
                //println("best weather condition")
            //} else if (windSpeed > 10 || windSpeed < 30) {
                //println("good weather condition")
            //} else {
                //println("bad weather condition")
            //}
}