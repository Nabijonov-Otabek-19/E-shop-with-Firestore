package uz.nabijonov.otabek.online_shop.model

import java.io.Serializable


data class CartModel(
    var product_id: Int?,
    var count: Int?
):Serializable