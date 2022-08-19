package uz.nabijonov.otabek.online_shop.screen.makeorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_make_order.*
import uz.nabijonov.otabek.online_shop.R
import uz.nabijonov.otabek.online_shop.model.ProductModel

class MakeOrderActivity : AppCompatActivity() {

    lateinit var items: ArrayList<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_order)

        items = intent.getSerializableExtra("total") as ArrayList<ProductModel>


        id_TV_TotalPrice.text = items.sumOf { (it.cart_count ?: 0) * (it.price ?: 0) }.toString()

        id_CV_back.setOnClickListener {
            finish()
        }
    }
}