package io.github.piteroni.api.specification.bind;

import io.github.piteroni.api.specification.data.ApiTestContextCollection;
import io.github.piteroni.api.specification.data.SearchCondition;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiTestContextCollectionTestData {
  protected class SearchConditionImpl implements SearchCondition {
    private String path;

    private String method;

    private Integer statusCode;

    public SearchConditionImpl(String path, String method, Integer statusCode) {
      this.path = path;
      this.method = method;
      this.statusCode = statusCode;
    }

    @Override
    public String getPath() {
      return path;
    }

    @Override
    public String getMethod() {
      return method;
    }

    @Override
    public Integer getStatusCode() {
      return statusCode;
    }
  }

  protected ApiTestContextCollection apiTestContextCollection;

  protected ObjectMapper mapper;

  protected String json;
}
