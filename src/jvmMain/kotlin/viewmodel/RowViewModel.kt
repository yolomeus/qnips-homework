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
     * Transform flow of [model.data.QnipsResponse] to flow of list of table rows, i.e. List<List<[TableItem]>>.
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
                val priceRow = getPriceRow(productRow)
                val allergenRow = getAllergenRow(productRow, allergenMap)

                // build new table of TableItem() with necessary information as string
                val tableRow = mutableListOf<TableItem>()
                for (i in productRow.indices) {
                    tableRow.add(
                        TableItem(
                            productRow[i]?.name.toString(),
                            allergenRow[i],
                            priceRow[i]
                        )
                    )
                }
                tableRows.add(tableRow)
            }
            // return rows of table items
            tableRows.toList()
        }

    /**
     * Retrieve row of day names, maps numbers 0..5 to names of weekdays.
     */
    fun getTableHeader() =
        _apiData.map { response ->
            // too many responsibilities and hardcoded but let's keep this simple...
            val dayNames = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag")
            val idxToName = dayNames.indices.associateWith { dayNames[it] }
            val weekdays = response.rows.map { row -> row.days.map { day -> idxToName[day.weekday] } }
            if (weekdays.isNotEmpty()) weekdays[0] else emptyList()
        }

    fun getRowLegend() =
        _apiData.map { response ->
            response.rows.map { it.name }
        }

    /**
     * Extract a table row, i.e. List<String> of allergens, given [productRow] and [allergenMap].
     */
    private fun getAllergenRow(productRow: List<Product?>, allergenMap: Map<String, Allergen>) =
        productRow.map { product ->
            product?.allergenIds?.map { alId -> allergenMap[alId]?.label }.toString()
        }

    /**
     * Extract a row of prices from [productRow] a row of [Product], as formatted strings with 2 decimal places.
     */
    private fun getPriceRow(productRow: List<Product?>) =
        productRow.map { product -> product?.price?.amount.toEuroStr() }

    /**
     * Retrieve row of product names given [row] and [products].
     */
    private fun getProductRow(row: Row, products: Map<Long, Product>) =
        row.days.map { day ->
            val prodId = day.productIds[0].id
            products[prodId]
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
data class TableItem(val title: String, val allergens: String, val price: String)

