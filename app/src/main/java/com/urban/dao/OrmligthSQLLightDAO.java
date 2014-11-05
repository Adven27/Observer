package com.urban.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.urban.data.Action;
import com.urban.data.ActionPage;
import com.urban.data.Address;
import com.urban.data.Advertising;
import com.urban.data.ApplicationProperties;
import com.urban.data.Cart;
import com.urban.data.Category;
import com.urban.data.Contact;
import com.urban.data.ContactType;
import com.urban.data.Event;
import com.urban.data.Image;
import com.urban.data.InfoPage;
import com.urban.data.News;
import com.urban.data.NewsCategory;
import com.urban.data.NotificationSubscribe;
import com.urban.data.Organization;
import com.urban.data.Page;
import com.urban.data.Person;
import com.urban.data.Photo;
import com.urban.data.PhotoGallary;
import com.urban.data.Place;
import com.urban.data.Position;
import com.urban.data.Stuff;
import com.urban.data.StuffCategory;
import com.urban.data.User;
import com.urban.data.UserProperties;
import com.urban.data.Voting;
import com.urban.data.dao.IDAO;
import com.urban.data.dao.UrbanCriterion;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.com.urban.data.sqlite.SQLiteUrbanCriterion;
import src.com.urban.data.sqlite.pojo.ActionPagePojo;
import src.com.urban.data.sqlite.pojo.ActionPojo;
import src.com.urban.data.sqlite.pojo.AddressPojo;
import src.com.urban.data.sqlite.pojo.AdvertisingPojo;
import src.com.urban.data.sqlite.pojo.ApplicationPropertiesPojo;
import src.com.urban.data.sqlite.pojo.CartPojo;
import src.com.urban.data.sqlite.pojo.CategoryPojo;
import src.com.urban.data.sqlite.pojo.ContactPojo;
import src.com.urban.data.sqlite.pojo.ContactTypePojo;
import src.com.urban.data.sqlite.pojo.EventPojo;
import src.com.urban.data.sqlite.pojo.ImagePojo;
import src.com.urban.data.sqlite.pojo.InfoPagePojo;
import src.com.urban.data.sqlite.pojo.NewsCategoryPojo;
import src.com.urban.data.sqlite.pojo.NewsPojo;
import src.com.urban.data.sqlite.pojo.NotificationSubscribePojo;
import src.com.urban.data.sqlite.pojo.OrganizationPojo;
import src.com.urban.data.sqlite.pojo.PagePojo;
import src.com.urban.data.sqlite.pojo.PersonPojo;
import src.com.urban.data.sqlite.pojo.PhotoGallaryPojo;
import src.com.urban.data.sqlite.pojo.PhotoPojo;
import src.com.urban.data.sqlite.pojo.PlacePojo;
import src.com.urban.data.sqlite.pojo.PositionPojo;
import src.com.urban.data.sqlite.pojo.StuffCategoryPojo;
import src.com.urban.data.sqlite.pojo.StuffPojo;
import src.com.urban.data.sqlite.pojo.UserPojo;
import src.com.urban.data.sqlite.pojo.UserPropertiesPojo;
import src.com.urban.data.sqlite.pojo.VotingPojo;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class OrmligthSQLLightDAO<T> extends OrmLiteSqliteOpenHelper implements IDAO {

    private Map<Class, RuntimeExceptionDao> cashedDao = new HashMap<Class, RuntimeExceptionDao>();

    private static Map<Class, Class> classes = new HashMap<Class, Class>();
    static {
        classes.put(Action.class, ActionPojo.class);
        classes.put(ActionPage.class, ActionPagePojo.class);
        classes.put(Address.class, AddressPojo.class);
        classes.put(Advertising.class, AdvertisingPojo.class);
        classes.put(ApplicationProperties.class, ApplicationPropertiesPojo.class);
        classes.put(Cart.class, CartPojo.class);
        classes.put(Category.class, CategoryPojo.class);
        classes.put(Contact.class, ContactPojo.class);
        classes.put(ContactType.class, ContactTypePojo.class);
        classes.put(Event.class, EventPojo.class);
        classes.put(Image.class, ImagePojo.class);
        classes.put(InfoPage.class, InfoPagePojo.class);
        classes.put(News.class, NewsPojo.class);
        classes.put(NewsCategory.class, NewsCategoryPojo.class);
        classes.put(Organization.class, OrganizationPojo.class);
        //classes.put(OrganizationPhone.class, OrganizationPhonePojo.class);
        classes.put(Page.class, PagePojo.class);
        classes.put(Person.class, PersonPojo.class);
        classes.put(Photo.class, PhotoPojo.class);
        classes.put(PhotoGallary.class, PhotoGallaryPojo.class);
        classes.put(Place.class, PlacePojo.class);
        classes.put(Position.class, PositionPojo.class);
        classes.put(Stuff.class, StuffPojo.class);
        classes.put(StuffCategory.class, StuffCategoryPojo.class);
        classes.put(User.class, UserPojo.class);
        classes.put(UserProperties.class, UserPropertiesPojo.class);
        classes.put(Voting.class, VotingPojo.class);
        classes.put(NotificationSubscribe.class, NotificationSubscribePojo.class);
    }


    public OrmligthSQLLightDAO(String dbName, int dbversion, Context context) {
        super(context, dbName, null, dbversion /*, R.raw.ormlite_config*/);
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        // TODO Auto-generated method stub
    }

    public <T> void save(T objToSave) {
        ((RuntimeExceptionDao<T, Long>)getRuntimeDAO(
                objToSave.getClass())).createOrUpdate(objToSave);
        // TODO Auto-generated method stub
    }

    public <T> Collection<T> getAll(Class<T> type) {
        return getRuntimeDAO(type).queryForAll();
    }

    public <T> T get(Class<T> type, long id) {
        return getRuntimeDAO(type).queryForId(id);
    }

    private <T> RuntimeExceptionDao<T, Long> getRuntimeDAO(Class<T> type){
        RuntimeExceptionDao<T, Long> dao;
        if (cashedDao.containsKey(type)){
            dao = cashedDao.get(type);
        }else{
            dao = getRuntimeExceptionDao(type);
            cashedDao.put(type, dao);
        }
        return dao;
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> UrbanCriterion createCriteria(Class<T> type) {
        try {
            return new SQLiteUrbanCriterion(type, getDao(type));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getByCriterion(Class<T> type, UrbanCriterion criterion) {
        try {
            Dao<T, String> dao = getDao(type);
            Where<T, String> where = ((SQLiteUrbanCriterion<T>) criterion).getWhere();
            QueryBuilder<T, String> qBuilder = dao.queryBuilder();
            qBuilder.setWhere(where);
            return qBuilder.query();
        } catch (SQLException e) {
            // TODO implement exception handling
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getUniqByCriterion(Class<T> type, UrbanCriterion criterion) {
        try {
            Dao<T, String> dao = getDao(type);
            Where<T, String> where = ((SQLiteUrbanCriterion<T>) criterion).getWhere();
            QueryBuilder<T, String> qBuilder = dao.queryBuilder();
            qBuilder.setWhere(where);

            return qBuilder.queryForFirst();// !!!!!!!!!!!!!
        } catch (SQLException e) {
            // TODO implement exception handling
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> Class<T> getPojo(Class<T> intf) {
        return classes.get(intf);
    }

    @Override
    public <T> void deleteAll(Class<T> type) {
        try {
            Dao<T, String> dao = getDao(type);
            DeleteBuilder<T, String> dBuilder = dao.deleteBuilder();
            dBuilder.delete();
        } catch (SQLException e) {
            // TODO implement exception handling
            e.printStackTrace();
        }
    }
}