package io.github.piteroni.api.specification.builder;

import io.github.piteroni.api.specification.model.OperationCase;
import io.github.piteroni.api.specification.builder.exception.ReferenceResolveException;
import io.github.piteroni.api.specification.builder.exception.InvalidSpecification;

import io.swagger.v3.parser.ResolverCache;
import io.swagger.v3.parser.models.RefFormat;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.List;
import java.util.Map.Entry;
import java.util.ArrayList;
import static java.lang.String.format;

public class OperationCaseBuilder {
   /**
    * 取得するレスポンスのメディアタイプを表す.
    */
   private static final String MEDIA_TYPE = "application/json";

   /**
    * ビルドの際にする使用する ResolverCache.
    */
   private ResolverCache cache;

   public OperationCaseBuilder(ResolverCache cache) {
    this.cache = cache;
   }

   /**
    * pathItemを元にOperationCaseリストをビルドする.
    *
    * @return ビルドしたOperationCaseリスト.
    */
   public List<OperationCase> build(String path, PathItem pathItem) throws InvalidSpecification {
      List<OperationCase> operationCaseList = new ArrayList<>();

      for (Entry<HttpMethod, Operation> operationEntrySet : pathItem.readOperationsMap().entrySet()) {
        HttpMethod method = operationEntrySet.getKey();
        RequestBody requestBody = operationEntrySet.getValue().getRequestBody();

        // OpenAPI ビルド時にresponsesが空のままパースが通ることはないので、ここではgetResponses()の返り値のNullチェックは行わない.
        for (Entry<String, ApiResponse> responseEntrySet : operationEntrySet.getValue().getResponses().entrySet()) {
           Integer statusCode = Integer.valueOf(responseEntrySet.getKey());
           String methodText = method.toString();
           ApiResponse response = responseEntrySet.getValue();
           String responseRef = response.get$ref();

           if ((response.getContent() == null || response.getContent().get(MEDIA_TYPE) == null) && responseRef != null) {
              var apiResponse = cache.loadRef(responseRef, RefFormat.INTERNAL, ApiResponse.class);

              if (apiResponse == null) {
                throw new ReferenceResolveException(format("The reference to %s does not exist", responseRef));
              }

              response = apiResponse;
           }

           operationCaseList.add(new OperationCase(path, methodText, statusCode, requestBody, response));
        }
      }

      return operationCaseList;
   }
}
