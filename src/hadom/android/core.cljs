(ns hadom.android.core
  (:require
   [compassus.core :as compassus]
   [hadom.shared.react-native :refer [ReactNative]]
   [hadom.shared.components.react-standard-components :as components]
   [om.next :as om :refer-macros [defui]]
   [re-natal.support :as sup]
   [hadom.state :as state]))

(set! js/window.React (js/require "react"))

(def logo-img (js/require "./images/cljs.png"))

(defui Index
  static om/IQuery
  (query [this]
    '[:app/msg])
  Object
  (render [this]
    (let [{:keys [app/msg]} (om/props this)]
      (components/view {:style {:flexDirection "column" :margin 40 :alignItems "center"}}
        (components/text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center"}} msg)
        (components/image {:source logo-img
                           :style  {:width 80 :height 80 :marginBottom 30}})
        (components/touchable-highlight {:style   {:backgroundColor "#999" :padding 10 :borderRadius 5}
                                         :onPress #(components/alert "HELLO!")}
          (components/text {:style {:color "white" :textAlign "center" :fontWeight "bold"}} "This is a button"))))))

(defui About
  Object
  (render [this]
    (components/text {:style {:fontSize 30 :fontWeight "100" :textAlign "center"}} "This is the about page.")))

(defui Wrapper
  Object
  (render [this]
    (let [{:keys [owner factory props]} (om/props this)
          route (compassus/current-route this)]
      (components/view {:style {:flexDirection "column"
                                :alignItems "stretch"
                                :flex 1
                                :backgroundColor "white"}}
        (components/text {:style {:fontSize 30 :fontWeight "100" :textAlign "center"}} "Navigation Wrapper")
        (components/touchable-highlight {:onPress #(compassus/set-route! this :home)}
          (components/text {:style {:color "black" :fontWeight "bold"}} "Home"))
        (components/touchable-highlight {:onPress #(compassus/set-route! this :about)}
          (components/text {:style {:color "blue" :fontWeight "bold"}} "About"))
        (factory props)))))

(defonce RootNode (sup/root-node! 1))
(defonce app-root (om/factory RootNode))
(def app-registry (.-AppRegistry ReactNative))

(def app
  (compassus/application
    {:routes {:home Index
              :about About}
     :index-route :home
     :reconciler state/reconciler
     :mixins [(compassus/wrap-render Wrapper)]}))

(defn init []
  (compassus/mount! app 1)
  (.registerComponent app-registry "Hadom" (fn [] app-root)))
