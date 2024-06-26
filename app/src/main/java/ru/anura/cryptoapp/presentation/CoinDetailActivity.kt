package ru.anura.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.anura.cryptoapp.R
import ru.anura.cryptoapp.databinding.ActivityCoinDetailBinding

class CoinDetailActivity : AppCompatActivity() {
    private var fromSymbol: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        parseIntent()
        if (savedInstanceState == null) {
            launchFragment()
        }
    }

    private fun launchFragment() {
        supportFragmentManager.popBackStack()
        val fragment = CoinDetailFragment.newInstance(fromSymbol)
        supportFragmentManager.beginTransaction()
            .replace(R.id.coin_detail_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}
