package org.example;


import javax.naming.NamingException;
import java.util.Collections;
import java.util.function.Function;

public class PorFavor extends ProvisioningStore{
    public static void main(String[] args) throws NamingException {
        ProvisioningStore provisioningstore = new ProvisioningStore();
        UserStore userstore = new UserStore();
        provisioningstore.newConnection();
        userstore.newConnection();

        for (int i=0; i<userstore.getUstore().size(); i++){
            for (int o=0; o<provisioningstore.getPstore().size(); o++){

                if(userstore.getUstore().get(i) != provisioningstore.getPstore().get(o)){
                    provisioningstore.getPstore().removeAll(Collections.singleton(userstore.getUstore().get(i)));
                }else {
                    continue;
                }
            }
        }
        System.out.println(userstore.getUstore());
        System.out.println(provisioningstore.getPstore());

        }
    }

