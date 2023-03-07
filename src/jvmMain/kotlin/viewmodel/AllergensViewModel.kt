package viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import model.RemoteDataSource
import model.data.Allergen

class AllergensViewModel(private val source: RemoteDataSource) {
    val allergens: Flow<List<Allergen>> = source.apiData
        .map { it["Allergens"]?.toStringMap<Allergen>()?.values?.toList() ?: emptyList() }

    fun updateState() {
        source.updateData()
    }

}


