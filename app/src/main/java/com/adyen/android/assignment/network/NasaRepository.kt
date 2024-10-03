package com.adyen.android.assignment.network

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture

class NasaRepository(
    private val planetaryService: PlanetaryService
) : BaseRepo {

    suspend fun getPictures(): Resource<List<AstronomyPicture>> {
        return safeApiResult {
            planetaryService.getPictures()
        }
    }
}