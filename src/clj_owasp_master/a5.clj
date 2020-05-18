(ns clj_owasp_master.a5
  (:require [clojure.xml :as xml]))

(def lyrics {:geni-e-o-zepelim "/tmp/lyrics/geni-e-o-zepelim"})

(defn get-lyric [music-name]
  (->> lyrics
       ((keyword music-name))
       slurp))

; file not found, claro!
;(print
;  (get-lyric "geni-e-o-zepelim"))

; solucao 2: NAO TER PERMISSAO DE ACESSO PRA FOLDERS QUE NAO TEM PERTENCEM!!!



(defn db-is-valid-session-token [token]
  ;fake implementation
  true)

(defn user-is-logged? [session-params]
  (db-is-valid-session-token
    (:session-token session-params)))

(defn user-can-read-article? [user-id news-id]
  (let [db {"1314-ABCD" (set [678324])
            "1515-112D" (set [123456])}]
    (println "Checking" user-id news-id)
    (-> db
        (get user-id)
        (get news-id))))

(defn slug-to-id [article-slug]
  (let [db {"/owasp-new-report-is-out"  678324
            "/xpto"  123456}]
    (get db article-slug)))

(defn- load-order [order-id]
  {:order-id order-id
   :price    (rand-int 1000)})




; /order/?order-id=678324
(defn- order [query-params session-params]
  (if-let [user-id (:user-id session-params)]
    ; forgot to check ownership of the order
    (load-order (:order-id query-params))))

(defn update-article! [article-id]
  (println "Updated " article-id)
  true)

; /article/owasp-new-report-is-out/?news-id=678324 POST
(defn- edit-article [article-slug query-params session-params]
  (if-let [user-id (:user-id session-params)]
    (if-let [news-id (slug-to-id article-slug)]
      (if (user-can-read-article? user-id news-id)
        ; USOU O SLUG, UPDATOU O ID!
        (update-article! (:news-id query-params))
        (println "Can not read this article" article-slug news-id)))))

(edit-article "/owasp-new-report-is-out" {:news-id 678324} {:user-id "1314-ABCD"})
(edit-article "/owasp-new-report-is-out" {:news-id 622224} {:user-id "1314-ABCD"})



; /article/owasp-new-report-is-out/?news-id=678324 POST
(defn- edit-article [article-slug query-params session-params]
  (if-let [user-id (:user-id session-params)]
    (if-let [news-id (:news-id query-params)]
      (if (user-can-read-article? user-id news-id)
        (update-article! news-id)
        (println "Can not read this article" article-slug news-id)))))


(edit-article "/owasp-new-report-is-out" {:news-id 678324} {:user-id "1314-ABCD"})
(edit-article "/owasp-new-report-is-out" {:news-id 622224} {:user-id "1314-ABCD"})

;; DEPOIS RECLAMAR DE READ!+=WRITE

;(defn my-profile [query-params]
;  (load-from-db (:user-id query-params)))

;(defn my-profile [all-params]
;  (load-from-db (:user-id all-params)))
