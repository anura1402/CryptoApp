package ru.anura.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.anura.cryptoapp.R
import ru.anura.cryptoapp.data.network.ApiFactory
import ru.anura.cryptoapp.databinding.ActivityCoinDetailBinding
import ru.anura.cryptoapp.domain.convertTimestampToTime

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private val binding by lazy{
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            binding.tvPrice.text = it.price
            binding.tvMinPrice.text = it.lowDay
            binding.tvMaxPrice.text = it.highDay
            binding.tvLastMarket.text = it.lastMarket
            binding.tvLastUpdate.text = convertTimestampToTime(it.lastUpdate)
            binding.tvFromSymbol.text = it.fromSymbol
            binding.tvToSymbol.text = it.toSymbol
            Picasso.get().load(ApiFactory.BASE_IMAGE_URL + it.imageUrl).into(binding.ivLogoCoin)
        }
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
