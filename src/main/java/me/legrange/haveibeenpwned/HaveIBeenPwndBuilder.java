package me.legrange.haveibeenpwned;

import java.net.Proxy;

/** A builder pattern to setup the HaveIBeenPwndApi the way you want.
 *
 * @author GideonLeGrange
 */
public final class HaveIBeenPwndBuilder {

    private static final String HIBP_REST_URL = "https://haveibeenpwned.com/api/v3/";
    private static final String PPW_REST_URL = "https://api.pwnedpasswords.com/";
    private static final String DEFAULT_USER_AGENT = "HaveIBeenPwndJava-v1";

    private boolean addPadding = false;
    private String apiKey = "";
    private String haveIbeenPwndUrl  = HIBP_REST_URL;
    private String pwndPasswordsUrl = PPW_REST_URL;
    private String userAgent = DEFAULT_USER_AGENT;
    private Proxy proxy = null;


    /** Create a new builder with default user agent.
     *
     * @return The builder
     */
    public static HaveIBeenPwndBuilder create() {
        return create(DEFAULT_USER_AGENT);
    }

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

    /** Change the User-Agent to send with an HTTP request from the default
     *
     * @param userAgent The URL to use
     * @return The builder
     */
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

    /** Build the API
     *
     * @return The API
     */
    public HaveIBeenPwndApi build() {
        return new HaveIBeenPwndApi(haveIbeenPwndUrl, pwndPasswordsUrl, addPadding, userAgent, apiKey, proxy);
    }

    private HaveIBeenPwndBuilder() {

    }

}
