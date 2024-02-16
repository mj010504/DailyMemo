package com.example.dailymemo.MyStream.Retrofit

import android.util.Log
import com.example.dailymemo.MainView
import com.example.dailymemo.MyStream.Retrofit.Response.diaryPublicTypeResponse
import com.example.dailymemo.MyStream.Retrofit.Response.makeMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.modifyMyStreamRequest
import com.example.dailymemo.MyStream.Retrofit.Response.modifyMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.openMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.removeMyStreamDiaryResponse
import com.example.dailymemo.MyStream.Retrofit.Response.removeMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.searchMyStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.showWatchStreamResponse
import com.example.dailymemo.MyStream.Retrofit.Response.streamPublicTypeResponse

import com.example.dailymemo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyStreamService {
    private lateinit var myStreamView: MyStreamView
    private lateinit var mainView : MainView

    fun setMyStreamView(myStreamView: MyStreamView) {
        this.myStreamView = myStreamView
    }

    fun setMainView(mainView: MainView) {
        this.mainView = mainView
    }

    fun showMystream(streamId : Int, userId: Int, page : Int) {
        val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)

        myStreamService.showMyStream(streamId, userId, page).enqueue(object: Callback<openMyStreamResponse> {
            override fun onResponse(
                call: Call<openMyStreamResponse>,
                response: Response<openMyStreamResponse>
            ) {
                if (response.code() == 200) {
                    myStreamView.showMyStreamSuccess(response.body()!!)
                }
                else {

                }
            }

            override fun onFailure(call: Call<openMyStreamResponse>, t: Throwable) {

            }

        })
    }

    fun searchMyStream(userId : Int, query: String, page: Int) {
        val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)

        myStreamService.searchMystream(userId,query,page).enqueue(object: Callback<searchMyStreamResponse>{
            override fun onResponse(
                call: Call<searchMyStreamResponse>,
                response: Response<searchMyStreamResponse>
            ) {
                if (response.code() == 200) {
                    myStreamView.searchMyStreamSuccess(response.body()!!)
                }
            }

            override fun onFailure(call: Call<searchMyStreamResponse>, t: Throwable) {

            }

        })
    }

    fun showWatchStream(userId: Int, page: Int) {
        val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)

        myStreamService.showWatchStream(userId,page).enqueue(object: Callback<showWatchStreamResponse>{
            override fun onResponse(
                call: Call<showWatchStreamResponse>,
                response: Response<showWatchStreamResponse>
            ) {
                if (response.code() == 200) {

                    mainView.showWatchStreamSuccess(response.body()!!.result.streamList)
                }

            }


            override fun onFailure(call: Call<showWatchStreamResponse>, t: Throwable) {

            }

        })
    }

    fun makeMyStream(userId : Int, streamName: String) {
        val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)

        myStreamService.makeMyStream(userId, streamName).enqueue(object: Callback<makeMyStreamResponse>{
            override fun onResponse(
                call: Call<makeMyStreamResponse>,
                response: Response<makeMyStreamResponse>
            ) {
                if (response.code() == 200) {
                    mainView.makeMyStreamSuccess(response.body()!!.result.streamId, response.body()!!.result.streamName)
                }


            }

            override fun onFailure(call: Call<makeMyStreamResponse>, t: Throwable) {

            }

        })
    }

    fun modifyMyStream(postId : Int, userId : Int, detail: String) {
        val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)
        val request = modifyMyStreamRequest(userId,detail)

        myStreamService.modifyMyStream(postId, request).enqueue(object: Callback<modifyMyStreamResponse>{
            override fun onResponse(
                call: Call<modifyMyStreamResponse>,
                response: Response<modifyMyStreamResponse>
            ) {

            }

            override fun onFailure(call: Call<modifyMyStreamResponse>, t: Throwable) {

            }

        })
    }

    fun diaryPublicType(userId : Int, postId : Int, isPulbic: Boolean) {
        val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)

        myStreamService.diaryPublicType(userId,postId,isPulbic).enqueue(object: Callback<diaryPublicTypeResponse>{
            override fun onResponse(
                call: Call<diaryPublicTypeResponse>,
                response: Response<diaryPublicTypeResponse>
            ) {
                if (response.code() == 200) {
                    myStreamView.diaryPublicTypeSuccess(response.body()!!.result.isPublic)
                }
            }

            override fun onFailure(call: Call<diaryPublicTypeResponse>, t: Throwable) {

            }

        })
    }

    fun streamPublicType(userId : Int, streamId : Int, isPulbic: Boolean) {
        val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)

        myStreamService.streamPublicType(userId, streamId, isPulbic)
            .enqueue(object : Callback<streamPublicTypeResponse> {
                override fun onResponse(
                    call: Call<streamPublicTypeResponse>,
                    response: Response<streamPublicTypeResponse>
                ) {

                }

                override fun onFailure(call: Call<streamPublicTypeResponse>, t: Throwable) {

                }

            })
    }

        fun removeMyStream(streamId : Int) {
            val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)

            myStreamService.removeMyStream(streamId).enqueue(object: Callback<removeMyStreamResponse>{
                override fun onResponse(
                    call: Call<removeMyStreamResponse>,
                    response: Response<removeMyStreamResponse>
                ) {

                }

                override fun onFailure(call: Call<removeMyStreamResponse>, t: Throwable) {

                }

            })
        }

        fun removeMyStreamDiary(postId : Int) {
            val myStreamService = getRetrofit().create(MyStreamRetrofitInterface::class.java)

            myStreamService.removeMyStreamDiary(postId).enqueue(object :Callback<removeMyStreamDiaryResponse>{
                override fun onResponse(
                    call: Call<removeMyStreamDiaryResponse>,
                    response: Response<removeMyStreamDiaryResponse>
                ) {
                    if (response.code() == 200) {
                        myStreamView.removeMyStreamDiarySuccess()
                    }

                }

                override fun onFailure(call: Call<removeMyStreamDiaryResponse>, t: Throwable) {

                }

            })
        }
}


