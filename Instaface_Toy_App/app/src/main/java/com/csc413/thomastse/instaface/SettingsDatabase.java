package com.csc413.thomastse.instaface;

/**
 * Created by thomastse on 10/14/15.
 *
 *  This is just a pseudo database class that holds the settings information.
 */
public class SettingsDatabase {
    public class Data{
        public String name;
        public String email;
        public boolean searchVegan;
        public boolean searchVegetarian;
        public boolean searchGlutenFree;

        // initialize data
        public Data(){
            name = "Eve Apple";
            email = "IAteTheApple@example.com";
            searchVegan = false;
            searchVegetarian = false;
            searchGlutenFree = false;
        }

        // record of data
        public String toString(){
            String ret;

            ret = "Name: " + name + "\n";
            ret += "Email: " + email + "\n";
            ret += "Search Vegan? " + onOff(searchVegan) + "\n";
            ret += "Search Vegetarian? " + onOff(searchVegetarian) + "\n";
            ret += "Search GlutenFree? " + onOff(searchGlutenFree) + "\n";

            return ret;
        }

        public String onOff(boolean bool){
            if(bool)
                return "On";
            return "Off";
        }

    }

    public static Data data;

    // instantiate database
    public SettingsDatabase(){
        data = new Data();
    }


}
