package io.github.piteroni.api.specification.main.singleton;

import io.swagger.v3.parser.ResolverCache;

public class ResolverCacheState {
  private static ResolverCache state = null;

  private ResolverCacheState() {
  }

  public static void setResoloverCache(ResolverCache cache) {
    if (state == null) {
      state = cache;
    }
  }

  public static ResolverCache get() {
    return state;
  }
}
