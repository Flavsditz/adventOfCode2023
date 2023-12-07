# Advent of Code Kotlin Template

[Advent of Code][aoc] – an annual event in December since 2015.
Every year since then, with the first day of December, a programming puzzles contest is published every day for
twenty-four days.
A set of Christmas-oriented challenges provides any input you have to use to answer using the language of your choice.
We offer you a template prepared to use with [Kotlin][kotlin] language within this repository.

## Setup

After you create a new project based on the current template repository using the **Use this template** button, a bare
minimal scaffold will appear in your GitHub account with the following structure:

```
.
├── README.md               README file
├── build.gradle.kts        Gradle configuration created with Kotlin DSL
├── settings.gradle.kts     Gradle project settings
├── gradle*                 Gradle wrapper files
└── src/main/kotlin
    ├── DayXX.kt            A day's challenge file 
    ├── Helper.kt           A set of utility methods shared across the day challenges
    │                       
└── src/main/resources      (create those files manually)
    ├── dayXX.txt           An empty file for the Day's input data
    ├── dayXX_test.txt      An optional Day test input data used for checks
    │    
```

> [!NOTE]
>
> All task input files (`src/*.txt`) are excluded from the repository with `.gitignore` – we should not post them
> publicly, as [Eric Wastl requested for](https://twitter.com/ericwastl/status/1465805354214830081).

## Execution Times

| Day | Part | Execution Time (ms) |
|-----|:----:|--------------------:|
| 1   |  1   |                  26 |
|     |  2   |                  49 |
| 2   |  1   |                  37 |
|     |  2   |                  36 |
| 3   |  1   |                  68 |
|     |  2   |                  52 |
| 4   |  1   |                  40 |
|     |  2   |                 289 |
| 5   |  1   |                  21 |
|     |  2   |              312598 |
| 6   |  1   |                  10 |
|     |  2   |                  18 |
| 7   |  1   |                  48 |
|     |  2   |                  47 |

[aoc]: https://adventofcode.com

[kotlin]: https://kotlinlang.org