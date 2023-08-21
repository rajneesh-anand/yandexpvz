package com.neo.yandexpvz.repository

import com.neo.yandexpvz.api.RestApi
import com.neo.yandexpvz.model.GiftCardResponse
import com.neo.yandexpvz.model.ProductList
import com.neo.yandexpvz.model.ProductResponse
import com.neo.yandexpvz.model.RedeemRequest
import com.neo.yandexpvz.model.RedeemResponse
import com.neo.yandexpvz.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class ProductRepository  @Inject constructor(private val api: RestApi) {

    suspend fun fetchProduct(productId:String) : NetworkResult<ProductResponse> {

        try {
            NetworkResult.Loading(data = true)
            val response =  api.getSingleProduct(productId)
            if (response.isSuccessful && response.body() != null) {
               return (NetworkResult.Success(response.body()!!))
            }
            else if(response.errorBody()!=null){
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                return (NetworkResult.Error(errorObj.getString("message")))
            }
            else{
                return (NetworkResult.Error("Something Went Wrong"))
            }
        }catch (exception: Exception){
            return NetworkResult.Error(message = "An error occurred ${exception.message.toString()}")
        }
        NetworkResult.Loading(data = false)
    }


    suspend fun redeemCoins(redeemRequest: RedeemRequest) : NetworkResult<RedeemResponse> {


        try {
            NetworkResult.Loading(data = true)
            val response =  api.redeemCoins(redeemRequest)

            if (response.isSuccessful && response.body() != null) {

                return (NetworkResult.Success(response.body()!!))
            }
            else if(response.errorBody()!=null){

                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())

                return (NetworkResult.Error(errorObj.getString("message")))
            }
            else{
                return (NetworkResult.Error("Something Went Wrong"))
            }


        }catch (exception: Exception){
            return NetworkResult.Error(message = "An error occurred ${exception.message.toString()}")
        }
        NetworkResult.Loading(data = false)




    }


    suspend fun fetchProductList() : NetworkResult<ProductList> {
        try {
            NetworkResult.Loading(data = true)
            val response =  api.fetchAllProducts()

            if (response.isSuccessful && response.body() != null) {

                return (NetworkResult.Success(response.body()!!))
            }
            else if(response.errorBody()!=null){

                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())

                return (NetworkResult.Error(errorObj.getString("message")))
            }
            else{
                return (NetworkResult.Error(message = "Something Went Wrong"))
            }


        }catch (exception: Exception){
            return NetworkResult.Error(message = "An error occurred ${exception.message.toString()}")
        }
        NetworkResult.Loading(data = false)
    }



}