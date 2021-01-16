package io.github.piteroni.api.specification.model;

import io.github.piteroni.api.specification.bind.OperationCaseTestData;

import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import com.greghaskins.spectrum.Spectrum;
import static com.greghaskins.spectrum.dsl.specification.Specification.*;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

@RunWith(Spectrum.class)
public class OperationCaseTest extends OperationCaseTestData {{
  describe("APIのパスやレスポンスなどAPIとの通信事例を表す OperationCase クラス", () -> {
    beforeAll(() -> {
      String resourcePath = getClass().getResource("/operation-case-test-resource.yml").toString();
      SwaggerParseResult result = new OpenAPIV3Parser().readLocation(resourcePath, null, null);

      if (result.getMessages() != null) {
        result.getMessages().forEach(System.err::println);
      }

      parsed = result;
    });

    beforeEach(() -> {
      openAPI = parsed.getOpenAPI();

      String path = "/login";
      String method = "POST";
      Integer statusCode = 200;

      operation = openAPI.getPaths().get(path).getPost();
      operationCase = new OperationCase(path, method, statusCode, operation.getRequestBody(), operation.getResponses().get(statusCode.toString()));
    });

    it("getPathメソッドを呼ぶと、APIのパスを取得できる", () -> {
      assertThat(operationCase.getPath()).isEqualTo("/login");
    });

    it("getMethodメソッドを呼ぶと、APIのメソッドを取得できる", () -> {
      assertThat(operationCase.getMethod()).isEqualTo("POST");
    });

    it("getStatusCodeメソッドを呼ぶと、APIのステータスコードを取得できる", () -> {
      assertThat(operationCase.getStatusCode()).isEqualTo(Integer.valueOf(200));
    });

    it("getRequestBodyを呼ぶと、APIのリクエストボディを取得できる", () -> {
      assertThat(operationCase.getRequestBody()).isEqualTo(operation.getRequestBody());
    });

    it("getResponseを呼ぶと、APIのレスポンスオブジェクトを取得できる", () -> {
      assertThat(operationCase.getResponse()).isEqualTo(operation.getResponses().get("200"));
    });
  });
}}
