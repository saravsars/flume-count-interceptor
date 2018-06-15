# flume-count-interceptor

Flume interceptor that adds rolling count to each event in flume header which can be used to maintain the order of events

## Getting started

1. **Build flume-count-interceptor**

    <pre>
    $ git clone https://github.com/saravsars/flume-count-interceptor.git
    $ cd flume-count-interceptor
    $ mvn clean package
    $ ls target
    flume-count-interceptor-1.0.jar
    </pre>

2. **Add JARs to the Flume classpath**

    <pre>
    $ cp /etc/flume-ng/conf/flume-env.sh.template /etc/flume-ng/conf/flume-env.sh
    $ vi /etc/flume-ng/conf/flume-env.sh
    FLUME_CLASSPATH="/path/to/file/flume-count-interceptor-1.0.jar"
    </pre>

3. **Set the Flume agent configuration**

    <pre>
    agent.sources.sourcename.interceptors = i1
    agent.sources.sourcename.interceptors.i1.type = com.sars.flume.interceptor.CountInterceptor$Builder
    agent.sources.sourcename.interceptors.i1.headerName = event_count
    </pre>

