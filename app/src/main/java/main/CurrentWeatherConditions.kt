package main

class CurrentWeatherConditions() {

    var currentHazardRating = 0;

    val GREEN_FLAG = 0
    val YELLOW_FLAG = 1
    val RED_FLAG = 2;
    var temperature = 75
    var windSpeed = 10
    var precipitation = 0

    fun calculateCurrentFlag(): Int {
        var currentFlag = 0;
        calculateTemperatureHazard();
        calculateWindHazard();
        calculatePrecipitationHazard();

        when(currentHazardRating) {
            0 -> currentFlag = GREEN_FLAG
            in 1..2 -> currentFlag = YELLOW_FLAG
            else -> currentFlag = RED_FLAG
        }
        return currentFlag;
    }

    //Adds to the hazard rating depending on which range of numbers the current temp falls into.
    //Basically a large piecewise function.
    private fun calculateTemperatureHazard() {

        when(temperature){
            in 105..120 -> currentHazardRating+=2
            in 95..105 -> currentHazardRating+=1
            in 40..74 -> currentHazardRating+=1
            in 0..39 -> currentHazardRating+=2
            in -30..-1 -> currentHazardRating+=3
        }
    }

    //Adds to the hazard rating depending on which range of numbers the current windspeed falls into
    //Basically a large piecewise function.
    private fun calculateWindHazard() {
        when(windSpeed){
            in 7..16 -> currentHazardRating +=1
            in 17..25 -> currentHazardRating += 2
            in 26..100 -> currentHazardRating += 3
        }
    }

    //Adds to the hazard rating depending on which range of numbers the current chance of precipitation falls into
    //Basically a large piecewise function.
    private fun calculatePrecipitationHazard() {
        when(precipitation){
            in 10..30 -> currentHazardRating += 1
            in 31..60 -> currentHazardRating += 2
            in 61..101 -> currentHazardRating += 3
        }
    }
}
