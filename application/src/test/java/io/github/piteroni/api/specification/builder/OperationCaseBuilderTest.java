package io.github.piteroni.api.specification.builder;

import io.github.piteroni.api.specification.model.OperationCase;
import io.github.piteroni.api.specification.builder.exception.ReferenceResolveException;
import io.github.piteroni.api.specification.bind.OperationCaseBuilderTestData;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.ResolverCache;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import com.greghaskins.spectrum.Spectrum;
import static com.greghaskins.spectrum.dsl.specification.Specification.*;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@RunWith(Spectrum.class)
public class OperationCaseBuilderTest extends OperationCaseBuilderTestData {{
  describe("PathItemからOperationCaseリストをビルドする OperationCaseBuilder クラス", () -> {
    beforeAll(() -> {
      String resourcePath = getClass().getResource("/operation-case-builder-test-resource.yml").toString();
      SwaggerParseResult result = new OpenAPIV3Parser().readLocation(resourcePath, null, null);

      if (result.getMessages() != null) {
        result.getMessages().forEach(System.err::println);
      }

      parsed = result;
    });

    beforeEach(() -> {
      openAPI = parsed.getOpenAPI();
    });

    it("buildメソッドを呼ぶと、PathItemからOperationCaseリストをビルドできる", () -> {
      OpenAPI openAPI = parsed.getOpenAPI();
      String path = "/ok";
      PathItem pathItem = openAPI.getPaths().get(path);
      ResolverCache cache = new ResolverCache(openAPI, null, null);

      List<OperationCase> operationCases = new OperationCaseBuilder(cache).build(path, pathItem);

      assertThat(operationCases).hasSize(2);
      assertThat(operationCases).contains(new OperationCase(path, "POST", 200, pathItem.getPost().getRequestBody(), pathItem.getPost().getResponses().get("200")));
      assertThat(operationCases).contains(new OperationCase(path, "POST", 201, pathItem.getPost().getRequestBody(), pathItem.getPost().getResponses().get("201")));
    });

    it("refに設定された値の参照先がレスポンスに設定される", () -> {
      OpenAPI openAPI = parsed.getOpenAPI();
      String path = "/ref";
      PathItem pathItem = openAPI.getPaths().get(path);
      ResolverCache cache = new ResolverCache(openAPI, null, null);

      List<OperationCase> operationCases = new OperationCaseBuilder(cache).build(path, pathItem);
      Optional<OperationCase> operationCase = operationCases.stream().filter(o -> {
        return o.getStatusCode().equals(401);
      }).findAny();

      assertThat(operationCases).hasSize(2);
      assertThat(operationCase.get().getResponse()).isEqualTo(openAPI.getComponents().getResponses().get("unauthorized").$ref(null));
    });

    it("refに設定された値の参照先が存在しない場合、例外が送出される", () -> {
      OpenAPI openAPI = parsed.getOpenAPI();
      String path = "/non-ref";
      PathItem pathItem = openAPI.getPaths().get(path);
      ResolverCache cache = new ResolverCache(openAPI, null, null);

      assertThatExceptionOfType(ReferenceResolveException.class).isThrownBy(() -> {
        new OperationCaseBuilder(cache).build(path, pathItem);
      });
    });
  });
}}

