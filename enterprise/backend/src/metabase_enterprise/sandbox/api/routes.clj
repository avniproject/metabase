(ns metabase-enterprise.sandbox.api.routes
  "Multi-tenant API routes."
  (:require [compojure.core :as compojure]
            [metabase-enterprise.sandbox.api.gtap :as gtap]
            [metabase-enterprise.sandbox.api.table :as table]
            [metabase-enterprise.sandbox.api.user :as user]
            [metabase.server.middleware.auth :as middleware.auth]))

;; Duplicated with `metabase.api.routes` but prevents circular deps between the two namespaces.
(def ^:private +auth
  "Wrap `routes` so they may only be accessed with proper authentiaction credentials."
  middleware.auth/enforce-authentication)

(compojure/defroutes ^{:doc "Ring routes for mt API endpoints."} routes
  (compojure/context
   "/mt"
   []

   (compojure/routes
    (compojure/context "/gtap" [] (+auth gtap/routes))
    (compojure/context "/user" [] (+auth user/routes))))
  (compojure/context "/table" [] (+auth table/routes)))
