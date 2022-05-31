package me.legrange.haveibeenpwned;

import java.net.Proxy;

import okhttp3.OkHttpClient;

/** A builder pattern to setup the HaveIBeenPwndApi the way you want.
 *
 * @author GideonLeGrange
 */
public final class HaveIBeenPwndBuilder {

    private static final String HIBP_REST_URL = "https://haveibeenpwned.com/api/v3/";
    private static final String PPW_REST_URL = "https://api.pwnedpasswords.com/";

    private boolean addPadding = false;
    private String apiKey = "";
    private String haveIbeenPwndUrl  = HIBP_REST_URL;
    private String pwndPasswordsUrl = PPW_REST_URL;
    private String userAgent;
    private Proxy proxy = null;
    private OkHttpClient.Builder okHttpClientBuilder;

    /** Create a new builder.
     *
     * @param userAgent The user agent to use when connecting
     * @return The builder
     */
    public static HaveIBeenPwndBuilder create(String userAgent) {
        HaveIBeenPwndBuilder builder = new HaveIBeenPwndBuilder();
        builder.userAgent = userAgent;
        return builder;
    }

    /** Add the API key to use.
     *
     * @param apiKey API key to use
     * @return The builder
     */
    public HaveIBeenPwndBuilder withApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /** Change the URL for the HaveIBeenPwnd breach service.
     *
     * @param url The URL to use
     * @return The builder
     */
    public HaveIBeenPwndBuilder withHaveIBeenPwndUrl(String url) {
        this.haveIbeenPwndUrl = url;
        return this;
    }

    /** Change the URL for the HaveIBeenPwnd password service.
     *
     * @param url The URL to use
     * @return The builder
     */
    public HaveIBeenPwndBuilder withPwndPasswordsUrl(String url) {
        this.pwndPasswordsUrl = url;
        return this;
    }

    /**
     * This is deprecated and left for backwards compatibility. Since specifying the
     * User-Agent is required, the right way to use the builder is to pass it to
     * create(). See the README for more information.
     */
    @Deprecated()
    public HaveIBeenPwndBuilder withUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /** Set if padding on pwnd password calls is enabled.
     *
     * @param addPadding Is padding is required?
     * @return The builder
     */
    public HaveIBeenPwndBuilder addPadding(boolean addPadding) {
        this.addPadding = addPadding;
        return this;
    }

    /** Add a HTTP proxy to use when connecting to the API.
     *
     * @param proxy The proxy to use
     * @return The builder
     */
    public HaveIBeenPwndBuilder withProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    /** Add a OkHttpClient builder to use when connecting to the API.
     *
     * @param okHttpClientBuilder the OkHttpClient builder to use
     * @return The builder
     */
    public HaveIBeenPwndBuilder withOkHttpClientBuilder(OkHttpClient.Builder okHttpClientBuilder ) {
        this.okHttpClientBuilder = okHttpClientBuilder;
        return this;
    }

    /** Build the API
     *
     * @return The API
     */
    public HaveIBeenPwndApi build() {
        return new HaveIBeenPwndApi(haveIbeenPwndUrl, pwndPasswordsUrl, addPadding, userAgent, apiKey, proxy, okHttpClientBuilder);
    }

    private HaveIBeenPwndBuilder() {
    }

}
