(ns clj_owasp_master.a4
  (:require [clojure.xml :as xml]))

(defn parse-document [xml-document]
  (xml/parse xml-document))

(defn get-document [uri]
  (->
    (slurp uri)
    (.getBytes "UTF-8")
    java.io.ByteArrayInputStream.
    (parse-document)))

;(get-document "my-file-or-uri-be-careful!!!")
;(get-document "user-inputted-file")
(println (get-document "src/nasty.xml"))

; SOLUTION IS LBIRARY DEPENDENT. VERY DANGERLOUS. ANY DESERIALIZATION WITH EXTERNAL ENTITY SUPPORT
; EVEN THE SLUYRP ALLOWS REMOTE FILE INJECTION!!!!
