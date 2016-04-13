package zekisanmobile.petsitter.util;

public class HandlersUtil {

    public static final String BASE_URL = "https://petsitterapi.herokuapp.com/api/v1/";
    private static final String SITTERS_CONTROLLER = "sitters/";
    private static final String PET_OWNERS_CONTROLLER = "pet_owners/";
    private static final String CONTACTS_OWNERS_CONTROLLER = "contacts/";

    public static String getAllSittersURL(){
        return BASE_URL + SITTERS_CONTROLLER;
    }

}
