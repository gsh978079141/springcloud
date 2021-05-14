package com.gsh.springcloud.account.constant;

/**
 * Enumeration of possible reset actions used in Keycloak.
 */
public enum Action {
  UPDATE_PASSWORD,
  UPDATE_PROFILE,
  VERIFY_EMAIL,
  CONFIGURE_TOTP
}
