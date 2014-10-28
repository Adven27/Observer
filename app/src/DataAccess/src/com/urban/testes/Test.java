package com.urban.testes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.mvstore.db.TransactionStore.Transaction;

import com.example.prototype.dao.DAO;
import com.example.prototype.dao.DefaultDaoInitializer;
import com.example.prototype.dao.JDBCSLQLightDao;
import com.j256.ormlite.misc.TransactionManager;
import com.urban.entity.Category;
import com.urban.entity.Organization;
import com.urban.entity.Position;
import com.urban.entity.pojo.CategoryPojo;
import com.urban.entity.pojo.CategoryPositionLinkPojo;
import com.urban.entity.pojo.OrganizationPojo;
import com.urban.entity.pojo.PositionPojo;

public class Test {


    public Test() {
    }

    public static void main(String[] args) {
        int categoryId = 0;

        DefaultDaoInitializer.setDaoImpl(new JDBCSLQLightDao("jdbc:sqlite:..\\Prototype\\assets\\urban.db"));

        List<PositionPojo> positions = new ArrayList<PositionPojo>();
        List<CategoryPositionLinkPojo> links = new ArrayList<CategoryPositionLinkPojo>();

        // Start of first category. Food.
        Organization organization = insertOrganization("Парижанка", "Не Вологжанка");
        PositionPojo position = insertPosition("Парижанка", organization);
        //DAO.save(position);
        positions.add(position);

        organization = insertOrganization("Лукоморье", "Для дубов");
        position = insertPosition("Лукоморье", organization);
        positions.add(position);

        organization = insertOrganization("АРС ресторан", "На раз");
        position = insertPosition("АРС ресторан", organization);
        positions.add(position);

        organization = insertOrganization("Штоф", "Два стопаря и ты готоф..");
        position = insertPosition("Штоф", organization);
        positions.add(position);

        organization = insertOrganization("Ласточкино гнездо", "Залетай!");
        position = insertPosition("Ласточкино гнездо", organization);
        positions.add(position);

        CategoryPojo category = insertCategory("Еда", categoryId, null, positions);
        links = insertCategoryPositionLinks(category, positions);
        // End of first category. Food.


    }

    public static CategoryPojo insertCategory(String name, int order, CategoryPojo parent,
            List<PositionPojo> positions) {

        CategoryPojo category = new CategoryPojo();
        category.setName(name);
        category.setOrder(order);
        category.setParent(parent);


        for (PositionPojo position : positions) {
            category.addPosition(position);
        }
        try {
            DAO.save(category);
        } catch (SQLException e) {
            return null;
        }
        return category;
    }

    public static PositionPojo insertPosition(String name, Organization organization) {
        PositionPojo position = new PositionPojo();
        position.setName(name);
        position.setOrganization(organization);
        //position.setPage(value);
        try {
            DAO.save(position);
        } catch (SQLException e) {
            return null;
        }
        return position;
    }

    public static OrganizationPojo insertOrganization(String name, String description) {
        OrganizationPojo organization = new OrganizationPojo();
        organization.setDescription(description);
        //organization.setLogo(value);
        try {
            DAO.save(organization);
        } catch (SQLException e) {
            return null;
        }
        return organization;
    }

    public static List<CategoryPositionLinkPojo> insertCategoryPositionLinks(CategoryPojo category, List<PositionPojo> positions) {
        List<CategoryPositionLinkPojo> list = new ArrayList<CategoryPositionLinkPojo>();
        //Транзакцию
        try {

            for (PositionPojo position : positions) {
                category.addPosition(position);

                CategoryPositionLinkPojo link = new CategoryPositionLinkPojo();
                link.setCategory(category);
                link.setPosition(position);
                list.add(link);
                DAO.save(link);
            }
        } catch (SQLException e) {
            return null;
        }

        return list;
    }
}
