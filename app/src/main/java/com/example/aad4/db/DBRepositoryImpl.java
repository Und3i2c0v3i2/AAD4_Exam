package com.example.aad4.db;


import com.example.aad4.entity.Comment;
import com.example.aad4.entity.TouristAttraction;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.aad4.db.DBHelper.COLUMN_FOREIGN_ID;


public class DBRepositoryImpl {


    private Dao<TouristAttraction, Integer> parentDao;
    private Dao<Comment, Integer> childDao;


    public DBRepositoryImpl(DBHelper dbHelper) {

        try {
            parentDao = dbHelper.getParentDao();
            childDao = dbHelper.getChildDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public int insert(TouristAttraction touristAttraction) {
        // return value (number of rows modified) can be used for toast or something like that
        int i = -1;
        if (touristAttraction != null) {

            if (checkIfExists(touristAttraction) == null) {

                try {
                    i = parentDao.create(touristAttraction);
                    for(Comment c : touristAttraction.getComments()) {
                        childDao.create(c);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return i;
    }


    public int modify(TouristAttraction touristAttraction) {
        int i = -1;
        if (touristAttraction != null) {

            try {
                i = parentDao.update(touristAttraction);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return i;
    }


    public int delete(TouristAttraction touristAttraction) {
        int i = -1;

        try {
            i = parentDao.delete(touristAttraction);
            deleteForeignCollection(touristAttraction.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }


    public List<TouristAttraction> getAll() {
        List<TouristAttraction> list = new ArrayList<>();

        try {
            list = parentDao.queryForAll();
            for(TouristAttraction ta : list) {
                List<Comment> comments = getForeignCollection(ta.getId());
                ta.setComments(comments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public TouristAttraction getById(int id) {
        TouristAttraction touristAttraction = null;
        try {
            touristAttraction = parentDao.queryForId(id);
            List<Comment> comments = getForeignCollection(touristAttraction.getId());
            touristAttraction.setComments(comments);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return touristAttraction;
    }


    public List<Comment> getForeignCollection(int id) {
        List<Comment> list = new ArrayList<>();

        try {
            list = childDao.queryForEq(COLUMN_FOREIGN_ID, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public void deleteForeignCollection(int id) {
        try {
            DeleteBuilder builder = childDao.deleteBuilder();
            builder.where()
                    .eq(COLUMN_FOREIGN_ID, id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TouristAttraction checkIfExists(TouristAttraction touristAttraction) {

        TouristAttraction exist = null;

        try {
            QueryBuilder<TouristAttraction, Integer> qb = parentDao.queryBuilder();
            Where<TouristAttraction, Integer> where = qb.where();
            where.eq("name", touristAttraction.getName())
                    .and()
                    .eq("address", touristAttraction.getAddress())
                    .and()
                    .eq("phone", touristAttraction.getPhone());
            PreparedQuery<TouristAttraction> pq = qb.prepare();
            exist = parentDao.queryForFirst(pq);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }



}
