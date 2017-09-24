package in.com.arbortag.core;

import in.com.arbortag.IBaseModel;

public class Asset implements IBaseModel{
    private String userId = null;
    private String assetId = null;
    private String assetName = null;
    private String division = null;
    private String subDivision = null;
    private String range = null;
    private String section = null;
    private String beat = null;
    private String plantation = null;
    private String model = null;
    private Location location = null;
    private String extent = null;
    private String year = null;
    private String month = null;
    private String scheme = null;
    private String totalNoOfPlantedTrees = null;
    private String sanctionOrderNo = null;
    private User reader = null;
    private User moderator = null;
    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public String getSanctionOrderNo() {
        return sanctionOrderNo;
    }

    public void setSanctionOrderNo(String sanctionOrderNo) {
        this.sanctionOrderNo = sanctionOrderNo;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public String getTotalNoOfPlantedTrees() {
        return totalNoOfPlantedTrees;
    }

    public void setTotalNoOfPlantedTrees(String totalNoOfPlantedTrees) {
        this.totalNoOfPlantedTrees = totalNoOfPlantedTrees;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getExtent() {
        return extent;
    }

    public void setExtent(String extent) {
        this.extent = extent;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlantation() {
        return plantation;
    }

    public void setPlantation(String plantation) {
        this.plantation = plantation;
    }

    public String getBeat() {
        return beat;
    }

    public void setBeat(String beat) {
        this.beat = beat;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }


}
