package api;

/**
 * Created by Harit Moolphunt on 18/7/2560.
 */

public class WooCommerceApi extends org.scribe.builder.api.DefaultApi10a {

    @Override
    public org.scribe.model.Verb getRequestTokenVerb()
    {
        return org.scribe.model.Verb.POST;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return "http://maxile.ddns.net:11111/wordpress/wc-auth/authorize";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "none";
    }

    @Override
    public String getAuthorizationUrl(org.scribe.model.Token requestToken) {
        return "none";
    }
}