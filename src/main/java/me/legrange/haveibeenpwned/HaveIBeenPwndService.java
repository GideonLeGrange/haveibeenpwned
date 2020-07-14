package me.legrange.haveibeenpwned;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author gideon
 */
interface HaveIBeenPwndService {

    @GET("breachedaccount/{account}")
    Call<List<Breach>> getAllBreachesForAccount(@Header("hibp-api-key") String apiKey,
            @Path(value = "account", encoded = false) String account,
            @Query("includeUnveridied") boolean includeUnverified,
            @Query("truncateResponse") boolean truncateResponse,
            @Query("domain") String domain);


    @GET("breaches")
    Call<List<Breach>> getBreaches(@Query("domain") String domain);

    @GET("breach/{name}")
    Call<Breach> getBreach(@Path(value="name", encoded = false) String name);
    
    @GET("dataclasses")
    Call<List<String>> getDataClasses();
    
    @GET("pasteaccount/{account}")
    Call<List<Paste>> getAllPastesForAccount(@Header("hibp-api-key") String apiKey, @Path(value="account", encoded = false) String account);

}
