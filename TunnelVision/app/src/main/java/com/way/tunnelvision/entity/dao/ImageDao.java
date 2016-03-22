package com.way.tunnelvision.entity.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.way.tunnelvision.entity.model.ImageModel;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by pc on 2016/2/20.
 */
public class ImageDao extends AbstractDao<ImageModel, Long> {
    public static final String TABLENAME = "IMAGEMODEL";
    public static final String COLUMNNAME_ID = "_id";
    public static final String COLUMNNAME_TITLE = "IMAGETITLE";
    public static final String COLUMNNAME_THUMBURL = "IMAGETHUMBURL";
    public static final String COLUMNNAME_SOURCEURL = "IMAGESOURCEURL";
    public static final String COLUMNNAME_HEIGHT = "IMAGEHEIGHT";
    public static final String COLUMNNAME_WIDTH = "IMAGEWIDTH";
    public static final String COLUMNNAME_ISCOLLECTION = "IMAGEISCOLLECTION";

    private static final String PROPERTYNAME_ID = "id";
    private static final String PROPERTYNAME_TITLE = "title";
    private static final String PROPERTYNAME_THUMBURL = "thumburl";
    private static final String PROPERTYNAME_SOURCEURL = "sourceurl";
    private static final String PROPERTYNAME_HEIGHT = "height";
    private static final String PROPERTYNAME_WIDTH = "width";
    private static final String PROPERTYNAME_ISCOLLECTION = "iscollection";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, PROPERTYNAME_ID, true, COLUMNNAME_ID);
        public final static Property IMAGE_TITLE = new Property(1, String.class, PROPERTYNAME_TITLE, false, COLUMNNAME_TITLE);
        public final static Property IMAGE_THUMBURL = new Property(2, String.class, PROPERTYNAME_THUMBURL, false, COLUMNNAME_THUMBURL);
        public final static Property IMAGE_SOURCEURL = new Property(3, String.class, PROPERTYNAME_SOURCEURL, false, COLUMNNAME_SOURCEURL);
        public final static Property IMAGE_HEIGHT = new Property(4, Integer.class, PROPERTYNAME_HEIGHT, false, COLUMNNAME_HEIGHT);
        public final static Property IMAGE_WIDTH = new Property(5, Integer.class, PROPERTYNAME_WIDTH, false, COLUMNNAME_WIDTH);
        public final static Property IMAGE_ISCOLLECTION = new Property(6, Integer.class, PROPERTYNAME_ISCOLLECTION, false, COLUMNNAME_ISCOLLECTION);
    }

    public ImageDao(DaoConfig config) {
        super(config);
    }

    public ImageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"" + TABLENAME + "\" (" + //
                "\"" + COLUMNNAME_ID + "\" INTEGER PRIMARY KEY," + // 0: id
                "\"" + COLUMNNAME_TITLE + "\" TEXT NOT NULL," + // 1: title
                "\"" + COLUMNNAME_THUMBURL + "\" TEXT," + // 2: thumburl
                "\"" + COLUMNNAME_SOURCEURL + "\" TEXT," + // 3: sourceurl
                "\"" + COLUMNNAME_HEIGHT + "\" INTEGER," + // 4: height
                "\"" + COLUMNNAME_WIDTH + "\" INTEGER," + // 5: width
                "\"" + COLUMNNAME_ISCOLLECTION + "\" INTEGER);"); // 6: iscollection
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"" + TABLENAME + "\"";
        db.execSQL(sql);
    }

    @Override
    protected ImageModel readEntity(Cursor cursor, int offset) {
        ImageModel entity = new ImageModel( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // thumburl
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // sourceurl
                cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // height
                cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5),  // width
                cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6)  // iscollection
        );
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, ImageModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));           // id
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));      // title
        entity.setThumburl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));   // thumburl
        entity.setSourceurl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));  // sourceurl
        entity.setHeight(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));        // height
        entity.setWidth(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));         // width
        entity.setIsCollection(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));         // iscollection
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, ImageModel entity) {
        stmt.clearBindings();

        Long id = entity.getId();// id
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String title = entity.getTitle();// title
        if (title != null) {
            stmt.bindString(2, title);
        }

        String thumburl = entity.getThumburl();// thumburl
        if (thumburl != null) {
            stmt.bindString(3, thumburl);
        }

        String sourceurl = entity.getSourceurl();// sourceurl
        if (sourceurl != null) {
            stmt.bindString(4, sourceurl);
        }

        int height = entity.getHeight(); // height
        if (height > -1) {
            stmt.bindLong(5, height);
        }

        int width = entity.getWidth(); // width
        if (width > -1) {
            stmt.bindLong(6, width);
        }

        int iscollection = entity.getIsCollection(); // iscollection
        if (iscollection > -1) {
            stmt.bindLong(7, iscollection);
        }
    }

    @Override
    protected Long updateKeyAfterInsert(ImageModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(ImageModel entity) {
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
