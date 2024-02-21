package com.example.dailymemo.OpenStream.Retrofit

import android.util.Log
import com.example.dailymemo.DailyBoard.Retrofit.Response.ChangeStreamRequest
import com.example.dailymemo.OpenStream.Retrofit.Response.ChangeCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.ChangeProfileRequest
import com.example.dailymemo.OpenStream.Retrofit.Response.ChangeStreamNameReqeust
import com.example.dailymemo.OpenStream.Retrofit.Response.DiaryResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.LikeResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.OpenStreamResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.RemoveCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.ShowCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.WriteCommentResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.changeProfileImageResponse
import com.example.dailymemo.OpenStream.Retrofit.Response.changeStreamNameResponse
import com.example.dailymemo.Setting.SettingView
import com.example.dailymemo.Setting.StreamNameView
import com.example.dailymemo.WatchStream.Comment.CommentView
import com.example.dailymemo.WatchStream.WatchStreamView
import com.example.dailymemo.getRetrofit
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class OpenStreamService {
    private lateinit var openStreamView: OpenStreamView
    private lateinit var watchStreamView : WatchStreamView
    private lateinit var commentView : CommentView
    private lateinit var settingView : SettingView
    private lateinit var streamNameView : StreamNameView

    fun setOpenStreamView(openStreamView: OpenStreamView) {
        this.openStreamView = openStreamView
    }

    fun setWatchStreamView(watchStreamView: WatchStreamView) {
        this.watchStreamView = watchStreamView
    }

    fun setCommentView(commentView: CommentView) {
        this.commentView = commentView
    }

    fun setSettingView(settingView: SettingView) {
        this.settingView = settingView
    }

    fun setStreamNameVIew(streamNameView: StreamNameView) {
        this.streamNameView = streamNameView
    }

    fun showOpenStream(jwt : String?, page : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)


        openStreamService.showOpenStream("Bearer "+jwt,page).enqueue(object: Callback<OpenStreamResponse> {
            override fun onResponse(
                call: Call<OpenStreamResponse>,
                response: Response<OpenStreamResponse>
            ) {
                if (response.code() == 200) {
                    openStreamView.showOpenStreamSuccess(response.body()!!)

                }
            }
            override fun onFailure(call: Call<OpenStreamResponse>, t: Throwable) {

            }

        })
    }


    fun search(jwt: String? ,query : String, page : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)


        openStreamService.search("Bearer "+jwt,query, page).enqueue(object: Callback<OpenStreamResponse> {
            override fun onResponse(
                call: Call<OpenStreamResponse>,
                response: Response<OpenStreamResponse>
            ) {
                if (response.code() == 200) {
                    openStreamView.searchOpenStreamSuccess(response.body()!!)
                }
            }

            override fun onFailure(call: Call<OpenStreamResponse>, t: Throwable) {

            }
        })
    }

    fun showDiary(post_id : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)


        openStreamService.showDiary(post_id).enqueue(object: Callback<DiaryResponse> {

            override fun onResponse(call: Call<DiaryResponse>, response: Response<DiaryResponse>) {

                if (response.code() == 200) {
                    watchStreamView.showDiarySuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<DiaryResponse>, t: Throwable) {

            }
        })
    }

    fun like(jwt: String?, post_id : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.like("Bearer "+jwt, post_id).enqueue(object: Callback<LikeResponse> {

            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {

                if (response.code() == 200) {
                    watchStreamView.onLikeBtnClickSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {

            }
        })
    }

    fun writeComment(jwt: String? ,postId: Int, detail :String) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.writeComment("Bearer "+jwt, postId, detail).enqueue(object: Callback<WriteCommentResponse> {

            override fun onResponse(
                call: Call<WriteCommentResponse>,
                response: Response<WriteCommentResponse>
            ) {
                if (response.code() == 200) {
                    commentView.writeCommentSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<WriteCommentResponse>, t: Throwable) {

            }
        })
    }

    fun showComment(jwt: String?,postId : Int, page : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.showComment("Bearer "+jwt, postId, page).enqueue(object: Callback<ShowCommentResponse> {

            override fun onResponse(
                call: Call<ShowCommentResponse>,
                response: Response<ShowCommentResponse>
            ) {
                if (response.code() == 200) {
                    Log.d("comment", "showcomment")
                    commentView.showCommentSuccess(response.body()!!)
                }
                else {
                    Log.d("comment", response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<ShowCommentResponse>, t: Throwable) {

            }
        })
    }

    fun removeComment(jwt: String?, commentId : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.removeComment("Bearer "+jwt, commentId).enqueue(object: Callback<RemoveCommentResponse> {

            override fun onResponse(
                call: Call<RemoveCommentResponse>,
                response: Response<RemoveCommentResponse>
            ) {
                if (response.code() == 200) {
                    commentView.removeCommentSuccess(commentId)
                }
            }

            override fun onFailure(call: Call<RemoveCommentResponse>, t: Throwable) {

            }
        })
    }

    fun changeComment(jwt: String?, commentId : Int, detail : String) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.changeComment("Bearer "+jwt, commentId, detail).enqueue(object: Callback<ChangeCommentResponse> {

            override fun onResponse(
                call: Call<ChangeCommentResponse>,
                response: Response<ChangeCommentResponse>
            ) {
                if (response.code() == 200) {
                    commentView.changeCommentSuccess(response.body()!!.result, response.body()!!.result.detail)
                }
            }

            override fun onFailure(call: Call<ChangeCommentResponse>, t: Throwable) {

            }
        })
    }

    fun changeProfileImage(jwt: String?, profileRequest: ChangeProfileRequest) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)

        openStreamService.changeProfileImage("Bearer "+jwt, profileRequest).enqueue(object : Callback<changeProfileImageResponse>{
            override fun onResponse(
                call: Call<changeProfileImageResponse>,
                response: Response<changeProfileImageResponse>
            ) {
                if(response.code() == 200) {
                    settingView.changeProfileSuccess()
                }
                else {
                    Log.d("changeProfile", response.code().toString()+response.message())
                }
            }

            override fun onFailure(call: Call<changeProfileImageResponse>, t: Throwable) {

            }

        } )
    }

    fun changeStreamName(jwt: String?, keyword: String, streamId : Int) {
        val openStreamService = getRetrofit().create(OpenStreamRetrofitInterface::class.java)
        val request = ChangeStreamNameReqeust(keyword, streamId)
        openStreamService.changeStreamName("Bearer "+ jwt, request).enqueue(object : Callback<changeStreamNameResponse>{
            override fun onResponse(
                call: Call<changeStreamNameResponse>,
                response: Response<changeStreamNameResponse>
            ) {
                if(response.code() == 200) {
                    streamNameView.changeStreamNameSuccess(keyword)
                }
                else {
                    Log.d("changeStreamName", response.code().toString()+response.message())
                }
            }

            override fun onFailure(call: Call<changeStreamNameResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}