(require '[cheshire.core :as json]
         '[clj-http.client :as client]
         '[clojure.test :as test]
         '[go-rest-cucumber-tests.utils :as utils])

;; TODO: Consider to add check for empty email
;; TODO: Consider to add check for no autorization
;; TODO: Consider to add check for gender (male/female cases)
;; TODO: Figure out how to add a Teardown step
;; TODO: Nofity devs about typo in "can be male of female" (should be OR instead OF)
;; TODO: Notify devs that user cannot be created with the same email next day despite the fact it is not listed in users

(def state (atom {}))

(defn get-url []
  (get-in @state [:endpoint]))

(defn get-user []
  (get-in @state [:user]))

(defn get-post-response []
  (get-in @state [:post-response]))

(defn get-get-response []
  (get-in @state [:get-response]))

(defn parse-body [response]
  (-> (:body response)
      (json/parse-string true)))

(defn get-autorization-header []
  {:authorization (str "Bearer " utils/api-token)})

(Given #"^I have the endpoint \"([^\"]*)\"$" [url]
       (swap! state assoc :endpoint url))

(Given #"^I have the following user details:$" [table]
       (let [user (-> (utils/datatable->maps table) first)]
         (swap! state assoc :user user)))

(When #"^I send a POST request to create the user$" []
      (let [user     (get-user)
            response (client/post (get-url)
                                  {:headers (get-autorization-header)
                                   :form-params {:name   (:name user)
                                                 :email  (:email user)
                                                 :gender (:gender user)
                                                 :status (:status user)}})
            body     (-> (:body response)
                         (json/parse-string true))]
        (swap! state assoc :post-response response)
        (swap! state assoc :body body)))

(Then #"^the response status should be (\d+)$" [status]
      (assert (= (Long/parseLong status) (-> (get-post-response) :status))))

(Then #"^the response body should contain the user ID$" []
      (let [body (-> (get-post-response) (parse-body))]
        (assert (int? (:id body)))))

(Then #"^the response body should contain the following user details:$" [table]
      (let [expected-details (-> (utils/datatable->maps table) first)
            actual-details   (-> (get-post-response) (parse-body))]
        (doseq [[k v] expected-details]
          (assert (= v (get actual-details k))))))

(When #"^I send a GET request to get the user details$" []
      (let [user     (get-user)
            id       (-> (get-post-response)
                         (parse-body)
                         :id)
            url      (str (get-url) "/" id)
            response (client/get url
                                 {:headers (get-autorization-header)})
            body     (-> (:body response)
                         (json/parse-string true))]
        (swap! state assoc :get-response response)))

(Then #"^the response should contain the same user details$" []
      (let [expected-details (-> (get-post-response) (parse-body))
            actual-details   (-> (get-get-response) (parse-body))]
        (doseq [[k v] expected-details]
          (assert (= v (get actual-details k))))))

(Given #"^I have the same endpoint" []
       (swap! state dissoc :post-response :get-response))

(Given #"^I have the following existing user without other fields:$" [table]
       (let [user (-> (utils/datatable->maps table) first)]
         (swap! state assoc :user user)))

(When #"^I send a POST request to create the same user$" []
      (let [user (get-user)
            response (client/post (get-url)
                                  {:headers (get-autorization-header)
                                   :form-params {:name   (:name user)
                                                 :email  (:email user)
                                                 :gender (:gender user)
                                                 :status (:status user)}
                                   :unexceptional-status #(= 422 %)})
            body (-> (:body response)
                     (json/parse-string true))]
        (swap! state assoc :post-response response)
        (swap! state assoc :body body)))

(Then #"^the existing user response status should be (\d+)$" [status]
      (assert (= (Long/parseLong status) (-> (get-post-response) :status))))

(Then #"^the response body should contain the following error details:$" [table]
      (let [expected-details (-> (utils/datatable->maps table))
            actual-details   (-> (get-post-response) (parse-body))]
        (assert (utils/equal-collections? expected-details actual-details))))
