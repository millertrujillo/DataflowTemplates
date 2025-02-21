/*
 * Copyright (C) 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.cloud.teleport.v2.options;

import com.google.cloud.teleport.metadata.TemplateParameter;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryOptions;
import org.apache.beam.sdk.options.Default;

/** Interface used by the JdbcToBigQuery pipeline to accept user input. */
public interface JdbcToBigQueryOptions extends CommonTemplateOptions, BigQueryOptions {

  @TemplateParameter.Text(
      order = 1,
      optional = false,
      regexes = {"^.+$"},
      description = "Cloud Storage paths for JDBC drivers",
      helpText = "Comma separate Cloud Storage paths for JDBC drivers.",
      example = "gs://your-bucket/driver_jar1.jar,gs://your-bucket/driver_jar2.jar")
  String getDriverJars();

  void setDriverJars(String driverJar);

  @TemplateParameter.Text(
      order = 2,
      optional = false,
      regexes = {"^.+$"},
      description = "JDBC driver class name.",
      helpText = "JDBC driver class name to use.",
      example = "com.mysql.jdbc.Driver")
  String getDriverClassName();

  void setDriverClassName(String driverClassName);

  @TemplateParameter.Text(
      order = 3,
      optional = false,
      regexes = {
        "(^jdbc:[a-zA-Z0-9/:@.?_+!*=&-;]+$)|(^([A-Za-z0-9+/]{4}){1,}([A-Za-z0-9+/]{0,3})={0,3})"
      },
      description = "JDBC connection URL string.",
      helpText =
          "Url connection string to connect to the JDBC source. Connection string can "
              + "be passed in as plaintext or as a base64 encoded string encrypted by Google Cloud KMS.",
      example = "jdbc:mysql://some-host:3306/sampledb")
  String getConnectionURL();

  void setConnectionURL(String connectionURL);

  @TemplateParameter.Text(
      order = 4,
      optional = true,
      regexes = {"^[a-zA-Z0-9_;!*&=@#-:\\/]+$"},
      description = "JDBC connection property string.",
      helpText =
          "Properties string to use for the JDBC connection. Format of the string must be [propertyName=property;]*.",
      example = "unicode=true;characterEncoding=UTF-8")
  String getConnectionProperties();

  void setConnectionProperties(String connectionProperties);

  @TemplateParameter.Text(
      order = 5,
      optional = true,
      regexes = {"^.+$"},
      description = "JDBC connection username.",
      helpText =
          "User name to be used for the JDBC connection. User name can be passed in as plaintext "
              + "or as a base64 encoded string encrypted by Google Cloud KMS.")
  String getUsername();

  void setUsername(String username);

  @TemplateParameter.Password(
      order = 6,
      optional = true,
      description = "JDBC connection password.",
      helpText =
          "Password to be used for the JDBC connection. Password can be passed in as plaintext "
              + "or as a base64 encoded string encrypted by Google Cloud KMS.")
  String getPassword();

  void setPassword(String password);

  @TemplateParameter.Text(
      order = 7,
      optional = false,
      regexes = {"^.+$"},
      description = "JDBC source SQL query.",
      helpText = "Query to be executed on the source to extract the data.",
      example = "select * from sampledb.sample_table")
  String getQuery();

  void setQuery(String query);

  @TemplateParameter.BigQueryTable(
      order = 8,
      description = "BigQuery output table",
      helpText =
          "BigQuery table location to write the output to. The name should be in the format <project>:<dataset>.<table_name>. The table's schema must match input objects.")
  String getOutputTable();

  void setOutputTable(String value);

  @TemplateParameter.GcsWriteFolder(
      order = 9,
      optional = false,
      description = "Temporary directory for BigQuery loading process",
      helpText = "Temporary directory for BigQuery loading process",
      example = "gs://your-bucket/your-files/temp_dir")
  String getBigQueryLoadingTemporaryDirectory();

  void setBigQueryLoadingTemporaryDirectory(String directory);

  @TemplateParameter.KmsEncryptionKey(
      order = 10,
      optional = true,
      description = "Google Cloud KMS key",
      helpText =
          "If this parameter is provided, password, user name and connection string should all be passed in encrypted. Encrypt parameters using the KMS API encrypt endpoint. See: https://cloud.google.com/kms/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys/encrypt",
      example = "projects/your-project/locations/global/keyRings/your-keyring/cryptoKeys/your-key")
  String getKMSEncryptionKey();

  void setKMSEncryptionKey(String keyName);

  @TemplateParameter.Boolean(
      order = 11,
      optional = true,
      description = "Whether to use column alias to map the rows.",
      helpText =
          "If enabled (set to true) the pipeline will consider column alias (\"AS\") instead of the column name to map the rows to BigQuery. Defaults to false.")
  @Default.Boolean(false)
  Boolean getUseColumnAlias();

  void setUseColumnAlias(Boolean useColumnAlias);

  @TemplateParameter.Boolean(
      order = 12,
      optional = true,
      description = "Whether to truncate data before writing",
      helpText =
          "If enabled (set to true) the pipeline will truncate before loading data into BigQuery. Defaults to false, which is used to only append data.")
  @Default.Boolean(false)
  Boolean getIsTruncate();

  void setIsTruncate(Boolean isTruncate);
}
