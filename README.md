# HPA demonstration for Kubernetes

Simple project to provide a demonstration for HPA - a simple application is run which generates CPU load, causing automatic scaling to take place.

To deploy - build/publish the artefact to Kubernetes as `stress-test:1.0.0` then create a namespace (`kubectl apply -f kubernetes --namespace stresstest-hpa`) 
and apply the configurations to the namespace (`kubectl apply -f kubernetes --namespace stresstest-hpa`).

Once the application is deployed, run `kubectl proxy` and browse to `http://localhost:8001/api/v1/namespaces/stresstest-hpa/services/http:stress-test-published:8080/proxy/(start|stop)` to start/stop a new thread.  
Using proxy ensures the http requests are load balanced.   Start/stop will be round-robined between pods so you may need to call stop a few times to shut everything down and start again.


