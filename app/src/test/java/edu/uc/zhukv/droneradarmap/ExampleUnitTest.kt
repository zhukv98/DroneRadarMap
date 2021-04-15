package edu.uc.zhukv.droneradarmap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.uc.zhukv.droneradarmap.dto.Airport
import edu.uc.zhukv.droneradarmap.ui.main.MainViewModel

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel
    
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun airportDTO_maintainsState() {
        var airport = Airport("Cincinnati Northern Kentucky Intl", "Cincinnati", "United States", "CVG", "39.048836", "-84.667822")
        assertTrue(airport.name.equals("Cincinnati Northern Kentucky Intl"))
        assertTrue(airport.City.equals("Cincinnati"))
        assertTrue(airport.Country.equals("United States"))
        assertTrue(airport.Iata.equals("CVG"))
        assertTrue(airport.Latitude.equals("39.048836"))
        assertTrue(airport.Longitude.equals("-84.667822"))
    }

    @Test
    fun airportDTO_toStringFormat() {
        var airport = Airport("Cincinnati Northern Kentucky Intl", "Cincinnati", "United States", "CVG", "39.048836", "-84.667822")
        assertTrue(airport.toString().equals("Airport: Cincinnati Northern Kentucky Intl City: Cincinnati Country: United States IATA: CVG  Lat: 39.048836  Lng: -84.667822"))
    }
    @Before
    fun populateAirports() {
        mvm = MainViewModel()
    }
    @Test
    fun airportDTO_isPopulated() {
        givenViewModelIsInitialized()
        whenJSONDataAreReadAndParsed()
        thenTheCollectionSizeShouldBeGreaterThanZero()
    }
    private fun givenViewModelIsInitialized() {

    }
    private fun whenJSONDataAreReadAndParsed() {
        mvm.fetchAirports()
    }
    private fun thenTheCollectionSizeShouldBeGreaterThanZero() {
        var allAirports = ArrayList<Airport>()
        mvm.airports.observeForever{
            allAirports = it
        }
        Thread.sleep(5000)
        assertNotNull(allAirports)
        assertTrue(allAirports.size > 0)
    }
    @Test
    fun airportDTO_containsCVG() {
        givenViewModelIsInitialized()
        whenJSONDataAreReadAndParsed()
        thenResultsShouldContainCVG()
    }

    private fun thenResultsShouldContainCVG() {
        var containsCVG:Boolean = false
        mvm.airports.observeForever {
            it.forEach {
                if (it.Iata.equals("CVG")) {
                    containsCVG = true
                }
            }
            assertTrue(containsCVG)
        }
    }
}