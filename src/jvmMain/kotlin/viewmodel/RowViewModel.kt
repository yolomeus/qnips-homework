package viewmodel

import kotlinx.coroutines.flow.map
import model.RemoteDataSource
import model.data.Allergen
import model.data.Product
import model.data.Row

class RowViewModel(private val source: RemoteDataSource) {
    fun getTableRows() =
        source.apiData.map {
            val allergenMap = it.allergens
            val products = it.products
            val rows = it.rows

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

    private fun getAllergenRow(productRow: List<Product?>, allergenMap: Map<String, Allergen>) =
        productRow.map { product ->
            product?.allergenIds?.map { alId -> allergenMap[alId]?.label }.toString()
        }

    private fun getPriceRow(productRow: List<Product?>) =
        productRow.map { product -> product?.price?.amount.toEuroStr() }

    private fun getProductRow(row: Row, products: Map<Long, Product>) =
        row.days.map { day ->
            val prodId = day.productIds[0].id
            products[prodId]
        }
}

private fun Number?.toEuroStr() = String.format("%.2f€", this)

data class TableItem(val title: String, val allergens: String, val price: String)

