# go-rest-cucumber-tests

Automation tests for testing user creation operation of the https://gorest.co.in API

## Usage

In `resources` folder rename `secrets-example.edn` file into `secrets.edn` and replace `place-your-api-token-here` in it with a valid access token (you can generate one on gorest.co.in).

Execute `lein cucumber` to run tests.

Cucumber test scenarios can be found under `features/create_user.feature` file.
