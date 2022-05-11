package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.WellContract;
import com.template.states.WellState;
import net.corda.core.crypto.SecureHash;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
/*
Version: 1.1
This Flow takes basic information and produces a WellState with the "PROPOSAL" status.
This is the first step, most information will be provided by the flow as most fields are empty.
All dates default to 1999-9-9.
@input: wellName(String), lease(string), location(List<Float>), locationType(String)
@defaults: status("PROPOSAL"), spudDate(1999,9,9), API("NONE"), UICProjectNumber("NONE"), permit("NONE"),
           permitExpiration(1999,9,9):
@output: WellState

Example run Command:
start ProposeWellFlow wellName: "My well", lease: "Your Field", location: [27.777, 39.11], locationType: "NAT27"
*/

@InitiatingFlow
@StartableByRPC
public class ProposeWellFlow extends FlowLogic<SignedTransaction> {

    //progress tracker created, but not used here.
    private final ProgressTracker progressTracker = new ProgressTracker();

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    //PROPERTIES
    private final String wellName;
    private final String lease;
    private final Party calGem;
    private final List<Float> location;
    private final String locationType;
    private final SecureHash.SHA256 wellBoreDiagram;
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

    //CONSTRUCTOR
    public ProposeWellFlow(String wellName, String lease, Party calGem, List<Float> location, String locationType,SecureHash.SHA256 docs,String wellType,String purpose,
                           String apiID,String opType,String injZone,String depth,String sec,String townShip,String range,String county,String designation,String wellNum,
                           String field, String statusDate, String pool, String bondNum,String production,String directDrill,String confidential,
                           String expiration, String bm,String district,String shore,String datum,String coernerCall,String description,String casingSize,String weight,String grade,
                           String top,String bottom,String cementDepth,String csg,String testID,String testType,String interval,String schedDate,String actDate,String futureDate,String wellboreNo,
                           String compDate,String botHoleMD,String waterBase,String botHoleTVD,String plugMD,String plugTVD,String bridgePlug,String cementPlug,String surfLatitude,String surfLongitude,
                           String botLatitude,String botLongitude) {
        this.wellName = wellName;
        this.lease = lease;
        this.calGem = calGem;
        this.location = location;
        this.locationType = locationType;
        this.wellBoreDiagram = docs;
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

    @Suspendable
    @Override
    public SignedTransaction call() throws FlowException {
        //LOCAL PROPERTIES
        final Party owner = getOurIdentity();
        final Party operator = getOurIdentity();
        final String API = "N/A";
        final String status = "Proposed";
        final String UICProjectNumber = "N/A";
        final String permit = "N/A";
        final LocalDate spudDate = LocalDate.of(1999,9,9);
        final LocalDate permitExpiration = LocalDate.of(1999,9,9);
        final String projectName = "N/A";
        final String wellType = "N/A";
        final String purpose = "N/A";
        final String apiID = "N/A";
        final String opType = "N/A";
        final String injZone = "N/A";
        final String depth = "N/A";
        final String sec = "N/A";
        final String townShip = "N/A";
        final String range = "N/A";
        final String county = "N/A";
        final String designation = "N/A";
        final String wellNum = "N/A";
        final String field = "N/A";
        final String statusDate = "N/A";
        final String pool = "N/A";
        final String bondNum = "N/A";
        final String production = "N/A";
        final String directDrill = "N/A";
        final String confidential = "N/A";
        final String expiration = "N/A";
        final String bm = "N/A";
        final String district = "N/A";
        final String shore = "N/A";
        final String datum = "N/A";
        final String coernerCall = "N/A";
        final String description = "N/A";
        final String casingSize = "N/A";
        final String weight = "N/A";
        final String grade = "N/A";
        final String top = "N/A";
        final String bottom = "N/A";
        final String cementDepth = "N/A";
        final String csg = "N/A";
        final String testID = "N/A";
        final String testType = "N/A";
        final String interval = "N/A";
        final String schedDate = "N/A";
        final String actDate = "N/A";
        final String futureDate = "N/A";
        final String wellboreNo = "N/A";
        final String compDate = "N/A";
        final String botHoleMD = "N/A";
        final String waterBase = "N/A";
        final String botHoleTVD = "N/A";
        final String plugMD = "N/A";
        final String plugTVD = "N/A";
        final String bridgePlug = "N/A";
        final String cementPlug = "N/A";
        final String surfLatitude = "N/A";
        final String surfLongitude = "N/A";
        final String botLatitude = "N/A";
        final String botLongitude = "N/A";




        final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

        //generate output state
        WellState output = new WellState(status, wellName, owner, operator, calGem, lease, locationType, location,
                spudDate, API, UICProjectNumber, permit, permitExpiration, wellBoreDiagram, projectName,wellType,purpose,
                apiID,opType,injZone,depth,sec,townShip,range,county,designation,wellNum,field,statusDate,pool,bondNum,
                production,directDrill,confidential,expiration,bm,district,shore,datum,coernerCall,description,casingSize,
                weight,grade,top,bottom,cementDepth,csg,testID,testType,interval,schedDate,actDate,futureDate,wellboreNo,
                compDate,botHoleMD,waterBase,botHoleTVD,plugMD,plugTVD,bridgePlug,cementPlug,surfLatitude,surfLongitude,botLatitude,botLongitude);

        //Create and build the builder
        final TransactionBuilder builder = new TransactionBuilder(notary);
        builder.addAttachment(wellBoreDiagram);
        builder.addOutputState(output, WellContract.ID);
        builder.addCommand(new WellContract.Commands.Propose(), Collections.singletonList(owner.getOwningKey()));
        builder.verify(getServiceHub());

        //convert builder -> SignedTransaction
        SignedTransaction signedTransaction = getServiceHub().signInitialTransaction(builder);
        //Finality flow needs a session list. This flow doesn't have other nodes that it needs to talk to
        //so we create an empty session list to pass.
        List<FlowSession> session = Collections.emptyList();
        //finalize, add to ledger.
        return subFlow(new FinalityFlow(signedTransaction, session));
    }
}
