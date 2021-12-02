package com.healthcare.billing.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.CPTGroup;
import com.healthcare.billing.model.ICD10;
import com.healthcare.billing.repository.model.ICD10Code;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JSONCodeManagement {

    private static final String LOCATION = "build/resources/main/templates/";
    private final List<ICD10> baseSearchICDCodes;
    private final Map<String, List<ICD10>> icdCodes;
    private final List<CPTGroup> cptGroups;
    private final Map<String, List<CPT>> cptMap;

    private static JSONCodeManagement codeManagement;

    private JSONCodeManagement() {
        this.baseSearchICDCodes = new LinkedList<>();
        this.icdCodes = new LinkedHashMap<>();
        this.cptGroups = new LinkedList<>();
        this.cptMap = new LinkedHashMap<>();
        initialize();
    }

    public static JSONCodeManagement getInstance() {
        if (codeManagement == null) {
            codeManagement = new JSONCodeManagement();
        }
        return codeManagement;
    }

    public List<ICD10> getBaseSearchICDCodes() {
        return this.baseSearchICDCodes;
    }

    public List<ICD10> getICDCodes(String search) {
        return this.icdCodes.get(search);
    }

    public List<CPTGroup> getCPTCodes() {
        return this.cptGroups;
    }

    public List<CPT> getCPTCodes(String groupId) {
        return this.cptMap.get(groupId);
    }

    private void initialize() {
        try {
            initializeBaseSearchWithFirstLevelICDCodes();
            initializeICD10WithSecondLevelICDCodes();
            initializeICD10withThirdLevelICDCodes();
            initializeCPTGroupAndMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initializeBaseSearchWithFirstLevelICDCodes() throws IOException {
        Map<String, String> map = toObject(new TypeToken<Map<String, String>>() {
        }.getType(), "firstSearch.json");
        for (String key : map.keySet()) {
            ICD10 code = createICD10(key, map.get(key));
            this.baseSearchICDCodes.add(code);
        }
    }

    void initializeICD10WithSecondLevelICDCodes() throws IOException {
        Map<String, Map<String, String>> map = toObject(new TypeToken<Map<String, Map<String, String>>>() {
        }.getType(), "secondSearch.json");
        for (String key : map.keySet()) {
            this.icdCodes.put(key, new LinkedList<>());
            for (String sKey : map.get(key).keySet()) {
                ICD10 code = createICD10(sKey, map.get(key).get(sKey));
                this.icdCodes.get(key).add(code);
            }
        }
    }

    void initializeICD10withThirdLevelICDCodes() throws IOException {
        Map<String, Map<String, Map<String, ICD10Code>>> map = toObject(new TypeToken<Map<String, Map<String, Map<String, ICD10Code>>>>() {
        }.getType(), "thirdSearch.json");
        for (String lOne : map.keySet()) {
            for (String lTwo : map.get(lOne).keySet()) {
                this.icdCodes.put(lTwo, new LinkedList<>());
                for (String key : map.get(lOne).get(lTwo).keySet()) {
                    ICD10Code code = map.get(lOne).get(lTwo).get(key);
                    ICD10 icd10 = createICD10(code);
                    this.icdCodes.get(lTwo).add(icd10);
                    Queue<ICD10Code> queue = new LinkedList<>();
                    queue.offer(code);
                    while (!queue.isEmpty()) {
                        ICD10Code subCode = queue.poll();
                        if (subCode.getSubCode() != null) {
                            this.icdCodes.put(subCode.getCode(), new LinkedList<>());
                            for (String sub : subCode.getSubCode().keySet()) {
                                ICD10Code subSubCode = subCode.getSubCode().get(sub);
                                queue.offer(subSubCode);
                                ICD10 sub10Code = createICD10(subSubCode);
                                this.icdCodes.get(subCode.getCode()).add(sub10Code);
                            }
                        }
                    }
                }
            }
        }
    }

    void initializeCPTGroupAndMap() throws IOException {
        Map<String, Map<String, String>> CPTs = toObject(new TypeToken<Map<String, Map<String, String>>>() {
        }.getType(), "cpt.json");
        for (String key : CPTs.keySet()) {
            CPTGroup group = createCPTGroup(key);
            this.cptMap.put(group.getId(), new LinkedList<>());
            List<CPT> cptCodes = new LinkedList<>();
            for (String cptKey : CPTs.get(key).keySet()) {
                CPT cpt = createCPT(cptKey, CPTs.get(key).get(cptKey));
                this.cptMap.get(group.getId()).add(cpt);
                cptCodes.add(cpt);
            }
            group.setCodes(cptCodes);
            this.cptGroups.add(group);
        }
    }

    private <T> T toObject(Type type, String fileName) throws IOException {
        Gson gson = new Gson();
        Reader thirdReader = Files.newBufferedReader(Paths.get(LOCATION + fileName));
        return gson.fromJson(thirdReader, type);
    }

    private ICD10 createICD10(ICD10Code icd10Code) {
        return createICD10(icd10Code.getCode(), icd10Code.getInformation());
    }

    private ICD10 createICD10(String code, String description) {
        ICD10 icd10 = new ICD10();
        // Using code as an ID
//        icd10.setId(UUID.randomUUID().toString());
        icd10.setCode(code);
        icd10.setDescription(description);
        return icd10;
    }

    private CPTGroup createCPTGroup(String description) {
        CPTGroup group = new CPTGroup();
        group.setId(UUID.randomUUID().toString());
        group.setDescription(description);
        return group;
    }

    private CPT createCPT(String code, String description) {
        CPT cpt = new CPT();
        // Using code as an ID
//        cpt.setId(UUID.randomUUID().toString());
        cpt.setCode(code);
        cpt.setDescription(description);
        return cpt;
    }
}
