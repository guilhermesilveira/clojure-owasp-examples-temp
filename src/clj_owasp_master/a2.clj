(ns clj_owasp_master.a2
  (:use clojure.java.io)
  (:require [crypto.password.bcrypt :as password]))

(use '[clojure.java.shell :only [sh]])

(def my-db (atom {}))

(defn add [table doc] (swap! my-db update-in [table] conj doc))

; allows any user
(defn register-new-user! [username password]
  (add :users {:username username :password password}))

(register-new-user! "matheus.bernardes" "banana")







(defn read-file [f]
  (-> (slurp f)
      (clojure.string/split-lines)))

(def common-passwords (read-file "src/common.txt"))
(defn is-common? [key]
  (some #(= key %) common-passwords))
(print common-passwords)

(is-common? "gui")
(is-common? "guigui")


; allows any user
(defn register-new-user! [username password]
  (if (is-common? password)
    (throw (Exception. "Unable"))
    (add :users {:username username :password password})))

(register-new-user! "matheus.bernardes" "banana")
(register-new-user! "matheus.bernardes" "gui")





(def encrypted (password/encrypt "foobar"))
(password/check "foobar" encrypted) ;; => true


; allows any user
(defn register-new-user! [username pwd]
  (let [e-pwd (password/encrypt pwd)]
    (if (is-common? pwd)
      (throw (Exception. "Unable"))
      (add :users {:username username :password e-pwd}))))

(register-new-user! "matheus.bernardes" "banana")



