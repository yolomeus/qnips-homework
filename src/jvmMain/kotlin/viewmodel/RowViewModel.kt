package viewmodel

import kotlinx.coroutines.flow.map
import model.RemoteDataSource

class RowViewModel(private val source: RemoteDataSource) {
    val rows = source.apiData
        .map { it.rows }

    fun getProducts(rowNr: Int, weekDay: Int) = rows.map { rows ->
        rows[rowNr].days[weekDay].productIds[0].id
    }

}
