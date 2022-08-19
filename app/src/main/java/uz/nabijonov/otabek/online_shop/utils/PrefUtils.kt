package uz.nabijonov.otabek.online_shop.utils

import com.orhanobut.hawk.Hawk
import uz.nabijonov.otabek.online_shop.model.ProductModel

object PrefUtils {
    private const val PREF_FAVORITES = "pref_favorites"
    private const val PREF_PR_CART = "pref_pr_cart"

    fun setFavorite(item: ProductModel) {
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())

        if (items.filter { it == item.id }.firstOrNull() != null) {
            items.remove(item.id)
        } else {
            items.add(item.id!!)
        }
        Hawk.put(PREF_FAVORITES, items)
    }

    fun setToCart(item: ProductModel){
        val items = Hawk.get(PREF_PR_CART, arrayListOf<Int>())

        if (items.filter { it == item.id }.firstOrNull() != null) {
            items.remove(item.id)
        } else {
            items.add(item.id!!)
        }
        Hawk.put(PREF_PR_CART, items)
    }

    fun checkCart(item: ProductModel): Boolean {
        val items = Hawk.get(PREF_PR_CART, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }


    fun getFavList(): ArrayList<Int> {
        return Hawk.get(PREF_FAVORITES, arrayListOf())
    }

    fun checkFavorite(item: ProductModel): Boolean {
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }
}