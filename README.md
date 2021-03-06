# AndroidBootcamp-GoogleImageSearch
Google Image Search project for Android Bootcamp at Yahoo!

![googleimagesearchdemo](https://cloud.githubusercontent.com/assets/1939291/5996549/d5156104-aa60-11e4-8bbd-0e48399f9a88.gif)

Forgot to show the infinite scroll (oops...):

![googleimagesearchdemo2](https://cloud.githubusercontent.com/assets/1939291/5996552/daa3895c-aa60-11e4-8ace-f43721391fb2.gif)

**User Stories:**
* [x] User can enter a search query that will display a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of advanced search options to filter results
* [x] User can configure advanced search filters such as:
  * Size (small, medium, large, extra-large)
  * Color filter (black, blue, brown, gray, green, etc...)
  * Type (faces, photo, clip art, line art)
  * Site (espn.com)
* [x] Subsequent searches will have any filters applied to the search results
* [x] User can tap on any image in results to see the image full-screen
* [x] User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

**Advanced Stories:**
* [x] Robust error handling, check if internet is available, handle error cases, network failures
* [x] Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* [x] User can share an image to their friends or email it to themselves
* [x] Replace Filter Settings Activity with a lightweight modal overlay
* [x] Improve the user interface and experiment with image assets and/or styling and coloring

**Bonus Stories:**
* [x] Use the StaggeredGridView to display improve the grid of image results
* [x] User can zoom or pan images displayed in full-screen detail view
