package uz.nabijonov.otabek.online_shop.model

import java.io.Serializable

data class ProductModel(
    val id: Int? = 0,
    val name: String? = "",
    val img: String? = "",
    val category: String? = "",
    val price: Int? = 0,
    var cart_count: Int? = 0
) : Serializable
