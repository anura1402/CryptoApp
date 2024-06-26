package ru.anura.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.anura.cryptoapp.R

class CoinDetailFragment : Fragment() {
    private lateinit var viewModel: CoinViewModel

    private var fromSymbol: String = ""

    private lateinit var tvPrice: TextView
    private lateinit var tvMinPrice: TextView
    private lateinit var tvMaxPrice: TextView
    private lateinit var tvLastMarket: TextView
    private lateinit var tvLastUpdate: TextView
    private lateinit var tvFromSymbol: TextView
    private lateinit var tvToSymbol: TextView
    private lateinit var ivLogoCoin: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coin_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        initViews(view)
        observeData(fromSymbol)
    }

    private fun observeData(fromSymbol: String) {
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            tvPrice.text = it.price
            tvMinPrice.text = it.lowDay
            tvMaxPrice.text = it.highDay
            tvLastMarket.text = it.lastMarket
            tvLastUpdate.text = it.lastUpdate
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            Picasso.get().load(it.imageUrl).into(ivLogoCoin)
        }
    }

    private fun initViews(view: View) {
        tvPrice = view.findViewById(R.id.tvPrice)
        tvMinPrice = view.findViewById(R.id.tvMinPrice)
        tvMaxPrice = view.findViewById(R.id.tvMaxPrice)
        tvLastMarket = view.findViewById(R.id.tvLastMarket)
        tvLastUpdate = view.findViewById(R.id.tvLastUpdate)
        tvFromSymbol = view.findViewById(R.id.tvFromSymbol)
        tvToSymbol = view.findViewById(R.id.tvToSymbol)
        ivLogoCoin = view.findViewById(R.id.ivLogoCoin)
    }

    private fun parseArgs() {
        val args = requireArguments()
        fromSymbol = args.getString(CRYPTO_NAME) ?: EMPTY_SYMBOL
    }

    companion object {
        private const val CRYPTO_NAME = "from symbol"
        private const val EMPTY_SYMBOL = ""

        fun newInstance(fromSymbol: String): CoinDetailFragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(CRYPTO_NAME, fromSymbol)
                }
            }
        }
    }
}