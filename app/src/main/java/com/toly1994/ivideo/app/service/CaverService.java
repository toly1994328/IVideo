//package com.toly1994.ivideo.app.service;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.support.annotation.Nullable;
//import com.toly1994.ivideo.model.CacheCaver;
//import com.toly1994.ivideo.model.VideoInfo;
//import com.toly1994.ivideo.model.VideoScanner;
//
//import java.util.List;
//
///**
// * 作者：张风捷特烈<br/>
// * 时间：2019/4/1/001:19:07<br/>
// * 邮箱：1981462002@qq.com<br/>
// * 说明：保存视频封面图的IntentService
// */
//public class CaverService extends IntentService {
//
//    public CaverService(String name) {
//        super(name);
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        List<VideoInfo> infos = VideoScanner.loadVideo(this);
//        CacheCaver caver = new CacheCaver(infos);
//        caver.save(()->{
//                mAdapter.notifyDataSetChanged();
//        });
//    }
//}
