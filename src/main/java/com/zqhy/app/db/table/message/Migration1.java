package com.zqhy.app.db.table.message;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.zqhy.app.db.AppDatabase;

/**
 * @author Administrator
 * @date 2018/11/11
 */
@Migration(version = AppDatabase.VERSION, database = AppDatabase.class)
public class Migration1 extends AlterTableMigration<MessageVo> {
    public Migration1(Class<MessageVo> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
    }
}
