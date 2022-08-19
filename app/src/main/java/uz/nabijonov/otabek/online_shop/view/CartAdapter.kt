package uz.nabijonov.otabek.online_shop.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cart_item.view.*
import uz.nabijonov.otabek.online_shop.R
import uz.nabijonov.otabek.online_shop.model.ProductModel

class CartAdapter(private val items: ArrayList<ProductModel>) :
    RecyclerView.Adapter<CartAdapter.ItemHolder>() {

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.id_TV_Price.text = item.price.toString()
        holder.itemView.id_TV_Prname.text = item.name
        Glide.with(holder.itemView).load(item.img).into(holder.itemView.id_imgProduct)

        holder.itemView.id_TV_Count.text = 1.toString()

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}