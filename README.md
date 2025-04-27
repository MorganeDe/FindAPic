# Fin A Pic

Find A Pic is a small app to search images and add them to your favorites.

# Main information

- The app is written in Kotlin with Jetpack Compose.
- The API used to search for images
  is [Pexels](https://www.pexels.com/api/documentation/#photos-search).

## API Key

The API key is not versioned and you should get yours by doing the following:

1. Register on [Pexels](https://www.pexels.com/api/)
2. Request your API key
3. At the root of the app, create the `apikeys.properties` file
4. In `apikeys.properties`, add the following: `pexels_api_key=[REPLACE_WITH_YOUR_API_KEY]`

# Features

## Search an image

The first screen you will see is the `Search` screen.
You will find a search bar and once you have typed in a topic, you may see the result images.

## Add / remove an image to / from favorites

Once you have searched for images, you can add or remove them to your favorites by clicking on the
heart in the image card.

## See your favorite images

The second screen in the app is the `Favorites` screen.
It will gather all your favorite images. You can remove an image the same way as described above.

## Empty results and error handling

- If a search has no results or no favorites are found, you will see a small explaining text.
- If there are any error while fetching resources, you will also see an explaining text with the
  error message.

# Frameworks and libraries

- Dependency injection: Koin
- Async image loading: Coil
- UI: Jetpack Compose with Material 3
- Networking: OkHttp, Retrofit
- Local storage: Room
- Tests: Mockk, Truth, JUnit 4

# Architecture

This app is written using MVVM + Clean Architecture patterns.

The app follows the following structure:

- commons_io: package for common IO operations helpers
- data: package gathering local and rest layer as well as the implementation of the repository
- di: package for dependency injection configuration
- domain: package for all business logic. This is where there are use case declarations with their
  implementations and the repository interface.
- ui: package for all UI logic. This is where there are the Composables, ViewModels and navigation.
  Each sub-package represents a feature (search, favorites).

# Local storage strategy

There are several ways to save favorite images.

Here, we store directly the whole image in the Room database, not just its id.
This way, the favorite images are directly retrieved from the local storage (as they are not likely
to change frequently) and so you can still see your favorites even in offline mode.

Alternatively, we could have stored only the image id and then used the API with OkHttp network
cache to retrieve each image by id (in the favorites screen).
This means that our only source of truth would be the API and thus we would have to establish a
cache invalidation.

# Testing

What is tested:

- Models extensions.
- Repositories.
- Use cases.
- View models.

There are no UI tests because it did not seem relevant without proper UI/UX rules.

# What can be improved

## Features

- Automatically hide keyboard after searching.
- Enable to clear the input in the search bar.
- Add placeholder for images that will not load / image source errors.
- Use image page link additionally to photographer and Pexels credits.
- Pagination could be handled to load more images (here we only fetch the first 15 results in the
  first page).
- Search locale could be handled.
- Accessibility.

## UI

- Dimensions are hardcoded and could be centralized.

## Tests

- The Image DAO could be tested integrally.
- Each Composable could be tested.