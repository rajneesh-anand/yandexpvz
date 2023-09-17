package com.neo.yandexpvz.repository


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neo.yandexpvz.api.RestApi
import com.neo.yandexpvz.model.UserRequest
import com.neo.yandexpvz.model.UserResponse
import com.neo.yandexpvz.model.UserUpdateResponse
import com.neo.yandexpvz.utils.NetworkResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject
import org.json.JSONObject
import java.io.File


class UserRepository @Inject constructor(private val api: RestApi) {


    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData



//
//    suspend fun registerUser(userRequest: UserRequest) {
//        _userResponseLiveData.postValue(NetworkResult.Loading())
//        val response = api.signup(userRequest)
//        handleResponse(response)
//    }
//
//    suspend fun loginUser(userRequest: UserRequest) {
//        _userResponseLiveData.postValue(NetworkResult.Loading())
//        val response =api.signin(userRequest)
//        handleResponse(response)
//    }



    suspend fun loginUser(userRequest: UserRequest) : NetworkResult<UserResponse> {
        try {
            NetworkResult.Loading(data = true)
            val response =  api.signin(userRequest)

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
            Log.d("yandexpvz user-repo","An error occurred ${exception.message.toString()}" )
            return NetworkResult.Error(message = "check your internet connection")

        }
        NetworkResult.Loading(data = false)
    }


    suspend fun registerUser(userRequest: UserRequest) : NetworkResult<UserResponse> {

        try {
            NetworkResult.Loading(data = true)
            val response =  api.signup(userRequest)

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
            Log.d("yandexpvz user-repo","An error occurred ${exception.message.toString()}" )
            return NetworkResult.Error(message = "check your internet connection")

        }
        NetworkResult.Loading(data = false)
    }


    suspend fun updatePassword(userRequest: UserRequest) : NetworkResult<UserResponse> {

        try {
            NetworkResult.Loading(data = true)
            val response =  api.updatePassword(userRequest)

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
            Log.d("yandexpvz user-repo","An error occurred ${exception.message.toString()}" )
            return NetworkResult.Error(message = "check your internet connection")

        }
        NetworkResult.Loading(data = false)
    }

    suspend fun updateProfile(file: File?, name:String, mobile:String) : NetworkResult<UserUpdateResponse> {

        try {
            NetworkResult.Loading(data = true)
            val response =  api.updateProfile(
                image = if (file != null) {
                    MultipartBody.Part
                        .createFormData(
                            "image",
                            file.name,
                            file.asRequestBody("image/*".toMediaTypeOrNull()))

                }else{
                    MultipartBody.Part
                        .createFormData(
                            "image",
                            "")
                }
                ,
                userName = MultipartBody.Part
                    .createFormData(
                        "userName",
                        name),

                userMobile = MultipartBody.Part
                    .createFormData(
                        "userMobile",
                        mobile),

                )

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
            Log.d("yandexpvz user-repo","An error occurred ${exception.message.toString()}" )
            return NetworkResult.Error(message = "check your internet connection")
        }
        NetworkResult.Loading(data = false)


    }



}