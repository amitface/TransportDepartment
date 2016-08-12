package com.converge.transportdepartment.Utility;

/**
 * Created by converge on 11/8/16.
 */
public class SlotData {

    private String rtocode;
    private long slotdate;
    private Integer slotno;
    private String slottime;
    private Integer avilablequota;

    public SlotData(String slotTime, int slotQuota) {
        this.slottime= slotTime;
        this.avilablequota= slotQuota;
    }


    public long getslotdate() {
        return slotdate;
    }
    public void setslotdate(long slotdate) {
        this.slotdate = slotdate;
    }

    public String getRtocode() {
        return rtocode;
    }
    public void setRtocode(String rtocode) {
        this.rtocode = rtocode;
    }

    public Integer slotno() {
        return slotno;
    }
    public void setSlotNo(Integer slotno) {
        this.slotno = slotno;
    }
    public String slottime() {
        return slottime;
    }
    public void setslottime(String slottime) {
        this.slottime = slottime;
    }

    public Integer avilablequota() {
        return avilablequota;
    }
    public void setavilablequota(Integer avilablequota) {
        this.avilablequota = avilablequota;
    }

}
