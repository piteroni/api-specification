package io.github.piteroni.api.specification.data;

import io.github.piteroni.api.specification.bind.ApiTestContextTestData;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.greghaskins.spectrum.Spectrum;
import static com.greghaskins.spectrum.dsl.specification.Specification.*;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

@RunWith(Spectrum.class)
public class ApiTestContextTest extends ApiTestContextTestData {{
  describe("APIテスト実行時のテスト対象やテストデータを扱う ApiTestContext クラス", () -> {
    beforeAll(() -> {
      json = "{" +
          "  \"path\": \"/login\"," +
          "  \"method\": \"POST\"," +
          "  \"statusCode\": 200," +
          "  \"requestData\": {" +
          "    \"email\": \"lind.lula@example.com\"," +
          "    \"password\": \"password\"" +
          "  }," +
          "  \"responseData\": {" +
          "    \"apiToken\": \"af3d45c6b31c5ff6d1497d244e8197489dcce1798d4b\"" +
          "  }" +
          "}";
    });

    beforeEach(() -> {
      mapper = new ObjectMapper();
      apiTestContext = mapper.readValue(json, ApiTestContext.class);
    });

    it("getPathメソッドを呼ぶと、APIのテスト対象パスを取得できる", () -> {
      assertThat(apiTestContext.getPath()).isEqualTo("/login");
    });

    it("getMethodメソッドを呼ぶと、APIのテスト対象HTTPメソッドを取得できる", () -> {
      assertThat(apiTestContext.getMethod()).isEqualTo("POST");
    });

    it("getStatusメソッドを呼ぶと、APIのテスト対象HTTPステータスコードを取得できる", () -> {
      assertThat(apiTestContext.getStatusCode()).isEqualTo(Integer.valueOf(200));
    });

    it("getRequestDataメソッドを呼ぶと、APIの送信したリクエストボディを取得できる", () -> {
      assertThat(apiTestContext.getRequestData().getAdditionalProperties()).containsEntry("email", "lind.lula@example.com");
    });

    it("getResponseDataメソッドを呼ぶと、APIから送信されたレスポンスボディを取得できる", () -> {
      assertThat(apiTestContext.getResponseData().getAdditionalProperties()).containsEntry("apiToken", "af3d45c6b31c5ff6d1497d244e8197489dcce1798d4b");
    });

    it("equalsメソッドを呼ぶと、オブジェクトを比較し、属性が一致するか否かを取得できる", () -> {
      assertThat(apiTestContext).isEqualTo(mapper.readValue(json, ApiTestContext.class));
    });
  });
}}
