
## Manual tracing example based on opentelemetry with a Spring Boot application 


### Introduction

The goal is to take advantage of the spring framework and instrument a basic rest application using opentelemetry. 
Both manual and automatic instrumentation will be covered. 


### Notes on current branch
This branch uses `tracer inject/extract()` calls that are meant to be used to propagate context across processes and service boundaries.  


### Download and install the application

<pre style="font-size: 12px">
git clone https://github.com/ptabasso2/springopentelemetry.git
</pre>

### Configure the tracer
In the `Application` class, 

### Build the application

<pre style="font-size: 12px">
COMP10619:~ pejman.tabassomi$ ./gradlew build
</pre>


### Test the application with  Datadog

**_1. Start the  Datadog Agent_**

Please provide your API key
<pre style="font-size: 12px">
COMP10619:~ pejman.tabassomi$ docker run -d --rm -h datadog --name datadog_agent -v /var/run/docker.sock:/var/run/docker.sock:ro -v /proc/:/host/proc/:ro -v /sys/fs/cgroup/:/host/sys/fs/cgroup:ro -p 8126:8126 -p 8125:8125/udp -e DD_API_KEY=xxxxxxxxxxxxxxxxxxxxxxx -e DD_TAGS=env:datadoghq.com -e DD_APM_ENABLED=true -e DD_APM_NON_LOCAL_TRAFFIC=true -e DD_PROCESS_AGENT_ENABLED=true -e DD_LOG_LEVEL=debug gcr.io/datadoghq/agent:7
</pre>

**_2. Run the application_**
<pre style="font-size: 12px">
COMP10619:~ pejman.tabassomi$ java -jar build/libs/SpringOpenTelemetry-1.0.jar
</pre>

**_3. Run the test several times_** 
<pre style="font-size: 12px">
COMP10619:~ pejman.tabassomi$ curl localhost:8080/Upstream
Ok
</pre>

**_4. Check the results in the Datadog UI (APM traces)_<br>**
https://app.datadoghq.com/apm/traces

**_5. Using the Java agent_<br>**


### Test the application with Jaeger

**_1. Start the Jaeger backend_**
<pre style="font-size: 12px">
COMP10619:~ pejman.tabassomi$ docker run --rm -p 6831:6831/udp -p 6832:6832/udp -p 16686:16686 jaegertracing/all-in-one:1.7 --log-level=debug
</pre>

**_2. Run the application_**
<pre style="font-size: 12px">
COMP10619:~ pejman.tabassomi$ java -jar build/libs/SpringOpenTelemetry-1.0.jar
</pre>

**_3. Check the results in the Jaeger UI (APM traces)_<br>**
http://localhost:16686


