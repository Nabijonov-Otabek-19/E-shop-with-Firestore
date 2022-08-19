package uz.nabijonov.otabek.online_shop.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_item.view.*
import uz.nabijonov.otabek.online_shop.R
import uz.nabijonov.otabek.online_shop.model.ProductModel
import uz.nabijonov.otabek.online_shop.screen.productdetail.ProductDetailActivity

class ProductAdapter(private val items: ArrayList<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ItemHolder>() {

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra("PItem", item)
            it.context.startActivity(intent)
        }

        holder.itemView.id_TV_Prname.text = item.name
        holder.itemView.id_TV_Catname.text = item.category
        holder.itemView.id_TV_Price.text = item.price.toString() + "$"

        Glide.with(holder.itemView.context).load(item.img).into(holder.itemView.id_imgProduct)
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}