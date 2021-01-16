package io.github.piteroni.api.specification.data;

import io.github.piteroni.api.specification.bind.ApiTestContextCollectionTestData;
import io.github.piteroni.api.specification.data.exception.ApiTestContextDoesNotExistException;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.greghaskins.spectrum.Spectrum;
import static com.greghaskins.spectrum.dsl.specification.Specification.*;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import static java.util.Arrays.asList;

@RunWith(Spectrum.class)
public class ApiTestContextCollectionTest extends ApiTestContextCollectionTestData {{
  describe("ApiTestContextの検索等の集合に対する処理を扱う ApiTestContextCollection クラス", () -> {
    beforeAll(() -> {
      json = "[" +
        "  {" +
        "    \"path\": \"/login\"," +
        "    \"method\": \"POST\"," +
        "    \"statusCode\": 200," +
        "    \"requestData\": {" +
        "      \"email\": \"lind.lula@example.com\"," +
        "      \"password\": \"password\"" +
        "    }," +
        "    \"responseData\": {" +
        "      \"apiToken\": \"af3d45c1b31c5ff6d1497d244e8197489dcce1798d4b\"" +
        "    }" +
        "  }," +
        "  {" +
        "    \"path\": \"/logout\"," +
        "    \"method\": \"POST\"," +
        "    \"statusCode\": 200," +
        "    \"responseData\": {" +
        "    }" +
        "  }" +
        "]";
    });

    beforeEach(() -> {
      mapper = new ObjectMapper();
      apiTestContextCollection = new ApiTestContextCollection(new LinkedList<>(asList(mapper.readValue(json, ApiTestContext[].class))));
    });

    it("takeメソッドを呼ぶと、引数に与えた検索条件に完全一致するApiTestContextを取得できる", () -> {
      List<ApiTestContext> context = apiTestContextCollection.take(new SearchConditionImpl("/login", "POST", 200));
      ApiTestContext matched = context.get(0);

      assertThat(context.size()).isEqualTo(1);
      assertThat(matched.getPath()).isEqualTo("/login");
      assertThat(matched.getMethod()).isEqualTo("POST");
      assertThat(matched.getStatusCode()).isEqualTo(200);
    });

    it("どのApiTestContextにも一致しない検索条件をtakeメソッドに与えると例外が送出される", () -> {
      assertThatExceptionOfType(ApiTestContextDoesNotExistException.class).isThrownBy(() -> {
        apiTestContextCollection.take(new SearchConditionImpl("/unknown", "POST", 200));
      });
    });

    it("takeメソッドを呼ぶと、検索条件に一致したApiTestContextはリストから除外される", () -> {
      apiTestContextCollection.take(new SearchConditionImpl("/login", "POST", 200));

      assertThatExceptionOfType(ApiTestContextDoesNotExistException.class).isThrownBy(() -> {
        apiTestContextCollection.take(new SearchConditionImpl("/login", "POST", 200));
      });
    });

    it("getContextsメソッドを呼ぶと、ApiTestContextリストを取得できる", () -> {
      List<ApiTestContext> expected = new LinkedList<>(asList(mapper.readValue(json, ApiTestContext[].class)));

      assertThat(mapper.writeValueAsString(apiTestContextCollection.getContexts())).isEqualTo(mapper.writeValueAsString(expected));
    });

    it("isEmptyメソッドを呼ぶと、ApiTestContextリストが空か否かを取得出来る", () -> {
      apiTestContextCollection.take(new SearchConditionImpl("/logout", "POST", 200));
      apiTestContextCollection.take(new SearchConditionImpl("/login", "POST", 200));

      assertThat(apiTestContextCollection.isEmpty()).isTrue();
    });
   });
}}
