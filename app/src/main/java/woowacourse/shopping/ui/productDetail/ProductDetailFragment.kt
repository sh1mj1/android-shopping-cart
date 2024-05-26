package woowacourse.shopping.ui.productDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.data.source.DummyProductHistoryDataSource
import woowacourse.shopping.data.source.DummyProductsDataSource
import woowacourse.shopping.data.source.DummyShoppingCartProductIdDataSource
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("FragmentCartListBinding is not initialized")

    private lateinit var factory: UniversalViewModelFactory
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater)

        initViewModel()

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.onItemChargeListener = viewModel

        return binding.root
    }

    private fun initViewModel() {
        arguments?.let {
            factory =
                UniversalViewModelFactory {
                    ProductDetailViewModel(
                        productId = it.getLong(PRODUCT_ID),
                        shoppingProductsRepository =
                            DefaultShoppingProductRepository(
                                DummyProductsDataSource(),
                                DummyShoppingCartProductIdDataSource(),
                            ),
                        productHistoryRepository =
                            DefaultProductHistoryRepository(
                                productHistoryDataSource = DummyProductHistoryDataSource(),
                                productDataSource = DummyProductsDataSource(),
                            ),
                    )
                }
            viewModel = ViewModelProvider(this, factory)[ProductDetailViewModel::class.java]
        }
        viewModel.loadAll()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: called")
        binding.productDetailToolbar.setOnMenuItemClickListener {
            navigateToMenuItem(it)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: called")
    }

    private fun navigateToMenuItem(it: MenuItem) =
        when (it.itemId) {
            R.id.action_x -> navigateToPreviousFragment()

            else -> false
        }

    private fun navigateToPreviousFragment(): Boolean {
        parentFragmentManager.popBackStack()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PRODUCT_ID = "productId"
        private const val TAG = "ProductDetailFragment"

        fun newInstance(productId: Long): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle().apply { putLong(PRODUCT_ID, productId) }
            fragment.arguments = bundle
            return fragment
        }
    }
}
