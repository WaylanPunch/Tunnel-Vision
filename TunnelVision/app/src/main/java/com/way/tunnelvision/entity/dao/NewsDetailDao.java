package com.way.tunnelvision.entity.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.way.tunnelvision.entity.model.NewsDetailModel;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by pc on 2016/2/20.
 */
public class NewsDetailDao extends AbstractDao<NewsDetailModel, Long> {
    public static final String TABLENAME = "NEWSDETAILMODEL";
    public static final String COLUMNNAME_ID = "_id";
    public static final String COLUMNNAME_DOCID = "NEWSDETAILDOCID";
    public static final String COLUMNNAME_TITLE = "NEWSDETAILTITLE";
    public static final String COLUMNNAME_SOURCE = "NEWSDETAILSOURCE";
    public static final String COLUMNNAME_BODY = "NEWSDETAILBODY";
    public static final String COLUMNNAME_PTIME = "NEWSDETAILPTIME";
    public static final String COLUMNNAME_COVER = "NEWSDETAILCOVER";
    public static final String COLUMNNAME_IMGARRAY = "NEWSDETAILIMGARRAY";

    private static final String PROPERTYNAME_ID = "id";
    private static final String PROPERTYNAME_DOCID = "docid";
    private static final String PROPERTYNAME_TITLE = "title";
    private static final String PROPERTYNAME_SOURCE = "source";
    private static final String PROPERTYNAME_BODY = "body";
    private static final String PROPERTYNAME_PTIME = "ptime";
    private static final String PROPERTYNAME_COVER = "cover";
    private static final String PROPERTYNAME_IMGARRAY = "imgarray";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, PROPERTYNAME_ID, true, COLUMNNAME_ID);
        public final static Property NEWSDETAILDOCID = new Property(1, String.class, PROPERTYNAME_DOCID, false, COLUMNNAME_DOCID);
        public final static Property NEWSDETAILTITLE = new Property(2, String.class, PROPERTYNAME_TITLE, false, COLUMNNAME_TITLE);
        public final static Property NEWSDETAILSOURCE = new Property(3, String.class, PROPERTYNAME_SOURCE, false, COLUMNNAME_SOURCE);
        public final static Property NEWSDETAILBODY = new Property(4, String.class, PROPERTYNAME_BODY, false, COLUMNNAME_BODY);
        public final static Property NEWSDETAILPTIME = new Property(5, String.class, PROPERTYNAME_PTIME, false, COLUMNNAME_PTIME);
        public final static Property NEWSDETAILCOVER = new Property(6, String.class, PROPERTYNAME_COVER, false, COLUMNNAME_COVER);
        public final static Property NEWSDETAILIMGARRAY = new Property(7, String.class, PROPERTYNAME_IMGARRAY, false, COLUMNNAME_IMGARRAY);
    }

    public NewsDetailDao(DaoConfig config) {
        super(config);
    }

    public NewsDetailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }


    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"" + TABLENAME + "\" (" + //
                "\"" + COLUMNNAME_ID + "\" INTEGER PRIMARY KEY," + // 0: id
                "\"" + COLUMNNAME_DOCID + "\" TEXT," + // 1: docid
                "\"" + COLUMNNAME_TITLE + "\" TEXT NOT NULL," + // 2: title
                "\"" + COLUMNNAME_SOURCE + "\" TEXT," + // 3: source
                "\"" + COLUMNNAME_BODY + "\" TEXT," + // 4: body
                "\"" + COLUMNNAME_PTIME + "\" TEXT," + // 5: ptime
                "\"" + COLUMNNAME_COVER + "\" TEXT," + // 5: cover
                "\"" + COLUMNNAME_IMGARRAY + "\" TEXT);"); // 6: imgarray
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"" + TABLENAME + "\"";
        db.execSQL(sql);
    }

    @Override
    protected NewsDetailModel readEntity(Cursor cursor, int offset) {
        NewsDetailModel entity = new NewsDetailModel( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // docid
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // source
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // body
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // ptime
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // cover
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7)  // imgarray
        );
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, NewsDetailModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));                // id
        entity.setDocid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));           // docid
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));           // title
        entity.setSource(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));          // source
        entity.setBody(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));            // body
        entity.setPtime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));           // ptime
        entity.setCover(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));           // cover
        entity.setImgArrayString(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));  // imgarray
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, NewsDetailModel entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String docid = entity.getDocid();
        if (docid != null) {
            stmt.bindString(2, docid);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }

        String source = entity.getSource();
        if (source != null) {
            stmt.bindString(4, source);
        }

        String body = entity.getBody();
        if (body != null) {
            stmt.bindString(5, body);
        }

        String ptime = entity.getPtime();
        if (ptime != null) {
            stmt.bindString(6, ptime);
        }

        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(7, cover);
        }

        String imgarray = entity.getImgArrayString();
        if (imgarray != null) {
            stmt.bindString(8, imgarray);
        }
    }

    @Override
    protected Long updateKeyAfterInsert(NewsDetailModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(NewsDetailModel entity) {
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
