(ns clj_owasp_master.a3
  (:use clojure.java.io)
  (:require [crypto.password.bcrypt :as password]
            [clojure.data.json :as json]))

(use '[clojure.java.shell :only [sh]])

(def my-db (atom {}))

(defn add [table doc]
  (swap! my-db update-in [table] conj doc)
  )







(defn read-file [f]
  (-> (slurp f)
      (clojure.string/split-lines)))

(def common-passwords (read-file "src/common.txt"))
(defn is-common? [key]
  (some #(= key %) common-passwords))
(print common-passwords)

(is-common? "gui")
(is-common? "guigui")




(def encrypted (password/encrypt "foobar"))
(password/check "foobar" encrypted)                         ;; => true


; allows any user
(defn register-new-user! [username pwd]
  (print username pwd)                                      ; LOGOU, ZOOU
  (let [e-pwd (password/encrypt pwd)]
    (if (is-common? pwd)
      (throw (Exception. "Unable"))
      (add :users {:username username :password e-pwd}))))

(register-new-user! "matheus.bernardes" "banana")

(defn add [table doc]
  (swap! my-db update-in [table] conj doc)
  (spit "pass.txt" (json/write-str @my-db))
  )

(register-new-user! "matheus.bernardes" "banana")



(defn do-upload [parameters]
  (println "dealing with upload"))


(defn continue [chain path parameters]
  (if chain
    (let [next-one (first chain)]
      (next-one (rest chain) path parameters))))

(defn upload-layer [chain path parameters]
  (if (:upload-file parameters)
    (do-upload parameters))
  (continue chain path parameters))

(defn log-layer [chain path parameters]
  (println path parameters)
  (continue chain path parameters))

(defn execution-layer [chain path parameters]
  (println "Executing for path" path))

(defn service [path parameters]
  (let [chain [log-layer upload-layer execution-layer]]
    (continue chain path parameters)))

; components might have the same problem
(service "/upload" {:upload-file "hi.txt"})
(service "/basic" {:password "hello"})



