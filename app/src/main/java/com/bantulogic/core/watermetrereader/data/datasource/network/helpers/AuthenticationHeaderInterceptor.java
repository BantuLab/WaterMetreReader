package com.bantulogic.core.watermetrereader.data.datasource.network.helpers;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationHeaderInterceptor implements Interceptor {
    //region COMMENTS/NOTES
    /**
     * Request an auth token... again
     * Your first request for an auth token might fail for several reasons:
     *
     * An error in the device or network caused AccountManager to fail.
     * The user decided not to grant your app access to the account.
     * The stored account credentials aren't sufficient to gain access to the account.
     * The cached auth token has expired.
     * If the request returns an HTTP error code of 401, then your token has been denied.
     * As mentioned in the last section, the most common reason for this is that the token
     * has expired. The fix is simple: call AccountManager.invalidateAuthToken() and repeat the
     * token acquisition dance one more time.
     *
     * Because expired tokens are such a common occurrence, and fixing them is so easy,
     * many applications just assume the token has expired before even asking for it.
     * If renewing a token is a cheap operation for your server, you might prefer to call
     * AccountManager.invalidateAuthToken() before the first call to AccountManager.getAuthToken(),
     * and spare yourself the need to request an auth token twice.
     * 1. find a way of getting hold of the currently logged-in user and the authentication token
     * -Credentials are exchanged against a token which is then attached to every subsequent request.
     * -Token are temporary and get expired/refreshed after a certain period of time.
     * -Apps might need to get the updated token in some way so that access won’t be revoked.
     * -If expired, app need to take necessary actions. There are several use cases as following:
     * -A. Use case 1: Some API returns the updated token in response header for once.
     *         // -B. Use case 2: MetreReaderApp should be instantly logged out when token get expired and prompt
     *         // -user to login again (For example: when user change facebook password using website and
     *         // -want to continue using the mobile app
     *         // -C. Use case 3: When token get expired, it might be required to automatically login in
     *         // -background to get the new token without notifying anything to user and let them do
     *         // -what they intended.
     *         // -Token might get expired any time. This can be encountered with any of the consecutive
     *         // -Http requests. That’s why we need to have a mechanism of handling token expiration that
     *         // -works centrally at the module level and doesn’t require any change in
     *         // -the top level implementation.
     *         //2. Attach the Auth token to every request to the API
     *         //3. If auth Token is expired, re-issue the request
     *   The use of tokens has many benefits compared to traditional methods such as cookies.
     *
     * WHY USE TOKENS
     * Tokens are stateless. The token is self-contained and contains all the information it needs
     * for authentication. This is great for scalability as it frees your server from having to
     * store session state.
     * Tokens can be generated from anywhere. Token generation is decoupled from token verification
     * allowing you the option to handle the signing of tokens on a separate server or even through
     * a different company such us Auth0.
     * Fine-grained access control. Within the token payload you can easily specify user roles and
     * permissions as well as resources that the user can access.
     * These are just some of the benefits JSON Web Tokens provide.
     * To learn more check out this blog post(https://auth0.com/blog/2014/01/07/angularjs-authentication-with-cookies-vs-token/)
     * that takes a deeper dive and compares tokens to cookies for managing authentication.
     *
     * ANATOMY OF A JSON WEB TOKEN
     * A JSON Web Token consists of three parts: Header, Payload and Signature.
     * The header and payload are Base64 encoded, then concatenated by a period, finally the
     * result is algorithmically signed producing a token in the form of header.claims.signature.
     * The header consists of metadata including the type of token and the hashing algorithm used
     * to sign the token. The payload contains the claims data that the token is encoding.
     *
     * Tokens are signed to protect against manipulation, they are not encrypted.
     * What this means is that a token can be easily decoded and its contents revealed.
     *
     * In a real world scenario, a client would make a request to the server and pass the token
     * with the request. The server would attempt to verify the token and, if successful,
     * would continue processing the request. If the server could not verify the token,
     * the server would send a 401 Unauthorized and a message saying that the request
     * could not be processed as authorization could not be verified.
     *
     * JSON WEB TOKEN BEST PRACTICES
     * Before we actually get to implementing JWT, let’s cover some best practices to ensure
     * token based authentication is properly implemented in your application.
     *
     * 1. Keep it secret. Keep it safe. The signing key should be treated like any other credentials
     * and revealed only to services that absolutely need it.
     * 2. Do not add sensitive data to the payload. Tokens are signed to protect against manipulation
     * and are easily decoded. Add the bare minimum number of claims to the payload for best
     * performance and security.
     * 3. Give tokens an expiration. Technically, once a token is signed – it is valid forever – unless
     * the signing key is changed or expiration explicitly set. This could pose potential issues so
     * have a strategy for expiring and/or revoking tokens.
     * 4. Embrace HTTPS. Do not send tokens over non-HTTPS connections as those requests can be
     * intercepted and tokens compromised.
     * 5. Consider all of your authorization use cases. Adding a secondary token verification system
     * that ensure tokens were generated from your server, for example, may not be common practice,
     * but may be necessary to meet your requirements.
     *
     * USE CASES FOR TOKEN BASED AUTHENTICATION
     * We’ve seen how easy it is to implement JWT authentication and secure our API.
     * To conclude, let’s examine use cases where token based authentication is best suited for.
     *
     * Platform-as-a-Service Applications – exposing RESTful APIs that will be consumed by a
     * variety of frameworks and clients.
     * Mobile Apps – implementing native or hybrid mobile apps that interact with your services.
     * Single Page Applications (SPA) – building modern applications with frameworks such as
     * Angular and React.
     */
    //endregion
    private String authToken;

    public AuthenticationHeaderInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken);
        Request request = builder.build();
        /**
         * Add some logic to invalidate the token
         *  Response mainResponse = chain.proceed(chain.request());
         *         if (mainResponse.code() == 401 || mainResponse.code() == 403) {
         *             session.invalidate();
         *         }
         *         return mainResponse;
         *
         */
        return chain.proceed(request);
    }
}