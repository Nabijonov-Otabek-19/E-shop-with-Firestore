package uz.nabijonov.otabek.online_shop.screen.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import uz.nabijonov.otabek.online_shop.databinding.FragmentCartBinding
import uz.nabijonov.otabek.online_shop.model.ProductModel
import uz.nabijonov.otabek.online_shop.screen.MainViewModel
import uz.nabijonov.otabek.online_shop.view.CartAdapter

class CartFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var productList: ArrayList<ProductModel>

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        productList = arrayListOf()

        viewModel.errorMes.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.progress.observe(requireActivity()) {
            binding.idCartRefresh.isRefreshing = it
        }

        viewModel.productsData.observe(requireActivity()) { data ->
            productList.clear()
            for (dc in data.documentChanges) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    productList.add(dc.document.toObject(ProductModel::class.java))
                }
            }
            binding.idRVCart.adapter = CartAdapter(productList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idCartRefresh.setOnRefreshListener {
            loadData()
        }


        binding.idRVCart.layoutManager = LinearLayoutManager(requireActivity())

        loadData()
    }

    private fun loadData() {
        viewModel.getCartPRByID()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}