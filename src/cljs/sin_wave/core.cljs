(ns sin-wave.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))


(defn main [])

(.log js/console "hello clojurescript")

(def canvas (.getElementById js/document "myCanvas"))

(def ctx (.getContext canvas "2d"))

(.clearRect ctx 0 0 (.-width canvas) (.-height canvas))

(def interval js/Rx.Observable.interval)

(def canvas-time (interval 10))

(-> canvas-time
    (.take 5)
    (.subscribe (fn [n] (.log js/console n))))

(defn deg-to-rad [n]
   (* (/ Math/PI 180) n))

(defn sine-coord "sine coordinates" [x]
  (let [sin (Math/sin (deg-to-rad x))
        y (- 100 (* sin 90))]
    (.log js/console "sin is " sin)
    {:x x
     :y y
     :sin sin}))

(.log js/console (str (sine-coord 50)))

(def sine-wave
  (.map canvas-time sine-coord))

(defn fill-rect [x y color]
  (set! (.-fillStyle ctx) color)
  (.fillRect ctx x y 2 2))

(-> sine-wave
    (.take 600)
    (.subscribe (fn [{:keys [x y]}]
                  (fill-rect x y "orange"))))
