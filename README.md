![notetion_cover](/assets/cover/notetion_cover.jpg)

# Notetion: AI Study Companion

Notetion is an AI-powered study companion app designed for Android. It addresses the challenges students face in organizing and comprehending study materials by leveraging Generative AI to offer features like scanning handwritten notes, automatic text correction, and AI-powered question generation. Notetion streamlines the study process by making note-taking more efficient and effective, ultimately supporting students in their exam preparation and overall learning.

Research indicates that while 66% of students prefer digital note-taking, complexities and inefficiencies remain. Notetion offers an accessible solution to enhance the digital note-taking experience. With its intuitive features and advanced AI integration, Notetion helps students overcome these hurdles, better organize their study materials, improve their understanding, and achieve their academic goals.

## Screenshots

<table>
  <tbody>
    <tr>
      <td><img src="assets/screenshot/home_screen.png?raw=true"/></td>
      <td><img src="assets/screenshot/scan_from_camera.png?raw=true"/></td>
      <td><img src="assets/screenshot/text_corrector.png?raw=true"/></td>
      <td><img src="assets/screenshot/quiz_screen.png?raw=true"/></td>
    </tr>
  </tbody>
</table>

## Features

- Note Management: Create, edit, and organize notes in one place.
- Text Editor: Write, edit, and format notes with ease.
- Scan From Camera: Convert handwritten or printed documents into digital text quickly and accurately.
- AI Text Corrector: Automatically correct writing and grammatical errors in notes.
- AI Generate Questions: Create practice questions from note material to help with exam preparation.

## Architecture

This diagram provides a high-level overview of the app's architecture and data flow:

![notetion_architecture](/assets/diagram/notetion_architecture.jpg)

> _The diagram illustrates how the app utilizes the Gemini API for natural language processing, integrates with ML Kit for text recognition, and stores data using Room and SQLite._

## Installation

To build this project, you need the latest stable version
of [Android Studio](https://developer.android.com/studio)

1. Clone or download the project and open it in Android Studio.
2. Create a `local.properties` file in the project root folder if it doesn't exist.
3. Add the following lines to the `local.properties` file:
   ```android
   ...

   GEMINI_API_KEY = "your gemini api key here"
   ```

4. Get your API keys from [Gemini API key](https://aistudio.google.com/apikey), and replace the placeholders with your keys.
5. Sync the project with Gradle and run the app on an Android emulator or a physical Android device.

## License

Notetion is open-source and released under the [MIT License](LICENSE).
