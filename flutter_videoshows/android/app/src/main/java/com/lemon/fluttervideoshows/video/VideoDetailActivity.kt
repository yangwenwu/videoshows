package com.lemon.fluttervideoshows.video

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayer.*
import cn.jzvd.JZVideoPlayerStandard
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.lemon.fluttervideoshows.R
import com.lemon.fluttervideoshows.application.BaseApp
import com.lemon.fluttervideoshows.application.GlideApp
import com.lemon.fluttervideoshows.http.common.Commrequest
import com.lemon.fluttervideoshows.http.common.HttpConstants
import com.lemon.fluttervideoshows.http.httpModel.*
import com.lemon.fluttervideoshows.http.https.ResponseListener
import com.lemon.fluttervideoshows.utils.*
import com.lemon.fluttervideoshows.video.MediaPlayer.JZExoPlayer
import com.lemon.fluttervideoshows.video.adapter.CommentRecycleAdapter
import com.lemon.fluttervideoshows.video.adapter.RecommendRecycleAdapter
import kotlinx.android.synthetic.main.video_detail_addheardview.*
import kotlinx.android.synthetic.main.video_detail_comment_bt_bar.*
import kotlinx.android.synthetic.main.video_detail_footbar.*
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList
import kotlinx.android.synthetic.main.video_detail_layout.*

class VideoDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var subjectCode: String? = null
    private var bigTitleImage: String? = null
    private var title: String? = null
    private var titleImage: String? = null
    private var dataId: String? = null
    private var jsonUrl: String? = null
    private var description: String? = null


//    private var homeNewsBean: HomeNewsBean? = null
    private var reCommentAdapter: RecommendRecycleAdapter? = null
    private var commentListAdapter: CommentRecycleAdapter? = null
    private val videoList = ArrayList<HomeNewsBean>()
    private val commentBeanList = ArrayList<CommentBean>()
    private var url: String? = null
    private var totalCount = 0
    private var page = 1
    private var totalPage = 1
    private var isBookmark = false
    private var ytbUrl: String? = null
    private var txyUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_detail_layout)
        subjectCode = intent.getStringExtra("subjectCode")
        title = intent.getStringExtra("title")
        bigTitleImage = intent.getStringExtra("bigTitleImage")
        titleImage = intent.getStringExtra("titleImage")
        dataId = intent.getStringExtra("dataId")
        jsonUrl = intent.getStringExtra("jsonUrl")
        description = intent.getStringExtra("description")
//        homeNewsBean = intent.getSerializableExtra("bean") as HomeNewsBean
        initView()
    }

    private fun initView() {
        val mgr = assets
        //根据路径得到Typeface
        val tf = Typeface.createFromAsset(mgr, "fonts/Lato-Bold.ttf")
        val tf2 = Typeface.createFromAsset(mgr, "fonts/Lato-Regular.ttf")
        val tf4 = Typeface.createFromAsset(mgr, "fonts/Lato-Light.ttf")
        video_title.typeface = tf4
        des.typeface = tf2
        subject.typeface = tf
        publish_time.typeface = tf2
        recommend.typeface = tf2

        //推荐列表
        rec_recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        reCommentAdapter = RecommendRecycleAdapter(this, videoList)
        rec_recyclerview.adapter = reCommentAdapter
        reCommentAdapter!!.setOnItemClickListener(itemClickListener)

        scrollView.smoothScrollTo(0,0)
//        video_title.
        //评论列表
        comment_recyclerview.layoutManager = LinearLayoutManager(this)
        commentListAdapter = CommentRecycleAdapter(this, commentBeanList)
        comment_recyclerview.adapter = commentListAdapter

        classicheader.setEnableLastTime(false)
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false)
        refreshLayout.setOnRefreshListener {
            page = 1
            refreshLayout.isEnableLoadMore = true
            queryCommentList(1)
        }
        refreshLayout.setOnLoadMoreListener {
            queryCommentList(page)
        }

        like_line.setOnClickListener(this)
        collect_line.setOnClickListener(this)
        comment_line.setOnClickListener(this)
        share_line.setOnClickListener(this)
        comment_close.setOnClickListener(this)
        comment_bt.setOnClickListener(this)

        if (bigTitleImage != null && bigTitleImage != "") {
            GlideApp.with(this)
                    .load(HttpConstants.SERVICEURL + StringUrlUtil.checkSeparator(bigTitleImage))
                    .centerCrop()
                    .dontAnimate()
                    .into(videoplayer.thumbImageView)
        } else {
            if (titleImage != null && titleImage != "") {
                GlideApp.with(this)
                        .load(HttpConstants.SERVICEURL + StringUrlUtil.checkSeparator(titleImage))
                        .centerCrop()
                        .dontAnimate()
                        .into(videoplayer.thumbImageView)
            }
        }

        videoplayer.layoutParams = LinearLayoutCompat.LayoutParams(DeviceUtils.width(), DeviceUtils.width() * 9 / 16)
        JZVideoPlayer.setMediaInterface(JZExoPlayer())
        videoplayer.mRetryLayout.visibility = View.GONE
        videoplayer.startButton.visibility = View.GONE
        videoplayer.loadingProgressBar.visibility = View.VISIBLE
        videoplayer.batteryTimeLayout.visibility = View.GONE
        videoplayer.thumbImageView.setOnClickListener { null }
        videoplayer.backButton.setOnClickListener {
            if (videoplayer.currentScreen == SCREEN_WINDOW_NORMAL) {
                finish()
            }
        }

        getData()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.collect_line //收藏
            -> if (QuickClick2TimesUtil.isFastClick()) {
                if (BaseApp.isLogin) {
                    if (isBookmark) {
                        cancelCollect()
                    } else {
                        addCollect()
                    }
                } else {
                    hideKeyBoard()
//                    val intent = Intent(this@VideoDetailActivity, LoginAndRegisterActivity::class.java)
//                    startActivity(intent)
                }
            }
            R.id.like_line //点赞
            -> if (QuickClick2TimesUtil.isFastClick()) {
                if (!NetWorkUtil.isNetworkAvailable(this@VideoDetailActivity)) {
//                    ToastUtils.showShort(this@VideoDetailActivity, resources.getString(R.string.net_error))
                    like_img.setImageResource(R.mipmap.detail_heart_selected)
                    like_img.isEnabled = false
                    return
                }
                //点赞
                setGood(dataId)
            }
            R.id.comment_line //评论
            -> if (QuickClick2TimesUtil.isFastClick()) {
                comment_layout.visibility = View.VISIBLE
                news_detail_commentbar.visibility = View.VISIBLE
            }
            R.id.share_line //分享
            -> {
                if (videoplayer.currentState == CURRENT_STATE_PLAYING) {
                    videoplayer.onStatePause()
                }
                if (QuickClick2TimesUtil.isFastClick()) {
//                    if (!NetWorkUtil.isNetworkAvailable(this@VideoDetailActivity)) {
//                        ToastUtils.showShort(this@VideoDetailActivity, resources.getString(R.string.net_error))
//                        return
//                    }
                    GlideApp.with(this@VideoDetailActivity)
                            .asFile()
                            .load(HttpConstants.SERVICEURL + bigTitleImage)
                            .error(R.drawable.ic_launcher)
                            .override(160, 90)
                            .into<SimpleTarget<File>>(object : SimpleTarget<File>() {
                                override fun onResourceReady(resource: File, transition: Transition<in File>?) {
//                                    ShareDialog(this@VideoDetailActivity, homeNewsBean!!.title, homeNewsBean!!.description, resource, "https://www.chinadailyhk.com${homeNewsBean!!.htmlUrl}?newsId=${homeNewsBean!!.dataId}&play_url=${homeNewsBean!!.jsonUrl}").show()
//                                    ShareDialog(this@VideoDetailActivity, homeNewsBean!!.title, homeNewsBean!!.description, resource, "https://www.chinadailyhk.com/subjects/video/2725/index.html?play_url=" + homeNewsBean!!.jsonUrl).show()
                                }
                            })
                }


//                GlideApp.with(this@VideoDetailActivity)
//                        .asFile()
//                        .load(HttpConstants.SERVICEURL + homeNewsBean!!.bigTitleImage)
//                        .error(R.mipmap.ic_launcher)
//                        .override(160, 90)
//                        .fitCenter()
//                        .into(object : SimpleTarget<File>() {
//                            override fun onResourceReady(resource: File, transition: Transition<in File>?) {
//                                ShareDialog(this@VideoDetailActivity, homeNewsBean!!.title, homeNewsBean!!.description, HttpConstants.SERVICEURL + homeNewsBean!!.bigTitleImage, resource, "https://www.chinadailyhk.com/subjects/video/2725/index.html?play_url=" + homeNewsBean!!.jsonUrl).show()
//                                //                                    new ShareDialog(VideoMDPlayerDetailsActivity.this, homeNewsBean.title, homeNewsBean.description, HttpConstants.SERVICEURL + homeNewsBean.bigTitleImage, resource, "https://www.chinadailyhk.com"+homeNewsBean.htmlUrl).show();
//                            }
//                        })
            }
            R.id.comment_close -> {
                comment_layout.visibility = View.GONE
                news_detail_commentbar.visibility = View.GONE
            }
            R.id.comment_bt -> if (QuickClick2TimesUtil.isFastClick()) {
                // 进行点击事件后的逻辑操作
                if (BaseApp.isLogin) {
                    var cm_content = edit_content.getText().toString() as String
                    if ( cm_content != "") {
                        hideKeyBoard()
                        comment(cm_content)
                    } else {
                        ToastUtil.show("Please input your comment")
                    }
                } else {
//                    val intent = Intent(this@VideoDetailActivity, LoginAndRegisterActivity::class.java)
//                    startActivity(intent)
                }
            }
        }
    }

    //获取传过来的json地址
    private fun getData() {
        getVideoDetail(dataId) //视频详情
        getLatestData(1, subjectCode!!) //推荐列表
        if (BaseApp.isLogin) {
            //查询是否已经收藏
            val userStr = SPUtils.get(this@VideoDetailActivity, "user", "") as String
            val userBean = JSON.parseObject(userStr, UserBean::class.java)
            val userId = userBean.id
            queryCollectState(userId, dataId)
        }
//        getGoodCount()
        queryCommentCount()//查询评论数量
        queryCommentList(1)//查询评论列表

    }

    /***
     * 查询评论数量
     */
    //查询评论数量
    //http://203.186.80.109/hknews-api/countComment?newsId=12346&type=2
    private fun queryCommentCount() {
        Commrequest.queryCommentCount(this@VideoDetailActivity, dataId, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                //{"resCode":"200","resMsg":"success","resObject":{"newsId":"17647","count":0}}
                val jsonObject = JSON.parseObject(t.`object`)
                val resMsg = jsonObject.getString("resMsg")
                if (resMsg == "success") {
                    val jsonObject1 = jsonObject.getJSONObject("resObject")
                    val count = jsonObject1.getInteger("count")!!
                    if (count == 0) {
                        //                        commentCount.setText("comments");
                        comment_count2.text = "COMMENTS"
                        comment_count2.contentDescription = "comments"
                    } else {
                        //                        commentCount.setText(count + " comments");
                        comment_count2.text = count.toString() + " COMMENTS"
                        comment_count2.contentDescription = count.toString() + " COMMENTS"
                    }

                }
            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {

            }
        })
    }

    /**
     * 获取点赞数
     */
    private fun getGoodCount() {
        Commrequest.getGoodCount(this@VideoDetailActivity, dataId, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                val jsonObject = JSON.parseObject(t.`object`)
                val idInfo = jsonObject.getJSONObject("resObject")
                val goodCount = idInfo.getIntValue("count")
                if (goodCount == 0) {
                    //                    likeCount.setText("like");
                } else {
                    //                    likeCount.setText(String.valueOf(goodCount)+" likes");
                }
            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {

            }
        })
    }

    private fun queryCollectState(userId: String, dataId: String?) {
        Commrequest.qureyIsCollect(this@VideoDetailActivity, userId, dataId, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                val jsonObject = JSON.parseObject(t.`object`)
                val resObject = jsonObject.getJSONObject("resObject")
                val isCollect = resObject.getBoolean("isCollect")!!
                if (isCollect) {
                    collect_img.setImageResource(R.mipmap.detail_bookmark_selected)
                    isBookmark = true
                } else {
                    collect_img.setImageResource(R.mipmap.detail_bookmark)
                    isBookmark = false
                }
            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {

            }
        })
    }

    /***
     * 评论列表
     */
    private fun queryCommentList(currentPage: Int) {
        Commrequest.queryCommentList(this@VideoDetailActivity, dataId, currentPage, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                val jsonObject = JSON.parseObject(t.`object`)
                val resMsg = jsonObject.getString("resMsg")
                if (resMsg == "success") {
                    val jsonObjectObj = jsonObject.getJSONObject("resObject")
                    val jsonArray = jsonObjectObj.getJSONArray("dateList")
                    if (jsonArray.size > 0) {
                        totalCount = jsonObjectObj.getIntValue("totalCount")
                        totalPage = jsonObjectObj.getIntValue("totalPage")
                        if (currentPage == 1) {
                            comment_empty.visibility = View.GONE
                            commentBeanList.clear()
                        }
                        if (jsonArray.size >=10){
                            page ++
                        }
                        val msg = Message()
                        msg.obj = jsonArray
                        msg.what = 3
                        handler.sendMessage(msg)
                    } else {
                        if (currentPage == 1) {
                            comment_empty.visibility = View.VISIBLE
                        }
                    }
                }
                refreshLayout.finishRefresh()
                refreshLayout.finishLoadMore()
            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {
                refreshLayout.finishRefresh()
                refreshLayout.finishLoadMore()
            }
        })
    }


    /***
     * 点赞
     * @param dataId
     */
    //点赞
    private fun setGood(dataId: String?) {  //  newsId=传dataId
        Commrequest.setGood(this@VideoDetailActivity, dataId, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                //返回结果
                //{"resCode":"200","resMsg":"success","resObject":null}

                val jsonObject = JSON.parseObject(t.`object`)
                val resMsg = jsonObject.getString("resMsg")
                if (resMsg == "success") {
                    like_img.setImageResource(R.mipmap.detail_heart_selected)
                    like_img.isEnabled = false
                    getGoodCount()
                }
            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {
            }
        })
    }

    /****
     * 取消收藏
     */
    private fun cancelCollect() {
        val userStr = SPUtils.get(this@VideoDetailActivity, "user", "") as String
        val userBean = JSON.parseObject(userStr, UserBean::class.java)
        val userId = userBean.id
        Commrequest.cancelCollection(this@VideoDetailActivity, userId, dataId, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                //返回结果
                //{"resCode":"200","resMsg":"success","resObject":null}

                val jsonObject = JSON.parseObject(t.`object`)
                val resMsg = jsonObject.getString("resMsg")
                if (resMsg == "success") {
                    collect_img.setImageResource(R.mipmap.detail_bookmark)
                    //                    like_img.setEnabled(false);
                    isBookmark = false
                }

            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {

            }
        })
    }

    /***
     * 添加收藏
     */
    private fun addCollect() {
        val userStr = SPUtils.get(this@VideoDetailActivity, "user", "") as String
        val userBean = JSON.parseObject(userStr, UserBean::class.java)
        val userId = userBean.id
        Commrequest.addCollection(this@VideoDetailActivity, userId, dataId, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                //返回结果
                //{"resCode":"200","resMsg":"success","resObject":null}

                val jsonObject = JSON.parseObject(t.`object`)
                val resMsg = jsonObject.getString("resMsg")
                if (resMsg == "success") {
                    collect_img.setImageResource(R.mipmap.detail_bookmark_selected)
                    //                    like_img.setEnabled(false);
                    isBookmark = true
                }

            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {

            }
        })
    }

    private val urlHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            clearSavedProgress(BaseApp.getInstance(), url)
            videoplayer.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, title)
            videoplayer.startVideo()
            videoplayer.isEnabled = true
            videoplayer.backButton.visibility = View.VISIBLE
            videoplayer.backButton.contentDescription = "back"
//            videoplayer.backButton.setOnClickListener {
//                onBackPressed()
//            }

        }
    }

    //设置youtube地址
    private fun setYoutubeUrl(pathUrl: String) {
        Thread(Runnable {
            val urlPath: URL
            val map: Map<String, String>?
            try {
                urlPath = URL(pathUrl)
                map = YouTubeParser.h264videosWithYoutubeURL(urlPath)
                url = map!!["medium"]
                if (null != url && url != "") {
                    urlHandler.sendEmptyMessage(1)
                } else {
                    //这里是调试
                    if (txyUrl != null) {
                        url = txyUrl
                        urlHandler.sendEmptyMessage(1)
                    }
                }

            } catch (e: MalformedURLException) {
//                e.printStackTrace()
                url = txyUrl
                urlHandler.sendEmptyMessage(1)
            }
        }).start()
    }


    //获取推荐的数据
    private fun getLatestData(page: Int, code: String) {
        Commrequest.getLatestDataList(this@VideoDetailActivity, code, 1, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                val jsonObject = JSON.parseObject(t.`object`)
                val resObject = jsonObject.getJSONObject("resObject")
                val jsonArray = resObject.getJSONArray("dateList")
                if (jsonArray.size > 0) {
                    for (i in jsonArray.indices) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val bean = JSON.parseObject(jsonObject.toJSONString(), HomeNewsBean::class.java)
                        if (bean.dataId == dataId) {
                        } else {
                            videoList.add(bean)
                        }
                    }
                    reCommentAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {

            }
        })
    }

    private fun getVideoDetail(dataId: String?) {
        Commrequest.getNewsDetail(this@VideoDetailActivity, dataId, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
//                videoplayer.setEnabled(true)
                val jsonObject = JSON.parseObject(t.`object`)
                val resObject = jsonObject.getJSONObject("resObject")
                val msg = Message()
                msg.obj = resObject
                msg.what = 1
                handler.sendMessage(msg)
            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {
//                videoplayer.isEnabled = true
                handler.sendEmptyMessage(2)
            }
        })
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun dispatchMessage(msg: Message) {
            when (msg.what) {
                1 //第一次进来加载，同时也是刷新的时候会调用
                -> {
                    val `object` = msg.obj as JSONObject
                    val videoBean = JSON.parseObject(`object`.toJSONString(), ResObject::class.java)
                    if (videoBean.title != null && videoBean.title != "") {
                        //将多个空格变成单空格
                        video_title.text = videoBean.title.replace(" +".toRegex(), " ")
                    } else {
                        video_title.visibility = View.GONE
                    }

                    if (videoBean.description != null && videoBean.description != "") {
                        //将多个空格变成单空格
                        des.text = videoBean.description.replace(" +".toRegex(), " ")
                    } else {
                        des.visibility = View.GONE
                    }

                    subject.text = videoBean.subjectName.toUpperCase()
                    if (videoBean.publishTime != null && videoBean.publishTime != "") {
                        publish_time.text = videoBean.publishTime.toUpperCase()
                    } else {
                        if (videoBean.fullPublishTime != null && videoBean.fullPublishTime != "") {
                            publish_time.text = videoBean.fullPublishTime.toUpperCase()
                        } else {
                            publish_time.text = ""
                        }
                    }
                    txyUrl = videoBean.txyUrl
                    ytbUrl = videoBean.ytbUrl
                    if (ytbUrl != null && ytbUrl != "") {
                        url = "https://www.youtube.com/watch?v=$ytbUrl"
                        setYoutubeUrl(url!!)
                    } else {
                        url = txyUrl
                        urlHandler.sendEmptyMessage(1)
                    }

//                    if (txyUrl != null && txyUrl != "") {
//                        url = txyUrl
//                        urlHandler.sendEmptyMessage(1)
//                    } else {
//                        //就直接使用ytb播放
//                        url = "https://www.youtube.com/watch?v=$ytbUrl"
//                        setYoutubeUrl(url!!)
//                    }

                    loading_layout.visibility = View.GONE
                    frame_layout1.visibility = View.VISIBLE
                    news_detail_foot_bar.visibility = View.VISIBLE
                }
                2 -> {
                    if (NetWorkUtil.isNetworkAvailable(this@VideoDetailActivity)) {
                        ToastUtils.showShort(this@VideoDetailActivity, resources.getString(R.string.load_fail))
                    } else {
                        ToastUtils.showShort(this@VideoDetailActivity, resources.getString(R.string.net_error))
                    }
                    setErrorView()
                }
                3 -> {
                    val jsonArray = msg.obj as JSONArray
                    if (jsonArray.size > 0) {
                        for (i in jsonArray.indices) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            val bean = JSON.parseObject(jsonObject1.toJSONString(), CommentBean::class.java)
                            commentBeanList.add(bean)
                        }
                    }
                    commentListAdapter!!.notifyDataSetChanged()
                    refreshLayout.isEnableLoadMore = true
                    if (commentBeanList.size == totalCount) {
                        refreshLayout.isEnableLoadMore = false
                    }

                }
            }
        }
    }


    /***
     * 加载失败
     */
    private fun setErrorView() {
        loading_layout.visibility = View.VISIBLE
        loading_progress.visibility = View.GONE
        result_view.visibility = View.VISIBLE
        refresh.visibility = View.VISIBLE
        refresh.setOnClickListener {
            //状态
            loading_progress.visibility = View.VISIBLE
            result_view.visibility = View.GONE
            if (NetWorkUtil.isNetworkAvailable(this@VideoDetailActivity)) {
                getData()
            } else {
                loading_progress.visibility = View.GONE
                result_view.visibility = View.VISIBLE
                ToastUtils.showShort(this@VideoDetailActivity, resources.getString(R.string.net_error))
            }
        }
    }

    //隐藏键盘
    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(edit_content.windowToken, 0)
    }

    /***
     * 评论
     * @param content
     */
    private fun comment(content: String) {
        val userStr = SPUtils.get(this@VideoDetailActivity, "user", "") as String
        val userBean = JSON.parseObject(userStr, UserBean::class.java)
        val userId = userBean.id
        val model = CommentModel()
        model.userId = userId
        model.content = content
        model.newsId = dataId
        model.type = "2"
        Commrequest.addComment(this@VideoDetailActivity, model, object : ResponseListener {
            override fun onResponse(t: BaseJsonBean, code: Int) {
                //返回结果
                //{"resCode":"200","resMsg":"success","resObject":null}

                val jsonObject = JSON.parseObject(t.`object`)
                val resMsg = jsonObject.getString("resMsg")
                if (resMsg == "success") {
                    //发布成功之后就清空
                    edit_content.setText("")
                    //刷新评论列表
                    page = 1
                    queryCommentList(1)
                    queryCommentCount()
                } else {
                    ToastUtil.show("Comment publish failed")
                }

            }

            override fun onFailure(t: BaseJsonBean?, errMessage: String) {
                ToastUtil.show(resources.getString(R.string.net_error))
            }
        })
    }


    internal var itemClickListener = object : RecommendRecycleAdapter.OnItemClickListener {
        override fun OnItemClick(view: View, position: Int) {
            val ttb = videoList[position]//经过具体测试，这里应该是添加了一头和一尾，所以position需要-2
            val subjectCode = ttb.subjectCode
            Goto(subjectCode, ttb, VideoDetailActivity::class.java)
        }

        override fun OnItemLongClick(view: View, position: Int) {

        }
    }

    private fun Goto(code: String, bean: HomeNewsBean, activity: Class<*>) {
        val i = Intent(this@VideoDetailActivity, activity)
        i.putExtra("code", code)
        i.putExtra("bean", bean)
        startActivity(i)
    }

    /**
     * 下面的这几个Activity的生命状态很重要
     */
    override fun onPause() {
        super.onPause()
        try {
            goOnPlayOnPause()
        } catch (e: Exception) {
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        try {
            videoplayer?.release()
        } catch (e: Exception) {
        }
    }

    override fun onBackPressed() {
        if (backPress()) {
            return
        }
        super.onBackPressed()
    }

}

