package com.healthcare.billing.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthcare.billing.model.CPT;
import com.healthcare.billing.model.CPTGroup;
import com.healthcare.billing.model.ICD10;
import com.healthcare.billing.repository.model.ICD10Code;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class JSONCodeManager {

    private final List<ICD10> baseSearchICD10s;
    private final Map<String, List<ICD10>> searchICD10s;
    private final Map<String, ICD10> icdMap;
    private final List<CPTGroup> cptGroups;
    private final Map<String, List<CPT>> cptGroupMap;
    private final Map<String, CPT> cptMap;

    private static JSONCodeManager codeManagement;

    private JSONCodeManager() {
        this.baseSearchICD10s = new LinkedList<>();
        this.searchICD10s = new LinkedHashMap<>();
        this.icdMap = new LinkedHashMap<>();
        this.cptGroups = new LinkedList<>();
        this.cptGroupMap = new LinkedHashMap<>();
        this.cptMap = new LinkedHashMap<>();
        initialize();
    }

    public static JSONCodeManager getInstance() {
        if (codeManagement == null) {
            codeManagement = new JSONCodeManager();
        }
        return codeManagement;
    }

    public List<ICD10> getBaseSearchICD10s() {
        return this.baseSearchICD10s;
    }

    public List<ICD10> getSearchICD10s(String search) {
        return this.searchICD10s.get(search);
    }

    public List<CPTGroup> getCPTCodes() {
        return this.cptGroups;
    }

    public List<CPT> getCPTCodes(String groupId) {
        return this.cptGroupMap.get(groupId);
    }

    public Map<String, CPT> getCPTMap() {
        return this.cptMap;
    }

    public CPT getCPT(String code) {
        return this.cptMap.get(code);
    }

    public Map<String, ICD10> getICD10Map() {
        return this.icdMap;
    }

    private void initialize() {
        try {
            initializeBaseSearchWithFirstLevelICDCodes();
            initializeICD10WithSecondLevelICDCodes();
            initializeICD10withThirdLevelICDCodes();
            initializeCPTGroupAndMap();
            initializeICDMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initializeBaseSearchWithFirstLevelICDCodes() throws IOException {
        Map<String, String> map = toObject(new TypeToken<Map<String, String>>() {
        }.getType(), "/firstSearch.json");
        for (String key : map.keySet()) {
            ICD10 code = createICD10(key, map.get(key));
            this.baseSearchICD10s.add(code);
        }
    }

    void initializeICD10WithSecondLevelICDCodes() throws IOException {
        Map<String, Map<String, String>> map = toObject(new TypeToken<Map<String, Map<String, String>>>() {
        }.getType(), "/secondSearch.json");
        for (String key : map.keySet()) {
            this.searchICD10s.put(key, new LinkedList<>());
            for (String sKey : map.get(key).keySet()) {
                ICD10 code = createICD10(sKey, map.get(key).get(sKey));
                this.searchICD10s.get(key).add(code);
            }
        }
    }

    void initializeICD10withThirdLevelICDCodes() throws IOException {
        Map<String, Map<String, Map<String, ICD10Code>>> map = toObject(new TypeToken<Map<String, Map<String, Map<String, ICD10Code>>>>() {
        }.getType(), "/thirdSearch.json");
        for (String lOne : map.keySet()) {
            for (String lTwo : map.get(lOne).keySet()) {
                this.searchICD10s.put(lTwo, new LinkedList<>());
                for (String key : map.get(lOne).get(lTwo).keySet()) {
                    ICD10Code code = map.get(lOne).get(lTwo).get(key);
                    ICD10 icd10 = createICD10(code);
                    this.searchICD10s.put(icd10.getCode(), new LinkedList<>());
                    this.searchICD10s.get(icd10.getCode()).add(icd10);
                    this.searchICD10s.get(lTwo).add(icd10);
                    Queue<ICD10Code> queue = new LinkedList<>();
                    queue.offer(code);
                    while (!queue.isEmpty()) {
                        ICD10Code subCode = queue.poll();
                        if (subCode.getSubCode() != null) {
//                            this.icdCodes.put(subCode.getCode(), new LinkedList<>());
                            if (icd10.getSubCodes() == null) icd10.setSubCodes(new LinkedList<>());
                            for (String sub : subCode.getSubCode().keySet()) {
                                ICD10Code subSubCode = subCode.getSubCode().get(sub);
                                queue.offer(subSubCode);
                                ICD10 sub10Code = createICD10(subSubCode);
                                icd10.getSubCodes().add(sub10Code);
//                                this.icdCodes.get(subCode.getCode()).add(sub10Code);
                            }
                        }
                    }
                }
            }
        }
    }

    void initializeCPTGroupAndMap() throws IOException {
        Map<String, Map<String, String>> CPTs = toObject(new TypeToken<Map<String, Map<String, String>>>() {
        }.getType(), "/cpt.json");
        for (String key : CPTs.keySet()) {
            CPTGroup group = createCPTGroup(key);
            this.cptGroupMap.put(group.getId(), new LinkedList<>());
            List<CPT> cptCodes = new LinkedList<>();
            for (String cptKey : CPTs.get(key).keySet()) {
                CPT cpt = createCPT(cptKey, CPTs.get(key).get(cptKey));
                this.cptGroupMap.get(group.getId()).add(cpt);
                this.cptMap.put(cpt.getCode(), cpt);
                cptCodes.add(cpt);
            }
            group.setCpts(cptCodes);
            this.cptGroups.add(group);
        }
    }

    void initializeICDMap() {
        for (String key : searchICD10s.keySet()) {
            if (!key.contains("-")) {
                ICD10 icd10 = searchICD10s.get(key).get(0);
                icdMap.put(icd10.getId(), icd10);
            }
        }
    }

    private <T> T toObject(Type type, String fileName) {
        Gson gson = new Gson();
        InputStream in = getClass().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return gson.fromJson(reader, type);
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
        cpt.setId(UUID.randomUUID().toString());
        cpt.setCode(code);
        cpt.setDescription(description);
        return cpt;
    }
}
