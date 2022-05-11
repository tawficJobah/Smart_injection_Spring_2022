package com.template.webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.flows.*;
import com.template.states.UICProjectState;
import com.template.states.WellState;
import net.corda.client.jackson.JacksonSupport;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.crypto.SecureHash;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.QueryCriteria;
import net.corda.core.transactions.SignedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

/**
 * Define your API endpoints here.
 */
@RestController
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://localhost:4200/welloperator/create-well",
        "http://localhost:4200/wo",
        "http://localhost:4200/reg",
        "http://localhost:4200/wo/addRemoveWellFlow",
        "http://localhost:4200/reg/project-review",

})
@RequestMapping("/") // The paths for HTTP requests are relative to this base path.
public class Controller {
    private final CordaRPCOps proxy;
    private final static Logger logger = LoggerFactory.getLogger(Controller.class);

    public Controller(NodeRPCConnection rpc) {
        this.proxy = rpc.proxy;
    }

    @Configuration
    class Plugin {
        @Bean
        public ObjectMapper registerModule() { return JacksonSupport.createNonRpcMapper(); }
    }

    @GetMapping(value = "/wells", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StateAndRef<WellState>> getWells() {
        return proxy.vaultQuery(WellState.class).getStates();
    }

    @GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StateAndRef<UICProjectState>> getProjects() {
        return proxy.vaultQuery(UICProjectState.class).getStates();
    }

    @GetMapping(value = "/templateendpoint", produces = "text/plain")
    private String templateendpoint() {
        return "Define an endpoint here.";
    }

    @GetMapping(value = "/flows", produces = "text/plain")
    private String flows() { return proxy.registeredFlows().toString(); }

    @GetMapping(value = "/propose", produces = "text/plain")
    public ResponseEntity<String> createWell() throws FileNotFoundException, FileAlreadyExistsException {
        String name = "O=PartyB,L=New York,C=US";
        String wellName = "Wellbert";
        String lease = "Your Field";
        List<Float> location = new ArrayList<>();
        location.add(27.777f);
        location.add(39.111f);
        String locationType = "Lat/Long";
        InputStream jarFile = new FileInputStream("/home/andrewfolsom/Desktop/attachments/test.jar");
        SecureHash hash = proxy.uploadAttachmentWithMetadata(jarFile, "Andrew", "test.txt");

        Party me = proxy.nodeInfo().getLegalIdentities().get(0);
        Party calGem = Optional.ofNullable(proxy.wellKnownPartyFromX500Name(CordaX500Name.parse(name))).orElseThrow(() -> new IllegalArgumentException("Unknown party name."));

        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(ProposeWellFlow.class, wellName, lease, calGem, location, locationType, hash).getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Transaction ID " + result.getId() + " comitted to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createWellTwo(@RequestParam("wellName") String wellName,
                                                @RequestParam("lease") String lease,
                                                @RequestParam("xLoc") String xLoc,
                                                @RequestParam("yLoc") String yLoc,
                                                @RequestParam("zLoc") String zLoc,
                                                @RequestParam("locationType") String locationType,
                                                @RequestParam("attachment")MultipartFile attachment) throws IOException {
        Party me = proxy.nodeInfo().getLegalIdentities().get(0);
        List<Float> location = new ArrayList<>();
        location.add(Float.parseFloat(xLoc));
        location.add(Float.parseFloat(yLoc));
        location.add(Float.parseFloat(zLoc));
        String name = "O=PartyB,L=New York,C=US";
        String status = "Proposed";
        InputStream jarFile = attachment.getInputStream();
        SecureHash hash = proxy.uploadAttachmentWithMetadata(jarFile, me.toString(), Objects.requireNonNull(attachment.getOriginalFilename()));
        Party calGem = Optional.ofNullable(proxy.wellKnownPartyFromX500Name(CordaX500Name.parse(name))).orElseThrow(() -> new IllegalArgumentException("Unknown party name."));

        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(
                    ProposeWellFlow.class, wellName, lease, calGem, location, locationType, hash)
                    .getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Transaction ID " + result.getId() + " comitted to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    /*-----------------------------------------New-------*/
    @PostMapping(value = "/proposeWellFlow", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> proposeWellFlow(@RequestParam("partyName") Party partyName,
                                                  @RequestParam("wellName") String wellName,
                                                  @RequestParam("lease") String lease,
                                                  @RequestParam("location") List<Float> location,
                                                  @RequestParam("locationType") String locationType,
                                                  @RequestParam("filePath") String filePath,
                                                  @RequestParam("fileUploader") String fileUploader,
                                                  @RequestParam("fileName") String fileName) throws IOException {

        InputStream jarFile = new FileInputStream(filePath);
        SecureHash hash = proxy.uploadAttachmentWithMetadata(jarFile, fileUploader, fileName);
        Party me = proxy.nodeInfo().getLegalIdentities().get(0);

        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(ProposeWellFlow.class, wellName, lease, partyName,
                    location, locationType, hash).getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Transaction ID " + result.getId() + " comitted to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "/updateWellFlow", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> updateWellFlow(@RequestParam("externalId") String externalId,
                                                 @RequestParam("lease") String lease,
                                                 @RequestParam("location") List<Float> location,
                                                 @RequestParam("locationType") String locationType,
                                                 @RequestParam("spudDate") List<Integer> spudDate) throws IOException {

        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(UpdateWellFlow.class, externalId, lease, location,
                    locationType, spudDate).getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Transaction ID " + result.getId() + " comitted to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "/createProjectFlow", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createProjectFlow(@RequestParam("projectName") String projectName) throws IOException {

        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(CreateProjectFlow.class, projectName).getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Transaction ID " + result.getId() + " comitted to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "/addRemoveWellFlow", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addRemoveWellFlow(@RequestParam("projectName") String projectName,
                                                    @RequestParam("externalIds") String externalIds,
                                                    @RequestParam("updates") String updates) throws IOException {
        List<String> idList = Arrays.asList(externalIds.split(","));
        List<String> updateList = Arrays.asList(updates.split(","));

        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(AddRemoveWellFlow.class, projectName, idList,
                    updateList).getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Transaction ID " + result.getId() + " comitted to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping(value = "/getProjectWellsQuery", produces = MediaType.TEXT_PLAIN_VALUE)
    public List<StateAndRef<WellState>> getProjectWellsQuery(@RequestParam("wellIds") List<UniqueIdentifier> wellIds) throws IOException {

        List<UniqueIdentifier> linearIds = new ArrayList<>(wellIds);
        QueryCriteria linearCriteriaAll = new QueryCriteria.LinearStateQueryCriteria(null, linearIds, Vault.StateStatus.UNCONSUMED, null);

        return proxy.vaultQueryByCriteria(linearCriteriaAll, WellState.class).getStates();

    }

    @PostMapping(value = "/approve", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> approveProjectFlow(@RequestParam("externalId") String externalId,
                                                    @RequestParam("APIs") String APIList,
                                                    @RequestParam("updates") String uicProjectNumber,
                                                    @RequestParam("permits") String permitsList,
                                                    @RequestParam("permitExpirations") String permitExpirationsList) throws IOException {
        List<String> APIs = Arrays.asList(APIList.split(","));
        List<String> permits = Arrays.asList(permitsList.split(","));
        List<String> permitExpirations = Arrays.asList(permitExpirationsList.split("],"));

        System.out.println("permitExpList" + permitExpirationsList);
        System.out.println("permitExp" + permitExpirations);

        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(ApproveInitiatorFlow.class, externalId, APIs,
                    uicProjectNumber, permits, permitExpirations).getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Transaction ID " + result.getId() + " comitted to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "/deny", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> denyProjectFlow(@RequestParam("externalId") String externalId) {
        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(DenyInitiatorFlow.class, externalId).getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Transaction ID " + result.getId() + " comitted to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "/submission", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uicProjectRequest(@RequestParam("externalId") String externalId) throws IOException {
        String name = "O=PartyB,L=New York,C=US";
        Party calGem = Optional.ofNullable(proxy.wellKnownPartyFromX500Name(CordaX500Name.parse(name))).orElseThrow(() -> new IllegalArgumentException("Unknown party name."));
        try {
            SignedTransaction result = proxy.startTrackedFlowDynamic(UICRequestInitiatorFlow.class, externalId, calGem).getReturnValue().get();
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Transaction ID " + result.getId() + " commited to ledger.\n" + result.getTx().getOutput(0));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}