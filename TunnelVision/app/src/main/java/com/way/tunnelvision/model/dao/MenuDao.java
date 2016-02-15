package com.way.tunnelvision.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.way.tunnelvision.model.MenuModel;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * DAO for table "MenuModel".
 * <p>
 * Created by pc on 2016/2/15.
 */
public class MenuDao extends AbstractDao<MenuModel, Long> {
    public static final String TABLENAME = "MENUMODEL";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MenuGUID = new Property(1, String.class, "text", false, "MENUGUID");
        public final static Property MenuTitle = new Property(1, String.class, "text", false, "MENUTITLE");
        public final static Property MenuLink = new Property(1, String.class, "text", false, "MENULINK");

        //public final static Property Text = new Property(1, String.class, "text", false, "TEXT");
        //public final static Property Comment = new Property(2, String.class, "comment", false, "COMMENT");
        //public final static Property Date = new Property(3, java.util.Date.class, "date", false, "DATE");
    }

    public MenuDao(DaoConfig config) {
        super(config);
    }

    public MenuDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"MENUMODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"MENUGUID\" TEXT NOT NULL ," + // 1: guid
                "\"MENUTITLE\" TEXT NOT NULL," + // 2: title
                "\"MENULINK\" TEXT NOT NULL);"); // 3: link
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MENUMODEL\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, MenuModel entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String guid = entity.getMenuGUID();
        if (guid != null) {
            stmt.bindString(2, guid);
        }

        String title = entity.getMenuTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }

        String link = entity.getMenuLink();
        if (link != null) {
            stmt.bindString(4, link);
        }

//        java.util.Date date = entity.getDate();
//        if (date != null) {
//            stmt.bindLong(4, date.getTime());
//        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public MenuModel readEntity(Cursor cursor, int offset) {
        MenuModel entity = new MenuModel( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // guid
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // link
                //cursor.getString(offset + 1), // guid
                //cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)) // date
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, MenuModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMenuGUID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMenuTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMenuLink(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        //entity.setText(cursor.getString(offset + 1));
        //entity.setComment(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        //entity.setDate(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(MenuModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(MenuModel entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
