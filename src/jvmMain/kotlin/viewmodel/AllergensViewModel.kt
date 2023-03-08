package viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import model.RemoteDataSource
import model.data.Allergen

class AllergensViewModel(private val source: RemoteDataSource) {
    val allergens: Flow<Map<String, Allergen>> = source.apiData.map { it.allergens }
}


