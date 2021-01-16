package io.github.piteroni.api.specification.builder;

import io.github.piteroni.api.specification.builder.exception.OperationCasesBuildException;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.ResolverCache;

import com.greghaskins.spectrum.Spectrum;
import static com.greghaskins.spectrum.dsl.specification.Specification.*;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

@RunWith(Spectrum.class)
public class OperationCaseBuildServiceTest {{
  describe("OpenAPIからOperationCaseリストをビルドする OperationCaseBuildService クラス", () -> {
    it("openAPIオブジェクトをbuildメソッドに渡すと、ビルドされたOperationCaseリストを取得できる", () -> {
      String resourcePath = getClass().getResource("/operation-case-build-service-test-correct-resource.yml").toString();
      OpenAPI openAPI = new OpenAPIV3Parser().readLocation(resourcePath, null, null).getOpenAPI();
      ResolverCache cache = new ResolverCache(openAPI, null, null);
      OperationCaseBuilder builder = new OperationCaseBuilder(cache);
      OperationCaseBuildService buildeService = new OperationCaseBuildService(builder);
      String path = "/correct";

      assertThat(buildeService.build(openAPI)).isEqualTo(builder.build(path, openAPI.getPaths().get(path)));
    });

    it("規約に従わないopenAPIオブジェクトをbuildメソッドに渡すと、例外が送出される", () -> {
      String resourcePath = getClass().getResource("/operation-case-build-service-test-incorrect-resource.yml").toString();
      OpenAPI openAPI = new OpenAPIV3Parser().readLocation(resourcePath, null, null).getOpenAPI();
      ResolverCache cache = new ResolverCache(openAPI, null, null);
      OperationCaseBuilder builder = new OperationCaseBuilder(cache);
      OperationCaseBuildService buildeService = new OperationCaseBuildService(builder);

      assertThatExceptionOfType(OperationCasesBuildException.class).isThrownBy(() -> {
        buildeService.build(openAPI);
      });
    });
  });
}}
