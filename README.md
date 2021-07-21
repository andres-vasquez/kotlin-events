# Kotlin Events

Android Kotlin App with Events in NYC

## Installation

Use Android Studio 4.1.1 or newer.

## API
The app is using the Ticket Master API described below:
https://developer.ticketmaster.com/products-and-docs/apis/getting-started/

As this app is a Demo purposes project, the call will be placed for my next trip 😊 ​


## Architecture
The app is using the suggested MVVM architecture separating the data access repository in a new module.
You can find a lot of tools inside this project as:
- JetPack compose components: Paging, Navigation, MotionLayout
- Architecture components: ViewModel, LiveData, Room
- Third-party libraries: retrofit, glide, Timber, Espresso

### Other considerations
- Multiple flavors: debug, preprod, release
- Databinding
- Unit and instrumentation testing.
- CI/CD with GitHub actions

## Important config variables
In the `gradle.properties` file you will found the following variables:

```java
#build variabless
API_KEY_DEBUG="BX3nSAOazE4ysAVlUMuu19QLsiPAHYLu"
API_KEY_PREPROD=""
API_KEY_RELEASE=""

#Gmaps
GMAP_API_KEY_DEBUG="AIzaSyAXuIop3cM_kGWGXkub6o3F-lz9qZizLiU"
GMAP_API_KEY_PREPROD=""
GMAP_API_KEY_RELEASE=""
```

Please consider getting your own API KEYS because the one described in the project may be suspended.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)

## Developed by
- [Andrés Vasquez](mailto:andres.vasquez.agramont@gmail.com)