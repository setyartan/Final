package ua.nure.kortyak.FinalDBase.db;

import ua.nure.kortyak.FinalDBase.db.dbManager.*;
import ua.nure.kortyak.FinalDBase.db.entity.Periodical;
import ua.nure.kortyak.FinalDBase.db.entity.Theme;
import ua.nure.kortyak.FinalDBase.db.entity.User;
import ua.nure.kortyak.FinalDBase.exception.DBException;

import java.util.List;

public class Demo {
    public static void main(String[] args) throws DBException {
//        DBManagerForUser dbManagerForUser = new DBManagerForUser();
//        DBManager.getInstance(dbManagerForUser);
//        System.out.println("===================");
//        dbManagerForUser.insertUser(User.createUser(
//                "obama", "pass", "obama", "obama", 1));
//        dbManagerForUser.insertUser(User.createUser(
//                "obama2", "pass", "abama2", "obama2", 1));
//        System.out.println(dbManagerForUser.findUserByLogin("obama"));
//        User user = dbManagerForUser.findUser(2);
//        user.setLastName("dfddfgf");
//        dbManagerForUser.updateUser(user);
//        System.out.println(dbManagerForUser.findUser(2));
//        System.out.println(dbManagerForUser.findAllUsers());
//        dbManagerForUser.deleteUser(dbManagerForUser.findUser(2));
//        System.out.println(dbManagerForUser.findAllUsers());
//
//        DBManagerForPeriodicals dbManagerForPeriodicals = new DBManagerForPeriodicals();
//        DBManager.getInstance(dbManagerForPeriodicals);
//        System.out.println("===================");
//        dbManagerForPeriodicals.insertPeriodical(Periodical.createPeriodical
//                ("EvgeniyLOOOOOVEEEE", 1000000000L));
//        dbManagerForPeriodicals.insertPeriodical(Periodical.createPeriodical
//                ("EvgeniyLOOOOVEEE", 10000000L));
//        System.out.println(dbManagerForPeriodicals.findPeriodicalByName("EvgeniyLOOOOOVEEEE"));
//        Periodical periodical = dbManagerForPeriodicals.findPeriodical(5);
//        Periodical periodical1 = dbManagerForPeriodicals.findPeriodical(2);
//        periodical.setName("EEEEVVVVGEEEEEEENIY");
//        dbManagerForPeriodicals.updatePeriodical(periodical);
//        System.out.println(dbManagerForPeriodicals.findPeriodical(5));
//        System.out.println(dbManagerForPeriodicals.findAllPeriodical());
//        dbManagerForPeriodicals.deletePeriodical(dbManagerForPeriodicals.findPeriodicalByName("EvgeniyLOOOOOVEEEE"));
//        System.out.println(dbManagerForPeriodicals.findAllPeriodical());
//
//        DBManagerPOU dbManagerPOU = new DBManagerPOU();
//        DBManager.getInstance(dbManagerPOU);
//        System.out.println("====================");
//        dbManagerPOU.insertPeriodicalsForUser(user, periodical, periodical1);
//        System.out.println(dbManagerPOU.findAllPeriodicalOfUser(user));
//        dbManagerPOU.deletePeriodicalOfUser(periodical, user);
//        System.out.println(dbManagerPOU.findAllPeriodicalOfUser(user));
//
//        DBManagerForThemes dbManagerForThemes = new DBManagerForThemes();
//        DBManager.getInstance(dbManagerForThemes);
//        System.out.println("====================");
//        dbManagerForThemes.insertTheme(Theme.createTheme("Art"));
//        dbManagerForThemes.insertTheme(Theme.createTheme("Cemistry"));
//        System.out.println(dbManagerForThemes.findThemeByName("Love"));
//        Theme theme = dbManagerForThemes.findTheme(3);
//        Theme theme1 = dbManagerForThemes.findTheme(2);
//        theme.setName("art2");
//        dbManagerForThemes.updateTheme(theme);
//        System.out.println(dbManagerForThemes.findTheme(3));
//        System.out.println(dbManagerForThemes.findAllThemes());
//        dbManagerForThemes.deleteTheme(dbManagerForThemes.findThemeByName("Cemistry"));
//        System.out.println(dbManagerForThemes.findAllThemes());
//
//        System.out.println("====================");
//        DBManagerPOT dbManagerPOT = new DBManagerPOT();
//        DBManager.getInstance(dbManagerPOT);
//        Periodical periodical2 = dbManagerForPeriodicals.findPeriodical(1);
//        dbManagerPOT.insertPeriodicalsForTheme(theme1, periodical2, periodical1);
//        System.out.println(dbManagerPOT.findAllPeriodicalOfTheme(theme1));
//        dbManagerPOT.deletePeriodicalOfTheme(periodical2, theme1);
//        System.out.println(dbManagerPOT.findAllPeriodicalOfTheme(theme1));
//
//        System.out.println("====================");
//        dbManagerForPeriodicals.insertPeriodical(Periodical.createPeriodical
//                ("AEvgeniyLOOOOOVEEEE", 100000000L));
//        dbManagerForPeriodicals.insertPeriodical(Periodical.createPeriodical
//                ("BEvgeniyLOOOOOVEEEE", 10000000L));
//        dbManagerForPeriodicals.insertPeriodical(Periodical.createPeriodical
//                ("CEvgeniyLOOOOOVEEEE", 1000000L));
//        dbManagerForPeriodicals.insertPeriodical(Periodical.createPeriodical
//                ("DEvgeniyLOOOOOVEEEE", 100000L));
//        dbManagerForPeriodicals.insertPeriodical(Periodical.createPeriodical
//                ("EEvgeniyLOOOOOVEEEE", 10000L));
//
//        List list = dbManagerForPeriodicals.findAllPeriodicalSortedByNameASC();
//        List list1 = dbManagerForPeriodicals.findAllPeriodicalSortedByNameDESC();
//        List list2 = dbManagerForPeriodicals.findAllPeriodicalSortedByPriceASC();
//        List list3 = dbManagerForPeriodicals.findAllPeriodicalSortedByPriceDESC();
//
//        for (Object value : list) {
//            System.out.println(value);
//        }
//        System.out.println("-------------");
//        for (Object o : list1) {
//            System.out.println(o);
//        }
//        System.out.println("-------------");
//        for (Object o : list2) {
//            System.out.println(o);
//        }
//        System.out.println("-------------");
//        for (Object o : list3) {
//            System.out.println(o);
//        }
//        System.out.println("-------");
//        //List list4 = dbManagerForPeriodicals.findAllPeriodicalSortedByPriceAndNameDESC();
//        //for (Object value : list4) {
//          //  System.out.println(value);
//        //}
//
//        System.out.println("-------");
//        List list5 = dbManagerForPeriodicals.findAllPeriodicalWithPattern("EEEE");
//        for (Object value : list5) {
//            System.out.println(value);
//        }
    }
}
