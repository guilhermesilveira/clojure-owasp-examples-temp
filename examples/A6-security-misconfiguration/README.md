# A6 - Security Misconfiguration

Security misconfiguration is the most commonly seen issue. This is commonly a result of insecure default configurations, incomplete or ad hoc configurations, open cloud storage, misconfigured HTTP headers, and verbose error messages containing sensitive information. Not only must all operating systems, frameworks, libraries, and applications be securely configured, but they must be patched and upgraded in a timely fashion.

# Example

![datomic-console](https://docs.datomic.com/on-prem/console-window.png)
This is a web interface to interact with datomic. It does not provide authentication and it still possible to find servers exposing this service on the interet allowing attackers to operate the datomic and even gain remote code execution using a payload like this one.

```clojure
[:find ?a
 :where
 [(clojure.java.shell/sh "id") ?a]
]
```

# Fix
In this specific case never expose to the internet a service so powerful and fragile.
