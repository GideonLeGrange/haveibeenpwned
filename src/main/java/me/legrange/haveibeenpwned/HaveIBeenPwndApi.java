package me.legrange.haveibeenpwned;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.codec.digest.DigestUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * API to access https://haveibeenpwned.com/.
 *
 * This API implements the specification found here:
 * https://haveibeenpwned.com/API/v2
 *
 * @author GideonLeGrange
 */
public final class HaveIBeenPwndApi {

    private final HaveIBeenPwndService hibpService;
    private final PwnedPasswordsService ppwService;
    private static final String HIBP_REST_URL = "https://haveibeenpwned.com/api/v2/";
    private static final String PPW_REST_URL = "https://api.pwnedpasswords.com/";
    private static final String DEFAULT_USER_AGENT = "HaveIBeenPwndJava-v1";

    /**
     * Create a new instance of the API with the default user agent 
     */
    public HaveIBeenPwndApi() {
        this(DEFAULT_USER_AGENT);
    }

    /**
     * Create a new instance of the API with the given user agent.
     *
     * @param userAgent The useragent to use.
     */
    public HaveIBeenPwndApi(String userAgent) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("User-Agent", userAgent).build();
            return chain.proceed(request);
        }).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HIBP_REST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        hibpService = retrofit.create(HaveIBeenPwndService.class);
        retrofit = new Retrofit.Builder()
                .baseUrl(PPW_REST_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
        ppwService = retrofit.create(PwnedPasswordsService.class);
    }

    /**
     * Getting all breaches for an account The most common use of the API is to
     * return a list of all breaches a particular account has been involved in.
     * The API takes a single parameter which is the account to be searched for
     *
     * @param account
     * @return The breaches for the account
     * @throws me.legrange.haveibeenpwned.HaveIBeenPwndException Thrown if there
     * is an error.
     */
    public List<Breach> getAllBreachesForAccount(String account) throws HaveIBeenPwndException {
        return getAllBreachesForAccount(account, null, false, false);
    }

    /**
     * Getting all breaches for an account The most common use of the API is to
     * return a list of all breaches a particular account has been involved in.
     *
     * @param account The account to search for
     * @param domain Filters the result set to only breaches against the domain
     * specified.
     * @param truncateResponse Returns only the name of the breach.
     * @param includeUnveridied Returns breaches that have been flagged as
     * "unverified"
     * @return The breaches for the account.
     * @throws HaveIBeenPwndException Thrown if there is an error.
     */
    public List<Breach> getAllBreachesForAccount(String account, String domain, boolean truncateResponse, boolean includeUnveridied) throws HaveIBeenPwndException {
        Call<List<Breach>> call = hibpService.getAllBreachesForAccount(account, includeUnveridied, truncateResponse, domain);
        try {
            Response<List<Breach>> res = call.execute();
            return res.body();
        } catch (IOException ex) {
            throw new HaveIBeenPwndException(ex.getMessage(), ex);
        }
    }

    /**
     * Sometimes just a single breach is required and this can be retrieved by
     * the breach "name". This is the stable value which may or may not be the
     * same as the breach "title" (which can change)
     *
     * @param breach The name of the breach
     * @return The breach if found
     * @throws HaveIBeenPwndException Thrown if there is an error.
     */
    public Optional<Breach> getBreachByName(String breach) throws HaveIBeenPwndException {
        Call<Breach> call = hibpService.getBreach(breach);
        try {
            Response<Breach> res = call.execute();
            return Optional.ofNullable(res.body());
        } catch (IOException ex) {
            throw new HaveIBeenPwndException(ex.getMessage(), ex);
        }
    }

    /**
     * A "data class" is an attribute of a record compromised in a breach
     *
     * @return All the data classes
     * @throws HaveIBeenPwndException Thrown if there is an error
     */
    public List<String> getAllDataClasses() throws HaveIBeenPwndException {
        Call<List<String>> call = hibpService.getDataClasses();
        try {
            return call.execute().body();
        } catch (IOException ex) {
            throw new HaveIBeenPwndException(ex.getMessage(), ex);
        }
    }

    /**
     * Each paste contains a number of attributes describing it. Note that
     * calling this for an account that has not been pwned will cause an
     * exception.
     *
     * @param account The account for which to get the pastes.
     * @return The pastes
     * @throws HaveIBeenPwndException Thrown if an error occurs, including if
     * the account has not been pwned
     */
    public List<Paste> getAllPastesForAccount(String account) throws HaveIBeenPwndException {
        return callService(hibpService.getAllPastesForAccount(account));
    }

    /**
     * Search pwned passwords for the given password. To understand how to use this, 
     * read the 'Searching by range' section in https://haveibeenpwned.com/API/v2
     *
     * @param hash5 The first 5 digits of the sha1 hash
     * @return
     * @throws HaveIBeenPwndException
     */
    public List<PwnedHash> searchByRange(String hash5) throws HaveIBeenPwndException {
        String res = callService(ppwService.searchByRange(hash5));
        Stream<String> lines = Arrays.asList(res.split("\n")).stream();
        return lines.map(line -> line.replace("\r","").split(":"))
                .map(parts -> new PwnedHash(parts[0], Integer.parseInt(parts[1])))
                .collect(Collectors.toList());
    }
    
    /** Check if a supplied password is pwned. 
     * 
     * Note that the password is not sent via the network. It is hashed using SHA1, and
     * only the first 5 characters of the hash is sent. Then the hash is compared against
     * the received possibly matching hashes. 
     * 
     * @param password The password to test
     * @return True if it is pwend
     * @throws HaveIBeenPwndException Thrown if something goes wrong. 
     */
    public boolean isPlainPasswordPwned(String password) throws HaveIBeenPwndException  {
        return isHashPasswordPwned(makeHash(password));
    }
    
    /** Check if a supplied hashed password is pwned. 
     * 
     * @param pwHash The password to test, encoded as a SHA1 hash. 
     * @return True if it is pwend
     * @throws HaveIBeenPwndException Thrown if something goes wrong. 
     */
    public boolean isHashPasswordPwned(String pwHash) throws HaveIBeenPwndException {
        String hash5 = pwHash.substring(0, 5);
        List<PwnedHash> hashes = searchByRange(hash5); 
        return hashes.stream().anyMatch(hash -> (hash5 + hash.getHash()).equals(pwHash));
    }
    
     
    /**
     * Make a SHA1 hash for sending to the Pwned Passwords API.
     *
     * @param password The password
     * @return The hash
     */
    public static String makeHash(String password) {
        return DigestUtils.sha1Hex(password).toUpperCase();
    }

    /**
     * Call a service and unpack it's result or errors
     *
     * @param <T> The type of service result
     * @param call The service to call
     * @return The result
     * @throws HaveIBeenPwndException Thrown if an error occurs
     */
    private <T> T callService(Call<T> call) throws HaveIBeenPwndException {
        try {
            Response<T> res = call.execute();
            if (res.isSuccessful()) {
                return res.body();
            } else {
                switch (res.code()) {
                    case 400:
                        throw new HaveIBeenPwndException("Bad request — the account does not comply with an acceptable format (i.e. it's an empty string)");
                    case 403:
                        throw new HaveIBeenPwndException("Forbidden — no user agent has been specified in the request");
                    case 404:
                        throw new HaveIBeenPwndException("Not found — the account could not be found and has therefore not been pwned");
                    case 429:
                        throw new HaveIBeenPwndException("Too many requests — the rate limit has been exceeded");
                    default:
                        throw new HaveIBeenPwndException("Unknown error code " + res.code());
                }
            }
        } catch (IOException ex) {
            throw new HaveIBeenPwndException(ex.getMessage(), ex);
        }
    }



}