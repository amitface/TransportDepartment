package com.converge.transportdepartment.DataBaseHelper;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by converge on 20/6/16.
 */

public class DBAdapter {
    static final String KEY_ROWID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "LLodisha";
    static final String DATABASE_TABLE = "user_detail";
    static final String DATABASE_TABLE_Id_Proof = "user_id_proof";
    static final int DATABASE_VERSION = 2;
    static final String DATABASE_CREATE ="CREATE TABLE user_detail (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE ," +
            " refno TEXT," +
//            " licence_type TEXT," +
//            " statecode TEXT," +
            " rtocode TEXT," +
            " first_name TEXT," +
            " middle_name TEXT," +
            " last_name TEXT," +
            " dob TEXT," +

            " gender TEXT," +

            " birth_place TEXT," +
            " year TEXT," +
            " month TEXT," +

            " birth_country TEXT," +

            " email_id TEXT," +

            " relation TEXT," +

            " p_first_name TEXT, " +
            " p_middle_name TEXT," +
            " p_last_name TEXT," +

            " p_flat_no TEXT," +
            " p_flat_house_name TEXT," +
            " p_house_no TEXT," +
            " p_street TEXT," +
            " p_locality TEXT," +
            " p_village_city TEXT," +
            " p_taluka TEXT," +
            " p_district TEXT, " +

            " p_state TEXT," +

            " p_years TEXT," +
            " p_months TEXT," +
            " p_pin TEXT," +
            " p_mobile_no TEXT," +

            " t_flat_no TEXT," +
            " t_flat_house_name TEXT," +
            " t_house_no TEXT," +
            " t_street TEXT," +
            " t_locality TEXT," +
            " t_village_city TEXT," +
            " t_taluka TEXT," +
            " t_district TEXT," +

            " t_state TEXT," +

            " t_years TEXT," +
            " t_months TEXT," +
            " t_pin TEXT," +
            " t_mobile_no TEXT," +

            " citizenship_status TEXT," +
            " edu_qualification TEXT," +
            " identification_marks TEXT," +
            " blood_group TEXT," +
            " blood_group_rh TEXT)";
//            " covs TEXT," +
//            " rcnumber TEXT," +
//            " parentletterforbelow18age TEXT," +
//            " allnecessarycertificates TEXT," +
//            " exemptedmedicaltest TEXT, " +
//            " exemptedpreliminarytest TEXT," +
//            " convicted TEXT," +
//            " attdlnumber TEXT, " +
//            " attdtofconviction TEXT," +
//
//
//            " attreason TEXT
//    static final String DATABASE_INSERT ="INSERT INTO user_detail VALUES('1','6','AP','OD01','L','Priyesh','Rao', 'Yogi','05-08-1989'" +
//                                         ",'male','father','Shankar','Rao','Yogi','4' ,'A mole on the right side of the neck'," +
//                                         "'O+','22','vijay nagar','hyderbad','Ranga Reddy'," +
//            "'AP','500012',NULL,'1234567890','5','5','22','vijay nagar'," +
//            "'hyderabad','Ranga Reddy','AP','500012',NULL,'5','5','birth'," +
//            " 'Warangal','2012','05','IND','ramana@gmail.com','3,4',NULL,NULL," +
//            " 'y','n','n','n',NULL," +
//            "NULL,NULL);";

    static final String DATABASE_ID_TABLE = "CREATE  TABLE user_id_proof (id  PRIMARY KEY  NOT NULL , name1 CHAR, doc_num1 TEXT, authority1 TEXT, do_issue1 TEXT," +
            " name2 CHAR, doc_num2 TEXT, authority2 TEXT, do_issue2 TEXT," +
            " name3 CHAR, doc_num3 TEXT, authority3 TEXT, do_issue3 TEXT," +
            " name4 CHAR, doc_num4 TEXT, authority4 TEXT, do_issue4 TEXT)";

    static final String DATABASE_INSERT_ID_TABLE ="INSERT INTO user_id_proof VALUES(1,"+
            "'0','','',''," +
            "'0','','',''," +
            "'0','','',''," +
            "'0','','','');";

    static final String DATABASE_INSERT ="INSERT INTO user_detail VALUES(1,'1'," +
            "'','','','','','','','','',''," +
            "'','','','','','','','','',''," +
            "'','','','','','','','','',''," +
            "'','','','','','','','','',''," +
            "'','','','','','');";

    static String [] dataFeildIdProof = {"id", "name1", "doc_num1", "authority1" ,"do_issue1",
            "name2","doc_num2 ","authority2 ","do_issue2",
            "name3","doc_num3 ","authority3 ","do_issue3",
            "name4","doc_num4 ","authority4 ","do_issue4"};

    static String [] dataFeilds={
            " refno ",
            " rtocode ",
            " first_name ",
            " middle_name ",
            " last_name ",
            " dob ",

            " gender ",

            " birth_place ",
            " year ",
            " month ",

            " birth_country ",

            " email_id ",

            " relation ",

            " p_first_name ",
            " p_middle_name ",
            " p_last_name ",

            " p_flat_no ",
            " p_flat_house_name ",
            " p_house_no ",
            " p_street ",
            " p_locality ",
            " p_village_city ",
            " p_taluka ",
            " p_district , " +

            " p_state ",

            " p_years ",
            " p_months ",
            " p_pin ",
            " p_mobile_no ",

            " t_flat_no ",
            " t_flat_house_name ",
            " t_house_no ",
            " t_street ",
            " t_locality ",
            " t_village_city ",
            " t_taluka ",
            " t_district ",

            " t_state ",

            " t_years ",
            " t_months ",
            " t_pin ",
            " t_mobile_no ",

            " citizenship_status ",
            " edu_qualification ",
            " identification_marks ",
            " blood_group ",
            " blood_group_rh "};
    //Id proof
    static final String id_name="name";

    static final String doc_num="doc_num";

    static final String authority="authority";

    static final String do_issue="do_issue";

    //Personal Info
    static final String refno="refno";
    static final String statecode="statecode";
    static final String rtocode="rtocode";
    static final String licence_type="licence_type";
    static final String first_name="first_name";
    static final String middle_name="middle_name";
    static final String last_name="last_name";
    static final String dob="dob";
    static final String gender="gender";

    static final String birth_place="birth_place";
    static final String year="year";
    static final String month="month";
    static final String birth_country="birth_country";
    static final String email_id="email_id";
    static final String relation="relation";
    static final String p_first_name="p_first_name";
    static final String p_middle_name="p_middle_name";
    static final String p_last_name="p_last_name";


//    static final String permanent_address="permanent_address";
    static final String p_flat_no="p_flat_no";
    static final String p_flat_house_name="p_flat_house_name";
    static final String p_house_no="p_house_no";
    static final String p_street="p_street";
    static final String p_locality="p_locality";
    static final String p_village_city="p_village_city";
    static final String p_taluka="p_taluka";
    static final String p_district="p_district";
    static final String p_state="p_state";
    static final String p_years="p_years";
    static final String p_months="p_months";
    static final String p_pin="p_pin";
    static final String p_mobile_no="p_mobile_no";
//    static final String p_phone_no="p_phone_no";
    //24

    static final String t_flat_no="t_flat_no";
    static final String t_flat_house_name="t_flat_house_name";
    static final String t_house_no="t_house_no";
    static final String t_street="t_street";
    static final String t_locality="t_locality";
    static final String t_village_city="t_village_city";
    static final String t_taluka="t_taluka";
    static final String t_district="t_district";
    static final String t_state="t_state";
    static final String t_years="t_years";
    static final String t_months="t_months";
    static final String t_pin="t_pin";
    static final String t_mobile_no="t_mobile_no";
    //33

    static final String citizenship_status="citizenship_status";
    static final String edu_qualification="edu_qualification";
    static final String identification_marks="identification_marks";
    static final String blood_group="blood_group";//13
    private final String blood_group_rh="blood_group_rh";

//    static final String migration="migration";

//    static final String covs="covs";
//    static final String rcnumber="rcnumber";
//    static final String parentletterforbelow18age="parentletterforbelow18age";
//    static final String allnecessarycertificates="allnecessarycertificates";
//    static final String exemptedmedicaltest="exemptedmedicaltest";
//    static final String exemptedpreliminarytest="exemptedpreliminarytest";
//    static final String convicted="convicted";
//    static final String attdlnumber="attdlnumber";
//    static final String attdtofconviction="attdtofconviction";
//    static final String attreason="attreason";



    final   Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    private static ProgressDialog progressDialog;


    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }




    private static class DatabaseHelper extends SQLiteOpenHelper
    {


        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {

            try {
                db.execSQL(DATABASE_CREATE);
                db.execSQL(DATABASE_ID_TABLE);

                db.execSQL(DATABASE_INSERT);
                db.execSQL(DATABASE_INSERT_ID_TABLE);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_Id_Proof);
            onCreate(db);
        }
    }
    
    
    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    //---insert a contact into the database---
    public long insertData(String name, String email)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    //---deletes a particular contact---
    public boolean deleteData(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    //---retrieves all the contacts---
    public Cursor getAllDetails()
    {
        return db.query(DATABASE_TABLE, dataFeilds , null, null, null, null, null);
    }

    public Cursor getAllDetailsIdProof() {
        return db.query(DATABASE_TABLE_Id_Proof, dataFeildIdProof , null, null, null, null, null);
    }
    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor = db.rawQuery("Select * from user_detail",null);
//                db.query(true, DATABASE_TABLE, "*", KEY_ROWID + "=" + rowId, null,
//            null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateDataIdProof(HashMap<String, String> hashMap) {
        ContentValues args = new ContentValues();

        args.put(id_name+"1", hashMap.get("name1"));
        args.put(id_name+"2", hashMap.get("name2"));
        args.put(id_name+"3", hashMap.get("name3"));
        args.put(id_name+"4", hashMap.get("name4"));

        args.put(doc_num+"1", hashMap.get("doc_num1"));
        args.put(doc_num+"2", hashMap.get("doc_num2"));
        args.put(doc_num+"3", hashMap.get("doc_num3"));
        args.put(doc_num+"4", hashMap.get("doc_num4"));

        args.put(authority+"1", hashMap.get("authority1"));
        args.put(authority+"2", hashMap.get("authority2"));
        args.put(authority+"3", hashMap.get("authority3"));
        args.put(authority+"4", hashMap.get("authority4"));

        args.put(do_issue+"1", hashMap.get("do_issue1"));
        args.put(do_issue+"2", hashMap.get("do_issue2"));
        args.put(do_issue+"3", hashMap.get("do_issue3"));
        args.put(do_issue+"4", hashMap.get("do_issue4"));
        return db.update(DATABASE_TABLE_Id_Proof, args, KEY_ROWID + "=" + 1, null) > 0;
    }


    //---updates a contact---
    public boolean updateData(HashMap<String, String> hashMap)
    {
        ContentValues args = new ContentValues();

//         args.put(statecode,hashMap.get("statecode"));
         args.put(rtocode,hashMap.get("rtocode"));
//         args.put(licence_type,hashMap.get("licence_type"));
         args.put(first_name,hashMap.get("first_name"));
         args.put(middle_name,hashMap.get("middle_name"));
         args.put(last_name,hashMap.get("last_name"));

         args.put(dob,hashMap.get("dob"));
         args.put(gender,hashMap.get("gender"));

        args.put(birth_place,hashMap.get("birth_place"));
        args.put(year,hashMap.get("year"));
        args.put(month,hashMap.get("month"));
        args.put(birth_country,hashMap.get("birth_country"));
        args.put(email_id,hashMap.get("email_id"));

         args.put(relation,hashMap.get("relation"));
         args.put(p_first_name,hashMap.get("p_first_name"));
         args.put(p_middle_name,hashMap.get("p_middle_name"));
         args.put(p_last_name,hashMap.get("p_last_name"));


//         args.put(permanent_address,hashMap.get("permanent_address"));
         args.put(p_flat_no,hashMap.get("p_flat_no"));
         args.put(p_flat_house_name,hashMap.get("p_flat_house_name"));
         args.put(p_house_no,hashMap.get("p_house_no"));
         args.put(p_street,hashMap.get("p_street"));
         args.put(p_locality,hashMap.get("p_locality"));
         args.put(p_village_city,hashMap.get("p_village_city"));
         args.put(p_taluka,hashMap.get("p_taluka"));
         args.put(p_district,hashMap.get("p_district"));
         args.put(p_state,hashMap.get("p_state"));
         args.put(p_years,hashMap.get("p_years"));
         args.put(p_months,hashMap.get("p_months"));
         args.put(p_pin,hashMap.get("p_pin"));
         args.put(p_mobile_no,hashMap.get("p_mobile_no"));
//         args.put(p_phone_no,hashMap.get("p_phone_no"));


         args.put(t_flat_no,hashMap.get("t_flat_no"));
         args.put(t_flat_house_name,hashMap.get("t_flat_house_name"));
         args.put(t_house_no,hashMap.get("t_house_no"));
         args.put(t_street,hashMap.get("t_street"));
         args.put(t_locality,hashMap.get("t_locality"));
         args.put(t_village_city,hashMap.get("t_village_city"));
         args.put(t_taluka,hashMap.get("t_taluka"));
         args.put(t_district,hashMap.get("t_district"));
         args.put(t_state,hashMap.get("t_state"));
         args.put(t_years,hashMap.get("t_years"));
         args.put(t_months,hashMap.get("t_months"));

         args.put(t_pin,hashMap.get("t_pin"));
         args.put(t_mobile_no,hashMap.get("t_moblie_no"));

         args.put(citizenship_status,hashMap.get("citizenship_status"));
         args.put(edu_qualification,hashMap.get("edu_qualification"));
         args.put(identification_marks,hashMap.get("identification_marks"));
         args.put(blood_group,hashMap.get("blood_group"));
         args.put(blood_group_rh,hashMap.get("blood_group_rh"));

//         args.put(migration,hashMap.get("migration"));

//         args.put(covs,hashMap.get("covs"));
//         args.put(rcnumber,hashMap.get("rcnumber"));
//         args.put(parentletterforbelow18age,hashMap.get("parentletterforbelow18age"));
//         args.put(allnecessarycertificates,hashMap.get("allnecessarycertificates"));
//         args.put(exemptedmedicaltest,hashMap.get("exemptedmedicaltest"));
//         args.put(exemptedpreliminarytest,hashMap.get("exemptedpreliminarytest"));
//         args.put(convicted,hashMap.get("convicted"));
//         args.put(attdlnumber,hashMap.get("attdlnumber"));
//         args.put(attdtofconviction,hashMap.get("attdtofconviction"));
//         args.put(attreason,hashMap.get("attreason"));
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + 1, null) > 0;
    }

}




//            "<exemptedpreliminarytest type=\"n\" />" +
//
//            "<convicted type=\"n\" />" +
//
//            "<attachdoc>" +
//
//            "<attdlnumber />" +
//
//            "<attdtofconviction />" +
//
//            "<attreason />" +
//
//            "</attachdoc>" +



//            "<list-of-proofs>" +
//
//            "<doc>" +
//
//            "<proofcode>1</proofcode>" +
//
//            "<licence-certificate-badge-no>c1</licence-certificate-badge-no>" +
//
//            "<issuing-authority>i1</issuing-authority>" +
//
//            "<date-of-issue>02-12-1992</date-of-issue>" +
//
//            "</doc>" +
//
//            "<doc>" +
//
//            "<proofcode>3</proofcode>" +
//
//            "<licence-certificate-badge-no>c2</licence-certificate-badge-no>" +
//
//            "<issuing-authority>i2</issuing-authority>" +
//
//            "<date-of-issue>02-12-2011</date-of-issue>" +
//
//            "</doc>" +
//
//            "<doc>" +
//
//            "<proofcode>O</proofcode>" +
//
//            "<licence-certificate-badge-no />" +
//
//            "<issuing-authority />" +
//
//            "<date-of-issue>02-12-2014</date-of-issue>" +
//
//            "</doc>" +
//
//            "</list-of-proofs>" +
