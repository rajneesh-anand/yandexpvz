package com.neo.yandexpvz.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neo.yandexpvz.api.RestApi
import com.neo.yandexpvz.db.CoinDao
import com.neo.yandexpvz.model.CoinList
import com.neo.yandexpvz.model.Product
import com.neo.yandexpvz.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepository @Inject constructor(
    private val dao: CoinDao,
    private val api: RestApi,
    @ApplicationContext private val context: Context,
)
{


    private var _productsList: MutableLiveData<List<Product>> = MutableLiveData(listOf())
    val productsList: LiveData<List<Product>> = _productsList


    private var balancedCoinLiveData = MutableLiveData<Int>()
    val balancedCoins: LiveData<Int>
        get() = balancedCoinLiveData

    suspend fun insertCoins(mobileId: String)  {

        if(NetworkUtils.isInternetAvailable(context)){
            val numberOfRecords = dao.getNumberOfRecords(mobileId)
            val result = api.getCoins(mobileId, numberOfRecords.toString())
            if(result?.body() != null){
                dao.insertCoin(result.body()!!.results)
//                coinsLiveData.postValue(result.body())
            }
            val sumEarnedCoins = dao.sumEarnedCoins(mobileId)
            val sumSpentCoins = dao.sumSpentCoins(mobileId)
            val balaceCoin = sumEarnedCoins-sumSpentCoins
            balancedCoinLiveData.postValue(balaceCoin)

        }
        else{

            val coins = dao.getCoins(mobileId)
            val coinList = CoinList(coins, 1)
//            coinsLiveData.postValue(coinList)
        }

    }

    fun fetchUserCoinBalance(mobileId:String)  {
        val sumEarnedCoins = dao.sumEarnedCoins(mobileId)
        val sumSpentCoins = dao.sumSpentCoins(mobileId)
        val balaceCoin = sumEarnedCoins-sumSpentCoins
        balancedCoinLiveData.postValue(balaceCoin)
    }

//
//    suspend fun fetchProductsList() {
//        val result = api.getAllProducts()
//        _productsList.postValue(result)
//    }


}



