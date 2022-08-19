package uz.nabijonov.otabek.online_shop.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import uz.nabijonov.otabek.online_shop.R
import uz.nabijonov.otabek.online_shop.screen.cart.CartFragment
import uz.nabijonov.otabek.online_shop.screen.favourite.FavouriteFragment
import uz.nabijonov.otabek.online_shop.screen.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment.newInstance()
    private val favouriteFragment = FavouriteFragment.newInstance()
    private val cartFragment = CartFragment.newInstance()
    private var activeFragment: Fragment = homeFragment

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel()

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, favouriteFragment, favouriteFragment.tag)
            .hide(favouriteFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, cartFragment, cartFragment.tag).hide(cartFragment).commit()


        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    id_TV_userName.text = getString(R.string.homeTitle)
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(homeFragment)
                        .commit()
                    activeFragment = homeFragment

                }
                R.id.actionFavourite -> {
                    id_TV_userName.text = getString(R.string.favoriteTitle)
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(favouriteFragment).commit()
                    activeFragment = favouriteFragment

                }
                R.id.actionCart -> {
                    id_TV_userName.text = getString(R.string.cartTitle)
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(cartFragment)
                        .commit()
                    activeFragment = cartFragment

                }
            }
            return@setOnItemSelectedListener true
        }
    }
}