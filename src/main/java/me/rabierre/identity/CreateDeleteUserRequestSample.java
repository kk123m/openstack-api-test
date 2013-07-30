package me.rabierre.identity;

import com.woorea.openstack.keystone.Keystone;
import com.woorea.openstack.keystone.model.Access;
import com.woorea.openstack.keystone.model.User;
import me.rabierre.SimpleConfiguration;

public class CreateDeleteUserRequestSample {
    public static void main(String[] args) {
        Keystone client = new Keystone(SimpleConfiguration.KEYSTONE_PUBLIC_URL);

        Access access = client
                .tokens()
                .authenticate()
                .withUsernamePassword(SimpleConfiguration.KEYSTONE_USERNAME, SimpleConfiguration.KEYSTONE_PASSWORD)
                .withTenantName(SimpleConfiguration.TENANT_NAME)
                .execute();

        Keystone admin = new Keystone(SimpleConfiguration.KEYSTONE_ADMIN_URL);
        admin.token(access.getToken().getId());

        User user = new User();
        user.setName("rabierre");
        user.setPassword("passwd123");
        user.setEnabled(Boolean.TRUE);

        // POST http://10.0.1.3:35357/v2.0/users
        User created = admin.users().create(user).execute(); // execute returns instance of user created in keystone.
        admin.users().delete(created.getId()).execute();
    }
}