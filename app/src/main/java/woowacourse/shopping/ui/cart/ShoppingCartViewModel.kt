package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener

abstract class ShoppingCartViewModel : ViewModel(), OnProductItemClickListener, OnItemQuantityChangeListener {
    abstract val uiState: ShoppingCartUiState

    abstract val event: SingleLiveData<ShoppingCartEvent>

    abstract fun loadAll()

    abstract fun nextPage()

    abstract fun previousPage()

    abstract fun deleteItem(cartItemId: Long)
}
