package me.rabierre.main;

import com.woorea.openstack.keystone.Keystone;
import com.woorea.openstack.keystone.model.Access;
import com.woorea.openstack.keystone.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: rabierre
 * Date: 13. 7. 29.
 * Time: 오후 2:24
 * To change this template use File | Settings | File Templates.
 */
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

        // execute returns instance of user which is created in keystone.
        User created = admin.users().create(user).execute();
        admin.users().delete(created.getId()).execute();
    }
}
