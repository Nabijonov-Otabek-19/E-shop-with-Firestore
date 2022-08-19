package uz.nabijonov.otabek.online_shop.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*
import uz.nabijonov.otabek.online_shop.repository.ShopRepository

class MainViewModel : ViewModel() {

    private val repository = ShopRepository()

    val errorMes = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val offersData = MutableLiveData<String>()
    val categoriesData = MutableLiveData<QuerySnapshot>()
    val productsData = MutableLiveData<QuerySnapshot>()


    fun getOffers() {
        repository.getOffers(errorMes, offersData)
    }

    fun getCategories() {
        repository.getCategories(errorMes, categoriesData)
    }

    fun getTopProducts() {
        repository.getTopProducts(errorMes, productsData)
    }

    fun getRPByCategory(itemTitle: String) {
        repository.getPRByCategory(itemTitle, errorMes, productsData)
    }

    fun getFavPRByID(){
        repository.getFavProductsByID(errorMes, progress, productsData)
    }

    fun getCartPRByID(){
        repository.getCartProductsByID(errorMes, progress, productsData)
    }
}