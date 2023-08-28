# 🪄 _(WIP)_ Compose Nav Transitions

###### A Jetpack Compose Library built on top of the Jetpack Compose Navigation Library to provide easy to use transitions between screens.

![Maven Central](https://img.shields.io/maven-central/v/dev.jianastrero.compose-nav-transitions/compose-nav-transitions?style=for-the-badge)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Static Badge](https://img.shields.io/badge/Jetpack_Compose-37bf6e?style=for-the-badge&logo=data%3Aimage%2Fpng%3Bbase64%2CiVBORw0KGgoAAAANSUhEUgAAAA0AAAAOCAYAAAD0f5bSAAAABHNCSVQICAgIfAhkiAAAAAFzUkdCAK7OHOkAAAAEZ0FNQQAAsY8L%2FGEFAAAACXBIWXMAAA7DAAAOwwHHb6hkAAAAX3pUWHRSYXcgcHJvZmlsZSB0eXBlIEFQUDEAAAiZ40pPzUstykxWKCjKT8vMSeVSAANjEy4TSxNLo0QDAwMLAwgwNDAwNgSSRkC2OVQo0QAFmJibpQGhuVmymSmIzwUAT7oVaBst2IwAAAFlSURBVChTY6AbYITScGD%2FtEfk37fvrQyMjBH%2FGRhmMTP%2Fbj2o2PgBKg0GcE32%2B%2BtZ%2FsmxZjD8%2B98E1CAIFWYAanzDyMjYwKT0a%2BZBxsY%2FIDEmsAwQfPjweS9QxWQgFvz74QvD76dvGP5%2B%2FMLA%2BO%2B%2FCFBsyv8dJvuhShmYoTTDx5ucM78%2F%2BMjMzfufIVDGgyFbMZGB7TcTw53bTxmYzwUyMN03kHiwr70FpBZuE8gh7448ZeBf%2B53BV8KNgZuZi8FPwp1B%2B4kHA8MrIagaCEDSBAEYIYMFoGpiZmE4fvwow%2BzpExk%2Bf%2FnKsOngLYar9z4yMDKzQxVAANxgDn3Hi8BQ02P495fh35c3DBKKVgzy5tkMLFwKQGEWkOv37qvhcQGphdv047eIKcO%2Ff8X%2FmZjfM%2FGJM7CKGjKwcquANLxhYPiXIXxxmztUKSbgNXUW5tB3mqgcMe2TY%2BuXyW71H1BDgY6AgQEAC35v3JCnE5EAAAAASUVORK5CYII%3D)
[![GitHub license](https://img.shields.io/github/license/jianastrero/compose-nav-transitions?style=for-the-badge)](https://github.com/jianastrero/compose-nav-transitions/blob/main/LICENSE)

## Demo

https://github.com/jianastrero/compose-nav-transitions/assets/7688625/067a78b5-a996-4afa-91c1-8d6e7c20da61

![Magic GIF](assets/magic.gif)

## Installation

```kotlin
// This library is built on top of Navigation Compose Library, You need to add it to your project first
// https://developer.android.com/jetpack/compose/navigation
implementation("androidx.navigation:navigation-compose:2.7.1")

implementation("dev.jianastrero.compose-nav-transitions:compose-nav-transitions:0.2.0-alpha01")
```

<details>

    <summary><h3>Version Guide</h3></summary>

| Compose Nav Transitions | Navigation Compose | Demo                                             |
  |-------------------------|--------------------|--------------------------------------------------|
| 0.2.0-alpha01           | 2.7.1              | ![v0.2.0-alpha01.gif](assets/v0.2.0-alpha01.gif) |
| 0.1.0-alpha01           | 2.7.1              | ![v0.1.0-alpha01.gif](assets/v0.1.0-alpha01.gif) |

</details>

## Usage

### Use NavTransitionHost instead of NavHost

```kotlin
NavTransitionHost(
    navController = navController,
    startDestination = "Home",
    modifier = Modifier.fillMaxSize()
) {
    /* Rest of your code */
}
```

### Use transitionComposable instead of composable

```kotlin
transitionComposable("home") {
    /* Rest of your code */
}
```

### Use sharedElement(tag) modifier for the views you want to animate

```kotlin
transitionComposable("home") {
    Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Home",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(32.dp)
                .sharedElement("text") // <-- Add this
                .padding(4.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.sample),
            contentDescription = "Sample Image",
            modifier = Modifier
                .size(100.dp)
                .clickable { navController.navigate("detail") }
                .sharedElement("image") // <-- Add this
        )
    }
}
```

## Contributing to Compose Nav Transitions

I love your input! I want to make contributing to this project as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer
- Or even just giving feedback

### How to contribute

To contribute to Compose Nav Transitions, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the original branch: `git push origin ComposeNavTransitions/main`
5. Create the pull request.

_Alternatively see the GitHub documentation
on [creating a pull request](https://docs.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request)._

### Any contributions you make will be under the MIT Software License

In short, when you submit code changes, your submissions are understood to be under the same [**MIT License
**](https://choosealicense.com/licenses/mit/)

### Write bug reports with detail, background, and sample code

Great Bug Reports tend to have:

- A quick summary and/or background
- Video (if possible) showing the problem
- Steps to reproduce
    - Be specific!
    - Give sample code if you can.
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or stuff you tried that didn't work)

### License

By contributing, you agree that your contributions will be licensed under its [**MIT License**](LICENSE).

<details>
    <summary><h3>LICENSE (MIT)</h3></summary>

    MIT License
    
    Copyright (c) 2023 Jian James Astrero
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

</details>
