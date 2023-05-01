# PhaseRun

## Description

You ever want to run a marathon, but start to get nervous, thinking "Am I ready yet?". 
Well guess what? We are here to help! 
We built this project to help all types of runners locate those races that they have time to be ready for based on 
their running experience, activity level, age, and our special fitness calculation algorithm. 
With this web app, we remove the headache of sifting through the available races and only show the what you have time to train for.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Credits](#credits)
- [License](#license)

## Installation

1. Pull the Github Repo
   * Go to the repository at this link: https://github.com/PhaseRUN/PhaseRUN-Capstone
   * Click the green "Code" button to
   * Choose the wanted cloning method
     * Ex for ssh copy: git@github.com:PhaseRUN/PhaseRUN-Capstone.git
   * Connect to wanted IDE
     * Ex for Intellij: Select File -> New ->  Project from version control -> Paste copied ssh info into the url
2. Create an application.properties file in the src.main.resources directory
3. Register at https://runsignup.com to obtain an API key, then add it to the application.properties file
   * Check the example.properties in the resources folder to verify syntax
4. Setup up the application properties with mysql information
   * Use the example.properties as a reference on how to set up application properties
   * Ensure to replace the USERNAME, PASSWORD, & DATABASE_DB with your local information

## Usage

Provide instructions and examples for use. Include screenshots as needed.

To add a screenshot, create an `assets/images` folder in your repository and upload your screenshot to it. Then, using the relative filepath, add it to your README using the following syntax:

    ```md
    ![alt text](assets/images/screenshot.png)
    ```

## Credits

Developers:
* Joe Garcia: https://github.com/gijoe250
* Kenneth Hayles: https://github.com/KMHayles
* Robert Arroyos: https://github.com/robertarroyos
* Chase Medford: https://github.com/ChaseLeeM
* Alexia Briones: https://github.com/lexibor

Third-party assets:
* Race Information API: https://runsignup.com
* Image File Saving API: https://www.filestack.com

Institution:
* Thank you to our instructors at Codeup: https://codeup.com/

## License

MIT License

Copyright (c) 2023 Joe Garcia, Kenneth Hayles, Robert Arroyos, Chase Medford, Alexia Briones

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
---
