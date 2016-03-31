package com.way.tunnelvision.entity.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.way.tunnelvision.entity.model.NewsModel;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by pc on 2016/2/20.
 */
public class NewsDao extends AbstractDao<NewsModel, Long> {
    public static final String TABLENAME = "NEWSMODEL";
    public static final String COLUMNNAME_ID = "_id";
    public static final String COLUMNNAME_DOCID = "NEWSDOCID";
    public static final String COLUMNNAME_TITLE = "NEWSTITLE";
    public static final String COLUMNNAME_DIGEST = "NEWSDIGEST";
    public static final String COLUMNNAME_IMGSRC = "NEWSIMGSRC";
    public static final String COLUMNNAME_SOURCE = "NEWSSOURCE";
    public static final String COLUMNNAME_PTIME = "NEWSPTIME";
    public static final String COLUMNNAME_TAG = "NEWSTAG";
    public static final String COLUMNNAME_ISCOLLECTION = "NEWSISCOLLECTION";

    private static final String PROPERTYNAME_ID = "id";
    private static final String PROPERTYNAME_DOCID = "docid";
    private static final String PROPERTYNAME_TITLE = "title";
    private static final String PROPERTYNAME_DIGEST = "digest";
    private static final String PROPERTYNAME_IMGSRC = "imgsrc";
    private static final String PROPERTYNAME_SOURCE = "source";
    private static final String PROPERTYNAME_PTIME = "ptime";
    private static final String PROPERTYNAME_TAG = "tag";
    private static final String PROPERTYNAME_ISCOLLECTION = "iscollection";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, PROPERTYNAME_ID, true, COLUMNNAME_ID);
        public final static Property NEWS_DOCID = new Property(1, String.class, PROPERTYNAME_DOCID, false, COLUMNNAME_DOCID);
        public final static Property NEWS_TITLE = new Property(2, String.class, PROPERTYNAME_TITLE, false, COLUMNNAME_TITLE);
        public final static Property NEWS_DIGEST = new Property(3, String.class, PROPERTYNAME_DIGEST, false, COLUMNNAME_DIGEST);
        public final static Property NEWS_IMGSRC = new Property(4, String.class, PROPERTYNAME_IMGSRC, false, COLUMNNAME_IMGSRC);
        public final static Property NEWS_SOURCE = new Property(5, String.class, PROPERTYNAME_SOURCE, false, COLUMNNAME_SOURCE);
        public final static Property NEWS_PTIME = new Property(6, String.class, PROPERTYNAME_PTIME, false, COLUMNNAME_PTIME);
        public final static Property NEWS_TAG = new Property(7, String.class, PROPERTYNAME_TAG, false, COLUMNNAME_TAG);
        public final static Property NEWS_ISCOLLECTION = new Property(8, Integer.class, PROPERTYNAME_ISCOLLECTION, false, COLUMNNAME_ISCOLLECTION);
    }

    public NewsDao(DaoConfig config) {
        super(config);
    }

    public NewsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"" + TABLENAME + "\" (" + //
                "\"" + COLUMNNAME_ID + "\" INTEGER PRIMARY KEY AUTOINCREMENT," + // 0: id
                "\"" + COLUMNNAME_DOCID + "\" TEXT," + // 1: docid
                "\"" + COLUMNNAME_TITLE + "\" TEXT NOT NULL," + // 2: title
                "\"" + COLUMNNAME_DIGEST + "\" TEXT," + // 3: digest
                "\"" + COLUMNNAME_IMGSRC + "\" TEXT," + // 4: imgsrc
                "\"" + COLUMNNAME_SOURCE + "\" TEXT," + // 5: source
                "\"" + COLUMNNAME_PTIME + "\" TEXT," + // 6: ptime
                "\"" + COLUMNNAME_TAG + "\" TEXT," + // 7: tag
                "\"" + COLUMNNAME_ISCOLLECTION + "\" INTEGER DEFAULT 0);"); // 8: isCollection
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"" + TABLENAME + "\"";
        db.execSQL(sql);
    }

    @Override
    protected NewsModel readEntity(Cursor cursor, int offset) {
        NewsModel entity = new NewsModel( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // docid
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // digest
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // imgsrc
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // source
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ptime
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7),  // tag
                cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8)  // isCollection
        );
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, NewsModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));                // id
        entity.setDocid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));           // docid
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));           // title
        entity.setDigest(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));          // digest
        entity.setImgsrc(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));            // imgsrc
        entity.setSource(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));           // source
        entity.setPtime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));           // ptime
        entity.setTag(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));         // tag
        entity.setIsCollection(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));  // isCollection
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, NewsModel entity) {
        stmt.clearBindings();

        Long id = entity.getId();// id
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String docid = entity.getDocid();// docid
        if (docid != null) {
            stmt.bindString(2, docid);
        }

        String title = entity.getTitle();// title
        if (title != null) {
            stmt.bindString(3, title);
        }

        String digest = entity.getDigest();// digest
        if (digest != null) {
            stmt.bindString(4, digest);
        }

        String imgsrc = entity.getImgsrc();// imgsrc
        if (imgsrc != null) {
            stmt.bindString(5, imgsrc);
        }

        String source = entity.getSource();// source
        if (source != null) {
            stmt.bindString(6, source);
        }

        String ptime = entity.getPtime();// ptime
        if (ptime != null) {
            stmt.bindString(7, ptime);
        }

        String tag = entity.getTag();// tag
        if (tag != null) {
            stmt.bindString(8, tag);
        }

        int isCollection = entity.getIsCollection(); // isCollection
        if (isCollection > -1) {
            stmt.bindLong(9, isCollection);
        }
    }

    @Override
    protected Long updateKeyAfterInsert(NewsModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(NewsModel entity) {
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
