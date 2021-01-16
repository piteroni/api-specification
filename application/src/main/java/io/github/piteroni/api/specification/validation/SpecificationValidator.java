package io.github.piteroni.api.specification.validation;

import io.github.piteroni.api.specification.model.OperationCase;
import io.github.piteroni.api.specification.data.ApiTestContext;
import io.github.piteroni.api.specification.data.ApiTestContextCollection;
import io.github.piteroni.api.specification.data.SearchCondition;
import io.github.piteroni.api.specification.data.exception.ApiTestContextDoesNotExistException;

import io.swagger.v3.oas.models.responses.ApiResponse;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.ValidationException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.String.format;

public class SpecificationValidator {
    /**
     * 取得するレスポンスのメディアタイプを表す.
     */
    private static final String MEDIA_TYPE = "application/json";

    /**
     * 1つ以上の要素の検証に失敗したかを表す.
     */
    private boolean isFailed = false;

    /**
     * 検証対象であるOperationCaseを格納するリスト.
     */
    private List<OperationCase> operationCases;

    /**
     * 検証結果を格納する連想配列.
     */
    private Map<String, OperationCaseValidationResult> validateResultMap = new HashMap<>();

    /**
     * 失敗した検証の結果を格納するリスト.
     */
    private List<ValidationResult> failedValidations = new ArrayList<>();

    public SpecificationValidator(List<OperationCase> operationCases) {
        this.operationCases = operationCases;
    }

    /**
     * 渡されたデータがOperationCaseに従っているか検証する.
     * 
     * @param apiTestContexts 検証対象データ.
     */
    public void validate(ApiTestContextCollection apiTestContextCollection) {
        for (OperationCase operationCase : operationCases) {
            String operationCaseName = format("%s(%s)", operationCase.getPath(), operationCase.getMethod());

            List<ApiTestContext> contextList = null;

            try {
                contextList = apiTestContextCollection.take((SearchCondition) operationCase);
            } catch (ApiTestContextDoesNotExistException e) {
                isFailed = true;

                if (!validateResultMap.containsKey(operationCaseName)) {
                    ValidationResult validationResult = new ValidationResult("-", operationCaseName);
                    validationResult.fail();
                    validationResult.setCause(e);
                    addValidationResult(operationCaseName, validationResult);
                }

                continue;
            }

            ValidationResult validationResult = validateResponse(operationCase, contextList);
            
            if (validationResult != null) {
                addValidationResult(operationCaseName, validationResult);
            }
        }
    }

    /**
     * 渡されたデータがレスポンススキーマに従っているか検証する.
     * 
     * @param operationCase レスポンススキーマを含んだOperationCaseオブジェクト.
     * @param contextList 検証対象データのリスト.
     * @return 検証が実施された場合に検証結果が返され、検証が実施されない場合はnullを返す.
     */
    private ValidationResult validateResponse(OperationCase operationCase, List<ApiTestContext> contextList) {
        ApiResponse response = operationCase.getResponse();

        if (response == null || response.getContent() == null || response.getContent().get(MEDIA_TYPE) == null) {
            return null;
        }

        io.swagger.v3.oas.models.media.Schema<?> responseSchema = response.getContent().get(MEDIA_TYPE).getSchema();

        String path = operationCase.getPath();
        String method = operationCase.getMethod();
        Integer statusCode = operationCase.getStatusCode();

        String validationTargetName = format("%d - response body", statusCode);
        String operationCaseSummary = format("%s(%s) %d - response body", path, method, statusCode);

        ValidationResult validationResult = new ValidationResult(validationTargetName, operationCaseSummary);

        Schema jsonResponseSchema = SchemaLoader.load(new JSONObject(responseSchema));

        try {
            contextList.forEach(context -> {
                jsonResponseSchema.validate(new JSONObject(context.getResponseData().getAdditionalProperties()));
            });
        } catch (ValidationException e) {
            validationResult.fail();
            validationResult.setCause(e);
        }

        return validationResult;
    }
    
    /**
     * 検証結果を各種リソースに追加する.
     * 
     * @param operationCaseName 連想配列のキーにあたるOperationCase名.
     * @param validationResult 検証結果.
     */
    private void addValidationResult(String operationCaseName, ValidationResult validationResult) {
        if (validateResultMap.containsKey(operationCaseName)) {
            OperationCaseValidationResult operationCaseValidationResult = validateResultMap.get(operationCaseName);

            operationCaseValidationResult.addValidationResult(validationResult);

            if (validationResult.isFailed()) {
                operationCaseValidationResult.fail();
            }
        } else {
            OperationCaseValidationResult operationCaseValidationResult = new OperationCaseValidationResult(
                    operationCaseName);

            if (validationResult.isFailed()) {
                operationCaseValidationResult.fail();
            }

            validateResultMap.put(operationCaseName,
                    operationCaseValidationResult.addValidationResult(validationResult));
        }
        
        if (validationResult.isFailed()) {
            isFailed = true;
            failedValidations.add(validationResult);
        }
    }

    /**
     * 1つ以上の要素の検証に失敗したかを取得する.
     * 
     * @return 1つ以上の要素の検証に失敗したか.
     */
    public boolean isFailed() {
        return isFailed;
    }

    /**
     * 検証結果が格納された連想配列を取得する.
     * 
     * @return 検証結果が格納された連想配列.
     */
    public Map<String, OperationCaseValidationResult> getValidateResultMap() {
        return validateResultMap;
    }

    /**
     * 失敗した検証の結果が格納されたリストを取得する.
     * 
     * @return 失敗した検証の結果が格納されたリスト.
     */
    public List<ValidationResult> getFailedValidations() {
        return failedValidations;
    }
}
