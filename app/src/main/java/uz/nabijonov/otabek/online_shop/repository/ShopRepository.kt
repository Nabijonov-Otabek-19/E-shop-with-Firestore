package uz.nabijonov.otabek.online_shop.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import uz.nabijonov.otabek.online_shop.utils.Constants

class ShopRepository {

    fun getOffers(error: MutableLiveData<String>, success: MutableLiveData<String>) {
        Constants.FirestoreDB.collection("Offers").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (que: QueryDocumentSnapshot in it.result) {
                        success.value = que.getString("img")
                    }
                } else {
                    error.value = it.exception.toString()
                }
            }
    }

    fun getCategories(error: MutableLiveData<String>, success: MutableLiveData<QuerySnapshot>) {
        Constants.FirestoreDB.collection("Categories").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    success.value = it.result
                } else {
                    error.value = it.exception.toString()
                }
            }
    }

    fun getTopProducts(error: MutableLiveData<String>, success: MutableLiveData<QuerySnapshot>) {
        Constants.FirestoreDB.collection("Products").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    success.value = it.result
                } else {
                    error.value = it.exception.toString()
                }
            }
    }

    fun getPRByCategory(
        itemTitle: String,
        error: MutableLiveData<String>,
        success: MutableLiveData<QuerySnapshot>
    ) {
        Constants.FirestoreDB.collection("Products")
            .whereEqualTo("category", itemTitle)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    success.value = it.result
                } else {
                    error.value = it.exception.toString()
                }
            }
    }

    fun getFavProductsByID(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<QuerySnapshot>
    ) {
        progress.value = true
        Constants.FirestoreDB.collection("Favorites")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    progress.value = false
                    success.value = it.result
                } else {
                    progress.value = false
                    error.value = it.exception.toString()
                }
            }
    }

    fun getCartProductsByID(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<QuerySnapshot>
    ) {
        progress.value = true
        Constants.FirestoreDB.collection("Orders")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    progress.value = false
                    success.value = it.result
                } else {
                    progress.value = false
                    error.value = it.exception.toString()
                }
            }
    }
}