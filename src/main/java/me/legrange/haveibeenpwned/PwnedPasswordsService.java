package me.legrange.haveibeenpwned;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *
 * @author gideon
 */
interface PwnedPasswordsService {

    @GET("range/{hash5}")
    Call<String> searchByRange(@Path("hash5") String hash5);

}
