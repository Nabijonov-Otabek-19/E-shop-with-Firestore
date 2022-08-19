package uz.nabijonov.otabek.online_shop.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_item.view.*
import uz.nabijonov.otabek.online_shop.R
import uz.nabijonov.otabek.online_shop.model.CategoryModel

interface CategoryCallback{
    fun onClickItem(item: CategoryModel)
}

class CategoryAdapter(private val items: ArrayList<CategoryModel>, private val callback: CategoryCallback) :
    RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            items.forEach {
                it.checked = false
            }
            item.checked = true

            callback.onClickItem(item)
            notifyDataSetChanged()
        }

        holder.itemView.id_TV_category.text = item.title

        if (item.checked) {
            holder.itemView.cardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
            holder.itemView.id_TV_category.setTextColor(Color.WHITE)
        } else {
            holder.itemView.cardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
            holder.itemView.id_TV_category.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}