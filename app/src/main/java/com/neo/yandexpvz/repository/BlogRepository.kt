package com.neo.yandexpvz.repository

import com.neo.yandexpvz.api.RestApi
import com.neo.yandexpvz.model.BlogResponse
import com.neo.yandexpvz.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class BlogRepository  @Inject constructor(private val api: RestApi) {

    suspend fun fetchBlogs() : NetworkResult<BlogResponse> {
        try {
            NetworkResult.Loading(data = true)
            val response =  api.fetchBlogs()

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

}