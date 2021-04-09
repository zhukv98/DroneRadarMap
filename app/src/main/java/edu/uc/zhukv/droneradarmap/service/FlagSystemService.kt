package edu.uc.zhukv.droneradarmap.service

class FlagSystemService() {

    val red_flag = 0
    val yellow_flag = 1
    val green_flag = 2
    val temperature = 75
    val windSpeed = 10
    val precipitation = 0

    fun calculateCurrentFlag(): Int {
        if (temperature == 75 && windSpeed == 10 && precipitation == 0 ) {
            return green_flag
        } else if(temperature < 75 && windSpeed < 10 && precipitation > 0) {
            return yellow_flag
        } else {
            return red_flag
        }
    }
}
