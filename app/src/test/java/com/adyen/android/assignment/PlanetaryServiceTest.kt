package com.adyen.android.assignment

import com.adyen.android.assignment.api.PlanetaryService
import kotlinx.coroutines.test.runTest
import org.junit.Test


class PlanetaryServiceTest {

    /**
     * Integration test -
     * ensures the [generated key](https://api.nasa.gov/) returns results from the api
     */
    @Test
    fun testResponseCode() = runTest {
        val response = PlanetaryService.instance.getPictures()
        println(response.body()?.first()?.url)
        assert(response.isSuccessful)
    }
}
