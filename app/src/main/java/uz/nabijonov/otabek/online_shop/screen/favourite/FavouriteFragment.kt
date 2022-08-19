package uz.nabijonov.otabek.online_shop.screen.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import uz.nabijonov.otabek.online_shop.databinding.FragmentFavouriteBinding
import uz.nabijonov.otabek.online_shop.model.ProductModel
import uz.nabijonov.otabek.online_shop.screen.MainViewModel
import uz.nabijonov.otabek.online_shop.view.ProductAdapter

class FavouriteFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var productList: ArrayList<ProductModel>

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        productList = arrayListOf()


        viewModel.errorMes.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.progress.observe(requireActivity()) {
            binding.idFRRefresh.isRefreshing = it
        }

        viewModel.productsData.observe(requireActivity()) {
            productList.clear()
            for (dc in it.documentChanges) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    productList.add(dc.document.toObject(ProductModel::class.java))
                }
            }
            binding.idRVTopProductsFav.adapter = ProductAdapter(productList)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idFRRefresh.setOnRefreshListener {
            loadData()
        }

        binding.idRVTopProductsFav.layoutManager = LinearLayoutManager(requireActivity())

        loadData()

    }

    private fun loadData() {
        viewModel.getFavPRByID()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }
}