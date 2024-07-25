package com.opencars.netgo.powerbiembedded.config;

public class Config {

    public static final boolean DEBUG = false;

    //	Two possible Authentication methods:
    //	- For authentication with master user credential choose MasterUser as AuthenticationType.
    //	- For authentication with app secret choose ServicePrincipal as AuthenticationType.
    //	More details here: https://aka.ms/EmbedServicePrincipal
    public static final String authenticationType = "ServicePrincipal";

    //	Common configuration properties for both authentication types
    // Enter workspaceId / groupId
    public static final String workspaceId = "009b373e-226a-48a5-890e-f220029d5af6";

    // The id of the report to embed.
    public static final String reportId = "90faa965-dc8b-47f8-ba10-bf08b4cbf75c";

    // Enter Application Id / Client Id
    public static final String clientId = "cc40a9f7-e47a-4f03-beb8-fe9ee536c90f";

    // Enter MasterUser credentials
    public static final String pbiUsername = "";
    public static final String pbiPassword = "";

    // Enter ServicePrincipal credentials
    public static final String tenantId = "f0c28cac-57dc-4da3-b1c7-73ce3bb4a6f1";
    public static final String appSecret = "EC-8Q~.WOoiunpGDnZgAK4lXLh.-Dlo_to9z1dax";

    //	DO NOT CHANGE
    public static final String authorityUrl = "https://login.microsoftonline.com/";
    public static final String scopeUrl = "https://analysis.windows.net/powerbi/api/.default";


    private Config(){
        //Private Constructor will prevent the instantiation of this class directly
        throw new IllegalStateException("Config class");
    }
}
