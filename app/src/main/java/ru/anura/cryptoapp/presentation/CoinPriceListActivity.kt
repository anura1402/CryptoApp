package ru.anura.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import ru.anura.cryptoapp.R
import ru.anura.cryptoapp.databinding.ActivityCoinPriceListBinding
import ru.anura.cryptoapp.domain.CoinInfo
import ru.anura.cryptoapp.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: CoinViewModel

    private val component by lazy {
        (application as CoinApp).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    //supportFragmentManager.popBackStack()
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null //убираем анимацию
        viewModel = ViewModelProvider(this,factory)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }

    }
    private fun isOnePaneMode() = binding.fragmentContainer == null
    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
    companion object{
        const val NAME = "CoinDetailActivity"
    }

}
