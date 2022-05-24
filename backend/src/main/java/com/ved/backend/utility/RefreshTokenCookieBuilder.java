package com.ved.backend.utility;

import javax.servlet.http.Cookie;

public class RefreshTokenCookieBuilder {
  private final Cookie cookie;
  public RefreshTokenCookieBuilder(String value) {
    this.cookie = new Cookie("refresh_token", value);
    this.cookie.setHttpOnly(true);
    this.cookie.setSecure(false);
    this.cookie.setPath("/");
  }

  public Cookie build() {
    return this.cookie;
  }
}
