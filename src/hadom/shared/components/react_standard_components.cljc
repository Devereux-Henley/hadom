(ns hadom.shared.components.react-standard-components
  (:require
   [hadom.shared.react-native :refer [ReactNative]]))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(def view (partial create-element (.-View ReactNative)))

(def text (partial create-element (.-Text ReactNative)))

(def image (partial create-element (.-Image ReactNative)))

(def touchable-highlight (partial create-element (.-TouchableHighlight ReactNative)))
