package org.example;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class ProvisioningStore {
    DirContext connection;

    private List<String> pstore;

    public void  newConnection() throws NamingException {

        this.setPstore(new ArrayList<String>());

        // Connection info
        var env = new Hashtable(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.153.130:10101");
        env.put(Context.SECURITY_PRINCIPAL, "cn=dsaadmin,ou=im,ou=ca,o=com");
        env.put(Context.SECURITY_CREDENTIALS, "Abc@123");

        try {
            connection = new InitialDirContext(env);
            System.out.println(connection);
        }
        catch (AuthenticationException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch (NamingException e) {
            e.printStackTrace();
        }

        String searchFilter = "(objectClass=inetOrgPerson)";
        String[] reqAtt = {"cn"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);

        NamingEnumeration users = connection.search("ou=people,ou=im,ou=ca,o=com", searchFilter,controls);

        SearchResult result = null;
        while (users.hasMore())
        {
            result = (SearchResult) users.next();
            Attributes attr = result.getAttributes();
            var dasdsa = attr.get("cn").toString().replace("cn: ","");
            pstore.add(dasdsa);
        }
    }

    public List<String> getPstore() {
        return pstore;
    }

    public void setPstore(List<String> pstore) {
        this.pstore = pstore;
    }

    public static void main(String[] args) throws NamingException {

        ProvisioningStore app = new ProvisioningStore();
        app.newConnection();

    }
}