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

class CoinPriceListActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null //убираем анимацию
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
            Log.d("CoinRepository", "adapter: OK $it")
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
}
