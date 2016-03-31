package com.way.tunnelvision.entity.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.way.tunnelvision.entity.model.HeaderImageModel;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by pc on 2016/2/20.
 */
public class HeaderImageDao extends AbstractDao<HeaderImageModel, Long> {
    public static final String TABLENAME = "HEADERIMAGEMODEL";
    public static final String COLUMNNAME_ID = "_id";
    public static final String COLUMNNAME_TITLE = "HEADERIMAGETITLE";
    public static final String COLUMNNAME_LINK = "HEADERIMAGELINK";
    public static final String COLUMNNAME_DESCRIPTION = "HEADERIMAGEDESCRIPTION";
    public static final String COLUMNNAME_URL = "HEADERIMAGEURL";
    public static final String COLUMNNAME_LENGTH = "HEADERIMAGELENGTH";
    public static final String COLUMNNAME_TYPE = "HEADERIMAGETYPE";
    public static final String COLUMNNAME_GUID = "HEADERIMAGEGUID";
    public static final String COLUMNNAME_PUBDATE = "HEADERIMAGEPUBDATE";
    public static final String COLUMNNAME_CHOSEN = "HEADERIMAGECHOSEN";

    private static final String PROPERTYNAME_ID = "id";
    private static final String PROPERTYNAME_TITLE = "title";
    private static final String PROPERTYNAME_LINK = "link";
    private static final String PROPERTYNAME_DESCRIPTION = "description";
    private static final String PROPERTYNAME_URL = "url";
    private static final String PROPERTYNAME_LENGTH = "length";
    private static final String PROPERTYNAME_TYPE = "type";
    private static final String PROPERTYNAME_GUID = "guid";
    private static final String PROPERTYNAME_PUBDATE = "pubDate";
    private static final String PROPERTYNAME_CHOSEN = "chosen";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, PROPERTYNAME_ID, true, COLUMNNAME_ID);
        public final static Property HEADERIMAGE_TITLE = new Property(1, String.class, PROPERTYNAME_TITLE, false, COLUMNNAME_TITLE);
        public final static Property HEADERIMAGE_LINK = new Property(2, String.class, PROPERTYNAME_LINK, false, COLUMNNAME_LINK);
        public final static Property HEADERIMAGE_DESCRIPTION = new Property(3, String.class, PROPERTYNAME_DESCRIPTION, false, COLUMNNAME_DESCRIPTION);
        public final static Property HEADERIMAGE_URL = new Property(4, String.class, PROPERTYNAME_URL, false, COLUMNNAME_URL);
        public final static Property HEADERIMAGE_LENGTH = new Property(5, Long.class, PROPERTYNAME_LENGTH, false, COLUMNNAME_LENGTH);
        public final static Property HEADERIMAGE_TYPE = new Property(6, String.class, PROPERTYNAME_TYPE, false, COLUMNNAME_TYPE);
        public final static Property HEADERIMAGE_GUID = new Property(7, String.class, PROPERTYNAME_GUID, false, COLUMNNAME_GUID);
        public final static Property HEADERIMAGE_PUBDATE = new Property(8, String.class, PROPERTYNAME_PUBDATE, false, COLUMNNAME_PUBDATE);
        public final static Property HEADERIMAGE_CHOSEN = new Property(9, Integer.class, PROPERTYNAME_CHOSEN, false, COLUMNNAME_CHOSEN);
    }

    public HeaderImageDao(DaoConfig config) {
        super(config);
    }

    public HeaderImageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"" + TABLENAME + "\" (" + //
                "\"" + COLUMNNAME_ID + "\" INTEGER PRIMARY KEY AUTOINCREMENT," +      // 0: id
                "\"" + COLUMNNAME_TITLE + "\" TEXT NOT NULL," +         // 1: title
                "\"" + COLUMNNAME_LINK + "\" TEXT," +                   // 2: link
                "\"" + COLUMNNAME_DESCRIPTION + "\" TEXT," +            // 3: description
                "\"" + COLUMNNAME_URL + "\" TEXT," +                    // 4: url
                "\"" + COLUMNNAME_LENGTH + "\" INTEGER," +              // 5: length
                "\"" + COLUMNNAME_TYPE + "\" TEXT," +                   // 6: type
                "\"" + COLUMNNAME_GUID + "\" TEXT," +                   // 7: guid
                "\"" + COLUMNNAME_PUBDATE + "\" TEXT," +                // 8: pubDate
                "\"" + COLUMNNAME_CHOSEN + "\" INTEGER DEFAULT 2);");   // 9: chosen
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"" + TABLENAME + "\"";
        db.execSQL(sql);
    }

    @Override
    protected HeaderImageModel readEntity(Cursor cursor, int offset) {
        HeaderImageModel entity = new HeaderImageModel( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),      // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1),    // title
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2),    // link
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3),    // description
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4),    // url
                cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5),      // length
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6),    // type
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7),    // guid
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8),    // pubDate
                cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9)        // chosen
        );
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, HeaderImageModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));                // id
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));           // title
        entity.setLink(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));            // link
        entity.setDescription(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));     // description
        entity.setUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));             // url
        entity.setLength(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));            // length
        entity.setType(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));            // type
        entity.setGuid(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));            // guid
        entity.setPubDate(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));         // pubDate
        entity.setChosen(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));             // chosen
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, HeaderImageModel entity) {
        stmt.clearBindings();

        Long id = entity.getId();                       // id
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String title = entity.getTitle();               // title
        if (title != null) {
            stmt.bindString(2, title);
        }

        String link = entity.getLink();                 // link
        if (link != null) {
            stmt.bindString(3, link);
        }

        String description = entity.getDescription();   // description
        if (description != null) {
            stmt.bindString(4, description);
        }

        String url = entity.getUrl();                   // url
        if (url != null) {
            stmt.bindString(5, url);
        }

        Long length = entity.getLength();               // length
        if (length != null) {
            stmt.bindLong(6, length);
        }

        String type = entity.getType();                 // type
        if (type != null) {
            stmt.bindString(7, type);
        }

        String guid = entity.getGuid();                 // guid
        if (guid != null) {
            stmt.bindString(8, guid);
        }

        String pubDate = entity.getPubDate();           // pubDate
        if (pubDate != null) {
            stmt.bindString(9, pubDate);
        }

        int chosen = entity.getChosen();                // chosen
        if (chosen > -1) {
            stmt.bindLong(10, chosen);
        }
    }

    @Override
    protected Long updateKeyAfterInsert(HeaderImageModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(HeaderImageModel entity) {
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
