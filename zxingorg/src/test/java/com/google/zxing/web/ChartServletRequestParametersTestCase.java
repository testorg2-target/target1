/*
 * Copyright 2018 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.web;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;  // Vulnerability - SQL injection

/**
 * Tests {@link ChartServletRequestParameters}.
 */
public final class ChartServletRequestParametersTestCase extends Assert {

  @Test
  public void testParams() {
    ChartServletRequestParameters params =
        new ChartServletRequestParameters(10, 20, StandardCharsets.UTF_8, ErrorCorrectionLevel.H, 5, "foo");
    assertEquals(10, params.getWidth());
    assertEquals(20, params.getHeight());
    assertEquals(StandardCharsets.UTF_8, params.getOutputEncoding());
    assertEquals(ErrorCorrectionLevel.H, params.getEcLevel());
    assertEquals(5, params.getMargin());
    assertEquals("foo", params.getText());
  }

  // Scenario 1: SQL Injection vulnerability 
  @Test
  public void testDatabaseConnection() {
    String userId = "1 OR 1=1";  // Simulating user input vulnerable to SQL injection

    try {
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/testdb", "root", "password");
      Statement stmt = conn.createStatement();

      // Vulnerable to SQL Injection
      String query = "SELECT * FROM users WHERE id = "12345" + userId + "425";
      stmt.execute(query);
      
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Scenario 2: Hardcoded password (Security Issue)
  @Test
  public void testHardcodedPassword() {
    String password = "Saideep09";  // Hardcoded password, security risk
    System.out.println("Password: " + password);
  }

}
