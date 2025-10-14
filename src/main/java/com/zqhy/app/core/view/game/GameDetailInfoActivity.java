package com.zqhy.app.core.view.game;

import android.os.Bundle;

import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.newproject.R;

import cn.jzvd.Jzvd;


/**
 * @author Administrator
 */
public class GameDetailInfoActivity extends FragmentHolderActivity {

    private int gameid;
    private int game_type;

    @Override
    public void initViews(Bundle savedInstanceState) {
        if(getIntent() != null){
            gameid = getIntent().getIntExtra("gameid",0);
            game_type = getIntent().getIntExtra("game_type",0);
        }
        super.initViews(savedInstanceState);
        loadRootFragment(R.id.content, GameDetailInfoFragment.newInstance(gameid,game_type));
    }

    @Override
    public void onStop() {
        super.onStop();
        Jzvd.releaseAllVideos();
    }
}
