(ns clj_owasp_master.a8
  (:require [clojure.string :as str]))

(defn- treat-dot-commas [word]
  (-> word
      (str/replace "." "")
      (str/replace "," ".")
      read-string))

(defn string-to-double [string]
  (-> string
      treat-dot-commas
      eval))


(string-to-double "1111.1111,30")

(string-to-double "(println \"oi\")")

; https://www.nitor.com/en/news-and-blogs/pitfalls-and-bumps-clojures-extensible-data-notation-edn






(defn continue [chain path parameters  session-parameters]
  (if chain
    (let [next-one (first chain)]
      (next-one (rest chain) path parameters  session-parameters))))


(defn- rot13-char [c]
  (cond
    (Character/isLowerCase c) (char (+ (mod (+ 13 (- (int c) 97)) 26) 97))
    (Character/isUpperCase c) (char (+ (mod (+ 13 (- (int c) 65)) 26) 65))
    :else c))

(defn rot13 [word]
  (apply str (map rot13-char word)))

(println (rot13 "guilherme.silveira"))
; NA, HORRIVEL!!!!


(defn get-user [query-parameters session-parameters]
  (:username (merge query-parameters session-parameters)))

(println (get-user {:username "admin"} {:username "gui"}))



(defn get-user [query-parameters session-parameters]
  (:username (merge session-parameters query-parameters )))

(println (get-user {:username "admin"} {:username "gui"}))

(defn get-user [session-parameters]
  (:username session-parameters))

(println (get-user {:username "gui"}))





(defn headers-layer [chain path parameters session-parameters]
  (let [result (continue chain path parameters session-parameters)]
    (assoc-in result [:headers :X-framework] "Our Framework 3.1")))

(defn error-control-layer [chain path parameters session-parameters]
  (try (continue chain path parameters session-parameters)
       (catch Exception e {:error 500
                           :body  (str (.getMessage e))
                           })))


(defn log-layer [chain path parameters session-parameters]
  (println path)
  (continue chain path parameters session-parameters))


(defn execution-layer [chain path parameters session-parameters]
  (println "Executing for path" path)
  {:code 200, :body (str "result for " path)}, :cookies {})

(defn service [path parameters session-parameters]
  (let [chain [error-control-layer log-layer headers-layer execution-layer]]
    (continue chain path parameters session-parameters)))

; components might have the same problem
(println (service "/basic" {:password "hello"} {}))
(println (service "/upload" {:upload-file "hi.txt"} {}))







