package org.example;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;


public class ProvisioningStore {


    DirContext connection;

    public void  newConnection(){

        // Connection info
        var env = new java.util.Hashtable(11);
        env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(javax.naming.Context.PROVIDER_URL, "ldap://192.168.153.130:10101");
        env.put(javax.naming.Context.SECURITY_PRINCIPAL, "cn=dsaadmin,ou=im,ou=ca,o=com");
        env.put(javax.naming.Context.SECURITY_CREDENTIALS, "Abc@123");

        try {
            connection = new InitialDirContext(env);
            System.out.println("Hello world!" + connection);
        }
        catch (AuthenticationException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void getAllUsers() throws NamingException {

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
            System.out.println(attr.get("cn"));
        }
    }
    public static void main(String[] args) throws NamingException {

        ProvisioningStore app = new ProvisioningStore();
        app.newConnection();
        app.getAllUsers();

    }
}