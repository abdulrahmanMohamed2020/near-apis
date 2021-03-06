package constants;

public class EndPoints {
    private static final String USER_URI = "/users";
    private static final String TRANSACTIONS_URI = "/transactions";
    private static final String NFT_URI = "/nfts";
    private static final String LOGIN_URI = "/login";
    private static final String CONTACTS_URI = "/contacts";

    // Endpoints of login
    public static final String CREATE_TRIGGER_LOGIN_OTP = LOGIN_URI;
    public static final String VERIFY_LOGIN_OTP = LOGIN_URI +"/verify";

    // Endpoints of users
    public static final String GET_USER = USER_URI+"/{userId}";
    public static final String CREATE_USER = "/user/create";
    public static final String UPDATE_USER = USER_URI+"/{userId}";
    public static final String DELETE_USER = USER_URI+"/{userId}";

    // Endpoints of transactions
    public static final String GET_TRANSACTION = TRANSACTIONS_URI+"/{transactionId}";
    public static final String GET_TRANSACTIONS_OF_AN_USER = TRANSACTIONS_URI+"/list/{userId}";
    public static final String GET_TRANSACTIONS_OF_NFT = TRANSACTIONS_URI+"/nft/{nftId}";
    public static final String CREATE_TRANSACTION = TRANSACTIONS_URI;
    public static final String UPDATE_TRANSACTION = TRANSACTIONS_URI+"/{transactionId}";
    public static final String DELETE_TRANSACTION = TRANSACTIONS_URI+"/{transactionId}";

    // Endpoints of nfts
    public static final String GET_ALL_NFTS = NFT_URI;
    public static final String CREATE_NFT = NFT_URI;
    public static final String GET_SINGLE_NFT = NFT_URI+"/{nftId}";
    public static final String UPDATE_NFT = NFT_URI+"/{nftId}";
    public static final String DELETE_NFT = NFT_URI+"/{nftId}";
    public static final String CLAIM_NFT = NFT_URI+"/{nftId}/claim";

    // Endpoints of contacts
    public static final String GET_CONTACT = CONTACTS_URI+"/{contactId}";
    public static final String UPDATE_CONTACT = CONTACTS_URI+"/{contactId}";
    public static final String DELETE_CONTACT = CONTACTS_URI+"/{contactId}";
    public static final String CREATE_CONTACT = CONTACTS_URI;
    public static final String IMPORT_CONTACTS = CONTACTS_URI+"/import";
    public static final String GET_CONTACTS_OF_AN_USER = CONTACTS_URI+"/list"+"/{userId}";
}
