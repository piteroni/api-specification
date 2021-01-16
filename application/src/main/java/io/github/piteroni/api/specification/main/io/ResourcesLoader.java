package io.github.piteroni.api.specification.main.io;

import io.github.piteroni.api.specification.data.ApiTestContext;
import io.github.piteroni.api.specification.main.io.exception.OpenAPIParseException;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import static java.util.Arrays.asList;

public class ResourcesLoader {
  private ResourcesLoader() {
  }

  /**
   * OpenAPI定義を取得する.
   *
   * @return
   *   OpenAPI定義
   * @throws OpenAPIParseException
   *   リソースがOpenAPI Specification定義に従わない場合に送出される.
   */
  public static OpenAPI getOpenAPI(String path) throws OpenAPIParseException {
    SwaggerParseResult result = new OpenAPIV3Parser().readLocation(path, null, null);

    List<String> messages = result.getMessages();

    if (!messages.isEmpty()) {
      throw new OpenAPIParseException(messages);
    }

    return result.getOpenAPI();
  }

  /**
   * APIテストコンテキストの一覧を取得する.
   *
   * @return
   *   APIテストコンテキストの一覧.
   * @throws IOException
   *   リソースがJSON形式に従わない場合、または低レイヤーでの問題が発生した場合に送出される.
   */
  public static List<ApiTestContext> getApiTestContexts(String path) throws IOException {
    List<String> lines = Files.readAllLines(Path.of(path));

    ObjectMapper mapper = new ObjectMapper();

    return new LinkedList<>(asList(mapper.readValue(String.join("", lines), ApiTestContext[].class)));
  }
}
