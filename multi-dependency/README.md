# Example Multi Module Binding Generation

### 1. XML Catalogs

#### Generated Catalog Files

XML catalogs help xml parsers and XJC find imported or included XML Schema files. The model builder maven plugin is used to generate 3 sets of xml catalog files.

1. `{project}/design-catalog.xml`

This file will contains a mapping of all XLM schemata in the `{project}/src/main/resources/{model.name}` directory. This file can be used to configure design tools such as OxygenXML to resolve all model files across different module folders or even from the classpath.

2. `{jar}/META-INF/{model.name}/catalog.xml`

This file is a basic catalog file that can be used to directly reference the data files in the given model. However when working with modular models it is possible that 2 models have the same xsd file. In this situation the catalogs must allow to differentiate the imports by name.

3. `{jar}/META-INF/common.xcat`

When resolving multiple catalog files from the classpath e.g. catalog file reference in a cxf runtime module, having them all named a same will cause the module to fail loading resolve any subsequent files. 

TODO: catalog generator should also support rewriteSystem formats:

```xml
<!DOCTYPE catalog
    PUBLIC "-//OASIS//DTD Entity Resolution XML Catalog V1.0//EN" "http://www.oasis-open.org/committees/entity/release/1.0/catalog.dtd">
<catalog xmlns="urn:oasis:names:tc:entity:xmlns:xml:catalog"
         prefer="public">
    <rewriteSystem systemIdStartString="http://www.w3.org/1999/xlink" rewritePrefix="w3c"/>
</catalog>
```

It will rewrite all URIs starting with http://www.w3.org to start with w3c instead.

So the schema location http://www.w3.org/1999/xlink.xsd in the following import:

```xml
<import namespace="http://www.w3.org/1999/xlink"
    schemaLocation="http://www.w3.org/1999/xlink.xsd"/>
```

Will be turned into `w3c/1999/xlink.xsd` and finally resolved to a local file.


### 2. Episodes



### 3. Gotchas

#### Multiple Modules with Same Schema Names 

The following describes how to setup models in different modules which may contain the same file names. Lets say model 1 and model 2 both contain a `SomeSchema.xsd` file. 


Lets say we have a 3rd schema that wants to import SomeSchema.xsd from model1 and model2. Something like below will not work obviously.

```xml
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">
  <!-- will never work -->
  <xsd:import namespace="urn:model1" schemaLocation="SomeSchema.xsd"/>
  <xsd:import namespace="urn:model2" schemaLocation="SomeSchema.xsd"/>
</xsd:schema>
```

We need to make sure that model 1 and 2 schemata are packaged in a folder that allows us to differentiate the 2 files by name. We also need to create a xml catalog file for each model that allows us to import these correctly.

Module for model 1

```
model1
  |
  +-- SomeSchema.xsd
  |
  +-- catalog.xml
```

Catalog file for model 1

```xml
<catalog xmlns="urn:oasis:names:tc:entity:xmlns:xml:catalog">
  <system systemId="http://www.oasis-open.org/committees/entity/release/1.1/catalog.dtd"
          uri="resource:org/apache/xml/resolver/etc/catalog.dtd"/>
  <systemSuffix systemIdSuffix="model1/SomeSchema.xsd" uri="SomeSchema.xsd"/>
</catalog>
```

Module for model 2

```
model2
  |
  +-- SomeSchema.xsd
  |
  +-- catalog.xml
```

Catalog file for model 2

```xml
<catalog xmlns="urn:oasis:names:tc:entity:xmlns:xml:catalog">
  <system systemId="http://www.oasis-open.org/committees/entity/release/1.1/catalog.dtd"
          uri="resource:org/apache/xml/resolver/etc/catalog.dtd"/>
  <systemSuffix systemIdSuffix="model2/SomeSchema.xsd" uri="SomeSchema.xsd"/>
</catalog>
```

Now we can import both schemata with the same file name into schema number 3 as below example by using the model1 and model2 catalog.xml files.

```xml
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">
	<xsd:import namespace="urn:model1" schemaLocation="model1/SomeSchema.xsd"/>
	<xsd:import namespace="urn:model2" schemaLocation="model2/SomeSchema.xsd"/>
</xsd:schema>
```

The design-model-maven-plugin's catalog generator has a feature to automatically generate XML catalog files with xml schema folder offsets. Simply specify a `systemIdPathOffset` greater than 0 and the xml catalogs generated by the plugin will offset the path to the immediate parent folder where the schema resides in.  

Example plugin execution 
```xml
<plugin>
  <groupId>io.fares.maven.plugins</groupId>
  <artifactId>design-builder-maven-plugin</artifactId>
  <executions>
    <execution>
      <id>generate-catalog</id>
      <phase>process-resources</phase>
      <goals>
        <goal>catalog</goal>
      </goals>
      <configuration>
        <sourceDirectory>${project.build.outputDirectory}/META-INF</sourceDirectory>
        <targetCatalogFile>${project.build.outputDirectory}/META-INF/schema/catalog.xml</targetCatalogFile>
        <systemIdPathOffset>1</systemIdPathOffset>
        <includes>
          <include>**/*.xsd</include>
        </includes>
        <verbose>true</verbose>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### XSDL Gen

```xml
<plugin>
  <groupId>org.jvnet.jax-ws-commons</groupId>
  <artifactId>jaxws-maven-plugin</artifactId>
  <version>2.3</version>
  <configuration>
    <vmArgs>
        <vmArg>-Djavax.xml.accessExternalSchema=all</vmArg>
    </vmArgs>
  </configuration>
</plugin>
```

#### XJC with Hibernate Generation

https://github.com/highsource/hyperjaxb3/blob/master/ejb/tests/annox/pom.xml

