package com.way.tunnelvision.entity.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.way.tunnelvision.entity.model.ChannelModel;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by pc on 2016/2/20.
 */
public class ChannelDao extends AbstractDao<ChannelModel, Long> {
    public static final String TABLENAME = "CHANNELMODEL";
    public static final String COLUMNNAME_ID = "_id";
    public static final String COLUMNNAME_GUID = "CHANNELGUID";
    public static final String COLUMNNAME_TITLE = "CHANNELTITLE";
    public static final String COLUMNNAME_NAME = "CHANNELNAME";
    public static final String COLUMNNAME_LINK = "CHANNELLINK";
    public static final String COLUMNNAME_TYPE = "CHANNELTYPE";
    public static final String COLUMNNAME_CHOSEN = "CHANNELCHOSEN";

    private static final String PROPERTYNAME_ID = "id";
    private static final String PROPERTYNAME_GUID = "guid";
    private static final String PROPERTYNAME_TITLE = "title";
    private static final String PROPERTYNAME_NAME = "name";
    private static final String PROPERTYNAME_LINK = "link";
    private static final String PROPERTYNAME_TYPE = "type";
    private static final String PROPERTYNAME_CHOSEN = "chosen";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, PROPERTYNAME_ID, true, COLUMNNAME_ID);
        public final static Property Channel_GUID = new Property(1, String.class, PROPERTYNAME_GUID, false, COLUMNNAME_GUID);
        public final static Property Channel_Title = new Property(2, String.class, PROPERTYNAME_TITLE, false, COLUMNNAME_TITLE);
        public final static Property Channel_Name = new Property(3, String.class, PROPERTYNAME_NAME, false, COLUMNNAME_NAME);
        public final static Property Channel_Link = new Property(4, String.class, PROPERTYNAME_LINK, false, COLUMNNAME_LINK);
        public final static Property Channel_Type = new Property(5, String.class, PROPERTYNAME_TYPE, false, COLUMNNAME_TYPE);
        public final static Property Channel_Chosen = new Property(6, Integer.class, PROPERTYNAME_CHOSEN, false, COLUMNNAME_CHOSEN);
    }

    public ChannelDao(DaoConfig config) {
        super(config);
    }

    public ChannelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"" + TABLENAME + "\" (" + //
                "\"" + COLUMNNAME_ID + "\" INTEGER PRIMARY KEY," + // 0: id
                "\"" + COLUMNNAME_GUID + "\" TEXT NOT NULL," + // 1: guid
                "\"" + COLUMNNAME_TITLE + "\" TEXT NOT NULL," + // 2: title
                "\"" + COLUMNNAME_NAME + "\" TEXT NOT NULL," + // 3: name
                "\"" + COLUMNNAME_LINK + "\" TEXT NOT NULL," + // 4: link
                "\"" + COLUMNNAME_TYPE + "\" INTEGER NOT NULL," + // 5: type
                "\"" + COLUMNNAME_CHOSEN + "\" INTEGER NOT NULL);"); // 6: chosen
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"" + TABLENAME + "\"";
        db.execSQL(sql);
    }

    @Override
    protected ChannelModel readEntity(Cursor cursor, int offset) {
        ChannelModel entity = new ChannelModel( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // guid
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // link
                cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // type
                cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6) // chosen
                //cursor.getString(offset + 1), // guid
                //cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)) // date
        );
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, ChannelModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setChannelGUID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setChannelTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setChannelName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setChannelLink(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setChannelType(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setChannelChosen(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        //entity.setText(cursor.getString(offset + 1));
        //entity.setComment(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        //entity.setDate(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, ChannelModel entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String guid = entity.getChannelGUID();
        if (guid != null) {
            stmt.bindString(2, guid);
        }

        String title = entity.getChannelTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }

        String name = entity.getChannelName();
        if (name != null) {
            stmt.bindString(4, name);
        }

        String link = entity.getChannelLink();
        if (link != null) {
            stmt.bindString(5, link);
        }

        int type = entity.getChannelType();
        if (type >= 0) {
            stmt.bindLong(6, type);
        }

        int chosen = entity.getChannelChosen();
        if (chosen >= 0) {
            stmt.bindLong(7, chosen);
        }
    }

    @Override
    protected Long updateKeyAfterInsert(ChannelModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(ChannelModel entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
