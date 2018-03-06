# WeatherApp

A simple, opensource weather app for Android. It gathers data from [OpenWeatherMap](https://openweathermap.org/), via their public API.

## Features
* Simple and intuitive design
* Current weather
* Detailed 5 day forecast
* Works with any city in the world
* Offline functionality (caching data)

## Used libraries:
* com.squareup.retrofit2:retrofit:2.3.0 - for work with Weather API.
* com.google.code.gson:gson:2.7 - working with JSON object.
* com.squareup.retrofit2:converter-gson:2.0.2 - convert JSON data.
* com.squareup.okhttp3:okhttp:3.6.0 - a part of Retrofit2.
* com.squareup.okhttp3:logging-interceptor:3.6.0
* com.squareup.picasso:picasso:2.5.2 - help getting images from site.

* com.android.support:appcompat-v7:26.+
* com.android.support.constraint:constraint-layout:1.0.2
* com.android.support:design:26+

## License
This application is Free Software ([GNU General Public License](https://www.gnu.org/licenses/gpl.html))
The weather data, list of cities and icons is provided by [OpenWeatherMap](https://openweathermap.org/), under the <a href='http://creativecommons.org/licenses/by-sa/2.0/'>Creative Commons license</a>.
