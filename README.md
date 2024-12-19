## Demo video

Main demo of the app:

<video src="https://github.com/ntheurer/FayInterview/blob/main/assets/Demo.mp4" controls="controls" style="max-width: 100%;"></video>

Timeout errors are also handled gracefully:

<video src="https://github.com/ntheurer/FayInterview/blob/main/assets/SocketTimeoutExceptionHandling.mp4" controls="controls" style="max-width: 100%;"></video>

## Any comments/notes for context

Some of the nice-to-haves / extra polish added:

- Material UI Theme used based on a primary color close to the Fay color
- Empty state for appointments overview screen (saying none found and offering a refresh button)
- Error handling for timeouts (if the service isn't used for a while then the first call will timeout)
- Material icons instead of basic shapes
- Sign up option links to the Fay website https://www.faynutrition.com/log-in
- Unit tests for handling logic
- Join Zoom button links to Zoom's website
- Loading spinners while backend calls are being made
- Dependency Injection with Dagger Hilt
- Token management with encrypted shared preferences

## Time spent on the three parts of the exercise
Login (UI and backend): 2 hours 50 minutes

Appointments overview (UI and backend): 3 hours 58 minutes

Nice to haves / polish: 3 hours 55 minutes

Total: 10 hours 43 minutes
