package com.zqhy.app.db.table.search;


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

public class SearchGameDbInstance {

    private static volatile SearchGameDbInstance instance;

    private SearchGameDbInstance() {
    }

    public static SearchGameDbInstance getInstance() {
        if (instance == null) {
            synchronized (SearchGameDbInstance.class) {
                if (instance == null) {
                    instance = new SearchGameDbInstance();
                }
            }
        }
        return instance;
    }


    /**
     * 保存游戏搜索历史
     *
     * @param searchGameVo
     * @return
     */
    private boolean saveSearchHistory(SearchGameVo searchGameVo) {
        if (searchGameVo == null) {
            return false;
        }
        ModelAdapter<SearchGameVo> adapter = FlowManager.getModelAdapter(SearchGameVo.class);
        return adapter.save(searchGameVo);
    }

    /**
     * 添加搜索历史条目（包括更新搜索时间）
     *
     * @param searchGameVo
     * @return
     */
    public boolean addSearchHistory(SearchGameVo searchGameVo) {
        if (searchGameVo == null) {
            return false;
        }
        SearchGameVo gameVo = SQLite.select().from(SearchGameVo.class).where(
                SearchGameVo_Table.gamename.eq(searchGameVo.getGamename()),
                SearchGameVo_Table.search_type.eq(searchGameVo.getSearch_type())).querySingle();

        if (gameVo != null) {
            //更新
            gameVo.setAdd_time(System.currentTimeMillis());
            return gameVo.update();
        } else {
            //新增
            searchGameVo.setAdd_time(System.currentTimeMillis());
            return saveSearchHistory(searchGameVo);
        }
    }

    /**
     * 获取搜索历史列表
     * 搜索条件：
     * search_type分类
     * add_time倒叙
     *
     * @return
     */
    public List<SearchGameVo> getSearchHistoryList(int search_type) {
        return SQLite.select()
                .from(SearchGameVo.class)
                .where(SearchGameVo_Table.search_type.eq(search_type))
                .orderBy(OrderBy.fromNameAlias(NameAlias.of("add_time")).descending())
                .queryList();
    }


    /**
     * 获取搜索历史前十
     *
     * @param search_type
     * @return
     */
    public List<SearchGameVo> getSearchHistoryListByTopTen(int search_type) {
        List<SearchGameVo> searchGameVos = getSearchHistoryList(search_type);
        if (searchGameVos == null) {
            return null;
        }
        if (searchGameVos.size() < 10) {
            return searchGameVos;
        } else {
            return searchGameVos.subList(0, 10);
        }
    }

    /**
     * 获取搜索历史前五
     *
     * @param search_type
     * @return
     */
    public List<SearchGameVo> getSearchHistoryListByTopFive(int search_type) {
        List<SearchGameVo> searchGameVos = getSearchHistoryList(search_type);
        if (searchGameVos == null) {
            return null;
        }
        if (searchGameVos.size() < 5) {
            return searchGameVos;
        } else {
            return searchGameVos.subList(0, 5);
        }
    }


    /**
     * 清空搜索列表
     *
     * @param search_type
     * @return
     */
    public boolean deleteAllSearchHistory(int search_type) {
        try {
            SQLite.delete()
                    .from(SearchGameVo.class)
                    .where(SearchGameVo_Table.search_type.eq(search_type))
                    .query();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除搜索历史条目
     *
     * @param searchGameVo
     * @return
     */
    public boolean deleteSearchHistoryItem(SearchGameVo searchGameVo) {
        if (searchGameVo == null) {
            return false;
        }
        ModelAdapter<SearchGameVo> adapter = FlowManager.getModelAdapter(SearchGameVo.class);
        return adapter.delete(searchGameVo);
    }

}
