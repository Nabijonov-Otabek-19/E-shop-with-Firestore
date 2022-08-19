package uz.nabijonov.otabek.online_shop.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_home.*
import uz.nabijonov.otabek.online_shop.databinding.FragmentHomeBinding
import uz.nabijonov.otabek.online_shop.model.CategoryModel
import uz.nabijonov.otabek.online_shop.model.ProductModel
import uz.nabijonov.otabek.online_shop.screen.MainViewModel
import uz.nabijonov.otabek.online_shop.view.CategoryAdapter
import uz.nabijonov.otabek.online_shop.view.CategoryCallback
import uz.nabijonov.otabek.online_shop.view.ProductAdapter


class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryList: ArrayList<CategoryModel>
    private lateinit var productList: ArrayList<ProductModel>
    private var imageList: ArrayList<SlideModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idRVCategories.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        binding.idRVTopProducts.layoutManager = LinearLayoutManager(requireActivity())

        categoryList = arrayListOf()
        productList = arrayListOf()

        viewModel.errorMes.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.offersData.observe(requireActivity()) {
            imageList.add(SlideModel(it, ScaleTypes.FIT))
            binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
        }

        viewModel.categoriesData.observe(requireActivity()) {
            for (dc in it.documentChanges) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    categoryList.add(dc.document.toObject(CategoryModel::class.java))
                }
            }
            id_RV_categories.adapter = CategoryAdapter(categoryList, object : CategoryCallback {
                override fun onClickItem(item: CategoryModel) {
                    viewModel.getRPByCategory(item.title.toString())
                }
            })
        }

        viewModel.productsData.observe(requireActivity()) {
            productList.clear()
            for (dc in it.documentChanges) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    productList.add(dc.document.toObject(ProductModel::class.java))
                }
            }
            binding.idRVTopProducts.adapter = ProductAdapter(productList)
        }

        loadData()
    }

    private fun loadData() {
        viewModel.getOffers()
        viewModel.getCategories()
        viewModel.getTopProducts()
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment = HomeFragment()
    }
}