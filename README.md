Dorota: news ticker in Play
=====================================

* Toy app that demonstrates how to use Guice to test futures (in this case, twitter futures returned by [Storehaus](https://github.com/twitter/storehaus), specifically)
* Takes news sources (RSS) and broadcasts them as "ticker" web application

## Dependencies
* [redis](http://redis.io/), at least version 2.6.16
* [Play](http://www.playframework.com/) -- "The High Velocity Web Framework For Java and Scala"

## Getting started
Assuming Play is installed and `redis-server` is running, just build the assembly:

```
./sbt update assembly
```
Then start the Play console:
```
play
```
And run:
```
[dorota] $ run
```
The application is now running in development mode. Open a browser at http://localhost:9000/

## What's with the name
* Named after [the fictional Dorota](http://gossipgirl.wikia.com/wiki/Dorota_Kishlovsky)

## License
```
Copyright 2013 Michael Ruggiero Sharethrough

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
