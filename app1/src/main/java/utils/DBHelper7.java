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
import bean.h5gouwu;
import bean.showtime;

/**
 * Created by misshu on 2017/7/12/012.
 */
public class DBHelper7 extends OrmLiteSqliteOpenHelper {
    public DBHelper7(Context context) {
        super(context, "vip6.db", null, 16);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表

        try {
            TableUtils.createTableIfNotExists(connectionSource, showtime.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, showtime.class, true);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }
    //增加
    public void insert(showtime show) {

        try {
            getDao(showtime.class).create(show);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }
    //删除
    public void delete(int id) {

        try {
            DeleteBuilder<showtime, Integer> builder = (DeleteBuilder<showtime, Integer>) getDao(showtime.class).deleteBuilder();
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
    public List<showtime> query(String bigshow) {
        List<showtime> list = null;
        try {
            QueryBuilder<showtime, Integer> builder = (QueryBuilder<showtime, Integer>) getDao(showtime.class).queryBuilder();
            list = builder.where().like("bigshow",bigshow).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //查询所有
    public List<showtime> queryAll() {
        List<showtime> list = null;

        try {
            list = getDao(showtime.class).queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
