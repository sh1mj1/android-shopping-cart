package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductData

interface ShoppingCartItemRepository {
    fun addCartItem(product: ProductData): Int

    fun findById(id: Int): ProductData

    fun loadPagedCartItems(page: Int): List<ProductData>

    fun removeCartItem(productId: Int): ProductData

    fun clearAllCartItems()

    fun isFinalPage(page: Int): Boolean
}
