package me.legrange.haveibeenpwned;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Collections;
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
public class HaveIBeenPwndApi {

    private final HaveIBeenPwndService hibpService;
    private final PwnedPasswordsService ppwService;
    private final boolean addPadding;

    /**
     * Create a new instance of the API with the given user agent.
     */
     HaveIBeenPwndApi(String hibpUrl, String ppwUrl, boolean addPadding, String userAgent, Proxy proxy) {
         OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(chain -> {
             Request request = chain.request().newBuilder().addHeader("User-Agent", userAgent).build();
             return chain.proceed(request);
         });
         if (proxy != null) {
             builder = builder.proxy(proxy);
         }
         OkHttpClient client = builder.build();
         Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(hibpUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        hibpService = retrofit.create(HaveIBeenPwndService.class);
        retrofit = new Retrofit.Builder()
                .baseUrl(ppwUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
        ppwService = retrofit.create(PwnedPasswordsService.class);
        this.addPadding = addPadding;
    }

    /**
     * Getting all breaches for an account The most common use of the API is to
     * return a list of all breaches a particular account has been involved in.
     * The API takes a single parameter which is the account to be searched for
     *
     * @param account The account to check for breach
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
        return callService(hibpService.getAllBreachesForAccount(account, includeUnveridied, truncateResponse, domain)).orElse(Collections.EMPTY_LIST);
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
        return callService(hibpService.getDataClasses()).orElse(Collections.EMPTY_LIST);
    }

    /**
     * Each paste contains a number of attributes describing it. Note that
     * calling this for an account that has not been pwned will cause an
     * exception.
     *
     * @param account The account for which to get the pastes.
     * @return The pastes
     * @throws HaveIBeenPwndException Thrown if an error occurs
     */
    public List<Paste> getAllPastesForAccount(String account) throws HaveIBeenPwndException {
        return callService(hibpService.getAllPastesForAccount(account)).orElse(Collections.EMPTY_LIST);
    }

    /**
     * Search pwned passwords for the given password. To understand how to use
     * this, read the 'Searching by range' section in
     * https://haveibeenpwned.com/API/v2
     *
     * @param hash5 The first 5 digits of the sha1 hash
     * @return The list of hashes partially matching the given hash 
     * @throws HaveIBeenPwndException Thrown if an error occurs
     */
    public List<PwnedHash> searchByRange(String hash5) throws HaveIBeenPwndException {
        String res = callService(ppwService.searchByRange(hash5, addPadding)).orElse("");
        if (!res.isEmpty()) {
            Stream<String> lines = Arrays.asList(res.split("\n")).stream();
            return lines.map(line -> line.replace("\r", "").split(":"))
                    .map(parts -> new PwnedHash(parts[0], Integer.parseInt(parts[1])))
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
    
    
    /** Check if a supplied account is pwned. 
     * 
     * @param account The account to check
     * @return True if the account has been pwned.
     * @throws HaveIBeenPwndException Thrown if an error occurs
     */
    public boolean isAccountPwned(String account) throws HaveIBeenPwndException {
        return !getAllBreachesForAccount(account).isEmpty();
    }

    /**
     * Check if a supplied password is pwned.
     *
     * Note that the password is not sent via the network. It is hashed using
     * SHA1, and only the first 5 characters of the hash is sent. Then the hash
     * is compared against the received possibly matching hashes.
     *
     * @param password The password to test
     * @return True if it is pwend
     * @throws HaveIBeenPwndException Thrown if something goes wrong.
     */
    public boolean isPlainPasswordPwned(String password) throws HaveIBeenPwndException {
        return isHashPasswordPwned(makeHash(password));
    }

    /**
     * Check if a supplied hashed password is pwned.
     *
     * @param pwHash The password to test, encoded as a SHA1 hash.
     * @return True if it is pwend
     * @throws HaveIBeenPwndException Thrown if something goes wrong.
     */
    public boolean isHashPasswordPwned(String pwHash) throws HaveIBeenPwndException {
        String hash5 = pwHash.substring(0, 5);
        List<PwnedHash> hashes = searchByRange(hash5).stream()
                .filter(hash -> hash.getCount() > 0)
                .collect(Collectors.toList());
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
    private <T> Optional<T> callService(Call<T> call) throws HaveIBeenPwndException {
        try {
            Response<T> res = call.execute();
            if (!res.isSuccessful()) {
                switch (res.code()) {
                    case 400:
                        throw new HaveIBeenPwndException("Bad request — the account does not comply with an acceptable format (i.e. it's an empty string)");
                    case 403:
                        throw new HaveIBeenPwndException("Forbidden — no user agent has been specified in the request");
                    case 404:
                        break;
                    case 429:
                        throw new HaveIBeenPwndException("Too many requests — the rate limit has been exceeded");
                    default:
                        throw new HaveIBeenPwndException("Unknown error code " + res.code());
                }
            }
            return Optional.ofNullable(res.body());
        } catch (IOException ex) {
            throw new HaveIBeenPwndException(ex.getMessage(), ex);
        }
    }

}
