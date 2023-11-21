package com.neo.yandexpvz.api

import com.neo.yandexpvz.model.ApiResponse
import com.neo.yandexpvz.model.BlogResponse
import com.neo.yandexpvz.model.CoinList
import com.neo.yandexpvz.model.GiftCardResponse
import com.neo.yandexpvz.model.ItemResponse
import com.neo.yandexpvz.model.ProductList
import com.neo.yandexpvz.model.ProductResponse
import com.neo.yandexpvz.model.RedeemRequest
import com.neo.yandexpvz.model.RedeemResponse
import com.neo.yandexpvz.model.UserRequest
import com.neo.yandexpvz.model.UserResponse
import com.neo.yandexpvz.model.UserUpdateResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import javax.inject.Singleton


@Singleton
interface RestApi {


    @GET("coin/user-coins/{mobileId}/{skip}")
    suspend fun getCoins(
        @Path("mobileId") mobileId: String,
        @Path("skip") skip: String,
    ) : Response<CoinList>


    @GET("product/products-list")
    suspend fun fetchAllProducts(): Response<ProductList>


    @GET("item/items-list")
    suspend fun fetchItems(): Response<ItemResponse>

    @GET("blog/blogs-list")
    suspend fun fetchBlogs(): Response<BlogResponse>


    @GET("user/gifts/{mobileId}")
    suspend fun fetchUserGiftCards(@Path("mobileId") mobileId:String): Response<GiftCardResponse>
//
//    @GET("volumes")
//    suspend fun getAllBooks(@Query("q") query: String): Book

//    @GET("volumes/{bookId}")
//    suspend fun getBookInfo(@Path("bookId") bookId: String): Item

    @POST("coin/redeem")
    suspend fun redeemCoins(@Body redeemRequest: RedeemRequest) : Response<RedeemResponse>


    @POST("user/register")
    suspend fun signup(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("user/signin")
    suspend fun signin(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("user/password/update")
    suspend fun updatePassword(@Body userRequest: UserRequest) : Response<UserResponse>

//
//    @GET("product/all-products")
//    suspend fun getAllProducts(): MutableList<Product>

    @GET("product/single-product/{productId}")
    suspend fun getSingleProduct(@Path("productId") productId: String): Response<ProductResponse>

    @Multipart
    @POST("user/profile/update")
    suspend fun updateProfile(
        @Part image: MultipartBody.Part,
        @Part userName: MultipartBody.Part,
        @Part userMobile: MultipartBody.Part,
    ): Response<UserUpdateResponse>

    @Multipart
    @POST("feedback/create")
    suspend fun sendFeedBackMessage(
        @Part image: MultipartBody.Part,
        @Part userName: MultipartBody.Part,
        @Part userMobile: MultipartBody.Part,
        @Part message: MultipartBody.Part,
        @Part category:  MultipartBody.Part,
    ): Response<ApiResponse>

//    @POST("user/fcm/token")
//    suspend fun fcmToken(@Body token: String) : Response<CommonResponse>
//


}