# Writer Effect Plugin for IntelliJ IDEA

A dynamic IntelliJ IDEA plugin that adds a typewriter-style animation to your code editor, simulating live typing by rewriting the current file character by character.

## Features

- **Typewriter Animation**: Animates the current file content with a character-by-character typing effect
- **Customizable Timing**: Configure the delay between characters (in milliseconds)
- **Initial Wait Period**: Set a wait time before the animation begins (in seconds)
- **Stop and Restore**: Instantly stop the animation and restore the original content
- **Smart Caret Positioning**: Automatically moves the caret and scrolls to keep the typing visible

## Installation

1. Open IntelliJ IDEA
2. Go to `File → Settings → Plugins`
3. Search for "Writer Effect"
4. Install the plugin and restart IntelliJ IDEA

## Usage

### Starting the Writer Effect

1. Open any file in the editor
2. Go to `Tools → Writer Effect: Start` or use the action search (`Ctrl+Shift+A` / `Cmd+Shift+A`)
3. Configure the animation settings:
   - **Character delay**: Time between each character (default: 50ms)
   - **Initial wait**: Delay before animation starts (default: 0 seconds)
4. The animation will begin after the specified wait time

### Stopping the Writer Effect

- Click the "Writer Effect: Stop" button in the main toolbar
- The animation will stop immediately and restore the original file content

## Use Cases

Perfect for:
- **Content Creators**: Create engaging code demonstrations for videos
- **Educators**: Develop interactive coding lessons and tutorials
- **Presenters**: Add visual flair to code presentations
- **Live Streamers**: Make coding sessions more dynamic and engaging
- **Developers**: Add some fun to your coding workflow

## Technical Details

The plugin is built using:
- **Kotlin**: Modern JVM language with coroutines support
- **IntelliJ Platform SDK**: Native integration with IntelliJ IDEA
- **Coroutines**: Non-blocking animation with proper cancellation support
- **EDT Safety**: Thread-safe document modifications

## Actions

The plugin provides two main actions:

1. **Writer Effect: Start** (`WriterEffectAction`)
   - Location: Tools menu
   - Starts the typewriter animation with customizable settings

2. **Writer Effect: Stop** (`StopWriterEffectAction`)
   - Location: Main toolbar
   - Stops the current animation and restores original content

## Configuration

The plugin prompts for configuration each time you start the effect:

- **Character Delay**: Minimum 1ms, default 50ms
- **Initial Wait**: Minimum 0 seconds, default 0 seconds

## Requirements

- IntelliJ IDEA (Community or Ultimate Edition)
- Java 21 or higher
- Kotlin API version 2.1

## Contributing

This plugin is open source and welcomes contributions! Please visit the [GitHub repository](https://github.com/kys0ff) for:
- Bug reports
- Feature requests
- Code contributions
- Documentation improvements

## License

Please refer to the project repository for licensing information.

## Support

For support, bug reports, or feature requests, please visit the project's GitHub repository or contact the developer at support@kys0ff.dev.

---

*Make your code come alive with Writer Effect!*
