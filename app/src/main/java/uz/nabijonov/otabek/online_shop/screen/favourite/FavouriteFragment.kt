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
import kotlinx.android.synthetic.main.fragment_favourite.*
import uz.nabijonov.otabek.online_shop.R
import uz.nabijonov.otabek.online_shop.model.ProductModel
import uz.nabijonov.otabek.online_shop.screen.MainViewModel
import uz.nabijonov.otabek.online_shop.view.ProductAdapter

class FavouriteFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var productList: ArrayList<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        productList = arrayListOf()


        viewModel.errorMes.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.progress.observe(requireActivity()) {
            id_FR_refresh.isRefreshing = it
        }

        viewModel.productsData.observe(requireActivity()) {
            productList.clear()
            for (dc in it.documentChanges) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    productList.add(dc.document.toObject(ProductModel::class.java))
                }
            }
            id_RV_topProductsFav.adapter = ProductAdapter(productList)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id_FR_refresh.setOnRefreshListener {
            loadData()
        }

        id_RV_topProductsFav.layoutManager = LinearLayoutManager(requireActivity())

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