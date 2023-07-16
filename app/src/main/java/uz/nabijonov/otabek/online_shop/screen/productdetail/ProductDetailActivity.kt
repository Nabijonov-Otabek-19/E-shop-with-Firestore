package uz.nabijonov.otabek.online_shop.screen.productdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.id_CV_back
import kotlinx.android.synthetic.main.activity_product_detail.id_CV_love
import kotlinx.android.synthetic.main.activity_product_detail.id_IV_love
import kotlinx.android.synthetic.main.activity_product_detail.id_TV_ProductName
import kotlinx.android.synthetic.main.activity_product_detail.id_TV_ProductPrice
import kotlinx.android.synthetic.main.activity_product_detail.id_TV_Title
import kotlinx.android.synthetic.main.activity_product_detail.id_btnAdd2Cart
import kotlinx.android.synthetic.main.activity_product_detail.id_imgProduct
import uz.nabijonov.otabek.online_shop.R
import uz.nabijonov.otabek.online_shop.model.ProductModel
import uz.nabijonov.otabek.online_shop.utils.Constants
import uz.nabijonov.otabek.online_shop.utils.PrefUtils

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var item: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        id_CV_back.setOnClickListener {
            finish()
        }

        item = intent.getSerializableExtra("PItem") as ProductModel

        id_CV_love.setOnClickListener {
            PrefUtils.setFavorite(item)
            if (PrefUtils.checkFavorite(item)) {
                setFavData2FireStore(item)
                id_IV_love.setImageResource(R.drawable.ic_love_full)
            } else {
                removeFavDataFromFS(item)
                id_IV_love.setImageResource(R.drawable.ic_love)
            }
        }

        id_TV_ProductName.text = item.name
        id_TV_Title.text = item.name
        id_TV_ProductPrice.text = item.price.toString() + " $"

        if (PrefUtils.checkFavorite(item)) {
            id_IV_love.setImageResource(R.drawable.ic_love_full)
        } else {
            id_IV_love.setImageResource(R.drawable.ic_love)
        }

        Glide.with(this).load(item.img).into(id_imgProduct)


        id_btnAdd2Cart.setOnClickListener {
            item.cart_count = 1
            PrefUtils.setToCart(item)
            if (PrefUtils.checkCart(item)) {
                setCartData2FS(item)
                Toast.makeText(this, "Product added to cart", Toast.LENGTH_LONG).show()
                id_btnAdd2Cart.text = ("Remove from cart").lowercase()
            } else {
                removeCartData(item)
                Toast.makeText(this, "Product removed from cart", Toast.LENGTH_LONG).show()
                id_btnAdd2Cart.text = ("Add to cart").lowercase()
            }
        }
        if (PrefUtils.checkCart(item)) {
            id_btnAdd2Cart.text = ("Remove from cart").lowercase()
        } else {
            id_btnAdd2Cart.text = ("Add to cart").lowercase()
        }
    }

    private fun setCartData2FS(item: ProductModel) {
        Constants.FirestoreDB.collection("Orders").add(item)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Product saved to cart", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun removeCartData(item: ProductModel) {
        Constants.FirestoreDB.collection("Orders")
            .whereEqualTo("id", item.id).get()
            .addOnSuccessListener {
                for (document in it) {
                    Constants.FirestoreDB.collection("Orders")
                        .document(document.id).delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Product removed", Toast.LENGTH_LONG).show()
                        }
                }
            }
    }

    private fun setFavData2FireStore(item: ProductModel) {
        Constants.FirestoreDB.collection("Favorites").add(item)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Product saved", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun removeFavDataFromFS(item: ProductModel) {
        Constants.FirestoreDB.collection("Favorites")
            .whereEqualTo("id", item.id).get()
            .addOnSuccessListener {
                for (document in it) {
                    Constants.FirestoreDB.collection("Favorites")
                        .document(document.id).delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Product removed", Toast.LENGTH_LONG).show()
                        }
                }
            }
    }
}