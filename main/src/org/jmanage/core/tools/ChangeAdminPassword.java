package org.jmanage.core.tools;

import org.jmanage.core.auth.UserManager;
import org.jmanage.core.auth.User;
import org.jmanage.core.auth.AuthConstants;
import org.jmanage.core.crypto.PasswordField;
import org.jmanage.core.crypto.EncryptedKey;
import org.jmanage.core.crypto.KeyManager;
import org.jmanage.core.crypto.Crypto;

import java.util.Arrays;

/**
 *
 * date:  Aug 3, 2004
 * @author	Rakesh Kalra
 */
public class ChangeAdminPassword {

    public static void main(String[] args)
        throws Exception {

        final UserManager userManager = UserManager.getInstance();
        final char[] oldPassword =
                PasswordField.getPassword("Enter Old password:");
        final User admin = userManager.getUser(AuthConstants.USER_ADMIN,
                oldPassword);
        if(admin == null){
            System.out.println("Invalid password.");
            return;
        }

        final char[] newPassword = getNewPassword();
        if(newPassword == null){
            return;
        }

        /* update the password for admin */
        admin.setPassword(Crypto.hash(newPassword));
        userManager.updateUser(admin);

        /* re-encrypt the key */
        EncryptedKey encryptedKey = KeyManager.readKey(oldPassword);
        encryptedKey.setPassword(newPassword);
        /* write the encryptedKey to the key file */
        KeyManager.writeKey(encryptedKey);

        System.out.println("Password has been changed");
    }

    private static char[] getNewPassword()
        throws Exception {

        final char[] password = PasswordField.getPassword("Enter new password:");
        final char[] password2 = PasswordField.getPassword("Re-enter new password:");
        if(!Arrays.equals(password, password2)){
            System.out.println("Passwords do not match.");
            return null;
        }
        return password;
    }
}
