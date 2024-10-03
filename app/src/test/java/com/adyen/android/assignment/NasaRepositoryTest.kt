package com.adyen.android.assignment

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.network.NasaRepository
import com.adyen.android.assignment.network.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.time.LocalDate

@ExperimentalCoroutinesApi
class NasaRepositoryTest {

    private lateinit var nasaRepository: NasaRepository
    private lateinit var planetaryService: PlanetaryService

    @Before
    fun setUp() {
        planetaryService = mockk()
        nasaRepository = NasaRepository(planetaryService)
    }

    @Test
    fun `getPictures success - returns success resource`() = runBlocking {
        // Arrange
        val mockPictures = listOf(
            AstronomyPicture(
                serviceVersion = "Jered",
                title = "Jc",
                explanation = "Kariann",
                date = LocalDate.now(),
                mediaType = "Kristofer",
                hdUrl = null,
                url = null

            )
        )
        val mockResponse = Response.success(mockPictures)
        coEvery { planetaryService.getPictures() } returns mockResponse

        // Act
        val result = nasaRepository.getPictures()

        // Assert
        assertTrue(result is Resource.Success)
        assertEquals(mockPictures, (result as Resource.Success).data)
    }
}