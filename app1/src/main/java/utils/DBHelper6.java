package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

import bean.UserBean;
import bean.gouwu3;
import bean.h5gouwu;

/**
 * Created by misshu on 2017/5/10/010.
 */
public class DBHelper6 extends OrmLiteSqliteOpenHelper {
    public DBHelper6(Context context) {
        super(context, "vip5.db", null, 15);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表

        try {
            TableUtils.createTableIfNotExists(connectionSource, h5gouwu.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, h5gouwu.class, true);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }
    //增加
    public void insert(h5gouwu h5gou) {

        try {
            getDao(h5gouwu.class).create(h5gou);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }
    //删除
    public void delete(int id) {

        try {
            DeleteBuilder<h5gouwu, Integer> builder = (DeleteBuilder<h5gouwu, Integer>) getDao(h5gouwu.class).deleteBuilder();
            builder.where().eq("_id", id);
            builder.delete();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    //更新
    public void updata(h5gouwu gou1, int id) {

        //更新指定列中的值

        try {
            UpdateBuilder<UserBean, Integer> builder = (UpdateBuilder<UserBean, Integer>) getDao(UserBean.class)
                    .updateBuilder();
            builder.where().eq("_id", id);
            builder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }



    }
    //查询
    public List<h5gouwu> query(String bigtitle) {
        List<h5gouwu> list = null;
        try {
            QueryBuilder<h5gouwu, Integer> builder = (QueryBuilder<h5gouwu, Integer>) getDao(h5gouwu.class).queryBuilder();
            list = builder.where().like("bigtitle",bigtitle).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //查询所有
    public List<h5gouwu> queryAll() {
        List<h5gouwu> list = null;

        try {
            list = getDao(h5gouwu.class).queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
