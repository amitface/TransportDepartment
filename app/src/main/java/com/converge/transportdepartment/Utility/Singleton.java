package com.converge.transportdepartment.Utility;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by converge on 17/6/16.
 */
public class Singleton
{

    public static boolean id_imgdone = false;
    public static boolean usr_imgdone = false;

    public static volatile String refno="";
    public static volatile String statecode="";
    public static volatile String rtocode="";
    public static volatile String applicant_first_name="";
    public static volatile String applicant_middle_name="";
    public static volatile String applicant_last_name="";    
    public static volatile String dob="";
    public static volatile String gender_type="";
    public static volatile String relation_type="";
    public static volatile String relative_first_name="";
    public static volatile String relative_middle_name="";
    public static volatile String relative_last_name="";
    public static volatile String edu_qualification="";
    public static volatile String identification_marks="";   
    public static volatile String blood_group="";
    
    //Permanent Address
    public static volatile String p_flat_house_no="";
    public static volatile String p_street_locality="";
    public static volatile String p_village_city="";
    public static volatile String p_district="";
    public static volatile String p_state="";
    public static volatile String p_pin="";
    public static volatile String p_phone_no="";
    public static volatile String p_mobile_no="";
    public static volatile String p_years="";
    public static volatile String p_months="";
    
    //Present Address
    public static volatile String t_flat_house_no="";
    public static volatile String t_street_locality="";
    public static volatile String t_village_city="";
    public static volatile String t_district="";
    public static volatile String t_state="";
    public static volatile String t_pin="";
    public static volatile String t_phone_no="";
    public static volatile String t_mobile_no="";
    public static volatile String t_years="";
    public static volatile String t_months="";
    
    public static volatile String citizenship_status_type="";
    public static volatile String birth_place="";
    public static volatile String year="";
    public static volatile String birth_country="";
    public static volatile String email_id="";
    public static volatile String proofcode="";
    public static volatile String licence_certificate_badge_no="";
    public static volatile String issuing_authority="";
    public static volatile String date_of_issue="";
    public static volatile String covs="";
    public static volatile String rcnumber="";
    public static volatile String parentleterforbelow18age="";
    public static volatile String allnecessarycertificates="";
    public static volatile String exemptedmedicaltest="";
    public static volatile String exemptedpreliminarytest="";
    public static volatile String convicted="";
    public static volatile String attdlnumber="";
    public static volatile String attdtofconviction="";
    public static volatile String attreason="";


    public static boolean checkInternetConenction(Context context)
    {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            return true;
        }
        else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {

            return false;
        }
        return false;
    }


}
