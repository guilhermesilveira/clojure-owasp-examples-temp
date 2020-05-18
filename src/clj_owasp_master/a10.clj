(ns clj_owasp_master.a10)

; armazenar os users e os acessos
; dizer que o ip estourou
; dizer que o usuario estourou
; dizer que a mesma senha estourou

(def db {:matheus.bernardes "banana"})
(def username-attempts (atom {}))
(def ip-attempts (atom {}))

; humm... better a safeguard on common password?
;(def password-attempts (atom {}))

(defn login [ip, username, password]
  (= (get db (keyword username)) password))

(println (login "12.31.32.22" "matheus.bernardes" "banana"))

(println (dotimes [_ 1000] (login "12.31.32.22" "matheus.bernardes" "banana")))


(defn my-inc [x]
  (if x (inc x)
        1))

(defn attempt-login [ip username password]
  (swap! username-attempts update-in [username] my-inc)
  (swap! ip-attempts update-in [ip] my-inc)
  ;(println "ha" @username-attempts @ip-attempts)
  (and (< (get  @username-attempts username) 30) (< (get  @ip-attempts ip) 100)))

; todo reset daily
; todo in the last 24 hours
; todo limit per failure not success

(defn login [ip, username, password]
  (let [k-ip (keyword ip)
        k-username (keyword username)]
    ;(println k-ip k-username)
    (if (attempt-login k-ip, k-username password)
      (= (get db k-username) password))
      (throw (Exception. "Ha"))))


(println (login "12.31.32.22" "matheus.bernardes" "banana"))

(println (dotimes [_ 1000] (login "12.31.32.22" "matheus.bernardes" "banana")))

