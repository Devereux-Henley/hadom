(ns hadom.state
  (:require
   [compassus.core :as compassus]
   [om.next :as om]
   [re-natal.support :as sup]))

(defonce app-state (atom {:app/msg "Welcome to Hadom"}))

(defmulti read om/dispatch)
(defmethod read :default
  [{:keys [state]} k _]
  (let [st @state]
    (if-let [[_ v] (find st k)]
      {:value v}
      {:value :not-found})))

(defonce reconciler
  (om/reconciler
    {:state        app-state
     :parser       (compassus/parser {:read read})
     :root-render  sup/root-render
     :root-unmount sup/root-unmount}))
