## Demo video

[Watch the demo](https://drive.google.com/file/d/16BHglKDaNSQjww9PGMlGHT3Fvla4rJsU/view?usp=sharing)

## Any comments/notes for context

It seems like a lot of medical apps require users to sign in each time so I decided to go with that 
approach. However, I did set up logic to store the token so it could be changed to instead check 
the token on app launch to keep the user authenticated. It just depends on what behavior is wanted.

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
