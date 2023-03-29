package viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import model.RemoteDataSource
import model.data.Allergen
import model.data.Product
import model.data.QnipsResponse
import model.data.Row

/**
 * ViewModel for connecting UI to DataSource. Responsible for transforming data flows into a consumable format for the
 * UI and for notifying the model if an explicit data update is requested.
 */
class RowViewModel(private val source: RemoteDataSource) {

    private val _apiData: Flow<QnipsResponse> = source.apiData

    /**
     * Transform flow of [model.data.QnipsResponse] to flow of list of table rows, i.e. List<List<[TableProduct]>>.
     */
    fun getTableRows() =
        _apiData.map {
            val allergenMap = it.allergens
            val products = it.products
            val rows = it.rows

            // our result list of table rows to be filled
            val tableRows = mutableListOf<List<TableItem>>()
            rows.forEach { row ->
                // retrieve rows for all 3 table contents
                val productRow = getProductRow(row, products)
                val tableRow = productRow.map { products ->
                    products.map { p ->
                        p?.let { x -> productToTableItem(x, allergenMap) }
                    }
                }.map { items -> TableItem(items) }
                tableRows.add(tableRow)
            }
            // return rows of table entries
            tableRows.toList()
        }

    private fun productToTableItem(product: Product, allergenMap: Map<String, Allergen>): TableProduct {
        val allergens = product.allergenIds.map { allergenMap[it]?.label.toString() }
        return TableProduct(
            product.name,
            allergens.toString(),
            product.price.amount.toEuroStr()
        )
    }

    /**
     * Retrieve row of day names, maps numbers 0..5 to names of weekdays.
     */
    fun getTableHeader() =
        _apiData.map { response ->
            // too many responsibilities and hardcoded but let's keep this simple...
            val dayNames = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag")
            val idxToName = dayNames.indices.associateWith { dayNames[it] }
            val weekdays = response.rows.map { row -> row.days.map { day -> idxToName[day.weekday] } }
            if (weekdays.isNotEmpty()) weekdays[0] else emptyList()
        }

    fun getRowLegend() =
        _apiData.map { response ->
            response.rows.map { it.name }
        }

    /**
     * Retrieve row of product names given [row] and [products].
     */
    private fun getProductRow(row: Row, products: Map<Long, Product>) =
        row.days.map { day ->
            day.productIds.map { products[it.id] }
        }

    /**
     * Request data update from [source].
     */
    fun updateData() {
        source.updateData()
    }
}

/**
 * Format [Number] to [String] with 2 decimal places.
 */
private fun Number?.toEuroStr() = String.format("%.2f€", this)

/**
 * Intermediate representation for table elements to be used in the UI.
 */
data class TableProduct(val title: String, val allergens: String, val price: String)
data class TableItem(val items: List<TableProduct?>)