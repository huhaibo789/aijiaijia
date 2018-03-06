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

import bean.Food;
import bean.UserBean;
import bean.gouwu;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class DBHelper2 extends OrmLiteSqliteOpenHelper {
    public DBHelper2(Context context) {
        super(context, "vip1.db", null, 11);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表

        try {
            TableUtils.createTableIfNotExists(connectionSource, gouwu.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, gouwu.class, true);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }
    //增加
    public void insert(gouwu gou) {

        try {
            getDao(gouwu.class).create(gou);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }
    //删除
    public void delete(int id) {

        try {
            DeleteBuilder<gouwu, Integer> builder = (DeleteBuilder<gouwu, Integer>) getDao(gouwu.class).deleteBuilder();
            builder.where().eq("_id", id);
            builder.delete();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    //更新
    public void updata(gouwu gou, int id) {

        //更新指定列中的值

        try {
            UpdateBuilder<UserBean, Integer> builder = (UpdateBuilder<UserBean, Integer>) getDao(UserBean.class)
                    .updateBuilder();
            builder.updateColumnValue("picture", gou.getPicture());
            builder.updateColumnValue("bigtitle", gou.getBigtitle());
            builder.updateColumnValue("nowprice",  gou.getNowprice());
            builder.updateColumnValue("previousprice",  gou.getPreviousprice());

            builder.where().eq("_id", id);
            builder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }



    }
    //查询
    public List<gouwu> query(String bigtitle) {
        List<gouwu> list = null;
        try {
            QueryBuilder<gouwu, Integer> builder = (QueryBuilder<gouwu, Integer>) getDao(gouwu.class).queryBuilder();
            list = builder.where().like("bigtitle",bigtitle).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //查询所有
    public List<gouwu> queryAll() {
        List<gouwu> list = null;

        try {
            list = getDao(gouwu.class).queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
