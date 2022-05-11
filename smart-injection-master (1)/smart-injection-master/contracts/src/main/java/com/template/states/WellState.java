package com.template.states;

import com.template.contracts.WellContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.crypto.SecureHash;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.serialization.ConstructorForDeserialization;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@BelongsToContract(WellContract.class)
public class WellState implements LinearState {
//------------------------------------------- PROPERTIES --------------------------------------------------------------

    private final UniqueIdentifier linearId;
    private final String status;
    private final String wellName;
    private final Party owner;
    private final Party operator;
    private final Party calGem;
    private final String lease;
    private final String locationType;
    private final List<Float> location;
    private final LocalDate spudDate;
    private final String projectName;
    private SecureHash.SHA256 wellBoreDiagram = null;
    private final List<AbstractParty> participants;

    private final String wellType;
    private final String purpose;
    private final String apiID;
    private final String opType;
    private final String injZone;
    private final String depth;
    private final String sec;
    private final String townShip;
    private final String range;
    private final String county;
    private final String designation;
    private final String wellNum;
    private final String field;
    private final String statusDate;
    private final String pool;
    private final String bondNum;
    private final String production;
    private final String directDrill;
    private final String confidential;
    private final String expiration;
    private final String bm;
    private final String district;
    private final String shore;
    private final String datum;
    private final String coernerCall;
    private final String description;
    private final String casingSize;
    private final String weight;
    private final String grade;
    private final String top;
    private final String bottom;
    private final String cementDepth;
    private final String csg;
    private final String testID;
    private final String testType;
    private final String interval;
    private final String schedDate;
    private final String actDate;
    private final String futureDate;
    private final String wellboreNo;
    private final String compDate;
    private final String botHoleMD;
    private final String waterBase;
    private final String botHoleTVD;
    private final String plugMD;
    private final String plugTVD;
    private final String bridgePlug;
    private final String cementPlug;
    private final String surfLatitude;
    private final String surfLongitude;
    private final String botLatitude;
    private final String botLongitude;

    //required - provided by CalGem, approval process
    private final String API;
    private final String UICProjectNumber;
    private final String permit;
    private final LocalDate permitExpiration;

// ------------------------------------------- PROPERTIES END----------------------------------------------------------
    //CONSTRUCTORS
    /*for well states that have already been created. Used by UpdateWellFlow
    @input: linearId(UniqueIdentifier), status(String), wellName(String), owner(Party), operator(Party), lease(String), locationType(String),
    location(List<Float>), spudDate(LocalDate), API(String), UICProjectNumber(String), permit(String),
    permitExpiration(LocalDate)
    Example:
    start UpdateWellFlow externalId: "my well", lease: "Your Field", location: [44.0, 33.00],
    locationType: "NAT27", spudDate: [2020,10,20]
    */
    @ConstructorForDeserialization
    public WellState(UniqueIdentifier linearId, String status, String wellName, Party owner, Party operator, Party calGem, String lease, String locationType,
                     List<Float> location, LocalDate spudDate, String API, String UICProjectNumber,
                     String permit, LocalDate permitExpiration, List<AbstractParty> participants, String projectName,String wellType,String purpose,
                    String apiID, String opType,String injZone,String depth, String sec, String townShip, String range, String county, String designation,
                     String wellNum, String field, String statusDate, String pool, String bondNum,String production,String directDrill,String confidential,
                     String expiration, String bm,String district,String shore,String datum,String coernerCall,String description,String casingSize,String weight,String grade,
                     String top,String bottom,String cementDepth,String csg,String testID,String testType,String interval,String schedDate,String actDate,String futureDate,String wellboreNo,
                     String compDate,String botHoleMD,String waterBase,String botHoleTVD,String plugMD,String plugTVD,String bridgePlug,String cementPlug,String surfLatitude,String surfLongitude,
                     String botLatitude,String botLongitude) {
        this.linearId = linearId;
        this.status = status;
        this.wellName = wellName;
        this.owner = owner;
        this.operator = operator;
        this.calGem = calGem;
        this.lease = lease;
        this.locationType = locationType;
        this.location = location;
        this.spudDate = spudDate;
        this.API = API;
        this.UICProjectNumber = UICProjectNumber;
        this.permit = permit;
        this.permitExpiration = permitExpiration;
        this.participants = participants;
        this.projectName = projectName;
        this.wellType = wellType;
        this.purpose = purpose;
        this.apiID = apiID;
        this.opType = opType;
        this.injZone = injZone;
        this.depth = depth;
        this.sec = sec;
        this.townShip = townShip;
        this.range = range;
        this.county = county;
        this.designation = designation;
        this.wellNum = wellNum;
        this.field = field;
        this.statusDate = statusDate;
        this.pool = pool;
        this.bondNum = bondNum;
        this.production = production;
        this.directDrill = directDrill;
        this.confidential = confidential;
        this.expiration = expiration;
        this.bm = bm;
        this.district = district;
        this.shore = shore;
        this.datum = datum;
        this.coernerCall = coernerCall;
        this.description = description;
        this.casingSize = casingSize;
        this.weight = weight;
        this.grade = grade;
        this.top = top;
        this.bottom = bottom;
        this.cementDepth = cementDepth;
        this.csg = csg;
        this.testID = testID;
        this.testType = testType;
        this.interval = interval;
        this.schedDate = schedDate;
        this.actDate = actDate;
        this.futureDate = futureDate;
        this.wellboreNo = wellboreNo;
        this.compDate = compDate;
        this.botHoleMD = botHoleMD;
        this.waterBase = waterBase;
        this.botHoleTVD = botHoleTVD;
        this.plugMD = plugMD;
        this.plugTVD = plugTVD;
        this.bridgePlug = bridgePlug;
        this.cementPlug = cementPlug;
        this.surfLatitude = surfLatitude;
        this.surfLongitude = surfLongitude;
        this.botLatitude = botLatitude;
        this.botLongitude = botLongitude;
    }

    /*for new well states. Used by ProposeWellFlow
    @input: status(String), wellName(String), owner(Party), operator(Party), lease(String), locationType(String),
    location(List<Float>), spudDate(LocalDate), API(String), UICProjectNumber(String), permit(String),
    permitExpiration(LocalDate, docs(SecureHash.SHA256)
    Example:
    start ProposeWellFlow wellName: "my well", lease: "Your Field", location: [27.777, 39.11], locationType: "NAT27",
    docs: 59DB8F7CBA460679443065AC63164D269E4E6CB72CCD3FA71822AE54B0AC2B37
    */
    public WellState(String status, String wellName, Party owner, Party operator, Party calGem, String lease, String locationType,
                     List<Float> location, LocalDate spudDate, String API, String UICProjectNumber,
                     String permit, LocalDate permitExpiration, SecureHash.SHA256 docs, String projectName,String wellType,String purpose,
                     String apiID, String opType,String injZone,String depth, String sec, String townShip, String range, String county,String designation,
                     String wellNum, String field, String statusDate,  String pool, String bondNum,String production,String directDrill,String confidential,
                     String expiration, String bm,String district,String shore,String datum,String coernerCall,String description,String casingSize,String weight,String grade,
                     String top,String bottom,String cementDepth,String csg,String testID,String testType,String interval,String schedDate,String actDate,String futureDate,String wellboreNo,
                     String compDate,String botHoleMD,String waterBase,String botHoleTVD,String plugMD,String plugTVD,String bridgePlug,String cementPlug,String surfLatitude,String surfLongitude,
                     String botLatitude,String botLongitude) {
        this.linearId = new UniqueIdentifier(wellName);
        this.status = status;
        this.wellName = wellName;
        this.owner = owner;
        this.operator = operator;
        this.calGem = calGem;
        this.lease = lease;
        this.locationType = locationType;
        this.location = location;
        this.spudDate = spudDate;
        this.API = API;
        this.UICProjectNumber = UICProjectNumber;
        this.permit = permit;
        this.permitExpiration = permitExpiration;
        this.wellBoreDiagram = docs;
        this.participants = new ArrayList<>(Collections.singleton(operator));
        this.projectName = projectName;
        this.wellType = wellType;
        this.purpose = purpose;
        this.apiID = apiID;
        this.opType = opType;
        this.injZone = injZone;
        this.depth = depth;
        this.sec = sec;
        this.townShip = townShip;
        this.range = range;
        this.county = county;
        this.designation = designation;
        this.wellNum = wellNum;
        this.field = field;
        this.statusDate = statusDate;
        this.pool = pool;
        this.bondNum = bondNum;
        this.production = production;
        this.directDrill = directDrill;
        this.confidential = confidential;
        this.expiration = expiration;
        this.bm = bm;
        this.district = district;
        this.shore = shore;
        this.datum = datum;
        this.coernerCall = coernerCall;
        this.description = description;
        this.casingSize = casingSize;
        this.weight = weight;
        this.grade = grade;
        this.top = top;
        this.bottom = bottom;
        this.cementDepth = cementDepth;
        this.csg = csg;
        this.testID = testID;
        this.testType = testType;
        this.interval = interval;
        this.schedDate = schedDate;
        this.actDate = actDate;
        this.futureDate = futureDate;
        this.wellboreNo = wellboreNo;
        this.compDate = compDate;
        this.botHoleMD = botHoleMD;
        this.waterBase = waterBase;
        this.botHoleTVD = botHoleTVD;
        this.plugMD = plugMD;
        this.plugTVD = plugTVD;
        this.bridgePlug = bridgePlug;
        this.cementPlug = cementPlug;
        this.surfLatitude = surfLatitude;
        this.surfLongitude = surfLongitude;
        this.botLatitude = botLatitude;
        this.botLongitude = botLongitude;
    }

    // For simple status changes such as Request and Deny
    public WellState(String newStatus, WellState w) {
        this.linearId = w.linearId;
        this.status = newStatus;
        this.wellName = w.wellName;
        this.owner = w.owner;
        this.operator = w.operator;
        this.calGem = w.calGem;
        this.lease = w.lease;
        this.locationType = w.locationType;
        this.location = w.location;
        this.spudDate = w.spudDate;
        this.API = w.API;
        this.UICProjectNumber = w.UICProjectNumber;
        this.permit = w.permit;
        this.permitExpiration = w.permitExpiration;
        this.participants = new ArrayList<>(w.participants);
        this.projectName = w.projectName;
        this.wellType = w.wellType;
        this.purpose = w.purpose;
        this.apiID = w.apiID;
        this.opType = w.opType;
        this.injZone = w.injZone;
        this.depth = w.depth;
        this.sec = w.sec;
        this.townShip = w.townShip;
        this.range = w.range;
        this.county = w.county;
        this.designation = w.designation;
        this.wellNum = w.wellNum;
        this.field = w.field;
        this.statusDate = w.statusDate;
        this.pool = w.pool;
        this.bondNum = w.bondNum;
        this.production = w.production;
        this.directDrill = w.directDrill;
        this.confidential = w.confidential;
        this.expiration = w.expiration;
        this.bm = w.bm;
        this.district = w.district;
        this.shore = w.shore;
        this.datum = w.datum;
        this.coernerCall = w.coernerCall;
        this.description = w.description;
        this.casingSize = w.casingSize;
        this.weight = w.weight;
        this.grade = w.grade;
        this.top = w.top;
        this.bottom = w.bottom;
        this.cementDepth = w.cementDepth;
        this.csg = w.csg;
        this.testID = w.testID;
        this.testType = w.testType;
        this.interval = w.interval;
        this.schedDate = w.schedDate;
        this.actDate = w.actDate;
        this.futureDate = w.futureDate;
        this.wellboreNo = w.wellboreNo;
        this.compDate = w.compDate;
        this.botHoleMD = w.botHoleMD;
        this.waterBase = w.waterBase;
        this.botHoleTVD = w.botHoleTVD;
        this.plugMD = w.plugMD;
        this.plugTVD = w.plugTVD;
        this.bridgePlug = w.bridgePlug;
        this.cementPlug = w.cementPlug;
        this.surfLatitude = w.surfLatitude;
        this.surfLongitude = w.surfLongitude;
        this.botLatitude = w.botLatitude;
        this.botLongitude = w.botLongitude;
    }

    // For use in the CalGem Approval process
    public WellState(String API, String UICProjectNumber, String permit, String permitExpiration,
                     WellState w) {
        this.linearId = w.linearId;
        this.status = w.status;
        this.wellName = w.wellName;
        this.owner = w.owner;
        this.spudDate = w.spudDate;
        this.operator = w.operator;
        this.calGem = w.calGem;
        this.lease = w.lease;
        this.locationType = w.locationType;
        this.location = w.location;

        this.API = API;
        this.UICProjectNumber = UICProjectNumber;
        this.permit = permit;
        this.projectName = w.projectName;
        this.wellType = w.wellType;
        this.purpose = w.purpose;
        this.apiID = w.apiID;
        this.opType = w.opType;
        this.injZone = w.injZone;
        this.depth = w.depth;
        this.sec = w.sec;
        this.townShip = w.townShip;
        this.range = w.range;
        this.county = w.county;
        this.designation = w.designation;
        this.wellNum = w.wellNum;
        this.field = w.field;
        this.statusDate = w.statusDate;
        this.pool = w.pool;
        this.bondNum = w.bondNum;
        this.production = w.production;
        this.directDrill = w.directDrill;
        this.confidential = w.confidential;
        this.expiration = w.expiration;
        this.bm = w.bm;
        this.district = w.district;
        this.shore = w.shore;
        this.datum = w.datum;
        this.coernerCall = w.coernerCall;
        this.description = w.description;
        this.casingSize = w.casingSize;
        this.weight = w.weight;
        this.grade = w.grade;
        this.top = w.top;
        this.bottom = w.bottom;
        this.cementDepth = w.cementDepth;
        this.csg = w.csg;
        this.testID = w.testID;
        this.testType = w.testType;
        this.interval = w.interval;
        this.schedDate = w.schedDate;
        this.actDate = w.actDate;
        this.futureDate = w.futureDate;
        this.wellboreNo = w.wellboreNo;
        this.compDate = w.compDate;
        this.botHoleMD = w.botHoleMD;
        this.waterBase = w.waterBase;
        this.botHoleTVD = w.botHoleTVD;
        this.plugMD = w.plugMD;
        this.plugTVD = w.plugTVD;
        this.bridgePlug = w.bridgePlug;
        this.cementPlug = w.cementPlug;
        this.surfLatitude = w.surfLatitude;
        this.surfLongitude = w.surfLongitude;
        this.botLatitude = w.botLatitude;
        this.botLongitude = w.botLongitude;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.permitExpiration = LocalDate.parse(permitExpiration, formatter);
        this.participants = new ArrayList<>(w.participants);
    }

    // Constructor for the UIC Request Flow
    public WellState(List<AbstractParty> updatedParticipants, WellState w) {
        this.linearId = w.linearId;
        this.status = w.status;
        this.wellName = w.wellName;
        this.owner = w.owner;
        this.operator = w.operator;
        this.calGem = w.calGem;
        this.lease = w.lease;
        this.locationType = w.locationType;
        this.location = w.location;
        this.spudDate = w.spudDate;
        this.API = w.API;
        this.UICProjectNumber = w.UICProjectNumber;
        this.permit = w.permit;
        this.permitExpiration = w.permitExpiration;
        this.participants = new ArrayList<>(updatedParticipants);
        this.projectName = w.projectName;
        this.wellType = w.wellType;
        this.purpose = w.purpose;
        this.apiID = w.apiID;
        this.opType = w.opType;
        this.injZone = w.injZone;
        this.depth = w.depth;
        this.sec = w.sec;
        this.townShip = w.townShip;
        this.range = w.range;
        this.county = w.county;
        this.designation = w.designation;
        this.wellNum = w.wellNum;
        this.field = w.field;
        this.statusDate = w.statusDate;
        this.pool = w.pool;
        this.bondNum = w.bondNum;
        this.production = w.production;
        this.directDrill = w.directDrill;
        this.confidential = w.confidential;
        this.expiration = w.expiration;
        this.bm = w.bm;
        this.district = w.district;
        this.shore = w.shore;
        this.datum = w.datum;
        this.coernerCall = w.coernerCall;
        this.description = w.description;
        this.casingSize = w.casingSize;
        this.weight = w.weight;
        this.grade = w.grade;
        this.top = w.top;
        this.bottom = w.bottom;
        this.cementDepth = w.cementDepth;
        this.csg = w.csg;
        this.testID = w.testID;
        this.testType = w.testType;
        this.interval = w.interval;
        this.schedDate = w.schedDate;
        this.actDate = w.actDate;
        this.futureDate = w.futureDate;
        this.wellboreNo = w.wellboreNo;
        this.compDate = w.compDate;
        this.botHoleMD = w.botHoleMD;
        this.waterBase = w.waterBase;
        this.botHoleTVD = w.botHoleTVD;
        this.plugMD = w.plugMD;
        this.plugTVD = w.plugTVD;
        this.bridgePlug = w.bridgePlug;
        this.cementPlug = w.cementPlug;
        this.surfLatitude = w.surfLatitude;
        this.surfLongitude = w.surfLongitude;
        this.botLatitude = w.botLatitude;
        this.botLongitude = w.botLongitude;
    }

    public String getStatus() { return status; }
    public String getWellName() { return wellName; }
    public Party getOwner() { return owner; }
    public Party getOperator() { return operator; }
    public Party getCalGem() { return calGem; }
    public String getLease() { return lease; }
    public String getLocationType() { return locationType; }
    public List<Float> getLocation() { return location; }
    public LocalDate getSpudDate() { return spudDate; }
    public String getAPI() { return API; }
    public String getUICProjectNumber() { return UICProjectNumber; }
    public String getPermit() { return permit; }
    public LocalDate getPermitExpiration() { return permitExpiration; }
    public SecureHash.SHA256 getWellBoreDiagram() { return wellBoreDiagram; }
    public String getProjectName() { return projectName; }
    public String getWellType() {        return wellType;    }
    public String getPurpose() {        return purpose;    }
    public String getApiID() {        return apiID;    }
    public String getActDate() {        return actDate;    }
    public String getBm() {        return bm;    }
    public String getBondNum() {        return bondNum;    }
    public String getBotHoleMD() {        return botHoleMD;    }
    public String getBotHoleTVD() {        return botHoleTVD;    }
    public String getBotLatitude() {        return botLatitude;    }
    public String getBotLongitude() {        return botLongitude;    }
    public String getBottom() {        return bottom;    }
    public String getBridgePlug() {        return bridgePlug;    }
    public String getCasingSize() {        return casingSize;    }
    public String getCementDepth() {        return cementDepth;    }
    public String getCementPlug() {        return cementPlug;    }
    public String getCoernerCall() {        return coernerCall;    }
    public String getCompDate() {        return compDate;    }
    public String getConfidential() {        return confidential;    }
    public String getCounty() {        return county;    }
    public String getCsg() {        return csg;    }
    public String getDatum() {        return datum;    }
    public String getDepth() {        return depth;    }
    public String getDescription() {        return description;    }
    public String getDesignation() {        return designation;    }
    public String getDirectDrill() {        return directDrill;    }
    public String getDistrict() {        return district;    }
    public String getExpiration() {        return expiration;    }
    public String getField() {        return field;    }
    public String getFutureDate() {        return futureDate;    }
    public String getGrade() {        return grade;    }
    public String getInjZone() {        return injZone;    }
    public String getInterval() {        return interval;    }
    public String getOpType() {        return opType;    }
    public String getPlugMD() {        return plugMD;    }
    public String getPlugTVD() {        return plugTVD;    }
    public String getPool() {        return pool;    }
    public String getProduction() {        return production;    }
    public String getRange() {        return range;    }
    public String getSchedDate() {        return schedDate;    }
    public String getSec() {        return sec;    }
    public String getShore() {        return shore;    }
    public String getStatusDate() {        return statusDate;    }
    public String getSurfLatitude() {        return surfLatitude;    }
    public String getSurfLongitude() {        return surfLongitude;    }
    public String getTestID() {        return testID;    }
    public String getTestType() {        return testType;    }
    public String getTop() {        return top;    }
    public String getTownShip() {        return townShip;    }
    public String getWaterBase() {        return waterBase;    }
    public String getWeight() {        return weight;    }
    public String getWellboreNo() {        return wellboreNo;    }
    public String getWellNum() {        return wellNum;    }

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return participants;
    }

    // Comparison Function
    public Boolean sameAs(WellState w) {
        if(this.wellName.equals(w.wellName) && this.owner.equals(w.owner) && this.operator.equals(w.operator)
        && this.lease.equals(w.lease) && this.locationType.equals(w.locationType) && this.location.equals(w.location)
        && this.spudDate.equals(w.spudDate) && this.API.equals(w.API)
        && this.UICProjectNumber.equals(w.UICProjectNumber) && this.permit.equals(w.permit)
        && this.permitExpiration.equals(w.permitExpiration)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

}
