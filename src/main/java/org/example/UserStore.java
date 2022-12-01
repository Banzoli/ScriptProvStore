package org.example;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

public class UserStore {
    DirContext connection;

    public void  newConnection(){

        // Connection info
        var env = new java.util.Hashtable(11);
        env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(javax.naming.Context.PROVIDER_URL, "ldap://192.168.153.130:20394");
        env.put(javax.naming.Context.SECURITY_PRINCIPAL, "eTdsaContainername=DSAs,eTnamespacename=CommonObjects,DC=etadb");
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

        String searchFilter = "(objectClass=eTGlobalUser)";
        String[] reqAtt = {"eTFullName"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);

        NamingEnumeration users = connection.search("eTGlobalUserContainerName=Global Users,eTNamespaceName=CommonObjects,dc=im,dc=etadb", searchFilter,controls);

        SearchResult result = null;
        while (users.hasMore())
        {
            result = (SearchResult) users.next();
            Attributes attr = result.getAttributes();
            System.out.println(attr.get("eTFullName"));
        }
    }
    public static void main(String[] args) throws NamingException {

        UserStore app = new UserStore();
        app.newConnection();
        app.getAllUsers();

    }
}
