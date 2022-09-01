package com.android.cerekartvendorapp.api


import com.android.cerekartvendorapp.BuildConfig
import com.android.cerekartvendorapp.constants.ApiConstants
import com.android.cerekartvendorapp.modal.BaseResponse
import com.android.cerekartvendorapp.modal.SignUpLoginResponse
import com.android.cerekartvendorapp.preferences.DataManager
import com.android.cerekartvendorapp.request.LoginSignupRequest
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiManager {
    private val apiClient: ApiClient
    private val registeredApiClient: ApiClient

    init {
        apiClient = httpClient
        registeredApiClient = httpRegisteredClient
    }

    private val httpClient: ApiClient
        get() {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(getHttpClient().build())
                .build()

            return retrofit.create(ApiClient::class.java)
        }

    private val httpRegisteredClient: ApiClient
        get() {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(getHttpRegisteredClient().build())
                .build()

            return retrofit.create(ApiClient::class.java)
        }

    /**
     * Method to create [OkHttpClient] builder by adding required headers in the [Request]
     *
     * @return OkHttpClient object
     */
    private fun getHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder: Request.Builder
                requestBuilder = original.newBuilder()
                    .header(
                        "Authorization",
                        Credentials.basic(ApiConstants.API_USERNAME, ApiConstants.API_PASSWORD)
                    )
                    .header("api_key", ApiConstants.API_KEY)
                    .header("platform", ApiConstants.PLATFORM_ANDROID)
                    .header("language", "en")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                val response = chain.proceed(request)
                response
            }
            .addInterceptor(getLoggingInterceptor())
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
    }

    /**
     * Method to create [OkHttpClient] builder by adding required headers in the [Request]
     *
     * @return OkHttpClient object
     */
    private fun getHttpRegisteredClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder: Request.Builder
                requestBuilder = original.newBuilder()
                    .header("authorization", "Bearer " + DataManager.getAccessToken())
                    .header("api_key", ApiConstants.API_KEY)
                    .header("platform", ApiConstants.PLATFORM_ANDROID)
                    .header("language", "en")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                val response = chain.proceed(request)
                response
            }
            .addInterceptor(getLoggingInterceptor())
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor(CustomHttpLogger())
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    fun hitLoginApi(signUpRequest: LoginSignupRequest): Call<BaseResponse<SignUpLoginResponse>> {
        return apiClient.hitLoginApi(signUpRequest)
    }



}
