  FROM gcr.io/dataflow-templates-base/java11-template-launcher-base

  ARG WORKDIR=/dataflow/template
  RUN mkdir -p ${WORKDIR}
  WORKDIR ${WORKDIR}

  COPY pom.xml .
  COPY src .

  ENV FLEX_TEMPLATE_JAVA_MAIN_CLASS="br.com.rodrigo.pipeline.MonthlyPurchasesPerGasStation"
  ENV FLEX_TEMPLATE_JAVA_CLASSPATH="${WORKDIR}/src/main/java/br/com/rodrigo/pipeline/MonthlyPurchasesPerGasStation.java"

  ENTRYPOINT ["/opt/google/dataflow/java_template_launcher"]