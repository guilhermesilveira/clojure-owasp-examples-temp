(ns clj_owasp_master.a1
  (:use clojure.java.io)
  )

(use '[clojure.java.shell :only [sh]])

(def key-path "/tmp/a1/")

(defn generate-rsa-key [{:keys [key-name]}]
      (let [key (str key-path key-name)
            dir (make-parents key)
            command (str "openssl genrsa -out " key " 2048")]
           (sh "bash" "-c" command)))

(generate-rsa-key {:key-name "certificate"})
(generate-rsa-key {:key-name "; ls #"})





; solution escape or library (sanitize, validate)

(defn run-cluster [config-file]
  (let [command (str "/bin/kafka " config-file)]
    (sh "bash" "-c" command)))

(run-cluster {:key-name "; ls / #"})





; solution : library or escape

(defn login [username password]
  (let [sql (str "select * from Users where username='" username "' and password='" password "'")]
    (print sql)))

(login "guilherme" "senha")
(login "guilherme" "' or id=1 having '1'='1")
(login "guilherme" "' or admin=1 having '1'='1")

