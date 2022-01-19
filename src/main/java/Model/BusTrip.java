package Model;

public class BusTrip extends Trip<BusTrip>{

    private String finalStop;
    private String originTerminal;
    private boolean isNonStop;
    private Long busCompanyId;
    private boolean isVip;

    public BusTrip() {
    }

    public BusTrip(String finalStop, String originTerminal, boolean isNonStop, Long busCompanyId, boolean isVip) {
        this.finalStop = finalStop;
        this.originTerminal = originTerminal;
        this.isNonStop = isNonStop;
        this.busCompanyId = busCompanyId;
        this.isVip = isVip;
    }

    public String getFinalStop() {
        return finalStop;
    }

    public void setFinalStop(String finalStop) {
        this.finalStop = finalStop;
    }

    public String getOriginTerminal() {
        return originTerminal;
    }

    public void setOriginTerminal(String originTerminal) {
        this.originTerminal = originTerminal;
    }

    public boolean isNonStop() {
        return isNonStop;
    }

    public void setNonStop(boolean nonStop) {
        isNonStop = nonStop;
    }

    public Long getBusCompanyId() {
        return busCompanyId;
    }

    public void setBusCompanyId(Long busCompanyId) {
        this.busCompanyId = busCompanyId;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }
}
