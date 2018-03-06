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
import bean.gouwu1;
import bean.gouwu2;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class DBHelper4 extends OrmLiteSqliteOpenHelper {
    public DBHelper4(Context context) {
        super(context, "vip3.db", null, 13);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表

        try {
            TableUtils.createTableIfNotExists(connectionSource, gouwu2.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, gouwu2.class, true);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }
    //增加
    public void insert(gouwu2 gou) {

        try {
            getDao(gouwu2.class).create(gou);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }
    //删除
    public void delete(int id) {

        try {
            DeleteBuilder<gouwu2, Integer> builder = (DeleteBuilder<gouwu2, Integer>) getDao(gouwu2.class).deleteBuilder();
            builder.where().eq("_id", id);
            builder.delete();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    //更新
    public void updata(gouwu2 gou1, int id) {

        //更新指定列中的值

        try {
            UpdateBuilder<UserBean, Integer> builder = (UpdateBuilder<UserBean, Integer>) getDao(UserBean.class)
                    .updateBuilder();
            builder.updateColumnValue("picture", gou1.getPicture());
            builder.updateColumnValue("bigtitle", gou1.getBigtitle());
            builder.updateColumnValue("nowprice",  gou1.getNowprice());
            builder.updateColumnValue("previousprice",  gou1.getPreviousprice());

            builder.where().eq("_id", id);
            builder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }



    }
    //查询
    public List<gouwu2> query(String bigtitle) {
        List<gouwu2> list = null;
        try {
            QueryBuilder<gouwu2, Integer> builder = (QueryBuilder<gouwu2, Integer>) getDao(gouwu2.class).queryBuilder();
            list = builder.where().like("bigtitle",bigtitle).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //查询所有
    public List<gouwu2> queryAll() {
        List<gouwu2> list = null;

        try {
            list = getDao(gouwu2.class).queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
