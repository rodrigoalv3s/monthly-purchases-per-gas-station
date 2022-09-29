# Sample Project - Apache Beam Pipeline

## Objective

This repository was created for personal study purposes.

## Use Case

The pipeline have to read the csv file containing gas station transactions and group them by gasStationId and summing the total of transactions per month (YYYY-MM).
### Dataset (input data)

This use case was executed using a dataset example from the Relational.Fit website:
[https://relational.fit.cvut.cz/dataset/CCS](Relational Fit CCS)

The database data was dumped into a csv file:

"TransactionID","Date","Time","CustomerID","CardID","GasStationID","ProductID","Amount","Price"
1,2012-08-24,"09:41:00",31543,486621,3704,2,28,672.64
2,2012-08-24,"10:03:00",46707,550134,3704,2,18,430.72
3,2012-08-24,"10:03:00",46707,550134,3704,23,1,121.99
4,2012-08-24,"13:53:00",7654,684220,656,5,5,120.74
5,2012-08-24,"08:49:00",17373,536109,741,2,28,645.05
....

### Output

The expected result is a csv file with the total of transactions per month per gas station:

| gasStationId | yearMonth | sumTransactions |
|--------------|-----------|-----------------|
| 1            | 2022-07   | 200.99          |
| 2            | 2022-08   | 100.99          |
| 3            | 2022-09   | 99.99           |

## Apache Beam concepts applied

In this pipeline was implemented the following concepts and techniques:

* IO connector
* ParDo
* PTransform
* Composite transforms
* Aggregation functions
* Schema
* Pipeline Options
* Unit Tests (Asserts and PAsserts)
* Dataflow Flex Templates

## How to run?

### Locally

```sh
mvn compile exec:java -Dexec.mainClass=br.com.rodrigo.pipeline.MonthlyPurchasesPerGasStation -Dexec.args="--inputPath=~/Documents/projects/dataflow/monthly-purchases-per-gas-station/input/ --outputPath=/Users/rodrigo/Documents/projects/dataflow/monthly-purchases-per-gas-station/output/ --fileName=transactions_1k_202209260946.csv" -P direct-runner
```

### Google Cloud Dataflow

```bash
gcloud builds submit --tag us-east1-docker.pkg.dev/playground-s-11-9edb741d/dataflow-samples/dataflow/monthly-purchases-per-gas-station:latest .
```

```bash
mvn clean package
```

```bash
gcloud dataflow flex-template build gs://dataflow-templates-playground-s-11-9edb741d/samples/dataflow/templates/monthly-purchases-per-gas-station.json \
--image-gcr-path "us-east1-docker.pkg.dev/playground-s-11-9edb741d/dataflow-samples/dataflow/monthly-purchases-per-gas-station:latest" \
--sdk-language "JAVA" \
--flex-template-base-image JAVA11 \
--metadata-file "metadata.json" \
--jar "target/monthly-purchases-per-gas-station-1.0-SNAPSHOT.jar" \
--env FLEX_TEMPLATE_JAVA_MAIN_CLASS="br.com.rodrigo.pipeline.MonthlyPurchasesPerGasStation"
```

```bash
gcloud dataflow flex-template run "monthly-purchases-per-gas-station-`date +%Y%m%d-%H%M%S`" \
--template-file-gcs-location "gs://dataflow-templates-playground-s-11-9edb741d/samples/dataflow/templates/monthly-purchases-per-gas-station.json" \
--parameters inputPath="gs://dataflow-data-bucket-playground-s-11-9edb741d/input/" \
--parameters fileName="input/transactions_1k_202209260946.csv" \
--parameters outputPath="gs://dataflow-data-bucket-playground-s-11-9edb741d/input/" \
--region "us-east1"
```
