# go-rest-cucumber-tests

Automation tests for testing user creation operation of the https://gorest.co.in API

## Usage

In `resources` folder rename `secrets-example.edn` file into `secrets.edn` and replace `place-your-api-token-here` in it with a valid access token (you can generate one on gorest.co.in).

Execute `lein cucumber` to run tests.

Cucumber test scenarios can be found under `features/create_user.feature` file 


## License

Copyright © 2024 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
