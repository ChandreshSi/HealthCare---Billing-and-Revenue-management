package com.healthcare.billing.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthcare.billing.model.CPT;
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
    private final Map<String, List<CPT>> cptCodes;

    private static JSONCodeManagement codeManagement;

    private JSONCodeManagement() {
        this.baseSearchICDCodes = new LinkedList<>();
        this.icdCodes = new LinkedHashMap<>();
        this.cptCodes = new LinkedHashMap<>();
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

    public Map<String, List<CPT>> getCPTCodes() {
        return this.cptCodes;
    }

    public List<CPT> getCPTCodes(String search) {
        return this.cptCodes.get(search);
    }

    private void initialize() {
        try {
            this.baseSearchICDCodes.addAll(getFirstLevelICDCodes());
            this.icdCodes.putAll(getSecondLevelICDCodes());
            this.icdCodes.putAll(getThirdLevelICDCodes());
            this.cptCodes.putAll(getCPT());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<ICD10> getFirstLevelICDCodes() throws IOException {
        List<ICD10> codes = new LinkedList<>();
        Map<String, String> map = toObject(new TypeToken<Map<String, String>>() {
        }.getType(), "firstSearch.json");
        for (String key : map.keySet()) {
            ICD10 code = new ICD10();
            code.setCode(key);
            code.setDescription(map.get(key));
            codes.add(code);
        }
        return codes;
    }

    Map<String, List<ICD10>> getSecondLevelICDCodes() throws IOException {
        Map<String, List<ICD10>> codes = new LinkedHashMap<>();
        Map<String, Map<String, String>> map = toObject(new TypeToken<Map<String, Map<String, String>>>() {
        }.getType(), "secondSearch.json");
        for (String key : map.keySet()) {
            codes.put(key, new LinkedList<>());
            for (String sKey : map.get(key).keySet()) {
                ICD10 code = createICD10(sKey, map.get(key).get(sKey));
                codes.get(key).add(code);
            }
        }
        return codes;
    }

    Map<String, List<ICD10>> getThirdLevelICDCodes() throws IOException {
        Map<String, List<ICD10>> codes = new LinkedHashMap<>();
        Map<String, Map<String, Map<String, ICD10Code>>> map = toObject(new TypeToken<Map<String, Map<String, Map<String, ICD10Code>>>>() {
        }.getType(), "thirdSearch.json");
        for (String lOne : map.keySet()) {
            for (String lTwo : map.get(lOne).keySet()) {
                codes.put(lTwo, new LinkedList<>());
                for (String key : map.get(lOne).get(lTwo).keySet()) {
                    ICD10Code code = map.get(lOne).get(lTwo).get(key);
                    ICD10 icd10 = createICD10(code);
                    codes.get(lTwo).add(icd10);
                    Queue<ICD10Code> queue = new LinkedList<>();
                    queue.offer(code);
                    while (!queue.isEmpty()) {
                        ICD10Code subCode = queue.poll();
                        if (subCode.getSubCode() != null) {
                            codes.put(subCode.getCode(), new LinkedList<>());
                            for (String sub : subCode.getSubCode().keySet()) {
                                ICD10Code subSubCode = subCode.getSubCode().get(sub);
                                queue.offer(subSubCode);
                                ICD10 sub10Code = createICD10(subSubCode);
                                codes.get(subCode.getCode()).add(sub10Code);
                            }
                        }
                    }
                }
            }
        }
        return codes;
    }

    Map<String, List<CPT>> getCPT() throws IOException {
        Map<String, List<CPT>> codes = new LinkedHashMap<>();
        Map<String, Map<String, String>> CPTs = toObject(new TypeToken<Map<String, Map<String, String>>>() {
        }.getType(), "cpt.json");
        for (String key : CPTs.keySet()) {
            codes.putIfAbsent(key, new LinkedList<>());
            for (String cptKey : CPTs.get(key).keySet()) {
                CPT cpt = new CPT();
                cpt.setCode(cptKey);
                cpt.setDescription(CPTs.get(key).get(cptKey));
                codes.get(key).add(cpt);
            }
        }
        return codes;
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
        icd10.setId(UUID.randomUUID().toString());
        icd10.setCode(code);
        icd10.setDescription(description);
        return icd10;
    }
}
