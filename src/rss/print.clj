(ns rss.print)

(defn print-rss-item [rss-items fields]
  (print (map #(select-keys % fields) rss-items)))