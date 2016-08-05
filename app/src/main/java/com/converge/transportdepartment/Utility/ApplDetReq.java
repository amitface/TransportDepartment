package com.converge.transportdepartment.Utility;

/**
 * Created by converge on 5/8/16.
 */
public class ApplDetReq {

        private long applNo;

        private String dob;

        private String servType;

        private String usrName;

        private String pwd;

        private String serviceName;

        public long getApplNo() {
            return applNo;
        }

        public void setApplNo(long applNo) {
            this.applNo = applNo;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getServType() {
            return servType;
        }

        public void setServType(String servType) {
            this.servType = servType;
        }

        public String getUsrName() {
            return usrName;
        }

        public void setUsrName(String usrName) {
            this.usrName = usrName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

    }
