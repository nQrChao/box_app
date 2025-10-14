package com.zqhy.app.db.table.bipartition;


import android.text.TextUtils;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class BipartitionGameDbInstance {

    private static volatile BipartitionGameDbInstance instance;

    private BipartitionGameDbInstance() {
    }

    public static BipartitionGameDbInstance getInstance() {
        if (instance == null) {
            synchronized (BipartitionGameDbInstance.class) {
                if (instance == null) {
                    instance = new BipartitionGameDbInstance();
                }
            }
        }
        return instance;
    }


    /**
     * 保存双开空间游戏记录
     *
     * @param bipartitionGameVo
     * @return
     */
    private boolean saveBipartitionGame(BipartitionGameVo bipartitionGameVo) {
        if (bipartitionGameVo == null) {
            return false;
        }
        ModelAdapter<BipartitionGameVo> adapter = FlowManager.getModelAdapter(BipartitionGameVo.class);
        return adapter.save(bipartitionGameVo);
    }

    /**
     * 修改双开空间游戏记录（更新打开时间）
     *
     * @param bipartitionGameVo
     * @return
     */
    public boolean changeBipartitionGame(BipartitionGameVo bipartitionGameVo) {
        if (bipartitionGameVo == null) {
            return false;
        }
        BipartitionGameVo gameVo = SQLite.select().from(BipartitionGameVo.class).where(BipartitionGameVo_Table.package_name.eq(bipartitionGameVo.getPackage_name())).querySingle();

        if (gameVo != null) {
            //更新
            gameVo.setAdd_time(System.currentTimeMillis());
            return gameVo.update();
        } else {
            //新增
            bipartitionGameVo.setAdd_time(System.currentTimeMillis());
            return saveBipartitionGame(bipartitionGameVo);
        }
    }

    /**
     * 根据包名检测是否存在游戏记录
     * @param package_name
     * @return
     */
    public boolean checkBipartitionGame(String package_name) {
        if (TextUtils.isEmpty(package_name)) {
            return false;
        }
        BipartitionGameVo gameVo = SQLite.select().from(BipartitionGameVo.class).where(BipartitionGameVo_Table.package_name.eq(package_name)).querySingle();

        if (gameVo != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据包名获取游戏记录
     * @param package_name
     * @return
     */
    public BipartitionGameVo getBipartitionGame(String package_name) {
        if (TextUtils.isEmpty(package_name)) {
            return null;
        }
        BipartitionGameVo gameVo = SQLite.select().from(BipartitionGameVo.class).where(BipartitionGameVo_Table.package_name.eq(package_name)).querySingle();

        if (gameVo != null) {
            return gameVo;
        } else {
            return null;
        }
    }

    /**
     * 获取双开空间游戏记录列表
     * add_time倒叙
     */
    public List<BipartitionGameVo> getBipartitionGameList() {
        return SQLite.select()
                .from(BipartitionGameVo.class)
                .orderBy(OrderBy.fromNameAlias(NameAlias.of("add_time")).descending())
                .queryList();
    }

    /**
     * 清空双开空间游戏记录
     */
    public boolean deleteAllBipartitionGame() {
        try {
            SQLite.delete().from(BipartitionGameVo.class).query();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除双开空间游戏记录
     *
     * @param package_name
     * @return
     */
    public boolean deleteBipartitionGameItem(String package_name) {
        if (TextUtils.isEmpty(package_name)) {
            return false;
        }
        BipartitionGameVo gameVo = SQLite.select().from(BipartitionGameVo.class).where(BipartitionGameVo_Table.package_name.eq(package_name)).querySingle();
        if (gameVo != null){
            ModelAdapter<BipartitionGameVo> adapter = FlowManager.getModelAdapter(BipartitionGameVo.class);
            return adapter.delete(gameVo);
        }else{
            return false;
        }
    }
}
