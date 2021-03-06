/**
 * Copyright 2004-2005 jManage.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmanage.core.auth;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.security.auth.callback.*;
import java.util.Map;

/**
 *
 * date:  Jun 27, 2004
 * @author	Rakesh Kalra, Shashank Bellary
 */
public class LoginModule implements javax.security.auth.spi.LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private boolean loginStatus;
    private User loggedInUser = null;

    /**
     * Initialize this LoginModule.
     *
     * <p> This method is called by the <code>LoginContext</code>
     * after this <code>LoginModule</code> has been instantiated.
     * The purpose of this method is to initialize this
     * <code>LoginModule</code> with the relevant information.
     * If this <code>LoginModule</code> does not understand
     * any of the data stored in <code>sharedState</code> or
     * <code>options</code> parameters, they can be ignored.
     *
     * <p>
     *
     * @param subject the <code>Subject</code> to be authenticated. <p>
     *
     * @param callbackHandler a <code>CallbackHandler</code> for communicating
     *			with the end user (prompting for usernames and
     *			passwords, for example). <p>
     *
     * @param sharedState state shared with other configured LoginModules. <p>
     *
     * @param options options specified in the login
     *			<code>Configuration</code> for this particular
     *			<code>LoginModule</code>.
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map sharedState, Map options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        loginStatus = false;
    }

    /**
     * Method to authenticate a <code>Subject</code> (phase 1).
     *
     * <p> The implementation of this method authenticates
     * a <code>Subject</code>.  For example, it may prompt for
     * <code>Subject</code> information such
     * as a username and password and then attempt to verify the password.
     * This method saves the result of the authentication attempt
     * as private state within the LoginModule.
     *
     * <p>
     *
     * @exception LoginException if the authentication fails
     *
     * @return true if the authentication succeeded, or false if this
     *			<code>LoginModule</code> should be ignored.
     */
    public boolean login() throws LoginException {
        /*  Authenticate user   */
        if (callbackHandler == null) {
            throw new LoginException("No callback handler is available");
        }
        NameCallback nameCallback = new NameCallback("username");
        PasswordCallback pwdCallback = new PasswordCallback("password", false);
        Callback[] callbacks = new Callback[] {nameCallback, pwdCallback};

        String username = null;
        char[] password = null;
        try {
            callbackHandler.handle(callbacks);
            username = nameCallback.getName();
            password = pwdCallback.getPassword();
        } catch (java.io.IOException ioe) {
            throw new LoginException(ioe.toString());
        } catch (UnsupportedCallbackException ce) {
            throw new LoginException("Error: " + ce.getCallback().toString());
        }
        /* check username and password */
        UserManager userManager = UserManager.getInstance();
        loggedInUser = userManager.verifyUsernamePassword(username, password);
        loginStatus = loggedInUser != null;
        return loginStatus;
    }

    /**
     * Method to commit the authentication process (phase 2).
     *
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates relevant
     * Principals and Credentials with the <code>Subject</code> located in the
     * <code>LoginModule</code>.  If this LoginModule's own
     * authentication attempted failed, then this method removes/destroys
     * any state that was originally saved.
     *
     * <p>
     *
     * @exception LoginException if the commit fails
     *
     * @return true if this method succeeded, or false if this
     *			<code>LoginModule</code> should be ignored.
     */
    public boolean commit() throws LoginException {
        if(loginStatus){
            subject.getPrincipals().add(loggedInUser);
            return true;
        }
        return false;
    }

    /**
     * Method to abort the authentication process (phase 2).
     *
     * <p> This method is called if the LoginContext's
     * overall authentication failed.
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method cleans up any state
     * that was originally saved.
     *
     * <p>
     *
     * @exception LoginException if the abort fails
     *
     * @return true if this method succeeded, or false if this
     *			<code>LoginModule</code> should be ignored.
     */
    public boolean abort() throws LoginException {
        return true;
    }

    /**
     * Method which logs out a <code>Subject</code>.
     *
     * <p>An implementation of this method might remove/destroy a Subject's
     * Principals and Credentials.
     *
     * <p>
     *
     * @exception LoginException if the logout fails
     *
     * @return true if this method succeeded, or false if this
     *			<code>LoginModule</code> should be ignored.
     */
    public boolean logout() throws LoginException {
        subject = null;
        loginStatus = false;
        return true;
    }
}